package br.com.gsn.sysbusweb.domain.dto;

import java.io.Serializable;


public class UsuarioWrapperDTO extends InformationRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private UsuarioDTO usuario;
	
	private LinhaFavoritaDTO linhasFavoritas[];

	public UsuarioWrapperDTO(){}
	
	public UsuarioWrapperDTO(String message){
		setMessage(message);
	}
	
	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

	public LinhaFavoritaDTO[] getLinhasFavoritas() {
		return linhasFavoritas;
	}

	public void setLinhasFavoritas(LinhaFavoritaDTO[] linhasFavoritas) {
		this.linhasFavoritas = linhasFavoritas;
	}
}

