package br.com.gsn.sysbusweb.rest;

import java.text.ParseException;
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

import br.com.gsn.sysbusweb.business.ReclamacaoBC;
import br.com.gsn.sysbusweb.domain.dto.ReclamacaoPorLinhaDTO;
import br.com.gsn.sysbusweb.domain.dto.ReclamacaoRankingDTO;
import br.com.gsn.sysbusweb.domain.dto.ReclamacaoRequestDTO;

@Path("reclamacao")
public class ReclamacaoResource {
	
	@Inject
	private ReclamacaoBC reclamacaoBC;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response insert(ReclamacaoRequestDTO reclamacaoParam) throws ParseException {
		
		reclamacaoBC.saveReclamacao(reclamacaoParam);
		
		return Response.status(Status.CREATED).entity(new ReclamacaoRequestDTO("Reclamacao criada com sucesso")).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value = "/linhasmaisreclamadas/{quantidade}")
	public Response listLinhasMaisReclamadas(@PathParam("quantidade") Integer quantidade) {
		List<ReclamacaoRankingDTO> list = reclamacaoBC.listLinhasMaisReclamadas(quantidade);
		
		if (list.isEmpty()) {
			return Response.status(Status.NOT_FOUND).entity(new ReclamacaoRankingDTO("Nenhuma reclamação registrada")).build();
		}
		
		return Response.ok().entity(list).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(value = "/principaisreclamacoesporlinha/{idLinha}/{quantidade}")
	public Response listPrincipaisReclamacoesPorLinha(@PathParam("idLinha") Long idLinha, @PathParam("quantidade") Integer quantidade) {
		
		List<ReclamacaoPorLinhaDTO> list = reclamacaoBC.listPrincipaisReclamacoesPorLinha(idLinha, quantidade);
		
		if (list.isEmpty()) {
			return Response.status(Status.NOT_FOUND).entity(new ReclamacaoPorLinhaDTO("Nenhuma reclamação registrada")).build();
		}
		
		return Response.ok().entity(list).build();
	}
}
