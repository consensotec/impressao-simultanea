package com.br.ipad.isc.gui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.br.ipad.isc.R;
import com.br.ipad.isc.util.ConstantesSistema;


public class ControladorAlerta  {
	
	private Context c;
	private int tipo;
	private String msg;
	private Boolean resposta = Boolean.FALSE;
	private int idMensagem;
		     
    public ControladorAlerta(Context c, int tipo, String msg, int idMensagem) {
		super();
		this.c = c;
		this.tipo = tipo;
		this.msg = msg;
		this.idMensagem = idMensagem;
	}
    
    public ControladorAlerta(Context c, int tipo, String msg) {
		super();
		this.c = c;
		this.tipo = tipo;
		this.msg = msg;
	}
	public boolean defineAlerta(){		
		
		c = getC();
		tipo = getTipo();
		
    	if(tipo == ConstantesSistema.ALERTA_MENSAGEM){
    		new AlertDialog.Builder(c)
			.setMessage( getMsg() )
			.setNeutralButton(c.getString(android.R.string.ok), 
				new DialogInterface.OnClickListener() {
					public void onClick(
						DialogInterface dialog,
						int which) {
						
						resposta = true;
						
					}
			}).show();
    	}
    	
    	if(tipo == ConstantesSistema.ALERTA_PERGUNTA){
    		 new AlertDialog.Builder(c)
			.setMessage( getMsg() )
			.setPositiveButton(c.getString(R.string.str_sim), 
				new DialogInterface.OnClickListener() {
					public void onClick(
						DialogInterface dialog,
						int which) {
						
						resposta = true;
						
					}
			})
			.setNegativeButton(c.getString(R.string.str_nao), 
				new DialogInterface.OnClickListener() {
					public void onClick(
						DialogInterface dialog,
						int which) {
						
						resposta = false;						
					}
			})
			.show();
    	}
    	
		
		return resposta;
    	
    }
	public Context getC() {
		return c;
	}
	public void setC(Context c) {
		this.c = c;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public boolean isResposta() {
		return resposta;
	}
	public int getIdMensagem() {
		return idMensagem;
	}
	public void setIdMensagem(int idMensagem) {
		this.idMensagem = idMensagem;
	}
	
    
}