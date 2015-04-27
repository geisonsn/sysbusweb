package br.com.gsn.sysbusweb.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="reclamacao")
@NamedQuery(name="Reclamacao.findAll", query="SELECT r FROM Reclamacao r")

public class Reclamacao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.DATE)
	@Column(name="data_ocorrencia")
	private Date dataOcorrencia;

	@Column(name="data_registro")
	private Date dataRegistro;

	private String descricao;

	@Temporal(TemporalType.TIME)
	private Date hora;

	@Column(name="placa_linha")
	private String placaLinha;

	@ManyToOne
	@JoinColumn(name="id_origem_reclamacao")
	private OrigemReclamacao origemReclamacao;

	@ManyToOne
	@JoinColumn(name="id_linha")
	private Linha linha;

	public Reclamacao() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataOcorrencia() {
		return this.dataOcorrencia;
	}

	public void setDataOcorrencia(Date dataOcorrencia) {
		this.dataOcorrencia = dataOcorrencia;
	}

	public Date getDataRegistro() {
		return this.dataRegistro;
	}

	public void setDataRegistro(Date dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getHora() {
		return this.hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	public String getPlacaLinha() {
		return this.placaLinha;
	}

	public void setPlacaLinha(String placaLinha) {
		this.placaLinha = placaLinha;
	}

	public OrigemReclamacao getOrigemReclamacao() {
		return this.origemReclamacao;
	}

	public void setOrigemReclamacao(OrigemReclamacao origemReclamacao) {
		this.origemReclamacao = origemReclamacao;
	}

	public Linha getLinha() {
		return this.linha;
	}

	public void setLinha(Linha linha) {
		this.linha = linha;
	}

}