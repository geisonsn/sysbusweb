package br.com.gsn.sysbusweb.business;

import java.util.List;

import br.com.gsn.sysbusweb.domain.LocalizacaoLinha;
import br.com.gsn.sysbusweb.domain.dto.LocalizacaoLinhaDTO;
import br.com.gsn.sysbusweb.domain.dto.LocalizacaoLinhaWrapperDTO;
import br.com.gsn.sysbusweb.persistence.LocalizacaoLinhaDAO;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;

@BusinessController
public class LocalizacaoLinhaBC extends	DelegateCrud<LocalizacaoLinha, Long, LocalizacaoLinhaDAO> {
	
	private static final long serialVersionUID = 1L;
	
	public List<LocalizacaoLinhaDTO> listUltimaLocalizacaoVeiculoDaLinha(String numeroLinha) {
		return getDelegate().listUltimaLocalizacaoVeiculoDaLinha(numeroLinha);
	}
	
	public LocalizacaoLinhaWrapperDTO listVeiculosEmDeslocamento(Long idUsuario, Integer intervalo) {
		return getDelegate().listVeiculosEmDeslocamento(idUsuario, intervalo);
	}
	
	public LocalizacaoLinhaWrapperDTO listVeiculosEmDeslocamentoProximos(Long idUsuario, Integer intervalo, Integer distancia, 
			String latitudeUsuario, String longitudeUsuario) {
		return getDelegate().listVeiculosEmDeslocamentoProximos(idUsuario, intervalo, distancia, latitudeUsuario, longitudeUsuario);
	}

}
