
package br.com.gsn.sysbusweb.business;

import java.util.List;

import javax.inject.Inject;

import br.com.gsn.sysbusweb.domain.Empresa;
import br.com.gsn.sysbusweb.persistence.EmpresaDAO;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;

@BusinessController
public class EmpresaBC extends DelegateCrud<Empresa, Long, EmpresaDAO> {
	private static final long serialVersionUID = 1L;

	@Inject
	private EnderecoBC enderecoBC;
	
	public Empresa salvar(Empresa empresa) {
		enderecoBC.insert(empresa.getEndereco());
		return this.insert(empresa);
	}
	
	public Empresa alterar(Empresa empresa) {
		enderecoBC.update(empresa.getEndereco());
		return this.update(empresa);
	}
	
	public List<Empresa> findByNome(String nome) {
		return getDelegate().findByNome(nome);
	}
}
