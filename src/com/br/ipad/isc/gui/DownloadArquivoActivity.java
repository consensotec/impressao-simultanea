package com.br.ipad.isc.gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;

import com.br.ipad.isc.R;
import com.br.ipad.isc.excecoes.FachadaException;
import com.br.ipad.isc.fachada.Fachada;
import com.br.ipad.isc.fachada.FachadaWebServer;
import com.br.ipad.isc.io.MessageDispatcher;
import com.br.ipad.isc.util.ConstantesSistema;
import com.br.ipad.isc.util.Util;

/**
 * 
 * Activity que irá gerenciar o processo 
 * de finalização de uma rota
 * 
 * @author Fernanda Almeida
 * @date 01/04/2012
 *
 */
public class DownloadArquivoActivity extends BaseActivity implements OnClickListener{

	private int fileLength;
    
	// Classe que irá controlador todo o processo
	private class RouteDownloadControl extends AsyncTask<ProgressBar, Integer, Integer>{

		private ProgressBar prb;
		protected boolean abort = false;
		
	    /**
	     * 
	     * Carrega um inputstream para o banco de dados do celular
	     * 
	     * @autor Bruno Barros
	     * @date 01/09/2011
	     * 
	     * @param arquivo
	     * @throws IOException
	     */
		public int carregarArquivoParaBD(BufferedReader leitor, int fileSize ) throws IOException {
			
			int retorno = ConstantesSistema.ERRO_GENERICO; 
			
			prb.setMax( fileSize );
			
			if (  leitor != null ){		
				
				String line = "";		
				
				int progress = 0;
				
				while ((line = leitor.readLine()) != null ){
					
					progress += line.getBytes().length;
					
					Fachada.getInstance().carregaLinhaParaBD( line );
											
					publishProgress( progress );
					retorno = ConstantesSistema.OK;
					
					if (abort){
						return ConstantesSistema.ERRO_REQUISICAO_ABORTAR;
					}
				}
				
				//Atualiza quantidade de imoveis em sistemaParametros
				Fachada.getInstance().atualizarQntImoveis();
				//Atualiza o indicador de arquivo carregado com sucesso.
				Fachada.getInstance().atualizarArquivoCarregadoBD();
				
			}
			
			return retorno;
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
			
			if(getIntent().getStringExtra("offline") != null){
				
				String path = getIntent().getStringExtra("offline");
				
				File file = new File(ConstantesSistema.CAMINHO_OFFLINE+ "/" + path );
				
				InputStream instream = null;
			    
				try {
					//InputStream do arquivo selecionado
					instream = new FileInputStream(file);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					Log.e( ConstantesSistema.CATEGORIA , e.getMessage());
				}
				
				try {
					
					int tamanhoArquivo = (int) file.length();
					
					//Unzip
					if (path.endsWith(".gz")) {
				    	instream = Util.inflateFile( instream, (int) file.length() );
				    	tamanhoArquivo = (int) file.length()*5;
				    }
										
					if(lerArquivoTxt(instream, tamanhoArquivo) == ConstantesSistema.ERRO_CARREGANDO_ARQUIVO){
						return ConstantesSistema.ERRO_CARREGANDO_ARQUIVO;
					}

				} catch (IOException e) {
					Log.e( ConstantesSistema.CATEGORIA , e.getMessage());
					e.printStackTrace();
				}	catch (FachadaException e) {
					Log.e( ConstantesSistema.CATEGORIA , e.getMessage());
					e.printStackTrace();
					return ConstantesSistema.ERRO_CARREGANDO_ARQUIVO_REGISTRO_DUPLICADO;
				}
				
				if ( abort ){
					return ConstantesSistema.ERRO_REQUISICAO_ABORTAR;
				}
				
				return ConstantesSistema.OK;
				
			}else{
			
				FachadaWebServer.getInstancia().iniciarConexaoWebServer(DownloadArquivoActivity.this);
				
				int sucess = ConstantesSistema.ERRO_GENERICO;
				
				boolean servidorOnline = FachadaWebServer.getInstancia().serverOnline();
			
				// Verificamos se o servidor está online
				if ( servidorOnline ){		
					Vector<Object> parametros = new Vector<Object>(2);
					parametros.add( new Byte( ConstantesSistema.BAIXAR_ROTA) );
					parametros.add(Long.valueOf(Util.getIMEI(DownloadArquivoActivity.this)));
					// serviço tipo celular = leitura 
//					parametros.add(new Integer(1));
					
					InputStream instream;
					
					try {
						instream = FachadaWebServer.getInstancia().comunicar( ConstantesSistema.ACAO, parametros );
						
						StringBuffer buffer = new StringBuffer();
						StringBuffer bufferValorParametro = new StringBuffer();
						
						int tamanho = FachadaWebServer.getInstancia().getFileLength();
						
						
						if(instream.read() == ConstantesSistema.RESPOSTA_OK_CHAR){
							
							for (int i = 1; i <= tamanho; i++) {
									
								char caracter = (char)instream.read();
								
								buffer.append(caracter);
								
								boolean ultimoCaracter = i == tamanho;
		
								if (MessageDispatcher.getInstancia().
										controlarParametros(buffer, caracter,bufferValorParametro, ultimoCaracter)) {
		
									buffer = new StringBuffer();
									bufferValorParametro = new StringBuffer();
									continue;
								}
								
								// Caso ache arquivos de retorno,
								// não haverão parametros subsequentes
								if (buffer.toString().indexOf(MessageDispatcher.PARAMETRO_ARQUIVO_ROTEIRO) > -1) {
									fileLength = tamanho - (i + 1);
									break;
								}
							}
							
							//Descompactar Arquivo
						    if ( MessageDispatcher.getInstancia().getTipoArquivo() == ConstantesSistema.TIPO_ARQUIVO_GZ ){
						    	instream = Util.inflateFile(instream, fileLength );
						    	fileLength = tamanho * 6;
						    }

							int retorno = lerArquivoTxt(instream, fileLength);
							
							if(retorno == ConstantesSistema.ERRO_CARREGANDO_ARQUIVO){
								return ConstantesSistema.ERRO_CARREGANDO_ARQUIVO;
							}else{
								sucess = retorno;
							}
						    
						}else{
							sucess = ConstantesSistema.ERRO_SEM_ARQUIVO;
						}
						
						
						if ( sucess == ConstantesSistema.OK ){
							// Enviamos uma mensagem ao GSAN informando que o arquivo foi carregado com sucesso
							if ( !FachadaWebServer.getInstancia().routeInitializationSignal() ){
								return ConstantesSistema.ERRO_SINAL_INICIALIZACAO_ROTEIRO;
							}
							
						}else {
							return sucess;
						}
						
	
					} catch (IOException e) {
						e.printStackTrace();
						Log.e( ConstantesSistema.CATEGORIA , e.getMessage());
						return ConstantesSistema.ERRO_CARREGANDO_ARQUIVO;
					}	catch (FachadaException e) {
						Log.e( ConstantesSistema.CATEGORIA , e.getMessage());
						e.printStackTrace();
						return ConstantesSistema.ERRO_CARREGANDO_ARQUIVO_REGISTRO_DUPLICADO;
					}
				} else {
					return ConstantesSistema.ERRO_SERVIDOR_OFF_LINE;
				}
				
				return ConstantesSistema.OK;		
			}
			
		}
		
	    @Override
	    protected void onPostExecute(Integer sucess) {
	    	String errorMsg = null;
	    	
	    	switch (sucess){
	    	
	    		case ConstantesSistema.ERRO_GENERICO:
	    			errorMsg = getString(R.string.str_download_erro_problema_desconhecido);
	    			break;
	    			
	    		case ConstantesSistema.ERRO_REQUISICAO_ABORTAR:
		    			errorMsg = getString(R.string.str_download_erro_abortar);
		    			break;
		    			
	    		case ConstantesSistema.ERRO_DOWNLOAD_ARQUIVO:
	    			errorMsg = getString(R.string.str_erro_download);
	    			break;
	    		
	    		case ConstantesSistema.ERRO_CARREGANDO_ARQUIVO:
	    			errorMsg = getString(R.string.str_erro_carregando);
	    			break;

	    		case ConstantesSistema.ERRO_SERVIDOR_OFF_LINE:
	    			errorMsg = getString(R.string.str_erro_off);
	    			break;
	    			
	    		case ConstantesSistema.ERRO_SINAL_INICIALIZACAO_ROTEIRO:
    				errorMsg = getString(R.string.str_erro_sinal_campo);
    				break;
    				
	    		case ConstantesSistema.ERRO_SEM_ARQUIVO:
    				errorMsg = getString(R.string.str_error_nofile);
    				break;  	
    				
	    		case ConstantesSistema.ERRO_CARREGANDO_ARQUIVO_REGISTRO_DUPLICADO:
    				errorMsg = getString(R.string.str_error_carregar_arquivo_registro_duplicado);
    				break;  	
    				
    				
	    		
	    	}
	    	
	    	if (sairSistema){
	    		Util.sairAplicacao(DownloadArquivoActivity.this);
	    	} else if ( errorMsg != null ){	
	    		
        		new AlertDialog.Builder(DownloadArquivoActivity.this)
    			.setTitle(getString(R.string.str_download_alert_file))
    			.setCancelable(false)
    			.setMessage( errorMsg )
    			.setNeutralButton(getString(android.R.string.ok), 
    				new DialogInterface.OnClickListener() {
    					public void onClick(
    						DialogInterface dialog,
    						int which) {
    						enviarListaArquivos();
    					}
    			}).show();
  
            	
	    	} else {
	    		
	        	Intent i = new Intent( DownloadArquivoActivity.this, LoginActivity.class);
	    		startActivity(i);
	    		finish();
	    	}
	    }
		
	    @Override
	    protected void onProgressUpdate(Integer... values) {
	    	prb.setProgress( values[0] );
	    }
	    
	    
	    public int lerArquivoTxt (InputStream instream, int tamanhoArquivo) throws IOException, FachadaException{

	    	int retorno = ConstantesSistema.ERRO_CARREGANDO_ARQUIVO;

	    	//Lemos a primeira linha
	    	BufferedReader leitor = new BufferedReader(new InputStreamReader(instream, "ISO-8859-1"));
	    	String line = leitor.readLine();

    		//Caso ainda tenha algum residuo antigo de BD. O banco é apagado
    		Fachada.getInstance().apagarBanco();
    		Log.d( ConstantesSistema.CATEGORIA , "Carregando banco de dados.");

    		//Carregamos a primeira linha no BD
    		Fachada.getInstance().carregaLinhaParaBD( line );
    		retorno = carregarArquivoParaBD(leitor, tamanhoArquivo);	

	    	return retorno;
	    }
	}
	
	private void enviarListaArquivos(){
		// Encaminhamos carregamento de arquivo offline
		Intent i = new Intent(DownloadArquivoActivity.this, SelecionarArquivoActivity.class);
		DownloadArquivoActivity.this.startActivity(i);
		finish();
	}
	
	private Button btnCancel;
	
	private RouteDownloadControl rdc;
	
	private boolean sairSistema;
	
	
	
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	if (execute){	    	
	        setContentView(R.layout.download_arquivo_activity);
	        
	        Fachada.setContext( this );
	        
	        btnCancel = ( Button ) findViewById(R.id.btnCancelDA);
	        btnCancel.setOnClickListener( this );
	        
	        //Caso banco de dados não exista
	        if (!Fachada.getInstance().verificarExistenciaBancoDeDados()){     
	        	startDownload();
	        		
	    	} else {
				Intent i = new Intent(this, LoginActivity.class);
	    		startActivity(i);
	    		finish();
	    	}   
	        
	        Button sair = (Button) findViewById(R.id.btnSair);
	        sair.setOnClickListener(new OnClickListener() {
				public void onClick(View arg0) {
					sairSistema = true;
					rdc.abort = true;				
				}
			});
    	}
    }
    
	private void startDownload() {
		ProgressBar progress = (ProgressBar) findViewById( R.id.progressPorLinha );
		progress.setIndeterminate( false );
		rdc = new RouteDownloadControl();
		rdc.execute( progress );		
	}

	public void onClick(View arg0) {
		rdc.abort = true;
	}
	
	@Override
    public void onBackPressed() {

       return;
    }
	
	@Override
	public void onAttachedToWindow()
	{  
		this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);     
		super.onAttachedToWindow();  
	}
	
}
