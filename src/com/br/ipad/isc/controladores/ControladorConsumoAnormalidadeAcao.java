
package com.br.ipad.isc.controladores;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.ConsumoAnormalidadeAcao;
import com.br.ipad.isc.excecoes.ControladorException;
import com.br.ipad.isc.excecoes.RepositorioException;
import com.br.ipad.isc.repositorios.RepositorioConsumoAnormalidadeAcao;
import com.br.ipad.isc.util.ConstantesSistema;

public class ControladorConsumoAnormalidadeAcao  extends ControladorBasico implements IControladorConsumoAnormalidadeAcao{
	
	private static ControladorConsumoAnormalidadeAcao instance;
	private RepositorioConsumoAnormalidadeAcao repositorioConsumoAnormalidadeAcao;
	protected static Context context;
	
	public void resetarInstancia(){
		instance = null;
	}
	
	protected ControladorConsumoAnormalidadeAcao(){
		super();
	}
	
	public static ControladorConsumoAnormalidadeAcao getInstance(){
		if ( instance == null ){
			instance =  new ControladorConsumoAnormalidadeAcao();
			instance.repositorioConsumoAnormalidadeAcao = RepositorioConsumoAnormalidadeAcao.getInstance();
		}
		
		return instance;		
	}

	public void setContext(Context ctx ) {
		context = ctx;		
	}	

	public ArrayList<ConsumoAnormalidadeAcao> buscarConsumoAnormalidadeAcao(Integer perfilId, Integer anormalidade,
			Integer categoriaPrincipal) throws ControladorException {
		ArrayList<ConsumoAnormalidadeAcao> retorno;
		
			try {
				retorno =  repositorioConsumoAnormalidadeAcao.buscarConsumoAnormalidadeAcao(perfilId, anormalidade,
						categoriaPrincipal);
				if(retorno==null){
					retorno =  repositorioConsumoAnormalidadeAcao.buscarConsumoAnormalidadeAcao(anormalidade,
							categoriaPrincipal);
				}
				if(retorno==null){
					retorno =  repositorioConsumoAnormalidadeAcao.buscarConsumoAnormalidadeAcao(anormalidade);
				}
				
				return retorno;
				
			} catch (RepositorioException ex){
				ex.printStackTrace();
				Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
				throw new ControladorException(context.getResources().getString(
						R.string.db_erro));
			}
	}	

	public Integer obterQtdConsumoAnormalidadeAcao(Integer perfilId, Integer anormalidade,
			Integer categoriaPrincipal) throws ControladorException {
			
			ArrayList<ConsumoAnormalidadeAcao> colecao = buscarConsumoAnormalidadeAcao(perfilId, anormalidade,
					categoriaPrincipal);
			if (colecao==null){
				return 0;
			}
			return colecao.size() + 1;
	}
	
	public ConsumoAnormalidadeAcao obterConsumoAnormalidadeAcao(int idAnormalidadeConsumo, int idCategoria, 
			int idPerfilImovel, int ordemMesAnormalidade) throws ControladorException {
		
		ArrayList<ConsumoAnormalidadeAcao> colecao = buscarConsumoAnormalidadeAcao(idPerfilImovel, idAnormalidadeConsumo,
				idCategoria);
		
		if(colecao!=null){
			for (ConsumoAnormalidadeAcao anormalidade : colecao){
				if (ordemMesAnormalidade == anormalidade.getCodigoMesConsecutivo().intValue()){
					return anormalidade;
				}
			}
		}		
		return null;
	}
	
}