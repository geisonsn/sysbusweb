package br.com.gsn.sysbusweb.view;

import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.persistence.PersistenceException;

import br.com.gsn.sysbusweb.business.TerminalBC;
import br.com.gsn.sysbusweb.domain.Terminal;
import br.com.gsn.sysbusweb.domain.endereco.Endereco;
import br.com.gsn.sysbusweb.domain.endereco.Logradouro;
import br.com.gsn.sysbusweb.exception.CampoObrigatorioException;
import br.com.gsn.sysbusweb.util.MessagesUtil;
import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.exception.ExceptionHandler;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractEditPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;

@ViewController
@PreviousView("./terminal_list.jsf?faces-redirect=true")
public class TerminalEditMB extends AbstractEditPageBean<Terminal, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private TerminalBC terminalBC;
	
	@Inject 
	private MessagesUtil messagesUtil;
	
	private Logradouro logradouro;
	
	@Override
	@Transactional
	public String delete() {
		this.terminalBC.delete(getId());
		return getPreviousView();
	}

	@Override
	@Transactional
	public String insert() {
		
		validarCamposObrigatorios();
		
		getBean().setEndereco(new Endereco());
		getBean().getEndereco().setLogradouro(logradouro);
		
		this.terminalBC.salvar(this.getBean());
		return getPreviousView();
	}

	@Override
	@Transactional
	public String update() {
		
		validarCamposObrigatorios();
		
		Terminal terminal = this.getBean();
		
		this.terminalBC.alterar(terminal);
		return getPreviousView();
	}
	
	private void validarCamposObrigatorios() {
		if (logradouro == null) {
			throw new CampoObrigatorioException();
		}
	}

	@Override
	protected Terminal handleLoad(Long id) {
		Terminal terminal = this.terminalBC.load(id);
		this.logradouro = terminal.getEndereco().getLogradouro();
		return terminal;
	}
	
	public void onItemSelected(AjaxBehaviorEvent event) {
		//Na criação de uma terminal deve-se criar um novo endereço
		/*if (getBean().getEndereco() == null) {
			getBean().setEndereco(new Endereco());
		}
		
		Logradouro logradouro = (Logradouro) ((UIOutput)event.getSource()).getValue();
		getBean().getEndereco().setLogradouro(logradouro);*/
	}
	
	public Logradouro getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(Logradouro logradouro) {
		this.logradouro = logradouro;
	}

	@ExceptionHandler
	public void capturaExcecao(PersistenceException e) {
		messagesUtil.exibirMensagemDuplicado();
	}
	
	@ExceptionHandler
	public void capturaExcecao(CampoObrigatorioException e) {
		messagesUtil.exibirMensagemCampoObrigatorio();
	}
}