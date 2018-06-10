package org.interview.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.interview.controller.vo.TempoEventoVO;
import org.interview.db.Colaborador;
import org.interview.db.Evento;
import org.interview.helpers.ValidadorHelper;
import org.interview.repository.ColaboradorRepo;
import org.interview.repository.EventoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ColaboradorServico {
	private static final int MINUTOS_POR_HORA = 60;
	private static final int SECONDOS_POR_MINUTE = 60;
	private static final int SEGUNDOS_POR_HORA = SECONDOS_POR_MINUTE * MINUTOS_POR_HORA;
	private static final int SABADO = 5;
	private static final int DOMINGO = 6;

	@Autowired
	private ColaboradorRepo colaboradorRepo;

	@Autowired
	private EventoRepo eventoRepo;

	@Transactional(readOnly = true)
	public List<Colaborador> obterTodos() {
		return colaboradorRepo.findAll();
	}

	/***
	 * Obtém as horas trabalhadas do dia pelo colaborador.
	 * 
	 * @param idColaborador
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public Object obterHorasTrabalhadasDia(String idColaborador) throws Exception {
		ValidadorHelper.validarIdentificador(idColaborador);

		List<Evento> eventos = eventoRepo.buscarEventosDiarioColaborador(idColaborador, LocalDate.now());

		ValidadorHelper.validarExistenciaEventos(idColaborador, eventos);

		return calcularEventosDia(eventos);
	}

	/***
	 * Calcula total de horas trabalhadas do dia.
	 * 
	 * @param eventos
	 * @return TempoEventoVO
	 */
	public TempoEventoVO calcularEventosDia(List<Evento> eventos) {
		LocalDateTime dtIniExp = null;
		LocalDateTime dtFimExp = null;
		LocalDateTime dtIniDesc = null;
		LocalDateTime dtFimDesc = null;
		for (Evento evento : eventos) {
			switch (evento.getTipoEvento()) {
			case INICIO_EXPEDIENTE:
				dtIniExp = evento.getDataHoraEvento();
				break;
			case FIM_EXPEDIENTE:
				dtFimExp = evento.getDataHoraEvento();
				break;
			case INICIO_DESCANSO:
				dtIniDesc = evento.getDataHoraEvento();
				break;
			case FIM_DESCANSO:
				dtFimDesc = evento.getDataHoraEvento();
				break;
			default:
				break;
			}
		}
		long segundosTrabalhados = obterSegundosTrabalhados(dtIniExp, dtFimExp, dtIniDesc, dtFimDesc);
		return obterClock(segundosTrabalhados);
	}

	private TempoEventoVO obterClock(long segundosTrabalhados) {
		long hora = segundosTrabalhados / SEGUNDOS_POR_HORA;
		long minuto = ((segundosTrabalhados % SEGUNDOS_POR_HORA) / SECONDOS_POR_MINUTE);
		long segundo = (segundosTrabalhados % SECONDOS_POR_MINUTE);
		return new TempoEventoVO(parseLongInteger(hora), parseLongInteger(minuto), parseLongInteger(segundo));
	}

	private long obterSegundosTrabalhados(LocalDateTime dtIniExp, LocalDateTime dtFimExp, LocalDateTime dtIniDesc,
			LocalDateTime dtFimDesc) {
		Duration duracaoPrimeiroExpediente = null;
		Duration duracaoSegundoExpediente = null;

		boolean temAdicionalNoturno = false;
		boolean temAdicionalSabado = false;
		boolean temAdicionalDomingo = false;

		if (dtIniExp != null && dtIniDesc != null) {

			duracaoPrimeiroExpediente = Duration.between(dtIniExp, dtIniDesc);

			temAdicionalSabado = existeAdicionalSabado(dtIniExp);
			temAdicionalDomingo = existeAdicionalDomingo(dtIniExp);
			temAdicionalNoturno = existeAdicionalNoturno(dtIniDesc, dtIniExp);
		}

		if (dtFimDesc != null && dtFimExp != null) {
			duracaoSegundoExpediente = Duration.between(dtFimDesc, dtFimExp);

			temAdicionalSabado = existeAdicionalSabado(dtIniExp);
			temAdicionalDomingo = existeAdicionalDomingo(dtIniExp);
			temAdicionalNoturno = existeAdicionalNoturno(dtFimExp, dtFimDesc);

		}

		if (duracaoPrimeiroExpediente != null && duracaoSegundoExpediente == null) {
			long seconds = duracaoPrimeiroExpediente.getSeconds();
			if (temAdicionalSabado) {
				return obterSegundosTrabalhadosSabado(seconds);
			}

			if (temAdicionalDomingo) {
				return obterSegundosTrabalhadosDomingo(seconds);
			}

			if (temAdicionalNoturno) {
				return obterSegundosTrabalhadosNoturno(seconds);
			}
			return seconds;

		} else if (duracaoPrimeiroExpediente != null && duracaoSegundoExpediente != null) {

			long seconds = duracaoPrimeiroExpediente.plus(duracaoSegundoExpediente).getSeconds();

			if (temAdicionalSabado) {
				return obterSegundosTrabalhadosSabado(seconds);
			}

			if (temAdicionalDomingo) {
				return obterSegundosTrabalhadosDomingo(seconds);
			}

			if (temAdicionalNoturno) {
				return (long) (seconds + (seconds) * (0.2));
			}

			return seconds;
		} else {
			return 0L;
		}
	}

	/***
	 * Verifica horas adicionais do sábado.
	 * 
	 * @param dtIniExp
	 * @return
	 */
	private boolean existeAdicionalDomingo(LocalDateTime dtIniExp) {
		if (dtIniExp.getDayOfWeek().ordinal() == DOMINGO) {
			return true;
		}
		return false;
	}

	/***
	 * Verifica horas adicionais do domingo.
	 * 
	 * @param dtIniExp
	 * @return
	 */
	private boolean existeAdicionalSabado(LocalDateTime dtIniExp) {
		if (dtIniExp.getDayOfWeek().ordinal() == SABADO) {
			return true;
		}
		return false;
	}

	/***
	 * Obtem segundos tarbalhados noturno
	 * 
	 * @param seconds
	 * @return
	 */
	private long obterSegundosTrabalhadosNoturno(long seconds) {
		return (long) (seconds + (seconds * (0.2)));
	}

	/***
	 * Obtem segundos trabalhados no domingo.
	 * 
	 * @param seconds
	 * @return
	 */
	private long obterSegundosTrabalhadosDomingo(long seconds) {
		return (long) (seconds * 2);
	}

	/***
	 * Obtem segundos trabalhados no sábado.
	 * 
	 * @param seconds
	 * @return
	 */
	private long obterSegundosTrabalhadosSabado(long seconds) {
		return (long) (seconds + (seconds * (0.5)));
	}

	/***
	 * Verifica se existe adicional noturno
	 * 
	 * @param dtFimExp
	 * @param dtFimDesc
	 * @return true ou false
	 */
	private boolean existeAdicionalNoturno(LocalDateTime dtFimExp, LocalDateTime dtFimDesc) {
		boolean after = dtFimDesc.isAfter(
				LocalDateTime.of(dtFimDesc.getYear(), dtFimDesc.getMonth(), dtFimDesc.getDayOfMonth(), 22, 0, 0));

		boolean before = dtFimExp
				.isBefore(LocalDateTime.of(dtFimExp.getYear(), dtFimExp.getMonth(), dtFimExp.getDayOfMonth(), 6, 0, 0));
		if (after && before) {
			return true;
		}
		return false;
	}

	private Integer parseLongInteger(Long valor) {
		return Optional.ofNullable(valor).map(Long::intValue).orElse(null);
	}

	/***
	 * Obtém as horas trabalhadas do dia pelo colaborador.
	 * 
	 * @param idColaborador
	 * @return TempoEventoVO
	 * @throws Exception
	 */
	public Object obterHorasTrabalhadasMes(String idColaborador) throws Exception {
		ValidadorHelper.validarIdentificador(idColaborador);
		List<Evento> eventos = eventoRepo.findAll();

		ValidadorHelper.validarExistenciaEventos(idColaborador, eventos);

		List<Evento> eventosFiltrados = eventos.stream()
				.filter(evn -> (evn.getColaborador().getId().equals(idColaborador)
						&& evn.getDataEvento().getMonth().equals(LocalDate.now().getMonth())))
				.collect(Collectors.toCollection(() -> new ArrayList<Evento>()));

		List<TempoEventoVO> clocksDiario = new LinkedList<TempoEventoVO>();
		List<Evento> eventosDoDia = new LinkedList<Evento>();
		for (Evento evento : eventosFiltrados) {
			if (eventosDoDia.contains(evento)) {
				continue;
			}
			eventosDoDia = eventos.stream().filter(evn -> evn.getDataEvento().equals(evento.getDataEvento()))
					.collect(Collectors.toCollection(() -> new ArrayList<Evento>()));
			clocksDiario.add(calcularEventosDia(eventosDoDia));
		}
		long seconds = 0L;
		for (TempoEventoVO clockVO : clocksDiario) {
			long segundosClock = (clockVO.getHora() * 3600) + (clockVO.getMinuto() * 60) + clockVO.getSegundo();
			seconds += segundosClock;
		}
		return obterClock(seconds);
	}

}
