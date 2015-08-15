package br.com.gsn.sysbusweb.persistence;

import java.util.List;

import javax.persistence.NoResultException;

import br.com.gsn.sysbusweb.domain.Usuario;
import br.com.gsn.sysbusweb.domain.enums.PerfilEnum;
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
	
	public Usuario getClienteByEmailEPassword(String email, String password) {
		
		StringBuffer jpql = new StringBuffer(" select usuario from PerfilUsuario perfil ")
			.append(" inner join perfil.usuario as usuario ")
			.append(" where usuario.email = :email ")
			.append(" and usuario.password = :password ")
			.append(" and perfil.perfil = :perfil");
		
		return (Usuario)getEntityManager()
				.createQuery(jpql.toString())
				.setParameter("email", email)
				.setParameter("password", password)
				.setParameter("perfil", PerfilEnum.CLIENTE)
				.getSingleResult();
	}
	
	public Usuario getByUsernameEEmail(String username, String email) throws NoResultException {
		return (Usuario)getEntityManager()
				.createNamedQuery("Usuario.getByUsernameEEmail")
				.setParameter("username", username.toLowerCase())
				.setParameter("email", email)
				.getSingleResult();
	}
	
	public Usuario getByEmail(String email) {
		return (Usuario) getEntityManager()
			.createNamedQuery(Usuario.GET_BY_EMAIL)
			.setParameter("email", email)
			.getSingleResult();
	}
}
