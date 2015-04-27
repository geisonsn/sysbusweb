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

@Entity
@Table(name = "veiculo")
@NamedQueries({
	@NamedQuery(name = "Veiculo.findAll", query = "SELECT v FROM Veiculo v"),
	@NamedQuery(name = "Veiculo.findByCampos", query = "SELECT v FROM Veiculo v inner join v.linha as l  "
			+ " WHERE v.numeroRegistro LIKE :numeroRegistro and UPPER(l.descricao) LIKE :linha")
})
public class Veiculo implements Serializable {
	private static final long serialVersionUID = 1L;

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

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public Linha getLinha() {
		return this.linha;
	}

	public void setLinha(Linha linha) {
		this.linha = linha;
	}

}