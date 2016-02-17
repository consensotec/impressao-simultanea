
package com.br.ipad.isc.controladores;

import java.util.ArrayList;

import android.content.Context;

import com.br.ipad.isc.bean.ImovelRevisitar;
import com.br.ipad.isc.excecoes.ControladorException;


public interface IControladorImovelRevisitar {
	
	public void setContext(Context ctx );		
	public ImovelRevisitar buscarImovelRevisitarPorImovel(Integer imovelId) throws ControladorException;
	public void setMatriculasRevisitar(String idsRevisitar) throws ControladorException;
	public ArrayList<ImovelRevisitar> buscarImovelNaoRevisitado()	throws ControladorException;
}