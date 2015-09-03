package br.com.gsn.sysbusweb.rest;

import java.text.ParseException;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.gsn.sysbusweb.business.ReclamacaoBC;
import br.com.gsn.sysbusweb.domain.dto.ReclamacaoRankingDTO;
import br.com.gsn.sysbusweb.domain.dto.ReclamacaoRequestDTO;

@Path("reclamacao")
public class ReclamacaoResource {
	
	@Inject
	private ReclamacaoBC reclamacaoBC;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response insert(ReclamacaoRequestDTO reclamacaoParam) throws ParseException {
		
		reclamacaoBC.saveReclamacao(reclamacaoParam);
		
		return Response.status(Status.CREATED).entity("Reclamacao criada com sucesso").build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value = "/linhasreclamadastopdez")
	public Response listTopDezLinhasReclamadas() {
		List<ReclamacaoRankingDTO> list = reclamacaoBC.listTopDezLinhasReclamadas();
		
		if (list.isEmpty()) {
			return Response.status(Status.NOT_FOUND).entity(new ReclamacaoRankingDTO("Nenhuma reclamação registrada")).build();
		}
		
		return Response.ok().entity(list).build();
	}

}
