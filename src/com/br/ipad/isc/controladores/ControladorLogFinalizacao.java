
package com.br.ipad.isc.controladores;

import android.content.Context;
import android.util.Log;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.LogFinalizacao;
import com.br.ipad.isc.excecoes.ControladorException;
import com.br.ipad.isc.excecoes.RepositorioException;
import com.br.ipad.isc.repositorios.RepositorioBasico;
import com.br.ipad.isc.util.ConstantesSistema;
import com.br.ipad.isc.util.Util;

public class ControladorLogFinalizacao  extends ControladorBasico implements IControladorLogFinalizacao{
	
	private static ControladorLogFinalizacao instance;
	protected static Context context;
	
	public void resetarInstancia(){
		instance = null;
	}
	
	protected ControladorLogFinalizacao(){
		super();
	}
	
	public static ControladorLogFinalizacao getInstance(){
		if ( instance == null ){
			instance =  new ControladorLogFinalizacao();
		}		
		return instance;		
	}

	public void setContext(Context ctx ) {
		context = ctx;		
	}	
	
	public void inserir(String msgTipoFinalizacao) throws ControladorException {
		try {
			
			LogFinalizacao logFinalizacao = new LogFinalizacao();
	    	logFinalizacao.setCodigoMensagemFinalizacao(msgTipoFinalizacao);
	    	logFinalizacao.setDataEnvio(Util.dataAtual());
	    	RepositorioBasico.getInstance().inserir(logFinalizacao);
	    	
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}
}