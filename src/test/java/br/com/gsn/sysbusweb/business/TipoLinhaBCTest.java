package br.com.gsn.sysbusweb.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.gsn.sysbusweb.domain.TipoLinha;
import br.gov.frameworkdemoiselle.junit.DemoiselleRunner;

@RunWith(DemoiselleRunner.class)
public class TipoLinhaBCTest {

	@Inject
	private TipoLinhaBC tipoLinhaBC;

	@Before
	public void before() {
		for (TipoLinha tipoLinha : tipoLinhaBC.findAll()) {
			tipoLinhaBC.delete(tipoLinha.getId());
		}
	}

	@Test(expected = PersistenceException.class)
	public void testInsert() {

		// modifique para inserir dados conforme o construtor
		TipoLinha tipoLinha = new TipoLinha("Alimentadora");
		tipoLinhaBC.insert(tipoLinha);
		tipoLinha = new TipoLinha("Alimentadora");
		tipoLinhaBC.insert(tipoLinha);
		List<TipoLinha> listOfTipoLinha = tipoLinhaBC.findAll();
		assertNotNull(listOfTipoLinha);
		assertEquals(1, listOfTipoLinha.size());
	}

	@Test
	public void testDelete() {

		// modifique para inserir dados conforme o construtor
		TipoLinha tipoLinha = new TipoLinha("descricao");
		tipoLinhaBC.insert(tipoLinha);

		List<TipoLinha> listOfTipoLinha = tipoLinhaBC.findAll();
		assertNotNull(listOfTipoLinha);
		assertEquals(1, listOfTipoLinha.size());

		tipoLinhaBC.delete(tipoLinha.getId());
		listOfTipoLinha = tipoLinhaBC.findAll();
		assertEquals(0, listOfTipoLinha.size());
	}

	@Test
	public void testUpdate() {
		// modifique para inserir dados conforme o construtor
		TipoLinha tipoLinha = new TipoLinha("descricao");
		tipoLinhaBC.insert(tipoLinha);

		List<TipoLinha> listOfTipoLinha = tipoLinhaBC.findAll();
		TipoLinha tipoLinha2 = (TipoLinha) listOfTipoLinha.get(0);
		assertNotNull(listOfTipoLinha);

		// alterar para tratar uma propriedade existente na Entidade TipoLinha
		// tipoLinha2.setUmaPropriedade("novo valor");
		tipoLinhaBC.update(tipoLinha2);

		listOfTipoLinha = tipoLinhaBC.findAll();
		TipoLinha tipoLinha3 = (TipoLinha) listOfTipoLinha.get(0);

		// alterar para tratar uma propriedade existente na Entidade TipoLinha
		// assertEquals("novo valor", tipoLinha3.getUmaPropriedade());
	}

}