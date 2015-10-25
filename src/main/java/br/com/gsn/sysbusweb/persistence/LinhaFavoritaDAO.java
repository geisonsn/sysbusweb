package br.com.gsn.sysbusweb.persistence;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import br.com.gsn.sysbusweb.domain.LinhaFavorita;
import br.com.gsn.sysbusweb.domain.dto.LinhaFavoritaDTO;
import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;

@PersistenceController
public class LinhaFavoritaDAO extends JPACrud<LinhaFavorita, Long> {

	private static final long serialVersionUID = 1L;
	
	public List<LinhaFavorita> findByUsuario(Long idUsuario) {
		
		return getEntityManager().createNamedQuery(LinhaFavorita.FIND_BY_USUARIO, LinhaFavorita.class)
			.setParameter("idUsuario", idUsuario)
			.getResultList();
	}
	
	/**
	 * Conta a quantidade de veículos em deslocamento para cada uma das linhas informadas no parâmetro
	 * @param intervalo entre a último registro e a data corrente
	 * @param linhas
	 * @return as linhas com a quantidadd de veículos em circulação
	 */
	@SuppressWarnings("unchecked")
	public List<LinhaFavoritaDTO> contarVeiculosEmDeslocamento(Integer intervalo, LinhaFavoritaDTO[] linhas) {
		
		StringBuffer sql = new StringBuffer();
		
		for (int i = 0; i < linhas.length; i++) {
			LinhaFavoritaDTO linha = linhas[i];
			sql
				.append(" select count(numero) as total, '" + linha.getNumeroLinha() + "' as numero from ( ")
				.append(" 	select max(ll.data_hora_registro) ultimo_registro, numero ")
				.append(" 	from localizacao_linha ll ")
				.append(" 	inner join veiculo_linha vl on vl.id = ll.id_veiculo_linha ")
				.append(" 	inner join linha l on l.id = vl.id_linha ")
				.append(" 	where l.numero = '" + linha.getNumeroLinha() + " '")
				.append(" 	and ll.data_hora_registro >= date_sub(sysdate(), interval " + intervalo + " hour) ")
				.append(" 	group by vl.id, l.numero) temp ");
			
			if ((i+1) < linhas.length) {
				sql.append(" union all");
			}
		}
		
		List<Object[]> resultList = getEntityManager()
			.createNativeQuery(sql.toString()).getResultList();
		
		List<LinhaFavoritaDTO> list = new ArrayList<LinhaFavoritaDTO>();
		for (Object object : resultList) {
			LinhaFavoritaDTO l = new LinhaFavoritaDTO();
			l.setTotalVeiculosEmDeslocamento(((BigInteger)((Object[])object)[0]).intValue());
			l.setNumeroLinha(((String)((Object[])object)[1]));
			list.add(l);
		}
		
		return list;
	}
	
	/**
	 * Remove todas as linhas para o usuário
	 * @param idUsuario
	 */
	public void remove(Long idUsuario) {
		getEntityManager().createQuery(" delete from LinhaFavorita l where l.usuario.id = :idUsuario ")
		.setParameter("idUsuario", idUsuario)
		.executeUpdate();
	}

}
