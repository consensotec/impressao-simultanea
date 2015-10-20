
package com.br.ipad.isc.excecoes;

public class NegocioException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public NegocioException(String mensagem) {
		super(mensagem);
	}
	
	public NegocioException() {
		super();
	}
}
