
package com.br.ipad.isc.repositorios;

import java.util.List;

import android.database.Cursor;
import android.util.Log;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.ConsumoHistorico;
import com.br.ipad.isc.bean.ConsumoHistorico.ConsumosHistoricos;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.bean.ImovelConta.ImovelContas;
import com.br.ipad.isc.excecoes.RepositorioException;
import com.br.ipad.isc.util.ConstantesSistema;

public class RepositorioConsumoHistorico extends RepositorioBasico implements IRepositorioConsumoHistorico {
	
	private static RepositorioConsumoHistorico instancia;	
	private ConsumoHistorico objeto;
	
	public void resetarInstancia(){
		instancia = null;
	}

	public static RepositorioConsumoHistorico getInstance(){
		if ( instancia == null ){
			instancia =  new RepositorioConsumoHistorico();
			instancia.objeto = new ConsumoHistorico();
		} 
		return instancia;
	}	

	public ConsumoHistorico buscarConsumoHistoricoPorImovelIdLigacaoTipo(Integer imovelId, Integer ligacaoTipo) throws RepositorioException {
		Cursor cursor = null;
		try {
			cursor = db.query(objeto.getNomeTabela(), objeto.getColunas(), ConsumosHistoricos.MATRICULA + "=" + imovelId 
					+" AND "+ConsumosHistoricos.TIPOLIGACAO + "=" + ligacaoTipo, null,
					null, null, null, null);

			if (cursor.moveToFirst()) {
				List<ConsumoHistorico> colecao = objeto.preencherObjetos(cursor);
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

	public Integer obterConsumoImoveisMicro(Integer idImovelMacro, Integer tipoLigacao) throws RepositorioException {
		
		Cursor cursor = null;
		Integer total = null;

		try {
			
			String query = "SELECT SUM ("+ConsumosHistoricos.CONSUMOCOBRADOMICRO+") AS total  FROM "+objeto.getNomeTabela()+
						   " WHERE "+ConsumosHistoricos.TIPOLIGACAO+" = "+tipoLigacao+" AND "+ConsumosHistoricos.MATRICULA+" " +
						   "IN(SELECT " +ImovelContas.ID+ " FROM " +(new ImovelConta()).getNomeTabela()+" WHERE "+ImovelContas.IDIMOVELCONDOMINIO+" = "+idImovelMacro+")"; 

			cursor = db.rawQuery(query, null);
			
			if (cursor.moveToFirst()) {
				
				int indice = cursor.getColumnIndex("total");
				total = cursor.getInt(indice);
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
		return total;
	}
	
	/**
	 * Retorna a quantidade de registro da tabela cosumo historico
	 * @author Carlos Chaves
	 * @return Integer
	 * @throws RepositorioException
	 */
	public Integer obterQuantidadeRegistroConsumoHistorico() throws RepositorioException {	
		Cursor cursor = null;
		
		try {
			String query = "SELECT COUNT(*) as qnt FROM "+ objeto.getNomeTabela();
			
			cursor = db.rawQuery(query, null);
			
			if (cursor.moveToFirst()) {
				int codigo = cursor.getColumnIndex("qnt");
				return cursor.getInt(codigo);
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
