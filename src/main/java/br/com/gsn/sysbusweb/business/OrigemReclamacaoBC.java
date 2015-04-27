
package br.com.gsn.sysbusweb.business;

import java.util.List;

import br.com.gsn.sysbusweb.domain.ObjetoReclamado;
import br.com.gsn.sysbusweb.domain.OrigemReclamacao;
import br.com.gsn.sysbusweb.domain.enums.ObjetoReclamadoEnum;
import br.com.gsn.sysbusweb.persistence.OrigemReclamacaoDAO;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;

@BusinessController
public class OrigemReclamacaoBC extends DelegateCrud<OrigemReclamacao, Long, OrigemReclamacaoDAO> {
	private static final long serialVersionUID = 1L;
	
	public List<OrigemReclamacao> findByObjetoReclamado(ObjetoReclamadoEnum objetoReclamado) {
		return getDelegate().findByObjetoReclamado(objetoReclamado);
	}
	
	public List<OrigemReclamacao> findByObjetoReclamado(ObjetoReclamado objetoReclamado) {
		return getDelegate().findByObjetoReclamado(objetoReclamado);
	}
	
}
