package br.com.gsn.sysbusweb.business;

import java.util.List;

import br.com.gsn.sysbusweb.domain.endereco.Logradouro;
import br.com.gsn.sysbusweb.persistence.LogradouroDAO;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;

@BusinessController
public class LogradouroBC extends DelegateCrud<Logradouro, Long, LogradouroDAO> {

	private static final long serialVersionUID = 1L;
	
	public List<Logradouro> findByNomeByBairro(String logradouro,  String bairro) {
		return getDelegate().findByNomeByBairro(logradouro, bairro);
	}

}
