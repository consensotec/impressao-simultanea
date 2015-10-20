
package com.br.ipad.isc.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import com.br.ipad.isc.util.Util;

/**
 * 
 * @author Fernanda Almeida
 * @since 19/07/2012
 */
public class ConsumoTipo extends ObjetoBasico implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//Construtor default
	public ConsumoTipo(){}
	
	public ConsumoTipo(ArrayList<String> obj){
		insertFromFile(obj);
	}
		
	private Integer id;
	private String descricao;
	private Date ultimaAlteracao;
	
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

	public Date getUltimaAlteracao() {
		return ultimaAlteracao;
	}
	public void setUltimaAlteracao(String ultimaAlteracao) {
		this.ultimaAlteracao = Util.getData(ultimaAlteracao);
	}
	
	private static String[] colunas = new String[] { ConsumosTipos.ID,ConsumosTipos.DESCRICAO,ConsumosTipos.ULTIMAALTERACAO
	};
	
	public String[] getColunas(){
		return colunas;
	}	
	
	public static final class ConsumosTipos implements BaseColumns {
		public static final String ID = "CSTP_ID";
		public static final String DESCRICAO = "CSTP_DESCRICAO";
		public static final String ULTIMAALTERACAO  = "CSTP_TMULTIMAALTERACAO";
	}
	
	public String getNomeTabela(){
		return "consumo_tipo";
	}
	
	public final class ConsumosTiposTipos {
		public final String ID = " INTEGER PRIMARY KEY AUTOINCREMENT ";	
		public final String DESCRICAO = " VARCHAR(30) NOT NULL ";
		public final String ULTIMAALTERACAO = " TIMESTAMP NOT NULL ";

		private String[] tipos = new String[] {
				ID, DESCRICAO, ULTIMAALTERACAO};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
	
	private void insertFromFile(ArrayList<String> obj){
		setIdString(obj.get(1));
		
		setDescricao(obj.get(2));
	
		String date = Util.convertDateToDateStr(Util.getCurrentDateTime());	
		setUltimaAlteracao(date);
	}

	public ContentValues preencherValues() {
		ContentValues values = new ContentValues();
		values.put(ConsumosTipos.ID, getId());
		values.put(ConsumosTipos.DESCRICAO, getDescricao());		
		String dataStr = Util.convertDateToDateStr(Util.getCurrentDateTime());
		values.put(ConsumosTipos.ULTIMAALTERACAO, dataStr);
		
		return values;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ConsumoTipo> preencherObjetos(Cursor cursor) {		
		ArrayList<ConsumoTipo> retorno = null;
		
		int codigo = cursor.getColumnIndex(ConsumosTipos.ID);
		int descricao = cursor.getColumnIndex(ConsumosTipos.DESCRICAO);			
		int ultimaAlteracao = cursor.getColumnIndex(ConsumosTipos.ULTIMAALTERACAO);
		
		if (cursor.moveToFirst()) {			
			retorno = new ArrayList<ConsumoTipo>();			
			do {	
				ConsumoTipo consumoTipo = new ConsumoTipo();

				consumoTipo.setId(Util.getIntBanco(cursor, ConsumosTipos.ID, codigo));
				consumoTipo.setDescricao(cursor.getString(descricao ));
				consumoTipo.setUltimaAlteracao(cursor.getString(ultimaAlteracao));
		
				retorno.add(consumoTipo);
				
			} while (cursor.moveToNext());
		}		
		return retorno;
	}
}