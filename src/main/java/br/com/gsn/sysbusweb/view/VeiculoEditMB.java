package br.com.gsn.sysbusweb.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.PersistenceException;

import br.com.gsn.sysbusweb.business.LinhaBC;
import br.com.gsn.sysbusweb.business.VeiculoLinhaBC;
import br.com.gsn.sysbusweb.domain.Linha;
import br.com.gsn.sysbusweb.domain.dto.VeiculoLinhaDTO;
import br.com.gsn.sysbusweb.exception.VeiculoExistenteException;
import br.com.gsn.sysbusweb.util.MessagesUtil;
import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.exception.ExceptionHandler;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import br.gov.frameworkdemoiselle.util.Parameter;

@ViewController
@PreviousView("./veiculo_list.jsf?faces-redirect=true")
public class VeiculoEditMB implements Serializable {
	/*extends AbstractEditPageBean<VeiculoLinha, Long> {*/

	private static final long serialVersionUID = 1L;

	@Inject
	private Parameter<String> id;
	
	@Inject
	private VeiculoLinhaBC veiculoLinhaBC;
	
	private VeiculoLinhaDTO bean;

	@Inject
	private LinhaBC linhaBC;

	@Inject
	private MessagesUtil messagesUtil;
	
	@PostConstruct
	private void init() {
//		System.out.println(id);
		/*if (id.getValue() != null) {
			System.out.println(id.getValue());
//			handleLoad(Long.valueOf(id.getValue()));
		} else {
			
		}*/
		
		/*if (this.bean == null) {
			initBean();
		}*/
		
	}
	
	public VeiculoLinhaDTO getBean() {
		if (this.bean == null) {
			initBean();
		}

		return this.bean;
	}
	
	public void setBean(VeiculoLinhaDTO bean) {
		this.bean = bean;
	}

	/*@Override
	@Transactional
	public String delete() {
		this.veiculoLinhaBC.delete(getId());
		return getPreviousView();
	}*/

	@Transactional
	public String insert() {
		veiculoLinhaBC.save(getBean().getVeiculoLinha());
		return getPreviousView();
	}

	@Transactional
	public String update() {
		this.veiculoLinhaBC.update(getBean().getVeiculoLinha());
		return getPreviousView();
	}
	
	public List<Linha> getListLinha() {
		return this.linhaBC.findAll();
	}

	public String getPreviousView() { 
		return "./veiculo_list.jsf?faces-redirect=true";
	}
	
	private void initBean() {
		if (isUpdateMode()) {
			this.bean = this.loadBean();
		} else {
			this.bean = this.createBean();
		}
	}
	
	public boolean isUpdateMode() {
		return id.getValue() != null;
	}

	private VeiculoLinhaDTO loadBean() {
		return new VeiculoLinhaDTO(this.veiculoLinhaBC.load(Long.valueOf(id.getValue())));
	}

	private VeiculoLinhaDTO createBean() {
		return new VeiculoLinhaDTO();
	}
	
	@ExceptionHandler
	public void capturaExcecao(PersistenceException e) {
		messagesUtil.exibirMensagemDuplicado();
	}
	
	@ExceptionHandler
	public void capturaExcecao(VeiculoExistenteException e) {
		messagesUtil.exibirMensagem("Veículo existente", e);
	}
}