
package com.br.ipad.isc.gui;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import com.br.ipad.isc.R;
import com.br.ipad.isc.adapters.LegendaAdapter;
import com.br.ipad.isc.adapters.ListaImovelAdapter;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.bean.ImovelRevisitar;
import com.br.ipad.isc.bean.SistemaParametros;
import com.br.ipad.isc.excecoes.FachadaException;
import com.br.ipad.isc.fachada.Fachada;
import com.br.ipad.isc.util.ConstantesSistema;

public class ListaImoveisActivity extends BaseActivity {
    /** Called when the activity is first created. */
	private ListaImovelAdapter adapter;
	private Intent i;
	private ListView lv;	
	private ImovelConta imovel;
	private ImageButton procurar;
	private Spinner tipoBuscaSpinner;
	private Spinner legendaSpinner;
	private ArrayList<ImovelConta> listImovel;
	private EditText busca;
	private String buscaAntiga = "";
	
	//private boolean desabilitaLimpaConsulta;
	private String[] tiposBusca;
	private String tipoBuscaSelecionado;
	private Fachada fachada = Fachada.getInstance();
	private String[] legendas;
	private Integer[] imagensLegenda;
	private HashMap<String, Object> mapLegenda;
	private ArrayList<HashMap<String, Object>> spinnerList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if (execute){	
	        setContentView(R.layout.lista_imoveis);
	        Fachada.setContext(this);
	                               
	        // Para o teclado não aparecer ao entrar na tela
	        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	        
	        setUpWidgets();
        }
    }
    
    private void setUpWidgets() {    	 
               
    	tiposBusca = new String[] { getString(R.string.str_imoveis_spinner_todos),
    			getString(R.string.str_matricula),
    			getString(R.string.str_imoveis_spinner_quadra),
    			getString(R.string.str_imoveis_spinner_hidrometro),
    			getString(R.string.str_imoveis_spinner_imoveis_nao_lidos),
    			getString(R.string.str_imoveis_spinner_posicao),
    			getString(R.string.str_imoveis_spinner_sequencial_rota),
    			getString(R.string.str_imoveis_spinner_imoveis_revisitar)};
    	
    	tipoBuscaSpinner = (Spinner) findViewById(R.id.buscaTipo);
    	
    	busca = (EditText) findViewById(R.id.busca);
    	    	
    	lv = (ListView) findViewById(R.id.listImoveis); 
        procurar = (ImageButton) findViewById(R.id.procurar);
         
        lv.setOnItemClickListener(new OnItemClickListener() {

 			
 			public void onItemClick(AdapterView<?> arg0, View view, int position,
 					long id) {
 				
 				imovel  = (ImovelConta) view.getTag();
 				i = new Intent(ListaImoveisActivity.this, TabsActivity.class);
 				i.putExtra("imovel", imovel);
 				startActivity(i);
 				
 			}
         	
 		});        

    	ArrayAdapter<String> tipoBuscaAdapter = new ArrayAdapter<String>(ListaImoveisActivity.this,
    			android.R.layout.simple_spinner_item,tiposBusca);
    	tipoBuscaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    	tipoBuscaSpinner.setAdapter(tipoBuscaAdapter);
    	
    	tipoBuscaSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) { 
				
				tipoBuscaSelecionado = (String) parent.getSelectedItem();
				if(tipoBuscaSelecionado.equals(getString(R.string.str_imoveis_spinner_todos))){

					limparLista();
					
					try {
						//Caso Compesa retornar lista com imóveis novos na frente
						listImovel =fachada.buscarImovelContas();
			    		
					} catch (FachadaException e) {
						Log.e( ConstantesSistema.CATEGORIA , e.getMessage());
						e.printStackTrace();
					}
					adapter = new ListaImovelAdapter(ListaImoveisActivity.this,listImovel);
					lv.setAdapter(adapter);
					
				}else{
					if( !buscaAntiga.equals(tipoBuscaSelecionado)){
						busca.setText("");
					}
					buscaAntiga = tipoBuscaSelecionado;
					busca.setEnabled(true);
				}
				if(tipoBuscaSelecionado.equals(getString(R.string.str_matricula))){
					busca.setInputType(InputType.TYPE_CLASS_NUMBER);
				}else 
				//HIDRÔMETRO
				if(tipoBuscaSelecionado.equals(getString(R.string.str_imoveis_spinner_hidrometro))){
					busca.setInputType(InputType.TYPE_CLASS_TEXT);
				}else
					
				// QUADRA
				if(tipoBuscaSelecionado.equals(getString(R.string.str_imoveis_spinner_quadra))){
					busca.setInputType(InputType.TYPE_CLASS_NUMBER);
				
				//NÃO LIDOS
				}else if(tipoBuscaSelecionado.equals(getString(R.string.str_imoveis_spinner_imoveis_nao_lidos))){

					limparLista();
					
					try {
						listImovel =fachada.buscarImovelContasNaoLidos();
					} catch (FachadaException e) {
						Log.e( ConstantesSistema.CATEGORIA , e.getMessage());
						e.printStackTrace();
					}
					if(listImovel != null){
						adapter = new ListaImovelAdapter(ListaImoveisActivity.this,listImovel);
						lv.setAdapter(adapter);
						
					//Limpa lista
					}else{
						listImovel.clear();
						adapter = new ListaImovelAdapter(ListaImoveisActivity.this,listImovel);
						lv.setAdapter(adapter);
					}
				
				//REVISITAR
				}else if(tipoBuscaSelecionado.equals(getString(R.string.str_imoveis_spinner_imoveis_revisitar))){	
					
					limparLista();
					
					ArrayList<ImovelRevisitar> imoveisRevisitar = Fachada.getInstance().buscarImovelNaoRevisitado();
					if(imoveisRevisitar != null){
						listImovel = new ArrayList<ImovelConta>();
						for(ImovelRevisitar imovelRevisitar : imoveisRevisitar){
							ImovelConta imovelRevisitado = fachada.pesquisarPorId(imovelRevisitar.getMatricula().getId(), new ImovelConta());
							listImovel.add(imovelRevisitado);
						}
						
						adapter = new ListaImovelAdapter(ListaImoveisActivity.this,listImovel);
						lv.setAdapter(adapter);
					//Limpa lista
					}else{
						listImovel.clear();
						adapter = new ListaImovelAdapter(ListaImoveisActivity.this,listImovel);
						lv.setAdapter(adapter);
					}
				
												
				}else if(tipoBuscaSelecionado.equals(getString(R.string.str_imoveis_spinner_posicao))){
					busca.setInputType(InputType.TYPE_CLASS_NUMBER);
				
				} else if(tipoBuscaSelecionado.equals(getString(R.string.str_imoveis_spinner_sequencial_rota))){
					busca.setInputType(InputType.TYPE_CLASS_NUMBER);
				}
				if( listImovel != null && listImovel.size()==0 && imovel == null){
					busca.setText("");
				}
				
				if(!busca.isEnabled()){
				    // Para o teclado não aparecer ao entrar na tela
	 		      InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	 		      mgr.hideSoftInputFromWindow(busca.getWindowToken(), 0);
				}
								
			}
			
			private void limparLista() {
				busca.setEnabled(false);
				//busca.setKeyListener(null);
				busca.setText("");

				//Caso alguma pesquisa já tenha sido feita, limpa a lista de imóveis para não juntar as pesquisas
				if( imovel != null){
					
					listImovel.clear();
					busca.setText("");
				}
				
			}



			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
         
        procurar.setOnClickListener(new OnClickListener() {
 			
 			
 			public void onClick(View arg0) {
 		        
 				if(!busca.getText().toString().trim().equals("")){
 					//Caso não esteja na opção 'Todos'
 					if(busca.isEnabled()){
	 					String valorBusca = busca.getText().toString().trim();
	 						 					
	 					try {
	 						
		 					// Busca por matrícula
			 				if(tipoBuscaSelecionado.equals(getString(R.string.str_matricula))){

		 						imovel = (ImovelConta) Fachada.getInstance()
		 								.pesquisarPorId(Integer.valueOf(valorBusca), new ImovelConta());		 								
									 				
			 				}else
			 				//Busca por Hidrômetro
			 				if(tipoBuscaSelecionado.equals(getString(R.string.str_imoveis_spinner_hidrometro))){
			 					imovel =fachada.buscarImovelContaPorHidrometro(valorBusca);
			 					
			 				}else
			 				//Busca por Quadra
			 				if(tipoBuscaSelecionado.equals(getString(R.string.str_imoveis_spinner_quadra))){
			 					listImovel =fachada.buscarImovelContaPorQuadra(valorBusca);
								if(listImovel != null){
				 					adapter = new ListaImovelAdapter(ListaImoveisActivity.this,listImovel);
				 					lv.setAdapter(adapter);
								}
								
			 				}else if(tipoBuscaSelecionado.equals(getString(R.string.str_imoveis_spinner_posicao))){
			 					imovel =fachada.buscarImovelContaPosicao(Integer.parseInt(valorBusca),null);
			 					
			 				}
			 				//Busca por Sequencial Rota
			 				else if(tipoBuscaSelecionado.equals(getString(R.string.str_imoveis_spinner_sequencial_rota))){
			 					imovel =fachada.buscarImovelContaSequencial(Integer.parseInt(valorBusca));
			 					
			 				}
			 				// Caso a pesquisa não retorne uma lista
							if(imovel != null && !tipoBuscaSelecionado.equals(getString(R.string.str_imoveis_spinner_quadra))){
								listImovel.clear();
								listImovel.add(imovel);
								adapter = new ListaImovelAdapter(ListaImoveisActivity.this,listImovel);
								lv.setAdapter(adapter);
								
							} if( (!tipoBuscaSelecionado.equals(getString(R.string.str_imoveis_spinner_quadra)) && imovel == null)
									|| tipoBuscaSelecionado.equals(getString(R.string.str_imoveis_spinner_quadra)) && listImovel == null
									|| (tipoBuscaSelecionado.equals(getString(R.string.str_imoveis_spinner_posicao)) && imovel == null )){
								
								new AlertDialog.Builder(ListaImoveisActivity.this)
				    			.setMessage( getString(R.string.str_imoveis_alert_pesquisa) )
				    			.setNeutralButton(getString(android.R.string.ok), 
				    				new DialogInterface.OnClickListener() {
				    					public void onClick(
				    						DialogInterface dialog,
				    						int which) {
				    						
				    						busca.setText("");
				    					}
				    			}).show();
							}
			 				
		 				} catch (NumberFormatException e) {
							Log.e( ConstantesSistema.CATEGORIA , e.getMessage());
							e.printStackTrace();
						} catch (FachadaException e) {
							Log.e( ConstantesSistema.CATEGORIA , e.getMessage());
							e.printStackTrace();
						}
		 				
					// Caso o usuário não tenha informado nada para pesquisa
	 				}else{
	 					new AlertDialog.Builder(ListaImoveisActivity.this)
						.setMessage( getString(R.string.str_imoveis_alert_pesquisa_vazia) )
						.setNeutralButton(getString(android.R.string.ok), 
							new DialogInterface.OnClickListener() {
								public void onClick(
									DialogInterface dialog,
									int which) {
								}
						}).show();
	 				}
	 			}

 		        // Para o teclado não aparecer ao entrar na tela
 		      InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
 		      mgr.hideSoftInputFromWindow(busca.getWindowToken(), 0);
 			}
 		});
		
        
        // CASO COMPESA, é exibido o botão das legendas
        if(SistemaParametros.getInstancia().getCodigoEmpresaFebraban().equals(ConstantesSistema.CODIGO_FEBRABAN_COMPESA)){
        
	        legendaSpinner = (Spinner) findViewById(R.id.legendaSpinner);
	        legendaSpinner.setVisibility(View.VISIBLE);
	        
	        legendas = new String[] { getString(R.string.str_imoveis_legenda_titulo),  getString(R.string.str_imoveis_legenda_vermelho),
	        		 getString(R.string.str_imoveis_legenda_amarelo),
	        		 getString(R.string.str_imoveis_legenda_verde),  getString(R.string.str_imoveis_legenda_azul) };
	        
	        imagensLegenda = new Integer[] { 0, R.drawable.bgnumeroparado,
	            R.drawable.bgnumeropausa, R.drawable.bgnumero, R.drawable.bgcondominiolegenda };
	       
	        try{
		        initializaListaLegenda();
		        LegendaAdapter legendaAdapter = new LegendaAdapter(getApplicationContext(), spinnerList);
		        legendaSpinner.setAdapter(legendaAdapter);
	       }catch(Exception e ){
	    	   Log.e(ConstantesSistema.CATEGORIA,e.getMessage());
	    	   e.printStackTrace();
	       }
        }
    }

    
    private void initializaListaLegenda() {
        // TODO Auto-generated method stub
    	spinnerList = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < legendas.length; i++) {
            mapLegenda = new HashMap<String, Object>();

            mapLegenda.put("Legenda", legendas[i]);
            mapLegenda.put("Icon", imagensLegenda[i]);
            spinnerList.add(mapLegenda);
        }
//        ImageView imageView = new ImageView(this);
//        imageView.setBackgroundResource((spinnerList.get(0).get("Icon"));
//        spinnerList.get(0).get("Name");
    }
    
	public boolean onKeyUp(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_MENU)
        {
        	Intent it = new Intent(this,MenuActivity.class); 
    		startActivity(it);
    		finish();
        }
       
        return true;
    }
    	
    @Override
    protected void onResume() {
    	super.onResume();
    
    	if (execute){
	    	try {
	    		//Caso Compesa retornar lista com imóveis novos na frente
	    		listImovel =fachada.buscarImovelContas();
	    		
				adapter = new ListaImovelAdapter(ListaImoveisActivity.this,listImovel);
			} catch (FachadaException e) {
				
				Log.e( ConstantesSistema.CATEGORIA , e.getMessage());
			} 
			lv.setAdapter(adapter);  
    	}
    }
    
    @Override
    protected void onRestart() {
    	super.onRestart();
    	
    	if (execute){
	    	try {
	    		//Caso Compesa retornar lista com imóveis novos na frente
	    		listImovel =fachada.buscarImovelContas();
	    		
				adapter = new ListaImovelAdapter(ListaImoveisActivity.this,listImovel);
			} catch (FachadaException e) {
				
				Log.e( ConstantesSistema.CATEGORIA , e.getMessage());
			} 
			lv.setAdapter(adapter);
    	}
    }
    
    
	@Override
    public void onBackPressed() { 
		/*Intent intent = new Intent(ListaImoveisActivity.this, MenuActivity.class);
    	startActivity(intent);*/
    	finish();
	}
    
}