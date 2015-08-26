
package br.com.gsn.sysbusweb.business;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.apache.commons.codec.digest.DigestUtils;

import br.com.gsn.sysbusweb.domain.PerfilUsuario;
import br.com.gsn.sysbusweb.domain.Usuario;
import br.com.gsn.sysbusweb.domain.enums.PerfilEnum;
import br.com.gsn.sysbusweb.exception.UsuarioExistenteException;
import br.com.gsn.sysbusweb.persistence.UsuarioDAO;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import br.gov.frameworkdemoiselle.transaction.Transactional;

@BusinessController
public class UsuarioBC extends DelegateCrud<Usuario, Long, UsuarioDAO> {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private PerfilUsuarioBC perfilUsuarioBC;
	
	public List<Usuario> findByNome(String nome) {
		return getDelegate().findByNome(nome);
	}
	
	public Usuario getByEmail(String email) {
		return getDelegate().getByEmail(email);
	}
	
	public Usuario getByUsernameEPassoword(String username, String password) {
		return getDelegate().getByUsernameEPassoword(username, DigestUtils.sha256Hex(password));
	}
	
	public Usuario getClienteByEmailEPassword(String email, String password) {
		return getDelegate().getClienteByEmailEPassword(email, DigestUtils.sha256Hex(password));
	}
	
	public Usuario getByUsernameEEmail(String username, String email) throws NoResultException {
		return getDelegate().getByUsernameEEmail(username, email);
	}
	
	/**
	 * Realiza o cadastro de um cliente (usuário do aplicativo) na base de dados
	 * @param usuario
	 * @return usuario cadastrado
	 * @throws UsuarioExistenteException caso o usuário já exista
	 */
	@Transactional
	public Usuario saveCliente(Usuario usuario) throws UsuarioExistenteException {
		
		try {
			
			Usuario usuarioBuscado = this.getByEmail(usuario.getEmail());
			
			if (usuarioBuscado.isCliente()) {
				throw new UsuarioExistenteException("Usuário já cadastrado");
			} else {
				this.insertPerfilCliente(usuarioBuscado);
			}
		} catch (NoResultException e) {
			usuario.setPassword(DigestUtils.sha256Hex(usuario.getPassword()));
			this.insert(usuario);
			this.insertPerfilCliente(usuario);
		}
		
		return usuario;
	}
	
	@Transactional
	private void insertPerfilCliente(Usuario usuario) {
		PerfilUsuario perfilUsuario = new PerfilUsuario();
		perfilUsuario.setPerfil(PerfilEnum.CLIENTE);
		perfilUsuario.setUsuario(usuario);
		perfilUsuarioBC.insert(perfilUsuario);
	}
}
