

package br.com.gsn.sysbusweb.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.gsn.sysbusweb.domain.ObjetoReclamado;
import br.gov.frameworkdemoiselle.junit.DemoiselleRunner;

@RunWith(DemoiselleRunner.class)
public class ObjetoReclamadoBCTest {

    @Inject
	private ObjetoReclamadoBC objetoReclamadoBC;
	
	@Before
	public void before() {
		for (ObjetoReclamado objetoReclamado : objetoReclamadoBC.findAll()) {
			objetoReclamadoBC.delete(objetoReclamado.getId());
		}
	}	
	
	
	@Test
	public void testInsert() {
				
		// modifique para inserir dados conforme o construtor
		ObjetoReclamado objetoReclamado = new ObjetoReclamado("descricao","sigla");
		objetoReclamadoBC.insert(objetoReclamado);
		List<ObjetoReclamado> listOfObjetoReclamado = objetoReclamadoBC.findAll();
		assertNotNull(listOfObjetoReclamado);
		assertEquals(1, listOfObjetoReclamado.size());
	}	
	
	@Test
	public void testDelete() {
		
		// modifique para inserir dados conforme o construtor
		ObjetoReclamado objetoReclamado = new ObjetoReclamado("descricao","sigla");
		objetoReclamadoBC.insert(objetoReclamado);
		
		List<ObjetoReclamado> listOfObjetoReclamado = objetoReclamadoBC.findAll();
		assertNotNull(listOfObjetoReclamado);
		assertEquals(1, listOfObjetoReclamado.size());
		
		objetoReclamadoBC.delete(objetoReclamado.getId());
		listOfObjetoReclamado = objetoReclamadoBC.findAll();
		assertEquals(0, listOfObjetoReclamado.size());
	}
	
	@Test
	public void testUpdate() {
		// modifique para inserir dados conforme o construtor
		ObjetoReclamado objetoReclamado = new ObjetoReclamado("descricao","sigla");
		objetoReclamadoBC.insert(objetoReclamado);
		
		List<ObjetoReclamado> listOfObjetoReclamado = objetoReclamadoBC.findAll();
		ObjetoReclamado objetoReclamado2 = (ObjetoReclamado)listOfObjetoReclamado.get(0);
		assertNotNull(listOfObjetoReclamado);

		// alterar para tratar uma propriedade existente na Entidade ObjetoReclamado
		// objetoReclamado2.setUmaPropriedade("novo valor");
		objetoReclamadoBC.update(objetoReclamado2);
		
		listOfObjetoReclamado = objetoReclamadoBC.findAll();
		ObjetoReclamado objetoReclamado3 = (ObjetoReclamado)listOfObjetoReclamado.get(0);
		
		// alterar para tratar uma propriedade existente na Entidade ObjetoReclamado
		// assertEquals("novo valor", objetoReclamado3.getUmaPropriedade());
	}

}