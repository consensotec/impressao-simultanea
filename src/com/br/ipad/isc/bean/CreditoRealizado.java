package com.br.ipad.isc.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import com.br.ipad.isc.util.Util;

/**
 * [] Classe Básica - Credito Realizado
 * 
 * @author Fernanda Almeida
 * @since 08/03/2012
 */
public class CreditoRealizado extends ObjetoBasico implements Serializable {
	//Construtor default
	public CreditoRealizado(){}
	
	public CreditoRealizado(ArrayList<String> obj){
		insertFromFile(obj);
	}
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private ImovelConta matricula;
	private String descricaoCreditoTipo;
	private BigDecimal valor;
	private Integer codigoCreditoTipo;
	private Date ultimaAlteracao;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public ImovelConta getMatricula() {
		return matricula;
	}
	public void setMatricula(ImovelConta matricula) {
		this.matricula = matricula;
	}
	public void setIdString(String id) {
		this.id = Util.verificarNuloInt(id);
	}
		
	public String getDescricaoCreditoTipo() {
		return descricaoCreditoTipo;
	}
	public void setDescricaoCreditoTipo(String descricaoCreditoTipo) {
		this.descricaoCreditoTipo = descricaoCreditoTipo;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public Integer getCodigoCreditoTipo() {
		return codigoCreditoTipo;
	}
	public void setCodigoCreditoTipo(Integer codigoCreditoTipo) {
		this.codigoCreditoTipo = codigoCreditoTipo;
	}
	public Date getUltimaAlteracao() {
		return ultimaAlteracao;
	}
	public void setUltimaAlteracao(String ultimaAlteracao) {
		this.ultimaAlteracao = Util.getData(ultimaAlteracao);
	}
	
	private static String[] colunas = new String[] { CreditosRealizados.ID,CreditosRealizados.MATRICULA, CreditosRealizados.DESCRICAOCREDITOTIPO,CreditosRealizados.VALOR, 
	                                                 CreditosRealizados.CODIGOCREDITOTIPO, CreditosRealizados.ULTIMAALTERACAO};
	
	public String[] getColunas(){
		return colunas;
	}	
	
	public static final class CreditosRealizados implements BaseColumns {
		public static final String ID = "CRRZ_ID";
		public static final String MATRICULA = "IMOV_ID";
		public static final String DESCRICAOCREDITOTIPO = "CRRZ_DSCREDITOTIPO";
		public static final String VALOR = "CRRZ_VALOR";
		public static final String CODIGOCREDITOTIPO = "CRRZ_CDCREDITOTIPO";
		public static final String ULTIMAALTERACAO = "CRRZ_TMULTIMAALTERACAO";
	}
	
	private void insertFromFile(ArrayList<String> obj){
		
		ImovelConta matricula = new ImovelConta();		
		matricula.setId(Integer.parseInt(obj.get(1)));
		setMatricula(matricula);
		
		setDescricaoCreditoTipo(obj.get(2));
		if(obj.get(3).length()!=0){	
			BigDecimal valor = new BigDecimal(obj.get(3));
			setValor(valor);	
		}
		if(obj.get(4).length()!=0){
			setCodigoCreditoTipo(Integer.parseInt(obj.get(4)));	
		}
		String date = Util.convertDateToDateStr(Util.getCurrentDateTime());	
		setUltimaAlteracao(date);
	}

	public String getNomeTabela(){
		return "credito_realizado";
	}
	
	public final class CreditoRealizadoTipos {
		public final String ID = " INTEGER PRIMARY KEY AUTOINCREMENT ";
		public final String MATRICULA = " INTEGER CONSTRAINT [FK1_CREDITO_REALIZADO] REFERENCES [imovel_conta]([IMOV_ID]) ON DELETE RESTRICT ON UPDATE RESTRICT ";
		public final String DESCRICAOCREDITOTIPO = " VARCHAR(90) NOT NULL ";
		public final String VALOR = " NUMERIC(13,2) NOT NULL ";
		public final String CODIGOCREDITOTIPO = " INTEGER  NULL ";
		public final String ULTIMAALTERACAO = " TIMESTAMP NOT NULL ";
				
		private String[] tipos = new String[] {
			ID, MATRICULA, DESCRICAOCREDITOTIPO, 
			VALOR, CODIGOCREDITOTIPO, ULTIMAALTERACAO};	
		
		public String[] getTipos(){
			return tipos;
		}
	}

	public ContentValues preencherValues() {
		ContentValues values = new ContentValues();
		values.put(CreditosRealizados.DESCRICAOCREDITOTIPO, getDescricaoCreditoTipo());
		values.put(CreditosRealizados.CODIGOCREDITOTIPO, getCodigoCreditoTipo());
		values.put(CreditosRealizados.MATRICULA, getMatricula().getId());
		if(getValor() != null){
			values.put(CreditosRealizados.VALOR, getValor().toString());
		}
		String dataStr = Util.convertDateToDateStr(Util.getCurrentDateTime());
		values.put(CreditosRealizados.ULTIMAALTERACAO, dataStr);
		
		return values;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<CreditoRealizado> preencherObjetos(Cursor cursor) {		
		ArrayList<CreditoRealizado> retorno = null;
		
		if (cursor.moveToFirst()) {	
			int codigo = cursor.getColumnIndex(CreditosRealizados.ID);
			int matricula = cursor.getColumnIndex(CreditosRealizados.MATRICULA);
			int codigoCreditoTipo = cursor.getColumnIndex(CreditosRealizados.CODIGOCREDITOTIPO);
			int valor = cursor.getColumnIndex(CreditosRealizados.VALOR);
			int descricaoCreditoTipo = cursor.getColumnIndex(CreditosRealizados.DESCRICAOCREDITOTIPO);
			int ultimaAlteracao = cursor.getColumnIndex(CreditosRealizados.ULTIMAALTERACAO);
			
			retorno = new ArrayList<CreditoRealizado>();			
			do {	
				CreditoRealizado creditoRealizado = new CreditoRealizado();
				if(Util.getIntBanco(cursor, CreditosRealizados.MATRICULA, matricula) != null){
					ImovelConta imovelConta = new ImovelConta(cursor.getInt(matricula));
					creditoRealizado.setMatricula(imovelConta);
				}
				creditoRealizado.setId(Util.getIntBanco(cursor, CreditosRealizados.ID, codigo));
				creditoRealizado.setDescricaoCreditoTipo(cursor.getString(descricaoCreditoTipo));
				creditoRealizado.setCodigoCreditoTipo(Util.getIntBanco(cursor, CreditosRealizados.CODIGOCREDITOTIPO, codigoCreditoTipo));
				creditoRealizado.setValor(Util.getDoubleBanco(cursor, CreditosRealizados.VALOR, valor));
				creditoRealizado.setUltimaAlteracao(cursor.getString(ultimaAlteracao));
			
				retorno.add(creditoRealizado);
				
			} while (cursor.moveToNext());
		}		
		return retorno;
	}
}