

package br.com.gsn.sysbusweb.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.gsn.sysbusweb.domain.TipoReclamacao;
import br.gov.frameworkdemoiselle.junit.DemoiselleRunner;

@RunWith(DemoiselleRunner.class)
public class TipoReclamacaoBCTest {

    @Inject
	private TipoReclamacaoBC tipoReclamacaoBC;
	
	@Before
	public void before() {
		for (TipoReclamacao tipoReclamacao : tipoReclamacaoBC.findAll()) {
			tipoReclamacaoBC.delete(tipoReclamacao.getId());
		}
	}	
	
	
	@Test
	public void testInsert() {
				
		// modifique para inserir dados conforme o construtor
		TipoReclamacao tipoReclamacao = new TipoReclamacao("descricao");
		tipoReclamacaoBC.insert(tipoReclamacao);
		List<TipoReclamacao> listOfTipoReclamacao = tipoReclamacaoBC.findAll();
		assertNotNull(listOfTipoReclamacao);
		assertEquals(1, listOfTipoReclamacao.size());
	}	
	
	@Test
	public void testDelete() {
		
		// modifique para inserir dados conforme o construtor
		TipoReclamacao tipoReclamacao = new TipoReclamacao("descricao");
		tipoReclamacaoBC.insert(tipoReclamacao);
		
		List<TipoReclamacao> listOfTipoReclamacao = tipoReclamacaoBC.findAll();
		assertNotNull(listOfTipoReclamacao);
		assertEquals(1, listOfTipoReclamacao.size());
		
		tipoReclamacaoBC.delete(tipoReclamacao.getId());
		listOfTipoReclamacao = tipoReclamacaoBC.findAll();
		assertEquals(0, listOfTipoReclamacao.size());
	}
	
	@Test
	public void testUpdate() {
		// modifique para inserir dados conforme o construtor
		TipoReclamacao tipoReclamacao = new TipoReclamacao("descricao");
		tipoReclamacaoBC.insert(tipoReclamacao);
		
		List<TipoReclamacao> listOfTipoReclamacao = tipoReclamacaoBC.findAll();
		TipoReclamacao tipoReclamacao2 = (TipoReclamacao)listOfTipoReclamacao.get(0);
		assertNotNull(listOfTipoReclamacao);

		// alterar para tratar uma propriedade existente na Entidade TipoReclamacao
		// tipoReclamacao2.setUmaPropriedade("novo valor");
		tipoReclamacaoBC.update(tipoReclamacao2);
		
		listOfTipoReclamacao = tipoReclamacaoBC.findAll();
		TipoReclamacao tipoReclamacao3 = (TipoReclamacao)listOfTipoReclamacao.get(0);
		
		// alterar para tratar uma propriedade existente na Entidade TipoReclamacao
		// assertEquals("novo valor", tipoReclamacao3.getUmaPropriedade());
	}

}