package br.com.gsn.sysbusweb.business;

import java.util.List;

import br.com.gsn.sysbusweb.domain.LocalizacaoLinha;
import br.com.gsn.sysbusweb.domain.dto.LocalizacaoLinhaDTO;
import br.com.gsn.sysbusweb.persistence.LocalizacaoLinhaDAO;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;

@BusinessController
public class LocalizacaoLinhaBC extends	DelegateCrud<LocalizacaoLinha, Long, LocalizacaoLinhaDAO> {
	
	private static final long serialVersionUID = 1L;
	
	public List<LocalizacaoLinhaDTO> listUltimaLocalizacaoVeiculoDaLinha(String numeroLinha) {
		return getDelegate().listUltimaLocalizacaoVeiculoDaLinha(numeroLinha);
	}

}
