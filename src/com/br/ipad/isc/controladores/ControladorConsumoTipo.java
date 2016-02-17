package com.br.ipad.isc.controladores;

import android.content.Context;

public class ControladorConsumoTipo extends ControladorBasico implements IControladorConsumoTipo{
	private static ControladorConsumoTipo instance;
	//private RepositorioConsumoTipo repositorioConsumoTipo;
	protected static Context context;
	
	public void resetarInstancia(){
		instance = null;
	}
	
	protected ControladorConsumoTipo(){
		super();
	}
	
	public static ControladorConsumoTipo getInstance(){
		if ( instance == null ){
			instance =  new ControladorConsumoTipo();
			//instance.repositorioConsumoTipo = RepositorioConsumoTipo.getInstance();
		}
		
		return instance;		
	}

	public void setContext(Context ctx ) {
		context = ctx;		
	}
	
}
	
	
