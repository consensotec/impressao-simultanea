
package com.br.ipad.isc.controladores;
import java.util.ArrayList;

import android.content.Context;

import com.br.ipad.isc.bean.ContaDebito;
import com.br.ipad.isc.excecoes.ControladorException;

public interface IControladorContaDebito {
	
	public void setContext(Context ctx );
	
	public ArrayList<ContaDebito> buscarContasDebitosPorIdImovel(Integer idImovel) throws ControladorException;


	
}