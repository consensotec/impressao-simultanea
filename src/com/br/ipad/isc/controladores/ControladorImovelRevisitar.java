
package com.br.ipad.isc.controladores;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.HidrometroInstalado;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.bean.ImovelRevisitar;
import com.br.ipad.isc.excecoes.ControladorException;
import com.br.ipad.isc.excecoes.RepositorioException;
import com.br.ipad.isc.repositorios.RepositorioBasico;
import com.br.ipad.isc.repositorios.RepositorioImovelRevisitar;
import com.br.ipad.isc.util.ConstantesSistema;
import com.br.ipad.isc.util.Util;

public class ControladorImovelRevisitar  extends ControladorBasico implements IControladorImovelRevisitar{
	
	private static ControladorImovelRevisitar instance;
	private RepositorioImovelRevisitar repositorioImovelRevisitar;
	protected static Context context;
	
	public void resetarInstancia(){
		instance = null;
	}
	
	protected ControladorImovelRevisitar(){
		super();
	}
	
	public static ControladorImovelRevisitar getInstance(){
		if ( instance == null ){
			instance =  new ControladorImovelRevisitar();
			instance.repositorioImovelRevisitar = RepositorioImovelRevisitar.getInstance();
		}
		
		return instance;		
	}

	public void setContext(Context ctx ) {
		context = ctx;		
	}	

	public ImovelRevisitar buscarImovelRevisitarPorImovel(Integer idImovel) throws ControladorException {
		try {
			return repositorioImovelRevisitar.buscarImovelRevisitarPorImovel(idImovel);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}		
	}
	
	  
    public void setMatriculasRevisitar(String idsRevisitar) throws ControladorException {
    	try {
    		ArrayList<String> strVector = Util.split(idsRevisitar, ',');
			ArrayList<String> matriculasRevisitar = new ArrayList<String>();
			
			for (int i = 0; i < strVector.size(); i++) {
				if (!matriculasRevisitar.contains(strVector.get(i))) {
					matriculasRevisitar.add(strVector.get(i));
	
					// Selecionamos o imovel(eis) passado(s)
					ImovelConta imovel = (ImovelConta) ControladorBasico.getInstance().
							pesquisarPorId(new Integer(strVector.get(i)), new ImovelConta()); 
												
					//Verifica se o imóvel já não foi inserido na base
					ImovelRevisitar imovelRevisitarRepetido = (ImovelRevisitar)RepositorioBasico.getInstance().
							pesquisarPorId(new Integer(strVector.get(i)), new ImovelRevisitar());
					
					if(imovelRevisitarRepetido == null){
						
						HidrometroInstalado hidrometroAgua = getControladorHidrometroInstalado().buscarHidrometroInstaladoPorImovelTipoMedicao(imovel.getId(), ConstantesSistema.LIGACAO_AGUA);
						HidrometroInstalado hidrometroPoco = getControladorHidrometroInstalado().buscarHidrometroInstaladoPorImovelTipoMedicao(imovel.getId(),ConstantesSistema.LIGACAO_POCO);
		
						//Reseta os dados de hidrômetro água que foram alterados pelo usuário
						if (hidrometroAgua != null) {
							hidrometroAgua.setLeitura(null);
							hidrometroAgua.setAnormalidade(null);
							hidrometroAgua.setDataLeitura(null);
							hidrometroAgua.setLeituraAnteriorDigitada(null);
							hidrometroAgua.setQtdDiasAjustado(null);
							hidrometroAgua.setLeituraAtualFaturamento(null);
							hidrometroAgua.setLeituraAtualFaturamentoHelper(null);
							
							ControladorBasico.getInstance().atualizar(hidrometroAgua);
						}
		
						//Reseta os dados de hidrômetro água que foram alterados pelo usuário
						if (hidrometroPoco != null) {
							hidrometroPoco.setLeitura(null);
							hidrometroPoco.setAnormalidade(null);
							hidrometroPoco.setDataLeitura(null);
							hidrometroPoco.setLeituraAnteriorDigitada(null);
							hidrometroPoco.setQtdDiasAjustado(null);
							hidrometroPoco.setLeituraAtualFaturamento(null);
							hidrometroPoco.setLeituraAtualFaturamentoHelper(null);
		
							ControladorBasico.getInstance().atualizar(hidrometroPoco);
						}
		
						imovel.setConsumoAguaMedidoHistoricoFaturamento(null);
						imovel.setVolumeEsgotoMedidoHistoricoFaturamento(null);
						imovel.setIndcImovelCalculado(ConstantesSistema.NAO);
						imovel.setIndcImovelEnviado(ConstantesSistema.NAO);
						imovel.setIndcImovelImpresso(ConstantesSistema.NAO);
		
						ImovelRevisitar imovelRevisitar = new ImovelRevisitar();
						imovelRevisitar.setMatricula(imovel);
						imovelRevisitar.setIndicadorRevisitado(ConstantesSistema.NAO);
						try {
							RepositorioBasico.getInstance().inserir(imovelRevisitar);
						} catch (RepositorioException e) {
						Log.e(ConstantesSistema.CATEGORIA, e.getMessage());
							e.printStackTrace();
						}
						
						RepositorioBasico.getInstance().atualizar(imovel);
					}
				}
			}
	    } catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}

    /**
	 * Retorna imóvel a revisitar com 
	 * indicador de revisitado igual a não e diferente do imóvel atual
	 * 
	 * @return ImovelRevisitar
	 * @params Integer idImovel
	 * @author Fernanda Almeida
	 * @date 20/09/2012
	 */
    public ArrayList<ImovelRevisitar> buscarImovelNaoRevisitado() throws ControladorException {
		try {
			return repositorioImovelRevisitar.buscarImovelNaoRevisitado();
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}		
	}
}