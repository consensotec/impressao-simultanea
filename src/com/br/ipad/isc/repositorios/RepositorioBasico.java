
package com.br.ipad.isc.repositorios;

import java.io.File;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.ObjetoBasico;
import com.br.ipad.isc.bean.SistemaParametros;
import com.br.ipad.isc.controladores.ControladorBasico;
import com.br.ipad.isc.controladores.ControladorCategoriaSubcategoria;
import com.br.ipad.isc.controladores.ControladorConsumoAnormalidade;
import com.br.ipad.isc.controladores.ControladorConsumoAnormalidadeAcao;
import com.br.ipad.isc.controladores.ControladorConsumoAnteriores;
import com.br.ipad.isc.controladores.ControladorConsumoHistorico;
import com.br.ipad.isc.controladores.ControladorConsumoTarifaCategoria;
import com.br.ipad.isc.controladores.ControladorConsumoTarifaFaixa;
import com.br.ipad.isc.controladores.ControladorConsumoTipo;
import com.br.ipad.isc.controladores.ControladorContaCategoria;
import com.br.ipad.isc.controladores.ControladorContaCategoriaConsumoFaixa;
import com.br.ipad.isc.controladores.ControladorContaDebito;
import com.br.ipad.isc.controladores.ControladorContaImposto;
import com.br.ipad.isc.controladores.ControladorCreditoRealizado;
import com.br.ipad.isc.controladores.ControladorDebitoCobrado;
import com.br.ipad.isc.controladores.ControladorFoto;
import com.br.ipad.isc.controladores.ControladorHidrometroInstalado;
import com.br.ipad.isc.controladores.ControladorImovelConta;
import com.br.ipad.isc.controladores.ControladorLeituraAnormalidade;
import com.br.ipad.isc.controladores.ControladorQualidadeAgua;
import com.br.ipad.isc.controladores.ControladorSequencialRotaMarcacao;
import com.br.ipad.isc.controladores.ControladorSistemaParametros;
import com.br.ipad.isc.excecoes.RepositorioException;
import com.br.ipad.isc.util.ConstantesSistema;
import com.br.ipad.isc.util.SQLiteHelper;
import com.br.ipad.isc.util.Util;

public class RepositorioBasico implements IRepositorioBasico {

	protected static Context context;
	private static RepositorioBasico instancia;

	public static final String NOME_BANCO = "isc_banco";
	public static final String CAMINHO_BANCO = "data/data/com.br.ipad.isc/databases/";

	protected static SQLiteDatabase db;
	private static SQLiteHelper dbHelper;

	
	private void abrirBanco() {

			try {			
				fecharBanco();
				
				if ( (!registrarBanco() || db == null)  
						|| (db != null && !db.isOpen()) ) {
					
					BDScript bDScript = new BDScript();
					
					dbHelper = new SQLiteHelper(context,NOME_BANCO,Util.getVersaoCode(context),
												bDScript.obterScriptBanco(),bDScript.obterScriptExcluirBanco());
					if(dbHelper!=null){
						synchronized (dbHelper){
							db = dbHelper.getWritableDatabase();
						}
					}
				}
				
			} catch (RepositorioException e) {
				e.printStackTrace();
				Log.e(ConstantesSistema.CATEGORIA, e.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				Log.e(ConstantesSistema.CATEGORIA, e.getMessage());
			}
		}

	public static void setContext(Context c) {
		context = c;
	}

	public void fecharBanco() throws RepositorioException {
		if (db != null) {
			try {
				db.close();
			} catch (SQLException sqe) {
				sqe.printStackTrace();
				Log.e(ConstantesSistema.CATEGORIA, sqe.getMessage());
				throw new RepositorioException(context.getResources().getString(R.string.db_erro));
			}
		}
		if (dbHelper != null) {
			dbHelper.close();
		}
	}

	protected RepositorioBasico() {
		super();

		if (db == null || (db != null && !db.isOpen())) {
			abrirBanco();
		}
	}

	public static RepositorioBasico getInstance() {
		if (instancia == null) {
			instancia = new RepositorioBasico();
		}
		return instancia;
	}

	public static boolean registrarBanco() {
		SQLiteDatabase registrarDb = null;

		try {
			registrarDb = SQLiteDatabase.openDatabase(CAMINHO_BANCO + NOME_BANCO, null, SQLiteDatabase.OPEN_READONLY);
			registrarDb.close();
		} catch (SQLiteException e) {
			e.printStackTrace();
			Log.d(ConstantesSistema.CATEGORIA, "RepositorioBasico.existeBanco(): Não");
		}

		return registrarDb != null ? true : false;
	}

	public void apagarBanco() {
		
		try {
			
			fecharBanco();

			instancia = null;
						
			File file = new File(CAMINHO_BANCO+NOME_BANCO+"-journal" );
			
			if ( file.exists() ){
				file.delete();
			}
			
			if (context.deleteDatabase(NOME_BANCO)) {
				Log.d(ConstantesSistema.CATEGORIA, "apagarBanco(): Banco de dados deletado.");
			} else {
				Log.d(ConstantesSistema.CATEGORIA, "apagarBanco(): Banco de dados não deletado.");
			}

		} catch (RepositorioException e) {
			Log.d(ConstantesSistema.CATEGORIA, "apagarBanco(): Banco de dados não deletado.");
		} finally {
			db = null;
			dbHelper = null;
			CarregaBD.CONTADOR_IMOVEL = 0;
		}
	
	}

	public void resetarInstancias() {
		
		RepositorioCategoriaSubcategoria.getInstance().resetarInstancia();
		RepositorioConsumoAnormalidade.getInstance().resetarInstancia();
		RepositorioConsumoAnormalidadeAcao.getInstance().resetarInstancia();
		RepositorioConsumoAnteriores.getInstance().resetarInstancia();
		RepositorioConsumoHistorico.getInstance().resetarInstancia();
		RepositorioConsumoTarifaCategoria.getInstance().resetarInstancia();
		RepositorioConsumoTarifaFaixa.getInstance().resetarInstancia();
		RepositorioConsumoTipo.getInstance().resetarInstancia();
		RepositorioContaCategoria.getInstance().resetarInstancia();
		RepositorioContaCategoriaConsumoFaixa.getInstance().resetarInstancia();
		RepositorioContaDebito.getInstance().resetarInstancia();
		RepositorioContaImposto.getInstance().resetarInstancia();
		RepositorioCreditoRealizado.getInstance().resetarInstancia();
		RepositorioDebitoCobrado.getInstance().resetarInstancia();
		RepositorioFaturamentoSituacaoTipo.getInstance().resetarInstancia();
		RepositorioFoto.getInstance().resetarInstancia();
		RepositorioHidrometroInstalado.getInstance().resetarInstancia();	
		RepositorioImovelConta.getInstance().resetarInstancia();
		RepositorioLeituraAnormalidade.getInstance().resetarInstancia();
		RepositorioQualidadeAgua.getInstance().resetarInstancia();
		RepositorioSequencialRotaMarcacao.getInstance().resetarInstancia();
		RepositorioSistemaParametros.getInstance().resetarInstancia();
		
		ControladorBasico.getInstance().resetarInstancia();
		ControladorCategoriaSubcategoria.getInstance().resetarInstancia();
		ControladorConsumoAnormalidade.getInstance().resetarInstancia();
		ControladorConsumoAnormalidadeAcao.getInstance().resetarInstancia();
		ControladorConsumoAnteriores.getInstance().resetarInstancia();
		ControladorConsumoHistorico.getInstance().resetarInstancia();
		ControladorConsumoTarifaCategoria.getInstance().resetarInstancia();
		ControladorConsumoTarifaFaixa.getInstance().resetarInstancia();
		ControladorConsumoTipo.getInstance().resetarInstancia();
		ControladorContaCategoria.getInstance().resetarInstancia();
		ControladorContaCategoriaConsumoFaixa.getInstance().resetarInstancia();
		ControladorContaDebito.getInstance().resetarInstancia();
		ControladorContaImposto.getInstance().resetarInstancia();
		ControladorCreditoRealizado.getInstance().resetarInstancia();
		ControladorDebitoCobrado.getInstance().resetarInstancia();
		ControladorFoto.getInstance().resetarInstancia();
		ControladorHidrometroInstalado.getInstance().resetarInstancia();	
		ControladorImovelConta.getInstance().resetarInstancia();
		ControladorLeituraAnormalidade.getInstance().resetarInstancia();
		ControladorQualidadeAgua.getInstance().resetarInstancia();
		ControladorSistemaParametros.getInstance().resetarInstancia();
		ControladorSequencialRotaMarcacao.getInstance().resetarInstancia();
		
		
	}
	
	public boolean verificarExistenciaBancoDeDados() {

		boolean retorno = false;	  

        if(registrarBanco()){
        	SistemaParametros.resetarInstancia();
	    	if(SistemaParametros.getInstancia() != null && SistemaParametros.getInstancia().getIndicadorBancoCarregado().equals(ConstantesSistema.SIM)){
	    		retorno = true;
	    	}
        }
	    return retorno;
	}	
	
	/**
	 * Atualiza todos os campos do objeto no banco de dados
	 * @author Amelia Pessoa
	 * @param objeto
	 * @throws RepositorioException
	 */
	public void atualizar(ObjetoBasico objeto) throws RepositorioException {
		ContentValues values = objeto.preencherValues();
		
		String _id = String.valueOf(objeto.getId());

		String where = objeto.getNameId() + "=?";
		String[] whereArgs = new String[] { _id };

		try {
			db.update(objeto.getNomeTabela(), values, where, whereArgs);
		} catch (SQLException sqe) {
			sqe.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , sqe.getMessage());
			throw new RepositorioException(context.getResources().getString(
					R.string.db_erro));
		}		
	}
	
	/**
	 * Remover objeto do BD
	 * @author Amelia Pessoa
	 * @param objeto
	 * @throws RepositorioException
	 */
	public void remover(ObjetoBasico objeto) throws RepositorioException {
		String _id = String.valueOf(objeto.getId());

		String where = objeto.getNameId() + "=?";
		String[] whereArgs = new String[] { _id };

		try {
			db.delete(objeto.getNomeTabela(), where, whereArgs);
		} catch (SQLException sqe) {
			sqe.printStackTrace();
			Log.e( ConstantesSistema.CATEGORIA , sqe.getMessage());
			throw new RepositorioException(context.getResources().getString(
					R.string.db_erro));
		}
	}
	
	/**
	 * Insere objeto no BD e retorna id gerado
	 * @author Amelia Pessoa
	 * @param objeto
	 * @throws RepositorioException
	 */
	public long inserir(ObjetoBasico objeto) throws RepositorioException {
		try {
			long idObjetoInserido = ConstantesSistema.ERRO_INSERIR_REGISTRO_BD;
			ContentValues values = objeto.preencherValues();
			
			idObjetoInserido = db.insert(objeto.getNomeTabela(), null, values);
				
			if(idObjetoInserido!=ConstantesSistema.ERRO_INSERIR_REGISTRO_BD){
				return idObjetoInserido;
			}else{
				throw new RepositorioException(context.getResources().getString(
						R.string.db_erro_inserir_registro));
			}
			
		}catch (SQLException sqe) {
			sqe.printStackTrace();
			Log.e( ConstantesSistema.CATEGORIA , sqe.getMessage());
			throw new RepositorioException(context.getResources().getString(
					R.string.db_erro_inserir_registro));
		}
	}
	
	/**
	 * Pesquisa objeto com base no id 
	 * Recebe como parametro objeto de tipo igual ao seu
	 * @author Amelia Pessoa
	 * @param objeto
	 * @throws RepositorioException
	 */
	public <T extends ObjetoBasico> T pesquisarPorId(Integer id, T objetoTipo) throws RepositorioException {
		Cursor cursor = null;
		ArrayList<T> colecao = null;

		try {
			cursor = db.query(objetoTipo.getNomeTabela(), objetoTipo.getColunas(), 
					objetoTipo.getNameId() + "=" + id , null,
					null, null, null, null);

			if (cursor.moveToFirst()) {
				colecao = objetoTipo.preencherObjetos(cursor); 
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
	
	/**
	 * Pesquisa lista de objetos 
	 * Recebe como parametro objeto de tipo igual ao seu
	 * @author Amelia Pessoa
	 * @param objeto
	 * @throws RepositorioException
	 */
	public <T extends ObjetoBasico>  ArrayList<T> pesquisar(T objetoTipo) throws RepositorioException {
		Cursor cursor = null;
		
		try {
			cursor = db.query(objetoTipo.getNomeTabela(), objetoTipo.getColunas(), null, null,
					null, null, null, null);

			if (cursor.moveToFirst()) {
				return objetoTipo.preencherObjetos(cursor);				
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