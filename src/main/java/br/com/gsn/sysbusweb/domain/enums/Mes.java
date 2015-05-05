package br.com.gsn.sysbusweb.domain.enums;

import java.util.Arrays;
import java.util.List;

import br.com.gsn.sysbusweb.util.Util;

public enum Mes {
	
	JANEIRO("Janeiro"),
	FEVEREIRO("Fevereiro"),
	MARCO("Março"),
	ABRIL("Abril"),
	MAIO("Maio"),
	JUNHO("Junho"),
	JULHO("Julho"),
	AGOSTO("Agosto"),
	SETEMBRO("Setembro"),
	OUTUBRO("Outubro"),
	NOVEMBRO("Novembro"),
	DEZEMBRO("Dezembro");
	
	private String descricao;
	
	Mes(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public static Mes getFromOrdinal(int ordinal) {
		return values()[ordinal];
	}
	
	public static List<Mes> list() {
		return Arrays.asList(Mes.values());
	}
	
	public static List<Mes> listToCurrentMonth() {
		int mesCorrente = Util.getMesCorrente() + 1;
		return list().subList(0, mesCorrente);
	}
}
