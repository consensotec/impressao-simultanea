package com.br.ipad.isc.bean;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;

public abstract class ObjetoBasico {

	public abstract ContentValues preencherValues();
	
	public abstract <T extends ObjetoBasico>  ArrayList<T> preencherObjetos(Cursor cursor);
	
	public abstract String getNomeTabela();
	
	public abstract Integer getId();
	
	public abstract String[] getColunas();
	
	public String getNameId(){
		return getColunas()[0];
	}
}
