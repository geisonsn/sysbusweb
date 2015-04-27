package br.com.gsn.sysbusweb.persistence;

import java.util.List;

import br.com.gsn.sysbusweb.domain.ObjetoReclamado;
import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;

@PersistenceController
public class ObjetoReclamadoDAO extends JPACrud<ObjetoReclamado, Long> {

	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	public List<ObjetoReclamado> findByAll(ObjetoReclamado o) {
		return (List<ObjetoReclamado>)getEntityManager()
				.createNamedQuery("ObjetoReclamado.findByCampos")
				.setParameter("descricao", "%" + o.getDescricao().toUpperCase() + "%")
				.setParameter("sigla", "%" + o.getSigla().toUpperCase() + "%")
				.getResultList();
	}
}
