
package com.br.ipad.isc.excecoes;

public class ControladorException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ControladorException(String mensagem) {
		super(mensagem);
	}
	
	public ControladorException() {
		super();
	}
}
