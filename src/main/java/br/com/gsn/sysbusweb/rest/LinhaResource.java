package br.com.gsn.sysbusweb.rest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import br.com.gsn.sysbusweb.business.LinhaBC;
import br.com.gsn.sysbusweb.business.VeiculoBC;
import br.com.gsn.sysbusweb.domain.Linha;
import br.com.gsn.sysbusweb.domain.Veiculo;
import br.com.gsn.sysbusweb.domain.dto.LinhaDTO;

@Path("linha")
public class LinhaResource {

	@Inject
	private LinhaBC bc;
	
	@Inject
	private VeiculoBC veiculoBC;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<LinhaDTO> list() {
		List<Linha> list = bc.findAll();
		
		List<LinhaDTO> listRetorno = new ArrayList<LinhaDTO>();
		for (Linha linha : list) {
			ModelMapper mapper = new ModelMapper();
			listRetorno.add(mapper.map(linha, LinhaDTO.class));
		}
		
		return listRetorno;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/veiculos/{numeroLinha}")
	public List<LinhaDTO> listByNumero(@PathParam("numeroLinha") String numeroLinha) {
		
		List<Veiculo> veiculos = veiculoBC.findByNumeroLinha(numeroLinha);
		
		List<LinhaDTO> listRetorno = new ArrayList<LinhaDTO>();
		for (Veiculo veiculo : veiculos) {
			ModelMapper mapper = new ModelMapper();
			mapper.addMappings(new PropertyMap<Veiculo, LinhaDTO>() {
				@Override
				protected void configure() {
					map().setId(source.getLinha().getId());
					map().setIdVeiculo(source.getId());
					map().setDescricao(source.getLinha().getDescricao());
					map().setNumeroLinha(source.getLinha().getNumero());
				}
			});
			listRetorno.add(mapper.map(veiculo, LinhaDTO.class));
		}
		
		return listRetorno;
	}
	
}
