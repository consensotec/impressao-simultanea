
package com.br.ipad.isc.controladores;

import android.content.Context;
import android.util.Log;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.bean.SistemaParametros;
import com.br.ipad.isc.excecoes.ControladorException;
import com.br.ipad.isc.excecoes.FachadaException;
import com.br.ipad.isc.excecoes.RepositorioException;
import com.br.ipad.isc.repositorios.RepositorioSistemaParametros;
import com.br.ipad.isc.util.ConstantesSistema;

public class ControladorSistemaParametros extends ControladorBasico implements IControladorSistemaParametros {
	
	private static ControladorSistemaParametros instance;
	private RepositorioSistemaParametros repositorioSistemaParametros;
	protected static Context context;
	
		
	public void resetarInstancia(){
		instance = null;
	}
	
	protected ControladorSistemaParametros(){
		super();
	}
	
	public static ControladorSistemaParametros getInstance(){
		if ( instance == null ){
			instance =  new ControladorSistemaParametros();
			instance.repositorioSistemaParametros = RepositorioSistemaParametros.getInstance();			
		}
		
		return instance;		
	}

	public void setContext(Context ctx ) {
		context = ctx;		
	}	

	public SistemaParametros buscarSistemaParametro() throws ControladorException {
		try {
			return repositorioSistemaParametros.buscarSistemaParametro();
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}		
	}	

	public void atualizarSistemaParametros(SistemaParametros sistemaParametros) throws ControladorException {
		try {
			repositorioSistemaParametros.atualizarSistemaParametros(sistemaParametros);
			SistemaParametros.resetarInstancia();
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
		
	}	
	
	public boolean validaSenhaApagar(String senha) {
		SistemaParametros sistemaParametros = SistemaParametros.getInstancia();
		 if((senha.equals(ConstantesSistema.SENHA_LEITURISTA)
   			  && !sistemaParametros.getCodigoEmpresaFebraban().equals(ConstantesSistema.CODIGO_FEBRABAN_CAERN) ) 
   			  ||
   			  (senha.equals(ConstantesSistema.SENHA_LEITURISTA_CAERN) 
   			  && (sistemaParametros.getCodigoEmpresaFebraban().equals(ConstantesSistema.CODIGO_FEBRABAN_CAERN)))) {
			  return true;
		  }
		 
		return false;
		
	}	
	
	public boolean validaSenhaAdm(String senha) {
		
		 if(senha.equals(ConstantesSistema.SENHA_ADMINISTRADOR)) {
			  return true;
		  }
		 
		return false;
		
	}	

	public void atualizarQntImoveis() throws ControladorException {
		try {
			repositorioSistemaParametros.atualizarQntImoveis();
			SistemaParametros.resetarInstancia();
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
		
	}
	
	/**
	 * Atualiza os atributos idImovelCondominio e qntImovelCondominio
	 * de SistemaParametros
	 * 
	 * @param imovelMacro
	 */
	public void atualizarDadosImovelMacro(ImovelConta imovel) throws ControladorException {
		try{
			
			Integer imovelMacroId = null;
			if(imovel.getIndcCondominio().equals(ConstantesSistema.SIM)){
				imovelMacroId = imovel.getId();
			} else {
				imovelMacroId = imovel.getMatriculaCondominio();
			}
			if(!imovelMacroId.equals(SistemaParametros.getInstancia().getIdImovelCondominio())){
        		this.atualizarIdQtdImovelCondominioSistemaParametros(imovelMacroId, 
        				getControladorImovelConta().obterQuantidadeImovelMicro(imovelMacroId));
        	}       	
			
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}		
	}
	
	public void atualizarArquivoCarregadoBD() throws ControladorException {
		try {
			repositorioSistemaParametros.atualizarArquivoCarregadoBD();
			SistemaParametros.resetarInstancia();
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
		
	}
	
	public String atualizarIndicadorRotaMarcacaoAtiva(Integer indicadorRotaMarcacaoAtiva) throws ControladorException {
		
		String retorno;
		
		try {
			
			//Caso seja para desativar. Limpar a tabela de sequencial rota.
			if(indicadorRotaMarcacaoAtiva.equals(ConstantesSistema.NAO)){
				getControladorSequencialRotaMarcacao().removerTodosSequencialRotaMarcacao();
				repositorioSistemaParametros.atualizarIndicadorRotaMarcacaoAtiva(indicadorRotaMarcacaoAtiva);
				retorno =  getContext().getString(R.string.str_rota_marcacao_desabilitada);
				
			
			}else{
				
				if(getControladorConsumoHistorico().obterQuantidadeRegistroConsumoHistorico() == 0){
					repositorioSistemaParametros.atualizarIndicadorRotaMarcacaoAtiva(indicadorRotaMarcacaoAtiva);
					retorno =  getContext().getString(R.string.str_rota_marcacao_habilitada);
				}else{
					retorno =  getContext().getString(R.string.str_rota_marcacao_exite_imovel_calculado);
				}
				
			}
			
			SistemaParametros.resetarInstancia();
			return retorno;
			
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
		
	}

	
	public void atualizarRoteiroOnlineOffline(Integer indicador) throws ControladorException {
		try {
			repositorioSistemaParametros.atualizarRoteiroOnlineOffline(indicador);
			SistemaParametros.resetarInstancia();
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}

	public void atualizarIdImovelSelecionadoSistemaParametros(Integer idSelecionado) throws ControladorException {
		try {
			repositorioSistemaParametros.atualizarIdImovelSelecionadoSistemaParametros(idSelecionado);			
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}
	
	public void atualizarIdQtdImovelCondominioSistemaParametros(Integer idImovel, Integer qntImovelCondominio) throws ControladorException {
		try {
			repositorioSistemaParametros.atualizarIdQtdImovelCondominioSistemaParametros(idImovel, qntImovelCondominio);			
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}
}