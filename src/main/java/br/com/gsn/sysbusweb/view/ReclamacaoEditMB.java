
package br.com.gsn.sysbusweb.view;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;

import org.primefaces.component.selectonemenu.SelectOneMenu;

import br.com.gsn.sysbusweb.business.LinhaBC;
import br.com.gsn.sysbusweb.business.OrigemReclamacaoBC;
import br.com.gsn.sysbusweb.business.ReclamacaoBC;
import br.com.gsn.sysbusweb.domain.Linha;
import br.com.gsn.sysbusweb.domain.OrigemReclamacao;
import br.com.gsn.sysbusweb.domain.Reclamacao;
import br.com.gsn.sysbusweb.domain.enums.ObjetoReclamadoEnum;
import br.com.gsn.sysbusweb.util.Util;
import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractEditPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;

@ViewController
@PreviousView("./reclamacao_list.jsf?faces-redirect=true")
public class ReclamacaoEditMB extends AbstractEditPageBean<Reclamacao, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private ReclamacaoBC reclamacaoBC;

	@Inject
	private OrigemReclamacaoBC origemReclamacaoBC;
	
	@Inject
	private LinhaBC linhaBC;
	
	private ObjetoReclamadoEnum objetoReclamado;
	
	private List<OrigemReclamacao> comboOrigemReclamacao;
	
	@PostConstruct
	private void init() {
		carregarDados();
	}


	private void carregarDados() {
		if (isUpdateMode()) {
			ObjetoReclamadoEnum objetoReclamado = getBean().getOrigemReclamacao().getObjetoReclamado();
			carregarComboOrigemReclamacao(objetoReclamado);
			this.setObjetoReclamado(objetoReclamado);
		}
	}
	

	public List<OrigemReclamacao> getOrigemReclamacaoList(){
		return origemReclamacaoBC.findAll();
	}
			
	public ObjetoReclamadoEnum getObjetoReclamado() {
		return objetoReclamado;
	}
    
    public void setObjetoReclamado(ObjetoReclamadoEnum objetoReclamado) {
		this.objetoReclamado = objetoReclamado;
	}
    
	public List<ObjetoReclamadoEnum> getComboObjetoReclamado() {
		return ObjetoReclamadoEnum.list(Boolean.TRUE);
	}
	
	public List<Linha> getLinhaList(){
		return linhaBC.findAll();
	}
	
	public List<OrigemReclamacao> getComboOrigemReclamacao() {
		return comboOrigemReclamacao;
	}

	public void setComboOrigemReclamacao(
			List<OrigemReclamacao> comboOrigemReclamacao) {
		this.comboOrigemReclamacao = comboOrigemReclamacao;
	}
	
	@Override
	@Transactional
	public String delete() {
		this.reclamacaoBC.delete(getId());
		return getPreviousView();
	}
	
	@Override
	@Transactional
	public String insert() {
		this.getBean().setDataRegistro(new Date());
		this.getBean().setPlacaLinha(Util.capitalize(this.getBean().getPlacaLinha()));
		this.getBean().setDescricao(Util.emptyToNull(this.getBean().getDescricao()));
		this.reclamacaoBC.insert(this.getBean());
		return getPreviousView();
	}
	
	@Override
	@Transactional
	public String update() {
		this.getBean().setPlacaLinha(Util.capitalize(this.getBean().getPlacaLinha()));
		this.getBean().setDescricao(Util.emptyToNull(this.getBean().getDescricao()));
		this.reclamacaoBC.update(this.getBean());
		return getPreviousView();
	}
	
	@Override
	protected Reclamacao handleLoad(Long id) {
		return this.reclamacaoBC.load(id);
	}	
	
	public void changeObjetoReclamado(AjaxBehaviorEvent event) {
		ObjetoReclamadoEnum itemSelecionado = (ObjetoReclamadoEnum)((SelectOneMenu)event.getSource()).getValue();
		carregarComboOrigemReclamacao(itemSelecionado);
	}
	
	private void carregarComboOrigemReclamacao(ObjetoReclamadoEnum itemSelecionado) {
		comboOrigemReclamacao = origemReclamacaoBC.findByObjetoReclamado(itemSelecionado);
	}

	public Date getDataMaxima() {
		return new Date();
	}
	
	public String getHoraMaxima() {
		return new SimpleDateFormat("HH").format(Calendar.getInstance().getTime());
	}
	
	public String getMinutoMaximo() {
		return new SimpleDateFormat("mm").format(Calendar.getInstance().getTime());
	}
}