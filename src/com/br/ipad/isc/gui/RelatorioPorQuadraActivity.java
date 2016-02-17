package com.br.ipad.isc.gui;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.br.ipad.isc.R;
import com.br.ipad.isc.adapters.ListaRelatorioPorQuadraAdapter;
import com.br.ipad.isc.bean.helpers.RelatorioPorQuadra;
import com.br.ipad.isc.fachada.Fachada;

public class RelatorioPorQuadraActivity extends BaseActivity {
	 /** Called when the activity is first created. */
		private ListaRelatorioPorQuadraAdapter adapter;
		private ListView lv;	
		private Fachada fachada;
		private ArrayList<Integer> arrayListIdsQuadras;
		
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        if (execute){	
		        setContentView(R.layout.lista_relatorio_por_quadra);
		        
		        fachada = Fachada.getInstance();
		        
		        lv = (ListView) findViewById(R.id.listRelatorioPorQuadra);
		         
		        lv.setOnItemClickListener(new OnItemClickListener() {
	
					public void onItemClick(AdapterView<?> arg0, View view, int position,
							long id) {					
	//					relatorioPorQuadra = (RelatorioPorQuadra) view.getTag();
	//						if(relatorioPorQuadra.getNumeroQuadra().equals("999")){
	//				
	//							
	//						}
						}
				});	
	        }
	    }
	    
	    @Override
	    protected void onResume() {
	    
	    	super.onResume();
	    	
	    	ArrayList<RelatorioPorQuadra> arrayListRelatorioPorQuadra = new ArrayList<RelatorioPorQuadra>();
	    	
	    	arrayListIdsQuadras = fachada.buscarQuadras();
	    	
	    	for(int i = 0; i< arrayListIdsQuadras.size(); i++){
	    		
	    		RelatorioPorQuadra relatorioPorQuadra = 
		    			new RelatorioPorQuadra(Fachada.getInstance().obterQuantidadeImoveisPorQuadra(arrayListIdsQuadras.get(i)), 
		    					Fachada.getInstance().obterQuantidadeImoveisVisitadosPorQuadra(arrayListIdsQuadras.get(i)), 
		    					Fachada.getInstance().obterQuantidadeImoveisNaoVisitadosPorQuadra(arrayListIdsQuadras.get(i)), 
		    					arrayListIdsQuadras.get(i));	    	

		    	arrayListRelatorioPorQuadra.add(relatorioPorQuadra);	
		    	
		    	
	    	}
	    	
			adapter = new ListaRelatorioPorQuadraAdapter(RelatorioPorQuadraActivity.this, arrayListRelatorioPorQuadra );
			lv.setAdapter(adapter);  
	    }
		
		@Override
	    public void onBackPressed() { 
			finish();
		}
	    
	    
}