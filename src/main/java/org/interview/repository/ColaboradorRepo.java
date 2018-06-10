package org.interview.repository;

import org.interview.db.Colaborador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColaboradorRepo extends JpaRepository<Colaborador, String> {

}
