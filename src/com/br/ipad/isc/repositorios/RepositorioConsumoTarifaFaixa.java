
package com.br.ipad.isc.repositorios;

import java.util.ArrayList;
import java.util.Date;

import android.database.Cursor;
import android.util.Log;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.ConsumoTarifaCategoria;
import com.br.ipad.isc.bean.ConsumoTarifaCategoria.ConsumosTarifasCategorias;
import com.br.ipad.isc.bean.ConsumoTarifaFaixa;
import com.br.ipad.isc.bean.ConsumoTarifaFaixa.ConsumosTarifasFaixas;
import com.br.ipad.isc.excecoes.RepositorioException;
import com.br.ipad.isc.util.ConstantesSistema;
import com.br.ipad.isc.util.Util;

public class RepositorioConsumoTarifaFaixa extends RepositorioBasico implements IRepositorioConsumoTarifaFaixa {
	
	private static RepositorioConsumoTarifaFaixa instancia;	
	private ConsumoTarifaFaixa objeto;
	
	public void resetarInstancia(){
		instancia = null;
	}

	public static RepositorioConsumoTarifaFaixa getInstance(){
		if ( instancia == null ){
			instancia =  new RepositorioConsumoTarifaFaixa();
			instancia.objeto = new ConsumoTarifaFaixa();
		} 
		return instancia;
	}	

	public ArrayList<ConsumoTarifaFaixa> buscarConsumosTarifasFaixasPorTarifaCateg(Integer idTarifaCateg) throws RepositorioException {
		Cursor cursor = null;
		try {
			cursor = db.query(objeto.getNomeTabela(), objeto.getColunas(), ConsumosTarifasFaixas.CONSUMOTARIFACATEGORIA + "=" + idTarifaCateg , null,
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
	
	public ArrayList<ConsumoTarifaFaixa> buscarConsumosTarifaFaixaPorId(int idTarifa, Date dataInicioVigencia) throws RepositorioException {
		Cursor cursor = null;
		try {
			cursor = db.query(objeto.getNomeTabela(), objeto.getColunas(), 
					ConsumosTarifasFaixas.CONSUMOTARIFACATEGORIA + "=" + idTarifa +
					" AND "+ConsumosTarifasFaixas.DATAVIGENCIA+"=\""+Util.convertDateToDateStr(dataInicioVigencia)+"\"",
					null, null, null, null, null);
			
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
	
	public ArrayList<ConsumoTarifaFaixa> buscarConsumosTarifaFaixaPorCodigo(int codigoTarifa, Date dataInicioVigencia) throws RepositorioException {
		Cursor cursor = null;
		try {
			String query = "SELECT * FROM "+ objeto.getNomeTabela() + " as faixa INNER JOIN "+(new ConsumoTarifaCategoria()).getNomeTabela()
					+" as categ ON faixa."+ConsumosTarifasFaixas.CONSUMOTARIFACATEGORIA +" = categ."+ ConsumosTarifasCategorias.ID +
					" WHERE categ."+ConsumosTarifasCategorias.CONSUMOTARIFA + "=" + codigoTarifa +
					" AND "+ConsumosTarifasFaixas.DATAVIGENCIA+"=\""+Util.convertDateToDateStr(dataInicioVigencia)+"\"";
			
			cursor = db.rawQuery(query, null);
			
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