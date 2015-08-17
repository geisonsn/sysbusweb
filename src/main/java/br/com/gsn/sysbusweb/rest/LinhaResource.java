package br.com.gsn.sysbusweb.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.gsn.sysbusweb.business.LinhaBC;
import br.com.gsn.sysbusweb.domain.dto.LinhaDTO;

@Path("linha")
public class LinhaResource {

	@Inject
	private LinhaBC linhaBC;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listAll() {
		
		List<LinhaDTO> linhas = linhaBC.listAll();
		
		if (linhas.isEmpty()) {
			Response.status(Status.NOT_FOUND).entity(new LinhaDTO("Nenhuma linha encontrada"));
		}
		
		return Response.ok().entity(linhas).build();
	}
	
}
