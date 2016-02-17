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
 * [] Classe Básica - Conta Imposto 
 * 
 * @author Fernanda Almeida
 * @since 08/03/2012
 */
public class ContaImposto extends ObjetoBasico implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//Construtor default
	public ContaImposto(){}
			
	public ContaImposto(ArrayList<String> obj){
		insertFromFile(obj);
	}
	
	private Integer id;
	private ImovelConta matricula;
	private Integer tipoImposto;
	private String descricaoImposto;
	private BigDecimal percentualAlicota;
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
	public Integer getTipoImposto() {
		return tipoImposto;
	}
	public void setTipoImposto(Integer tipoImposto) {
		this.tipoImposto = tipoImposto;
	}
	public String getDescricaoImposto() {
		return descricaoImposto;
	}
	public void setDescricaoImposto(String descricaoImposto) {
		this.descricaoImposto = descricaoImposto;
	}
	public BigDecimal getPercentualAlicota() {
		return percentualAlicota;
	}
	public void setPercentualAlicota(BigDecimal percentualAlicota) {
		this.percentualAlicota = percentualAlicota;
	}
	public void setIdString(String id) {
		this.id = Util.verificarNuloInt(id);
	}
		
	public Date getUltimaAlteracao() {
		return ultimaAlteracao;
	}
	public void setUltimaAlteracao(String ultimaAlteracao) {
		this.ultimaAlteracao = Util.getData(ultimaAlteracao);
	}
	
	private static String[] colunas = new String[] { ContasImpostos.ID,ContasImpostos.MATRICULA, ContasImpostos.IDIMPOSTO,ContasImpostos.DESCRICAOIMPOSTOTIPO, 
	                                                 ContasImpostos.PERCENTUALALIQUOTA, ContasImpostos.ULTIMAALTERACAO};
	
	public String[] getColunas(){
		return colunas;
	}	
	
	public static final class ContasImpostos implements BaseColumns {
		public static final String ID = "CNIP_ID";
		public static final String MATRICULA = "IMOV_ID";
		public static final String IDIMPOSTO = "IMTP_ID";
		public static final String DESCRICAOIMPOSTOTIPO = "CNIP_DSIMPOSTOTIPO";
		public static final String PERCENTUALALIQUOTA = "CNIP_PCALIQUOTA";
		public static final String ULTIMAALTERACAO = "CNIP_TMULTIMAALTERACAO";
	}
	
	private void insertFromFile(ArrayList<String> obj){
		
		ImovelConta matricula = new ImovelConta();
		matricula.setId(Integer.parseInt(obj.get(1)));
		setMatricula(matricula);

		if(obj.get(2).length()!=0){
			setTipoImposto(Integer.parseInt(obj.get(2)));
		}	
		setDescricaoImposto(obj.get(3));	
		if(obj.get(4).length()!=0){
			BigDecimal percentual = new BigDecimal(obj.get(4));
			setPercentualAlicota(percentual);
		}
		String date = Util.convertDateToDateStr(Util.getCurrentDateTime());	
		setUltimaAlteracao(date);
	}
	
	public String getNomeTabela(){
		return "conta_imposto";
	}
	
	public final class ContaImpostosTipos {
		public final String ID = " INTEGER PRIMARY KEY AUTOINCREMENT ";
		public final String MATRICULA = " INTEGER CONSTRAINT [FK1_CONTA_IMPOSTO] REFERENCES [imovel_conta]([IMOV_ID]) ON DELETE RESTRICT ON UPDATE RESTRICT ";
		public final String IDIMPOSTO = " INTEGER  NOT NULL ";
		public final String DESCRICAOIMPOSTOTIPO = " VARCHAR(10) NOT NULL ";
		public final String PERCENTUALALIQUOTA = " NUMERIC(5,2) NOT NULL ";
		public final String ULTIMAALTERACAO = " TIMESTAMP NOT NULL ";
				
		
		private String[] tipos = new String[] {
			ID, MATRICULA, IDIMPOSTO, DESCRICAOIMPOSTOTIPO, 
            PERCENTUALALIQUOTA, ULTIMAALTERACAO};
            
		public String[] getTipos(){
			return tipos;
		}
	}

	public ContentValues preencherValues() {
		ContentValues values = new ContentValues();
		values.put(ContasImpostos.DESCRICAOIMPOSTOTIPO, getDescricaoImposto());
		values.put(ContasImpostos.IDIMPOSTO, getTipoImposto());
		values.put(ContasImpostos.MATRICULA, getMatricula().getId());
		if(getPercentualAlicota() != null){
			values.put(ContasImpostos.PERCENTUALALIQUOTA, getPercentualAlicota().toString());
		}
		String dataStr = Util.convertDateToDateStr(Util.getCurrentDateTime());
		values.put(ContasImpostos.ULTIMAALTERACAO, dataStr);
		return values;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ContaImposto> preencherObjetos(Cursor cursor) {		
		ArrayList<ContaImposto> retorno = null;
		
		if (cursor.moveToFirst()) {	
			int codigo = cursor.getColumnIndex(ContasImpostos.ID);
			int matricula = cursor.getColumnIndex(ContasImpostos.MATRICULA);
			int idImposto = cursor.getColumnIndex(ContasImpostos.IDIMPOSTO);
			int percentualAliquota = cursor.getColumnIndex(ContasImpostos.PERCENTUALALIQUOTA);
			int descricaoImpostoTipo = cursor.getColumnIndex(ContasImpostos.DESCRICAOIMPOSTOTIPO);
			int ultimaAlteracao = cursor.getColumnIndex(ContasImpostos.ULTIMAALTERACAO);
			
			retorno = new ArrayList<ContaImposto>();			
			do {	
				ContaImposto contaImposto = new ContaImposto();
				
				if(Util.getIntBanco(cursor, ContasImpostos.MATRICULA, matricula) != null){
					ImovelConta imovelConta = new ImovelConta(cursor.getInt(matricula));
					contaImposto.setMatricula(imovelConta);
				}
				contaImposto.setId(Util.getIntBanco(cursor, ContasImpostos.ID, codigo));
				contaImposto.setDescricaoImposto(cursor.getString(descricaoImpostoTipo));
				contaImposto.setTipoImposto(Util.getIntBanco(cursor, ContasImpostos.IDIMPOSTO, idImposto));
				contaImposto.setPercentualAlicota(Util.getDoubleBanco(cursor, ContasImpostos.PERCENTUALALIQUOTA, percentualAliquota));
				contaImposto.setUltimaAlteracao(cursor.getString(ultimaAlteracao));
				
				retorno.add(contaImposto);
				
			} while (cursor.moveToNext());
		}		
		return retorno;
	}
}
