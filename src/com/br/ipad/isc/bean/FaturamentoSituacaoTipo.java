package com.br.ipad.isc.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import com.br.ipad.isc.util.Util;

/**
 * [] Classe Básica - Sistema Parâmetro
 * 
 * @author Fernanda Almeida
 * @since 08/03/2012
 */
public class FaturamentoSituacaoTipo extends ObjetoBasico implements Serializable {

	//Construtor default
	public FaturamentoSituacaoTipo(){}
	
	public FaturamentoSituacaoTipo(ArrayList<String> obj){
		insertFromFile(obj);
	}
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer indicadorDesconsiderarEstouroAltoConsumo;;
	private Integer idAnormalidadeConsumoSemLeitura;
	private Integer idAnormalidadeConsumoComLeitura;
	private Integer idAnormalidadeLeituraSemLeitura;
	private Integer idAnormalidadeLeituraComLeitura;
	private Integer indcValidaAgua = 0;
	private Integer indcValidaEsgoto = 0;
	private Date ultimaAlteracao;

	public final static int NITRATO = 9;
		
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setIdString(String id) {
		this.id = Util.verificarNuloInt(id);
	}
	
	public Integer getIndicadorDesconsiderarEstouroAltoConsumo() {
		return indicadorDesconsiderarEstouroAltoConsumo;
	}
	public void setIndicadorDesconsiderarEstouroAltoConsumo(Integer indicadorDesconsiderarEstouroAltoConsumo) {
		this.indicadorDesconsiderarEstouroAltoConsumo = indicadorDesconsiderarEstouroAltoConsumo;
	}
	public Integer getIdAnormalidadeConsumoSemLeitura() {
		return idAnormalidadeConsumoSemLeitura;
	}
	public void setIdAnormalidadeConsumoSemLeitura(Integer idAnormalidadeConsumoSemLeitura) {
		this.idAnormalidadeConsumoSemLeitura = idAnormalidadeConsumoSemLeitura;
	}
	public Integer getIdAnormalidadeConsumoComLeitura() {
		return idAnormalidadeConsumoComLeitura;
	}
	public void setIdAnormalidadeConsumoComLeitura(Integer idAnormalidadeConsumoComLeitura) {
		this.idAnormalidadeConsumoComLeitura = idAnormalidadeConsumoComLeitura;
	}
	public Integer getIdAnormalidadeLeituraSemLeitura() {
		return idAnormalidadeLeituraSemLeitura;
	}
	public void setIdAnormalidadeLeituraSemLeitura(Integer idAnormalidadeLeituraSemLeitura) {
		this.idAnormalidadeLeituraSemLeitura = idAnormalidadeLeituraSemLeitura;
	}
	public Integer getIdAnormalidadeLeituraComLeitura() {
		return idAnormalidadeLeituraComLeitura;
	}
	public void setIdAnormalidadeLeituraComLeitura(Integer idAnormalidadeLeituraComLeitura) {
		this.idAnormalidadeLeituraComLeitura = idAnormalidadeLeituraComLeitura;
	}

	public Integer getIndcValidaAgua() {
		return indcValidaAgua;
	}
	public void setIndcValidaAgua(Integer indcValidaAgua) {
		this.indcValidaAgua = indcValidaAgua;
	}
	public Integer getIndcValidaEsgoto() {
		return indcValidaEsgoto;
	}
	public void setIndcValidaEsgoto(Integer indcValidaEsgoto) {
		this.indcValidaEsgoto = indcValidaEsgoto;
	}
	public Date getUltimaAlteracao() {
		return ultimaAlteracao;
	}
	public void setUltimaAlteracao(String ultimaAlteracao) {
		this.ultimaAlteracao = Util.getData(ultimaAlteracao);
	}
	
	private static String[] colunas = new String[] { FaturamentoSituacaoTipos.ID, FaturamentoSituacaoTipos.IDCONSACOBRARSEMLEITURA,  FaturamentoSituacaoTipos.IDCONSACOBRARCOMLEITURA, FaturamentoSituacaoTipos.IDLEITURAANORMALIDADESEMLEITURA,
                                             FaturamentoSituacaoTipos.IDLEITURAANORMALIDADECOMLEITURA, FaturamentoSituacaoTipos.INDICADORVALIDOAGUA,
                                             FaturamentoSituacaoTipos.INDICADORVALIDOESGOTO, FaturamentoSituacaoTipos.INDICADORDESCONSIDERESTALTCONS, FaturamentoSituacaoTipos.ULTIMAALTERACAO};
	
	public String[] getColunas(){
		return colunas;
	}	
	
	public static final class FaturamentoSituacaoTipos implements BaseColumns {
		public static final String ID = "FTST_ID";
		public static final String IDCONSACOBRARSEMLEITURA = "FTST_IDCONSACOBRARSEMLEIT";
		public static final String IDCONSACOBRARCOMLEITURA  = "FTST_IDCONSACOBRARCOMLEIT";
		public static final String IDLEITURAANORMALIDADESEMLEITURA  = "FTST_IDLEITAFATURARSEMLEIT";
		public static final String IDLEITURAANORMALIDADECOMLEITURA  = "LALT_IDLEITAFATURARCOMLEIT";
		public static final String INDICADORVALIDOAGUA  = "FTST_ICVALIDOAGUA";
		public static final String INDICADORVALIDOESGOTO  = "FTST_ICVALIDOESGOTO";
		public static final String INDICADORDESCONSIDERESTALTCONS  = "FTST_ICDESCONSIDERARACEC";
		public static final String ULTIMAALTERACAO = "FTST_TMULTIMAALTERACAO";
	}
	
	private void insertFromFile(ArrayList<String> obj){
		
		setIdString(obj.get(1));	
		if(obj.get(2).length()!=0){
			setIdAnormalidadeConsumoSemLeitura(Integer.parseInt(obj.get(2)));		
		}
		if(obj.get(3).length()!=0){
			setIdAnormalidadeConsumoComLeitura(Integer.parseInt(obj.get(3)));		
		}
		if(obj.get(4).length()!=0){
			setIdAnormalidadeLeituraSemLeitura(Integer.parseInt(obj.get(4)));		
		}
		if(obj.get(5).length()!=0){
			setIdAnormalidadeLeituraComLeitura(Integer.parseInt(obj.get(5)));		
		}
		if(obj.get(6).length()!=0){	
			setIndcValidaAgua(Integer.parseInt(obj.get(6)));		
		}
		if(obj.get(7).length()!=0){
			setIndcValidaEsgoto(Integer.parseInt(obj.get(7)));		
		}
		if(obj.get(8).length()!=0){
			setIndicadorDesconsiderarEstouroAltoConsumo(Integer.parseInt(obj.get(8)));	
		}
		String date = Util.convertDateToDateStr(Util.getCurrentDateTime());	
		setUltimaAlteracao(date);
	}

	public String getNomeTabela(){
		return "faturamento_situacao_tipo";
	}
	
	public final class FaturamentoSituacaoTipoTipos {
		public final String ID = " INTEGER PRIMARY KEY AUTOINCREMENT ";
		public final String IDCONSACOBRARSEMLEITURA = " INTEGER NOT NULL ";
		public final String IDCONSACOBRARCOMLEITURA  = " INTEGER NOT NULL ";
		public final String IDLEITURAANORMALIDADESEMLEITURA  = " INTEGER NOT NULL ";
		public final String IDLEITURAANORMALIDADECOMLEITURA  = " INTEGER NOT NULL ";
		public final String INDICADORVALIDOAGUA  = " INTEGER NULL DEFAULT 2 ";
		public final String INDICADORVALIDOESGOTO  = " INTEGER NULL DEFAULT 2 ";
		public final String INDICADORDESCONSIDERESTALTCONS  = " INTEGER NULL DEFAULT 2 ";
		public final String ULTIMAALTERACAO = " TIMESTAMP NOT NULL ";
		
		private String[] tipos = new String[] {
			ID, IDCONSACOBRARSEMLEITURA, IDCONSACOBRARCOMLEITURA, IDLEITURAANORMALIDADESEMLEITURA,
			IDLEITURAANORMALIDADECOMLEITURA, INDICADORVALIDOAGUA, INDICADORVALIDOESGOTO,
			INDICADORDESCONSIDERESTALTCONS, ULTIMAALTERACAO};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
	
	public ContentValues preencherValues() {
		ContentValues values = new ContentValues();
		values.put(FaturamentoSituacaoTipos.ID, getId());
		values.put(FaturamentoSituacaoTipos.IDCONSACOBRARCOMLEITURA, getIdAnormalidadeConsumoComLeitura());
		values.put(FaturamentoSituacaoTipos.IDCONSACOBRARSEMLEITURA, getIdAnormalidadeConsumoSemLeitura());
		values.put(FaturamentoSituacaoTipos.IDLEITURAANORMALIDADECOMLEITURA, getIdAnormalidadeLeituraComLeitura());
		values.put(FaturamentoSituacaoTipos.IDLEITURAANORMALIDADESEMLEITURA, getIdAnormalidadeLeituraSemLeitura());
		values.put(FaturamentoSituacaoTipos.INDICADORDESCONSIDERESTALTCONS, getIndicadorDesconsiderarEstouroAltoConsumo());
		values.put(FaturamentoSituacaoTipos.INDICADORVALIDOAGUA, getIndcValidaAgua());
		values.put(FaturamentoSituacaoTipos.INDICADORVALIDOESGOTO, getIndcValidaEsgoto());
		String dataStr = Util.convertDateToDateStr(Util.getCurrentDateTime());
		values.put(FaturamentoSituacaoTipos.ULTIMAALTERACAO, dataStr);
		
		return values;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<FaturamentoSituacaoTipo> preencherObjetos(Cursor cursor) {		
		ArrayList<FaturamentoSituacaoTipo> retorno = null;
		
		if (cursor.moveToFirst()) {	
			int codigo = cursor.getColumnIndex(FaturamentoSituacaoTipos.ID);
			int anormConsSemLeitura = cursor.getColumnIndex(FaturamentoSituacaoTipos.IDCONSACOBRARSEMLEITURA);
			int anormConsComLeitura = cursor.getColumnIndex(FaturamentoSituacaoTipos.IDCONSACOBRARCOMLEITURA);
			int anormLeituraSemLeitura = cursor.getColumnIndex(FaturamentoSituacaoTipos.IDLEITURAANORMALIDADECOMLEITURA);
			int anormLeituraComLeitura = cursor.getColumnIndex(FaturamentoSituacaoTipos.IDLEITURAANORMALIDADESEMLEITURA);
			int indicadorDesconsiderarEC = cursor.getColumnIndex(FaturamentoSituacaoTipos.INDICADORDESCONSIDERESTALTCONS);
			int indicadorValidoAgua = cursor.getColumnIndex(FaturamentoSituacaoTipos.INDICADORVALIDOAGUA);
			int indicadorValidoEsgoto = cursor.getColumnIndex(FaturamentoSituacaoTipos.INDICADORVALIDOESGOTO);
			int ultimaAlteracao = cursor.getColumnIndex(FaturamentoSituacaoTipos.ULTIMAALTERACAO);
			
			retorno = new ArrayList<FaturamentoSituacaoTipo>();	
			
			do {	
				FaturamentoSituacaoTipo faturamentoSituacaoTipo = new FaturamentoSituacaoTipo();
				
				faturamentoSituacaoTipo.setId(Util.getIntBanco(cursor, FaturamentoSituacaoTipos.ID, codigo));
				faturamentoSituacaoTipo.setIdAnormalidadeConsumoSemLeitura(Util.getIntBanco(cursor, FaturamentoSituacaoTipos.IDCONSACOBRARSEMLEITURA, anormConsSemLeitura));
				faturamentoSituacaoTipo.setIdAnormalidadeConsumoComLeitura(Util.getIntBanco(cursor, FaturamentoSituacaoTipos.IDCONSACOBRARCOMLEITURA, anormConsComLeitura));
				faturamentoSituacaoTipo.setIndcValidaAgua(Util.getIntBanco(cursor, FaturamentoSituacaoTipos.INDICADORVALIDOAGUA, indicadorValidoAgua));
				faturamentoSituacaoTipo.setIndcValidaEsgoto(Util.getIntBanco(cursor, FaturamentoSituacaoTipos.INDICADORVALIDOESGOTO, indicadorValidoEsgoto));
				faturamentoSituacaoTipo.setIdAnormalidadeLeituraComLeitura(Util.getIntBanco(cursor, FaturamentoSituacaoTipos.IDLEITURAANORMALIDADECOMLEITURA, anormLeituraComLeitura));
				faturamentoSituacaoTipo.setIdAnormalidadeLeituraSemLeitura(Util.getIntBanco(cursor, FaturamentoSituacaoTipos.IDLEITURAANORMALIDADESEMLEITURA, anormLeituraSemLeitura));
				faturamentoSituacaoTipo.setIndicadorDesconsiderarEstouroAltoConsumo(Util.getIntBanco(cursor, FaturamentoSituacaoTipos.INDICADORDESCONSIDERESTALTCONS, indicadorDesconsiderarEC));			
				faturamentoSituacaoTipo.setUltimaAlteracao(cursor.getString(ultimaAlteracao));
			
				retorno.add(faturamentoSituacaoTipo);
				
			} while (cursor.moveToNext());
		}		
		return retorno;
	}
}