
package com.br.ipad.isc.repositorios;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.util.Log;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.ConsumoHistorico;
import com.br.ipad.isc.bean.ConsumoHistorico.ConsumosHistoricos;
import com.br.ipad.isc.bean.LeituraAnormalidade;
import com.br.ipad.isc.bean.LeituraAnormalidade.LeiturasAnormalidades;
import com.br.ipad.isc.excecoes.RepositorioException;
import com.br.ipad.isc.util.ConstantesSistema;

public class RepositorioLeituraAnormalidade extends RepositorioBasico implements IRepositorioLeituraAnormalidade{
	
	private static RepositorioLeituraAnormalidade instancia;	
	private LeituraAnormalidade objeto;
	
	public void resetarInstancia(){
		instancia = null;
	}

	public static RepositorioLeituraAnormalidade getInstance(){
		if ( instancia == null ){
			instancia =  new RepositorioLeituraAnormalidade();
			instancia.objeto = new LeituraAnormalidade();
		} 
		return instancia;
	}	

	public LeituraAnormalidade buscarLeituraAnormalidadePorIdComUsoAtivo(Integer id) throws RepositorioException {
		Cursor cursor = null;
		try {
			cursor = db.query(objeto.getNomeTabela(), objeto.getColunas(), LeiturasAnormalidades.ID + "=" + id +
					" AND "+LeiturasAnormalidades.INDICADORUSO + "=" + 1, null,null, null, null, null);
						
			if (cursor.moveToFirst()) {
				List<LeituraAnormalidade> colecao = objeto.preencherObjetos(cursor);
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

	public ArrayList<LeituraAnormalidade> buscarLeiturasAnormalidadesComUsoAtivo() throws RepositorioException {
		Cursor cursor = null;
		try {
			cursor = db.query(objeto.getNomeTabela(), objeto.getColunas(), LeiturasAnormalidades.INDICADORUSO + "=" + 1 , null,
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

	public LeituraAnormalidade buscarLeituraAnormalidadeImovel(Integer idImovel,Integer tipoLigacao) throws RepositorioException {
		Cursor cursor = null;
		try {
			String query = "SELECT * FROM "+objeto.getNomeTabela()+" ltan INNER JOIN " +(new ConsumoHistorico()).getNomeTabela() +" cshi" +
			" ON ltan.ltan_id=cshi.ltan_id WHERE "+ ConsumosHistoricos.MATRICULA+" =? and "+ 
			ConsumosHistoricos.TIPOLIGACAO + "="+ tipoLigacao;

			cursor = db.rawQuery(query, new String[]{String.valueOf(idImovel)});

			if (cursor.moveToFirst()) {
				List<LeituraAnormalidade> colecao = objeto.preencherObjetos(cursor);
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
