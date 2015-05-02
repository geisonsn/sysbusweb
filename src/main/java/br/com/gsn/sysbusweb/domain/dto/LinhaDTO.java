package br.com.gsn.sysbusweb.domain.dto;

import java.io.Serializable;

public class LinhaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String numero;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

}
