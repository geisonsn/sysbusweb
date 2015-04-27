package br.com.gsn.sysbusweb.view;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.apache.commons.codec.digest.DigestUtils;

import br.com.gsn.sysbusweb.business.UsuarioBC;
import br.com.gsn.sysbusweb.domain.Usuario;
import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.exception.ExceptionHandler;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractEditPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;

@ViewController
@PreviousView("./usuario_list.jsf")
public class UsuarioEditMB extends AbstractEditPageBean<Usuario, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioBC usuarioBC;

	@Override
	@Transactional
	public String delete() {
		this.usuarioBC.delete(getId());
		return getPreviousView();
	}

	@Override
	@Transactional
	public String insert() {
		getBean().setPassword(DigestUtils.sha256Hex(getBean().getPassword()));
		this.usuarioBC.insert(this.getBean());
		return getPreviousView();
	}

	@Override
	@Transactional
	public String update() {
		getBean().setPassword(DigestUtils.sha256Hex(getBean().getPassword()));
		this.usuarioBC.update(this.getBean());
		return getPreviousView();
	}

	@Override
	protected Usuario handleLoad(Long id) {
		return this.usuarioBC.load(id);
	}

	@ExceptionHandler
	public void capturaExcecao(PersistenceException e) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				"Registro duplicado",
				"Informações já cadastradas para outro registro");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

}