
package com.br.ipad.isc.bean.helpers;


/**
 * [UC0970] Efetuar Rateio de Consumo no Dispositivo Movel
 * 
 * Metodo responsavel em efeturar a divisão da diferença entre o consumo
 * coletado no hidrometro macro e a soma dos hidrometros micro
 * 
 * Helper responsavel pela passagem de parametros entre os métodos do UC supra
 * citado.
 * 
 * @date 10/08/2012
 * @author Amelia Pessoa
 * 
 */
public class RateioConsumoHelper {

	public RateioConsumoHelper(Integer imovelId){
		this.imovelId = imovelId;
	}
	public RateioConsumoHelper(){}
	
	private int imovelId;

	// Agua
	int quantidadeEconomiasAguaMedidas = 0;
	int quantidadeEconomiasAguaNaoMedidas = 0;
	int consumoLigacaoAguaTotal = 0;

	// Esgoto
	int quantidadeEconomiasEsgotoMedidas = 0;
	int quantidadeEconomiasEsgotoNaoMedidas = 0;
	int consumoLigacaoEsgotoTotal = 0;

	// Geral
	int consumoMinimoTotal = 0;
	int consumoASerRateadoAgua = 0;
	int consumoASerRateadoEsgoto = 0;

	public int getConsumoASerRateadoEsgoto() {
		return consumoASerRateadoEsgoto;
	}

	public void setConsumoASerRateadoEsgoto(int consumoASerRateadoEsgoto) {
		this.consumoASerRateadoEsgoto = consumoASerRateadoEsgoto;
	}

	public int getConsumoASerRateadoAgua() {
		return consumoASerRateadoAgua;
	}

	public void setConsumoASerRateadoAgua(int consumoASerRateadoAgua) {
		this.consumoASerRateadoAgua = consumoASerRateadoAgua;
	}

	public int getQuantidadeEconomiasAguaMedidas() {
		return quantidadeEconomiasAguaMedidas;
	}

	public int getQuantidadeEconomiasAguaNaoMedidas() {
		return quantidadeEconomiasAguaNaoMedidas;
	}

	public int getConsumoLigacaoAguaTotal() {
		return consumoLigacaoAguaTotal;
	}

	public int getQuantidadeEconomiasEsgotoMedidas() {
		return quantidadeEconomiasEsgotoMedidas;
	}

	public int getQuantidadeEconomiasEsgotoNaoMedidas() {
		return quantidadeEconomiasEsgotoNaoMedidas;
	}

	public int getConsumoLigacaoEsgotoTotal() {
		return consumoLigacaoEsgotoTotal;
	}

	public int getConsumoMinimoTotal() {
		return consumoMinimoTotal;
	}

	public void setQuantidadeEconomiasAguaMedidas(int quantidadeEconomiasAguaMedidas) {
		this.quantidadeEconomiasAguaMedidas = quantidadeEconomiasAguaMedidas;
	}

	public void setQuantidadeEconomiasAguaNaoMedidas(int quantidadeEconomiasAguaNaoMedidas) {
		this.quantidadeEconomiasAguaNaoMedidas = quantidadeEconomiasAguaNaoMedidas;
	}

	public void setConsumoLigacaoAguaTotal(int consumoLigacaoAguaTotal) {
		this.consumoLigacaoAguaTotal = consumoLigacaoAguaTotal;
	}

	public void setQuantidadeEconomiasEsgotoMedidas(int quantidadeEconomiasEsgotoMedidas) {
		this.quantidadeEconomiasEsgotoMedidas = quantidadeEconomiasEsgotoMedidas;
	}

	public void setQuantidadeEconomiasEsgotoNaoMedidas(int quantidadeEconomiasEsgotoNaoMedidas) {
		this.quantidadeEconomiasEsgotoNaoMedidas = quantidadeEconomiasEsgotoNaoMedidas;
	}

	public void setConsumoLigacaoEsgotoTotal(int consumoLigacaoEsgotoTotal) {
		this.consumoLigacaoEsgotoTotal = consumoLigacaoEsgotoTotal;
	}

	public void setConsumoMinimoTotal(int consumoMinimoTotal) {
		this.consumoMinimoTotal = consumoMinimoTotal;
	}

	public int getImovelId() {
		return imovelId;
	}

	public void setImovelId(int imovelId) {
		this.imovelId = imovelId;
	}
}
