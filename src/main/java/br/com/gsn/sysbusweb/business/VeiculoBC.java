package br.com.gsn.sysbusweb.business;

import java.util.Collections;
import java.util.List;

import br.com.gsn.sysbusweb.domain.Veiculo;
import br.com.gsn.sysbusweb.domain.dto.VeiculoDTO;
import br.com.gsn.sysbusweb.persistence.VeiculoDAO;
import br.com.gsn.sysbusweb.util.ModelMapperUtil;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;

@BusinessController
public class VeiculoBC extends DelegateCrud<Veiculo, Long, VeiculoDAO> {
	private static final long serialVersionUID = 1L;

	public List<Veiculo> findByLinhaByNumero(String linha, String numeroRegistro) {
		return getDelegate().findByLinhaByNumero(linha, numeroRegistro);
	}
	
	public List<VeiculoDTO> findByNumeroLinha(String numeroLinha) {

		List<Veiculo> veiculos = getDelegate().findByNumeroLinha(numeroLinha);
		
		if (veiculos.isEmpty()) {
			return Collections.emptyList();
		}
		
		return ModelMapperUtil.map(veiculos, VeiculoDTO.class);
	}

}
