
package com.br.ipad.isc.excecoes;

public class ZebraException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ZebraException(String mensagem) {
		super(mensagem);
	}
	
	public ZebraException() {
		super();
	}	
}
