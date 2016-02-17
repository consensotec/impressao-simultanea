package com.br.ipad.isc.controladores;


/**
 * Controlador Imovel Oracle
 * Método exclusivos
 * 
 * @author Amelia Pessoa
 * @date 18/07/2012
 */
public class ControladorImovelOracle extends ControladorImovel {

	private static ControladorImovelOracle instance;
	
	protected ControladorImovelOracle(){
		super();
	}
	
	public static ControladorImovelOracle getInstance(){
		if ( instance == null ){
			instance =  new ControladorImovelOracle();
		}		
		return instance;		
	}
}
