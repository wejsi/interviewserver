package org.interview.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.interview.db.Colaborador;
import org.interview.db.Evento;
import org.interview.service.ColaboradorServico;
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
 * Classe responsável pelo controle e definição dos endpoints referente ao ponto
 * do colaborador.
 * 
 * @author weslen.silva
 *
 */
@RestController
@RequestMapping("api/ponto")
public class ColaboradorController {

	@Autowired
	private ColaboradorServico colaboradorServico;

	@Autowired
	private EventoServico eventoServico;

	/***
	 * Obtém lista de todos os colaboradores cadastrados.
	 * 
	 * @return ResponseEntity<?>.
	 */
	@RequestMapping(path = "/colaboradores", method = RequestMethod.GET)
	public ResponseEntity<?> obterColaboradores() {
		try {
			return new ResponseEntity<List<Colaborador>>(getColaboradores(), HttpStatus.OK);
		} catch (Exception e) {
			return trataErros(e);
		}
	}

	/***
	 * Obtém o colaborador cadastrado pelo id.
	 * 
	 * @param id
	 * @return ResponseEntity<?>.
	 */
	@RequestMapping(path = "/colaborador/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> obterColaborador(@PathVariable(value = "id") String id) {
		try {
			return new ResponseEntity<Colaborador>(getColaboradores().stream()
					.filter(colaborador -> colaborador.getId().equals(id)).findFirst().orElse(null), HttpStatus.OK);
		} catch (Exception e) {
			return trataErros(e);
		}

	}

	/***
	 * Registra evento(Apontamento) do colaborador cadastrado pelo id.
	 * 
	 * @param id
	 * @return ResponseEntity<?>.
	 */
	@RequestMapping(path = "/colaborador/{id}/registrar", method = RequestMethod.PUT)
	public ResponseEntity<?> registrar(@PathVariable String id) {
		try {
			return new ResponseEntity<Evento>(eventoServico.registrar(id), HttpStatus.OK);
		} catch (Exception e) {
			return trataErros(e);
		}
	}

	/***
	 * Obtém a quantidade de horas diária trabalhadas do colaborador cadastrado pelo
	 * id.
	 * 
	 * @param id
	 * @return ResponseEntity<?>.
	 */
	@RequestMapping(path = "/colaborador/{id}/total-horas-dia", method = RequestMethod.GET)
	public ResponseEntity<?> obterHorasDia(@PathVariable String id) {
		try {
			return new ResponseEntity<Object>(colaboradorServico.obterHorasTrabalhadasDia(id), HttpStatus.OK);
		} catch (Exception e) {
			return trataErros(e);
		}
	}

	/***
	 * Obtém a quantidade de horas mensal trabalhadas do colaborador cadastrado pelo
	 * id.
	 * 
	 * @param id
	 * @return ResponseEntity<?>.
	 */
	@RequestMapping(path = "/colaborador/{id}/total-horas-mes", method = RequestMethod.GET)
	public ResponseEntity<?> obterHorasMes(@PathVariable String id) {
		try {
			return new ResponseEntity<Object>(colaboradorServico.obterHorasTrabalhadasMes(id), HttpStatus.OK);
		} catch (Exception e) {
			return trataErros(e);
		}
	}

	/***
	 * Trata erros de execução.
	 * 
	 * @param e
	 * @return ResponseEntity<?>.
	 */
	private ResponseEntity<Object> trataErros(Exception e) {
		return new ResponseEntity<Object>(
				new ErrorDetails(DataUtil.formatarDateTime(LocalDateTime.now()), e.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/***
	 * Obtém todos colaboradores.
	 * 
	 * @return
	 */
	private List<Colaborador> getColaboradores() {
		return colaboradorServico.obterTodos();
	}

}
