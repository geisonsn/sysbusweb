package br.com.gsn.sysbusweb.view;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.PersistenceException;

import br.com.gsn.sysbusweb.business.VeiculoBC;
import br.com.gsn.sysbusweb.domain.Veiculo;
import br.com.gsn.sysbusweb.util.Util;
import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.exception.ExceptionHandler;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractEditPageBean;

@ViewController
@PreviousView("./veiculo_list.jsf?faces-redirect=true")
public class VeiculoEditMB extends AbstractEditPageBean<Veiculo, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private VeiculoBC veiculoBC;
	
	@Override
	public String delete() {
		this.veiculoBC.delete(getId());
		return getPreviousView();
	}

	@Override
	public String insert() {
		this.getBean().setPlaca(Util.capitalize(this.getBean().getPlaca()));
		veiculoBC.insert(getBean());
		return getPreviousView();
	}

	@Override
	public String update() {
		this.getBean().setPlaca(Util.capitalize(this.getBean().getPlaca()));
		this.veiculoBC.update(getBean());
		return getPreviousView();
	}

	@Override
	protected Veiculo handleLoad(Long id) {
		Veiculo veiculo = this.veiculoBC.load(id);
		return veiculo;
	}

	@ExceptionHandler
	public void capturaExcecao(PersistenceException e) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Registro duplicado", "Informações já cadastradas para outro registro");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	@ExceptionHandler
	public void capturaExcecao(RuntimeException e) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Registro duplicado", e.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
}