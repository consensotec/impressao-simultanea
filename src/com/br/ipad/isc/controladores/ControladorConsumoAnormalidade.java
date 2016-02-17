
package com.br.ipad.isc.controladores;

import android.content.Context;

public class ControladorConsumoAnormalidade  extends ControladorBasico implements IControladorConsumoAnormalidade{
	
	private static ControladorConsumoAnormalidade instance;
	//private RepositorioConsumoAnormalidade repositorioConsumoAnormalidade;
	protected static Context context;
	
	public void resetarInstancia(){
		instance = null;
	}
	
	protected ControladorConsumoAnormalidade(){
		super();
	}
	
	public static ControladorConsumoAnormalidade getInstance(){
		if ( instance == null ){
			instance =  new ControladorConsumoAnormalidade();
			//instance.repositorioConsumoAnormalidade = RepositorioConsumoAnormalidade.getInstance();
		}		
		return instance;		
	}

	public void setContext(Context ctx ) {
		context = ctx;		
	}	
	
}