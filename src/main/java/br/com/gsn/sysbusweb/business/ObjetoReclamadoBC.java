
package br.com.gsn.sysbusweb.business;

import java.util.List;

import br.com.gsn.sysbusweb.domain.ObjetoReclamado;
import br.com.gsn.sysbusweb.persistence.ObjetoReclamadoDAO;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;

@BusinessController
public class ObjetoReclamadoBC extends DelegateCrud<ObjetoReclamado, Long, ObjetoReclamadoDAO> {
	private static final long serialVersionUID = 1L;
	
	public List<ObjetoReclamado> findByAll(ObjetoReclamado o) {
		return getDelegate().findByAll(o);
	}
	
}
