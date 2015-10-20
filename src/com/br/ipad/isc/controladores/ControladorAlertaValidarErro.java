package com.br.ipad.isc.controladores;

import android.content.Intent;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.gui.DownloadApkActivity;
import com.br.ipad.isc.gui.TabsActivity;

public class ControladorAlertaValidarErro extends ControladorAlertaBasico  {

	private ImovelConta imovel;	
	
	//Construtor imóvel revisitar
	public ControladorAlertaValidarErro(ImovelConta imovel) {
		super();
		this.imovel = imovel;
	}	
	
	public ControladorAlertaValidarErro() {
		super();
	}	
		

	@Override
	public void alertaPerguntaSim() {
		//Imóvel a revisitar
		Intent intent = new Intent(getContext(), TabsActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);    
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("imovel", imovel);
		intent.putExtra(getContext().getString(R.string.str_extra_alert_revisitar), true);
		getContext().startActivity(intent); 
		
	}

	@Override
	public void alertaPerguntaNao() {		
			
		
	}

	@Override
	public void alertaMensagem() {
		
		Intent intent = new Intent(getContext(), DownloadApkActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);    
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		getContext().startActivity(intent); 
		
	}


}