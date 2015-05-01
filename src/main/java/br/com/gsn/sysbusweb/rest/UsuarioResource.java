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
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/{username}/{password}")
	public Response login(@PathParam("username") String username, @PathParam("password") String password) {
		try {
			usuarioBC.getByUsernameEPassoword(username, password);
			return Response.status(Status.OK).entity("OK").build();
		} catch (NoResultException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response insert(UsuarioDTO usuarioParam) {
		
		try {
			usuarioBC.getByUsernameEEmail(usuarioParam.getUsername(), usuarioParam.getEmail());
			String mensagem = "Usuário já cadastrado";
			
			return Response.status(Status.CONFLICT).entity(mensagem).build();
		} catch (NoResultException e) {
		}
		
		ModelMapper mapper = new ModelMapper();
		
		Usuario usuario = mapper.map(usuarioParam, Usuario.class);
		
		Long id = usuarioBC.saveCliente(usuario).getId();
		
		return Response.status(Status.CREATED).entity(id).build();
	}

}
