
package br.com.gsn.sysbusweb.business;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.codec.digest.DigestUtils;

import br.com.gsn.sysbusweb.domain.PerfilUsuario;
import br.com.gsn.sysbusweb.domain.Usuario;
import br.com.gsn.sysbusweb.domain.enums.PerfilEnum;
import br.com.gsn.sysbusweb.persistence.UsuarioDAO;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;

@BusinessController
public class UsuarioBC extends DelegateCrud<Usuario, Long, UsuarioDAO> {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private PerfilUsuarioBC perfilUsuarioBC;
	
	public List<Usuario> findByNome(String nome) {
		return getDelegate().findByNome(nome);
	}
	
	public Usuario getByUsernameEPassoword(String username, String password) {
		return getDelegate().getByUsernameEPassoword(username, DigestUtils.sha256Hex(password));
	}
	
	public Usuario getByUsernameEEmail(String username, String email) {
		return getDelegate().getByUsernameEEmail(username, email);
	}
	
	public Usuario saveCliente(Usuario usuario) {
		
		usuario.setPassword(DigestUtils.sha256Hex(usuario.getPassword()));
		
		this.insert(usuario);
		
		PerfilUsuario perfilUsuario = new PerfilUsuario();
		perfilUsuario.setPerfil(PerfilEnum.CLIENTE);
		perfilUsuario.setUsuario(usuario);
		
		perfilUsuarioBC.insert(perfilUsuario);
		
		return usuario;
	}
}
