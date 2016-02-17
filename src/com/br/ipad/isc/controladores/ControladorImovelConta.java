
package com.br.ipad.isc.controladores;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.DebitoCobrado;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.bean.SistemaParametros;
import com.br.ipad.isc.conexao.ConexaoEnviaImovel;
import com.br.ipad.isc.excecoes.ControladorException;
import com.br.ipad.isc.excecoes.RepositorioException;
import com.br.ipad.isc.fachada.Fachada;
import com.br.ipad.isc.repositorios.RepositorioImovelConta;
import com.br.ipad.isc.util.ConstantesSistema;
import com.br.ipad.isc.util.Util;

public class ControladorImovelConta  extends ControladorBasico implements IControladorImovelConta{
	
	private static ControladorImovelConta instance;
	private RepositorioImovelConta repositorioImovelConta;
	protected static Context context;	
	
	public void resetarInstancia(){
		instance = null;
	}
	
	protected ControladorImovelConta(){
		super();
	}
	
	public static ControladorImovelConta getInstance(){
		if ( instance == null ){
			instance =  new ControladorImovelConta();
			instance.repositorioImovelConta = RepositorioImovelConta.getInstance();
		}
		
		return instance;		
	}

	public void setContext(Context ctx ) {
		context = ctx;		
	}	

	public Integer getQtdImoveis() throws ControladorException {
		try {
			return repositorioImovelConta.getQtdImoveis();
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(getContext().getResources().getString(
					R.string.db_erro));
		}
	}

	public ArrayList<ImovelConta> buscarImovelContas() throws ControladorException {
		try {
			return repositorioImovelConta.buscarImovelContas();
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(getContext().getResources().getString(
					R.string.db_erro));
		}
	}

	public ArrayList<Integer> getIdsNaoLidos() throws ControladorException {
		try {
			return repositorioImovelConta.getIdsNaoLidos();
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(getContext().getResources().getString(
					R.string.db_erro));
		}
	}

	public double obterValorContaSemImposto(Integer imovelId) throws ControladorException {

		double valorContaSemImposto = (
				getControladorContaCategoria().obterValorTotal(imovelId, ConstantesSistema.LIGACAO_AGUA) + 
				getControladorContaCategoria().obterValorTotal(imovelId, ConstantesSistema.LIGACAO_POCO) + 
				getControladorDebitoCobrado().obterValorDebitoTotal(imovelId) -
				getControladorCreditoRealizado().obterValorCreditoTotal(imovelId)
				);

		if (valorContaSemImposto < 0d) {
			valorContaSemImposto = 0d;
		}
		return Util.arredondar(valorContaSemImposto, 2);
	}

	public double obterValorContaSemCreditos(Integer imovelId) throws ControladorException {

		double valorContaSemCreditos = 0d;
		double valorTotalAgua = getControladorContaCategoria().obterValorTotal(imovelId, ConstantesSistema.LIGACAO_AGUA);
		double valorTotalEsgoto = getControladorContaCategoria().obterValorTotal(imovelId, ConstantesSistema.LIGACAO_POCO);
		double valorTotalDebito = getControladorDebitoCobrado().obterValorDebitoTotal(imovelId);
		double valorTotalImposto = getControladorContaImposto().obterValorImpostoTotal(imovelId);
			
		valorContaSemCreditos = valorTotalAgua + valorTotalEsgoto + valorTotalDebito - valorTotalImposto;
				
		return Util.arredondar(valorContaSemCreditos, 2);
	}
	
		
	/**
	 * M�todo que verifica se o im�vel deve ser enviado logo ap�s seu calculo,
	 * ou apenas no final do roteiro
	 * 
	 * @author Bruno Barros
	 * @date 20/10/2009
	 * @return true - O im�vel deve ser enviado assim que calculado false - O
	 *         im�vel deve N�O ser enviado assim que calculado
	 * @throws ControladorException 
	 **/
	public boolean enviarAoCalcular(ImovelConta imovel) throws ControladorException {

		// Verificamos o valor m�nimo da conta
		boolean enviarContaValorMenorPermitido = isValorContaMenorPermitido(imovel);
		
		DebitoCobrado debito = getControladorDebitoCobrado().buscarDebitoCobradoPorCodigo(
				ConstantesSistema.TARIFA_CORTADO_DEC_18_251_94,imovel.getId());
//
		//SistemaParametros sistemaParametros = SistemaParametros.getInstancia();
//		/**
//		 * Ser� necess�rio reenviar caso haja altera��o na leitura de agua ou
//		 * anormalidade de agua ou na leitura de poco ou anormalidade de poco ou
//		 * na anormalidade sem hidrometro. S� podemos enviar im�veis que n�o
//		 * tenham valores residuais de cr�dito e n�o possuam debito do tipo
//		 * tarifa de cortado e a conta n�o senha maior que o permitido OU se a
//		 * op��o de bloqueio de reemiss�o de conta estiver marcada
//		 **/
		double valorCreditos = Fachada.getInstance().obterValorCreditoTotal(imovel.getId());
		double valorContaSemCreditos = obterValorContaSemCreditos(imovel.getId());
		double valorResidual = 0d;
		
		if (valorCreditos != 0d) {
			if (valorContaSemCreditos < valorCreditos) {
				valorResidual = valorCreditos - valorContaSemCreditos;							
			}
		}
				
		if (imovel.getIndcImovelCalculado().equals(ConstantesSistema.SIM)
				&& imovel.getIndcImovelEnviado().equals(ConstantesSistema.NAO)
				&& (valorResidual == 0d && debito == null && enviarContaValorMenorPermitido)){
				//	� perigoso usar essa op��o, pois se a conta for impressa novamente
				//  pode dar inconsistencia no GSAN
//				|| sistemaParametros.getIndcBloquearReemissaoConta().equals(ConstantesSistema.SIM)
			return true;
		} else {
			return false;
		}
	}
	
	public String getDescricaoSitLigacaoAgua(Integer idSituacaoLigAgua) {
		String descricaoSitLigacaoAgua = "";
		
		switch (idSituacaoLigAgua) {
		case ConstantesSistema.POTENCIAL:
			descricaoSitLigacaoAgua = "POTENCIAL";
			break;
		case ConstantesSistema.FACTIVEL:
			descricaoSitLigacaoAgua = "FACTIVEL";
			break;
		case ConstantesSistema.LIGADO:
			descricaoSitLigacaoAgua = "LIGADO";
			break;
		case ConstantesSistema.EM_FISCALIZACAO:
			descricaoSitLigacaoAgua = "LIGADO EM ANALISE.";
			break;
		case ConstantesSistema.CORTADO:
			descricaoSitLigacaoAgua = "CORTADO";
			break;
		case ConstantesSistema.SUPRIMIDO:
			descricaoSitLigacaoAgua = "SUPRIMIDO";
			break;
		case ConstantesSistema.SUPR_PARC:
			descricaoSitLigacaoAgua = "SUPR. PARC.";
			break;
		case ConstantesSistema.SUPR_PARC_PEDIDO:
			descricaoSitLigacaoAgua = "SUP. PARC. PED.";
			break;
		case ConstantesSistema.EM_CANCELAMENTO:
			descricaoSitLigacaoAgua = "EM CANCEL.";
			break;
		}
		
		return descricaoSitLigacaoAgua;
	}

	public String getDescricaoSitLigacaoEsgoto(Integer idSituacaoLigacaoEsgoto) {
		String descricaoSitLigacaoEsgoto = "";
		switch (idSituacaoLigacaoEsgoto) {
		case ConstantesSistema.POTENCIAL:
			descricaoSitLigacaoEsgoto = "POTENCIAL";
			break;
		case ConstantesSistema.FACTIVEL:
			descricaoSitLigacaoEsgoto = "FACTIVEL";
			break;
		case ConstantesSistema.LIGADO:
			descricaoSitLigacaoEsgoto = "LIGADO";
			break;
		case ConstantesSistema.EM_FISCALIZACAO:
			descricaoSitLigacaoEsgoto = "EM FISCAL.";
			break;
		case ConstantesSistema.LIG_FORA_USO:
			descricaoSitLigacaoEsgoto = "LIG. FORA DE USO";
			break;
		case ConstantesSistema.TAMPONADO:
			descricaoSitLigacaoEsgoto = "TAMPONADO";
			break;
		case ConstantesSistema.CONVERSAO:
			descricaoSitLigacaoEsgoto = "CONVERSAO";
			break;
		}
		return descricaoSitLigacaoEsgoto;
	}

	/**
	 * @author Fernanda Almeida
	 * @param posicao,proximo 
	 * @date 30/07/2012
	 */
	public ImovelConta buscarImovelContaPosicao(Integer posicao, Boolean proximo)
			throws ControladorException {
		try {

			ArrayList<ImovelConta> imoveis = repositorioImovelConta.buscarImovelContas();
			
			if(proximo != null){
				//Caso tenha clicado no bot�o "pr�ximo"
				if(proximo == true){
					//Caso esteja na �ltima posic�o, manda para a primeira
					if(imoveis.size() == posicao.intValue()){
						posicao = 1;
					}else{
						posicao++;
					}
				//Caso tenha clicado no bot�o "Anterior"
				}else{
					//Caso esteja na primeira posi��o, manda para a �ltima
					if(posicao.intValue() == 1){
						posicao = imoveis.size();
					}else{
						posicao--;
					}
				}
			}
			
			return repositorioImovelConta.buscarImovelContaPosicao(posicao);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(getContext().getResources().getString(
					R.string.db_erro));
		}
	}
	
	/**
	 * M�todo que verifica se o im�vel deve ser enviado ao finalizar o processo.
	 * 
	 * @author Bruno Barros
	 * @date 18/05/2010
	 * @return true - O im�vel deve ser enviado false - O im�vel deve N�O ser
	 *         enviado
	 * 
	 **/
	public boolean enviarAoFinalizar(ImovelConta imovel) {
		
		if( ((imovel.getIndcImovelCalculado().equals(ConstantesSistema.SIM) 
				&& !imovel.isCondominio() ) 
				//Condicao 2
				|| 
				(imovel.isCondominio() 
				&& imovel.getIndcRateioRealizado().equals(ConstantesSistema.SIM)))
				&& imovel.getIndcImovelEnviado().equals(ConstantesSistema.NAO))
				{
			return true;
		}else{
			return false;
		}
		
	}
	
	public ArrayList<ImovelConta> buscarImovelContasLidos() throws ControladorException {
		try {
			return repositorioImovelConta.buscarImovelContasLidos();
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(getContext().getResources().getString(
					R.string.db_erro));
		}
	}
	
	public String formatarInscricao (String inscricao){
	
		String inscricaoFormatada = inscricao.trim();

		String localidade, setor, quadra, lote, sublote;

		if (inscricaoFormatada.length() == 16) {
			localidade = inscricao.substring(0, 3);
			setor = inscricao.substring(3, 6);
			quadra = inscricao.substring(6, 9);
			lote = inscricao.substring(9, 13);
			sublote = inscricao.substring(13, 16);

			inscricaoFormatada = localidade + "." + setor + "." + quadra + "." + lote + "." + sublote;
		} else {
			localidade = inscricao.substring(0, 3);
			setor = inscricao.substring(3, 6);
			quadra = inscricao.substring(6, 10);
			lote = inscricao.substring(10, 14);
			sublote = inscricao.substring(14, 17);

			inscricaoFormatada = localidade + "." + setor + "." + quadra + "." + lote + "." + sublote;
		}

		return inscricaoFormatada;
	
	
	}
	
public String formatarRota (String inscricao,ImovelConta imovel){
		
		String rotaFormatada = inscricao.trim();

		String localidade, setor, rota, seq;

		localidade = inscricao.substring(0, 3);
		setor = inscricao.substring(3, 6);
		rota = imovel.getCodigoRota().toString();
		seq = imovel.getSequencialRota().toString();
		String codigoEmpresa =  SistemaParametros.getInstancia().
				getCodigoEmpresaFebraban();

		rotaFormatada = localidade + "." + setor + "." + rota;
		if(codigoEmpresa != null && codigoEmpresa.equals(ConstantesSistema.CODIGO_FEBRABAN_CAERN)){
			rotaFormatada += "/" + seq;
		}

		return rotaFormatada;
	
	
	}
	
	public ArrayList<ImovelConta> buscarImovelContasNaoLidos() throws ControladorException {
		try {
			return repositorioImovelConta.buscarImovelContasNaoLidos();
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(getContext().getResources().getString(
					R.string.db_erro));
		}
	}


	/**
	 * Retorna os ids dos im�veis n�o calculados
	 * vinculados ao im�vel macro passado como parametro.
	 * 
	 * @author Amelia Pessoa
	 * @param idImovelMacro
	 * @return ArrayList<Integer>
	 * @throws ControladorException
	 */
	public ArrayList<Integer> buscarImovelCondominiosNaoCalculados(Integer idImovelMacro) throws ControladorException {
		try {
			return repositorioImovelConta.buscarImovelCondominiosNaoCalculados(idImovelMacro);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(getContext().getResources().getString(
					R.string.db_erro));
		}
	}


	/**
	 * Retorna os ids dos im�veis n�o impressos
	 * vinculados ao im�vel macro passado como parametro.
	 *  
	 * @author Amelia Pessoa
	 * @param idImovelMacro
	 * @return ArrayList<Integer>
	 * @throws ControladorException
	 */
	public ArrayList<Integer> buscarImovelCondominiosNaoImpressos(Integer idImovelMacro) throws ControladorException {
		try {
			return repositorioImovelConta.buscarImovelCondominiosNaoImpressos(idImovelMacro);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(getContext().getResources().getString(
					R.string.db_erro));
		}
	}
	
	public ImovelConta buscarImovelContaPorHidrometro(String hidrometroNumero)
			throws ControladorException {
		try {
			return repositorioImovelConta.buscarImovelContaPorHidrometro(hidrometroNumero);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(getContext().getResources().getString(
					R.string.db_erro));
		}
	}

	
	public ArrayList<ImovelConta> buscarImovelContaPorQuadra(String numeroQuadra)
			throws ControladorException {
		try {
			return repositorioImovelConta.buscarImovelContaPorQuadra(numeroQuadra);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(getContext().getResources().getString(
					R.string.db_erro));
		}
	}

	/**
	 * Verifica se o valor da conta informada no im�vel � inferior ao valor
	 * minimo permitido para impress�o da mesma
	 * 
	 * @author Bruno Barros
	 * @date 28/05/2010
	 * @return
	 */
	public boolean isValorContaMenorPermitido(ImovelConta imovel) throws ControladorException {

		// Caso o valor da conta seja menor que o valor
		// permitido
		// para ser impresso,
		// s� enviar a conta no final do processo (Finalizar Roteiro)
		boolean enviarContaValorMaiorPermitido = true;

		double valorConta = getControladorConta().obterValorConta(imovel.getId()); 
				
		double valorMinimoEmissaoConta =  SistemaParametros.getInstancia().
				getValorMinimEmissaoConta().doubleValue();
		
		if (valorConta < valorMinimoEmissaoConta) {
			if (getControladorCreditoRealizado().obterValorCreditoTotal(imovel.getId()).doubleValue() == 0d ) {
				enviarContaValorMaiorPermitido = false;
			}
		}

		return enviarContaValorMaiorPermitido;
	}

	/**
	 * Verifica se o valor da conta informada no im�vel � superior ao valor
	 * maximo permitido para impress�o da mesma
	 * 
	 * @author Breno Santos
	 * @date 21/06/2011
	 * @return
	 */
	public boolean isValorContaMaiorPermitido(ImovelConta imovel) throws ControladorException {

		// Caso o valor da conta seja maior que o valor
		// permitido
		// para ser impresso,
		// s� enviar a conta no final do processo (Finalizar Roteiro)
		boolean enviarContaValorMaiorPermitido = true;

		double valorConta = getControladorConta().obterValorConta(imovel.getId());
		
		double valorMaximoEmissaoConta = ConstantesSistema.VALOR_LIMITE_CONTA;

		if (valorConta > valorMaximoEmissaoConta) {
			enviarContaValorMaiorPermitido = false;
		}

		return enviarContaValorMaiorPermitido;
	}
	

	/**
	 * Retorna o ImovelAreaComum para o ImovelMacro
	 * passado como par�metro
	 * @param idImovelMacro
	 * @return
	 */
	public ImovelConta obterImovelAreaComum(Integer idImovelMacro) throws ControladorException{
		try {
			return repositorioImovelConta.obterImovelAreaComum(idImovelMacro);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(getContext().getResources().getString(
					R.string.db_erro));
		}
	}

	
	public ImovelConta buscarImovelContaPorPosicao(Integer posicao) throws ControladorException {
		try {
			return repositorioImovelConta.buscarImovelContaPosicao(posicao);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(getContext().getResources().getString(
					R.string.db_erro));
		}
	}
	


	/**
	 * Retorna o id do �ltimo im�vel micro
	 * para o ImovelMacro passado como par�metro
	 * 
	 * @author Amelia Pessoa
	 * @param idImovelMacro
	 * @return
	 */
	public Integer obterIdUltimoImovelMicro(Integer idImovelMacro) throws ControladorException{
		try {
			return repositorioImovelConta.obterIdUltimoImovelMicro(idImovelMacro);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(getContext().getResources().getString(
					R.string.db_erro));
		}
	}
	
	/**
	 * Retorna a quantidade de imoveis vinculados ao ImovelMacro passado como par�metro
	 * INCLUINDO ele mesmo
	 * 
	 * @param idImovelMacro
	 * @return
	 */
	public Integer obterQuantidadeImovelMicro(Integer idImovelMacro) throws ControladorException{
		try {
			return repositorioImovelConta.obterQuantidadeImovelMicro(idImovelMacro) + 1;
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(getContext().getResources().getString(
					R.string.db_erro));
		}
	}
	
	/**
	 * Retorna os ids dos imoveis micro vinculados ao ImovelMacro passado como par�metro
	 * 
	 * @author Amelia Pessoa
	 *  
	 * @param idImovelMacro
	 * @return
	 */
	public ArrayList<Integer> buscarIdsImoveisMicro(Integer idImovelMacro) throws ControladorException {
		try {
			return repositorioImovelConta.buscarIdsImoveisMicro(idImovelMacro);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(getContext().getResources().getString(
					R.string.db_erro));
		}
	}
	
	/**
	 * Retorna o id do primeiro imovel micro n�o calculado.
	 * Se n�o houver nenhum, retorna null
	 * 
	 * @author Amelia Pessoa
	 *  
	 * @param idImovelMacro
	 * @return
	 */
	public Integer obterPosicaoImovelCondominioNaoCalculado(Integer idImovelMacro) throws ControladorException {
		try {
			return repositorioImovelConta.obterPosicaoImovelCondominioNaoCalculado(idImovelMacro);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(getContext().getResources().getString(
					R.string.db_erro));
		}
	}

	/**
	 * Verifica se todos os im�veis micro vinculado ao macro passado
	 * como parametro tem o indcRateio = SIM (inclusive o proprio macro)
	 * 
	 * @author Amelia Pessoa
	 * @param idImovelMacro
	 * @return boolean indicando se todos foram rateados
	 * @throws ControladorException
	 */
	public boolean verificarRateioCondominio(Integer idImovelMacro) throws ControladorException {
		try {
			Integer id =  repositorioImovelConta.verificarRateioCondominio(idImovelMacro);
			return id==null?true:false;
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(getContext().getResources().getString(
					R.string.db_erro));
		}
	}

	/**
	 * Verifica se j� existe algum im�vel impresso para o
	 * im�vel macro passado como parametro
	 * (Se todos foram impressos retorna false)
	 * 
	 * @author Amelia Pessoa
	 * @param id
	 * @return
	 */
	public boolean existeImovelImpresso(Integer idImovelMacro) throws ControladorException {
		try {
			Integer id =  repositorioImovelConta.existeImovelImpresso(idImovelMacro);
			if (id!=null){
				//Verifica se todos ja foram impressos
				ArrayList<Integer> naoImpressos = buscarImovelCondominiosNaoImpressos(idImovelMacro);
				if (naoImpressos==null || naoImpressos.size()==0){
					return false;
				}
			} else {
				return false;
			}
			return true;
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(getContext().getResources().getString(
					R.string.db_erro));
		}
	}	
	
	/**
	 * @author Fernanda Almeida
	 * @date 25/07/2012
	 * Instancia nova Thread e envia im�vel
	 */
	public void enviarEmBackground(final ImovelConta imovel) throws ControladorException {		
									
		ImovelConta imovelAtualizado = (ImovelConta) Fachada.getInstance()
				.pesquisarPorId(imovel.getId(), new ImovelConta()); 
		
		// S� envia caso transmisssao offline seja igual a 2 e imov�l n�o tenha sido enviado
		if(SistemaParametros.getInstancia().getIndicadorTransmissaoOffline().equals(ConstantesSistema.NAO) 
				&& imovelAtualizado.getIndcImovelEnviado().equals(ConstantesSistema.NAO)){
			
			ConexaoEnviaImovel conexaoEnviaImovel = new ConexaoEnviaImovel();
			conexaoEnviaImovel.execute(imovelAtualizado,getContext());
		}
				
	}

	/**
	 * Retorna todos os im�vies lidos e n�o enviados, incluindo condom�nio
	 * @author Fernanda
	 * @date 26/09/2012
	 */
	public ArrayList<Integer> buscarIdsImoveisLidosNaoEnviados() throws ControladorException {	
		try {
			return repositorioImovelConta.buscarIdsImoveisLidosNaoEnviados();
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(getContext().getResources().getString(
					R.string.db_erro));
		}
	}
	
	/**
	 * Retorna todos os im�vies lidos e n�o enviados, excluindo condom�nio
	 * @author Fernanda
	 * @date 26/09/2012
	 */
	public ArrayList<Integer> buscarIdsImoveisLidosNaoEnviadosNaoCondominio() throws ControladorException {	
		try {
			return repositorioImovelConta.buscarIdsImoveisLidosNaoEnviadosNaoCondominio();
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(getContext().getResources().getString(
					R.string.db_erro));
		}
	}
	
	/**
	 * Retorna a quantidade de imoveis lido
	 * @author Carlos Chaves
	 * @return Integer
	 * @throws ControladorException
	 */
	public Integer obterQuantidadesImoveisLidos() throws ControladorException{
		try {
			return repositorioImovelConta.obterQuantidadesImoveisLidos();
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(getContext().getResources().getString(
					R.string.db_erro));
		}
	}
	
	/**
	 * Retorna ImovelConta com base no sequencial passado como parametro
	 * @author Amelia Pessoa
	 * 
	 * @param sequencial
	 * @return ImovelConta
	 */
	public ImovelConta buscarImovelContaSequencial(int sequencial) throws ControladorException{
		try {
			return repositorioImovelConta.buscarImovelContaSequencial(sequencial);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(getContext().getResources().getString(
					R.string.db_erro));
		}
	}

	
	/**
	 * Verifica se o imovel pode ser recalculado
	 * @author Carlos Chaves
	 * 
	 * @param Integer indicadorImovelImpresso
	 * @return boolean
	 */	
	public boolean verificarBloqueioRecalcularConta(ImovelConta imovel) throws ControladorException { 
		boolean bloqueiaConta = false;
		if(imovel.getIndcImovelCalculado().equals(ConstantesSistema.SIM ) 
				&& SistemaParametros.getInstancia().getIndcBloquearReemissaoConta().equals(ConstantesSistema.SIM) 
				&& SistemaParametros.getInstancia().getIndicadorSistemaLeitura().equals(ConstantesSistema.NAO)
				){
			if(!imovel.isCondominio()){
				bloqueiaConta = true;
			}else{
				if(imovel.getIndcRateioRealizado().equals(ConstantesSistema.NAO)){
					bloqueiaConta = false;
				}else{
					bloqueiaConta = true;
				}
			}
		}
		return bloqueiaConta;
	}
	
	/**
	 * Retorna a quantidade de imoveis visistado
	 * @author Carlos Chaves
	 * @return Integer
	 * @throws RepositorioException
	 */
	public Integer obterQuantidadeImoveisVisitados() throws ControladorException{
		try {
			return repositorioImovelConta.obterQuantidadeImoveisVisitados();
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(getContext().getResources().getString(
					R.string.db_erro));
		}
	}
	
	/**
	 * Retorna a quantidade de imoveis visistado com anormalidade
	 * @author Carlos Chaves
	 * @return Integer
	 * @throws RepositorioException
	 */
	public Integer obterQuantidadeImoveisVisitadosComAnormalidade() throws ControladorException{
		try {
			return repositorioImovelConta.obterQuantidadeImoveisVisitadosComAnormalidade();
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(getContext().getResources().getString(
					R.string.db_erro));
		}
	}
	
	/**
	 * Retorna a quantidade de imoveis visistado sem anormalidade
	 * @author Carlos Chaves
	 * @return Integer
	 * @throws RepositorioException
	 */
//	public Integer obterQuantidadeImoveisVisitadosSemAnormalidade() throws ControladorException{
//		try {
//			return repositorioImovelConta.obterQuantidadeImoveisVisitadosSemAnormalidade();
//		} catch (RepositorioException ex){
//			ex.printStackTrace();
//			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
//			throw new ControladorException(getContext().getResources().getString(
//					R.string.db_erro));
//		}
//	}
	
	/**
	 * Retorna a quantidade de imoveis de uma quadra
	 * @param Quadra
	 * @author Carlos Chaves
	 * @return Integer
	 * @throws RepositorioException
	 */
	public Integer obterQuantidadeImoveisPorQuadra(Integer numeroQuadra) throws ControladorException{
		try {
			return repositorioImovelConta.obterQuantidadeImoveisPorQuadra(numeroQuadra);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(getContext().getResources().getString(
					R.string.db_erro));
		}
	}
	
	/**
	 * Retorna a quantidade de imoveis visitados de uma quadra
	 * @param Quadra
	 * @author Carlos Chaves
	 * @return Integer
	 * @throws RepositorioException
	 */
	public Integer obterQuantidadeImoveisVisitadosPorQuadra(Integer numeroQuadra) throws ControladorException{
		try {
			return repositorioImovelConta.obterQuantidadeImoveisVisitadosPorQuadra(numeroQuadra);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(getContext().getResources().getString(
					R.string.db_erro));
		}
	}
	
	/**
	 * Retorna a quantidade de imoveis nao visitados de uma quadra
	 * @param Quadra
	 * @author Carlos Chaves
	 * @return Integer
	 * @throws RepositorioException
	 */
	public Integer obterQuantidadeImoveisNaoVisitadosPorQuadra(Integer numeroQuadra) throws ControladorException{
		try {
			return repositorioImovelConta.obterQuantidadeImoveisNaoVisitadosPorQuadra(numeroQuadra);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(getContext().getResources().getString(
					R.string.db_erro));
		}
	}
	
	/**
	 * Retorna os ids de todas as quadras
	 * 
	 * @author Carlos Chaves
	 * @return ArrayList<Integer>
	 * @throws RepositorioException
	 */
	public ArrayList<Integer> buscarQuadras() throws ControladorException {
		try {
			return repositorioImovelConta.buscarQuadras();
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(getContext().getResources().getString(
					R.string.db_erro));
		}
	}

	/**
	 * @author Fernanda
	 * @date 12/09/2012
	 * @param ImovelConta
	 */
	@Override
	public void atualizarIndicadorImovelEnviado(String idImovelMacro) throws ControladorException {
		
		try{
			repositorioImovelConta.atualizarIndicadorImovelEnviado(idImovelMacro);
			
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(getContext().getResources().getString(
					R.string.db_erro));
		}
	}
	
	/**
	 * Metodo que inverte o roteiro de todos os imoveis sem
	 * tratar o imovel condominio.
	 * @author Carlos Chaves
	 * @date 19/09/2012
	 */
	private void inverterRoteiroImoveis() throws ControladorException {
		
		try{
			repositorioImovelConta.inverterRoteiroImoveis();
			
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(getContext().getResources().getString(
					R.string.db_erro));
		}
	}
		
	/**
	 * Metodo que inverte o roteiro
	 * @author Carlos Chaves
	 * @date 19/09/2012
	 */
	@Override
	public void inverterRoteiro() throws ControladorException {
		
		//Inverte o roteiro completo sem tratar
		//o imovel condomino
		inverterRoteiroImoveis();
		
		//Trata a invercao da rota do imovel condominio
		
		ArrayList<Integer> arrayListIdsImoveisMacro = new ArrayList<Integer>(); 
		
		try{
			//Obtem todos os imoveis macro
			arrayListIdsImoveisMacro = obterIdsImoveisMacro();
			
			for(int i=0; i< arrayListIdsImoveisMacro.size(); i++){
				//Obtem imovel condominio completo (macro + micros)
				ArrayList<Integer> arrayListIdsImoveisCondominio = obterImovelCondominioCompleto(arrayListIdsImoveisMacro.get(i));
				
				int posicao = obterPosicaoImovel(arrayListIdsImoveisCondominio.get(arrayListIdsImoveisCondominio.size()-1));
				
				for(int j=0; j< arrayListIdsImoveisCondominio.size(); j++){
					atualizarPosicaoImovel(posicao, arrayListIdsImoveisCondominio.get(j));
					posicao--;
				}
				
			}
		//Fim Trata a invercao da rota do imovel condominio	
		} catch (ControladorException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(getContext().getResources().getString(
					R.string.db_erro));
		}
	}
	
	/**
	 * Retorna os ids dos imoveis macro
	 * 
	 * @author Carlos Chaves
	 * @return ArrayList<Integer>
	 * @throws RepositorioException
	 */
	@Override
	public ArrayList<Integer> obterIdsImoveisMacro() throws ControladorException {
		try {
			return repositorioImovelConta.obterIdsImoveisMacro();
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(getContext().getResources().getString(
					R.string.db_erro));
		}
	}
	
	/**
	 * Retorna a posicao do imovel
	 * @param Integer imovelId
	 * @author Carlos Chaves
	 * @return Integer
	 * @throws RepositorioException
	 */
	public Integer obterPosicaoImovel(Integer imovId) throws ControladorException{
		try {
			return repositorioImovelConta.obterPosicaoImovel(imovId);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(getContext().getResources().getString(
					R.string.db_erro));
		}
	}

	public void atualizarPosicaoImovel(Integer posicao, Integer imovelId) throws ControladorException {
		
		try{
			repositorioImovelConta.atualizarPosicaoImovel(posicao, imovelId);
			
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(getContext().getResources().getString(
					R.string.db_erro));
		}
	}
	
	/**
	 * Metodo que retorna os ids do imovel condominio completo(id do macro e ids dos micros). 
	 * @param Integer idImovelMacro
	 * @author Carlos Chaves
	 * @date 19/09/2012
	 */
	public ArrayList<Integer> obterImovelCondominioCompleto(Integer idImovelMacro) throws ControladorException {
		try {
			return repositorioImovelConta.obterImovelCondominioCompleto(idImovelMacro);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(getContext().getResources().getString(
					R.string.db_erro));
		}
	}
	
	public ArrayList<Integer> buscarIdsImoveisLidos() throws ControladorException {	
		try {
			return repositorioImovelConta.buscarIdsImoveisLidos();
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(getContext().getResources().getString(
					R.string.db_erro));
		}
	}

	/**
	 * Atualiza o indicador de imovel calculado, de acordo com o valor passado
	 * @author Fernanda
	 * @param Integer idImovel,Integer indicador
	 * @date 02/10/2012
	 */
	@Override
	public void atualizarIndicadorImovelCalculado(Integer idImovel,
			Integer indicador) throws ControladorException {
		try {
			repositorioImovelConta.atualizarIndicadorImovelCalculado(idImovel,indicador);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(getContext().getResources().getString(
					R.string.db_erro));
		}
		
	}
	
	public ArrayList<ImovelConta> buscarImovelCondominio(Integer idImovelMacro) throws ControladorException {
		try {
			return repositorioImovelConta.buscarImovelCondominio(idImovelMacro);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(getContext().getResources().getString(
					R.string.db_erro));
		}
	}
	
	/**
	 * Retorna todos os im�vies lidos, incluindo condom�nio ja calculado
	 * @author Carlos Chaves
	 * @date 30/10/2012
	 */
	public ArrayList<Integer> buscarIdsImoveisCalculados() throws ControladorException {	
		try {
			return repositorioImovelConta.buscarIdsImoveisCalculados();
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(getContext().getResources().getString(
					R.string.db_erro));
		}
	}
	
	/**
	 * Retorna o primeiro im�vel da lista de im�veis
	 * @author S�vio Luiz
	 * @date 26/12/2012
	 */
	public ImovelConta buscarPrimeiroImovel() throws ControladorException {	
		try {
			return repositorioImovelConta.buscarPrimeiroImovel();
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(getContext().getResources().getString(
					R.string.db_erro));
		}
	}

	/**
	 * Retorna lista de im�veis com os mais novos na frente
	 * [RM8368] COMPESA
	 *  
	 * @author Fernanda Almeida	 
	 * @date 09/01/2012
	 * 
	 */
	public ArrayList<ImovelConta> buscarImoveisOrdenadosNovos()
			throws ControladorException {
				
		try {
			ArrayList<ImovelConta> listaRetorno = new ArrayList<ImovelConta>();
			
			ArrayList<ImovelConta> listaSequencialNulo = repositorioImovelConta.buscarImoveisSequencialNulo();
		
			ArrayList<ImovelConta> listaSequencialNaoNulo = repositorioImovelConta.buscarImoveisSequencialNaoNulo();
			
			// junta as listas
			listaRetorno  = listaSequencialNulo;
			listaRetorno.addAll(listaSequencialNaoNulo);
			
			return listaRetorno;
		} catch (RepositorioException e) {
			e.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , e.getMessage());
			throw new ControladorException(getContext().getResources().getString(
					R.string.db_erro));
		}
		
	}
	
	
	/**
	 * Metodo que atualiza os indicadores que indicam que o 
	 * imovel condominio n�o esta calculado
	 * @author Carlos Chaves
	 * @date 07/03/2013
	 * @param Integer idMacro
	 * @param Integer imovelCalculado
	 */
	public void atualizarIndicadorImovelCondominioNaoCalculado(Integer idMacro)  
			throws ControladorException{

		try{
			repositorioImovelConta.atualizarIndicadorImovelCondominioNaoCalculado(idMacro);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(getContext().getResources().getString(
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
	public void atualizarIndicadorContinuaImpressao(Integer idMacro, Integer indicadorContinuaImpressao)  
			throws ControladorException{

		try{
			
			if(idMacro!=null){
				repositorioImovelConta.atualizarIndicadorContinuaImpressao(idMacro, indicadorContinuaImpressao);
			}
			
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(getContext().getResources().getString(
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
	public Integer obterIndicadorPermiteContinuarImpressao(Integer idMacro) throws ControladorException {	
		try {
			return repositorioImovelConta.obterIndicadorPermiteContinuarImpressao(idMacro);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(getContext().getResources().getString(
					R.string.db_erro));
		}
	}
	
	/**
	 * Retorna todos os im�vies lidos e n�o impressos, incluindo condom�nio
	 * @author Fernanda
	 * @date 14/05/2013
	 */
	public ArrayList<Integer> buscarIdsImoveisLidosNaoImpressos() throws ControladorException {
		try {
			return repositorioImovelConta.buscarIdsImoveisLidosNaoImpressos();
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(getContext().getResources().getString(
					R.string.db_erro));
		}
	}
	
	/**
	 * Retorna a soma dos valores de agua e esgoto
	 * @author Fábio Aguiar
	 * @date 01/06/2015
	 */
	public double obterSomaValorAguaEsgoto(Integer imovelId) throws ControladorException {

		double valorConta = (
				getControladorContaCategoria().obterValorTotal(imovelId, ConstantesSistema.LIGACAO_AGUA) + 
				getControladorContaCategoria().obterValorTotal(imovelId, ConstantesSistema.LIGACAO_POCO)
				);

		if (valorConta < 0d) {
			valorConta = 0d;
		}
		return Util.arredondar(valorConta, 2);
	}}