package com.br.ipad.isc.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import com.br.ipad.isc.fachada.Fachada;
import com.br.ipad.isc.util.Util;

/**
 * [] Classe Básica - Consumo Tarifa Categoria
 * 
 * @author Fernanda Almeida
 * @since 08/03/2012
 */
public class ConsumoTarifaFaixa extends ObjetoBasico implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//Construtor default
	public ConsumoTarifaFaixa(){}
	
	public ConsumoTarifaFaixa(ArrayList<String> obj, long idConsumoCategoria){
		insertFromFile(obj, idConsumoCategoria);
	}
		
	private Integer id;
	private ConsumoTarifaCategoria consumoTarifaCategoria;
	private Date dataVigencia;
	private Integer consumoFaixaInicio;
	private Integer consumoFaixaFim;
	private BigDecimal valorConsumoTarifa;
	private Date ultimaAlteracao;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Date getDataVigencia() {
		return dataVigencia;
	}
	public void setDataVigencia(Date dataVigencia) {
		this.dataVigencia = dataVigencia;
	}

	public void setIdString(String id) {
		this.id = Util.verificarNuloInt(id);
	}
		
	public ConsumoTarifaCategoria getConsumoTarifaCategoria() {
		return consumoTarifaCategoria;
	}
	public void setConsumoTarifaCategoria(ConsumoTarifaCategoria consumoTarifaCategoria) {
		this.consumoTarifaCategoria = consumoTarifaCategoria;
	}
	public Integer getConsumoFaixaFim() {
		return consumoFaixaFim;
	}
	public void setConsumoFaixaFim(Integer consumoFaixaFim) {
		this.consumoFaixaFim = consumoFaixaFim;
	}
	public Integer getConsumoFaixaInicio() {
		return consumoFaixaInicio;
	}
	public void setConsumoFaixaInicio(Integer consumoFaixaInicio) {
		this.consumoFaixaInicio = consumoFaixaInicio;
	}
	public BigDecimal getValorConsumoTarifa() {
		return valorConsumoTarifa;
	}
	public void setValorConsumoTarifa(BigDecimal valorConsumoTarifa) {
		this.valorConsumoTarifa = valorConsumoTarifa;
	}
	public Date getUltimaAlteracao() {
		return ultimaAlteracao;
	}
	public void setUltimaAlteracao(String ultimaAlteracao) {
		this.ultimaAlteracao = Util.getData(ultimaAlteracao);
	}
	
	private static String[] colunas = new String[] { ConsumosTarifasFaixas.ID, ConsumosTarifasFaixas.CONSUMOTARIFACATEGORIA,ConsumosTarifasFaixas.DATAVIGENCIA, 
	      ConsumosTarifasFaixas.CONSUMOFAIXAINICIO,ConsumosTarifasFaixas.CONSUMOFAIXAFIM,ConsumosTarifasFaixas.VALORCONSUMOTARIFA, ConsumosTarifasFaixas.ULTIMAALTERACAO};
	
	public String[] getColunas(){
		return colunas;
	}	
	
	public static final class ConsumosTarifasFaixas implements BaseColumns {
		public static final String ID = "CTFX_ID";
		public static final String CONSUMOTARIFACATEGORIA = "CSTC_ID";
		public static final String DATAVIGENCIA = "CTFX_DTVIGENCIA";
		public static final String CONSUMOFAIXAINICIO = "CTFX_NNCOSUMOFAIXAINICIO";
		public static final String CONSUMOFAIXAFIM = "CTFX_NNCONSUMOFAIXAFIM";
		public static final String VALORCONSUMOTARIFA = "CTFX_VLCONSUMOTARIFA";   
		public static final String ULTIMAALTERACAO = "CTFX_TMULTIMAALTERACAO";
	}
	
	private void insertFromFile(ArrayList<String> obj, long idConsumoCategoria){
		
		ConsumoTarifaCategoria consumoCategoria = new ConsumoTarifaCategoria();
		consumoCategoria.setId((int)idConsumoCategoria);
		setConsumoTarifaCategoria(consumoCategoria);
				
		if(obj.get(2).length() != 0){
			Date dataFormatada = Util.convertDateStrToDate1(obj.get(2));
			setDataVigencia(dataFormatada);	
		}
		if(obj.get(5).length() != 0){
			setConsumoFaixaInicio(Integer.parseInt(obj.get(5)));
		}
		if(obj.get(6).length() != 0){
			setConsumoFaixaFim(Integer.parseInt(obj.get(6)));
		}
		if(obj.get(7).length() != 0){
			BigDecimal valorConsTarifa = new BigDecimal(obj.get(7));
			setValorConsumoTarifa(valorConsTarifa);
		}
		String date = Util.convertDateToDateStr(Util.getCurrentDateTime());	
		setUltimaAlteracao(date);			
	}

	public String getNomeTabela(){
		return "consumo_tarifa_faixa";
	}
	
	public final class ConsumosTarifasFaixasTipos {
		public final String ID = " INTEGER PRIMARY KEY AUTOINCREMENT ";
		public final String CONSUMOTARIFACATEGORIA = " INTEGER CONSTRAINT [FK1_CONSUMO_TARIFA_FAIXA] REFERENCES [consumo_tarifa_categoria]([CSTC_ID]) ON DELETE RESTRICT ON UPDATE RESTRICT ";
		public final String DATAVIGENCIA = " DATE NOT NULL ";
		public final String CONSUMOFAIXAINICIO = " INTEGER NOT NULL ";
		public final String CONSUMOFAIXAFIM = " INTEGER NOT NULL ";
		public final String VALORCONSUMOTARIFA = " NUMERIC(13,2) NOT NULL";   
		public final String ULTIMAALTERACAO = " TIMESTAMP NOT NULL ";
			 		
		private String[] tipos = new String[] {
				ID, CONSUMOTARIFACATEGORIA, DATAVIGENCIA, CONSUMOFAIXAINICIO,
				CONSUMOFAIXAFIM, VALORCONSUMOTARIFA, ULTIMAALTERACAO};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
	
	public ContentValues preencherValues() {
		ContentValues values = new ContentValues();
		values.put(ConsumosTarifasFaixas.CONSUMOFAIXAFIM, getConsumoFaixaFim());
		if(getDataVigencia() != null){
			String dataStr = Util.convertDateToDateStr(getDataVigencia());		
			values.put(ConsumosTarifasFaixas.DATAVIGENCIA, dataStr);	
		}
				
		if(getConsumoTarifaCategoria() != null){
			values.put(ConsumosTarifasFaixas.CONSUMOTARIFACATEGORIA, getConsumoTarifaCategoria().getId());
		}
		
		values.put(ConsumosTarifasFaixas.CONSUMOFAIXAINICIO, getConsumoFaixaInicio());
		values.put(ConsumosTarifasFaixas.CONSUMOFAIXAFIM, getConsumoFaixaFim());
		if(getConsumoTarifaCategoria() != null){
			values.put(ConsumosTarifasFaixas.CONSUMOTARIFACATEGORIA, getConsumoTarifaCategoria().getId());
		}
		if(getValorConsumoTarifa()!= null){
			values.put(ConsumosTarifasFaixas.VALORCONSUMOTARIFA, getValorConsumoTarifa().toString());
		}
		String dataStr = Util.convertDateToDateStr(Util.getCurrentDateTime());
		values.put(ConsumosTarifasFaixas.ULTIMAALTERACAO, dataStr);
		
		return values;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ConsumoTarifaFaixa> preencherObjetos(Cursor cursor) {		
		ArrayList<ConsumoTarifaFaixa> retorno = null;
		
		int codigo = cursor.getColumnIndex(ConsumosTarifasFaixas.ID);
		int consumoFaixaFim = cursor.getColumnIndex(ConsumosTarifasFaixas.CONSUMOFAIXAFIM);
		int consumoFaixaInicio = cursor.getColumnIndex(ConsumosTarifasFaixas.CONSUMOFAIXAINICIO);
		int dataVigencia = cursor.getColumnIndex(ConsumosTarifasFaixas.DATAVIGENCIA);
		int consumoTarifaCategoria = cursor.getColumnIndex(ConsumosTarifasFaixas.CONSUMOTARIFACATEGORIA);
		int valorConsumoTarifa= cursor.getColumnIndex(ConsumosTarifasFaixas.VALORCONSUMOTARIFA);
		int ultimaAlteracao = cursor.getColumnIndex(ConsumosTarifasFaixas.ULTIMAALTERACAO);
		
		if (cursor.moveToFirst()) {			
			retorno = new ArrayList<ConsumoTarifaFaixa>();			
			do {	
				ConsumoTarifaFaixa consumoTarifaFaixa = new ConsumoTarifaFaixa();
				consumoTarifaFaixa.setId(Util.getIntBanco(cursor, ConsumosTarifasFaixas.ID, codigo));
				consumoTarifaFaixa.setConsumoFaixaFim(Util.getIntBanco(cursor, ConsumosTarifasFaixas.CONSUMOFAIXAFIM, consumoFaixaFim));
				consumoTarifaFaixa.setConsumoFaixaInicio(Util.getIntBanco(cursor, ConsumosTarifasFaixas.CONSUMOFAIXAINICIO, consumoFaixaInicio));
				
				if(Util.getIntBanco(cursor, ConsumosTarifasFaixas.CONSUMOTARIFACATEGORIA, consumoTarifaCategoria) != null){
					
					ConsumoTarifaCategoria objConsumoTarifaCategoria = Fachada.getInstance().
							pesquisarPorId(cursor.getInt(consumoTarifaCategoria), new ConsumoTarifaCategoria());
					consumoTarifaFaixa.setConsumoTarifaCategoria(objConsumoTarifaCategoria);			
				}
				
				consumoTarifaFaixa.setDataVigencia(Util.convertStrToDataBusca(cursor.getString(dataVigencia)));
									
				consumoTarifaFaixa.setValorConsumoTarifa(Util.getDoubleBanco(cursor, ConsumosTarifasFaixas.CONSUMOFAIXAFIM, valorConsumoTarifa));
				consumoTarifaFaixa.setUltimaAlteracao(cursor.getString(ultimaAlteracao));
			
				retorno.add(consumoTarifaFaixa);
				
			} while (cursor.moveToNext());
		}		
		return retorno;
	}
}
