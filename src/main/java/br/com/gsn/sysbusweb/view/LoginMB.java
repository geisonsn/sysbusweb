package br.com.gsn.sysbusweb.view;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.NoResultException;

import br.com.gsn.sysbusweb.business.UsuarioBC;
import br.com.gsn.sysbusweb.domain.Usuario;

@ManagedBean
@SessionScoped
public class LoginMB implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioBC usuarioBC;

	private String username;
	
	private String senha;
	
	private Usuario usuario;

	public String login() {
		
		try {
			
			this.usuario = usuarioBC.getByUsernameEPassoword(this.username, this.senha);
			
			if (this.usuario.isAdministrador()) {
				return "usuario_list.jsf?faces-redirect=true";
			} else if (this.usuario.isGestor()) {
				return "gestor.jsf?faces-redirect=true";
			} else if (this.usuario.isMantenedor()) {
				return "veiculo_list.jsf?faces-redirect=true";
			} else {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Acesso não permitido",
						"Este usuário não possui privilégios para acessar o sistema");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		} catch (NoResultException e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Acesso não permitido",
					"Usuário ou senha informados incorretamente!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return null;
	}
	
	public String logout() {
		return "login.jsf?faces-redirect=true";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public Usuario getUsuario() {
		return this.usuario;
	}
	
}
