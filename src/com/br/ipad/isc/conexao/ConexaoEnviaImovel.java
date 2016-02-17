package com.br.ipad.isc.conexao;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.controladores.ControladorAlertaValidarErro;
import com.br.ipad.isc.controladores.ControladorFoto;
import com.br.ipad.isc.controladores.ControladorImovelConta;
import com.br.ipad.isc.excecoes.ControladorException;
import com.br.ipad.isc.util.ConstantesSistema;

public class ConexaoEnviaImovel extends AsyncTask<Object, Integer, Boolean> {

	private Context contexto;
    private ImovelConta imovel;
	
	/**
     * Prepare activity before upload
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * Clean app state after upload is completed
     */
    @Override
    protected void onPostExecute(Boolean retorno) {
        super.onPostExecute(retorno);
                
		String msgErro = ConexaoWebServer.getMensagemError();
		
		if(msgErro!=null){
		
			ControladorAlertaValidarErro controladorAlerta = new ControladorAlertaValidarErro();
			controladorAlerta.defineAlerta(ConstantesSistema.ALERTA_MENSAGEM, msgErro, 1);
						
		}

    }

    @Override
    protected Boolean doInBackground(Object... params) {
    	imovel = (ImovelConta) params[0];
    	contexto = (Context) params[1];
    	boolean retorno = false;
    	try {
			if(ControladorImovelConta.getInstance().enviarAoCalcular(imovel)){
					ComunicacaoWebServer.getInstancia().setContext(contexto);
					retorno = ComunicacaoWebServer.enviaCalculado(imovel,contexto);
					
			}
						
			ControladorFoto.getInstance().enviarFotosOnline(imovel);
			
		} catch (ControladorException e) {
			Log.e(ConstantesSistema.CATEGORIA, e.getMessage());
			e.printStackTrace();
		}
    	
		return retorno;
    	
    
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    
}
