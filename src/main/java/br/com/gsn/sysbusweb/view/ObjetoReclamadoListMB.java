package br.com.gsn.sysbusweb.view;

import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import br.com.gsn.sysbusweb.business.ObjetoReclamadoBC;
import br.com.gsn.sysbusweb.business.OrigemReclamacaoBC;
import br.com.gsn.sysbusweb.domain.ObjetoReclamado;
import br.gov.frameworkdemoiselle.annotation.NextView;
import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractListPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;

@ViewController
@NextView("./objetoReclamado_edit.jsf")
@PreviousView("./objetoReclamado_list.jsf")
public class ObjetoReclamadoListMB extends AbstractListPageBean<ObjetoReclamado, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private ObjetoReclamadoBC objetoReclamadoBC;
	
	@Inject OrigemReclamacaoBC origemReclamacaoBC;
	
	private List<ObjetoReclamado> listObjetoReclamado;
	
	private String descricao;
	private String sigla;
	
	private ObjetoReclamado selecionado;
	
	@PostConstruct
	private void init() {
		if (this.listObjetoReclamado == null) {
			this.listObjetoReclamado = handleResultList();
		}
	}
	
	public void findByAll() {
		ObjetoReclamado pesquisado = new ObjetoReclamado(descricao, sigla);
		this.listObjetoReclamado = this.objetoReclamadoBC.findByAll(pesquisado);
	}
	
	@Override
	protected List<ObjetoReclamado> handleResultList() {
		return this.objetoReclamadoBC.findAll();
	}
	
	@Transactional
	public String deleteSelection() {
		boolean delete;
		for (Iterator<Long> iter = getSelection().keySet().iterator(); iter.hasNext();) {
			Long id = iter.next();
			delete = getSelection().get(id);
			if (delete) {
				objetoReclamadoBC.delete(id);
				iter.remove();
			}
		}
		return getPreviousView();
	}
	
	public void deletar() {
		this.objetoReclamadoBC.delete(selecionado.getId());
		this.listObjetoReclamado = handleResultList();
	}
	
	public List<ObjetoReclamado> getListObjetoReclamado() {
		return listObjetoReclamado;
	}
	
	public ObjetoReclamado getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(ObjetoReclamado selecionado) {
		this.selecionado = selecionado;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

}