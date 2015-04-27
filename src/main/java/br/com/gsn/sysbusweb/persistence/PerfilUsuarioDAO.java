package br.com.gsn.sysbusweb.persistence;

import java.util.List;

import br.com.gsn.sysbusweb.domain.PerfilUsuario;
import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;

@PersistenceController
public class PerfilUsuarioDAO extends JPACrud<PerfilUsuario, Long> {

	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	public List<PerfilUsuario> findByIdUsuario(Long idUsuario) {
		return (List<PerfilUsuario>)getEntityManager()
			.createNamedQuery("PerfilUsuario.findByIdUsuario")
			.setParameter("idUsuario", idUsuario)
			.getResultList();
}

}
