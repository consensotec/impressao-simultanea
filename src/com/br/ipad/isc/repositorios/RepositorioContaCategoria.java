
package com.br.ipad.isc.repositorios;

import java.util.List;

import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.CategoriaSubcategoria;
import com.br.ipad.isc.bean.CategoriaSubcategoria.CategoriasSubcategorias;
import com.br.ipad.isc.bean.ContaCategoria;
import com.br.ipad.isc.bean.ContaCategoria.ContasCategorias;
import com.br.ipad.isc.excecoes.RepositorioException;
import com.br.ipad.isc.util.ConstantesSistema;

public class RepositorioContaCategoria extends RepositorioBasico implements IRepositorioContaCategoria {
	
	private static RepositorioContaCategoria instancia;	
	private ContaCategoria objeto;
	
	public void resetarInstancia(){
		instancia = null;
	}

	public static RepositorioContaCategoria getInstance(){
		if ( instancia == null ){
			instancia =  new RepositorioContaCategoria();
			instancia.objeto = new ContaCategoria();
		} 
		return instancia;
	}	

	public ContaCategoria buscarContaCategoriaPorCategoriaSubcategoriaId(Integer categoriaSubcategoriaId, Integer tipoMedicao) 
	throws RepositorioException {
		Cursor cursor = null;
		try {
			cursor = db.query(objeto.getNomeTabela(), objeto.getColunas(),
				ContasCategorias.CATEGORIASUBCATEGORIA + "=" + categoriaSubcategoriaId 
				+ " and " + ContasCategorias.TIPOLIGACAO + "=" + tipoMedicao , null,
					null, null, null, null);

			if (cursor.moveToFirst()) {
				List<ContaCategoria> colecao = objeto.preencherObjetos(cursor);
				if(colecao!=null){
					return colecao.get(0);
				}
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
	
	
	public Double obterValorTotal(Integer imovelId, Integer tipoMedicao) throws RepositorioException {
		Cursor cursor = null;
	
		Double retorno = null;
		try {
			CategoriaSubcategoria categoriaSubcategoria = new CategoriaSubcategoria();
			
			String query = "SELECT SUM("+ContasCategorias.VALORFATURADO
					+") AS soma FROM "+ objeto.getNomeTabela() + " INNER JOIN " +categoriaSubcategoria.getNomeTabela() +
					" ON " + objeto.getNomeTabela()+"."+ContasCategorias.CATEGORIASUBCATEGORIA+" = "+
					categoriaSubcategoria.getNomeTabela()+"."+CategoriasSubcategorias.ID +
					" WHERE "+ContasCategorias.TIPOLIGACAO+" = "+tipoMedicao +					
					" AND "+CategoriasSubcategorias.MATRICULA+" = "+imovelId;
			cursor = db.rawQuery(query, null);
			
			if (cursor.moveToFirst()) {
				
				int num = cursor.getColumnIndex("soma");
				retorno = cursor.getDouble(num);
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
	
	public void removerImovelContaCategoria(Integer idImovel,int tipoLigacao) throws RepositorioException {
		Cursor cursor = null;
		try {
			CategoriaSubcategoria categoriaSubcategoria = new CategoriaSubcategoria();
			
			String query = "SELECT "+ContasCategorias.ID
					+" FROM "+ objeto.getNomeTabela() + " INNER JOIN " +categoriaSubcategoria.getNomeTabela() +
					" ON " + objeto.getNomeTabela() +"."+ ContasCategorias.CATEGORIASUBCATEGORIA+" = "+
					categoriaSubcategoria.getNomeTabela() +"."+ CategoriasSubcategorias.ID +
					" WHERE "+CategoriasSubcategorias.MATRICULA+" = "+idImovel +" AND " + ContasCategorias.TIPOLIGACAO +" = " +tipoLigacao ;
			
			String where = ContasCategorias.ID + "=? ";
			String[] whereArgs = new String[1];

			cursor = db.rawQuery(query, null);
						
			if (cursor.moveToFirst()) {
						
				int id = cursor.getColumnIndex(ContasCategorias.ID);
				
				do {	
					Integer idCategoria = cursor.getInt(id);
					whereArgs[0] = idCategoria.toString();
					
					RepositorioContaCategoriaConsumoFaixa.getInstance().removerContaCategoriaConsumoFaixa(whereArgs);
					db.delete(objeto.getNomeTabela(), where, whereArgs);
					
				} while (cursor.moveToNext());
			}
						
		} catch (SQLException sqe) {
			sqe.printStackTrace();
			Log.e( ConstantesSistema.CATEGORIA , sqe.getMessage());
			throw new RepositorioException(context.getResources().getString(
					R.string.db_erro));
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}
}