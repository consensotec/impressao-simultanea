
package com.br.ipad.isc.controladores;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.util.Log;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.CategoriaSubcategoria;
import com.br.ipad.isc.bean.ConsumoHistorico;
import com.br.ipad.isc.bean.ConsumoTarifaCategoria;
import com.br.ipad.isc.bean.HidrometroInstalado;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.bean.SistemaParametros;
import com.br.ipad.isc.excecoes.ControladorException;
import com.br.ipad.isc.excecoes.RepositorioException;
import com.br.ipad.isc.repositorios.RepositorioConsumoHistorico;
import com.br.ipad.isc.util.ConstantesSistema;
import com.br.ipad.isc.util.Util;

public class ControladorConsumoHistorico extends ControladorBasico implements IControladorConsumoHistorico{
	
	private static ControladorConsumoHistorico instance;
	private RepositorioConsumoHistorico repositorioConsumoHistorico;
	protected static Context context;
	
	public void resetarInstancia(){
		instance = null;
	}
	
	protected ControladorConsumoHistorico(){
		super();
	}
	
	public static ControladorConsumoHistorico getInstance(){
		if ( instance == null ){
			instance =  new ControladorConsumoHistorico();
			instance.repositorioConsumoHistorico = RepositorioConsumoHistorico.getInstance();			
		}
		
		return instance;		
	}

	public void setContext(Context ctx ) {
		context = ctx;		
	}	

	public ConsumoHistorico buscarConsumoHistoricoPorImovelIdLigacaoTipo(Integer imovelId, Integer ligacaoTipo) throws ControladorException {
		try {
			return repositorioConsumoHistorico.buscarConsumoHistoricoPorImovelIdLigacaoTipo(imovelId, ligacaoTipo);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}

	
	/**
	 * [SB0010] - Ajuste do Consumo para o Múltiplo da Quantidade de Economias
	 */
	public void ajustarConsumo(ImovelConta imovel, ConsumoHistorico consumo, int qtdEconomias, Integer leituraAnterior, 
			int tipoMedicao) throws ControladorException {

		// [SB0009] 1.
		int restoDiv = 0;
		
		int calculoExcessoImovel = consumo.getConsumoCobradoMes() - imovel.getConsumoMinimoImovel();

		if (calculoExcessoImovel > 0) {

			// Obter Quantidade de Economias do imóvel
			int quantidadeEconomiasImovel = getControladorCategoriaSubcategoria(). 
					obterQuantidadeEconomiasTotal(imovel.getId());

			// Cálculo do Excesso por economia = Consumo Excedente / quantidade
			// total de economias do imóvel
			int calculoExcessoEconomia = calculoExcessoImovel / quantidadeEconomiasImovel;

			ArrayList<ConsumoTarifaCategoria> tarifas = 
					getControladorConsumoTarifaCategoria().buscarConsumoTarifaCategoriaPorCodigo(imovel.getCodigoTarifa());
			
			Date dataVigenciaPrimeiraVigencia = tarifas.get(0).getDataVigencia();

			// Retorno
			int calculoConsumoTotalAjustado = 0;

			ArrayList<CategoriaSubcategoria> categoriasOuSubcategorias = 
					getControladorCategoriaSubcategoria().buscarCategoriaSubcategoriaPorImovelId(imovel.getId());

			for (CategoriaSubcategoria categoriaOuSubcategoria : categoriasOuSubcategorias) {
				int codigoSubcategoria = 0;

				if (categoriaOuSubcategoria.getCodigoSubcategoria() != null
						&& !categoriaOuSubcategoria.getCodigoSubcategoria().equals("")) {
					codigoSubcategoria = categoriaOuSubcategoria.getCodigoSubcategoria();
				}

				boolean calculoPorSubcategoria = false;

				// Calculamos por subcategoria
				for (ConsumoTarifaCategoria tarifa : tarifas){
				
					if (Util.compararData(dataVigenciaPrimeiraVigencia, tarifa.getDataVigencia()) == 0
							&& imovel.getCodigoTarifa().intValue() == tarifa.getConsumoTarifa().intValue()
							&& tarifa.getIdCategoria() != null && categoriaOuSubcategoria.getCodigoCategoria() == tarifa.getIdCategoria().intValue()
							&& tarifa.getIdSubcategoria() != null && codigoSubcategoria == tarifa.getIdSubcategoria().intValue()) {
						// Consumo excendente da categoria = Cálculo do Excesso
						// por economia * quantidade de economias da categoria
						int consumoExcedenteCategoriaOuSubcategoria = calculoExcessoEconomia
								* categoriaOuSubcategoria.getQtdEconomiasSubcategoria();

						// Consumo da categoria = Consumo excendente da
						// categoria + ConsumoMinimoCategoria
						int consumoCategoriaOuSubcategoria = consumoExcedenteCategoriaOuSubcategoria
								+ (tarifa.getConsumoMinimoSubcategoria() * categoriaOuSubcategoria.getQtdEconomiasSubcategoria());

						// TOTALIZANDO O CONSUMO EXCEDENTE
						calculoConsumoTotalAjustado += consumoCategoriaOuSubcategoria;

						calculoPorSubcategoria = true;
						break;
					}
				}

				if (!calculoPorSubcategoria) {

					// Calculamos por categoria
					for (ConsumoTarifaCategoria tarifa : tarifas){

						if (Util.compararData(dataVigenciaPrimeiraVigencia, tarifa.getDataVigencia()) == 0
								&& imovel.getCodigoTarifa().intValue() == tarifa.getConsumoTarifa().intValue()
								&& categoriaOuSubcategoria.getCodigoCategoria() == tarifa.getIdCategoria().intValue()
								&& (tarifa.getIdSubcategoria() == null || tarifa.getIdSubcategoria().intValue() == 0)) {

							// Consumo excendente da categoria = Cálculo do
							// Excesso por economia * quantidade de economias da
							// categoria
							int consumoExcedenteCategoriaOuSubcategoria = calculoExcessoEconomia
									* categoriaOuSubcategoria.getQtdEconomiasSubcategoria();

							// Consumo da categoria = Consumo excendente da
							// categoria + ConsumoMinimoCategoria
							int consumoCategoriaOuSubcategoria = consumoExcedenteCategoriaOuSubcategoria
									+ (tarifa.getConsumoMinimoSubcategoria() * categoriaOuSubcategoria.getQtdEconomiasSubcategoria());

							// TOTALIZANDO O CONSUMO EXCEDENTE
							calculoConsumoTotalAjustado += consumoCategoriaOuSubcategoria;

							break;
						}
					}
				}
			}

			calculoConsumoTotalAjustado = consumo.getConsumoCobradoMes() / qtdEconomias;
			calculoConsumoTotalAjustado = calculoConsumoTotalAjustado * qtdEconomias;

			restoDiv = consumo.getConsumoCobradoMes() - calculoConsumoTotalAjustado;

		} else {
			restoDiv = consumo.getConsumoCobradoMes() % qtdEconomias;
		}

		int leituraFaturadaAtual = 0;

		// [SB0009] 2.1.
		if (leituraAnterior != null) {

			if (tipoMedicao == ConstantesSistema.LIGACAO_AGUA) {
				HidrometroInstalado hidrometroAgua = getControladorHidrometroInstalado().
						buscarHidrometroInstaladoPorImovelTipoMedicao(imovel.getId(), ConstantesSistema.LIGACAO_AGUA);
				
				if (hidrometroAgua != null) {

					if (hidrometroAgua.getLeituraAtualFaturamento() != null) {
						leituraFaturadaAtual = hidrometroAgua.getLeituraAtualFaturamento()
								- restoDiv;
						hidrometroAgua.setLeituraAtualFaturamento(leituraFaturadaAtual);
						hidrometroAgua.setLeituraAtualFaturamentoHelper(leituraFaturadaAtual);
						ControladorBasico.getInstance().atualizar(hidrometroAgua);
					}

				}
			} else {
				HidrometroInstalado hidrometroPoco = getControladorHidrometroInstalado().
						buscarHidrometroInstaladoPorImovelTipoMedicao(imovel.getId(), ConstantesSistema.LIGACAO_POCO);
				
				if (hidrometroPoco != null) {
					if (hidrometroPoco.getLeituraAtualFaturamento() != null) {

						leituraFaturadaAtual = hidrometroPoco.getLeituraAtualFaturamento()
								- restoDiv;

						hidrometroPoco.setLeituraAtualFaturamento(leituraFaturadaAtual);
						hidrometroPoco.setLeituraAtualFaturamentoHelper(leituraFaturadaAtual);
						ControladorBasico.getInstance().atualizar(hidrometroPoco);
					}

				}
			}

			if (consumo.getLeituraAtual().intValue() == (leituraAnterior.intValue() + consumo.getConsumoCobradoMes())) {
				consumo.setLeituraAtual(consumo.getLeituraAtual() - restoDiv);
			}
		}

		// [SB0009] 2.2.
		consumo.setConsumoCobradoMes(consumo.getConsumoCobradoMes() - restoDiv);
	}

	
	/**
	 * [UC0101] - Consistir Leituras e Calcular Consumos [SF0017] - Ajuste
	 * Mensal de Consumo
	 */
	public void ajusteMensalConsumo(ImovelConta imovel, 
			HidrometroInstalado hidrometroInstalado, int tipoMedicao, ConsumoHistorico consumo) throws ControladorException {

		Date dataLeituraAnteriorFaturamento = null;
		Date dataLeituraLeituraAtualFaturamento = null;

		dataLeituraAnteriorFaturamento = hidrometroInstalado.getDataLeituraAnterior();
		dataLeituraLeituraAtualFaturamento = hidrometroInstalado.getDataLeitura();

		SistemaParametros sistemaParametros =  SistemaParametros.getInstancia();
		
		// System.out.println("Leitura Anterior Faturamento: "
		// + dataLeituraAnteriorFaturamento);
		// System.out.println("Leitura Atual Faturamento: "
		// + dataLeituraLeituraAtualFaturamento);

		int quantidadeDiasConsumoAjustado = 0;

		// Obtém a quantidade de dias de consumo
		int quantidadeDiasConsumo = (int) Util.obterQuantidadeDiasEntreDuasDatas(dataLeituraLeituraAtualFaturamento,dataLeituraAnteriorFaturamento);

		/*
		 * Caso a quantidade de dias não seja maior do que zero, retornar para
		 * para correspondente no subfluxo que que chamou.
		 * 
		 * Data: 06/04/2010 Desenvolvedor: Breno Santos
		 */

		if (quantidadeDiasConsumo > 0) {
			// Verifica se a data do ajuste é não nula
			if (sistemaParametros.getDataAjusteLeitura() != null) {
				// Obtém a quantidade de dias de consumo ajustado
				quantidadeDiasConsumoAjustado = (int) Util.obterQuantidadeDiasEntreDuasDatasPositivo(dataLeituraAnteriorFaturamento, 
						sistemaParametros.getDataAjusteLeitura());

			} else {

				// Cria objeto
				Calendar data = Calendar.getInstance();

				// Seta a data com a data de leitura anterior faturamento
				data.setTime(dataLeituraAnteriorFaturamento);

				// Obtém a quantidade de dias
				int dias = Util.quantidadeDiasMes(data);

				// Obtém a quantidade de dias de consumo ajustado
				if (sistemaParametros.getQtdDiasAjusteConsumo() != null) {
					quantidadeDiasConsumoAjustado = sistemaParametros.getQtdDiasAjusteConsumo();
				} else {
					quantidadeDiasConsumoAjustado = dias;
				}

			}

			hidrometroInstalado.setQtdDiasAjustado(quantidadeDiasConsumoAjustado);

			// Obtém os dias de ajuste
			int diasAjuste = quantidadeDiasConsumoAjustado - quantidadeDiasConsumo;

			if (diasAjuste < -3 || diasAjuste > 3) {

				if (hidrometroInstalado.getLeitura() != null) {

					// Cálculo para obter a leitura ajustada
					int leituraAjustada = hidrometroInstalado.getLeitura()
							+ Util.divideDepoisMultiplica(consumo.getConsumoCobradoMes(), quantidadeDiasConsumo, diasAjuste);

					int numeroDigitosHidrometro = 0;

					// Verifica qual o tipo de medicação e obtém o número de
					// dígitos
					// do
					// hidrômetro
					if (tipoMedicao == ConstantesSistema.LIGACAO_AGUA) {
						numeroDigitosHidrometro =  getControladorHidrometroInstalado().buscarHidrometroInstaladoPorImovelTipoMedicao(
								imovel.getId(), ConstantesSistema.LIGACAO_AGUA).getNumDigitosLeituraHidrometro();								

					} else if (tipoMedicao == ConstantesSistema.LIGACAO_POCO) {
						numeroDigitosHidrometro = getControladorHidrometroInstalado().buscarHidrometroInstaladoPorImovelTipoMedicao(
								imovel.getId(), ConstantesSistema.LIGACAO_POCO).getNumDigitosLeituraHidrometro();
					}

					// Obtém 10 elevado ao numeroDigitosHidrometro
					int dezElevadoNumeroDigitos = (int) Util.pow(10, numeroDigitosHidrometro);

					// Obtém 10 elevado ao numeroDigitosHidrometro - 1
					int dezElevadoNumeroDigitosMenosUm = ((int) Util.pow(10, numeroDigitosHidrometro)) - 1;

					// Caso a leitura ajustada menor que 0
					if (leituraAjustada < 0) {
						hidrometroInstalado.setLeituraAtualFaturamento(leituraAjustada + dezElevadoNumeroDigitos);
						hidrometroInstalado.setLeituraAtualFaturamentoHelper(leituraAjustada + dezElevadoNumeroDigitos);

						// Caso a leitura ajustada maior dez elevado ao número
						// de
						// dígitos menos um
					} else if (leituraAjustada > dezElevadoNumeroDigitosMenosUm) {
						hidrometroInstalado.setLeituraAtualFaturamento(leituraAjustada - dezElevadoNumeroDigitos);
						hidrometroInstalado.setLeituraAtualFaturamentoHelper(leituraAjustada - dezElevadoNumeroDigitos);

						// Caso demais casos
					} else {
						hidrometroInstalado.setLeituraAtualFaturamento(leituraAjustada);
						hidrometroInstalado.setLeituraAtualFaturamentoHelper(leituraAjustada);
					}
				}

				// Obtém o consumo a ser cobrado mês
				int consumoASerCobradoMes = Util.divideDepoisMultiplica(consumo.getConsumoCobradoMes(), quantidadeDiasConsumo, quantidadeDiasConsumoAjustado);

				// Seta o consumo a ser cobrado mês
				consumo.setConsumoCobradoMes(consumoASerCobradoMes);

				// Adiciona ou subtrai de acordo com os dias de ajuste
//				Date dataLeituraAtualFaturamento = Util.adicionarNumeroDiasDeUmaData(hidrometroInstalado.getDataLeitura(), diasAjuste);

				// Seta a data da leitura atual de faturamento
//				hidrometroInstalado.setDataLeitura(dataLeituraAtualFaturamento);

			}
		}
	}
	
	public Integer obterConsumoImoveisMicro(Integer idImovelMacro,Integer tipoLigacao) throws ControladorException {
		try {
			return repositorioConsumoHistorico.obterConsumoImoveisMicro(idImovelMacro,tipoLigacao);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}		
	}
	
	public Integer obterQuantidadeRegistroConsumoHistorico() throws ControladorException {
		try {
			return repositorioConsumoHistorico.obterQuantidadeRegistroConsumoHistorico();
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}		
	}
}