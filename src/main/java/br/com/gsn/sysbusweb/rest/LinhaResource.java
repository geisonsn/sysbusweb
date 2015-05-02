package br.com.gsn.sysbusweb.rest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.modelmapper.ModelMapper;

import br.com.gsn.sysbusweb.business.LinhaBC;
import br.com.gsn.sysbusweb.domain.Linha;
import br.com.gsn.sysbusweb.domain.dto.LinhaDTO;

@Path("linha")
public class LinhaResource {

	@Inject
	private LinhaBC bc;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<LinhaDTO> list() {
		List<Linha> list = bc.findAll();
		
		List<LinhaDTO> listRetorno = new ArrayList<>();
		for (Linha linha : list) {
			ModelMapper mapper = new ModelMapper();
			listRetorno.add(mapper.map(linha, LinhaDTO.class));
		}
		
		return listRetorno;
	}
	
}
