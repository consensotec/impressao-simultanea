package com.br.ipad.isc.impressao;

import com.br.ipad.isc.bean.SistemaParametros;
import com.br.ipad.isc.util.ConstantesSistema;
import com.br.ipad.isc.util.Util;

public class CodigoDeBarras {
	
	//Singleton
	private static CodigoDeBarras instancia;
	
	private CodigoDeBarras() {
		super();
	}

	public static CodigoDeBarras getInstancia() {
		if (instancia == null) {
			instancia = new CodigoDeBarras();
		}		
		return instancia;
	}
	
	/**
	 * Obtém a representação númerica do código de barras de um pagamento de
	 * acordo com os parâmetros informados [UC0229] Obter Representação Numérica
	 * do Código de Barras
	 * 
	 * @author Pedro Alexandre
	 * @date 20/04/2006
	 * @param tipoPagamento
	 * @param valorCodigoBarra
	 * @param idLocalidade
	 * @param matriculaImovel
	 * @param anoMesReferenciaConta
	 * @param digitoVerificadorRefContaModulo10
	 * @param idTipoDebito
	 * @param anoEmissaoGuiaPagamento
	 * @param sequencialDocumentoCobranca
	 * @param idTipoDocumento
	 * @param idCliente
	 * @param seqFaturaClienteResponsavel
	 * @return
	 * @throws ParametroNaoInformadoException
	 */
	public StringBuilder obterRepresentacaoNumericaCodigoBarra(Integer tipoPagamento, double valorCodigoBarra,
			Integer idLocalidade, Integer matriculaImovel, String mesAnoReferenciaConta,
			Integer digitoVerificadorRefContaModulo10, Integer idTipoDebito, String anoEmissaoGuiaPagamento,
			String sequencialDocumentoCobranca, Integer idTipoDocumento, Integer idCliente,
			Integer seqFaturaClienteResponsavel) {
			
				
		SistemaParametros sistemaParametro =  SistemaParametros.getInstancia();
		
		// [FS0001] Verificar compatibilidade dos campos informados com o tipo
		// de pagamento
		if (tipoPagamento == null) {
			return new StringBuilder("atencao.parametros.incompletos.codigobarra");
		} else {
			// Caso o tipo de pagamento seja referente a conta
			if (tipoPagamento.intValue() == 3) {

				// Caso o código da localidade ou a matrícula do imóvel ou o
				// mês/ano da referência da conta ou o dígito verificador da
				// referência da conta no módulo 10
				// não forem informados levanta uma exceção para o usuário
				// indicando que os parâmetros para geração do código de barras
				// está incompleto.
				if (idLocalidade == null || matriculaImovel == null || mesAnoReferenciaConta == null
						|| digitoVerificadorRefContaModulo10 == null) {
					return new StringBuilder("atencao.parametros.incompletos.codigobarra");
				}

				// Caso o tipo de pagamento seja referente a guia de pagamento
			} else if (tipoPagamento.intValue() == 4) {

				// Caso o código da localidade ou a matrícula do imóvel ou o
				// tipo de débito ou o ano da emissão da guia de pagamento
				// não forem informados levanta uma exceção para o usuário
				// indicando que os parâmetros para geração do código de barras
				// está incompleto.
				if (idLocalidade == null || matriculaImovel == null || idTipoDebito == null
						|| anoEmissaoGuiaPagamento == null) {
					return new StringBuilder("atencao.parametros.incompletos.codigobarra");
				}

				// Caso a tipo de pagamento seja referente a documento de
				// cobrança
			} else if (tipoPagamento.intValue() == 5) {

				// Caso o código da localidade ou a matrícula do imóvel ou o
				// sequencial do documento de cobrança ou o tipo de documento
				// não forem informados levanta uma exceção para o usuário
				// indicando que os parâmetros para geração do código de barras
				// está incompleto.
				if (idLocalidade == null || matriculaImovel == null || sequencialDocumentoCobranca == null
						|| idTipoDocumento == null) {
					return new StringBuilder("atencao.parametros.incompletos.codigobarra");
				}

				// Caso o tipo de pagamento seja referente a fatura do cliente
				// responsável
			} else if (tipoPagamento.intValue() == 7) {
				// Caso o código do cliente ou o mês/ano da referência da conta
				// ou o sequencial da fatura do cliente responsável
				// não forem informados levanta uma exceção para o usuário
				// indicando que os parâmetros para geração do código de barras
				// está incompleto.
				if (idCliente == null || mesAnoReferenciaConta == null || seqFaturaClienteResponsavel == null) {
					return new StringBuilder("atencao.parametros.incompletos.codigobarra");
				}

				// Caso a tipo de pagamento seja referente a guia de pagamento
			} else if (tipoPagamento.intValue() == 6) {
				// Caso o código da localidade ou id do cliente ou o
				// tipo de débito ou o ano da emissão da guia de pagamento
				// não forem informados levanta uma exceção para o usuário
				// indicando que os parâmetros para geração do código de barras
				// está incompleto.
				if (idLocalidade == null || idCliente == null || idTipoDebito == null || anoEmissaoGuiaPagamento == null) {
					return new StringBuilder("atencao.parametros.incompletos.codigobarra");
				}
			} else if (tipoPagamento.intValue() == 8) {

				// Caso o código do cliente ou o
				// sequencial do documento de cobrança ou o tipo de documento
				// não forem informados levanta uma exceção para o usuário
				// indicando que os parâmetros para geração do código de barras
				// está incompleto.
				if (idCliente == null || sequencialDocumentoCobranca == null || idTipoDocumento == null) {
					return new StringBuilder("atencao.parametros.incompletos.codigobarra");
				}
			}
		}

		// Cria a variável que vai armazenar a representação númerica do código
		// de barras
		StringBuilder representacaoNumericaCodigoBarra = new StringBuilder();

		// G.05.1 - Identificação do produto
		StringBuilder identificacaoProduto = new StringBuilder("8");
		representacaoNumericaCodigoBarra.append(identificacaoProduto);

		// G.05.2 - Identificação do segmento
		StringBuilder identificacaoSegmento = new StringBuilder("2");
		representacaoNumericaCodigoBarra.append(identificacaoSegmento);
		
		// G.05.3 - Identificação dovalor real ou referência

		// MODULO 10
		int moduloVerificador = ConstantesSistema.MODULO_VERIFICADOR_10;
		StringBuilder identificacaoValorRealOuReferencia = new StringBuilder("6");	
		
		// MODULO 11
		if (sistemaParametro.getModuloVerificadorCodigoBarras() != null
				&& sistemaParametro.getModuloVerificadorCodigoBarras().equals(11)) {

			// MODULO 11
			moduloVerificador = ConstantesSistema.MODULO_VERIFICADOR_11;
			identificacaoValorRealOuReferencia = new StringBuilder("8");

		}

		representacaoNumericaCodigoBarra.append(identificacaoValorRealOuReferencia);

		String valorContaString = Util.formatarDoubleParaMoedaReal(valorCodigoBarra);

		valorContaString = Util.replaceAll(valorContaString, ".", "");
		valorContaString = Util.replaceAll(valorContaString, ",", "");

		// G.05.5 - Valor do código de barras
		String valorCodigoBarraFormatado = Util.adicionarZerosEsquerdaNumero(11, valorContaString);
		representacaoNumericaCodigoBarra.append(valorCodigoBarraFormatado);

		// G.05.6 - Identificação da empresa
		// Fixo por enquanto
		String identificacaoEmpresa = sistemaParametro.getCodigoEmpresaFebraban();
		identificacaoEmpresa = Util.adicionarZerosEsquerdaNumero(4, identificacaoEmpresa);
		representacaoNumericaCodigoBarra.append(identificacaoEmpresa);

		// G.05.7 Identificação do pagamento
		// [SB0001] Obter Identificação do Pagamento
		String identificacaoPagamento = null;

		if (sistemaParametro.getCodigoEmpresaFebraban().equalsIgnoreCase(ConstantesSistema.CODIGO_FEBRABAN_CAER)) {
			identificacaoPagamento = Util.obterIdentificacaoPagamentoCAER(tipoPagamento, idLocalidade, matriculaImovel, mesAnoReferenciaConta, digitoVerificadorRefContaModulo10, idTipoDebito, anoEmissaoGuiaPagamento, sequencialDocumentoCobranca, idTipoDocumento, idCliente, seqFaturaClienteResponsavel);
		} else {
			identificacaoPagamento = Util.obterIdentificacaoPagamento(tipoPagamento, idLocalidade, matriculaImovel, mesAnoReferenciaConta, digitoVerificadorRefContaModulo10, idTipoDebito, anoEmissaoGuiaPagamento, sequencialDocumentoCobranca, idTipoDocumento, idCliente, seqFaturaClienteResponsavel);
		}
		representacaoNumericaCodigoBarra.append(identificacaoPagamento
				+ tipoPagamento.toString());

		// G.05.4 - Dígito verificador geral
		// [SB0002] Obter Dígito verificador geral
		String digitoVerificadorGeral = (Util.obterDigitoVerificadorGeral(representacaoNumericaCodigoBarra.toString(),
					moduloVerificador)).toString();

		// Monta a representaçaõ númerica com todos os campos informados
		representacaoNumericaCodigoBarra = new StringBuilder(
				identificacaoProduto.append(identificacaoSegmento)
				.append(identificacaoValorRealOuReferencia)
				+ digitoVerificadorGeral + valorCodigoBarraFormatado 
				+ identificacaoEmpresa + identificacaoPagamento
				+ tipoPagamento.toString());

		// Cria as variáveis que vão armazenar o código de barra separado por
		// campos
		// e seus respectivos dígitos verificadores se existirem
		String codigoBarraCampo1 = null;
		String codigoBarraDigitoVerificadorCampo1 = null;
		String codigoBarraCampo2 = null;
		String codigoBarraDigitoVerificadorCampo2 = null;
		String codigoBarraCampo3 = null;
		String codigoBarraDigitoVerificadorCampo3 = null;
		String codigoBarraCampo4 = null;
		String codigoBarraDigitoVerificadorCampo4 = null;

		// Separa as 44 posições do código de barras em 4 grupos de onze
		// posições
		// e para cada um dos grupos calcula o dígito verificador do módulo 11
		codigoBarraCampo1 = representacaoNumericaCodigoBarra.substring(0, 11);
		// codigoBarraDigitoVerificadorCampo1 =
		// (obterDigitoVerificadorModulo11(new Long(
		// codigoBarraCampo1))).toString();
		codigoBarraDigitoVerificadorCampo1 = (Util.obterDigitoVerificador(Long.parseLong(codigoBarraCampo1), 
				moduloVerificador)).toString();

		codigoBarraCampo2 = representacaoNumericaCodigoBarra.substring(11, 22);
		/*
		 * codigoBarraDigitoVerificadorCampo2 =
		 * (obterDigitoVerificadorModulo11(new Long(
		 * codigoBarraCampo2))).toString();
		 */
		codigoBarraDigitoVerificadorCampo2 = (Util.obterDigitoVerificador(Long.parseLong(codigoBarraCampo2), 
				moduloVerificador)).toString();

		codigoBarraCampo3 = representacaoNumericaCodigoBarra.substring(22, 33);
		/*
		 * codigoBarraDigitoVerificadorCampo3 =
		 * (obterDigitoVerificadorModulo11(new Long(
		 * codigoBarraCampo3))).toString();
		 */
		codigoBarraDigitoVerificadorCampo3 = (Util.obterDigitoVerificador(Long.parseLong(codigoBarraCampo3),
				moduloVerificador)).toString();

		codigoBarraCampo4 = representacaoNumericaCodigoBarra.substring(33, 44);
		/*
		 * codigoBarraDigitoVerificadorCampo4 =
		 * (obterDigitoVerificadorModulo11(new Long(
		 * codigoBarraCampo4))).toString();
		 */
		codigoBarraDigitoVerificadorCampo4 = (Util.obterDigitoVerificador(Long.parseLong(codigoBarraCampo4), 
				moduloVerificador)).toString();

		// Monta a representação númerica do código de barras com os dígitos
		// verificadores
		representacaoNumericaCodigoBarra = new StringBuilder(				
				codigoBarraCampo1 + codigoBarraDigitoVerificadorCampo1 + codigoBarraCampo2
				+ codigoBarraDigitoVerificadorCampo2 + codigoBarraCampo3 + codigoBarraDigitoVerificadorCampo3
				+ codigoBarraCampo4 + codigoBarraDigitoVerificadorCampo4);

		// Retorna a representação númerica do código de barras
		return  representacaoNumericaCodigoBarra;
	}
	
}
