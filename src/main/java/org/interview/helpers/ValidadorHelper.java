package org.interview.helpers;

import java.util.List;

import org.interview.controller.vo.TempoEventoVO;
import org.interview.db.Evento;
import org.springframework.util.StringUtils;

public class ValidadorHelper {

	private static final int SEGUNDOS_MINUTOS = 60;
	private static final int SEGUNDOS_HORA = 3600;

	/***
	 * Valida identificador do colaborador.
	 * 
	 * @param idColaborador
	 */
	public static void validarIdentificador(String idColaborador) {
		if (StringUtils.isEmpty(idColaborador)) {
			throw new IllegalArgumentException("Identificador é obrigatório");
		}
	}

	/***
	 * Valida existencia de eventos do colaborador.
	 * 
	 * @param idColaborador
	 * @param eventos
	 * @throws Exception
	 */
	public static void validarExistenciaEventos(String idColaborador, List<Evento> eventos) throws Exception {
		validarIdentificador(idColaborador);
		if (eventos == null || eventos.isEmpty()) {
			throw new Exception("Não existe eventos para o colaborador do pis : " + idColaborador);
		}
	}

	/***
	 * Valida intervalo de descanso.
	 * 
	 * @param eventosCalculadosDia
	 * @param segundosDescansados
	 * @throws Exception
	 */
	public static void validarIntervaloDoDescanso(TempoEventoVO eventosCalculadosDia, long segundosDescansados)
			throws Exception {

		int h = eventosCalculadosDia.getHora();
		int m = eventosCalculadosDia.getMinuto();
		int s = eventosCalculadosDia.getSegundo();

		int totalSegundos = (h * SEGUNDOS_HORA) + (m * SEGUNDOS_MINUTOS) + s;

		int totalSegundosQuatroHoras = 4 * SEGUNDOS_HORA;

		if (totalSegundos < totalSegundosQuatroHoras) {
			return;
		} else {
			int totalSegundosSeisHoras = 6 * SEGUNDOS_HORA;
			if ((totalSegundos > totalSegundosQuatroHoras) && (totalSegundos <= totalSegundosSeisHoras)
					&& segundosDescansados < (15 * 60)) {
				throw new Exception("Você precisa gozar no minimo 15 minutos em descanso.");
			} else {
				if (segundosDescansados < SEGUNDOS_HORA) {
					throw new Exception("Você precisa gozar no minimo 1 hora em descanso.");
				}
			}
		}
	}
}
