package br.com.gsn.sysbusweb.view;

import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import br.com.gsn.sysbusweb.business.VeiculoLinhaBC;
import br.com.gsn.sysbusweb.domain.VeiculoLinha;
import br.gov.frameworkdemoiselle.annotation.NextView;
import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractListPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;

@ViewController
@NextView("./veiculo_linha_edit.jsf")
@PreviousView("./veiculo_linha_list.jsf?faces-redirect=true")
public class VeiculoLinhaListMB extends AbstractListPageBean<VeiculoLinha, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private VeiculoLinhaBC veiculoLinhaBC;

	private List<VeiculoLinha> listVeiculo;

	private VeiculoLinha objetoExcluido;

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
		this.veiculoLinhaBC.delete(objetoExcluido.getId());
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
				veiculoLinhaBC.delete(id);
				iter.remove();
			}
		}
		return getPreviousView();
	}

	@Override
	public List<VeiculoLinha> handleResultList() {
		return this.veiculoLinhaBC.findAll();
	}

	public void pesquisar() {
		this.listVeiculo = this.veiculoLinhaBC.findByLinhaByNumero(linha, numeroRegistro);
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

	public List<VeiculoLinha> getListVeiculo() {
		return listVeiculo;
	}

	public VeiculoLinha getObjetoExcluido() {
		return objetoExcluido;
	}

	public void setObjetoExcluido(VeiculoLinha objetoExcluido) {
		this.objetoExcluido = objetoExcluido;
	}

}