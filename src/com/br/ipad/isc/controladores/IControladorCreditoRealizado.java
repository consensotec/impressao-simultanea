
package com.br.ipad.isc.controladores;

import java.util.Collection;

import android.content.Context;

import com.br.ipad.isc.bean.CreditoRealizado;
import com.br.ipad.isc.excecoes.ControladorException;

public interface IControladorCreditoRealizado  {
	
	public void setContext(Context ctx );

	public Collection<CreditoRealizado> buscarCreditoRealizadoPorImovelId(Integer imovelId) throws ControladorException;

	public Double obterValorCreditoTotal(Integer imovelId) throws ControladorException;
	
	public CreditoRealizado buscarCreditoRealizadoPorDescricao(String descricaoCredito,Integer imovelId) throws ControladorException;

	public Integer obterQntCreditoRealizadoPorImovelId(Integer imovelId) throws ControladorException;
}
