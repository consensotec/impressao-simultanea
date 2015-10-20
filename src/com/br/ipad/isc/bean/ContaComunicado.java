package com.br.ipad.isc.bean;

import java.io.Serializable;
import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import com.br.ipad.isc.util.Util;

/**
 * @author Jonathan Marcos
 * @since 09/02/2014
 */
public class ContaComunicado extends ObjetoBasico implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static String CONJUNTO_CARACTERE_ENTER = "<brbr>";
	public static String INICIO_LINHA = "";
	
	public ContaComunicado(){}
	
	public ContaComunicado(ArrayList<String> objeto){
		insertFromFile(objeto);
	}
	
	private Integer id;
	private String descricao;
	
	public static final class ContaComunicados implements BaseColumns {
		public static final String ID = "COMU_ID";
		public static final String DESCRICAO = "COMU_DSDESCRICAO";
	}
	
	private static String[] colunas = new String[] {
		ContaComunicados.ID,
		ContaComunicados.DESCRICAO
	};
	
	public String[] getColunas(){
		return colunas;
	}
	
	public final class ContaComunicadosTipos {
		public final String ID = " INTEGER PRIMARY KEY ";	
		public final String DESCRICAO = " TEXT NOT NULL ";

		private String[] tipos = new String[] {
				ID, 
				DESCRICAO
		};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
	
	private void insertFromFile(ArrayList<String> obj){
		setId(Integer.valueOf(obj.get(1).toString()));
		setDescricao(obj.get(2));
	}
	
	public ContentValues preencherValues() {
		ContentValues values = new ContentValues();
		values.put(ContaComunicados.ID, getId());
		values.put(ContaComunicados.DESCRICAO, getDescricao());		
		return values;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ContaComunicado> preencherObjetos(Cursor cursor) {		
		ArrayList<ContaComunicado> retorno = null;
		
		int codigo = cursor.getColumnIndex(ContaComunicados.ID);
		int descricao = cursor.getColumnIndex(ContaComunicados.DESCRICAO);			

		if (cursor.moveToFirst()) {			
			retorno = new ArrayList<ContaComunicado>();			
			do {	
				ContaComunicado contaComunicado = new ContaComunicado();

				contaComunicado.setId(Util.getIntBanco(cursor, ContaComunicados.ID, codigo));
				contaComunicado.setDescricao(cursor.getString(descricao ));
			
				retorno.add(contaComunicado);
				
			} while (cursor.moveToNext());
		}		
		return retorno;
	}
	
	public String getNomeTabela(){
		return "conta_comunicado";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setIdString(String id) {
		this.id = Util.verificarNuloInt(id);
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}