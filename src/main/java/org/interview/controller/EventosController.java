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

/***
 * Classe responsável pelo controle e definição dos endpoints referente aos
 * eventos do colaborador.
 * 
 * @author weslen.silva
 *
 */
@RestController
@RequestMapping("api/ponto")
public class EventosController {
	@Autowired
	private EventoServico eventoServico;

	/***
	 * Obtém os eventos(Apontamentos) diário do colaborador pelo id.
	 * 
	 * @param id
	 * @return ResponseEntity<?>.
	 */
	@RequestMapping(path = "/eventos/colaborador/{id}/diario", method = RequestMethod.GET)
	public ResponseEntity<?> obterEventosDiarioColaborador(@PathVariable(value = "id") String id) {
		try {
			return new ResponseEntity<List<Evento>>(eventoServico.buscarEventosDiarioColaborador(id), HttpStatus.OK);
		} catch (Exception e) {
			return tratarErros(e);
		}
	}

	/***
	 * Obtém os eventos(Apontamentos) mensal do colaborador pelo id.
	 * 
	 * @param id
	 * @return ResponseEntity<?>.
	 */
	@RequestMapping(path = "/eventos/colaborador/{id}/mensal", method = RequestMethod.GET)
	public ResponseEntity<?> obterColaborador(@PathVariable(value = "id") String id) {
		try {
			return new ResponseEntity<List<Evento>>(eventoServico.buscarEventosMensalColaborador(id), HttpStatus.OK);
		} catch (Exception e) {
			return tratarErros(e);
		}
	}

	/***
	 * Trata erros de execução.
	 * 
	 * @param e
	 * @return ResponseEntity<?>.
	 */
	private ResponseEntity<Object> tratarErros(Exception e) {
		return new ResponseEntity<Object>(
				new ErrorDetails(DataUtil.formatarDateTime(LocalDateTime.now()), e.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
