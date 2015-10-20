
package com.br.ipad.isc.controladores;

import android.content.Context;
import android.util.Log;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.QualidadeAgua;
import com.br.ipad.isc.excecoes.ControladorException;
import com.br.ipad.isc.excecoes.RepositorioException;
import com.br.ipad.isc.repositorios.RepositorioQualidadeAgua;
import com.br.ipad.isc.util.ConstantesSistema;

public class ControladorQualidadeAgua  extends ControladorBasico implements IControladorQualidadeAgua{
	
	private static ControladorQualidadeAgua instance;
	private RepositorioQualidadeAgua repositorioQualidadeAgua;
	protected static Context context;
	
	public void resetarInstancia(){
		instance = null;
	}
	
	protected ControladorQualidadeAgua(){
		super();
	}
	
	public static ControladorQualidadeAgua getInstance(){
		if ( instance == null ){
			instance =  new ControladorQualidadeAgua();
			instance.repositorioQualidadeAgua = RepositorioQualidadeAgua.getInstance();
		}
		
		return instance;		
	}

	public void setContext(Context ctx ) {
		context = ctx;		
	}	

	public QualidadeAgua buscarQualidadeAguaPorLocalidadeSetorComercial(Integer idLocalidade, Integer idSetorComercial) throws ControladorException {
		try {
			return repositorioQualidadeAgua.buscarQualidadeAguaPorLocalidadeSetorComercial(idLocalidade, idSetorComercial);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}		
	}
	
	public QualidadeAgua buscarQualidadeAguaPorLocalidade(Integer idLocalidade) throws ControladorException {
		try {
			return repositorioQualidadeAgua.buscarQualidadeAguaPorLocalidade(idLocalidade);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}		
	}
	
	public QualidadeAgua buscarQualidadeAguaSemLocalidade() throws ControladorException {
		try{
			return repositorioQualidadeAgua.buscarQualidadeAguaSemLocalidade();
		}catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}
	
}