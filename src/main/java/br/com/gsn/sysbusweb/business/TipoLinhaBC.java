
package br.com.gsn.sysbusweb.business;

import java.util.List;

import br.com.gsn.sysbusweb.domain.TipoLinha;
import br.com.gsn.sysbusweb.persistence.TipoLinhaDAO;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;

@BusinessController
public class TipoLinhaBC extends DelegateCrud<TipoLinha, Long, TipoLinhaDAO> {
	private static final long serialVersionUID = 1L;
	
	public List<TipoLinha> findByDescricao(String descricao) {
		return getDelegate().findByDescricao(descricao);
	}
	
}
