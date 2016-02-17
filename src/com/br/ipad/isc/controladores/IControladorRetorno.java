package com.br.ipad.isc.controladores;

import com.br.ipad.isc.excecoes.ControladorException;

public interface IControladorRetorno {
	
	public String geraRetornoImovel( Integer idOS ) throws ControladorException;

}
