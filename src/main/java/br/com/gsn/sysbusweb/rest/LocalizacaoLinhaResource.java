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

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import br.com.gsn.sysbusweb.business.LocalizacaoLinhaBC;
import br.com.gsn.sysbusweb.domain.LocalizacaoLinha;
import br.com.gsn.sysbusweb.domain.Veiculo;
import br.com.gsn.sysbusweb.domain.dto.LocalizacaoLinhaDTO;
import br.com.gsn.sysbusweb.domain.dto.LocalizacaoLinhaWrapperDTO;
import br.com.gsn.sysbusweb.util.Dates;

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
	@Path("/veiculosemdeslocamento/{idUsuario}/{intervalo}")
	public Response listVeiculosEmDeslocamento(@PathParam("idUsuario") Long idUsuario, @PathParam("intervalo") Integer intervalo) {
		
		LocalizacaoLinhaWrapperDTO wrapper = localizacaoLinhaBC.listVeiculosEmDeslocamento(idUsuario, intervalo);
		
		if (isEmpty(wrapper)) {
			return Response.status(Status.NOT_FOUND).entity(new LocalizacaoLinhaWrapperDTO("Nenhum registro encontrado")).build();
		}
		
		return Response.status(Status.OK).entity(wrapper).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/veiculosemdeslocamentoproximos/{idUsuario}/{intervalo}/{distancia}/{latitude}/{longitude}")
	public Response listVeiculosEmDeslocamento(@PathParam("idUsuario") Long idUsuario, @PathParam("intervalo") Integer intervalo,
			@PathParam("distancia") Integer distancia, @PathParam("latitude") String latitude, @PathParam("longitude") String longitude) {
		
		LocalizacaoLinhaWrapperDTO wrapper = localizacaoLinhaBC.listVeiculosEmDeslocamentoProximos(idUsuario, intervalo, distancia, 
				latitude, longitude);
		
		if (isEmpty(wrapper)) {
			return Response.status(Status.NOT_FOUND).entity(new LocalizacaoLinhaDTO("Nenhum registro encontrado")).build();
		}
		
		return Response.status(Status.OK).entity(wrapper).build();
	}
	
	public Boolean isEmpty(LocalizacaoLinhaWrapperDTO wrapper) {
		return wrapper.getLinhasFavoritas().isEmpty() && wrapper.getLinhasNaoFavoritas().isEmpty();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response insert(LocalizacaoLinhaDTO localizacaoParam) throws ParseException {
		
		ModelMapper mapper = new ModelMapper();
		mapper.addMappings(new PropertyMap<LocalizacaoLinhaDTO, LocalizacaoLinha>() {
			@Override
			protected void configure() {
				map().setId(null);
				map().setVeiculo(new Veiculo());
				map().getVeiculo().setId(source.getIdVeiculo());
				map().setDataHoraRegistro(null);
			}
		});
		
		LocalizacaoLinha localizacao = mapper.map(localizacaoParam, LocalizacaoLinha.class);
		localizacao.setDataHoraRegistro(Dates.parse(localizacaoParam.getDataHoraRegistro(), Dates.FORMAT_PT_BR_DATE_HOUR));
		localizacao = localizacaoLinhaBC.insert(localizacao);
		
		localizacaoParam = new LocalizacaoLinhaDTO("Localizacao cadastrada com sucesso");
		
		return Response.status(Status.CREATED).entity(localizacaoParam).build();
	}

}
