package br.com.gsn.sysbusweb.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;

import br.com.gsn.sysbusweb.domain.Linha;
import br.com.gsn.sysbusweb.domain.Veiculo;
import br.com.gsn.sysbusweb.domain.dto.VeiculoDTO;
import br.gov.frameworkdemoiselle.template.JPACrud;

public class VeiculoDAO extends JPACrud<Veiculo, Long> {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public List<Veiculo> findAll() {
		return getEntityManager().createNamedQuery(Veiculo.FIND_ALL, Veiculo.class).getResultList();
	}
	
	public Veiculo getByNumeroRegistro(String numeroRegistro) {
		return getEntityManager()
				.createNamedQuery(Veiculo.GET_BY_NUMERO_REGISTRO, Veiculo.class)
				.setParameter("numeroRegistro", numeroRegistro)
				.getSingleResult();
	}
	
	public Veiculo getByPlaca(String placa) {
		return getEntityManager()
				.createNamedQuery(Veiculo.GET_BY_PLACA, Veiculo.class)
				.setParameter("placa", placa)
				.getSingleResult();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<VeiculoDTO> findByNumeroLinha(String numeroLinha) {
		StringBuffer jpql = new StringBuffer();
		jpql
			.append(" select l.id as idLinha, l.descricao as descricaoLinha, ") 
			.append(" l.numero as numeroLinha, v.id as idVeiculo, v.numeroRegistro ") 
			.append(" from Veiculo v, Linha l ")
			.append(" where v.empresa.id = l.empresa.id ")
			.append(" and l.numero = :numeroLinha ")
			.append(" order by v.numeroRegistro ");
		
		Query query = getEntityManager().createQuery(jpql.toString());
		query.setParameter("numeroLinha", numeroLinha);
		
		List<Object[]> list = query.getResultList();
		
		List<VeiculoDTO> listVeiculos = new ArrayList<VeiculoDTO>();
		
		for (Object[] objeto : list) {
			VeiculoDTO v = new VeiculoDTO();
			v.setIdLinha(((Number)objeto[0]).longValue());
			v.setDescricaoLinha(((String)objeto[1]));
			v.setNumeroLinha(((String)objeto[2]));
			v.setIdVeiculo(((Number)objeto[3]).longValue());
			v.setNumeroRegistro(((String)objeto[4]));
			listVeiculos.add(v);
		}
		
		return listVeiculos;
	}
	
	@SuppressWarnings("unchecked")
	public List<Veiculo> findByNumeroRegistroOuPlaca(String numeroRegistro, String placa) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		StringBuffer jpql = new StringBuffer(" select v from Veiculo v ")
			.append(" where ");
		
		int contArgumentos = 0;
		
		if (StringUtils.isNotEmpty(numeroRegistro)) {
			jpql.append(" upper(v.numeroRegistro) LIKE :numeroRegistro ");
			params.put("numeroRegistro", "%" + numeroRegistro.toUpperCase() + "%");
			contArgumentos++;
		}
		
		if (StringUtils.isNotEmpty(placa)) {
			if (contArgumentos > 0) {
				jpql.append(" and ");
			}
			jpql.append(" upper(v.placa) LIKE :placa ");
			params.put("placa", "%" + placa.toUpperCase() + "%");
		}
		
		jpql.append(" order by v.numeroRegistro, v.placa ");
		
		Query query = getEntityManager()
				.createQuery(jpql.toString(), Veiculo.class);
		
		for (String key : params.keySet()) {
			query.setParameter(key, params.get(key));
		}
		
		return (List<Veiculo>) query.getResultList();
	}
	
	public List<Veiculo> listVeiculosNaoCadastradosParaLinha(Linha linha) {
		StringBuffer jpql = new StringBuffer()
			.append(" select v from Veiculo v ")
			.append(" where v.id not in ") 
			.append(" ( ") 
			.append(" 		select vl.veiculo.id from VeiculoLinha vl ")
			.append(" 		where vl.linha.id = :idLinha ")
			.append(" ) ")
			.append(" order by v.numeroRegistro ");
		
		return getEntityManager()
				.createQuery(jpql.toString(), Veiculo.class)
				.setParameter("idLinha", linha.getId())
				.getResultList();
	}

	public List<Veiculo> listVeiculosCadastradosParaLinha(Linha linha) {
		StringBuffer jpql = new StringBuffer()
			.append(" select vl.veiculo from VeiculoLinha vl ")
			.append(" where vl.linha.id = :idLinha "); 
		
		return getEntityManager()
				.createQuery(jpql.toString(), Veiculo.class)
				.setParameter("idLinha", linha.getId())
				.getResultList();
	}

}
