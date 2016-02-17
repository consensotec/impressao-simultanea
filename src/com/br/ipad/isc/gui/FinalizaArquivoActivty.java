
package com.br.ipad.isc.gui;

import java.util.ArrayList;
import java.util.Date;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.bean.SistemaParametros;
import com.br.ipad.isc.conexao.ConexaoWebServer;
import com.br.ipad.isc.controladores.ControladorAlertaValidarMensagemConexao;
import com.br.ipad.isc.fachada.Fachada;
import com.br.ipad.isc.fachada.FachadaWebServer;
import com.br.ipad.isc.io.ArquivoRetorno;
import com.br.ipad.isc.util.ConstantesSistema;
import com.br.ipad.isc.util.Util;

public class FinalizaArquivoActivty extends BaseActivity implements OnClickListener{
	
	private short tipoFinalizacao = 0;;
		
	private ArquivoRetorno arquivoRetorno;
	
	private ArrayList<Integer> imoveisImprimir;
	
	private int valorProgresso = 0;
		
	private static Object[] retorno;
	
	private int posicaoImovelNaoImpresso = -1;
	
	private int progress = 0;
	
	private int total;
	
	private String nomeArquivo;
	
	private Fachada fachada  = Fachada.getInstance();
	
	private boolean perguntaImprimir;
	
	// Classe que irá controlador todo o processo
	private class FinalizaArquivoControlador extends AsyncTask<Object, Integer, Integer>{

		private ProgressBar prb;
		protected boolean abort = false;
					
				
	    /**
	     * Prepare activity before upload
	     */
	    
	    protected void onPreExecute() {
	        super.onPreExecute();
	        /* Caso seja sistema de leitura, não será exibida a pergunta 
 			se deseja imprimir o imóvel
 		 	RM8368 UC1303 */
     		if(SistemaParametros.getInstancia().getIndicadorSistemaLeitura().equals(ConstantesSistema.SIM)){
     			perguntaImprimir = false;
     		}else{
     			verificaSePerguntaImprimir();
     		}
     		
        	arquivoRetorno = new ArquivoRetorno();
        	arquivoRetorno.setArrayListImovelConta(tipoFinalizacao);
	        
	    }		
		
	    /**
	     * Valida de o alert de pergunta de impressao do imóvel deve ser exibido
	     */
		private void verificaSePerguntaImprimir() {

			String loginActivity = getIntent().getStringExtra("login");
     		if(tipoFinalizacao != ArquivoRetorno.ARQUIVO_INCOMPLETO && 
	         			tipoFinalizacao != ArquivoRetorno.ARQUIVO_LIDOS_ATE_AGORA && 
	         			(loginActivity == null || loginActivity.equals(""))){
     			
     			perguntaImprimir = true;
     		}
		}

		@Override
		protected Integer doInBackground(Object... params) {
			prb = (ProgressBar) params[0];
			tipoFinalizacao = (Short) params[1];
			valorProgresso = (Integer) params[2];
						
			int sucesso = ConstantesSistema.ERRO_GENERICO; 
			
			try{	
				
				// Seta a barra de progresso para a posição que estava, caso houve alerta de imóvel não impresso
				if( valorProgresso != 0){
					publishProgress(valorProgresso);
				}

				prb.setMax(100);
				
				int posicao = 0;
				if(posicaoImovelNaoImpresso != -1){
					posicao = posicaoImovelNaoImpresso;
				}
				
				sucesso = verificarImoveisNaoImpressos(posicao);
				if(sucesso != ConstantesSistema.OK){
					return sucesso;
				}
				
				//Controlando o valorProgresso por percentuais
				total = arquivoRetorno.getTotalImoveis();
				
				
				//Apenas finaliza o arquivo, não há imóvel calculado
				if(total == 0 && ( tipoFinalizacao == ArquivoRetorno.ARQUIVO_INCOMPLETO || tipoFinalizacao == ArquivoRetorno.ARQUIVO_COMPLETO ) ){
					retorno = gerar(0,progress, false);		
										
					publishProgress(90);
				}				
		        									
				//Gera arquivo imóvel a imóvel
				for(int i = 0; i<total; i++){
					
					// Soma mais um para ter o tempo de envio, alem da geracao do arquivo
					progress = (int) (((double) (i+1)/(total+1)) * 100);
										
					retorno = gerar(i,progress, false);
					
					if ( abort ){
						return ConexaoWebServer.ABORTADO;
					}					
					publishProgress(progress);	
					
					if(retorno!= null && retorno[0] != null && retorno[0]== Boolean.TRUE){
						break;
					}
											
					
					
				}

				//Envia caso não houve erro
				if(retorno!= null && retorno[0] != null && retorno[0]!= Boolean.TRUE){
					nomeArquivo =((String) retorno[2]); 
									
					if(tipoFinalizacao != ArquivoRetorno.ARQUIVO_LIDOS_ATE_AGORA){
						Util.escreverArquivoTexto(nomeArquivo, ArquivoRetorno.montaArquivo.toString(), ConstantesSistema.CAMINHO_RETORNO);
						
						//Apagar arquivos de backup
						Util.apagarArquivos(ConstantesSistema.CAMINHO_BACKUP);
						Util.escreverArquivoTexto(Util.dataFormatada(new Date())+"_"+nomeArquivo, ArquivoRetorno.montaArquivo.toString(), ConstantesSistema.CAMINHO_BACKUP);
					}
					
					sucesso = FachadaWebServer.getInstancia().enviarDados(nomeArquivo,tipoFinalizacao,FinalizaArquivoActivty.this,ArquivoRetorno.montaArquivo);
				
					publishProgress(100);	
					
					// reseta para não ocorrer duplicação de registros
					ArquivoRetorno.montaArquivo = new StringBuilder();

	    			retorno = null;
					
				}else{
					sucesso = ConexaoWebServer.ERRO_GERACAO_ARQUIVO;
				}
				
				if ( abort ){
					ArquivoRetorno.montaArquivo = new StringBuilder();
					return ConstantesSistema.ERRO_REQUISICAO_ABORTAR;
				}

			} catch ( Exception e ){
				ArquivoRetorno.montaArquivo = new StringBuilder();
				Log.e( ConstantesSistema.CATEGORIA , e.getMessage() );
				e.printStackTrace();
				sucesso = ConexaoWebServer.ERRO_GENERICO;
			}
							
			return sucesso;
			
		}
		
		/**
		 * Exibe alerta caso exista algum imovel nao enviado nao impresso
		 * @param posicao
		 */
	    private int verificarImoveisNaoImpressos(int posicao) {

			int sucesso;
	    	sucesso = ConstantesSistema.OK;
	    	
	    	if( perguntaImprimir ){
		    	if (imoveisImprimir == null){
		    		imoveisImprimir = fachada.buscarIdsImoveisLidosNaoImpressos();
		    	}
				// Se não já tiver passado pelo último imovel nao impresso
				if(imoveisImprimir != null && imoveisImprimir.size() > 0 && posicao != imoveisImprimir.size()){
					
					posicaoImovelNaoImpresso = posicao++;
					sucesso = ConexaoWebServer.ERRO_GERACAO_ARQUIVO;
				}		
	    	}
	    	return sucesso;
		}

		@Override
		protected void onPostExecute(Integer result) {
	    	
	    	ControladorAlertaValidarMensagemConexao controladorAlerta = null;
	    	String msgTipoFinalizacao = null;
	    		    	
	    	boolean enviou = false;
	    	switch (result){	    	
	    	
	    		case ConexaoWebServer.ERRO_GENERICO:
	    			msgTipoFinalizacao = getString(R.string.str_erro_generico_envio);
	    			break;    			
	    		case ConexaoWebServer.ERRO_ABORTAR:
	    			msgTipoFinalizacao = getString(R.string.str_erro_aborted);
	    			break;
	    		case ConexaoWebServer.ERRO_GERACAO_ARQUIVO:
	    			msgTipoFinalizacao = getString(R.string.str_erro_retorno);
	    			break;
	    		case ConstantesSistema.ERRO_SERVIDOR_OFF_LINE:
	    			msgTipoFinalizacao = getString(R.string.str_erro_off);
	    			break;	
	    		case ConexaoWebServer.ERRO_SINAL_FINALIZACAO:
	    			msgTipoFinalizacao = getString(R.string.str_erro_finalizacao);
	    			break;
	    		case ConstantesSistema.OK:

					enviou = true;
	    			
	    			if (tipoFinalizacao == ArquivoRetorno.ARQUIVO_INCOMPLETO || tipoFinalizacao == ArquivoRetorno.ARQUIVO_COMPLETO
	    					|| tipoFinalizacao == ArquivoRetorno.ARQUIVO_TODOS_OS_CALCULADOS) {
	    			
	    				msgTipoFinalizacao = getString(R.string.str_finalizacao_alert);
	    			
	    			// Caso lidos ate agora	
	    			}else{
	    				msgTipoFinalizacao = getString(R.string.str_enviados_alert);
	    			}
	    			break;	
	    		default:
		    		msgTipoFinalizacao = getString(R.string.str_erro_generico_envio_default);
	    			
                	
	    	}
	    	
	    	
    		if ( msgTipoFinalizacao != null ){

    			//Insere data e mensagem recebida do gsan
    	    	fachada.insereLogFinalizacao(msgTipoFinalizacao);
    			
    			String msg = msgTipoFinalizacao;
    			String msgErroGsan = FachadaWebServer.getMensagemError();
    			
	         	if (msgErroGsan != null){
	         		enviou = false;
	         		
	         		if(!msgErroGsan.equals("")){
	         			msg = msgErroGsan;
	         			
	         			if(msgErroGsan.equals(getString(R.string.str_erro_quantidade_diferente)) 
	         					&& tipoFinalizacao == ArquivoRetorno.ARQUIVO_COMPLETO){
	         				msg = msgErroGsan + ". "+getString(R.string.str_encaminhar_arquivo_completo);
	         			}
	         			
	         			if(msgErroGsan.equals(getString(R.string.str_erro_quantidade_diferente)) 
	         					&& tipoFinalizacao == ArquivoRetorno.ARQUIVO_TODOS_OS_CALCULADOS){
	         				msg = msgErroGsan + ". "+getString(R.string.str_finalizar_completo_erro);
	         			}
	         			
	         		}
	         		
	         	}
	         	
	         	if(retorno != null){
	         		nomeArquivo =((String) retorno[2]); 
	         	}
	         	
	         			        
		     // Caso existe algum imóvel não impresso
	         	if(perguntaImprimir && imoveisImprimir!= null && imoveisImprimir.size()>0 && posicaoImovelNaoImpresso != imoveisImprimir.size() && 
	         			posicaoImovelNaoImpresso != -1 &&
	         			tipoFinalizacao != ArquivoRetorno.ARQUIVO_INCOMPLETO && 
	         			tipoFinalizacao != ArquivoRetorno.ARQUIVO_LIDOS_ATE_AGORA){
	         			         			         		
	         		// Pega o imóvel não impresso
	         		Integer indice = imoveisImprimir.get(posicaoImovelNaoImpresso);
	         		
	    	        ImovelConta imovel = 
	    	        		(ImovelConta)fachada.pesquisarPorId(indice, new ImovelConta());	        	        		   	    	        	         		
	         		
	         		controladorAlerta = new ControladorAlertaValidarMensagemConexao(imovel,nomeArquivo,ConstantesSistema.LISTA,(byte) 0,false,progress,posicaoImovelNaoImpresso,tipoFinalizacao);
	         		
	    			controladorAlerta.defineAlerta(ConstantesSistema.ALERTA_PERGUNTA,getString(R.string.str_erro_nao_impresso,indice),1);	         		

	         	}else if (tipoFinalizacao == ArquivoRetorno.ARQUIVO_INCOMPLETO || tipoFinalizacao == ArquivoRetorno.ARQUIVO_COMPLETO
    					|| tipoFinalizacao == ArquivoRetorno.ARQUIVO_TODOS_OS_CALCULADOS) {
    			
    				controladorAlerta = new ControladorAlertaValidarMensagemConexao(null,ConstantesSistema.SAIR_APLICACAO,ConstantesSistema.MENU_PRINCIPAL,enviou,tipoFinalizacao,nomeArquivo,msgErroGsan );
    				controladorAlerta.defineAlerta(ConstantesSistema.ALERTA_MENSAGEM,msg,1);
    				ArquivoRetorno.montaArquivo = new StringBuilder();
 
    			
	         	}else{
    				controladorAlerta = new ControladorAlertaValidarMensagemConexao(null,ConstantesSistema.MENU_PRINCIPAL,ConstantesSistema.MENU_PRINCIPAL,enviou,tipoFinalizacao,nomeArquivo,msgErroGsan);
    				controladorAlerta.defineAlerta(ConstantesSistema.ALERTA_MENSAGEM,msg,1);
    				ArquivoRetorno.montaArquivo = new StringBuilder();
    			}		           
	         		        	
    		}
	    }
		
		@Override
	    protected void onProgressUpdate(Integer... values) {
	        super.onProgressUpdate(values);
	        
	        int valorProgresso = values[0];
						
			prb.setProgress(valorProgresso);	
	    }		
	}
	
	private Button btnCancel;
	
	private FinalizaArquivoControlador fac;
		
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finalizar_arquivo_activity);
        
        Fachada.setContext( this );
                      
        String tipo = getIntent().getStringExtra(getString(R.string.str_extra_tipo_finalizacao));
        if(tipo != null){
        	tipoFinalizacao = new Short(tipo);
        }
        
        if(getIntent().getStringExtra(getString(R.string.str_extra_progresso)) != null){
        	
        	valorProgresso  = new Integer(getIntent().getStringExtra(getString(R.string.str_extra_progresso)));
        	
        }
                 
        posicaoImovelNaoImpresso = (int) getIntent().getIntExtra("posicao", -1);
        start();        
        
    }

	public Object[] gerar(int posicao, int progress, boolean continua) {
		Object[] retorno = new Object[1];		
			
    	retorno = FachadaWebServer.getInstancia() 
    			.comunicacao(tipoFinalizacao,arquivoRetorno,posicao,continua);	    	
    	
    	boolean houveErro = false;
    	if(retorno != null){
    		
    		houveErro = (Boolean) retorno[0];
    		
    		if(houveErro){
    			retorno[0] = houveErro;
    		}
    	}
	    	
		    
		return retorno;
	}

	public void onClick(View arg0) {
		
		if ( arg0 == btnCancel ){
			// Cancelamos a operação
			fac.abort = true;
		}
		
	}
	
	private void start() {
		ProgressBar barraProgresso = (ProgressBar) findViewById(R.id.progress);
		barraProgresso.setIndeterminate(false);
		fac = new FinalizaArquivoControlador();
		fac.execute( barraProgresso,tipoFinalizacao,valorProgresso );		
	}
	
	@Override
	protected void onDestroy() {
		
		super.onDestroy();
//		
	}
	
	//Bloqueia o botao home
	@Override
	public void onAttachedToWindow()
	{  
		this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);     
		super.onAttachedToWindow();  
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		 if(keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
	           
	     }
		 
		 return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public void onBackPressed() { 
		//Faz nada
	}
}