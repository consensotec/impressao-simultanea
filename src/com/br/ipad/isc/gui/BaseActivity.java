package com.br.ipad.isc.gui;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.br.ipad.isc.fachada.Fachada;

/**
 * Activity Base identificara o device usado
 * Se a orientacao default do celular dor LANDSCAPE
 * Todas as telas do ISC ficarao com Screen LANDSCAPE
 * 
 * @author Amelia Pessoa
 */
public class BaseActivity extends Activity {

	protected boolean execute = true;
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
	     savedInstanceState.putBoolean("execute", true);
	    super.onSaveInstanceState(savedInstanceState);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	       super.onCreate(savedInstanceState);

	       if(Fachada.getInstance().isOrientacaoLandscape(this)){
	    	   
	    	   if (savedInstanceState != null) {
	               super.onRestoreInstanceState(savedInstanceState);

	               boolean resultCode = savedInstanceState.getBoolean("execute");
	               savedInstanceState.remove("execute");
	               this.execute = resultCode;
	           } else {
	        	   execute = false;
	        	   setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	           }
	       }	        
	}
}
