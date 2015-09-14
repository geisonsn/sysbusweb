package br.com.gsn.sysbusweb.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.gsn.sysbusweb.business.VeiculoBC;
import br.com.gsn.sysbusweb.business.VeiculoLinhaBC;
import br.com.gsn.sysbusweb.domain.dto.VeiculoDTO;
import br.com.gsn.sysbusweb.exception.VeiculoExistenteException;

@Path("veiculo")
public class VeiculoResource {

	@Inject
	private VeiculoBC veiculoBC;
	
	@Inject
	private VeiculoLinhaBC veiculoLinhaBC;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{numeroLinha}")
	public Response listByNumero(@PathParam("numeroLinha") String numeroLinha) {
		
		List<VeiculoDTO> veiculos = veiculoLinhaBC.findByNumeroLinha(numeroLinha);
		
		if (veiculos.isEmpty()) {
			return Response.status(Status.NOT_FOUND).entity(new VeiculoDTO("Nenhum veículo para a linha informada")).build();
		}
		
		return Response.ok(veiculos).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response insert(VeiculoDTO veiculo) {
		
		try {
			veiculoBC.saveVeiculo(veiculo);
		} catch (VeiculoExistenteException e) {
			return Response.status(Status.CONFLICT).entity(e.getMessage()).build();
		}
		
		return Response.status(Status.CREATED).entity(new VeiculoDTO("Cadastro realizado com sucesso")).build();
	}
	
}
