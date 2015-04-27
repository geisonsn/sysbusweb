package br.com.gsn.sysbusweb.view;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.annotation.NextView;
import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractListPageBean;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import br.com.gsn.sysbusweb.business.ReclamacaoBC;
import br.com.gsn.sysbusweb.domain.Reclamacao;
import br.com.gsn.sysbusweb.domain.dto.ReclamacaoDTO;

@ViewController
@NextView("./reclamacao_edit.jsf")
@PreviousView("./reclamacao_list.jsf")
public class ReclamacaoListMB extends AbstractListPageBean<Reclamacao, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private ReclamacaoBC reclamacaoBC;
	
	@Override
	protected List<Reclamacao> handleResultList() {
		
		List<ReclamacaoDTO> lista = reclamacaoBC.listObjetosMaisReclamados();
		
		return this.reclamacaoBC.findAll();
	}
	
	@Transactional
	public String deleteSelection() {
		boolean delete;
		for (Iterator<Long> iter = getSelection().keySet().iterator(); iter.hasNext();) {
			Long id = iter.next();
			delete = getSelection().get(id);
			if (delete) {
				reclamacaoBC.delete(id);
				iter.remove();
			}
		}
		return getPreviousView();
	}

}