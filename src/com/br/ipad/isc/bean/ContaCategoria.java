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
 * [] Classe Básica - Conta Categoria
 * 
 * @author Fernanda Almeida
 * @since 08/03/2012
 */
public class ContaCategoria extends ObjetoBasico implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private CategoriaSubcategoria categoriaSubcategoria;
	private Integer tipoLigacao;
	private BigDecimal valorFaturado;
	private Integer numConsumo;
	private BigDecimal valorTarifaMinima;
	private Integer numConsumoMinimo;
	private Date ultimaAlteracao;
	//campos auxiliares criados para o calculo proporcional
	double[] vlFaturadoAntFaixa = {0,0,0,0,0};
	double[] vlTarifaAntFaixa= {0,0,0,0,0};

	
	public ContaCategoria() {
		super();
	}
	
	public ContaCategoria(Integer id) {
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
		
	public CategoriaSubcategoria getCategoriaSubcategoria() {
		return categoriaSubcategoria;
	}
	public void setCategoriaSubcategoria(CategoriaSubcategoria categoriaSubcategoria) {
		this.categoriaSubcategoria = categoriaSubcategoria;
	}
	public Integer getTipoLigacao() {
		return tipoLigacao;
	}
	public void setTipoLigacao(Integer tipoLigacao) {
		this.tipoLigacao = tipoLigacao;
	}
	public BigDecimal getValorFaturado() {
		return valorFaturado;
	}
	public void setValorFaturado(BigDecimal valorFaturado) {
		this.valorFaturado = valorFaturado;
	}
	public Integer getNumConsumo() {
		return numConsumo;
	}
	public void setNumConsumo(Integer numConsumo) {
		this.numConsumo = numConsumo;
	}
	public BigDecimal getValorTarifaMinima() {
		return valorTarifaMinima;
	}
	public void setValorTarifaMinima(BigDecimal valorTarifaMinima) {
		this.valorTarifaMinima = valorTarifaMinima;
	}
	public Integer getNumConsumoMinimo() {
		return numConsumoMinimo;
	}
	public void setNumConsumoMinimo(Integer numConsumoMinimo) {
		this.numConsumoMinimo = numConsumoMinimo;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Date getUltimaAlteracao() {
		return ultimaAlteracao;
	}
	public void setUltimaAlteracao(String ultimaAlteracao) {
		this.ultimaAlteracao = Util.getData(ultimaAlteracao);
	}
	public void setUltimaAlteracao(Date ultimaAlteracao) {
		this.ultimaAlteracao = ultimaAlteracao;
	}
	
	private static String[] colunas = new String[] { ContasCategorias.ID, ContasCategorias.CATEGORIASUBCATEGORIA,ContasCategorias.TIPOLIGACAO, 
	      ContasCategorias.VALORFATURADO,ContasCategorias.NUMCONSUMO, ContasCategorias.VALORTARIFAMINIMA,
	      ContasCategorias.NUMCONSUMOMINIMO, ContasCategorias.ULTIMAALTERACAO};
	
	public String[] getColunas(){
		return colunas;
	}	
	
	public static final class ContasCategorias implements BaseColumns {
		public static final String ID = "CTCG_ID";
		public static final String CATEGORIASUBCATEGORIA = "CASC_ID";
		public static final String TIPOLIGACAO = "LGTI_ID";
		public static final String VALORFATURADO = "CTCG_VLFATURADO";
		public static final String NUMCONSUMO = "CTCG_NNCONSUMO";   
		public static final String VALORTARIFAMINIMA = "CTCG_VLTARIFAMINIMA";
		public static final String NUMCONSUMOMINIMO = "CTCG_NNCONSUMOMIN";  
		public static final String ULTIMAALTERACAO = "CTCG_TMULTIMAALTERACAO";
	}
	
	public String getNomeTabela(){
		return "conta_categoria";
	}
	
	public final class ContasCategoriasTipos {
		public final String ID = " INTEGER PRIMARY KEY AUTOINCREMENT ";
		public final String CATEGORIASUBCATEGORIA = " CONSTRAINT [FK1_CONTA_CATEGORIA] REFERENCES [categoria_subcategoria]([CASC_ID]) ON DELETE RESTRICT ON UPDATE RESTRICT ";
		public final String TIPOLIGACAO = " INTEGER NOT NULL ";
		public final String VALORFATURADO = " NUMERIC(13,2) NOT NULL ";
		public final String NUMCONSUMO = " INTEGER NOT NULL ";   
		public final String VALORTARIFAMINIMA = " NUMERIC(13,2) NOT NULL ";
		public final String NUMCONSUMOMINIMO = " INTEGER NOT NULL ";   
		public final String ULTIMAALTERACAO = " TIMESTAMP NOT NULL ";
			 
		private String[] tipos = new String[] {
				ID, CATEGORIASUBCATEGORIA, TIPOLIGACAO, VALORFATURADO,
				NUMCONSUMO, VALORTARIFAMINIMA, NUMCONSUMOMINIMO, ULTIMAALTERACAO};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
	
	public ContentValues preencherValues() {
		ContentValues values = new ContentValues();
		if (getCategoriaSubcategoria()!=null){
			values.put(ContasCategorias.CATEGORIASUBCATEGORIA, getCategoriaSubcategoria().getId());
		}
		values.put(ContasCategorias.NUMCONSUMO, getNumConsumo());
		values.put(ContasCategorias.NUMCONSUMOMINIMO, getNumConsumoMinimo());
		values.put(ContasCategorias.TIPOLIGACAO, getTipoLigacao());
		if(getValorFaturado() != null){
			values.put(ContasCategorias.VALORFATURADO, getValorFaturado().toString());
		}
		if(getValorTarifaMinima() != null){
			values.put(ContasCategorias.VALORTARIFAMINIMA, getValorTarifaMinima().toString());
		}
		String dataStr = Util.convertDateToDateStr(Util.getCurrentDateTime());
		values.put(ContasCategorias.ULTIMAALTERACAO, dataStr);
		
		return values;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ContaCategoria> preencherObjetos(Cursor cursor) {		
		ArrayList<ContaCategoria> retorno = null;
		
		int codigo = cursor.getColumnIndex(ContasCategorias.ID);
		int categSubcateg = cursor.getColumnIndex(ContasCategorias.CATEGORIASUBCATEGORIA);
		int numConsumo = cursor.getColumnIndex(ContasCategorias.NUMCONSUMO);
		int valorFaturado = cursor.getColumnIndex(ContasCategorias.VALORFATURADO);
		int tipoLigacao = cursor.getColumnIndex(ContasCategorias.TIPOLIGACAO);
		int numConsumoMin = cursor.getColumnIndex(ContasCategorias.NUMCONSUMOMINIMO);
		int valorTarifaMin = cursor.getColumnIndex(ContasCategorias.VALORTARIFAMINIMA);
		int ultimaAlteracao = cursor.getColumnIndex(ContasCategorias.ULTIMAALTERACAO);
		
		if (cursor.moveToFirst()) {			
			retorno = new ArrayList<ContaCategoria>();
			
			do {	
				ContaCategoria contaCategoria = new ContaCategoria();
				if(Util.getIntBanco(cursor, ContasCategorias.CATEGORIASUBCATEGORIA, categSubcateg)!=null){
					CategoriaSubcategoria objCategSubcateg = new CategoriaSubcategoria(cursor.getInt(categSubcateg));
					contaCategoria.setCategoriaSubcategoria(objCategSubcateg);
				}
				contaCategoria.setId(Util.getIntBanco(cursor, ContasCategorias.ID, codigo));
				contaCategoria.setNumConsumo(Util.getIntBanco(cursor, ContasCategorias.NUMCONSUMO, numConsumo));
				contaCategoria.setNumConsumoMinimo(Util.getIntBanco(cursor, ContasCategorias.NUMCONSUMOMINIMO, numConsumoMin));
				contaCategoria.setTipoLigacao(Util.getIntBanco(cursor, ContasCategorias.TIPOLIGACAO, tipoLigacao));
				contaCategoria.setValorFaturado(Util.getDoubleBanco(cursor, ContasCategorias.VALORFATURADO, valorFaturado));
				contaCategoria.setValorTarifaMinima(Util.getDoubleBanco(cursor, ContasCategorias.VALORTARIFAMINIMA, valorTarifaMin));
				contaCategoria.setUltimaAlteracao(cursor.getString(ultimaAlteracao));
			
				retorno.add(contaCategoria);
				
			} while (cursor.moveToNext());
		}		
		return retorno;
	}

	public double[] getVlFaturadoAntFaixa() {
		return vlFaturadoAntFaixa;
	}

	public void setVlFaturadoAntFaixa(double[] vlFaturadoAntFaixa) {
		this.vlFaturadoAntFaixa = vlFaturadoAntFaixa;
	}

	public double[] getVlTarifaAntFaixa() {
		return vlTarifaAntFaixa;
	}

	public void setVlTarifaAntFaixa(double[] vlTarifaAntFaixa) {
		this.vlTarifaAntFaixa = vlTarifaAntFaixa;
	}

}