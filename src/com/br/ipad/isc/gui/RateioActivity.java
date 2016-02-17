package com.br.ipad.isc.gui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.bean.SistemaParametros;
import com.br.ipad.isc.controladores.ControladorAlertaValidarConexaoImpressora;
import com.br.ipad.isc.controladores.ControladorRateioImovelCondominio;
import com.br.ipad.isc.excecoes.ConexaoImpressoraException;
import com.br.ipad.isc.excecoes.ControladorException;
import com.br.ipad.isc.excecoes.StatusImpressoraException;
import com.br.ipad.isc.excecoes.ZebraException;
import com.br.ipad.isc.fachada.Fachada;
import com.br.ipad.isc.util.ConstantesSistema;

/**
 * Activity que irá gerenciar o processo 
 * de rateio do imovel condominio
 * 
 * @author Amelia Pessoa
 * @date 28/08/2012
 */
public class RateioActivity extends BaseActivity implements OnClickListener{

	private ImovelConta imovelMacro = null;
	private ProgressBar prb;
	protected boolean abort = false;
	private RouteRateioControl rdc;
	
	private final int SUCESSO = 100;
	//Erros
	private final int ABORTADO = 1;
	private final int CONEXAO_IMPRESSORA_EXCEPTION = 2;
	private final int STATUS_IMPRESSORA_EXCEPTION = 3;
	private final int ZEBRA_EXCEPTION = 4;
	private final int EXCEPTION = 5;
	private final int IMPRESSAO_INICIADA = 6;
	
	private String erro = null;
	
	private ControladorRateioImovelCondominio contrRateio;
	private String validado = null;
	private boolean completo;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download_arquivo_activity);
        Fachada.setContext( this );
        
        //Recupera parametros
        imovelMacro = (ImovelConta) getIntent().getSerializableExtra("macro"); 
        
        boolean impressoraOk = false;
        //Verifica impressora
        if( SistemaParametros.getInstancia().getIndicadorSistemaLeitura().equals(ConstantesSistema.SIM) ){
        	impressoraOk = true;
        }else{
        	impressoraOk = 
        		Fachada.getInstance().verificarExistenciaImpressora(imovelMacro);
        }
        
        Button btnSair = (Button) findViewById(R.id.btnSair);
        btnSair.setVisibility(View.INVISIBLE);
        
        if (impressoraOk){
        	
        	Button btnCancel = (Button) findViewById(R.id.btnCancelDA);
	        btnCancel.setVisibility(View.INVISIBLE);
	        btnCancel.setOnClickListener( this );
	        
	        TextView text = (TextView) findViewById(R.id.textViewProgress);
	        text.setText("Realizando o rateio. Por favor, aguarde...");
	        	        
	        start();  
        }
    }
    
	private void start() {
		ProgressBar progress = (ProgressBar) findViewById(R.id.progressPorLinha);
		progress.setIndeterminate(false);
		rdc = new RouteRateioControl();
		rdc.execute( progress );		
	}

	@Override
    public void onBackPressed() { 
		//Faz nada
	}
	
	public void onClick(View arg0) {}
	
	// Classe que irá controlar todo o processo
	private class RouteRateioControl extends AsyncTask<ProgressBar, Integer, Integer>{

		private ControladorAlertaValidarConexaoImpressora controladorAlertaValidarConexaoImpressora = null;
		
		public int realizaRateio() {
			try {
				int sucess = SUCESSO; 
				
				//Controlando o progresso por percentuais				
				int progress = 0;
				prb.setMax(contrRateio.obterTotal());
								
				while (contrRateio.obterTotal()!=0){
					
					progress++;
					
					try {				
						
						contrRateio.proximoRateio();						
					} catch (ConexaoImpressoraException e) { //Erro de conexao, mostra msg e fica na mesma tela
						erro = e.getMessage();
						return CONEXAO_IMPRESSORA_EXCEPTION;
					} 
					catch (StatusImpressoraException e) {
						erro = e.getMessage();
						return STATUS_IMPRESSORA_EXCEPTION;
					}
					catch (ZebraException e) {
						erro = e.getMessage();
						return ZEBRA_EXCEPTION;
					}
					catch (Exception e) {
						erro = e.getMessage();
						return EXCEPTION;
					}
					
					publishProgress(progress);
					
					if ( abort ){
						return ABORTADO;
					}								
				}
				
				//Concluir rateio
				contrRateio.concluirRateio();
				publishProgress(progress);
				
				return sucess;
			} catch (ControladorException e) {
				e.printStackTrace();
				return EXCEPTION;
			}
	    }		
		
	    /**
	     * Prepare activity before upload
	     */
	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	    }		
		
		@Override
		protected Integer doInBackground(ProgressBar... params) {
			prb = params[0];			
			
			try {
				//Verifica a impressao para efetuar o rateio se não foi leitura	
				
				if (verificaImpressao()){
					
					return realizaRateio();
				} else {
					return IMPRESSAO_INICIADA;
				}
			} catch(Exception ex){
				return EXCEPTION;
			}
		}
		
		/**
		 * Verifica se alguma conta daquele imovel macro jah foi impressa, 
		 * se sim pergunta ao usuário se ele quer imprimir tudo 
		 * ou apenas imoveis nao impressos
		 * @throws ControladorException 
		 */
		private boolean verificaImpressao() {
			try {
				if (validado!=null && validado.equals("ok")){
					//Chama efetuar rateio 
					contrRateio = Fachada.getInstance().efetuarRateio(imovelMacro, completo);
					return true;
				} else if (!Fachada.getInstance().existeImovelImpresso(imovelMacro.getId())){
					//Chama efetuar rateio completo
					contrRateio = Fachada.getInstance().efetuarRateio(imovelMacro, true);
					return true;
				}else if(Fachada.getInstance().obterIndicadorPermiteContinuarImpressao(imovelMacro.getId()).equals(ConstantesSistema.NAO)){
					//Chama efetuar rateio completo
					contrRateio = Fachada.getInstance().efetuarRateio(imovelMacro, true);
					return true;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			
			return false;
		}
		
	    @Override
	    protected void onPostExecute(Integer sucess) {
	    	
	    	if (sucess.intValue()==CONEXAO_IMPRESSORA_EXCEPTION){ //Msg de erro e volta para tela do ultimo imovel
	    		Fachada.getInstance().atualizarIndicadorContinuaImpressao(imovelMacro.getId(), ConstantesSistema.SIM);
	    		controladorAlertaValidarConexaoImpressora = new ControladorAlertaValidarConexaoImpressora(imovelMacro);
				controladorAlertaValidarConexaoImpressora.defineAlerta(ConstantesSistema.ALERTA_MENSAGEM, erro, R.string.falha_conecao);
	    	} else if (sucess.intValue()==STATUS_IMPRESSORA_EXCEPTION){
	    		Fachada.getInstance().atualizarIndicadorContinuaImpressao(imovelMacro.getId(), ConstantesSistema.SIM);
	    		controladorAlertaValidarConexaoImpressora = new ControladorAlertaValidarConexaoImpressora(imovelMacro);
				controladorAlertaValidarConexaoImpressora.defineAlerta(ConstantesSistema.ALERTA_PERGUNTA, getString(R.string.str_erro_impressora, erro), 0);
	    	} else if (sucess.intValue()==ZEBRA_EXCEPTION){
	    		Fachada.getInstance().atualizarIndicadorContinuaImpressao(imovelMacro.getId(), ConstantesSistema.SIM);
	    		controladorAlertaValidarConexaoImpressora = new ControladorAlertaValidarConexaoImpressora(imovelMacro);
				controladorAlertaValidarConexaoImpressora.defineAlerta(ConstantesSistema.ALERTA_MENSAGEM,  getString(R.string.falha_conecao), R.string.falha_conecao);
	    	} else if (sucess.intValue()==EXCEPTION){
	    		Fachada.getInstance().atualizarIndicadorContinuaImpressao(imovelMacro.getId(), ConstantesSistema.SIM);
	    		controladorAlertaValidarConexaoImpressora = new ControladorAlertaValidarConexaoImpressora(imovelMacro);
				controladorAlertaValidarConexaoImpressora.defineAlerta(ConstantesSistema.ALERTA_MENSAGEM,  getString(R.string.str_impressao_erro), R.string.str_impressao_erro);
	    	} else if (sucess.intValue()==IMPRESSAO_INICIADA){
	    		String msg = "Existem imóveis já impressos nesse condomínio.\nDeseja continuar a impressao?";				
				 new AlertDialog.Builder(RateioActivity.this)
					.setMessage(msg)
					.setPositiveButton("Continuar de onde parou", 
						new DialogInterface.OnClickListener() {
							public void onClick(
								DialogInterface dialog,
								int which) {
								//Guardar parametros 'completo' e 'validado' e chamar o Rateio novamente
								completo = false;
								validado = "ok";
					    		start();																	
							}
					})
					.setCancelable(false)
					.setNegativeButton("Imprimir tudo novamente", 
						new DialogInterface.OnClickListener() {
							public void onClick(
								DialogInterface dialog,
								int which) {
								//Guardar parametros 'completo' e 'validado' e chamar o Rateio novamente
								completo = true;
								validado = "ok";
					    		start();				
							}
					})
					.show();
	    	} else {
	    		//Direciona para proximo imovel
	    		Integer idUltimo = Fachada.getInstance().obterIdUltimoImovelMicro(imovelMacro.getId());
	    		ImovelConta ultimo = (ImovelConta) Fachada.getInstance()
						.pesquisarPorId(idUltimo, new ImovelConta()); 
	    				
	    		irTelaHidrometro(ultimo.getPosicao()+1);	    		
	    	}
	    }
		
	    @Override
	    protected void onProgressUpdate(Integer... values) {
	    	prb.setProgress( values[0] );
	    }
	    
	    private void irTelaHidrometro(Integer posicao){
	    	ImovelConta imovelProximo = Fachada.getInstance().buscarImovelContaPorPosicao(posicao);
	    	if(imovelProximo==null){
	    		imovelProximo = Fachada.getInstance().buscarImovelContaPorPosicao(1);
	    	}
    		Intent i = new Intent(RateioActivity.this,TabsActivity.class);
			i.putExtra("imovel", imovelProximo);
			finish();
			startActivity(i);
	    }
	}
	
}