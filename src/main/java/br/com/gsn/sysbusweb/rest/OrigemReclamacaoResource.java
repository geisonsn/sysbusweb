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

import br.com.gsn.sysbusweb.business.OrigemReclamacaoBC;
import br.com.gsn.sysbusweb.domain.dto.OrigemReclamacaoDTO;

@Path("origemreclamacao")
public class OrigemReclamacaoResource {
	
	@Inject
	private OrigemReclamacaoBC origemReclamacaoBC;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{objetoReclamado}")
	public Response list(@PathParam("objetoReclamado") String objetoReclamado) {
		
		List<OrigemReclamacaoDTO> list = origemReclamacaoBC.findByObjetoReclamado(objetoReclamado);
		
		if (list.isEmpty()) {
			return Response.status(Status.NOT_FOUND).entity(new OrigemReclamacaoDTO("Nenhum registro encontrado")).build();
		}
		
		return Response.ok().entity(list).build();
	}
	

}
