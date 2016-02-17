
package com.br.ipad.isc.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.br.ipad.isc.R;
import com.br.ipad.isc.adapters.ListaImpressoraAdapter;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.bean.helpers.Impressora;
import com.br.ipad.isc.fachada.Fachada;
import com.br.ipad.isc.util.Bluetooth;
import com.br.ipad.isc.util.SettingsHelper;
import com.br.ipad.isc.util.Util;
import com.zebra.android.discovery.DiscoveredPrinterBluetooth;

public class ListaImpressorasActivity extends BaseActivity {
    /** Called when the activity is first created. */
	private ListaImpressoraAdapter adapter;
	
	private ListView lv;	
	
	private ArrayList<Impressora> impressorasEncontradas = null;
	private Map<String, DiscoveredPrinterBluetooth>	impressoras = new HashMap<String, DiscoveredPrinterBluetooth>();
	
	
	private Impressora impressora;

    private ImovelConta imovel;
    private Button btPesquisarNovamente;
    private TextView status;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      
        if (execute){	
	        Bluetooth.ativarBluetooth();
	        
	        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
	        setContentView(R.layout.lista_impressoras);
	        Fachada.setContext( this );
	        
	        status = (TextView) findViewById(R.id.status);
	        status.setText("");
	        
	        
	        //Inicia o array de impressoras encontradas
	        impressorasEncontradas = new ArrayList<Impressora>();
	        lv = (ListView) findViewById(R.id.listImpressoras);
	        lv.setOnItemClickListener(mDeviceClickListener);
	        
	        btPesquisarNovamente = (Button) findViewById(R.id.btPesquisarNovamente);
	        btPesquisarNovamente.setOnClickListener(new OnClickListener() {
	        	public void onClick(View arg0) {
	        		
	        		//Limpar a lista de impressoras encontradas
	        		impressorasEncontradas.clear();
	        		impressoras.clear();
	        		adapter = null; 
					lv.setAdapter(adapter);
	        		
	        		Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
					startActivity(intent);        		
	        	}
	        });
	        
	        onResume();
        }
    } 
    
    /**
     * Comeca a procurar dispositivos com BluetoothAdapter
     */

    // Funcao do click da listagem de impressorras
    private OnItemClickListener mDeviceClickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
        	
            impressora  = (Impressora) v.getTag();
            String key = impressora.getBluetoothName() + " (" + impressora.getBluetoothAdress() + ")";
            DiscoveredPrinterBluetooth p = impressoras.get(key);
    		SettingsHelper.saveBluetoothAddress(getApplicationContext(), p.address);
    		SettingsHelper.savePrinterName(getApplicationContext(), p.friendlyName);

    		imovel = (ImovelConta) getIntent().getSerializableExtra("imovel");
    		
    		if(imovel != null){
    			 
    			//Se for imóvel condomínio direciona para RateioActivity
    			if(imovel.isCondominio()){
    				Intent it = new Intent(ListaImpressorasActivity.this, RateioActivity.class);
    				it.putExtra("macro", imovel);
    				finish();
    				startActivity(it);
    			} else {
    
	    			boolean contaImpressa = Fachada.getInstance().verificarImpressaoConta(imovel,getApplicationContext(),0);
	    			
					if(contaImpressa){	    					    				
						Util.chamaProximo(ListaImpressorasActivity.this, imovel);
						//finish();
					}
    			}
    				
    		}else{
    			
    			AlertDialog.Builder alert = new AlertDialog.Builder(ListaImpressorasActivity.this);
    			alert.setTitle(R.string.sucesso_conecao);
    			alert.setNeutralButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
    				
    				public void onClick(DialogInterface dialog, int which) {
    					//Intent it = new Intent(ListaImpressorasActivity.this, MenuActivity.class);
    					//startActivity(it);
    					finish();
    				}
    			});
    			alert.show();

    		}
    		  
        }
    };
    
    
    @Override
    protected void onResume() {
    	super.onResume();
    	if (execute){    		
    		buscarImpressorasPareadas();
    	}
    }
    
    private void buscarImpressorasPareadas(){
    	
    	BluetoothAdapter mBtAdapter;
    	
    	// Pega o adptador Bluetooth local
    	mBtAdapter = BluetoothAdapter.getDefaultAdapter();
    	
    	//Procura por dispositivos pareados
        Set <BluetoothDevice> dispositivosPareados = mBtAdapter.getBondedDevices();
       
        for(BluetoothDevice dispositivoParareado: dispositivosPareados){
        	
        	BluetoothDevice device = mBtAdapter.getRemoteDevice(dispositivoParareado.toString());
        	
        	//Verifica se o dispositivo é uma impressora
        	if (device.getBluetoothClass() != null && device.getBluetoothClass().getMajorDeviceClass() == 0x600) {
        		if ((device.getBluetoothClass().getDeviceClass() & 0x80) != 0) {

        			DiscoveredPrinterBluetooth p = new DiscoveredPrinterBluetooth(device.getAddress(), device.getName());
        			String key = p.friendlyName + " (" + p.address + ")";
        			Impressora imp = new Impressora();
        			imp.setBluetoothAdress(device.getAddress());
        			imp.setBluetoothName(device.getName());

        			if(!impressorasEncontradas.contains(imp)){
        				impressorasEncontradas.add(imp);
        				impressoras.put(key, p);
        				adapter = new ListaImpressoraAdapter(ListaImpressorasActivity.this,impressorasEncontradas); 
        				lv.setAdapter(adapter);
        			}
        		}
        	}
        }
        
        if (impressorasEncontradas.size() == 0) {
        	status.setText(getString(R.string.dispositivo_nao_pareado));
        }else{
        	status.setText(R.string.selecionar_dispositivo);
        }
        	
    }
    
    @Override
    public void onBackPressed() {
		finish();
    }
    

}