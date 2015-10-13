package br.com.gsn.sysbusweb.persistence;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import br.com.gsn.sysbusweb.domain.LocalizacaoLinha;
import br.com.gsn.sysbusweb.domain.dto.LocalizacaoLinhaDTO;
import br.com.gsn.sysbusweb.domain.dto.LocalizacaoLinhaWrapperDTO;
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
	public LocalizacaoLinhaWrapperDTO listVeiculosEmDeslocamento(Long idUsuario, Integer intervalo) {
		
		StringBuffer sql = new StringBuffer();
		
		LocalizacaoLinhaWrapperDTO wrapper = new LocalizacaoLinhaWrapperDTO();
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		//Se informado usuário, busca as linhas dos seus favoritos
		if (idUsuario != null && idUsuario != 0) {
			sql
				.append(" select * from ( ")
				.append("	select ")
				.append("		l.id id_linha, ") 
				.append("		l.numero linha, ") 
				.append("		l.descricao descricao_linha, ") 
				.append("		e.nome nome_empresa, ") 
				.append("		v.numero_registro veiculo,  ")  
				.append("		max(ll.data_hora_registro) ultimo_registro, ")
				.append("		ll.latitude, ")
				.append("		ll.longitude, ")
				.append("		'S' favoritos ")
				.append("	from linha_favorita lf ")
				.append("	inner join localizacao_linha ll on ll.id_linha = lf.id_linha ")
				.append("	inner join veiculo v on v.id = ll.id_veiculo ")
				.append("	inner join linha l on l.id = ll.id_linha ")
				.append("	inner join empresa e on e.id = l.id_empresa ")
				.append("	where lf.id_usuario = :idUsuario ")
				.append("	and ll.data_hora_registro >= date_sub(sysdate(), interval :intervalof hour) ")
				.append("	group by l.id, v.id ")
				.append("	order by ultimo_registro desc, linha, veiculo ")
				.append(" ) as favoritos ")
				.append(" union all ");
			params.put("idUsuario", idUsuario);
			params.put("intervalof", intervalo);
		}
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
			.append("		ll.longitude, ")
			.append("		'N' favoritos ")
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
			
		for (Object[] source : resultList) {
			Long idLinha = ((Integer) source[0]).longValue();
			String linha = (String) source[1];
			String descricaoLinha = (String) source[2];
			String empresa = (String) source[3];
			String veiculo = (String) source[4];
			Date ultimoRegistro = Dates.parse(((Timestamp) source[5]), Dates.FORMAT_PT_BR_DATE_HOUR);
			String latitude = (String) source[6];
			String longitude = (String) source[7];
			String linhaFavorita = (String) source[8];
			
			LocalizacaoLinhaDTO localizacaoLinha = new LocalizacaoLinhaDTO(idLinha, linha, descricaoLinha, empresa, veiculo, latitude, longitude, ultimoRegistro, linhaFavorita);
			
			if (linhaFavorita.equals("S")) {
				wrapper.getLinhasFavoritas().add(localizacaoLinha);
			} else {
				wrapper.getLinhasNaoFavoritas().add(localizacaoLinha);
			}
		}
		
		return wrapper;
	}
	
	@SuppressWarnings("unchecked")
	public LocalizacaoLinhaWrapperDTO listVeiculosEmDeslocamentoProximos(Long idUsuario, Integer intervalo, Integer distanciaPesquisada, 
			String latitudeUsuario, String longitudeUsuario) {
		
		StringBuffer sql = new StringBuffer();
		
		LocalizacaoLinhaWrapperDTO wrapper = new LocalizacaoLinhaWrapperDTO();
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		//Se o usuário estiver cadastrado, busca também as linhas favoritas
		if (idUsuario != null && idUsuario != 0) {
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
			sql.append("      (:raiof                         * acos( cos(radians(:latitudef0)) * cos(radians( \n");
			sql.append("      localizacao_linha.latitude))  * cos(radians(:longitudef) - radians( \n");
			sql.append("      localizacao_linha.longitude)) + sin(radians(:latitudef1)) * sin( \n");
			sql.append("      radians(localizacao_linha.latitude)) )) AS distancia , \n");
			sql.append("      'S' favorita \n");
			sql.append("    FROM \n");
			sql.append("      linha_favorita \n");
			sql.append("    INNER JOIN localizacao_linha \n");
			sql.append("    ON \n");
			sql.append("      localizacao_linha.id_linha = linha_favorita.id_linha \n");
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
			sql.append("      interval :intervalof hour) \n");
			sql.append("    AND linha_favorita.id_usuario = :idUsuario \n");
			sql.append("    GROUP BY \n");
			sql.append("      linha.id, veiculo.id \n");
			sql.append("    HAVING \n");
			sql.append("      distancia <= :distancia0 \n");
			sql.append("    ORDER BY ultimo_registro desc, linha, veiculo ");
			sql.append("  ) \n");
			sql.append("  favoritos \n");
			sql.append("UNION ALL \n");
			
			params.put("raiof", RAIO_TERRESTRE);
			params.put("latitudef0", latitudeUsuario);
			params.put("longitudef", longitudeUsuario);
			params.put("latitudef1", latitudeUsuario);
			params.put("intervalof", intervalo);
			params.put("idUsuario", idUsuario);
			params.put("distancia0", distanciaPesquisada);
		}
		
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
			String linhaFavorita = (String) source[9];
			
			LocalizacaoLinhaDTO localizacaoLinha = new LocalizacaoLinhaDTO(idLinha, linha, descricaoLinha, empresa, veiculo, latitude, longitude, ultimoRegistro, linhaFavorita, distancia);
			
			if (linhaFavorita.equals("S")) {
				wrapper.getLinhasFavoritas().add(localizacaoLinha);
			} else {
				wrapper.getLinhasNaoFavoritas().add(localizacaoLinha);
			}
		}
		
		return wrapper;
	}

}
