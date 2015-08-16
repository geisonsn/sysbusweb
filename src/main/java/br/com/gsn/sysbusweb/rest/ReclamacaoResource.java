package br.com.gsn.sysbusweb.rest;

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

import org.modelmapper.ModelMapper;

import br.com.gsn.sysbusweb.business.ReclamacaoBC;
import br.com.gsn.sysbusweb.domain.Reclamacao;
import br.com.gsn.sysbusweb.domain.dto.ReclamacaoDTO;

@Path("reclamacao")
public class ReclamacaoResource {
	
	@Inject
	private ReclamacaoBC reclamacaoBC;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ReclamacaoDTO> findAll() {
		
		/*List<UsuarioDTO> listRetorno = new ArrayList<UsuarioDTO>();
		
		List<Usuario> list = reclamacaoBC.findAll();
		
		for (Usuario usuario : list) {
			ModelMapper mapper = new ModelMapper();
			UsuarioDTO map = mapper.map(usuario, UsuarioDTO.class);
			listRetorno.add(map);
		}*/
		
		return null;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response insert(ReclamacaoDTO reclamacaoParam) {
		
		ModelMapper mapper = new ModelMapper();
		
		Reclamacao reclamacao = mapper.map(reclamacaoParam, Reclamacao.class);
		
		reclamacaoBC.insert(reclamacao);
		
		return Response.status(Status.CREATED).entity("Reclamação criada com sucesso").build();
	}

}
