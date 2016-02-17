
package com.br.ipad.isc.controladores;

import java.util.Collection;

import android.content.Context;
import android.util.Log;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.CreditoRealizado;
import com.br.ipad.isc.excecoes.ControladorException;
import com.br.ipad.isc.excecoes.RepositorioException;
import com.br.ipad.isc.repositorios.RepositorioCreditoRealizado;
import com.br.ipad.isc.util.ConstantesSistema;

public class ControladorCreditoRealizado  extends ControladorBasico implements IControladorCreditoRealizado{
	
	private static ControladorCreditoRealizado instance;
	private RepositorioCreditoRealizado repositorioCreditoRealizado;
	protected static Context context;
	
	public void resetarInstancia(){
		instance = null;
	}
	
	protected ControladorCreditoRealizado(){
		super();
	}
	
	public static ControladorCreditoRealizado getInstance(){
		if ( instance == null ){
			instance =  new ControladorCreditoRealizado();
			instance.repositorioCreditoRealizado = RepositorioCreditoRealizado.getInstance();
		}
		
		return instance;		
	}

	public void setContext(Context ctx ) {
		context = ctx;		
	}	

	public Collection<CreditoRealizado> buscarCreditoRealizadoPorImovelId(Integer imovelId) throws ControladorException {
		try {
			return repositorioCreditoRealizado.buscarCreditoRealizadoPorImovelId(imovelId);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}

	public Double obterValorCreditoTotal(Integer imovelId) throws ControladorException {
		try {
			return repositorioCreditoRealizado.obterValorCreditoTotal(imovelId);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}
	
	public CreditoRealizado buscarCreditoRealizadoPorDescricao(String descricaoCredito,Integer imovelId) throws ControladorException {
		try {
			return repositorioCreditoRealizado.buscarCreditoRealizadoPorDescricao(descricaoCredito,imovelId);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}
	
	public Integer obterQntCreditoRealizadoPorImovelId(Integer imovelId) throws ControladorException {
		try {
			return repositorioCreditoRealizado.obterQntCreditoRealizadoPorImovelId(imovelId);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}

}
