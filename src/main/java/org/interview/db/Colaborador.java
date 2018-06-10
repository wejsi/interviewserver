package org.interview.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "COL_COLABORADOR", schema = "PUBLIC")
public class Colaborador {

	private String id;

	private String name;

	public Colaborador() {

	}

	public Colaborador(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	@Id
	@Column(name = "COL_ID")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "COL_NM", length = 30, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
