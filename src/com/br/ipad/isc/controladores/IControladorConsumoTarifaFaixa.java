
package com.br.ipad.isc.controladores;

import java.util.ArrayList;
import java.util.Date;

import android.content.Context;

import com.br.ipad.isc.bean.ConsumoTarifaFaixa;
import com.br.ipad.isc.excecoes.ControladorException;

public interface IControladorConsumoTarifaFaixa {
	
	public void setContext(Context ctx );

	public ArrayList<ConsumoTarifaFaixa> buscarConsumosTarifasFaixasPorTarifaCateg(Integer idTarifaCateg) 
			throws ControladorException;

	public ArrayList<ConsumoTarifaFaixa> buscarConsumosTarifaFaixaPorId(int idTarifa, Date dataInicioVigencia) 
			throws ControladorException;
	
	public ArrayList<ConsumoTarifaFaixa> selecionarFaixasCalculoValorFaturadoPorId(boolean tipoTarifaPorCategoria, 
			Integer codigoCategoria, Integer codigoSubCategoria, int codigoTarifa, Date dataInicioVigencia) 
					throws ControladorException;
	
	public ArrayList<ConsumoTarifaFaixa> buscarConsumosTarifaFaixaPorCodigo(int codigoTarifa, Date dataInicioVigencia) 
			throws ControladorException;
}