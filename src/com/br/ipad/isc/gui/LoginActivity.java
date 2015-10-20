package com.br.ipad.isc.gui;

import java.io.File;
import java.io.FileFilter;
import java.util.Date;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.SistemaParametros;
import com.br.ipad.isc.controladores.ControladorAlertaValidarMensagemConexao;
import com.br.ipad.isc.excecoes.ZebraException;
import com.br.ipad.isc.fachada.Fachada;
import com.br.ipad.isc.fachada.FachadaWebServer;
import com.br.ipad.isc.impressao.ZebraUtils;
import com.br.ipad.isc.io.ArquivoRetorno;
import com.br.ipad.isc.util.Bluetooth;
import com.br.ipad.isc.util.ConstantesSistema;
import com.br.ipad.isc.util.Criptografia;
import com.br.ipad.isc.util.Util;

public class LoginActivity extends BaseActivity {
	
	private TextView login;
	private TextView senha;
	private TextView versao;
	private Button btLogin;
	private Button btSair;
	private boolean arquivoRetorno = false;
	private Fachada fachada;
	LocationManager manager = null;
	
	
	private void exibirMensagem(String mensagem, final boolean fecharActivity){

		
		AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
		alert.setTitle(R.string.str_login_alert_title);
		alert.setMessage(mensagem);
		alert.setNeutralButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				
				if(fecharActivity){
					sair();
					return;
				}
			}
		});
		alert.show();
		
	}
	
	private boolean validarVersao(){
		
		boolean retorno = false;
		
		if(SistemaParametros.getInstancia() != null){
			
			String strVersaoSistema = Util.getVersaoSistema(getApplicationContext()).replace(".", "");
			String strVersaoArquivo = SistemaParametros.getInstancia().getVersaoCelular().replace(".", "");
			
			Integer versaoSistema = Integer.parseInt( strVersaoSistema  );
			Integer versaoArquivo = Integer.parseInt( strVersaoArquivo );

			if(versaoArquivo <= versaoSistema){
				retorno = true;
			 } 
						
		}
		
		return retorno;
	}
	
	private boolean validarFaixaDatas(){
		
		boolean retorno = true;
				
		if(SistemaParametros.getInstancia().getDataInicio() != null && SistemaParametros.getInstancia().getDataFim() != null){
			
			Integer dataAtual = Integer.parseInt(Util.formatarDataSemBarra(new Date()));
			Integer dataInicioBloqueio = Integer.parseInt(Util.formatarDataSemBarra(SistemaParametros.getInstancia().getDataInicio()));
			Integer dataFinalBloqueio = Integer.parseInt(Util.formatarDataSemBarra(SistemaParametros.getInstancia().getDataFim()));
			
			if(dataAtual < dataInicioBloqueio || dataAtual > dataFinalBloqueio){
				retorno = false;
			}
			
		}
		
		return retorno;
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (execute){
	        if(getIntent().getBooleanExtra("sair", false)){    		
	        	sair();
	        }else{
	        	
	        	manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);	
	            setContentView(R.layout.tela_login);
	            Fachada.setContext(this);
	            fachada = Fachada.getInstance();
	            
	            // Para o teclado não aparecer ao entrar na tela
	            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	            
	            //Verificar se é emulador ou aparelho
	            if("sdk".equals(Build.PRODUCT) || "google_sdk".equals(Build.MODEL) ){
	            	ConstantesSistema.SIMULADOR = true;
	            }else{
	            	ConstantesSistema.SIMULADOR = false;
	            }
	            
	            
	            Bluetooth.resetarBluetooth();
	    		
	            versao = (TextView) findViewById(R.id.versao);
	            versao.setText(" Produção: "+Util.getVersaoSistema(getApplicationContext()));
	                        
	            login = (TextView) findViewById(R.id.edtLogin);
	    		senha = (TextView) findViewById(R.id.edtPassword);
	    		
	    		setUpButtons();            
	        	
	        }
        }
    }
    
    public void setUpButtons(){
    	btLogin = (Button) findViewById(R.id.btLogin);
        btSair = (Button) findViewById(R.id.btSair);
        
        btSair.setOnClickListener(new OnClickListener() {
			
        	public void onClick(View arg0) {
        		sair();
			}
        });
        
        
        
        btLogin.setOnClickListener(new OnClickListener() {
			
        	public void onClick(View arg0) {
        		
        		// caso não permita múltiplas rota, é verificado se já existe arquivo de retorno.
        		// caso positivo, náo é permitido fazer login
        		if(!ConstantesSistema.PERMITE_BAIXAR_MULTIPLAS_ROTAS){
        			
        			File file = new File(ConstantesSistema.CAMINHO_RETORNO);
        			FileFilter filter = new FileFilter() {
        	            public boolean accept(File pathname) {
        	            	return pathname.getName().endsWith(".txt");
        	            }
        	        };
        	        
        			final String nomeArquivo;
        			File[] prefFiles = file.listFiles(filter);
        			 
        			if(prefFiles != null && prefFiles.length != 0){
        		        	
        				arquivoRetorno = true;
        				
        				//CASO TODOS OS IMOVEIS ESTEJAM CALCULADOS
        				if(Fachada.getInstance().obterQuantidadeImoveisVisitados().equals(Fachada.getInstance().obterQuantidadeImoveis())){
        						
        					Util.apagarArquivoRetorno();
        					
        					
        					Intent i = new Intent(LoginActivity.this,FinalizaArquivoActivty.class);
				    		i.putExtra(getString(R.string.str_extra_tipo_finalizacao), ArquivoRetorno.ARQUIVO_TODOS_OS_CALCULADOS+"");
				    		i.putExtra("login", "ok");
				    		startActivity(i);
				    		finish();

        				}else{

	    		        	nomeArquivo = prefFiles[0].getName();
	    		        	
	    		        	
			        		final ProgressDialog mProgressDialog = ProgressDialog.show(LoginActivity.this, "",getString(R.string.str_login_rota_pendente));
			                
			                mProgressDialog.setIndeterminate(false);
			                mProgressDialog.setMax(100);
			                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			        			
			        		AsyncTask<Object, Integer, Integer> taskDownloadFile = new AsyncTask<Object, Integer, Integer>(){
	
			        			@Override
			        			protected Integer doInBackground(Object... arg0) {
			        				
			        				FachadaWebServer.getInstancia().setContextComunicacaoWebServer(LoginActivity.this);
			        				int retorno = FachadaWebServer.getInstancia()
			        						.enviarDados(nomeArquivo, ArquivoRetorno.ARQUIVO_COMPLETO, LoginActivity.this,null);
			        				
			        				return retorno;
			        			}
			        			
			        			@Override
			        			protected void onPostExecute(Integer result) {
			        				// TODO Auto-generated method stub
			        				super.onPostExecute(result);
			        				ControladorAlertaValidarMensagemConexao controladorAlerta = null;
			        		    	String msgTipoFinalizacao = null;
			        		    	
			        		    	boolean enviou = false;
			        		    	switch (result){	    	
			        		    	
			        		    		case ConstantesSistema.ERRO_GENERICO:
			        		    			msgTipoFinalizacao = getString(R.string.str_erro_generico_envio);
			        		    			break;  
			        		    		case ConstantesSistema.ERRO_SINAL_INICIALIZACAO_ROTEIRO:
			        		    			msgTipoFinalizacao = getString(R.string.str_erro_finalizacao);
			        		    			break;
			        		    		case ConstantesSistema.ERRO_SERVIDOR_OFF_LINE:
			        		    			msgTipoFinalizacao = getString(R.string.str_erro_off);
			        		    			break;	
			        		    		case ConstantesSistema.OK:
	
			        	    				enviou = true;
			        		    			
		        		    				msgTipoFinalizacao = getString(R.string.str_finalizacao_alert);
			        	        			   		        	                	
			        		    	}
			        		    	   		
	
			        	    		mProgressDialog.dismiss();
			        	    		if ( msgTipoFinalizacao != null ){
			        	    			//Insere data e mensagem recebida do gsan
			        	    	    	Fachada.getInstance().insereLogFinalizacao(msgTipoFinalizacao);
			        	    	    	
			        	    			String msg = msgTipoFinalizacao;
			        	    			String msgErroGsan = FachadaWebServer.getMensagemError();
			        	    			
			        		         	if (msgErroGsan != null){
			        		         		enviou = false;
			        		         		msg = msgErroGsan;
			        		         	}
			        		         	    		        	         		    		        		    			
	        		    				controladorAlerta = new ControladorAlertaValidarMensagemConexao(ConstantesSistema.SAIR_APLICACAO,ConstantesSistema.MENU_PRINCIPAL,enviou,msgErroGsan,nomeArquivo );
	        		    				controladorAlerta.defineAlerta(ConstantesSistema.ALERTA_MENSAGEM,msg,1);
	        		    				ArquivoRetorno.montaArquivo = new StringBuilder();
			        		    			
			        			         		        		         		        	
			        	    		}
			        			}
			        		
			        		};
			        		
			        		taskDownloadFile.execute();
	    		        	
	//    		        		new AlertDialog.Builder(LoginActivity.this) 
	//    		    			.setMessage( getString(R.string.str_login_rota_pendente) )
	//    		    			.setNeutralButton( getString(android.R.string.ok), 
	//    		    				new DialogInterface.OnClickListener() {
	//    		    					public void onClick(
	//    		    						DialogInterface dialog,
	//    		    						int which) {
	//    		    						      
	//    		    						Intent i = new Intent(LoginActivity.this,FinalizaArquivoActivty.class);
	//    		    						i.putExtra(getString(R.string.str_extra_tipo_finalizacao), ArquivoRetorno.ARQUIVO_COMPLETO);
	//    		    						startActivity(i);
	//    		    					}
	//    		    			}).show();
        				}
    		        	    		        	
					}else{
						arquivoRetorno = false;
					}
        		}
        				
        		if(!arquivoRetorno){
        	
	        		if(!validarVersao()){
	        			exibirMensagem(getString(R.string.str_versao_desatualizada), true);
	        		}else{
	        				        				
						String strLogin = login.getText().toString();
						String strSenha = senha.getText().toString();
						
						if(strLogin.trim().trim().equals("")){
							//Caso Login seja em branco
							exibirMensagem(getString(R.string.str_login_branco), false);					
						}
						
						else if(strSenha.trim().equals("")){
							//Caso senha senha em branco
							exibirMensagem(getString(R.string.str_senha_branco), false);
							
						}else{
							//Caso login e senha estejam preenchidos
							
							if(!validarFaixaDatas()){
	    	        			exibirMensagem(getString(R.string.str_login_alert_wrong_date), true);
							}else{
	
	    						if(! strLogin.equals(SistemaParametros.getInstancia().getLogin())){
	    							
	    							String loginInvalido = getString(R.string.str_login_invalido, strLogin); 
	    							exibirMensagem(loginInvalido, false);
	    							
	    						}else{
	    							if(! SistemaParametros.getInstancia().getSenha().equals(Criptografia.encode(strSenha))  ){
	    								
	    								exibirMensagem(getString(R.string.str_senha_invalida),false);
	    								
	    							}else{
	    								
	    								if ( SistemaParametros.getInstancia().getIndicadorArmazenarCoordenadas() != null 
	    										&& SistemaParametros.getInstancia().getIndicadorArmazenarCoordenadas().equals(ConstantesSistema.SIM) 
	    										&& !manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//			        							//Pergunta ao usuário para habilitar o GPS
			        				    		AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
			        							alert.setTitle("ISC");
			        				    		alert.setMessage("GPS Desconectado. Será necessário ativá-lo para continuar a operação.");
			        							alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			        								
			        								public void onClick(DialogInterface arg0, int arg1) {
			        									Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			        									startActivity(intent);
			        								}
			        							});
			        							alert.show();
			    							
			        					}
	    								else {
	    									
		    								String data = Util.convertDateToDateStr(new Date());
		    			        			AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
		    			        			alert.setTitle(R.string.str_login_alert_title);
		    			        			alert.setMessage("A data atual é " + data.substring(0,10) + ". Confirma? ");
		    			        			
		    			        			alert.setPositiveButton(R.string.str_sim, new DialogInterface.OnClickListener(){
		
		    			        				
		    			        				public void onClick(DialogInterface arg0, int arg1) {
		    			        					Bluetooth.ativarBluetooth();
		    			        					Intent it = new Intent(LoginActivity.this, MenuActivity.class);
		    	    								startActivity(it);
		    	    								finish();
		    	    								
		    			        				}
		    			            			
		    			        			});
		    			        			
		    			        			//Caso nao confirme!
		    			        			alert.setNegativeButton(R.string.str_nao, new DialogInterface.OnClickListener() {
		    			        				public void onClick(DialogInterface dialog, int which) {
		    			        					Intent intent = new Intent(Settings.ACTION_DATE_SETTINGS);
		    			        					startActivity(intent);
		    			        					
		    			        				}
		    			        			});
		    			        			
		    			        			alert.show();
		    								
	    								}
	    							}
	    						}
								
							}
							
						}
	        
	        		}
				}
        	}

        });
    }
    
    public void onBackPressed() {
    	sair();
    }  
    
    public void sair(){

    	moveTaskToBack(true);
    	Bluetooth.desativarBluetooth();
    	senha.setText("");
    	login.setText("");
    	
    	try {
			ZebraUtils.getInstance(getApplicationContext()).disconnect();
		} catch (ZebraException e) {
			e.printStackTrace();
		}
    	
    	System.exit(0);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	
    	MenuItem menuItem = menu.add(0, 1, 0, R.string.str_apagar_dados);
        return super.onCreateOptionsMenu(menu);
    }
    
    
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
    
    	switch (item.getItemId()) {		
    		case 1:
    			
				new AlertDialog.Builder(LoginActivity.this)
				.setMessage( getString(R.string.str_menu_confirma_apagar) )
				.setPositiveButton(getString(R.string.str_sim), 
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							
							AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);
							
							alertDialog.setTitle(getString(R.string.str_menu_apagar));
							alertDialog.setMessage(getString(R.string.str_senha_branco));
							
				    		final EditText input = new EditText(getApplicationContext());
				    		input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
				    		input.setTransformationMethod(PasswordTransformationMethod.getInstance());
				    		alertDialog.setView(input);
							
							alertDialog.setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
							      public void onClick(DialogInterface dialog, int which) {
							 
							    	  String senha = input.getText().toString();
							    	  
							    	 if(fachada.validaSenhaApagar(senha)) {
					    		    	fachada.apagarBanco();
					    		    	new AlertDialog.Builder(LoginActivity.this)
						    			.setTitle(getString(R.string.str_menu_apagar))
						    			.setMessage(getString(R.string.str_susseco_apagar_tudo))
						    			.setNeutralButton(getString(android.R.string.ok), 
						    				new DialogInterface.OnClickListener() {
						    					public void onClick(DialogInterface dialog,int which) {
						    						Intent i = new Intent(LoginActivity.this,DownloadApkActivity.class);
						    						startActivity(i);
						    						finish();		
						    						
						    					}
						    					
						    					
						    			}).setOnKeyListener(new DialogInterface.OnKeyListener(){
											@Override
											public boolean onKey(DialogInterface arg0,int arg1, KeyEvent arg2) {																				
												Intent i = new Intent(LoginActivity.this,DownloadApkActivity.class);
					    						startActivity(i);
					    						finish();
					    						return true;
											}
						    	         })
						    			.show();
							    		    
						    		  }else{
						    			// Informamos ao usuário que a senha foi incorreta
							        		new AlertDialog.Builder(LoginActivity.this)
							    			.setTitle(getString(R.string.str_menu_apagar))
							    			.setMessage(getString(R.string.str_senha_invalida))
							    			.setNeutralButton(getString(android.R.string.ok), 
							    				new DialogInterface.OnClickListener() {
							    					public void onClick(DialogInterface dialog,int which) {
							    						
							    					}
							    			}).show();
						    		  }									      
							 
							    }

							}); 
							
							alertDialog.setNegativeButton(getString(R.string.str_geral_bt_cancela), new DialogInterface.OnClickListener() {
					    		  public void onClick(DialogInterface dialog, int whichButton) {}
					    		});
							
							alertDialog.show();
							
						}
				})
				.setNegativeButton(getString(R.string.str_nao), 
					new DialogInterface.OnClickListener() {
						public void onClick(
							DialogInterface dialog,
							int which) {
											
						}
				})
				.show();

				
				
    			
    		break;
    	
    	}
    	
    	return super.onMenuItemSelected(featureId, item);
    }
 
}