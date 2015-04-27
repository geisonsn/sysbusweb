package br.com.gsn.sysbusweb.domain.enums;

public enum PerfilEnum {
	ADMINISTRADOR("Administrador"),
	GESTOR("Gestor"),
	MANTENEDOR("Mantenedor"),
	CLIENTE("Cliente");
	
	private String descricao;
	
	private PerfilEnum(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
}
