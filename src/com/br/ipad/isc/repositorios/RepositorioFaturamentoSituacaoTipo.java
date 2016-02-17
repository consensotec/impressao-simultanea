
package com.br.ipad.isc.repositorios;

public class RepositorioFaturamentoSituacaoTipo extends RepositorioBasico implements IRepositorioFaturamentoSituacaoTipo {
	
	private static RepositorioFaturamentoSituacaoTipo instancia;	

	public void resetarInstancia(){
		instancia = null;
	}

	public static RepositorioFaturamentoSituacaoTipo getInstance(){
		if ( instancia == null ){
			instancia =  new RepositorioFaturamentoSituacaoTipo();
		} 
		return instancia;
	}

}
