package br.com.gsn.sysbusweb.domain.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum ObjetoReclamadoEnum {
	
	MOTORISTA("Motorista"), 
	COBRADOR("Cobrador"),
	VEICULO("Veículo"), 
	OUTROS("Outros");
	
	private String descricao;
	
	private ObjetoReclamadoEnum(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
	public static ObjetoReclamadoEnum getFromOrdinal(int index) {
		return values()[index];
	}
	
	public static List<ObjetoReclamadoEnum> list() {
		return construirListaObjetoReclamado();
	}

	private static List<ObjetoReclamadoEnum> construirListaObjetoReclamado() {
		
		List<ObjetoReclamadoEnum> list = Arrays.asList(values());
		
		ordenarPorDescricao(list);
		
		ajustarLista(list);
		
		return list;
	}

	private static void ordenarPorDescricao(List<ObjetoReclamadoEnum> list) {
		Collections.sort(list, new Comparator<ObjetoReclamadoEnum>() {
			@Override
			public int compare(ObjetoReclamadoEnum o1, ObjetoReclamadoEnum o2) {
				return o1.descricao.compareTo(o2.descricao);
			}
		});
	}
	
	private static void ajustarLista(List<ObjetoReclamadoEnum> lista) {
		int indexOutrosOrdenado = lista.indexOf(OUTROS); //Posicao ocupada por OUTROS apos sort
		//Põe a opção 'Outros' no final e reajusta os outros elementos na lista
		for (int index = indexOutrosOrdenado; index < lista.size() - 1; index++) {
			ObjetoReclamadoEnum elementoEsq = lista.get(index);
			ObjetoReclamadoEnum elementoDir = lista.get(index+1);
			lista.set(index, elementoDir);
			lista.set(index + 1, elementoEsq);
		}
	}
	
	/*public static void main(String[] args) {
		List<ObjetoReclamadoEnum> list = ObjetoReclamadoEnum.list();
		for (ObjetoReclamadoEnum objetoReclamadoEnum : list) {
			System.out.println("ObjetoReclamado [" + objetoReclamadoEnum.name() + ", " + objetoReclamadoEnum.ordinal() + "]");
		}
	}*/
	
}
