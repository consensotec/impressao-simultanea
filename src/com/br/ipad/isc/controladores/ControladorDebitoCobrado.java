
package com.br.ipad.isc.controladores;

import java.util.Collection;

import android.content.Context;
import android.util.Log;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.DebitoCobrado;
import com.br.ipad.isc.excecoes.ControladorException;
import com.br.ipad.isc.excecoes.RepositorioException;
import com.br.ipad.isc.repositorios.RepositorioDebitoCobrado;
import com.br.ipad.isc.util.ConstantesSistema;

public class ControladorDebitoCobrado  extends ControladorBasico implements IControladorDebitoCobrado{
	
	private static ControladorDebitoCobrado instance;
	private RepositorioDebitoCobrado repositorioDebitoCobrado;
	protected static Context context;
	
	public void resetarInstancia(){
		instance = null;
	}
	
	protected ControladorDebitoCobrado(){
		super();
	}
	
	public static ControladorDebitoCobrado getInstance(){
		if ( instance == null ){
			instance =  new ControladorDebitoCobrado();
			instance.repositorioDebitoCobrado = RepositorioDebitoCobrado.getInstance();
		}
		
		return instance;		
	}

	public void setContext(Context ctx ) {
		context = ctx;		
	}	

	public Collection<DebitoCobrado> buscarDebitoCobradoPorImovelId(Integer imovelId) throws ControladorException {
		try {
			return repositorioDebitoCobrado.buscarDebitoCobradoPorImovelId(imovelId);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}		

	public DebitoCobrado buscarDebitoCobradoPorCodigo(Integer debitoCodigo,Integer imovelId) throws ControladorException {
		try {
			return repositorioDebitoCobrado.buscarDebitoCobradoPorCodigo(debitoCodigo,imovelId);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}
	
	public Double obterValorDebitoTotal(Integer imovelId) throws ControladorException{
		try {
			return repositorioDebitoCobrado.obterValorDebitoTotal(imovelId);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}
	
	public Integer obterQntDebitoCobradoPorImovelId(Integer imovelId) throws ControladorException{
		try {
			return repositorioDebitoCobrado.obterQntDebitoCobradoPorImovelId(imovelId);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}
}