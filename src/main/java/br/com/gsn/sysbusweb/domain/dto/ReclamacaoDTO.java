package br.com.gsn.sysbusweb.domain.dto;

import java.io.Serializable;
import java.text.DecimalFormat;

public class ReclamacaoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String objetoReclamado;
	private String empresa;
	private String numeroLinha;
	private String tipoReclamacao;
	private String dataRegistro;
	private String dataOcorrencia;
	private String horaOcorrencia;
	private String placaVeiculo;
	private String detalhesReclamacao; 
	private Integer totalReclamacoes;
	private double percentual;
	
	public ReclamacaoDTO() {}

	public String getObjetoReclamado() {
		return objetoReclamado;
	}

	public void setObjetoReclamado(String objetoReclamado) {
		this.objetoReclamado = objetoReclamado;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getNumeroLinha() {
		return numeroLinha;
	}

	public void setNumeroLinha(String numeroLinha) {
		this.numeroLinha = numeroLinha;
	}

	public String getTipoReclamacao() {
		return tipoReclamacao;
	}

	public void setTipoReclamacao(String tipoReclamacao) {
		this.tipoReclamacao = tipoReclamacao;
	}
	
	public String getDataRegistro() {
		return dataRegistro;
	}

	public void setDataRegistro(String dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	public String getDataOcorrencia() {
		return dataOcorrencia;
	}

	public void setDataOcorrencia(String dataOcorrencia) {
		this.dataOcorrencia = dataOcorrencia;
	}

	public String getHoraOcorrencia() {
		return horaOcorrencia;
	}

	public void setHoraOcorrencia(String horaOcorrencia) {
		this.horaOcorrencia = horaOcorrencia;
	}

	public String getPlacaVeiculo() {
		return placaVeiculo;
	}

	public void setPlacaVeiculo(String placaVeiculo) {
		this.placaVeiculo = placaVeiculo;
	}

	public String getDetalhesReclamacao() {
		return detalhesReclamacao;
	}

	public void setDetalhesReclamacao(String detalhesReclamacao) {
		this.detalhesReclamacao = detalhesReclamacao;
	}

	public void setPercentual(double percentual) {
		this.percentual = percentual;
	}

	public Integer getTotalReclamacoes() {
		return totalReclamacoes;
	}
	
	public String getPercentual() {
		DecimalFormat format = new DecimalFormat("###.##");
		return format.format(percentual);
	}
	
	public void setPercentual(Integer totalGeralReclamacoes) {
		this.percentual = (this.getTotalReclamacoes() * 100.0) / totalGeralReclamacoes;
	}

	public void setTotalReclamacoes(Integer totalReclamacoes) {
		this.totalReclamacoes = totalReclamacoes;
	}
	
	public static class Builder {
		private Integer totalGeralReclamacoes;
		private ReclamacaoDTO reclamacao;
		
		public Builder(Integer totalGeralReclamacoes) {
			this.totalGeralReclamacoes = totalGeralReclamacoes;
			this.reclamacao = new ReclamacaoDTO();
		}
		
		public Builder objetoReclamado(String objetoReclamado) {
			reclamacao.setObjetoReclamado(objetoReclamado);
			return this;
		}
		
		public Builder numeroLinha(String numeroLinha) {
			reclamacao.setNumeroLinha(numeroLinha);
			return this;
		}
		
		public Builder empresa(String empresa) {
			reclamacao.setEmpresa(empresa);
			return this;
		}
		
		public Builder tipoReclamacao(String tipoReclamacao) {
			reclamacao.setTipoReclamacao(tipoReclamacao);
			return this;
		}
		
		public Builder totalReclamacoes(Integer totalReclamacoes) {
			reclamacao.setTotalReclamacoes(totalReclamacoes);
			return this;
		}
		
		public ReclamacaoDTO build() {
			reclamacao.setPercentual(this.totalGeralReclamacoes);
			return reclamacao;
		}
	}
}
