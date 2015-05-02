
package br.com.gsn.sysbusweb.business;

import java.util.List;

import br.com.gsn.sysbusweb.domain.TipoReclamacao;
import br.com.gsn.sysbusweb.domain.enums.ObjetoReclamadoEnum;
import br.com.gsn.sysbusweb.persistence.TipoReclamacaoDAO;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;

@BusinessController
public class TipoReclamacaoBC extends DelegateCrud<TipoReclamacao, Long, TipoReclamacaoDAO> {
	private static final long serialVersionUID = 1L;

	public List<TipoReclamacao> findByDescricao(String descricao) {
		return getDelegate().findByDescricao(descricao);
	}
	
	public List<TipoReclamacao> listTipoReclamacaoCadastradasAoObjetoReclamado(ObjetoReclamadoEnum objetoReclamado) {
		return getDelegate().listTipoReclamacaoCadastradasAoObjetoReclamado(objetoReclamado);
	}
	
	public List<TipoReclamacao> listTipoReclamacaoNaoCadastradasAoObjetoReclamado(ObjetoReclamadoEnum objetoReclamado) {
		return getDelegate().listTipoReclamacaoNaoCadastradasAoObjetoReclamado(objetoReclamado);
	}
}
