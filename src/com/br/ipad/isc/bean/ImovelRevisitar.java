
package com.br.ipad.isc.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import com.br.ipad.isc.bean.ConsumoAnteriores.ConsumosAnteriores;
import com.br.ipad.isc.util.Util;

/**
 * [] Classe Básica - Categoria Subcategoria
 * 
 * @author Fernanda Almeida
 * @since 08/03/2012
 */
public class ImovelRevisitar extends ObjetoBasico implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private ImovelConta matricula;
	private Integer indicadorRevisitado;
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
	
	public Integer getIndicadorRevisitado() {
		return indicadorRevisitado;
	}
	public void setIndicadorRevisitado(Integer indicadorRevisitado) {
		this.indicadorRevisitado = indicadorRevisitado;
	}
	
	public Date getUltimaAlteracao() {
		return ultimaAlteracao;
	}
	public void setUltimaAlteracao(String ultimaAlteracao) {
		this.ultimaAlteracao = Util.getData(ultimaAlteracao);
	}

	private static String[] colunas = new String[] { ImoveisRevisitar.ID,ImoveisRevisitar.MATRICULA
		,ImoveisRevisitar.INDICADORREVISITADO,ImoveisRevisitar.ULTIMAALTERACAO
	};
	
	public String[] getColunas(){
		return colunas;
	}	
	
	public String getNomeTabela(){
		return "imovel_revisitar";
	}
	
	public static final class ImoveisRevisitar implements BaseColumns {
		public static final String ID = "IMRE_ID";
		public static final String MATRICULA = "IMOV_ID";
		public static final String INDICADORREVISITADO = "IMRE_ICREVISITADO";
		public static final String ULTIMAALTERACAO = "IMRE_TMULTIMAALTERACAO";
	}
			
	public final class ImoveisRevisitarTipos {
		public final String ID = " INTEGER PRIMARY KEY AUTOINCREMENT ";
		public final String MATRICULA = "  INTEGER CONSTRAINT [FK1_IMOVEL_REVISITAR] REFERENCES [imovel_conta]([IMOV_ID]) ON DELETE RESTRICT ON UPDATE RESTRICT";
		public final String INDICADORREVISITADO = " INTEGER NULL DEFAULT 2 ";
		public final String ULTIMAALTERACAO = " TIMESTAMP NOT NULL ";
		
		private String[] tipos = new String[] { ID,MATRICULA
			,INDICADORREVISITADO,ULTIMAALTERACAO};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
	
	public ContentValues preencherValues() {
		ContentValues values = new ContentValues();
		
		values.put(ImoveisRevisitar.MATRICULA, getMatricula().getId());
		values.put(ImoveisRevisitar.INDICADORREVISITADO, getIndicadorRevisitado());
		String dataStr = Util.convertDateToDateStr(Util.getCurrentDateTime());
		values.put(ImoveisRevisitar.ULTIMAALTERACAO, dataStr);
		
		return values;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ImovelRevisitar> preencherObjetos(Cursor cursor) {		
			
		int codigo = cursor.getColumnIndex(ImoveisRevisitar.ID);
		int matricula = cursor.getColumnIndex(ImoveisRevisitar.MATRICULA);
		int indicadorRevisitado = cursor.getColumnIndex(ImoveisRevisitar.INDICADORREVISITADO);
		int ultimaAlteracao = cursor.getColumnIndex(ImoveisRevisitar.ULTIMAALTERACAO);
		
		ArrayList<ImovelRevisitar> imoveisRevisitar = new ArrayList<ImovelRevisitar>();
		
		do{
			ImovelRevisitar imovelRevisitar = new ImovelRevisitar();
			if(Util.getIntBanco(cursor, ImoveisRevisitar.MATRICULA, matricula) != null){
				ImovelConta imovelConta = new ImovelConta(Util.getIntBanco(cursor, ConsumosAnteriores.MATRICULA, matricula));
				imovelRevisitar.setMatricula(imovelConta);
			}
			
			imovelRevisitar.setId(cursor.getInt(codigo));
			imovelRevisitar.setIndicadorRevisitado(cursor.getInt(indicadorRevisitado));
			imovelRevisitar.setUltimaAlteracao(cursor.getString(ultimaAlteracao));
			
			imoveisRevisitar.add(imovelRevisitar);
			
			
		} while (cursor.moveToNext());
		
		return imoveisRevisitar;
	}

}
