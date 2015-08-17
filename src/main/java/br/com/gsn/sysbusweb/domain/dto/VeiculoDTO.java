package br.com.gsn.sysbusweb.domain.dto;

import java.io.Serializable;

import br.com.gsn.sysbusweb.domain.Veiculo;

public class VeiculoDTO extends InformationRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idLinha;

	private Long idVeiculo;

	private String descricaoLinha;

	private String numeroLinha;

	private String numeroRegistro;

	public VeiculoDTO(){}
	
	public VeiculoDTO(String message) {
		setMessage(message);
	}
	
	public VeiculoDTO(Veiculo veiculo) {
		
	}

	public Long getIdLinha() {
		return idLinha;
	}

	public void setIdLinha(Long idLinha) {
		this.idLinha = idLinha;
	}

	public Long getIdVeiculo() {
		return idVeiculo;
	}

	public void setIdVeiculo(Long idVeiculo) {
		this.idVeiculo = idVeiculo;
	}

	public String getDescricaoLinha() {
		return descricaoLinha;
	}

	public void setDescricaoLinha(String descricaoLinha) {
		this.descricaoLinha = descricaoLinha;
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
