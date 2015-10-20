
package com.br.ipad.isc.repositorios;

import java.util.ArrayList;

import android.database.Cursor;
import android.util.Log;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.ContaDebito;
import com.br.ipad.isc.bean.ContaDebito.ContasDebitos;
import com.br.ipad.isc.excecoes.RepositorioException;
import com.br.ipad.isc.util.ConstantesSistema;

public class RepositorioContaDebito extends RepositorioBasico implements IRepositorioContaDebito{
	
	private static RepositorioContaDebito instancia;	
	private ContaDebito objeto;
	
	public void resetarInstancia(){
		instancia = null;
	}

	public static RepositorioContaDebito getInstance(){
		if ( instancia == null ){
			instancia =  new RepositorioContaDebito();
			instancia.objeto = new ContaDebito();
		} 
		return instancia;
	}	

	public ArrayList<ContaDebito> buscarContasDebitosPorIdImovel(Integer idImovel) throws RepositorioException {
		Cursor cursor = null;
		try {
			
			cursor = db.query(objeto.getNomeTabela(), objeto.getColunas(), ContasDebitos.MATRICULA + "=" + idImovel, null,
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

}