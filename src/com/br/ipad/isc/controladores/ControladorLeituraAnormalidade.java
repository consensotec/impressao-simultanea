
package com.br.ipad.isc.controladores;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.LeituraAnormalidade;
import com.br.ipad.isc.excecoes.ControladorException;
import com.br.ipad.isc.excecoes.RepositorioException;
import com.br.ipad.isc.repositorios.RepositorioLeituraAnormalidade;
import com.br.ipad.isc.util.ConstantesSistema;

public class ControladorLeituraAnormalidade  extends ControladorBasico implements IControladorLeituraAnormalidade{
	
	private static ControladorLeituraAnormalidade instance;
	private RepositorioLeituraAnormalidade repositorioLeituraAnormalidade;
	protected static Context context;
	
	public void resetarInstancia(){
		instance = null;
	}
	
	protected ControladorLeituraAnormalidade(){
		super();
	}
	
	public static ControladorLeituraAnormalidade getInstance(){
		if ( instance == null ){
			instance =  new ControladorLeituraAnormalidade();
			instance.repositorioLeituraAnormalidade = RepositorioLeituraAnormalidade.getInstance();
		}
		
		return instance;		
	}

	public void setContext(Context ctx ) {
		context = ctx;		
	}	

	public LeituraAnormalidade buscarLeituraAnormalidadePorIdComUsoAtivo(Integer id) throws ControladorException {
		try {
			return repositorioLeituraAnormalidade.buscarLeituraAnormalidadePorIdComUsoAtivo(id);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}		
	}

	@Override
	public ArrayList<LeituraAnormalidade> buscarLeiturasAnormalidadesComUsoAtivo()
			throws ControladorException {
		try {
			return repositorioLeituraAnormalidade.buscarLeiturasAnormalidadesComUsoAtivo();
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}		
	}

	public LeituraAnormalidade buscarLeituraAnormalidadeImovel(Integer idImovel,Integer tipoLigacao) throws ControladorException {
		try {
			return repositorioLeituraAnormalidade.buscarLeituraAnormalidadeImovel(idImovel,tipoLigacao);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}		
	}
	
}