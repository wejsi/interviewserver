package org.interview.controller.vo;

public class TempoEventoVO {
	private int hora;
	private int minuto;
	private int segundo;

	public TempoEventoVO() {

	}

	public TempoEventoVO(int hora, int minuto, int segundo) {
		super();
		this.hora = hora;
		this.minuto = minuto;
		this.segundo = segundo;
	}

	public String getTotalHorasTrabalhadas() {
		return String.format("%02d", hora) + ":"
				+ String.format("%02d", minuto) + ":"
				+ String.format("%02d", segundo);
	}

	public int getHora() {
		return hora;
	}

	public int getMinuto() {
		return minuto;
	}

	public int getSegundo() {
		return segundo;
	}

}
