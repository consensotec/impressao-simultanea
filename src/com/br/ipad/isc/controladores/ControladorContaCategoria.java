
package com.br.ipad.isc.controladores;

import android.content.Context;
import android.util.Log;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.ContaCategoria;
import com.br.ipad.isc.excecoes.ControladorException;
import com.br.ipad.isc.excecoes.RepositorioException;
import com.br.ipad.isc.repositorios.RepositorioContaCategoria;
import com.br.ipad.isc.util.ConstantesSistema;

public class ControladorContaCategoria  extends ControladorBasico implements IControladorContaCategoria {
	
	private static ControladorContaCategoria instance;
	private RepositorioContaCategoria repositorioContaCategoria;
	protected static Context context;
	
	public void resetarInstancia(){
		instance = null;
	}
	
	protected ControladorContaCategoria(){
		super();
	}
	
	public static ControladorContaCategoria getInstance(){
		if ( instance == null ){
			instance =  new ControladorContaCategoria();
			instance.repositorioContaCategoria = RepositorioContaCategoria.getInstance();
		}
		
		return instance;		
	}

	public void setContext(Context ctx ) {
		context = ctx;		
	}	

	public ContaCategoria buscarContaCategoriaPorCategoriaSubcategoriaId(Integer categoriaSubcategoriaId, Integer tipoMedicao) 
			throws ControladorException {
		try {
			return repositorioContaCategoria.buscarContaCategoriaPorCategoriaSubcategoriaId(categoriaSubcategoriaId, tipoMedicao);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}

	public Double obterValorTotal(Integer imovelId, Integer tipoMedicao) throws ControladorException {
		try {
			return repositorioContaCategoria.obterValorTotal(imovelId, tipoMedicao);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}
	
	public void removerImovelContaCategoria(Integer idImovel,int tipoLigacao) throws ControladorException {
		try {
			repositorioContaCategoria.removerImovelContaCategoria(idImovel,tipoLigacao);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}
}