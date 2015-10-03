package br.com.gsn.sysbusweb.domain.dto;

import java.io.Serializable;

public class ReclamacaoRankingDTO extends InformationRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long idLinha;
	
	private String numeroLinha;
	
	private String nomeEmpresa;
	
	private Long totalReclamacoes;

	public ReclamacaoRankingDTO() {}
	
	public ReclamacaoRankingDTO(String message) {
		super.setMessage(message);
	}
	
	public ReclamacaoRankingDTO(Long idLinha, String numeroLinha, String nomeEmpresa,
			Long totalReclamacoes) {
		super();
		this.idLinha = idLinha;
		this.numeroLinha = numeroLinha;
		this.nomeEmpresa = nomeEmpresa;
		this.totalReclamacoes = totalReclamacoes;
	}

	public Long getIdLinha() {
		return idLinha;
	}

	public void setIdLinha(Long idLinha) {
		this.idLinha = idLinha;
	}

	public String getNumeroLinha() {
		return numeroLinha;
	}

	public void setNumeroLinha(String numeroLinha) {
		this.numeroLinha = numeroLinha;
	}

	public String getNomeEmpresa() {
		return nomeEmpresa;
	}

	public void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}

	public Long getTotalReclamacoes() {
		return totalReclamacoes;
	}

	public void setTotalReclamacoes(Long totalReclamacoes) {
		this.totalReclamacoes = totalReclamacoes;
	}
}
