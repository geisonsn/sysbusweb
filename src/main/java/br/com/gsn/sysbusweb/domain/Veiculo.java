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

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotBlank;

import br.com.gsn.sysbusweb.util.Util;

@Entity
@Table(name = "veiculo")
@NamedQueries({
	@NamedQuery(name = Veiculo.FIND_ALL, query = "SELECT v FROM Veiculo v order by v.numeroRegistro, v.placa"),
	@NamedQuery(name = Veiculo.FIND_BY_REGISTRO_BY_PLACA, query = "SELECT v FROM Veiculo v  "
			+ " WHERE upper(v.numeroRegistro) LIKE :numeroRegistro or upper(v.placa) LIKE :placa "
			+ " order by v.numeroRegistro, v.placa "),
	@NamedQuery(name = Veiculo.GET_BY_NUMERO_REGISTRO, query = "select v from Veiculo v "
			+ " where v.numeroRegistro = :numeroRegistro "),
	@NamedQuery(name = Veiculo.GET_BY_PLACA, query = "select v from Veiculo v "
			+ " where v.placa = :placa ")
})
public class Veiculo implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String FIND_ALL = "Veiculo.findAll";
	public static final String FIND_BY_REGISTRO_BY_PLACA = "Veiculo.findByRegistroOrPlaca";
	public static final String GET_BY_NUMERO_REGISTRO = "Veiculo.getByNumeroRegistro";
	public static final String GET_BY_PLACA = "Veiculo.getByPlaca";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(name = "numero_registro")
	private String numeroRegistro;

	private String placa;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_empresa")
	private Empresa empresa;

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
	
	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getDescricaoFormatada() {
		return this.getNumeroRegistro() + (StringUtils.isNotEmpty(this.placa) ? " - " + getPlacaFormatada() : "");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((empresa == null) ? 0 : empresa.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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