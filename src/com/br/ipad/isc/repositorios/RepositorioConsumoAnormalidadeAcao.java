
package com.br.ipad.isc.repositorios;

import java.util.ArrayList;

import android.database.Cursor;
import android.util.Log;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.ConsumoAnormalidadeAcao;
import com.br.ipad.isc.bean.ConsumoAnormalidadeAcao.ConsumoAnormalidadeAcoes;
import com.br.ipad.isc.excecoes.RepositorioException;
import com.br.ipad.isc.util.ConstantesSistema;

public class RepositorioConsumoAnormalidadeAcao extends RepositorioBasico implements IRepositorioConsumoAnormalidadeAcao {
	
	private static RepositorioConsumoAnormalidadeAcao instancia;	
	private ConsumoAnormalidadeAcao objeto;
	
	public void resetarInstancia(){
		instancia = null;
	}

	public static RepositorioConsumoAnormalidadeAcao getInstance(){
		if ( instancia == null ){
			instancia =  new RepositorioConsumoAnormalidadeAcao();
			instancia.objeto = new ConsumoAnormalidadeAcao();
		} 
		return instancia;
	}	

	public ArrayList<ConsumoAnormalidadeAcao> buscarConsumoAnormalidadeAcao(Integer perfilId, Integer anormalidade,
			Integer categoriaPrincipal) throws RepositorioException {
		
		Cursor cursor = null;
		ConsumoAnormalidadeAcao consumoAnormalidadeAcao = null;
		
		try {
						
			String where = ConsumoAnormalidadeAcoes.CONSUMOANORMALIDADE + "=" + anormalidade;
			if (perfilId!=null && perfilId!=0){
				where += " AND "+ConsumoAnormalidadeAcoes.IDPERFIL + "=" + perfilId;
			}
			if (categoriaPrincipal!=null && categoriaPrincipal!=0){
				where += " AND "+ConsumoAnormalidadeAcoes.IDCATEGORIA + "=" + categoriaPrincipal;
			}
			
			cursor = db.query(objeto.getNomeTabela(), objeto.getColunas(), where, null,
					null, null, null, null);

			if (cursor.moveToFirst()) {
				consumoAnormalidadeAcao = new ConsumoAnormalidadeAcao();
				return consumoAnormalidadeAcao.preencherObjetos(cursor);
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
	
	public ArrayList<ConsumoAnormalidadeAcao> buscarConsumoAnormalidadeAcao(Integer anormalidade,
			Integer categoriaPrincipal) throws RepositorioException {
		
		Cursor cursor = null;
		ConsumoAnormalidadeAcao consumoAnormalidadeAcao = null;
		
		try {
			
			String where = ConsumoAnormalidadeAcoes.CONSUMOANORMALIDADE + "=" + anormalidade;
			where += " AND "+ConsumoAnormalidadeAcoes.IDPERFIL + " IS NULL ";
			if (categoriaPrincipal!=null && categoriaPrincipal!=0){
				where += " AND "+ConsumoAnormalidadeAcoes.IDCATEGORIA + "=" + categoriaPrincipal;
			}
			
			cursor = db.query(objeto.getNomeTabela(), objeto.getColunas(), where, null,
					null, null, null, null);

			if (cursor.moveToFirst()) {
				consumoAnormalidadeAcao = new ConsumoAnormalidadeAcao();
				return consumoAnormalidadeAcao.preencherObjetos(cursor);
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
	
	public ArrayList<ConsumoAnormalidadeAcao> buscarConsumoAnormalidadeAcao(Integer anormalidade) throws RepositorioException {
		Cursor cursor = null;
		ConsumoAnormalidadeAcao consumoAnormalidadeAcao = null;
		
		try {			
			
			String where = ConsumoAnormalidadeAcoes.CONSUMOANORMALIDADE + "=" + anormalidade;
			where += " AND "+ConsumoAnormalidadeAcoes.IDPERFIL + " IS NULL AND "+
					ConsumoAnormalidadeAcoes.IDCATEGORIA + " IS NULL ";
			
			cursor = db.query(objeto.getNomeTabela(), objeto.getColunas(), where, null,
					null, null, null, null);

			if (cursor.moveToFirst()) {
				consumoAnormalidadeAcao = new ConsumoAnormalidadeAcao();
				return consumoAnormalidadeAcao.preencherObjetos(cursor);
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
