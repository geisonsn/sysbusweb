package br.com.gsn.sysbusweb.domain;

import java.io.Serializable;

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
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import br.com.gsn.sysbusweb.util.Util;

@Entity
@Table(name = "veiculo")
@NamedQueries({
	@NamedQuery(name = "Veiculo.findAll", query = "SELECT v FROM Veiculo v order by v.linha.numero, v.numeroRegistro"),
	@NamedQuery(name = Veiculo.FIND_BY_LINHA_BY_REGISTRO, query = "SELECT v FROM Veiculo v  "
			+ " WHERE v.numeroRegistro LIKE :numeroRegistro and UPPER(v.linha.numero) LIKE :linha "
			+ " ORDER BY v.linha.numero, v.numeroRegistro"),
	@NamedQuery(name = Veiculo.FIND_BY_NUMERO_LINHA, query = "select v from Veiculo v "
			+ " where v.linha.numero = :numeroLinha "
			+ " order by v.numeroRegistro "),
	@NamedQuery(name = Veiculo.GET_BY_NUMERO_REGISTRO, query = "select v from Veiculo v "
			+ " where v.numeroRegistro = :numeroRegistro ")
})
public class Veiculo implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String FIND_BY_LINHA_BY_REGISTRO = "Veiculo.findByLinhaByRegistro";
	public static final String FIND_BY_NUMERO_LINHA = "Veiculo.findByNumeroLinha";
	public static final String GET_BY_NUMERO_REGISTRO = "Veiculo.getByNumeroRegistro";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(name = "numero_registro")
	private String numeroRegistro;

	private String placa;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_linha")
	private Linha linha;

	public Veiculo() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroRegistro() {
		return this.numeroRegistro;
	}

	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}

	public String getPlaca() {
		return this.placa;
	}
	
	public String getPlacaFormatada() {
		return Util.formatarPlaca(this.placa);
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public Linha getLinha() {
		return this.linha;
	}

	public void setLinha(Linha linha) {
		this.linha = linha;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((linha == null) ? 0 : linha.hashCode());
		result = prime * result
				+ ((numeroRegistro == null) ? 0 : numeroRegistro.hashCode());
		result = prime * result + ((placa == null) ? 0 : placa.hashCode());
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
		Veiculo other = (Veiculo) obj;
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
		if (numeroRegistro == null) {
			if (other.numeroRegistro != null)
				return false;
		} else if (!numeroRegistro.equals(other.numeroRegistro))
			return false;
		if (placa == null) {
			if (other.placa != null)
				return false;
		} else if (!placa.equals(other.placa))
			return false;
		return true;
	}
}