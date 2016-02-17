
package com.br.ipad.isc.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import com.br.ipad.isc.util.Util;

/**
 * [] Classe Básica - Categoria Subcategoria
 * 
 * @author Fernanda Almeida
 * @since 08/03/2012
 */
public class LogFinalizacao extends ObjetoBasico implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Date dataEnvio;
	private String codigoMensagemFinalizacao;
	
	public LogFinalizacao() {
		super();
	}
	
	public LogFinalizacao(Integer id) {
		super();
		this.id = id;
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
	
	public Date getDataEnvio() {
		return dataEnvio;
	}

	public void setDataEnvio(Date dataEnvio) {
		this.dataEnvio = dataEnvio;
	}

	public String getCodigoMensagemFinalizacao() {
		return codigoMensagemFinalizacao;
	}

	public void setCodigoMensagemFinalizacao(String codigoMensagemFinalizacao) {
		this.codigoMensagemFinalizacao = codigoMensagemFinalizacao;
	}

	private static String[] colunas = new String[] { LogFinalizacoes.ID,LogFinalizacoes.DATA_ENVIO,LogFinalizacoes.CODIGO_MENSAGEM_FINALIZACAO
	};
	
	public String[] getColunas(){
		return colunas;
	}	
	
	public static final class LogFinalizacoes implements BaseColumns {
		public static final String ID = "LGFI_ID";
		public static final String DATA_ENVIO = "LGFI_DTENVIO";
		public static final String CODIGO_MENSAGEM_FINALIZACAO = "LGFI_CDMSGFINALIZACAO";
	}	
		
	public final class LogFinalizacoesTipos {
		public final String ID = " INTEGER PRIMARY KEY AUTOINCREMENT ";
		public final String DATA_ENVIO = " INTEGER NULL";
		public final String CODIGO_MENSAGEM_FINALIZACAO = " INTEGER NULL";
		
		private String[] tipos = new String[] { ID,DATA_ENVIO, CODIGO_MENSAGEM_FINALIZACAO};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
	
	public String getNomeTabela(){
		return "log_finalizacao";
	}
	
	public ContentValues preencherValues() {
		ContentValues values = new ContentValues();
		
		if(getDataEnvio() != null){
			String dataStr = Util.convertDateToDateStr(getDataEnvio());		
			values.put(LogFinalizacoes.DATA_ENVIO, dataStr);	
		}
		values.put(LogFinalizacoes.CODIGO_MENSAGEM_FINALIZACAO, getCodigoMensagemFinalizacao());
		
		return values;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<LogFinalizacao> preencherObjetos(Cursor cursor) {		
			
		int codigo = cursor.getColumnIndex(LogFinalizacoes.ID);
		int dataEnvio = cursor.getColumnIndex(LogFinalizacoes.DATA_ENVIO);
		int codigoMsgFinalizacao = cursor.getColumnIndex(LogFinalizacoes.CODIGO_MENSAGEM_FINALIZACAO);
		
		ArrayList<LogFinalizacao> logFinalizacoes = new ArrayList<LogFinalizacao>();
		
		do{
			LogFinalizacao logFinalizacao = new LogFinalizacao();
				
			logFinalizacao.setId(cursor.getInt(codigo));
			logFinalizacao.setDataEnvio(Util.convertStrToDataBusca(cursor.getString(dataEnvio)));
			logFinalizacao.setCodigoMensagemFinalizacao(cursor.getString(codigoMsgFinalizacao));
			
			logFinalizacoes.add(logFinalizacao);
			
			
		} while (cursor.moveToNext());
		
		return logFinalizacoes;
	}

}
