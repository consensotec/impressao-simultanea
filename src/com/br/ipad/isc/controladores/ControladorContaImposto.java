
package com.br.ipad.isc.controladores;

import java.util.Collection;

import android.content.Context;
import android.util.Log;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.ContaImposto;
import com.br.ipad.isc.excecoes.ControladorException;
import com.br.ipad.isc.excecoes.RepositorioException;
import com.br.ipad.isc.repositorios.RepositorioContaImposto;
import com.br.ipad.isc.util.ConstantesSistema;
import com.br.ipad.isc.util.Util;

public class ControladorContaImposto   extends ControladorBasico implements IControladorContaImposto{
	
	private static ControladorContaImposto instance;
	private RepositorioContaImposto repositorioContaImposto;
	protected static Context context;
	
	public void resetarInstancia(){
		instance = null;
	}
	
	protected ControladorContaImposto(){
		super();
	}
	
	public static ControladorContaImposto getInstance(){
		if ( instance == null ){
			instance =  new ControladorContaImposto();
			instance.repositorioContaImposto = RepositorioContaImposto.getInstance();			
		}		
		return instance;		
	}

	public void setContext(Context ctx ) {
		context = ctx;		
	}	

	public Collection<ContaImposto> buscarContaImpostoPorImovelId(Integer imovelId) throws ControladorException {
		try {
			return repositorioContaImposto.buscarContaImpostoPorImovelId(imovelId);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}
	
	public Integer obterQntContaImpostoPorImovelId(Integer imovelId) throws ControladorException {
		try {
			return repositorioContaImposto.obterQntContaImpostoPorImovelId(imovelId);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}
	
	public Double obterValorImpostoTotal(Integer imovelId) throws ControladorException {
		
		Double soma = 0d;
		Collection<ContaImposto> colecao = buscarContaImpostoPorImovelId(imovelId);
		
		double valorImposto = 0;
		
		if (colecao!=null){
			
			double percentualAlicotaTotal = 0; 
			
			for (ContaImposto imposto : colecao){
				percentualAlicotaTotal += imposto.getPercentualAlicota().doubleValue();
			}
			
			valorImposto = Util.arredondar(
					getControladorImovelConta().obterValorContaSemImposto(imovelId)
					* Util.arredondar((percentualAlicotaTotal / 100), 7), 2);
		}
		return Util.truncar(valorImposto, 2);			
	}	
		
}