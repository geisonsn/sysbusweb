
package br.com.gsn.sysbusweb.business;

import java.util.List;

import br.com.gsn.sysbusweb.domain.Linha;
import br.com.gsn.sysbusweb.persistence.LinhaDAO;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;

@BusinessController
public class LinhaBC extends DelegateCrud<Linha, Long, LinhaDAO> {
	private static final long serialVersionUID = 1L;

	public List<Linha> findByDescricaoByNumero(String descricao, String numero) {
		return getDelegate().findByDescricaoByNumero(descricao, numero);
	}
}
