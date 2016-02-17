
package com.br.ipad.isc.repositorios;

import java.util.Collection;
import java.util.List;

import android.database.Cursor;
import android.util.Log;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.CreditoRealizado;
import com.br.ipad.isc.bean.CreditoRealizado.CreditosRealizados;
import com.br.ipad.isc.excecoes.RepositorioException;
import com.br.ipad.isc.util.ConstantesSistema;

public class RepositorioCreditoRealizado extends RepositorioBasico implements IRepositorioCreditoRealizado {
	
	private static RepositorioCreditoRealizado instancia;	
	private CreditoRealizado objeto;
	
	public void resetarInstancia(){
		instancia = null;
	}

	public static RepositorioCreditoRealizado getInstance(){
		if ( instancia == null ){
			instancia =  new RepositorioCreditoRealizado();
			instancia.objeto = new CreditoRealizado();
		} 
		return instancia;
	}	

	public Collection<CreditoRealizado> buscarCreditoRealizadoPorImovelId(Integer imovelId) throws RepositorioException {
		Cursor cursor = null;
		try {
			
			cursor = db.query(objeto.getNomeTabela(), objeto.getColunas(),  CreditosRealizados.MATRICULA + "=" + imovelId 
					+ " AND " +CreditosRealizados.VALOR+ "> 0 AND " +CreditosRealizados.VALOR+ " IS NOT NULL", null,
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
	
	public Integer obterQntCreditoRealizadoPorImovelId(Integer imovelId) throws RepositorioException {
		Cursor cursor = null;
		try {
			
			String query = "SELECT COUNT(*) AS qnt FROM "+ objeto.getNomeTabela() + " WHERE "+
					CreditosRealizados.MATRICULA+ "=" + imovelId;
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
	
	public Double obterValorCreditoTotal(Integer imovelId) throws RepositorioException {
		Cursor cursor = null;
	
		Double retorno = null;
		try {
			
			String query = "SELECT SUM("+CreditosRealizados.VALOR
					+") AS qnt FROM "+ objeto.getNomeTabela() + " WHERE "+CreditosRealizados.MATRICULA+ "=" + imovelId;
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
	
	public CreditoRealizado buscarCreditoRealizadoPorDescricao(String descricaoCredito,Integer imovelId) throws RepositorioException {
		Cursor cursor = null;
		try {
			String query = "SELECT * FROM "+ objeto.getNomeTabela() + " WHERE "
					+CreditosRealizados.DESCRICAOCREDITOTIPO + " like \"" + descricaoCredito +"%\" AND "
					+CreditosRealizados.MATRICULA + "=" + imovelId;
			cursor = db.rawQuery(query, null);
			
			if (cursor.moveToFirst()) {
				List<CreditoRealizado> colecao = objeto.preencherObjetos(cursor);
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
}
