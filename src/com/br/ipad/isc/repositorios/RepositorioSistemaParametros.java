
package com.br.ipad.isc.repositorios;

import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.SistemaParametros;
import com.br.ipad.isc.bean.SistemaParametros.SistemasParametros;
import com.br.ipad.isc.excecoes.RepositorioException;
import com.br.ipad.isc.util.ConstantesSistema;

public class RepositorioSistemaParametros extends RepositorioBasico implements IRepositorioSistemaParametros {
	
	private static RepositorioSistemaParametros instancia;	
	private SistemaParametros objeto;
	
	public void resetarInstancia(){
		instancia = null;
	}

	public static RepositorioSistemaParametros getInstance(){
		if ( instancia == null ){
			instancia =  new RepositorioSistemaParametros();
			instancia.objeto = new SistemaParametros();
		} 
		return instancia;
	}	

	public SistemaParametros buscarSistemaParametro() throws RepositorioException {
		Cursor cursor = null;
		try {
			cursor = db.query(objeto.getNomeTabela(), objeto.getColunas(),null , null,
					null, null, null, null);
			if (cursor.moveToFirst()) {
				List<SistemaParametros> colecao = objeto.preencherObjetos(cursor);
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

	public void atualizarSistemaParametros(SistemaParametros sistemaParametros) throws RepositorioException {
		ContentValues values = sistemaParametros.preencherValues();
		
		String _id = String.valueOf(sistemaParametros.getId());

		String where = SistemasParametros.ID + "=?";
		String[] whereArgs = new String[] { _id };

		try {
			db.update(objeto.getNomeTabela(), values, where, whereArgs);
			SistemaParametros.resetarInstancia();
		} catch (SQLException sqe) {
			sqe.printStackTrace();
			Log.e( ConstantesSistema.CATEGORIA , sqe.getMessage());
			throw new RepositorioException(context.getResources().getString(
					R.string.db_erro));
		}		
		SistemaParametros.resetarInstancia();		
	}
	
	public void atualizarIdImovelSelecionadoSistemaParametros(Integer idSelecionado) throws RepositorioException {
		ContentValues values = new ContentValues();
		values.put(SistemasParametros.IDIMOVELSELECIONADO, idSelecionado);
		
		String _id = String.valueOf(SistemaParametros.getInstancia().getId());

		String where = SistemasParametros.ID + "=?";
		String[] whereArgs = new String[] { _id };

		try {
			db.update(objeto.getNomeTabela(), values, where, whereArgs);
			SistemaParametros.resetarInstancia();
		} catch (SQLException sqe) {
			sqe.printStackTrace();
			Log.e( ConstantesSistema.CATEGORIA , sqe.getMessage());
			throw new RepositorioException(context.getResources().getString(
					R.string.db_erro));
		}		
		SistemaParametros.resetarInstancia();		
	}

	public void atualizarIdQtdImovelCondominioSistemaParametros(Integer idImovel, Integer qntImovelCondominio) throws RepositorioException {
		ContentValues values = new ContentValues();
		values.put(SistemasParametros.IDIMOVELCONDOMINIO, idImovel);
		values.put(SistemasParametros.QNTIMOVELCONDOMINIO, qntImovelCondominio);
		
		String _id = String.valueOf(SistemaParametros.getInstancia().getId());

		String where = SistemasParametros.ID + "=?";
		String[] whereArgs = new String[] { _id };

		try {
			db.update(objeto.getNomeTabela(), values, where, whereArgs);
			SistemaParametros.resetarInstancia();
		} catch (SQLException sqe) {
			sqe.printStackTrace();
			Log.e( ConstantesSistema.CATEGORIA , sqe.getMessage());
			throw new RepositorioException(context.getResources().getString(
					R.string.db_erro));
		}		
		SistemaParametros.resetarInstancia();		
	}
	
	public void atualizarQntImoveis() throws RepositorioException {
		Integer qnt = RepositorioImovelConta.getInstance().getQtdImoveis();
		ContentValues values = new ContentValues();
		values.put(SistemasParametros.QNTIMOVEIS, qnt);		
		
		String _id = String.valueOf(SistemaParametros.getInstancia().getId());

		String where = SistemasParametros.ID + "=?";
		String[] whereArgs = new String[] { _id };

		try {
			db.update(objeto.getNomeTabela(), values, where, whereArgs);
			
			//reseta instância para que não seja utilizada um objeto desatualizado
			SistemaParametros.resetarInstancia();
		} catch (SQLException sqe) {
			sqe.printStackTrace();
			Log.e( ConstantesSistema.CATEGORIA , sqe.getMessage());
			throw new RepositorioException(context.getResources().getString(
					R.string.db_erro));
		}
	}
	
	public void atualizarArquivoCarregadoBD() throws RepositorioException {
		
		ContentValues values = new ContentValues();
		values.put(SistemasParametros.INDICADORBANCOCARREGADO, ConstantesSistema.SIM);		
		
		String _id = String.valueOf(SistemaParametros.getInstancia().getId());

		String where = SistemasParametros.ID + "=?";
		String[] whereArgs = new String[] { _id };

		try {
			db.update(objeto.getNomeTabela(), values, where, whereArgs);
			SistemaParametros.resetarInstancia();
		} catch (SQLException sqe) {
			sqe.printStackTrace();
			Log.e( ConstantesSistema.CATEGORIA , sqe.getMessage());
			throw new RepositorioException(context.getResources().getString(
					R.string.db_erro));
		}
	}
	

	public void atualizarIndicadorRotaMarcacaoAtiva(Integer indicadorRotaMarcacaoAtiva) throws RepositorioException {
		
		ContentValues values = new ContentValues();
		values.put(SistemasParametros.INDICADORROTAMARCACAOATIVA, indicadorRotaMarcacaoAtiva);		
		
		String _id = String.valueOf(SistemaParametros.getInstancia().getId());

		String where = SistemasParametros.ID + "=?";
		String[] whereArgs = new String[] { _id };

		try {
			db.update(objeto.getNomeTabela(), values, where, whereArgs);
			SistemaParametros.resetarInstancia();
		} catch (SQLException sqe) {
			sqe.printStackTrace();
			Log.e( ConstantesSistema.CATEGORIA , sqe.getMessage());
			throw new RepositorioException(context.getResources().getString(
					R.string.db_erro));
		}
	}
	

	public void atualizarRoteiroOnlineOffline(Integer indicador) throws RepositorioException {
		ContentValues values = new ContentValues();
		
		values.put(SistemasParametros.INDICADORTRANSMISSAOOFFLINE, indicador);		
		
		String _id = String.valueOf(SistemaParametros.getInstancia().getId());
		String where = SistemasParametros.ID + "=?";
		String[] whereArgs = new String[] { _id };
		
		try {
			db.update(objeto.getNomeTabela(), values, where, whereArgs);
			SistemaParametros.resetarInstancia();
		} catch (SQLException sqe) {
			sqe.printStackTrace();
			Log.e( ConstantesSistema.CATEGORIA , sqe.getMessage());
			throw new RepositorioException(context.getResources().getString(
					R.string.db_erro));
		}
	}
	
}