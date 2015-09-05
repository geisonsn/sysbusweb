package br.com.gsn.sysbusweb.persistence;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.gsn.sysbusweb.domain.Reclamacao;
import br.com.gsn.sysbusweb.domain.dto.ReclamacaoDTO;
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
	 * Retorna as dez linhas mais reclamadas
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ReclamacaoRankingDTO> listTopDezLinhasReclamadas() {
		
		StringBuffer sql = new StringBuffer();
		
		sql
		.append(" select count(lin.id) reclamacoes, lin.numero linha, emp.nome as empresa ")
		.append(" from reclamacao rec ")
		.append(" inner join linha lin on lin.id = rec.id_linha ")
		.append(" inner join empresa emp on emp.id = lin.id_empresa ")
		.append(" where rec.data_registro >= date_sub(sysdate(), interval 1 month) ")
		.append(" group by lin.numero ")
		.append(" order by reclamacoes desc, lin.numero ")
		.append(" limit 10 ");
		
		List<Object[]> resultList = getEntityManager()
				.createNativeQuery(sql.toString())
				.getResultList();
		
		List<ReclamacaoRankingDTO> list = new ArrayList<ReclamacaoRankingDTO>();
		
		for (Object object : resultList) {
			BigInteger reclamacoes = (BigInteger)((Object[])object)[0];
			String linha = (String)((Object[])object)[1];
			String empresa = (String)((Object[])object)[2];
			list.add(new ReclamacaoRankingDTO(linha, empresa, reclamacoes.longValue()));
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
	public List<ReclamacaoDTO> listObjetosMaisReclamados() {
		
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
			.append(" inner join origem_reclamacao orr on orr.id = rec.id_origem_reclamacao ")
			.append(" where rec.data_registro >= date_sub(sysdate(), interval 1 month) ")
			.append(" group by rec.objeto_reclamado ")
			.append(" order by rec.objeto_reclamado ");
		
		List<Object[]> resultList = getEntityManager()
				.createNativeQuery(sql.toString())
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
	public List<ReclamacaoDTO> listEmpresasMaisReclamadas() {
		
		StringBuffer sql = new StringBuffer();
		
		sql
			.append(" select count(emp.id) reclamacoes, emp.nome as empresa ")
			.append(" from reclamacao rec ")
			.append(" inner join linha lin on lin.id = rec.id_linha ")
			.append(" inner join empresa emp on emp.id = lin.id_empresa ")
			.append(" where rec.data_registro >= date_sub(sysdate(), interval 1 month) ")
			.append(" group by emp.id ")
			.append(" order by emp.nome ");
		
		List<Object[]> resultList = getEntityManager()
				.createNativeQuery(sql.toString())
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
	public List<ReclamacaoDTO> listLinhasMaisReclamadas() {
		
		StringBuffer sql = new StringBuffer();
		
		sql
		.append(" select count(lin.id) reclamacoes, lin.numero, emp.nome as empresa ")
		.append(" from reclamacao rec ")
		.append(" inner join linha lin on lin.id = rec.id_linha ")
		.append(" inner join empresa emp on emp.id = lin.id_empresa ")
		.append(" where rec.data_registro >= date_sub(sysdate(), interval 1 month) ")
		.append(" group by lin.id ")
		.append(" order by lin.numero ");
		
		List<Object[]> resultList = getEntityManager()
				.createNativeQuery(sql.toString())
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
	public List<ReclamacaoDTO> listPrincipaisReclamacoes() {
		
		StringBuffer sql = new StringBuffer();
		
		sql
		.append(" select count(tre.id) reclamacoes, tre.descricao tipo_reclamacao ")
		.append(" from reclamacao rec ")
		.append(" inner join origem_reclamacao ori on ori.id = rec.id_origem_reclamacao ")
		.append(" inner join tipo_reclamacao tre on tre.id = ori.id_tipo_reclamacao ")
		.append(" where rec.data_registro >= date_sub(sysdate(), interval 1 month) ")
		.append(" group by tre.id ")
		.append(" order by tre.descricao ");
		
		List<Object[]> resultList = getEntityManager()
				.createNativeQuery(sql.toString())
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

}
