package br.com.gsn.sysbusweb.domain.enums;


public enum TipoLogradouroEnum {
	
	NENHUM("", "---"),
	ACESSO("Ac", "Acesso"),
	ALAMEDA("Al", "Alameda"),
	AVENIDA("Av", "Avenida"),
	BECO("Bc", "Beco"),
	BOULEVARD("Bv", "Boulevard"),
	ESTRADA("Est", "Estrada"),
	PASSAGEM("Ps", "Passagem"),
	PONTE("Pnt", "Ponte"),
	PRAÇA("Pç", "Praça"),
	RAMAL("Rl", "Ramal"),
	RUA("R", "Rua"),
	RODOVIA("Rv", "Rodovia"),
	TRAVESSA("Tv", "Travessa"),
	VIADUTO("Vi", "Viaduto"),
	VILA("Vl", "Vila"),
	ESCADARIA("Ec", "Escadaria"),
	CAMINHO("Ca", "Caminho"),
	QUADRA("Qd", "Quadra"), 
	GALERIA("Gl", "Galeria"),
	TRECHO("Tre", "Trecho"),
	SETOR("St", "Setor"),
	CONJUNTO("Cj", "Conjunto"),
	CHÁCARA("Cha", "Chácara");
	
	private String abreviatura;
	private String descricao;
	
	private TipoLogradouroEnum(String abreviatura, String descricao) {
		this.abreviatura = abreviatura;
		this.descricao = descricao;
	}
	
	public String getAbreviatura() {
		if (abreviatura.length() > 0) {
			return abreviatura + ".";
		} else {
			return "";
		}
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public static TipoLogradouroEnum fromOrdinal(int ordinal) {
		if (ordinal >= values().length || ordinal < 0) {
			return null;
		}
		
		return values()[ordinal];
	}

	public static boolean isAbreviaturaOuTipo(String token) {
		for (TipoLogradouroEnum tipo : values()) {
			if (tipo.abreviatura.equalsIgnoreCase(token)
					|| tipo.descricao.equalsIgnoreCase(token)) {
				return true;
			}
		}
		
		return false;
	}

}
