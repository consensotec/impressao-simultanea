
package com.br.ipad.isc.controladores;

import java.util.List;

import android.content.Context;
import android.util.Log;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.ConsumoAnteriores;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.excecoes.ControladorException;
import com.br.ipad.isc.excecoes.RepositorioException;
import com.br.ipad.isc.repositorios.RepositorioConsumoAnteriores;
import com.br.ipad.isc.util.ConstantesSistema;
import com.br.ipad.isc.util.Util;

public class ControladorConsumoAnteriores  extends ControladorBasico implements IControladorConsumoAnteriores{
	
	private static ControladorConsumoAnteriores instance;
	private RepositorioConsumoAnteriores repositorioConsumoAnteriores;
	protected static Context context;
	
	public void resetarInstancia(){
		instance = null;
	}
	
	protected ControladorConsumoAnteriores(){
		super();
	}
	
	public static ControladorConsumoAnteriores getInstance(){
		if ( instance == null ){
			instance =  new ControladorConsumoAnteriores();
			instance.repositorioConsumoAnteriores = RepositorioConsumoAnteriores.getInstance();
		}
		
		return instance;		
	}

	public void setContext(Context ctx ) {
		context = ctx;		
	}	

	public List<ConsumoAnteriores> buscarConsumoAnterioresPorImovelId(Integer imovelId) throws ControladorException {
		try {
			return repositorioConsumoAnteriores.buscarConsumoAnterioresPorImovelId(imovelId);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}
	
	public ConsumoAnteriores buscarConsumoAnterioresPorImovelAnoMesPorTipoLigacao(Integer imovelId, Integer anoMes,Integer tipoMedicao) throws ControladorException {
		try {
			return repositorioConsumoAnteriores.buscarConsumoAnterioresPorImovelAnoMesPorTipoLigacao(imovelId, anoMes,tipoMedicao);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}

	public List<ConsumoAnteriores> buscarConsumoAnterioresPorImovelTipoLigacao(Integer imovelId, Integer tipoMedicao) throws ControladorException {
		try {
			return repositorioConsumoAnteriores.buscarConsumoAnterioresPorImovelTipoLigacao(imovelId, tipoMedicao);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}
	
	public List<ConsumoAnteriores> buscarConsumoAnterioresPorImovelAnormalidade(Integer imovelId, Integer idAnormalidadeConsumo) 
			throws ControladorException {
		try {
			return repositorioConsumoAnteriores.buscarConsumoAnterioresPorImovelAnormalidade(imovelId, 
					idAnormalidadeConsumo);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}
	
	public ConsumoAnteriores buscarConsumoAnterioresPorImovelAnormalidade(Integer imovelId, Integer idAnormalidadeConsumo, Integer anoMes) 
			throws ControladorException {
		try {
			return repositorioConsumoAnteriores.buscarConsumoAnterioresPorImovelAnormalidade(imovelId, 
					idAnormalidadeConsumo, anoMes);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}
	
	public int obtemOrdemAnormalidade(ImovelConta imovel, int idAnormalidadeConsumo,int anoMesReferencia) 
			throws ControladorException {
		int sequencia = 1;
		
		List<ConsumoAnteriores> colecao = buscarConsumoAnterioresPorImovelAnormalidade(
				imovel.getId(), idAnormalidadeConsumo);

		if(colecao != null){
			for(ConsumoAnteriores consumo : colecao){
				anoMesReferencia = Util.subtrairMesDoAnoMes(anoMesReferencia, 1);
				
				int anoMes = consumo.getAnoMesReferencia();
	
				if (anoMes == anoMesReferencia && consumo.getAnormalidadeConsumo() == idAnormalidadeConsumo) {
					sequencia++;
				}else{
					break;
				}

			}
		}
		
		return sequencia; 		
	}
}