package br.com.gsn.sysbusweb.view;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.PersistenceException;

import br.com.gsn.sysbusweb.business.TipoLinhaBC;
import br.com.gsn.sysbusweb.domain.TipoLinha;
import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.exception.ExceptionHandler;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractEditPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;

@ViewController
@PreviousView("./tipoLinha_list.jsf?faces-redirect=true")
public class TipoLinhaEditMB extends AbstractEditPageBean<TipoLinha, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private TipoLinhaBC tipoLinhaBC;

	@Override
	@Transactional
	public String delete() {
		this.tipoLinhaBC.delete(getId());
		return getPreviousView();
	}

	@Override
	@Transactional
	public String insert() {
		this.tipoLinhaBC.insert(this.getBean());
		return getPreviousView();
	}

	@Override
	@Transactional
	public String update() {
		this.tipoLinhaBC.update(this.getBean());
		return getPreviousView();
	}

	@Override
	protected TipoLinha handleLoad(Long id) {
		return this.tipoLinhaBC.load(id);
	}
	
	@ExceptionHandler
	public void capturaExcecao(PersistenceException e) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Registro duplicado", "Registro duplicado para o tipo de linha informado");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	@ExceptionHandler
	public void capturaExcecao(RuntimeException e) {
		throw e;
	}
}