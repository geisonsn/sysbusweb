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
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "linha")
@NamedQueries({
	@NamedQuery(name = "Linha.findAll", query = "SELECT l FROM Linha l order by l.numero, l.descricao"),
	@NamedQuery(name = "Linha.findByDescricaoByNumero", query = "SELECT l FROM Linha l WHERE UPPER(l.descricao) LIKE :descricao and UPPER(l.numero) LIKE :numero "
														+ " ORDER BY l.numero, l.descricao ")
})
public class Linha implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	private String descricao;

	@NotEmpty
	private String numero;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_empresa")
	private Empresa empresa;

	@ManyToOne
	@JoinColumn(name = "id_tipo_linha")
	private TipoLinha tipoLinha;

	@ManyToOne
	@JoinColumn(name = "id_terminal")
	private Terminal terminal;

	public Linha() {
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

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Empresa getEmpresa() {
		return this.empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public TipoLinha getTipoLinha() {
		return this.tipoLinha;
	}

	public void setTipoLinha(TipoLinha tipoLinha) {
		this.tipoLinha = tipoLinha;
	}

	public Terminal getTerminal() {
		return this.terminal;
	}

	public void setTerminal(Terminal terminal) {
		this.terminal = terminal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((empresa == null) ? 0 : empresa.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result
				+ ((terminal == null) ? 0 : terminal.hashCode());
		result = prime * result
				+ ((tipoLinha == null) ? 0 : tipoLinha.hashCode());
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
		Linha other = (Linha) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (empresa == null) {
			if (other.empresa != null)
				return false;
		} else if (!empresa.equals(other.empresa))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		if (terminal == null) {
			if (other.terminal != null)
				return false;
		} else if (!terminal.equals(other.terminal))
			return false;
		if (tipoLinha == null) {
			if (other.tipoLinha != null)
				return false;
		} else if (!tipoLinha.equals(other.tipoLinha))
			return false;
		return true;
	}

}