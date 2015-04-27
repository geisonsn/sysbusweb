package br.com.gsn.sysbusweb.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "objeto_reclamado")
@NamedQueries({
		@NamedQuery(name = "ObjetoReclamado.findAll", query = "SELECT o FROM ObjetoReclamado o"),
		@NamedQuery(name = "ObjetoReclamado.findByDescricao", query = "SELECT o FROM ObjetoReclamado o WHERE UPPER(o.descricao) LIKE :descricao"),
		@NamedQuery(name = "ObjetoReclamado.findByCampos", query = "SELECT o FROM ObjetoReclamado o WHERE UPPER(o.descricao) LIKE :descricao and UPPER(o.sigla) LIKE :sigla") })
public class ObjetoReclamado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	private String descricao;

	@NotEmpty
	private String sigla;

	/*@OneToMany(mappedBy = "objetoReclamado")
	private List<OrigemReclamacao> listOrigemReclamacao;*/

	public ObjetoReclamado() {
	}

	public ObjetoReclamado(String descricao, String sigla) {
		this.descricao = descricao;
		this.sigla = sigla;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getSigla() {
		return this.sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	/*public List<OrigemReclamacao> getListOrigemReclamacao() {
		return listOrigemReclamacao;
	}

	public void setListOrigemReclamacao(
			List<OrigemReclamacao> listOrigemReclamacao) {
		this.listOrigemReclamacao = listOrigemReclamacao;
	}*/

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((sigla == null) ? 0 : sigla.hashCode());
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
		ObjetoReclamado other = (ObjetoReclamado) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (sigla == null) {
			if (other.sigla != null)
				return false;
		} else if (!sigla.equals(other.sigla))
			return false;
		return true;
	}

}