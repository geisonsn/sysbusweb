package br.com.gsn.sysbusweb.view;

import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.persistence.PersistenceException;

import br.com.gsn.sysbusweb.business.EmpresaBC;
import br.com.gsn.sysbusweb.domain.Empresa;
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
@PreviousView("./empresa_list.jsf?faces-redirect=true")
public class EmpresaEditMB extends AbstractEditPageBean<Empresa, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private EmpresaBC empresaBC;
	
	@Inject 
	private MessagesUtil messagesUtil;
	
	private Logradouro logradouro;
	
	
	@Override
	@Transactional
	public String delete() {
		this.empresaBC.delete(getId());
		return getPreviousView();
	}

	@Override
	@Transactional
	public String insert() {
		
		validarCamposObrigatorios();
		
		getBean().setEndereco(new Endereco());
		getBean().getEndereco().setLogradouro(logradouro);
		
		this.empresaBC.salvar(this.getBean());
		return getPreviousView();
	}

	@Override
	@Transactional
	public String update() {
		
		validarCamposObrigatorios();
		
		Empresa empresa = this.getBean();
		
		this.empresaBC.alterar(empresa);
		return getPreviousView();
	}
	
	private void validarCamposObrigatorios() {
		if (logradouro == null) {
			throw new CampoObrigatorioException();
		}
	}

	@Override
	protected Empresa handleLoad(Long id) {
		Empresa empresa = this.empresaBC.load(id);
		this.logradouro = empresa.getEndereco().getLogradouro();
		return empresa;
	}
	
	public void onItemSelected(AjaxBehaviorEvent event) {
		//Na criação de uma empresa deve-se criar um novo endereço
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