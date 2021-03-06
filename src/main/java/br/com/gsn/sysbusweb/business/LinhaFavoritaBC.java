package br.com.gsn.sysbusweb.business;

import java.util.Collections;
import java.util.List;

import org.modelmapper.PropertyMap;

import br.com.gsn.sysbusweb.domain.Linha;
import br.com.gsn.sysbusweb.domain.LinhaFavorita;
import br.com.gsn.sysbusweb.domain.Usuario;
import br.com.gsn.sysbusweb.domain.dto.LinhaFavoritaDTO;
import br.com.gsn.sysbusweb.domain.dto.SincronizarFavoritoDTO;
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
	
	/**
	 * Sincroniza os favoritos do usuário
	 * @param usuarioWrapper
	 */
	public void sincronizarFavoritos(SincronizarFavoritoDTO sincronizarFavoritoDTO) {
		Long idUsuario = sincronizarFavoritoDTO.getIdUsuario();
		
		List<LinhaFavoritaDTO> favoritosTemporario = sincronizarFavoritoDTO.getLinhasFavoritas();
		
		if (favoritosTemporario.isEmpty()) {
			//Remove todos os favoritos
			getDelegate().remove(idUsuario);
		} else {
			List<LinhaFavorita> favoritosCadastrados = this.findByUsuario(idUsuario);
			
			if (favoritosCadastrados.isEmpty()) {
				//Incluir todos os favoritos
				for (LinhaFavoritaDTO linha : favoritosTemporario) {
					this.insert(linha);
				}
			} else {
				//Linhas a inserir
				for (LinhaFavoritaDTO linha : favoritosTemporario) {
					boolean contem = false;
					for (LinhaFavorita fc : favoritosCadastrados) {
						if (linha.getIdLinha().equals(fc.getLinha().getId())) {
							contem = true;
							break;
						}
					}
					if (!contem) {
						this.insert(linha);
					}
				}
				//Linhas a excluir
				for (LinhaFavorita fc : favoritosCadastrados) {
					boolean contem = false;
					for (LinhaFavoritaDTO linha : favoritosTemporario) {
						if (linha.getIdLinha().equals(fc.getLinha().getId())) {
							contem = true;
							break;
						}
					}
					if (!contem) {
						delete(fc.getId());
					}
				}
			}
		}
	}

}
