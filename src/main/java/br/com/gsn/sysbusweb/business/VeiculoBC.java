package br.com.gsn.sysbusweb.business;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.apache.commons.lang.StringUtils;

import br.com.gsn.sysbusweb.domain.Linha;
import br.com.gsn.sysbusweb.domain.Veiculo;
import br.com.gsn.sysbusweb.domain.dto.VeiculoDTO;
import br.com.gsn.sysbusweb.exception.VeiculoExistenteException;
import br.com.gsn.sysbusweb.persistence.VeiculoDAO;
import br.com.gsn.sysbusweb.util.Util;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;

@BusinessController
public class VeiculoBC extends DelegateCrud<Veiculo, Long, VeiculoDAO> {
	private static final long serialVersionUID = 1L;

	@Inject
	private LinhaBC linhaBC;
	
	public List<Veiculo> findAll() {
		return getDelegate().findAll();
	}
	
	public Veiculo getByNumeroRegistro(String numeroRegistro) {
		try {
			return getDelegate().getByNumeroRegistro(numeroRegistro);
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public Veiculo getByPlaca(String placa) {
		try {
			return getDelegate().getByPlaca(placa);
		} catch (NoResultException e) {
			return null;
		}
	}
	
	
	public List<Veiculo> findByNumeroRegistroOuPlaca(String numeroRegistro, String placa) {
		return getDelegate().findByNumeroRegistroOuPlaca(numeroRegistro, placa);
	}
	
	@Deprecated
	public Veiculo saveVeiculo(Veiculo veiculo) throws VeiculoExistenteException {
		
		veiculo.setPlaca(Util.capitalize(veiculo.getPlaca()));//Caso haja valor, ajusta a placa
		
		Veiculo veiculoPesquisadoPorRegistro = this.getByNumeroRegistro(veiculo.getNumeroRegistro());
		
		this.validarPlacaExistente(veiculo.getPlaca(), veiculoPesquisadoPorRegistro); //Lança exceção caso a placa já tenha sido cadastrada para outro veículo
		
		if (veiculoPesquisadoPorRegistro == null) {
			veiculo = this.insert(veiculo);
		} else {
			veiculo.setId(veiculoPesquisadoPorRegistro.getId());
		}
		
		return veiculo;
	}

	@Deprecated
	private void validarPlacaExistente(String placa, Veiculo veiculoPesquisadoPorRegistro) {
		Veiculo veiculoPesquisadoPorPlaca = null;
		
		if (StringUtils.isNotEmpty(placa)) {
			veiculoPesquisadoPorPlaca = this.getByPlaca(placa);
		}
		
		if (veiculoPesquisadoPorPlaca != null && !veiculoPesquisadoPorPlaca.equals(veiculoPesquisadoPorRegistro)) {
			throw new VeiculoExistenteException("Já existe um veículo cadastrado com a placa informada");
		}
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
//		v.setLinha(linha);
		v.setNumeroRegistro(veiculo.getNumeroRegistro());
		
		this.insert(v);
	}

	public List<Veiculo> listVeiculosNaoCadastradosParaLinha(Linha linha) {
		return getDelegate().listVeiculosNaoCadastradosParaLinha(linha);
	}

	public List<Veiculo> listVeiculosCadastradosParaLinha(Linha linha) {
		return getDelegate().listVeiculosCadastradosParaLinha(linha);
	}

}
