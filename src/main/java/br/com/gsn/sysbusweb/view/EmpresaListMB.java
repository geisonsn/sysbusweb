package br.com.gsn.sysbusweb.view;

import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import br.com.gsn.sysbusweb.business.EmpresaBC;
import br.com.gsn.sysbusweb.domain.Empresa;
import br.gov.frameworkdemoiselle.annotation.NextView;
import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractListPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;

@ViewController
@NextView("./empresa_edit.jsf")
@PreviousView("./empresa_list.jsf?faces-redirect=true")
public class EmpresaListMB extends AbstractListPageBean<Empresa, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private EmpresaBC empresaBC;
	
	private List<Empresa> listEmpresa;
	
	private Empresa objetoExcluido;

	private String nome;

	@PostConstruct
	public void init() {
		if (this.listEmpresa == null) {
			this.listEmpresa = handleResultList();
		}
	}
	
	@Transactional
	public void deletar() {
		this.empresaBC.delete(objetoExcluido.getId());
		this.listEmpresa = handleResultList();
	}
	
	@Transactional
	public String deleteSelection() {
		boolean delete;
		for (Iterator<Long> iter = getSelection().keySet().iterator(); iter
				.hasNext();) {
			Long id = iter.next();
			delete = getSelection().get(id);
			if (delete) {
				empresaBC.delete(id);
				iter.remove();
			}
		}
		return getPreviousView();
	}
	
	@Override
	public List<Empresa> handleResultList() {
		return this.empresaBC.findAll();
	}
	
	public void findByNome() {
		this.listEmpresa = this.empresaBC.findByNome(this.nome);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Empresa> getListEmpresa() {
		return listEmpresa;
	}

	public Empresa getObjetoExcluido() {
		return objetoExcluido;
	}

	public void setObjetoExcluido(Empresa objetoExcluido) {
		this.objetoExcluido = objetoExcluido;
	}
	
	
	
}