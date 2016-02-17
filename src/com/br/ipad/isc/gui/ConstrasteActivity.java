package com.br.ipad.isc.gui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.SistemaParametros;
import com.br.ipad.isc.fachada.Fachada;


public class ConstrasteActivity extends Activity {

	
	private SeekBar seekBarContraste;
	private SistemaParametros sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.constrast_activity);
        Fachada.setContext(this);
        
        seekBarContraste = ( SeekBar ) this.findViewById( R.id.seek_bar_contraste );
        
        seekBarContraste.setProgress(0);
        seekBarContraste.setMax( 3 );
        seekBarContraste.setSecondaryProgress(0);
        
        sp = Fachada.getInstance().buscarSistemaParametro();
        
        seekBarContraste.setProgress( sp.getContrasteConta() );
        
        seekBarContraste.setOnSeekBarChangeListener(new OnSeekBarChangeListener() { 

            @Override 
            public void onStopTrackingTouch(SeekBar seekBar) { 
                 
            } 

            @Override 
            public void onStartTrackingTouch(SeekBar seekBar) { 

            } 

            @Override 
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) { 
            	sp.setContrasteConta(progress); 
            	Fachada.getInstance().atualizar( sp );            	
            	Toast.makeText(getBaseContext(), String.valueOf(progress), Toast.LENGTH_SHORT).show();
            } 
        });         
	}
	
}
