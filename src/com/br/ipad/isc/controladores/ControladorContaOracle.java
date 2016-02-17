package com.br.ipad.isc.controladores;



/**
 * Controlador Conta Oracle
 * Método exclusivos
 * 
 * @author Amelia Pessoa
 * @date 18/07/2012
 */
public class ControladorContaOracle extends ControladorConta {

	private static ControladorContaOracle instance;
	
	protected ControladorContaOracle(){
		super();
	}
	
	public static ControladorContaOracle getInstance(){
		if ( instance == null ){
			instance =  new ControladorContaOracle();
		}		
		return instance;		
	}
	
	
}
