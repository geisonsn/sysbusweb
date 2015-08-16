package br.com.gsn.sysbusweb.rest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.NoResultException;
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
import br.com.gsn.sysbusweb.exception.ClienteExistenteException;

@Path("usuario")
public class UsuarioResource {
	
	@Inject
	private UsuarioBC usuarioBC;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<UsuarioDTO> findAll() {
		
		List<UsuarioDTO> listRetorno = new ArrayList<UsuarioDTO>();
		
		List<Usuario> list = usuarioBC.findAll();
		
		for (Usuario usuario : list) {
			ModelMapper mapper = new ModelMapper();
			UsuarioDTO map = mapper.map(usuario, UsuarioDTO.class);
			listRetorno.add(map);
		}
		
		return listRetorno;
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{email}/{password}")
	public Response login(@PathParam("email") String email, @PathParam("password") String password) {
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		try {
			
			Usuario usuario = usuarioBC.getClienteByEmailEPassword(email, password);
			
			ModelMapper mapper = new ModelMapper();
			mapper.map(usuario, usuarioDTO);
			
			return Response.status(Status.OK).entity(usuarioDTO).build();
		} catch (NoResultException e) {
			usuarioDTO.setMessage("Usuário não cadastrado");
			return Response.status(Status.NOT_FOUND).entity(usuarioDTO).build();
		}
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
		} catch (ClienteExistenteException e) {
			usuarioParam.setMessage(e.getMessage());
			return Response.status(Status.CONFLICT).entity(usuarioParam).build();
		}
	}

}
