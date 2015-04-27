package br.com.gsn.sysbusweb.persistence;

import java.util.List;

import br.com.gsn.sysbusweb.domain.TipoLinha;
import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;

@PersistenceController
public class TipoLinhaDAO extends JPACrud<TipoLinha, Long> {

	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	public List<TipoLinha> findByDescricao(String descricao) {
		return (List<TipoLinha>)getEntityManager()
				.createNamedQuery("TipoLinha.findByDescricao")
				.setParameter("descricao", "%" + descricao.toUpperCase() + "%")
				.getResultList();
	}

}
