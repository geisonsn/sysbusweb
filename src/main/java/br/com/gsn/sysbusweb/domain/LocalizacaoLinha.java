package br.com.gsn.sysbusweb.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "localizacao_linha")
@NamedQuery(name="LocalizacaoLinha.findAll", query="SELECT loc FROM LocalizacaoLinha loc")
public class LocalizacaoLinha implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_linha")
	private Linha linha;
	
	@ManyToOne
	@JoinColumn(name = "id_veiculo")
	private Veiculo veiculo;
	
	@ManyToOne
	@JoinColumn(name="id_usuario")
	private Usuario usuario;
	
	@Column(name = "lotacao_veiculo")
	private String locataoVeiculo;
	
	@Column(name = "descricao")
	private String status;

	private String latitude;

	private String longitude;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_hora_registro")
	private Date dataHoraRegistro;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Linha getLinha() {
		return linha;
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getLocataoVeiculo() {
		return locataoVeiculo;
	}

	public void setLocataoVeiculo(String locataoVeiculo) {
		this.locataoVeiculo = locataoVeiculo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public Date getDataHoraRegistro() {
		return dataHoraRegistro;
	}

	public void setDataHoraRegistro(Date dataHoraRegistro) {
		this.dataHoraRegistro = dataHoraRegistro;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((dataHoraRegistro == null) ? 0 : dataHoraRegistro.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result + ((linha == null) ? 0 : linha.hashCode());
		result = prime * result
				+ ((locataoVeiculo == null) ? 0 : locataoVeiculo.hashCode());
		result = prime * result
				+ ((longitude == null) ? 0 : longitude.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
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
		LocalizacaoLinha other = (LocalizacaoLinha) obj;
		if (dataHoraRegistro == null) {
			if (other.dataHoraRegistro != null)
				return false;
		} else if (!dataHoraRegistro.equals(other.dataHoraRegistro))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!latitude.equals(other.latitude))
			return false;
		if (linha == null) {
			if (other.linha != null)
				return false;
		} else if (!linha.equals(other.linha))
			return false;
		if (locataoVeiculo == null) {
			if (other.locataoVeiculo != null)
				return false;
		} else if (!locataoVeiculo.equals(other.locataoVeiculo))
			return false;
		if (longitude == null) {
			if (other.longitude != null)
				return false;
		} else if (!longitude.equals(other.longitude))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		if (veiculo == null) {
			if (other.veiculo != null)
				return false;
		} else if (!veiculo.equals(other.veiculo))
			return false;
		return true;
	}

}
