package br.com.gsn.sysbusweb.persistence;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.gsn.sysbusweb.domain.Reclamacao;
import br.com.gsn.sysbusweb.domain.dto.ReclamacaoDTO;
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
			.append(" select count(obr.id) as reclamacoes, obr.descricao as reclamado ")
			.append(" from reclamacao rec ")
			.append(" inner join origem_reclamacao orr on orr.id = rec.id_origem_reclamacao ")
			.append(" inner join objeto_reclamado obr on obr.id = orr.objeto_reclamado ")
			.append(" where rec.data_registro >= date_sub(sysdate(), interval 1 month) ")
			.append(" group by obr.id ")
			.append(" order by obr.descricao ");
		
		List<Object[]> resultList = getEntityManager()
				.createNativeQuery(sql.toString())
				.getResultList();
		
		int total = calcularGeralReclamacoes(resultList);
		List<ReclamacaoDTO> list = new ArrayList<ReclamacaoDTO>();
		
		for (Object object : resultList) {
			BigInteger reclamacoes = (BigInteger)((Object[])object)[0];
			String reclamado = (String)((Object[])object)[1];
			
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
