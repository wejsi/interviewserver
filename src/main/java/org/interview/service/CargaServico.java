package org.interview.service;

import java.util.Arrays;

import org.interview.db.Colaborador;
import org.interview.repository.ColaboradorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CargaServico {

	@Autowired
	private ColaboradorRepo colaboradorRepo;

	public void carregarColaboradores() {
		Colaborador c1 = new Colaborador("12083924837", "Joao");
		Colaborador c2 = new Colaborador("12055473249", "Maria");
		Colaborador c3 = new Colaborador("12031314892", "Pedro");
		Colaborador c4 = new Colaborador("12089294380", "Lucas");
		Colaborador c5 = new Colaborador("12063955842", "Jorge");
		Colaborador c6 = new Colaborador("12007412855", "Amanda");
		Colaborador c7 = new Colaborador("12073641352", "Carol");
		Colaborador c8 = new Colaborador("12081175233", "Ricardo");
		Colaborador c9 = new Colaborador("12061634640", "Roberta");
		Colaborador c10 = new Colaborador("12063168140", "Ana");
		colaboradorRepo.save(Arrays.asList(c1, c2, c3, c4, c5, c6, c7, c8, c9,
				c10));

	}
}
