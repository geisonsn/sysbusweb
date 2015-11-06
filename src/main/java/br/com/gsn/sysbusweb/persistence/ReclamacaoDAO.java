package br.com.gsn.sysbusweb.persistence;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.gsn.sysbusweb.domain.Reclamacao;
import br.com.gsn.sysbusweb.domain.dto.ReclamacaoDTO;
import br.com.gsn.sysbusweb.domain.dto.ReclamacaoPorLinhaDTO;
import br.com.gsn.sysbusweb.domain.dto.ReclamacaoRankingDTO;
import br.com.gsn.sysbusweb.domain.enums.ObjetoReclamadoEnum;
import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;

@PersistenceController
public class ReclamacaoDAO extends JPACrud<Reclamacao, Long> {

	private static final long serialVersionUID = 1L;
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Reclamacao> findAll() {
		return getEntityManager().createNamedQuery("Reclamacao.findAll").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Reclamacao> findByPeriodo(Date dataInicio, Date dataFim) {
		
		return (List<Reclamacao>) getEntityManager()
				.createNamedQuery("Reclamacao.findByPeriodo")
				.setParameter("dataInicio", dataInicio)
				.setParameter("dataFim", dataFim)
				.getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Reclamacao> findByMes(int mes) {
		
		StringBuffer sql = new StringBuffer();
		sql
			.append("SELECT r FROM  Reclamacao r ") 
			.append("left join r.linha as l ")
			.append("left join r.origemReclamacao as o ") 
			.append("left join o.tipoReclamacao as t ")
			.append("WHERE month(r.dataRegistro) = :mes ")
			.append("order by r.dataRegistro desc, l.numero, r.dataOcorrencia, t.descricao");
		
		return (List<Reclamacao>) getEntityManager()
				.createQuery(sql.toString())
				.setParameter("mes", mes)
				.getResultList();
	}
	
	/**
	 * Retorna as linhas mais reclamadas no intervalo de um mês
	 * @param quantidade de linhas exibidas
	 * @return linhas mais reclamadas
	 */
	@SuppressWarnings("unchecked")
	public List<ReclamacaoRankingDTO> listLinhasMaisReclamadas(Integer quantidade) {
		
		quantidade = (quantidade == null || quantidade == 0) ? 10 : quantidade;
		
		StringBuffer sql = new StringBuffer();
		
		sql
		.append(" select count(lin.id) reclamacoes, lin.id id_linha, lin.numero linha, emp.nome as empresa ")
		.append(" from reclamacao rec ")
		.append(" inner join linha lin on lin.id = rec.id_linha ")
		.append(" inner join empresa emp on emp.id = lin.id_empresa ")
		.append(" where rec.data_registro >= date_sub(sysdate(), interval 1 month) ")
		.append(" group by lin.numero ")
		.append(" order by reclamacoes desc, lin.numero ")
		.append(" limit :quantidade ");
		
		List<Object[]> resultList = getEntityManager()
				.createNativeQuery(sql.toString())
				.setParameter("quantidade", quantidade)
				.getResultList();
		
		List<ReclamacaoRankingDTO> list = new ArrayList<ReclamacaoRankingDTO>();
		
		for (Object[] object : resultList) {
			BigInteger reclamacoes = (BigInteger)object[0];
			Long idLinha = ((Integer) object[1]).longValue();
			String linha = (String) object[2];
			String empresa = (String) object[3];
			list.add(new ReclamacaoRankingDTO(idLinha, linha, empresa, reclamacoes.longValue()));
		}
		return list;
	}
	
	/**
	 * Retorna as principais reclamações da linha no intervalo de um mês
	 * @param quantidade de reclamações a serem exibidas
	 * @return principais reclamações
	 */
	@SuppressWarnings("unchecked")
	public List<ReclamacaoPorLinhaDTO> listPrincipaisReclamacoesPorLinha(Long idLinha, Integer quantidade) {
		
		quantidade = (quantidade == null || quantidade == 0) ? 10 : quantidade;
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT \n");
		sql.append("  CASE \n");
		sql.append("    WHEN origem_reclamacao.objeto_reclamado = 1 \n");
		sql.append("    THEN 'Motorista' \n");
		sql.append("    WHEN origem_reclamacao.objeto_reclamado = 2 \n");
		sql.append("    THEN 'Cobrador' \n");
		sql.append("    WHEN origem_reclamacao.objeto_reclamado = 3 \n");
		sql.append("    THEN 'Veículo' \n");
		sql.append("    ELSE 'Outros' \n");
		sql.append("  END AS objeto_reclamado_desc, \n");
		sql.append("  tipo_reclamacao.descricao as tipo_reclamacao, \n");
		sql.append("  COUNT(reclamacao.id) quantidade \n");
		sql.append("FROM \n");
		sql.append("  reclamacao \n");
		sql.append("LEFT JOIN origem_reclamacao \n");
		sql.append("ON \n");
		sql.append("  origem_reclamacao.id = reclamacao.id_origem_reclamacao \n");
		sql.append("LEFT JOIN tipo_reclamacao \n");
		sql.append("ON \n");
		sql.append("  tipo_reclamacao.id = origem_reclamacao.id_tipo_reclamacao \n");
		sql.append("WHERE \n");
		sql.append("  reclamacao.id_linha         = :idLinha \n");
		sql.append("AND reclamacao.data_registro >= date_sub(sysdate(), interval 1 MONTH) \n");
		sql.append("GROUP BY \n");
		sql.append("  tipo_reclamacao.id \n");
		sql.append("ORDER BY ");
		sql.append("  quantidade desc, ");
		sql.append("  CASE \n");
		sql.append("    WHEN objeto_reclamado_desc = 'Cobrador' \n");
		sql.append("    THEN 1 \n");
		sql.append("    WHEN objeto_reclamado_desc = 'Motorista' \n");
		sql.append("    THEN 2 \n");
		sql.append("    WHEN objeto_reclamado_desc = 'Veículo' \n");
		sql.append("    THEN 3 \n");
		sql.append("    ELSE 4 \n");
		sql.append("  END, \n");
		sql.append("  tipo_reclamacao.descricao LIMIT :quantidade ");
		
		List<Object[]> resultList = getEntityManager()
				.createNativeQuery(sql.toString())
				.setParameter("idLinha", idLinha)
				.setParameter("quantidade", quantidade)
				.getResultList();
		
		List<ReclamacaoPorLinhaDTO> list = new ArrayList<ReclamacaoPorLinhaDTO>();
		
		for (Object[] object : resultList) {
			String reclamado = (String) object[0];
			String tipoReclamacao = (String)((Object[])object)[1];
			Number reclamacoes = (Number) object[2];
			list.add(new ReclamacaoPorLinhaDTO(reclamado, tipoReclamacao, reclamacoes.longValue()));
		}
		
		return list;
	}

	
	@SuppressWarnings("unchecked")
	public List<ReclamacaoDTO> listReclamadosPorMes(int mes) {
		
		StringBuffer sql = new StringBuffer();
		
		sql
			.append(" select count(rec.objeto_reclamado) as reclamacoes, rec.objeto_reclamado as reclamado ")
			.append(" from reclamacao rec ")
			.append(" where month(rec.data_registro) = :mes ")
			.append(" group by rec.objeto_reclamado ");
		
		/*sql
			.append("select count(objeto_reclamado) as total, '"+ ObjetoReclamadoEnum.MOTORISTA.getDescricao() + "' as objeto_reclamado ")
			.append("from reclamacao ")
			.append("where month(data_registro) = :mes ")
			.append("and objeto_reclamado = " + ObjetoReclamadoEnum.MOTORISTA.ordinal() + " ")
			.append("union all ")
			.append("select count(objeto_reclamado) as total, '"+ ObjetoReclamadoEnum.COBRADOR.getDescricao() + "' as objeto_reclamado ")
			.append("from reclamacao ")
			.append("where month(data_registro) = :mes ")
			.append("and objeto_reclamado = " + ObjetoReclamadoEnum.COBRADOR.ordinal() + " ")
			.append("union all ")
			.append("select count(objeto_reclamado) as total, '"+ ObjetoReclamadoEnum.VEICULO.getDescricao() + "' as objeto_reclamado ")
			.append("from reclamacao ")
			.append("where month(data_registro) = :mes ")
			.append("and objeto_reclamado = " + ObjetoReclamadoEnum.VEICULO.ordinal() + " ")
			.append("union all ")
			.append("select count(objeto_reclamado) as total, '"+ ObjetoReclamadoEnum.OUTROS.getDescricao() + "' as objeto_reclamado ")
			.append("from reclamacao ")
			.append("where month(data_registro) = :mes ")
			.append("and objeto_reclamado = " + ObjetoReclamadoEnum.OUTROS.ordinal() + " ");*/
		
		List<Object[]> resultList = getEntityManager()
				.createNativeQuery(sql.toString())
				.setParameter("mes", mes)
				.getResultList();
		
		List<ReclamacaoDTO> list = new ArrayList<ReclamacaoDTO>();
		
		for (Object object : resultList) {
			BigInteger reclamacoes = (BigInteger)((Object[])object)[0];
			String reclamado = ObjetoReclamadoEnum.getFromOrdinal((Integer)((Object[])object)[1]).getDescricao();
//			String reclamado = (String)((Object[])object)[1];
			
			list.add(new ReclamacaoDTO.Builder(0)
				.objetoReclamado(reclamado)
				.totalReclamacoes(reclamacoes.intValue())
				.build());
		}
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<ReclamacaoDTO> listObjetosMaisReclamados(int mes) {
		
		/*StringBuffer sql = new StringBuffer();
		sql
		.append(" select emp.nome as descricao, count(emp.id) as reclamacoes from Reclamacao r ")
		.append(" inner join r.linha l ")
		.append(" inner join l.empresa emp ")
		.append(" where r.dataRegistro >= :tempo ")
		.append(" group by emp.id ")
		.append(" order by emp.nome asc ");*/
		
		StringBuffer sql = new StringBuffer();
		
		sql
			.append(" select count(rec.objeto_reclamado) as reclamacoes, rec.objeto_reclamado as reclamado ")
			.append(" from reclamacao rec ")
			.append(" left join origem_reclamacao orr on orr.id = rec.id_origem_reclamacao ")
			.append(" where month(rec.data_registro) = :mes ")
			.append(" group by rec.objeto_reclamado ")
			.append(" order by reclamacoes desc, rec.objeto_reclamado ");
		
		List<Object[]> resultList = getEntityManager()
				.createNativeQuery(sql.toString())
				.setParameter("mes", mes)
				.getResultList();
		
		int total = calcularGeralReclamacoes(resultList);
		List<ReclamacaoDTO> list = new ArrayList<ReclamacaoDTO>();
		
		for (Object object : resultList) {
			BigInteger reclamacoes = (BigInteger)((Object[])object)[0];
			String reclamado = ObjetoReclamadoEnum.getFromOrdinal(((Integer)((Object[])object)[1])).getDescricao();
			
			list.add(new ReclamacaoDTO.Builder(total)
				.objetoReclamado(reclamado)
				.totalReclamacoes(reclamacoes.intValue())
				.build());
		}
		
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<ReclamacaoDTO> listEmpresasMaisReclamadas(int mes) {
		
		StringBuffer sql = new StringBuffer();
		
		sql
			.append(" select count(emp.id) reclamacoes, emp.nome as empresa ")
			.append(" from reclamacao rec ")
			.append(" inner join linha lin on lin.id = rec.id_linha ")
			.append(" inner join empresa emp on emp.id = lin.id_empresa ")
			.append(" where month(rec.data_registro) = :mes ")
			.append(" group by emp.id ")
			.append(" order by reclamacoes desc, emp.nome ");
		
		List<Object[]> resultList = getEntityManager()
				.createNativeQuery(sql.toString())
				.setParameter("mes", mes)
				.getResultList();
		
		int total = calcularGeralReclamacoes(resultList);
		List<ReclamacaoDTO> list = new ArrayList<ReclamacaoDTO>();
		
		for (Object object : resultList) {
			BigInteger reclamacoes = (BigInteger)((Object[])object)[0];
			String empresa = (String)((Object[])object)[1];
			
			list.add(new ReclamacaoDTO.Builder(total)
				.empresa(empresa)
				.totalReclamacoes(reclamacoes.intValue())
				.build());
		}
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<ReclamacaoDTO> listLinhasMaisReclamadas(int mes) {
		
		StringBuffer sql = new StringBuffer();
		
		sql
		.append(" select count(lin.id) reclamacoes, lin.numero, emp.nome as empresa ")
		.append(" from reclamacao rec ")
		.append(" inner join linha lin on lin.id = rec.id_linha ")
		.append(" inner join empresa emp on emp.id = lin.id_empresa ")
		.append(" where month(rec.data_registro) = :mes ")
		.append(" group by lin.id ")
		.append(" order by reclamacoes desc, lin.numero ");
		
		List<Object[]> resultList = getEntityManager()
				.createNativeQuery(sql.toString())
				.setParameter("mes", mes)
				.getResultList();
		
		int total = calcularGeralReclamacoes(resultList);
		List<ReclamacaoDTO> list = new ArrayList<ReclamacaoDTO>();
		
		for (Object object : resultList) {
			BigInteger reclamacoes = (BigInteger)((Object[])object)[0];
			String linha = (String)((Object[])object)[1];
			String empresa = (String)((Object[])object)[2];
			
			list.add(new ReclamacaoDTO.Builder(total)
				.numeroLinha(linha)
				.empresa(empresa)
				.totalReclamacoes(reclamacoes.intValue())
				.build());
		}
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<ReclamacaoDTO> listPrincipaisReclamacoes(int mes) {
		
		StringBuffer sql = new StringBuffer();
		
		sql
		.append(" select count(tre.id) reclamacoes, tre.descricao tipo_reclamacao ")
		.append(" from reclamacao rec ")
		.append(" inner join origem_reclamacao ori on ori.id = rec.id_origem_reclamacao ")
		.append(" inner join tipo_reclamacao tre on tre.id = ori.id_tipo_reclamacao ")
		.append(" where month(rec.data_registro) = :mes ")
		.append(" group by tre.id ")
		.append(" order by reclamacoes desc, tre.descricao ");
		
		List<Object[]> resultList = getEntityManager()
				.createNativeQuery(sql.toString())
				.setParameter("mes", mes)
				.getResultList();
		
		int total = calcularGeralReclamacoes(resultList);
		List<ReclamacaoDTO> list = new ArrayList<ReclamacaoDTO>();
		
		for (Object object : resultList) {
			BigInteger reclamacoes = (BigInteger)((Object[])object)[0];
			String tipoReclamacao = (String)((Object[])object)[1];
			
			list.add(new ReclamacaoDTO.Builder(total)
				.tipoReclamacao(tipoReclamacao)
				.totalReclamacoes(reclamacoes.intValue())
				.build());
		}
		
		return list;
	}
	
	private int calcularGeralReclamacoes(List<Object[]> resultList) {
		int total = 0;
		for (Object object : resultList) {
			total += ((BigInteger)((Object[])object)[0]).intValue();
		}
		return total;
	}
	
	public List<ReclamacaoDTO> listarHorarioComMaiorLotacao() {
		StringBuffer sql = new StringBuffer();
		
		sql.append(" select time_format(reclamacao.hora, '%H') hora_ocorrencia, count(reclamacao.id) qtde ");
		sql.append(" from reclamacao ");
		sql.append(" inner join origem_reclamacao on origem_reclamacao.id = reclamacao.id_origem_reclamacao ");
		sql.append(" inner join tipo_reclamacao on tipo_reclamacao.id = origem_reclamacao.id_tipo_reclamacao ");
		sql.append(" where tipo_reclamacao.id = 2 "); //Exceço de lotação
		sql.append(" and reclamacao.hora is not null ");
		sql.append(" group by hora_ocorrencia ");
		sql.append(" order by qtde desc, hora_ocorrencia ");
		sql.append(" limit 10 ");
		
		@SuppressWarnings("unchecked")
		List<Object[]> list = (List<Object[]>)getEntityManager()
			.createNativeQuery(sql.toString()).getResultList();
		
		List<ReclamacaoDTO> source = new ArrayList<ReclamacaoDTO>();
		for (Object[] objeto : list) {
			ReclamacaoDTO r = new ReclamacaoDTO();
			r.setHoraOcorrencia((String)objeto[0]);
			r.setTotalReclamacoes(((Number)objeto[1]).intValue());
			source.add(r);
		}
		
		return source;
		
	}
	
	public List<ReclamacaoDTO> listarLinhasComMaisLotacao() {
		StringBuffer sql = new StringBuffer();
		
		sql.append(" select linha.numero, count(reclamacao.id) qtde ");
		sql.append(" from reclamacao ");
		sql.append(" inner join origem_reclamacao on origem_reclamacao.id = reclamacao.id_origem_reclamacao ");
		sql.append(" inner join tipo_reclamacao on tipo_reclamacao.id = origem_reclamacao.id_tipo_reclamacao ");
		sql.append(" inner join linha on linha.id = reclamacao.id_linha ");
		sql.append(" where tipo_reclamacao.id = 2 "); //Exceço de lotação
		sql.append(" and reclamacao.hora is not null ");
		sql.append(" group by linha.id ");
		sql.append(" order by qtde desc, linha.numero ");
		sql.append(" limit 10 ");
		
		@SuppressWarnings("unchecked")
		List<Object[]> list = (List<Object[]>)getEntityManager()
			.createNativeQuery(sql.toString()).getResultList();
		
		List<ReclamacaoDTO> source = new ArrayList<ReclamacaoDTO>();
		for (Object[] objeto : list) {
			ReclamacaoDTO r = new ReclamacaoDTO();
			r.setNumeroLinha((String)objeto[0]);
			r.setTotalReclamacoes(((Number)objeto[1]).intValue());
			source.add(r);
		}
		
		return source;
	}

}
