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
 * [] Classe Básica - Consumo Tarifa Categoria
 * 
 * @author Fernanda Almeida
 * @since 08/03/2012
 */
public class ConsumoTarifaCategoria extends ObjetoBasico  implements Serializable {

	//Construtor default
	public ConsumoTarifaCategoria(){}
	
	public ConsumoTarifaCategoria(ArrayList<String> obj){
		insertFromFile(obj);
	}
		
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer consumoTarifa;
	private Date dataVigencia;
	private Integer idCategoria;
	private Integer idSubcategoria;
	private Integer consumoMinimoSubcategoria;
	private BigDecimal valorTarifaMinimaCategoria;
	private Date ultimaAlteracao;
	
	public ConsumoTarifaCategoria(Integer id) {
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
	
	public Date getDataVigencia() {
		return dataVigencia;
	}
	public void setDataVigencia(Date dataVigencia) {
		this.dataVigencia = dataVigencia;
	}
	public Integer getIdCategoria() {
		return idCategoria;
	}
	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}
	public Integer getIdSubcategoria() {
		return idSubcategoria;
	}
	public void setIdSubcategoria(Integer idSubcategoria) {
		this.idSubcategoria = idSubcategoria;
	}
	public Integer getConsumoTarifa() {
		return consumoTarifa;
	}
	public void setConsumoTarifa(Integer consumoTarifa) {
		this.consumoTarifa = consumoTarifa;
	}
	public Integer getConsumoMinimoSubcategoria() {
		return consumoMinimoSubcategoria;
	}
	public void setConsumoMinimoSubcategoria(Integer consumoMinimoSubcategoria) {
		this.consumoMinimoSubcategoria = consumoMinimoSubcategoria;
	}
	public BigDecimal getValorTarifaMinimaCategoria() {
		return valorTarifaMinimaCategoria;
	}
	public void setValorTarifaMinimaCategoria(BigDecimal valorTarifaMinimaCategoria) {
		this.valorTarifaMinimaCategoria = valorTarifaMinimaCategoria;
	}
	public Date getUltimaAlteracao() {
		return ultimaAlteracao;
	}
	public void setUltimaAlteracao(String ultimaAlteracao) {
		this.ultimaAlteracao = Util.getData(ultimaAlteracao);
	}
	
	private static String[] colunas = new String[] { ConsumosTarifasCategorias.ID,ConsumosTarifasCategorias.CONSUMOTARIFA,
	                                                 ConsumosTarifasCategorias.DATAVIGENCIA, ConsumosTarifasCategorias.IDCATEGORIA, ConsumosTarifasCategorias.IDSUBCATEGORIA,
	                                        ConsumosTarifasCategorias.CONSUMOMINIMOSUBCATEGORIA,ConsumosTarifasCategorias.VALORTARIFAMINIMACATEGORIA, ConsumosTarifasCategorias.ULTIMAALTERACAO};
	
	public String[] getColunas(){
		return colunas;
	}	
	
	public static final class ConsumosTarifasCategorias implements BaseColumns {
		public static final String ID = "CSTC_ID";
		public static final String CONSUMOTARIFA = "CSTF_ID";
		public static final String DATAVIGENCIA = "CSTF_DTVIGENCIA";
		public static final String IDCATEGORIA = "CATG_ID";
		public static final String IDSUBCATEGORIA = "SCAT_ID";
		public static final String CONSUMOMINIMOSUBCATEGORIA = "CSTF_NNCONSUMOMINIMO";
		public static final String VALORTARIFAMINIMACATEGORIA = "CSTF_VLTARIFAMINIMA";
		public static final String ULTIMAALTERACAO = "CSTF_TMULTIMAALTERACAO";
	}
	
	private void insertFromFile(ArrayList<String> obj){
		
		if(obj.get(1).length() != 0){
			setConsumoTarifa(Integer.parseInt(obj.get(1)));
		}
		if(obj.get(2).length() != 0){
			Date dataFormatada = Util.convertDateStrToDate1(obj.get(2));
			setDataVigencia(dataFormatada);	
		}
		if(obj.get(3).length() != 0){
			setIdCategoria(Integer.parseInt(obj.get(3)));	
		}
		if(obj.get(4).length() != 0){
			setIdSubcategoria(Integer.parseInt(obj.get(4)));	
		}
		if(obj.get(5).length() != 0){
			setConsumoMinimoSubcategoria(Integer.parseInt(obj.get(5)));
		}
		if(obj.get(6).length() != 0){
			BigDecimal tarifaMinima = new BigDecimal(obj.get(6));
			setValorTarifaMinimaCategoria(tarifaMinima);
		}
		String date = Util.convertDateToDateStr(Util.getCurrentDateTime());	
		setUltimaAlteracao(date);
	}
	
	public String getNomeTabela(){
		return "consumo_tarifa_categoria";
	}
	
	public final class ConsumosTarifasCategoriasTipos {
		public final String ID = " INTEGER PRIMARY KEY AUTOINCREMENT ";
		public final String CONSUMOTARIFA = " INTEGER NOT NULL ";
		public final String DATAVIGENCIA = " DATE NOT NULL ";
		public final String IDCATEGORIA = " INTEGER NOT NULL ";
		public final String IDSUBCATEGORIA = " INTEGER NULL ";
		public final String CONSUMOMINIMOSUBCATEGORIA = " INTEGER NOT NULL ";
		public final String VALORTARIFAMINIMACATEGORIA = " NUMERIC(13,2) NOT NULL ";		
		public final String ULTIMAALTERACAO = " TIMESTAMP NOT NULL ";
		 
		private String[] tipos = new String[] {
				ID, CONSUMOTARIFA, DATAVIGENCIA, IDCATEGORIA, IDSUBCATEGORIA,
				CONSUMOMINIMOSUBCATEGORIA, VALORTARIFAMINIMACATEGORIA, ULTIMAALTERACAO};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
	
	public ContentValues preencherValues() {
		ContentValues values = new ContentValues();
		
		values.put(ConsumosTarifasCategorias.CONSUMOTARIFA, getConsumoTarifa());
		values.put(ConsumosTarifasCategorias.CONSUMOMINIMOSUBCATEGORIA, getConsumoMinimoSubcategoria());
		if(getDataVigencia() != null){
			String dataStr = Util.convertDateToDateStr(getDataVigencia());		
			values.put(ConsumosTarifasCategorias.DATAVIGENCIA, dataStr);	
		}
		values.put(ConsumosTarifasCategorias.IDCATEGORIA, getIdCategoria());
		values.put(ConsumosTarifasCategorias.IDSUBCATEGORIA, getIdSubcategoria());
		if(getValorTarifaMinimaCategoria() != null){
			values.put(ConsumosTarifasCategorias.VALORTARIFAMINIMACATEGORIA, getValorTarifaMinimaCategoria().toString());
		}
		String dataStr = Util.convertDateToDateStr(Util.getCurrentDateTime());
		values.put(ConsumosTarifasCategorias.ULTIMAALTERACAO, dataStr);
		return values;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ConsumoTarifaCategoria> preencherObjetos(Cursor cursor) {		
		ArrayList<ConsumoTarifaCategoria> consumosAnormalidades = null;
		
		if (cursor.moveToFirst()) {
			int codigo = cursor.getColumnIndex(ConsumosTarifasCategorias.ID);
			int consumoTarifa = cursor.getColumnIndex(ConsumosTarifasCategorias.CONSUMOTARIFA);
			int consumoMinCategoria = cursor.getColumnIndex(ConsumosTarifasCategorias.CONSUMOMINIMOSUBCATEGORIA);
			int dataVigencia = cursor.getColumnIndex(ConsumosTarifasCategorias.DATAVIGENCIA);
			int idSubcategoria = cursor.getColumnIndex(ConsumosTarifasCategorias.IDSUBCATEGORIA);
			int idCategoria = cursor.getColumnIndex(ConsumosTarifasCategorias.IDCATEGORIA);
			int valorTarifaMinCategoria = cursor.getColumnIndex(ConsumosTarifasCategorias.VALORTARIFAMINIMACATEGORIA);
			int ultimaAlteracao = cursor.getColumnIndex(ConsumosTarifasCategorias.ULTIMAALTERACAO);
			
			consumosAnormalidades = new ArrayList<ConsumoTarifaCategoria>();
			ConsumoTarifaCategoria consumoTarifaCategoria = null;
			do {	
				consumoTarifaCategoria = new ConsumoTarifaCategoria();
				
				consumoTarifaCategoria.setId(Util.getIntBanco(cursor, ConsumosTarifasCategorias.ID, codigo));
				consumoTarifaCategoria.setConsumoTarifa(Util.getIntBanco(cursor, ConsumosTarifasCategorias.CONSUMOTARIFA, consumoTarifa));
				consumoTarifaCategoria.setConsumoMinimoSubcategoria(Util.getIntBanco(cursor, ConsumosTarifasCategorias.CONSUMOMINIMOSUBCATEGORIA, consumoMinCategoria));
				
				consumoTarifaCategoria.setDataVigencia(Util.convertStrToDataBusca(cursor.getString(dataVigencia)));
				
				consumoTarifaCategoria.setIdCategoria(Util.getIntBanco(cursor, ConsumosTarifasCategorias.IDCATEGORIA, idCategoria));
				consumoTarifaCategoria.setIdSubcategoria(Util.getIntBanco(cursor, ConsumosTarifasCategorias.IDSUBCATEGORIA, idSubcategoria));
				consumoTarifaCategoria.setValorTarifaMinimaCategoria(Util.getDoubleBanco(cursor, ConsumosTarifasCategorias.VALORTARIFAMINIMACATEGORIA, valorTarifaMinCategoria));
				consumoTarifaCategoria.setUltimaAlteracao(cursor.getString(ultimaAlteracao));
					
				consumosAnormalidades.add(consumoTarifaCategoria);
				
			} while (cursor.moveToNext());
		}
		
		return consumosAnormalidades;
	}

}