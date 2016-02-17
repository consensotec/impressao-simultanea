
package com.br.ipad.isc.repositorios;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.HidrometroInstalado;
import com.br.ipad.isc.bean.HidrometroInstalado.HidrometrosInstalados;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.bean.ImovelConta.ImovelContas;
import com.br.ipad.isc.excecoes.ControladorException;
import com.br.ipad.isc.excecoes.RepositorioException;
import com.br.ipad.isc.util.ConstantesSistema;

public class RepositorioImovelConta extends RepositorioBasico implements IRepositorioImovelConta {
	
	private static RepositorioImovelConta instancia;	
	private ImovelConta objeto;
	
	public void resetarInstancia(){
		instancia = null;
	}

	public static RepositorioImovelConta getInstance(){
		if ( instancia == null ){
			instancia =  new RepositorioImovelConta();
			instancia.objeto = new ImovelConta();
		} 
		return instancia;
	}	

	public Integer getQtdImoveis() throws RepositorioException {
		Cursor cursor = null;
		Integer total = null;

		try {

			String query = "SELECT COUNT("+ImovelContas.ID+") AS total FROM "+ objeto.getNomeTabela();

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
	
	public ArrayList<ImovelConta> buscarImovelContas() throws RepositorioException {
		Cursor cursor = null;

		ArrayList<ImovelConta> imoveisContas = null;
		try {
			imoveisContas = new ArrayList<ImovelConta>();
			cursor = db.query(objeto.getNomeTabela(), objeto.getColunas(), null, null,
					null, null, ImovelContas.POSICAO);

			if (cursor.moveToFirst()) {
				int codigo = cursor.getColumnIndex(ImovelContas.ID);
				int endereco = cursor.getColumnIndex(ImovelContas.ENDERECO);
				int indicadorImovelCalculado = cursor.getColumnIndex(ImovelContas.INDICADORIMOVCALCULADO);
				int indicadorImovelEnviado = cursor.getColumnIndex(ImovelContas.INDICADORIMOVELENVIADO);
				int indicadorImovelImpresso = cursor.getColumnIndex(ImovelContas.INDICADORIMOVELIMPRESSO);
				int nomeUsuario = cursor.getColumnIndex(ImovelContas.NOMEUSUARIO);	
				int posicao = cursor.getColumnIndex(ImovelContas.POSICAO);		
				
				//Necessarios para verificacao do imovel condominio
				int icCondominio = cursor.getColumnIndex(ImovelContas.INDICADORIMOVELCONDOMINIO);
				int matriculaCondominio = cursor.getColumnIndex(ImovelContas.IDIMOVELCONDOMINIO);
				int posicaoCondominio = cursor.getColumnIndex(ImovelContas.POSICAOIMOVELCONDOMINIO);
				
				do {	
					ImovelConta imovelConta = new ImovelConta();
					imovelConta.setId(cursor.getInt(codigo));		
					imovelConta.setNomeUsuario(cursor.getString(nomeUsuario));			
					imovelConta.setEndereco(cursor.getString(endereco));					
					imovelConta.setIndcImovelCalculado(cursor.getInt(indicadorImovelCalculado ));
					imovelConta.setIndcImovelEnviado(cursor.getInt(indicadorImovelEnviado));
					imovelConta.setIndcImovelImpresso(cursor.getInt(indicadorImovelImpresso ));
					
					imovelConta.setPosicao(cursor.getInt(posicao));		
					imovelConta.setIndcCondominio(cursor.getInt(icCondominio));
					imovelConta.setMatriculaCondominio(cursor.getInt(matriculaCondominio));
					imovelConta.setPosicaoImovelCondominio(cursor.getInt(posicaoCondominio));
					
					imoveisContas.add(imovelConta);
					
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
		return imoveisContas;
	}
	
	
	/**
	 * Retorna imóveis NAO LIDOS 
	 * E, PARA IMOVEIS CONDOMINIOS, retorna os imóveis não rateados
	 * @return
	 * @throws RepositorioException
	 */
	
	public ArrayList<ImovelConta> buscarImovelContasNaoLidos() throws RepositorioException {
		Cursor cursor = null;

		ArrayList<ImovelConta> imoveisContas = null;
		try {
			imoveisContas = new ArrayList<ImovelConta>();
			
			String query = "SELECT * FROM " + objeto.getNomeTabela() + " WHERE ("+ImovelContas.INDICADORIMOVCALCULADO + 
					"=" + ConstantesSistema.NAO+ " AND " + ImovelContas.IDIMOVELCONDOMINIO + " is null AND "+
					ImovelContas.INDICADORIMOVELCONDOMINIO + "=" + ConstantesSistema.NAO+ ") or " +
					"(("+ImovelContas.IDIMOVELCONDOMINIO+" is not null OR " + ImovelContas.INDICADORIMOVELCONDOMINIO + "=" + ConstantesSistema.SIM+ ") " +
					"and " + ImovelContas.INDICADORRATEIOREALIZADO+ "=" + ConstantesSistema.NAO+ ") ORDER BY "+ ImovelContas.POSICAO;
					
			cursor = db.rawQuery(query, null);
			
			if (cursor.moveToFirst()) {
				int codigo = cursor.getColumnIndex(ImovelContas.ID);
				int endereco = cursor.getColumnIndex(ImovelContas.ENDERECO);
				int indicadorImovelCalculado = cursor.getColumnIndex(ImovelContas.INDICADORIMOVCALCULADO);
				int indicadorImovelEnviado = cursor.getColumnIndex(ImovelContas.INDICADORIMOVELENVIADO);
				int indicadorImovelImpresso = cursor.getColumnIndex(ImovelContas.INDICADORIMOVELIMPRESSO);
				int nomeUsuario = cursor.getColumnIndex(ImovelContas.NOMEUSUARIO);	
				int posicao = cursor.getColumnIndex(ImovelContas.POSICAO);		
				
				//Necessarios para verificacao do imovel condominio
				int icCondominio = cursor.getColumnIndex(ImovelContas.INDICADORIMOVELCONDOMINIO);
				int matriculaCondominio = cursor.getColumnIndex(ImovelContas.IDIMOVELCONDOMINIO);
				int posicaoCondominio = cursor.getColumnIndex(ImovelContas.POSICAOIMOVELCONDOMINIO);
				
				do {	
					ImovelConta imovelConta = new ImovelConta();
					imovelConta.setId(cursor.getInt(codigo));		
					imovelConta.setNomeUsuario(cursor.getString(nomeUsuario));			
					imovelConta.setEndereco(cursor.getString(endereco));					
					imovelConta.setIndcImovelCalculado(cursor.getInt(indicadorImovelCalculado ));
					imovelConta.setIndcImovelEnviado(cursor.getInt(indicadorImovelEnviado));
					imovelConta.setIndcImovelImpresso(cursor.getInt(indicadorImovelImpresso ));			
					imovelConta.setPosicao(cursor.getInt(posicao));		

					imovelConta.setIndcCondominio(cursor.getInt(icCondominio));
					imovelConta.setMatriculaCondominio(cursor.getInt(matriculaCondominio));
					imovelConta.setPosicaoImovelCondominio(cursor.getInt(posicaoCondominio));
					
					imoveisContas.add(imovelConta);
					
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
		return imoveisContas;
	}

	/**
	 * Retorna um imovel condominio completo micro+macro
	 * @param Integer idImovelMacro
	 * @author Carlos Chaves
	 * @return Integer
	 * @throws RepositorioException
	 */
	
	public ArrayList<ImovelConta> buscarImovelCondominio(Integer idImovelMacro) throws RepositorioException {
		Cursor cursor = null;

		ArrayList<ImovelConta> imoveisContas = null;
		try {
			imoveisContas = new ArrayList<ImovelConta>();
			cursor = db.query(objeto.getNomeTabela(), objeto.getColunas(), 
					ImovelContas.IDIMOVELCONDOMINIO + "=" + idImovelMacro 
					+ " OR " +ImovelContas.ID+ " = " +idImovelMacro , null,
					null, null, ImovelContas.POSICAOIMOVELCONDOMINIO +" DESC");
			

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
		return imoveisContas;
	}
	
	public ArrayList<Integer> getIdsNaoLidos() throws RepositorioException {
		Cursor cursor = null;
		ArrayList<Integer> ids = null;

		try {
			ids = new ArrayList<Integer>();
			
			String query = "SELECT "+ ImovelContas.ID +" FROM "+ objeto.getNomeTabela() + 
			" WHERE "+ImovelContas.INDICADORIMOVCALCULADO + " = "+ ConstantesSistema.NAO;
			cursor = db.rawQuery(query, null);
			
			if (cursor.moveToFirst()) {				
				int codigo = cursor.getColumnIndex(ImovelContas.ID);
			
				do{
					Integer id = cursor.getInt(codigo);
					
					ids.add(id);
					
				}while(cursor.moveToNext());
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
		return ids;
	}
	
	public ImovelConta buscarImovelContaPorHidrometro(String hidrometroNumero) throws RepositorioException {
		Cursor cursor = null;
		try {
			String query = "SELECT * FROM "+objeto.getNomeTabela()+" imov INNER JOIN " +(new HidrometroInstalado()).getNomeTabela() +" hidr" +
					" ON imov.imov_id=hidr.imov_id WHERE "+ HidrometrosInstalados.NUMEROHIDROMETRO +" =?";

			cursor = db.rawQuery(query, new String[]{String.valueOf(hidrometroNumero).toUpperCase( new Locale( "pt", "BR" ) )});
		
			if (cursor.moveToFirst()) {
				List<ImovelConta> colecao = objeto.preencherObjetos(cursor);
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
	
	public ArrayList<ImovelConta> buscarImovelContaPorQuadra(String numeroQuadra) throws RepositorioException {
		Cursor cursor = null;
		try {
			cursor = db.query(objeto.getNomeTabela(), objeto.getColunas(), ImovelContas.NUMEROQUADRA + "=" + numeroQuadra , null,
					null, null, ImovelContas.POSICAO);

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
	
	public ArrayList<ImovelConta> buscarImovelContasLidos() throws RepositorioException {
		Cursor cursor = null;
		try {
			cursor = db.query(objeto.getNomeTabela(), objeto.getColunas(),ImovelContas.INDICADORIMOVCALCULADO + " = "+ ConstantesSistema.SIM , null,
					null, null, null, null);

			if(cursor.moveToFirst()){
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
	public ImovelConta buscarImovelContaPosicao(Integer posicao) throws RepositorioException {
		Cursor cursor = null;
		try {
			cursor = db.query(objeto.getNomeTabela(), objeto.getColunas(), ImovelContas.POSICAO + "=" +posicao, null,
					null, null, null);

			if (cursor.moveToFirst()) {
				List<ImovelConta> colecao = objeto.preencherObjetos(cursor);
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

	/**
	 * Retorna os ids dos imóveis não calculados
	 * vinculados ao imóvel macro passado como parametro.
	 * 
	 * @author Amelia Pessoa
	 * @param idImovelMacro
	 * @return ArrayList<Integer>
	 * @throws ControladorException
	 */
	public ArrayList<Integer> buscarImovelCondominiosNaoCalculados(Integer idImovelMacro) throws RepositorioException {
		Cursor cursor = null;

		ArrayList<Integer> imoveisContas;
		try {
			imoveisContas = new ArrayList<Integer>();
			
			String query = "SELECT "+ImovelContas.ID+" FROM "+ objeto.getNomeTabela() + " WHERE "+
					ImovelContas.INDICADORIMOVCALCULADO + "=" + ConstantesSistema.NAO +
					" AND "+ ImovelContas.IDIMOVELCONDOMINIO + "=" + idImovelMacro;
			
			cursor = db.rawQuery(query, null);

			if (cursor.moveToFirst()) {
				int codigo = cursor.getColumnIndex(ImovelContas.ID);
				
				do {	
					Integer imovelConta = cursor.getInt(codigo);
					imoveisContas.add(imovelConta);
					
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
		return imoveisContas;
	}

	/**
	 * Retorna os ids dos imóveis não impressos
	 * vinculados ao imóvel macro passado como parametro.
	 * + anterior dependendo do parametro
	 * 
	 * @author Amelia Pessoa
	 * @param idImovelMacro
	 * @return ArrayList<Integer>
	 * @throws ControladorException
	 */
	public ArrayList<Integer> buscarImovelCondominiosNaoImpressos(Integer idImovelMacro) throws RepositorioException {
	
		Cursor cursor = null;

		ArrayList<Integer> imoveisContas;
		try {
			imoveisContas = new ArrayList<Integer>();
			
			String query = "SELECT "+ImovelContas.ID+" FROM "+ objeto.getNomeTabela() + " WHERE "+
					ImovelContas.INDICADORIMOVELIMPRESSO + "=" + ConstantesSistema.NAO +
					" AND "+ ImovelContas.INDICADORNAOPERMITEIMPRESSAO+ "=" + ConstantesSistema.NAO +
					" AND "+ ImovelContas.IDIMOVELCONDOMINIO + "=" + idImovelMacro+
					"  ORDER  BY "+ ImovelContas.POSICAO;
			
			cursor = db.rawQuery(query, null);

			if (cursor.moveToFirst()) {
				int codigo = cursor.getColumnIndex(ImovelContas.ID);
				
				do {	
					Integer imovelConta = cursor.getInt(codigo);
					imoveisContas.add(imovelConta);
					
				} while (cursor.moveToNext());
				
				//Buscar anterior			
				String queryAnterior = "SELECT "+ImovelContas.ID+" FROM "+ objeto.getNomeTabela() + " WHERE "+
						ImovelContas.POSICAOIMOVELCONDOMINIO+" = (SELECT "+ImovelContas.POSICAOIMOVELCONDOMINIO+
						"-1 FROM "+objeto.getNomeTabela() + " WHERE "+ ImovelContas.ID +"="+
						imoveisContas.get(0)+")" + 
						" AND "+ ImovelContas.IDIMOVELCONDOMINIO + "=" + idImovelMacro;					
				
				cursor = null;
				cursor = db.rawQuery(queryAnterior, null);

				if (cursor.moveToFirst()) {
					int codigoAnt = cursor.getColumnIndex(ImovelContas.ID);
					
					Integer imovelConta = cursor.getInt(codigoAnt);
					imoveisContas.add(0,imovelConta);
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
		return imoveisContas;
	}
	
	/**
	 * Retorna o ImovelAreaComum para o ImovelMacro
	 * passado como parâmetro
	 * 
	 * @author Amelia Pessoa
	 * @param imovelMacro
	 * @return
	 */
	public ImovelConta obterImovelAreaComum(Integer idImovelMacro) throws RepositorioException{
		Cursor cursor = null;
		try {
			String query = "SELECT * FROM "+ objeto.getNomeTabela() + " WHERE "+
					ImovelContas.INDICADORAREACOMUM + "=" + ConstantesSistema.SIM +
					" AND "+ ImovelContas.IDIMOVELCONDOMINIO + "=" + idImovelMacro;			
			cursor = db.rawQuery(query, null);
			
			if (cursor.moveToFirst()) {
				List<ImovelConta> colecao = objeto.preencherObjetos(cursor);
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
	
	/**
	 * Retorna o id do último imóvel micro
	 * para o ImovelMacro passado como parâmetro
	 * 
	 * @author Amelia Pessoa
	 * @param idImovelMacro
	 * @return
	 */
	public Integer obterIdUltimoImovelMicro(Integer idImovelMacro) throws RepositorioException{
		Cursor cursor = null;
		
		try {
			String query = "SELECT "+ImovelContas.POSICAOIMOVELCONDOMINIO+", "+ImovelContas.ID+" as id FROM "+ objeto.getNomeTabela() + " WHERE "+
					ImovelContas.IDIMOVELCONDOMINIO + "=" + idImovelMacro+ " ORDER BY "+ImovelContas.POSICAOIMOVELCONDOMINIO+" DESC LIMIT 1 ";
			cursor = db.rawQuery(query, null);
			
			if (cursor.moveToFirst()) {
				int codigo = cursor.getColumnIndex("id");
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
	
	/**
	 * Retorna a quantidade de imoveis vinculados ao ImovelMacro passado como parâmetro
	 *  
	 * @param idImovelMacro
	 * @return
	 */
	public Integer obterQuantidadeImovelMicro(Integer idImovelMacro) throws RepositorioException {	
		Cursor cursor = null;
		
		try {
			String query = "SELECT COUNT(*) as qnt FROM "+ objeto.getNomeTabela() + " WHERE "+
					ImovelContas.IDIMOVELCONDOMINIO + "=" + idImovelMacro;			
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
	
	/**
	 * Retorna os ids dos imoveis micro vinculados ao ImovelMacro passado como parâmetro
	 * @author Amelia Pessoa
	 *  
	 * @param idImovelMacro
	 * @return
	 */
	public ArrayList<Integer> buscarIdsImoveisMicro(Integer idImovelMacro) throws RepositorioException {
		Cursor cursor = null;
		ArrayList<Integer> ids = null;

		try {
			ids = new ArrayList<Integer>();
			
			String query = "SELECT "+ ImovelContas.ID +" FROM "+ objeto.getNomeTabela() + 
					" WHERE "+ImovelContas.IDIMOVELCONDOMINIO + "=" + idImovelMacro+
					" ORDER BY "+ImovelContas.POSICAO;
			
			cursor = db.rawQuery(query, null);
			
			if (cursor.moveToFirst()) {				
				int codigo = cursor.getColumnIndex(ImovelContas.ID);
			
				do{
					Integer id = cursor.getInt(codigo);
					
					ids.add(id);
					
				}while(cursor.moveToNext());
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
		return ids;
	}
	
	/**
	 * Retorna o id do primeiro imovel micro não calculado.
	 * Se não houver nenhum, retorna null
	 * 
	 * @author Amelia Pessoa
	 *  
	 * @param idImovelMacro
	 * @return
	 */
	public Integer obterPosicaoImovelCondominioNaoCalculado(Integer idImovelMacro) throws RepositorioException{
		Cursor cursor = null;
		
		try {
			String query = "SELECT "+ImovelContas.POSICAO+" as posicao FROM "+ objeto.getNomeTabela() + 
					" WHERE ("+ImovelContas.ID +"="+idImovelMacro+" OR "+
					ImovelContas.IDIMOVELCONDOMINIO + "=" + idImovelMacro+") AND "+
					ImovelContas.INDICADORIMOVCALCULADO + "=" + ConstantesSistema.NAO+
					" ORDER BY "+ImovelContas.POSICAO;
			
			cursor = db.rawQuery(query, null);
			
			if (cursor.moveToFirst()) {
				int posicao = cursor.getColumnIndex("posicao");
				return cursor.getInt(posicao);
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
	 * Busca nos imóveis micro vinculado ao macro passado
	 * como parametro algum que tenha indcRateio = NAO (inclusive o proprio macro)
	 * 
	 * @author Amelia Pessoa
	 * @param idImovelMacro
	 * @return id do imovel não rateado
	 * @throws ControladorException
	 */
	public Integer verificarRateioCondominio(Integer idImovelMacro) throws RepositorioException {
		Cursor cursor = null;
		
		try {
			String query = "SELECT "+ImovelContas.ID+" as id FROM "+ objeto.getNomeTabela() + 
					" WHERE ("+ImovelContas.IDIMOVELCONDOMINIO + "=" + idImovelMacro+" OR "+
					ImovelContas.ID + "= "+idImovelMacro+")"+
					" AND "+ImovelContas.INDICADORRATEIOREALIZADO + "=" + ConstantesSistema.NAO;
			
			cursor = db.rawQuery(query, null);
			
			if (cursor.moveToFirst()) {
				int posicao = cursor.getColumnIndex("id");
				return cursor.getInt(posicao);
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
	 * Verifica se já existe algum imóvel impresso para o
	 * imóvel macro passado como parametro
	 *  
	 * @author Amelia Pessoa
	 * @param id
	 * @return
	 */
	public Integer existeImovelImpresso(Integer idImovelMacro) throws RepositorioException {
	
		Cursor cursor = null;

		try {
			String query = "SELECT "+ImovelContas.ID+" as id FROM "+ objeto.getNomeTabela() + " WHERE "+
					ImovelContas.INDICADORIMOVELIMPRESSO + "=" + ConstantesSistema.SIM +
					" AND "+ ImovelContas.IDIMOVELCONDOMINIO + "=" + idImovelMacro;
			
			cursor = db.rawQuery(query, null);

			if (cursor.moveToFirst()) {
				int posicao = cursor.getColumnIndex("id");
				return cursor.getInt(posicao);
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
	 * Retorna os ids dos imóveis lidos
	 * 
	 * @author Fernanda Almeida
	 * @return ArrayList<Integer>
	 * @throws ControladorException
	 */
	public ArrayList<Integer> buscarIdsImoveisLidos() throws RepositorioException {
		Cursor cursor = null;
		ArrayList<Integer> ids = null;

		try {
			ids = new ArrayList<Integer>();
			
			//Busca ids lidos e NAO CONDOMINIO 
			String query = "SELECT "+ ImovelContas.ID +" FROM "+ objeto.getNomeTabela() + 
			" WHERE "+ImovelContas.INDICADORIMOVCALCULADO + " = "+ ConstantesSistema.SIM +
			" AND "+ImovelContas.IDIMOVELCONDOMINIO + " is null AND "+
			ImovelContas.INDICADORIMOVELCONDOMINIO + "="+ ConstantesSistema.NAO;			
			cursor = db.rawQuery(query, null);
			
			if (cursor.moveToFirst()) {				
				int codigo = cursor.getColumnIndex(ImovelContas.ID);
			
				do{
					Integer id = cursor.getInt(codigo);
					
					ids.add(id);
					
				}while(cursor.moveToNext());
			}
			
			//Busca ids lidos e CONDOMINIO 
			ids.addAll(buscarIdsImoveisCondominioLidos(null));
			
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
		return ids;
	}

	/**
	 * Retorna todos os imóvies lidos e não enviados, incluindo condomínio
	 * @author Fernanda
	 * @date 14/09/2012
	 */
	public ArrayList<Integer> buscarIdsImoveisLidosNaoEnviados() throws RepositorioException {
		//TODO AMELIA
		Cursor cursor = null;
		ArrayList<Integer> ids = null;

		try {
			ids = new ArrayList<Integer>();
			
			String query = "SELECT "+ ImovelContas.ID +" FROM "+ objeto.getNomeTabela() + 
			// OU NAO IMPRESSO E  PERMITE IMPRESSAO e CALCULADO
			" WHERE  (" +ImovelContas.INDICADORIMOVCALCULADO + " = "+ ConstantesSistema.SIM + " AND "+
					ImovelContas.INDICADORIMOVELIMPRESSO + " = "+ ConstantesSistema.NAO +
					" AND "+ ImovelContas.INDICADORNAOPERMITEIMPRESSAO + " = "+ ConstantesSistema.NAO + 
					" AND "+ImovelContas.IDIMOVELCONDOMINIO + " is null AND "+
					 ImovelContas.INDICADORIMOVELCONDOMINIO + "="+ ConstantesSistema.NAO +")"+
					" OR " +
					// NAO ENVIADOS
					"(" +ImovelContas.INDICADORIMOVCALCULADO + " = "+ ConstantesSistema.SIM +					
					" AND "+ImovelContas.IDIMOVELCONDOMINIO + " is null AND "+
					ImovelContas.INDICADORIMOVELCONDOMINIO + "="+ ConstantesSistema.NAO;
			
			String queryNaoEnviado = " AND "+ ImovelContas.INDICADORIMOVELENVIADO + " = "+ConstantesSistema.NAO;
			String fimQuery = ")";
			
			cursor = db.rawQuery(query+queryNaoEnviado+fimQuery, null);
			
			if (cursor.moveToFirst()) {				
				int codigo = cursor.getColumnIndex(ImovelContas.ID);
			
				do{
					Integer id = cursor.getInt(codigo);
					
					ids.add(id);
					
				}while(cursor.moveToNext());
			}
			
			//Busca ids lidos e CONDOMINIO 
			ids.addAll(buscarIdsImoveisCondominioLidos(queryNaoEnviado));
			
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
		return ids;
	}
	
	/**
	 * Retorna todos os imóvies lidos, incluindo condomínio ja calculado
	 * @author Carlos Chaves
	 * @date 30/10/2012
	 */
	public ArrayList<Integer> buscarIdsImoveisCalculados() throws RepositorioException {
		Cursor cursor = null;
		ArrayList<Integer> ids = null;

		try {
			ids = new ArrayList<Integer>();
			
			//Busca ids lidos e NAO CONDOMINIO 
			String query = "SELECT "+ ImovelContas.ID +" FROM "+ objeto.getNomeTabela() + 
			" WHERE "+ImovelContas.INDICADORIMOVCALCULADO + " = "+ ConstantesSistema.SIM +
			" AND "+ImovelContas.IDIMOVELCONDOMINIO + " is null AND "+
			ImovelContas.INDICADORIMOVELCONDOMINIO + "="+ ConstantesSistema.NAO;
			
			cursor = db.rawQuery(query, null);
			
			if (cursor.moveToFirst()) {				
				int codigo = cursor.getColumnIndex(ImovelContas.ID);
			
				do{
					Integer id = cursor.getInt(codigo);
					
					ids.add(id);
					
				}while(cursor.moveToNext());
			}
			
			//Busca ids lidos e CONDOMINIO 
			ids.addAll(buscarIdsImoveisCondominioLidos(null));
			
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
		return ids;
	}

	/**
	 * Retorna todos os imóvies lidos e não enviados, excluindo condomínio
	 * @author Fernanda
	 * @date 26/09/2012
	 */
	public ArrayList<Integer> buscarIdsImoveisLidosNaoEnviadosNaoCondominio() throws RepositorioException {
		Cursor cursor = null;
		ArrayList<Integer> ids = null;

		try {
			ids = new ArrayList<Integer>();
			
			String query = "SELECT "+ ImovelContas.ID +" FROM "+ objeto.getNomeTabela() +" WHERE "
				+ImovelContas.INDICADORIMOVCALCULADO + " = "+ ConstantesSistema.SIM +
				" AND "+ ImovelContas.INDICADORIMOVELENVIADO + " = "+ConstantesSistema.NAO+ " AND ( "+
				ImovelContas.INDICADORIMOVELCONDOMINIO+ " = "+ ConstantesSistema.NAO + " AND "+ ImovelContas.IDIMOVELCONDOMINIO +" IS NULL) ";
				
			cursor = db.rawQuery(query, null);
			
			if (cursor.moveToFirst()) {				
				int codigo = cursor.getColumnIndex(ImovelContas.ID);
			
				do{
					Integer id = cursor.getInt(codigo);
					
					ids.add(id);
					
				}while(cursor.moveToNext());
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
		return ids;
	}

	/**
	 * Retorna a quantidade de imoveis lido
	 * @author Carlos Chaves,Fernanda Almeida
	 * @return Integer
	 * @throws RepositorioException
	 */
	public Integer obterQuantidadesImoveisLidos() throws RepositorioException {	
		Cursor cursor = null;
		
		try {
			String query = "SELECT COUNT(*) as qnt FROM "+ objeto.getNomeTabela() +" WHERE "
				+ImovelContas.INDICADORIMOVCALCULADO + " = "+ ConstantesSistema.SIM +
				" AND "+ ImovelContas.INDICADORIMOVELENVIADO + " = "+ConstantesSistema.NAO+ " AND (( "+
				ImovelContas.INDICADORIMOVELCONDOMINIO+ " = "+ ConstantesSistema.NAO + " AND "+ ImovelContas.IDIMOVELCONDOMINIO +" IS NULL) OR ((" +
				ImovelContas.IDIMOVELCONDOMINIO + " IS NOT NULL) AND "+ ImovelContas.INDICADORRATEIOREALIZADO + " = "+ ConstantesSistema.SIM+ "))";

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

	/**
	 * Retorna ImovelConta com base no sequencial passado como parametro
	 * @author Amelia Pessoa
	 * 
	 * @param sequencial
	 * @return ImovelConta
	 */
	public ImovelConta buscarImovelContaSequencial(int sequencial) throws RepositorioException {
		Cursor cursor = null;
		try {
			cursor = db.query(objeto.getNomeTabela(), objeto.getColunas(), ImovelContas.NUMEROSEQUENCIALROTA
					+ "="+sequencial, null,null, null, null);

			if (cursor.moveToFirst()) {
				List<ImovelConta> colecao = objeto.preencherObjetos(cursor);
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
	
	/**
	 * Retorna a quantidade de imoveis visistado
	 * @author Carlos Chaves
	 * @return Integer
	 * @throws RepositorioException
	 */
	public Integer obterQuantidadeImoveisVisitados() throws RepositorioException {	
		Cursor cursor = null;
		
		try {
			String query = "SELECT COUNT(*) as qnt FROM "+ objeto.getNomeTabela() + 
					" WHERE "+ImovelContas.INDICADORIMOVCALCULADO + " = "+ ConstantesSistema.SIM;
			
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
	
	/**
	 * Retorna a quantidade de imoveis visistado Com anormalidade
	 * @author Carlos Chaves
	 * @return Integer
	 * @throws RepositorioException
	 */
	public Integer obterQuantidadeImoveisVisitadosComAnormalidade() throws RepositorioException {	
		Cursor cursor = null;
		
		try {
			HidrometroInstalado hidrometroInstalado = new HidrometroInstalado();
			String query = "SELECT COUNT( distinct "+objeto.getNomeTabela()+"."+ImovelContas.ID +" ) as qnt FROM "+ objeto.getNomeTabela() + 
					" JOIN " + hidrometroInstalado.getNomeTabela() +
					" ON  " + objeto.getNomeTabela() +"."+ImovelContas.ID + " = " + hidrometroInstalado.getNomeTabela() +"."+ HidrometrosInstalados.MATRICULA + 
					" WHERE "+ImovelContas.INDICADORIMOVCALCULADO + " = "+ ConstantesSistema.SIM +
					" AND "+ HidrometrosInstalados.IDANORMALIDADECAMPO + " IS NOT NULL AND " +HidrometrosInstalados.IDANORMALIDADECAMPO+ " != 0";
			
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
	
	/**
	 * Retorna a quantidade de imoveis visistado Sem anormalidade
	 * @author Carlos Chaves
	 * @return Integer
	 * @throws RepositorioException
	 */
//	public Integer obterQuantidadeImoveisVisitadosSemAnormalidade() throws RepositorioException {	
//		Cursor cursor = null;
//		
//		try {
//			ConsumoHistorico consumoHistorico = new ConsumoHistorico();
//			String query = "SELECT COUNT( distinct "+objeto.getNomeTabela()+"."+ImovelContas.ID +" ) as qnt FROM "+ objeto.getNomeTabela() + 
//					" JOIN " + consumoHistorico.getNomeTabela() +
//					" ON  " + objeto.getNomeTabela() +"."+ImovelContas.ID + " = " + consumoHistorico.getNomeTabela() +"."+ ConsumosHistoricos.MATRICULA + 
//					" WHERE "+ImovelContas.INDICADORIMOVCALCULADO + " = "+ ConstantesSistema.SIM +
//					" AND ("+ ConsumosHistoricos.ANORMLEITURAFATURADA + " IS NULL OR " +ConsumosHistoricos.ANORMLEITURAFATURADA+ " = 0)" ;
//			
//			cursor = db.rawQuery(query, null);
//			
//			if (cursor.moveToFirst()) {
//				int codigo = cursor.getColumnIndex("qnt");
//				return cursor.getInt(codigo);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			Log.e( ConstantesSistema.CATEGORIA , e.getMessage());
//			throw new RepositorioException(context.getResources().getString(
//					R.string.db_erro));
//		} finally {
//			if (cursor != null) {
//				cursor.close();
//			}
//		}
//		return null;
//	}
	
	/**
	 * Retorna a quantidade de imoveis de uma quadra
	 * @param Quadra
	 * @author Carlos Chaves
	 * @return Integer
	 * @throws RepositorioException
	 */
	public Integer obterQuantidadeImoveisPorQuadra(Integer numeroQuadra) throws RepositorioException {	
		Cursor cursor = null;
		
		try {
			String query = "SELECT COUNT(*) as qnt FROM "+ objeto.getNomeTabela() + 
					" WHERE "+ImovelContas.NUMEROQUADRA + " = "+ numeroQuadra;
			
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
	
	/**
	 * Retorna a quantidade de imoveis visitados de uma quadra
	 * @param Quadra
	 * @author Carlos Chaves
	 * @return Integer
	 * @throws RepositorioException
	 */
	public Integer obterQuantidadeImoveisVisitadosPorQuadra(Integer numeroQuadra) throws RepositorioException {	
		Cursor cursor = null;
		
		try {
			String query = "SELECT COUNT(*) as qnt FROM "+ objeto.getNomeTabela() + 
					" WHERE "+ImovelContas.NUMEROQUADRA + " = "+ numeroQuadra+
					" AND " + ImovelContas.INDICADORIMOVCALCULADO + " = "+ ConstantesSistema.SIM;
			
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
	
	/**
	 * Retorna a quantidade de imoveis nao visitados de uma quadra
	 * @param Quadra
	 * @author Carlos Chaves
	 * @return Integer
	 * @throws RepositorioException
	 */
	public Integer obterQuantidadeImoveisNaoVisitadosPorQuadra(Integer numeroQuadra) throws RepositorioException {	
		Cursor cursor = null;
		
		try {
			String query = "SELECT COUNT(*) as qnt FROM "+ objeto.getNomeTabela() + 
					" WHERE "+ImovelContas.NUMEROQUADRA + " = "+ numeroQuadra+
					" AND " + ImovelContas.INDICADORIMOVCALCULADO + " = "+ ConstantesSistema.NAO;
			
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
	
	/**
	 * Retorna os ids de todas as quadras
	 * 
	 * @author Carlos Chaves
	 * @return ArrayList<Integer>
	 * @throws RepositorioException
	 */
	public ArrayList<Integer> buscarQuadras() throws RepositorioException {
		Cursor cursor = null;
		ArrayList<Integer> ids = null;

		try {
			ids = new ArrayList<Integer>();
			
			String query = "SELECT DISTINCT "+ ImovelContas.NUMEROQUADRA +" FROM "+ objeto.getNomeTabela();
			cursor = db.rawQuery(query, null);
			
			if (cursor.moveToFirst()) {				
				int codigo = cursor.getColumnIndex(ImovelContas.NUMEROQUADRA);
			
				do{
					Integer id = cursor.getInt(codigo);
					
					ids.add(id);
					
				}while(cursor.moveToNext());
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
		return ids;
	}
	
	/**
	 * @author Fernanda
	 * @date 12/09/2012
	 * @param ImovelConta
	 */
	public void atualizarIndicadorImovelEnviado(String idImovelMacro) throws RepositorioException {
		ContentValues values = new ContentValues();
		
		values.put(ImovelContas.INDICADORIMOVELENVIADO,ConstantesSistema.SIM);
		
		String where = ImovelContas.IDIMOVELCONDOMINIO + "=? OR "+ ImovelContas.ID + "=?";
		String[] whereArgs = new String[] { idImovelMacro,idImovelMacro };

		try {
			db.update(objeto.getNomeTabela(), values, where, whereArgs);
		} catch (SQLException sqe) {
			sqe.printStackTrace();
			Log.e( ConstantesSistema.CATEGORIA , sqe.getMessage());
			throw new RepositorioException(context.getResources().getString(
					R.string.db_erro));
		}
		
	}
	
	/**
	 * Metodo que inverte o roteiro de todos os imoveis.
	 * @author Carlos Chaves
	 * @date 19/09/2012
	 */
	public void inverterRoteiroImoveis() throws RepositorioException {	
		Cursor cursor = null;
		
		int quantidadeImoveisTotal = getQtdImoveis();
		
		try {
			String query = "UPDATE " +objeto.getNomeTabela()+
					" SET " +ImovelContas.POSICAO+ " = (" +quantidadeImoveisTotal+ " - "+ImovelContas.POSICAO+ " + 1 );";
			
			db.execSQL(query);
			
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

	}
	
	/**
	 * Metodo que retorna os ids do imovel condominio completo(id do macro e ids dos micros). 
	 * @param Integer idImovelMacro
	 * @author Carlos Chaves
	 * @date 19/09/2012
	 */
	public ArrayList<Integer> obterImovelCondominioCompleto(Integer idImovelMacro) throws RepositorioException {	
		
		Cursor cursor = null;
		ArrayList<Integer> ids = null;
		
		try {
			ids = new ArrayList<Integer>();
			String query = 
					"SELECT " 
						+ImovelContas.ID+ 
					" FROM "
						+objeto.getNomeTabela()+ 
					" WHERE "
						+ImovelContas.IDIMOVELCONDOMINIO+ " = " +idImovelMacro+ 
					" OR " 
						+ImovelContas.ID+ " = " +idImovelMacro+
					" ORDER BY " +ImovelContas.POSICAOIMOVELCONDOMINIO+ " DESC";
			
			cursor = db.rawQuery(query, null);
			
			if (cursor.moveToFirst()) {				
				int codigo = cursor.getColumnIndex(ImovelContas.ID);
			
				do{
					Integer id = cursor.getInt(codigo);
					
					ids.add(id);
					
				}while(cursor.moveToNext());
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

		return ids;
	}
	
	/**
	 * Retorna os ids dos imoveis macro
	 * 
	 * @author Carlos Chaves
	 * @return ArrayList<Integer>
	 * @throws RepositorioException
	 */
	public ArrayList<Integer> obterIdsImoveisMacro() throws RepositorioException {
		Cursor cursor = null;
		ArrayList<Integer> ids = null;

		try {
			ids = new ArrayList<Integer>();
			
			String query = 
					"SELECT " 
						+ImovelContas.ID +
					" FROM " 
						+objeto.getNomeTabela()+ 
					" WHERE "  
						+ImovelContas.INDICADORIMOVELCONDOMINIO+ " = " + ConstantesSistema.SIM;
			cursor = db.rawQuery(query, null);
			
			if (cursor.moveToFirst()) {				
				int codigo = cursor.getColumnIndex(ImovelContas.ID);
			
				do{
					Integer id = cursor.getInt(codigo);
					ids.add(id);
					
				}while(cursor.moveToNext());
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
		return ids;
	}
	
	/**
	 * Retorna a posicao do imovel
	 * @param Integer imovelId
	 * @author Carlos Chaves
	 * @return Integer
	 * @throws RepositorioException
	 */
	public Integer obterPosicaoImovel(Integer imovelId) throws RepositorioException {	
		Cursor cursor = null;
		
		try {
			String query = "SELECT " +ImovelContas.POSICAO+ " FROM " + objeto.getNomeTabela() + 
					" WHERE "+ImovelContas.ID + " = "+ imovelId;
			
			cursor = db.rawQuery(query, null);
			
			if (cursor.moveToFirst()) {
				int codigo = cursor.getColumnIndex(ImovelContas.POSICAO);
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
	
	public void atualizarPosicaoImovel(Integer posicao,Integer imovelId) throws RepositorioException {
		
		Cursor cursor = null;
		
		try {
			String query = 
					"UPDATE " 
						+objeto.getNomeTabela()+
					" SET "
						+ImovelContas.POSICAO+ " = (" +posicao+ ")" +
					" WHERE " 
						+ImovelContas.ID+ " = " +imovelId+ ";";
			
			db.execSQL(query);
			
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
	}
	
	/**
	 * @author Fernanda
	 * @date 12/09/2012
	 * @param ImovelConta
	 */
	public void atualizarIndicadorImovelCalculado(Integer idImovel,Integer indicador) throws RepositorioException {
		ContentValues values = new ContentValues();
		
		values.put(ImovelContas.INDICADORIMOVCALCULADO,indicador);
		
		String where = ImovelContas.ID + "=?";
		String[] whereArgs = new String[] { idImovel.toString() };

		try {
			db.update(objeto.getNomeTabela(), values, where, whereArgs);
		} catch (SQLException sqe) {
			sqe.printStackTrace();
			Log.e( ConstantesSistema.CATEGORIA , sqe.getMessage());
			throw new RepositorioException(context.getResources().getString(
					R.string.db_erro));
		}
		
	}

	/**
	 * Retorna os ids dos imóveis CONDOMINIO lidos, considerando o 
	 * imóvel MACRO e todos os seus micros ou nada se os mesmos ainda não estiverem rateados
	 * 
	 * @author Amelia Pessoa
	 * @return ArrayList<Integer>
	 * @throws ControladorException
	 */
	public ArrayList<Integer> buscarIdsImoveisCondominioLidos(String condicao) throws RepositorioException {
		Cursor cursor = null;
		ArrayList<Integer> ids = null;
		ArrayList<Integer> retorno = null;
		
		try {
			ids = new ArrayList<Integer>();
			retorno = new ArrayList<Integer>();
			
			//Busca ids dos imoveis MACRO com RATEIO REALIZADO
			String query = "SELECT "+ ImovelContas.ID +" FROM "+ objeto.getNomeTabela() + 
			" WHERE "+ImovelContas.INDICADORIMOVELCONDOMINIO + "="+ ConstantesSistema.SIM+
			" AND "+ImovelContas.INDICADORRATEIOREALIZADO + "="+ ConstantesSistema.SIM;	
			if(condicao!=null){
				query+=condicao;
			}
			
			cursor = db.rawQuery(query, null);
			
			if (cursor.moveToFirst()) {				
				int codigo = cursor.getColumnIndex(ImovelContas.ID);			
				do{
					Integer id = cursor.getInt(codigo);					
					ids.add(id);					
				}while(cursor.moveToNext());
			}
			
			//Para cada MACRO encontrado busca seus micros
			for(Integer idMacro:ids){
				retorno.add(idMacro);
				ArrayList<Integer> micros = this.buscarIdsImoveisMicro(idMacro);
				retorno.addAll(micros);
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
	public ImovelConta buscarPrimeiroImovel() throws RepositorioException {
		Cursor cursor = null;
		try {
			cursor = db.query(objeto.getNomeTabela(), objeto.getColunas(), null, null,
					null, null, ImovelContas.POSICAO,"1");

			if (cursor.moveToFirst()) {
				List<ImovelConta> colecao = objeto.preencherObjetos(cursor);
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
	
	/**
	 * Retorna todos os imóvies nao nulos ordenados por posicao
	 * @author Fernanda
	 * @date 26/09/2012
	 */
	public ArrayList<ImovelConta> buscarImoveisSequencialNaoNulo() throws RepositorioException {
		Cursor cursor = null;

		ArrayList<ImovelConta> imoveisContas = null;

		try {
			imoveisContas = new ArrayList<ImovelConta>();
			
			String query = "SELECT * FROM "+ objeto.getNomeTabela() +" WHERE "
				+ImovelContas.NUMEROSEQUENCIALROTA + " IS NOT NULL  ORDER BY " +ImovelContas.POSICAO;
				
			cursor = db.rawQuery(query, null);
			
			if (cursor.moveToFirst()) {				
				int codigo = cursor.getColumnIndex(ImovelContas.ID);
				int endereco = cursor.getColumnIndex(ImovelContas.ENDERECO);
				int indicadorImovelCalculado = cursor.getColumnIndex(ImovelContas.INDICADORIMOVCALCULADO);
				int indicadorImovelEnviado = cursor.getColumnIndex(ImovelContas.INDICADORIMOVELENVIADO);
				int indicadorImovelImpresso = cursor.getColumnIndex(ImovelContas.INDICADORIMOVELIMPRESSO);
				int nomeUsuario = cursor.getColumnIndex(ImovelContas.NOMEUSUARIO);	
				int posicao = cursor.getColumnIndex(ImovelContas.POSICAO);		
				
				//Necessarios para verificacao do imovel condominio
				int icCondominio = cursor.getColumnIndex(ImovelContas.INDICADORIMOVELCONDOMINIO);
				int matriculaCondominio = cursor.getColumnIndex(ImovelContas.IDIMOVELCONDOMINIO);
				int posicaoCondominio = cursor.getColumnIndex(ImovelContas.POSICAOIMOVELCONDOMINIO);
				
				do {	
					ImovelConta imovelConta = new ImovelConta();
					imovelConta.setId(cursor.getInt(codigo));		
					imovelConta.setNomeUsuario(cursor.getString(nomeUsuario));			
					imovelConta.setEndereco(cursor.getString(endereco));					
					imovelConta.setIndcImovelCalculado(cursor.getInt(indicadorImovelCalculado ));
					imovelConta.setIndcImovelEnviado(cursor.getInt(indicadorImovelEnviado));
					imovelConta.setIndcImovelImpresso(cursor.getInt(indicadorImovelImpresso ));
					
					imovelConta.setPosicao(cursor.getInt(posicao));		
					imovelConta.setIndcCondominio(cursor.getInt(icCondominio));
					imovelConta.setMatriculaCondominio(cursor.getInt(matriculaCondominio));
					imovelConta.setPosicaoImovelCondominio(cursor.getInt(posicaoCondominio));
					
					imoveisContas.add(imovelConta);
					
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
		return imoveisContas;
	}
	
	/**
	 * Retorna todos os imóvies nulos ordenados por posicao
	 * @author Fernanda
	 * @date 06/01/2013
	 */
	public ArrayList<ImovelConta> buscarImoveisSequencialNulo() throws RepositorioException {
		Cursor cursor = null;

		ArrayList<ImovelConta> imoveisContas = null;

		try {
			imoveisContas = new ArrayList<ImovelConta>();
			
			String query = "SELECT * FROM "+ objeto.getNomeTabela() +" WHERE "
				+ImovelContas.NUMEROSEQUENCIALROTA + " IS NULL ORDER BY " +ImovelContas.POSICAO;
				
			cursor = db.rawQuery(query, null);
			
			if (cursor.moveToFirst()) {				
				int codigo = cursor.getColumnIndex(ImovelContas.ID);
				int endereco = cursor.getColumnIndex(ImovelContas.ENDERECO);
				int indicadorImovelCalculado = cursor.getColumnIndex(ImovelContas.INDICADORIMOVCALCULADO);
				int indicadorImovelEnviado = cursor.getColumnIndex(ImovelContas.INDICADORIMOVELENVIADO);
				int indicadorImovelImpresso = cursor.getColumnIndex(ImovelContas.INDICADORIMOVELIMPRESSO);
				int nomeUsuario = cursor.getColumnIndex(ImovelContas.NOMEUSUARIO);	
				int posicao = cursor.getColumnIndex(ImovelContas.POSICAO);		
				
				//Necessarios para verificacao do imovel condominio
				int icCondominio = cursor.getColumnIndex(ImovelContas.INDICADORIMOVELCONDOMINIO);
				int matriculaCondominio = cursor.getColumnIndex(ImovelContas.IDIMOVELCONDOMINIO);
				int posicaoCondominio = cursor.getColumnIndex(ImovelContas.POSICAOIMOVELCONDOMINIO);
				
				do {	
					ImovelConta imovelConta = new ImovelConta();
					imovelConta.setId(cursor.getInt(codigo));		
					imovelConta.setNomeUsuario(cursor.getString(nomeUsuario));			
					imovelConta.setEndereco(cursor.getString(endereco));					
					imovelConta.setIndcImovelCalculado(cursor.getInt(indicadorImovelCalculado ));
					imovelConta.setIndcImovelEnviado(cursor.getInt(indicadorImovelEnviado));
					imovelConta.setIndcImovelImpresso(cursor.getInt(indicadorImovelImpresso ));
					
					imovelConta.setPosicao(cursor.getInt(posicao));		
					imovelConta.setIndcCondominio(cursor.getInt(icCondominio));
					imovelConta.setMatriculaCondominio(cursor.getInt(matriculaCondominio));
					imovelConta.setPosicaoImovelCondominio(cursor.getInt(posicaoCondominio));
					
					imoveisContas.add(imovelConta);
					
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
		return imoveisContas;
	}
	
	/**
	 * Insere um imovelConta no BD sem a data de vencimento e retorna id gerado
	 * @author Erivan Sousa
	 * @param imovelConta
	 * @throws RepositorioException
	 */
	public long inserirImovelContaVencimento(ImovelConta imovelConta) throws RepositorioException {
		try {
			long idObjetoInserido = ConstantesSistema.ERRO_INSERIR_REGISTRO_BD;
			ContentValues values = imovelConta.preencherValues();
			
			if(imovelConta.getDataVencimento()!=null){
				values.put(ImovelContas.DATAVENCIMENTOCONTA, imovelConta.getDataVencimento().getTime());
			}			
			
			idObjetoInserido = db.insert(imovelConta.getNomeTabela(), null, values);
				
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
	 * Metodo que atualiza os indicadores que indicam que o 
	 * imovel condominio não esta calculado
	 * @author Carlos Chaves
	 * @date 07/03/2013
	 * @param Integer idMacro
	 * @param Integer imovelCalculado
	 */
	public void atualizarIndicadorImovelCondominioNaoCalculado(Integer idMacro) 
			throws RepositorioException {
		
		ContentValues values = new ContentValues();
		
		values.put(ImovelContas.INDICADORIMOVELIMPRESSO,ConstantesSistema.NAO);
		values.put(ImovelContas.INDICADORRATEIOREALIZADO,ConstantesSistema.NAO);
		
		String where;
		String[] whereArgs;
		
		where = ImovelContas.ID + "= ?  OR "+ ImovelContas.IDIMOVELCONDOMINIO + "= ?";
		whereArgs = new String[] { idMacro.toString(), idMacro.toString() };
		
		try {
			db.update(objeto.getNomeTabela(), values, where, whereArgs);
		} catch (SQLException sqe) {
			sqe.printStackTrace();
			Log.e( ConstantesSistema.CATEGORIA , sqe.getMessage());
			throw new RepositorioException(context.getResources().getString(
					R.string.db_erro));
		}
		
	}
	
	
	/**
	 * Metodo que atualiza o indicador de permite continuar
	 * impressao do imovel macro
	 * @author Carlos Chaves
	 * @date 19/03/2013
	 * @param Integer idMacro
	 */
	public void atualizarIndicadorContinuaImpressao(Integer idImovelMacro, Integer indicadorContinuaImpressao) throws RepositorioException {
		ContentValues values = new ContentValues();
		
		values.put(ImovelContas.INDICADORCONTINUAIMPRESSAO,indicadorContinuaImpressao);
		
		String where = ImovelContas.ID + "=?";
		String[] whereArgs = new String[] {idImovelMacro+""};

		try {
			db.update(objeto.getNomeTabela(), values, where, whereArgs);
		} catch (SQLException sqe) {
			sqe.printStackTrace();
			Log.e( ConstantesSistema.CATEGORIA , sqe.getMessage());
			throw new RepositorioException(context.getResources().getString(
					R.string.db_erro));
		}
		
	}
	
	/**
	 * Metodo que obtem o indicador de permite continuar
	 * impressao do imovel macro
	 * @author Carlos Chaves
	 * @date 19/03/2013
	 * @param Integer idMacro
	 */
	public Integer obterIndicadorPermiteContinuarImpressao(Integer idMacro) throws RepositorioException {	
		Cursor cursor = null;
		
		try {
			String query = "SELECT " +ImovelContas.INDICADORCONTINUAIMPRESSAO+ " FROM " + objeto.getNomeTabela() + 
					" WHERE "+ImovelContas.ID + " = "+ idMacro;
			
			cursor = db.rawQuery(query, null);
			
			if (cursor.moveToFirst()) {
				int codigo = cursor.getColumnIndex(ImovelContas.INDICADORCONTINUAIMPRESSAO);
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
	
	/**
	 * Retorna todos os imóvies lidos e não impressos, incluindo condomínio
	 * @author Fernanda
	 * @date 14/05/2013
	 */
	public ArrayList<Integer> buscarIdsImoveisLidosNaoImpressos() throws RepositorioException {
		Cursor cursor = null;
		ArrayList<Integer> ids = null;

		try {
			ids = new ArrayList<Integer>();
			
			String query = "SELECT "+ ImovelContas.ID +" FROM "+ objeto.getNomeTabela() + 
			// OU NAO IMPRESSO E  PERMITE IMPRESSAO e CALCULADO
			" WHERE  " +ImovelContas.INDICADORIMOVCALCULADO + " = "+ ConstantesSistema.SIM + " AND "+
					ImovelContas.INDICADORIMOVELIMPRESSO + " = "+ ConstantesSistema.NAO +
					" AND "+ ImovelContas.INDICADORNAOPERMITEIMPRESSAO + " = "+ ConstantesSistema.NAO + 
					" AND "+ImovelContas.IDIMOVELCONDOMINIO + " is null AND "+
					 ImovelContas.INDICADORIMOVELCONDOMINIO + "="+ ConstantesSistema.NAO +
					" ORDER BY "+ ImovelContas.POSICAO + " ASC";
			
		
			cursor = db.rawQuery(query,null);
			
			if (cursor.moveToFirst()) {				
				int codigo = cursor.getColumnIndex(ImovelContas.ID);
			
				do{
					Integer id = cursor.getInt(codigo);
					
					ids.add(id);
					
				}while(cursor.moveToNext());
			}
			// NAO IMPRESSO E NAO ENVIADO
			String queryNaoEnviado = " AND " +ImovelContas.INDICADORIMOVELIMPRESSO + " = "+ ConstantesSistema.NAO +
					" AND "+ ImovelContas.INDICADORNAOPERMITEIMPRESSAO + " = "+ ConstantesSistema.NAO_MEDIDO;
			
			//Busca ids lidos e CONDOMINIO e NAO IMPRESSO E NAO ENVIADO
			ids.addAll(buscarIdsImoveisCondominioLidos(queryNaoEnviado));
			
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
		return ids;
	}
		
}