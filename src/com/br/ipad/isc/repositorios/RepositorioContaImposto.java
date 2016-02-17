
package com.br.ipad.isc.repositorios;

import java.util.Collection;

import android.database.Cursor;
import android.util.Log;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.ContaImposto;
import com.br.ipad.isc.bean.ContaImposto.ContasImpostos;
import com.br.ipad.isc.excecoes.RepositorioException;
import com.br.ipad.isc.util.ConstantesSistema;

public class RepositorioContaImposto extends RepositorioBasico implements IRepositorioContaImposto {
	
	private static RepositorioContaImposto instancia;	
	private ContaImposto objeto;
	
	public void resetarInstancia(){
		instancia = null;
	}

	public static RepositorioContaImposto getInstance(){
		if ( instancia == null ){
			instancia =  new RepositorioContaImposto();
			instancia.objeto = new ContaImposto();
		} 
		return instancia;
	}	

	public Collection<ContaImposto> buscarContaImpostoPorImovelId(Integer imovelId) throws RepositorioException {
		Cursor cursor = null;
		try {
			
			cursor = db.query(objeto.getNomeTabela(), objeto.getColunas(), ContasImpostos.MATRICULA + "=" + imovelId , null,
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
	
	public Integer obterQntContaImpostoPorImovelId(Integer imovelId) throws RepositorioException {
		Cursor cursor = null;
		
		try {
			
			String query = "SELECT COUNT(*) AS qnt FROM "+ objeto.getNomeTabela() + " WHERE "+
					ContasImpostos.MATRICULA+ "=" + imovelId;
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
}
