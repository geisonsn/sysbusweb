package br.com.gsn.sysbusweb.domain.endereco;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "conjunto")
@NamedQuery(name = "Conjunto.findAll", query = "SELECT c FROM Conjunto c")
public class Conjunto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "id_tipo_localidade")
	private Long idTipoLocalidade;

	private String nome;

	@ManyToOne
	@JoinColumn(name = "id_bairro")
	private Bairro bairro;

	@ManyToOne
	@JoinColumn(name = "id_municipio")
	private Municipio municipio;

	public Conjunto() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdTipoLocalidade() {
		return this.idTipoLocalidade;
	}

	public void setIdTipoLocalidade(Long idTipoLocalidade) {
		this.idTipoLocalidade = idTipoLocalidade;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Bairro getBairro() {
		return this.bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}

	public Municipio getMunicipio() {
		return this.municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bairro == null) ? 0 : bairro.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime
				* result
				+ ((idTipoLocalidade == null) ? 0 : idTipoLocalidade.hashCode());
		result = prime * result
				+ ((municipio == null) ? 0 : municipio.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Conjunto other = (Conjunto) obj;
		if (bairro == null) {
			if (other.bairro != null)
				return false;
		} else if (!bairro.equals(other.bairro))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idTipoLocalidade == null) {
			if (other.idTipoLocalidade != null)
				return false;
		} else if (!idTipoLocalidade.equals(other.idTipoLocalidade))
			return false;
		if (municipio == null) {
			if (other.municipio != null)
				return false;
		} else if (!municipio.equals(other.municipio))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

}