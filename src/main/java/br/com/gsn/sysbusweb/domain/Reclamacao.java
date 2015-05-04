package br.com.gsn.sysbusweb.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
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

import br.com.gsn.sysbusweb.domain.enums.ObjetoReclamadoEnum;
import br.com.gsn.sysbusweb.util.Util;
import br.com.gsn.sysbusweb.util.Util.FormatoData;
import br.com.gsn.sysbusweb.util.Util.FormatoHora;

@Entity
@Table(name="reclamacao")
@NamedQueries({
	@NamedQuery(name="Reclamacao.findAll", query="SELECT r FROM Reclamacao r "
					+ "left join r.linha as l "
					+ "left join r.origemReclamacao as o " 
					+ "left join o.tipoReclamacao as t "
					+ "order by r.dataRegistro desc, l.numero, r.dataOcorrencia, t.descricao"),
	@NamedQuery(name = "Reclamacao.findByPeriodo", query= "SELECT r FROM  Reclamacao r " 
					+ "left join r.linha as l "
					+ "left join r.origemReclamacao as o " 
					+ "left join o.tipoReclamacao as t "
					+ "WHERE r.dataRegistro between :dataInicio and :dataFim "
					+ "order by r.dataRegistro desc, l.numero, r.dataOcorrencia, t.descricao")
	
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

	@Enumerated
	@Column(name = "objeto_reclamado")
	private ObjetoReclamadoEnum objetoReclamado;
	
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

	public ObjetoReclamadoEnum getObjetoReclamado() {
		return objetoReclamado;
	}

	public void setObjetoReclamado(ObjetoReclamadoEnum objetoReclamado) {
		this.objetoReclamado = objetoReclamado;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataOcorrencia == null) ? 0 : dataOcorrencia.hashCode());
		result = prime * result
				+ ((dataRegistro == null) ? 0 : dataRegistro.hashCode());
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((hora == null) ? 0 : hora.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((linha == null) ? 0 : linha.hashCode());
		result = prime * result
				+ ((objetoReclamado == null) ? 0 : objetoReclamado.hashCode());
		result = prime
				* result
				+ ((origemReclamacao == null) ? 0 : origemReclamacao.hashCode());
		result = prime * result
				+ ((placaLinha == null) ? 0 : placaLinha.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reclamacao other = (Reclamacao) obj;
		if (dataOcorrencia == null) {
			if (other.dataOcorrencia != null)
				return false;
		} else if (!dataOcorrencia.equals(other.dataOcorrencia))
			return false;
		if (dataRegistro == null) {
			if (other.dataRegistro != null)
				return false;
		} else if (!dataRegistro.equals(other.dataRegistro))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (hora == null) {
			if (other.hora != null)
				return false;
		} else if (!hora.equals(other.hora))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (linha == null) {
			if (other.linha != null)
				return false;
		} else if (!linha.equals(other.linha))
			return false;
		if (objetoReclamado != other.objetoReclamado)
			return false;
		if (origemReclamacao == null) {
			if (other.origemReclamacao != null)
				return false;
		} else if (!origemReclamacao.equals(other.origemReclamacao))
			return false;
		if (placaLinha == null) {
			if (other.placaLinha != null)
				return false;
		} else if (!placaLinha.equals(other.placaLinha))
			return false;
		return true;
	}
	
	
	public static void main(String[] args) {
		ObjetoReclamadoEnum obj = ObjetoReclamadoEnum.MOTORISTA;
		Object[] enumConstants = obj.getClass().getEnumConstants();
		for (int i = 0; i < enumConstants.length; i++) {
			System.out.println(enumConstants[i].toString());
		}
	}
	

}