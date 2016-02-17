
package com.br.ipad.isc.controladores;

import java.util.Date;

import android.content.Context;

import com.br.ipad.isc.bean.ConsumoHistorico;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.excecoes.ControladorException;

public interface IControladorImovel  {

	
	public void setContext(Context ctx);

	/**
	 * Retorna o imóvel selecionado.
	 * 
	 * @return imóvel selecionado.
	 */
	public ImovelConta getImovelSelecionado();

	public void setImovelSelecionado(ImovelConta imovel);


	/**
	 * Retorna se é o momento para realizar uma tentativa de envio das
	 * atualizações pendentes.
	 * 
	 * @return true se for para enviar, false caso contrário.
	 */
	public boolean deveTentarEnviar();

	/**
	 * Retorna o contador de imóveis visitados.
	 * 
	 * @return Vetor de imóveis.
	 */
	public int getContadorVisitados();

	public void setContadorVisitados(int contadorVisitados);

	
	/**
	 * Inverte o roteiro.
	 */
	public void inverterRoteiro();

	/**
	 * Restorna se o roteiro está ou não invertido.
	 * 
	 * @return true se tiver e false caso contrário.
	 */
	public boolean roteiroEstaInvertido();

	/**
	 * Retorna o índice do imóvel selecionado.
	 * 
	 * @return índice do imóvel selecionado.
	 */
	public int getIndiceAtual();

	/**
	 * Retorna o índice do imóvel condominio.
	 * 
	 * @return índice do imóvel condominio.
	 */
	public int getIndiceAtualImovelCondominio();

	/**
	 * Segue para o PRÓXIMO imóvel.
	 * 
	 * @return O identificador do erro de validação.
	 */
	public int proximo() throws ControladorException;

	/**
	 * Volta para o imóvel anterior.
	 * 
	 * @return O identificador do erro de validação.
	 */
	public int anterior() throws ControladorException;

	public void setIndiceImovelCondomio(int indiceImovelCondomio);
	

	/**
	 * Tipo de Medicao do Imovel Atual
	 * 
	 * @return Tipo de Medição
	 */
	public int getTipoMedicaoSelecionado();

	/**
	 * Vai para o tipoMedicao Poço do Imovel selecionado, salvando os valores
	 * alterados caso seja necessário.
	 * 
	 * @param leitura
	 *            Leitura digitada pelo usuário.
	 * @param anormalidade
	 *            Anormalidade digitada pelo usuário.
	 * @return O identificador do erro de validação.
	 */
	public int poco(String leitura, int anormalidade, int tipoMedicao);

	/**
	 * Verifica os dados informados antes de gerar a conta..
	 * 
	 * @param leitura
	 *            Leitura digitada pelo usuário.
	 * @param anormalidade
	 *            Anormalidade digitada pelo usuário.
	 * @return O identificador do erro de validação.
	 */
	public int andarConta(String leitura, int anormalidade, int tipoMedicao);

	
	/**
	 * Calcula o consumo minimo do imovel. Inicialmente tentamos pesquisar por
	 * subcategoria, e caso nao consigamos, pesquisamos por categorias.
	 * 
	 * @param imovel
	 * @return consumo minimo do imovel
	 */
	public int calcularConsumoMinimoImovel(ImovelConta imovel, Date dataInicioVigencia) throws ControladorException;
	

	/**
	 * Segundo o [UC0743] 2.1. o subfluxo [SB0001 - Cáculo Simples para uma
	 * única Tarifa] É aplicado quando todos os dados (Registro do tipo 1) têm a
	 * mesma data inicial de vigência.
	 * 
	 * @return Verdadeiro se for para aplicar o cálculo simples, e falso caso
	 *         contrário.
	 */
	public Object[] deveAplicarCalculoSimples(ImovelConta imovel) throws ControladorException;

	/**
	 * [UC0743] Calcular Valores de Água/Esgoto
	 */
	public void calcularValores(ImovelConta imovel, ConsumoHistorico consumo, int tipoMedicao) throws ControladorException;

	public void carregarImovelSelecionado() throws ControladorException;

	public int getQuantidadeImoveis();

	public int getQtdRegistros();

	public void setQtdRegistros(int qtdRegistros);
	

	public void proximoNaoLido() throws ControladorException;
			

}