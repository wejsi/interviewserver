package org.interview.helpers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import org.interview.db.Evento;
import org.interview.db.TipoEvento;

public class EventoHelper {
	/***
	 * Verifica se o colaborador já apontou o inicio e o fim do descanso.
	 * 
	 * @param eventos
	 * @return true ou false.
	 */
	public static boolean existeEventoInicioFimDescanso(List<Evento> eventos) {
		return existeEventoInicioDescanso(eventos) && existeEventoFimDescanso(eventos);
	}

	/***
	 * Verifica se o colaborador já apontou o do descanso.
	 * 
	 * @param eventos
	 * @return true ou false.
	 */
	private static boolean existeEventoFimDescanso(List<Evento> eventos) {
		return eventos.stream().anyMatch(evn -> TipoEvento.FIM_DESCANSO.equals(evn.getTipoEvento()));
	}

	/***
	 * Verifica se o colaborador já apontou o inicio do descanso.
	 * 
	 * @param eventos
	 * @return true ou false.
	 */
	public static boolean existeEventoInicioDescanso(List<Evento> eventos) {
		return eventos.stream().anyMatch(evn -> TipoEvento.INICIO_DESCANSO.equals(evn.getTipoEvento()));
	}

	/***
	 * Verifica se o colaborador já apontou o inicio do expediente.
	 * 
	 * @param eventos
	 * @return true ou false.
	 */
	public static boolean existeEventoInicioExpediente(List<Evento> eventos) {
		return eventos.stream().anyMatch(evn -> TipoEvento.INICIO_EXPEDIENTE.equals(evn.getTipoEvento()));
	}

	/***
	 * Verifica se o colaborador já apontou o fim do expediente.
	 * 
	 * @param eventos
	 * @return true ou false.
	 */
	public static boolean existeEventoFimExpediente(List<Evento> eventos) {
		return eventos.stream().anyMatch(evn -> TipoEvento.FIM_EXPEDIENTE.equals(evn.getTipoEvento()));
	}

	/***
	 * Verifica se existe evento recente. <br>
	 * Obtém os segundos de duração entre o evento de dataHora recente e a data hora
	 * atual. Também verifica se o tempo e menor que 1 minuto.
	 * 
	 * @param eventos
	 * @return
	 */
	public static boolean existeEventoRecente(List<Evento> eventos) {
		Evento maxEvento = eventos.stream().max(new Comparator<Evento>() {
			@Override
			public int compare(Evento o1, Evento o2) {
				return o1.getDataHoraEvento().compareTo(o2.getDataHoraEvento());
			}
		}).get();

		return Duration.between(maxEvento.getDataHoraEvento(), LocalDateTime.now()).getSeconds() <= 60;
	}
}
