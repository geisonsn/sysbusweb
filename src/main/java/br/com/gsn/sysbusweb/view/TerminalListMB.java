package br.com.gsn.sysbusweb.view;

import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import br.com.gsn.sysbusweb.business.TerminalBC;
import br.com.gsn.sysbusweb.domain.Terminal;
import br.gov.frameworkdemoiselle.annotation.NextView;
import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractListPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;

@ViewController
@NextView("./terminal_edit.jsf")
@PreviousView("./terminal_list.jsf?faces-redirect=true")
public class TerminalListMB extends AbstractListPageBean<Terminal, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private TerminalBC terminalBC;
	
	private List<Terminal> listTerminal;
	
	private Terminal objetoExcluido;

	private String descricao;

	@PostConstruct
	public void init() {
		if (this.listTerminal == null) {
			this.listTerminal = handleResultList();
		}
	}
	
	@Transactional
	public void deletar() {
		this.terminalBC.delete(objetoExcluido.getId());
		this.listTerminal = handleResultList();
	}
	
	@Transactional
	public String deleteSelection() {
		boolean delete;
		for (Iterator<Long> iter = getSelection().keySet().iterator(); iter
				.hasNext();) {
			Long id = iter.next();
			delete = getSelection().get(id);
			if (delete) {
				terminalBC.delete(id);
				iter.remove();
			}
		}
		return getPreviousView();
	}
	
	@Override
	public List<Terminal> handleResultList() {
		return this.terminalBC.findAll();
	}
	
	public void findByDescricao() {
		this.listTerminal = this.terminalBC.findByDescricao(this.descricao);
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Terminal> getListTerminal() {
		return listTerminal;
	}

	public Terminal getObjetoExcluido() {
		return objetoExcluido;
	}

	public void setObjetoExcluido(Terminal objetoExcluido) {
		this.objetoExcluido = objetoExcluido;
	}
	
	
	
}