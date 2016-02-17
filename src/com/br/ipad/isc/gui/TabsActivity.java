
package com.br.ipad.isc.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.ConsumoHistorico;
import com.br.ipad.isc.bean.Foto;
import com.br.ipad.isc.bean.HidrometroInstalado;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.bean.LeituraAnormalidade;
import com.br.ipad.isc.bean.SistemaParametros;
import com.br.ipad.isc.bean.Foto.Fotos;
import com.br.ipad.isc.bean.helpers.CameraHelper;
import com.br.ipad.isc.controladores.ControladorAlertaValidarErro;
import com.br.ipad.isc.controladores.ControladorAlertaValidarFoto;
import com.br.ipad.isc.excecoes.ControladorException;
import com.br.ipad.isc.excecoes.FachadaException;
import com.br.ipad.isc.excecoes.NegocioException;
import com.br.ipad.isc.fachada.Fachada;
import com.br.ipad.isc.util.ConstantesSistema;
import com.br.ipad.isc.util.Util;

public class TabsActivity extends BaseTabActivity implements  OnTabChangeListener  {

	public static TabHost mTabHost;
	protected ImovelConta imovel;
			
	private Fachada fachada = Fachada.getInstance();
	SistemaParametros sistemaParametros = SistemaParametros.getInstancia();
	
	public static HidrometroInstalado hidrometroInstaladoAgua;
	public static HidrometroInstalado hidrometroInstaladoPoco;
	public static LeituraAnormalidade anormalidadeAgua;
	public static LeituraAnormalidade anormalidadePoco;
	public static LeituraAnormalidade anormalidade = null;	
	public static boolean fotoAgua = false;
	public static boolean chamarProximo = true;
	public static Integer medicaoTipo = null;
	public static String abaAtual = null;
	
	private Button proximo;
	private Button anterior;
	
	private TextView totalImoveis;

	private TextView posicao;
	
	protected Button calcular;
	protected Button imprimir;
	protected Button imprimirCaern;
	protected Button calcularCaern;
	protected Button imprimir2ViaCondominio;
	

	private String mensagemFoto;
	protected Button visualizar;

//	private ImovelConta imovelMacro;
	
	
	private void setupTabHost() {
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();
		
	}

	/** Called when the activity is first created. */
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		if (execute){	
			fotoAgua = false;
			// construct the tabhost
			setContentView(R.layout.tabs_activity);
			Fachada.setContext(this);
			
			// Para o teclado não aparecer ao entrar na tela
		    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	
		    imovel = (ImovelConta) getIntent().getSerializableExtra("imovel");
		    if(imovel == null){
		    	imovel = fachada.buscarPrimeiroImovel();
				
		    }
		    
		    imovel = (ImovelConta) Fachada.getInstance()
					.pesquisarPorId(imovel.getId(), new ImovelConta());
		    			    	    
	        setupTabHost();
			setUpTab();
			
			setUpWidgets();
			
			atualizarPosicoes();
			
			setUpImprimir();
			setUpCalcularCaern();
			setUpImprimirCaern();
			
			//Busca parametro 'Posicao'
			Integer posicao = (Integer) getIntent().getSerializableExtra("posicao");
			if (posicao!=null){
				getIntent().removeExtra("posicao");
				iniciarProximaIntent(posicao, true);			
			}			
	        
	        verificarImovelCondominio();
	        verificarBloqueioRecalcularConta();
	         
		}
	}
	
	
	private void atualizarPosicoes() {
		totalImoveis = (TextView) findViewById(R.id.total);
	    posicao = (TextView) findViewById(R.id.posicao);
	    
	    if(imovel!=null){	
			posicao.setText(imovel.getPosicao()+"/");
			totalImoveis.setText(SistemaParametros.getInstancia().getQtdImoveis().toString());
		}		
	}

	private void setUpTab() {
		   
	    Intent itHidrometro = new Intent(this, HidrometroAguaActivity.class);
	    itHidrometro.putExtra("imovel", imovel);
	    
	    Intent itHidrometroEsgoto = new Intent(this, HidrometroEsgotoActivity.class);
	    itHidrometroEsgoto.putExtra("imovel", imovel);
	    
	    Intent itConta = new Intent(this, ContaActivity.class);
	    itConta.putExtra("imovel", imovel);
	    	    
	    try {
	    	hidrometroInstaladoAgua = Fachada.getInstance().buscarHidrometroInstaladoPorImovelTipoMedicao(imovel.getId(), ConstantesSistema.LIGACAO_AGUA);
	    	hidrometroInstaladoPoco = Fachada.getInstance().buscarHidrometroInstaladoPorImovelTipoMedicao(imovel.getId(), ConstantesSistema.LIGACAO_POCO);
	    	
	    	if(hidrometroInstaladoAgua != null && hidrometroInstaladoAgua.getIndcParalizacaoLeitura() != null && 
	    			hidrometroInstaladoAgua.getIndcParalizacaoLeitura().equals(ConstantesSistema.SIM)){
	    		hidrometroInstaladoAgua = null;
	    	}
	    	
	    	if(hidrometroInstaladoPoco != null && hidrometroInstaladoPoco.getIndcParalizacaoLeitura() != null && 
	    			hidrometroInstaladoPoco.getIndcParalizacaoLeitura().equals(ConstantesSistema.SIM)){
	    		hidrometroInstaladoPoco = null;
	    	}
	    	
	    	// 1.1	    
			if(hidrometroInstaladoAgua != null){	  									
				 setupTab(new TextView(this), getString(R.string.str_tab_hidrometro), itHidrometro);				 
			}
			
			//1.2
			if(hidrometroInstaladoPoco != null){ 		
				setupTab(new TextView(this), getString(R.string.str_tab_poco), itHidrometroEsgoto);				
			} 
			
			//1.3
			if (hidrometroInstaladoAgua == null && hidrometroInstaladoPoco == null) {
				if(imovel.getIndcImovelImpresso().equals(ConstantesSistema.NAO) && imovel.getIndcNaoPermiteImpressao().equals(ConstantesSistema.NAO)){
					fachada.calcularConta(imovel,true,chamarProximo);
				}
				setupTab(new TextView(this), getString(R.string.str_tab_conta), itConta);
					
			}
			
			// Verifica se houve um erro ao calcular e seta a tab inicial de acordo com o erro
			if(getIntent().getStringExtra("agua")!= null){
				mTabHost.setCurrentTabByTag(getString(R.string.str_tab_hidrometro));
			}else if(getIntent().getStringExtra("esgoto")!= null) {
				mTabHost.setCurrentTabByTag(getString(R.string.str_tab_poco));
			}
			
			mTabHost.setOnTabChangedListener(this);
			
	    } catch (FachadaException e) {
			Log.e( ConstantesSistema.CATEGORIA , e.getMessage());
			e.printStackTrace();
		}	
	}
	
     private void setupTab(final View view, final String tag, Intent it) {
    	
    	View tabview = createTabView(mTabHost.getContext(), tag);
		TabSpec setContent = mTabHost.newTabSpec(tag).setIndicator(tabview).setContent(it); {} ;
		mTabHost.addTab(setContent);
     }
     
     /**
 	 * Verifica se a leitura e/ou anormalidade do imóvel 
 	 * foi alterada
 	 * 
 	 * @return
 	 */
 	public boolean leituraAlterada(){
 		boolean agua = false;
 		boolean poco = false;
 		EditText leitura = null;
 		EditText anormalidade = null;
 		if (hidrometroInstaladoAgua!=null){
 			leitura = HidrometroBaseActivity.leituraAgua;
 			anormalidade = HidrometroBaseActivity.idAnormalidadeAgua;
 			
 			//Verifica se a leitura ou anormalidade foi modficada ou  estao em branco
 			if(!leitura.getText().toString().trim()
 					.equals(hidrometroInstaladoAgua.getLeitura() != null ? hidrometroInstaladoAgua.getLeitura().toString():"")
 					||
 				!anormalidade.getText().toString().trim()
 					.equals(hidrometroInstaladoAgua.getAnormalidade() != null ?hidrometroInstaladoAgua.getAnormalidade().toString():"")
 					||
 					( anormalidade.getText().toString().trim().equals("") && leitura.getText().toString().trim().equals("") ) 
 					){
 				agua = true;
 			}else{
 				agua = false;
 			}
 			
 			
 		}if (hidrometroInstaladoPoco!=null){
 			leitura = HidrometroBaseActivity.leituraPoco;
 			anormalidade = HidrometroBaseActivity.idAnormalidadePoco;
 			//Verifica se a leitura ou anormalidade foi modficada ou  estao em branco
 			if(!leitura.getText().toString().trim()
 					.equals(hidrometroInstaladoPoco.getLeitura() != null ? hidrometroInstaladoPoco.getLeitura().toString():"")
 					||
 				!anormalidade.getText().toString().trim()
 					.equals(hidrometroInstaladoPoco.getAnormalidade() != null ?hidrometroInstaladoPoco.getAnormalidade().toString():"")
 					||
 					( anormalidade.getText().toString().trim().equals("") && leitura.getText().toString().trim().equals("") ) 
 					){
 				poco = true;
 			}else{
 				poco = false;
 			}
 		}
 			
 		//Imóveis não medidos só devem ser recalculados se ainda não foi impresso
 		if(hidrometroInstaladoPoco == null && hidrometroInstaladoAgua == null){ 
 			if(imovel.getIndcImovelCalculado().equals(ConstantesSistema.NAO)){
 				agua =  true;
 			}
 		}
 		
 		//Se agua OU poco tiver sido alterado retorna TRUE
 		return agua || poco;
 	}
	
	private void setUpWidgets() {
		totalImoveis = (TextView) findViewById(R.id.total);
        posicao = (TextView) findViewById(R.id.posicao);	
        
        proximo = (Button) findViewById(R.id.proximo);
        proximo.setHeight(45);
        proximo.setBackgroundResource(R.drawable.botao_bg_selector);
        
		anterior = (Button) findViewById(R.id.anterior);
        anterior.setHeight(45);
        anterior.setBackgroundResource(R.drawable.botao_bg_selector);
        
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.bgproximo);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(bmp);
        bitmapDrawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);        
        LinearLayout layout = (LinearLayout) findViewById(R.id.barra);
        layout.setBackgroundDrawable(bitmapDrawable);
        
		
		if(imovel!=null){	
			posicao.setText(imovel.getPosicao()+"/");
			totalImoveis.setText(SistemaParametros.getInstancia().getQtdImovelCondominio()+"");
		}
				
		proximo.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View arg0) {
				chamarProximo = true;
				chamaProximo(imovel.getPosicao(),chamarProximo);				
			}

		});
		
		anterior.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View arg0) {
				chamarProximo = false;
				chamaProximo(imovel.getPosicao(),chamarProximo);		
			}
		});

		calcular = (Button) findViewById(R.id.calcular);
		calcular.setOnClickListener(new OnClickListener() {
				
			public void onClick(View arg0) {	
			
				Object[] retornoFoto =  validarFoto(0,true,ConstantesSistema.BOTAO_CALCULAR);
				boolean existeFoto = (Boolean) retornoFoto[0];
				String mensagem = (String) retornoFoto[1];
				//Verifica se anormalidade obriga usuario a registrar foto da anormalidade.
				if ( verificaFotoValidada(existeFoto,mensagem)){
									
					validaCalcular(chamarProximo);
									
				}
			}

		});			
		
		
		
		visualizar = (Button) findViewById(R.id.visualizar);
		//fotos = (Button) findViewById(R.id.fotos);
		
		visualizar.setOnClickListener(new OnClickListener() {			
			
			public void onClick(View arg0) {
				Intent i = new Intent(TabsActivity.this,InformacoesGeraisActivity.class);
			    i.putExtra("imovel", imovel);
				startActivity(i);
			}
		});
		
		setaBotaoImprimir();		
			
    }    

	 
		
	private void setaBotaoImprimir() {
		

		
		if(hidrometroInstaladoAgua != null && HidrometroBaseActivity.leituraAgua != null){
			
			setKeylistener(HidrometroBaseActivity.leituraAgua);
		}
		if(hidrometroInstaladoPoco != null && HidrometroBaseActivity.leituraPoco != null){
			setKeylistener(HidrometroBaseActivity.leituraPoco);			
		}
		
	}

	private void setKeylistener(EditText campo) {
			
		campo.setOnKeyListener(new OnKeyListener() {
				
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if(keyCode == KeyEvent.KEYCODE_ENTER  && event.getAction()==0){
					if ( imovel.isCondominio() ){
						chamarProximo = true;
						chamaProximo(imovel.getPosicao(),chamarProximo);
					} else {
						clickBotaoImprimir();
					}
				}
				return false;
			}
		});
		
	}
	
	

	protected boolean verificaFotoValidada(boolean existeFoto, String mensagem) {
		boolean retorno = false;
	
		if( existeFoto && (mensagem == null || mensagem.length() == 0) && !fotoAgua ){
			retorno = true;
		}
		return retorno;
	}

	public void validaCalcular(boolean proximo) {
		//valida a leitura
		boolean contaCalculada = false;
			
		//atualiza o histórico
		atualizarHistorico();
		
		//[UC1303][SB0005] Validar Leitura
		if(TabsActivity.hidrometroInstaladoAgua != null){
			contaCalculada = fachada.validarLeituraMensagem(hidrometroInstaladoAgua,imovel,0,false,proximo);

		}
		if(TabsActivity.hidrometroInstaladoAgua == null && TabsActivity.hidrometroInstaladoPoco != null){
			contaCalculada = fachada.validarLeituraMensagem(hidrometroInstaladoPoco,imovel,0,false,proximo);		
		}                     
		
    	// Se conta calculada
    	if(contaCalculada ){
    		chamaProximo(imovel.getPosicao(),proximo);	
    	} 
	}

	/**
	 * Metodo responsável por validar a obrigatoriedade de registar foto de acordo com anormalidade informada, caso tenha sido informada.
	 * 
	 * @author Arthur Carvalho
	 * alterado por Fernanda Almeida em 16/01/2013
	 * @param proximo 
	 * @param posicao 
	 * @since 06/09/2012
	 * @return
	 */
	public Object[] validarFoto(Integer posicao, boolean proximo, int botaoClicado){
	
		boolean existeFoto = true;
		Object[] retorno = new Object[2];
		abaAtual = mTabHost.getCurrentTabTag();
		
		String selection = null;
		String[] selectionArgs = null;
		ArrayList<Foto> listaFoto = null;
				
		Collection<LeituraAnormalidade> colecaoLeituraAnormalidade = new ArrayList<LeituraAnormalidade>();
		
		if ( anormalidadeAgua != null && hidrometroInstaladoAgua != null && !fotoAgua) {
			colecaoLeituraAnormalidade.add(anormalidadeAgua);
		}  
		
		if ( anormalidadePoco != null && hidrometroInstaladoPoco != null ) {
			colecaoLeituraAnormalidade.add(anormalidadePoco);
		}
		
		if ( colecaoLeituraAnormalidade != null && !colecaoLeituraAnormalidade.isEmpty() ) {
			
			Iterator<LeituraAnormalidade> iteratorLeituraAnormalidade = colecaoLeituraAnormalidade.iterator();
			while ( iteratorLeituraAnormalidade.hasNext() ) { 
				
				anormalidade = (LeituraAnormalidade) iteratorLeituraAnormalidade.next();
				if (anormalidade != null  && anormalidade.getId() != null ) {

					defineMensagemFotoTipo();
					Integer idAnormalidade = anormalidade.getId();
					
					LeituraAnormalidade leituraAnormalidade =  
							(LeituraAnormalidade) Fachada.getInstance()
							.pesquisarPorId(idAnormalidade, new LeituraAnormalidade());
												
					if ( existeFoto && leituraAnormalidade != null && leituraAnormalidade.getIndicadorFotoObrigatoria() != null && 
							leituraAnormalidade.getIndicadorFotoObrigatoria().equals(ConstantesSistema.SIM) ) {
						
						try {
							String mensagem = "";
							if ( anormalidadeAgua != null && hidrometroInstaladoAgua != null && anormalidadeAgua.equals(anormalidade) ) {
								mensagem = getString(R.string.str_foto_obrigatorio_agua);
								abaAtual = getString(R.string.str_tab_hidrometro);
								
							}  
							
							if ( anormalidadePoco != null && hidrometroInstaladoPoco != null && anormalidadePoco.equals(anormalidade) ) {
								mensagem = getString(R.string.str_foto_obrigatorio_poco);
								abaAtual = getString(R.string.str_tab_poco);
							}
							
							selection = Fotos.IMOVEL_CONTA_ID+" =? AND " + Fotos.MEDICAOTIPO + "=? AND " + Fotos.LEITURA_ANORMALIDADE_ID+" =? ";
							
							selectionArgs = new String[]
							{
								String.valueOf(imovel.getId()),
								String.valueOf(medicaoTipo),
								String.valueOf(leituraAnormalidade.getId())
							};
							
							listaFoto = fachada.buscarFotos(selection,selectionArgs);
							
							if (listaFoto==null || listaFoto.size()==0) {
								
								existeFoto = false;
								
								// Informamos ao usuário que foi tudo certo
			                    new AlertDialog.Builder(TabsActivity.this).setTitle(getString(R.string.obrigatorio))
                                          .setMessage(mensagem)
                                          .setIcon(R.drawable.warning)
                                          .setCancelable(false)
                                          .setPositiveButton(getString(R.string.str_sim),
                                                             new DialogInterface.OnClickListener() {
                                                                
                                                                 public void onClick(DialogInterface dialog, int which) {
                                                                	 
                                                                	 if(abaAtual.equals(getString(R.string.str_tab_hidrometro))){
                                                             		 	medicaoTipo = ConstantesSistema.LIGACAO_AGUA;
                                                             		 }  
                                                             		
                                                                	 if(abaAtual.equals(getString(R.string.str_tab_poco))){	
                                                             		 	medicaoTipo = ConstantesSistema.LIGACAO_POCO;
                                                             		 }
                                                                	 
                                                                	 Intent camera;
                                                            		 camera = new Intent(TabsActivity.this, FotoActivity.class);
                                                                  	 CameraHelper helper = new CameraHelper(imovel, medicaoTipo, anormalidade.getId(),null,null);
                                                                  	 camera.putExtra(ConstantesSistema.FOTO_HELPER, helper);                          
                                                                  	 
                                                                  	 if(SistemaParametros.getInstancia().getCodigoEmpresaFebraban()
                                                                  			 .equals(ConstantesSistema.CODIGO_FEBRABAN_COMPESA)){
                                                                  		camera.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                                                  	 }
                                                                  	 
                                                                  	 startActivity(camera);
                                                                 }
                                                             })
                                          .setNegativeButton(getString(R.string.str_nao),
                                                             new DialogInterface.OnClickListener() {
                                                                
                                                                 public void onClick(DialogInterface dialog, int which) {
                                                                     
                                                                 }
                                                             })
                                          .show();
								
							}else{
																
								if(medicaoTipo == ConstantesSistema.LIGACAO_AGUA && colecaoLeituraAnormalidade.size() >1){
									fotoAgua = true;
								}
								
								retorno[1] = mensagem;	
								ControladorAlertaValidarFoto alertaFoto = fachada.getControladorAlertaValidarFoto(TabsActivity.this, hidrometroInstaladoAgua, hidrometroInstaladoPoco, imovel, 
										posicao, proximo, false,medicaoTipo,anormalidade.getId(),null);
								
								selection = Fotos.IMOVEL_CONTA_ID+" =? AND " + Fotos.FOTO_TIPO + "=? AND " +Fotos.MEDICAOTIPO + "=? AND " + Fotos.LEITURA_ANORMALIDADE_ID+" =? ";
								
								selectionArgs = new String[]
								{
									String.valueOf(imovel.getId()),
									String.valueOf(ConstantesSistema.FOTO_ANORMALIDADE),
									String.valueOf(medicaoTipo),
									String.valueOf(leituraAnormalidade.getId())
								};
								
								listaFoto = fachada.buscarFotos(selection,selectionArgs);
								
								if(listaFoto==null || listaFoto.size()==0)
								{
									new AlertDialog.Builder(TabsActivity.this).setTitle(getString(R.string.obrigatorio))
			                        .setMessage(getString(R.string.str_informe_foto_anormalidade_imovel))
			                        .setIcon(R.drawable.warning)
			                        .setCancelable(false)
			                        .setPositiveButton(TabsActivity.this.getString(R.string.str_sim),
			                                           new DialogInterface.OnClickListener() {
			                                              
			                                               public void onClick(DialogInterface dialog, int which) {
			                                              	
			                                            	   Intent camera;
			                                            	   camera = new Intent(TabsActivity.this, FotoActivity.class);
                                                               CameraHelper helper = new CameraHelper(imovel, medicaoTipo, anormalidade.getId(),null,null);
                                                               camera.putExtra("helper", helper);                          
                                                            	 
                                                               if(SistemaParametros.getInstancia().getCodigoEmpresaFebraban()
                                                            			 .equals(ConstantesSistema.CODIGO_FEBRABAN_COMPESA)){
                                                            		camera.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                                               }
                                                            	 
                                                            	 startActivity(camera);
			                                               }
			                                           })
			                        .setNegativeButton(TabsActivity.this.getString(R.string.str_nao),
			                                           new DialogInterface.OnClickListener() {
			                                              
			                                               public void onClick(DialogInterface dialog, int which) {
			                                                   
			                                               }
			                                           })
			                        .show();
								}
								else
								{
									alertaFoto.defineAlerta(ConstantesSistema.ALERTA_PERGUNTA, mensagemFoto, botaoClicado);
								}
								
								if(colecaoLeituraAnormalidade.size() >1 && fotoAgua){
									//fotoAgua = false;
									
									break;
								}else{
									fotoAgua = false;
								}
							}
							
						} catch (FachadaException e) {
							Log.e( ConstantesSistema.CATEGORIA , e.getMessage());
							e.printStackTrace();
						}
					}else{
						if(medicaoTipo != ConstantesSistema.LIGACAO_AGUA && colecaoLeituraAnormalidade.size() < 2){
							if(botaoClicado == ConstantesSistema.BOTAO_CALCULAR){
								validaCalcular(proximo);
							}
							if(botaoClicado == ConstantesSistema.BOTAO_IMPRIMIR){
								validarImprimir();
							}
							if(botaoClicado == ConstantesSistema.BOTAO_PROXIMO){
								validaChamaProximo(posicao, proximo);
							}
							if(botaoClicado == ConstantesSistema.BOTAO_ANTERIOR){
								validaChamaAnterior(posicao, proximo);
							}
							existeFoto = false;
						}
					}
					
				}
			}
						
		}
	
			retorno[0] = existeFoto;
		
		return retorno;
	}
	
	
	private void defineMensagemFotoTipo() {

		if ( anormalidadeAgua != null && hidrometroInstaladoAgua != null && anormalidadeAgua.equals(anormalidade) ) {
			medicaoTipo = 1;
			mensagemFoto = getString(R.string.str_foto_obrigatorio_agua_substituir);
		}  
		
		if ( anormalidadePoco != null && hidrometroInstaladoPoco != null && anormalidadePoco.equals(anormalidade) ) {
			medicaoTipo = 2;
			mensagemFoto = getString(R.string.str_foto_obrigatorio_poco_substituir);
		}
		
	}

	public View createTabContent(String tag) {
		
		return null;
	}

	
	
	public void chamaProximo(Integer posicao,boolean proximo){
				
		if( proximo ) {
			
			if(  imovel.isCondominio() ) {
				
				if(validarDadosLeitura()){
				
					Object[] retornoFoto =  validarFoto(posicao,proximo,ConstantesSistema.BOTAO_PROXIMO);
					boolean existeFoto = (Boolean) retornoFoto[0];
					String mensagem = (String) retornoFoto[1];
								
					//Se o imóvel for condomínio mandar calcular em background
					//O sistema valida se é obrigatorio registar foto de acordo com anormalidade informada, caso tenha sido informada.
					if ( verificaFotoValidada(existeFoto,mensagem)){
						validaChamaProximo(posicao, proximo);
					}else{
						fotoAgua = false;
					}
				}else{
					
					if(imovel.getIndcImovelCalculado().equals(ConstantesSistema.SIM)){
						
						ArrayList<Integer> tiposMedicao = new ArrayList<Integer>();
						
						if(hidrometroInstaladoAgua!=null){
							tiposMedicao.add(ConstantesSistema.LIGACAO_AGUA);
						}
						
						if(hidrometroInstaladoPoco!=null){
							tiposMedicao.add(ConstantesSistema.LIGACAO_POCO);
						}
						
					    for(Integer tipoMedicao: tiposMedicao){  
					    	ControladorAlertaValidarErro alertaErro = fachada.getControladorAlertaValidarErro();
					    	alertaErro.apagaDados(imovel, tipoMedicao.intValue(), 0);  
					     }  
					}
					
					//Caso NAO tenha sido informado leitura e/ou anormalidade Chamar o proximo
					iniciarProximaIntent(posicao,proximo);
				}
				
			}
			// Só chama próximo caso não seja imóvel condomínio	
			else if( !imovel.isCondominio() ){
				iniciarProximaIntent(posicao,proximo);
			}							
			
		// Chama anterior
		} else {
			if(  imovel.isCondominio() ) {
				
				if(validarDadosLeitura()){
				
					Object[] retornoFoto =  validarFoto(posicao,proximo,ConstantesSistema.BOTAO_ANTERIOR);
					boolean existeFoto = (Boolean) retornoFoto[0];
					String mensagem = (String) retornoFoto[1];
								
					//Se o imóvel for condomínio mandar calcular em background
					//O sistema valida se é obrigatorio registar foto de acordo com anormalidade informada, caso tenha sido informada.
					if ( verificaFotoValidada(existeFoto,mensagem)){
						validaChamaAnterior(posicao, proximo);
					}else{
						fotoAgua = false;
					}
				}else{
					
					if(imovel.getIndcImovelCalculado().equals(ConstantesSistema.SIM)){
						
						ArrayList<Integer> tiposMedicao = new ArrayList<Integer>();
						
						if(hidrometroInstaladoAgua!=null){
							tiposMedicao.add(ConstantesSistema.LIGACAO_AGUA);
						}
						
						if(hidrometroInstaladoPoco!=null){
							tiposMedicao.add(ConstantesSistema.LIGACAO_POCO);
						}
						
					    for(Integer tipoMedicao: tiposMedicao){  
					    	ControladorAlertaValidarErro alertaErro = fachada.getControladorAlertaValidarErro();
					    	alertaErro.apagaDados(imovel, tipoMedicao.intValue(), 0);  
					     }  
					}
					
					//Caso NAO tenha sido informado leitura e/ou anormalidade Chamar o proximo
					iniciarProximaIntent(posicao,proximo);
				}
				
			}
			// Só chama próximo caso não seja imóvel condomínio	
			else if( !imovel.isCondominio() ){
				iniciarProximaIntent(posicao,proximo);
			}
		}	
		
	}
	
	public void validaChamaProximo(Integer posicao,boolean proximo) {
		boolean contaCalculada = false;
		
		//RM4693 - Só deve ser recalculado se a leitura e/ou anormalidade forem alteradas 
		if(leituraAlterada()) {
			
			atualizarHistorico();
			
			atualizarIndcImovelCondomioNaoCalculado(false);
			
			//[UC1303][SB0005] Validar Leitura
			if(TabsActivity.hidrometroInstaladoAgua != null){
				contaCalculada = fachada.validarLeituraMensagem(TabsActivity.hidrometroInstaladoAgua, imovel,0,false,proximo);

			}
			if(TabsActivity.hidrometroInstaladoAgua == null && TabsActivity.hidrometroInstaladoPoco != null){
				contaCalculada = fachada.validarLeituraMensagem(TabsActivity.hidrometroInstaladoPoco, imovel,0,false,proximo);			
			}
		
	    	// Se conta calculada ou for imóvel nao medido
	    	if(contaCalculada || 
	    			(TabsActivity.hidrometroInstaladoAgua == null && TabsActivity.hidrometroInstaladoPoco == null)){
	    		
	    		iniciarProximaIntent(posicao,proximo);
	    	}
			
		} else {
			
			iniciarProximaIntent(posicao,proximo);
		}
	}
	
	public void validaChamaAnterior(Integer posicao, boolean proximo){
		boolean contaCalculada = false;
		
		//RM4693 - Só deve ser recalculado se a leitura e/ou anormalidade forem alteradas
		if(leituraAlterada()) {
			
			atualizarHistorico();
			
			atualizarIndcImovelCondomioNaoCalculado(false);
			
			//[UC1303][SB0005] Validar Leitura
			if(TabsActivity.hidrometroInstaladoAgua != null){
				contaCalculada = fachada.validarLeituraMensagem(TabsActivity.hidrometroInstaladoAgua, imovel,0,false,proximo);

			}
			if(TabsActivity.hidrometroInstaladoAgua == null && TabsActivity.hidrometroInstaladoPoco != null){
				contaCalculada = fachada.validarLeituraMensagem(TabsActivity.hidrometroInstaladoPoco, imovel,0,false,proximo);			
			}
		
	    	// Se conta calculada ou for imóvel nao medido
	    	if(contaCalculada || 
	    			(TabsActivity.hidrometroInstaladoAgua == null && TabsActivity.hidrometroInstaladoPoco == null)){
	    		
	    		iniciarProximaIntent(posicao,proximo);
	    	}
			
		} else {
			
			iniciarProximaIntent(posicao,proximo);
			
		}
		
	}

	public void iniciarProximaIntent(Integer posicao,boolean proximo){
		
		HidrometroAguaActivity.naoHouveErro = false;
		HidrometroEsgotoActivity.naoHouveErro = false;
		
		Util.enviaEmBackground(imovel, TabsActivity.this);
				
		ImovelConta imovelProximo = Fachada.getInstance().buscarImovelContaPosicao(posicao,proximo);
		Intent i = getIntent();
		i.putExtra("imovel", imovelProximo);
		finish();
		startActivity(i);
		
	}
	
  

	/** 
     *  Se for imóvel condomínio não exibe botão calcular
     *  E exibe botão imprimir apenas se for o último
     */
    protected void verificarImovelCondominio(){
    	
    	boolean habilitarbotaoImprimirCaern = fachada.isMotoralaDefyPro(this);
    	boolean habilitarbotaoCalcularCaern = fachada.isMotoralaDefyPro(this);
    	
    	if(imovel.isCondominio()){
    	
         	
    		 boolean habilitarBotao2Via = SistemaParametros.getInstancia().
    				 getCodigoEmpresaFebraban().equals(ConstantesSistema.CODIGO_FEBRABAN_COMPESA);
    		 
         	calcular.setVisibility(View.GONE);
         	Integer idMicro = imovel.getMatriculaCondominio();
		
         	if(idMicro!=null && fachada.permiteImprimir2ViaImovelMicro(idMicro) && habilitarBotao2Via ){
         		         		
     			imprimir2ViaCondominio.setVisibility(View.VISIBLE);   
         		imprimir2ViaCondominio.setEnabled(true);
     		
         		imprimir.setVisibility(View.GONE);   
         		imprimir.setEnabled(false);
        		imprimirCaern.setEnabled(false);
        		imprimirCaern.setClickable(false);
         		
         	} else if (!fachada.permiteImprimir(imovel)){
         		imprimir.setVisibility(View.GONE);
         		imprimir.setEnabled(false);
        		imprimirCaern.setEnabled(false);
        		imprimirCaern.setClickable(false);
         		
         		if(habilitarBotao2Via){
         			imprimir2ViaCondominio.setVisibility(View.GONE);
         			imprimir2ViaCondominio.setEnabled(false);
         		}
         		
         		if(habilitarbotaoImprimirCaern){
         			imprimirCaern.setVisibility(View.GONE);
         		}
         		if(habilitarbotaoCalcularCaern){
         			imprimirCaern.setVisibility(View.GONE);
         		}
         		
         	} 
         	else {
         		imprimir.setVisibility(View.VISIBLE);   
         		imprimir.setEnabled(true);
        		imprimirCaern.setEnabled(true);
        		imprimirCaern.setClickable(true);
         		
         		if(habilitarBotao2Via){
	         		imprimir2ViaCondominio.setVisibility(View.GONE);
	         		imprimir2ViaCondominio.setEnabled(true);
         		}
	         		
         		if(habilitarbotaoImprimirCaern){
         			imprimirCaern.setVisibility(View.VISIBLE);   
             		imprimirCaern.setEnabled(true);
         		}
         		
         		
         	}
         } else {
         	calcular.setVisibility(View.VISIBLE);
         	imprimir.setVisibility(View.VISIBLE); 
     		imprimir.setEnabled(true);
     		
     		if(habilitarbotaoImprimirCaern){
     			imprimirCaern.setVisibility(View.VISIBLE); 
         		imprimirCaern.setEnabled(true);	
     		}
     		if(habilitarbotaoCalcularCaern){
     			calcularCaern.setVisibility(View.VISIBLE); 
     			calcularCaern.setEnabled(true);	
     		}
     		
         }
    	 
    	 //Se for imovel não medido nao exibir botao calcular
    	 if(TabsActivity.hidrometroInstaladoAgua == null && TabsActivity.hidrometroInstaladoPoco == null){
 			calcular.setVisibility(View.GONE);
 		}
    	 
    	desabilitaImprimirLeitura();
    	
    	 
    }
    
    // CASO CAERN e LEITURA = 1, não é exibido o botão de imprimir
    private void desabilitaImprimirLeitura() {
    	
    	if(	sistemaParametros.getIndicadorSistemaLeitura().equals(ConstantesSistema.SIM )) {
    		    		
    		if( fachada.isMotoralaDefyPro(this)){
    		
	    		if(imovel.isCondominio()){
		    		// Se for último condomínio
		    		if (fachada.permiteImprimir(imovel) ){
		    			imprimirCaern.setVisibility(View.VISIBLE); 
			     		imprimirCaern.setEnabled(true);
			     		imprimirCaern.setText(getString(R.string.str_tab_menu_ratear));
			     		
			     		imprimir.setText(getString(R.string.str_tab_menu_ratear));
		    			    		
		    		}else{
		    			imprimirCaern.setVisibility(View.GONE); 
						imprimir.setVisibility(View.GONE);  
		    		}
				}else{
					imprimirCaern.setVisibility(View.GONE); 
					imprimir.setVisibility(View.GONE);  
				}
    		}else{
    			if(imovel.isCondominio()){
		    		// Se for último condomínio
		    		if (fachada.permiteImprimir(imovel) ){
		    			imprimir.setVisibility(View.VISIBLE);  
		    			imprimir.setEnabled(true);
		    			imprimirCaern.setEnabled(true);
		    			imprimirCaern.setClickable(true);
		    			imprimir.setText(getString(R.string.str_tab_menu_ratear));
		    			    		
		    		}else{
		    			imprimir.setVisibility(View.GONE);  
		    		}
				}else{
					imprimir.setVisibility(View.GONE);  
				}
    			
    		}
    		 
      	}
		
	}

    protected void setUpImprimirCaern(){
    	imprimirCaern = (Button) findViewById(R.id.imprimirCaern);
    	if(fachada.isMotoralaDefyPro(this) ){
    		imprimirCaern.setHeight(45);
    		imprimirCaern.setBackgroundResource(R.drawable.botao_bg_selector);
    		imprimirCaern.setVisibility(View.VISIBLE);
    		imprimirCaern.setOnClickListener(new OnClickListener() {	

    			public void onClick(View arg0) {
    				clickBotaoImprimir();
    			}
    		});
    	}
    }
	
	protected void setUpCalcularCaern(){
		calcularCaern = (Button) findViewById(R.id.calcularCaern);
		if(fachada.isMotoralaDefyPro(this) ){
	    	calcularCaern.setHeight(45);
	        calcularCaern.setBackgroundResource(R.drawable.botao_bg_selector);
			calcularCaern.setOnClickListener(new OnClickListener() {	
				
				public void onClick(View arg0) {
					validaCalcular(chamarProximo);
				}
	
			});
		}
	}
    
    protected void setUpImprimir(){
		imprimir = (Button) findViewById(R.id.imprimir);
		imprimir.setOnClickListener(new OnClickListener() {			
			
			public void onClick(View arg0) {
				clickBotaoImprimir();
			}

		});
		
		imprimir2ViaCondominio = (Button) findViewById(R.id.imprimir_2_via_condominio);
		imprimir2ViaCondominio.setOnClickListener(new OnClickListener() {			
			
			public void onClick(View arg0) {
				clickBotaoImprimir2ViaCondominio();
			}

		});
	}
    	
	private static View createTabView(final Context context, final String text) {
		View view = LayoutInflater.from(context).inflate(R.layout.tabs_bg, null);
		TextView tv = (TextView) view.findViewById(R.id.tabsText);
		tv.setText(text);
		return view;
	}

	public void recarrega(ImovelConta imovel,HidrometroInstalado hidrometroInstalado){
		Intent i = getProximaIntent();
		i.putExtra("imovel", imovel);
		if(hidrometroInstalado.getTipoMedicao().intValue() == ConstantesSistema.LIGACAO_AGUA){
			i.putExtra("agua", "ok");			
		}else{
			i.putExtra("esgoto", "ok");						
		}
		finish();
		startActivity(i);
	}
	
	public void atualizarHistorico(){
	
		if (hidrometroInstaladoAgua!=null){
			atualizarHidrometro(hidrometroInstaladoAgua, anormalidadeAgua, ConstantesSistema.LIGACAO_AGUA);	
		}
		if (hidrometroInstaladoPoco!=null){
			atualizarHidrometro(hidrometroInstaladoPoco, anormalidadePoco, ConstantesSistema.LIGACAO_POCO);
		}
					
	}
	
	protected void atualizarHidrometro(HidrometroInstalado hidrometro, 
			LeituraAnormalidade anormalidade, int tipoMedicao){
		
		EditText leitura = null;
		if (tipoMedicao == ConstantesSistema.LIGACAO_AGUA){
			leitura = HidrometroBaseActivity.leituraAgua;
		} else {
			leitura = HidrometroBaseActivity.leituraPoco;
		}
	
		if(hidrometro.getLeitura() != null && leitura!=null && leitura.getText().toString().length() != 0){
			hidrometro.setLeitura(Integer.valueOf(leitura.getText().toString()));
			
		}else{
			if(leitura!=null && leitura.getText()!=null && leitura.getText().toString().length() != 0){
				hidrometro.setLeitura(Integer.valueOf(leitura.getText().toString()));
			
			// Caso não haja leitura digitada e não haja leitura atual no banco
			}else{
				hidrometro.setLeitura(null);
				hidrometro.setLeituraAnteriorDigitada(null);
			}
		}
		
		if(anormalidade != null && anormalidade.getId() > 0){
			hidrometro.setAnormalidade(anormalidade.getId());
		}else{
			hidrometro.setAnormalidade(null);
		}
		
		fachada.atualizar(hidrometro);
					
	}
	
	private Intent getProximaIntent(){
		if(TabsActivity.this.getClass().equals(HidrometroAguaActivity.class)){
			HidrometroAguaActivity ParentActivity;
			ParentActivity = (HidrometroAguaActivity) TabsActivity.this.getParent();
			return ParentActivity.getProximaIntent();			
		}else if(TabsActivity.this.getClass().equals(ContaActivity.class)){
			ContaActivity ParentActivity;
			ParentActivity = (ContaActivity) TabsActivity.this.getParent();
			return ParentActivity.getProximaIntent();
		}else if(TabsActivity.this.getClass().equals(HidrometroEsgotoActivity.class)){
			HidrometroEsgotoActivity ParentActivity;
			ParentActivity = (HidrometroEsgotoActivity) TabsActivity.this.getParent();
			return ParentActivity.getProximaIntent();		
		}
		return null;
	}
	
	private void verificarBloqueioRecalcularConta(){    	
    	if(fachada.verificarBloqueioRecalcularConta(imovel)){
    		calcular.setVisibility(View.GONE);
    		calcularCaern.setVisibility(View.GONE);
    	}     	
    }
	
	public void onTabChanged(String tabId) {
		if (tabId.equals(getString(R.string.str_tab_conta))){
			calcular.setVisibility(View.GONE);
		} else {
			calcular.setVisibility(View.VISIBLE);
		}
		
		verificarBloqueioRecalcularConta();
	}

	
	
	public void clickBotaoImprimir(){
		
		imprimir.setEnabled(false);
		imprimirCaern.setEnabled(false);
		imprimirCaern.setClickable(false);
		Object[] retornoFoto =  validarFoto(0,true,ConstantesSistema.BOTAO_IMPRIMIR);
		boolean existeFoto = (Boolean) retornoFoto[0];
		String mensagem = (String) retornoFoto[1];
			
		//O sistema valida se é obrigatorio registar foto de acordo com anormalidade informada, caso tenha sido informada.
		if ( verificaFotoValidada(existeFoto,mensagem)){
			
			validarImprimir();		
		}

		imprimir.setEnabled(true);
		imprimir.setClickable(true);
		imprimirCaern.setEnabled(true);
		imprimirCaern.setClickable(true);
	}

	public void validarImprimir() {
		boolean contaCalculada = false;
		
		if(!fachada.verificarBloqueioRecalcularConta(imovel)){
		
			if(imovel.isCondominio() && imovelCondominioAlterado()){
				exibirAlertaLeituraAlterada();
			}else{
				//atualiza o histórico
				atualizarHistorico();
				
				boolean imprimirConta = true;
				
				//[UC1303][SB0005] Validar Leitura
				if(TabsActivity.hidrometroInstaladoAgua != null){
					contaCalculada = fachada.validarLeituraMensagem(TabsActivity.hidrometroInstaladoAgua, imovel,0,imprimirConta,chamarProximo);


					if(TabsActivity.hidrometroInstaladoAgua == null && TabsActivity.hidrometroInstaladoPoco == null){
						contaCalculada = true;	
					}    

				}else{
					contaCalculada = true;
				}
				if(TabsActivity.hidrometroInstaladoAgua == null && TabsActivity.hidrometroInstaladoPoco != null){
					contaCalculada = fachada.validarLeituraMensagem(TabsActivity.hidrometroInstaladoPoco, imovel,0,imprimirConta,chamarProximo);			
				}
				
				if(TabsActivity.hidrometroInstaladoAgua == null && TabsActivity.hidrometroInstaladoPoco == null){
					contaCalculada = true;	
				}  
			}
		}else{
			contaCalculada = true;
		}
		//Se for ULTIMO imovel condominio Calcular Rateio
		if(imovel.isCondominio()){
			    				
			if (contaCalculada || fachada.verificarBloqueioRecalcularConta(imovel) ){
				
//				if(chamarProximo){
//					ControladorAlertaValidarErro alertaErro = fachada.getControladorAlertaValidarErro();
//					alertaErro.exbirMensagemImovelCondominioNaoCalculado(imovel, this);
//				}else{
//					chamaProximo(imovel.getPosicao(), chamarProximo);
//				}
				
				ControladorAlertaValidarErro alertaErro = fachada.getControladorAlertaValidarErro();
				alertaErro.exbirMensagemImovelCondominioNaoCalculado(imovel, this);
				
			}
		}
	
    	// Se conta calculada ou imóvel não medido
		else if(contaCalculada 
				|| (TabsActivity.hidrometroInstaladoAgua == null && TabsActivity.hidrometroInstaladoPoco == null )){
			imprimirConta();
    	}
	}

	private void clickBotaoImprimir2ViaCondominio(){
		
		imprimir2ViaCondominio.setEnabled(false);
		
		boolean leituraAlterada = leituraAlterada();
		
		ImovelConta imovelMacro = 
				(ImovelConta) Fachada.getInstance()
				.pesquisarPorId(imovel.getMatriculaCondominio(), imovel);
		
		if(fachada.permiteImprimir2ViaImovelMicro(imovelMacro.getId()) && !leituraAlterada ){
			
			boolean contaImpressa = fachada.verificarImpressaoConta(imovel,getApplicationContext(),0);
			
			if(contaImpressa){
				iniciarProximaIntent(imovel.getPosicao(),true);						
			}
			
		}else{
			exibirAlertaLeituraAlterada();
		}
		imprimir2ViaCondominio.setEnabled(true);
	}
	


	public void imprimirConta(){
		boolean contaImpressa = fachada.verificarImpressaoConta(imovel,getApplicationContext(),0);
		
		if(contaImpressa){
			chamaProximo(imovel.getPosicao(),true);						
		}
	}
	
	
	
	@Override
    public void onBackPressed() { 
		finish();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	
	@Override
	protected void onResume() {
		super.onResume();
		fotoAgua = false;
		setUpImprimirCaern();
		setUpCalcularCaern();
		verificarImovelCondominio();
		verificarBloqueioRecalcularConta();
	}
	
	/** 
	 * Verifica se foi informada leitura de agua e/ou poco
	 */
	private boolean validarDadosLeitura(){
		
		boolean dadosLeituraAguaInformado = true;
		boolean dadosLeituraPocoInformado = true;
		if(hidrometroInstaladoAgua!=null ){
			EditText leituraAgua = HidrometroBaseActivity.leituraAgua;
			EditText anormalidadeAgua = HidrometroBaseActivity.idAnormalidadeAgua;
			
			if( (leituraAgua==null || leituraAgua.getText().toString().trim().equals("")) &&
					(anormalidadeAgua==null || anormalidadeAgua.getText().toString().trim().equals(""))){
				
				dadosLeituraAguaInformado = false;
			}
		}
		
		if(hidrometroInstaladoPoco!=null ){
			EditText leituraPoco = HidrometroBaseActivity.leituraPoco;
			EditText anormalidadePoco = HidrometroBaseActivity.idAnormalidadePoco;
			
			if( (leituraPoco==null || leituraPoco.getText().toString().trim().equals("")) &&
					(anormalidadePoco==null || anormalidadePoco.getText().toString().trim().equals(""))){
				
				dadosLeituraPocoInformado = false;
			}
		}
		
		return dadosLeituraAguaInformado && dadosLeituraPocoInformado;
	}
	
	/** Metodo que exibi a menssagem de leitura alterada
	 *  quando a leitura do imovelCondominio foi modificada 
	 *  apos o mesmo jÃ¡ estÃ¡ rateado
	 */
	public void exibirAlertaLeituraAlterada(){
		new AlertDialog.Builder(TabsActivity.this)
		.setTitle(getString(R.string.str_atencao))
		.setMessage(getString(R.string.str_imov_condominio_micro_modificado))
		.setNeutralButton(getString(android.R.string.ok), 
			new DialogInterface.OnClickListener() {
				public void onClick(
					DialogInterface dialog,
					int which) {
					
					atualizarIndcImovelCondomioNaoCalculado(true);
					
					//Desabilitar Botao Imprimir
					verificarImovelCondominio();
					
					//Limpa o os valores da anormalidade e leitura
					EditText leitura = null;
					EditText idAnormalidade = null;
					Spinner anormalidade = null;
					if (hidrometroInstaladoAgua!=null){
						leitura = HidrometroBaseActivity.leituraAgua;
						idAnormalidade = HidrometroBaseActivity.idAnormalidadeAgua;
						anormalidade = HidrometroBaseActivity.anormalidadeSpinnerAgua;
						
						leitura.setText("");
						idAnormalidade.setText("");
						anormalidade.setSelection(0);
						
						ConsumoHistorico consumoHistoricoBaseAgua = 
								fachada.buscarConsumoHistoricoPorImovelIdTipoLigacao(imovel.getId(),ConstantesSistema.LIGACAO_AGUA);
						if(consumoHistoricoBaseAgua != null && !consumoHistoricoBaseAgua.equals("")){
							fachada.remover(consumoHistoricoBaseAgua);
						}
						
						hidrometroInstaladoAgua.setLeitura(null);
						hidrometroInstaladoAgua.setAnormalidade(null);
					    fachada.atualizar(hidrometroInstaladoAgua);

					}if (hidrometroInstaladoPoco!=null){
						leitura = HidrometroBaseActivity.leituraPoco;
						idAnormalidade = HidrometroBaseActivity.idAnormalidadePoco;
						anormalidade = HidrometroBaseActivity.anormalidadeSpinnerPoco;
						
						leitura.setText("");
						idAnormalidade.setText("");
						anormalidade.setSelection(0);
						
						ConsumoHistorico consumoHistoricoBasePoso = 
								fachada.buscarConsumoHistoricoPorImovelIdTipoLigacao(imovel.getId(),ConstantesSistema.LIGACAO_POCO);
						if(consumoHistoricoBasePoso != null && !consumoHistoricoBasePoso.equals("")){
							fachada.remover(consumoHistoricoBasePoso);
						}
						
						hidrometroInstaladoPoco.setLeitura(null);
						hidrometroInstaladoPoco.setAnormalidade(null);
					    fachada.atualizar(hidrometroInstaladoPoco);
						
					} 
				}
		}).show();
	}

	
	private void atualizarIndcImovelCondomioNaoCalculado(boolean isAvisoImovelCondominioModificado){
		if(imovel.isCondominio()){
			
			if(isAvisoImovelCondominioModificado){
				fachada.
					atualizarIndicadorImovelCalculado(imovel.getId(), ConstantesSistema.NAO);
			}
			
			Integer idMacro = null;
			
			if(imovel.getMatriculaCondominio()!=null){
				idMacro = imovel.getMatriculaCondominio();
			}else{
				idMacro = imovel.getId();
			}
			
			fachada.
				atualizarIndicadorContinuaImpressao(idMacro, ConstantesSistema.NAO);
			
			fachada.
				atualizarIndicadorImovelCondominioNaoCalculado(idMacro);
				
		}
	}
	
	
	private boolean imovelCondominioAlterado(){
		
		boolean agua = false;
 		boolean poco = false;
 		EditText leitura = null;
 		EditText anormalidade = null;
 		if (hidrometroInstaladoAgua!=null){
 			leitura = HidrometroBaseActivity.leituraAgua;
 			anormalidade = HidrometroBaseActivity.idAnormalidadeAgua;
 			
			//Verifica se a leitura ou anormalidade foi modficada
			if((!leitura.getText().toString().trim().equals("") 
					&& hidrometroInstaladoAgua.getLeitura() != null
					 && !leitura.getText().toString().equals(hidrometroInstaladoAgua.getLeitura().toString()))
					 ||
					 (!anormalidade.getText().toString().trim().equals("") 
						&& hidrometroInstaladoAgua.getAnormalidade() != null
						 && !anormalidade.getText().toString().equals(hidrometroInstaladoAgua.getAnormalidade().toString()))
	 					  ){
				agua = true;
			}else{
				agua = false;
			}
 			
 			
 		}if (hidrometroInstaladoPoco!=null){
 			leitura = HidrometroBaseActivity.leituraPoco;
 			anormalidade = HidrometroBaseActivity.idAnormalidadePoco;
 			//Verifica se a leitura ou anormalidade
 			if((!leitura.getText().toString().trim().equals("") 
					&& hidrometroInstaladoPoco.getLeitura() != null
					 && !leitura.getText().toString().equals(hidrometroInstaladoPoco.getLeitura().toString()))
					 ||
					 (!anormalidade.getText().toString().trim().equals("") 
						&& hidrometroInstaladoPoco.getAnormalidade() != null
						 && !anormalidade.getText().toString().equals(hidrometroInstaladoPoco.getAnormalidade().toString()))
	 					  ){
 				poco = true;
 			}else{
 				poco = false;
 			}
 		}
 			
 		//Imóveis não medidos só devem ser recalculados se ainda não foi impresso
 		if(hidrometroInstaladoPoco == null && hidrometroInstaladoAgua == null){ 
 			agua = false;
 		}
 		
 		//Se agua OU poco tiver sido alterado retorna TRUE
 		return agua || poco;
		
	}
}