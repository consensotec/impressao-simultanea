
package com.br.ipad.isc.controladores;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.CategoriaSubcategoria;
import com.br.ipad.isc.excecoes.ControladorException;
import com.br.ipad.isc.excecoes.RepositorioException;
import com.br.ipad.isc.repositorios.RepositorioCategoriaSubcategoria;
import com.br.ipad.isc.util.ConstantesSistema;


public class ControladorCategoriaSubcategoria extends ControladorBasico implements IControladorCategoriaSubcategoria{
	
	private static ControladorCategoriaSubcategoria instance;
	private RepositorioCategoriaSubcategoria repositorioCategoriaSubcategoria;
	protected static Context context;
	
	public void resetarInstancia(){
		instance = null;
	}
	
	protected ControladorCategoriaSubcategoria(){
		super();
	}
	
	public static ControladorCategoriaSubcategoria getInstance(){
		if ( instance == null ){
			instance =  new ControladorCategoriaSubcategoria();
			instance.repositorioCategoriaSubcategoria = RepositorioCategoriaSubcategoria.getInstance();
		}		
		return instance;		
	}

	public void setContext(Context ctx ) {
		context = ctx;		
	}	
	
	public ArrayList<CategoriaSubcategoria> buscarCategoriaSubcategoriaPorImovelId(Integer imovelId) throws ControladorException {
		try {
			return repositorioCategoriaSubcategoria.buscarCategoriaSubcategoriaPorImovelId(imovelId);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}	

	public Integer obterQuantidadeEconomiasTotal(Integer imovelId) throws ControladorException {
		try {
			return repositorioCategoriaSubcategoria.obterQuantidadeEconomiasTotal(imovelId);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}
	
	public Integer obterCategoriaPrincipal(Integer imovelId) throws ControladorException {
		try {
			return repositorioCategoriaSubcategoria.obterCategoriaPrincipal(imovelId);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}
	
	public Integer obterQuantidadeEconomias(Integer imovelId, Integer codigoCategoria, Integer codigoSubcategoria) throws ControladorException {
		try {
			return repositorioCategoriaSubcategoria.obterQuantidadeEconomias(imovelId, codigoCategoria, codigoSubcategoria);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}
}