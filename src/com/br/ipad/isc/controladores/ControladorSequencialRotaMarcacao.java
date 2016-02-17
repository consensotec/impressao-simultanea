
package com.br.ipad.isc.controladores;

import android.content.Context;
import android.util.Log;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.bean.SequencialRotaMarcacao;
import com.br.ipad.isc.bean.SistemaParametros;
import com.br.ipad.isc.excecoes.ControladorException;
import com.br.ipad.isc.excecoes.RepositorioException;
import com.br.ipad.isc.repositorios.RepositorioSequencialRotaMarcacao;
import com.br.ipad.isc.util.ConstantesSistema;

public class ControladorSequencialRotaMarcacao  extends ControladorBasico implements IControladorSequencialRotaMarcacao{
	
	private static ControladorSequencialRotaMarcacao instance;
	private RepositorioSequencialRotaMarcacao repositorioSequencialRotaMarcacao;
	protected static Context context;
	
	public void resetarInstancia(){
		instance = null;
	}
	
	protected ControladorSequencialRotaMarcacao(){
		super();
	}
	
	public static ControladorSequencialRotaMarcacao getInstance(){
		if ( instance == null ){
			instance =  new ControladorSequencialRotaMarcacao();
			instance.repositorioSequencialRotaMarcacao = RepositorioSequencialRotaMarcacao.getInstance();
		}		
		return instance;		
	}

	public void setContext(Context ctx ) {
		context = ctx;		
	}

	public SequencialRotaMarcacao buscarSequencialRotaMarcacao(Integer idImovel) throws ControladorException {
		
		try {
			return repositorioSequencialRotaMarcacao.buscarSequencialRotaMarcacao(idImovel);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}
	
	public void removerTodosSequencialRotaMarcacao() throws ControladorException {
		
		try {
			repositorioSequencialRotaMarcacao.removerTodosSequencialRotaMarcacao();
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
		
	}
	
	public void gravarSequencialRotaMarcacao(ImovelConta imovel) throws ControladorException {
		
		//Setar Sequencial Rota Marcacao 
		if(SistemaParametros.getInstancia().getIndicadorRotaMarcacaoAtiva().equals(ConstantesSistema.SIM)){
			SequencialRotaMarcacao sequencialRotaMarcacao = new SequencialRotaMarcacao();
			sequencialRotaMarcacao.setAnoMesReferencia(imovel.getAnoMesConta());
			sequencialRotaMarcacao.setMatricula(imovel);
			
			//Verifica Se ja existe sequencial rota para este imovel
			if(getControladorSequencialRotaMarcacao().buscarSequencialRotaMarcacao(imovel.getId()) == null){
				ControladorBasico.getInstance().inserir(sequencialRotaMarcacao);
			}			
		}		
	}
	
}