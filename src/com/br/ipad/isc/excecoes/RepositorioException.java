
package com.br.ipad.isc.excecoes;

public class RepositorioException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public RepositorioException(String mensagem) {
		super(mensagem);
	}
	
	public RepositorioException() {
		super();
	}	
}
