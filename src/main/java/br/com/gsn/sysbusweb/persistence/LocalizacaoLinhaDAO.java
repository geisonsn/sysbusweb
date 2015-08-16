package br.com.gsn.sysbusweb.persistence;

import java.util.List;

import br.com.gsn.sysbusweb.domain.LocalizacaoLinha;
import br.com.gsn.sysbusweb.domain.dto.LocalizacaoLinhaDTO;
import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;

@PersistenceController
public class LocalizacaoLinhaDAO extends JPACrud<LocalizacaoLinha, Long> {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Lista a última localização dos veículos de uma determinada linha
	 * @param numeroLinha pesquisa
	 * @return localização dos veículos
	 */
	@SuppressWarnings("unchecked")
	public List<LocalizacaoLinhaDTO> listUltimaLocalizacaoVeiculoDaLinha(String numeroLinha) {
		
		StringBuffer jpql = new StringBuffer();
		
		jpql.append(" select new br.com.gsn.sysbusweb.domain.dto.LocalizacaoLinhaDTO( ")
			.append(" li.numero, ve.numeroRegistro, ll.latitude, ll.longitude, ")
			.append(" max(ll.dataHoraRegistro) as dataHoraRegistro) ")
			.append(" from LocalizacaoLinha as ll ") 
			.append(" inner join ll.veiculo as ve ")
			.append(" inner join ve.linha as li ")
			.append(" where li.numero = :numeroLinha ")
			.append(" group by ve.numeroRegistro ")
			.append(" order by ll.dataHoraRegistro asc, ve.numeroRegistro ");
		
		return (List<LocalizacaoLinhaDTO>) getEntityManager().createQuery(jpql.toString())
			.setParameter("numeroLinha", numeroLinha)
			.getResultList();
	}

}
