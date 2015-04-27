package br.com.gsn.sysbusweb.domain.endereco;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "ponto_referencia")
@NamedQuery(name = "PontoReferencia.findAll", query = "SELECT p FROM PontoReferencia p")
public class PontoReferencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "classificacao_ponto")
	private Long classificacaoPonto;

	private String complemento;

	private String descricao;

	private String endereco;

	@Column(name = "id_tipo_ponto_referencia")
	private Long idTipoPontoReferencia;

	@Column(name = "numero_ponto")
	private String numeroPonto;

	@Column(name = "sigla_ponto")
	private String siglaPonto;

	@ManyToOne
	@JoinColumn(name = "id_bairro")
	private Bairro bairro;

	@ManyToOne
	@JoinColumn(name = "id_conjunto")
	private Conjunto conjunto;

	public PontoReferencia() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getClassificacaoPonto() {
		return this.classificacaoPonto;
	}

	public void setClassificacaoPonto(Long classificacaoPonto) {
		this.classificacaoPonto = classificacaoPonto;
	}

	public String getComplemento() {
		return this.complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getEndereco() {
		return this.endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Long getIdTipoPontoReferencia() {
		return this.idTipoPontoReferencia;
	}

	public void setIdTipoPontoReferencia(Long idTipoPontoReferencia) {
		this.idTipoPontoReferencia = idTipoPontoReferencia;
	}

	public String getNumeroPonto() {
		return this.numeroPonto;
	}

	public void setNumeroPonto(String numeroPonto) {
		this.numeroPonto = numeroPonto;
	}

	public String getSiglaPonto() {
		return this.siglaPonto;
	}

	public void setSiglaPonto(String siglaPonto) {
		this.siglaPonto = siglaPonto;
	}

	public Bairro getBairro() {
		return this.bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}

	public Conjunto getConjunto() {
		return this.conjunto;
	}

	public void setConjunto(Conjunto conjunto) {
		this.conjunto = conjunto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bairro == null) ? 0 : bairro.hashCode());
		result = prime
				* result
				+ ((classificacaoPonto == null) ? 0 : classificacaoPonto
						.hashCode());
		result = prime * result
				+ ((complemento == null) ? 0 : complemento.hashCode());
		result = prime * result
				+ ((conjunto == null) ? 0 : conjunto.hashCode());
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result
				+ ((endereco == null) ? 0 : endereco.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime
				* result
				+ ((idTipoPontoReferencia == null) ? 0 : idTipoPontoReferencia
						.hashCode());
		result = prime * result
				+ ((numeroPonto == null) ? 0 : numeroPonto.hashCode());
		result = prime * result
				+ ((siglaPonto == null) ? 0 : siglaPonto.hashCode());
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
		PontoReferencia other = (PontoReferencia) obj;
		if (bairro == null) {
			if (other.bairro != null)
				return false;
		} else if (!bairro.equals(other.bairro))
			return false;
		if (classificacaoPonto == null) {
			if (other.classificacaoPonto != null)
				return false;
		} else if (!classificacaoPonto.equals(other.classificacaoPonto))
			return false;
		if (complemento == null) {
			if (other.complemento != null)
				return false;
		} else if (!complemento.equals(other.complemento))
			return false;
		if (conjunto == null) {
			if (other.conjunto != null)
				return false;
		} else if (!conjunto.equals(other.conjunto))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (endereco == null) {
			if (other.endereco != null)
				return false;
		} else if (!endereco.equals(other.endereco))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idTipoPontoReferencia == null) {
			if (other.idTipoPontoReferencia != null)
				return false;
		} else if (!idTipoPontoReferencia.equals(other.idTipoPontoReferencia))
			return false;
		if (numeroPonto == null) {
			if (other.numeroPonto != null)
				return false;
		} else if (!numeroPonto.equals(other.numeroPonto))
			return false;
		if (siglaPonto == null) {
			if (other.siglaPonto != null)
				return false;
		} else if (!siglaPonto.equals(other.siglaPonto))
			return false;
		return true;
	}

}