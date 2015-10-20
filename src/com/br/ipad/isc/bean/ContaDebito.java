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
 * [] Classe Básica - Conta Dábito
 * 
 * @author Fernanda Almeida
 * @since 08/03/2012
 */
public class ContaDebito extends ObjetoBasico implements Serializable {

	//Construtor default
	public ContaDebito(){}
	
	public ContaDebito(ArrayList<String> obj){
		insertFromFile(obj);
	}
		
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private ImovelConta matricula;
	private String anoMesReferencia;
	private BigDecimal valorConta;
	private BigDecimal valorAcrescimoImpontualidade;
	private Date dataVencimentoConta;
	private Date ultimaAlteracao;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setIdString(String id) {
		this.id = Util.verificarNuloInt(id);
	}	
	
	public ImovelConta getMatricula() {
		return matricula;
	}
	public void setMatricula(ImovelConta matricula) {
		this.matricula = matricula;
	}
	public String getAnoMesReferencia() {
		return anoMesReferencia;
	}
	public void setAnoMesReferencia(String anoMesReferencia) {
		this.anoMesReferencia = anoMesReferencia;
	}
	
	public BigDecimal getValorConta() {
		return valorConta;
	}
	public void setValorConta(BigDecimal valorConta) {
		this.valorConta = valorConta;
	}
	public BigDecimal getValorAcrescimoImpontualidade() {
		return valorAcrescimoImpontualidade;
	}
	public void setValorAcrescimoImpontualidade(BigDecimal valorAcrescimoImpontualidade) {
		this.valorAcrescimoImpontualidade = valorAcrescimoImpontualidade;
	}
	public Date getDataVencimentoConta() {
		return dataVencimentoConta;
	}
	public void setDataVencimentoConta(Date dataVencimentoConta) {
		this.dataVencimentoConta = dataVencimentoConta;
	}
	public Date getUltimaAlteracao() {
		return ultimaAlteracao;
	}
	public void setUltimaAlteracao(String ultimaAlteracao) {
		this.ultimaAlteracao = Util.getData(ultimaAlteracao);
	}
	
	private static String[] colunas = new String[] { ContasDebitos.ID,ContasDebitos.MATRICULA, ContasDebitos.ANOMESREFERENCIA, ContasDebitos.VALORCONTA, 
                                             ContasDebitos.DATAVENCIMENTOCONTA, ContasDebitos.VALORACRESCIMOIMPONTUALIDADE,ContasDebitos.ULTIMAALTERACAO
	};
	
	public String[] getColunas(){
		return colunas;
	}	
	
	public static final class ContasDebitos implements BaseColumns {
		public static final String ID = "CODB_ID";
		public static final String MATRICULA = "IMOV_ID";
		public static final String ANOMESREFERENCIA = "CODB_AMREFERCONTA";
		public static final String VALORCONTA   = "CODB_VALORCONTA";
		public static final String DATAVENCIMENTOCONTA  = "CODB_DTVENCICONTA";
		public static final String VALORACRESCIMOIMPONTUALIDADE   = "CODB_VLACRESIMPONT";
		public static final String ULTIMAALTERACAO  = "CODB_TMULTIMAALTERACAO";
	}
	
	public String getNomeTabela(){
		return "conta_debito";
	}
	
	public final class ContasDebitosTipos {
		public final String ID = " INTEGER PRIMARY KEY AUTOINCREMENT ";	
		public final String MATRICULA = " CONSTRAINT [FK1_CONTA_DEBITO] REFERENCES [imovel_conta]([IMOV_ID]) ON DELETE RESTRICT ON UPDATE RESTRICT ";
		public final String ANOMESREFERENCIA = " VARCHAR(10)  NOT NULL ";
		public final String VALORCONTA   = " NUMERIC(13,2) NOT NULL ";
		public final String DATAVENCIMENTOCONTA  = " DATE NOT NULL ";
		public final String VALORACRESCIMOIMPONTUALIDADE   = " NUMERIC(13,2) NOT NULL ";
		public final String ULTIMAALTERACAO = " TIMESTAMP NOT NULL ";
			
		private String[] tipos = new String[] {
				ID, MATRICULA, ANOMESREFERENCIA, VALORCONTA, DATAVENCIMENTOCONTA,
				VALORACRESCIMOIMPONTUALIDADE, ULTIMAALTERACAO};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
	
	private void insertFromFile(ArrayList<String> obj){
		
		ImovelConta matricula = new ImovelConta();
		matricula.setId(Integer.parseInt(obj.get(1)));
		setMatricula(matricula);

		if(obj.get(2).length()!=0){
			setAnoMesReferencia(obj.get(2));	
		}
		if(obj.get(3).length()!=0){
			BigDecimal valorConta = new BigDecimal(obj.get(3));
			setValorConta(valorConta);	
		}
		if(obj.get(4).length()!=0){ 
			Date dataFormatada = Util.convertDateStrToDate1(obj.get(4));
			setDataVencimentoConta(dataFormatada);
		}
		if(obj.get(5).length()!=0){
			BigDecimal valorAcrescimoImpontualidade = new BigDecimal(obj.get(5));
			setValorAcrescimoImpontualidade(valorAcrescimoImpontualidade);	
		}
		
		String date = Util.convertDateToDateStr(Util.getCurrentDateTime());	
		setUltimaAlteracao(date);			
	}
	
	public ContentValues preencherValues() {
		ContentValues values = new ContentValues();
		values.put(ContasDebitos.ANOMESREFERENCIA, getAnoMesReferencia());
		values.put(ContasDebitos.MATRICULA, getMatricula().getId());
		if(getDataVencimentoConta() != null){
			String dataStr = Util.convertDateToDateStr(getDataVencimentoConta());		
			values.put(ContasDebitos.DATAVENCIMENTOCONTA, dataStr);	
		}
		if(getValorAcrescimoImpontualidade() != null){
			values.put(ContasDebitos.VALORACRESCIMOIMPONTUALIDADE, getValorAcrescimoImpontualidade().toString());
		}
		if(getValorConta() != null){
			values.put(ContasDebitos.VALORCONTA, getValorConta().toString());
		}
		String dataStr = Util.convertDateToDateStr(Util.getCurrentDateTime());
		values.put(ContasDebitos.ULTIMAALTERACAO, dataStr);
		
		return values;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ContaDebito> preencherObjetos(Cursor cursor) {		
		ArrayList<ContaDebito> retorno = null;
		
		if (cursor.moveToFirst()) {	
			int codigo = cursor.getColumnIndex(ContasDebitos.ID);
			int matricula = cursor.getColumnIndex(ContasDebitos.MATRICULA);
			int amReferencia = cursor.getColumnIndex(ContasDebitos.ANOMESREFERENCIA);
			int dataVencimento = cursor.getColumnIndex(ContasDebitos.DATAVENCIMENTOCONTA);
			int valorImpontualidade = cursor.getColumnIndex(ContasDebitos.VALORACRESCIMOIMPONTUALIDADE);
			int valorConta = cursor.getColumnIndex(ContasDebitos.VALORCONTA);
			int ultimaAlteracao = cursor.getColumnIndex(ContasDebitos.ULTIMAALTERACAO);
			
			retorno = new ArrayList<ContaDebito>();			
			do {	
				ContaDebito contaDebito = new ContaDebito();

				if(Util.getIntBanco(cursor, ContasDebitos.MATRICULA, matricula) != null){
					ImovelConta imovelConta = new ImovelConta(cursor.getInt(matricula));
					contaDebito.setMatricula(imovelConta);
				}
				contaDebito.setId(Util.getIntBanco(cursor, ContasDebitos.ID, codigo));
				contaDebito.setAnoMesReferencia(cursor.getString(amReferencia));
				
				//formata data vencimento
				Date data = new Date();
				String dataStr = cursor.getString(dataVencimento);
				if(dataStr != null){
					data = Util.convertDateStrToDate(dataStr);	
					contaDebito.setDataVencimentoConta(data);
				}
				
				contaDebito.setValorAcrescimoImpontualidade(Util.getDoubleBanco(cursor, ContasDebitos.VALORACRESCIMOIMPONTUALIDADE, valorImpontualidade));
				contaDebito.setValorConta(Util.getDoubleBanco(cursor, ContasDebitos.VALORCONTA, valorConta));
				contaDebito.setUltimaAlteracao(cursor.getString(ultimaAlteracao));
				
				retorno.add(contaDebito);
				
			} while (cursor.moveToNext());
		}		
		return retorno;
	}

}
