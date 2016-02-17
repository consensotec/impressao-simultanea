
package com.br.ipad.isc.excecoes;

public class FachadaException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public FachadaException(String mensagem) {
		super(mensagem);
	}
	
	public FachadaException() {
		super();
	}
}
