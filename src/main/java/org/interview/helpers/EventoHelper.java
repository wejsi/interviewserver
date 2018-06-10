package org.interview.helpers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import org.interview.db.Evento;
import org.interview.db.TipoEvento;

public class EventoHelper {
	public static boolean existeEventoInicioFimDescanso(List<Evento> eventos) {
		return existeEventoInicioDescanso(eventos)
				&& existeEventoFimDescanso(eventos);
	}

	private static boolean existeEventoFimDescanso(List<Evento> eventos) {
		return eventos.stream().anyMatch(
				evn -> TipoEvento.FIM_DESCANSO.equals(evn.getTipoEvento()));
	}

	public static boolean existeEventoInicioDescanso(List<Evento> eventos) {
		return eventos.stream().anyMatch(
				evn -> TipoEvento.INICIO_DESCANSO.equals(evn.getTipoEvento()));
	}

	public static boolean existeEventoInicioExpediente(List<Evento> eventos) {
		return eventos.stream()
				.anyMatch(
						evn -> TipoEvento.INICIO_EXPEDIENTE.equals(evn
								.getTipoEvento()));
	}

	public static boolean existeEventoFimExpediente(List<Evento> eventos) {
		return eventos.stream().anyMatch(
				evn -> TipoEvento.FIM_EXPEDIENTE.equals(evn.getTipoEvento()));
	}

	public static boolean existeEventoRecente(List<Evento> eventos) {
		Evento maxEvento = eventos.stream().max(new Comparator<Evento>() {
			@Override
			public int compare(Evento o1, Evento o2) {
				return o1.getDataHoraEvento().compareTo(o2.getDataHoraEvento());
			}
		}).get();

		return Duration.between(maxEvento.getDataHoraEvento(),
				LocalDateTime.now()).getSeconds() <= 60;
	}
}
