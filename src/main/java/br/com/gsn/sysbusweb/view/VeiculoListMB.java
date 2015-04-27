package br.com.gsn.sysbusweb.view;

import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import br.com.gsn.sysbusweb.business.VeiculoBC;
import br.com.gsn.sysbusweb.domain.Veiculo;
import br.gov.frameworkdemoiselle.annotation.NextView;
import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractListPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;

@ViewController
@NextView("./veiculo_edit.jsf")
@PreviousView("./veiculo_list.jsf?faces-redirect=true")
public class VeiculoListMB extends AbstractListPageBean<Veiculo, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private VeiculoBC veiculoBC;

	private List<Veiculo> listVeiculo;

	private Veiculo objetoExcluido;

	private String linha;
	private String numeroRegistro;

	@PostConstruct
	public void init() {
		if (this.listVeiculo == null) {
			this.listVeiculo = handleResultList();
		}
	}

	@Transactional
	public void deletar() {
		this.veiculoBC.delete(objetoExcluido.getId());
		this.listVeiculo = handleResultList();
	}

	@Transactional
	public String deleteSelection() {
		boolean delete;
		for (Iterator<Long> iter = getSelection().keySet().iterator(); iter
				.hasNext();) {
			Long id = iter.next();
			delete = getSelection().get(id);
			if (delete) {
				veiculoBC.delete(id);
				iter.remove();
			}
		}
		return getPreviousView();
	}

	@Override
	public List<Veiculo> handleResultList() {
		return this.veiculoBC.findAll();
	}

	public void pesquisar() {
		this.listVeiculo = this.veiculoBC.findByLinhaByNumero(linha, numeroRegistro);
	}

	public String getLinha() {
		return linha;
	}

	public void setLinha(String linha) {
		this.linha = linha;
	}

	public String getNumeroRegistro() {
		return numeroRegistro;
	}

	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}

	public List<Veiculo> getListVeiculo() {
		return listVeiculo;
	}

	public Veiculo getObjetoExcluido() {
		return objetoExcluido;
	}

	public void setObjetoExcluido(Veiculo objetoExcluido) {
		this.objetoExcluido = objetoExcluido;
	}

}