
package com.br.ipad.isc.controladores;

import android.content.Context;

import com.br.ipad.isc.excecoes.ControladorException;


public interface IControladorLogFinalizacao {
	
	public void setContext(Context ctx );		
	public void inserir(String msgTipoFinalizacao) throws ControladorException;
}