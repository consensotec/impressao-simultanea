
package com.br.ipad.isc.repositorios;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.util.Log;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.HidrometroInstalado;
import com.br.ipad.isc.bean.HidrometroInstalado.HidrometrosInstalados;
import com.br.ipad.isc.excecoes.RepositorioException;
import com.br.ipad.isc.util.ConstantesSistema;

public class RepositorioHidrometroInstalado extends RepositorioBasico implements IRepositorioHidrometroInstalado {
	
	private static RepositorioHidrometroInstalado instancia;	
	private  HidrometroInstalado objeto;
	
	public void resetarInstancia(){
		instancia = null;
	}

	public static RepositorioHidrometroInstalado getInstance(){
		if ( instancia == null ){
			instancia =  new RepositorioHidrometroInstalado();
			instancia.objeto = new HidrometroInstalado();
		} 
		return instancia;
	}	

	public HidrometroInstalado buscarHidrometroInstaladoPorImovelTipoMedicao(Integer imovelId, Integer tipoMedicao)
			throws RepositorioException {
		Cursor cursor = null;
		try {
			
			cursor = db.query(objeto.getNomeTabela(), objeto.getColunas(), HidrometrosInstalados.MATRICULA + "=" + imovelId
					+" AND "+HidrometrosInstalados.MEDICAOTIPO + "=" + tipoMedicao,
					null, null, null, null, null);			
	
			if (cursor.moveToFirst()) {
				List<HidrometroInstalado> colecao = objeto.preencherObjetos(cursor);
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
	
	public HidrometroInstalado buscarLeituraHidrometroTipoMedicao(Integer imovelId, Integer tipoMedicao)
	throws RepositorioException {
		Cursor cursor = null;
		try {
			
			cursor = db.query(objeto.getNomeTabela(), objeto.getColunas(), HidrometrosInstalados.MATRICULA + "=" + imovelId
					+" AND "+HidrometrosInstalados.MEDICAOTIPO + "=" + tipoMedicao,
					null, null, null, null, null);			
		
			if (cursor.moveToFirst()) {
				List<HidrometroInstalado> colecao = objeto.preencherObjetos(cursor);
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
	
		
	public ArrayList<HidrometroInstalado> buscarHidrometroInstaladoImovel(Integer imovelId) throws RepositorioException {
		Cursor cursor = null;
		try {
			
			cursor = db.query(objeto.getNomeTabela(), objeto.getColunas(), HidrometrosInstalados.MATRICULA + "=" + imovelId 
					, null,null, null, null, null);

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
