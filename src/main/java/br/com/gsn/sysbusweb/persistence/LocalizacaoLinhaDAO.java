package br.com.gsn.sysbusweb.persistence;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import br.com.gsn.sysbusweb.domain.LocalizacaoLinha;
import br.com.gsn.sysbusweb.domain.dto.LocalizacaoLinhaDTO;
import br.com.gsn.sysbusweb.util.Dates;
import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;

@PersistenceController
public class LocalizacaoLinhaDAO extends JPACrud<LocalizacaoLinha, Long> {

	private static final long serialVersionUID = 1L;
	
	private final Integer RAIO_TERRESTRE = 6371;
	
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
	
	@SuppressWarnings("unchecked")
	public List<LocalizacaoLinhaDTO> listVeiculosEmDeslocamento(Integer intervalo) {
		
		StringBuffer sql = new StringBuffer();
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		sql
		.append(" select * from ( ")
		.append("	select ") 
		.append("		l.id id_linha, ") 
		.append("		l.numero linha, ") 
		.append("		l.descricao descricao_linha, ") 
		.append("		e.nome nome_empresa, ")  
		.append("		v.numero_registro veiculo, ")    
		.append("		max(ll.data_hora_registro) ultimo_registro, ")
		.append("		ll.latitude, ")
		.append("		ll.longitude ")
		.append("	from localizacao_linha ll ")
		.append("	inner join veiculo v on v.id = ll.id_veiculo ")
		.append("	inner join linha l on l.id = ll.id_linha ")
		.append("	inner join empresa e on e.id = l.id_empresa ")
		.append("	where ll.data_hora_registro >= date_sub(sysdate(), interval :intervalo hour) ")
		.append("	group by l.id, v.id ")
		.append("	order by ultimo_registro desc, linha, veiculo ")
		.append(" ) nao_favoritos ");
		
		params.put("intervalo", intervalo);
		
		Query query = getEntityManager().createNativeQuery(sql.toString());
		
		for (String key : params.keySet()) {
			query.setParameter(key, params.get(key));
		}
		
		List<Object[]> resultList = query.getResultList();
		
		List<LocalizacaoLinhaDTO> list = new ArrayList<LocalizacaoLinhaDTO>();
		
		for (Object[] source : resultList) {
			Long idLinha = ((Integer) source[0]).longValue();
			String linha = (String) source[1];
			String descricaoLinha = (String) source[2];
			String empresa = (String) source[3];
			String veiculo = (String) source[4];
			Date ultimoRegistro = Dates.parse(((Timestamp) source[5]), Dates.FORMAT_PT_BR_DATE_HOUR);
			String latitude = (String) source[6];
			String longitude = (String) source[7];
			list.add(new LocalizacaoLinhaDTO(idLinha, linha, descricaoLinha, empresa, veiculo, latitude, longitude, ultimoRegistro));
		}
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<LocalizacaoLinhaDTO> listVeiculosEmDeslocamentoProximos(Integer intervalo, Integer distanciaPesquisada, 
			String latitudeUsuario, String longitudeUsuario) {
		
		StringBuffer sql = new StringBuffer();
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		sql.append("SELECT \n");
		sql.append("  * \n");
		sql.append("FROM \n");
		sql.append("  ( \n");
		sql.append("    SELECT \n");
		sql.append("      linha.id id_linha, \n");
		sql.append("      linha.numero linha, \n");
		sql.append("      linha.descricao descricao_linha, \n");
		sql.append("      empresa.nome empresa, \n");
		sql.append("      veiculo.numero_registro veiculo, \n");
		sql.append("      MAX(localizacao_linha.data_hora_registro) ultimo_registro, \n");
		sql.append("      localizacao_linha.latitude, \n");
		sql.append("      localizacao_linha.longitude, \n");
		sql.append("      (:raio                         * acos( cos(radians(:latitude0)) * cos(radians( \n");
		sql.append("      localizacao_linha.latitude))  * cos(radians(:longitude) - radians( \n");
		sql.append("      localizacao_linha.longitude)) + sin(radians(:latitude1)) * sin( \n");
		sql.append("      radians(localizacao_linha.latitude)) )) AS distancia, \n");
		sql.append("      'N' favorita \n");
		sql.append("    FROM \n");
		sql.append("      localizacao_linha \n");
		sql.append("    INNER JOIN veiculo \n");
		sql.append("    ON \n");
		sql.append("      veiculo.id = localizacao_linha.id_veiculo \n");
		sql.append("    INNER JOIN linha \n");
		sql.append("    ON \n");
		sql.append("      linha.id = localizacao_linha.id_linha \n");
		sql.append("    INNER JOIN empresa \n");
		sql.append("    ON \n");
		sql.append("      empresa.id = linha.id_empresa \n");
		sql.append("    WHERE \n");
		sql.append("      localizacao_linha.data_hora_registro >= date_sub(sysdate(), \n");
		sql.append("      interval :intervalo hour) \n");
		sql.append("    GROUP BY \n");
		sql.append("      linha.id, veiculo.id \n");
		sql.append("    HAVING \n");
		sql.append("      distancia <= :distancia \n");
		sql.append("    ORDER BY ultimo_registro desc, linha, veiculo ");
		sql.append("  ) \n");
		sql.append("  nao_favoritos");
		
		params.put("raio", RAIO_TERRESTRE);
		params.put("latitude0", latitudeUsuario);
		params.put("longitude", longitudeUsuario);
		params.put("latitude1", latitudeUsuario);
		params.put("intervalo", intervalo);
		params.put("distancia", distanciaPesquisada);
		
		Query query = getEntityManager().createNativeQuery(sql.toString());
		
		for (String key : params.keySet()) {
			query.setParameter(key, params.get(key));
		}
		
		List<Object[]> resultList = query.getResultList();
		
		List<LocalizacaoLinhaDTO> list = new ArrayList<LocalizacaoLinhaDTO>();
		
		for (Object[] source : resultList) {
			Long idLinha = ((Integer) source[0]).longValue();
			String linha = (String) source[1];
			String descricaoLinha = (String) source[2];
			String empresa = (String) source[3];
			String veiculo = (String) source[4];
			Date ultimoRegistro = Dates.parse(((Timestamp) source[5]), Dates.FORMAT_PT_BR_DATE_HOUR);
			String latitude = (String) source[6];
			String longitude = (String) source[7];
			String distancia = new DecimalFormat("#0.00").format(((Double) source[8]));
			list.add(new LocalizacaoLinhaDTO(idLinha, linha, descricaoLinha, empresa, veiculo, latitude, longitude, ultimoRegistro, distancia));
		}
		
		return list;
	}

}
