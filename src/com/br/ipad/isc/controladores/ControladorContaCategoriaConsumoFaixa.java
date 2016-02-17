
package com.br.ipad.isc.controladores;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.ContaCategoriaConsumoFaixa;
import com.br.ipad.isc.excecoes.ControladorException;
import com.br.ipad.isc.excecoes.RepositorioException;
import com.br.ipad.isc.repositorios.RepositorioContaCategoriaConsumoFaixa;
import com.br.ipad.isc.util.ConstantesSistema;

public class ControladorContaCategoriaConsumoFaixa   extends ControladorBasico implements IControladorContaCategoriaConsumoFaixa{
	
	private static ControladorContaCategoriaConsumoFaixa instance;
	private RepositorioContaCategoriaConsumoFaixa repositorioContaCategoriaConsumoFaixa;
	protected static Context context;
	
	public void resetarInstancia(){
		instance = null;
	}
	
	protected ControladorContaCategoriaConsumoFaixa(){
		super();
	}
	
	public static ControladorContaCategoriaConsumoFaixa getInstance(){
		if ( instance == null ){
			instance =  new ControladorContaCategoriaConsumoFaixa();
			instance.repositorioContaCategoriaConsumoFaixa = RepositorioContaCategoriaConsumoFaixa.getInstance();
		}
		
		return instance;		
	}

	public void setContext(Context ctx ) {
		context = ctx;		
	}	

	public ArrayList<ContaCategoriaConsumoFaixa> buscarContasCategoriasConsumosFaixasPorPorContaCategoriaId(Integer idContaCategoria) throws ControladorException {
		try {
			return repositorioContaCategoriaConsumoFaixa.buscarContasCategoriasConsumosFaixasPorPorContaCategoriaId(idContaCategoria);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}

	public Double obterTotalConsumoContasCategoriasConsumosFaixasPorPorContaCategoriaId(Integer idContaCategoria) throws ControladorException {
		try {
			return repositorioContaCategoriaConsumoFaixa.
					obterTotalConsumoContasCategoriasConsumosFaixasPorPorContaCategoriaId(idContaCategoria);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}

	public Double obterTotalValorTarifaContasCategoriasConsumosFaixasPorPorContaCategoriaId(
			Integer idContaCategoria) throws ControladorException {
		try {
			return repositorioContaCategoriaConsumoFaixa.
					obterTotalValorTarifaContasCategoriasConsumosFaixasPorPorContaCategoriaId(idContaCategoria);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}
}