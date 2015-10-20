package com.br.ipad.isc.gui;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils.TruncateAt;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.br.ipad.isc.R;
import com.br.ipad.isc.adapters.AnormalidadeAdapter;
import com.br.ipad.isc.bean.HidrometroInstalado;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.bean.ImovelRevisitar;
import com.br.ipad.isc.bean.LeituraAnormalidade;
import com.br.ipad.isc.bean.SistemaParametros;
import com.br.ipad.isc.excecoes.FachadaException;
import com.br.ipad.isc.fachada.Fachada;
import com.br.ipad.isc.util.ConstantesSistema;
import com.br.ipad.isc.util.Util;

public abstract class HidrometroBaseActivity extends BaseActivity {
    
	protected ImovelConta imovel;
	protected TextView endereco;
	protected TextView rotaFinalizada;
	public static TextView nomeEndereco;
	private LinearLayout llInscricao;
	private TextView rota;
	protected TextView inscricao;
	protected TextView hidrometro;
	protected TextView matricula;
	
	public static EditText idAnormalidadeAgua;
	public static EditText leituraAgua;
	public static EditText idAnormalidadePoco;
	public static EditText leituraPoco;
	
	public static Spinner anormalidadeSpinnerAgua;
	public static Spinner anormalidadeSpinnerPoco;
	
	protected TextView localInstalacao;

	protected ArrayList<LeituraAnormalidade> anormalidadeList;
	public static AnormalidadeAdapter anormalidadeAdapter;
	protected ImageView status;
	protected HidrometroInstalado hidrometroBase;
	
	protected LinearLayout llRevisitar;
	protected LinearLayout llAlgumRevisitar;
	
	protected TextView totalImoveis;
	protected TextView posicao;
			
	protected Fachada fachada = Fachada.getInstance();
	
	// Identifica a não ocorrencia alguma mensagem de erro ao validar a leitura
	// Caso negativo, não atualiza os dados ao trocar de aba
	public static boolean naoHouveErro = true;

	/**
	 * Métodos abstratos
	 */
	protected abstract int getTipoLigacao();
	protected abstract int getLayout();
	protected abstract Intent getProximaIntent();
	protected abstract HidrometroInstalado getHidrometro();
	protected abstract LeituraAnormalidade getAnormalidade();
	protected abstract void verificarErro();
	protected abstract EditText getLeitura();
	protected abstract void setLeitura(EditText leitura);
	protected abstract EditText getAnormalidadeInformada();
	protected abstract void setAnormalidadeInformada(EditText anormalidadeInformada);
	protected abstract Spinner getSpinnerAnormalidade();
	protected abstract void setSpinnerAnormalidade(Spinner spinnerAnormalidade);
	
//	protected boolean atualizarHistorico(){
//		if (getHidrometro()!=null){		
//			if(getHidrometro().getLeitura() != null
//					&& getLeitura()!=null && getLeitura().getText().toString().length() != 0){
//				getHidrometro().setLeituraAnteriorDigitada(getHidrometro().getLeitura());
//				getHidrometro().setLeitura(Integer.valueOf(getLeitura().getText().toString()));
//			}else{
//				if(getLeitura()!=null && getLeitura().getText().toString().length() != 0){
//					getHidrometro().setLeitura(Integer.valueOf(getLeitura().getText().toString()));
//				}else{
//					getHidrometro().setLeitura(null);
//					getHidrometro().setLeituraAnteriorDigitada(null);
//				}
//			}
//			
//			if(getAnormalidade() != null && getAnormalidade().getId() > 0){
//				getHidrometro().setAnormalidade(getAnormalidade().getId());
//			}else{
//				getHidrometro().setAnormalidade(null);
//			}
//			
//			fachada.atualizar(getHidrometro());
//			
//			if((getHidrometro().getLeitura() != null &&
//					getLeitura()!=null && (getLeitura().getText().toString().length() != 0)
//					|| (getAnormalidade() != null && getAnormalidade().getId() > 0))){
//				return true;
//			}
//		}
//		return false;
//	}	
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        Fachada.setContext(this);
        
		imovel = (ImovelConta) getIntent().getSerializableExtra("imovel");
		
        if (getTipoLigacao() != 0){        
	        hidrometroBase= fachada.buscarHidrometroInstaladoPorImovelTipoMedicao(imovel.getId(), getTipoLigacao());
	        
	        if(imovel.isCondominio()){
	        	//Atualiza dados no Sistema Parametro
	             fachada.atualizarDadosImovelMacro(imovel);
	        }
        }       
        
        setUpWidgets();
        verificarBloqueioRecalcularConta();
        verificarRotaFinalizalida();
        
        
    }

    protected void verificarImovelRevisitar() {
		
		// Caso o imóvel atual precise ser revisitado
		ImovelRevisitar imovelRevisitar = Fachada.getInstance().buscarImovelRevisitarPorImovel(imovel.getId());
		llRevisitar = (LinearLayout) findViewById(R.id.revisitar);
		if(imovelRevisitar == null ||imovelRevisitar != null && imovelRevisitar.getIndicadorRevisitado() != null 
				&& imovelRevisitar.getIndicadorRevisitado().equals(ConstantesSistema.SIM) ){
			
			llRevisitar.setVisibility(View.GONE);

		}else{
			llRevisitar.setVisibility(View.VISIBLE);
		}
		
		// Caso exista algum imóvel a ser revisitado
		ArrayList<ImovelRevisitar> imoveisRevisitar = Fachada.getInstance().buscarImovelNaoRevisitado();
		if(imoveisRevisitar != null){
			
			llAlgumRevisitar = (LinearLayout) findViewById(R.id.revisitarAlgum);
			llAlgumRevisitar.setVisibility(View.VISIBLE);
						
		}
		
		
	}
    
	protected void setUpWidgets() {

		nomeEndereco = (TextView) findViewById(R.id.nomeEndereco);

		nomeEndereco.setFocusableInTouchMode(true);
		nomeEndereco.requestFocus();
		
		nomeEndereco.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if(keyCode == KeyEvent.KEYCODE_ENTER  && event.getAction()==0){

					TabsActivity ParentActivity;
					ParentActivity = (TabsActivity) HidrometroBaseActivity.this.getParent();
					ParentActivity.clickBotaoImprimir();
				// Diferente do botão voltar
				}else if(keyCode != 4 && getLeitura().isEnabled()){
					getLeitura().setFocusableInTouchMode(true);
					getLeitura().requestFocus();
					
					char unicodeChar = event.getNumber();					
					if( Character.isDigit(unicodeChar) ){
						getLeitura().setText(unicodeChar+"");
						getLeitura().setSelection(getLeitura().getText().length());
					}
				}
				return false;
			}
		});

		// CASO COMPESA, é exibida a inscrição 
        if(SistemaParametros.getInstancia().getCodigoEmpresaFebraban().equals(ConstantesSistema.CODIGO_FEBRABAN_COMPESA)){
			llInscricao = (LinearLayout) findViewById(R.id.llInscricao);
			llInscricao.setVisibility(View.VISIBLE);
			inscricao = (TextView) findViewById(R.id.inscricao);
			inscricao.setText(fachada.formatarInscricao(imovel.getInscricao()+""));
        }
        
   		rota = (TextView) findViewById(R.id.rota);
		rota.setText(fachada.formatarRota(imovel.getInscricao(), imovel));
       
        
        verificarImovelRevisitar();
        	
		if(imovel!=null){	
			//Se for imovel condominio exibir a posicao no condominio
			if(imovel.isCondominio()){
				TextView posicaoCondominio = (TextView) findViewById(R.id.posicaoCondominio);
				String condominio = "Condomínio: "+imovel.getPosicaoImovelCondominio()
						+"/"+SistemaParametros.getInstancia().getQtdImovelCondominio();
					posicaoCondominio.setText(condominio);
					
					posicaoCondominio.setVisibility(View.VISIBLE);
					
					nomeEndereco.setVisibility(View.GONE);
			}
		}
		status = (ImageView) findViewById(R.id.status);
		
		//Caso calculado e não impresso
		if(imovel.getIndcImovelCalculado().equals(ConstantesSistema.SIM) && imovel.getIndcImovelImpresso().equals(ConstantesSistema.NAO)){
		
			status.setImageResource(R.drawable.bolacalculado);
		// Caso calculado e impresso
		}else if(imovel.getIndcImovelCalculado().equals(ConstantesSistema.SIM) && imovel.getIndcImovelImpresso().equals(ConstantesSistema.SIM)){
			
			status.setImageResource(R.drawable.bolaimpresso);
		}
		
		rotaFinalizada = (TextView) findViewById(R.id.rota_finalizada);
		endereco = (TextView) findViewById(R.id.endereco);
		endereco.setEllipsize(TruncateAt.MARQUEE);
		endereco.setText(imovel.getEndereco());
		endereco.setSelected(true);
		endereco.setSingleLine(true);
		
		setLeitura((EditText) findViewById(R.id.leitura));
	    
		if(hidrometroBase.getNumDigitosLeituraHidrometro() != null){
		     InputFilter[] FilterArray = new InputFilter[1];
//		     FilterArray[0] = new InputFilter.LengthFilter(hidrometroBase.getNumDigitosLeituraHidrometro());
		     FilterArray[0] = new InputFilter.LengthFilter(7);
		     if (getLeitura()!=null){
		    	 getLeitura().setFilters(FilterArray);
		     }
	     }
	     
	     if(hidrometroBase.getLeitura()!=null ){
	    	 if (getLeitura()!=null){
	    		 getLeitura().setText(hidrometroBase.getLeitura()+"");
	    	 }
	     }	     

	     
	     setAnormalidadeInformada((EditText) findViewById(R.id.idAnormalidade));
	     	   
	     if(getAnormalidadeInformada()!=null){
		     getAnormalidadeInformada().setOnKeyListener(new OnKeyListener() {		
				
				public boolean onKey(View arg0, int key, KeyEvent arg2) {
					
					// Imprime caso tenha sido apertado no enter
//					}else{
						getAnormalidadeInformada().setSelection(getAnormalidadeInformada().getText().length());
						
						if (arg2.getAction()==KeyEvent.ACTION_UP) {
							
							int position = 0;
						
							//Apagar
							if(key == KeyEvent.KEYCODE_DEL){
								if(getTipoLigacao()==ConstantesSistema.LIGACAO_AGUA){
									if(getAnormalidadeInformada().getText().toString().length() == 0){
										getSpinnerAnormalidade().setSelection(0);
										TabsActivity.anormalidadeAgua= null;
									}
								}else{
									if(getAnormalidadeInformada().getText().toString().length() == 0){
										getSpinnerAnormalidade().setSelection(0);
										TabsActivity.anormalidadePoco= null;
									}
								}
							}
							
							try {
								if(getTipoLigacao()==ConstantesSistema.LIGACAO_AGUA){
			
									if(getAnormalidadeInformada().getText().toString().length() != 0){
										
										TabsActivity.anormalidadeAgua = Fachada.getInstance().
												pesquisarPorId(Integer.parseInt(
														getAnormalidadeInformada().getText().toString()), new LeituraAnormalidade());
										
										if(TabsActivity.anormalidadeAgua != null){
											position = anormalidadeAdapter.getPosition(TabsActivity.anormalidadeAgua);
										}
									}else{
										TabsActivity.anormalidadeAgua = null;
									}
							
									anormalidadeSpinnerAgua.setSelection(position);
									
								}else{
									if(getAnormalidadeInformada().getText().toString().length() != 0){
										
										TabsActivity.anormalidadePoco = Fachada.getInstance().
												pesquisarPorId(Integer.parseInt(
														getAnormalidadeInformada().getText().toString()), new LeituraAnormalidade());
										
										if(TabsActivity.anormalidadePoco != null){
											position = anormalidadeAdapter.getPosition(TabsActivity.anormalidadePoco);
										}
									}else{
										TabsActivity.anormalidadePoco = null;
									}
									
									anormalidadeSpinnerPoco.setSelection(position);
								}
								
								if(key == KeyEvent.KEYCODE_ENTER){
									TabsActivity ParentActivity;
									ParentActivity = (TabsActivity) HidrometroBaseActivity.this.getParent();
									ParentActivity.clickBotaoImprimir();
								}
								 
								return true;
							
							} catch (NumberFormatException e) {
								Log.e( ConstantesSistema.CATEGORIA , e.getMessage());
								e.printStackTrace();
							} catch (FachadaException e) {
								Log.e( ConstantesSistema.CATEGORIA , e.getMessage());
								e.printStackTrace();
							}
						}
//					}
					return false;
				}
			});
	    }
	    
	           
        hidrometro = (TextView) findViewById(R.id.hidrometro);
        hidrometro.setText(hidrometroBase.getNumeroHidrometro());
        
        localInstalacao = (TextView) findViewById(R.id.local);
        localInstalacao.setText(hidrometroBase.getLocalInstalacao());
        
        matricula = ( TextView ) findViewById( R.id.matricula );
        matricula.setText( imovel.getId().intValue()+"" );
        
        setSpinnerAnormalidade((Spinner) findViewById(R.id.anormalidade));
        
        anormalidadeList = fachada.buscarLeiturasAnormalidadesComUsoAtivo();
		
		anormalidadeAdapter = new AnormalidadeAdapter(HidrometroBaseActivity.this, anormalidadeList );
		getSpinnerAnormalidade().setAdapter(anormalidadeAdapter);
        
        if (getHidrometro()!=null){
	        if(getHidrometro().getAnormalidade() != null && getHidrometro().getAnormalidade()!=0){
	        	
	        	getAnormalidadeInformada().setText(getHidrometro().getAnormalidade()+"");
	        	
			     if (getTipoLigacao()==ConstantesSistema.LIGACAO_AGUA){
			    	 if (getAnormalidadeInformada().getText()!=null && 
			    			 !getAnormalidadeInformada().getText().toString().equals("")) {
			    		 
			    		 TabsActivity.anormalidadeAgua = (LeituraAnormalidade) Fachada.getInstance()
									.pesquisarPorId(Integer.parseInt(getAnormalidadeInformada().getText().toString()), 
											new LeituraAnormalidade());			    		 
			    	 }
			     } else {
			    	 if (getAnormalidadeInformada().getText()!=null && 
			    			 !getAnormalidadeInformada().getText().toString().equals("")) {
			    		 TabsActivity.anormalidadePoco= (LeituraAnormalidade) Fachada.getInstance()
									.pesquisarPorId(Integer.parseInt(getAnormalidadeInformada().getText().toString()), 
											new LeituraAnormalidade());
			    	 }
			     }
			     
				if(getAnormalidade()!= null){
					int position = anormalidadeAdapter.getPosition(getAnormalidade());
					if(position != 0){
						getSpinnerAnormalidade().setSelection(position);
					}
				}
		    }
        }
        getSpinnerAnormalidade().setOnItemSelectedListener(new OnItemSelectedListener() {

			
			public void onItemSelected(AdapterView<?> arg0, View view, int position,
 					long id) {
				
				try {
					 
					if (getTipoLigacao()==ConstantesSistema.LIGACAO_AGUA){
				    	 TabsActivity.anormalidadeAgua = Fachada.getInstance().
				    			 buscarLeituraAnormalidadePorIdComUsoAtivo(anormalidadeList.get(position).getId());				    			 
				     } else {
				    	 TabsActivity.anormalidadePoco= Fachada.getInstance().
				    			 buscarLeituraAnormalidadePorIdComUsoAtivo(anormalidadeList.get(position).getId());				    			 
				     }
					
					if(getAnormalidade()!= null && getAnormalidade().getId()!=0){
						getAnormalidadeInformada().setText(getAnormalidade().getId().toString());
					}else{
						getAnormalidadeInformada().setText("");
					}
				} catch (NumberFormatException e) {
					Log.e( ConstantesSistema.CATEGORIA , e.getMessage());
					e.printStackTrace();
				} catch (FachadaException e) {
					Log.e( ConstantesSistema.CATEGORIA , e.getMessage());
					e.printStackTrace();
				}
				
			}

			
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
     
	}
	
	
		
	public void iniciarProximaIntent(Integer posicao,boolean proximo){

		Util.enviaEmBackground(imovel,getApplicationContext());
		
		ImovelConta imovelProximo = Fachada.getInstance().buscarImovelContaPosicao(posicao,proximo);
		Intent i = new Intent(HidrometroBaseActivity.this,TabsActivity.class);
		i.putExtra("imovel", imovelProximo);
		finish();
		startActivity(i);		
	}
	
	@Override
	protected void onPause() {
		super.onPause();		
		if (getTipoLigacao() != 0){    
			status = (ImageView) findViewById(R.id.status);
			if(imovel.getIndcImovelCalculado().equals(ConstantesSistema.SIM) && imovel.getIndcImovelImpresso().equals(ConstantesSistema.NAO)){
				status.setImageResource(R.drawable.bolacalculado);
			}else if(imovel.getIndcImovelCalculado().equals(ConstantesSistema.SIM) && imovel.getIndcImovelImpresso().equals(ConstantesSistema.SIM)){
				status.setImageResource(R.drawable.bolaimpresso);
			}
			
			verificarErro();
		}
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
	
	
	
	public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU)
        {
        	Intent it = new Intent(this,MenuActivity.class); 
        	getParent().finish();
        	finish();
    		startActivity(it);
    		
    		
        }
        return true;
    }

	@Override
    public void onBackPressed() {
		 Intent i = new Intent(HidrometroBaseActivity.this,ListaImoveisActivity.class);
		 startActivity(i);
		 finish();
		 //getParent().finish();
    }  
	

    private void verificarBloqueioRecalcularConta(){
    	
    	if(fachada.verificarBloqueioRecalcularConta(imovel) == true){
    		if (getLeitura()!=null){
    			getLeitura().setFocusable(false);
    			getLeitura().setEnabled(false);
    		}
    		if(getAnormalidadeInformada()!=null){
    			getAnormalidadeInformada().setFocusable(false);
    			getAnormalidadeInformada().setEnabled(false);
    		}
    		if(getSpinnerAnormalidade() != null){
    			getSpinnerAnormalidade().setEnabled(false);
	    	}
    	}
    		
    }
    
    /**
	 * Verifica se a leitura e/ou anormalidade do imóvel 
	 * foi alterada
	 * 
	 * @return
	 */
	protected boolean leituraAlteradaUnica(Integer tipoLigacao){
		boolean alterado = false;
		EditText leitura = null;
		EditText anormalidade = null;
		
		if(tipoLigacao.equals(ConstantesSistema.LIGACAO_AGUA)){
			leitura = HidrometroBaseActivity.leituraAgua;
			anormalidade = HidrometroBaseActivity.idAnormalidadeAgua;
		}else{
			leitura = HidrometroBaseActivity.leituraPoco;
			anormalidade = HidrometroBaseActivity.idAnormalidadePoco;
		}
		
		//Verifica se a leitura foi atualizada
		if ( (getHidrometro().getLeitura()==null && leitura == null ||
				getHidrometro().getLeitura()==null && (leitura!= null && leitura.getText().toString().trim().length()==0) || 
				(getHidrometro().getLeitura()!=null && getHidrometro().getLeitura()!=null && leitura.getText().toString().equals(getHidrometro().getLeitura().toString())))
				&& 
				//Verifica se a anormalidade foi atualizada
				((anormalidade.getText().toString().equals("") && getHidrometro().getAnormalidade()==null)
						|| (getHidrometro().getAnormalidade()!=null && anormalidade.getText().toString().equals(getHidrometro().getAnormalidade().toString())))){
			
			//Se leitura e anormalidade estão iguais
			alterado = false;
		} else {
			alterado = true;
		}
		
		return alterado;
		
	}
	
	private void verificarRotaFinalizalida(){
		if(fachada.obterQuantidadeImoveisVisitados().equals(fachada.obterQuantidadeImoveis())){
			if(nomeEndereco!=null && rotaFinalizada!=null){
				nomeEndereco.setVisibility(View.GONE);
				rotaFinalizada.setVisibility(View.VISIBLE);
			}
		}
	}
    
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	Fachada.setContext(this);
    	
    	verificarBloqueioRecalcularConta();
    	verificarRotaFinalizalida();
    }
    
}