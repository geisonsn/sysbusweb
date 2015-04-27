package br.com.gsn.sysbusweb.business;

import java.util.List;

import br.com.gsn.sysbusweb.domain.Reclamacao;
import br.com.gsn.sysbusweb.domain.dto.ReclamacaoDTO;
import br.com.gsn.sysbusweb.persistence.ReclamacaoDAO;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;

@BusinessController
public class ReclamacaoBC extends DelegateCrud<Reclamacao, Long, ReclamacaoDAO> {

	private static final long serialVersionUID = 1L;

	public List<ReclamacaoDTO> listObjetosMaisReclamados() {
		return getDelegate().listObjetosMaisReclamados();
	}

	public List<ReclamacaoDTO> listLinhasMaisReclamadas() {
		return getDelegate().listLinhasMaisReclamadas();
	}

	public List<ReclamacaoDTO> listEmpresasMaisReclamadas() {
		return getDelegate().listEmpresasMaisReclamadas();
	}

	public List<ReclamacaoDTO> listPrincipaisReclamacoes() {
		return getDelegate().listPrincipaisReclamacoes();
	}

}
