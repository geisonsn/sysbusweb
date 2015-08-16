package br.com.gsn.sysbusweb.domain.dto;

import java.io.Serializable;

public class LinhaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private Long idVeiculo;
	
	private String descricao;

	private String numeroLinha;

	private String numeroRegistro;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getIdVeiculo() {
		return idVeiculo;
	}

	public void setIdVeiculo(Long idVeiculo) {
		this.idVeiculo = idVeiculo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getNumeroLinha() {
		return numeroLinha;
	}

	public void setNumeroLinha(String numeroLinha) {
		this.numeroLinha = numeroLinha;
	}

	public String getNumeroRegistro() {
		return numeroRegistro;
	}

	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}
}
