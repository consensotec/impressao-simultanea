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
 * [] Classe Básica - Faturamento Conta Faixa
 * 
 * @author Fernanda Almeida
 * @since 08/03/2012
 */
public class ContaCategoriaConsumoFaixa extends ObjetoBasico implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private ContaCategoria contaCategoria;
	private Integer numConsumo;
	private BigDecimal valorFaturado;
	private Integer numConsumoInicial;
	private Integer numConsumoFinal;
	private BigDecimal valorTarifa;
	private Date ultimaAlteracao;
	
	/* Construtor Minimo*/
	public ContaCategoriaConsumoFaixa(Integer numConsumo,
			BigDecimal valorFaturado, Integer numConsumoInicial,
			Integer numConsumoFinal, BigDecimal valorTarifa) {
		super();
		this.numConsumo = numConsumo;
		this.valorFaturado = valorFaturado;
		this.numConsumoInicial = numConsumoInicial;
		this.numConsumoFinal = numConsumoFinal;
		this.valorTarifa = valorTarifa;
	}
	/* Construtor Vazio*/
	public ContaCategoriaConsumoFaixa() {}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setIdString(String id) {
		this.id = Util.verificarNuloInt(id);
	}		
	public ContaCategoria getContaCategoria() {
		return contaCategoria;
	}
	public void setContaCategoria(ContaCategoria contaCategoria) {
		this.contaCategoria = contaCategoria;
	}
	public Integer getNumConsumo() {
		return numConsumo;
	}
	public void setNumConsumo(Integer numConsumo) {
		this.numConsumo = numConsumo;
	}
	public BigDecimal getValorFaturado() {
		return valorFaturado;
	}
	public void setValorFaturado(BigDecimal valorFaturado) {
		this.valorFaturado = valorFaturado;
	}
	public Integer getNumConsumoInicial() {
		return numConsumoInicial;
	}
	public void setNumConsumoInicial(Integer numConsumoInicial) {
		this.numConsumoInicial = numConsumoInicial;
	}
	public Integer getNumConsumoFinal() {
		return numConsumoFinal;
	}
	public void setNumConsumoFinal(Integer numConsumoFinal) {
		this.numConsumoFinal = numConsumoFinal;
	}
	public BigDecimal getValorTarifa() {
		return valorTarifa;
	}
	public void setValorTarifa(BigDecimal valorTarifa) {
		this.valorTarifa = valorTarifa;
	}
	public Date getUltimaAlteracao() {
		return ultimaAlteracao;
	}
	public void setUltimaAlteracao(String ultimaAlteracao) {
		this.ultimaAlteracao = Util.getData(ultimaAlteracao);
	}
	
	private static String[] colunas = new String[] { ContasCategoriasConsumosFaixas.ID, ContasCategoriasConsumosFaixas.CONTACATEGORIA,ContasCategoriasConsumosFaixas.NUMCONSUMO, 
	      ContasCategoriasConsumosFaixas.VALORFATURADO,ContasCategoriasConsumosFaixas.NUMCONSUMOINICIAL,
	      ContasCategoriasConsumosFaixas.NUMCONSUMOFINAL,ContasCategoriasConsumosFaixas.VALORTARIFA,ContasCategoriasConsumosFaixas.ULTIMAALTERACAO};
	
	public String[] getColunas(){
		return colunas;
	}	
	
	public static final class ContasCategoriasConsumosFaixas implements BaseColumns {
		public static final String ID = "CCCF_ID";
		public static final String CONTACATEGORIA = "CTCG_ID";
		public static final String NUMCONSUMO = "CCCF_NNCONSUMO";   
		public static final String VALORFATURADO = "CCCF_VLFATURADO";
		public static final String NUMCONSUMOINICIAL = "CCCF_NNCONSUMOINICIAL";
		public static final String NUMCONSUMOFINAL = "CCCF_NNCONSUMOFINAL";  
		public static final String VALORTARIFA = "CCCF_VLTARIFA";  
		public static final String ULTIMAALTERACAO = "CCCF_TMULTIMAALTERACAO";
	}
	
	public String getNomeTabela(){
		return "conta_catg_cons_fx";
	}
	
	public final class ContasCategoriasConsumosFaixasTipos {
		public final String ID = " INTEGER PRIMARY KEY AUTOINCREMENT ";		
		public final String CONTACATEGORIA = " CONSTRAINT [FK1_CONTA_CATG_CONS_FX] REFERENCES [conta_categoria]([CTCG_ID]) ON DELETE RESTRICT ON UPDATE RESTRICT ";
		public final String NUMCONSUMO = " INTEGER NOT NULL ";  		
		public final String VALORFATURADO = " NUMERIC(13,2) NOT NULL ";		
		public final String NUMCONSUMOINICIAL = " INTEGER NOT NULL ";
		public final String NUMCONSUMOFINAL = " INTEGER NOT NULL ";  
		public final String VALORTARIFA = " NUMERIC(13,2) NOT NULL ";		
		public final String ULTIMAALTERACAO = " TIMESTAMP NOT NULL ";
			 
		private String[] tipos = new String[] {
				ID, CONTACATEGORIA, NUMCONSUMO, VALORFATURADO,
				NUMCONSUMOINICIAL, NUMCONSUMOFINAL, VALORTARIFA, ULTIMAALTERACAO};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
	
	public ContentValues preencherValues() {
		ContentValues values = new ContentValues();
		values.put(ContasCategoriasConsumosFaixas.CONTACATEGORIA, getContaCategoria().getId());
		values.put(ContasCategoriasConsumosFaixas.NUMCONSUMO, getNumConsumo());
			values.put(ContasCategoriasConsumosFaixas.NUMCONSUMOFINAL, getNumConsumoFinal());
			values.put(ContasCategoriasConsumosFaixas.NUMCONSUMOINICIAL, getNumConsumoInicial());
		if(getValorFaturado() != null){
			values.put(ContasCategoriasConsumosFaixas.VALORFATURADO, getValorFaturado().toString());
		}if(getValorTarifa() != null){
			values.put(ContasCategoriasConsumosFaixas.VALORTARIFA, getValorTarifa().toString());
		}
		String dataStr = Util.convertDateToDateStr(Util.getCurrentDateTime());
		values.put(ContasCategoriasConsumosFaixas.ULTIMAALTERACAO, dataStr);
		
		return values;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ContaCategoriaConsumoFaixa> preencherObjetos(Cursor cursor) {		
		ArrayList<ContaCategoriaConsumoFaixa> retorno = null;
		
		if (cursor.moveToFirst()) {
			
			int codigo = cursor.getColumnIndex(ContasCategoriasConsumosFaixas.ID);
			int contaCateg = cursor.getColumnIndex(ContasCategoriasConsumosFaixas.CONTACATEGORIA);
			int numConsumo = cursor.getColumnIndex(ContasCategoriasConsumosFaixas.NUMCONSUMO);
			int valorFaturado = cursor.getColumnIndex(ContasCategoriasConsumosFaixas.VALORFATURADO);
			int numConsumoFinal = cursor.getColumnIndex(ContasCategoriasConsumosFaixas.NUMCONSUMOFINAL);
			int numConsumoInicial = cursor.getColumnIndex(ContasCategoriasConsumosFaixas.NUMCONSUMOINICIAL);
			int valorTarifa = cursor.getColumnIndex(ContasCategoriasConsumosFaixas.VALORTARIFA);
			int ultimaAlteracao = cursor.getColumnIndex(ContasCategoriasConsumosFaixas.ULTIMAALTERACAO);
			
			retorno = new ArrayList<ContaCategoriaConsumoFaixa>();			

			do {	
				ContaCategoriaConsumoFaixa contaCategoriaConsumoFaixa = new ContaCategoriaConsumoFaixa();
			
				if(Util.getIntBanco(cursor, ContasCategoriasConsumosFaixas.CONTACATEGORIA, contaCateg)!=null){
					ContaCategoria objContaCategoria = new ContaCategoria(cursor.getInt(contaCateg));
					contaCategoriaConsumoFaixa.setContaCategoria(objContaCategoria);
				}
				contaCategoriaConsumoFaixa.setId(Util.getIntBanco(cursor, ContasCategoriasConsumosFaixas.ID, codigo));
				contaCategoriaConsumoFaixa.setNumConsumo(Util.getIntBanco(cursor, ContasCategoriasConsumosFaixas.NUMCONSUMO, numConsumo));
				contaCategoriaConsumoFaixa.setValorFaturado(Util.getDoubleBanco(cursor, ContasCategoriasConsumosFaixas.VALORFATURADO, valorFaturado));
				contaCategoriaConsumoFaixa.setNumConsumoFinal(Util.getIntBanco(cursor, ContasCategoriasConsumosFaixas.NUMCONSUMOFINAL, numConsumoFinal));
				contaCategoriaConsumoFaixa.setNumConsumoInicial(Util.getIntBanco(cursor, ContasCategoriasConsumosFaixas.NUMCONSUMOINICIAL, numConsumoInicial));
				contaCategoriaConsumoFaixa.setValorTarifa(Util.getDoubleBanco(cursor, ContasCategoriasConsumosFaixas.VALORTARIFA, valorTarifa));
				contaCategoriaConsumoFaixa.setUltimaAlteracao(cursor.getString(ultimaAlteracao));
			
				retorno.add(contaCategoriaConsumoFaixa);
				
			} while (cursor.moveToNext());
		}		
		return retorno;
	}
}