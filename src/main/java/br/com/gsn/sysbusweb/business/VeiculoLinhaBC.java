package br.com.gsn.sysbusweb.business;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;

import br.com.gsn.sysbusweb.domain.Veiculo;
import br.com.gsn.sysbusweb.domain.VeiculoLinha;
import br.com.gsn.sysbusweb.exception.VeiculoExistenteException;
import br.com.gsn.sysbusweb.persistence.VeiculoLinhaDAO;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;

@BusinessController
public class VeiculoLinhaBC extends DelegateCrud<VeiculoLinha, Long, VeiculoLinhaDAO> {
	private static final long serialVersionUID = 1L;

	@Inject
	private VeiculoBC veiculoBC;
	
	public List<VeiculoLinha> listByNumeroLinhaByNumeroRegistroExcludente(Long id, String numeroLinha, String numeroRegistro, String placa) {
		return getDelegate().listByNumeroLinhaByNumeroRegistroExcludente(id, numeroLinha, numeroRegistro, placa);
	}
	
	public void save(VeiculoLinha veiculoLinha) {
		Veiculo veiculo = veiculoBC.saveVeiculo(veiculoLinha.getVeiculo());
		veiculoLinha.setVeiculo(veiculo);
		this.insert(veiculoLinha);
	}
	
	public VeiculoLinha update(VeiculoLinha veiculoLinha) {
		
		validarIgualdadeVeiculos(veiculoLinha); //Se veículo do número de registro diferente do veículo do número de placa lança exceção
		
		List<VeiculoLinha> veiculosEncontrados = this
			.listByNumeroLinhaByNumeroRegistroExcludente(
				veiculoLinha.getId(), 
				veiculoLinha.getLinha().getNumero(), 
				veiculoLinha.getVeiculo().getNumeroRegistro(), 
				veiculoLinha.getVeiculo().getPlaca());
		
		if (!veiculosEncontrados.isEmpty()) {
			throw new VeiculoExistenteException("Já existe um registro cadastrado com os dados informados");
		}
		
//		Veiculo byNumeroRegistro = veiculoBC.getByNumeroRegistro(veiculoLinha.getVeiculo().getNumeroRegistro());
		List<Veiculo> veiculoPesquisados = veiculoBC.getByNumeroRegistroOuPlaca(veiculoLinha.getVeiculo().getNumeroRegistro(), veiculoLinha.getVeiculo().getPlaca());
		if (veiculoPesquisados.isEmpty()) {
			veiculoBC.update(veiculoLinha.getVeiculo());
			System.out.println("atualizando dados do veiculo");
		} else {
			//TODO verificar este trecho
			Veiculo veiculo = veiculoPesquisados.get(0);
			veiculo.setNumeroRegistro(veiculoLinha.getVeiculo().getNumeroRegistro());
			veiculo.setPlaca(veiculoLinha.getVeiculo().getPlaca());
			veiculoBC.update(veiculoLinha.getVeiculo());
			veiculoLinha.setVeiculo(veiculo);
		}
		
		//busca
		
//		Veiculo veiculo = veiculoBC.updateVeiculo(veiculoLinha);
//		veiculoLinha.setVeiculo(veiculo);
		return this.update(veiculoLinha);
//		return veiculoLinha;
	}

	private void validarIgualdadeVeiculos(VeiculoLinha veiculoLinha) {
		Veiculo pesquisadoPorPlaca = null;
		
		if (StringUtils.isNotEmpty(veiculoLinha.getVeiculo().getPlaca())) {
			pesquisadoPorPlaca = veiculoBC.getByPlaca(veiculoLinha.getVeiculo().getPlaca());
		}
		
		if (pesquisadoPorPlaca != null) {
			Veiculo pesquisadoPorNumeroRegistro = veiculoBC.getByNumeroRegistro(veiculoLinha.getVeiculo().getNumeroRegistro());
			if (pesquisadoPorNumeroRegistro != null && !pesquisadoPorPlaca.equals(pesquisadoPorNumeroRegistro)) {
				throw new VeiculoExistenteException("A placa que você está tentando cadastrar não pode ser associado a este veículo, pois pertence ao veículo de registro: "+ pesquisadoPorPlaca.getNumeroRegistro());
			}
		}
	}
	
	public List<VeiculoLinha> findByLinhaByNumero(String linha, String numeroRegistro) {
		return getDelegate().findByLinhaByNumero(linha, numeroRegistro);
	}
	
	public VeiculoLinha getByNumeroLinhaByNumeroRegistro(String numeroLinha, String numeroRegistro) {
		return getDelegate().getByNumeroLinhaByNumeroRegistro(numeroLinha, numeroRegistro);
	}
}
