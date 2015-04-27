package br.com.gsn.sysbusweb.view;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.PersistenceException;

import br.com.gsn.sysbusweb.business.TipoReclamacaoBC;
import br.com.gsn.sysbusweb.domain.TipoReclamacao;
import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.exception.ExceptionHandler;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractEditPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;

@ViewController
@PreviousView("./tipoReclamacao_list.jsf")
public class TipoReclamacaoEditMB extends
		AbstractEditPageBean<TipoReclamacao, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private TipoReclamacaoBC tipoReclamacaoBC;

	@Override
	@Transactional
	public String delete() {
		this.tipoReclamacaoBC.delete(getId());
		return getPreviousView();
	}

	@Override
	@Transactional
	public String insert() {
		this.tipoReclamacaoBC.insert(this.getBean());
		return getPreviousView();
	}

	@Override
	@Transactional
	public String update() {
		this.tipoReclamacaoBC.update(this.getBean());
		return getPreviousView();
	}

	@Override
	protected TipoReclamacao handleLoad(Long id) {
		return this.tipoReclamacaoBC.load(id);
	}
	
	@ExceptionHandler
	public void capturaExcecao(PersistenceException e) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Registro duplicado", "Registro já existente");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
}