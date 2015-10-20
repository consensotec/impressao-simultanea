
package com.br.ipad.isc.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import com.br.ipad.isc.util.Util;

/**
 * [] Classe Básica - Consumo Anormalidade
 * 
 * @author Fernanda Almeida
 * @since 08/03/2012
 */
public class ConsumoAnormalidade extends ObjetoBasico implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public ConsumoAnormalidade(ArrayList<String> obj){
		insertFromFile(obj);
	}
	
	//Construtor Vazio
	public ConsumoAnormalidade(){}
	
	public final static int CONSUMO_ANORM_LEITURA = 17;
	public final static int CONSUMO_FORA_FAIXA = 13;
	
	private Integer id;
	private String mensagemConta;
	private String descricao;
	private Date ultimaAlteracao;
	private Integer indicadorRegraImovelCondominio;
	private Integer indicadorFotoAbrigatoria;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setIdString(String id) {
		this.id = Util.verificarNuloInt(id);
	}
	
	public String getMensagemConta() {
		return mensagemConta;
	}
	public void setMensagemConta(String mensagemConta) {
		this.mensagemConta = mensagemConta;
	}
	public Date getUltimaAlteracao() {
		return ultimaAlteracao;
	}
	public void setUltimaAlteracao(String ultimaAlteracao) {
		this.ultimaAlteracao = Util.getData(ultimaAlteracao);
	}
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public ConsumoAnormalidade(int idConsumoAnormalidade){
		this.id = idConsumoAnormalidade;
	}
	
	public Integer getIndicadorRegraImovelCondominio() {
		return indicadorRegraImovelCondominio;
	}

	public void setIndicadorRegraImovelCondominio(Integer indicadorRegraImovelCondominio) {
		this.indicadorRegraImovelCondominio = indicadorRegraImovelCondominio;
	}

	public Integer getIndicadorFotoAbrigatoria() {
		return indicadorFotoAbrigatoria;
	}

	public void setIndicadorFotoAbrigatoria(Integer indicadorFotoAbrigatoria) {
		this.indicadorFotoAbrigatoria = indicadorFotoAbrigatoria;
	}

	private static String[] colunas = new String[] { ConsumoAnormalidades.ID, ConsumoAnormalidades.MENSAGEMCONTA,
		ConsumoAnormalidades.DESCRICAO ,ConsumoAnormalidades.ULTIMAALTERACAO, ConsumoAnormalidades.IC_REGRA_IMOVEL_CONDOMINIO,
		ConsumoAnormalidades.IC_FOTO_OBRIGATORIA};
	
	public String[] getColunas(){
		return colunas;
	}	
	
	public static final class ConsumoAnormalidades implements BaseColumns {
		public static final String ID = "CSAN_ID";
		public static final String MENSAGEMCONTA = "CSAN_DSMENSAGEMCONTA";
		public static final String DESCRICAO = "CSAN_DSCONSUMOANORMALIDADE";
		public static final String ULTIMAALTERACAO = "CSAN_TMULTIMAALTERACAO";
		public static final String IC_REGRA_IMOVEL_CONDOMINIO = "CSAN_ICREGRAIMOVCONDOMINIO";
		public static final String IC_FOTO_OBRIGATORIA = "CSAN_ICFOTOOBRIGATORIA";
	}
	
	private void insertFromFile(ArrayList<String> obj){
		setIdString(obj.get(1));	
		setMensagemConta(obj.get(2));
		setDescricao(obj.get(3));
		String date = Util.convertDateToDateStr(Util.getCurrentDateTime());	
		setUltimaAlteracao(date);
		setIndicadorRegraImovelCondominio(Integer.parseInt(obj.get(4)));
		
		if(obj.size()==6)
		{
			setIndicadorFotoAbrigatoria(Integer.parseInt(obj.get(5)));
		}
	}
		
	public String getNomeTabela(){
		return "consumo_anormalidade";
	}
	
	public final class ConsumoAnormalidadesTipos {
		public final String ID = " INTEGER PRIMARY KEY AUTOINCREMENT ";
		public final String MENSAGEMCONTA = " VARCHAR(120) NULL";
		public final String DESCRICAO = " VARCHAR(25) NOT NULL";
		public final String ULTIMAALTERACAO = " TIMESTAMP NOT NULL";
		public final String IC_REGRA_IMOVEL_CONDOMINIO = " INTEGER NOT NULL";
		public final String IC_FOTO_OBRIGATORIA = " INTEGER NULL";
		
		private String[] tipos = new String[] {ID, MENSAGEMCONTA,
			DESCRICAO ,ULTIMAALTERACAO, IC_REGRA_IMOVEL_CONDOMINIO,
			IC_FOTO_OBRIGATORIA};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
	
	public ContentValues preencherValues() {
		ContentValues values = new ContentValues();
		
		values.put(ConsumoAnormalidades.ID, getId());
		values.put(ConsumoAnormalidades.MENSAGEMCONTA, getMensagemConta());
		values.put(ConsumoAnormalidades.DESCRICAO, getDescricao());
		String dataStr = Util.convertDateToDateStr(Util.getCurrentDateTime());
		values.put(ConsumoAnormalidades.ULTIMAALTERACAO, dataStr);
		values.put(ConsumoAnormalidades.IC_REGRA_IMOVEL_CONDOMINIO, getIndicadorRegraImovelCondominio());
		values.put(ConsumoAnormalidades.IC_FOTO_OBRIGATORIA, getIndicadorFotoAbrigatoria());
		return values;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ConsumoAnormalidade> preencherObjetos(Cursor cursor) {		
			
		int codigo = cursor.getColumnIndex(ConsumoAnormalidades.ID);
		int mensagemConta = cursor.getColumnIndex(ConsumoAnormalidades.MENSAGEMCONTA);
		int descricao = cursor.getColumnIndex(ConsumoAnormalidades.DESCRICAO);
		int ultimaAlteracao = cursor.getColumnIndex(ConsumoAnormalidades.ULTIMAALTERACAO);
		int icRegraImovelCondominio = cursor.getColumnIndex(ConsumoAnormalidades.IC_REGRA_IMOVEL_CONDOMINIO);
		int icFotoObrigatoria  = cursor.getColumnIndex(ConsumoAnormalidades.IC_FOTO_OBRIGATORIA);
		
		ArrayList<ConsumoAnormalidade> consumosAnormalidades = new ArrayList<ConsumoAnormalidade>();
		do {	
			ConsumoAnormalidade consumoAnormalidade = new ConsumoAnormalidade();
			consumoAnormalidade.setId(cursor.getInt(codigo));
			consumoAnormalidade.setMensagemConta(cursor.getString(mensagemConta));
			consumoAnormalidade.setDescricao(cursor.getString(descricao));
			consumoAnormalidade.setUltimaAlteracao(cursor.getString(ultimaAlteracao));
			consumoAnormalidade.setIndicadorRegraImovelCondominio(cursor.getInt(icRegraImovelCondominio));
			consumoAnormalidade.setIndicadorFotoAbrigatoria(cursor.getInt(icFotoObrigatoria));
			
			consumosAnormalidades.add(consumoAnormalidade);
			
		} while (cursor.moveToNext());
		
		return consumosAnormalidades;
	}

}
