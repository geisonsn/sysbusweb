package br.com.gsn.sysbusweb.view;

import java.util.Iterator;
import java.util.List;

import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;

import org.primefaces.component.selectonemenu.SelectOneMenu;

import br.com.gsn.sysbusweb.business.ObjetoReclamadoBC;
import br.com.gsn.sysbusweb.business.OrigemReclamacaoBC;
import br.com.gsn.sysbusweb.domain.ObjetoReclamado;
import br.com.gsn.sysbusweb.domain.OrigemReclamacao;
import br.com.gsn.sysbusweb.domain.enums.ObjetoReclamadoEnum;
import br.gov.frameworkdemoiselle.annotation.NextView;
import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractListPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;

@ViewController
@NextView("./origemReclamacao_edit.jsf")
@PreviousView("./origemReclamacao_list.jsf")
public class OrigemReclamacaoListMB extends AbstractListPageBean<OrigemReclamacao, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private OrigemReclamacaoBC origemReclamacaoBC;
	
	@Inject
	private ObjetoReclamadoBC objetoReclamadoBC;
	
	private List<OrigemReclamacao> listOrigemReclamacao;
	
	private ObjetoReclamadoEnum objetoReclamado;
	
	private ObjetoReclamado objetoReclamadoBean;
	
	private OrigemReclamacao objetoExcluido;
	
	@Override
	protected List<OrigemReclamacao> handleResultList() {
		return null;
	}
	
	@Transactional
	public String deleteSelection() {
		boolean delete;
		for (Iterator<Long> iter = getSelection().keySet().iterator(); iter.hasNext();) {
			Long id = iter.next();
			delete = getSelection().get(id);
			if (delete) {
				origemReclamacaoBC.delete(id);
				iter.remove();
			}
		}
		return getPreviousView();
	}
	
	public void deletar() {
		this.origemReclamacaoBC.delete(objetoExcluido.getId());
		this.listOrigemReclamacao = handleResultList();
	}
	
	public void changeObjetoReclamado(AjaxBehaviorEvent event) {
		ObjetoReclamadoEnum itemSelecionado = (ObjetoReclamadoEnum)((SelectOneMenu)event.getSource()).getValue();
		this.carregarListaOrigemReclamacao(itemSelecionado);
	}
	
	public void changeObjetoReclamadoBean(AjaxBehaviorEvent event) {
		ObjetoReclamadoEnum itemSelecionado = (ObjetoReclamadoEnum)((SelectOneMenu)event.getSource()).getValue();
		
		this.carregarListaOrigemReclamacao(itemSelecionado);
	}
	
	private void carregarListaOrigemReclamacao(ObjetoReclamadoEnum objetoReclamado) {
		if (objetoReclamado != null) {
			this.listOrigemReclamacao = origemReclamacaoBC.findByObjetoReclamado(objetoReclamado);
		} else {
			this.listOrigemReclamacao = null;
		}
	}
	
	public List<ObjetoReclamadoEnum> getComboObjetoReclamado() {
		return  ObjetoReclamadoEnum.list(Boolean.TRUE);
	}
	
	public List<OrigemReclamacao> getListOrigemReclamacao() {
		return listOrigemReclamacao;
	}
	
	public OrigemReclamacao getObjetoExcluido() {
		return objetoExcluido;
	}

	public void setObjetoExcluido(OrigemReclamacao objetoExcluido) {
		this.objetoExcluido = objetoExcluido;
	}

	public ObjetoReclamadoEnum getObjetoReclamado() {
		return objetoReclamado;
	}
	
	public ObjetoReclamadoEnum setObjetoReclamado() {
		return objetoReclamado;
	}
	
	public List<ObjetoReclamado> getComboObjetoReclamadoBean() {
		return objetoReclamadoBC.findAll();
	}

	public ObjetoReclamado getObjetoReclamadoBean() {
		return objetoReclamadoBean;
	}

	public void setObjetoReclamadoBean(ObjetoReclamado objetoReclamadoBean) {
		this.objetoReclamadoBean = objetoReclamadoBean;
	}
    
}