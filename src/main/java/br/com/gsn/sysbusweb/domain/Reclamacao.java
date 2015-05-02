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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import br.com.gsn.sysbusweb.util.Util;
import br.com.gsn.sysbusweb.util.Util.FormatoData;
import br.com.gsn.sysbusweb.util.Util.FormatoHora;

@Entity
@Table(name="reclamacao")
@NamedQueries({
	@NamedQuery(name="Reclamacao.findAll", query="SELECT r FROM Reclamacao r inner join r.linha as l " 
					+ "order by r.dataRegistro desc, l.numero, r.dataOcorrencia, r.origemReclamacao.tipoReclamacao.descricao"),
	@NamedQuery(name = "Reclamacao.findByPeriodo", query= "SELECT r FROM  Reclamacao r inner join r.linha as l " 
					+ "WHERE r.dataRegistro between :dataInicio and :dataFim "
					+ "order by r.dataRegistro desc, l.numero, r.dataOcorrencia, r.origemReclamacao.tipoReclamacao.descricao")
	
})

public class Reclamacao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name="data_ocorrencia")
	private Date dataOcorrencia;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_registro")
	private Date dataRegistro;

	private String descricao;

	@Temporal(TemporalType.TIME)
	private Date hora;

	@Column(name="placa_linha")
	private String placaLinha;

	@NotNull
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
	
	public String getDataRegistroFormatada() {
		return Util.formatarData(this.dataRegistro, FormatoData.Completo);
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
	
	public String getHoraFormatada() {
		return Util.formatarHora(this.hora, FormatoHora.Simples);
	}

	public String getPlacaLinha() {
		return this.placaLinha;
	}
	
	public String getPlacaFormatada() {
		return Util.formatarPlaca(this.placaLinha);
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
	
	public String getDataOcorrenciaFormatada() {
		return Util.formatarData(this.dataOcorrencia, 
				FormatoData.Simples);
	}

}