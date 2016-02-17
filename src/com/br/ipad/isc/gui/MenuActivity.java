
package com.br.ipad.isc.gui;

import java.util.ArrayList;
import java.util.Date;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.br.ipad.isc.R;
import com.br.ipad.isc.adapters.ListaMenuAdapter;
import com.br.ipad.isc.bean.Foto;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.bean.ImovelRevisitar;
import com.br.ipad.isc.bean.SistemaParametros;
import com.br.ipad.isc.bean.helpers.Menu;
import com.br.ipad.isc.excecoes.FachadaException;
import com.br.ipad.isc.excecoes.NegocioException;
import com.br.ipad.isc.fachada.Fachada;
import com.br.ipad.isc.io.ArquivoRetorno;
import com.br.ipad.isc.io.ExportBancoDados;
import com.br.ipad.isc.util.ConstantesSistema;
import com.br.ipad.isc.util.Util;


public class MenuActivity extends BaseActivity {
		
		private ListaMenuAdapter adapter;
		private ListView lv;	
		private Menu menu;
		private Fachada fachada;
				
		private ImovelConta imovelProximo;
		
		ArrayList<ImovelConta> imoveisNaoLidos;
		
		ProgressDialog mProgressDialog = null;
		ArrayList<Foto> colecaoFotos;
		private int total;
		boolean sucess = false;
		private int posicao;
		
		@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	     // caso não permita múltiplas rota, é verificado se já existe arquivo de retorno.
    		// caso positivo, náo é permitido fazer login
    		if(!ConstantesSistema.PERMITE_BAIXAR_MULTIPLAS_ROTAS){
    			Util.apagarArquivoRetorno();
    		}
	        
	        ArquivoRetorno.montaArquivo = new StringBuilder();
    		  
	        
	        if (execute){	
		        setContentView(R.layout.menu);
		        
		        Fachada.setContext(getApplicationContext());
		        fachada = Fachada.getInstance();
	
		        lv = (ListView) findViewById(R.id.listMenu);
		        
		        lv.setOnItemClickListener(new OnItemClickListener() {
	
		        	/**
		        	 * EVENTOS DO MENU
		        	 */
					public void onItemClick(AdapterView<?> arg0, View view, int position,
							long id) {
						
						final int posicaoLista = position;
	
						menu = (Menu) view.getTag();
						
						if(menu.getNome().equals(getString(R.string.str_menu_impressora))){
				
				        	Intent intent = new Intent(MenuActivity.this, ListaImpressorasActivity.class);
				        	startActivity(intent);
				        	
							
						}else if(menu.getNome().equals(getString(R.string.str_menu_lista))){
													
							imovelProximo = fachada.buscarImovelContaPosicao(SistemaParametros.getInstancia().getIdImovelSelecionado(),true);
				        							
							Intent intent = new Intent(MenuActivity.this, TabsActivity.class);
							intent.putExtra("imovel", imovelProximo);
							startActivity(intent);
						
						}else if(menu.getNome().equals(getString(R.string.str_menu_consulta))){
							
							Intent intent = new Intent(MenuActivity.this, ListaImoveisActivity.class);
							startActivity(intent);
			
			            							
						}else if(menu.getNome().equals(getString(R.string.str_menu_finaliza))){
							
							finalizarArquivoMenu();
							
								
						}else if (menu.getNome().equals(getString(R.string.str_menu_apagar))) {
							new AlertDialog.Builder(MenuActivity.this)
							.setMessage( getString(R.string.str_menu_confirma_apagar) )
							.setPositiveButton(getString(R.string.str_sim), 
								new DialogInterface.OnClickListener() {
									public void onClick(
										DialogInterface dialog,
										int which) {
										
										AlertDialog.Builder alertDialog = new AlertDialog.Builder(MenuActivity.this);
										
										alertDialog.setTitle(getString(R.string.str_menu_apagar));
										alertDialog.setMessage(getString(R.string.str_senha_branco));
										
										// Set an EditText view to get user input 
							    		final EditText input = new EditText(getApplicationContext());
							    		input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
							    		input.setTransformationMethod(PasswordTransformationMethod.getInstance());
							    		alertDialog.setView(input);
										
										alertDialog.setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
										      public void onClick(DialogInterface dialog, int which) {
										 
										    	  String senha = input.getText().toString();
										    	  
										    	 if(fachada.validaSenhaApagar(senha)) {
								    		    	fachada.apagarBanco();
									  	        	Intent i = new Intent(MenuActivity.this,DownloadApkActivity.class);
									  	        	startActivity(i);
									  	        	finish();
										    		    
									    		  }else{
									    			// Informamos ao usuário que a senha foi incorreta
										        		new AlertDialog.Builder(MenuActivity.this)
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
								    		  public void onClick(DialogInterface dialog, int whichButton) {
								    		    // Canceled.
	
								
								    		  }
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
			
							
							}else if(menu.getNome().equals(getString(R.string.str_menu_enviar_finalizados))){
								
					    		enviarImoveisLidos(posicaoLista);
								
					           
							}else if(menu.getNome().equals(getString(R.string.str_menu_gerar_retorno))){
	
								boolean finalizaCompleto = true;
								if(validarFotosTransmitidas() && verificaImoveisNaolidos(finalizaCompleto) && verificarImoveisRevisitar()){
									
									  Intent i = new Intent(MenuActivity.this,FinalizaArquivoActivty.class);
						    		  i.putExtra(getString(R.string.str_extra_tipo_finalizacao), ArquivoRetorno.ARQUIVO_TODOS_OS_CALCULADOS+"");
						    		  startActivity(i);
						    		  finish();
								}							
					
							}else if((menu.getNome().equals(getString(R.string.str_menu_roteiro_offline))) || 
									(menu.getNome().equals(getString(R.string.str_menu_roteiro_online)))){
								
								if (SistemaParametros.getInstancia().getIndicadorTransmissaoOffline().equals(ConstantesSistema.NAO)) {
									
									new AlertDialog.Builder(MenuActivity.this)
					    			.setMessage(getString(R.string.str_menu_transformar_offline))
					    			.setPositiveButton(getString(R.string.str_sim), 
					    				new DialogInterface.OnClickListener() {
					    					public void onClick(DialogInterface dialog,int which) {
					    						fachada.atualizarRoteiroOnlineOffline(ConstantesSistema.SIM);
					    	                    lv.setSelection(posicaoLista);
					    	                    exibirMensagem(getString(R.string.str_sucesso_transformar_roteiro_offline), "", posicaoLista);
					    					}
					    			
					    			})
					    			.setNegativeButton(getString(R.string.str_nao), 
						    				new DialogInterface.OnClickListener() {
						    					public void onClick(DialogInterface dialog,int which) {
						    							
						    					}
					    			}).
					    			show();
															
								} else {
									new AlertDialog.Builder(MenuActivity.this)
					    			.setMessage(getString(R.string.str_menu_transformar_online))
					    			.setPositiveButton(getString(R.string.str_sim), 
					    				new DialogInterface.OnClickListener() {
					    					public void onClick(DialogInterface dialog,int which) {
					    						fachada.atualizarRoteiroOnlineOffline(ConstantesSistema.NAO);
					    						lv.setSelection(posicaoLista);
					    						exibirMensagem(getString(R.string.str_sucesso_transformar_roteiro_online), "", posicaoLista);
					    					}
					    			
					    			})
					    			.setNegativeButton(getString(R.string.str_nao), 
						    				new DialogInterface.OnClickListener() {
						    					public void onClick(DialogInterface dialog,int which) {
						    											    						
						    					}
					    			}).
					    			show();
								}							
							} else if (menu.getNome().equals(getString(R.string.str_menu_finaliza_incompleto))){							
										
							  perguntaSenha(getString(R.string.str_menu_finaliza_incompleto),getString(R.string.str_menu_confirma_finalizar), ArquivoRetorno.ARQUIVO_INCOMPLETO);
	
							}else if(menu.getNome().equals(getString(R.string.str_menu_relatorio))){
								
								Intent i = new Intent(MenuActivity.this,RelatorioActivity.class);
	    						startActivity(i);
	
							}else if(menu.getNome().equals(getString(R.string.str_menu_sair))){
								
								
								AlertDialog.Builder alert = new AlertDialog.Builder(MenuActivity.this);
			        			alert.setTitle(getString(R.string.str_menu_sair));
			        			alert.setMessage(getString(R.string.str_menu_confirma_sair));
			        			
			        			alert.setPositiveButton(R.string.str_sim, new DialogInterface.OnClickListener(){
	
			        				public void onClick(DialogInterface arg0, int arg1) {
			        				
			        				  //Util.chamarTelaLogin(getApplicationContext(), true);
			        				  Util.sairAplicacao(MenuActivity.this);
		        			          
			        				}
			            			
			        			});
			        			
			        			//Caso nao confirme!
			        			alert.setNegativeButton(R.string.str_nao, new DialogInterface.OnClickListener() {
			        				public void onClick(DialogInterface dialog, int which) {
			        					
			        				}
			        			});
			        			
			        			alert.show();
								
							}else if(menu.getNome().equals(getString(R.string.str_menu_rota))){
								
								
								if(SistemaParametros.getInstancia().getIndicadorRotaMarcacaoAtiva().equals(ConstantesSistema.SIM)){
									
									AlertDialog.Builder alert = new AlertDialog.Builder(MenuActivity.this);
				        			alert.setTitle("");
				        			alert.setMessage(getString(R.string.str_rota_marcacao_desativar));
				        			
				        			alert.setPositiveButton(R.string.str_sim, new DialogInterface.OnClickListener(){
	
				        				public void onClick(DialogInterface arg0, int arg1) {
				        					exibirMensagem(fachada.atualizarIndicadorRotaMarcacaoAtiva(ConstantesSistema.NAO), getString(R.string.str_menu_rota), posicaoLista);
				        				}
				            			
				        			});
				        			
				        			//Caso nao confirme!
				        			alert.setNegativeButton(R.string.str_nao, new DialogInterface.OnClickListener() {
				        				public void onClick(DialogInterface dialog, int which) {
				        					
				        				}
				        			});
				        			
				        			alert.show();
									
									
								}else{
									
									AlertDialog.Builder alert = new AlertDialog.Builder(MenuActivity.this);
				        			alert.setTitle("");
				        			alert.setMessage(getString(R.string.str_rota_marcacao_ativar));
				        			
				        			alert.setPositiveButton(R.string.str_sim, new DialogInterface.OnClickListener(){
	
				        				public void onClick(DialogInterface arg0, int arg1) {
			        						//ATIVAR ROTA MARCACAO
				        					exibirMensagem(fachada.atualizarIndicadorRotaMarcacaoAtiva(ConstantesSistema.SIM), getString(R.string.str_menu_rota), posicaoLista);	
				        				}
				            			
				        			});
				        			
				        			//Caso nao confirme!
				        			alert.setNegativeButton(R.string.str_nao, new DialogInterface.OnClickListener() {
				        				public void onClick(DialogInterface dialog, int which) {
				        					
				        				}
				        			});
				        			
				        			alert.show();
									
								}
	
							}else if(menu.getNome().equals(getString(R.string.str_menu_inverter_roteiro))){
								Fachada.getInstance().inverterRoteiro();
								exibirMensagem(getString(R.string.str_menu_roteiro_invertido),"", posicaoLista);
							}else if(menu.getNome().equals(getString(R.string.str_menu_exportar_bd))){
								Date d = new Date();
								Long getTime = d.getTime();
								ArquivoRetorno arquivo = new ArquivoRetorno();
								String nome = arquivo.getCaminhoArquivoRetorno(ArquivoRetorno.ARQUIVO_COMPLETO);
								new ExportBancoDados().exportarBancoNovoNome(getTime, nome);
							
								exibirMensagem(getString(R.string.str_alert_exportar_bd),"", posicaoLista);
							} else if ( menu.getNome().equals(getString( R.string.str_menu_contraste ) ) ){
								Intent intent = new Intent(MenuActivity.this, ConstrasteActivity.class);
								startActivity(intent);								
							}
							
						}
				});
	        }
	    }

    	protected boolean verificarImoveisRevisitar() {
    		boolean retorno = false;
    		ArrayList<ImovelRevisitar> imovelRevisitar = Fachada.getInstance().buscarImovelNaoRevisitado();
    		if(imovelRevisitar != null){
    			new AlertDialog.Builder(MenuActivity.this)
    			.setMessage("Erro Roteiro, Ainda ha imóveis a revisitar!")
    			.setNeutralButton(getString(android.R.string.ok), 
    				new DialogInterface.OnClickListener() {
    					public void onClick(DialogInterface dialog,int which) {
    						    						
    					}
    			}).show();
    		}else{
    			retorno = true;    			
    		}
    		return retorno;
			
		}

		//[FS0001] Verifica Se Todos Imóveis Foram Calculados 
	    protected boolean verificaImoveisNaolidos(boolean finalizaCompleto) {
    		boolean retorno = true;

    		if(finalizaCompleto){
				imoveisNaoLidos = Fachada.getInstance().buscarImovelContasNaoLidos();
			
	    		if(imoveisNaoLidos != null && imoveisNaoLidos.size()!= 0 ){
			    	retorno = false;					
			    	
					new AlertDialog.Builder(MenuActivity.this)
					.setMessage(getString(R.string.str_menu_alert_imoveis_finalizar,imoveisNaoLidos.size(), 
	    					SistemaParametros.getInstancia().getQtdImoveis()))   			
	    			.setNeutralButton(getString(android.R.string.ok), 
	    				new DialogInterface.OnClickListener() {
	    					public void onClick(DialogInterface dialog,int which) {
	    						
	    						Intent i = new Intent(MenuActivity.this,TabsActivity.class);
	    						i.putExtra("imovel", imoveisNaoLidos.get(0));
	    						startActivity(i);
	    						
	    					}
	    			}).show();
					
				}
    		}
			return retorno;
		}	 
	    
	    @Override
	    protected void onResume() {
	    
	    	super.onResume();
	    
	    	if (execute && Fachada.getInstance().verificarExistenciaBancoDeDados()){
		    	ArrayList<Menu> arrayListMenu = new ArrayList<Menu>();
		    	
		    	Menu menu1 = new Menu(getString(R.string.str_menu_lista), getString(R.string.str_menu_lista_legenda));		
		    	arrayListMenu.add(menu1);
		    	
		    	Menu menu2 = new Menu(getString(R.string.str_menu_consulta), getString(R.string.str_menu_consulta_legenda));
		    	arrayListMenu.add(menu2);
		    	
		    	Menu menu3 = new Menu(getString(R.string.str_menu_finaliza), getString(R.string.str_menu_finaliza_legenda));
		    	arrayListMenu.add(menu3);
		    	
		    	Menu menu4 = new Menu(getString(R.string.str_menu_relatorio), getString(R.string.str_menu_relatorio_legenda));
		    	arrayListMenu.add(menu4);
		    	
		    	Menu menu5 = new Menu(getString(R.string.str_menu_impressora), getString(R.string.str_menu_impressora_legenda));
		    	arrayListMenu.add(menu5);
		    	
		    	Menu menu6 = new Menu(getString(R.string.str_menu_apagar), getString(R.string.str_menu_apagar_legenda));
		        arrayListMenu.add(menu6);
		    	
		        Menu menu7 = new Menu(getString(R.string.str_menu_finaliza_incompleto), getString(R.string.str_menu_finaliza_incompleto_legenda));	    	
		    	arrayListMenu.add(menu7);
		    	
		    	Menu menu8 = new Menu(getString(R.string.str_menu_enviar_finalizados), getString(R.string.str_menu_enviar_finalizados_legenda));	
		    	arrayListMenu.add(menu8);
		    	
		    	if (SistemaParametros.getInstancia().getIndicadorTransmissaoOffline().equals(ConstantesSistema.NAO)){
		    		Menu menu9 = new Menu(getString(R.string.str_menu_roteiro_offline), getString(R.string.str_menu_roteiro_offline_legenda));
		    		arrayListMenu.add(menu9);
		    	} else if (SistemaParametros.getInstancia().getIndicadorTransmissaoOffline().equals(ConstantesSistema.SIM)){
		    		Menu menu9 = new Menu(getString(R.string.str_menu_roteiro_online), getString(R.string.str_menu_roteiro_online_legenda));
		    		arrayListMenu.add(menu9);
		    	}
		    	
		    	Menu menu10 = new Menu(getString(R.string.str_menu_gerar_retorno), getString(R.string.str_menu_gerar_retorno_legenda));
		    	arrayListMenu.add(menu10);
	
		    	//Caso a empresa tenha rota de marcação, adicionamos a opcao
		    	//rotra de marcacao no menu.
				if(SistemaParametros.getInstancia().getIndicadorRotaMarcacao().equals(ConstantesSistema.SIM)){
					String statusRotaMarcacao = getString(R.string.str_ativar) ;
			    	
			    	 
			    	if(SistemaParametros.getInstancia().getIndicadorRotaMarcacaoAtiva().equals(ConstantesSistema.SIM)){
			    		statusRotaMarcacao = getString(R.string.str_desativadar);
			    	}
			    	
					Menu menu11 = new Menu(getString(R.string.str_menu_rota), getString(R.string.str_menu_rota_legenda, statusRotaMarcacao));
					arrayListMenu.add(menu11);
		    	}
				
				Menu menuSair = new Menu(getString(R.string.str_menu_inverter_roteiro), getString(R.string.str_menu_inverter_roteiro_legenda));
				arrayListMenu.add(menuSair);
				
				Menu menu12 = new Menu(getString(R.string.str_menu_exportar_bd), getString(R.string.str_menu_exportar_bd_legenda));	
		    	arrayListMenu.add(menu12);
		    	
				Menu menu13 = new Menu(getString(R.string.str_menu_contraste), getString(R.string.str_menu_contraste_legenda));
		    	arrayListMenu.add(menu13);
		    	
				Menu menu14 = new Menu(getString(R.string.str_menu_sair), getString(R.string.str_menu_sair_legenda));
		    	arrayListMenu.add(menu14);
		    	    
				adapter = new ListaMenuAdapter(MenuActivity.this, arrayListMenu);
	
				lv.setAdapter(adapter);  
	    	}
	    	else{
	    		Intent i = new Intent(MenuActivity.this, DownloadApkActivity.class);
				MenuActivity.this.startActivity(i);
				finish();
	    	}
	    }
	    
	    private void perguntaSenha(String mensagemTitulo,String msgApagar,final short tipoFinalizacao){

	    	boolean finalizaCompleto = false;

	    	if(verificaImoveisNaolidos(finalizaCompleto)){
	    	
		    	AlertDialog.Builder alertDialog = new AlertDialog.Builder(MenuActivity.this);
				
				alertDialog.setTitle(mensagemTitulo);
				alertDialog.setMessage(msgApagar);
				
				
				// Set an EditText view to get user input 
	    		final EditText input = new EditText(getApplicationContext());
	    		input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
	    		input.setTransformationMethod(PasswordTransformationMethod.getInstance());
	    		alertDialog.setView(input);
				
				alertDialog.setPositiveButton(getString(R.string.str_geral_bt_confirmar), new DialogInterface.OnClickListener() {
				      public void onClick(DialogInterface dialog, int which) {
				 
				    	  String senha = input.getText().toString();	    	  
				    		  
					    	  if(fachada.validaSenhaAdm(senha)) {
					    		  Intent i = new Intent(MenuActivity.this,FinalizaArquivoActivty.class);
					    		  i.putExtra(getString(R.string.str_extra_tipo_finalizacao), tipoFinalizacao+"");
					    		  startActivity(i);
					    		  finish();
					    		  			    		    
					    	  }else{
				    			// Informamos ao usuário que a senha foi incorreta
					        		new AlertDialog.Builder(MenuActivity.this)
					    			.setTitle(getString(R.string.str_menu_finaliza_incompleto))
					    			.setMessage(getString(R.string.str_senha_invalida))
					    			.setNeutralButton( getString(android.R.string.ok), 
					    				new DialogInterface.OnClickListener() {
					    					public void onClick(DialogInterface dialog,int which) {
					    						
					    					}
					    			}).show();
					    	  }
				      	} 
				     }); 
			
			alertDialog.setNegativeButton(getString(R.string.str_geral_bt_cancela), new DialogInterface.OnClickListener() {
	    		  public void onClick(DialogInterface dialog, int whichButton) {
	    		    // Canceled.
	    		  }
	    		});
			
			alertDialog.show();
	    }
		
	    }
	    
	    
	    private void exibirMensagem(String mensagem, String titulo, final int posicaoLista){

			
			AlertDialog.Builder alert = new AlertDialog.Builder(MenuActivity.this);
			alert.setTitle(titulo);
			alert.setMessage(mensagem);
			alert.setNeutralButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					onResume();	
					lv.setSelection(posicaoLista);
					return;
				}
			});
			alert.show();
			
		}
	    
	    @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

	        super.onActivityResult(requestCode, resultCode, data);

	        Util.existeSDCARD();

	        // verifica se é o código de foto
	        if (requestCode == 9595) {

	            // verifica se a foto foi salva
	            if (resultCode == RESULT_OK) {
	             
	            }
	        }

	    }
	    
	    
	    
	    protected boolean validarFotosTransmitidas() {
			
	    	try {
	    		
	    		colecaoFotos = Fachada.getInstance().buscarFotosPendentes();
		
	    		if ( colecaoFotos != null && !colecaoFotos.isEmpty() && !sucess ) {
    			
					          mProgressDialog = new ProgressDialog(MenuActivity.this) {
					              @Override
					              public void onBackPressed() {
					              }
					
					              @Override
					              public boolean onSearchRequested() {
					                  return false;
					              }
					          };
					          total = colecaoFotos.size();
		                      mProgressDialog.setTitle("Enviando as fotos");
		                      mProgressDialog.setMessage("Aguarde...");
		                      mProgressDialog.setIndeterminate(false);
		                      mProgressDialog.setMax(total);
		                      mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		                      mProgressDialog.show();

                              AsyncTask<Object, Object, Object> taskDownloadFile = new AsyncTask<Object, Object, Object>() {

                                  @Override
                                  protected Object doInBackground(
                                          Object... arg0) {
                                	  
		    							for ( int j = 0; j < colecaoFotos.size(); j ++ ){
		    								
		    								Foto foto = colecaoFotos.get( j );
											
		    	    						try {
													sucess = fachada.enviarFotosOnline(foto);
													if ( sucess) {
														mProgressDialog.setProgress(j);
													}
												} catch (FachadaException e) {
													e.printStackTrace();
												} catch (NegocioException e) {
													e.printStackTrace();
												}
		    							}
		    							mProgressDialog.dismiss();
		    							
		    							return sucess;
                                  }
                                  @Override
                                protected void onPostExecute(Object result) {
                                	
                                	super.onPostExecute(result);
                                	

                        	    	try {
                        	    		
                        	    		colecaoFotos = Fachada.getInstance().buscarFotosPendentes();
                        		
                        	    		if ( colecaoFotos == null || colecaoFotos.isEmpty() ) {
                                			finalizarArquivoMenu();
                                		} else {
                        	    			// Informamos ao usuário que foi tudo certo
                        	    			new AlertDialog.Builder(MenuActivity.this)
                        	    				.setTitle("Erro no envio das fotos On-line.")
                        	    				.setMessage("Confirme abaixo para enviar as fotos ou cancele o envio.")
                                                .setIcon(R.drawable.warning)
                                                .setPositiveButton("Enviar",
                                                new DialogInterface.OnClickListener() {
                                                  @Override
                                                  public void onClick( DialogInterface dialog, int which ) {
                                                	  sucess = false;
                                                	  finalizarArquivoMenu();
                                                  }
                                                })
                                                .setNegativeButton("Cancelar",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(
                                                            DialogInterface dialog,
                                                            int which) {
                                                  	  
                                                  	  sucess = true;
                                                  	  finalizarArquivoMenu();
                                                    }
                                                })
                                                .show();
                                		}
                        	    	} catch (FachadaException e) {
                        				e.printStackTrace();
                        			} catch (NegocioException e) {
                        				e.printStackTrace();
                        			}
                                  }
                              };

                              taskDownloadFile.execute();
	    		} else {
	    			return true;
	    		}
			
	    	} catch (FachadaException e) {
				e.printStackTrace();
			} catch (NegocioException e) {
				e.printStackTrace();
			}
	    	
            
	    	return sucess;
        }

	    public void finalizarArquivoMenu(){
	    	//[FS0001]
			boolean finalizaCompleto = true;
			if(validarFotosTransmitidas() && verificaImoveisNaolidos(finalizaCompleto) && verificarImoveisRevisitar()){
				
				Intent i = new Intent(MenuActivity.this,FinalizaArquivoActivty.class);
		        i.putExtra(getString(R.string.str_extra_tipo_finalizacao), ArquivoRetorno.ARQUIVO_COMPLETO+"");
		        startActivity(i);   
		        finish();
			 
			}	
	    }
	    
	    public void enviarImoveisLidos(int posicaoLista){
	    	ArrayList<Integer> arrayListImovelConta = Fachada.getInstance().buscarIdsImoveisLidosNaoEnviadosNaoCondominio();
	    	
	    	posicao = posicaoLista;
	    	
	    	if ( validarFotosNaoEnviadas() ) {
		    	
	    		if(arrayListImovelConta != null && arrayListImovelConta.size() != 0 ){
				
					Intent i = new Intent(MenuActivity.this,FinalizaArquivoActivty.class);
			        i.putExtra(getString(R.string.str_extra_tipo_finalizacao), ArquivoRetorno.ARQUIVO_LIDOS_ATE_AGORA+"");
			        startActivity(i);  
		    		finish();
			        
	    		}else{
	    			
	    			exibirMensagem(getString(R.string.str_menu_alert_nao_lido),"", posicaoLista);
	    		}
	    	}
	    }
	    
	    
	    protected boolean validarFotosNaoEnviadas() {
			
	    	try {
	    		
	    		colecaoFotos = Fachada.getInstance().buscarFotosPendentes();
		
	    		if ( colecaoFotos != null && !colecaoFotos.isEmpty() && !sucess ) {
    			
					          mProgressDialog = new ProgressDialog(MenuActivity.this) {
					              @Override
					              public void onBackPressed() {
					              }
					
					              @Override
					              public boolean onSearchRequested() {
					                  return false;
					              }
					          };
					          total = colecaoFotos.size();
					          mProgressDialog.setIcon(R.drawable.arqretorno);
		                      mProgressDialog.setTitle("Enviando as fotos");
		                      mProgressDialog.setMessage("Aguarde...");
		                      mProgressDialog.setIndeterminate(false);
		                      mProgressDialog.setMax(total);
		                      mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		                      mProgressDialog.show();

                              AsyncTask<Object, Object, Object> taskDownloadFile = new AsyncTask<Object, Object, Object>() {

                                  @Override
                                  protected Object doInBackground(
                                          Object... arg0) {
                                	  
		    							for ( int j = 0; j < colecaoFotos.size(); j ++ ){
		    								
		    								Foto foto = colecaoFotos.get( j );
											
		    	    						try {
													sucess = fachada.enviarFotosOnline(foto);
													if ( sucess) {
														mProgressDialog.setProgress(j);
													}
												} catch (FachadaException e) {
													e.printStackTrace();
												} catch (NegocioException e) {
													e.printStackTrace();
												}
		    							}
		    							mProgressDialog.dismiss();
		    							
		    							return sucess;
                                  }
                                  @Override
                                protected void onPostExecute(Object result) {
                                	super.onPostExecute(result);
                                	

                        	    	try {
                        	    		
                        	    		colecaoFotos = Fachada.getInstance().buscarFotosPendentes();
                        		
                        	    		if ( colecaoFotos == null || colecaoFotos.isEmpty() ) {
                        	    			enviarImoveisLidos(posicao);;
                                		} else {
                        	    			// Informamos ao usuário que foi tudo certo
                        	    			new AlertDialog.Builder(MenuActivity.this)
                        	    				.setTitle("Erro no envio das fotos On-line.")
                        	    				.setMessage("Confirme abaixo para enviar as fotos ou cancele o envio.")
                                                .setIcon(R.drawable.warning)
                                                .setPositiveButton("Enviar",
                                                new DialogInterface.OnClickListener() {
                                                  @Override
                                                  public void onClick( DialogInterface dialog, int which ) {
                                                	  sucess = false;
                                                	  enviarImoveisLidos(posicao);;
                                                  }
                                                })
                                                .setNegativeButton("Cancelar",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(
                                                            DialogInterface dialog,
                                                            int which) {
                                                  	  
                                                  	  sucess = true;
                                                  	enviarImoveisLidos(posicao);;
                                                    }
                                                })
                                                .show();
                                		}
                        	    	} catch (FachadaException e) {
                        				e.printStackTrace();
                        			} catch (NegocioException e) {
                        				e.printStackTrace();
                        			}
                                  }
                              };

                              taskDownloadFile.execute();
	    		} else {
	    			return true;
	    		}
			
	    	} catch (FachadaException e) {
				e.printStackTrace();
			} catch (NegocioException e) {
				e.printStackTrace();
			}
	    	
            
	    	return sucess;
        }
		
	    
	    @Override
	    public void onBackPressed() { 
			//Faz nada
		}
	    
	    
}