package br.com.gsn.sysbusweb.persistence;

import java.util.List;

import br.com.gsn.sysbusweb.domain.Usuario;
import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;

@PersistenceController
public class UsuarioDAO extends JPACrud<Usuario, Long> {

	private static final long serialVersionUID = 1L;
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Usuario> findAll() {
		return (List<Usuario>) getEntityManager().createNamedQuery("Usuario.findAll").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Usuario> findByNome(String nome) {
		return (List<Usuario>)getEntityManager()
				.createNamedQuery("Usuario.findByNome")
				.setParameter("nome", "%" + nome + "%")
				.getResultList();
	}
	
	public Usuario getByUsernameEPassoword(String username, String password) {
		return (Usuario)getEntityManager()
				.createNamedQuery("Usuario.getByUsernameEPassword")
				.setParameter("username", username)
				.setParameter("password", password)
				.getSingleResult();
	}
	
	public Usuario getByUsernameEEmail(String username, String email) {
		return (Usuario)getEntityManager()
				.createNamedQuery("Usuario.getByUsernameEEmail")
				.setParameter("username", username.toLowerCase())
				.setParameter("email", email)
				.getSingleResult();
	}
	
	

}
