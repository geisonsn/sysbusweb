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

@Entity
@Table(name = "veiculo_linha")
@NamedQueries({
	@NamedQuery(name = VeiculoLinha.FIND_ALL, query = "SELECT v FROM VeiculoLinha v "
			+ " order by v.linha.numero, v.veiculo.numeroRegistro"),
	@NamedQuery(name = VeiculoLinha.FIND_BY_LINHA_BY_REGISTRO, query = "SELECT v FROM VeiculoLinha v  "
			+ " WHERE v.veiculo.numeroRegistro LIKE :numeroRegistro and UPPER(v.linha.numero) LIKE :linha "
			+ " ORDER BY v.linha.numero, v.veiculo.numeroRegistro"),
	@NamedQuery(name = VeiculoLinha.FIND_BY_NUMERO_LINHA, query = "select v from VeiculoLinha v "
			+ " where v.linha.numero = :numeroLinha "
			+ " order by v.veiculo.numeroRegistro "),
	@NamedQuery(name = VeiculoLinha.GET_BY_NUMERO_LINHA_BY_NUMERO_REGISTRO, query = "select v from VeiculoLinha v "
			+ " where v.linha.numero = :numero and v.veiculo.numeroRegistro = :numeroRegistro "),
	@NamedQuery(name = VeiculoLinha.GET_BY_NUMERO_LINHA_BY_PLACA, query = "select v from VeiculoLinha v "
			+ " where v.linha.numero = :numero and v.veiculo.numeroRegistro = :numeroRegistro ")
})
public class VeiculoLinha implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String FIND_ALL = "VeiculoLinha.findAll";
	public static final String FIND_BY_LINHA_BY_REGISTRO = "VeiculoLinha.findByLinhaByRegistro";
	public static final String FIND_BY_NUMERO_LINHA = "VeiculoLinha.findByNumeroLinha";
	public static final String GET_BY_NUMERO_LINHA_BY_NUMERO_REGISTRO = "VeiculoLinha.getByNumerLinhaByNumeroRegistro";
	public static final String GET_BY_NUMERO_LINHA_BY_PLACA = "VeiculoLinha.getByNumerLinhaByPlaca";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_linha")
	private Linha linha;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_veiculo")
	private Veiculo veiculo;

	public VeiculoLinha() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Linha getLinha() {
		return this.linha;
	}

	public void setLinha(Linha linha) {
		this.linha = linha;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((linha == null) ? 0 : linha.hashCode());
		result = prime * result + ((veiculo == null) ? 0 : veiculo.hashCode());
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
		VeiculoLinha other = (VeiculoLinha) obj;
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
		if (veiculo == null) {
			if (other.veiculo != null)
				return false;
		} else if (!veiculo.equals(other.veiculo))
			return false;
		return true;
	}

	
}