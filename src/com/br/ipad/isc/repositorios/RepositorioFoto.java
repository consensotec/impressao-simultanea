package com.br.ipad.isc.repositorios;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.util.Log;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.Foto;
import com.br.ipad.isc.bean.Foto.Fotos;
import com.br.ipad.isc.excecoes.RepositorioException;
import com.br.ipad.isc.util.ConstantesSistema;

/**
 * @author Arthur Carvalho
 */
public class RepositorioFoto extends RepositorioBasico implements IRepositorioFoto {
		
	private static RepositorioFoto instancia;	
	private Foto objeto;
	
	public void resetarInstancia(){
		instancia = null;
	}

	public static RepositorioFoto getInstance(){
		if ( instancia == null ){
			instancia =  new RepositorioFoto();
			instancia.objeto = new Foto();
		} 
		return instancia;
	}	

	public Foto buscarFotoTipo(Integer id, Integer tipoFoto, Integer tipoMedicao,Integer idLeituraAnormalidade,Integer idConsumoAnormalidade) throws RepositorioException {
		
		Cursor cursor = null;
		
		String selection = Fotos.IMOVEL_CONTA_ID + "=? AND " + Fotos.FOTO_TIPO + "=? AND " + Fotos.MEDICAOTIPO + "=?";
		String[] selectionArgs = null;
		
		if(idLeituraAnormalidade!=null)
		{
			selection+=" AND "+Fotos.LEITURA_ANORMALIDADE_ID +"=? ";
			
			selectionArgs = new String[] 
			        {
			              String.valueOf(id),
			              String.valueOf(tipoFoto),
			              String.valueOf(tipoMedicao),
			              String.valueOf(idLeituraAnormalidade)
			        };
		}
			
		if(idConsumoAnormalidade!=null)
		{
			selection+=" AND "+Fotos.CONSUMO_ANORMALIDADE_ID +"=? ";
			
			selectionArgs = new String[] 
			        {
			              String.valueOf(id),
			              String.valueOf(tipoFoto),
			              String.valueOf(tipoMedicao),
			              String.valueOf(idConsumoAnormalidade)
			        };
		}
		
		try {
			cursor = db.query(objeto.getNomeTabela(), objeto.getColunas(), selection , selectionArgs,
					null, null, "FOTO_ID DESC", null);

			if (cursor.moveToFirst()) {
				List<Foto> colecao = objeto.preencherObjetos(cursor);
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

	public ArrayList<Foto> buscarFotos(Integer idImovel, Integer tipoMedicao) throws RepositorioException {
		Cursor cursor = null;
		try {
			String selection = Fotos.IMOVEL_CONTA_ID + "=? AND " + Fotos.MEDICAOTIPO + "=?";
	        String[] selectionArgs = new String[] {
	                
	                String.valueOf(idImovel),
	                // encerradas
	                String.valueOf(tipoMedicao)};
			
			cursor = db.query(objeto.getNomeTabela(), objeto.getColunas(), selection, selectionArgs,
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
	
	public ArrayList<Foto> buscarFotos(String selection,  String[] selectionArgs) throws RepositorioException {
		Cursor cursor = null;
		try {
			cursor = db.query(objeto.getNomeTabela(), objeto.getColunas(), selection, selectionArgs,
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
	
	public ArrayList<Foto> buscarFotosPendentes() throws RepositorioException {
		Cursor cursor = null;
		try {
			String selection = Fotos.INDICADOR_TRANSMITIDO + "=? ";
	        String[] selectionArgs = new String[] {String.valueOf(ConstantesSistema.NAO)};
	        
			cursor = db.query(objeto.getNomeTabela(), objeto.getColunas(), selection, selectionArgs,null, null, null, null);

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
	
	public ArrayList<Foto> buscarFotosPendentes(Integer id) throws RepositorioException {
		Cursor cursor = null;
		try {
			String selection = Fotos.IMOVEL_CONTA_ID + "=? AND " + Fotos.INDICADOR_TRANSMITIDO + "=?";
	        String[] selectionArgs = new String[] {
	                
	                String.valueOf(id),
	                // encerradas
	                String.valueOf(ConstantesSistema.NAO)};
	        
			cursor = db.query(objeto.getNomeTabela(), objeto.getColunas(), selection, selectionArgs,null, null, null, null);

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
