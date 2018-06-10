package org.interview.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.interview.db.Evento;
import org.interview.service.EventoServico;
import org.interview.utils.DataUtil;
import org.interview.utils.ErrorDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/ponto")
public class EventosController {
	@Autowired
	private EventoServico eventoServico;

	@RequestMapping(path = "/eventos/colaborador/{id}/diario", method = RequestMethod.GET)
	public ResponseEntity<?> obterEventosDiarioColaborador(@PathVariable(value = "id") String id) {
		try {
			return new ResponseEntity<List<Evento>>(eventoServico.buscarEventosDiarioColaborador(id), HttpStatus.OK);
		} catch (Exception e) {
			return tratarErros(e);
		}
	}

	@RequestMapping(path = "/eventos/colaborador/{id}/mensal", method = RequestMethod.GET)
	public ResponseEntity<?> obterColaborador(@PathVariable(value = "id") String id) {
		try {
			return new ResponseEntity<List<Evento>>(getEventosColaboradorMensal(id), HttpStatus.OK);
		} catch (Exception e) {
			return tratarErros(e);
		}
	}

	private ResponseEntity<Object> tratarErros(Exception e) {
		return new ResponseEntity<Object>(
				new ErrorDetails(DataUtil.formatarDateTime(LocalDateTime.now()), e.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private List<Evento> getEventosColaboradorMensal(String idColaborador) throws Exception {
		return eventoServico.buscarEventosMensalColaborador(idColaborador);
	}
}
