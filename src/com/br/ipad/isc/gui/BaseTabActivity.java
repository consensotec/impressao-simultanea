package com.br.ipad.isc.gui;

import android.app.TabActivity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.br.ipad.isc.fachada.Fachada;

/**
 * TabActivity Base identificara o device usado
 * Se a orientacao default do celular dor LANDSCAPE
 * Todas as telas do ISC ficarao com Screen LANDSCAPE
 * 
 * @author Amelia Pessoa
 */
public class BaseTabActivity extends TabActivity {

	protected boolean execute = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	       super.onCreate(savedInstanceState);

	       if(Fachada.getInstance().isOrientacaoLandscape(this)){
	    	   setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	       }	        
	}
}
