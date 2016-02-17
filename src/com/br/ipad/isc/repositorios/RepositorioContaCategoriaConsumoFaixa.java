
package com.br.ipad.isc.repositorios;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.ContaCategoriaConsumoFaixa;
import com.br.ipad.isc.bean.ContaCategoriaConsumoFaixa.ContasCategoriasConsumosFaixas;
import com.br.ipad.isc.excecoes.RepositorioException;
import com.br.ipad.isc.util.ConstantesSistema;

public class RepositorioContaCategoriaConsumoFaixa extends RepositorioBasico implements IRepositorioContaCategoriaConsumoFaixa {
	
	private static RepositorioContaCategoriaConsumoFaixa instancia;	
	private ContaCategoriaConsumoFaixa objeto;
	
	public void resetarInstancia(){
		instancia = null;
	}

	public static RepositorioContaCategoriaConsumoFaixa getInstance(){
		if ( instancia == null ){
			instancia =  new RepositorioContaCategoriaConsumoFaixa();
			instancia.objeto = new ContaCategoriaConsumoFaixa();
		} 
		return instancia;
	}	

	public ArrayList<ContaCategoriaConsumoFaixa> buscarContasCategoriasConsumosFaixasPorPorContaCategoriaId
			 (Integer idContaCategoria) throws RepositorioException {
		Cursor cursor = null;
		try {
			
			
			cursor = db.query(objeto.getNomeTabela(), objeto.getColunas(), 
					ContasCategoriasConsumosFaixas.CONTACATEGORIA + "=" + idContaCategoria , null, null, null, null, null);

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

	public Double obterTotalConsumoContasCategoriasConsumosFaixasPorPorContaCategoriaId
	 	(Integer idContaCategoria) throws RepositorioException {
		Cursor cursor = null;
		
		try {
			
			String query = "SELECT SUM("+ContasCategoriasConsumosFaixas.NUMCONSUMO
					+") AS total FROM "+ objeto.getNomeTabela() + " WHERE "+ContasCategoriasConsumosFaixas.CONTACATEGORIA
					+ "=" + idContaCategoria;
			
			cursor = db.rawQuery(query, null);
			
			if (cursor.moveToFirst()) {
				
				int num = cursor.getColumnIndex("total");
				return cursor.getDouble(num);

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
	
	public void removerContaCategoriaConsumoFaixa(String[] idContaCategoria) throws RepositorioException {
		
		String where = ContasCategoriasConsumosFaixas.CONTACATEGORIA + "=?";
		String[] whereArgs = idContaCategoria;

		try {
			db.delete( objeto.getNomeTabela(), where, whereArgs);
		} catch (SQLException sqe) {
			sqe.printStackTrace();
			Log.e( ConstantesSistema.CATEGORIA , sqe.getMessage());
			throw new RepositorioException(context.getResources().getString(
					R.string.db_erro));
		}
	}

	public Double obterTotalValorTarifaContasCategoriasConsumosFaixasPorPorContaCategoriaId(
			Integer idContaCategoria) throws RepositorioException {
		Cursor cursor = null;
		
		try {
			
			String query = "SELECT SUM("+ContasCategoriasConsumosFaixas.VALORTARIFA
					+") AS total FROM "+ objeto.getNomeTabela() + " WHERE "+ContasCategoriasConsumosFaixas.CONTACATEGORIA
					+ "=" + idContaCategoria;
			
			cursor = db.rawQuery(query, null);
			
			if (cursor.moveToFirst()) {
				
				int num = cursor.getColumnIndex("total");
				return cursor.getDouble(num);

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
	
}