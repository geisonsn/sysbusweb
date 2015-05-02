package br.com.gsn.sysbusweb.view;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import br.com.gsn.sysbusweb.business.LinhaBC;
import br.com.gsn.sysbusweb.business.VeiculoBC;
import br.com.gsn.sysbusweb.domain.Linha;
import br.com.gsn.sysbusweb.domain.Veiculo;
import br.com.gsn.sysbusweb.util.MessagesUtil;
import br.com.gsn.sysbusweb.util.Util;
import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.exception.ExceptionHandler;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractEditPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;

@ViewController
@PreviousView("./veiculo_list.jsf?faces-redirect=true")
public class VeiculoEditMB extends AbstractEditPageBean<Veiculo, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private VeiculoBC veiculoBC;

	@Inject
	private LinhaBC linhaBC;

	@Inject
	private MessagesUtil messagesUtil;

	@Override
	@Transactional
	public String delete() {
		this.veiculoBC.delete(getId());
		return getPreviousView();
	}

	@Override
	@Transactional
	public String insert() {
		this.getBean().setPlaca(Util.capitalize(this.getBean().getPlaca()));
		this.veiculoBC.insert(getBean());
		return getPreviousView();
	}

	@Override
	@Transactional
	public String update() {
		this.getBean().setPlaca(Util.capitalize(this.getBean().getPlaca()));
		this.veiculoBC.update(getBean());
		return getPreviousView();
	}
	
	@Override
	protected Veiculo handleLoad(Long id) {
		return this.veiculoBC.load(id);
	}

	public List<Linha> getListLinha() {
		return this.linhaBC.findAll();
	}

	@ExceptionHandler
	public void capturaExcecao(PersistenceException e) {
		messagesUtil.exibirMensagemDuplicado();
	}

}