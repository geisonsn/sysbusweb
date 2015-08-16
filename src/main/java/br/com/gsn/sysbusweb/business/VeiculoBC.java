package br.com.gsn.sysbusweb.business;

import java.util.List;

import br.com.gsn.sysbusweb.domain.Veiculo;
import br.com.gsn.sysbusweb.persistence.VeiculoDAO;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;

@BusinessController
public class VeiculoBC extends DelegateCrud<Veiculo, Long, VeiculoDAO> {
	private static final long serialVersionUID = 1L;

	public List<Veiculo> findByLinhaByNumero(String linha, String numeroRegistro) {
		return getDelegate().findByLinhaByNumero(linha, numeroRegistro);
	}
	
	public List<Veiculo> findByNumeroLinha(String numeroLinha) {
		return getDelegate().findByNumeroLinha(numeroLinha);
	}

}
