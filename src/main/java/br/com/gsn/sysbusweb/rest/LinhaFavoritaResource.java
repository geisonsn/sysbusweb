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

import br.com.gsn.sysbusweb.business.LinhaFavoritaBC;
import br.com.gsn.sysbusweb.domain.dto.LinhaFavoritaDTO;
import br.com.gsn.sysbusweb.domain.dto.LocalizacaoLinhaDTO;

@Path("linhafavorita")
public class LinhaFavoritaResource {
	
	@Inject
	private LinhaFavoritaBC linhaFavoritaBC;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{idUsuario}")
	public Response findByIdUsuario(@PathParam("idUsuario") Long idUsuario) {
		List<LinhaFavoritaDTO> list = linhaFavoritaBC.listByUsuario(idUsuario);
		
		if (list.isEmpty()) {
			return Response.status(Status.NOT_FOUND).entity(new LocalizacaoLinhaDTO("Nenhum registro encontrado")).build();
		}
		
		return Response.status(Status.OK).entity(list).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response insert(LinhaFavoritaDTO linhaFavorita) throws ParseException {
		
		linhaFavorita = linhaFavoritaBC.insert(linhaFavorita);
		
		return Response.status(Status.CREATED).entity(linhaFavorita).build();
	}

}
