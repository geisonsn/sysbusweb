package br.com.gsn.sysbusweb.view;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.model.DualListModel;

import br.com.gsn.sysbusweb.business.LinhaBC;
import br.com.gsn.sysbusweb.business.VeiculoBC;
import br.com.gsn.sysbusweb.business.VeiculoLinhaBC;
import br.com.gsn.sysbusweb.domain.Linha;
import br.com.gsn.sysbusweb.domain.Veiculo;
import br.com.gsn.sysbusweb.domain.VeiculoLinha;
import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.exception.ExceptionHandler;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractEditPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;

@ViewController
@PreviousView("./veiculo_linha_list.jsf?faces-redirect=true")
public class VeiculoLinhaEditMB extends AbstractEditPageBean<VeiculoLinha, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private LinhaBC linhaBC;
	
	@Inject
	private VeiculoBC veiculoBC;
	
	@Inject
	private VeiculoLinhaBC veiculoLinhaBC;
	
    private DualListModel<Veiculo> veiculos = new DualListModel<Veiculo>();
    
	
    public DualListModel<Veiculo> getVeiculos() {
    	return veiculos;
    }
    
    public void setVeiculos(DualListModel<Veiculo> veiculos) {
    	this.veiculos = veiculos;
    }
    
    public void changeLinha(AjaxBehaviorEvent event) {
		Linha linha = (Linha)((SelectOneMenu)event.getSource()).getValue();
		carregarPickedList(linha);
	}
    
    private void carregarPickedList(Linha linha) {
    	
    	List<Veiculo> disponiveis = new ArrayList<Veiculo>();
    	List<Veiculo> associadas = new ArrayList<Veiculo>();
    	
    	if (linha != null) {
    		disponiveis = veiculoBC.listVeiculosNaoCadastradosParaLinha(linha);
    		associadas = veiculoBC.listVeiculosCadastradosParaLinha(linha);
    	}
    	
    	this.veiculos = new DualListModel<Veiculo>(disponiveis, associadas);
    }
    
    
	@Override
	public String delete() {
		return getPreviousView();
	}

	@Override
	public String insert() {
		return getPreviousView();
	}
	
	@Transactional
	public void save() {
		
		List<Veiculo> veiculosSelecionados = veiculos.getTarget();
		
		veiculoLinhaBC.salvarVeiculos(getBean().getLinha(), veiculosSelecionados);
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Registros salvos", "Operação realizada com sucesso");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	@Override
	public String update() {
		return getPreviousView();
	}

	@Override
	protected VeiculoLinha handleLoad(Long id) {
		VeiculoLinha veiculoLinha = this.veiculoLinhaBC.load(id);
		return veiculoLinha;
	}
	
	public List<Linha> getListLinha() {
		List<Linha> findAll = this.linhaBC.findAll();
		return findAll;
	}
	
	public List<Veiculo> getListVeiculo() {
		return this.veiculoBC.findAll();
	}
	
	@ExceptionHandler
	public void capturaExcecao(PersistenceException e) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Registro duplicado", "Informações já cadastradas para outro registro");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	@ExceptionHandler
	public void capturaExcecao(RuntimeException e) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Registro duplicado", e.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
}