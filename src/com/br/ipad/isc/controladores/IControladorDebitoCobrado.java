
package com.br.ipad.isc.controladores;

import java.util.Collection;

import android.content.Context;

import com.br.ipad.isc.bean.DebitoCobrado;
import com.br.ipad.isc.excecoes.ControladorException;

public interface IControladorDebitoCobrado {
	
	public void setContext(Context ctx );

	public Collection<DebitoCobrado> buscarDebitoCobradoPorImovelId(Integer imovelId) throws ControladorException;

	public DebitoCobrado buscarDebitoCobradoPorCodigo(Integer debitoCodigo,Integer imovelId) throws ControladorException;
	
	public Double obterValorDebitoTotal(Integer imovelId) throws ControladorException;
	
	public Integer obterQntDebitoCobradoPorImovelId(Integer imovelId) throws ControladorException;
}