package br.com.gsn.sysbusweb.business;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.PropertyMap;

import br.com.gsn.sysbusweb.domain.Linha;
import br.com.gsn.sysbusweb.domain.Veiculo;
import br.com.gsn.sysbusweb.domain.VeiculoLinha;
import br.com.gsn.sysbusweb.domain.dto.VeiculoDTO;
import br.com.gsn.sysbusweb.exception.VeiculoExistenteException;
import br.com.gsn.sysbusweb.persistence.VeiculoLinhaDAO;
import br.com.gsn.sysbusweb.util.ModelMapperUtil;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;

@BusinessController
public class VeiculoLinhaBC extends DelegateCrud<VeiculoLinha, Long, VeiculoLinhaDAO> {
	private static final long serialVersionUID = 1L;

	@Inject
	private VeiculoBC veiculoBC;
	
	@Inject
	private LinhaBC linhaBC;
	
	public List<VeiculoLinha> listByNumeroLinhaByNumeroRegistroExcludente(Long id, String numeroLinha, String numeroRegistro, String placa) {
		return getDelegate().listByNumeroLinhaByNumeroRegistroExcludente(id, numeroLinha, numeroRegistro, placa);
	}
	
	public List<VeiculoDTO> findByNumeroLinha(String numeroLinha) {

		List<VeiculoLinha> veiculos = getDelegate().findByNumeroLinha(numeroLinha);
		
		if (veiculos.isEmpty()) {
			return Collections.emptyList();
		}
		
		return ModelMapperUtil.map(veiculos, VeiculoDTO.class, new PropertyMap<VeiculoLinha, VeiculoDTO>() {
			@Override
			protected void configure() {
				map().setIdVeiculo(source.getId());				
				map().setNumeroRegistro(source.getVeiculo().getNumeroRegistro());				
				map().setIdLinha(source.getLinha().getId());				
				map().setDescricaoLinha(source.getLinha().getDescricao());				
				map().setNumeroLinha(source.getLinha().getNumero());				
			}
		});
	}
	
	public void saveVeiculo(VeiculoDTO veiculoRequest) throws VeiculoExistenteException {
		
		VeiculoLinha veiculoLinha = this
			.getByNumeroLinhaByNumeroRegistro(veiculoRequest.getNumeroLinha(), veiculoRequest.getNumeroRegistro());
		
		if (veiculoLinha != null) {
			throw new VeiculoExistenteException("Veículo já cadastrado");
		}
		
		/**
		 * Verifica se a linha informada já existe, se não existir é realizado o cadastro
		 */
		Linha linha = linhaBC.getByNumeroLinha(veiculoRequest.getNumeroLinha());
		if (linha == null) {
			linha = new Linha();
			linha.setNumero(veiculoRequest.getNumeroLinha());
			linha.setDescricao(veiculoRequest.getNumeroLinha());
			linhaBC.insert(linha);
		}
		
		/**
		 * Verifica se o veículo já existe, caso contrário o insere na base
		 */
		Veiculo veiculo = veiculoBC.getByNumeroRegistro(veiculoRequest.getNumeroRegistro());
		if (veiculo == null) {
			veiculo = new Veiculo();
			veiculo.setNumeroRegistro(veiculoRequest.getNumeroRegistro());
			veiculoBC.insert(veiculo);
		}
		
		/**
		 * Insere o veículo linha
		 */
		veiculoLinha = new VeiculoLinha();
		veiculoLinha.setLinha(linha);
		veiculoLinha.setVeiculo(veiculo);
		this.insert(veiculoLinha);
	}
	
	@Deprecated
	public void save(VeiculoLinha veiculoLinha) {
		Veiculo veiculo = veiculoBC.saveVeiculo(veiculoLinha.getVeiculo());
		veiculoLinha.setVeiculo(veiculo);
		this.insert(veiculoLinha);
	}
	
	@Deprecated
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
		List<Veiculo> veiculoPesquisados = veiculoBC.findByNumeroRegistroOuPlaca(veiculoLinha.getVeiculo().getNumeroRegistro(), veiculoLinha.getVeiculo().getPlaca());
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

	@Deprecated
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
		VeiculoLinha veiculoLinha = null;
		try {
			veiculoLinha = getDelegate().getByNumeroLinhaByNumeroRegistro(numeroLinha, numeroRegistro);
		} catch (NoResultException e) {
		}
		return veiculoLinha;
	}

	public void salvarVeiculos(Linha linha, List<Veiculo> veiculosSelecionados) {
		
		List<Veiculo> reclamacoesCadastradas = veiculoBC
			.listVeiculosCadastradosParaLinha(linha);
		
		List<Veiculo> novas = this.recuperarVeiculosIncluidas(reclamacoesCadastradas, 
				veiculosSelecionados);
		
		this.salvarReclamacao(linha, novas);
		
		List<Long> removidas = this.recuperarVeiculosRemovidos(reclamacoesCadastradas, 
				veiculosSelecionados);
		
		this.removerVeiculos(linha, removidas);
		
	}
	
	private List<Veiculo> recuperarVeiculosIncluidas(
			List<Veiculo> veiculosCadastrados,
			List<Veiculo> veiculosSelecionados) {
		
		//Se ainda não existem reclamações cadastradas todas as selecionadas são elegíveis para a inserção
		if (veiculosCadastrados.isEmpty()) {
			return veiculosSelecionados;
		}
		
		List<Veiculo> list = new ArrayList<Veiculo>();
		
		for (Veiculo selecionada : veiculosSelecionados) {
			boolean isNova = true;
			for (Veiculo cadastrada : veiculosCadastrados) {
				if (selecionada.equals(cadastrada)) {
					isNova = false;
					break;
				}
			}
			if (isNova) {
				list.add(selecionada);
			}
		}
		
		return list;
	}

	private List<Long> recuperarVeiculosRemovidos(
			List<Veiculo> veiculosCadastrados,
			List<Veiculo> veiculosSelecionados) {
		
		//Se ainda não existe veiculos cadastrados nenhuma remoção ocorrerá
		if (veiculosCadastrados.isEmpty()) {
			return Arrays.asList(ArrayUtils.EMPTY_LONG_OBJECT_ARRAY);
		}
		
		List<Long> list = new ArrayList<Long>();
		
		for (Veiculo cadastrado : veiculosCadastrados) {
			boolean isRemovida = true;
			for (Veiculo selecionado : veiculosSelecionados) {
				if (cadastrado.equals(selecionado)) {
					isRemovida = false;
					break;
				}
			}
			if (isRemovida) {
				list.add(cadastrado.getId());
			}
		}
		
		return list;
	}
	
	private void salvarReclamacao(Linha linha,
			List<Veiculo> novas) {
		
		for (Veiculo veiculo : novas) {
			VeiculoLinha veiculoLinha = new VeiculoLinha();
			veiculoLinha.setVeiculo(veiculo);
			veiculoLinha.setLinha(linha);
			this.insert(veiculoLinha);
		}
	}
	
	private void removerVeiculos(Linha linha,
			List<Long> removidos) {
		
		if (!removidos.isEmpty()) {
			getDelegate().removeByLinha(linha, removidos);
		}
	}
}
