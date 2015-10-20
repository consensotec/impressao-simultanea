
package com.br.ipad.isc.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import com.br.ipad.isc.util.Util;

/**
 * [] Classe Básica - Categoria Subcategoria
 * 
 * @author Fernanda Almeida
 * @since 08/03/2012
 */
public class ConsumoAnteriores extends ObjetoBasico implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//Construtor default
	public ConsumoAnteriores(){}
	
	public ConsumoAnteriores(ArrayList<String> obj){
		insertFromFile(obj);
	}
		
	private Integer id;
	private ImovelConta matricula;
	private Integer anoMesReferencia;
	private Integer tipoLigacao;
	private Integer consumo;
	private Integer anormalidadeLeitura;
	private Integer anormalidadeConsumo;
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
	public void setIdString(String id) {
		this.id = Util.verificarNuloInt(id);
	}
	
	public Integer getTipoLigacao() {
		return tipoLigacao;
	}
	public void setTipoLigacao(Integer tipoLigacao) {
		this.tipoLigacao = tipoLigacao;
	}
	public Integer getConsumo() {
		return consumo;
	}
	public void setConsumo(Integer consumo) {
		this.consumo = consumo;
	}
	public Integer getAnormalidadeLeitura() {
		return anormalidadeLeitura;
	}
	public void setAnormalidadeLeitura(Integer anormalidadeLeitura) {
		this.anormalidadeLeitura = anormalidadeLeitura;
	}
	public Integer getAnormalidadeConsumo() {
		return anormalidadeConsumo;
	}
	public void setAnormalidadeConsumo(Integer anormalidadeConsumo) {
		this.anormalidadeConsumo = anormalidadeConsumo;
	}
	
	public Integer getAnoMesReferencia() {
		return anoMesReferencia;
	}
	public void setAnoMesReferencia(Integer anoMesReferencia) {
		this.anoMesReferencia = anoMesReferencia;
	}
	public Date getUltimaAlteracao() {
		return ultimaAlteracao;
	}
	public void setUltimaAlteracao(String ultimaAlteracao) {
		this.ultimaAlteracao = Util.getData(ultimaAlteracao);
	}
	
	private static String[] colunas = new String[] { ConsumosAnteriores.ID,ConsumosAnteriores.MATRICULA, ConsumosAnteriores.TIPOLIGACAO, ConsumosAnteriores.ANOMESREFERENCIA, 
                                             ConsumosAnteriores.CONSUMOFATURADO, ConsumosAnteriores.IDANORMALIDADELEITURA, 
                                             ConsumosAnteriores.IDANORMALIDADECONSUMO, ConsumosAnteriores.ULTIMAALTERACAO
	};
	
	public String[] getColunas(){
		return colunas;
	}	
	
	public static final class ConsumosAnteriores implements BaseColumns {
		public static final String ID = "CSAT_ID";
		public static final String MATRICULA = "IMOV_ID";
		public static final String TIPOLIGACAO = "LGTI_ID";
		public static final String ANOMESREFERENCIA   = "CSAT_AMREFERENCIA";
		public static final String CONSUMOFATURADO  = "CSAT_CONSUMOFATURADO";
		public static final String IDANORMALIDADELEITURA   = "CSAT_IDANORLEITURA";
		public static final String IDANORMALIDADECONSUMO   = "CSAT_IDANORMCONSUMO";
		public static final String ULTIMAALTERACAO  = "CSAT_TMULTIMAALTERACAO";
	}
	
	public final class ConsumosAnterioresTipos {
		public final String ID = " INTEGER PRIMARY KEY AUTOINCREMENT ";	
		public final String MATRICULA = " CONSTRAINT [FK1_CONSUMO_ANTERIORES] REFERENCES [imovel_conta]([IMOV_ID]) ON DELETE RESTRICT ON UPDATE RESTRICT ";
		public final String TIPOLIGACAO = " INTEGER  NOT NULL ";
		public final String ANOMESREFERENCIA   = " INTEGER  NOT NULL ";
		public final String CONSUMOFATURADO  = " INTEGER  NOT NULL ";
		public final String IDANORMALIDADELEITURA   = " INTEGER  NOT NULL ";
		public final String IDANORMALIDADECONSUMO   = " INTEGER  NOT NULL ";
		public final String ULTIMAALTERACAO = " TIMESTAMP NOT NULL ";
			 
		private String[] tipos = new String[] {
				ID, MATRICULA, TIPOLIGACAO, ANOMESREFERENCIA, CONSUMOFATURADO,
				IDANORMALIDADELEITURA, IDANORMALIDADECONSUMO, ULTIMAALTERACAO};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
	
	private void insertFromFile(ArrayList<String> obj){
		
		ImovelConta matricula = new ImovelConta();		
		matricula.setId(Integer.parseInt(obj.get(1)));
		setMatricula(matricula);	
		
		setTipoLigacao(Integer.parseInt(obj.get(2)));	
		setAnoMesReferencia(Integer.parseInt(obj.get(3)));	
		setConsumo(Integer.parseInt(obj.get(4)));	
		setAnormalidadeLeitura(Integer.parseInt(obj.get(5)));
		setAnormalidadeConsumo(Integer.parseInt(obj.get(6)));
		String date = Util.convertDateToDateStr(Util.getCurrentDateTime());	
		setUltimaAlteracao(date);	
	}
	
	public String getNomeTabela(){
		return "consumo_anteriores";
	}
	
	public ContentValues preencherValues() {
		ContentValues values = new ContentValues();
				
		values.put(ConsumosAnteriores.ANOMESREFERENCIA, getAnoMesReferencia());
		values.put(ConsumosAnteriores.MATRICULA, getMatricula().getId());
		values.put(ConsumosAnteriores.CONSUMOFATURADO, getConsumo());
		values.put(ConsumosAnteriores.IDANORMALIDADECONSUMO, getAnormalidadeConsumo());
		values.put(ConsumosAnteriores.IDANORMALIDADELEITURA, getAnormalidadeLeitura());
		values.put(ConsumosAnteriores.TIPOLIGACAO, getTipoLigacao());
		String dataStr = Util.convertDateToDateStr(Util.getCurrentDateTime());
		values.put(ConsumosAnteriores.ULTIMAALTERACAO, dataStr);
		
		return values;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ConsumoAnteriores> preencherObjetos(Cursor cursor) {		
		ArrayList<ConsumoAnteriores> retorno = null;
		
		if (cursor.moveToFirst()) {	
			int codigo = cursor.getColumnIndex(ConsumosAnteriores.ID);
			int matricula = cursor.getColumnIndex(ConsumosAnteriores.MATRICULA);
			int amReferencia = cursor.getColumnIndex(ConsumosAnteriores.ANOMESREFERENCIA);
			int consumoFaturado = cursor.getColumnIndex(ConsumosAnteriores.CONSUMOFATURADO);
			int anormalidadeConsumo = cursor.getColumnIndex(ConsumosAnteriores.IDANORMALIDADECONSUMO);
			int anormalidadeLeitura = cursor.getColumnIndex(ConsumosAnteriores.IDANORMALIDADELEITURA);
			int tipoLigacao = cursor.getColumnIndex(ConsumosAnteriores.TIPOLIGACAO);
			int ultimaAlteracao = cursor.getColumnIndex(ConsumosAnteriores.ULTIMAALTERACAO);
			
			retorno = new ArrayList<ConsumoAnteriores>();			
			do{
				ConsumoAnteriores consumoAnterior = new ConsumoAnteriores();
				
				if(Util.getIntBanco(cursor, ConsumosAnteriores.MATRICULA, matricula) != null){
					ImovelConta imovelConta = new ImovelConta(cursor.getInt(matricula));
					consumoAnterior.setMatricula(imovelConta);
				}				
				consumoAnterior.setId(Util.getIntBanco(cursor, ConsumosAnteriores.ID, codigo));
				consumoAnterior.setAnoMesReferencia(Util.getIntBanco(cursor, ConsumosAnteriores.ANOMESREFERENCIA, amReferencia));
				consumoAnterior.setConsumo(Util.getIntBanco(cursor, ConsumosAnteriores.CONSUMOFATURADO, consumoFaturado));
				consumoAnterior.setAnormalidadeConsumo(Util.getIntBanco(cursor, ConsumosAnteriores.IDANORMALIDADECONSUMO, anormalidadeConsumo));
				consumoAnterior.setAnormalidadeLeitura(Util.getIntBanco(cursor, ConsumosAnteriores.IDANORMALIDADELEITURA, anormalidadeLeitura));
				consumoAnterior.setTipoLigacao(Util.getIntBanco(cursor, ConsumosAnteriores.TIPOLIGACAO, tipoLigacao));
				consumoAnterior.setUltimaAlteracao(cursor.getString(ultimaAlteracao));
				
				retorno.add(consumoAnterior);					
			} while (cursor.moveToNext());
		}		
		return retorno;
	}

}
