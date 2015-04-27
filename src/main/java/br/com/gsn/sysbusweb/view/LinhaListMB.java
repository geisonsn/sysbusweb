package br.com.gsn.sysbusweb.view;

import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import br.com.gsn.sysbusweb.business.LinhaBC;
import br.com.gsn.sysbusweb.domain.Linha;
import br.gov.frameworkdemoiselle.annotation.NextView;
import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractListPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;

@ViewController
@NextView("./linha_edit.jsf")
@PreviousView("./linha_list.jsf?faces-redirect=true")
public class LinhaListMB extends AbstractListPageBean<Linha, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private LinhaBC linhaBC;

	private List<Linha> listLinha;

	private Linha objetoExcluido;

	private String descricao;
	private String numero;

	@PostConstruct
	public void init() {
		if (this.listLinha == null) {
			this.listLinha = handleResultList();
		}
	}

	@Transactional
	public void deletar() {
		this.linhaBC.delete(objetoExcluido.getId());
		this.listLinha = handleResultList();
	}

	@Transactional
	public String deleteSelection() {
		boolean delete;
		for (Iterator<Long> iter = getSelection().keySet().iterator(); iter
				.hasNext();) {
			Long id = iter.next();
			delete = getSelection().get(id);
			if (delete) {
				linhaBC.delete(id);
				iter.remove();
			}
		}
		return getPreviousView();
	}

	@Override
	public List<Linha> handleResultList() {
		return this.linhaBC.findAll();
	}

	public void findByDescricaoByNumero() {
		this.listLinha = this.linhaBC.findByDescricaoByNumero(this.descricao,
				this.numero);
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public List<Linha> getListLinha() {
		return listLinha;
	}

	public Linha getObjetoExcluido() {
		return objetoExcluido;
	}

	public void setObjetoExcluido(Linha objetoExcluido) {
		this.objetoExcluido = objetoExcluido;
	}

}