
package com.br.ipad.isc.controladores;

import java.util.Collection;

import android.content.Context;

import com.br.ipad.isc.bean.ContaImposto;
import com.br.ipad.isc.excecoes.ControladorException;

public interface IControladorContaImposto  {
	
	public void setContext(Context ctx );

	public Collection<ContaImposto> buscarContaImpostoPorImovelId(Integer imovelId) throws ControladorException;
	
	public Double obterValorImpostoTotal(Integer imovelId) throws ControladorException;

	public Integer obterQntContaImpostoPorImovelId(Integer imovelId) throws ControladorException;
}