
package com.br.ipad.isc.controladores;

import java.util.ArrayList;

import android.content.Context;

import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.excecoes.ControladorException;

public interface IControladorImovelConta  {
	
	public void setContext(Context ctx );

	public Integer getQtdImoveis() throws ControladorException;

	public ArrayList<ImovelConta> buscarImovelContas() throws ControladorException;

	public ArrayList<Integer> getIdsNaoLidos() throws ControladorException;
	
	public double obterValorContaSemImposto(Integer imovelId) throws ControladorException;

	public double obterValorContaSemCreditos(Integer imovelId) throws ControladorException;
	
	public String getDescricaoSitLigacaoEsgoto(Integer idSituacaoLigEsgoto);
	
	public String getDescricaoSitLigacaoAgua(Integer idSituacaoLigAgua);
	
	public String formatarInscricao (String inscricao);
	
	public ArrayList<ImovelConta> buscarImovelContasNaoLidos() throws ControladorException;
	
	
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
	public boolean enviarAoCalcular(ImovelConta imovel) throws ControladorException;

	public ImovelConta buscarImovelContaPosicao(Integer posicao, Boolean proximo) throws ControladorException;
	
	public boolean enviarAoFinalizar(ImovelConta imovel);

	public ArrayList<ImovelConta> buscarImovelContasLidos() throws ControladorException;
	
	/**
	 * Retorna os ids dos im�veis n�o calculados
	 * vinculados ao im�vel macro passado como parametro.
	 * 
	 * @author Amelia Pessoa
	 * @param idImovelMacro
	 * @return ArrayList<Integer>
	 * @throws ControladorException
	 */
	public ArrayList<Integer> buscarImovelCondominiosNaoCalculados(Integer idImovelMacro) throws ControladorException;

	public ImovelConta buscarImovelContaPorHidrometro(String hidrometroNumero) throws ControladorException;

	public ArrayList<ImovelConta> buscarImovelContaPorQuadra(String numeroQuadra) throws ControladorException;
	
	public ImovelConta buscarImovelContaPorPosicao(Integer posicao) throws ControladorException;

	/**
	 * Verifica se o valor da conta informada no im�vel � inferior ao valor
	 * minimo permitido para impress�o da mesma
	 * 
	 * @author Bruno Barros
	 * @date 28/05/2010
	 * @return
	 */
	public boolean isValorContaMenorPermitido(ImovelConta imovel) throws ControladorException;
	
	/**
	 * Verifica se o valor da conta informada no im�vel � superior ao valor
	 * maximo permitido para impress�o da mesma
	 * 
	 * @author Breno Santos
	 * @date 21/06/2011
	 * @return
	 */
	public boolean isValorContaMaiorPermitido(ImovelConta imovel) throws ControladorException;
	
	/**
	 * Retorna o ImovelAreaComum para o ImovelMacro
	 * passado como par�metro
	 * @param imovelMacro
	 * @return
	 */
	public ImovelConta obterImovelAreaComum(Integer idImovelMacro) throws ControladorException;
	
	/**
	 * Retorna o id do �ltimo im�vel micro
	 * para o ImovelMacro passado como par�metro
	 * @param idImovelMacro
	 * @return
	 */
	public Integer obterIdUltimoImovelMicro(Integer idImovelMacro) throws ControladorException;
	
	/**
	 * Retorna a quantidade de imoveis vinculados ao ImovelMacro passado como par�metro
	 * INCLUINDO ele mesmo
	 * 
	 * @param idImovelMacro
	 * @return
	 */
	public Integer obterQuantidadeImovelMicro(Integer idImovelMacro) throws ControladorException;
	
	/**
	 * Retorna os ids dos imoveis micro vinculados ao ImovelMacro passado como par�metro
	 * @author Amelia Pessoa
	 *  
	 * @param idImovelMacro
	 * @return
	 */
	public ArrayList<Integer> buscarIdsImoveisMicro(Integer idImovelMacro) throws ControladorException;
	
	/**
	 * Retorna o id do primeiro imovel micro n�o calculado.
	 * Se n�o houver nenhum, retorna null
	 * 
	 * @author Amelia Pessoa
	 *  
	 * @param idImovelMacro
	 * @return
	 */
	public Integer obterPosicaoImovelCondominioNaoCalculado(Integer idImovelMacro) throws ControladorException;
	
	/**
	 * Retorna os ids dos im�veis n�o impressos
	 * vinculados ao im�vel macro passado como parametro.
	 * 
	 * @author Amelia Pessoa
	 * @param idImovelMacro
	 * @return ArrayList<Integer>
	 * @throws ControladorException
	 */
	public ArrayList<Integer> buscarImovelCondominiosNaoImpressos(Integer idImovelMacro) throws ControladorException;
	
	/**
	 * @author Fernanda Almeida
	 * @return 
	 * @date 25/07/2012
	 * Instancia nova Thread e envia im�vel
	 */
	public void enviarEmBackground(final ImovelConta imovel) throws ControladorException;

	/**
	 * Verifica se todos os im�veis micro vinculado ao macro passado
	 * como parametro tem o indcRateio = SIM (inclusive o proprio macro)
	 * 
	 *  @author Amelia Pessoa
	 * @param idImovelMacro
	 * @return boolean indicando se todos foram rateados
	 * @throws ControladorException
	 */
	public boolean verificarRateioCondominio(Integer idImovelMacro) throws ControladorException;
	
	/**
	 * Verifica se j� existe algum im�vel impresso para o
	 * im�vel macro passado como parametro
	 * 
	 * @author Amelia Pessoa
	 * @param id
	 * @return
	 */
	public boolean existeImovelImpresso(Integer idImovelMacro) throws ControladorException;
	
	
	/**
	 * Retorna a quantidade de imoveis lido
	 * @author Carlos Chaves
	 * @return Integer
	 * @throws ControladorException
	 */
	public Integer obterQuantidadesImoveisLidos() throws ControladorException;

	/**
	 * Retorna ImovelConta com base no sequencial passado como parametro
	 * @author Amelia Pessoa
	 * 
	 * @param sequencial
	 * @return ImovelConta
	 */
	public ImovelConta buscarImovelContaSequencial(int sequencial) throws ControladorException;

	/**
	 * Retorna ids dos im�veis lidos n�o enviados
	 * @author Fernanda
	 * @return ArrayList<Integer>
	 * @throws ControladorException
	 */
	public ArrayList<Integer> buscarIdsImoveisLidosNaoEnviados() throws ControladorException;
	

	/**
	 * Retorna ids dos im�veis lidos n�o enviados,excluindo condominio
	 * @author Fernanda
	 * @return ArrayList<Integer>
	 * @throws ControladorException
	 */
	public ArrayList<Integer> buscarIdsImoveisLidosNaoEnviadosNaoCondominio() throws ControladorException;
	
	/**
	 * Verifica se o imovel pode ser recalculado
	 * @author Carlos Chaves
	 * 
	 * @param Integer indicadorImovelImpresso
	 * @return boolean
	 */
	
	public boolean verificarBloqueioRecalcularConta(ImovelConta imovel) throws ControladorException;
	
	public Integer obterQuantidadeImoveisVisitados() throws ControladorException;
	public Integer obterQuantidadeImoveisVisitadosComAnormalidade() throws ControladorException;
//	public Integer obterQuantidadeImoveisVisitadosSemAnormalidade() throws ControladorException;
	public Integer obterQuantidadeImoveisPorQuadra(Integer numeroQuadra) throws ControladorException;
	public Integer obterQuantidadeImoveisVisitadosPorQuadra(Integer numeroQuadra) throws ControladorException;
	public Integer obterQuantidadeImoveisNaoVisitadosPorQuadra(Integer numeroQuadra) throws ControladorException;	
	public ArrayList<Integer> buscarQuadras() throws ControladorException;
	
	void atualizarIndicadorImovelEnviado(String idImovelMacro) throws ControladorException;
	
	public ArrayList<Integer> obterIdsImoveisMacro() throws ControladorException;
	public void inverterRoteiro() throws ControladorException;	
	public Integer obterPosicaoImovel(Integer imovelId) throws ControladorException;
	public void atualizarPosicaoImovel(Integer posicao,Integer imovelId) throws ControladorException;
	public ArrayList<Integer> obterImovelCondominioCompleto(Integer idImovelMacro) throws ControladorException;
	public ArrayList<Integer> buscarIdsImoveisLidos() throws ControladorException;
	
	public void atualizarIndicadorImovelCalculado(Integer idImovel,Integer indicador) throws ControladorException;
	
	public ArrayList<ImovelConta> buscarImovelCondominio(Integer idImovelMacro) throws ControladorException;
	public ArrayList<Integer> buscarIdsImoveisCalculados() throws 	ControladorException;
	
	/**
	 * Retorna o primeiro im�vel da lista de im�veis
	 * @author S�vio Luiz
	 * @date 26/12/2012
	 */
	public ImovelConta buscarPrimeiroImovel() throws ControladorException;

	/**
	 * Retorna lista de im�veis com os mais novos na frente
	 * [RM8368] COMPESA
	 *  
	 * @author Fernanda Almeida	 * 
	 * @date 09/01/2012
	 * 
	 */
	public ArrayList<ImovelConta> buscarImoveisOrdenadosNovos() throws ControladorException;
	
	/**
	 * Metodo que atualiza os indicadores que indicam que o 
	 * imovel condominio n�o esta calculado
	 * @author Carlos Chaves
	 * @date 07/03/2013
	 * @param Integer idMacro
	 * @param Integer imovelCalculado
	 */
	public void atualizarIndicadorImovelCondominioNaoCalculado(Integer idMacro) 
			throws ControladorException;
	
	/**
	 * Metodo que atualiza o indicador de permite continuar
	 * impressao do imovel macro
	 * @author Carlos Chaves
	 * @date 19/03/2013
	 * @param Integer idMacro
	 */
	public void atualizarIndicadorContinuaImpressao(Integer idImovelMacro, Integer indicadorContinuaImpressao) throws ControladorException;
	
	/**
	 * Metodo que obtem o indicador de permite continuar
	 * impressao do imovel macro
	 * @author Carlos Chaves
	 * @date 19/03/2013
	 * @param Integer idMacro
	 */
	public Integer obterIndicadorPermiteContinuarImpressao(Integer idMacro) throws ControladorException;


	/**
	 * @author Fernanda Almeida
	 * @date 07/03/2013
	 * @param Integer idImovel
	 * @param Integer idMacro
	 * @param Integer imovelCalculado
	 */
	public String formatarRota(String inscricao, ImovelConta imovel);

	/**
	 * Retorna todos os im�vies lidos e n�o impressos, incluindo condom�nio
	 * @author Fernanda
	 * @date 14/05/2013
	 */
	public ArrayList<Integer> buscarIdsImoveisLidosNaoImpressos() throws ControladorException;
	
	/**
	 * Retorna a soma dos valores de agua e esgoto
	 * @author 	Fábio Aguiar
	 * @date 01/06/2015
	 */
	public double obterSomaValorAguaEsgoto(Integer imovelId) throws ControladorException;}