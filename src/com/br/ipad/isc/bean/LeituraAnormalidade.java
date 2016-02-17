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
 * [] Classe Básica - Consumo Anormalidade
 * 
 * @author Fernanda Almeida
 * @since 08/03/2012
 */
public class LeituraAnormalidade extends ObjetoBasico implements Serializable {

	//Construtor default
	public LeituraAnormalidade(){}
	
	public LeituraAnormalidade(ArrayList<String> obj){
		insertFromFile(obj);
	}
	private static final long serialVersionUID = 1L;
	
	public static final int TER_LEITURA = 1;
	public static final int NAO_TER_LEITURA = 2;
	public static final int DUAS_OPCOES = 0;
	
	private Integer id;
	private String descricaoAnormalidadeLeitura;
	private Integer indicadorAceitaLeitura;
	private Integer idConsumoACobrarSemLeitura;
	private Integer idConsumoACobrarComLeitura;
	private Integer idLeituraAnormLeituraSemLeitura;
	private Integer idLeituraAnormLeituraComLeitura;
	private Integer indicadorUso;
	private BigDecimal numeroFatorSemLeitura;
	private BigDecimal numeroFatorComLeitura;
	private Integer indicadorCalcadaMensagem;
	private Integer indicadorHidrometroMensagem;
	private Integer indicadorNaoImpressaoConta;
	private Integer indicadorFotoObrigatoria;
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
		
	public String getDescricaoAnormalidadeLeitura() {
		return descricaoAnormalidadeLeitura;
	}
	public void setDescricaoAnormalidadeLeitura(String descricaoAnormalidadeLeitura) {
		this.descricaoAnormalidadeLeitura = descricaoAnormalidadeLeitura;
	}
	public Integer getIndicadorAceitaLeitura() {
		return indicadorAceitaLeitura;
	}
	public void setIndicadorAceitaLeitura(Integer indicadorAceitaLeitura) {
		this.indicadorAceitaLeitura = indicadorAceitaLeitura;
	}
	public Integer getIdConsumoACobrarSemLeitura() {
		return idConsumoACobrarSemLeitura;
	}
	public void setIdConsumoACobrarSemLeitura(Integer idConsumoACobrarSemLeitura) {
		this.idConsumoACobrarSemLeitura = idConsumoACobrarSemLeitura;
	}
	public Integer getIdConsumoACobrarComLeitura() {
		return idConsumoACobrarComLeitura;
	}
	public void setIdConsumoACobrarComLeitura(Integer idConsumoACobrarComLeitura) {
		this.idConsumoACobrarComLeitura = idConsumoACobrarComLeitura;
	}
	public Integer getIdLeituraAnormLeituraSemLeitura() {
		return idLeituraAnormLeituraSemLeitura;
	}
	public void setIdLeituraAnormLeituraSemLeitura(Integer idLeituraAnormLeituraSemLeitura) {
		this.idLeituraAnormLeituraSemLeitura = idLeituraAnormLeituraSemLeitura;
	}
	public Integer getIdLeituraAnormLeituraComLeitura() {
		return idLeituraAnormLeituraComLeitura;
	}
	public void setIdLeituraAnormLeituraComLeitura(Integer idLeituraAnormLeituraComLeitura) {
		this.idLeituraAnormLeituraComLeitura = idLeituraAnormLeituraComLeitura;
	}
	public Integer getIndicadorUso() {
		return indicadorUso;
	}
	public void setIndicadorUso(Integer indicadorUso) {
		this.indicadorUso = indicadorUso;
	}
	public BigDecimal getNumeroFatorSemLeitura() {
		return numeroFatorSemLeitura;
	}
	public void setNumeroFatorSemLeitura(BigDecimal numeroFatorSemLeitura) {
		this.numeroFatorSemLeitura = numeroFatorSemLeitura;
	}
	public BigDecimal getNumeroFatorComLeitura() {
		return numeroFatorComLeitura;
	}
	public void setNumeroFatorComLeitura(BigDecimal numeroFatorComLeitura) {
		this.numeroFatorComLeitura = numeroFatorComLeitura;
	}
	public Integer getIndicadorCalcadaMensagem() {
		return indicadorCalcadaMensagem;
	}
	public void setIndicadorCalcadaMensagem(Integer indicadorCalcadaMensagem) {
		this.indicadorCalcadaMensagem = indicadorCalcadaMensagem;
	}
	public Integer getIndicadorFotoObrigatoria() {
		return indicadorFotoObrigatoria;
	}
	public void setIndicadorFotoObrigatoria(Integer indicadorFotoObrigatoria) {
		this.indicadorFotoObrigatoria = indicadorFotoObrigatoria;
	}
	public Integer getIndicadorHidrometroMensagem() {
		return indicadorHidrometroMensagem;
	}
	public void setIndicadorHidrometroMensagem(Integer indicadorHidrometroMensagem) {
		this.indicadorHidrometroMensagem = indicadorHidrometroMensagem;
	}
	public Integer getIndicadorNaoImpressaoConta() {
		return indicadorNaoImpressaoConta;
	}
	public void setIndicadorNaoImpressaoConta(Integer indicadorNaoImpressaoConta) {
		this.indicadorNaoImpressaoConta = indicadorNaoImpressaoConta;
	}
	public Date getUltimaAlteracao() {
		return ultimaAlteracao;
	}
	public void setUltimaAlteracao(String ultimaAlteracao) {
		this.ultimaAlteracao = Util.getData(ultimaAlteracao);
	}
	
	private static String[] colunas = new String[] { LeiturasAnormalidades.ID, LeiturasAnormalidades.DESCRICAOLEITURAANORMALIDADE, LeiturasAnormalidades.INDICADORLEITURA,
             LeiturasAnormalidades.IDCONSUMOACOBRARCOMLEITURA, LeiturasAnormalidades.IDCONSUMOACOBRARSEMLEITURA, LeiturasAnormalidades.IDLEITURAANORMALIDADECOMLEITURA,
             LeiturasAnormalidades.IDLEITURAANORMALIDADESEMLEITURA, LeiturasAnormalidades.INDICADORUSO, LeiturasAnormalidades.NUMEROFATORSEMLEITURA,
             LeiturasAnormalidades.NUMEROFATORCOMLEITURA, LeiturasAnormalidades.INDICADORCALCADAMENSAGEM, LeiturasAnormalidades.INDICADORHIDROMETROMENSAGEM,
             LeiturasAnormalidades.INDICADORNAOIMPRIMIRCONTA,LeiturasAnormalidades.INDICADORFOTOOBRIGATORIA, LeiturasAnormalidades.ULTIMAALTERACAO,
	};
	
	public String[] getColunas(){
		return colunas;
	}	
	
	public static final class LeiturasAnormalidades implements BaseColumns {
		public static final String ID = "LTAN_ID";
		public static final String DESCRICAOLEITURAANORMALIDADE = "LTAN_DSLEITURAANORMALIDADE";
		public static final String INDICADORLEITURA = "LTAN_ICLEITURA";
		public static final String IDCONSUMOACOBRARCOMLEITURA = "LACS_IDCONSACOBRARCOMLEIT";
		public static final String IDCONSUMOACOBRARSEMLEITURA = "LACS_IDCONSACOBRARSEMLEIT";
		public static final String IDLEITURAANORMALIDADECOMLEITURA = "LALT_IDLEITAFATURARCOMLEIT";
		public static final String IDLEITURAANORMALIDADESEMLEITURA = "LALT_IDLEITAFATURARSEMLEIT";
		public static final String INDICADORUSO = "LTAN_ICUSO";
		public static final String NUMEROFATORSEMLEITURA = "LTAN_NNFATORSEMLEITURA";
		public static final String NUMEROFATORCOMLEITURA = "LTAN_NNFATORCOMLEITURA";
		public static final String INDICADORCALCADAMENSAGEM = "LTAN_ICCALCADAMSG";
		public static final String INDICADORHIDROMETROMENSAGEM = "LTAN_ICSUBSHIDROMETRORMSG";
		public static final String INDICADORNAOIMPRIMIRCONTA = "LTAN_ICNAOIMPRIMIRCONTA";
		public static final String INDICADORFOTOOBRIGATORIA = "LTAN_ICFOTOOBRIGATORIA";
		public static final String ULTIMAALTERACAO = "LTAN_TMULTIMAALTERACAO";
	}
	
	private void insertFromFile(ArrayList<String> obj){
		
		setIdString(obj.get(1));	
		setDescricaoAnormalidadeLeitura(obj.get(2));	
		setIndicadorAceitaLeitura(Integer.parseInt(obj.get(3)));	
		setIdConsumoACobrarComLeitura(Integer.parseInt(obj.get(4)));
		setIdConsumoACobrarSemLeitura(Integer.parseInt(obj.get(5)));
		setIdLeituraAnormLeituraComLeitura(Integer.parseInt(obj.get(6)));
		setIdLeituraAnormLeituraSemLeitura(Integer.parseInt(obj.get(7)));
		setIndicadorUso(Integer.parseInt(obj.get(8)));
		if(obj.get(9).length()!=0){
			BigDecimal numeroSemLeitura = new BigDecimal(obj.get(9));
			setNumeroFatorSemLeitura(numeroSemLeitura);
		}
		if(obj.get(10).length()!=0){
			BigDecimal numeroComLeitura = new BigDecimal(obj.get(10));
			setNumeroFatorComLeitura(numeroComLeitura);
		}
		setIndicadorCalcadaMensagem(Integer.parseInt(obj.get(11)));
		setIndicadorHidrometroMensagem(Integer.parseInt(obj.get(12)));
		setIndicadorNaoImpressaoConta(Integer.parseInt(obj.get(13)));	
		if(obj.get(14).length() != 0){
			setIndicadorFotoObrigatoria(Integer.parseInt(obj.get(14)));	
		}
		
		String date = Util.convertDateToDateStr(Util.getCurrentDateTime());	
		setUltimaAlteracao(date);
	}

	public String getNomeTabela(){
		return "leitura_anormalidade";
	}
	
	public final class LeiturasAnormalidadesTipos {
		public final String ID = " INTEGER PRIMARY KEY AUTOINCREMENT ";
		public final String DESCRICAOLEITURAANORMALIDADE = " VARCHAR(25) NOT NULL ";
		public final String INDICADORLEITURA = " INTEGER NOT NULL ";
		public final String IDCONSUMOACOBRARCOMLEITURA = " INTEGER NOT NULL ";
		public final String IDCONSUMOACOBRARSEMLEITURA = " INTEGER NOT NULL ";
		public final String IDLEITURAANORMALIDADECOMLEITURA = " INTEGER NOT NULL ";
		public final String IDLEITURAANORMALIDADESEMLEITURA = " INTEGER NOT NULL ";
		public final String INDICADORUSO = " INTEGER NOT NULL ";				
		public final String NUMEROFATORSEMLEITURA = " NUMERIC(3,2) NOT NULL ";
		public final String NUMEROFATORCOMLEITURA = " NUMERIC(3,2) NOT NULL ";		
		public final String INDICADORCALCADAMENSAGEM = " INTEGER NOT NULL ";
		public final String INDICADORHIDROMETROMENSAGEM = " INTEGER NOT NULL ";
		public final String INDICADORNAOIMPRIMIRCONTA = " INTEGER NOT NULL DEFAULT 2 ";
		public final String INDICADORFOTOOBRIGATORIA = " INTEGER NOT NULL DEFAULT 2 ";
		public final String ULTIMAALTERACAO = " TIMESTAMP NOT NULL ";
		
		private String[] tipos = new String[] {
			ID, DESCRICAOLEITURAANORMALIDADE, INDICADORLEITURA, IDCONSUMOACOBRARCOMLEITURA,
			IDCONSUMOACOBRARSEMLEITURA, IDLEITURAANORMALIDADECOMLEITURA, IDLEITURAANORMALIDADESEMLEITURA,
			INDICADORUSO, NUMEROFATORSEMLEITURA, NUMEROFATORCOMLEITURA, INDICADORCALCADAMENSAGEM, 
			INDICADORHIDROMETROMENSAGEM, INDICADORNAOIMPRIMIRCONTA, INDICADORFOTOOBRIGATORIA,ULTIMAALTERACAO};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
	
	public ContentValues preencherValues() {
		ContentValues values = new ContentValues();
		values.put(LeiturasAnormalidades.ID, getId());
		values.put(LeiturasAnormalidades.DESCRICAOLEITURAANORMALIDADE, getDescricaoAnormalidadeLeitura());
		values.put(LeiturasAnormalidades.IDCONSUMOACOBRARCOMLEITURA, getIdConsumoACobrarComLeitura());
		values.put(LeiturasAnormalidades.IDCONSUMOACOBRARSEMLEITURA, getIdConsumoACobrarSemLeitura());
		values.put(LeiturasAnormalidades.IDLEITURAANORMALIDADECOMLEITURA, getIdLeituraAnormLeituraComLeitura());
		values.put(LeiturasAnormalidades.IDLEITURAANORMALIDADESEMLEITURA, getIdLeituraAnormLeituraSemLeitura());
		values.put(LeiturasAnormalidades.INDICADORCALCADAMENSAGEM, getIndicadorCalcadaMensagem());
		values.put(LeiturasAnormalidades.INDICADORHIDROMETROMENSAGEM, getIndicadorHidrometroMensagem());
		values.put(LeiturasAnormalidades.INDICADORLEITURA, getIndicadorAceitaLeitura());
		values.put(LeiturasAnormalidades.INDICADORNAOIMPRIMIRCONTA, getIndicadorNaoImpressaoConta());
		values.put(LeiturasAnormalidades.INDICADORFOTOOBRIGATORIA, getIndicadorFotoObrigatoria());
		values.put(LeiturasAnormalidades.INDICADORUSO, getIndicadorUso());
		if(getNumeroFatorComLeitura() != null){
			values.put(LeiturasAnormalidades.NUMEROFATORCOMLEITURA, getNumeroFatorComLeitura().toString());
		}
		if(getNumeroFatorSemLeitura() != null){
			values.put(LeiturasAnormalidades.NUMEROFATORSEMLEITURA, getNumeroFatorSemLeitura().toString());
		}
		String dataStr = Util.convertDateToDateStr(Util.getCurrentDateTime());
		values.put(LeiturasAnormalidades.ULTIMAALTERACAO, dataStr);
		
		return values;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<LeituraAnormalidade> preencherObjetos(Cursor cursor) {		
		ArrayList<LeituraAnormalidade> retorno = null;
		
		if (cursor.moveToFirst()) {	
			int codigo = cursor.getColumnIndex(LeiturasAnormalidades.ID);
			int descLeituraAnormalidade = cursor.getColumnIndex(LeiturasAnormalidades.DESCRICAOLEITURAANORMALIDADE);
			int idConsumoACobrarComLeitura = cursor.getColumnIndex(LeiturasAnormalidades.IDCONSUMOACOBRARCOMLEITURA);
			int idConsumoACobrarSemLeitura = cursor.getColumnIndex(LeiturasAnormalidades.IDCONSUMOACOBRARSEMLEITURA);
			int idLeituraAnormComLeitura = cursor.getColumnIndex(LeiturasAnormalidades.IDLEITURAANORMALIDADECOMLEITURA);
			int idLeituraAnormSemLeitura = cursor.getColumnIndex(LeiturasAnormalidades.IDLEITURAANORMALIDADESEMLEITURA);
			int indicadorCalcada = cursor.getColumnIndex(LeiturasAnormalidades.INDICADORCALCADAMENSAGEM);
			int indicadorHidrometro = cursor.getColumnIndex(LeiturasAnormalidades.INDICADORHIDROMETROMENSAGEM);
			int indicadorLeitura = cursor.getColumnIndex(LeiturasAnormalidades.INDICADORLEITURA);
			int indicadorNaoImprimir = cursor.getColumnIndex(LeiturasAnormalidades.INDICADORNAOIMPRIMIRCONTA);
			int indicadorUso = cursor.getColumnIndex(LeiturasAnormalidades.INDICADORUSO);
			int numeroFatorComLeitura = cursor.getColumnIndex(LeiturasAnormalidades.NUMEROFATORCOMLEITURA);
			int numeroFatorSemLeitura = cursor.getColumnIndex(LeiturasAnormalidades.NUMEROFATORSEMLEITURA);
			int ultimaAlteracao = cursor.getColumnIndex(LeiturasAnormalidades.ULTIMAALTERACAO);
			int indicadorFotoObrigatorio = cursor.getColumnIndex(LeiturasAnormalidades.INDICADORFOTOOBRIGATORIA);
			
			retorno = new ArrayList<LeituraAnormalidade>();			
			do {	
				LeituraAnormalidade leituraAnormalidade = new LeituraAnormalidade();
				leituraAnormalidade.setId(Util.getIntBanco(cursor, LeiturasAnormalidades.ID, codigo));
				leituraAnormalidade.setDescricaoAnormalidadeLeitura(cursor.getString(descLeituraAnormalidade));
				leituraAnormalidade.setIdConsumoACobrarComLeitura(Util.getIntBanco(cursor, LeiturasAnormalidades.IDCONSUMOACOBRARCOMLEITURA, idConsumoACobrarComLeitura));
				leituraAnormalidade.setIdConsumoACobrarSemLeitura(Util.getIntBanco(cursor, LeiturasAnormalidades.IDCONSUMOACOBRARSEMLEITURA, idConsumoACobrarSemLeitura));
				leituraAnormalidade.setIdLeituraAnormLeituraComLeitura(Util.getIntBanco(cursor, LeiturasAnormalidades.IDLEITURAANORMALIDADECOMLEITURA, idLeituraAnormComLeitura));
				leituraAnormalidade.setIdLeituraAnormLeituraSemLeitura(Util.getIntBanco(cursor, LeiturasAnormalidades.IDLEITURAANORMALIDADESEMLEITURA, idLeituraAnormSemLeitura));
				leituraAnormalidade.setIndicadorAceitaLeitura(Util.getIntBanco(cursor, LeiturasAnormalidades.INDICADORLEITURA, indicadorLeitura));
				leituraAnormalidade.setIndicadorCalcadaMensagem(Util.getIntBanco(cursor, LeiturasAnormalidades.INDICADORCALCADAMENSAGEM, indicadorCalcada));
				leituraAnormalidade.setIndicadorHidrometroMensagem(Util.getIntBanco(cursor, LeiturasAnormalidades.INDICADORHIDROMETROMENSAGEM, indicadorHidrometro));
				leituraAnormalidade.setIndicadorNaoImpressaoConta(Util.getIntBanco(cursor, LeiturasAnormalidades.INDICADORNAOIMPRIMIRCONTA, indicadorNaoImprimir));
				leituraAnormalidade.setIndicadorUso(Util.getIntBanco(cursor, LeiturasAnormalidades.INDICADORUSO, indicadorUso));
				leituraAnormalidade.setNumeroFatorComLeitura(Util.getDoubleBanco(cursor, LeiturasAnormalidades.NUMEROFATORCOMLEITURA, numeroFatorComLeitura));
				leituraAnormalidade.setNumeroFatorSemLeitura(Util.getDoubleBanco(cursor, LeiturasAnormalidades.NUMEROFATORSEMLEITURA, numeroFatorSemLeitura));
				leituraAnormalidade.setUltimaAlteracao(cursor.getString(ultimaAlteracao));
				leituraAnormalidade.setIndicadorFotoObrigatoria(Util.getIntBanco(cursor, LeiturasAnormalidades.INDICADORFOTOOBRIGATORIA, indicadorFotoObrigatorio));
				
				retorno.add(leituraAnormalidade);
				
			} while (cursor.moveToNext());
		}		
		return retorno;
	}
}