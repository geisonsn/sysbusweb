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

import br.com.gsn.sysbusweb.business.LocalizacaoLinhaBC;
import br.com.gsn.sysbusweb.domain.dto.LocalizacaoLinhaDTO;
import br.com.gsn.sysbusweb.domain.dto.LocalizacaoLinhaInsertDTO;

@Path("localizacaolinha")
public class LocalizacaoLinhaResource {
	
	@Inject
	private LocalizacaoLinhaBC localizacaoLinhaBC;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{numeroLinha}")
	public Response findByNumeroLinha(@PathParam("numeroLinha") String numeroLinha) {
		List<LocalizacaoLinhaDTO> list = localizacaoLinhaBC.listUltimaLocalizacaoVeiculoDaLinha(numeroLinha);
		
		if (list.isEmpty()) {
			return Response.status(Status.NOT_FOUND).entity(new LocalizacaoLinhaDTO("Nenhum registro encontrado")).build();
		}
		
		return Response.status(Status.OK).entity(list).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/veiculosemdeslocamento/{intervalo}")
	public Response listVeiculosEmDeslocamento(@PathParam("intervalo") Integer intervalo) {
		
		List<LocalizacaoLinhaDTO> list = localizacaoLinhaBC.listVeiculosEmDeslocamento(intervalo);
		
		if (list.isEmpty()) {
			return Response.status(Status.NOT_FOUND).entity(new LocalizacaoLinhaDTO("Nenhum registro encontrado")).build();
		}
		
		return Response.status(Status.OK).entity(list).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/veiculosemdeslocamentoproximos/{intervalo}/{distancia}/{latitude}/{longitude}")
	public Response listVeiculosEmDeslocamento(@PathParam("intervalo") Integer intervalo,
			@PathParam("distancia") Integer distancia, @PathParam("latitude") String latitude, @PathParam("longitude") String longitude) {
		
		List<LocalizacaoLinhaDTO> list = localizacaoLinhaBC.listVeiculosEmDeslocamentoProximos(intervalo, distancia, 
				latitude, longitude);
		
		if (list.isEmpty()) {
			return Response.status(Status.NOT_FOUND).entity(new LocalizacaoLinhaDTO("Nenhum registro encontrado")).build();
		}
		
		return Response.status(Status.OK).entity(list).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response insert(LocalizacaoLinhaInsertDTO localizacaoParam) throws ParseException {
		localizacaoLinhaBC.insert(localizacaoParam);
		localizacaoParam = new LocalizacaoLinhaInsertDTO("Localizacao cadastrada com sucesso");
		return Response.status(Status.CREATED).entity(localizacaoParam).build();
	}

}
