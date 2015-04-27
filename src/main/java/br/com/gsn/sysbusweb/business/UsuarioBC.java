
package br.com.gsn.sysbusweb.business;

import java.util.List;

import br.com.gsn.sysbusweb.domain.Usuario;
import br.com.gsn.sysbusweb.persistence.UsuarioDAO;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;

@BusinessController
public class UsuarioBC extends DelegateCrud<Usuario, Long, UsuarioDAO> {
	private static final long serialVersionUID = 1L;
	
	public List<Usuario> findByNome(String nome) {
		return getDelegate().findByNome(nome);
	}
	
	public Usuario getByUsernameEPassoword(String username, String password) {
		return getDelegate().getByUsernameEPassoword(username, password);
	}
}
