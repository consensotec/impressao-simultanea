package com.br.ipad.isc.controladores;

/**
 * Controlador Imovel Postgres
 * Método exclusivos
 * 
 * @author Amelia Pessoa
 * @date 18/07/2012
 */
public class ControladorImovelPostgres extends ControladorImovel {

	private static ControladorImovelPostgres instance;
	
	protected ControladorImovelPostgres(){
		super();
	}
	
	public static ControladorImovelPostgres getInstance(){
		if ( instance == null ){
			instance =  new ControladorImovelPostgres();
		}		
		return instance;		
	}
}
