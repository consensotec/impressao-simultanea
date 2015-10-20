package com.br.ipad.isc.bean;

import java.io.Serializable;
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
 * [] Classe Básica - Consumo Histórico
 * 
 * @author Fernanda Almeida
 * @since 08/03/2012
 */
public class ConsumoHistorico extends ObjetoBasico implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private ImovelConta matricula;
	private Integer tipoLigacao;
	private Integer consumoMedidoMes;
	private Integer consumoCobradoMes;
	private Integer consumoCobradoMesImoveisMicro;
	private Integer consumoRateio;
	private Integer consumoCobradoSemContratoDemanda;
	private Integer consumoCobradoMesOriginal;
	private Integer leituraAtual;
	private Integer tipoConsumo;
	private ConsumoAnormalidade consumoAnormalidade;
	private Integer diasConsumo;
	private LeituraAnormalidade anormalidadeLeituraFaturada; 
	private Date ultimaAlteracao;
	
	/* Construtor Vazio */
	public ConsumoHistorico() {
		super();
		consumoMedidoMes = 0;
		consumoCobradoMes = 0;
		consumoCobradoMesImoveisMicro = 0;
		consumoRateio = 0;
		consumoCobradoSemContratoDemanda = 0;
		consumoCobradoMesOriginal = 0;
		leituraAtual = 0;
		tipoConsumo = 0;
	}
	
	/* Construtor Mínimo */
	public ConsumoHistorico(Integer consumoCobradoMes, Integer tipoConsumo) {
		super();
		this.consumoCobradoMes = consumoCobradoMes;
		this.tipoConsumo = tipoConsumo;
	}
	
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
	
	public void setIdString(String id) {
		this.id = Util.verificarNuloInt(id);
	}
	
	public Integer getTipoLigacao() {
		return tipoLigacao;
	}
	public void setTipoLigacao(Integer tipoLigacao) {
		this.tipoLigacao = tipoLigacao;
	}
	public Integer getConsumoMedidoMes() {
		return consumoMedidoMes;
	}
	public void setConsumoMedidoMes(Integer consumoMedidoMes) {
		this.consumoMedidoMes = consumoMedidoMes;
	}
	public Integer getConsumoCobradoMes() {
		return consumoCobradoMes;
	}
	public void setConsumoCobradoMes(Integer consumoCobradoMes) {
		this.consumoCobradoMes = consumoCobradoMes;
	}
	public Integer getConsumoCobradoMesImoveisMicro() {
		return consumoCobradoMesImoveisMicro;
	}
	public void setConsumoCobradoMesImoveisMicro(Integer consumoCobradoMesImoveisMicro) {
		this.consumoCobradoMesImoveisMicro = consumoCobradoMesImoveisMicro;
	}
	public Integer getConsumoRateio() {
		return consumoRateio;
	}
	public void setConsumoRateio(Integer consumoRateio) {
		this.consumoRateio = consumoRateio;
	}
	public Integer getConsumoCobradoSemContratoDemanda() {
		return consumoCobradoSemContratoDemanda;
	}
	public void setConsumoCobradoSemContratoDemanda(Integer consumoCobradoSemContratoDemanda) {
		this.consumoCobradoSemContratoDemanda = consumoCobradoSemContratoDemanda;
	}
	public Integer getConsumoCobradoMesOriginal() {
		return consumoCobradoMesOriginal;
	}
	public void setConsumoCobradoMesOriginal(Integer consumoCobradoMesOriginal) {
		this.consumoCobradoMesOriginal = consumoCobradoMesOriginal;
	}
	public Integer getLeituraAtual() {
		return leituraAtual;
	}
	public void setLeituraAtual(Integer leituraAtual) {
		this.leituraAtual = leituraAtual;
	}
	public Integer getTipoConsumo() {
		return tipoConsumo;
	}
	public void setTipoConsumo(Integer tipoConsumo) {
		this.tipoConsumo = tipoConsumo;
	}
	public ConsumoAnormalidade getConsumoAnormalidade() {
		return consumoAnormalidade;
	}
	public void setConsumoAnormalidade(ConsumoAnormalidade consumoAnormalidade) {
		this.consumoAnormalidade = consumoAnormalidade;
	}
	public Integer getDiasConsumo() {
		return diasConsumo;
	}
	public void setDiasConsumo(Integer diasConsumo) {
		this.diasConsumo = diasConsumo;
	}
	public LeituraAnormalidade getAnormalidadeLeituraFaturada() {
		return anormalidadeLeituraFaturada;
	}
	public void setAnormalidadeLeituraFaturada(LeituraAnormalidade anormalidadeLeituraFaturada) {
		this.anormalidadeLeituraFaturada = anormalidadeLeituraFaturada;
	}
	public Date getUltimaAlteracao() {
		return ultimaAlteracao;
	}
	public void setUltimaAlteracao(Long ultimaAlteracao) {
		this.ultimaAlteracao = new Date(ultimaAlteracao);
	}
	public void setUltimaAlteracao(Date ultimaAlteracao) {
		this.ultimaAlteracao = ultimaAlteracao;
	}
	private static String[] colunas = new String[] { ConsumosHistoricos.ID, ConsumosHistoricos.MATRICULA, 
	                                       ConsumosHistoricos.TIPOLIGACAO, ConsumosHistoricos.CONSUMOMEDIDO,  ConsumosHistoricos.COSNUMOCOBRADO,
	                                       ConsumosHistoricos.CONSUMOCOBRADOMICRO, ConsumosHistoricos.CONSUMORATEIO,  ConsumosHistoricos.CONSUMOCOBSEMCONTRATODEMANDA,
	                                       ConsumosHistoricos.CONSUMOCOBRADOORIGINAL, ConsumosHistoricos.LEITURAATUAL,  ConsumosHistoricos.TIPOCONSUMO,
	                                       ConsumosHistoricos.ANORMALIDADECONSUMO, ConsumosHistoricos.DIASCONSUMO,  ConsumosHistoricos.ANORMLEITURAFATURADA,
	                                       ConsumosHistoricos.ULTIMAALTERACAO	                                       
	};
	
	public String[] getColunas(){
		return colunas;
	}	
	
	public static final class ConsumosHistoricos implements BaseColumns {
		public static final String ID = "CSHI_ID";
		public static final String MATRICULA = "IMOV_ID";
		public static final String TIPOLIGACAO = "LGTI_ID";
		public static final String CONSUMOMEDIDO = "CSHI_NNCONSUMOMEDIDO";
		public static final String COSNUMOCOBRADO = "CSHI_NNCONSUMOCOBRADO";
		public static final String CONSUMOCOBRADOMICRO = "CSHI_NNCONSUMOCOBMICRO";
		public static final String CONSUMORATEIO = "CSHI_NNCONSUMORATEIO";
		public static final String CONSUMOCOBSEMCONTRATODEMANDA = "CSHI_NNCONCOBSEMCTDEMAN";
		public static final String CONSUMOCOBRADOORIGINAL = "CSHI_NNCONSCOBORIGINAL";
		public static final String LEITURAATUAL = "CSHI_NNLEITURAATUAL";
		public static final String TIPOCONSUMO = "CSTP_ID";
		public static final String ANORMALIDADECONSUMO = "CSAN_ID";
		public static final String DIASCONSUMO = "CSHI_NNDIASCONSUMO";
		public static final String ANORMLEITURAFATURADA = "LTAN_ID";
		public static final String ULTIMAALTERACAO = "CSHI_TMULTIMAALTERACAO";
	}

	public String getNomeTabela(){
		return "consumo_historico";
	}
	
	public final class ConsumosHistoricosTipos {
		public final String ID = " INTEGER PRIMARY KEY AUTOINCREMENT";
		public final String MATRICULA = " INTEGER CONSTRAINT [FK1_CONSUMO_HISTORICO] REFERENCES [imovel_conta]([IMOV_ID]) ON DELETE RESTRICT ON UPDATE RESTRICT";
		public final String TIPOLIGACAO = " INTEGER NOT NULL";
		public final String CONSUMOMEDIDO = " INTEGER NULL";
		public final String COSNUMOCOBRADO = " INTEGER NOT NULL";
		public final String CONSUMOCOBRADOMICRO = " INTEGER NULL";
		public final String CONSUMORATEIO = " INTEGER NULL";
		public final String CONSUMOCOBSEMCONTRATODEMANDA = " INTEGER NULL";
		public final String CONSUMOCOBRADOORIGINAL = " INTEGER NOT NULL";
		public final String LEITURAATUAL = " INTEGER NULL";
		public final String TIPOCONSUMO = " INTEGER NOT NULL";
		public final String ANORMALIDADECONSUMO = " INTEGER CONSTRAINT [FK2_CONSUMO_HISTORICO] REFERENCES [consumo_anormalidade]([CSAN_ID]) ON DELETE RESTRICT ON UPDATE RESTRICT";
		public final String DIASCONSUMO = " INTEGER NULL";
		public final String ANORMLEITURAFATURADA = " CONSTRAINT [FK2_CONSUMO_HISTORICO] REFERENCES [leitura_anormalidade]([LTAN_ID]) ON DELETE RESTRICT ON UPDATE RESTRICT";
		public final String ULTIMAALTERACAO = " INTEGER NOT NULL";
		
		private String[] tipos = new String[] {ID, MATRICULA, 
	        TIPOLIGACAO, CONSUMOMEDIDO,  COSNUMOCOBRADO,
	        CONSUMOCOBRADOMICRO, CONSUMORATEIO,  CONSUMOCOBSEMCONTRATODEMANDA,
	        CONSUMOCOBRADOORIGINAL, LEITURAATUAL,  TIPOCONSUMO,
	        ANORMALIDADECONSUMO, DIASCONSUMO,  ANORMLEITURAFATURADA,
	        ULTIMAALTERACAO};	

		public String[] getTipos(){
			return tipos;
		}
	}

	public ContentValues preencherValues() {
		ContentValues values = new ContentValues();
		
		values.put(ConsumosHistoricos.MATRICULA, getMatricula().getId());
		values.put(ConsumosHistoricos.TIPOLIGACAO, getTipoLigacao());
		values.put(ConsumosHistoricos.CONSUMOMEDIDO, getConsumoMedidoMes());
		values.put(ConsumosHistoricos.COSNUMOCOBRADO, getConsumoCobradoMes());	
		values.put(ConsumosHistoricos.CONSUMOCOBRADOMICRO, getConsumoCobradoMesImoveisMicro());
		values.put(ConsumosHistoricos.CONSUMORATEIO, getConsumoRateio());	
		values.put(ConsumosHistoricos.CONSUMOCOBSEMCONTRATODEMANDA, getConsumoCobradoSemContratoDemanda());	
		values.put(ConsumosHistoricos.CONSUMOCOBRADOORIGINAL, getConsumoCobradoMesOriginal());	
		values.put(ConsumosHistoricos.LEITURAATUAL, getLeituraAtual());	
		values.put(ConsumosHistoricos.TIPOCONSUMO, getTipoConsumo());	
		if (getConsumoAnormalidade()!=null) {
			values.put(ConsumosHistoricos.ANORMALIDADECONSUMO, getConsumoAnormalidade().getId());
		}
		values.put(ConsumosHistoricos.DIASCONSUMO, getDiasConsumo());
		if (getAnormalidadeLeituraFaturada()!=null) {
			values.put(ConsumosHistoricos.ANORMLEITURAFATURADA, getAnormalidadeLeituraFaturada().getId());
		}
		values.put(ConsumosHistoricos.ULTIMAALTERACAO, (new Date()).getTime());
		return values;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ConsumoHistorico> preencherObjetos(Cursor cursor) {		
			
		int codigo = cursor.getColumnIndex(ConsumosHistoricos.ID);
		int anormCons = cursor.getColumnIndex(ConsumosHistoricos.ANORMALIDADECONSUMO);
		int anormLeitFaturada = cursor.getColumnIndex(ConsumosHistoricos.ANORMLEITURAFATURADA);
		int consCobradoMicro = cursor.getColumnIndex(ConsumosHistoricos.CONSUMOCOBRADOMICRO);
		int consCobradoOriginal = cursor.getColumnIndex(ConsumosHistoricos.CONSUMOCOBRADOORIGINAL);
		int consCobradoSemContrDemanda = cursor.getColumnIndex(ConsumosHistoricos.CONSUMOCOBSEMCONTRATODEMANDA);
		int consumoMedio = cursor.getColumnIndex(ConsumosHistoricos.CONSUMOMEDIDO);
		int consumoRateio = cursor.getColumnIndex(ConsumosHistoricos.CONSUMORATEIO);
		int consumoCobrado = cursor.getColumnIndex(ConsumosHistoricos.COSNUMOCOBRADO);
		int diasConsumo = cursor.getColumnIndex(ConsumosHistoricos.DIASCONSUMO);
		int leituraAtual = cursor.getColumnIndex(ConsumosHistoricos.LEITURAATUAL);
		int matricula = cursor.getColumnIndex(ConsumosHistoricos.MATRICULA);	
		int tipoConsumo = cursor.getColumnIndex(ConsumosHistoricos.TIPOCONSUMO);
		int tipoLigacao = cursor.getColumnIndex(ConsumosHistoricos.TIPOLIGACAO);				
		int ultimaAlteracao = cursor.getColumnIndex(ConsumosHistoricos.ULTIMAALTERACAO);
						
		ArrayList<ConsumoHistorico> consumosHistoricos = new ArrayList<ConsumoHistorico>();
		do {	
			ConsumoHistorico consumoHistorico = new ConsumoHistorico();
			
			try {
				ImovelConta objImovelConta = (ImovelConta)RepositorioBasico.getInstance()
				.pesquisarPorId(cursor.getInt(matricula), new ImovelConta());
				
				consumoHistorico.setMatricula(objImovelConta);
				consumoHistorico.setId(cursor.getInt(codigo));
				
				ConsumoAnormalidade consumoAnorm = (ConsumoAnormalidade)RepositorioBasico.getInstance()
						.pesquisarPorId(cursor.getInt(anormCons), new ConsumoAnormalidade());
				consumoHistorico.setConsumoAnormalidade(consumoAnorm);
				LeituraAnormalidade leitura = (LeituraAnormalidade)RepositorioBasico.getInstance()
						.pesquisarPorId(cursor.getInt(anormLeitFaturada), new LeituraAnormalidade());
				consumoHistorico.setAnormalidadeLeituraFaturada(leitura);
				
			} catch (RepositorioException e) {
				Log.e( ConstantesSistema.CATEGORIA , e.getMessage());
				e.printStackTrace();
			}
			
			consumoHistorico.setConsumoCobradoMesImoveisMicro(Util.getIntBanco(cursor, ConsumosHistoricos.CONSUMOCOBRADOMICRO, consCobradoMicro));
			consumoHistorico.setConsumoCobradoMesOriginal(Util.getIntBanco(cursor, ConsumosHistoricos.CONSUMOCOBRADOORIGINAL, consCobradoOriginal));
			consumoHistorico.setConsumoCobradoSemContratoDemanda(Util.getIntBanco(cursor, ConsumosHistoricos.CONSUMOCOBSEMCONTRATODEMANDA, consCobradoSemContrDemanda));
			consumoHistorico.setConsumoMedidoMes(Util.getIntBanco(cursor, ConsumosHistoricos.CONSUMOMEDIDO, consumoMedio));
			consumoHistorico.setConsumoRateio(Util.getIntBanco(cursor, ConsumosHistoricos.CONSUMORATEIO, consumoRateio));
			consumoHistorico.setConsumoCobradoMes(Util.getIntBanco(cursor, ConsumosHistoricos.COSNUMOCOBRADO, consumoCobrado));	
			consumoHistorico.setDiasConsumo(Util.getIntBanco(cursor, ConsumosHistoricos.DIASCONSUMO, diasConsumo));			
			consumoHistorico.setLeituraAtual(Util.getIntBanco(cursor, ConsumosHistoricos.LEITURAATUAL, leituraAtual));			
			consumoHistorico.setTipoConsumo(Util.getIntBanco(cursor, ConsumosHistoricos.TIPOCONSUMO, tipoConsumo));	
			consumoHistorico.setTipoLigacao(Util.getIntBanco(cursor, ConsumosHistoricos.TIPOLIGACAO, tipoLigacao));		
			consumoHistorico.setUltimaAlteracao(cursor.getLong(ultimaAlteracao));
	
			consumosHistoricos.add(consumoHistorico);
			
		} while (cursor.moveToNext());
		
		return consumosHistoricos;
	}


}
