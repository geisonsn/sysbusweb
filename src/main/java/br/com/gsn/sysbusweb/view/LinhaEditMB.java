package br.com.gsn.sysbusweb.view;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.PersistenceException;

import br.com.gsn.sysbusweb.business.EmpresaBC;
import br.com.gsn.sysbusweb.business.LinhaBC;
import br.com.gsn.sysbusweb.business.TerminalBC;
import br.com.gsn.sysbusweb.business.TipoLinhaBC;
import br.com.gsn.sysbusweb.domain.Empresa;
import br.com.gsn.sysbusweb.domain.Linha;
import br.com.gsn.sysbusweb.domain.Terminal;
import br.com.gsn.sysbusweb.domain.TipoLinha;
import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.exception.ExceptionHandler;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractEditPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;

@ViewController
@PreviousView("./linha_list.jsf?faces-redirect=true")
public class LinhaEditMB extends AbstractEditPageBean<Linha, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private LinhaBC linhaBC;
	
	@Inject
	private EmpresaBC empresaBC;
	
	@Inject
	private TipoLinhaBC tipoLinhaBC;
	
	@Inject
	private TerminalBC terminalBC;
	
	@Override
	@Transactional
	public String delete() {
		this.linhaBC.delete(getId());
		return getPreviousView();
	}

	@Override
	@Transactional
	public String insert() {
		this.linhaBC.insert(getBean());
		return getPreviousView();
	}

	@Override
	@Transactional
	public String update() {
		this.linhaBC.update(getBean());
		return getPreviousView();
	}

	@Override
	protected Linha handleLoad(Long id) {
		Linha linha = this.linhaBC.load(id);
		return linha;
	}
	
	public List<Empresa> getListEmpresa() {
		return this.empresaBC.findAll();
	}
	
	public List<Terminal> getListTerminal() {
		return this.terminalBC.findAll();
	}
	
	public List<TipoLinha> getListTipoLinha() {
		return this.tipoLinhaBC.findAll();
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