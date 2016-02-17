package com.br.ipad.isc.repositorios;

import java.util.ArrayList;

import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.excecoes.ControladorException;
import com.br.ipad.isc.excecoes.RepositorioException;

public interface IRepositorioImovelConta {
	
	public ArrayList<ImovelConta> buscarImovelContas() throws RepositorioException;
	public Integer getQtdImoveis() throws RepositorioException;
	public ArrayList<Integer> getIdsNaoLidos() throws RepositorioException;
	public ImovelConta buscarImovelContaPorHidrometro(String hidrometroNumero) throws RepositorioException;
	public ArrayList<ImovelConta> buscarImovelContasLidos() throws RepositorioException;
	public ArrayList<ImovelConta> buscarImovelContaPorQuadra(String numeroQuadra) throws RepositorioException;
	public ImovelConta buscarImovelContaPosicao(Integer posicao) throws RepositorioException;

	/**
	 * Retorna os ids dos imóveis não calculados
	 * vinculados ao imóvel macro passado como parametro.
	 * 
	 * @author Amelia Pessoa
	 * @param idImovelMacro
	 * @return ArrayList<Integer>
	 * @throws ControladorException
	 */
	public ArrayList<Integer> buscarImovelCondominiosNaoCalculados(Integer idImovelMacro) throws RepositorioException;
	
	/**
	 * Retorna o ImovelAreaComum para o ImovelMacro
	 * passado como parâmetro
	 * 
	 * @author Amelia Pessoa
	 * @param imovelMacro
	 * @return
	 */
	public ImovelConta obterImovelAreaComum(Integer idImovelMacro) throws RepositorioException;
	
	/**
	 * Retorna o id do último imóvel micro
	 * para o ImovelMacro passado como parâmetro
	 * 
	 * @author Amelia Pessoa
	 * @param idImovelMacro
	 * @return
	 */
	public Integer obterIdUltimoImovelMicro(Integer idImovelMacro) throws RepositorioException;
	
	/**
	 * Retorna a quantidade de imoveis vinculados ao ImovelMacro passado como parâmetro
	 *  
	 * @param idImovelMacro
	 * @return
	 */
	public Integer obterQuantidadeImovelMicro(Integer idImovelMacro) throws RepositorioException;
	
	/**
	 * Retorna os ids dos imoveis micro vinculados ao ImovelMacro passado como parâmetro
	 * @author Amelia Pessoa
	 *  
	 * @param idImovelMacro
	 * @return
	 */
	public ArrayList<Integer> buscarIdsImoveisMicro(Integer idImovelMacro) throws RepositorioException;
	
	/**
	 * Retorna o id do primeiro imovel micro não calculado.
	 * Se não houver nenhum, retorna null
	 * 
	 * @author Amelia Pessoa
	 *  
	 * @param idImovelMacro
	 * @return
	 */
	public Integer obterPosicaoImovelCondominioNaoCalculado(Integer idImovelMacro) throws RepositorioException;
	
	/**
	 * Retorna os ids dos imóveis não impressos
	 * vinculados ao imóvel macro passado como parametro.
	 * 
	 * @author Amelia Pessoa
	 * @param idImovelMacro
	 * @return ArrayList<Integer>
	 * @throws ControladorException
	 */
	public ArrayList<Integer> buscarImovelCondominiosNaoImpressos(Integer idImovelMacro) throws RepositorioException;
	
	/**
	 * Busca nos imóveis micro vinculado ao macro passado
	 * como parametro algum que tenha indcRateio = SIM (inclusive o proprio macro)
	 * 
	 * @author Amelia Pessoa
	 * @param idImovelMacro
	 * @return id do imovel não rateado
	 * @throws ControladorException
	 */
	public Integer verificarRateioCondominio(Integer idImovelMacro) throws RepositorioException;
	
	/**
	 * Busca ids imóveis lidos
	 * 
	 * @author Fernanda Almeida
	 * @return ArrayList<Integer>
	 * @throws RepositorioException
	 */
	public ArrayList<Integer> buscarIdsImoveisLidosNaoEnviados() throws RepositorioException;
	
	/**
	 * Busca ids imóveis lidos não enviado e não condomínio
	 * 
	 * @author Fernanda Almeida
	 * @return ArrayList<Integer>
	 * @throws RepositorioException
	 */
	public ArrayList<Integer> buscarIdsImoveisLidosNaoEnviadosNaoCondominio() throws RepositorioException;
	
	/**
	 * Retorna a quantidade de imoveis lido
	 * @author Carlos Chaves
	 * @return Integer
	 * @throws RepositorioException
	 */
	public Integer obterQuantidadesImoveisLidos() throws RepositorioException;
	
	public Integer obterQuantidadeImoveisVisitados() throws RepositorioException;
	public Integer obterQuantidadeImoveisVisitadosComAnormalidade() throws RepositorioException;
//	public Integer obterQuantidadeImoveisVisitadosSemAnormalidade() throws RepositorioException;
	public Integer obterQuantidadeImoveisPorQuadra(Integer numeroQuadra) throws RepositorioException;
	public Integer obterQuantidadeImoveisVisitadosPorQuadra(Integer numeroQuadra) throws RepositorioException;
	public Integer obterQuantidadeImoveisNaoVisitadosPorQuadra(Integer numeroQuadra) throws RepositorioException;	
	public ArrayList<Integer> buscarQuadras() throws RepositorioException;
	
	public void atualizarIndicadorImovelEnviado(String idImovelMacro) throws RepositorioException;
	public void inverterRoteiroImoveis() throws RepositorioException;	
	public ArrayList<Integer> obterImovelCondominioCompleto(Integer idImovelMacro) throws RepositorioException;
	public ArrayList<Integer> obterIdsImoveisMacro() throws RepositorioException;
	public Integer obterPosicaoImovel(Integer imovelId) throws RepositorioException;
	public void atualizarPosicaoImovel(Integer posicao,Integer imovelId) throws RepositorioException;
	public ArrayList<Integer> buscarIdsImoveisLidos() throws RepositorioException;
	
	public void atualizarIndicadorImovelCalculado(Integer idImovel,Integer indicador) throws RepositorioException;
	
	public ArrayList<ImovelConta> buscarImovelCondominio(Integer idImovelMacro) throws RepositorioException;
	
	public ArrayList<Integer> buscarIdsImoveisCalculados() throws RepositorioException;
	
	public ImovelConta buscarPrimeiroImovel() throws RepositorioException; 
     /**
	 * Retorna os ids dos imóveis CONDOMINIO lidos, considerando o 
	 * imóvel MACRO e todos os seus micros ou nada se os mesmos ainda não estiverem rateados
	 * 
	 * @author Amelia Pessoa
	 * @return ArrayList<Integer>
	 * @throws ControladorException
	 */
	public ArrayList<Integer> buscarIdsImoveisCondominioLidos(String condicao) throws RepositorioException;
	
	/**
	 * Retorna lista de imóveis com sequencial rota nulo
	 * [RM8368] COMPESA
	 *  
	 * @author Fernanda Almeida	 * 
	 * @date 09/01/2012
	 * 
	 */
	public ArrayList<ImovelConta> buscarImoveisSequencialNulo() throws RepositorioException;
	
	/**
	 * Retorna lista de imóveis com sequencial rota não nulo
	 * [RM8368] COMPESA
	 *  
	 * @author Fernanda Almeida	 * 
	 * @date 09/01/2012
	 * 
	 */
	public ArrayList<ImovelConta> buscarImoveisSequencialNaoNulo() throws RepositorioException;
	
	/**
	 * Insere um imovelConta no BD sem a data de vencimento e retorna id gerado
	 * @author Erivan Sousa
	 * @param imovelConta
	 * @throws RepositorioException
	 */
	public long inserirImovelContaVencimento(ImovelConta imovelConta) throws RepositorioException;
	
	/**
	 * Metodo que atualiza os indicadores que indicam que o 
	 * imovel condominio não esta calculado
	 * @author Carlos Chaves
	 * @date 07/03/2013
	 * @param Integer idMacro
	 * @param Integer imovelCalculado
	 */
	public void atualizarIndicadorImovelCondominioNaoCalculado(Integer idMacro) 
			throws RepositorioException;
	
	/**
	 * Metodo que atualiza o indicador de permite continuar
	 * impressao do imovel macro
	 * @author Carlos Chaves
	 * @date 19/03/2013
	 * @param Integer idMacro
	 */
	public void atualizarIndicadorContinuaImpressao(Integer idImovelMacro, Integer indicadorContinuaImpressao) throws RepositorioException;
	
	/**
	 * Metodo que obtem o indicador de permite continuar
	 * impressao do imovel macro
	 * @author Carlos Chaves
	 * @date 19/03/2013
	 * @param Integer idMacro
	 */
	public Integer obterIndicadorPermiteContinuarImpressao(Integer idMacro) throws RepositorioException;
}