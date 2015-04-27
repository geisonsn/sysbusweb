package br.com.gsn.sysbusweb.domain.endereco;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "endereco")
@NamedQuery(name = "Endereco.findAll", query = "SELECT e FROM Endereco e")
public class Endereco implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String cep;

	private String complemento;

	private String latitude;

	private String longitude;

	@Column(name = "numero_andar")
	private String numeroAndar;

	@Column(name = "numero_apartamento")
	private String numeroApartamento;

	@Column(name = "numero_residencia")
	private String numeroResidencia;

	@ManyToOne
	@JoinColumn(name = "id_logradouro")
	private Logradouro logradouro;

	@ManyToOne
	@JoinColumn(name = "id_cruzamento")
	private Logradouro cruzamento;

	@ManyToOne
	@JoinColumn(name = "id_ponto_referencia")
	private PontoReferencia pontoReferencia;

	@ManyToOne
	@JoinColumn(name = "id_conjunto")
	private Conjunto conjunto;

	public Endereco() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCep() {
		return this.cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getComplemento() {
		return this.complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getLatitude() {
		return this.latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return this.longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getNumeroAndar() {
		return this.numeroAndar;
	}

	public void setNumeroAndar(String numeroAndar) {
		this.numeroAndar = numeroAndar;
	}

	public String getNumeroApartamento() {
		return this.numeroApartamento;
	}

	public void setNumeroApartamento(String numeroApartamento) {
		this.numeroApartamento = numeroApartamento;
	}

	public String getNumeroResidencia() {
		return this.numeroResidencia;
	}

	public void setNumeroResidencia(String numeroResidencia) {
		this.numeroResidencia = numeroResidencia;
	}

	public Logradouro getLogradouro() {
		return this.logradouro;
	}

	public void setLogradouro(Logradouro logradouro) {
		this.logradouro = logradouro;
	}

	public Logradouro getCruzamento() {
		return this.cruzamento;
	}

	public void setCruzamento(Logradouro cruzamento) {
		this.cruzamento = cruzamento;
	}

	public PontoReferencia getPontoReferencia() {
		return this.pontoReferencia;
	}

	public void setPontoReferencia(PontoReferencia pontoReferencia) {
		this.pontoReferencia = pontoReferencia;
	}

	public Conjunto getConjunto() {
		return this.conjunto;
	}

	public void setConjunto(Conjunto conjunto) {
		this.conjunto = conjunto;
	}
	
	public String getEnderecoFormatado() {
		return this.logradouro.getLogradouroFormatado();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cep == null) ? 0 : cep.hashCode());
		result = prime * result
				+ ((complemento == null) ? 0 : complemento.hashCode());
		result = prime * result
				+ ((conjunto == null) ? 0 : conjunto.hashCode());
		result = prime * result
				+ ((cruzamento == null) ? 0 : cruzamento.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result
				+ ((logradouro == null) ? 0 : logradouro.hashCode());
		result = prime * result
				+ ((longitude == null) ? 0 : longitude.hashCode());
		result = prime * result
				+ ((numeroAndar == null) ? 0 : numeroAndar.hashCode());
		result = prime
				* result
				+ ((numeroApartamento == null) ? 0 : numeroApartamento
						.hashCode());
		result = prime
				* result
				+ ((numeroResidencia == null) ? 0 : numeroResidencia.hashCode());
		result = prime * result
				+ ((pontoReferencia == null) ? 0 : pontoReferencia.hashCode());
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
		Endereco other = (Endereco) obj;
		if (cep == null) {
			if (other.cep != null)
				return false;
		} else if (!cep.equals(other.cep))
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
		if (cruzamento == null) {
			if (other.cruzamento != null)
				return false;
		} else if (!cruzamento.equals(other.cruzamento))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!latitude.equals(other.latitude))
			return false;
		if (logradouro == null) {
			if (other.logradouro != null)
				return false;
		} else if (!logradouro.equals(other.logradouro))
			return false;
		if (longitude == null) {
			if (other.longitude != null)
				return false;
		} else if (!longitude.equals(other.longitude))
			return false;
		if (numeroAndar == null) {
			if (other.numeroAndar != null)
				return false;
		} else if (!numeroAndar.equals(other.numeroAndar))
			return false;
		if (numeroApartamento == null) {
			if (other.numeroApartamento != null)
				return false;
		} else if (!numeroApartamento.equals(other.numeroApartamento))
			return false;
		if (numeroResidencia == null) {
			if (other.numeroResidencia != null)
				return false;
		} else if (!numeroResidencia.equals(other.numeroResidencia))
			return false;
		if (pontoReferencia == null) {
			if (other.pontoReferencia != null)
				return false;
		} else if (!pontoReferencia.equals(other.pontoReferencia))
			return false;
		return true;
	}

}