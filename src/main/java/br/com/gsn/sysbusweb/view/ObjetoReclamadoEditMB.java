package br.com.gsn.sysbusweb.view;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.PersistenceException;

import br.com.gsn.sysbusweb.business.ObjetoReclamadoBC;
import br.com.gsn.sysbusweb.business.TipoReclamacaoBC;
import br.com.gsn.sysbusweb.domain.ObjetoReclamado;
import br.com.gsn.sysbusweb.domain.TipoReclamacao;
import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.exception.ExceptionHandler;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractEditPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;

@ViewController
@PreviousView("./objetoReclamado_list.jsf?faces-redirect=true")
public class ObjetoReclamadoEditMB extends
		AbstractEditPageBean<ObjetoReclamado, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private ObjetoReclamadoBC objetoReclamadoBC;
	
	@Inject
	private TipoReclamacaoBC tipoReclamacaoBC;
	
	private TipoReclamacao tipoReclamacao;

	@Override
	@Transactional
	public String delete() {
		this.objetoReclamadoBC.delete(getId());
		return getPreviousView();
	}

	@Override
	@Transactional
	public String insert() {
		this.objetoReclamadoBC.insert(this.getBean());
		return getPreviousView();
	}

	@Override
	@Transactional
	public String update() {
		try {
			this.objetoReclamadoBC.update(this.getBean());
			return getPreviousView();
		} catch (RuntimeException e) {
			e.printStackTrace();
			return getCurrentView();
		} catch (Exception e) {
			e.printStackTrace();
			return getCurrentView();
		}
	}

	@Override
	protected ObjetoReclamado handleLoad(Long id) {
		return this.objetoReclamadoBC.load(id);
	}
	
	public void add() {
		if (tipoReclamacao != null) {
			
		}
	}
	
	public List<TipoReclamacao> getListTipoReclamacao() {
		return this.tipoReclamacaoBC.findAll();
	}

	public TipoReclamacao getTipoReclamacao() {
		return tipoReclamacao;
	}

	public void setTipoReclamacao(TipoReclamacao tipoReclamacao) {
		this.tipoReclamacao = tipoReclamacao;
	}

	@ExceptionHandler
	public void capturaExcecao(PersistenceException e) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				"Registro duplicado",
				"Já existe um registro com os dados informados");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
/*
	@ExceptionHandler
	public void capturaExcecao(TransactionException e) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				"Falha no sistema", "Falha desconhecida");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	@ExceptionHandler
	public void capturaExcecao(RuntimeException e) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				"Falha no sistema", "Falha desconhecida");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	@ExceptionHandler
	public void capturaExcecao(ConstraintViolationException e) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				"Falha no sistema", "Falha desconhecida");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	@ExceptionHandler
	public void capturaExcecao(RollbackException e) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				"Falha no sistema", "Falha desconhecida");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}*/
}