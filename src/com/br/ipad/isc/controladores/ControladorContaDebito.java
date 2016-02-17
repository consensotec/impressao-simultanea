
package com.br.ipad.isc.controladores;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.ContaDebito;
import com.br.ipad.isc.excecoes.ControladorException;
import com.br.ipad.isc.excecoes.RepositorioException;
import com.br.ipad.isc.repositorios.RepositorioContaDebito;
import com.br.ipad.isc.util.ConstantesSistema;

public class ControladorContaDebito  extends ControladorBasico implements IControladorContaDebito{
	
	private static ControladorContaDebito instance;
	private RepositorioContaDebito repositorioContaDebito;
	protected static Context context;
	
	public void resetarInstancia(){
		instance = null;
	}
	
	protected ControladorContaDebito(){
		super();
	}
	
	public static ControladorContaDebito getInstance(){
		if ( instance == null ){
			instance =  new ControladorContaDebito();
			instance.repositorioContaDebito = RepositorioContaDebito.getInstance();
		}
		
		return instance;		
	}

	public void setContext(Context ctx ) {
		context = ctx;		
	}	

	public ArrayList<ContaDebito> buscarContasDebitosPorIdImovel(Integer idImovel) throws ControladorException {
		try {
			return repositorioContaDebito.buscarContasDebitosPorIdImovel(idImovel);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}		
	}

	
}