package br.com.gsn.sysbusweb.view;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.primefaces.component.selectonemenu.SelectOneMenu;

import br.com.gsn.sysbusweb.business.ObjetoReclamadoBC;
import br.com.gsn.sysbusweb.business.OrigemReclamacaoBC;
import br.com.gsn.sysbusweb.domain.ObjetoReclamado;
import br.com.gsn.sysbusweb.domain.OrigemReclamacao;
import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.exception.ExceptionHandler;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractEditPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;

@ViewController
@PreviousView("./origemReclamacao_list.jsf?faces-redirect=true")
public class OrigemReclamacaoEditMB extends	AbstractEditPageBean<OrigemReclamacao, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private OrigemReclamacaoBC origemReclamacaoBC;
	
	@Inject
	private ObjetoReclamadoBC objetoReclamadoBC;
	
	private ObjetoReclamado objetoReclamado;
	
	private List<OrigemReclamacao> listOrigemReclamacao;
	
	@Override
	@Transactional
	public String delete() {
		this.origemReclamacaoBC.delete(getId());
		return getPreviousView();
	}

	@Override
	@Transactional
	public String insert() {
		this.origemReclamacaoBC.insert(this.getBean());
		return getPreviousView();
	}

	@Override
	@Transactional
	public String update() {
		try {
			this.origemReclamacaoBC.update(this.getBean());
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
	protected OrigemReclamacao handleLoad(Long id) {
		return this.origemReclamacaoBC.load(id);
	}
	
	public void add() {
		System.out.println("fdfd");
	}
	
	public List<ObjetoReclamado> getComboObjetoReclamado() {
		return  objetoReclamadoBC.findAll();
	}
	
	public void changeObjetoReclamado(AjaxBehaviorEvent event) {
		ObjetoReclamado itemSelecionado = (ObjetoReclamado)((SelectOneMenu)event.getSource()).getValue();
		this.carregarListaOrigemReclamacao(itemSelecionado);
	}
	
	public List<OrigemReclamacao> getListOrigemReclamacao() {
		return listOrigemReclamacao;
	}
	
	private void carregarListaOrigemReclamacao(ObjetoReclamado objetoReclamado) {
		if (objetoReclamado != null) {
			this.listOrigemReclamacao = origemReclamacaoBC.findByObjetoReclamado(objetoReclamado);
		} else {
			listOrigemReclamacao = null;
		}
	}
	
	public ObjetoReclamado getObjetoReclamado() {
		return objetoReclamado;
	}
	
	public ObjetoReclamado setObjetoReclamado() {
		return objetoReclamado;
	}

	@ExceptionHandler
	public void capturaExcecao(PersistenceException e) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				"Registro duplicado",
				"Já existe um registro com os dados informados");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	@ExceptionHandler
	public void capturaExcecao(RuntimeException e) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				"Falha no sistema", "Falha desconhecida");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

}