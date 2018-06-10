package org.interview.utils;

public class ErrorDetails {
	private String dataHora;
	private String message;

	public ErrorDetails(String dataHora, String message) {
		super();
		this.dataHora = dataHora;
		this.message = message;
	}

	public String getDataHora() {
		return dataHora;
	}

	public void setDataHora(String dataHora) {
		this.dataHora = dataHora;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
