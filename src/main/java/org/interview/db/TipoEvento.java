package org.interview.db;

public enum TipoEvento {
	INICIO_EXPEDIENTE(1, "Inicio expediente"), //
	FIM_EXPEDIENTE(2, "Inicio expediente"), //
	INICIO_DESCANSO(3, "Inicio expediente"), //
	FIM_DESCANSO(4, "Inicio expediente"); //

	private Integer codigo;
	private String descricao;

	private TipoEvento(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
