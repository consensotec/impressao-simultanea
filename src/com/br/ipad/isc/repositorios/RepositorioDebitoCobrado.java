
package com.br.ipad.isc.repositorios;

import java.util.Collection;
import java.util.List;

import android.database.Cursor;
import android.util.Log;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.DebitoCobrado;
import com.br.ipad.isc.bean.DebitoCobrado.DebitosCobrados;
import com.br.ipad.isc.excecoes.RepositorioException;
import com.br.ipad.isc.util.ConstantesSistema;

public class RepositorioDebitoCobrado extends RepositorioBasico implements IRepositorioDebitoCobrado {
	
	private static RepositorioDebitoCobrado instancia;	
	private DebitoCobrado objeto;
	
	public void resetarInstancia(){
		instancia = null;
	}

	public static RepositorioDebitoCobrado getInstance(){
		if ( instancia == null ){
			instancia =  new RepositorioDebitoCobrado();
			instancia.objeto = new DebitoCobrado();
		} 
		return instancia;
	}	

	public Collection<DebitoCobrado> buscarDebitoCobradoPorImovelId(Integer imovelId) throws RepositorioException {
		Cursor cursor = null;

		Collection<DebitoCobrado> debitosCobrados = null;
		try {
			
			cursor = db.query(objeto.getNomeTabela(), objeto.getColunas(), DebitosCobrados.MATRICULA + "=" + imovelId , null,
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
		return debitosCobrados;
	}
	
	public Integer obterQntDebitoCobradoPorImovelId(Integer imovelId) throws RepositorioException {
		Cursor cursor = null;

		try {
			
			String query = "SELECT COUNT(*) AS qnt FROM "+ objeto.getNomeTabela() + " WHERE "+
					DebitosCobrados.MATRICULA+ "=" + imovelId;
			cursor = db.rawQuery(query, null);
			
			if (cursor.moveToFirst()) {
				
				int num = cursor.getColumnIndex("qnt");
				return cursor.getInt(num);

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
	
	public DebitoCobrado buscarDebitoCobradoPorCodigo(Integer debitoCodigo,Integer imovelId) throws RepositorioException {
		Cursor cursor = null;
		try {
			cursor = db.query(objeto.getNomeTabela(), objeto.getColunas(), DebitosCobrados.CODIGODEBITO + "=" + debitoCodigo +" AND "
								+DebitosCobrados.MATRICULA + "=" + imovelId, null,
					null, null, null, null);

			if (cursor.moveToFirst()) {
				List<DebitoCobrado> colecao = objeto.preencherObjetos(cursor);
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
	
	public Double obterValorDebitoTotal(Integer imovelId) throws RepositorioException {
		Cursor cursor = null;
	
		Double retorno = null;
		try {
			
			String query = "SELECT SUM("+DebitosCobrados.VALOR
					+") AS qnt FROM "+ objeto.getNomeTabela() + " WHERE "+DebitosCobrados.MATRICULA+ "=" + imovelId;
			cursor = db.rawQuery(query, null);
			
			if (cursor.moveToFirst()) {
				
				int num = cursor.getColumnIndex("qnt");
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
}