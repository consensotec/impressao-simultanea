
package com.br.ipad.isc.repositorios;

import java.util.ArrayList;

import android.database.Cursor;
import android.util.Log;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.CategoriaSubcategoria;
import com.br.ipad.isc.bean.CategoriaSubcategoria.CategoriasSubcategorias;
import com.br.ipad.isc.excecoes.RepositorioException;
import com.br.ipad.isc.util.ConstantesSistema;

public class RepositorioCategoriaSubcategoria extends RepositorioBasico implements IRepositorioCategoriaSubcategoria {
	
	private static RepositorioCategoriaSubcategoria instancia;	
	private CategoriaSubcategoria objeto;
	
	public void resetarInstancia(){
		instancia = null;
	}

	public static RepositorioCategoriaSubcategoria getInstance(){
		if ( instancia == null ){
			instancia =  new RepositorioCategoriaSubcategoria();
			instancia.objeto = new CategoriaSubcategoria();
		} 
		return instancia;
	}	

	public ArrayList<CategoriaSubcategoria> buscarCategoriaSubcategoriaPorImovelId(Integer imovelId) throws RepositorioException {
		Cursor cursor = null;
		try {
			cursor = db.query(objeto.getNomeTabela(), objeto.getColunas(), CategoriasSubcategorias.MATRICULA + "=" + imovelId , null,
				null, null, null, null);
	
			if (cursor.moveToFirst()) {
				return objeto.preencherObjetos(cursor);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e( ConstantesSistema.CATEGORIA , e.getMessage());
			throw new RepositorioException(context.getResources().getString(
					R.string.db_erro));
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return null;
	}
	
	public Integer obterQuantidadeEconomiasTotal(Integer imovelId) throws RepositorioException {
		Cursor cursor = null;
	
		Integer retorno = null;
		try {
			
			String query = "SELECT SUM("+CategoriasSubcategorias.QUANTIDADEECONOMIA
					+") AS qnt FROM "+ objeto.getNomeTabela() + " WHERE "+CategoriasSubcategorias.MATRICULA+ "=" + imovelId;
			cursor = db.rawQuery(query, null);
			
			if (cursor.moveToFirst()) {
				
				int num = cursor.getColumnIndex("qnt");
				retorno = cursor.getInt(num);

			}			
			
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , e.getMessage());
			throw new RepositorioException(context.getResources().getString(
					R.string.db_erro));
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return retorno;
	}
	
	public Integer obterCategoriaPrincipal(Integer imovelId) throws RepositorioException {
		Cursor cursor = null;
	
		Integer retorno = null;
		try {
			
			String query = "SELECT MAX("+CategoriasSubcategorias.QUANTIDADEECONOMIA
					+") AS qnt, "+CategoriasSubcategorias.IDCATEGORIA+" as id FROM "+ objeto.getNomeTabela() + 
					" WHERE "+CategoriasSubcategorias.MATRICULA+ "=" + imovelId;
			cursor = db.rawQuery(query, null);
			
			if (cursor.moveToFirst()) {
				
				int num = cursor.getColumnIndex("id");
				retorno = cursor.getInt(num);

			}			
			
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , e.getMessage());
			throw new RepositorioException(context.getResources().getString(
					R.string.db_erro));
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return retorno;
	}

	public Integer obterQuantidadeEconomias(Integer imovelId, Integer codigoCategoria, Integer codigoSubcategoria) throws RepositorioException {
		Cursor cursor = null;
	
		Integer retorno = null;
		try {
			
			String query = "SELECT SUM("+CategoriasSubcategorias.QUANTIDADEECONOMIA
					+") AS qnt FROM "+ objeto.getNomeTabela() + " WHERE "+
					CategoriasSubcategorias.IDCATEGORIA+ "=" + codigoCategoria+
					" AND "+CategoriasSubcategorias.IDSUBCATEGORIA+ "=" + codigoSubcategoria +
					" AND "+CategoriasSubcategorias.MATRICULA+ "=" + imovelId;
			cursor = db.rawQuery(query, null);
			
			if (cursor.moveToFirst()) {
				
				int num = cursor.getColumnIndex("qnt");
				retorno = cursor.getInt(num);

			}			
			
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , e.getMessage());
			throw new RepositorioException(context.getResources().getString(
					R.string.db_erro));
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return retorno;
	}
}