package br.com.gsn.sysbusweb.rest;

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

import org.modelmapper.ModelMapper;

import br.com.gsn.sysbusweb.business.UsuarioBC;
import br.com.gsn.sysbusweb.domain.Usuario;
import br.com.gsn.sysbusweb.domain.dto.UsuarioDTO;
import br.com.gsn.sysbusweb.domain.dto.UsuarioWrapperDTO;
import br.com.gsn.sysbusweb.exception.UsuarioExistenteException;

@Path("usuario")
public class UsuarioResource {
	
	@Inject
	private UsuarioBC usuarioBC;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{email}/{password}")
	public Response login(@PathParam("email") String email, @PathParam("password") String password) {
		
		UsuarioWrapperDTO usuarioWrapperDTO = usuarioBC.login(email, password);
		
		if (usuarioWrapperDTO.getUsuario() == null) {
			return Response.status(Status.NOT_FOUND).entity(new UsuarioWrapperDTO("Usuário não cadastrado")).build();
		}
		
		return Response.ok().entity(usuarioWrapperDTO).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response insert(UsuarioDTO usuarioParam) {
		
		ModelMapper mapper = new ModelMapper();
		
		Usuario usuario = mapper.map(usuarioParam, Usuario.class);
		
		try {
			usuario = usuarioBC.saveCliente(usuario);
			mapper.map(usuario, usuarioParam);
			return Response.status(Status.CREATED).entity(usuarioParam).build();
		} catch (UsuarioExistenteException e) {
			usuarioParam.setMessage(e.getMessage());
			return Response.status(Status.CONFLICT).entity(usuarioParam).build();
		}
	}

}
