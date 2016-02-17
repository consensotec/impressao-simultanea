
package com.br.ipad.isc.controladores;

import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.util.Log;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.ConsumoTarifaFaixa;
import com.br.ipad.isc.excecoes.ControladorException;
import com.br.ipad.isc.excecoes.RepositorioException;
import com.br.ipad.isc.repositorios.RepositorioConsumoTarifaFaixa;
import com.br.ipad.isc.util.ConstantesSistema;

public class ControladorConsumoTarifaFaixa extends ControladorBasico implements IControladorConsumoTarifaFaixa {
	
	private static ControladorConsumoTarifaFaixa instance;
	private RepositorioConsumoTarifaFaixa repositorioConsumoTarifaFaixa;
	protected static Context context;
	
	public void resetarInstancia(){
		instance = null;
	}
	
	protected ControladorConsumoTarifaFaixa(){
		super();
	}
	
	public static ControladorConsumoTarifaFaixa getInstance(){
		if ( instance == null ){
			instance =  new ControladorConsumoTarifaFaixa();
			instance.repositorioConsumoTarifaFaixa = RepositorioConsumoTarifaFaixa.getInstance();
		}
		
		return instance;		
	}

	public void setContext(Context ctx ) {
		context = ctx;		
	}	

	public ArrayList<ConsumoTarifaFaixa> buscarConsumosTarifasFaixasPorTarifaCateg(Integer idTarifaCateg) throws ControladorException {
		try {
			return repositorioConsumoTarifaFaixa.buscarConsumosTarifasFaixasPorTarifaCateg(idTarifaCateg);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}

	public ArrayList<ConsumoTarifaFaixa> buscarConsumosTarifaFaixaPorId(int idTarifa, Date dataInicioVigencia) 
			throws ControladorException {
		try {
			return repositorioConsumoTarifaFaixa.buscarConsumosTarifaFaixaPorId(idTarifa, dataInicioVigencia);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}
	
	public ArrayList<ConsumoTarifaFaixa> buscarConsumosTarifaFaixaPorCodigo(int codigoTarifa, Date dataInicioVigencia) 
			throws ControladorException {
		try {
			return repositorioConsumoTarifaFaixa.buscarConsumosTarifaFaixaPorCodigo(codigoTarifa, dataInicioVigencia);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}
	
	public ArrayList<ConsumoTarifaFaixa> selecionarFaixasCalculoValorFaturadoPorId(boolean tipoTarifaPorCategoria, 
			Integer codigoCategoria, Integer codigoSubCategoria, int idTarifa, Date dataInicioVigencia) throws ControladorException {

		ArrayList<ConsumoTarifaFaixa> retorno = new ArrayList<ConsumoTarifaFaixa>();
		
		ArrayList<ConsumoTarifaFaixa> colecao = 
				buscarConsumosTarifaFaixaPorId(idTarifa, dataInicioVigencia);
		
		for (ConsumoTarifaFaixa registro : colecao) {
			if (tipoTarifaPorCategoria) {
				if (codigoCategoria.intValue() == registro.getConsumoTarifaCategoria().getIdCategoria().intValue()
						&& (registro.getConsumoTarifaCategoria().getIdSubcategoria() == null || 
						registro.getConsumoTarifaCategoria().getIdSubcategoria().intValue() == 0)) {
					retorno.add(registro);
				}
			} else {
				if (codigoCategoria.intValue() == registro.getConsumoTarifaCategoria().getIdCategoria()
						&& codigoSubCategoria.intValue() == 
						registro.getConsumoTarifaCategoria().getIdSubcategoria().intValue()) {
					retorno.add(registro);
				}
			}
		}

		return retorno;
	}
	
	public ArrayList<ConsumoTarifaFaixa> selecionarFaixasCalculoValorFaturadoPorCodigo(boolean tipoTarifaPorCategoria, 
			Integer codigoCategoria, Integer codigoSubCategoria, int codigoTarifa, Date dataInicioVigencia) throws ControladorException {

		ArrayList<ConsumoTarifaFaixa> retorno = new ArrayList<ConsumoTarifaFaixa>();
		
		ArrayList<ConsumoTarifaFaixa> colecao = 
				buscarConsumosTarifaFaixaPorCodigo(codigoTarifa, dataInicioVigencia);
		
		if (colecao!=null){
			for (ConsumoTarifaFaixa registro : colecao) {
				if (tipoTarifaPorCategoria) {
					if (codigoCategoria.intValue() == registro.getConsumoTarifaCategoria().getIdCategoria().intValue()
							&& (registro.getConsumoTarifaCategoria().getIdSubcategoria() == null || 
							registro.getConsumoTarifaCategoria().getIdSubcategoria().intValue() == 0)) {
						retorno.add(registro);
					}
				} else {
					if (codigoCategoria.intValue() == registro.getConsumoTarifaCategoria().getIdCategoria()
							&& codigoSubCategoria.intValue() == 
							registro.getConsumoTarifaCategoria().getIdSubcategoria().intValue()) {
						retorno.add(registro);
					}
				}
			}
		}
		
		return retorno;
	}
	
}