
package br.com.gsn.sysbusweb.business;

import java.util.List;

import javax.inject.Inject;

import br.com.gsn.sysbusweb.domain.Terminal;
import br.com.gsn.sysbusweb.persistence.TerminalDAO;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;

@BusinessController
public class TerminalBC extends DelegateCrud<Terminal, Long, TerminalDAO> {
	private static final long serialVersionUID = 1L;

	@Inject
	private EnderecoBC enderecoBC;
	
	public Terminal salvar(Terminal terminal) {
		enderecoBC.insert(terminal.getEndereco());
		return this.insert(terminal);
	}
	
	public Terminal alterar(Terminal terminal) {
		enderecoBC.update(terminal.getEndereco());
		return this.update(terminal);
	}
	
	public List<Terminal> findByDescricao(String descricao) {
		return getDelegate().findByDescricao(descricao);
	}
}
