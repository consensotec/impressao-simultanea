package com.br.ipad.isc.util;

import android.bluetooth.BluetoothAdapter;

public class Bluetooth {
	
	public static boolean  ativarBluetooth(){
		
		if(!ConstantesSistema.SIMULADOR){
			BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
			if (!mBluetoothAdapter.isEnabled()) {
	        	return mBluetoothAdapter.enable();
	        }
		}
	     
        
        
        return false;
	}
	
	public static boolean desativarBluetooth(){
		
		if(!ConstantesSistema.SIMULADOR){
			BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();    
			
	        if (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()) {
	        	 mBluetoothAdapter.disable();
	        	 return true;
	        }
		}
		
        return false;
	}
	

	public static void resetarBluetooth(){

		if(!ConstantesSistema.SIMULADOR){
			
			if(desativarBluetooth()){
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			ativarBluetooth();
		}
	}
}
