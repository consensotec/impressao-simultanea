
package com.br.ipad.isc.controladores;

import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.util.Log;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.ConsumoTarifaCategoria;
import com.br.ipad.isc.excecoes.ControladorException;
import com.br.ipad.isc.excecoes.RepositorioException;
import com.br.ipad.isc.repositorios.RepositorioConsumoTarifaCategoria;
import com.br.ipad.isc.util.ConstantesSistema;

public class ControladorConsumoTarifaCategoria  extends ControladorBasico implements IControladorConsumoTarifaCategoria{
	
	private static ControladorConsumoTarifaCategoria instance;
	private RepositorioConsumoTarifaCategoria repositorioConsumoTarifaCategoria;
	protected static Context context;
	
	public void resetarInstancia(){
		instance = null;
	}
	
	protected ControladorConsumoTarifaCategoria(){
		super();
	}
	
	public static ControladorConsumoTarifaCategoria getInstance(){
		if ( instance == null ){
			instance =  new ControladorConsumoTarifaCategoria();
			instance.repositorioConsumoTarifaCategoria = RepositorioConsumoTarifaCategoria.getInstance();
		}		
		return instance;		
	}

	public void setContext(Context ctx ) {
		context = ctx;		
	}	

	public ArrayList<ConsumoTarifaCategoria> buscarConsumoTarifaCategoriaPorCodigo(Integer codigo) throws ControladorException {
		try {
			return repositorioConsumoTarifaCategoria.buscarConsumoTarifaCategoriaPorCodigo(codigo);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}
	
	public ConsumoTarifaCategoria buscarConsumoTarifaCategoriaPorIds(
			ConsumoTarifaCategoria consumoTarifaCatg, Integer idImovel)	throws ControladorException {
		try {
			return repositorioConsumoTarifaCategoria.buscarConsumoTarifaCategoriaPorIds(consumoTarifaCatg, idImovel);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}

	public ArrayList<ConsumoTarifaCategoria> buscarConsumosTarifasCategorias(Integer imovelId) throws ControladorException {
		try {
			return repositorioConsumoTarifaCategoria.buscarConsumosTarifasCategorias(imovelId);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}	

	public ArrayList<ConsumoTarifaCategoria> buscarConsumosTarifasCategorias(int codigoTarifa, Date dataInicioVigencia) throws ControladorException {
		try {
			return repositorioConsumoTarifaCategoria.buscarConsumosTarifasCategorias(codigoTarifa, dataInicioVigencia);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}
	
	public ArrayList<Date> buscarVigenciasConsumosTarifasCategorias(int codigoTarifa) throws ControladorException {
		try {
			return repositorioConsumoTarifaCategoria.buscarVigenciasConsumosTarifasCategorias(codigoTarifa);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}
	
	public ConsumoTarifaCategoria pesquisarDadosTarifaImovel(boolean tipoTarifaPorCategoria, String codigoCategoria,
			String codigoSubCategoria, int codigoTarifa, Date dataInicioVigencia) throws ControladorException {

		ConsumoTarifaCategoria retorno = null;
		
		ArrayList<ConsumoTarifaCategoria> colecao = 
				buscarConsumosTarifasCategorias(codigoTarifa, dataInicioVigencia); 

		for (ConsumoTarifaCategoria registro : colecao) {

			if (tipoTarifaPorCategoria) {

				if (Integer.parseInt(codigoCategoria) == registro.getIdCategoria()
						&& (registro.getIdSubcategoria() == null || 
						registro.getIdSubcategoria() == 0)) {
					retorno = registro;
					break;
				}
			} else {
				if (Integer.parseInt(codigoCategoria) == registro.getIdCategoria()
						&& Integer.parseInt(codigoSubCategoria) == registro.getIdSubcategoria()) {
					retorno = registro;
					break;
				}
			}
		}

		return retorno;
	}
}