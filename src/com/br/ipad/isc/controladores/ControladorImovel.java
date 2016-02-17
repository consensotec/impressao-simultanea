
package com.br.ipad.isc.controladores;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;

import com.br.ipad.isc.background.BackGroundTaskEnviarImovelOnline;
import com.br.ipad.isc.bean.CategoriaSubcategoria;
import com.br.ipad.isc.bean.ConsumoHistorico;
import com.br.ipad.isc.bean.ConsumoTarifaCategoria;
import com.br.ipad.isc.bean.ConsumoTarifaFaixa;
import com.br.ipad.isc.bean.ContaCategoria;
import com.br.ipad.isc.bean.ContaCategoriaConsumoFaixa;
import com.br.ipad.isc.bean.HidrometroInstalado;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.bean.SistemaParametros;
import com.br.ipad.isc.excecoes.ControladorException;
import com.br.ipad.isc.util.ConstantesSistema;
import com.br.ipad.isc.util.Util;

/**
 * A classe ControladorImoveis é responsável por toda a lógica referente aos
 * imóveis, como navegar na lista de imóveis e validar valores digitados pelo
 * leiturista. Dentro do padrão MVC esta classe representa um Controller.
 */
public class ControladorImovel extends ControladorBasico implements
		IControladorImovel {

	/**
	 * Quantidade mínima de imóveis com atualizações enviados ao servidor a cada
	 * tentativa.
	 */
	private static final byte MIN_IMOVEIS_POR_ENVIO = 10;

	/**
	 * Constante que indica a direção PRÓXIMO da navegação no vetor de imóveis.
	 */
	private static final byte NEXT = 0;

	/**
	 * Constante que indica a direção ANTERIOR da navegação no vetor de imóveis.
	 */
	private static final byte PREV = 1;

	private static ImovelConta imovelSelecionado = new ImovelConta();

	private static int qtdImoveis = 0;
	private int qtdRegistros = 0;

	/**
	 * Índice do imóvel selecionado.
	 */
	private int idImovelSelecionado = 1;

	private int indiceImovelCondomio = 1;

	public static ControladorImovel instancia;

	/**
	 * Tipo de Medicao do Imovel selecionado.
	 */
	private int tipoMedicaoSelecionado;

	/**
	 * Contador de imóveis visitados.
	 */
	private int contadorVisitados = 0;

	/**
	 * Contador usado para controlar as tentativas de envio das atualizações
	 * pendentes.
	 */
	private int contadorEnvio;

	/**
	 * Indica se a rota está invertida.
	 */
	private boolean roteiroEstaInvertido;

	protected static Context context;

	public void setContext(Context ctx) {
		context = ctx;
	}

	/**
	 * Cria uma instância do objeto ControladorImoveis.
	 */
	public ControladorImovel() {
		this.roteiroEstaInvertido = false;

		// Colocamos sempre o ultimo imovel que parou
		SistemaParametros sistemaParametros = SistemaParametros.getInstancia();
		if (sistemaParametros.getIdImovelSelecionado() != null
				&& sistemaParametros.getIdImovelSelecionado().intValue() != 0) {
			idImovelSelecionado = sistemaParametros.getIdImovelSelecionado();

			// if (Configuracao.getInstancia().getIndiceImovelCondominio() != 0)
			// {
			// indiceImovelCondomio =
			// Configuracao.getInstancia().getIndiceImovelCondominio();
			// }
		}
	}

	/**
	 * Retorna o imóvel selecionado.
	 * 
	 * @return imóvel selecionado.
	 */
	public ImovelConta getImovelSelecionado() {
		return imovelSelecionado;
	}

	public void setImovelSelecionado(ImovelConta imovel) {
		imovelSelecionado = imovel;
		idImovelSelecionado = imovel.getId();

		if (imovelSelecionado.getIndcCondominio().equals(ConstantesSistema.SIM)) {
			this.indiceImovelCondomio = 1;
		}
		// else if (imovelSelecionado.getIdImovelCondominio() !=
		// ConstantesSistema.NULO_INT) {
		// indiceImovelCondomio = imovelSelecionado.getId() -
		// (imovelSelecionado.getIdImovelCondominio() - 1);
		// }

	}

	public static ControladorImovel getInstancia() {

		if (ControladorImovel.instancia == null) {
			ControladorImovel.instancia = new ControladorImovel();
		}

		return ControladorImovel.instancia;
	}

	/**
	 * Retorna se é o momento para realizar uma tentativa de envio das
	 * atualizações pendentes.
	 * 
	 * @return true se for para enviar, false caso contrário.
	 */
	public boolean deveTentarEnviar() {
		boolean resposta = this.contadorEnvio == MIN_IMOVEIS_POR_ENVIO;
		if (resposta) {
			this.contadorEnvio = 0;
		}

		return resposta;
	}

	/**
	 * Retorna o contador de imóveis visitados.
	 * 
	 * @return Vetor de imóveis.
	 */
	public int getContadorVisitados() {
		return this.contadorVisitados;
	}

	public void setContadorVisitados(int contadorVisitados) {
		this.contadorVisitados = contadorVisitados + 1;

	}

	/**
	 * Inverte o roteiro.
	 */
	public void inverterRoteiro() {
		this.roteiroEstaInvertido = !this.roteiroEstaInvertido;
	}

	/**
	 * Restorna se o roteiro está ou não invertido.
	 * 
	 * @return true se tiver e false caso contrário.
	 */
	public boolean roteiroEstaInvertido() {
		return this.roteiroEstaInvertido;
	}

	/**
	 * Retorna o índice do imóvel selecionado.
	 * 
	 * @return índice do imóvel selecionado.
	 */
	public int getIndiceAtual() {
		return this.idImovelSelecionado;
	}

	/**
	 * Retorna o índice do imóvel condominio.
	 * 
	 * @return índice do imóvel condominio.
	 */
	public int getIndiceAtualImovelCondominio() {
		return this.indiceImovelCondomio;
	}

	/**
	 * Segue para o PRÓXIMO imóvel.
	 * 
	 * @return O identificador do erro de validação.
	 */
	public int proximo() throws ControladorException {

		int resposta = ConstantesSistema.ID_SEM_ERRO;
		if (this.roteiroEstaInvertido) {
			resposta = this.andar(PREV);
		} else {
			resposta = this.andar(NEXT);
		}

		return resposta;
	}

	/**
	 * Volta para o imóvel anterior.
	 * 
	 * @return O identificador do erro de validação.
	 */
	public int anterior() throws ControladorException {

		int resposta = ConstantesSistema.ID_SEM_ERRO;

		if (this.roteiroEstaInvertido) {
			resposta = this.andar(NEXT);
		} else {
			resposta = this.andar(PREV);
		}

		return resposta;
	}

	/**
	 * Segue para o PRÓXIMO imóvel ou volta para o anterior, salvando os valores
	 * alterados caso seja necessário.
	 * 
	 * @param direction
	 *            A direção que se deve mover.
	 * @param leitura
	 *            Leitura digitada pelo usuário.
	 * @param anormalidade
	 *            Anormalidade digitada pelo usuário.
	 * @return O identificador do erro de validação.
	 */
	private int andar(int direction) throws ControladorException {

		ImovelConta imovel = (ImovelConta) ControladorBasico.getInstance()
				.pesquisarPorId(SistemaParametros.getInstancia().getIdImovelSelecionado(), new ImovelConta());

		// Antes de enviarmos, verificamos se o imovel é imovel
		// condomínio, pois o mesmo só deve ser enviado no final
		// if
		// (!ControladorImoveis.getInstancia().getImovelSelecionado().isImovelCondominio())
		// {

		if (SistemaParametros.getInstancia().getIndicadorTransmissaoOffline() == null
				|| SistemaParametros.getInstancia().getIndicadorTransmissaoOffline()
						.equals(ConstantesSistema.NAO)) {

			int quantidadeEnvioImovel = ConstantesSistema.FREQUENCIA_ENVIO_IMOVEIS;

			if (quantidadeEnvioImovel <= 1) {
				BackGroundTaskEnviarImovelOnline bk = new BackGroundTaskEnviarImovelOnline(
						imovel);
				bk.start();
			} 
//			else {
//				EnvioImovel.getInstancia().add(imovel);
//			}
		}
		// }

		// Marcamos esse imovel medido na rota de
		// marcação quando o mesmo calculado. Os não
		// Medidos serão marcador apenas quando forem impressos
		// if (sistemaParametros.getIndcRotaMarcacaoAtivada() ==
		// ConstantesSistema.SIM
		// && (imovel.getIndcImovelCalculado() == ConstantesSistema.SIM &&
		// imovel.getSequencialRotaMarcacao() == ConstantesSistema.NULO_INT)) {
		// imovel.setSequencialRotaMarcacao(sistemaParametros.getSequencialAtualRotaMarcacao());
		//
		// getControladorImovelConta().atualizarImovelConta(imovel);
		// }

		imovel = null;
		Integer qnt = getControladorImovelConta().getQtdImoveis();
		switch (direction) {
		case NEXT:
			// avança para o próximo imóvel
			this.idImovelSelecionado++;

			if (this.idImovelSelecionado > qnt.intValue()) {
				this.idImovelSelecionado = 1;
			}

			break;
		case PREV:
			// retorna para o imóvel anterior
			if (this.idImovelSelecionado == 1) {
				this.idImovelSelecionado = qnt.intValue();
			} else {
				this.idImovelSelecionado--;
			}

			break;
		}

		getControladorSistemaParametros().atualizarIdImovelSelecionadoSistemaParametros(idImovelSelecionado);
		
		imovelSelecionado = (ImovelConta) ControladorBasico.getInstance()
				.pesquisarPorId(SistemaParametros.getInstancia().getIdImovelSelecionado(), new ImovelConta());
				

		// if (imovelSelecionado.getIndcCondominio() == ConstantesSistema.SIM) {
		// this.indiceImovelCondomio = 1;
		// } else if (imovelSelecionado.getIdImovelCondominio() !=
		// ConstantesSistema.NULO_INT) {
		// indiceImovelCondomio = imovelSelecionado.getId() -
		// (imovelSelecionado.getIdImovelCondominio() - 1);
		// }
		//
		// sistemaParametros.setIndiceImovelCondominio(indiceImovelCondomio);


		// Verificamos se estamos trabalhando com imoveis condomínio
		if (imovelSelecionado.getIndcCondominio().equals(ConstantesSistema.SIM)) {
			indiceImovelCondomio = 1;
		}

		return ConstantesSistema.ID_SEM_ERRO;
	}

	public void setIndiceImovelCondomio(int indiceImovelCondomio) {
		this.indiceImovelCondomio = indiceImovelCondomio;
	}

	/**
	 * Tipo de Medicao do Imovel Atual
	 * 
	 * @return Tipo de Medição
	 */
	public int getTipoMedicaoSelecionado() {
		return tipoMedicaoSelecionado;
	}

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
	public int poco(String leitura, int anormalidade, int tipoMedicao) {
		int resposta = ConstantesSistema.ID_SEM_ERRO;
		// resposta = this.andar(POCO, leitura, anormalidade,tipoMedicao);

		return resposta;
	}

	/**
	 * Verifica os dados informados antes de gerar a conta..
	 * 
	 * @param leitura
	 *            Leitura digitada pelo usuário.
	 * @param anormalidade
	 *            Anormalidade digitada pelo usuário.
	 * @return O identificador do erro de validação.
	 */
	public int andarConta(String leitura, int anormalidade, int tipoMedicao) {
		int resposta = ConstantesSistema.ID_SEM_ERRO;
		// resposta = this.andar(CONTA, leitura, anormalidade,tipoMedicao);

		return resposta;
	}

	/**
	 * Calcula o consumo minimo do imovel. Inicialmente tentamos pesquisar por
	 * subcategoria, e caso nao consigamos, pesquisamos por categorias.
	 * 
	 * @param imovel
	 * @return consumo minimo do imovel
	 */
	public int calcularConsumoMinimoImovel(ImovelConta imovel,
			Date dataInicioVigencia) throws ControladorException {

		// Retorno
		int consumoMinimoImovel = 0;

		ArrayList<CategoriaSubcategoria> colecao = getControladorCategoriaSubcategoria()
				.buscarCategoriaSubcategoriaPorImovelId(imovel.getId());
		for (CategoriaSubcategoria categoriaSubcategoria : colecao) {

			int codigoSubcategoria = 0;

			if (categoriaSubcategoria.getCodigoSubcategoria() != null
					&& !categoriaSubcategoria.getCodigoSubcategoria()
							.equals("")) {
				codigoSubcategoria = categoriaSubcategoria
						.getCodigoSubcategoria();
			}

			boolean calculoPorSubcategoria = false;

			// Calculamos por subcategoria
			ArrayList<ConsumoTarifaCategoria> colecaoConsumoTarifa = getControladorConsumoTarifaCategoria()
					.buscarConsumosTarifasCategorias(imovel.getId());

			for (ConsumoTarifaCategoria tarifa : colecaoConsumoTarifa) {

				if (Util.compararData(dataInicioVigencia,
						tarifa.getDataVigencia()) == 0
						&& imovel.getCodigoTarifa().intValue() == tarifa
								.getConsumoTarifa().intValue()
						&& categoriaSubcategoria.getCodigoCategoria()
								.intValue() == tarifa.getIdCategoria()
								.intValue()
						&& codigoSubcategoria == tarifa.getIdSubcategoria()
								.intValue()) {
					consumoMinimoImovel += tarifa
							.getConsumoMinimoSubcategoria()
							* categoriaSubcategoria
									.getQtdEconomiasSubcategoria();
					calculoPorSubcategoria = true;
					break;
				}
			}

			if (!calculoPorSubcategoria) {
				// Calculamos por categoria
				for (ConsumoTarifaCategoria tarifa : colecaoConsumoTarifa) {

					if (Util.compararData(dataInicioVigencia,
							tarifa.getDataVigencia()) == 0
							&& imovel.getCodigoTarifa().intValue() == tarifa
									.getConsumoTarifa().intValue()
							&& categoriaSubcategoria.getCodigoCategoria()
									.intValue() == tarifa.getIdCategoria()
									.intValue()
							&& (tarifa.getIdSubcategoria() == null || tarifa
									.getIdSubcategoria().intValue() == 0)) {
						consumoMinimoImovel += tarifa
								.getConsumoMinimoSubcategoria()
								* categoriaSubcategoria
										.getQtdEconomiasSubcategoria();
						break;
					}
				}
			}
		}

		return consumoMinimoImovel;
	}

	/**
     * Segundo o [UC0743] 2.1. o subfluxo [SB0001 - Cáculo Simples para uma
     * única Tarifa] É aplicado quando todos os dados (Registro do tipo 1) têm a
     * mesma data inicial de vigência.
     * 
     * @return Verdadeiro se for para aplicar o cálculo simples, e falso caso
     *         contrário.
     */
    public Object[] deveAplicarCalculoSimples(ImovelConta imovel) throws ControladorException {

		Object[] retorno = new Object[2];
		Boolean calculoSimples = Boolean.TRUE;
		
		ArrayList<ConsumoTarifaCategoria> colecaoConsumoTarifa = getControladorConsumoTarifaCategoria()
				.buscarConsumosTarifasCategorias(imovel.getId());
		
		int tamanho = colecaoConsumoTarifa.size();
				
		Date dataInicioVigencia = null;
		
		Date dataleituraAnterior = null;
		
		HidrometroInstalado hidrometroAgua = getControladorHidrometroInstalado().
				buscarHidrometroInstaladoPorImovelTipoMedicao(imovel.getId(), ConstantesSistema.LIGACAO_AGUA);
		
		if (hidrometroAgua != null){
			dataleituraAnterior = hidrometroAgua.getDataLeituraAnterior();
		}else{
			HidrometroInstalado hidrometroPoco = getControladorHidrometroInstalado().
					buscarHidrometroInstaladoPorImovelTipoMedicao(imovel.getId(), ConstantesSistema.LIGACAO_POCO);
			
			if (hidrometroPoco != null){
			  dataleituraAnterior = hidrometroPoco.getDataLeituraAnterior();
			}else{
				dataleituraAnterior = imovel.getDataLeituraAnterior();
			}
		}
		
		Date data0 = colecaoConsumoTarifa.get(0).getDataVigencia();
	
		dataInicioVigencia = data0;
	
		Date data1 = null;
		for (int i = 1; i < tamanho; i++) {
		    data1 = colecaoConsumoTarifa.get(i).getDataVigencia();
		    Date dataMaior = null;
		    if(Util.compararData(data0, data1) >= 0){
		    	dataMaior = data0;
		    }else{
		    	dataMaior = data1;
		    }
		    
		    	    
		    //System.out.println(Util.formatarData(dataMaior));
	
		    // Caso a data 0 seja diferente da data 1 
		    if (Util.compararData(data0, data1) != 0){
		    	// e a data maior seja menor que a data atual 
			    // e a data anterior seja menor que a maior data
		    	// o calculo é proporcional
		    	if( Util.compararData(dataMaior, new Date()) < 0 &&
			      Util.compararData(dataleituraAnterior,dataMaior) < 0){
		    		calculoSimples = Boolean.FALSE;	 
			    }else{
			    	// e a data anterior seja maior ou igual que a maior data
			       if (Util.compararData(dataleituraAnterior,dataMaior) >= 0){
			    	   dataInicioVigencia = dataMaior;
			       }
			    	
			    }
		    }
		}
		retorno[0] = calculoSimples;
		retorno[1] = dataInicioVigencia;
		return retorno;
    }
    
	/**
	 * [UC0743] Calcular Valores de Água/Esgoto
	 */
	public void calcularValores(ImovelConta imovel, ConsumoHistorico consumo,
			int tipoMedicao) throws ControladorException {
		Object[] dadosCalculo = this.deveAplicarCalculoSimples(imovel);
		boolean calculoSimples = ((Boolean) dadosCalculo[0]).booleanValue();
		Date dataInicioVigencia = (Date) dadosCalculo[1];
		if (calculoSimples) {
			this.calculoSimples(imovel, consumo, tipoMedicao,
					dataInicioVigencia);
		} else {
			this.calculoProporcionalMaisUmaTarifa(imovel, consumo, tipoMedicao);
		}

		// Aplica o percentual de cobrança de esgoto
		if (tipoMedicao == ConstantesSistema.LIGACAO_POCO) {
			ArrayList<CategoriaSubcategoria> colecao = getControladorCategoriaSubcategoria()
					.buscarCategoriaSubcategoriaPorImovelId(imovel.getId());

			for (CategoriaSubcategoria dadosEconomiasSubcategorias : colecao) {
				// Buscar todas as ContaCategoria para o tipo de ligação POCO
				ContaCategoria faturamentoEsgoto = getControladorContaCategoria()
						.buscarContaCategoriaPorCategoriaSubcategoriaId(
								dadosEconomiasSubcategorias.getId(),
								ConstantesSistema.LIGACAO_POCO);

				if (faturamentoEsgoto == null) {
					faturamentoEsgoto = new ContaCategoria();
				}

				BigDecimal valorFaturado = BigDecimal.ZERO;
				BigDecimal result = imovel.getPercentCobrancaEsgoto().divide(new BigDecimal(
						"100"), 2, BigDecimal.ROUND_HALF_UP);

				if (faturamentoEsgoto.getValorFaturado() != null) {
					// Valor faturado de esgoto
					valorFaturado = faturamentoEsgoto.getValorFaturado().multiply(result);
					valorFaturado = valorFaturado.setScale(2,
							BigDecimal.ROUND_HALF_UP);
				}

				BigDecimal valorTarifaMinima = BigDecimal.ZERO;
				if (faturamentoEsgoto.getValorTarifaMinima() != null) {
					valorTarifaMinima = faturamentoEsgoto.getValorTarifaMinima().multiply(result);
					valorTarifaMinima = valorTarifaMinima.setScale(2,
							BigDecimal.ROUND_HALF_UP);

				}

				faturamentoEsgoto
						.setValorFaturado(valorFaturado);
				faturamentoEsgoto.setValorTarifaMinima(
						valorTarifaMinima);
				faturamentoEsgoto.setTipoLigacao(tipoMedicao);
				faturamentoEsgoto
						.setCategoriaSubcategoria(dadosEconomiasSubcategorias);
				if (faturamentoEsgoto.getId() == null
						|| faturamentoEsgoto.getId().intValue() == 0) {
					ControladorBasico.getInstance().inserir(
							faturamentoEsgoto);
				} else {
					ControladorBasico.getInstance().atualizar(
							faturamentoEsgoto);
				}
			}

		}

	}

	/**
	 * [SB0001] - Cálculo Simples para uma Única Tarifa.
	 */
	private void calculoSimples(ImovelConta imovel, ConsumoHistorico consumo,
			int tipoMedicao, Date dataInicioVigencia)
			throws ControladorException {

		// 1. Verificamos se o tipo de calculo é por categoria ou por
		// subcategoria
		SistemaParametros sistemaParametros = SistemaParametros.getInstancia();
		boolean tipoTarifaPorCategoria = sistemaParametros
				.getIndcTarifaCatgoria().equals(ConstantesSistema.SIM);

		BigDecimal valorFaturadoFaixa = BigDecimal.ZERO;

		int consumoPorEconomia=0;
		
		// 4. Calculamos o consumo por economia
		if (consumo!=null){
			consumoPorEconomia = consumo.getConsumoCobradoMes().intValue()
				/ getControladorCategoriaSubcategoria()
						.obterQuantidadeEconomiasTotal(imovel.getId())
						.intValue();
		} 
		
		int consumoEconomiaCategoriaOuSubcategoria = 0;
		int consumoFaturadoCategoriaOuSubcategoria = 0;

		// int excessoUsado = 0;

		// 5. Para cada registro tipo 2
		ArrayList<CategoriaSubcategoria> colecao = getControladorCategoriaSubcategoria()
				.buscarCategoriaSubcategoriaPorImovelId(imovel.getId());

		// Remove ContasCategoria e ContasCategoriaConsumoFaixa do BD
		getControladorContaCategoria().removerImovelContaCategoria(
				imovel.getId(), tipoMedicao);

		for (CategoriaSubcategoria dadosEconomiasSubcategorias : colecao) {

			// 5.1 Quantidade de economias para cada categoria ou subcategoria
			int quantidadeEconomiasCategoriaSubCategoria;

			if (dadosEconomiasSubcategorias.getFatorEconomiaCategoria() != null
					&& dadosEconomiasSubcategorias.getFatorEconomiaCategoria()
							.intValue() != 0) {
				quantidadeEconomiasCategoriaSubCategoria = dadosEconomiasSubcategorias
						.getFatorEconomiaCategoria();
			} else {
				if (tipoTarifaPorCategoria) {
					quantidadeEconomiasCategoriaSubCategoria = getControladorCategoriaSubcategoria()
							.obterQuantidadeEconomias(
									imovel.getId(),
									dadosEconomiasSubcategorias
											.getCodigoCategoria(),
									dadosEconomiasSubcategorias
											.getCodigoSubcategoria());
				} else {
					quantidadeEconomiasCategoriaSubCategoria = dadosEconomiasSubcategorias
							.getQtdEconomiasSubcategoria();
				}
			}

			// 5.2 Seleciona as tarifas de consumo para cada categoria ou
			// subcategoria do imovel
			ConsumoTarifaCategoria dadosTarifa = null;

			if (tipoTarifaPorCategoria) {
				dadosTarifa = getControladorConsumoTarifaCategoria()
						.pesquisarDadosTarifaImovel(
								tipoTarifaPorCategoria,
								dadosEconomiasSubcategorias
										.getCodigoCategoria() + "",
								dadosEconomiasSubcategorias
										.getCodigoSubcategoria().toString(),
								imovel.getCodigoTarifa(), dataInicioVigencia);
			} else {
				dadosTarifa = getControladorConsumoTarifaCategoria()
						.pesquisarDadosTarifaImovel(
								tipoTarifaPorCategoria,
								dadosEconomiasSubcategorias
										.getCodigoCategoria() + "",
								dadosEconomiasSubcategorias
										.getCodigoSubcategoria().toString(),
								imovel.getCodigoTarifa(), dataInicioVigencia);

				if (dadosTarifa == null) {
					dadosTarifa = getControladorConsumoTarifaCategoria()
							.pesquisarDadosTarifaImovel(
									true,
									dadosEconomiasSubcategorias
											.getCodigoCategoria() + "",
									dadosEconomiasSubcategorias
											.getCodigoSubcategoria().toString(),
									imovel.getCodigoTarifa(),
									dataInicioVigencia);
				}
			}

			// 5.3 Calcula os seguintes valores, da categoria ou subcategoria
			// pesquisada.
			// 5.3.1
			BigDecimal valorTarifaMinima = dadosTarifa
					.getValorTarifaMinimaCategoria().multiply(new BigDecimal(""+quantidadeEconomiasCategoriaSubCategoria));

			// 5.3.2
			int consumoMinimo = dadosTarifa.getConsumoMinimoSubcategoria()
					* quantidadeEconomiasCategoriaSubCategoria;

			// 5.3.3
			BigDecimal valorPorEconomia = dadosTarifa
					.getValorTarifaMinimaCategoria();

			int excessoEconomiaCategoria = consumoPorEconomia
					- dadosTarifa.getConsumoMinimoSubcategoria();

			// 5.3.4
			if (excessoEconomiaCategoria > 0) {
				consumoEconomiaCategoriaOuSubcategoria = dadosTarifa
						.getConsumoMinimoSubcategoria();
			} else {
				if (consumoPorEconomia > dadosTarifa
						.getConsumoMinimoSubcategoria()) {
					consumoEconomiaCategoriaOuSubcategoria = dadosTarifa
							.getConsumoMinimoSubcategoria();
				} else {
					consumoEconomiaCategoriaOuSubcategoria = consumoPorEconomia;
				}
			}

			int consumoExcedente = 0;

			// 5.3.5
			// 5.3.5.1
			if (excessoEconomiaCategoria > 0) {
				// 5.3.5.1.1
				consumoExcedente = excessoEconomiaCategoria;

			} else {
				if (dadosTarifa.getConsumoMinimoSubcategoria() != null) {
					consumoExcedente = consumoPorEconomia
							- dadosTarifa.getConsumoMinimoSubcategoria();
				}
			}

			ArrayList<ContaCategoriaConsumoFaixa> faixasParaInclusao = new ArrayList<ContaCategoriaConsumoFaixa>();

			// 5.4
			if (consumoExcedente > 0) {
				// 5.4.2
				ArrayList<ConsumoTarifaFaixa> faixas = null;

				if (tipoTarifaPorCategoria) {
					faixas = getControladorConsumoTarifaFaixa()
							.selecionarFaixasCalculoValorFaturadoPorCodigo(
									tipoTarifaPorCategoria,
									dadosEconomiasSubcategorias
											.getCodigoCategoria(),
									dadosEconomiasSubcategorias
											.getCodigoSubcategoria(),
									imovel.getCodigoTarifa(),
									dataInicioVigencia);
				} else {
					faixas = getControladorConsumoTarifaFaixa()
							.selecionarFaixasCalculoValorFaturadoPorCodigo(
									tipoTarifaPorCategoria,
									dadosEconomiasSubcategorias
											.getCodigoCategoria(),
									dadosEconomiasSubcategorias
											.getCodigoSubcategoria(),
									imovel.getCodigoTarifa(),
									dataInicioVigencia);

					if (faixas == null || faixas.isEmpty()) {
						faixas = getControladorConsumoTarifaFaixa()
								.selecionarFaixasCalculoValorFaturadoPorCodigo(
										true,
										dadosEconomiasSubcategorias
												.getCodigoCategoria(),
										dadosEconomiasSubcategorias
												.getCodigoSubcategoria(),
										imovel.getCodigoTarifa(),
										dataInicioVigencia);
					}
				}

				int consumoFaturadoFaixa = 0;
				int limiteInicialConsumoFaixa = 0;
				int limiteFinalConsumoFaixa = 0;

				int limiteFaixaFimAnterior = 0;
				if (dadosTarifa.getConsumoMinimoSubcategoria() != null
						&& dadosTarifa.getConsumoMinimoSubcategoria()
								.intValue() != 0) {
					limiteFaixaFimAnterior = dadosTarifa
							.getConsumoMinimoSubcategoria();
				}

				// 5.4.3
				if (imovel.getTipoCalculoTarifa().intValue() == ConstantesSistema.TIPO_CALCULO_TARIFA_2) {
					// 5.4.3.1
					for (ConsumoTarifaFaixa faixa : faixas) {

						if (faixa.getConsumoFaixaFim() != 0
								&& consumoPorEconomia <= faixa
										.getConsumoFaixaFim()) {

							consumoFaturadoFaixa = consumo
									.getConsumoCobradoMes();

							valorFaturadoFaixa = this
									.calcularValorFaturadoFaixa(
											consumoPorEconomia,
											valorTarifaMinima, faixa
													.getValorConsumoTarifa());

							limiteInicialConsumoFaixa = faixa
									.getConsumoFaixaInicio();
							limiteFinalConsumoFaixa = faixa
									.getConsumoFaixaFim();

							ContaCategoriaConsumoFaixa faixaParaInclusao = new ContaCategoriaConsumoFaixa(
									consumoFaturadoFaixa, valorFaturadoFaixa,
									limiteInicialConsumoFaixa,
									limiteFinalConsumoFaixa,
									faixa.getValorConsumoTarifa());

							faixasParaInclusao.add(faixaParaInclusao);
							valorPorEconomia = valorFaturadoFaixa;
							consumoEconomiaCategoriaOuSubcategoria = consumoFaturadoFaixa;

							break;
						}
					}
				} else {
					// 5.4.4
					for (int j = 0; j < faixas.size() && consumoExcedente > 0; j++) {
						ConsumoTarifaFaixa faixa = faixas.get(j);

						// 5.4.4.1.1
						consumoFaturadoFaixa = (faixa.getConsumoFaixaFim() - limiteFaixaFimAnterior);

						// 5.4.4.1.2
						if (consumoExcedente < consumoFaturadoFaixa) {
							consumoFaturadoFaixa = consumoExcedente;
						}

						// 5.4.4.1.3
						valorFaturadoFaixa = faixa.getValorConsumoTarifa().multiply(new BigDecimal(""+consumoFaturadoFaixa));

						// 5.4.4.1.4
						valorPorEconomia = valorPorEconomia.add(valorFaturadoFaixa);

						// 5.4.4.1.5

						consumoEconomiaCategoriaOuSubcategoria = consumoEconomiaCategoriaOuSubcategoria
								+ consumoFaturadoFaixa;

						// 5.4.4.1.6
						limiteInicialConsumoFaixa = faixa
								.getConsumoFaixaInicio();

						// 5.4.4.1.7
						limiteFinalConsumoFaixa = faixa.getConsumoFaixaFim();

						// 5.4.4.1.9
						consumoExcedente -= consumoFaturadoFaixa;

						ContaCategoriaConsumoFaixa faixaParaInclusao = new ContaCategoriaConsumoFaixa(
								consumoFaturadoFaixa, valorFaturadoFaixa,
								limiteInicialConsumoFaixa,
								limiteFinalConsumoFaixa,
								faixa.getValorConsumoTarifa());

						faixasParaInclusao.add(faixaParaInclusao);

						// Recupera a faixa final da faixa anterior
						limiteFaixaFimAnterior = faixa.getConsumoFaixaFim();
					}
				}
			}

			// 5.5
			BigDecimal valorFaturado = BigDecimal.ZERO;

			if (imovel.getTipoCalculoTarifa().intValue() == ConstantesSistema.TIPO_CALCULO_TARIFA_2) {

				if (valorFaturadoFaixa != BigDecimal.ZERO) {
					valorFaturado = valorFaturadoFaixa;
				} else {
					valorFaturado = valorPorEconomia.multiply(new BigDecimal(""+ quantidadeEconomiasCategoriaSubCategoria));
				}
			} else {
				valorFaturado = valorPorEconomia.multiply(new BigDecimal(""+quantidadeEconomiasCategoriaSubCategoria));
			}

			// 5.6
			if (imovel.getTipoCalculoTarifa().intValue() == ConstantesSistema.TIPO_CALCULO_TARIFA_2) {
				consumoFaturadoCategoriaOuSubcategoria = consumoEconomiaCategoriaOuSubcategoria;
			} else {
				consumoFaturadoCategoriaOuSubcategoria = consumoEconomiaCategoriaOuSubcategoria
						* quantidadeEconomiasCategoriaSubCategoria;
			}

			// Grava ContasCategoriaConsumoFaixa no BD
			ContaCategoria contaCategoria = new ContaCategoria();
			contaCategoria
					.setCategoriaSubcategoria(dadosEconomiasSubcategorias);
			contaCategoria.setValorFaturado(valorFaturado);
			contaCategoria
					.setNumConsumo(consumoFaturadoCategoriaOuSubcategoria);
			contaCategoria.setValorTarifaMinima(
					valorTarifaMinima);
			contaCategoria.setNumConsumoMinimo(consumoMinimo);
			contaCategoria.setTipoLigacao(tipoMedicao);
			if (contaCategoria.getId() == null
					|| contaCategoria.getId().intValue() == 0) {
				long id = ControladorBasico.getInstance().inserir(
						contaCategoria);
				String idString = "" + id;
				contaCategoria.setId(Integer.parseInt(idString));
			} else {
				ControladorBasico.getInstance().atualizar(
						contaCategoria);
			}

			for (ContaCategoriaConsumoFaixa faixa : faixasParaInclusao) {
				faixa.setContaCategoria(contaCategoria);
				ControladorBasico.getInstance()
						.inserir(faixa);
			}
		}
	}

	/**
	 * Calculamos o valor a ser faturado na faixa
	 * 
	 * @param consumoFaturado
	 *            consumo que foi faturafo
	 * @param valorTarifaMinimaCategoria
	 *            Tarifa minima da categoria
	 * @param valorTarifaFaixa
	 *            Tarifa na faixa
	 * @return
	 */
	private BigDecimal calcularValorFaturadoFaixa(int consumoFaturado,
			BigDecimal valorTarifaMinimaCategoria, BigDecimal valorTarifaFaixa) {

		// Legenda: x = consumoFaturado; NI = valorTarifaMinima

		BigDecimal retorno = BigDecimal.ZERO;

		int CONSUMO_SUPERIOR = 201;

		// Consumidores Taxados
		if (consumoFaturado < CONSUMO_SUPERIOR) {

			BigDecimal divisor = new BigDecimal("10000");
			int multiplicadorExp = 7;

			// NI
			if (consumoFaturado <= 10) {
				retorno = valorTarifaMinimaCategoria;
			}
			// NI(7x² + valorTarifaFaixa * x) / 10000
			else {

				BigDecimal parcial = (new BigDecimal(""+consumoFaturado)).multiply(new BigDecimal(""+consumoFaturado));
				parcial = parcial.multiply(new BigDecimal(""+multiplicadorExp));

				BigDecimal parcial2 = valorTarifaFaixa.multiply(new BigDecimal(""+consumoFaturado));

				BigDecimal parcialFinal = parcial.add(parcial2);

				parcialFinal = valorTarifaMinimaCategoria.multiply(parcialFinal);

				retorno = parcialFinal.divide(divisor);
			}
		}
		// Consumo Superior = NI(valorTarifaFaixa * x - 11,2)
		else {

			BigDecimal valor1 = new BigDecimal("11.2");

			BigDecimal parcial = valorTarifaFaixa.multiply(new BigDecimal(""+consumoFaturado));

			parcial = parcial.subtract(valor1);

			retorno = valorTarifaMinimaCategoria.multiply(parcial);
		}

		return retorno;
	}

	public void carregarImovelSelecionado() throws ControladorException {
		// Verificamos qual o ultimo imovel selecionado
		SistemaParametros sistemaParametros = SistemaParametros.getInstancia();
		ImovelConta imovel = (ImovelConta) ControladorBasico.getInstance()
				.pesquisarPorId(sistemaParametros.getIdImovelSelecionado(), new ImovelConta());

		this.setImovelSelecionado(imovel);

		idImovelSelecionado = imovel.getId();
	}

	public int getQuantidadeImoveis() {
		return ControladorImovel.qtdImoveis;
	}

	public int getQtdRegistros() {
		return qtdRegistros;
	}

	public void setQtdRegistros(int qtdRegistros) {
		this.qtdRegistros = qtdRegistros;
	}

	public void proximoNaoLido() throws ControladorException {

		ArrayList<Integer> naoLidos = getControladorImovelConta()
				.getIdsNaoLidos();

		if (naoLidos != null && naoLidos.size() > 0) {
			ImovelConta imovel = (ImovelConta) ControladorBasico.getInstance().
					pesquisarPorId(naoLidos.get(0), new ImovelConta());
			if (imovel != null) {
				this.setImovelSelecionado(imovel);
			}
		}
	}

	// public void proximoARevisitar() {
	//
	// Configuracao conf = Configuracao.getInstancia();
	//
	// if (conf.getMatriculasRevisitar() != null &&
	// !conf.getMatriculasRevisitar().isEmpty()) {
	// Vector resultados =
	// Repositorio.consultarImoveis(FiltroImovel.PESQUISAR_POR_MATRICULA,
	// conf.getMatriculasRevisitar().elementAt(0).toString());
	//
	// ControladorImoveis.getInstancia().setImovelSelecionado((ImovelConta)
	// resultados.elementAt(0));
	//
	// Abas.getInstancia().criarAbas();
	// }
	// else {
	// Util.mostrarErro("Todos os imóveis foram revisitados...");
	// }
	// }

	/**
	 * [UC0743] Calcular Valores de Água/Esgoto no Dispositivo Móvel 
	 * [SB0002 – Cálculo Proporcional Para Mais de Uma Tarifa]
	 * 
	 * @author Bruno Barros, Amelia Pessoa
	 * @date 16/10/2009, 03/09/2012
	 */
	public void calculoProporcionalMaisUmaTarifa(ImovelConta imovel,
			ConsumoHistorico consumo, int tipoMedicao)
			throws ControladorException {

		// 1.Selecionamos as tarifas
		ArrayList<ConsumoTarifaCategoria> tarifas = getControladorConsumoTarifaCategoria()
				.buscarConsumosTarifasCategorias(imovel.getId());

		// Selecionamos a data de leitura anterior,
		// dando prioridade a ligação da agua
		HidrometroInstalado hidrometroAgua = getControladorHidrometroInstalado()
				.buscarHidrometroInstaladoPorImovelTipoMedicao(imovel.getId(),
						ConstantesSistema.LIGACAO_AGUA);
		HidrometroInstalado hidrometroPoco = getControladorHidrometroInstalado()
				.buscarHidrometroInstaladoPorImovelTipoMedicao(imovel.getId(),
						ConstantesSistema.LIGACAO_POCO);

		// Verificamos se de onde podemos pegar a data de leitura anterior
		Date dataLeituraAnterior = null;

		if (hidrometroAgua != null) {
			// Pegamos de agua
			dataLeituraAnterior = hidrometroAgua.getDataLeituraAnterior();
		} else if (hidrometroPoco != null) {
			// Pegamos de Poco
			dataLeituraAnterior = hidrometroPoco.getDataLeituraAnterior();
		} else {
			// Caso seja não medido,
			// pegamos a data que foi passada no imóvel
			dataLeituraAnterior = imovel.getDataLeituraAnterior();
		}

		// 2.Calculamos a quantidade de dias entre as leituras
		// sendo essa quantidade = data atual - leitura anterior
		long quantidadeDiasDesdeUltimaLeitura = Util.obterDiferencasDatasDias(
				new Date(), dataLeituraAnterior);

		// 3.Data da vigência inicial = data da leitura anterior
		Date dataInicioVigencia = dataLeituraAnterior;

		// Para cada tarifa vigente,
		// ordenando por data de início da vigência,
		// realizamos o calculo simples normalmente
		ArrayList<CategoriaSubcategoria> categoriasOuSubcategorias = getControladorCategoriaSubcategoria()
				.buscarCategoriaSubcategoriaPorImovelId(imovel.getId());
		ArrayList<ContaCategoria> listaContaCategoria = new ArrayList<ContaCategoria>();
		ContaCategoria contaCategoriaAux = null;
		
		//Variaveis auxiliares usadas no calculo das faixas
		
		for (int i = 0; i <tarifas.size(); i++) {
			// Calculo simples
			ConsumoTarifaCategoria tarifa = tarifas.get(i);

			this.calculoSimples(imovel, consumo, tipoMedicao,
					tarifa.getDataVigencia());

			// Caso exista um proxima tarifa, então o fim da data de vigencia
			// da tarifa atual é igual a data de inicio da proxima tarifa, menos 1
			// dia. Caso não haja, o fim da vigencia é a data de hoje
			Date dataFimVigencia = null;

			if (i != (tarifas.size() - 1)) {
				ConsumoTarifaCategoria proxTarifa = tarifas.get(i + 1);
				dataFimVigencia = Util.adicionarNumeroDiasDeUmaData(
						proxTarifa.getDataVigencia(), -1);
			} else {
				dataFimVigencia = new Date();
			}
			
			// Calculamos agora a quantidade de dias
			// que essa tarifa ficou vigente.
			long qtdDiasVigenciaTarifaDentroPeriodoLeitura;

			// Caso seja a primeira tarifa,
			// calculamos a diferença de dias entre
			// o inicio da tarifa atual e o da proxima,
			// achando assim a quantidade de dias vigente.
			if (i == 0) {
				qtdDiasVigenciaTarifaDentroPeriodoLeitura = Util
						.obterDiferencasDatasDias(dataFimVigencia,
								dataInicioVigencia);
				// Caso nao seja a primeira vigencia,
				// realizamos o mesmo calculo, apenas adicionando 1 a diferenca
				// para que a proxima vigencia leve em consideração o dia de
				// inicio
			} else {
				qtdDiasVigenciaTarifaDentroPeriodoLeitura = Util
						.obterDiferencasDatasDias(dataFimVigencia,
								dataInicioVigencia);

				qtdDiasVigenciaTarifaDentroPeriodoLeitura++;
			}

			// Calculamos agora o fator que deve ser aplicado
			// para a atual tarifa
			double fatorVigenciaTarifa = Util.arredondar(
					(double) qtdDiasVigenciaTarifaDentroPeriodoLeitura
							/ (double) quantidadeDiasDesdeUltimaLeitura, 4);

			// Selecionamos agora, categoria por categoria,
			// para iniciarmos os calculos
			// Vector categorias = imovel.getCategorias();
			double valorFaturadoCalculoProporcional = 0d;
			double valorTarifaMinimaCalculoProporcional = 0d;

			
			for (int j = 0; j < categoriasOuSubcategorias.size(); j++) {
				CategoriaSubcategoria categoria = categoriasOuSubcategorias
						.get(j);

				// Aplicamos, para cada categoria
				// o fator de vigencia para os seguintes
				// valores: Valor faturado; Valor da tarifa mínima;

				// Buscar a ContaCategoria correspondente
				ContaCategoria contaCategoria = getControladorContaCategoria()
						.buscarContaCategoriaPorCategoriaSubcategoriaId(
								categoria.getId(), tipoMedicao);
				if (contaCategoria == null) {
					contaCategoria = new ContaCategoria();
				}

				if (listaContaCategoria.size()>j){
					contaCategoriaAux = listaContaCategoria.get(j);
				} else {
					contaCategoriaAux = null;
				}
				
				if(contaCategoriaAux==null){
					contaCategoriaAux = new ContaCategoria(); 
					contaCategoriaAux.setId(contaCategoria.getId());
					contaCategoriaAux.setCategoriaSubcategoria(contaCategoria.getCategoriaSubcategoria());
					contaCategoriaAux.setNumConsumo(contaCategoria.getNumConsumo());
					contaCategoriaAux.setNumConsumoMinimo(contaCategoria.getNumConsumoMinimo());
					contaCategoriaAux.setTipoLigacao(contaCategoria.getTipoLigacao());
					
					contaCategoriaAux.setValorFaturado(new BigDecimal("0"));
					contaCategoriaAux.setValorTarifaMinima(new BigDecimal("0"));
					contaCategoriaAux.setUltimaAlteracao(new Date());
					
					listaContaCategoria.add(j, contaCategoriaAux);
				} else {
					contaCategoriaAux.setId(contaCategoria.getId());
					contaCategoriaAux.setCategoriaSubcategoria(contaCategoria.getCategoriaSubcategoria());	
				}
				
				// Calculamos arredondando para duas casas
				valorFaturadoCalculoProporcional = Util.arredondar(
						contaCategoria.getValorFaturado().doubleValue()
								* fatorVigenciaTarifa, 2);

				// Calculamos arredondando para duas casas
				valorTarifaMinimaCalculoProporcional = Util.arredondar(
						contaCategoria.getValorTarifaMinima().doubleValue()
								* fatorVigenciaTarifa, 2);

				// Acumulamos
				contaCategoriaAux.setValorFaturado(new BigDecimal(
						contaCategoriaAux.getValorFaturado().doubleValue() +
						valorFaturadoCalculoProporcional));

				contaCategoriaAux.setValorTarifaMinima(new BigDecimal(
						contaCategoriaAux.getValorTarifaMinima().doubleValue() +
								valorTarifaMinimaCalculoProporcional));

				// Armazenamos a ContaCategoria
				if (contaCategoriaAux.getId() == null
						|| contaCategoriaAux.getId().intValue() == 0) {
					long id = ControladorBasico.getInstance()
							.inserir(contaCategoriaAux);
					String idString = "" + id;
					contaCategoriaAux.setId(Integer.parseInt(idString));
				} else {
					ControladorBasico.getInstance().atualizar(
							contaCategoriaAux);
				}

				// Realizamos agora, o mesmo procedimento para
				// cada faixa da tarifa de consumo
				ArrayList<ContaCategoriaConsumoFaixa> faixas = getControladorContaCategoriaConsumoFaixa()
						.buscarContasCategoriasConsumosFaixasPorPorContaCategoriaId(
								contaCategoria.getId());

				if (faixas!=null) {
					for (int k = 0; k <= faixas.size() - 1; k++) {
						ContaCategoriaConsumoFaixa faixa = faixas.get(k);
	
						double valorFaturadoPorFatorNaFaixa = Util.arredondar(faixa.getValorFaturado()
										.doubleValue() * fatorVigenciaTarifa, 2);
	
						double valorTarifaPorFatorNaFaixa = Util.arredondar(faixa.getValorTarifa()
										.doubleValue() * fatorVigenciaTarifa, 2);	
						
						double[] valorFaturadoAnFaixa = contaCategoriaAux.getVlFaturadoAntFaixa();
						double[] valorTarifaAntFaixa = contaCategoriaAux.getVlTarifaAntFaixa();
						
						
						// seta os dados do faturamento proporcional no dado de
						// faturamento
						if (i==0){
							faixa.setValorFaturado(new BigDecimal(Util.arredondar(valorFaturadoPorFatorNaFaixa, 2)));
							faixa.setValorTarifa(new BigDecimal(Util.arredondar(valorTarifaPorFatorNaFaixa, 2)));
							//Guarda os valores da primeira faixa
							valorFaturadoAnFaixa[k] = Util.arredondar(valorFaturadoPorFatorNaFaixa, 2);
							valorTarifaAntFaixa[k] = Util.arredondar(valorTarifaPorFatorNaFaixa, 2);
							
						} else {
							faixa.setValorFaturado(new BigDecimal(Util.arredondar(valorFaturadoAnFaixa[k]+valorFaturadoPorFatorNaFaixa, 2)));
							faixa.setValorTarifa(new BigDecimal(Util.arredondar(valorTarifaAntFaixa[k] +valorTarifaPorFatorNaFaixa,2)));
							
						}
						
						
						//Atualizamos no BD
						ControladorBasico.getInstance().atualizar(faixa);
					}
				}
				
				// Substituimos o os dados de faturamento pelo acumulador
				// proporcionalmente
//				if (tipoMedicao == ConstantesSistema.LIGACAO_AGUA) {
//					// Ajuste nos valores das faixas com o valor total da conta
//					DadosFaturamento dadosFaturamentoProporcionalAlterado = categoria
//							.getFaturamentoAguaProporcional();
//					if (dadosFaturamentoProporcionalAlterado != null
//							&& !dadosFaturamentoProporcionalAlterado.equals("")) {
//						Vector faixas = dadosFaturamentoProporcionalAlterado
//								.getFaixas();
//
//						double valorFaturado = dadosFaturamentoProporcionalAlterado
//								.getValorTarifaMinima();
//						for (int k = 0; k <= faixas.size() - 1; k++) {
//							DadosFaturamentoFaixa faixa = (DadosFaturamentoFaixa) faixas
//									.elementAt(k);
//							valorFaturado = valorFaturado
//									+ Util.arredondar(
//											faixa.getValorFaturado()
//													* categoria
//															.getQtdEconomiasSubcategoria(),
//											2);
//						}
//						dadosFaturamentoProporcionalAlterado
//								.setValorFaturado(valorFaturado);
//					}
//					categoria.setFaturamentoAgua(dadosFaturamentoProporcionalAlterado);
//					categoria.setFaturamentoAguaProporcional(null);
//				}
//
//				if (tipoMedicao == ConstantesSistema.LIGACAO_POCO) {
//					// Ajuste nos valores das faixas com o valor total da conta
//					categoria.setFaturamentoEsgoto(categoria
//							.getFaturamentoEsgotoProporcional());
//					categoria.setFaturamentoEsgotoProporcional(null);
//				}

			// Informamos que a proxima vigencia
			// inicia no final da anteiror mais um dia
			dataInicioVigencia = Util.adicionarNumeroDiasDeUmaData(
					dataFimVigencia, +1);
			}
		}
	}

}