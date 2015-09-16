package br.com.gsn.sysbusweb.business;

import java.util.Collections;
import java.util.List;

import org.modelmapper.PropertyMap;

import br.com.gsn.sysbusweb.domain.Linha;
import br.com.gsn.sysbusweb.domain.LinhaFavorita;
import br.com.gsn.sysbusweb.domain.Usuario;
import br.com.gsn.sysbusweb.domain.dto.LinhaFavoritaDTO;
import br.com.gsn.sysbusweb.persistence.LinhaFavoritaDAO;
import br.com.gsn.sysbusweb.util.ModelMapperUtil;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;

@BusinessController
public class LinhaFavoritaBC extends DelegateCrud<LinhaFavorita, Long, LinhaFavoritaDAO> {
	
	private static final long serialVersionUID = 1L;
	
	public List<LinhaFavorita> findByUsuario(Long idUsuario) {
		return getDelegate().findByUsuario(idUsuario);
	}
	
	public List<LinhaFavoritaDTO> listByUsuario(Long idUsuario) {
		List<LinhaFavorita> list = this.findByUsuario(idUsuario);
		
		if (list.isEmpty()) {
			return Collections.emptyList();
		}
		
		return ModelMapperUtil.map(list, LinhaFavoritaDTO.class, new PropertyMap<LinhaFavorita, LinhaFavoritaDTO>() {
			@Override
			protected void configure() {
				map().setIdLinhaFavorita(source.getId());
				map().setIdLinha(source.getLinha().getId());
				map().setEmpresaLinha(source.getLinha().getEmpresa().getNome());
				map().setDescricaoLinha(source.getLinha().getDescricao());
			}
		});
	}
	
	public LinhaFavoritaDTO insert(LinhaFavoritaDTO linhaFavoritaDTO) {
		LinhaFavorita linhaFavorita = new LinhaFavorita();
		linhaFavorita.setUsuario(new Usuario());
		linhaFavorita.getUsuario().setId(linhaFavoritaDTO.getIdUsuario());
		linhaFavorita.setLinha(new Linha());
		linhaFavorita.getLinha().setId(linhaFavoritaDTO.getIdLinha());
		this.insert(linhaFavorita);
		return linhaFavoritaDTO;
	}
	
	public List<LinhaFavoritaDTO> findFavoritosComLocalizacao(Integer intervalo, LinhaFavoritaDTO[] linhasFavoritas) {
		return getDelegate().contarVeiculosEmDeslocamento(intervalo, linhasFavoritas);
	}

}
