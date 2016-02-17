
package com.br.ipad.isc.controladores;

import java.util.ArrayList;

import com.br.ipad.isc.bean.ConsumoHistorico;
import com.br.ipad.isc.bean.HidrometroInstalado;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.bean.LeituraAnormalidade;
import com.br.ipad.isc.bean.SistemaParametros;
import com.br.ipad.isc.bean.helpers.RateioConsumoHelper;
import com.br.ipad.isc.conexao.ComunicacaoWebServer;
import com.br.ipad.isc.excecoes.ConexaoImpressoraException;
import com.br.ipad.isc.excecoes.ControladorException;
import com.br.ipad.isc.excecoes.ZebraException;
import com.br.ipad.isc.util.ConstantesSistema;
import com.br.ipad.isc.util.Util;

public class ControladorRateioImovelCondominio extends ControladorBasico {

	private ImovelConta imovelMacro;
	private boolean existeImovelVinculadoRateioAreaComumAgua = false;
	private boolean existeImovelVinculadoRateioAreaComumEsgoto = false;
	private int consumoAguaASerRateadoPorEconomiaMedido;
	private int consumoAguaASerRateadoPorEconomiaNaoMedido;
	private int consumoEsgotoASerRateadoPorEconomiaMedido;
	private int consumoEsgotoASerRateadoPorEconomiaNaoMedido;
	private RateioConsumoHelper helper;
	private boolean imprimirEnviarValorContaMenorMinimo = true;
	//private boolean imprimirEnviarValorContaMaiorMaximo = true;	
	private SistemaParametros sistemaParametros = SistemaParametros.getInstancia();
	private ArrayList<ImovelConta> imoveisParaEnvio = new ArrayList<ImovelConta>();	
	private ArrayList<Integer> colecaoMicros = new ArrayList<Integer>();
	private boolean enviarImovelCondominio = true;
	
	public ControladorRateioImovelCondominio(ImovelConta imovelMacro,
			boolean existeImovelVinculadoRateioAreaComumAgua,
			boolean existeImovelVinculadoRateioAreaComumEsgoto,
			int consumoAguaASerRateadoPorEconomiaMedido,
			int consumoAguaASerRateadoPorEconomiaNaoMedido,
			int consumoEsgotoASerRateadoPorEconomiaMedido,
			int consumoEsgotoASerRateadoPorEconomiaNaoMedido,
			RateioConsumoHelper helper, boolean completo) {
		
		super();
		this.imovelMacro=imovelMacro;
		this.existeImovelVinculadoRateioAreaComumAgua = existeImovelVinculadoRateioAreaComumAgua;
		this.existeImovelVinculadoRateioAreaComumEsgoto = existeImovelVinculadoRateioAreaComumEsgoto;
		this.consumoAguaASerRateadoPorEconomiaMedido = consumoAguaASerRateadoPorEconomiaMedido;
		this.consumoAguaASerRateadoPorEconomiaNaoMedido = consumoAguaASerRateadoPorEconomiaNaoMedido;
		this.consumoEsgotoASerRateadoPorEconomiaMedido=consumoEsgotoASerRateadoPorEconomiaMedido;
		this.consumoEsgotoASerRateadoPorEconomiaNaoMedido=consumoEsgotoASerRateadoPorEconomiaNaoMedido;
		this.helper = helper;
		
		buscarImoveis(completo);
		
	}

	private void buscarImoveis(boolean completo){
		try {
			if (completo){ //Busca imoveis micro vinculados ao imovel macro
				colecaoMicros = getControladorImovelConta().buscarIdsImoveisMicro(imovelMacro.getId());
			} else { //Busca apenas imoveis micro ainda não impressos (+ anterior) 
				colecaoMicros = getControladorImovelConta().buscarImovelCondominiosNaoImpressos(imovelMacro.getId());			
			}
		} catch (ControladorException e) {
			e.printStackTrace();
		}
	}
	
	//Métodos auxiliares para barra de progresso
	public int obterTotal(){
		return colecaoMicros.size();
	}
	
	public void proximoRateio() throws ControladorException, ZebraException {
		
		Integer indice = colecaoMicros.get(0);
		ImovelConta imovelMicro = (ImovelConta) ControladorBasico.getInstance().
				pesquisarPorId(indice, new ImovelConta()); 
				
		boolean imprimir = false;
		
		//Verifica se o imovel condominio jah foi rateado, se não manda ratear
//		if (!getControladorImovelConta().verificarRateioCondominio(imovelMacro.getId())){
			imprimir = ratear(imovelMicro);
//		} else {
//			imprimir = !imovelMicro.getIndcNaoPermiteImpressao().equals(ConstantesSistema.SIM);
//		}
		
		//Verifica se pode e manda imprimir
		if (imprimir){			
//			imovelMicro.getIndcNaoPermiteImpressao().equals(ConstantesSistema.NAO);
			
			// Imprimir caso não seja caern e leitra = 1
			if(	sistemaParametros.getIndicadorSistemaLeitura().equals(ConstantesSistema.NAO ) ) {
				
				imprimir(imovelMicro);
			}
/*		} else {
			imovelMicro.setIndcNaoPermiteImpressao(ConstantesSistema.SIM);*/
		}
		
		//Atualiza o imóvel para não enviado
		imovelMicro.setIndcImovelEnviado(ConstantesSistema.NAO);
		
		//Atualiza o indicador rateioRealizado do imovel
		if (imovelMicro.getIndcRateioRealizado().equals(ConstantesSistema.NAO)){
			imovelMicro.setIndcRateioRealizado(ConstantesSistema.SIM);			
		}		
		ControladorBasico.getInstance().atualizar(imovelMicro);
		
		//Remove o imovel da lista
		colecaoMicros.remove(imovelMicro.getId());		
		
		boolean permiteEnviarImovel = getControladorImovelConta().enviarAoCalcular(imovelMicro);
	
		if (permiteEnviarImovel && enviarImovelCondominio) {
			enviarImovelCondominio = true;
		}else{
			enviarImovelCondominio = false;
		}
	}
	
	/**
	 * UC0970 Efetuar Rateio de Consumo no Dispositivo Movel
	 * [SB0009][SB0010] Ajustar Leitura Macromedido Agua/Poco
	 * Metodo responsavel por efetuar o ajuste na leitura do macro medido
	 * @author Carlos Chaves
	 * @param consumoHistorico
	 * @param tipoLigacao
	 * @throws ControladorException
	 */
	private void ajustarLeituraMacro(ConsumoHistorico consumoHistorico) throws ControladorException{
		if(consumoHistorico!=null){
			
			Integer idMacro = consumoHistorico.getMatricula().getId();
			
			HidrometroInstalado hidrometro = getControladorHidrometroInstalado()
					.buscarHidrometroInstaladoPorImovelTipoMedicao(idMacro, consumoHistorico.getTipoLigacao());
			
			if(hidrometro!=null && hidrometro.getLeituraAtualFaturamentoHelper()!=null){
			
				Integer consumoCobrado = consumoHistorico.getConsumoCobradoMes();
				Integer consumoMicros = getControladorConsumoHistorico().obterConsumoImoveisMicro(idMacro,consumoHistorico.getTipoLigacao());
				Integer consumoARatear = consumoCobrado - consumoMicros;
				Integer valorAjuste = consumoARatear % (getControladorImovelConta().obterQuantidadeImovelMicro(idMacro)-1);
				
				if(consumoARatear!=0 && valorAjuste!=0){
					hidrometro.setLeituraAtualFaturamento(hidrometro.getLeituraAtualFaturamentoHelper()-valorAjuste);
				}else{
					hidrometro.setLeituraAtualFaturamento(hidrometro.getLeituraAtualFaturamentoHelper());
				}
				
				ControladorBasico.getInstance().atualizar(hidrometro);
			}
		}
	}
	
	public void concluirRateio() throws ControladorException{
		//imprime o extrato do macro
		
		
		ConsumoHistorico consumoHistorioAguaMacro = getControladorConsumoHistorico().
				buscarConsumoHistoricoPorImovelIdLigacaoTipo(imovelMacro.getId(), ConstantesSistema.LIGACAO_AGUA);
		
		ConsumoHistorico consumoHistorioPocoMacro = getControladorConsumoHistorico().
				buscarConsumoHistoricoPorImovelIdLigacaoTipo(imovelMacro.getId(), ConstantesSistema.LIGACAO_POCO);


		ajustarLeituraMacro(consumoHistorioAguaMacro);
		
		ajustarLeituraMacro(consumoHistorioPocoMacro);
		
		getControladorImpressao().imprimirExtratoMacro(getContext(), imovelMacro);
		//Atualiza indicador de impressao  do imovel macro
		if(sistemaParametros.getIndicadorSistemaLeitura() != null && 
				!sistemaParametros.getIndicadorSistemaLeitura().equals(ConstantesSistema.SIM)){
			imovelMacro.setIndcImovelImpresso(ConstantesSistema.SIM);
		}
		imovelMacro.setIndcRateioRealizado(ConstantesSistema.SIM);
		
		ControladorBasico.getInstance().atualizar(imovelMacro);
		
		// Caso o imóvel ja exista na lista
		// não deve ser adicionado novamente
//		if (!imoveisParaEnvio.contains(imovelMacro)) {
//			imoveisParaEnvio.add(imovelMacro);
//		}
		
		// Obtem todos o imovel condominio completo
		imoveisParaEnvio = getControladorImovelConta().buscarImovelCondominio(imovelMacro.getId());
		
		if(sistemaParametros.getIndicadorTransmissaoOffline().equals(ConstantesSistema.NAO)
				&& enviarImovelCondominio){
			new Thread(new Runnable() {

				@Override
				public void run() {
						ComunicacaoWebServer.enviarImovelOnLine(
								imoveisParaEnvio, getContext());

				}

			}).start();
		}

		
	}
	
	//Retorna se o imóvel pode imprimir
	private boolean ratear(ImovelConta imovelMicro) throws ControladorException, ZebraException {
		int tipoLigacao = 0;

		HidrometroInstalado hidrometroAguaMicro = getControladorHidrometroInstalado().
				buscarHidrometroInstaladoPorImovelTipoMedicao(imovelMicro.getId(), ConstantesSistema.LIGACAO_AGUA);
		HidrometroInstalado hidrometroPocoMicro = getControladorHidrometroInstalado().
				buscarHidrometroInstaladoPorImovelTipoMedicao(imovelMicro.getId(), ConstantesSistema.LIGACAO_POCO);
		
		if (hidrometroAguaMicro != null) {
			tipoLigacao = ConstantesSistema.LIGACAO_AGUA;
		} else if (hidrometroPocoMicro != null) {
			tipoLigacao = ConstantesSistema.LIGACAO_POCO;
		}

		// caso o tipo de rateio seja igual a área comum
		// limpa o consumo a ser rateado por imóvel micro
		if (existeImovelVinculadoRateioAreaComumAgua) {

			if (hidrometroAguaMicro != null) {
				determinarRateioAguaMedido(imovelMicro, tipoLigacao, consumoAguaASerRateadoPorEconomiaMedido, existeImovelVinculadoRateioAreaComumAgua);
			} else {
				determinarRateioAguaNaoMedido(imovelMicro, tipoLigacao, consumoAguaASerRateadoPorEconomiaNaoMedido, imovelMacro, existeImovelVinculadoRateioAreaComumAgua);
			}
			
		} else {
			if (helper.getQuantidadeEconomiasAguaNaoMedidas() > 0) {
				determinarRateioAguaNaoMedido(imovelMicro, tipoLigacao, consumoAguaASerRateadoPorEconomiaNaoMedido, imovelMacro, existeImovelVinculadoRateioAreaComumAgua);

			} else {
				determinarRateioAguaMedido(imovelMicro, tipoLigacao, consumoAguaASerRateadoPorEconomiaMedido,  existeImovelVinculadoRateioAreaComumAgua);
			}
		}
				
		if (existeImovelVinculadoRateioAreaComumEsgoto) {
			
			if (hidrometroPocoMicro != null) {

				determinarRateioEsgotoMedido(imovelMicro, consumoEsgotoASerRateadoPorEconomiaMedido, imovelMacro, existeImovelVinculadoRateioAreaComumEsgoto);

			} else {
				determinarRateioEsgotoNaoMedido(imovelMicro, consumoEsgotoASerRateadoPorEconomiaNaoMedido, imovelMacro, existeImovelVinculadoRateioAreaComumEsgoto);
			}

		} else {
			
			if (helper.getQuantidadeEconomiasEsgotoNaoMedidas() > 0) {

				determinarRateioEsgotoNaoMedido(imovelMicro, consumoEsgotoASerRateadoPorEconomiaNaoMedido, imovelMacro, existeImovelVinculadoRateioAreaComumEsgoto);

			} else {
				determinarRateioEsgotoMedido(imovelMicro, consumoEsgotoASerRateadoPorEconomiaMedido, imovelMacro, existeImovelVinculadoRateioAreaComumEsgoto);
			}

		}

		ControladorBasico.getInstance().atualizar(imovelMicro);

		ConsumoHistorico consumoAgua = getControladorConsumoHistorico().
				buscarConsumoHistoricoPorImovelIdLigacaoTipo(imovelMicro.getId(), ConstantesSistema.LIGACAO_AGUA);
		ConsumoHistorico consumoEsgoto = getControladorConsumoHistorico().
				buscarConsumoHistoricoPorImovelIdLigacaoTipo(imovelMicro.getId(), ConstantesSistema.LIGACAO_ESGOTO);
		
		// Calculamos os valores do imovel 
		ControladorConta.getInstance().calcularValores(imovelMicro, consumoAgua, consumoEsgoto);

		// Caso o imóvel ja exista na lista
		// não deve ser adicionado novamente
//		if (!imoveisParaEnvio.contains(imovelMicro)) {
//			imoveisParaEnvio.add(imovelMicro);
//		}
		

		// Caso o valor da conta seja menor que o valor
		// permitido para ser impresso,
		// não imprimir a conta ou
		// o valor do crédito for maior que o valor da conta,
		// não imprime a conta
		imprimirEnviarValorContaMenorMinimo = getControladorImovelConta().isValorContaMenorPermitido(imovelMicro);

		//imprimirEnviarValorContaMaiorMaximo = getControladorImovelConta().isValorContaMaiorPermitido(imovelMicro);

		boolean validarValor = true;

		if (!sistemaParametros.getCodigoEmpresaFebraban().equals(ConstantesSistema.CODIGO_FEBRABAN_COMPESA)) {
			validarValor = false;
		}

		if (!imprimirEnviarValorContaMenorMinimo || imovelMicro.getIndcEmissaoConta().equals(ConstantesSistema.NAO)) {
			// System.out
			// .println("Valor da conta menor que o valor permitido!");
		} else if (Util.validarImpressaoValorAcimaLimite(
				ControladorConta.getInstance().obterValorConta(imovelMicro.getId()),
				ConstantesSistema.VALOR_LIMITE_CONTA, imovelMicro)
				&& validarValor) {

			// System.out.println("Valor não permite impressao!");

		} else {
			// @autor Thúlio Araújo
			// @since 21/12/2011
			// [FS0002] - Verificar não impressão da conta.
			// Caso seja informado uma anormalidade de leitura com indicador
			// de não impressão da conta
			// igual a 1 (SIM) (Indicador de Não Impressão da Conta do
			// Registro tipo 14 seja igual a 1 - SIM),
			// então a conta não será impressa.
			int anormalidadeInformada = Integer.MIN_VALUE;

			if (hidrometroAguaMicro != null && hidrometroAguaMicro.getAnormalidade()!=null) {
				anormalidadeInformada = hidrometroAguaMicro.getAnormalidade().intValue();
			}

			boolean indicadorNaoImprimirConta = false;

			if (anormalidadeInformada > 0) {
				LeituraAnormalidade anormalidade = (LeituraAnormalidade) ControladorBasico.getInstance()
						.pesquisarPorId(anormalidadeInformada, new LeituraAnormalidade());
										
				if (anormalidade!=null){
					if (anormalidade.getIndicadorNaoImpressaoConta().equals(ConstantesSistema.SIM)) {
						indicadorNaoImprimirConta = true;
					}
				}					
			}
			
			return !indicadorNaoImprimirConta;			
		}
		
		return false;
	}
	
	private void imprimir(ImovelConta imovelMicro) throws ControladorException, ZebraException {
		if (getControladorImpressao().verificarExistenciaImpressora(imovelMicro)){
			//getControladorImpressao().verificarImpressaoConta(imovelMicro,getContext(),0,false);	
			getControladorImpressao().imprimirConta(imovelMicro,context); 
		} else {
			throw new ConexaoImpressoraException();
		}
	}
	
	private void determinarRateioAguaNaoMedido(ImovelConta imovelMicro, int tipoLigacao,
			int consumoAguaASerRateadoPorEconomiaNaoMedido, ImovelConta imovelMacro, boolean existeImovelVinculadoRateioAreaComumAgua)
					throws ControladorException {
		// Calculamos o rateio para o imovel micro, para não medido
		HidrometroInstalado hidrometro = getControladorHidrometroInstalado().
				buscarHidrometroInstaladoPorImovelTipoMedicao(imovelMicro.getId(), tipoLigacao);
		
		if (existeImovelVinculadoRateioAreaComumAgua && hidrometro != null
				&& imovelMicro.getIndcAreaComum().equals(ConstantesSistema.SIM)) {

			consumoAguaASerRateadoPorEconomiaNaoMedido = getControladorConsumoHistorico().
					buscarConsumoHistoricoPorImovelIdLigacaoTipo(imovelMicro.getId(),ConstantesSistema.LIGACAO_AGUA).getConsumoRateio();
			
			calcularConsumoAguaImovelMicroNaoMedido(imovelMicro, consumoAguaASerRateadoPorEconomiaNaoMedido);

		} else {
			calcularConsumoAguaImovelMicroNaoMedido(imovelMicro, consumoAguaASerRateadoPorEconomiaNaoMedido);
		}
	}
	
	private void determinarRateioAguaMedido(ImovelConta imovelMicro, int tipoLigacao,
			int consumoAguaASerRateadoPorEconomiaMedido, 
			boolean existeImovelVinculadoRateioAreaComumAgua) throws ControladorException {
		
		//Calculamos o rateio para o imovel micro, para medido
		HidrometroInstalado hidrometro = getControladorHidrometroInstalado().
				buscarHidrometroInstaladoPorImovelTipoMedicao(imovelMicro.getId(), tipoLigacao);
		
		if (existeImovelVinculadoRateioAreaComumAgua && hidrometro != null
				&& imovelMicro.getIndcAreaComum().equals(ConstantesSistema.SIM)) {

			consumoAguaASerRateadoPorEconomiaMedido = getControladorConsumoHistorico().
					buscarConsumoHistoricoPorImovelIdLigacaoTipo(imovelMicro.getId(), ConstantesSistema.LIGACAO_AGUA)
					.getConsumoRateio();

			calcularConsumoAguaImovelMicroMedido(imovelMicro, consumoAguaASerRateadoPorEconomiaMedido);

		} else {
			calcularConsumoAguaImovelMicroMedido(imovelMicro, consumoAguaASerRateadoPorEconomiaMedido);
		}
	}

	
	private void determinarRateioEsgotoNaoMedido(ImovelConta imovelMicro, int consumoEsgotoASerRateadoPorEconomiaNaoMedido,
			ImovelConta imovelMacro, boolean existeImovelVinculadoRateioAreaComumEsgoto) throws ControladorException {
		// Calculamos o rateio para o imovel micro, para não medido
		HidrometroInstalado hidrometroAgua = getControladorHidrometroInstalado().
				buscarHidrometroInstaladoPorImovelTipoMedicao(imovelMicro.getId(), ConstantesSistema.LIGACAO_AGUA);
		
		if (existeImovelVinculadoRateioAreaComumEsgoto && hidrometroAgua != null
				&& imovelMicro.getIndcAreaComum().equals(ConstantesSistema.SIM)) {

			
			consumoEsgotoASerRateadoPorEconomiaNaoMedido = getControladorConsumoHistorico().
					buscarConsumoHistoricoPorImovelIdLigacaoTipo(imovelMicro.getId(), ConstantesSistema.LIGACAO_ESGOTO)
					.getConsumoRateio();	
			
			calcularConsumoEsgotoImovelMicroNaoMedido(imovelMicro, consumoEsgotoASerRateadoPorEconomiaNaoMedido, imovelMacro);

		} else {
			calcularConsumoEsgotoImovelMicroNaoMedido(imovelMicro, consumoEsgotoASerRateadoPorEconomiaNaoMedido, imovelMacro);
		}
	}

	private void determinarRateioEsgotoMedido(ImovelConta imovelMicro, int consumoEsgotoASerRateadoPorEconomiaMedido,
			ImovelConta imovelMacro, boolean existeImovelVinculadoRateioAreaComumEsgoto) throws ControladorException {
		// Calculamos o rateio para o imovel micro, para medido
		HidrometroInstalado hidrometroAgua = getControladorHidrometroInstalado().
				buscarHidrometroInstaladoPorImovelTipoMedicao(imovelMicro.getId(), ConstantesSistema.LIGACAO_AGUA);
		
		if (existeImovelVinculadoRateioAreaComumEsgoto && hidrometroAgua != null
				&& imovelMicro.getIndcAreaComum().equals(ConstantesSistema.SIM)) {

			consumoEsgotoASerRateadoPorEconomiaMedido = getControladorConsumoHistorico().
					buscarConsumoHistoricoPorImovelIdLigacaoTipo(imovelMicro.getId(), ConstantesSistema.LIGACAO_ESGOTO)
					.getConsumoRateio();					

			calcularConsumoEsgotoImovelMicroMedido(imovelMicro, consumoEsgotoASerRateadoPorEconomiaMedido);

		} else {
			calcularConsumoEsgotoImovelMicroMedido(imovelMicro, consumoEsgotoASerRateadoPorEconomiaMedido);
		}
	}
	
	private void calcularConsumoAguaImovelMicroNaoMedido(ImovelConta imovelMicro,
			int consumoAguaASerRateadoPorEconomiaNaoMedido) throws ControladorException {
		// Caso o imovel seja cortado ou ligado de agua
		if (SistemaParametros.getInstancia().getIndicadorRateioAreaComumImovelNaoFat().equals(ConstantesSistema.SIM)
				|| imovelMicro.getIndcFaturamentoAgua().equals(ConstantesSistema.SIM)) {
		
			
			HidrometroInstalado ligacaoAguaImovelMicro = getControladorHidrometroInstalado().
					buscarHidrometroInstaladoPorImovelTipoMedicao(imovelMicro.getId(), ConstantesSistema.LIGACAO_AGUA);
			
			//Obtem consumo Agua
			ConsumoHistorico consumoAgua = getControladorConsumoHistorico().
					buscarConsumoHistoricoPorImovelIdLigacaoTipo(imovelMicro.getId(), ConstantesSistema.LIGACAO_AGUA);
			
			if (consumoAgua!=null){
				// Caso o imóvel não possua hidrômetro de água (não exista registro
				// tipo 8 para o
				// Tipo de Medição = ligação de água), o consumo rateio água do
				// imóvel = consumo de água a ser
				// rateado por economia * Quantidade de economias do imóvel; caso
				// contrário, o consumo rateio água do
				// imóvel = 0;
				if (ligacaoAguaImovelMicro == null) {
					consumoAgua.setConsumoRateio(consumoAguaASerRateadoPorEconomiaNaoMedido
							* getControladorCategoriaSubcategoria().obterQuantidadeEconomiasTotal(imovelMicro.getId()).intValue());
					// Caso contrario
				} else {
					consumoAgua.setConsumoRateio(0);
				}
				
				// Caso o consumo de rateio água do imóvel seja menor que zero e o
				// consumo de rateio água do
				// imóvel * (-1) seja maior que o consumo faturado (Consumo a ser
				// cobrado no mês), então o consumo
				// de rateio água do imóvel = ao consumo faturado * (-1)
				if (consumoAgua.getConsumoRateio().intValue() < 0
						&& (consumoAgua.getConsumoRateio().intValue() * -1) > consumoAgua.getConsumoCobradoMesOriginal().intValue()) {
					consumoAgua.setConsumoRateio(consumoAgua.getConsumoCobradoMesOriginal().intValue()	* -1);
				}
	
				// O consumo de água dos imóveis MICRO = consumo faturado do mês
				// (Consumo a ser cobrado no mês);
				consumoAgua.setConsumoCobradoMesImoveisMicro(consumoAgua.getConsumoCobradoMesOriginal());
	
				// O consumo faturado no mês (Consumo a ser cobrado no mês) =
				// consumo faturado no mês +
				// consumo rateio água do imóvel (Consumo Rateio Água);
				consumoAgua.setConsumoCobradoMes(consumoAgua.getConsumoCobradoMesOriginal().intValue()
						+ consumoAgua.getConsumoRateio().intValue());
				
				ControladorBasico.getInstance().atualizar(consumoAgua);
			}
		}
	}

	
	private void calcularConsumoAguaImovelMicroMedido(ImovelConta imovelMicro, 
			int consumoAguaASerRateadoPorEconomiaMedido) throws ControladorException {
		
		// Caso o imovel seja cortado ou ligado de agua
		if (SistemaParametros.getInstancia().getIndicadorRateioAreaComumImovelNaoFat().equals(ConstantesSistema.SIM)
				|| imovelMicro.getIndcFaturamentoAgua().equals(ConstantesSistema.SIM)) {
		
			//Obtem consumo de agua
			ConsumoHistorico consumoAguaMicro = getControladorConsumoHistorico().
					buscarConsumoHistoricoPorImovelIdLigacaoTipo(imovelMicro.getId(), ConstantesSistema.LIGACAO_AGUA);
			
			if (consumoAguaMicro!=null){
				// 5.1 O consumo rateio água do imóvel (Consumo Rateio Água) =
				// Consumo de água a ser rateado por
				// economia * Quantidade de economias do imóvel;
				consumoAguaMicro.setConsumoRateio(consumoAguaASerRateadoPorEconomiaMedido
						* getControladorCategoriaSubcategoria().obterQuantidadeEconomiasTotal(imovelMicro.getId()).intValue());
	
				// 5.2 Caso o consumo de rateio água do imóvel (Consumo Rateio
				// Água) seja menor que zero e o
				// consumo de rateio água do imóvel * (-1) seja maior que o
				// consumo faturado (Consumo a ser
				// cobrado no mês), então o consumo de rateio água do imóvel = o
				// consumo faturado * (-1).
				if (consumoAguaMicro.getConsumoRateio().intValue() < 0
						&& (consumoAguaMicro.getConsumoRateio().intValue() * -1) > consumoAguaMicro.getConsumoCobradoMesOriginal().intValue()) {
					consumoAguaMicro.setConsumoRateio(consumoAguaMicro.getConsumoCobradoMesOriginal().intValue() * -1);
				}
	
				// 5.3 O consumo de água dos imóveis MICRO (Consumo Água Imóveis
				// MICRO) = consumo faturado
				// do mês (Consumo a ser cobrado no mês);
				consumoAguaMicro.setConsumoCobradoMesImoveisMicro(consumoAguaMicro.getConsumoCobradoMesOriginal());
	
				// 5.4 O consumo faturado no mês (Consumo a ser cobrado no mês)
				// = consumo faturado no mês +
				// consumo rateio água do imóvel (Consumo Rateio Água);
				consumoAguaMicro.setConsumoCobradoMes(consumoAguaMicro.getConsumoCobradoMesOriginal()
						+ consumoAguaMicro.getConsumoRateio());	
				
				ControladorBasico.getInstance().atualizar(consumoAguaMicro);
			}
		}
	}

	private void calcularConsumoEsgotoImovelMicroNaoMedido(ImovelConta imovelMicro,
			int consumoEsgotoASerRateadoPorEconomiaNaoMedido, ImovelConta imovelMacro) throws ControladorException {
		// Caso o imovel seja cortado ou ligado de esgoto
		if (SistemaParametros.getInstancia().getIndicadorRateioAreaComumImovelNaoFat().equals(ConstantesSistema.SIM)
				|| imovelMicro.getIndcFaturamentoEsgoto().equals(ConstantesSistema.SIM)) {
		
			// Selecionamos a ligação de agua do imovel macro
			// para verificarmos se o mesmo possue
			// tipo de rateio = 4
			HidrometroInstalado ligacaoAguaImovelMacro = getControladorHidrometroInstalado().
					buscarHidrometroInstaladoPorImovelTipoMedicao(imovelMacro.getId(), ConstantesSistema.LIGACAO_AGUA);
			
			ConsumoHistorico consumoEsgotoMicro = getControladorConsumoHistorico().
					buscarConsumoHistoricoPorImovelIdLigacaoTipo(imovelMicro.getId(), ConstantesSistema.LIGACAO_ESGOTO);
			
			if (consumoEsgotoMicro!=null){
				// Caso o imóvel não possua hidrômetro de esgoto (não exista
				// registro tipo 8 para o
				// Tipo de Medição = ligação de esgoto), o consumo rateio esgoto do
				// imóvel = consumo de esgoto a ser
				// rateado por economia * Quantidade de economias do imóvel; caso
				// contrário, o consumo rateio esgoto do
				// imóvel = 0;
				if (ligacaoAguaImovelMacro != null
						&& ligacaoAguaImovelMacro.getTipoRateio()!=null &&
								ligacaoAguaImovelMacro.getTipoRateio().intValue()== ConstantesSistema.TIPO_RATEIO_NAO_MEDIDO_AGUA) {
	
					HidrometroInstalado ligacaoAguaImovelMicro = getControladorHidrometroInstalado().
							buscarHidrometroInstaladoPorImovelTipoMedicao(imovelMicro.getId(), ConstantesSistema.LIGACAO_AGUA);
					
					if (ligacaoAguaImovelMicro == null) {
						consumoEsgotoMicro.setConsumoRateio(consumoEsgotoASerRateadoPorEconomiaNaoMedido
								* getControladorCategoriaSubcategoria().obterQuantidadeEconomiasTotal(imovelMicro.getId()).intValue());
						// Caso Contrario
					} else {
						consumoEsgotoMicro.setConsumoRateio(0);
					}
				} else {
	
					HidrometroInstalado ligacaoEsgotoImovelMicro = getControladorHidrometroInstalado().
							buscarHidrometroInstaladoPorImovelTipoMedicao(imovelMicro.getId(), ConstantesSistema.LIGACAO_POCO);
					
					if (ligacaoEsgotoImovelMicro == null) {
						consumoEsgotoMicro.setConsumoRateio(consumoEsgotoASerRateadoPorEconomiaNaoMedido
								* getControladorCategoriaSubcategoria().obterQuantidadeEconomiasTotal(imovelMicro.getId()).intValue());;
						// Caso contrario
					} else {
						consumoEsgotoMicro.setConsumoRateio(0);
					}
				}
	
				// Caso o consumo de rateio esgoto do imóvel seja menor que zero e o
				// consumo de rateio esgoto do
				// imóvel * (-1) seja maior que o consumo faturado (Consumo a ser
				// cobrado no mês), então o consumo
				// de rateio esgoto do imóvel = ao consumo faturado * (-1)
				if (consumoEsgotoMicro.getConsumoRateio() < 0
						&& (consumoEsgotoMicro.getConsumoRateio() * -1) > consumoEsgotoMicro.getConsumoCobradoMesOriginal()) {
					consumoEsgotoMicro.setConsumoRateio(consumoEsgotoMicro.getConsumoCobradoMesOriginal()
							* -1);
				}
	
				// O consumo de esgoto dos imóveis MICRO = consumo faturado do mês
				// (Consumo a ser cobrado no mês);
				consumoEsgotoMicro.setConsumoCobradoMesImoveisMicro(consumoEsgotoMicro.getConsumoCobradoMesOriginal());
	
				// O consumo faturado no mês (Consumo a ser cobrado no mês) =
				// consumo faturado no mês +
				// consumo rateio esgoto do imóvel (Consumo Rateio esgoto);
				consumoEsgotoMicro.setConsumoCobradoMes(consumoEsgotoMicro.getConsumoCobradoMesOriginal()
						+ consumoEsgotoMicro.getConsumoRateio());
				
				ControladorBasico.getInstance().atualizar(consumoEsgotoMicro);
			}
		}
	}

	private void calcularConsumoEsgotoImovelMicroMedido(ImovelConta imovelMicro,
			int consumoEsgotoASerRateadoPorEconomiaMedido) throws ControladorException {

		// Caso o imovel seja cortado ou ligado de esgoto
		if (SistemaParametros.getInstancia().getIndicadorRateioAreaComumImovelNaoFat().equals(ConstantesSistema.SIM)
				|| imovelMicro.getIndcFaturamentoEsgoto().equals(ConstantesSistema.SIM)) {
		
			//Obtem consumo esgoto do imovel
			ConsumoHistorico consumo = getControladorConsumoHistorico().
					buscarConsumoHistoricoPorImovelIdLigacaoTipo(imovelMicro.getId(), ConstantesSistema.LIGACAO_ESGOTO);
			
			if (consumo!=null){
				// 5.1 O consumo rateio esgoto do imóvel (Consumo Rateio esgoto) =
				// Consumo de esgoto a ser rateado por
				// economia * Quantidade de economias do imóvel;
				consumo.setConsumoRateio(consumoEsgotoASerRateadoPorEconomiaMedido
						* getControladorCategoriaSubcategoria().obterQuantidadeEconomiasTotal(imovelMicro.getId()));
	
				// 5.2 Caso o consumo de rateio esgoto do imóvel (Consumo Rateio
				// esgoto) seja menor que zero e o
				// consumo de rateio esgoto do imóvel * (-1) seja maior que o
				// consumo faturado (Consumo a ser
				// cobrado no mês), então o consumo de rateio esgoto do imóvel = o
				// consumo faturado * (-1).
				if (consumo.getConsumoRateio().intValue() < 0
						&& (consumo.getConsumoRateio() * -1) > consumo.getConsumoCobradoMesOriginal()) {
						consumo.setConsumoRateio(consumo.getConsumoCobradoMesOriginal()	* -1);
				}
	
				// 5.3 O consumo de esgoto dos imóveis MICRO (Consumo esgoto Imóveis
				// MICRO) = consumo faturado
				// do mês (Consumo a ser cobrado no mês);
				consumo.setConsumoCobradoMesImoveisMicro(consumo.getConsumoCobradoMesOriginal());
	
				// 5.4 O consumo faturado no mês (Consumo a ser cobrado no mês)
				// = consumo faturado no mês +
				// consumo rateio esgoto do imóvel (Consumo Rateio esgoto);
				consumo.setConsumoCobradoMes(consumo.getConsumoCobradoMesOriginal()
						+ consumo.getConsumoRateio());
				
				ControladorBasico.getInstance().atualizar(consumo);
			}
		}
	}

}