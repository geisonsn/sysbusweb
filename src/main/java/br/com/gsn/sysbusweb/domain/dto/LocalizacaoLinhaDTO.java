package br.com.gsn.sysbusweb.domain.dto;

import java.io.Serializable;
import java.util.Date;

import br.com.gsn.sysbusweb.util.Dates;

public class LocalizacaoLinhaDTO extends InformationRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idLinha;
	
	private Long idVeiculo;

	private String numeroLinha;

	private String numeroRegistro;

	private String latitude;

	private String longitude;

	private String dataHoraRegistro;
	
	public LocalizacaoLinhaDTO() {}
	
	public LocalizacaoLinhaDTO(String numeroLinha, String numeroRegistro,
			String latitude, String longitude, Date dataHoraRegistro) {
		this.numeroLinha = numeroLinha;
		this.numeroRegistro = numeroRegistro;
		this.latitude = latitude;
		this.longitude = longitude;
		this.dataHoraRegistro = Dates.format(dataHoraRegistro, Dates.FORMAT_PT_BR_COMPLETE);
	}

	public LocalizacaoLinhaDTO(String message) {
		setMessage(message);
	}

	public Long getIdLinha() {
		return idLinha;
	}

	public void setIdLinha(Long idLinha) {
		this.idLinha = idLinha;
	}

	public Long getIdVeiculo() {
		return idVeiculo;
	}

	public void setIdVeiculo(Long idVeiculo) {
		this.idVeiculo = idVeiculo;
	}

	public String getNumeroLinha() {
		return numeroLinha;
	}

	public void setNumeroLinha(String numeroLinha) {
		this.numeroLinha = numeroLinha;
	}

	public String getNumeroRegistro() {
		return numeroRegistro;
	}

	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
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

	public String getDataHoraRegistro() {
		return dataHoraRegistro;
	}

	public void setDataHoraRegistro(String dataHoraRegistro) {
		this.dataHoraRegistro = dataHoraRegistro;
	}

}
