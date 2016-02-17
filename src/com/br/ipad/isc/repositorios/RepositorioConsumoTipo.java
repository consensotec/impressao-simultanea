
package com.br.ipad.isc.repositorios;

public class RepositorioConsumoTipo extends RepositorioBasico implements IRepositorioConsumoTipo {
	
	private static RepositorioConsumoTipo instancia;	

	public void resetarInstancia(){
		instancia = null;
	}

	public static RepositorioConsumoTipo getInstance(){
		if ( instancia == null ){
			instancia =  new RepositorioConsumoTipo();
		} 
		return instancia;
	}	

}