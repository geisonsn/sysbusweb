package br.com.gsn.sysbusweb.domain.dto;

import java.io.Serializable;


public class SincronizarFavoritoDTO extends InformationRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idUsuario;
	
	private String linhas;

	public SincronizarFavoritoDTO(){}
	
	public SincronizarFavoritoDTO(String message){
		setMessage(message);
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getLinhas() {
		return linhas;
	}

	public void setLinhas(String linhas) {
		this.linhas = linhas;
	}
	
}

