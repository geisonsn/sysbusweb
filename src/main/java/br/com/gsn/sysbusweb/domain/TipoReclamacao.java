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
@Table(name="tipo_reclamacao")
@NamedQueries({
	@NamedQuery(name="TipoReclamacao.findAll", query="SELECT t FROM TipoReclamacao t"),
	@NamedQuery(name = "TipoReclamacao.findByDescricao", query = "SELECT t FROM TipoReclamacao t WHERE UPPER(t.descricao) LIKE :descricao"),
	
	@NamedQuery(name = "TipoReclamacao.cadastradasAoObjetoReclamado", query = "select t from TipoReclamacao t where t.id in ( "
									+ "select o.tipoReclamacao.id from OrigemReclamacao o " 
									+ "inner join o.tipoReclamacao as t " 
									+ "where o.objetoReclamado = :objetoReclamado) "
									+ "order by t.descricao "),
	
	@NamedQuery(name = "TipoReclamacao.naoCadastradasAoObjetoReclamado", query = "select t from TipoReclamacao t where t.id not in ( " 
									+ "select o.tipoReclamacao.id from OrigemReclamacao o " 
									+ "inner join o.tipoReclamacao as t " 
									+ "where o.objetoReclamado = :objetoReclamado) "
									+ "order by t.descricao ")

})
public class TipoReclamacao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	private String descricao;

	public TipoReclamacao() {
	}

	public TipoReclamacao(String descricao) {
		this.descricao = descricao;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		TipoReclamacao other = (TipoReclamacao) obj;
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
		return true;
	}

	@Override
	public String toString() {
		return "TipoReclamacao [id=" + id + ", descricao=" + descricao + "]";
	}

}