
package com.br.ipad.isc.repositorios;

import java.util.List;

import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.SequencialRotaMarcacao;
import com.br.ipad.isc.bean.SequencialRotaMarcacao.SequencialRotaMarcacoes;
import com.br.ipad.isc.excecoes.RepositorioException;
import com.br.ipad.isc.util.ConstantesSistema;

public class RepositorioSequencialRotaMarcacao extends RepositorioBasico implements IRepositorioSequencialRotaMarcacao {
	
	private static RepositorioSequencialRotaMarcacao instancia;	
	private SequencialRotaMarcacao objeto;
	
	public void resetarInstancia(){
		instancia = null;
	}

	public static RepositorioSequencialRotaMarcacao getInstance(){
		if ( instancia == null ){
			instancia =  new RepositorioSequencialRotaMarcacao();
			instancia.objeto = new SequencialRotaMarcacao();
		} 
		return instancia;
	}	

	public SequencialRotaMarcacao buscarSequencialRotaMarcacao(Integer idImovel) throws RepositorioException {
		Cursor cursor = null;
		try {
			cursor = db.query(objeto.getNomeTabela(), objeto.getColunas(), SequencialRotaMarcacoes.MATRICULA + "=" + idImovel 
					,null,null, null, null, null);
			if (cursor.moveToFirst()) {
				List<SequencialRotaMarcacao> colecao = objeto.preencherObjetos(cursor);
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
	
	public void removerTodosSequencialRotaMarcacao() throws RepositorioException {
		try {
			db.delete(objeto.getNomeTabela(), null, null);
		} catch (SQLException sqe) {
			sqe.printStackTrace();
			Log.e( ConstantesSistema.CATEGORIA , sqe.getMessage());
			throw new RepositorioException(context.getResources().getString(
					R.string.db_erro));
		}		
	}
}