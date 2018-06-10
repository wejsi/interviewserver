package org.interview.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.interview.controller.vo.ClockDetails;
import org.interview.db.Colaborador;
import org.interview.db.Evento;
import org.interview.db.TipoEvento;
import org.interview.helpers.EventoHelper;
import org.interview.helpers.ValidadorHelper;
import org.interview.repository.ColaboradorRepo;
import org.interview.repository.EventoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventoServico {

	@Autowired
	private EventoRepo eventoRepo;

	@Autowired
	private ColaboradorRepo colaboradorRepo;

	@Autowired
	private ColaboradorServico colaboradorServico;

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Evento registrar(String idColaborador) throws Exception {
		Colaborador colaborador = colaboradorRepo.getOne(idColaborador);
		List<Evento> eventos = eventoRepo.buscarEventosDiarioColaborador(idColaborador, LocalDate.now());
		Evento e = processarEvento(colaborador, eventos);
		eventoRepo.save(e);
		return e;
	}

	private Evento processarEvento(Colaborador colaborador, List<Evento> eventos) throws Exception {
		Evento e = null;
		validarExpediente(eventos);
		if (eventos == null || eventos.isEmpty()) {
			e = gerarEvento(colaborador, TipoEvento.INICIO_EXPEDIENTE);
		} else {
			if (EventoHelper.existeEventoRecente(eventos)) {
				throw new Exception("Você registrou o ponto recentemente favor aguardar ao menos de 1 minuto.");
			} else {
				if (EventoHelper.existeEventoInicioFimDescanso(eventos)) {
					e = gerarEvento(colaborador, TipoEvento.FIM_EXPEDIENTE);
				} else {
					if (EventoHelper.existeEventoInicioDescanso(eventos)) {

						ClockDetails eventosCalculadosDia = colaboradorServico.calcularEventosDia(eventos);
						long totalSegundosDescansados = obterSegundosDescanso(eventos);
						ValidadorHelper.validarFimDescanso(eventosCalculadosDia, totalSegundosDescansados);

						e = gerarEvento(colaborador, TipoEvento.FIM_DESCANSO);
					} else {
						e = gerarEvento(colaborador, TipoEvento.INICIO_DESCANSO);
					}
				}
			}
		}
		return e;
	}

	private long obterSegundosDescanso(List<Evento> eventos) {
		LocalDateTime dtIniDesc = null;
		LocalDateTime dtFimDesc = null;
		for (Evento evento : eventos) {
			switch (evento.getTipoEvento()) {
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
		return Duration.between(dtIniDesc, dtFimDesc).getSeconds();
	}

	private void validarExpediente(List<Evento> eventos) throws Exception {
		if (eventos.size() == 4) {
			throw new Exception("Você já encerrou o expediente.");
		}
	}

	private Evento gerarEvento(Colaborador colaborador, TipoEvento tipo) {
		return new Evento(null, colaborador, tipo, LocalDate.now(), LocalDateTime.now());
	}

	@Transactional(readOnly = true)
	public List<Evento> buscarEventosDiarioColaborador(String idColaborador) {
		return eventoRepo.buscarEventosDiarioColaborador(idColaborador, LocalDate.now());
	}

	@Transactional(readOnly = true)
	public List<Evento> buscarEventosMensalColaborador(String idColaborador) throws Exception {

		List<Evento> eventos = eventoRepo.findAll();

		ValidadorHelper.validarExistenciaEventos(idColaborador, eventos);

		List<Evento> eventosFiltrados = eventos.stream()
				.filter(evn -> (evn.getColaborador().getId().equals(idColaborador)
						&& evn.getDataEvento().getMonth().equals(LocalDate.now().getMonth())))
				.collect(Collectors.toCollection(() -> new ArrayList<Evento>()));

		return eventosFiltrados;
	}

}
