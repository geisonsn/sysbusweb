package br.com.gsn.sysbusweb.domain.endereco;

import java.io.Serializable;

import javax.persistence.*;

import br.com.gsn.sysbusweb.domain.enums.TipoLogradouroEnum;

@Entity
@Table(name = "logradouro")
@NamedQueries({
	@NamedQuery(name = "Logradouro.findAll", query = "SELECT l FROM Logradouro l"),
	@NamedQuery(name = "Logradouro.findByNomeByBairro", 
		query = "select l from Logradouro l inner join l.bairro as b "
		+ "where (upper(l.nome) like :logradouro or upper(l.nomeAntigo) like :logradouro or upper(l.nomePopular) like :logradouro) "
		+ "and upper(b.nome) like :bairro "
		+ "order by l.nome, b.nome ")
})
public class Logradouro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String cep;

	@Column(name = "id_tipo_logradouro")
	private Long idTipoLogradouro;

	@Column(name = "id_tipo_logradouro_antigo")
	private Long idTipoLogradouroAntigo;

	@Column(name = "id_tipo_logradouro_popular")
	private Long idTipoLogradouroPopular;

	private String nome;

	@Column(name = "nome_antigo")
	private String nomeAntigo;

	@Column(name = "nome_popular")
	private String nomePopular;

	@ManyToOne
	@JoinColumn(name = "id_bairro")
	private Bairro bairro;

	public Logradouro() {
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

	public Long getIdTipoLogradouro() {
		return this.idTipoLogradouro;
	}

	public void setIdTipoLogradouro(Long idTipoLogradouro) {
		this.idTipoLogradouro = idTipoLogradouro;
	}

	public Long getIdTipoLogradouroAntigo() {
		return this.idTipoLogradouroAntigo;
	}

	public void setIdTipoLogradouroAntigo(Long idTipoLogradouroAntigo) {
		this.idTipoLogradouroAntigo = idTipoLogradouroAntigo;
	}

	public Long getIdTipoLogradouroPopular() {
		return this.idTipoLogradouroPopular;
	}

	public void setIdTipoLogradouroPopular(Long idTipoLogradouroPopular) {
		this.idTipoLogradouroPopular = idTipoLogradouroPopular;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNomeAntigo() {
		return this.nomeAntigo;
	}

	public void setNomeAntigo(String nomeAntigo) {
		this.nomeAntigo = nomeAntigo;
	}

	public String getNomePopular() {
		return this.nomePopular;
	}

	public void setNomePopular(String nomePopular) {
		this.nomePopular = nomePopular;
	}

	public Bairro getBairro() {
		return this.bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}
	
	public String getLogradouroFormatado() {
		return TipoLogradouroEnum.fromOrdinal(this.idTipoLogradouro.intValue()) + " " + this.nome + ", " + this.bairro.getNome();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bairro == null) ? 0 : bairro.hashCode());
		result = prime * result + ((cep == null) ? 0 : cep.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime
				* result
				+ ((idTipoLogradouro == null) ? 0 : idTipoLogradouro.hashCode());
		result = prime
				* result
				+ ((idTipoLogradouroAntigo == null) ? 0
						: idTipoLogradouroAntigo.hashCode());
		result = prime
				* result
				+ ((idTipoLogradouroPopular == null) ? 0
						: idTipoLogradouroPopular.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result
				+ ((nomeAntigo == null) ? 0 : nomeAntigo.hashCode());
		result = prime * result
				+ ((nomePopular == null) ? 0 : nomePopular.hashCode());
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
		Logradouro other = (Logradouro) obj;
		if (bairro == null) {
			if (other.bairro != null)
				return false;
		} else if (!bairro.equals(other.bairro))
			return false;
		if (cep == null) {
			if (other.cep != null)
				return false;
		} else if (!cep.equals(other.cep))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idTipoLogradouro == null) {
			if (other.idTipoLogradouro != null)
				return false;
		} else if (!idTipoLogradouro.equals(other.idTipoLogradouro))
			return false;
		if (idTipoLogradouroAntigo == null) {
			if (other.idTipoLogradouroAntigo != null)
				return false;
		} else if (!idTipoLogradouroAntigo.equals(other.idTipoLogradouroAntigo))
			return false;
		if (idTipoLogradouroPopular == null) {
			if (other.idTipoLogradouroPopular != null)
				return false;
		} else if (!idTipoLogradouroPopular
				.equals(other.idTipoLogradouroPopular))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (nomeAntigo == null) {
			if (other.nomeAntigo != null)
				return false;
		} else if (!nomeAntigo.equals(other.nomeAntigo))
			return false;
		if (nomePopular == null) {
			if (other.nomePopular != null)
				return false;
		} else if (!nomePopular.equals(other.nomePopular))
			return false;
		return true;
	}

}