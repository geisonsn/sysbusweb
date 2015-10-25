package br.com.gsn.sysbusweb.domain.dto;

import java.io.Serializable;
import java.util.List;


public class SincronizarFavoritoDTO extends InformationRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idUsuario;
	
	private List<Long> linhas;

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

	public List<Long> getLinhas() {
		return linhas;
	}

	public void setLinhas(List<Long> linhas) {
		this.linhas = linhas;
	}
	
}

