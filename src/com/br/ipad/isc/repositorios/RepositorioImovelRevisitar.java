
package com.br.ipad.isc.repositorios;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.util.Log;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.ImovelRevisitar;
import com.br.ipad.isc.bean.ImovelRevisitar.ImoveisRevisitar;
import com.br.ipad.isc.excecoes.RepositorioException;
import com.br.ipad.isc.util.ConstantesSistema;

public class RepositorioImovelRevisitar extends RepositorioBasico implements IRepositorioImovelRevisitar{
	
	private static RepositorioImovelRevisitar instancia;	
	private ImovelRevisitar objeto;
	
	public void resetarInstancia(){
		instancia = null;
	}

	public static RepositorioImovelRevisitar getInstance(){
		if ( instancia == null ){
			instancia =  new RepositorioImovelRevisitar();
			instancia.objeto = new ImovelRevisitar();
		} 
		return instancia;
	}	

	@Override
	public ImovelRevisitar buscarImovelRevisitarPorImovel(Integer imovelId)
			throws RepositorioException {
		Cursor cursor = null;
		try {
			cursor = db.query(objeto.getNomeTabela(), objeto.getColunas(), ImoveisRevisitar.MATRICULA + "=" + imovelId , null,
					null, null, null, null);
			if (cursor.moveToFirst()) {
				List<ImovelRevisitar> colecao = objeto.preencherObjetos(cursor);
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
	
	public ArrayList<ImovelRevisitar> buscarImovelNaoRevisitado() throws RepositorioException {
		Cursor cursor = null;
		try {
			cursor = db.query(objeto.getNomeTabela(), objeto.getColunas(), ImoveisRevisitar.INDICADORREVISITADO + " != "+ ConstantesSistema.SIM + 
					 " OR "+ ImoveisRevisitar.INDICADORREVISITADO +" IS NULL ", null,
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