
package com.br.ipad.isc.repositorios;

public class RepositorioConsumoAnormalidade extends RepositorioBasico implements IRepositorioConsumoAnormalidade {
	
	private static RepositorioConsumoAnormalidade instancia;	
	
	public void resetarInstancia(){
		instancia = null;
	}

	public static RepositorioConsumoAnormalidade getInstance(){
		if ( instancia == null ){
			instancia =  new RepositorioConsumoAnormalidade();
		} 
		return instancia;
	}

}
