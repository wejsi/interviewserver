package org.interview.repository;

import java.time.LocalDate;
import java.util.List;

import org.interview.db.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EventoRepo extends JpaRepository<Evento, Integer>,
		JpaSpecificationExecutor<Evento> {

	@Query("SELECT e FROM Evento e WHERE e.colaborador.id = :col_id and e.dataEvento = :dataEvento")
	List<Evento> buscarEventosDiarioColaborador(@Param("col_id") String col_id,
			@Param("dataEvento") LocalDate dataEvento);

}
