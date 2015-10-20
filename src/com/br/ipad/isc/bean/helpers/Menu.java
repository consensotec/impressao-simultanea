
package com.br.ipad.isc.bean.helpers;

import java.io.Serializable;

/**
 * [] Classe Básica - Menu
 * 
 * @author Carlos Chaves
 * @since 18/07/2012
 */
public class Menu implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String nome;
	private String legenda;
	
	public Menu(String nome, String legenda) {
		super();
		this.nome = nome;
		this.legenda = legenda;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLegenda() {
		return legenda;
	}

	public void setLegenda(String legenda) {
		this.legenda = legenda;
	}
	
	
	
	
	
	

}
