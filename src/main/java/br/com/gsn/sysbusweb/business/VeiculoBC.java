package br.com.gsn.sysbusweb.business;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import br.com.gsn.sysbusweb.domain.Linha;
import br.com.gsn.sysbusweb.domain.Veiculo;
import br.com.gsn.sysbusweb.domain.dto.VeiculoDTO;
import br.com.gsn.sysbusweb.exception.VeiculoExistenteException;
import br.com.gsn.sysbusweb.persistence.VeiculoDAO;
import br.com.gsn.sysbusweb.util.ModelMapperUtil;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;

@BusinessController
public class VeiculoBC extends DelegateCrud<Veiculo, Long, VeiculoDAO> {
	private static final long serialVersionUID = 1L;

	@Inject
	private LinhaBC linhaBC;
	
	public List<Veiculo> findByLinhaByNumero(String linha, String numeroRegistro) {
		return getDelegate().findByLinhaByNumero(linha, numeroRegistro);
	}
	
	public Veiculo getByNumeroRegistro(String numeroRegistro) {
		return getDelegate().getByNumeroRegistro(numeroRegistro);
	}
	
	public List<VeiculoDTO> findByNumeroLinha(String numeroLinha) {

		List<Veiculo> veiculos = getDelegate().findByNumeroLinha(numeroLinha);
		
		if (veiculos.isEmpty()) {
			return Collections.emptyList();
		}
		
		return ModelMapperUtil.map(veiculos, VeiculoDTO.class);
	}

	public void saveVeiculo(VeiculoDTO veiculo) throws VeiculoExistenteException {
		/**
		 * Verifica se já existe veículo com o mesmo número de registro, se sim lança exceção
		 */
		try {
			this.getByNumeroRegistro(veiculo.getNumeroRegistro());
			throw new VeiculoExistenteException("Veículo já cadastrado");
		} catch(NoResultException e) {
		}
		
		/**
		 * Verifica se a linha informada já existe, se não existir é realizado o cadastro
		 */
		Linha linha = null;
		try {
			linha = linhaBC.getByNumeroLinha(veiculo.getNumeroLinha());
		} catch (NoResultException e) {
			linha = new Linha();
			linha.setNumero(veiculo.getNumeroLinha());
			linha.setDescricao(veiculo.getNumeroLinha());
			linhaBC.insert(linha);
		}
		
		Veiculo v = new Veiculo();
		v.setLinha(linha);
		v.setNumeroRegistro(veiculo.getNumeroRegistro());
		
		this.insert(v);
	}

}
