
package com.br.ipad.isc.repositorios;

import java.util.List;

import android.database.Cursor;
import android.util.Log;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.QualidadeAgua;
import com.br.ipad.isc.bean.QualidadeAgua.QualidadesAguas;
import com.br.ipad.isc.excecoes.RepositorioException;
import com.br.ipad.isc.util.ConstantesSistema;

public class RepositorioQualidadeAgua extends RepositorioBasico implements IRepositorioQualidadeAgua {
	
	private static RepositorioQualidadeAgua instancia;	
	private QualidadeAgua objeto;
		
	public void resetarInstancia(){
		instancia = null;
	}

	public static RepositorioQualidadeAgua getInstance(){
		if ( instancia == null ){
			instancia =  new RepositorioQualidadeAgua();
			instancia.objeto = new QualidadeAgua();
		} 
		return instancia;
	}	

	public QualidadeAgua buscarQualidadeAguaPorLocalidadeSetorComercial(Integer idLocalidade, Integer idSetorComercial) throws RepositorioException {
		Cursor cursor = null;
		QualidadeAgua qualidadeAgua = null;

		try {
			cursor = db.query(objeto.getNomeTabela(), objeto.getColunas(), 
					QualidadesAguas.IDLOCALIDADE + "=" + idLocalidade +" and " + 
					QualidadesAguas.IDSETORCOMERCIAL + "=" + idSetorComercial , 
					null,null, null, null, null);

			if (cursor.moveToFirst()) {
				List<QualidadeAgua> colecao = objeto.preencherObjetos(cursor);
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
		return qualidadeAgua;
	}
	
	public QualidadeAgua buscarQualidadeAguaPorLocalidade(Integer idLocalidade) throws RepositorioException {
		Cursor cursor = null;
		try {
			cursor = db.query(objeto.getNomeTabela(), objeto.getColunas(), 
					QualidadesAguas.IDLOCALIDADE + "=" + idLocalidade , 
					null,null, null, null, null);

			if (cursor.moveToFirst()) {
				List<QualidadeAgua> colecao = objeto.preencherObjetos(cursor);
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
	
	public QualidadeAgua buscarQualidadeAguaSemLocalidade() throws RepositorioException {
		Cursor cursor = null;
		try {
			String query = "SELECT * FROM "+objeto.getNomeTabela()+" qualidade " 
					+ " WHERE " + QualidadesAguas.IDLOCALIDADE + " IS NULL " 
					+ " AND " + QualidadesAguas.IDSETORCOMERCIAL + " IS NULL " ;

			cursor = db.rawQuery(query, null);
		
			if (cursor.moveToFirst()) {
				List<QualidadeAgua> colecao = objeto.preencherObjetos(cursor);
				if(colecao != null){
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