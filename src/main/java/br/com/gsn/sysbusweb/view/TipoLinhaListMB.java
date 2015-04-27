package br.com.gsn.sysbusweb.view;

import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import br.com.gsn.sysbusweb.business.TipoLinhaBC;
import br.com.gsn.sysbusweb.domain.TipoLinha;
import br.gov.frameworkdemoiselle.annotation.NextView;
import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractListPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;

@ViewController
@NextView("./tipoLinha_edit.jsf")
@PreviousView("./tipoLinha_list.jsf?faces-redirect=true")
public class TipoLinhaListMB extends AbstractListPageBean<TipoLinha, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private TipoLinhaBC tipoLinhaBC;
	
	private List<TipoLinha> listTipoLinha;
	
	private TipoLinha selecionada;

	private String descricao;

	@PostConstruct
	public void init() {
		if (this.listTipoLinha == null) {
			this.listTipoLinha = handleResultList();
		}
	}
	
	@Transactional
	public void deletar() {
		this.tipoLinhaBC.delete(selecionada.getId());
		this.listTipoLinha = handleResultList();
	}
	
	@Transactional
	public String deleteSelection() {
		boolean delete;
		for (Iterator<Long> iter = getSelection().keySet().iterator(); iter
				.hasNext();) {
			Long id = iter.next();
			delete = getSelection().get(id);
			if (delete) {
				tipoLinhaBC.delete(id);
				iter.remove();
			}
		}
		return getPreviousView();
	}
	
	@Override
	public List<TipoLinha> handleResultList() {
		return this.tipoLinhaBC.findAll();
	}
	
	public void findByDescricao() {
		this.listTipoLinha = this.tipoLinhaBC.findByDescricao(this.descricao);
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<TipoLinha> getListTipoLinha() {
		return listTipoLinha;
	}

	public TipoLinha getSelecionada() {
		return selecionada;
	}

	public void setSelecionada(TipoLinha selecionada) {
		this.selecionada = selecionada;
	}
	
	
	
}