package br.com.gsn.sysbusweb.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.gsn.sysbusweb.business.VeiculoBC;
import br.com.gsn.sysbusweb.domain.dto.VeiculoDTO;

@Path("veiculo")
public class VeiculoResource {

	@Inject
	private VeiculoBC veiculoBC;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{numeroLinha}")
	public Response listByNumero(@PathParam("numeroLinha") String numeroLinha) {
		
		List<VeiculoDTO> veiculos = veiculoBC.findByNumeroLinha(numeroLinha);
		
		if (veiculos.isEmpty()) {
			return Response.status(Status.NOT_FOUND).entity(new VeiculoDTO("Nenhum veículo para a linha")).build();
		}
		
		return Response.ok(veiculos).build();
	}
	
}
