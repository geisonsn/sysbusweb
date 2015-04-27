package br.com.gsn.sysbusweb.business;

import br.com.gsn.sysbusweb.domain.endereco.Endereco;
import br.com.gsn.sysbusweb.persistence.EnderecoDAO;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;

@BusinessController
public class EnderecoBC extends DelegateCrud<Endereco, Long, EnderecoDAO> {

	private static final long serialVersionUID = 1L;

}
