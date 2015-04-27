

package br.com.gsn.sysbusweb.business;

import static org.junit.Assert.*;
import java.util.*;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import br.gov.frameworkdemoiselle.junit.DemoiselleRunner;
import br.com.gsn.sysbusweb.domain.Reclamacao;
import br.com.gsn.sysbusweb.business.ReclamacaoBC;

@RunWith(DemoiselleRunner.class)
public class ReclamacaoBCTest {

    @Inject
	private ReclamacaoBC reclamacaoBC;
	
	@Before
	public void before() {
		for (Reclamacao reclamacao : reclamacaoBC.findAll()) {
			reclamacaoBC.delete(reclamacao.getId());
		}
	}	
	
	
	@Test
	public void testInsert() {
				
		// modifique para inserir dados conforme o construtor
		Reclamacao reclamacao = new Reclamacao();
		reclamacaoBC.insert(reclamacao);
		List<Reclamacao> listOfReclamacao = reclamacaoBC.findAll();
		assertNotNull(listOfReclamacao);
		assertEquals(1, listOfReclamacao.size());
	}	
	
	@Test
	public void testDelete() {
		
		// modifique para inserir dados conforme o construtor
		Reclamacao reclamacao = new Reclamacao();
		reclamacaoBC.insert(reclamacao);
		
		List<Reclamacao> listOfReclamacao = reclamacaoBC.findAll();
		assertNotNull(listOfReclamacao);
		assertEquals(1, listOfReclamacao.size());
		
		reclamacaoBC.delete(reclamacao.getId());
		listOfReclamacao = reclamacaoBC.findAll();
		assertEquals(0, listOfReclamacao.size());
	}
	
	@Test
	public void testUpdate() {
		// modifique para inserir dados conforme o construtor
		Reclamacao reclamacao = new Reclamacao();
		reclamacaoBC.insert(reclamacao);
		
		List<Reclamacao> listOfReclamacao = reclamacaoBC.findAll();
		Reclamacao reclamacao2 = (Reclamacao)listOfReclamacao.get(0);
		assertNotNull(listOfReclamacao);

		// alterar para tratar uma propriedade existente na Entidade Reclamacao
		// reclamacao2.setUmaPropriedade("novo valor");
		reclamacaoBC.update(reclamacao2);
		
		listOfReclamacao = reclamacaoBC.findAll();
		Reclamacao reclamacao3 = (Reclamacao)listOfReclamacao.get(0);
		
		// alterar para tratar uma propriedade existente na Entidade Reclamacao
		// assertEquals("novo valor", reclamacao3.getUmaPropriedade());
	}

}