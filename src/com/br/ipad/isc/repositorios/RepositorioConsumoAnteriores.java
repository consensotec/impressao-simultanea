
package com.br.ipad.isc.repositorios;

import java.util.List;

import android.database.Cursor;
import android.util.Log;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.ConsumoAnteriores;
import com.br.ipad.isc.bean.ConsumoAnteriores.ConsumosAnteriores;
import com.br.ipad.isc.excecoes.RepositorioException;
import com.br.ipad.isc.util.ConstantesSistema;

public class RepositorioConsumoAnteriores extends RepositorioBasico implements IRepositorioConsumoAnteriores {
	
	private static RepositorioConsumoAnteriores instancia;	
	private ConsumoAnteriores objeto;
	
	public void resetarInstancia(){
		instancia = null;
	}

	public static RepositorioConsumoAnteriores getInstance(){
		if ( instancia == null ){
			instancia =  new RepositorioConsumoAnteriores();
			instancia.objeto = new ConsumoAnteriores();
		} 
		return instancia;
	}	

	public List<ConsumoAnteriores> buscarConsumoAnterioresPorImovelId(Integer imovelId) throws RepositorioException {
		Cursor cursor = null;
		try {
			
			cursor = db.query(objeto.getNomeTabela(), objeto.getColunas(), ConsumosAnteriores.MATRICULA + "=" + imovelId , null,
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
	
	public ConsumoAnteriores buscarConsumoAnterioresPorImovelAnoMesPorTipoLigacao(Integer imovelId, Integer anoMes,Integer tipoValidacao) throws RepositorioException {
		Cursor cursor = null;
		try {
			
			cursor = db.query(objeto.getNomeTabela(), objeto.getColunas(), ConsumosAnteriores.MATRICULA + "=" + imovelId + " AND "
					+ ConsumosAnteriores.ANOMESREFERENCIA + "=" + anoMes + " and " +ConsumosAnteriores.TIPOLIGACAO +"="+ tipoValidacao
					, null,null, null, null, null);

			if (cursor.moveToFirst()) {
				List<ConsumoAnteriores> colecao = objeto.preencherObjetos(cursor);
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
	
	public List<ConsumoAnteriores> buscarConsumoAnterioresPorImovelTipoLigacao(Integer imovelId, Integer tipoMedicao) throws RepositorioException {
		Cursor cursor = null;
		try {
			
			cursor = db.query(objeto.getNomeTabela(), objeto.getColunas(), ConsumosAnteriores.MATRICULA + "=" + imovelId + " AND "
					+ ConsumosAnteriores.TIPOLIGACAO + "=" + tipoMedicao, null,null, null, null, null);

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
	
	public List<ConsumoAnteriores> buscarConsumoAnterioresPorImovelAnormalidade(Integer imovelId, Integer idAnormalidadeConsumo) throws RepositorioException {
		Cursor cursor = null;
		try {
			
			cursor = db.query(objeto.getNomeTabela(), objeto.getColunas(), ConsumosAnteriores.MATRICULA + "=" + imovelId + " AND "
					+ ConsumosAnteriores.IDANORMALIDADECONSUMO + "=" + idAnormalidadeConsumo, null,null, null, null, null);

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
	
	public ConsumoAnteriores buscarConsumoAnterioresPorImovelAnormalidade(Integer imovelId, Integer idAnormalidadeConsumo,
			Integer anoMes) throws RepositorioException {
		
		Cursor cursor = null;
		try {
			
			cursor = db.query(objeto.getNomeTabela(), objeto.getColunas(), ConsumosAnteriores.MATRICULA + "=" + imovelId + " AND "
					+ ConsumosAnteriores.IDANORMALIDADECONSUMO + "=" + idAnormalidadeConsumo + " AND "
					+ ConsumosAnteriores.ANOMESREFERENCIA + "=" + anoMes, null,null, null, null, null);

			if (cursor.moveToFirst()) {
				List<ConsumoAnteriores> colecao = objeto.preencherObjetos(cursor);
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
