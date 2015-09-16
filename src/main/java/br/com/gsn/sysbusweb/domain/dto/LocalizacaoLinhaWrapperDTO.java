package br.com.gsn.sysbusweb.domain.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LocalizacaoLinhaWrapperDTO extends InformationRequest implements
		Serializable {

	private static final long serialVersionUID = 1L;
	
	public LocalizacaoLinhaWrapperDTO() {
		linhasFavoritas = new ArrayList<LocalizacaoLinhaDTO>();
		linhasNaoFavoritas = new ArrayList<LocalizacaoLinhaDTO>();
	}

	private List<LocalizacaoLinhaDTO> linhasFavoritas;

	private List<LocalizacaoLinhaDTO> linhasNaoFavoritas;

	public List<LocalizacaoLinhaDTO> getLinhasFavoritas() {
		return linhasFavoritas;
	}

	public void setLinhasFavoritas(List<LocalizacaoLinhaDTO> linhasFavoritas) {
		this.linhasFavoritas = linhasFavoritas;
	}

	public List<LocalizacaoLinhaDTO> getLinhasNaoFavoritas() {
		return linhasNaoFavoritas;
	}

	public void setLinhasNaoFavoritas(
			List<LocalizacaoLinhaDTO> linhasNaoFavoritas) {
		this.linhasNaoFavoritas = linhasNaoFavoritas;
	}
	
	public boolean isEmpty() {
		return this.getLinhasFavoritas().isEmpty() && this.getLinhasNaoFavoritas().isEmpty();
	}

}
