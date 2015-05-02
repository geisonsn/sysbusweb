package br.com.gsn.sysbusweb.view;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.PersistenceException;

import br.com.gsn.sysbusweb.business.PerfilUsuarioBC;
import br.com.gsn.sysbusweb.business.UsuarioBC;
import br.com.gsn.sysbusweb.domain.PerfilUsuario;
import br.com.gsn.sysbusweb.domain.enums.PerfilEnum;
import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.exception.ExceptionHandler;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractEditPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import br.gov.frameworkdemoiselle.util.Parameter;

@ViewController
@PreviousView("./usuario_list.jsf?faces-redirect=true")
public class PerfilUsuarioEditMB extends AbstractEditPageBean<PerfilUsuario, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private PerfilUsuarioBC perfilUsuarioBC;
	
	@Inject
	private UsuarioBC usuarioBC;
	
	private PerfilEnum[] perfisSelecionados;
	
	@Inject
	private Parameter<String> idUsuario;
	
	@PostConstruct
	private void init() {
		this.perfisSelecionados = this.perfilUsuarioBC.getPerfisSalvos(Long.valueOf(idUsuario.getValue()));
	}

	@Override
	@Transactional
	public String delete() {
		return null;
	}

	public String salvarPerfil() {
		Long idUser = Long.valueOf(idUsuario.getValue());
		perfilUsuarioBC.salvarPerfil(idUser, perfisSelecionados);
		return getPreviousView();
	}
	
	@Override
	@Transactional
	public String insert() {
		return getPreviousView();
	}

	@Override
	@Transactional
	public String update() {
		return getPreviousView();
	}

	@Override
	protected PerfilUsuario handleLoad(Long id) {
		return null;
	}
	
	public PerfilEnum[] getListPerfil() {
		return PerfilEnum.values();
	}
	
	public String getUsuario() {
		return usuarioBC.load(Long.valueOf(idUsuario.getValue())).getNome();
	}
	
	public PerfilEnum[] getPefisSelecionados() {
		return this.perfisSelecionados;
	}

	public void setPefisSelecionados(PerfilEnum[] pefisSelecionados) {
		this.perfisSelecionados = pefisSelecionados;
	}

	@ExceptionHandler
	public void capturaExcecao(PersistenceException e) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				"Registro duplicado",
				"Informações já cadastradas para outro registro");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

}