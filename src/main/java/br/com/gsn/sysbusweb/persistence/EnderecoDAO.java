package br.com.gsn.sysbusweb.persistence;

import br.com.gsn.sysbusweb.domain.endereco.Endereco;
import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;

@PersistenceController
public class EnderecoDAO extends JPACrud<Endereco, Long> {

	private static final long serialVersionUID = 1L;
}
