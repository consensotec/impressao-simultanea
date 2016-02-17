
package com.br.ipad.isc.gui;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.br.ipad.isc.R;
import com.br.ipad.isc.adapters.SelecionarArquivoAdapter;
import com.br.ipad.isc.fachada.Fachada;
import com.br.ipad.isc.util.ConstantesSistema;
import com.br.ipad.isc.util.Util;

public class SelecionarArquivoActivity extends BaseActivity {

	private File filesDir;
	private ArrayList<String> listFiles = new ArrayList<String>();
	private SelecionarArquivoAdapter adapter;
	private String path;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (execute){	
			setContentView(R.layout.selecionar_arquivo_activity);	
			Fachada.setContext(this);
			
			if(Fachada.getInstance().verificarExistenciaBancoDeDados()){
				Intent it = new Intent(SelecionarArquivoActivity.this, LoginActivity.class);
				startActivity(it);
				finish();
			}
		
			 filesDir = new File( ConstantesSistema.CAMINHO_OFFLINE );
			 if(!filesDir.exists()){
				 filesDir.mkdirs();
			 }
			
	
	        FileFilter filter = new FileFilter() {
	            public boolean accept(File pathname) {
	            	return pathname.getName().endsWith("");
	            }
	        };
	        
	        ListView lv = (ListView) findViewById(R.id.listFiles);
	        File[] prefFiles = filesDir.listFiles(filter);
	        if(prefFiles != null){
		        for (File f : prefFiles) {
		        	listFiles.add(f.getName());
		        }
	        }
	        
	        adapter = new SelecionarArquivoAdapter(this, listFiles);
	        lv.setAdapter(adapter);
	                
	        lv.setOnItemClickListener(new OnItemClickListener() {
	
				public void onItemClick(AdapterView<?> arg0, View v, int position, long pos) {
	
					// Guarda o path do diretorio selecionado
					path = (String) v.getTag();
					
					// Chama a tela de carregamento do arquivo 
					// passando o parametro offline
					Intent it = new Intent(SelecionarArquivoActivity.this, DownloadArquivoActivity.class);
					it.putExtra("offline", path); 
					startActivity(it);
					finish();
					
					
				}
			});
		}
    } 
	
	@Override
	public void onBackPressed() {
		Intent i = new Intent(this, DownloadArquivoActivity.class);
		this.startActivity(i);
		finish();
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	
    	//return super.onCreateOptionsMenu(menu);
    	menu.add(0,ConstantesSistema.MENU_SAIR,0,R.string.str_menu_sair);
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected( MenuItem item) {    	
    	
    	switch (item.getItemId()){
    	case ConstantesSistema.MENU_SAIR:
    		
    		Util.sairAplicacao(SelecionarArquivoActivity.this);
    		
    		break;
    	}
		return false;
    }
	


       
}

