package com.br.ipad.isc.gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.Vector;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.br.ipad.isc.R;
import com.br.ipad.isc.fachada.Fachada;
import com.br.ipad.isc.fachada.FachadaWebServer;
import com.br.ipad.isc.io.ArquivoRetorno;
import com.br.ipad.isc.util.ConstantesSistema;
import com.br.ipad.isc.util.Util;

public class DownloadApkActivity extends BaseActivity {	

	//private static String VERSAO_BAIXADA = "";
	
	private static final int ERROR_ABORT_REQUESTED = 99;
	public static final int ERRO_VERSAO_INDISPONIVEL = 98;
	public static final int IGNORE_DOWNLOAD_VERSION = 90;
	private static final int ERRO_VERIFICANDO_VERSAO = 97;
	private static final int ERRO_DOWNLOAD_APK = 96;
	
	private class DownloadApkControl extends AsyncTask<ProgressBar, Integer, Integer>{

		private ProgressBar prbRoute;
		protected boolean abort = false;
		
    /**
     * Prepare activity before upload
     */
	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	        File diretorioRaiz = new File( ConstantesSistema.CAMINHO_SDCARD );
			 if(!diretorioRaiz.exists()){					 
				 diretorioRaiz.mkdirs();
			 }
			 
	        File caminhoPastaIsc = new File( ConstantesSistema.CAMINHO_VERSAO );
			 if(!caminhoPastaIsc.exists()){					 
				 caminhoPastaIsc.mkdirs();
			 }
	    }

	    @Override
	    protected void onPostExecute(Integer sucess) {	    	
	    	if(rdc.abort == false){
		    	String errorMsg = DownloadApkActivity.this.verificaErros(sucess);
		    	if (sairSistema){
		    		finish();
		    	}
		    	else if ( errorMsg != null ){	
			
			    	new AlertDialog.Builder(DownloadApkActivity.this)
					.setTitle(getString(R.string.str_alert_download_title))
					.setMessage( errorMsg )
					.setCancelable(false)
					.setNeutralButton(getString(android.R.string.ok), 
						new DialogInterface.OnClickListener() {
							public void onClick(
								DialogInterface dialog,
								int which) {
								Intent i = new Intent(DownloadApkActivity.this, DownloadArquivoActivity.class);
								DownloadApkActivity.this.startActivity(i);
								finish();
							}
					}).show();
				} else {							
					String appName = ConstantesSistema.CAMINHO_VERSAO + "/"+ ConstantesSistema.NOME_APK;
	                Intent intent = new Intent(Intent.ACTION_VIEW); 
	                intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
	                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	                intent.setDataAndType(Uri.fromFile(new File(appName)),
	                "application/vnd.android.package-archive");
	                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	                DownloadApkActivity.this.startActivityForResult(intent, ConstantesSistema.INSTALL_APK);
	    		}
	    	}
	    }	   
	    
	    @Override
	    protected void onProgressUpdate(Integer... values) {
	    	prbRoute.setProgress( values[0] );
	    }	
	    
	    private int baixarApk(InputStream in, int fileSize){			

			File file = new File(ConstantesSistema.CAMINHO_VERSAO+"/"+ConstantesSistema.NOME_APK);
			
			try{	    	

   			 prbRoute.setMax( fileSize );
				
				FileOutputStream fos = new FileOutputStream(file);
		       
				// Guarda o valor do primeiro byte de resposta
				// para depois verificar se o download foi feito
		        //String valor = Util.getValorRespostaInputStream(in);
		        
		        int progress = 0;
		       
                byte[] buffer = new byte[in.available()];
                int len;
                if(fileSize!=1){
                	 while ((len = in.read(buffer)) != -1) {
                     	progress += len;
 		    	  		publishProgress( progress );
 		    	  		fos.write(buffer,0,len);
 		    	  		// Por último, escreve o valor do byte resposta 
 			           
                     }
                     //fos.write((byte)ConstantesSistema.RESPOSTA_OK_CHAR);
                     //fos.flush();
 		            fos.close();
                }else{
                	return ERROR_ABORT_REQUESTED;
                }
                //if( valor.equals("*")){
//                    while ((len = in.read(buffer)) != -1) {
//                    	progress += len;
//		    	  		publishProgress( progress );
//		    	  		fos.write(buffer,0,len);
//		    	  		// Por último, escreve o valor do byte resposta 
//			           
//                    }
//                    //fos.write((byte)ConstantesSistema.RESPOSTA_OK_CHAR);
//                    //fos.flush();
//		            fos.close();
		            
		       // }else{
		      //  	return ERROR_ABORT_REQUESTED;
		      //  }
				} catch (FileNotFoundException e) {
					Log.e( ConstantesSistema.CATEGORIA , e.getMessage());
					e.printStackTrace();
				} catch (IOException e) {
					Log.e( ConstantesSistema.CATEGORIA , e.getMessage());
					e.printStackTrace();
				}
			return ConstantesSistema.OK;
		}
	
		@Override
		protected Integer doInBackground(ProgressBar... params) {
			prbRoute = params[0];	
			
			try{
	    	
				Vector<Object> parametros = new Vector<Object>(2);
				parametros.add( new Byte( ConstantesSistema.DOWNLOAD_APK ) );
				parametros.add(Long.parseLong(Util.getIMEI( DownloadApkActivity.this)));
				
				FachadaWebServer.getInstancia().iniciarConexaoWebServer(DownloadApkActivity.this);
				InputStream in = FachadaWebServer.getInstancia().comunicar( ConstantesSistema.ACAO, parametros );
				baixarApk(in, FachadaWebServer.getInstancia().getFileLength());
				
			} catch (MalformedURLException e) {
				e.printStackTrace();
				Log.e( ConstantesSistema.CATEGORIA , e.getMessage());
				return ConstantesSistema.ERRO_GENERICO;
			} catch (IOException e) {
				e.printStackTrace();
				Log.e( ConstantesSistema.CATEGORIA , e.getMessage());
				return ConstantesSistema.ERRO_CARREGANDO_ARQUIVO;
			} catch ( Exception e ){
				e.printStackTrace();
				Log.e( ConstantesSistema.CATEGORIA , e.getMessage());
				return ConstantesSistema.ERRO_CARREGANDO_ARQUIVO;
			}			
			
			return ConstantesSistema.OK;
		}		
	
	}	
	
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    	super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == ConstantesSistema.INSTALL_APK) {

			if (resultCode != RESULT_OK) {
				new AlertDialog.Builder(DownloadApkActivity.this)
    			.setTitle(getString(R.string.str_download_alert_file))
    			.setCancelable(false)
    			.setMessage(getString(R.string.str_error_aborted) )
    			.setNeutralButton(getString(android.R.string.ok), 
    				new DialogInterface.OnClickListener() {
    					public void onClick(
    						DialogInterface dialog,
    						int which) {
    						
    						Intent i = new Intent(DownloadApkActivity.this, DownloadApkActivity.class);
    						DownloadApkActivity.this.startActivity(i);
    					
    					}
    			}).show(); 
			}else{

	            //ConstantesSistema.VERSAO_SISTEMA = VERSAO_BAIXADA;
			}
			
		}
    }
	
	private Button btnCancel;
	private Button btnSair;
	private boolean sairSistema;
	private DownloadApkControl rdc;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if (execute){	
	        setContentView(R.layout.download_arquivo_activity);
	        Fachada.setContext( this );
	        
	        TextView text = (TextView) findViewById(R.id.textViewProgress);
	        text.setText("Baixando nova versão. Por favor, aguarde...");
	        
	        // Caso tenha retornado mensagem ao enviar imóvel ao gsan
	        if(getIntent().getStringExtra("msgErro")!=null){
	
				Fachada.getInstance().alertaMensagemConexao(null,ConstantesSistema.SAIR_APLICACAO,ConstantesSistema.SAIR_APLICACAO,false,ArquivoRetorno.ARQUIVO_LIDOS_ATE_AGORA,
						ConstantesSistema.ALERTA_MENSAGEM, getIntent().getStringExtra("msgErro"), 1);
	     
		   }else  if(!Fachada.getInstance().verificarExistenciaBancoDeDados()){
			        	
			        	
	        	btnCancel = ( Button ) findViewById(R.id.btnCancelDA);
	        	btnCancel.setVisibility(View.INVISIBLE);
	
	        	
	        	btnSair = ( Button ) findViewById(R.id.btnSair);
	            btnSair.setOnClickListener(new OnClickListener() {
	    			
	    			public void onClick(View arg0) {
	    				sairSistema = true;
	    				rdc.abort = true;
	    				finish();
	    				
	    			} 
	    		});
	            
	            int sucess = compararVersao();
	            	
	            if(sucess == ConstantesSistema.OK) {
	        		startDownload();        	
	        		
	            }else{
	
	            	String errorMsg = DownloadApkActivity.this.verificaErros(sucess);   
	     	    	
	     	    	if ( errorMsg != null ){	
	     	    		 boolean exiteBanco = Fachada.getInstance().verificarExistenciaBancoDeDados();
	     	            
	     	            if ( exiteBanco ){ 
	     	            	new AlertDialog.Builder(DownloadApkActivity.this)
	     	     			.setTitle(getString(R.string.str_download_alert_file))
	     	     			.setMessage( errorMsg )
	     	     			.setCancelable(false)
	     	     			.setNeutralButton(getString(android.R.string.ok), 
	     	     				new DialogInterface.OnClickListener() {
	     	     					public void onClick(
	     	     						DialogInterface dialog,
	     	     						int which) {
	     	     						//Manda para tela de login
	     	     						Intent i = new Intent(DownloadApkActivity.this, LoginActivity.class);
	     	     						DownloadApkActivity.this.startActivity(i);
	     	     						finish();
	     	     					}
	     	     			}).show();
	     	            }else{
	    	
	    	             	new AlertDialog.Builder(DownloadApkActivity.this)
	    	     			.setTitle(getString(R.string.str_download_alert_file))
	    	     			.setMessage( errorMsg )
	    	     			.setCancelable(false)
	    	     			.setNeutralButton(getString(android.R.string.ok), 
	    	     				new DialogInterface.OnClickListener() {
	    	     					public void onClick(
	    	     						DialogInterface dialog,
	    	     						int which) {
	    	     						
	    	     						// Caso contrario, encaminhamos para a tela de carregamento de arquivo offline
	    	     						Intent i = new Intent(DownloadApkActivity.this, SelecionarArquivoActivity.class);
	    	     						DownloadApkActivity.this.startActivity(i);
	    	     						finish();
	    	     					}
	    	     			}).show();
	     	            }
	    				
	                 }else{
	                	 	// Caso contrario, encaminhamos para a tela de carregamento de arquivo online
	    					Intent i = new Intent(DownloadApkActivity.this, DownloadArquivoActivity.class);
	    					DownloadApkActivity.this.startActivity(i);
	    					finish();
	                 }
	            }
	        	
	        }else{
	        	Intent i = new Intent(this, LoginActivity.class);
	    		startActivity(i);
	    		finish();
	        }        
        }
    }
    
    private void startDownload() {
		ProgressBar progress = (ProgressBar) findViewById( R.id.progressPorLinha );
		progress.setIndeterminate( false );
		rdc = new DownloadApkControl();
		rdc.execute( progress );		
	}
    
    private int compararVersao() {		
		FachadaWebServer.getInstancia().iniciarConexaoWebServer(DownloadApkActivity.this);
		
		int sucess = ConstantesSistema.ERRO_GENERICO;
		
		boolean servidorOnline = FachadaWebServer.getInstancia().serverOnline();
		
		//Caso o servidor esteja online
		if ( servidorOnline ){	
			Vector<Object> parametros = new Vector<Object>(2);
			parametros.add( new Byte( ConstantesSistema.VERIFICAR_VERSAO ) );
			parametros.add( new Long( Util.getIMEI(DownloadApkActivity.this) ) );

			
			String strVersaoAtual = Util.getVersaoSistema(getApplicationContext());
			Integer intVersaoAtual = Integer.valueOf(strVersaoAtual.replace(".",""));
			
			try {
				InputStream in = FachadaWebServer.getInstancia().comunicar( ConstantesSistema.ACAO, parametros );
			
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	    	    StringBuilder sb = new StringBuilder();
	    	    String line = null;
	    	    String strNovaVersao = null;
	    	    Integer intNovaVersao = null;
	    	   
				while ((line = reader.readLine()) != null) {
				  sb.append(line + "\n");
				}
				if(sb.length() != 0){
					strNovaVersao = sb.substring(1,sb.length()-1);
					if(strNovaVersao != ""){
						strNovaVersao = strNovaVersao.replace(".", "");
						intNovaVersao = Integer.valueOf(strNovaVersao);
					}
				}
					
				if(intNovaVersao != null){
					
					if(intNovaVersao > intVersaoAtual){
						//VERSAO_BAIXADA = strNovaVersao;
						sucess= ConstantesSistema.OK;			
					}else{
					// Ignora o download
					sucess= IGNORE_DOWNLOAD_VERSION;		
				}
				}else{
					// Ignora o download
					sucess= IGNORE_DOWNLOAD_VERSION;		
				}
				
				} catch (IOException e) {
					Log.e( ConstantesSistema.CATEGORIA , e.getMessage());
					e.printStackTrace();
					return ERRO_VERIFICANDO_VERSAO;
				}
		}else{
			
			sucess= ConstantesSistema.ERRO_SERVIDOR_OFF_LINE;
		}

		return sucess;
		
    }

	private String verificaErros(Integer sucess) {
		String errorMsg = null;
		
		switch (sucess){ 
    	
			case ConstantesSistema.ERRO_GENERICO:
				errorMsg = getString(R.string.str_download_erro_problema_desconhecido);
				break;
				
			case ConstantesSistema.ERRO_DOWNLOAD_ARQUIVO:
				errorMsg = getString(R.string.str_error_download);
				break;				
				
			case ConstantesSistema.ERRO_CARREGANDO_ARQUIVO:
				errorMsg = getString(R.string.str_error_laoding);
				break;			
				
			case ConstantesSistema.ERRO_SERVIDOR_OFF_LINE:
				errorMsg = getString(R.string.str_error_off);
				break;	    			
		
			case ERRO_DOWNLOAD_APK:
				errorMsg = getString(R.string.str_alert_download_apk_erro);
				break;
				
			case ERRO_VERIFICANDO_VERSAO:
				errorMsg = getString(R.string.str_alert_verificar_versao_erro);
				break;

			case ERRO_VERSAO_INDISPONIVEL:
				errorMsg = getString(R.string.str_alert_download_download_error);
				break;
				
			case ERROR_ABORT_REQUESTED:
				errorMsg = getString(R.string.str_error_aborted);
				break;
		}
		
		if(errorMsg != null){
			return errorMsg;
		}
		
		return null;		
		
	}
	
	public void onBackPressed() {
		return;
	}
    
	  
	@Override
	public void onAttachedToWindow()
	{  
		this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);     
		super.onAttachedToWindow();  
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		
		// Caso o usuário tenha apertado em cancelar ao baixar a versão
    	new AlertDialog.Builder(DownloadApkActivity.this)
		.setTitle(getString(R.string.str_download_alert_file))
		.setMessage( getString(R.string.str_error_aborted) )
		.setCancelable(false)
		.setNeutralButton(getString(android.R.string.ok), 
			new DialogInterface.OnClickListener() {
				public void onClick(
					DialogInterface dialog,
					int which) {
					
					Intent i = new Intent(DownloadApkActivity.this, DownloadApkActivity.class);
					startActivity(i);
					finish();
				
				}
		}).show(); 
	}  	
	
}