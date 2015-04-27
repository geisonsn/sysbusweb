package br.com.gsn.sysbusweb.view;

import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import br.com.gsn.sysbusweb.business.TipoReclamacaoBC;
import br.com.gsn.sysbusweb.domain.TipoReclamacao;
import br.gov.frameworkdemoiselle.annotation.NextView;
import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractListPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;

@ViewController
@NextView("./tipoReclamacao_edit.jsf")
@PreviousView("./tipoReclamacao_list.jsf")
public class TipoReclamacaoListMB extends AbstractListPageBean<TipoReclamacao, Long> {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private TipoReclamacaoBC tipoReclamacaoBC;
	
	private String descricao;
	
	private List<TipoReclamacao> listTipoReclamacao;
	
	private TipoReclamacao objetoRemocao;
	
	@PostConstruct
	private void init() {
		if (listTipoReclamacao == null) {
			this.listTipoReclamacao = this.handleResultList();
		}
	}
	
	@Override
	protected List<TipoReclamacao> handleResultList() {
		return this.tipoReclamacaoBC.findAll();
	}
	
	@Transactional
	public String deleteSelection() {
		boolean delete;
		for (Iterator<Long> iter = getSelection().keySet().iterator(); iter.hasNext();) {
			Long id = iter.next();
			delete = getSelection().get(id);
			if (delete) {
				tipoReclamacaoBC.delete(id);
				iter.remove();
			}
		}
		return getPreviousView();
	}
	
	@Transactional
	public void deletar() {
		this.tipoReclamacaoBC.delete(objetoRemocao.getId());
		this.listTipoReclamacao = handleResultList();
	}
	
	public void findByDescricao() {
		this.listTipoReclamacao = tipoReclamacaoBC.findByDescricao(this.descricao);
	}

	public List<TipoReclamacao> getListTipoReclamacao() {
		return this.listTipoReclamacao;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public TipoReclamacao getObjetoRemocao() {
		return objetoRemocao;
	}

	public void setObjetoRemocao(TipoReclamacao objetoRemocao) {
		this.objetoRemocao = objetoRemocao;
	}
	
	
}