package br.com.gsn.sysbusweb.persistence;

import java.util.List;

import br.com.gsn.sysbusweb.domain.LinhaFavorita;
import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;

@PersistenceController
public class LinhaFavoritaDAO extends JPACrud<LinhaFavorita, Long> {

	private static final long serialVersionUID = 1L;
	
	public List<LinhaFavorita> findByUsuario(Long idUsuario) {
		
		return getEntityManager().createNamedQuery(LinhaFavorita.FIND_BY_USUARIO, LinhaFavorita.class)
			.setParameter("idUsuario", idUsuario)
			.getResultList();
	}

}
