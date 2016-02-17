
package com.br.ipad.isc.controladores;

import java.util.ArrayList;
import java.util.Date;

import android.content.Context;

import com.br.ipad.isc.bean.ConsumoTarifaCategoria;
import com.br.ipad.isc.excecoes.ControladorException;

public interface IControladorConsumoTarifaCategoria  {
	
	public void setContext(Context ctx );

	public ConsumoTarifaCategoria buscarConsumoTarifaCategoriaPorIds(
			ConsumoTarifaCategoria consumoTarifaCatg, Integer idImovel)	throws ControladorException;

	public ArrayList<ConsumoTarifaCategoria> buscarConsumosTarifasCategorias(Integer imovelId) throws ControladorException;	

	public ArrayList<ConsumoTarifaCategoria> buscarConsumosTarifasCategorias(int codigoTarifa, Date dataInicioVigencia) 
			throws ControladorException;
	
	public ArrayList<Date> buscarVigenciasConsumosTarifasCategorias(int codigoTarifa) 
			throws ControladorException;
	
	public ConsumoTarifaCategoria pesquisarDadosTarifaImovel(boolean tipoTarifaPorCategoria, String codigoCategoria,
			String codigoSubCategoria, int codigoTarifa, Date dataInicioVigencia) throws ControladorException;
	
	public ArrayList<ConsumoTarifaCategoria> buscarConsumoTarifaCategoriaPorCodigo(Integer codigo) throws ControladorException;
}