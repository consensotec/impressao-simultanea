
package com.br.ipad.isc.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.util.Log;

import com.br.ipad.isc.excecoes.RepositorioException;
import com.br.ipad.isc.repositorios.RepositorioBasico;
import com.br.ipad.isc.util.ConstantesSistema;
import com.br.ipad.isc.util.Util;

/**
 * [] Classe Básica - Consumo Anormalidade Ação
 * 
 * @author Fernanda Almeida
 * @since 08/03/2012
 */
public class ConsumoAnormalidadeAcao extends ObjetoBasico implements Serializable {

	public ConsumoAnormalidadeAcao(){}
	
	public ConsumoAnormalidadeAcao(ArrayList<String> obj){
		insertFromFile(obj);
	}

	private static final long serialVersionUID = 1L;

	private Integer id;
	private ConsumoAnormalidade consumoAnormalidade;
	private Integer idCategoria;
	private Integer idPerfil;
	private Integer idLeituraAnormalidadeConsumo;
	private Integer idLeituraAnormalidadeConsumoSegundoMes;
	private Integer idLeituraAnormalidadeConsumoTerceiroMes;
	private BigDecimal fatorConsumo;
	private BigDecimal fatorConsumoSegundoMes;
	private BigDecimal fatorConsumoTerceiroMes;
	private String mensagemConta;
	private String mensagemContaSegundoMes;
	private String mensagemContaTerceiroMes;

	private Integer codigoMesConsecutivo;

	private Date ultimaAlteracao;

    private Short indicadorGeracaoCartaMes1;
    private Short indicadorGeracaoCartaMes2;
    private Short indicadorGeracaoCartaMes3;
    private Short indicadorCobrancaConsumoNormal;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setIdString(String id) {
		this.id = Util.verificarNuloInt(id);
	}
		
	public ConsumoAnormalidade getConsumoAnormalidade() {
		return consumoAnormalidade;
	}
	public void setConsumoAnormalidade(ConsumoAnormalidade consumoAnormalidade) {
		this.consumoAnormalidade = consumoAnormalidade;
	}
	public Integer getIdCategoria() {
		return idCategoria;
	}
	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}
	public Integer getIdPerfil() {
		return idPerfil;
	}
	public void setIdPerfil(Integer idPerfil) {
		this.idPerfil = idPerfil;
	}
	public Integer getIdLeituraAnormalidadeConsumo() {
		return idLeituraAnormalidadeConsumo;
	}
	public void setIdLeituraAnormalidadeConsumo(Integer idLeituraAnormalidadeConsumo) {
		this.idLeituraAnormalidadeConsumo = idLeituraAnormalidadeConsumo;
	}
	public Integer getIdLeituraAnormalidadeConsumoSegundoMes() {
		return idLeituraAnormalidadeConsumoSegundoMes;
	}
	public void setIdLeituraAnormalidadeConsumoSegundoMes(Integer idLeituraAnormalidadeConsumoSegundoMes) {
		this.idLeituraAnormalidadeConsumoSegundoMes = idLeituraAnormalidadeConsumoSegundoMes;
	}
	public Integer getIdLeituraAnormalidadeConsumoTerceiroMes() {
		return idLeituraAnormalidadeConsumoTerceiroMes;
	}
	public void setIdLeituraAnormalidadeConsumoTerceiroMes(Integer idLeituraAnormalidadeConsumoTerceiroMes) {
		this.idLeituraAnormalidadeConsumoTerceiroMes = idLeituraAnormalidadeConsumoTerceiroMes;
	}
	public BigDecimal getFatorConsumo() {
		return fatorConsumo;
	}
	public void setFatorConsumo(BigDecimal fatorConsumo) {
		this.fatorConsumo = fatorConsumo;
	}
	public BigDecimal getFatorConsumoSegundoMes() {
		return fatorConsumoSegundoMes;
	}
	public void setFatorConsumoSegundoMes(BigDecimal fatorConsumoSegundoMes) {
		this.fatorConsumoSegundoMes = fatorConsumoSegundoMes;
	}
	public BigDecimal getFatorConsumoTerceiroMes() {
		return fatorConsumoTerceiroMes;
	}
	public void setFatorConsumoTerceiroMes(BigDecimal fatorConsumoTerceiroMes) {
		this.fatorConsumoTerceiroMes = fatorConsumoTerceiroMes;
	}
	public String getMensagemConta() {
		return mensagemConta;
	}
	public void setMensagemConta(String mensagemConta) {
		this.mensagemConta = mensagemConta;
	}
	public String getMensagemContaSegundoMes() {
		return mensagemContaSegundoMes;
	}
	public void setMensagemContaSegundoMes(String mensagemContaSegundoMes) {
		this.mensagemContaSegundoMes = mensagemContaSegundoMes;
	}
	public String getMensagemContaTerceiroMes() {
		return mensagemContaTerceiroMes;
	}
	public void setMensagemContaTerceiroMes(String mensagemContaTerceiroMes) {
		this.mensagemContaTerceiroMes = mensagemContaTerceiroMes;
	}
	public Integer getCodigoMesConsecutivo() {
		return codigoMesConsecutivo;
	}
	public void setCodigoMesConsecutivo(Integer codigoMesConsecutivo) {
		this.codigoMesConsecutivo = codigoMesConsecutivo;
	}
	public Date getUltimaAlteracao() {
		return ultimaAlteracao;
	}
	public void setUltimaAlteracao(String ultimaAlteracao) {
		this.ultimaAlteracao = Util.getData(ultimaAlteracao);
	}
	public Short getIndicadorGeracaoCartaMes1() {
		return indicadorGeracaoCartaMes1;
	}
	public void setIndicadorGeracaoCartaMes1(Short indicadorGeracaoCartaMes1) {
		this.indicadorGeracaoCartaMes1 = indicadorGeracaoCartaMes1;
	}
	public Short getIndicadorGeracaoCartaMes2() {
		return indicadorGeracaoCartaMes2;
	}
	public void setIndicadorGeracaoCartaMes2(Short indicadorGeracaoCartaMes2) {
		this.indicadorGeracaoCartaMes2 = indicadorGeracaoCartaMes2;
	}
	public Short getIndicadorGeracaoCartaMes3() {
		return indicadorGeracaoCartaMes3;
	}
	public void setIndicadorGeracaoCartaMes3(Short indicadorGeracaoCartaMes3) {
		this.indicadorGeracaoCartaMes3 = indicadorGeracaoCartaMes3;
	}
	public Short getIndicadorCobrancaConsumoNormal() {
		return indicadorCobrancaConsumoNormal;
	}
	public void setIndicadorCobrancaConsumoNormal(Short indicadorCobrancaConsumoNormal) {
		this.indicadorCobrancaConsumoNormal = indicadorCobrancaConsumoNormal;
	}

	private static String[] colunas = new String[] { ConsumoAnormalidadeAcoes.ID, ConsumoAnormalidadeAcoes.CONSUMOANORMALIDADE,
		ConsumoAnormalidadeAcoes.IDCATEGORIA, ConsumoAnormalidadeAcoes.IDPERFIL,
		ConsumoAnormalidadeAcoes.IDLEITURAANORMCONSUMO,ConsumoAnormalidadeAcoes.IDLEITURAANORMCONSUMOSEGMES,
		ConsumoAnormalidadeAcoes.IDLEITURAANORMCONSUMOTERMES, ConsumoAnormalidadeAcoes.FATORCONSUMO,
		ConsumoAnormalidadeAcoes.FATORCONSUMOSEGMES, ConsumoAnormalidadeAcoes.FATORCONSUMOTERMES,
		ConsumoAnormalidadeAcoes.MENSAGEMCONTA, ConsumoAnormalidadeAcoes.MENSAGEMCONTASEGMES,
		ConsumoAnormalidadeAcoes.MENSAGEMCONTATERMES, ConsumoAnormalidadeAcoes.CDMESCONSECUTIVOS,  
		ConsumoAnormalidadeAcoes.ULTIMAALTERACAO,
		ConsumoAnormalidadeAcoes.ICGERACAOCARTAMES1,
		ConsumoAnormalidadeAcoes.ICGERACAOCARTAMES2,
		ConsumoAnormalidadeAcoes.ICGERACAOCARTAMES3,
		ConsumoAnormalidadeAcoes.ICCOBRANCACONSUMONORMAL
	};
	
	public String[] getColunas(){
		return colunas;
	}	
	
	public String getNomeTabela(){
		return "consumo_anormalidade_acao";
	}
	
	public static final class ConsumoAnormalidadeAcoes implements BaseColumns {
		public static final String ID = "CSAA_ID";
		public static final String CONSUMOANORMALIDADE = "CSAN_ID";
		public static final String IDCATEGORIA = "CATG_ID";
		public static final String IDPERFIL = "IPER_ID";
		public static final String IDLEITURAANORMCONSUMO = "LACS_ID";
		public static final String IDLEITURAANORMCONSUMOSEGMES = "LACS_IDMES2";
		public static final String IDLEITURAANORMCONSUMOTERMES = "LACS_IDMES3";
		public static final String FATORCONSUMO = "CSAA_NNFATORCONSUMO";
		public static final String FATORCONSUMOSEGMES = "CSAA_NNFATORCONSUMOMES2";
		public static final String FATORCONSUMOTERMES = "CSAA_NNFATORCONSUMOMES3";
		public static final String MENSAGEMCONTA = "CSAA_DSMENSAGEMCONTA";
		public static final String MENSAGEMCONTASEGMES = "CSAA_DSMENSAGEMCONTAMES2";
		public static final String MENSAGEMCONTATERMES = "CSAA_DSMENSAGEMCONTAMES3";
		public static final String CDMESCONSECUTIVOS = "CSAA_CDMESESCONSECUTIVOS";
		public static final String ULTIMAALTERACAO = "CSAA_TMULTIMAALTERACAO";
		public static final String ICGERACAOCARTAMES1 = "CSAA_ICGERACAOCARTAMES1";
		public static final String ICGERACAOCARTAMES2 = "CSAA_ICGERACAOCARTAMES2";
		public static final String ICGERACAOCARTAMES3 = "CSAA_ICGERACAOCARTAMES3";
		public static final String ICCOBRANCACONSUMONORMAL = "CSAA_ICCOBRCONSUMONORMAL";
	}
	
	private void insertFromFile(ArrayList<String> obj){
		
		ConsumoAnormalidade consumoAnormalidade = new ConsumoAnormalidade();
		consumoAnormalidade.setIdString(obj.get(1));
		setConsumoAnormalidade(consumoAnormalidade);
		
		if(obj.get(2).length()!=0){	
		setIdCategoria(Integer.parseInt(obj.get(2)));	
		}
		if(obj.get(3).length()!=0){	
		setIdPerfil(Integer.parseInt(obj.get(3)));	
		}
		if(obj.get(4).length()!=0){	
		setIdLeituraAnormalidadeConsumo(Integer.parseInt(obj.get(4)));
		}
		if(obj.get(5).length()!=0){	
		setIdLeituraAnormalidadeConsumoSegundoMes(Integer.parseInt(obj.get(5)));
		}
		if(obj.get(6).length()!=0){	
		setIdLeituraAnormalidadeConsumoTerceiroMes(Integer.parseInt(obj.get(6)));
		}
		if(obj.get(7).length()!=0){	
		BigDecimal fato = new BigDecimal(obj.get(7));
		setFatorConsumo(fato);
		}
		if(obj.get(8).length()!=0){	
		BigDecimal fatoSegundoMes = new BigDecimal(obj.get(8));
		setFatorConsumoSegundoMes(fatoSegundoMes);
		}
		if(obj.get(9).length()!=0){	
		BigDecimal fatoTerceiroMes = new BigDecimal(obj.get(9));
		setFatorConsumoTerceiroMes(fatoTerceiroMes);
		
		}
		setMensagemConta(obj.get(10));
		setMensagemContaSegundoMes(obj.get(11));
		setMensagemContaTerceiroMes(obj.get(12));
		String date = Util.convertDateToDateStr(Util.getCurrentDateTime());	
		setUltimaAlteracao(date);
		
		if(obj.get(13).length()!=0){	
			setCodigoMesConsecutivo(Integer.parseInt(obj.get(13)));	
		}

		setIndicadorGeracaoCartaMes1(Short.parseShort(obj.get(14)));
		setIndicadorGeracaoCartaMes2(Short.parseShort(obj.get(15)));
		setIndicadorGeracaoCartaMes3(Short.parseShort(obj.get(16)));
		setIndicadorCobrancaConsumoNormal(Short.parseShort(obj.get(17)));
	}
	
	public final class ConsumoAnormalidadeAcoesTipos {
		public final String ID = " INTEGER PRIMARY KEY AUTOINCREMENT";
		public final String CONSUMOANORMALIDADE = " INTEGER NOT NULL";
		public final String IDCATEGORIA = " INTEGER  NULL";
		public final String IDPERFIL = " INTEGER  NULL";
		public final String IDLEITURAANORMCONSUMO = " INTEGER  NULL";
		public final String IDLEITURAANORMCONSUMOSEGMES = " INTEGER  NULL";
		public final String IDLEITURAANORMCONSUMOTERMES = " INTEGER  NULL";
		public final String FATORCONSUMO = " NUMERIC(5,2) NULL";
		public final String FATORCONSUMOSEGMES = " NUMERIC(5,2) NULL";
		public final String FATORCONSUMOTERMES = " NUMERIC(5,2) NULL";
		public final String MENSAGEMCONTA = " VARCHAR(120) NULL";
		public final String MENSAGEMCONTASEGMES = " VARCHAR(120) NULL";
		public final String MENSAGEMCONTATERMES = " VARCHAR(120) NULL";
		public final String CDMESCONSECUTIVOS = " INTEGER NULL";
		public final String ULTIMAALTERACAO = " TIMESTAMP NOT NULL";
		public final String ICGERACAOCARTAMES1 = " INTEGER NOT NULL";
		public final String ICGERACAOCARTAMES2 = " INTEGER NOT NULL";
		public final String ICGERACAOCARTAMES3 = " INTEGER NOT NULL";
		public final String ICCOBRANCACONSUMONORMAL = " INTEGER NOT NULL";
		
		private String[] tipos = new String[] {ID, CONSUMOANORMALIDADE,
			IDCATEGORIA, IDPERFIL,
			IDLEITURAANORMCONSUMO,IDLEITURAANORMCONSUMOSEGMES,
			IDLEITURAANORMCONSUMOTERMES, FATORCONSUMO,
			FATORCONSUMOSEGMES, FATORCONSUMOTERMES,
			MENSAGEMCONTA, MENSAGEMCONTASEGMES,
			MENSAGEMCONTATERMES, CDMESCONSECUTIVOS,  
			ULTIMAALTERACAO,
			ICGERACAOCARTAMES1, ICGERACAOCARTAMES2,
			ICGERACAOCARTAMES3, ICCOBRANCACONSUMONORMAL};	
		
		public String[] getTipos(){
			return tipos;
		}
	}

	public ContentValues preencherValues() {
		ContentValues values = new ContentValues();

		values.put(ConsumoAnormalidadeAcoes.CONSUMOANORMALIDADE, getConsumoAnormalidade().getId());
		values.put(ConsumoAnormalidadeAcoes.FATORCONSUMO, getFatorConsumo().toString());
		values.put(ConsumoAnormalidadeAcoes.FATORCONSUMOSEGMES, getFatorConsumoSegundoMes().toString());
		values.put(ConsumoAnormalidadeAcoes.FATORCONSUMOTERMES, getFatorConsumoTerceiroMes().toString());
		values.put(ConsumoAnormalidadeAcoes.IDLEITURAANORMCONSUMO, getIdLeituraAnormalidadeConsumo());
		values.put(ConsumoAnormalidadeAcoes.IDLEITURAANORMCONSUMOSEGMES, getIdLeituraAnormalidadeConsumoSegundoMes());
		values.put(ConsumoAnormalidadeAcoes.IDLEITURAANORMCONSUMOTERMES, getIdLeituraAnormalidadeConsumoTerceiroMes());
		values.put(ConsumoAnormalidadeAcoes.IDPERFIL, getIdPerfil());
		values.put(ConsumoAnormalidadeAcoes.IDCATEGORIA, getIdCategoria());
		values.put(ConsumoAnormalidadeAcoes.MENSAGEMCONTA, getMensagemConta());
		values.put(ConsumoAnormalidadeAcoes.MENSAGEMCONTASEGMES, getMensagemContaSegundoMes());
		values.put(ConsumoAnormalidadeAcoes.MENSAGEMCONTATERMES, getMensagemContaTerceiroMes());
		values.put(ConsumoAnormalidadeAcoes.CDMESCONSECUTIVOS, getCodigoMesConsecutivo());

		String dataStr = Util.convertDateToDateStr(Util.getCurrentDateTime());
		values.put(ConsumoAnormalidadeAcoes.ULTIMAALTERACAO, dataStr);

		values.put(ConsumoAnormalidadeAcoes.ICGERACAOCARTAMES1, getIndicadorGeracaoCartaMes1());
		values.put(ConsumoAnormalidadeAcoes.ICGERACAOCARTAMES2, getIndicadorGeracaoCartaMes2());
		values.put(ConsumoAnormalidadeAcoes.ICGERACAOCARTAMES3, getIndicadorGeracaoCartaMes3());
		values.put(ConsumoAnormalidadeAcoes.ICCOBRANCACONSUMONORMAL, getIndicadorCobrancaConsumoNormal());

		return values;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ConsumoAnormalidadeAcao> preencherObjetos(Cursor cursor) {		
		int codigo = cursor.getColumnIndex(ConsumoAnormalidadeAcoes.ID);
		int consumoAnormalidade = cursor.getColumnIndex(ConsumoAnormalidadeAcoes.CONSUMOANORMALIDADE);
		int fatorConsumoPrimeMes = cursor.getColumnIndex(ConsumoAnormalidadeAcoes.FATORCONSUMO);
		int fatorConsumoSegMes = cursor.getColumnIndex(ConsumoAnormalidadeAcoes.FATORCONSUMOSEGMES);
		int fatorConsumoTercMes = cursor.getColumnIndex(ConsumoAnormalidadeAcoes.FATORCONSUMOTERMES);
		int idCategoria = cursor.getColumnIndex(ConsumoAnormalidadeAcoes.IDCATEGORIA);
		int leituraAnormConsPrimMes = cursor.getColumnIndex(ConsumoAnormalidadeAcoes.IDLEITURAANORMCONSUMO);
		int leituraAnormConsSegMes = cursor.getColumnIndex(ConsumoAnormalidadeAcoes.IDLEITURAANORMCONSUMOSEGMES);
		int leituraAnormConsTercMes = cursor.getColumnIndex(ConsumoAnormalidadeAcoes.IDLEITURAANORMCONSUMOTERMES);
		int idPerfil = cursor.getColumnIndex(ConsumoAnormalidadeAcoes.IDPERFIL);
		int mensagemContaPrimMes = cursor.getColumnIndex(ConsumoAnormalidadeAcoes.MENSAGEMCONTA);
		int mensagemContaSegMes = cursor.getColumnIndex(ConsumoAnormalidadeAcoes.MENSAGEMCONTASEGMES);
		int mensagemContaTercMes = cursor.getColumnIndex(ConsumoAnormalidadeAcoes.MENSAGEMCONTATERMES);
		int ultimaAlteracao = cursor.getColumnIndex(ConsumoAnormalidadeAcoes.ULTIMAALTERACAO);
		int cdMesConsecutivo = cursor.getColumnIndex(ConsumoAnormalidadeAcoes.CDMESCONSECUTIVOS);
		int icGeracaoCartaMes1 = cursor.getColumnIndex(ConsumoAnormalidadeAcoes.ICGERACAOCARTAMES1);
		int icGeracaoCartaMes2 = cursor.getColumnIndex(ConsumoAnormalidadeAcoes.ICGERACAOCARTAMES2);
		int icGeracaoCartaMes3 = cursor.getColumnIndex(ConsumoAnormalidadeAcoes.ICGERACAOCARTAMES3);
		int icCobrancaoConsumoNormal = cursor.getColumnIndex(ConsumoAnormalidadeAcoes.ICCOBRANCACONSUMONORMAL);

		ArrayList<ConsumoAnormalidadeAcao> consumoAnormalidadeAcoes = new ArrayList<ConsumoAnormalidadeAcao>();
		do {
			ConsumoAnormalidadeAcao consumoAnormalidadeAcao = new ConsumoAnormalidadeAcao();
			if(Util.getIntBanco(cursor, ConsumoAnormalidadeAcoes.CONSUMOANORMALIDADE, consumoAnormalidade) !=  null){
				ConsumoAnormalidade objConsAnormalidade;
				try {
					objConsAnormalidade = (ConsumoAnormalidade)RepositorioBasico.getInstance()
							.pesquisarPorId(cursor.getInt(consumoAnormalidade), new ConsumoAnormalidade());
					consumoAnormalidadeAcao.setConsumoAnormalidade(objConsAnormalidade);
				} catch (RepositorioException e) {
					Log.e(ConstantesSistema.CATEGORIA, e.getMessage());
					e.printStackTrace();
				}
			}
			consumoAnormalidadeAcao.setId(cursor.getInt(codigo));
			consumoAnormalidadeAcao.setFatorConsumo(Util.getDoubleBanco(cursor, ConsumoAnormalidadeAcoes.FATORCONSUMO, fatorConsumoPrimeMes));
			consumoAnormalidadeAcao.setFatorConsumoSegundoMes(Util.getDoubleBanco(cursor, ConsumoAnormalidadeAcoes.FATORCONSUMOSEGMES, fatorConsumoSegMes));
			consumoAnormalidadeAcao.setFatorConsumoTerceiroMes(Util.getDoubleBanco(cursor, ConsumoAnormalidadeAcoes.FATORCONSUMOTERMES, fatorConsumoTercMes));
			consumoAnormalidadeAcao.setIdCategoria(Util.getIntBanco(cursor, ConsumoAnormalidadeAcoes.IDCATEGORIA, idCategoria));
			consumoAnormalidadeAcao.setIdLeituraAnormalidadeConsumo(Util.getIntBanco(cursor, ConsumoAnormalidadeAcoes.IDLEITURAANORMCONSUMO, leituraAnormConsPrimMes));
			consumoAnormalidadeAcao.setIdLeituraAnormalidadeConsumoSegundoMes(Util.getIntBanco(cursor, ConsumoAnormalidadeAcoes.IDLEITURAANORMCONSUMOSEGMES, leituraAnormConsSegMes));
			consumoAnormalidadeAcao.setIdLeituraAnormalidadeConsumoTerceiroMes(Util.getIntBanco(cursor, ConsumoAnormalidadeAcoes.IDLEITURAANORMCONSUMOTERMES, leituraAnormConsTercMes));
			consumoAnormalidadeAcao.setIdPerfil(Util.getIntBanco(cursor, ConsumoAnormalidadeAcoes.IDPERFIL, idPerfil));
			consumoAnormalidadeAcao.setMensagemConta(cursor.getString(mensagemContaPrimMes));
			consumoAnormalidadeAcao.setMensagemContaSegundoMes(cursor.getString(mensagemContaSegMes));
			consumoAnormalidadeAcao.setMensagemContaTerceiroMes(cursor.getString(mensagemContaTercMes));
			consumoAnormalidadeAcao.setCodigoMesConsecutivo(Util.getIntBanco(cursor, ConsumoAnormalidadeAcoes.CDMESCONSECUTIVOS, cdMesConsecutivo));
			consumoAnormalidadeAcao.setUltimaAlteracao(cursor.getString(ultimaAlteracao));
			consumoAnormalidadeAcao.setIndicadorGeracaoCartaMes1(cursor.getShort(icGeracaoCartaMes1));
			consumoAnormalidadeAcao.setIndicadorGeracaoCartaMes2(cursor.getShort(icGeracaoCartaMes2));
			consumoAnormalidadeAcao.setIndicadorGeracaoCartaMes3(cursor.getShort(icGeracaoCartaMes3));
			consumoAnormalidadeAcao.setIndicadorCobrancaConsumoNormal(cursor.getShort(icCobrancaoConsumoNormal));
			
			consumoAnormalidadeAcoes.add(consumoAnormalidadeAcao);
			
		} while (cursor.moveToNext());

		return consumoAnormalidadeAcoes;
	}
}
