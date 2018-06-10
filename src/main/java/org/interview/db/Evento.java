package org.interview.db;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.interview.db.converters.LocalDateConverter;
import org.interview.db.converters.LocalDateTimeConverter;
import org.interview.utils.DataUtil;

@Entity
@Table(name = "EVE_EVENTO", schema = "PUBLIC")
public class Evento {

	private static final String COL_ID = "COL_ID";

	private Integer id;
	private Colaborador colaborador;
	private TipoEvento tipoEvento;
	private LocalDate dataEvento;
	private LocalDateTime dataHoraEvento;

	public Evento() {
	}

	public Evento(Integer id, Colaborador colaborador, TipoEvento tipoEvento,
			LocalDate dataEvento, LocalDateTime dataHoraEvento) {
		super();
		this.id = id;
		this.colaborador = colaborador;
		this.tipoEvento = tipoEvento;
		this.dataEvento = dataEvento;
		this.dataHoraEvento = dataHoraEvento;
	}

	@Id
	@Column(name = "EVE_ID", insertable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = COL_ID, referencedColumnName = COL_ID, nullable = false)
	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	@Column(name = "EVE_CD_TIPO", nullable = false)
	@Enumerated(EnumType.ORDINAL)
	public TipoEvento getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(TipoEvento tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	@Column(name = "EVE_DT_EVENTO", nullable = false)
	@Convert(converter = LocalDateConverter.class)
	public LocalDate getDataEvento() {
		return dataEvento;
	}

	public void setDataEvento(LocalDate dataEvento) {
		this.dataEvento = dataEvento;
	}

	@Column(name = "EVE_DH_EVENTO", nullable = false)
	@Convert(converter = LocalDateTimeConverter.class)
	public LocalDateTime getDataHoraEvento() {
		return dataHoraEvento;
	}

	public void setDataHoraEvento(LocalDateTime dataHoraEvento) {
		this.dataHoraEvento = dataHoraEvento;
	}

	@Transient
	public String getDataHoraEventoFormatada() {
		return DataUtil.formatarDateTime(dataHoraEvento);
	}

	@Transient
	public String getDataEventoFormatada() {
		return DataUtil.formatarDate(dataEvento);
	}

}
