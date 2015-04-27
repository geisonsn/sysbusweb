package br.com.gsn.sysbusweb.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "origem_reclamacao")
@NamedQueries({
		@NamedQuery(name = "OrigemReclamacao.findAll", query = "SELECT o FROM OrigemReclamacao o"),
		@NamedQuery(name = "OrigemReclamacao.findByObjetoReclamado", query = "SELECT o FROM OrigemReclamacao o WHERE o.objetoReclamado = :objetoReclamado") })
public class OrigemReclamacao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "objeto_reclamado")
	private ObjetoReclamado objetoReclamado;
	
	/*@Enumerated
	@Column(name = "objeto_reclamado")
	private ObjetoReclamadoEnum objetoReclamado;*/

	@ManyToOne
	@JoinColumn(name = "id_tipo_reclamacao")
	private TipoReclamacao tipoReclamacao;

	public OrigemReclamacao() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ObjetoReclamado getObjetoReclamado() {
		return this.objetoReclamado;
	}

	public void setObjetoReclamado(ObjetoReclamado objetoReclamado) {
		this.objetoReclamado = objetoReclamado;
	}
	
	/*public ObjetoReclamadoEnum getObjetoReclamado() {
		return objetoReclamado;
	}
	
	public void setObjetoReclamado(ObjetoReclamadoEnum objetoReclamado) {
		this.objetoReclamado = objetoReclamado;
	}*/
	
	public TipoReclamacao getTipoReclamacao() {
		return this.tipoReclamacao;
	}


	public void setTipoReclamacao(TipoReclamacao tipoReclamacao) {
		this.tipoReclamacao = tipoReclamacao;
	}
	
	public String getDescricaoFormatada() {
		return this.getObjetoReclamado().getDescricao() + " - " + this.tipoReclamacao.getDescricao();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((objetoReclamado == null) ? 0 : objetoReclamado.hashCode());
		result = prime * result
				+ ((tipoReclamacao == null) ? 0 : tipoReclamacao.hashCode());
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
		OrigemReclamacao other = (OrigemReclamacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (objetoReclamado != other.objetoReclamado)
			return false;
		if (tipoReclamacao == null) {
			if (other.tipoReclamacao != null)
				return false;
		} else if (!tipoReclamacao.equals(other.tipoReclamacao))
			return false;
		return true;
	}

}