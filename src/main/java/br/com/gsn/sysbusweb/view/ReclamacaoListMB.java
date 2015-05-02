package br.com.gsn.sysbusweb.view;

import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import br.com.gsn.sysbusweb.business.ReclamacaoBC;
import br.com.gsn.sysbusweb.domain.Reclamacao;
import br.gov.frameworkdemoiselle.annotation.NextView;
import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractListPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;

@ViewController
@NextView("./reclamacao_edit.jsf")
@PreviousView("./reclamacao_list.jsf")
public class ReclamacaoListMB extends AbstractListPageBean<Reclamacao, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private ReclamacaoBC reclamacaoBC;

	private List<Reclamacao> listReclamacao;
	
	private Reclamacao objetoExcluido;
	
	
	public List<Reclamacao> getListReclamacao() {
		return listReclamacao;
	}

	public void setListReclamacao(List<Reclamacao> listReclamacao) {
		this.listReclamacao = listReclamacao;
	}
	
	public Reclamacao getObjetoExcluido() {
		return objetoExcluido;
	}

	public void setObjetoExcluido(Reclamacao objetoExcluido) {
		this.objetoExcluido = objetoExcluido;
	}

	@PostConstruct
	public void init() {
		if (this.listReclamacao == null) {
			this.listReclamacao = handleResultList();
		}
	}
	
	@Override
	protected List<Reclamacao> handleResultList() {
		return this.reclamacaoBC.findAll();
	}
	
	@Transactional
	public String deleteSelection() {
		boolean delete;
		for (Iterator<Long> iter = getSelection().keySet().iterator(); iter.hasNext();) {
			Long id = iter.next();
			delete = getSelection().get(id);
			if (delete) {
				reclamacaoBC.delete(id);
				iter.remove();
			}
		}
		return getPreviousView();
	}
	
	@Transactional
	public void deletar() {
		this.reclamacaoBC.delete(objetoExcluido.getId());
		this.listReclamacao = handleResultList();
	}

}