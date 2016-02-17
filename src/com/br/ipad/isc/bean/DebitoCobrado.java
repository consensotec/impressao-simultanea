package com.br.ipad.isc.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import com.br.ipad.isc.util.ConstantesSistema;
import com.br.ipad.isc.util.Util;

/**
 * [] Classe Básica - Debito Cobrado
 * 
 * @author Fernanda Almeida
 * @since 08/03/2012
 */
public class DebitoCobrado extends ObjetoBasico implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//Construtor default
	public DebitoCobrado(){}
	
	public DebitoCobrado(ArrayList<String> obj){
		insertFromFile(obj);
	}
		
	private Integer id;
	private ImovelConta matricula;
	private String descricaoDebitoTipo;
	private BigDecimal valor;
	private Integer codigoDebito;
	private Integer indicadorUso;
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
		
	public String getDescricaoDebitoTipo() {
		return descricaoDebitoTipo;
	}
	public void setDescricaoDebitoTipo(String descricaoDebitoTipo) {
		this.descricaoDebitoTipo = descricaoDebitoTipo;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public Integer getIndicadorUso() {
		return indicadorUso;
	}
	public void setIndicadorUso(Integer indicadorUso) {
		this.indicadorUso = indicadorUso;
	}
	public Integer getCodigoDebito() {
		return codigoDebito;
	}
	public void setCodigoDebito(Integer codigoDebito) {
		this.codigoDebito = codigoDebito;
	}
	public Date getUltimaAlteracao() {
		return ultimaAlteracao;
	}
	public void setUltimaAlteracao(String ultimaAlteracao) {
		this.ultimaAlteracao = Util.getData(ultimaAlteracao);
	}
	
	private static String[] colunas = new String[] { DebitosCobrados.ID,DebitosCobrados.MATRICULA, DebitosCobrados.DESCRICAODEBITOTIPO,DebitosCobrados.VALOR, 
	                                       DebitosCobrados.CODIGODEBITO,DebitosCobrados.INDICADORUSO, DebitosCobrados.ULTIMAALTERACAO};
	
	public String[] getColunas(){
		return colunas;
	}	
	
	public static final class DebitosCobrados implements BaseColumns {
		public static final String ID = "DBCO_ID";
		public static final String MATRICULA = "IMOV_ID";
		public static final String DESCRICAODEBITOTIPO = "DBCO_DSDEBITOTIPO";
		public static final String VALOR = "DBCO_VALOR";
		public static final String CODIGODEBITO = "DBCO_CDDEBITO";
		public static final String INDICADORUSO = "DBCO_ICUSO";
		public static final String ULTIMAALTERACAO = "DBCO_TMULTIMAALTERACAO";
	}
	
	public String getNomeTabela(){
		return "debito_cobrado";
	}
	
	public final class DebitosCobradosTipos {
		public final String ID = " INTEGER PRIMARY KEY AUTOINCREMENT ";	
		public final String MATRICULA = " INTEGER CONSTRAINT [FK1_DEBITO_COBRADO] REFERENCES [imovel_conta]([IMOV_ID]) ON DELETE RESTRICT ON UPDATE RESTRICT ";
		public final String DESCRICAODEBITOTIPO = " VARCHAR(90) NOT NULL ";
		public final String VALOR = " NUMERIC(13,2) NOT NULL ";
		public final String CODIGODEBITO = " INTEGER  NULL ";
		public final String INDICADORUSO = " INTEGER NULL DEFAULT 2 ";
		public final String ULTIMAALTERACAO = " TIMESTAMP NOT NULL ";
		
		
		private String[] tipos = new String[] {
				ID, MATRICULA, DESCRICAODEBITOTIPO, VALOR,
				CODIGODEBITO, INDICADORUSO, ULTIMAALTERACAO};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
	
	private void insertFromFile(ArrayList<String> obj){
		ImovelConta matricula = new ImovelConta();		
		matricula.setId(Integer.parseInt(obj.get(1)));
		setMatricula(matricula);	
		
		setDescricaoDebitoTipo(obj.get(2));	
		if(obj.get(3).length()!=0){
			BigDecimal valor = new BigDecimal(obj.get(3));
			setValor(valor);	
		}
		if(obj.get(4).length()!=0){
			setCodigoDebito(Integer.parseInt(obj.get(4)));	
		}
		String date = Util.convertDateToDateStr(Util.getCurrentDateTime());
		setIndicadorUso(new Integer(ConstantesSistema.SIM));	
		setUltimaAlteracao(date);
	}

	public ContentValues preencherValues() {
		ContentValues values = new ContentValues();
		
		values.put(DebitosCobrados.CODIGODEBITO, getCodigoDebito());
		
		values.put(DebitosCobrados.DESCRICAODEBITOTIPO, getDescricaoDebitoTipo());
		values.put(DebitosCobrados.MATRICULA, getMatricula().getId());
		
		if(getValor() != null){
			values.put(DebitosCobrados.VALOR, getValor().toString());
		}
		
		values.put(DebitosCobrados.INDICADORUSO, getIndicadorUso());
		
		String dataStr = Util.convertDateToDateStr(Util.getCurrentDateTime());
		
		
		
		values.put(DebitosCobrados.ULTIMAALTERACAO, dataStr);
		
		return values;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<DebitoCobrado> preencherObjetos(Cursor cursor) {		
		ArrayList<DebitoCobrado> retorno = null;
		
		if (cursor.moveToFirst()) {	
			int codigo = cursor.getColumnIndex(DebitosCobrados.ID);
			int matricula = cursor.getColumnIndex(DebitosCobrados.MATRICULA);
			int codigoDebito = cursor.getColumnIndex(DebitosCobrados.CODIGODEBITO);
			int valor = cursor.getColumnIndex(DebitosCobrados.VALOR);
			int indicadorUso = cursor.getColumnIndex(DebitosCobrados.INDICADORUSO);
			int descricaoDebito = cursor.getColumnIndex(DebitosCobrados.DESCRICAODEBITOTIPO);
			int ultimaAlteracao = cursor.getColumnIndex(DebitosCobrados.ULTIMAALTERACAO);
			
			retorno = new ArrayList<DebitoCobrado>();			
			do {	
				DebitoCobrado debitoCobrado = new DebitoCobrado();
				if(Util.getIntBanco(cursor, DebitosCobrados.MATRICULA, matricula) != null){
					ImovelConta imovelConta = new ImovelConta(cursor.getInt(matricula));
					debitoCobrado.setMatricula(imovelConta);
				}
				debitoCobrado.setId(Util.getIntBanco(cursor, DebitosCobrados.ID, codigo));
				debitoCobrado.setDescricaoDebitoTipo(cursor.getString(descricaoDebito));
				debitoCobrado.setCodigoDebito(Util.getIntBanco(cursor, DebitosCobrados.CODIGODEBITO, codigoDebito));
				debitoCobrado.setIndicadorUso(Util.getIntBanco(cursor, DebitosCobrados.INDICADORUSO, indicadorUso));
				debitoCobrado.setValor(Util.getDoubleBanco(cursor, DebitosCobrados.VALOR, valor));
				debitoCobrado.setUltimaAlteracao(cursor.getString(ultimaAlteracao));
				
				retorno.add(debitoCobrado);
				
			} while (cursor.moveToNext());
		}		
		return retorno;
	}
}
