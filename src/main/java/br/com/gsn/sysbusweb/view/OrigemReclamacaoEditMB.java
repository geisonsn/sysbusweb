package br.com.gsn.sysbusweb.view;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.model.DualListModel;

import br.com.gsn.sysbusweb.business.OrigemReclamacaoBC;
import br.com.gsn.sysbusweb.business.TipoReclamacaoBC;
import br.com.gsn.sysbusweb.domain.OrigemReclamacao;
import br.com.gsn.sysbusweb.domain.TipoReclamacao;
import br.com.gsn.sysbusweb.domain.enums.ObjetoReclamadoEnum;
import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.exception.ExceptionHandler;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractEditPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;

@ViewController
@PreviousView("./origemReclamacao_list.jsf?faces-redirect=true")
public class OrigemReclamacaoEditMB extends	AbstractEditPageBean<OrigemReclamacao, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private OrigemReclamacaoBC origemReclamacaoBC;
	
	@Inject
	private TipoReclamacaoBC tipoReclamacaoBC;
	
	private List<TipoReclamacao> reclamacoesCadastradas;
	
	private List<OrigemReclamacao> reclamacoesCadastradas2;
    
    private DualListModel<TipoReclamacao> reclamacoes = new DualListModel<>();
    
    private DualListModel<OrigemReclamacao> reclamacoes2 = new DualListModel<>();
    
    private ObjetoReclamadoEnum objetoReclamado;
	
	public DualListModel<OrigemReclamacao> getReclamacoes2() {
		return reclamacoes2;
	}
    
    public void setReclamacoes2(DualListModel<OrigemReclamacao> reclamacoes) {
		this.reclamacoes2 = reclamacoes;
	}
    
    public DualListModel<TipoReclamacao> getReclamacoes() {
    	return reclamacoes;
    }
    
    public void setReclamacoes(DualListModel<TipoReclamacao> reclamacoes) {
    	this.reclamacoes = reclamacoes;
    }
    
    public List<ObjetoReclamadoEnum> getComboObjetoReclamado() {
		return ObjetoReclamadoEnum.list(Boolean.TRUE);
	}
    
    public ObjetoReclamadoEnum getObjetoReclamado() {
		return objetoReclamado;
	}
    
    public void setObjetoReclamado(ObjetoReclamadoEnum objetoReclamado) {
		this.objetoReclamado = objetoReclamado;
	}
    
	@Override
	@Transactional
	public String delete() {
		this.origemReclamacaoBC.delete(getId());
		return getPreviousView();
	}

	@Transactional
	public void salvar() {
		List<TipoReclamacao> target = reclamacoes.getTarget();
//		List<OrigemReclamacao> target = reclamacoes.getTarget();
		origemReclamacaoBC.salvarReclamacoes(objetoReclamado, reclamacoesCadastradas, target);
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Registros salvos", "Operação realizada com sucesso");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	@Override
	@Transactional
	public String insert() {
		return getPreviousView();
	}

	@Override
	@Transactional
	public String update() {
		return getPreviousView();
	}

	@Override
	protected OrigemReclamacao handleLoad(Long id) {
		return this.origemReclamacaoBC.load(id);
	}
	
    public void changeObjetoReclamadoEnum(AjaxBehaviorEvent event) {
		ObjetoReclamadoEnum itemSelecionado = (ObjetoReclamadoEnum)((SelectOneMenu)event.getSource()).getValue();
		carregarPickedList(itemSelecionado);
	}
    
    private void carregarPickedList2(ObjetoReclamadoEnum objetoReclamado) {
    	
    	List<TipoReclamacao> listTipoReclamacao = new ArrayList<>();
    	
    	List<OrigemReclamacao> disponiveis = new ArrayList<>();
    	List<OrigemReclamacao> selecionadas = new ArrayList<>();
    	
    	
    	if (!ObjetoReclamadoEnum.SELECIONE.equals(objetoReclamado)) {
    		listTipoReclamacao = tipoReclamacaoBC.listTipoReclamacaoNaoCadastradasAoObjetoReclamado(objetoReclamado);
    		for (TipoReclamacao tipoReclamacao : listTipoReclamacao) {
    			disponiveis.add(new OrigemReclamacao(objetoReclamado, tipoReclamacao));
			}
    		selecionadas = reclamacoesCadastradas2 = origemReclamacaoBC.findByObjetoReclamado(objetoReclamado);
    		
    	}
		reclamacoes2 = new DualListModel<>(disponiveis, selecionadas);
	}
    
    private void carregarPickedList(ObjetoReclamadoEnum objetoReclamado) {
    	
    	List<TipoReclamacao> disponiveis = new ArrayList<>();
    	List<TipoReclamacao> selecionadas = new ArrayList<>();
    	
    	if (!ObjetoReclamadoEnum.SELECIONE.equals(objetoReclamado)) {
    		disponiveis = tipoReclamacaoBC.listTipoReclamacaoNaoCadastradasAoObjetoReclamado(objetoReclamado);
    		selecionadas = reclamacoesCadastradas = tipoReclamacaoBC
    				.listTipoReclamacaoCadastradasAoObjetoReclamado(objetoReclamado);
    	}
    	reclamacoes = new DualListModel<>(disponiveis, selecionadas);
    }
    
    @ExceptionHandler
	public void capturaExcecao(PersistenceException e) {
    	String titulo = "";
    	String mensagem = "";
    	if (e.getCause() instanceof ConstraintViolationException) {
    		titulo = "Registro em uso";
    		mensagem = "Registro adicionado não pode ser removido pois está em uso";
    	}
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, titulo, mensagem);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	@ExceptionHandler
	public void capturaExcecao(RuntimeException e) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				"Falha no sistema", "Falha desconhecida");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
}