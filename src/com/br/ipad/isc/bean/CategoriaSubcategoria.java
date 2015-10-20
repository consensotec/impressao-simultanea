
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
public class CategoriaSubcategoria extends ObjetoBasico implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private ImovelConta matricula;
	private Integer codigoCategoria;
	private String descricaoCategoria;
	private Integer codigoSubcategoria;
	private String descricaoSubcategoria;
	private Integer qtdEconomiasSubcategoria;
	private String descricaoAbreviadaCategoria;
	private String descricaoAbreviadaSubcategoria;
	private Integer fatorEconomiaCategoria;
	private Date ultimaAlteracao;
	
	public CategoriaSubcategoria() {
		super();
	}
	
	public CategoriaSubcategoria(Integer id) {
		super();
		this.id = id;
	}
	public CategoriaSubcategoria(ArrayList<String> obj) {
		insertFromFile(obj);
	}
	
	public Integer getCodigoCategoria() {
		return codigoCategoria;
	}
	public void setCodigoCategoria(Integer codigoCategoria) {
		this.codigoCategoria = codigoCategoria;
	}
	public ImovelConta getMatricula() {
		return matricula;
	}
	public void setMatricula(ImovelConta matricula) {
		this.matricula = matricula;
	}
	public Integer getCodigoSubcategoria() {
		return codigoSubcategoria;
	}
	public void setCodigoSubcategoria(Integer codigoSubcategoria) {
		this.codigoSubcategoria = codigoSubcategoria;
	}
	public Integer getQtdEconomiasSubcategoria() {
		return qtdEconomiasSubcategoria;
	}
	public void setQtdEconomiasSubcategoria(Integer qtdEconomiasSubcategoria) {
		this.qtdEconomiasSubcategoria = qtdEconomiasSubcategoria;
	}
	public Integer getFatorEconomiaCategoria() {
		return fatorEconomiaCategoria;
	}
	public void setFatorEconomiaCategoria(Integer fatorEconomiaCategoria) {
		this.fatorEconomiaCategoria = fatorEconomiaCategoria;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setIdString(String id) {
		this.id = Util.verificarNuloInt(id);
	}
	
	public String getDescricaoCategoria() {
		return descricaoCategoria;
	}
	public void setDescricaoCategoria(String descricaoCategoria) {
		this.descricaoCategoria = descricaoCategoria;
	}
	public String getDescricaoSubcategoria() {
		return descricaoSubcategoria;
	}
	public void setDescricaoSubcategoria(String descricaoSubcategoria) {
		this.descricaoSubcategoria = descricaoSubcategoria;
	}
	public String getDescricaoAbreviadaCategoria() {
		return descricaoAbreviadaCategoria;
	}
	public void setDescricaoAbreviadaCategoria(String descricaoAbreviadaCategoria) {
		this.descricaoAbreviadaCategoria = descricaoAbreviadaCategoria;
	}
	public String getDescricaoAbreviadaSubcategoria() {
		return descricaoAbreviadaSubcategoria;
	}
	public void setDescricaoAbreviadaSubcategoria(String descricaoAbreviadaSubcategoria) {
		this.descricaoAbreviadaSubcategoria = descricaoAbreviadaSubcategoria;
	}
	public Date getUltimaAlteracao() {
		return ultimaAlteracao;
	}
	public void setUltimaAlteracao(String ultimaAlteracao) {
		this.ultimaAlteracao = Util.getData(ultimaAlteracao);
	}
	
	private static String[] colunas = new String[] { CategoriasSubcategorias.ID,CategoriasSubcategorias.MATRICULA, CategoriasSubcategorias.IDCATEGORIA,
	                                         CategoriasSubcategorias.DESCRICAOCATEGORIA, CategoriasSubcategorias.IDSUBCATEGORIA, CategoriasSubcategorias.DESCRICAOSUBCATEGORIA, CategoriasSubcategorias.QUANTIDADEECONOMIA,
                                             CategoriasSubcategorias.DESCRICAOABREVIADACATEGORIA, CategoriasSubcategorias.DESCRICAOABREVIADASUBCATEGORIA, 
                                             CategoriasSubcategorias.FATORECONOMIAS, CategoriasSubcategorias.ULTIMAALTERACAO
	};
	
	public String[] getColunas(){
		return colunas;
	}	
	
	public static final class CategoriasSubcategorias implements BaseColumns {
		public static final String ID = "CASC_ID";
		public static final String MATRICULA = "IMOV_ID";
		public static final String IDCATEGORIA = "CATG_ID";
		public static final String DESCRICAOCATEGORIA  = "CATG_DESCRICAO";
		public static final String IDSUBCATEGORIA = "SCAT_ID";
		public static final String DESCRICAOSUBCATEGORIA  = "SCAT_DESCRICAO";
		public static final String QUANTIDADEECONOMIA  = "CASC_QTDECONOMIA";
		public static final String DESCRICAOABREVIADACATEGORIA   = "CATG_DSABREVIADO";
		public static final String DESCRICAOABREVIADASUBCATEGORIA  = "SCAT_DSABREVIADA";
		public static final String FATORECONOMIAS   = "CATG_NNFATORECONOMIAS";
		public static final String ULTIMAALTERACAO  = "CASC_TMULTIMAALTERACAO";
	}
	
	private void insertFromFile(ArrayList<String> obj){
		
		ImovelConta matricula = new ImovelConta();		
		matricula.setId(Integer.parseInt(obj.get(1)));
		setMatricula(matricula);

		if(obj.get(2).length()!=0){
			setCodigoCategoria(Integer.parseInt(obj.get(2)));
		}
		setDescricaoCategoria(obj.get(3));	
		if(obj.get(4).length()!=0){
			setCodigoSubcategoria(Integer.parseInt(obj.get(4)));	
		}
		setDescricaoSubcategoria(obj.get(5));	
		if(obj.get(6).length()!=0){
			setQtdEconomiasSubcategoria(Integer.parseInt(obj.get(6)));
		}
		setDescricaoAbreviadaCategoria(obj.get(7));	
		setDescricaoAbreviadaSubcategoria(obj.get(8));	
		if(obj.get(9).length()!=0){
			setFatorEconomiaCategoria(Integer.parseInt(obj.get(9)));
		}	
		String date = Util.convertDateToDateStr(Util.getCurrentDateTime());	
		setUltimaAlteracao(date);			
	}

	
	
	public final class CategoriasSubcategoriasTipos {
		public final String ID = " INTEGER PRIMARY KEY AUTOINCREMENT ";
		public final String MATRICULA = "  INTEGER CONSTRAINT [FK1_CATEGORIA_SUBCATEGORIA] REFERENCES [imovel_conta]([IMOV_ID]) ON DELETE RESTRICT ON UPDATE RESTRICT";
		public final String IDCATEGORIA = " INTEGER NOT NULL ";
		public final String DESCRICAOCATEGORIA = " VARCHAR(15) NOT NULL";
		public final String IDSUBCATEGORIA = " INTEGER NOT NULL ";
		public final String DESCRICAOSUBCATEGORIA  = " VARCHAR(50) NOT NULL";
		public final String QUANTIDADEECONOMIA = " INTEGER NOT NULL ";	
		public final String DESCRICAOABREVIADACATEGORIA = " VARCHAR(3) NULL"; 
		public final String DESCRICAOABREVIADASUBCATEGORIA = " VARCHAR(20) NULL ";
		public final String FATORECONOMIAS = " INTEGER  NULL ";
		public final String ULTIMAALTERACAO = " TIMESTAMP NOT NULL ";
		
		private String[] tipos = new String[] { ID,MATRICULA, IDCATEGORIA,
	        DESCRICAOCATEGORIA, IDSUBCATEGORIA, DESCRICAOSUBCATEGORIA, QUANTIDADEECONOMIA,
	        DESCRICAOABREVIADACATEGORIA, DESCRICAOABREVIADASUBCATEGORIA, 
	        FATORECONOMIAS, ULTIMAALTERACAO};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
	
	public String getNomeTabela(){
		return "categoria_subcategoria";
	}
	
	public ContentValues preencherValues() {
		ContentValues values = new ContentValues();
		
		values.put(CategoriasSubcategorias.MATRICULA, getMatricula().getId());
		values.put(CategoriasSubcategorias.IDCATEGORIA, getCodigoCategoria());
		values.put(CategoriasSubcategorias.DESCRICAOCATEGORIA, getDescricaoCategoria());
		values.put(CategoriasSubcategorias.QUANTIDADEECONOMIA, getQtdEconomiasSubcategoria());
		values.put(CategoriasSubcategorias.DESCRICAOSUBCATEGORIA, getDescricaoCategoria());
		values.put(CategoriasSubcategorias.IDSUBCATEGORIA, getCodigoSubcategoria());
		values.put(CategoriasSubcategorias.DESCRICAOABREVIADACATEGORIA, getDescricaoAbreviadaCategoria());
		values.put(CategoriasSubcategorias.DESCRICAOABREVIADASUBCATEGORIA, getDescricaoAbreviadaSubcategoria());
		values.put(CategoriasSubcategorias.FATORECONOMIAS, getFatorEconomiaCategoria());
		String dataStr = Util.convertDateToDateStr(Util.getCurrentDateTime());
		values.put(CategoriasSubcategorias.ULTIMAALTERACAO, dataStr);
		return values;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<CategoriaSubcategoria> preencherObjetos(Cursor cursor) {		
			
		int codigo = cursor.getColumnIndex(CategoriasSubcategorias.ID);
		int matricula = cursor.getColumnIndex(CategoriasSubcategorias.MATRICULA);
		int idSubcategoria = cursor.getColumnIndex(CategoriasSubcategorias.IDSUBCATEGORIA);
		int idCategoria = cursor.getColumnIndex(CategoriasSubcategorias.IDCATEGORIA);
		int descricaoAbreviadaSubcategoria = cursor.getColumnIndex(CategoriasSubcategorias.DESCRICAOABREVIADASUBCATEGORIA);
		int descricaoAbreviadaCategoria = cursor.getColumnIndex(CategoriasSubcategorias.DESCRICAOABREVIADACATEGORIA);
		int descricaoCategoria = cursor.getColumnIndex(CategoriasSubcategorias.DESCRICAOCATEGORIA);
		int descricaoSubcategoria = cursor.getColumnIndex(CategoriasSubcategorias.DESCRICAOSUBCATEGORIA);
		int fatorEconomias = cursor.getColumnIndex(CategoriasSubcategorias.FATORECONOMIAS);
		int quantidadeEconomia = cursor.getColumnIndex(CategoriasSubcategorias.QUANTIDADEECONOMIA);
		int ultimaAlteracao = cursor.getColumnIndex(CategoriasSubcategorias.ULTIMAALTERACAO);
		
		ArrayList<CategoriaSubcategoria> categoriasSubcategorias = new ArrayList<CategoriaSubcategoria>();
		
		do{
			CategoriaSubcategoria categoriaSubcategoria = new CategoriaSubcategoria();
			if(Util.getIntBanco(cursor, CategoriasSubcategorias.MATRICULA, matricula) != null){
				ImovelConta imovelConta = new ImovelConta(Util.getIntBanco(cursor, ConsumosAnteriores.MATRICULA, matricula));
				categoriaSubcategoria.setMatricula(imovelConta);
			}
			
			categoriaSubcategoria.setId(cursor.getInt(codigo));
			categoriaSubcategoria.setCodigoCategoria(Util.getIntBanco(cursor, CategoriasSubcategorias.IDCATEGORIA, idCategoria));
			categoriaSubcategoria.setCodigoSubcategoria(Util.getIntBanco(cursor, CategoriasSubcategorias.IDSUBCATEGORIA, idSubcategoria));
			categoriaSubcategoria.setDescricaoAbreviadaCategoria(cursor.getString(descricaoAbreviadaCategoria));
			categoriaSubcategoria.setDescricaoAbreviadaSubcategoria(cursor.getString(descricaoAbreviadaSubcategoria));
			categoriaSubcategoria.setDescricaoCategoria(cursor.getString(descricaoCategoria));
			categoriaSubcategoria.setDescricaoSubcategoria(cursor.getString(descricaoSubcategoria));
			categoriaSubcategoria.setFatorEconomiaCategoria(Util.getIntBanco(cursor, CategoriasSubcategorias.FATORECONOMIAS, fatorEconomias));
			categoriaSubcategoria.setQtdEconomiasSubcategoria(Util.getIntBanco(cursor, CategoriasSubcategorias.QUANTIDADEECONOMIA, quantidadeEconomia));
			categoriaSubcategoria.setUltimaAlteracao(cursor.getString(ultimaAlteracao));
			
			categoriasSubcategorias.add(categoriaSubcategoria);
			
			
		} while (cursor.moveToNext());
		
		return categoriasSubcategorias;
	}

}
