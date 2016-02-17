
package com.br.ipad.isc.controladores;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.HidrometroInstalado;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.bean.ObjetoBasico;
import com.br.ipad.isc.bean.SistemaParametros;
import com.br.ipad.isc.excecoes.ControladorException;
import com.br.ipad.isc.excecoes.RepositorioException;
import com.br.ipad.isc.repositorios.CarregaBD;
import com.br.ipad.isc.repositorios.RepositorioBasico;
import com.br.ipad.isc.util.ConstantesSistema;
import com.br.ipad.isc.util.Util;

public class ControladorBasico implements IControladorBasico {
	
	private static ControladorBasico instance;
	protected static Context context;

		
	public static Context getContext() {
		return context;
	}

	//Controladores	
	private ControladorHidrometroInstalado controladorHidrometroInstalado;
	private ControladorCategoriaSubcategoria controladorCategoriaSubcategoria;
	private ControladorDebitoCobrado controladorDebitoCobrado;
	private ControladorImovelConta controladorImovelConta;
	private ControladorConsumoAnteriores controladorConsumoAnteriores;
	private ControladorSistemaParametros controladorSistemaParametros;
	private ControladorContaCategoria controladorContaCategoria;
	private ControladorConsumoHistorico controladorConsumoHistorico;
	private ControladorCreditoRealizado controladorCreditoRealizado;
	private ControladorConsumoAnormalidadeAcao controladorConsumoAnormalidadeAcao;
	private ControladorConsumoAnormalidade controladorConsumoAnormalidade;
	private ControladorConsumoTarifaCategoria controladorConsumoTarifaCategoria;
	private ControladorConsumoTarifaFaixa controladorConsumoTarifaFaixa;
	private ControladorContaCategoriaConsumoFaixa controladorContaCategoriaConsumoFaixa;
	private ControladorContaImposto controladorContaImposto;
//	private ControladorQualidadeAgua controladorQualidadeAgua;
//	private ControladorContaDebito controladorContaDebito;
	private ControladorLeituraAnormalidade controladorLeituraAnormalidade;
	private ControladorAlertaValidarLeitura controladorAlertaValidarLeitura;
	private ControladorAlertaValidarImpressao controladorAlertaValidarImpressao;
	private ControladorAlertaValidarConexaoImpressora controladorAlertaValidarConexaoImpressora;
	private ControladorImpressao controladorImpressao;
	private ControladorFoto controladorFoto;
	private ControladorImovelRevisitar controladorImovelRevisitar;
	private ControladorSequencialRotaMarcacao controladorSequencialRotaMarcacao;	
		
	//Controlador com diferenciação de empresa
	private ControladorConta controladorConta;
	private ControladorImovel controladorImovel;
	
	private RepositorioBasico repositorioBasico;
	
	public void resetarInstancia(){
		instance = null;
	}
	
	protected ControladorBasico(){
		super();		
	}
	
	public static ControladorBasico getInstance(){
		if ( instance == null ){
			instance =  new ControladorBasico();				
		}		
		return instance;		
	}

	public void setContext( Context ctx ) {
		context = ctx;
		RepositorioBasico.setContext(ctx);
		instance.repositorioBasico = RepositorioBasico.getInstance();
	}
	
	public ControladorFoto getControladorFoto() {
		if(controladorFoto==null){
			controladorFoto = ControladorFoto.getInstance();
		}
		return controladorFoto;
	}
	
	public ControladorAlertaValidarLeitura getControladorAlertaValidarLeitura(HidrometroInstalado hidrometro,ImovelConta imovel,int tipoMedicao,boolean imprimir, boolean proximo) {
		
		controladorAlertaValidarLeitura = new ControladorAlertaValidarLeitura(hidrometro,imovel,tipoMedicao,imprimir,proximo);
	
		return controladorAlertaValidarLeitura;
	}
	
	public ControladorAlertaValidarImpressao getControladorAlertaValidarImpressao(ImovelConta imovel) {
		
		controladorAlertaValidarImpressao = new ControladorAlertaValidarImpressao(imovel);
	
		return controladorAlertaValidarImpressao;
	}
	
	public ControladorAlertaValidarConexaoImpressora getControladorAlertaValidarConexaoImpressora(ImovelConta imovel){
		
		controladorAlertaValidarConexaoImpressora= new ControladorAlertaValidarConexaoImpressora(imovel);
	
		return controladorAlertaValidarConexaoImpressora;
	}
	
	public ControladorHidrometroInstalado getControladorHidrometroInstalado() {
		if(controladorHidrometroInstalado==null){
			controladorHidrometroInstalado = ControladorHidrometroInstalado.getInstance();
		}
		return controladorHidrometroInstalado;
	}
	
	public ControladorImpressao getControladorImpressao() {
		if(controladorImpressao==null){
			controladorImpressao = ControladorImpressao.getInstance();
		}
		return controladorImpressao;
	}

	public ControladorCategoriaSubcategoria getControladorCategoriaSubcategoria() {
		if(controladorCategoriaSubcategoria==null){
			controladorCategoriaSubcategoria = ControladorCategoriaSubcategoria.getInstance();
		}
		return controladorCategoriaSubcategoria;
	}

	public ControladorDebitoCobrado getControladorDebitoCobrado() {
		if(controladorDebitoCobrado==null){
			controladorDebitoCobrado = ControladorDebitoCobrado.getInstance();
		}
		return controladorDebitoCobrado;
	}

	public ControladorImovelConta getControladorImovelConta() {
		if(controladorImovelConta==null){
			controladorImovelConta = ControladorImovelConta.getInstance();
		}
		return controladorImovelConta;
	}

	public ControladorConsumoAnteriores getControladorConsumoAnteriores() {
		if(controladorConsumoAnteriores==null){
			controladorConsumoAnteriores = ControladorConsumoAnteriores.getInstance();
		}
		return controladorConsumoAnteriores;
	}

	public ControladorSistemaParametros getControladorSistemaParametros() {
		if(controladorSistemaParametros==null){
			controladorSistemaParametros = ControladorSistemaParametros.getInstance();
		}
		return controladorSistemaParametros;
	}

	public ControladorContaCategoria getControladorContaCategoria() {
		if(controladorContaCategoria==null){
			controladorContaCategoria = ControladorContaCategoria.getInstance();
		}
		return controladorContaCategoria;
	}

	public ControladorConsumoHistorico getControladorConsumoHistorico() {
		if(controladorConsumoHistorico==null){
			controladorConsumoHistorico = ControladorConsumoHistorico.getInstance();
		}
		return controladorConsumoHistorico;
	}

	public ControladorCreditoRealizado getControladorCreditoRealizado() {
		if(controladorCreditoRealizado==null){
			controladorCreditoRealizado = ControladorCreditoRealizado.getInstance();
		}
		return controladorCreditoRealizado;
	}

	public ControladorConsumoAnormalidadeAcao getControladorConsumoAnormalidadeAcao() {
		if(controladorConsumoAnormalidadeAcao==null){
			controladorConsumoAnormalidadeAcao = ControladorConsumoAnormalidadeAcao.getInstance();
		}
		return controladorConsumoAnormalidadeAcao;
	}
	
	public ControladorConsumoAnormalidade getControladorConsumoAnormalidade() {
		if(controladorConsumoAnormalidade==null){
			controladorConsumoAnormalidade = ControladorConsumoAnormalidade.getInstance();
		}
		return controladorConsumoAnormalidade;
	}
	
	public ControladorConsumoTarifaCategoria getControladorConsumoTarifaCategoria() {
		if(controladorConsumoTarifaCategoria==null){
			controladorConsumoTarifaCategoria = ControladorConsumoTarifaCategoria.getInstance();
		}
		return controladorConsumoTarifaCategoria;
	}
	
	public ControladorConsumoTarifaFaixa getControladorConsumoTarifaFaixa() {
		if(controladorConsumoTarifaFaixa==null){
			controladorConsumoTarifaFaixa = ControladorConsumoTarifaFaixa.getInstance();
		}
		return controladorConsumoTarifaFaixa;
	}
	
	public ControladorContaCategoriaConsumoFaixa getControladorContaCategoriaConsumoFaixa() {
		if(controladorContaCategoriaConsumoFaixa==null){
			controladorContaCategoriaConsumoFaixa = ControladorContaCategoriaConsumoFaixa.getInstance();
		}
		return controladorContaCategoriaConsumoFaixa;
	}	
	
	public ControladorContaImposto getControladorContaImposto() {
		if(controladorContaImposto==null){
			controladorContaImposto = ControladorContaImposto.getInstance();
		}
		return controladorContaImposto;
	}
	
	public ControladorConta getControladorConta() {
		
		String empresa =  SistemaParametros.getInstancia().getCodigoEmpresaFebraban();
		if(empresa.equals(ConstantesSistema.CODIGO_FEBRABAN_COMPESA)){
			controladorConta = ControladorContaOracle.getInstance();
		} else {
			controladorConta = ControladorContaPostgres.getInstance();
		}
		
		return controladorConta;
	}	
	
	public ControladorImovel getControladorImovel() {
		
		String empresa =  SistemaParametros.getInstancia().getCodigoEmpresaFebraban();
		if(empresa.equals(ConstantesSistema.CODIGO_FEBRABAN_COMPESA)){
			controladorImovel = ControladorImovelOracle.getInstance();
		} else {
			controladorImovel = ControladorImovelPostgres.getInstance();
		}
		
		return controladorImovel;
	}
	
//	public ControladorQualidadeAgua getControladorQualidadeAgua() {
//		if(controladorQualidadeAgua==null){
//			controladorQualidadeAgua = ControladorQualidadeAgua.getInstance();
//		}
//		return controladorQualidadeAgua;
//	}
//	
//	public ControladorContaDebito getControladorContaDebito() {
//		if(controladorContaDebito==null){
//			controladorContaDebito = ControladorContaDebito.getInstance();
//		}
//		return controladorContaDebito;
//	}
	
	public ControladorLeituraAnormalidade getControladorLeituraAnormalidade() {
		if(controladorLeituraAnormalidade==null){
			controladorLeituraAnormalidade = ControladorLeituraAnormalidade.getInstance();
		}
		return controladorLeituraAnormalidade;
	}
	
	public ControladorSequencialRotaMarcacao getControladorSequencialRotaMarcacao() {
		if(controladorSequencialRotaMarcacao==null){
			controladorSequencialRotaMarcacao = ControladorSequencialRotaMarcacao.getInstance();
		}
		return controladorSequencialRotaMarcacao;
	}

	public ControladorImovelRevisitar getControladorImovelRevisitar() {
		
		if(controladorImovelRevisitar == null){
			controladorImovelRevisitar = ControladorImovelRevisitar.getInstance();	
		}
		
		return controladorImovelRevisitar;
	}	

	/**
	 * Atualiza todos os campos do objeto no banco de dados
	 * @param objeto
	 * @throws RepositorioException
	 */
	public void atualizar(ObjetoBasico objeto) throws ControladorException {
		try {
			repositorioBasico.atualizar(objeto);
		} catch (RepositorioException ex) {
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}		
	}
	
	/**
	 * Remover objeto do BD
	 * @param objeto
	 * @throws RepositorioException
	 */
	public void remover(ObjetoBasico objeto) throws ControladorException {
		try {
			repositorioBasico.remover(objeto);
		} catch (RepositorioException ex) {
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}
	
	/**
	 * Insere objeto no BD e retorna id gerado
	 * @param objeto
	 * @throws RepositorioException
	 */
	public long inserir(ObjetoBasico objeto) throws ControladorException {
		try {
			return repositorioBasico.inserir(objeto);
		} catch (RepositorioException ex) {
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}
	
	/**
	 * Pesquisa objeto com base no id 
	 * Recebe como parametro objeto de tipo igual ao seu
	 * @author Amelia Pessoa
	 * @param objeto
	 * @throws RepositorioException
	 */
	public <T extends ObjetoBasico> T pesquisarPorId(Integer id, T objetoTipo) throws ControladorException {
		try {
			return repositorioBasico.pesquisarPorId(id, objetoTipo);
		} catch (RepositorioException ex) {
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}
	
	/**
	 * Pesquisa lista de objetos 
	 * Recebe como parametro objeto de tipo igual ao seu
	 * @author Amelia Pessoa
	 * @param objeto
	 * @throws RepositorioException
	 */
	public <T extends ObjetoBasico>  ArrayList<T> pesquisar(T objetoTipo) throws ControladorException {
		try {
			return repositorioBasico.pesquisar(objetoTipo);
		} catch (RepositorioException ex) {
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}
	
	public boolean verificarExistenciaBancoDeDados() {
		return repositorioBasico.verificarExistenciaBancoDeDados();		
	}
	
	public void carregaLinhaParaBD(String line) throws ControladorException {
		try {
			CarregaBD.getInstance().carregaLinhaParaBD(line);
		} catch (RepositorioException ex) {
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}
	
	public void apagarBanco() {
		RepositorioBasico.getInstance().apagarBanco();
		
		//Apaga todas as subpasta de isc menos a sub pasta carregamento.
		File diretorio = new File(ConstantesSistema.CAMINHO_ISC);                        	
		Util.deletarPastas( diretorio );
	}
	

	public static void copiaArquivoBkp(File arquivo, Long getTime){
		
	    if (arquivo.exists())  {
	    	File pastaBackup = new File(ConstantesSistema.CAMINHO_ISC+"/backupRetorno");
			if(!pastaBackup.isDirectory()){
				pastaBackup.mkdirs();
			}
		    FileChannel sourceChannel = null;  
		     FileChannel destinoChannel = null;  
		     
		     File backup = new File(ConstantesSistema.CAMINHO_BACKUP_RETORNO+"/"+getTime+arquivo.getName());
		     	  
		     try {  
		         try {
		        	 sourceChannel = new FileInputStream(arquivo).getChannel();
				
			         destinoChannel = new FileOutputStream(backup).getChannel();  
			         sourceChannel.transferTo(0, sourceChannel.size(),  
		                 destinoChannel);  
		         } catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
				} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}  
				
		     } finally {  
		         if (sourceChannel != null && sourceChannel.isOpen())
					try {
						sourceChannel.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
		         if (destinoChannel != null && destinoChannel.isOpen())
					try {
						destinoChannel.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
		    
			}  
	    }
	}
}