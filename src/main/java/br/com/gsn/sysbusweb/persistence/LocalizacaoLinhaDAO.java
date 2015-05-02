package br.com.gsn.sysbusweb.persistence;

import br.com.gsn.sysbusweb.domain.LocalizacaoLinha;
import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;

@PersistenceController
public class LocalizacaoLinhaDAO extends JPACrud<LocalizacaoLinha, Long> {

	private static final long serialVersionUID = 1L;

}
