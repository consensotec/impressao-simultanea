
package com.br.ipad.isc.controladores;

import java.util.ArrayList;

import android.content.Context;

import com.br.ipad.isc.bean.ContaCategoriaConsumoFaixa;
import com.br.ipad.isc.excecoes.ControladorException;

public interface IControladorContaCategoriaConsumoFaixa {
	
	public void setContext(Context ctx );

	public ArrayList<ContaCategoriaConsumoFaixa> buscarContasCategoriasConsumosFaixasPorPorContaCategoriaId(Integer idContaCategoria) throws ControladorException;
	
	public Double obterTotalConsumoContasCategoriasConsumosFaixasPorPorContaCategoriaId(Integer idContaCategoria) 
			throws ControladorException;

	public Double obterTotalValorTarifaContasCategoriasConsumosFaixasPorPorContaCategoriaId(
			Integer idContaCategoria)throws ControladorException;

}