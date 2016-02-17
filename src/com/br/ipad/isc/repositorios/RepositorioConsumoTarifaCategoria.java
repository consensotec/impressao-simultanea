
package com.br.ipad.isc.repositorios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.database.Cursor;
import android.util.Log;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.CategoriaSubcategoria;
import com.br.ipad.isc.bean.CategoriaSubcategoria.CategoriasSubcategorias;
import com.br.ipad.isc.bean.ConsumoTarifaCategoria;
import com.br.ipad.isc.bean.ConsumoTarifaCategoria.ConsumosTarifasCategorias;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.bean.ImovelConta.ImovelContas;
import com.br.ipad.isc.excecoes.RepositorioException;
import com.br.ipad.isc.util.ConstantesSistema;
import com.br.ipad.isc.util.Util;

public class RepositorioConsumoTarifaCategoria extends RepositorioBasico implements IRepositorioConsumoTarifaCategoria {
	
	private static RepositorioConsumoTarifaCategoria instancia;	
	private ConsumoTarifaCategoria objeto;
	
	public void resetarInstancia(){
		instancia = null;
	}

	public static RepositorioConsumoTarifaCategoria getInstance(){
		if ( instancia == null ){
			instancia =  new RepositorioConsumoTarifaCategoria();
			instancia.objeto = new ConsumoTarifaCategoria();
		} 
		return instancia;
	}	
	
	public ArrayList<ConsumoTarifaCategoria> buscarConsumoTarifaCategoriaPorCodigo(Integer codigoTarifa) throws RepositorioException {
		Cursor cursor = null;
		try {
			cursor = db.query(objeto.getNomeTabela(), objeto.getColunas(), ConsumosTarifasCategorias.CONSUMOTARIFA + "=" 
					+ codigoTarifa , null, null, null, null, null);

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
	
	public ConsumoTarifaCategoria buscarConsumoTarifaCategoriaPorIds(
			ConsumoTarifaCategoria consumoTarifaCatg, Integer idImovel)	throws RepositorioException {
		Cursor cursor = null;
		try {
			String condicao = ConsumosTarifasCategorias.CONSUMOTARIFA + "=" + consumoTarifaCatg.getConsumoTarifa() + " and " 
			+ ConsumosTarifasCategorias.IDCATEGORIA + "=" + consumoTarifaCatg.getIdCategoria();
			if(consumoTarifaCatg.getIdSubcategoria() != null){
				condicao += " and " + ConsumosTarifasCategorias.IDSUBCATEGORIA + " = " + consumoTarifaCatg.getIdSubcategoria();
			}else{
				condicao += " and " + ConsumosTarifasCategorias.IDSUBCATEGORIA + " is null";
			}
			
			cursor = db.query(objeto.getNomeTabela(), objeto.getColunas(),condicao, null,null, null, null, null);

			if (cursor.moveToFirst()) {
				List<ConsumoTarifaCategoria> colecao = objeto.preencherObjetos(cursor);
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

	public ArrayList<ConsumoTarifaCategoria> buscarConsumosTarifasCategorias(Integer imovelId) 
			throws RepositorioException {
		
		CategoriaSubcategoria categoriaSubcategoria = new CategoriaSubcategoria();	
		
		String query ="select * from "+objeto.getNomeTabela()+
				" join "+(new ImovelConta()).getNomeTabela()+" on "+ConsumosTarifasCategorias.CONSUMOTARIFA+" = "+
				ImovelContas.CODIGOTARIFA+" where "+ImovelContas.ID+" = "+imovelId+" and "+
				ConsumosTarifasCategorias.IDCATEGORIA+" = (select "+CategoriasSubcategorias.IDCATEGORIA
				+" from "+categoriaSubcategoria.getNomeTabela()+" where "+ImovelContas.ID+" = "+imovelId+")"+
				" order by cast( substr(" + ConsumosTarifasCategorias.DATAVIGENCIA +" ,7,4)||substr(" + ConsumosTarifasCategorias.DATAVIGENCIA + ",4,2)||substr("  + ConsumosTarifasCategorias.DATAVIGENCIA + ",1,2) as integer )"; 
		
		Cursor cursor = null;
		try {
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

	public ArrayList<ConsumoTarifaCategoria> buscarConsumosTarifasCategorias(int codigoTarifa, Date dataInicioVigencia) throws RepositorioException {
		Cursor cursor = null;
		try {
			cursor = db.query(objeto.getNomeTabela(), objeto.getColunas(), 
					ConsumosTarifasCategorias.CONSUMOTARIFA + "=" + codigoTarifa +
					" AND "+ConsumosTarifasCategorias.DATAVIGENCIA+"=\""+Util.convertDateToDateStr(dataInicioVigencia)+"\"",
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
	
	public ArrayList<Date> buscarVigenciasConsumosTarifasCategorias(int codigoTarifa) throws RepositorioException {
		Cursor cursor = null;

		ArrayList<Date> retorno=null;
		try {
			
			String query = "SELECT DISTINCT("+ConsumosTarifasCategorias.DATAVIGENCIA
					+") FROM "+ objeto.getNomeTabela() + " WHERE "+
					ConsumosTarifasCategorias.CONSUMOTARIFA + "=" + codigoTarifa + 
					" ORDER BY "+ConsumosTarifasCategorias.DATAVIGENCIA+" ASC ";
			cursor = db.rawQuery(query, null);
			
			if (cursor.moveToFirst()) {
				retorno = new ArrayList<Date>();

				//int dataVigencia = cursor.getColumnIndex(ConsumosTarifasCategorias.DATAVIGENCIA);
				
				do {	
										
					retorno.add(Util.convertStrToDataBusca(cursor.getString(0)));
					
				} while (cursor.moveToNext());
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
		return retorno;
	}
	
	public ConsumoTarifaCategoria buscarConsumoTarifaCategoriaId(
			Integer consumoTarifaId, Integer categoriaId, Integer subCategoriaId, String dataVigencia)	throws RepositorioException {
		Cursor cursor = null;
		ConsumoTarifaCategoria consumoTarifaCategoria = null;

		try {
			
			String query = "SELECT DISTINCT("+ConsumosTarifasCategorias.DATAVIGENCIA
					+") FROM "+ objeto.getNomeTabela() + " WHERE "+
					ConsumosTarifasCategorias.CONSUMOTARIFA + "=" + consumoTarifaId + 
					" ORDER BY "+ConsumosTarifasCategorias.DATAVIGENCIA+" ASC ";
			cursor = db.rawQuery(query, null);
			
			String condicao = ConsumosTarifasCategorias.CONSUMOTARIFA + "=" + consumoTarifaId + " and " 
			+ ConsumosTarifasCategorias.IDCATEGORIA + "=" + categoriaId + " and "
			+ ConsumosTarifasCategorias.DATAVIGENCIA+ "='"+dataVigencia+"'";
			if(subCategoriaId != null){
				condicao += " and " + ConsumosTarifasCategorias.IDSUBCATEGORIA + " = " + subCategoriaId;
			}else{
				condicao += " and " + ConsumosTarifasCategorias.IDSUBCATEGORIA + " is null";
			}
			
			cursor = db.query(objeto.getNomeTabela(), objeto.getColunas(),condicao, null,null, null, null, null);

			if (cursor.moveToFirst()) {

				consumoTarifaCategoria = new ConsumoTarifaCategoria();

				int codigo = cursor.getColumnIndex(ConsumosTarifasCategorias.ID);
				consumoTarifaCategoria.setId(cursor.getInt(codigo));			
				
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
		return consumoTarifaCategoria;
	}
}