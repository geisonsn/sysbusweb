
package br.com.gsn.sysbusweb.view;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.com.gsn.sysbusweb.business.LinhaBC;
import br.com.gsn.sysbusweb.business.OrigemReclamacaoBC;
import br.com.gsn.sysbusweb.business.ReclamacaoBC;
import br.com.gsn.sysbusweb.domain.Linha;
import br.com.gsn.sysbusweb.domain.OrigemReclamacao;
import br.com.gsn.sysbusweb.domain.Reclamacao;
import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractEditPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;

@ViewController
@PreviousView("./reclamacao_list.jsf")
public class ReclamacaoEditMB extends AbstractEditPageBean<Reclamacao, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private ReclamacaoBC reclamacaoBC;

	@Inject
	private OrigemReclamacaoBC origemReclamacaoBC;
	
	public List<OrigemReclamacao> getOrigemReclamacaoList(){
		return origemReclamacaoBC.findAll();
	}
			
	@Inject
	private LinhaBC linhaBC;
	
	public List<Linha> getLinhaList(){
		return linhaBC.findAll();
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
		this.getBean().setDataRegistro(Calendar.getInstance().getTime());
		this.reclamacaoBC.insert(this.getBean());
		return getPreviousView();
	}
	
	@Override
	@Transactional
	public String update() {
		this.reclamacaoBC.update(this.getBean());
		return getPreviousView();
	}
	
	@Override
	protected Reclamacao handleLoad(Long id) {
		return this.reclamacaoBC.load(id);
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