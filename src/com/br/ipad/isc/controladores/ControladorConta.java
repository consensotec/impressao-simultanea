
package com.br.ipad.isc.controladores;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.br.ipad.isc.bean.ConsumoAnormalidade;
import com.br.ipad.isc.bean.ConsumoAnormalidadeAcao;
import com.br.ipad.isc.bean.ConsumoAnteriores;
import com.br.ipad.isc.bean.ConsumoHistorico;
import com.br.ipad.isc.bean.CreditoRealizado;
import com.br.ipad.isc.bean.DebitoCobrado;
import com.br.ipad.isc.bean.FaturamentoSituacaoTipo;
import com.br.ipad.isc.bean.HidrometroInstalado;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.bean.ImovelRevisitar;
import com.br.ipad.isc.bean.LeituraAnormalidade;
import com.br.ipad.isc.bean.SistemaParametros;
import com.br.ipad.isc.bean.helpers.RateioConsumoHelper;
import com.br.ipad.isc.excecoes.ControladorException;
import com.br.ipad.isc.fachada.Fachada;
import com.br.ipad.isc.util.ConstantesSistema;
import com.br.ipad.isc.util.Util;

public class ControladorConta extends ControladorBasico implements IControladorConta, LocationListener {

	protected LocationManager locationManager;
	
	private static ControladorConta instance;
	protected static Context context;
	public void resetarInstancia(){
		instance = null;
	}
	
	protected ControladorConta(){
		super();	
	}
	
	public static ControladorConta getInstance(){
		if ( instance == null ){
			instance =  new ControladorConta();
		}		
		return instance;		
	}

	public void setContext(Context ctx) {
		context = ctx;		
	}		


	/**
	 * [UC0740] Calcular Consumo no Dispositivo Móvel
	 */
	public boolean calcularConta(ImovelConta imovel, Boolean imprimir, Boolean proximo) throws ControladorException {

		//Atualiza as coordenadas do imóvel
		if ( SistemaParametros.getInstancia().getIndicadorArmazenarCoordenadas() != null && SistemaParametros.getInstancia().getIndicadorArmazenarCoordenadas().equals(ConstantesSistema.SIM) ) {
			
			locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
			locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, this);
			
			Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if(location != null){
				imovel.setNumeroCoordenadaX(BigDecimal.valueOf(location.getLatitude()));
				imovel.setNumeroCoordenadaY(BigDecimal.valueOf(location.getLongitude()));
			}
		}
		
		boolean contaCalculada = false;
		
		ConsumoHistorico consumoAgua = null;
		
		ConsumoHistorico consumoEsgoto = null;

		HidrometroInstalado hidrometroAgua = getControladorHidrometroInstalado().
				buscarHidrometroInstaladoPorImovelTipoMedicao(imovel.getId(), ConstantesSistema.LIGACAO_AGUA);
		HidrometroInstalado hidrometroPoco = getControladorHidrometroInstalado().
				buscarHidrometroInstaladoPorImovelTipoMedicao(imovel.getId(), ConstantesSistema.LIGACAO_POCO);
		
		//Se for imóvel não medido e já estiver calculado, não calcula
		if(hidrometroAgua == null && hidrometroPoco == null && imovel.getIndcImovelCalculado().equals(ConstantesSistema.SIM) && !imprimir){
			return contaCalculada;
		
		}else{
			
			// [UC0740] 2.
			if (imovel.getIndcFaturamentoAgua().equals(ConstantesSistema.SIM) || 
					hidrometroAgua != null) {
				
				consumoAgua = new ConsumoHistorico();
				
				// [UC0740] 2.1.
				if (hidrometroAgua != null) {				
					
					this.calcularConsumo(imovel, ConstantesSistema.LIGACAO_AGUA, consumoAgua,null);
	
					// Caso o consumo a ser cobrado no mês seja menor que o consumo
					// mínimo de água
					if ((imovel.getConsumoMinAgua() != null && imovel.getConsumoMinAgua() != null)
							&& (consumoAgua.getConsumoCobradoMes() != null && consumoAgua.getConsumoCobradoMes() < imovel.getConsumoMinAgua())) {
						// Seta o consumo histórico
						consumoAgua.setConsumoCobradoMes(imovel.getConsumoMinAgua());
						// Seta o consumo anormalidade
						consumoAgua.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_MINIMO_FIX);
					}
					// [UC0740] 2.2.
				} else {
					int consumoMinimo = 0;
					int consumoTipo = 0;
					if (imovel.getConsumoMinAgua() != null && imovel.getConsumoMinAgua().intValue()!=0) {
						consumoMinimo = imovel.getConsumoMinAgua();
						consumoTipo = ConstantesSistema.CONSUMO_TIPO_MINIMO_FIX;
					} else {
						consumoMinimo = imovel.getConsumoMinimoImovel();
						consumoTipo = ConstantesSistema.CONSUMO_TIPO_NAO_MEDIDO;
					}
	
					consumoAgua = new ConsumoHistorico(consumoMinimo,consumoTipo);
	
					// verifica se existe situação tipo
					if (imovel.getFaturamentoSituacaoTipo()!= null && !imovel.getFaturamentoSituacaoTipo().equals("")) {
						FaturamentoSituacaoTipo situacaoTipo = imovel.getFaturamentoSituacaoTipo();
						if (situacaoTipo.getIndcValidaAgua()!=null && situacaoTipo.getIndcValidaAgua().equals(ConstantesSistema.SIM)) {
							dadosFaturamentoEspecialNaoMedido(imovel, consumoAgua, ConstantesSistema.LIGACAO_AGUA);
						}
					}
				}
			} else {
				consumoAgua = null;
			}
		
			// [UC0740] 4.
			int qtdEconomias = getControladorCategoriaSubcategoria().obterQuantidadeEconomiasTotal(imovel.getId());				
	
			if (qtdEconomias > 1) {
				// [SB0009]
				if (consumoAgua != null && consumoAgua.getConsumoCobradoMes() != null) {
					Integer leituraAnterior = null;
	
					if (hidrometroAgua != null) {
						leituraAnterior = obterLeituraAnterior(hidrometroAgua);
					}
	
					getControladorConsumoHistorico().ajustarConsumo(imovel, consumoAgua, qtdEconomias, 
							leituraAnterior, ConstantesSistema.LIGACAO_AGUA);				
				}
			}
	
			if (consumoAgua != null && consumoAgua.getTipoConsumo() != null 
					&& (!consumoAgua.getTipoConsumo().equals(ConstantesSistema.CONSUMO_TIPO_NAO_MEDIDO) || 
					!consumoAgua.getTipoConsumo().equals(ConstantesSistema.CONSUMO_TIPO_MINIMO_FIX))) {
				if (hidrometroAgua != null) {
					consumoAgua.setDiasConsumo(Util.obterQuantidadeDiasEntreDuasDatasPositivo(hidrometroAgua.getDataLeituraAnterior(), Util.dataAtual()));
				}
				
			}
	
			// Alteração para UC0740 - Consumo Real
			if (imovel.getIndcConsumoReal().equals(ConstantesSistema.SIM) 
					|| imovel.getLigacaoAguaSituacaoIndicadorLeituraReal().compareTo(ConstantesSistema.SIM_SHORT)==0) {
	
				// Caso seja necessário verificar a quantidade de dias de corte
				if (imovel.getNumeroDiasCorte() != null && imovel.getNumeroDiasCorte() != 
						0) {
	
					if (hidrometroAgua != null) {
	
						if (imovel.getDataCorte() != null) {
	
							double diferencaDiasCorte = Util.obterQuantidadeDiasEntreDuasDatas(imovel.getDataCorte(), hidrometroAgua.getDataLeituraAnterior() );
							
							// imovel cortado a menos de 1 mes(data de corte maior
							// que data leitura anterior)
							if (diferencaDiasCorte > 0) {
	
								if (diferencaDiasCorte <= imovel.getNumeroDiasCorte()) {
									// Imovel nao e faturado
									imovel.setIndicadorParalizarFaturamentoAgua(1);
	
									if (consumoAgua != null) {
										consumoAgua.setConsumoCobradoMes(0);
									}
								} else {
									// Imovel e faturado
									// System.out.println("Continuar Processamento.");
									imovel.setIndicadorParalizarFaturamentoAgua(2);
								}
	
							} else {
								if ( (
										(imovel.getIndcConsumoReal().equals(ConstantesSistema.SIM) 
												&& consumoAgua.getTipoConsumo().equals(ConstantesSistema.CONSUMO_TIPO_REAL)) ||
										(imovel.getLigacaoAguaSituacaoIndicadorLeituraReal().compareTo(ConstantesSistema.SIM_SHORT)==0 
												&& hidrometroAgua.getLeitura() != null) 
									) && consumoAgua.getConsumoCobradoMes().intValue() > 0) {
									// Imovel e faturado
									// System.out.println("Continuar Processamento.");
									imovel.setIndicadorParalizarFaturamentoAgua(2);
								} else {
									// Imovel nao e faturado
									imovel.setIndicadorParalizarFaturamentoAgua(1);
	
									if (consumoAgua != null) {
										consumoAgua.setConsumoCobradoMes(0);
									}
								}
							}
						}
	
					} else {
						// Caso o imovel seja nao medido
						if (imovel.getDataCorte() != null && imovel.getDataLeituraAnterior() != null) {
	
							double numeroDiasDiferencaDatas = Util.obterQuantidadeDiasEntreDuasDatas(imovel.getDataCorte(), imovel.getDataLeituraAnterior());
	
							// Caso não haja ligação de água
							if (numeroDiasDiferencaDatas > 0) {
	
								if (numeroDiasDiferencaDatas <= imovel.getNumeroDiasCorte()) {
									// Imovel não é faturado
									imovel.setIndicadorParalizarFaturamentoAgua(1);
	
									if (consumoAgua != null) {
										consumoAgua.setConsumoCobradoMes(0);
									}
								} else {
									// Imovel é faturado
									// System.out.println("Continuar processamento.");
									imovel.setIndicadorParalizarFaturamentoAgua(2);
								}
	
							} else {
								// Imovel não é faturado
								imovel.setIndicadorParalizarFaturamentoAgua(1);
	
								if (consumoAgua != null) {
									consumoAgua.setConsumoCobradoMes(0);
								}
							}
	
						}
					}
				}
			}
	
			// Guardamos o consumo original
			if (consumoAgua != null) {
				consumoAgua.setConsumoCobradoMesOriginal(consumoAgua.getConsumoCobradoMes());
			}
	
			// [UC0740] 3.
			if ((imovel.getIndcFaturamentoEsgoto()!=null && imovel.getIndcFaturamentoEsgoto().equals(ConstantesSistema.SIM)) || 
					hidrometroPoco != null) {
				
				consumoEsgoto = new ConsumoHistorico();
				
				// [UC0740] 3.1.
				if (hidrometroPoco != null) {
					this.calcularConsumo(imovel, ConstantesSistema.LIGACAO_POCO, consumoEsgoto,consumoAgua);
	
					// Caso exista Consumo a Ser Cobrado no Mês da ligação de água,
					// o Consumo a Ser Cobrado no Mês de esgoto = (Cobrado no Mês da
					// ligação de água + Consumo a Ser Cobrado no Mês);
					// caso contrário, o Consumo a Ser Cobrado no Mês de esgoto =
					// Consumo a Ser Cobrado no Mês.
					// Seta o consumo histórico
					int consumoFaturadoMes = 0;
	
					if (consumoAgua != null && consumoAgua.getConsumoCobradoMes()!= null
							&& !consumoEsgoto.getTipoConsumo().equals(ConstantesSistema.CONSUMO_TIPO_MEDIA_HIDR)
							&& !consumoEsgoto.getTipoConsumo().equals(ConstantesSistema.CONSUMO_TIPO_MEDIA_IMOV)) {
	
						consumoFaturadoMes = consumoAgua.getConsumoCobradoMes();
	
						
						if (SistemaParametros.getInstancia().getIndicadorPercentualColetaEsgoto().intValue() > 0
								&& SistemaParametros.getInstancia().getIndicadorPercentualColetaEsgoto().equals(ConstantesSistema.SIM)) {
	
							consumoFaturadoMes = consumoFaturadoMes
									+ (Util.arredondar(((consumoEsgoto.getConsumoCobradoMes().doubleValue() * imovel.getPercentColetaEsgoto().doubleValue()) / 100)));
						} else {
							consumoFaturadoMes = consumoFaturadoMes + consumoEsgoto.getConsumoCobradoMes();
						}
						consumoEsgoto.setConsumoCobradoMes(consumoFaturadoMes);
					}
	
					// [UC0740] 3.2.
				} else {
	
					int consumoMinnoEsgoto = 0;
					if (imovel.getConsumoMinEsgoto() != null) {
						consumoMinnoEsgoto = imovel.getConsumoMinEsgoto();
					}
	
					consumoEsgoto = new ConsumoHistorico(consumoMinnoEsgoto,ConstantesSistema.CONSUMO_TIPO_NAO_MEDIDO);
	
					if (imovel.getIndcFaturamentoAgua()!=null &&
							imovel.getIndcFaturamentoAgua().equals(ConstantesSistema.SIM) 
							&& consumoAgua != null
							&& consumoAgua.getConsumoCobradoMes() != null
							&& imovel.getIndicadorParalizarFaturamentoAgua().equals(ConstantesSistema.NAO)) {
	
						if (consumoAgua.getConsumoCobradoSemContratoDemanda()!=null &&  
								consumoAgua.getConsumoCobradoSemContratoDemanda().intValue()> 0) {
							consumoEsgoto.setConsumoCobradoMes(consumoAgua.getConsumoCobradoSemContratoDemanda());
						} else {
							consumoEsgoto.setConsumoCobradoMes(consumoAgua.getConsumoCobradoMes());
						}
					} else {
						consumoEsgoto.setConsumoCobradoMes(imovel.getConsumoMinimoImovel());
					}
	
					// Caso o consumo a ser cobrado mês seja inferior ao consumo
					// mínimo
					if ((imovel.getConsumoMinEsgoto() != null)
							&& consumoEsgoto.getConsumoCobradoMes().intValue() < imovel.getConsumoMinEsgoto().intValue()) {
	
						// O consumo a ser cobrado mês será o consumo mínimo da
						// ligação de esgoto
						consumoEsgoto.setConsumoCobradoMes(imovel.getConsumoMinEsgoto());
	
						// A anormalidade de consumo será o consumo mínimo fixado de
						// esgoto
						ConsumoAnormalidade consumoAnormalidade = new ConsumoAnormalidade(ConstantesSistema.CONSUMO_MINIMO_FIXADO);
						consumoEsgoto.setConsumoAnormalidade(consumoAnormalidade);
	
						/*
						 * Colocado por Raphael Rossiter em 17/03/2009 - Analista:
						 * Aryed Lins O TIPO do consumo será CONSUMO_MINIMO_FIXADO
						 */
						consumoEsgoto.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_MINIMO_FIX);
	
					}
	
					// verifica se existe situação tipo
					if (imovel.getFaturamentoSituacaoTipo() != null && !imovel.getFaturamentoSituacaoTipo().equals("")) {
						FaturamentoSituacaoTipo situacaoTipo = imovel.getFaturamentoSituacaoTipo();
						if (situacaoTipo.getIndcValidaEsgoto()!=null && situacaoTipo.getIndcValidaEsgoto().equals(ConstantesSistema.SIM)) {
							dadosFaturamentoEspecialNaoMedido(imovel, consumoEsgoto, ConstantesSistema.LIGACAO_POCO);
						}
					}
				}
	
				// [UC0740] 3.3.
				// ***************************************************************
				// Autor: Ivan Sérgio
				// Reponsavel: Aryed
				// Data: 22/12/2010
				// Caso o tipo de consumo não correponda a volume médio
				// de esgoto (média do hidrômetro (3) e média do imóvel (9)),
				// aplica o fator de coleta ao Consumo;
				// ***************************************************************
				if (hidrometroPoco != null && consumoAgua != null) {
					if (consumoEsgoto.getTipoConsumo().intValue() != ConstantesSistema.CONSUMO_TIPO_MEDIA_HIDR
							&& consumoEsgoto.getTipoConsumo().intValue() != ConstantesSistema.CONSUMO_TIPO_MEDIA_IMOV) {
						if (SistemaParametros.getInstancia().getIndicadorPercentualColetaEsgoto().intValue() > 0
								&& SistemaParametros.getInstancia().getIndicadorPercentualColetaEsgoto().equals(ConstantesSistema.NAO)) {
							consumoEsgoto.setConsumoCobradoMes(Util.arredondar(((consumoEsgoto.getConsumoCobradoMes().doubleValue()
									* imovel.getPercentColetaEsgoto().doubleValue()) / 100)));
						}
					}
				} else {
					consumoEsgoto.setConsumoCobradoMes(Util.arredondar(((consumoEsgoto.getConsumoCobradoMes().doubleValue()
							* imovel.getPercentColetaEsgoto().doubleValue()) / 100)));
				}
				
				//~~~~~~~>
			}
	
			/*
			 * 4.Caso o imóvel não esteja mais cortado (Situação da Ligação de Agua
			 * <> 5 do registro tipo 1), ou o consumo de água tenha sido real e
			 * maior que zero (Tipo de Consumo = 1 e o Consumo a ser Cobrado no Mês
			 * maior que zero da tabela ), excluir o débito a cobrar referente a
			 * Tarifa de Cortado Dec. 18.251/94 ( excluir registro do tipo 4 com
			 * Código do débito igual ao código do débito 99).
			 */
			DebitoCobrado debito = getControladorDebitoCobrado().buscarDebitoCobradoPorCodigo(
					ConstantesSistema.TARIFA_CORTADO_DEC_18_251_94,imovel.getId());
	
			// Caso o débito exista, reiniciamos para o estado inicial,
			// caso haja necessidade de recalculo.
			if (debito != null) {
				debito.setIndicadorUso(ConstantesSistema.SIM);
				imovel.setIndcFaturamentoAgua(ConstantesSistema.SIM);
			}
	
			if ((!imovel.getSituacaoLigAgua().equals(ConstantesSistema.CORTADO))
					|| (consumoAgua != null && consumoAgua.getTipoConsumo().intValue() == ConstantesSistema.CONSUMO_TIPO_REAL 
					&& consumoAgua.getConsumoCobradoMes().intValue() > 0)) {
	
				if (debito != null) {
					debito.setIndicadorUso(ConstantesSistema.NAO);
				}
			}
			
			
			//Atualiza o débito da tarifa de cortado
			if (debito != null){
				ControladorBasico.getInstance().atualizar(debito);
			}
	
			if (qtdEconomias > 1) {
				if (consumoEsgoto != null && consumoEsgoto.getConsumoCobradoMes() != null) {
					Integer leituraAnterior = null;
					if (hidrometroPoco != null) {
						leituraAnterior = obterLeituraAnterior(hidrometroPoco);
					}
					getControladorConsumoHistorico().ajustarConsumo(imovel, consumoEsgoto, qtdEconomias, 
							leituraAnterior, ConstantesSistema.LIGACAO_POCO);
				}
			}
	
			if (consumoEsgoto != null
					&& consumoEsgoto.getTipoConsumo()!=null && (consumoEsgoto.getTipoConsumo().intValue() != ConstantesSistema.CONSUMO_TIPO_NAO_MEDIDO 
					|| consumoEsgoto.getTipoConsumo().intValue() != ConstantesSistema.CONSUMO_TIPO_MINIMO_FIX)) {
				if (hidrometroPoco != null) {
					consumoEsgoto.setDiasConsumo(Util.obterQuantidadeDiasEntreDuasDatasPositivo(hidrometroPoco.getDataLeituraAnterior(), 
							Util.dataAtual()));
				}
			}
	
			if (consumoEsgoto != null) {
				consumoEsgoto.setConsumoCobradoMesOriginal(consumoEsgoto.getConsumoCobradoMes());
			}
	
			// Caso nao seja calculado o consumo de agua nem o de poco, porem o
			// imovel possua débitos
			// diminuimos ele dos não lidos
			if (consumoAgua == null && consumoEsgoto == null && 
					getControladorDebitoCobrado().obterValorDebitoTotal(imovel.getId()) > 0) {
				imovel.setIndcImovelCalculado(ConstantesSistema.SIM);
				imovel.setIndcImovelImpresso(ConstantesSistema.NAO);
			}
			
			//Se for imovel condominio e já tiver sido rateado, voltar o 
			//indicador para 2
			if(imovel.isCondominio()
					&& (imovel.getIndcRateioRealizado().equals(ConstantesSistema.SIM))){
				imovel.setIndcRateioRealizado(ConstantesSistema.NAO);
			}
			ControladorBasico.getInstance().atualizar(imovel);
			
			
			//Verifica se o imóvel tem um percentual de esgoto alternativo
			if (consumoEsgoto != null) {
				verificarPercentualEsgotoAlternativo(imovel, consumoEsgoto.getConsumoCobradoMes());
			}
	
			// SB0000
			// 9
			if (hidrometroAgua != null) {
				if (hidrometroAgua.getConsumoMinimoContratadoContratoDemanda() != null) {
					// [SB0014] – Compara Consumo Mínimo Contratado
					int consumoMinimoContratado = hidrometroAgua.getConsumoMinimoContratadoContratoDemanda()
							* getControladorCategoriaSubcategoria().obterQuantidadeEconomiasTotal(imovel.getId());
	
					if (imovel.getSituacaoLigAgua() != null && imovel.getSituacaoLigAgua().equals(ConstantesSistema.LIGADO)
							&& consumoMinimoContratado > consumoAgua.getConsumoCobradoMes()) {
						consumoAgua.setConsumoCobradoSemContratoDemanda(consumoAgua.getConsumoCobradoMes());
						consumoAgua.setConsumoCobradoMes(consumoMinimoContratado);
						consumoAgua.setConsumoAnormalidade(null);
						consumoAgua.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_FIXO_CONTRATO_DEMANDA);
					}
	
				}
			}
	
			//Inseri os consumos
			if(consumoAgua!=null){
				consumoAgua.setMatricula(imovel);
				consumoAgua.setTipoLigacao(ConstantesSistema.LIGACAO_AGUA);
				ConsumoHistorico consumoHistoricoBase = getControladorConsumoHistorico().buscarConsumoHistoricoPorImovelIdLigacaoTipo(imovel.getId(),ConstantesSistema.LIGACAO_AGUA);
				if(consumoHistoricoBase != null && !consumoHistoricoBase.equals("")){
					ControladorBasico.getInstance().remover(consumoHistoricoBase);
				}
				ControladorBasico.getInstance().inserir(consumoAgua);	
							
			}
			if(consumoEsgoto!=null){
				consumoEsgoto.setMatricula(imovel);
				consumoEsgoto.setTipoLigacao(ConstantesSistema.LIGACAO_POCO);
				ConsumoHistorico consumoHistoricoBase = getControladorConsumoHistorico().buscarConsumoHistoricoPorImovelIdLigacaoTipo(imovel.getId(),ConstantesSistema.LIGACAO_POCO);
				if(consumoHistoricoBase != null && !consumoHistoricoBase.equals("")){
					ControladorBasico.getInstance().remover(consumoHistoricoBase);
				}
				ControladorBasico.getInstance().inserir(consumoEsgoto);
			}
			
			// [UC0740] 5. - Calcular Valores de Água/Esgoto
			if (imovel.getIndcCondominio().intValue() != ConstantesSistema.SIM.intValue()
					&& imovel.getMatriculaCondominio()==null) {
				this.calcularValores(imovel, consumoAgua, consumoEsgoto);
			} 
							
			//Seta o indicador de imóvel calculado
			imovel.setIndcImovelCalculado(ConstantesSistema.SIM);
			imovel.setIndcImovelImpresso(ConstantesSistema.NAO);
			ControladorBasico.getInstance().atualizar(imovel);
	
			contaCalculada = true;
	
			// incrementa o valor do imovel selecionado em Sistema Parametros		
			getControladorSistemaParametros().atualizarIdImovelSelecionadoSistemaParametros(imovel.getPosicao());
			
			// Verifica anormalidade em consumo de água se o imovel NAO FOR IMOVEL CONDOMINIO
			//if(imovel.getIndcCondominio().equals(ConstantesSistema.NAO) && imovel.getMatriculaCondominio()==null){
				if(consumoAgua != null && consumoAgua.getConsumoAnormalidade() != null 
						&& consumoAgua.getConsumoAnormalidade().getId().intValue() != ConsumoAnormalidade.CONSUMO_ANORM_LEITURA)
				{
					
					consumoAgua.setConsumoAnormalidade(Fachada.getInstance().pesquisarPorId(
							consumoAgua.getConsumoAnormalidade().getId(), consumoAgua.getConsumoAnormalidade()));
					
					if(
							(
									consumoAgua.getConsumoAnormalidade().getId().intValue() == ConsumoAnormalidade.CONSUMO_FORA_FAIXA 
									&& consumoAgua.getConsumoAnormalidade().getIndicadorFotoAbrigatoria()!=null 
									&& consumoAgua.getConsumoAnormalidade().getIndicadorFotoAbrigatoria().equals(ConstantesSistema.SIM)
							)
						   
						|| 
						   consumoAgua.getConsumoAnormalidade().getId().intValue() != ConsumoAnormalidade.CONSUMO_FORA_FAIXA
					)
					{
						ConsumoAnormalidade consumoAnormAgua = 
								ControladorBasico.getInstance().pesquisarPorId(consumoAgua.getConsumoAnormalidade().getId(), new ConsumoAnormalidade());
						
						if(consumoAnormAgua !=null && consumoAnormAgua.getDescricao()!=null && !consumoAnormAgua.getDescricao().equals("")){
							ControladorAlertaValidarAnormalidadeConsumo controladorAlertaAnormalidade = new ControladorAlertaValidarAnormalidadeConsumo(imovel,imprimir,hidrometroAgua,hidrometroPoco,ConstantesSistema.LIGACAO_AGUA,proximo); 
							
							String msg = consumoAnormAgua.getDescricao()+ ". Deseja confirmar?";
							contaCalculada = controladorAlertaAnormalidade.defineAlerta(ConstantesSistema.ALERTA_PERGUNTA,msg,1);
											
						}else{
							// Verifica anormalidade em consumo de poço
							if(consumoEsgoto != null && consumoEsgoto.getConsumoAnormalidade() != null 
									&& consumoEsgoto.getConsumoAnormalidade().getId().intValue() != ConsumoAnormalidade.CONSUMO_ANORM_LEITURA)
								{
								
								consumoEsgoto.setConsumoAnormalidade(Fachada.getInstance().pesquisarPorId(
										consumoEsgoto.getConsumoAnormalidade().getId(), consumoEsgoto.getConsumoAnormalidade()));
								
								if(
										(
												consumoEsgoto.getConsumoAnormalidade().getId().intValue() == ConsumoAnormalidade.CONSUMO_FORA_FAIXA 
												&& consumoEsgoto.getConsumoAnormalidade().getIndicadorFotoAbrigatoria()!=null 
												&& consumoEsgoto.getConsumoAnormalidade().getIndicadorFotoAbrigatoria().equals(ConstantesSistema.SIM)
										)
									   
									|| 
									   
									consumoEsgoto.getConsumoAnormalidade().getId().intValue() != ConsumoAnormalidade.CONSUMO_FORA_FAIXA)
								{
									ConsumoAnormalidade consumoAnormEsgoto = 
											ControladorBasico.getInstance().pesquisarPorId(consumoEsgoto.getConsumoAnormalidade().getId(), new ConsumoAnormalidade());
											
									if(consumoAnormEsgoto !=null && consumoAnormEsgoto.getDescricao()!=null && !consumoAnormEsgoto.getDescricao().equals("")){
										ControladorAlertaValidarAnormalidadeConsumo controladorAlertaAnormalidade = new ControladorAlertaValidarAnormalidadeConsumo(imovel,imprimir,hidrometroAgua,hidrometroPoco,ConstantesSistema.LIGACAO_ESGOTO,proximo); 
				
										String msg = consumoAnormEsgoto.getDescricao()+ ". Deseja confirmar?";
										contaCalculada = controladorAlertaAnormalidade.defineAlerta(ConstantesSistema.ALERTA_PERGUNTA,msg,1);
														
									}
								}
							}
						}
					}
				}
				
//				if(consumoAgua.getConsumoAnormalidade()!=null)
//				{
//					ControladorAlertaValidarAnormalidadeConsumo controladorAlertaAnormalidade = new ControladorAlertaValidarAnormalidadeConsumo(imovel,imprimir,hidrometroAgua,hidrometroPoco,ConstantesSistema.LIGACAO_AGUA,proximo); 
//					
//					String msg = "Tirar Foto ?";
//					contaCalculada = controladorAlertaAnormalidade.defineAlerta(ConstantesSistema.ALERTA_MENSAGEM,msg,2);
//				}
				
				// Altera o valor do indicador de enviado para que, ao ir ao próximo, o imóvel seja enviado
				imovel.setIndcImovelEnviado(ConstantesSistema.NAO);
				ControladorBasico.getInstance().atualizar(imovel);
				
				//Verifica se foi imóvel revisitado
				ImovelRevisitar imovelRevisitar = getControladorImovelRevisitar().buscarImovelRevisitarPorImovel(imovel.getId());
				if(imovelRevisitar != null){
					imovelRevisitar.setIndicadorRevisitado(ConstantesSistema.SIM);
					ControladorBasico.getInstance().atualizar(imovelRevisitar);
				}
				
			//Setar Sequencial Rota Marcacao 
			if(contaCalculada){
				
				getControladorSequencialRotaMarcacao().gravarSequencialRotaMarcacao(imovel);
				
			}
		}
			
		return contaCalculada;
	}
	

	/**
	 * [SB0000] - Calcular Consumo
	 */
	private void calcularConsumo(ImovelConta imovel, int tipoMedicao, ConsumoHistorico consumo,ConsumoHistorico consumoAgua) throws ControladorException {

		// Apagamos as mensagens, caso tenha sido colocadas anteriormente
		// de Alto, Baixo, ou de Estouro de consumo
		imovel.setMensagemContaAnormalidade1(null);
		imovel.setMensagemContaAnormalidade2(null);
		imovel.setMensagemContaAnormalidade3(null);

		HidrometroInstalado hidrometroInstalado = getControladorHidrometroInstalado().
				buscarHidrometroInstaladoPorImovelTipoMedicao(imovel.getId(), tipoMedicao);
		
		int cMedio;

		// Verificamos se o consumo médio veio do
		// registro tipo 8 ou do imóvel
		if (hidrometroInstalado != null) {
			cMedio = hidrometroInstalado.getConsumoMedio();
		} else {

			if (tipoMedicao == ConstantesSistema.LIGACAO_AGUA) {
				cMedio = imovel.getConsumoMedioLigacaoAgua();
			} else {
				cMedio = imovel.getConsumoMedioEsgoto();
			}

		}

		hidrometroInstalado.setDataLeitura(Util.dataAtual());

		// leitura atual informada
		Integer leitura = hidrometroInstalado.getLeitura();
		hidrometroInstalado.setLeituraAtualFaturamento(leitura);
		hidrometroInstalado.setLeituraAtualFaturamentoHelper(leitura);
		
		// seta a anormalidade informada na anormalidade de leitura faturada
		if (hidrometroInstalado.getAnormalidade()!=null &&  hidrometroInstalado.getAnormalidade().intValue() > 0) {
			LeituraAnormalidade anormalidade = new LeituraAnormalidade();
			anormalidade.setId(hidrometroInstalado.getAnormalidade());
			consumo.setAnormalidadeLeituraFaturada(anormalidade);
		}
		
		// CAERN
//		if(hidrometroInstalado.getAnormalidadeFaturadaCaern() != null){ 
//			LeituraAnormalidade anormalidade = new LeituraAnormalidade();
//			anormalidade.setId(hidrometroInstalado.getAnormalidadeFaturadaCaern());			
//			consumo.setAnormalidadeLeituraFaturada(anormalidade);
//			hidrometroInstalado.setAnormalidadeFaturadaCaern(null);
//		}

		Integer leituraAnterior = this.obterLeituraAnterior(hidrometroInstalado);
		
		// [SB0000] 1. Caso a leitura tenha sido coletada:
		if (leitura != null) {

			// [SB0000] 1.1. Caso a leitura atual informada seja maior que a
			// leitura anterior
			if (leitura.intValue() > leituraAnterior.intValue()) {

				// [SB0001] - Dados para Faturamento para leitura Maior que a
				// Anterior

				// [SB0001] 1.
				consumo.setConsumoMedidoMes(leitura - leituraAnterior);
				consumo.setConsumoCobradoMes(leitura - leituraAnterior);
				consumo.setLeituraAtual(leitura);

				int sitAnt = hidrometroInstalado.getCodigoSituacaoLeituraAnterior();
				if (sitAnt == ConstantesSistema.LEITURA_SITU_REALIZADA || sitAnt == 
						ConstantesSistema.LEITURA_SITU_CONFIRMADA) {
					consumo.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_REAL);
				} else {
					consumo.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_ESTIMADO);
				}

				if (leitura.intValue() < hidrometroInstalado.getLeituraLimiteInferior().intValue() || 
					leitura.intValue() > hidrometroInstalado.getLeituraLimiteSuperior().intValue()) {
					ConsumoAnormalidade consumoAnormalidade = new ConsumoAnormalidade(ConstantesSistema.CONSUMO_ANORM_FORA_DE_FAIXA);
					consumo.setConsumoAnormalidade(consumoAnormalidade);
				} else {
					consumo.setConsumoAnormalidade(null);
				}

				// [SB0000] 1.2. Caso a leitura atual informada seja igual à
				// leitura anterior
			} else if (leitura.intValue() == leituraAnterior.intValue()) {

				// [SB0002] - Dados para Faturamento para leitura Igual à
				// Anterior

				// [SB0002] 1.
				consumo.setConsumoMedidoMes(0);
				consumo.setConsumoCobradoMes(0);
				consumo.setLeituraAtual(leitura);

				int sitAnt = hidrometroInstalado.getCodigoSituacaoLeituraAnterior();
				if (sitAnt == ConstantesSistema.LEITURA_SITU_REALIZADA 
						|| sitAnt == ConstantesSistema.LEITURA_SITU_CONFIRMADA) {
					consumo.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_REAL);
				} else {
					consumo.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_ESTIMADO);
				}

				consumo.setConsumoAnormalidade(null);

				// [SB0002] 2.
				if (leitura.intValue() > 1 && (hidrometroInstalado.getAnormalidade() == null
						|| hidrometroInstalado.getAnormalidade().intValue() == 0)) {

					if (imovel.getIndicadorAbastecimentoAgua().equals(ConstantesSistema.SIM)
							&& imovel.getIndicadorImovelSazonal().equals(ConstantesSistema.NAO)) {

						// 2.1
						if (tipoMedicao == ConstantesSistema.LIGACAO_AGUA) {
							boolean primeiraCondicao = false;
							boolean hidrometroParado = false;

							// Não exista ligação de esgoto
							/*
							 * if (imovel.getIndcFaturamentoEsgoto() != SIM) {
							 * primeiraCondicao = true; }
							 */
							// Caso exista, o valor do consumo mínimo fixado de
							// esgoto seja igual a nulo
							if(SistemaParametros.getInstancia().getCodigoEmpresaFebraban().equals(ConstantesSistema.CODIGO_FEBRABAN_CAERN)){
								LeituraAnormalidade anormalidade = new LeituraAnormalidade();
								
								if(((imovel.getConsumoMinEsgoto() == ConstantesSistema.VOLUME_MINIMO_ESGOTO) || 
										(imovel.getConsumoMinEsgoto() == null)) &&
											((imovel.getTipoPoco() == null) || 
													(imovel.getTipoPoco() == 0))){
									if(hidrometroInstalado.getAnormalidadeFaturadaCaern().equals(ConstantesSistema.SIM)){
										anormalidade.setId(ConstantesSistema.ANORM_HIDROMETRO_PARADO);
										consumo.setAnormalidadeLeituraFaturada(anormalidade);
										hidrometroInstalado.setAnormalidadeFaturadaCaern(null);
									}else{
										anormalidade.setId(ConstantesSistema.ANORM_HIDR_SEM_CONSUMO);
										consumo.setAnormalidadeLeituraFaturada(anormalidade);
										hidrometroInstalado.setAnormalidadeFaturadaCaern(null);									
									}
								}else{
									anormalidade.setId(ConstantesSistema.ANORM_HIDR_SEM_CONSUMO);
									consumo.setAnormalidadeLeituraFaturada(anormalidade);
									hidrometroInstalado.setAnormalidadeFaturadaCaern(null);																		
								}
							}else{
							
								if (imovel.getConsumoMinEsgoto() == null) {
	
									primeiraCondicao = true;
								}
	
								// Não exista poço no imóvel
								if (primeiraCondicao
										&& (imovel.getTipoPoco() == null || imovel.getTipoPoco().intValue() == 0)) {
	
									hidrometroParado = true;
								}
	
								if (hidrometroParado) {
	
									/*
									 * O sistema gera a Anormalidade de Leitura de
									 * Faturamento com o valor correspondente a
									 * hidrômetro parado
									 */
									LeituraAnormalidade anormalidade = new LeituraAnormalidade();
									anormalidade.setId(ConstantesSistema.ANORM_HIDROMETRO_PARADO);
									consumo.setAnormalidadeLeituraFaturada(anormalidade);						
	
								} else {
	
									// 2.1.1
									if (imovel.getTipoPoco() != null
											|| imovel.getConsumoMinEsgoto() != null) {
										
										LeituraAnormalidade anormalidade = new LeituraAnormalidade();
										anormalidade.setId(ConstantesSistema.ANORM_HIDR_SEM_CONSUMO);
										consumo.setAnormalidadeLeituraFaturada(anormalidade);
									}
								}
							}
						
						}

						else if (tipoMedicao == ConstantesSistema.LIGACAO_POCO) {
							// 2.2.1
							if (!imovel.getIndcFaturamentoAgua().equals(ConstantesSistema.SIM) ||
									(getControladorHidrometroInstalado().buscarHidrometroInstaladoPorImovelTipoMedicao(imovel.getId(), ConstantesSistema.LIGACAO_AGUA) != null && 
									(consumoAgua != null && consumoAgua.getConsumoAnormalidade()!=null && consumoAgua.getConsumoAnormalidade().getId()!=null && 
											consumoAgua.getConsumoAnormalidade().getId().equals(ConstantesSistema.CONSUMO_ANORM_BAIXO_CONSUMO)))) {

								LeituraAnormalidade anormalidade = new LeituraAnormalidade();
								anormalidade.setId(ConstantesSistema.ANORM_HIDROMETRO_PARADO);
								
								consumo.setAnormalidadeLeituraFaturada(anormalidade);													
								// 2.2.2
							} else {
								LeituraAnormalidade anormalidade = new LeituraAnormalidade();
								anormalidade.setId(ConstantesSistema.ANORM_HIDR_SEM_CONSUMO);
								consumo.setAnormalidadeLeituraFaturada(anormalidade);
							}
						}
					}
				}

				// [SB0000] 1.3.
			} else {
				boolean voltarFluxoPrincipal = false;

				// mudança no SB0003 para a CAERN
				voltarFluxoPrincipal = controlaSubstituicaoHidrometro(hidrometroInstalado,consumo,leitura, cMedio);

				// [SB0003] 2.2.
				if (!voltarFluxoPrincipal) {
					if (leitura.intValue() <= cMedio) {

						// [SB0003] 2.2.1.
						consumo.setConsumoMedidoMes(leitura);
						consumo.setConsumoCobradoMes(cMedio);
						consumo.setLeituraAtual(leitura);
						consumo.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_MEDIA_HIDR);
						ConsumoAnormalidade consumoAnormalidade = new ConsumoAnormalidade(ConstantesSistema.CONSUMO_ANORM_HIDR_SUBST_N_INFO);
						consumo.setConsumoAnormalidade(consumoAnormalidade);
						

						// [SB0003] 2.2.2.
						voltarFluxoPrincipal = true;
					}
				}

				// [SB0003] 3.
				if (!voltarFluxoPrincipal) {
					consumo.setConsumoMedidoMes(null);
					consumo.setConsumoCobradoMes(cMedio);
					consumo.setLeituraAtual(leitura);
					consumo.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_MEDIA_HIDR);
					if (hidrometroInstalado.getCodigoSituacaoLeituraAnterior().intValue() == ConstantesSistema.LEITURA_SITU_CONFIRMADA
							|| hidrometroInstalado.getCodigoSituacaoLeituraAnterior().intValue() == ConstantesSistema.LEITURA_SITU_REALIZADA) {
						ConsumoAnormalidade consumoAnormalidade = new ConsumoAnormalidade(ConstantesSistema.CONSUMO_ANORM_LEIT_MENOR_ANTE);
						consumo.setConsumoAnormalidade(consumoAnormalidade);
					} else {
						ConsumoAnormalidade consumoAnormalidade = new ConsumoAnormalidade(ConstantesSistema.CONSUMO_ANORM_LEIT_MENOR_PROJ);
						consumo.setConsumoAnormalidade(consumoAnormalidade);
					}

				}
			}
		} else {
			// [SB0000] 3.
			if ((leitura == null )
					&& (consumo.getAnormalidadeLeituraFaturada()==null)) {
				// [SB0005]
				// [SB0005] 1.
				consumo.setConsumoMedidoMes(null);
				consumo.setConsumoCobradoMes(cMedio);
				consumo.setLeituraAtual(leituraAnterior + cMedio);
				consumo.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_MEDIA_HIDR);
				if (hidrometroInstalado.getIndcParalizacaoLeitura().equals(ConstantesSistema.NAO)) {
					ConsumoAnormalidade consumoAnormalidade = new ConsumoAnormalidade(ConstantesSistema.CONSUMO_ANORM_LEITURA_N_INFO);
					consumo.setConsumoAnormalidade(consumoAnormalidade);
				}				
			}
		}

		// [SB0000] 2. Caso a anormalidade de leitura tenha sido informada
		if (consumo.getAnormalidadeLeituraFaturada() !=null) {

			LeituraAnormalidade anormalidade = 
					ControladorBasico.getInstance().pesquisarPorId(
					consumo.getAnormalidadeLeituraFaturada().getId(), new LeituraAnormalidade());

			// [SB0004] - Dados para Faturamento com Anormalidade de leitura
			// 1. leitura Atual Informada não coletada
			if (leitura == null) {
				// 1.1
				consumo.setConsumoMedidoMes(null);
				
				ConsumoAnormalidade consumoAnormalidade = new ConsumoAnormalidade(ConstantesSistema.ANORMALIDADE_LEITURA);
				consumo.setConsumoAnormalidade(consumoAnormalidade);

				// 1.2
				// 1.2.1
				if (anormalidade.getIdConsumoACobrarSemLeitura()!=null) {
					if (anormalidade.getIdConsumoACobrarSemLeitura().intValue() == ConstantesSistema.NAO_OCORRE) {
						consumo.setConsumoCobradoMes(cMedio);
						consumo.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_MEDIA_HIDR);
					}
					// 1.2.2
					else if (anormalidade.getIdConsumoACobrarSemLeitura().intValue() == ConstantesSistema.MINIMO) {
						consumo.setConsumoCobradoMes(imovel.getConsumoMinimoImovel());
						consumo.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_SEM);
					}
					// 1.2.3
					else if (anormalidade.getIdConsumoACobrarSemLeitura().intValue() == ConstantesSistema.MEDIA) {
						consumo.setConsumoCobradoMes(cMedio);
						consumo.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_MEDIA_HIDR);
					}
				}
	
				// 1.3
				// 1.3.1
				if (anormalidade.getIdLeituraAnormLeituraSemLeitura()!=null) {
					if (anormalidade.getIdLeituraAnormLeituraSemLeitura().intValue() == ConstantesSistema.ANTERIOR_MAIS_A_MEDIA) {
						consumo.setLeituraAtual(obterLeituraAnterior(hidrometroInstalado) + cMedio);
					}
					// 1.3.2
					else if (anormalidade.getIdLeituraAnormLeituraSemLeitura().intValue() == ConstantesSistema.ANTERIOR) {
						consumo.setLeituraAtual(obterLeituraAnterior(hidrometroInstalado));
					}
					// 1.3.3
					else if (anormalidade.getIdLeituraAnormLeituraSemLeitura().intValue() == ConstantesSistema.ANTERIOR_MAIS_O_CONSUMO) {
						consumo.setLeituraAtual(obterLeituraAnterior(hidrometroInstalado)
								+ consumo.getConsumoCobradoMes());
					}
				}

				// 1.1.4. O sistema deverá aplicar o fator definido sem leitura
				// no sistema ao consumo apurado
				// de acordo com o definido na anormalidade especificada
				// (LTAN_NNFATORSEMLEITURA da tabela LEITURA_ANORMALIDADE
				// com LTAN_ID = anormalidade informada).
				if (consumo.getConsumoCobradoMes() != null && consumo.getConsumoCobradoMes().intValue() != 0 
						&& anormalidade.getNumeroFatorSemLeitura() != null) {

					double consumoFaturadoMesSemLeitura = consumo.getConsumoCobradoMes();

					consumoFaturadoMesSemLeitura = anormalidade.getNumeroFatorSemLeitura().doubleValue()
							* consumoFaturadoMesSemLeitura;

					consumo.setConsumoCobradoMes((int) consumoFaturadoMesSemLeitura);

				}
			}
			// 2.Leitura Atual Informada
			else {
				// 2.2
				// 2.2.1
				if (anormalidade!=null && anormalidade.getIdConsumoACobrarComLeitura()!=null) {
					if (anormalidade.getIdConsumoACobrarComLeitura().equals(ConstantesSistema.NAO_OCORRE)) {
						consumo.setConsumoCobradoMes(cMedio);
						consumo.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_MEDIA_HIDR);
					}
					// 2.2.2
					else if (anormalidade.getIdConsumoACobrarComLeitura().equals(ConstantesSistema.MINIMO)) {
						consumo.setConsumoCobradoMes(0);
						consumo.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_SEM);
					}
					// 2.2.3
					else if (anormalidade.getIdConsumoACobrarComLeitura().equals(ConstantesSistema.MEDIA)) {
						consumo.setConsumoCobradoMes(cMedio);
						consumo.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_MEDIA_HIDR);
	
					}
					// 2.2.4
					else if (anormalidade.getIdConsumoACobrarComLeitura().equals(ConstantesSistema.NORMAL)) {
						// Fazer nada já calculado
					}
					// 2.2.5
					else if (anormalidade.getIdConsumoACobrarComLeitura().equals(ConstantesSistema.MAIOR_ENTRE_O_CONSUMO_MEDIO)) {
						if (cMedio > consumo.getConsumoCobradoMes()) {
							consumo.setConsumoCobradoMes(cMedio);
							consumo.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_MEDIA_HIDR);
						}
					}
					// 2.2.6
					else if (anormalidade.getIdConsumoACobrarComLeitura().equals(ConstantesSistema.MENOR_ENTRE_O_CONSUMO_MEDIO)) {
						if (cMedio < consumo.getConsumoCobradoMes()) {
							consumo.setConsumoCobradoMes(cMedio);
							consumo.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_MEDIA_HIDR);
						}
	
					}
					// 2.3.1
					if (anormalidade.getIdLeituraAnormLeituraComLeitura().equals(ConstantesSistema.ANTERIOR_MAIS_A_MEDIA)) {
						consumo.setLeituraAtual(obterLeituraAnterior(hidrometroInstalado) + cMedio);
					}
					// 2.3.2
					else if (anormalidade.getIdLeituraAnormLeituraComLeitura().equals(ConstantesSistema.ANTERIOR)) {
						consumo.setLeituraAtual(obterLeituraAnterior(hidrometroInstalado));
					}
					// 2.3.3
					else if (anormalidade.getIdLeituraAnormLeituraComLeitura().equals(ConstantesSistema.ANTERIOR_MAIS_O_CONSUMO)) {
						consumo.setLeituraAtual(obterLeituraAnterior(hidrometroInstalado)
								+ consumo.getConsumoCobradoMes());
					}
					// 2.3.4
					else if (anormalidade.getIdLeituraAnormLeituraComLeitura().equals(ConstantesSistema.INFORMADO)) {
						// Fazer nada Já calculado anteriormente
					}
				}
				
				// 1.2.3. O sistema deverá aplicar o fator definido com leitura
				// no sistema ao consumo apurado de acordo com o
				// definido na anormalidade especificada (LTAN_NNFATORCOMLEITURA
				// da tabela LEITURA_ANORMALIDADE
				// com LTAN_ID = anormalidade informada).
				if ((consumo.getConsumoCobradoMes() != null && anormalidade != null && (anormalidade.getNumeroFatorComLeitura() != null))) {

					double consumoFaturadoMesComLeitura = consumo.getConsumoCobradoMes();

					consumoFaturadoMesComLeitura = anormalidade.getNumeroFatorComLeitura().doubleValue()
							* consumoFaturadoMesComLeitura;

					consumo.setConsumoCobradoMes((int) consumoFaturadoMesComLeitura);

				}

			}

		}

		// verifica se existe situação tipo
		if (imovel.getFaturamentoSituacaoTipo() != null && !imovel.getFaturamentoSituacaoTipo().equals("")) {
			FaturamentoSituacaoTipo situacaoTipo = imovel.getFaturamentoSituacaoTipo();
			// se existe e o tipo de ligação é agua,
			// determina o consumo para água
			if (tipoMedicao == ConstantesSistema.LIGACAO_AGUA) {
				if (situacaoTipo.getIndcValidaAgua()!=null &&  situacaoTipo.getIndcValidaAgua().equals(ConstantesSistema.SIM)) {
					dadosFaturamentoEspecialMedido(imovel, consumo, tipoMedicao);
				}
			} else {
				// determina o consumo para esgoto
				if (tipoMedicao == ConstantesSistema.LIGACAO_POCO) {
					if (situacaoTipo.getIndcValidaEsgoto()!=null && situacaoTipo.getIndcValidaEsgoto().equals(ConstantesSistema.SIM)) {
						dadosFaturamentoEspecialMedido(imovel, consumo, tipoMedicao);
					}
				}
			}

		}

		//RM 5981 - Caso o indicador de desconsiderar EC e AC seja igual a 1, então desconsiderar EC e AC.
		if (imovel.getFaturamentoSituacaoTipo()==null || 
				imovel.getFaturamentoSituacaoTipo().getIndicadorDesconsiderarEstouroAltoConsumo()==null ||
				imovel.getFaturamentoSituacaoTipo().getIndicadorDesconsiderarEstouroAltoConsumo().intValue()==2) {		
			

			//[UC1305] - Carregar Arquivo Texto Impressão Simultânea de Contas / RN20140912125
			boolean novaLigacao = verificarNovaLigacao(imovel, hidrometroInstalado);
			
			if (tipoMedicao != ConstantesSistema.LIGACAO_AGUA || !novaLigacao) {
				
				// [SB0000] 4.
				// [SB0006] - Verificar Estouro de Consumo
				// [SB0006] 1.
				/*
				 * Proposta: RN2013015726
				 * Analista: Claudio Lira 
				 * Autor: Davi Menezes
				 * Data: 18/04/2013
				 * 
				 * ==========================================================================================================
				 * 
				 * [SB0007] Caso o imóvel não seja um micromedidor ou macromedidor de imóvel condominio 
				 * e o indicador de regra de condominio de consumo anormalidade seja igual a 2
				 */
				if (!verificarEstouroConsumo(imovel, consumo, hidrometroInstalado, tipoMedicao)) {
						
						ConsumoAnormalidade consumoAnormalidade = new ConsumoAnormalidade();
						consumoAnormalidade = Fachada.getInstance().pesquisarPorId(
								ConstantesSistema.CONSUMO_ANORM_ALTO_CONSUMO, consumoAnormalidade);
					
						if(!imovel.isCondominio() || (consumoAnormalidade.getIndicadorRegraImovelCondominio() != null 
							&& consumoAnormalidade.getIndicadorRegraImovelCondominio().equals(ConstantesSistema.NAO))){
							
							// [SB0000] 5. Caso não tenha estouro de consumo
							// [SB0007] - Verificar Alto Consumo
							verificarAltoConsumo(imovel, consumo, hidrometroInstalado, tipoMedicao);
						}
				}
			}
		}

		// [SB0000] 6. Caso o tipo de poço corresponda à sem poço (valor zero),
		// a leitura tenha sido coletada, e a anormalidade não tenha sido
		// informada
		int sitLigAgua = imovel.getSituacaoLigAgua();

		if (sitLigAgua != ConstantesSistema.CORTADO && (imovel.getTipoPoco() == null || imovel.getTipoPoco().intValue() == 0)
				&& leitura != null && consumo.getAnormalidadeLeituraFaturada() == null) {
			
			/*
			 * Proposta: RN2013015726
			 * Analista: Claudio Lira 
			 * Autor: Davi Menezes
			 * Data: 18/04/2013
			 * 
			 * ==========================================================================================================
			 * 
			 * [SB0007] Caso o imóvel não seja um micromedidor ou macromedidor de imóvel condominio 
			 * e o indicador de regra de condominio de consumo anormalidade seja igual a 2
			 */
			ConsumoAnormalidade consumoAnormalidade = new ConsumoAnormalidade();
			consumoAnormalidade = Fachada.getInstance().pesquisarPorId(
					ConstantesSistema.CONSUMO_ANORM_BAIXO_CONSUMO, consumoAnormalidade);
			
			if(!imovel.isCondominio() || (consumoAnormalidade.getIndicadorRegraImovelCondominio() != null 
					&& consumoAnormalidade.getIndicadorRegraImovelCondominio().equals(ConstantesSistema.NAO))){
					verificarBaixoConsumo(imovel, consumo, hidrometroInstalado, tipoMedicao);
			}

		}

		// 9. Caso a anormalidade de consumo esteja deferente de nulo
		// (CSAN_ID da tabela de CONSUMO_HISTORICO que esta sendo processado),
		// então [SB0024 - Recuperar Dados da Tabela de Consumo Anormalidade
		// Ação].
		if (consumo.getConsumoAnormalidade() !=null && consumo.getConsumoAnormalidade().getId().intValue() > 0) {
			this.recuperarDadosConsumoAnormalidadeAcao(imovel, consumo, hidrometroInstalado, tipoMedicao, consumo.getConsumoAnormalidade());
		}

		// Caso esteja indicado o ajuste mensal do consumo
		if (SistemaParametros.getInstancia().getIndicadorAjusteConsumo() != null && SistemaParametros.getInstancia().getIndicadorAjusteConsumo().equals(ConstantesSistema.SIM)) {
			// [SF0017] - Ajuste Mensal do Consumo
			getControladorConsumoHistorico().ajusteMensalConsumo(imovel, 
						hidrometroInstalado, tipoMedicao, consumo);			

		}

		//Atualiza o hidrometro
		if (hidrometroInstalado!=null){
			ControladorBasico.getInstance().atualizar(hidrometroInstalado);
		}
		
		//Atualiza objeto
		ControladorBasico.getInstance().atualizar(imovel);
	
			
		
		// System.out.println("********** " + imovel.getMatricula()
		// + "********** ");
		// System.out.println("Consumo Agua: "
		// + (consumoAgua != null ? consumoAgua.getConsumoCobradoMes()
		// + "" : "Nulo"));
		// System.out.println("Consumo Esgoto: "
		// + (consumoEsgoto != null ? consumoEsgoto.getConsumoCobradoMes()
		// + "" : "Nulo"));
		// System.out.println("*************************** ");

	}
	

	public void calcularValores(ImovelConta imovel, ConsumoHistorico consumoAgua, ConsumoHistorico consumoEsgoto) throws ControladorException {

		ControladorImovel controladorImoveis = getControladorImovel();

		boolean imovelComDebitoTipoCortado = false;

		DebitoCobrado debito = getControladorDebitoCobrado().buscarDebitoCobradoPorCodigo(
				ConstantesSistema.TARIFA_CORTADO_DEC_18_251_94,imovel.getId());

		if (debito != null && debito.getIndicadorUso().equals(ConstantesSistema.SIM)) {
			imovelComDebitoTipoCortado = true;
		}

		// [UC0743] 2.
		if ((imovel.getIndcFaturamentoAgua().equals(ConstantesSistema.SIM) && 
				imovel.getIndicadorParalizarFaturamentoAgua().equals(ConstantesSistema.NAO) && !imovelComDebitoTipoCortado)
				|| (imovel.isCondominio() && 
						SistemaParametros.getInstancia().getIndicadorRateioAreaComumImovelNaoFat().equals(ConstantesSistema.SIM))
						&& consumoAgua!=null) {
			controladorImoveis.calcularValores(imovel, consumoAgua, ConstantesSistema.LIGACAO_AGUA);
		} else {
			// Agora o indicador de faturamento
			// pode ser alterado dinamicamente.
			// Sendo assim, zeramos os valores calculados
			// de agua e de esgoto caso seja necessário
			getControladorContaCategoria().removerImovelContaCategoria(imovel.getId(),ConstantesSistema.LIGACAO_AGUA);
		}

		// [UC0743] 3.
		if ((imovel.getIndcFaturamentoEsgoto().equals(ConstantesSistema.SIM) && 
				imovel.getIndicadorParalizarFaturamentoEsgoto().equals(ConstantesSistema.NAO))
				|| (imovel.isCondominio() && SistemaParametros.getInstancia().
						getIndicadorRateioAreaComumImovelNaoFat().equals(ConstantesSistema.SIM))
						&& consumoEsgoto!=null) {
			controladorImoveis.calcularValores(imovel, consumoEsgoto, ConstantesSistema.LIGACAO_POCO);
		}

		// Salvamos que o imovel ja foi calculado
		// imovel.setIndcImovelCalculado( Constantes.SIM );

		// Se o imóvel estiver com a situação referente a nitrato
		if (imovel.getFaturamentoSituacaoTipo() != null && !imovel.getFaturamentoSituacaoTipo().equals("")) {
			if (imovel.getFaturamentoSituacaoTipo().getId().intValue() == ConstantesSistema.NITRATO) {
				// calcula 50% do valor da água
				double valorCreditoNitrato = Util.truncar(getControladorContaCategoria().
						obterValorTotal(imovel.getId(), ConstantesSistema.LIGACAO_AGUA).doubleValue() / 2, 2);
								
				// atualiza o crédito referente a Nitrato com o valor
				// calculado do crédito
				CreditoRealizado creditoNitrato = getControladorCreditoRealizado().buscarCreditoRealizadoPorDescricao(ConstantesSistema.DESCRICAO_CERDITO_NITRATO,imovel.getId());						
				if (creditoNitrato!=null){
					creditoNitrato.setValor(new BigDecimal(valorCreditoNitrato));
					ControladorBasico.getInstance().atualizar(creditoNitrato);
				}
			}
		}

		//CONTRATO DE DEMANA 
		if (imovel.getSituacaoLigAgua() != null && imovel.getSituacaoLigAgua().equals(ConstantesSistema.LIGADO)) {

			HidrometroInstalado hidrometroAgua = getControladorHidrometroInstalado().
					buscarHidrometroInstaladoPorImovelTipoMedicao(imovel.getId(), ConstantesSistema.LIGACAO_AGUA);
			
			if (hidrometroAgua != null && hidrometroAgua.getPercentualDescontoContratoDemanda() != null
					&& hidrometroAgua.getPercentualDescontoContratoDemanda().intValue() > 0) {

				// Crédito do contrato de demanda
				double creditoContratoDemandaAgua = Util.truncar(getControladorContaCategoria().
						obterValorTotal(imovel.getId(), ConstantesSistema.LIGACAO_AGUA).doubleValue()
						* (hidrometroAgua.getPercentualDescontoContratoDemanda() / 100d), 2);

				double creditoContratoDemandaEsgoto = Util.truncar(getControladorContaCategoria().
						obterValorTotal(imovel.getId(), ConstantesSistema.LIGACAO_POCO).doubleValue()
						* (hidrometroAgua.getPercentualDescontoContratoDemanda() / 100d), 2);
				
				
				CreditoRealizado creditoContratoDemanda = getControladorCreditoRealizado().buscarCreditoRealizadoPorDescricao(ConstantesSistema.DESCRICAO_CREDITO_CONTRATO_DEMANDA,imovel.getId());						
				if (creditoContratoDemanda!=null){
					creditoContratoDemanda.setValor(new BigDecimal(creditoContratoDemandaAgua + creditoContratoDemandaEsgoto));
					ControladorBasico.getInstance().atualizar(creditoContratoDemanda);
				}
			}
		}
	}


	/**
	 * [UC0970] Efetuar Rateio de Consumo no Dispositivo Movel Metodo
	 * responsavel em efeturar a divisão da diferença entre o consumo coletado
	 * no hidrometro macro e a soma dos hidrometros micro
	 * 
	 * [SB0002] Determinar Rateio de Agua
	 * 
	 * @date 26/11/2009
	 * @author Bruno Barros
	 * 
	 * @return Object[] 0, Boolean: Caso algum error tenha ocorrido, informamos
	 *         1, Boolean: Ainda existem imoveis a serem percorridos; 2,
	 *         Integer: Id do imovel condominio que ainda precisa ser percorrido
	 * 
	 * @param helper
	 *            Helper com os dados necessários para execução
	 */
	public ControladorRateioImovelCondominio efetuarRateio(ImovelConta imovelMacro, boolean completo) throws ControladorException {
		
		RateioConsumoHelper helper = atualizarResumoEfetuarRateio(imovelMacro);
		
		// veficica se existe rateio para área comum
		boolean existeImovelVinculadoRateioAreaComumAgua = false;
		boolean existeImovelVinculadoRateioAreaComumEsgoto = false;


		// Calculamos o valor do consumo a ser rateado na ligação de agua
		if (imovelMacro.getIndcFaturamentoAgua().equals(ConstantesSistema.SIM)) {
			helper.setConsumoASerRateadoAgua(calcularConsumoAguaASerRateado(imovelMacro, helper));
		}

		// Calculamos o valor do consumo a ser rateado na ligação de esgoto
		if (imovelMacro.getIndcFaturamentoEsgoto().equals(ConstantesSistema.SIM)) {
			helper.setConsumoASerRateadoEsgoto(calcularConsumoEsgotoASerRateado(imovelMacro, helper));
		}

		HidrometroInstalado hidrometroAgua = getControladorHidrometroInstalado().
				buscarHidrometroInstaladoPorImovelTipoMedicao(imovelMacro.getId(), ConstantesSistema.LIGACAO_AGUA);
		
		if (hidrometroAgua != null && hidrometroAgua.getTipoRateio()!=null
				&& hidrometroAgua.getTipoRateio().intValue() == ConstantesSistema.TIPO_RATEIO_AREA_COMUM) {
			
			ImovelConta imovelAreaComum = getControladorImovelConta().obterImovelAreaComum(imovelMacro.getId());		
			if (imovelAreaComum != null && !imovelAreaComum.equals("")) {
				if (helper.getConsumoASerRateadoAgua() > 0){
					existeImovelVinculadoRateioAreaComumAgua = true;
				}
				if (helper.getConsumoASerRateadoEsgoto() > 0){
					existeImovelVinculadoRateioAreaComumEsgoto = true;
				}
			}
			
		}
		
		if (imovelMacro.getIndcFaturamentoAgua().equals(ConstantesSistema.SIM)) {
			//Atualizamos o consumo do hidrometro macro com os valores de agua
			ConsumoHistorico consumoMacro = getControladorConsumoHistorico().
					buscarConsumoHistoricoPorImovelIdLigacaoTipo(imovelMacro.getId(), ConstantesSistema.LIGACAO_AGUA);
			
			// Alteração para verificação de tipo área comum
			if (existeImovelVinculadoRateioAreaComumAgua) {
				ImovelConta imovelAreaComum = getControladorImovelConta().obterImovelAreaComum(imovelMacro.getId());
				
				ConsumoHistorico consumoAreaComum = getControladorConsumoHistorico().
						buscarConsumoHistoricoPorImovelIdLigacaoTipo(imovelAreaComum.getId(), ConstantesSistema.LIGACAO_AGUA);				
				consumoAreaComum.setConsumoRateio(helper.getConsumoASerRateadoAgua());
				ControladorBasico.getInstance().atualizar(consumoAreaComum);
			
				consumoMacro.setConsumoCobradoMesImoveisMicro(helper.getConsumoLigacaoAguaTotal());
				
			} else {
				consumoMacro.setConsumoRateio(helper.getConsumoASerRateadoAgua());
				consumoMacro.setConsumoCobradoMesImoveisMicro(helper.getConsumoLigacaoAguaTotal());
			}
			
			ControladorBasico.getInstance().atualizar(consumoMacro);
		}

		// Calculamos o valor do consumo de agua por economia, tanto do medido
		// quanto do não medido
		int consumoAguaASerRateadoPorEconomiaNaoMedido = 0;

		if (helper.getQuantidadeEconomiasAguaNaoMedidas() > 0) {
			if (existeImovelVinculadoRateioAreaComumAgua) {
				consumoAguaASerRateadoPorEconomiaNaoMedido = helper.getConsumoASerRateadoAgua();
			} else {
				// para o caso de area comum
				consumoAguaASerRateadoPorEconomiaNaoMedido = helper.getConsumoASerRateadoAgua()
						/ helper.getQuantidadeEconomiasAguaNaoMedidas();

			}

		}

		/*
		 * Proposta: PE2012016602
		 * Analista: Sávio Luiz 
		 * Autor: Thúlio Araújo
		 * Data: 10/02/2012
		 * 
		 * ==========================================================================================================
		 * 
		 * 3.5. Caso consumo de rateio água seja menor que zero e o indicador de rateio negativo
		 * do imóvel macro seja igual a NÃO Indicador para imóvel macro aceitar rateio negativo = 2
		 * do Registro Tipo 1), então consumo de rateio água = 0 (Zero).
		 */
		if (consumoAguaASerRateadoPorEconomiaNaoMedido < 0 && imovelMacro.getIndicadorImovelRateioNegativo().equals(ConstantesSistema.NAO)){
			consumoAguaASerRateadoPorEconomiaNaoMedido = 0;
		
		/*
		 * 3.6. Caso Contrário,  Caso o consumo de rateio água seja menor que zero e seja menor 
		 * que o negativo do decremento máximo de consumo por economia (Decremento Máximo de Consumo 
		 * por Economia), o consumo de água a ser rateado por economia = decremento máximo de 
		 * consumo por economia * (-1);
		 */
		} else if (consumoAguaASerRateadoPorEconomiaNaoMedido < 0 && consumoAguaASerRateadoPorEconomiaNaoMedido < 
				SistemaParametros.getInstancia().getDecrementoMaximoConsumoEconomia().intValue() * -1) {

			consumoAguaASerRateadoPorEconomiaNaoMedido = SistemaParametros.getInstancia().
						getDecrementoMaximoConsumoEconomia().intValue()	* -1;
			

		}
		
		/* =========================================================================================================== */
		int consumoAguaASerRateadoPorEconomiaMedido = 0;

		if (helper.getQuantidadeEconomiasAguaMedidas() > 0) {

			if (existeImovelVinculadoRateioAreaComumAgua) {
				consumoAguaASerRateadoPorEconomiaMedido = helper.getConsumoASerRateadoAgua();
			} else {
				// para o caso de area comum
				consumoAguaASerRateadoPorEconomiaMedido = helper.getConsumoASerRateadoAgua()
						/ helper.getQuantidadeEconomiasAguaMedidas();

			}
		}
		
		/*
		 * Proposta: PE2012016602
		 * Analista: Sávio Luiz 
		 * Autor: Thúlio Araújo
		 * Data: 10/02/2012
		 * 
		 * ==========================================================================================================
		 * 
		 * 3. Caso consumo de rateio água seja menor que zero e o indicador de rateio negativo
		 * do imóvel macro seja igual a NÃO Indicador para imóvel macro aceitar rateio negativo = 2
		 * do Registro Tipo 1), então consumo de rateio água = 0 (Zero).
		 */
		if (consumoAguaASerRateadoPorEconomiaMedido < 0 && imovelMacro.getIndicadorImovelRateioNegativo().equals(ConstantesSistema.NAO)){
			consumoAguaASerRateadoPorEconomiaMedido = 0;
		
		/*
		 * 4. Caso Contrário,  Caso o consumo de rateio água seja menor que zero e seja menor 
		 * que o negativo do decremento máximo de consumo por economia (Decremento Máximo de Consumo 
		 * por Economia), o consumo de água a ser rateado por economia = decremento máximo de 
		 * consumo por economia * (-1);
		 */
		} else if (consumoAguaASerRateadoPorEconomiaMedido < 0 && consumoAguaASerRateadoPorEconomiaMedido < 
				SistemaParametros.getInstancia().getDecrementoMaximoConsumoEconomia() * -1) {

			consumoAguaASerRateadoPorEconomiaMedido = SistemaParametros.getInstancia()
						.getDecrementoMaximoConsumoEconomia() * -1;			
		}
		
		/* =========================================================================================================== */ 
		
		// Caso o consumo de água a ser rateado por economia MEDIDO seja maior
		// que o incremento máximo de consumo por
		// economia (Incremento Máximo de Consumo por Economia), o consumo de
		// água a ser rateado por economia =
		// incremento máximo de consumo por economia;
		if (consumoAguaASerRateadoPorEconomiaMedido > SistemaParametros.getInstancia().getIncrementoMaximoConsumoEconomia().intValue()) {

			if (!existeImovelVinculadoRateioAreaComumAgua) {
				consumoAguaASerRateadoPorEconomiaMedido = SistemaParametros.getInstancia().getIncrementoMaximoConsumoEconomia().intValue();
			}
		}

		// caso o tipo de rateio seja igual a área comum
		// limpa o consumo a ser rateado por imóvel micro
		if (existeImovelVinculadoRateioAreaComumAgua) {
			consumoAguaASerRateadoPorEconomiaMedido = 0;
			consumoAguaASerRateadoPorEconomiaNaoMedido = 0;
		}

		if (helper.getConsumoASerRateadoEsgoto() < 0 && imovelMacro.getIndicadorImovelRateioNegativo().equals(ConstantesSistema.SIM)){
			existeImovelVinculadoRateioAreaComumEsgoto = false;
		}
		
		// No imóvel MACRO, o consumo rateio esgoto do imóvel (Consumo Rateio
		// Esgoto) =
		// Consumo de esgoto a ser rateado e o consumo de esgoto dos imóveis
		// MICRO (Consumo Esgoto Imóveis MICRO) =
		// soma do consumo de esgoto dos imóveis MICRO;
		if (imovelMacro.getIndcFaturamentoEsgoto().equals(ConstantesSistema.SIM)) {
			
			ConsumoHistorico consumoEsgotoMacro = getControladorConsumoHistorico().
					buscarConsumoHistoricoPorImovelIdLigacaoTipo(imovelMacro.getId(), ConstantesSistema.LIGACAO_ESGOTO);
			
			// Alteração para verificação de tipo área comum
			if (existeImovelVinculadoRateioAreaComumEsgoto) {

				ImovelConta imovelAreaComum = getControladorImovelConta().obterImovelAreaComum(imovelMacro.getId());
				
				ConsumoHistorico consumoEsgotoAreaComum = getControladorConsumoHistorico().
						buscarConsumoHistoricoPorImovelIdLigacaoTipo(imovelAreaComum.getId(), ConstantesSistema.LIGACAO_ESGOTO);
				
				consumoEsgotoAreaComum.setConsumoRateio(helper.getConsumoASerRateadoEsgoto());
				ControladorBasico.getInstance().atualizar(consumoEsgotoAreaComum);
								
				consumoEsgotoMacro.setConsumoCobradoMesImoveisMicro(helper.getConsumoLigacaoEsgotoTotal());				
			} else {
				consumoEsgotoMacro.setConsumoRateio(helper.getConsumoASerRateadoEsgoto());
				consumoEsgotoMacro.setConsumoCobradoMesImoveisMicro(helper.getConsumoLigacaoEsgotoTotal());
			}
			
			ControladorBasico.getInstance().atualizar(consumoEsgotoMacro);

		}

		int consumoEsgotoASerRateadoPorEconomiaNaoMedido = 0;

		if (helper.getQuantidadeEconomiasEsgotoNaoMedidas() > 0) {

			if (existeImovelVinculadoRateioAreaComumEsgoto) {
				consumoEsgotoASerRateadoPorEconomiaNaoMedido = helper.getConsumoASerRateadoEsgoto();
			} else {
				// para o caso de area comum
				consumoEsgotoASerRateadoPorEconomiaNaoMedido = helper.getConsumoASerRateadoEsgoto()
						/ helper.getQuantidadeEconomiasEsgotoNaoMedidas();

			}
		}

		if (helper.getConsumoASerRateadoEsgoto() < 0 && SistemaParametros.getInstancia().getIndicadorDesconsiderarRateioEsgoto().intValue() > 0 && 
				SistemaParametros.getInstancia().getIndicadorDesconsiderarRateioEsgoto().equals(ConstantesSistema.SIM)){
			consumoEsgotoASerRateadoPorEconomiaNaoMedido = 0;
		}
		
		/*
		 * Proposta: PE2012016602
		 * Analista: Sávio Luiz 
		 * Autor: Thúlio Araújo
		 * Data: 10/02/2012
		 * 
		 * ==========================================================================================================
		 * 
		 * 3. Caso consumo de rateio esgoto seja menor que zero e o indicador de rateio negativo
		 * do imóvel macro seja igual a NÃO Indicador para imóvel macro aceitar rateio negativo = 2
		 * do Registro Tipo 1), então consumo de rateio esgoto = 0 (Zero).
		 */
		if (consumoEsgotoASerRateadoPorEconomiaNaoMedido < 0 && imovelMacro.getIndicadorImovelRateioNegativo().equals(
				ConstantesSistema.NAO)){
			consumoEsgotoASerRateadoPorEconomiaNaoMedido = 0;
		
		/*
		 * 4. Caso Contrário,  Caso o consumo de rateio esgoto seja menor que zero e seja menor 
		 * que o negativo do decremento máximo de consumo por economia (Decremento Máximo de Consumo 
		 * por Economia), o consumo de esgoto a ser rateado por economia = decremento máximo de 
		 * consumo por economia * (-1);
		 */
		} else if (consumoEsgotoASerRateadoPorEconomiaNaoMedido < 0 && consumoEsgotoASerRateadoPorEconomiaNaoMedido < 
				SistemaParametros.getInstancia().getDecrementoMaximoConsumoEconomia() * -1) {

			consumoEsgotoASerRateadoPorEconomiaNaoMedido = SistemaParametros.getInstancia().getDecrementoMaximoConsumoEconomia()
					.intValue()	* -1;			

		}
		
		int consumoEsgotoASerRateadoPorEconomiaMedido = 0;

		if (helper.getQuantidadeEconomiasEsgotoMedidas() > 0) {

			if (existeImovelVinculadoRateioAreaComumEsgoto) {

				consumoEsgotoASerRateadoPorEconomiaMedido = helper.getConsumoASerRateadoEsgoto();

			} else {
				// para o caso de area comum
				consumoEsgotoASerRateadoPorEconomiaMedido = helper.getConsumoASerRateadoEsgoto()
						/ helper.getQuantidadeEconomiasEsgotoMedidas();

			}
		}

		if (consumoEsgotoASerRateadoPorEconomiaMedido <= 0) {
			consumoEsgotoASerRateadoPorEconomiaMedido = 0;
		} else if (SistemaParametros.getInstancia().getIndicadorDesconsiderarRateioEsgoto().intValue() > 0 && 
				SistemaParametros.getInstancia().getIndicadorDesconsiderarRateioEsgoto().equals(ConstantesSistema.SIM)){
			consumoEsgotoASerRateadoPorEconomiaMedido = 0;
		}

		if (consumoEsgotoASerRateadoPorEconomiaMedido > SistemaParametros.getInstancia().getIncrementoMaximoConsumoEconomia().intValue()) {

			if (!existeImovelVinculadoRateioAreaComumEsgoto) {

				consumoEsgotoASerRateadoPorEconomiaMedido = SistemaParametros.getInstancia().getIncrementoMaximoConsumoEconomia();

			}

		}

		// caso o tipo de rateio seja igual a área comum
		// limpa o consumo a ser rateado por imóvel micro
		if (existeImovelVinculadoRateioAreaComumEsgoto) {
			consumoEsgotoASerRateadoPorEconomiaMedido = 0;
			consumoEsgotoASerRateadoPorEconomiaNaoMedido = 0;
		}

		//Calculamos o hidrometro macro
		ConsumoHistorico consumoAguaMacro = getControladorConsumoHistorico().
				buscarConsumoHistoricoPorImovelIdLigacaoTipo(imovelMacro.getId(), ConstantesSistema.LIGACAO_AGUA);
		ConsumoHistorico consumoEsgotoMacro = getControladorConsumoHistorico().
				buscarConsumoHistoricoPorImovelIdLigacaoTipo(imovelMacro.getId(), ConstantesSistema.LIGACAO_ESGOTO);
		calcularValores(imovelMacro, consumoAguaMacro, consumoEsgotoMacro);
		
				
		ControladorRateioImovelCondominio controladoreAlertaRateio = 
					new ControladorRateioImovelCondominio(imovelMacro,
						existeImovelVinculadoRateioAreaComumAgua,
						existeImovelVinculadoRateioAreaComumEsgoto,
						consumoAguaASerRateadoPorEconomiaMedido,
						consumoAguaASerRateadoPorEconomiaNaoMedido,
						consumoEsgotoASerRateadoPorEconomiaMedido,
						consumoEsgotoASerRateadoPorEconomiaNaoMedido,
						helper, completo);
		
		
		return controladoreAlertaRateio;
		
	}

	
	private int calcularConsumoAguaASerRateado(ImovelConta imovelMacro, RateioConsumoHelper helper) throws ControladorException {
		
		//Busca consumo de agua
		ConsumoHistorico consumoAgua = getControladorConsumoHistorico().
				buscarConsumoHistoricoPorImovelIdLigacaoTipo(imovelMacro.getId(), ConstantesSistema.LIGACAO_AGUA);
		
		// Calculamos o valor do consumo a ser rateado na ligação de agua
		int consumoAguaASerRateadoAgua = consumoAgua.getConsumoCobradoMesOriginal().intValue()
				- helper.getConsumoLigacaoAguaTotal();

		// Caso o consumo de agua a ser rateado seja maior que zero e o consumo
		// de agua do imovel macro seja menor ou igual a soma dos consumo
		// mínimos, atrituir
		// valor zero ao consumo de agua a ser rateado
//		if (consumoAguaASerRateadoAgua > 0
//				&& consumoAgua.getConsumoCobradoMesOriginal().intValue() <= helper.getConsumoMinimoTotal()) {
//			consumoAguaASerRateadoAgua = 0;
//		}

		// Caso o valor absoluto do consumo de Esgoto a ser rateado seja menor
		// ou igual a consumo de Esgoto do imovel marco * percentual de
		// tolerancia para
		// rateio do consumo atribuir zero ao consumo Esgoto a ser rateado
		if (Math.abs(consumoAguaASerRateadoAgua) <= consumoAgua.getConsumoCobradoMesOriginal().intValue()
				* (SistemaParametros.getInstancia().getPercentToleranciaRateio().intValue() / 100)) {
			consumoAguaASerRateadoAgua = 0;
		}

		return consumoAguaASerRateadoAgua;
	}

	
	private int calcularConsumoEsgotoASerRateado(ImovelConta imovelMacro, RateioConsumoHelper helper) throws ControladorException {

		//Busca consumo de agua
		ConsumoHistorico consumoEsgoto = getControladorConsumoHistorico().
						buscarConsumoHistoricoPorImovelIdLigacaoTipo(imovelMacro.getId(), ConstantesSistema.LIGACAO_ESGOTO);
				
		// Calculamos o valor do consumo a ser rateado na ligação de esgoto
		// Consumo de esgoto a ser rateado = consumo de esgoto do imóvel MACRO –
		// soma do consumo de esgoto dos imóveis MICRO;
		int consumoEsgotoASerRateadoEsgoto = consumoEsgoto.getConsumoCobradoMesOriginal().intValue()
				- helper.getConsumoLigacaoEsgotoTotal();

		// Caso o consumo de Esgoto a ser rateado seja maior que sero
		// e o consumo de Esgoto do imovel macro seja menor ou igual a soma dos
		// consumo
		// mínimos, atrituir valor zero ao consumo de Esgoto a ser rateado
//		if (consumoEsgotoASerRateadoEsgoto > 0
//				&& consumoEsgoto.getConsumoCobradoMesOriginal().intValue() <= helper.getConsumoMinimoTotal()) {
//			consumoEsgotoASerRateadoEsgoto = 0;
//		}

		// Caso o valor absoluto do consumo de Esgoto a ser rateado seja menor
		// ou igual a consumo de Esgoto do imovel marco * percentual de
		// tolerancia para rateio do consumo
		// atribuir zero ao consumo Esgoto a ser rateado
		if (Math.abs(consumoEsgotoASerRateadoEsgoto) <= consumoEsgoto.getConsumoCobradoMesOriginal().intValue()
				* (SistemaParametros.getInstancia().getPercentToleranciaRateio().doubleValue() / 100)) {
			consumoEsgotoASerRateadoEsgoto = 0;
		}

		return consumoEsgotoASerRateadoEsgoto;
	}

	private boolean verificarNovaLigacao(ImovelConta imovelConta, HidrometroInstalado hidrometroAgua) {
		
		boolean novaLigacao = false;
		
		if (imovelConta != null) {
			
			Integer maxDiasNovaLigacao = SistemaParametros.getInstancia().getMaxDiasNovaLigacao();
			
			if (maxDiasNovaLigacao != null && maxDiasNovaLigacao.intValue() > 0) {
				Date dataRestabelecimento = imovelConta.getDataLigacaoRestabelecimento();
				Date dataLigacao = imovelConta.getDataLigacaoAgua();
				Date dataLimiteNovaLigacao = null;
				
				// Verifica ligação nova
				if (dataRestabelecimento != null && dataRestabelecimento.compareTo(dataLigacao) > 0) {
					dataLimiteNovaLigacao = Util.adicionarNumeroDiasDeUmaData(dataRestabelecimento, maxDiasNovaLigacao);
				} else if (dataLigacao != null) {
					dataLimiteNovaLigacao = Util.adicionarNumeroDiasDeUmaData(dataLigacao, maxDiasNovaLigacao);
				}
				
				if (dataLimiteNovaLigacao != null && dataLimiteNovaLigacao.compareTo(hidrometroAgua.getDataLeitura()) > 0) {
					novaLigacao = true;
				}
				
				// Verifica hidrômetro novo
				if (hidrometroAgua != null && hidrometroAgua.getDataInstalacaoHidrometro() != null && hidrometroAgua.getDataLeituraAnterior() != null) {
					
					Date dataLimiteHidrometroNovo = null;
					Date dataInstalacaoHidrometro = hidrometroAgua.getDataInstalacaoHidrometro();
					
					if (dataInstalacaoHidrometro.compareTo(hidrometroAgua.getDataLeituraAnterior()) == 0) {
						dataLimiteHidrometroNovo = Util.adicionarNumeroDiasDeUmaData(dataInstalacaoHidrometro, maxDiasNovaLigacao);
					}
					
					if (dataLimiteHidrometroNovo != null && dataLimiteHidrometroNovo.compareTo(hidrometroAgua.getDataLeitura()) > 0) {
						novaLigacao = true;
					}
				}
			}
			
			
		}
		
		return novaLigacao;
	}

	public boolean verificarEstouroConsumo(ImovelConta imovel, ConsumoHistorico consumo, 
			HidrometroInstalado hidrometroInstalado, int tipoMedicao) throws ControladorException {

		int cMedio;

		// Verificamos se o consumo médio veio do
		// registro tipo 8 ou do imóvel
		if (hidrometroInstalado != null) {
			cMedio = hidrometroInstalado.getConsumoMedio();
		} else {
			if (tipoMedicao == ConstantesSistema.LIGACAO_AGUA) {
				cMedio = imovel.getConsumoMedioLigacaoAgua();
			} else {
				cMedio = imovel.getConsumoMedioEsgoto();
			}
		}

		boolean estouro = false;
		
		ConsumoAnormalidade consumoAnormal = new ConsumoAnormalidade();
		consumoAnormal = Fachada.getInstance().pesquisarPorId(
				ConstantesSistema.CONSUMO_ANORM_ESTOURO, consumoAnormal);
	
		if(!imovel.isCondominio() || (consumoAnormal.getIndicadorRegraImovelCondominio() != null 
				&& consumoAnormal.getIndicadorRegraImovelCondominio().equals(ConstantesSistema.NAO))){
		
			// [SB0000] 4.
			// [SB0006] - Verificar Estouro de Consumo
			// [SB0006] 1.
			int mediaVezesFatorEstouro = Util.arredondar(imovel.getFatorMultEstouro().doubleValue() * cMedio);
	
			if (consumo.getConsumoCobradoMes() != null && consumo.getConsumoCobradoMes().intValue() > imovel.getConsumoEstouro().intValue()
					&& consumo.getConsumoCobradoMes().intValue() > mediaVezesFatorEstouro) {
	
				// [SB0006] 1.1.
				// Calendar c = Calendar.getInstance();
				// c.setTime( imovel.getdata );
				
				int anoMes = Util.subtrairMesDoAnoMes(imovel.getAnoMesConta(), 1);
				ConsumoAnteriores consumoAnterior = null;
				
				consumoAnterior = getControladorConsumoAnteriores().
				buscarConsumoAnterioresPorImovelAnoMesPorTipoLigacao(imovel.getId(), anoMes,tipoMedicao);			
	
				Integer anormConsumoAnterior = null;
				if (consumoAnterior != null && consumoAnterior.getAnormalidadeConsumo() != 0) {
					anormConsumoAnterior = consumoAnterior.getAnormalidadeConsumo();
				}
	
				boolean cond1 = anormConsumoAnterior != null
						&& anormConsumoAnterior != ConstantesSistema.CONSUMO_ANORM_ESTOURO
						&& anormConsumoAnterior != ConstantesSistema.CONSUMO_ANORM_ESTOURO_MEDIA;
	
				// [SB0006] 1.1. (continuação)
				if (cond1 || consumo.getConsumoCobradoMes() > imovel.getConsumoMaximo()
						|| anormConsumoAnterior == null) {
					// [SB0006] 1.1.1.
					ConsumoAnormalidade consumoAnormalidade = new ConsumoAnormalidade(ConstantesSistema.CONSUMO_ANORM_ESTOURO);
					consumo.setConsumoAnormalidade(consumoAnormalidade);
	
					// [SB0006] 1.1.2.
					consumo.setConsumoCobradoMes(cMedio);
	
					// [SB0006] 1.1.3.
					consumo.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_MEDIA_HIDR);
	
					// [SB0006] 1.2.
				} else {
					ConsumoAnormalidade consumoAnormalidade = new ConsumoAnormalidade(ConstantesSistema.CONSUMO_ANORM_ESTOURO);
					consumo.setConsumoAnormalidade(consumoAnormalidade);
				}
	
				estouro = true;
			}
		}

		return estouro;
	}

	public void verificarAltoConsumo(ImovelConta imovel, ConsumoHistorico consumo, 
			HidrometroInstalado hidrometroInstalado, int tipoMedicao) {

		int cMedio;

		// Verificamos se o consumo médio veio do
		// registro tipo 8 ou do imóvel
		if (hidrometroInstalado != null) {
			cMedio = hidrometroInstalado.getConsumoMedio();
		} else {

			if (tipoMedicao == ConstantesSistema.LIGACAO_AGUA) {

				cMedio = imovel.getConsumoMedioLigacaoAgua();
			} else {

				cMedio = imovel.getConsumoMedioEsgoto();
			}
		}

		// [SB0007] - Verificar Alto Consumo
		int resultado = Util.arredondar(imovel.getFatorMultMediaAltoConsumo().doubleValue() * cMedio);

		if (consumo.getConsumoCobradoMes() != null && consumo.getConsumoCobradoMes().intValue() > imovel.getAltoConsumo() && consumo.getConsumoCobradoMes() > resultado) {
			ConsumoAnormalidade consumoAnormalidade = new ConsumoAnormalidade(ConstantesSistema.CONSUMO_ANORM_ALTO_CONSUMO);
			consumo.setConsumoAnormalidade(consumoAnormalidade);
		}
	}

	public void verificarBaixoConsumo(ImovelConta imovel, ConsumoHistorico consumo, 
			HidrometroInstalado hidrometroInstalado, int tipoMedicao) {

		int cMedio;

		// Verificamos se o consumo médio veio do
		// registro tipo 8 ou do imóvel
		if (hidrometroInstalado != null) {
			cMedio = hidrometroInstalado.getConsumoMedio();
		} else {

			if (tipoMedicao == ConstantesSistema.LIGACAO_AGUA) {
				cMedio = imovel.getConsumoMedioLigacaoAgua();
			} else {
				cMedio = imovel.getConsumoMedioEsgoto();
			}
		}

		// [SB0008] - Verificar Baixo Consumo
		double percentual = imovel.getPercentBaixoConsumo().doubleValue() / 100;
		double consumoMedioPercent = cMedio * percentual;

		if (cMedio > imovel.getBaixoConsumo().intValue() && consumo.getConsumoCobradoMes() != null && consumo.getConsumoCobradoMes().intValue() < consumoMedioPercent) {
			ConsumoAnormalidade consumoAnormalidade = new ConsumoAnormalidade(ConstantesSistema.CONSUMO_ANORM_BAIXO_CONSUMO);
			consumo.setConsumoAnormalidade(consumoAnormalidade);
		}		
	}

	/**
	 * [UC0101] - Consistir Leituras e Calcular Consumos [SF0012] - Obter
	 * Leitura Anterior
	 * 
	 */
	public int obterLeituraAnterior(HidrometroInstalado hidrometroInstalado) {
		int retorno = 0;

		if (hidrometroInstalado != null) {
			if (hidrometroInstalado.getLeituraAnteriorInformada() != null
					&& hidrometroInstalado.getLeitura() != null) {

				if (hidrometroInstalado.getLeituraAnteriorInformada().equals(hidrometroInstalado.getLeitura())) {
					retorno = hidrometroInstalado.getLeituraAnteriorInformada();
				} else {
					retorno = hidrometroInstalado.getLeituraAnteriorFaturamento();
				}
			} else {
				retorno = hidrometroInstalado.getLeituraAnteriorFaturamento();
			}
		}
		return retorno;
	}

	private void dadosFaturamentoEspecialNaoMedido(ImovelConta imovel, ConsumoHistorico consumo, int ligacaoTipo) throws ControladorException {
		
		HidrometroInstalado hidrometroInstalado = getControladorHidrometroInstalado().
				buscarHidrometroInstaladoPorImovelTipoMedicao(imovel.getId(), ligacaoTipo);

		int cMedio;

		// Verificamos se o consumo médio veio do
		// registro tipo 8 ou do imóvel
		if (hidrometroInstalado != null) {
			cMedio = hidrometroInstalado.getConsumoMedio();
		} else {
			if (ligacaoTipo == ConstantesSistema.LIGACAO_AGUA) {
				cMedio = imovel.getConsumoMedioLigacaoAgua();
			} else {
				cMedio = imovel.getConsumoMedioEsgoto();
			}
		}

		FaturamentoSituacaoTipo situacaoTipo = imovel.getFaturamentoSituacaoTipo();

		if (situacaoTipo != null) {
			if (situacaoTipo.getIdAnormalidadeConsumoSemLeitura()!=null && 
					situacaoTipo.getIdAnormalidadeConsumoSemLeitura().intValue() == ConstantesSistema.NAO_OCORRE) {
				consumo.setConsumoCobradoMes(cMedio);
				consumo.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_MEDIA_IMOV);
			} else if (situacaoTipo.getIdAnormalidadeConsumoSemLeitura()!=null && 
					situacaoTipo.getIdAnormalidadeConsumoSemLeitura().intValue() == ConstantesSistema.MINIMO) {
				consumo.setConsumoCobradoMes(imovel.getConsumoMinimoImovel());
				consumo.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_FIXO_SITUACAO_ESPECIAL);
			} else if (situacaoTipo.getIdAnormalidadeConsumoSemLeitura()!=null && 
					situacaoTipo.getIdAnormalidadeConsumoSemLeitura().intValue() == ConstantesSistema.MEDIA) {
				consumo.setConsumoCobradoMes(cMedio);
				consumo.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_MEDIA_HIDR);
			} else if (situacaoTipo.getIdAnormalidadeConsumoSemLeitura()!=null &&
					situacaoTipo.getIdAnormalidadeConsumoSemLeitura().intValue() == ConstantesSistema.FIXO) {
				

				if (ligacaoTipo == ConstantesSistema.LIGACAO_AGUA) {

					if (imovel.getConsumoAguaNaoMedidoHistoricoFaturamento() != null) {

						/*
						 * Caso o consumo calculado seja MENOR que o consumo
						 * fixo, colocar o consumo calculado; caso contrário,
						 * colocar o consumo fixo.
						 */
//						if (consumo.getConsumoCobradoMes().intValue() > 
//							imovel.getConsumoAguaNaoMedidoHistoricoFaturamento().intValue()) {
							consumo.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_FIXO_SITUACAO_ESPECIAL);
							consumo.setConsumoCobradoMes(imovel.getConsumoAguaNaoMedidoHistoricoFaturamento());
//						}
					}

				} else if (imovel.getVolumeEsgotoNaoMedidoHistoricoFaturamento() != null) {
					/*
					 * Caso o volume calculado seja MENOR que o volume fixo,
					 * colocar o volume calculado; caso contrário, colocar o
					 * volume fixo.
					 */
//					if (consumo.getConsumoCobradoMes().intValue()
//							> imovel.getVolumeEsgotoNaoMedidoHistoricoFaturamento().intValue()) {
						consumo.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_FIXO_SITUACAO_ESPECIAL);
						consumo.setConsumoCobradoMes(imovel.getVolumeEsgotoNaoMedidoHistoricoFaturamento());
//					}
				}

			} else if (situacaoTipo.getIdAnormalidadeConsumoSemLeitura()!=null && situacaoTipo.getIdAnormalidadeConsumoSemLeitura().intValue() == ConstantesSistema.NAO_MEDIDO) {
				// Seta o tipo de consumo
				consumo.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_ESTIMADO);

				consumo.setConsumoCobradoMes(imovel.getConsumoMinimoImovel());
			}
		}
		
	}

	private void dadosFaturamentoEspecialMedido(ImovelConta imovel, ConsumoHistorico consumo, int ligacaoTipo) 
			throws ControladorException {
		
		FaturamentoSituacaoTipo situacaoTipo = imovel.getFaturamentoSituacaoTipo();

		HidrometroInstalado hidrometroInstalado = getControladorHidrometroInstalado().
				buscarHidrometroInstaladoPorImovelTipoMedicao(imovel.getId(), ligacaoTipo);

		int cMedio;

		// Verificamos se o consumo médio veio do
		// registro tipo 8 ou do imóvel
		if (hidrometroInstalado != null) {
			cMedio = hidrometroInstalado.getConsumoMedio();
		} else {

			if (ligacaoTipo == ConstantesSistema.LIGACAO_AGUA) {

				cMedio = imovel.getConsumoMedioLigacaoAgua();
			} else {

				cMedio = imovel.getConsumoMedioEsgoto();
			}
		}

		int leituraAnterior = obterLeituraAnterior(hidrometroInstalado);

		if (situacaoTipo != null) {
			if (hidrometroInstalado != null && hidrometroInstalado.getLeitura() == null) {

				if (situacaoTipo.getIdAnormalidadeConsumoSemLeitura()!=null &&
						situacaoTipo.getIdAnormalidadeConsumoSemLeitura().intValue() == ConstantesSistema.NAO_OCORRE) {
					consumo.setConsumoCobradoMes(cMedio);
					consumo.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_MEDIA_IMOV);
				} else if (situacaoTipo.getIdAnormalidadeConsumoSemLeitura()!=null &&
						situacaoTipo.getIdAnormalidadeConsumoSemLeitura().intValue() == ConstantesSistema.MINIMO) {
					consumo.setConsumoCobradoMes(imovel.getConsumoMinimoImovel());
					consumo.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_FIXO_SITUACAO_ESPECIAL);
				} else if (situacaoTipo.getIdAnormalidadeConsumoSemLeitura()!=null &&
						situacaoTipo.getIdAnormalidadeConsumoSemLeitura().intValue() == ConstantesSistema.MEDIA) {
					consumo.setConsumoCobradoMes(cMedio);
					consumo.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_MEDIA_HIDR);
				} else if (situacaoTipo.getIdAnormalidadeConsumoSemLeitura() !=null &&
						situacaoTipo.getIdAnormalidadeConsumoSemLeitura().intValue()== ConstantesSistema.FIXO) {
					

					if (ligacaoTipo == ConstantesSistema.LIGACAO_AGUA) {

						if (imovel.getConsumoAguaMedidoHistoricoFaturamento() != null) {

							consumo.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_FIXO_SITUACAO_ESPECIAL);
							consumo.setConsumoCobradoMes(imovel.getConsumoAguaMedidoHistoricoFaturamento());
						}

					} else if (imovel.getVolumeEsgotoMedidoHistoricoFaturamento() != null) {
						    consumo.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_FIXO_SITUACAO_ESPECIAL);
							consumo.setConsumoCobradoMes(imovel.getVolumeEsgotoMedidoHistoricoFaturamento());
					}

				} else if (situacaoTipo.getIdAnormalidadeConsumoSemLeitura()!=null &&
						situacaoTipo.getIdAnormalidadeConsumoSemLeitura().intValue() == ConstantesSistema.NAO_MEDIDO) {
					// Seta o tipo de consumo
					consumo.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_ESTIMADO);

					consumo.setConsumoCobradoMes(imovel.getConsumoMinimoImovel());
				}

				// Caso leitura atual informada diferente de zero
			} else if (hidrometroInstalado != null && hidrometroInstalado.getLeitura() != null) {
				
				if (situacaoTipo.getIdAnormalidadeConsumoComLeitura()!=null){
					// Caso a leitura anormalidade leitura com leitura seja igual a
					// leitura anormalidade consumo não ocorre
					if (situacaoTipo.getIdAnormalidadeConsumoComLeitura().intValue() == ConstantesSistema.NAO_OCORRE) {
						// O consumo a ser cobrado no mes será o consumo médio do
						// hidrômetro
						consumo.setConsumoCobradoMes(cMedio);
						consumo.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_MEDIA_HIDR);
					}
					// Caso a leitura anormalidade leitura com leitura seja igual a
					// leitura anormalidade consumo mínimo
					else if (situacaoTipo.getIdAnormalidadeConsumoComLeitura().intValue() == ConstantesSistema.MINIMO) {
	
						consumo.setConsumoCobradoMes(imovel.getConsumoMinimoImovel());
						consumo.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_FIXO_SITUACAO_ESPECIAL);
	
					} else if (situacaoTipo.getIdAnormalidadeConsumoComLeitura().intValue() == ConstantesSistema.MEDIA) {
						// O consumo a ser cobrado no mes será o consumo médio do
						// hidrômetro
						consumo.setConsumoCobradoMes(cMedio);
						consumo.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_MEDIA_HIDR);
						// Caso a leitura anormalidade leitura com leitura seja
						// igual a
						// leitura anormalidade consumo medido
					} else if (situacaoTipo.getIdAnormalidadeConsumoComLeitura().intValue() == ConstantesSistema.MAIOR_ENTRE_O_CONSUMO_MEDIO) {
						// Caso o consumo médio hidrômetro seja maior que o consumo
						// medido
						if (cMedio > consumo.getConsumoCobradoMes().intValue()) {
							// Consumo a ser cobrado no mês será o já calculado
							consumo.setConsumoCobradoMes(cMedio);
							// Seta o tipo de consumo
							consumo.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_MEDIA_HIDR);
						} else {
							consumo.setConsumoCobradoMes(consumo.getConsumoCobradoMes());
						}
	
					} else if (situacaoTipo.getIdAnormalidadeConsumoComLeitura().intValue() == ConstantesSistema.MENOR_ENTRE_O_CONSUMO_MEDIO) {
						// Caso o consumo médio hidrômetro seja maior que o consumo
						// medido
						if (cMedio < consumo.getConsumoCobradoMes().intValue()) {
							// Consumo a ser cobrado no mês será o já calculado
							consumo.setConsumoCobradoMes(cMedio);
							// Seta o tipo de consumo
							consumo.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_MEDIA_HIDR);
						} else {
							consumo.setConsumoCobradoMes(consumo.getConsumoCobradoMes());
						}
	
					}
	
					/*
					 * Colocado por Raphael Rossiter em 12/08/2008 - Analista:
					 * Rosana Carvalho OBJ: Verificar a situação especial de
					 * faturamento quando o consumo de água e/ou volume de esgoto
					 * está fixo.
					 */
					else if (situacaoTipo.getIdAnormalidadeConsumoComLeitura().intValue() == ConstantesSistema.FIXO) {

						// Consumo a ser cobrado no mês será o consumo fixado no
						// histórico da situação especial
						if (ligacaoTipo == ConstantesSistema.LIGACAO_AGUA) {
	
							if (imovel.getConsumoAguaMedidoHistoricoFaturamento() != null) {
								consumo.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_FIXO_SITUACAO_ESPECIAL);
								consumo.setConsumoCobradoMes(imovel.getConsumoAguaMedidoHistoricoFaturamento());
	
							}
	
						} else if (imovel.getVolumeEsgotoMedidoHistoricoFaturamento() != null) {
							consumo.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_FIXO_SITUACAO_ESPECIAL);
							consumo.setConsumoCobradoMes(imovel.getVolumeEsgotoMedidoHistoricoFaturamento());
	
						}
					} else if (situacaoTipo.getIdAnormalidadeConsumoComLeitura().intValue() == ConstantesSistema.NAO_MEDIDO) {
	
						// Seta o tipo de consumo
						consumo.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_ESTIMADO);
	
						consumo.setConsumoCobradoMes(imovel.getConsumoMinimoImovel());
	
					}
				}
				
				// Caso a leitura anormalidade leitura com leitura
				// seja igual a leitura anormaliade leitura ->
				// <<anterior mais média>>
				if (situacaoTipo.getIdAnormalidadeLeituraComLeitura()!=null){
					if (situacaoTipo.getIdAnormalidadeLeituraComLeitura().intValue() == ConstantesSistema.ANTERIOR_MAIS_A_MEDIA) {
						// Seta a leitura atual de faturamento
						consumo.setLeituraAtual(leituraAnterior + cMedio);
						// <<anterior>>
					} else if (situacaoTipo.getIdAnormalidadeLeituraComLeitura().intValue()== ConstantesSistema.ANTERIOR) {
						// Seta a leitura atual de faturamento
						consumo.setLeituraAtual(leituraAnterior);
						// <<anterior mais consumo>>
					} else if (situacaoTipo.getIdAnormalidadeLeituraComLeitura().intValue() == ConstantesSistema.ANTERIOR_MAIS_O_CONSUMO) {
						// Seta a leitura atual de faturamento
						consumo.setLeituraAtual(leituraAnterior + consumo.getConsumoCobradoMes());
						// <<leitura informada>>
					} else if (situacaoTipo.getIdAnormalidadeLeituraComLeitura().intValue() == ConstantesSistema.INFORMADO) {
						consumo.setLeituraAtual(consumo.getLeituraAtual());
					}
				}
			}
		}
	}

	

	/**
	 * Proposta: RN2011071073 - 08/11/2011 - Savio Luiz - RM1073 - CAERN
	 * (Calcular o minimo quando a leitura atual for menor que a leitura
	 * projetada).
	 * 
	 * [UC0101] - Consistir Leituras e Calcular Consumos [SB0024] - Recuperar
	 * Dados da Tabela de Consumo Anormalidade Acao
	 * 
	 * @since 16/11/2011
	 * @author Thúlio Araújo
	 * 
	 * @param imovel
	 * @param consumo
	 * @param reg8
	 * @param tipoMedicao
	 * @param reg11
	 * @param consumoAnormalidadeTipo
	 */
	public void recuperarDadosConsumoAnormalidadeAcao(ImovelConta imovel, ConsumoHistorico consumo, 
			HidrometroInstalado hidrometroInstalado, int tipoMedicao,
			ConsumoAnormalidade consumoAnormalidadeTipo) throws ControladorException {

		int cMedio;

		// Verificamos se o consumo medio veio do
		// registro tipo 8 ou do imovel
		if (hidrometroInstalado != null) {
			cMedio = hidrometroInstalado.getConsumoMedio();
		} else {

			if (tipoMedicao == ConstantesSistema.LIGACAO_AGUA) {

				cMedio = imovel.getConsumoMedioLigacaoAgua();
			} else {

				cMedio = imovel.getConsumoMedioEsgoto();
			}
		}

		int anormConsumo = consumoAnormalidadeTipo.getId();
		int idImovelPerfil = imovel.getCodigoPerfil();
		int categoriaPrincipal = getControladorCategoriaSubcategoria().obterCategoriaPrincipal(imovel.getId()); 
				
		Integer idLeituraAnormalidadeConsumo = null;
		Double numerofatorConsumo = null;
		String mensagemContaPrimeiroMes = null;
		
		//RM 7219
		//Verifica a quantos meses existe anormalidade
		int anoMesConta = imovel.getAnoMesConta();
		int ordemMesAnormalidade = getControladorConsumoAnteriores(). 
				obtemOrdemAnormalidade(imovel, consumoAnormalidadeTipo.getId(),anoMesConta );
		
		//verifica a quantidade de ação para a anormalidade informada
		int qtdAcaoAnormalidade = getControladorConsumoAnormalidadeAcao().obterQtdConsumoAnormalidadeAcao(
				idImovelPerfil, anormConsumo, categoriaPrincipal);				
		
		if(qtdAcaoAnormalidade == 0){
			//verifica a quantidade de ação para a anormalidade informada sem o perfil
			qtdAcaoAnormalidade = getControladorConsumoAnormalidadeAcao().obterQtdConsumoAnormalidadeAcao(
					null, anormConsumo, categoriaPrincipal);					
		}
		
		if(qtdAcaoAnormalidade == 0){
			//verifica a quantidade de ação para a anormalidade informada sem o perfil e a categoria
			qtdAcaoAnormalidade = getControladorConsumoAnormalidadeAcao().obterQtdConsumoAnormalidadeAcao(
					null, anormConsumo, null);			
		}
		
		//caso a quantidade ação de anormalidade seja menor do que a ordem de anormalidade
		// no histórico
		if(qtdAcaoAnormalidade < ordemMesAnormalidade){
			ordemMesAnormalidade = qtdAcaoAnormalidade;
		}
		
		//Obtem Registros Tipo 12 para o consumo anormalidade tipo
		ConsumoAnormalidadeAcao acao = getControladorConsumoAnormalidadeAcao().obterConsumoAnormalidadeAcao(anormConsumo,  
				categoriaPrincipal,idImovelPerfil, ordemMesAnormalidade);
				
		//Obtem a leitura anormalidade e fator de consumo
		if (acao!=null){
			idLeituraAnormalidadeConsumo = acao.getIdLeituraAnormalidadeConsumo();					
			numerofatorConsumo = acao.getFatorConsumo().doubleValue();
		
			//Mensagem
			mensagemContaPrimeiroMes = acao.getMensagemConta();
		}
		
		if (mensagemContaPrimeiroMes != null) {
			String[] mensagem = null;
			if (SistemaParametros.getInstancia().getCodigoEmpresaFebraban().equals(
					ConstantesSistema.CODIGO_FEBRABAN_CAERN)) {
				mensagem = Util.dividirString(
						mensagemContaPrimeiroMes, 60);
			} else {
				mensagem = Util.dividirString(
						mensagemContaPrimeiroMes, 40);
			}
			
			switch (mensagem.length) {
			case 3:
				imovel.setMensagemContaAnormalidade3(mensagem[2]);
			case 2:
				imovel.setMensagemContaAnormalidade2(mensagem[1]);
			case 1:
				imovel.setMensagemContaAnormalidade1(mensagem[0]);
				break;
			}
		}
		
		//Atualiza objeto
		ControladorBasico.getInstance().atualizar(imovel);
		//Fim RM7219

		// 3.1.1.1. O sistema gera a Anormalidade de Consumo com o valor
		// correspondente a estouro de consumo da tabela
		// CONSUMO_ANORMALIDADE
		consumo.setConsumoAnormalidade(consumoAnormalidadeTipo);

		if (idLeituraAnormalidadeConsumo != null && idLeituraAnormalidadeConsumo.intValue() == ConstantesSistema.NAO_OCORRE) {

			consumo.setConsumoCobradoMes(cMedio);
			consumo.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_MEDIA_HIDR);

		} else if (idLeituraAnormalidadeConsumo != null && idLeituraAnormalidadeConsumo.intValue() == ConstantesSistema.MINIMO) {

			// O Consumo a Ser Cobrado no Mes sera o valor retornado
			// por [UC0105 â Obter Consumo Minimo da Ligacao
			consumo.setConsumoCobradoMes(imovel.getConsumoMinimoImovel());
			// Seta o tipo de consumo
			consumo.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_SEM);

			// 1.1.5
		} else if (idLeituraAnormalidadeConsumo != null && idLeituraAnormalidadeConsumo.intValue() == ConstantesSistema.MEDIA) {

			/*
			 * // Consumo a ser cobrado no mes sera o consumo medio do //
			 * hidrometro consumo.setConsumoCobradoMes(cMedio);
			 * consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_HIDR);
			 */

			// 1.1.5.1
			/*
			 * int anoMesAnterior1 = Integer.parseInt(
			 * imovel.getAnoMesConta() ); anoMesAnterior1 =
			 * Util.subtrairMesDoAnoMes( anoMesAnterior1, 1); ImovelReg3
			 * registroMesAnterior1 = imovel.getRegistro3( anoMesAnterior1
			 * );
			 * 
			 * int anoMesAnterior2 = Util.subtrairMesDoAnoMes(
			 * anoMesAnterior1, 1); ImovelReg3 registroMesAnterior2 =
			 * imovel.getRegistro3( anoMesAnterior2 );
			 */

			// 1.1.1.2.3.1. Caso a anormalidade de consumo informada seja
			// ALTO CONSUMO (6) ou ESTOURO DE CONSUMO (5),
			// entao o Consumo a Ser Cobrado no Mes sera o maior entre o
			// consumo medio do hidrometro multiplicado pelo fator
			// de multiplicacaoo da quantidade de vezes da media
			// (CSAA_NNFATORCONSUMOMES (1,2,3) dependendo do mes calculado
			// anteriormente) e o Consumo Total de Referencia.
			if ((anormConsumo == ConstantesSistema.CONSUMO_ANORM_ALTO_CONSUMO || 
					anormConsumo == ConstantesSistema.CONSUMO_ANORM_ESTOURO) && numerofatorConsumo != null) {
				int consumofaturadoMesInt = Util.arredondar(cMedio * numerofatorConsumo);
				
				if (imovel.getConsumoEstouro() > consumofaturadoMesInt) {
					consumo.setConsumoCobradoMes(imovel.getConsumoEstouro());
				} else {
					consumo.setConsumoCobradoMes(consumofaturadoMesInt);
				}
				
				// Caso a categoria seja residencial e tenha dado EC ou AC no primeiro mês,
				// não verificar o maior entre o consumo médio do hidrômetro multiplicado pelo fator
				// de multiplicação da quantidade de vezes da média
				// e o Consumo Total de Referencia
				//if(acao.getIdCategoria() == ConstantesSistema.RESIDENCIAL && ordemMesAnormalidade == 1){
					//consumo.setConsumoCobradoMes(consumofaturadoMesInt);
				//}

				
			} else {
				consumo.setConsumoCobradoMes(cMedio);
				consumo.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_MEDIA_HIDR);
			}

			/*
			 * if ( ( reg3MesAnterior != null &&
			 * reg3MesAnterior.getAnormalidadeConsumo() !=
			 * Consumo.CONSUMO_ANORM_ESTOURO &&
			 * reg3MesAnterior.getAnormalidadeConsumo() !=
			 * Consumo.CONSUMO_ANORM_ESTOURO_MEDIA ) || (
			 * reg3SegundoMesAnterior != null &&
			 * reg3SegundoMesAnterior.getAnormalidadeConsumo() !=
			 * Consumo.CONSUMO_ANORM_ESTOURO &&
			 * reg3SegundoMesAnterior.getAnormalidadeConsumo() !=
			 * Consumo.CONSUMO_ANORM_ESTOURO_MEDIA ) ){
			 * 
			 * double consumofaturadoMes = consumo.getConsumoCobradoMes();
			 * consumofaturadoMes = consumofaturadoMes * numerofatorConsumo;
			 * int consumofaturadoMesInt =
			 * Util.arredondar(consumofaturadoMes);
			 * 
			 * if ( consumofaturadoMesInt > resultado ){
			 * consumo.setConsumoCobradoMes( consumofaturadoMesInt ); } else
			 * { consumo.setConsumoCobradoMes( resultado ); } } else {
			 * double consumofaturadoMes = cMedio; consumofaturadoMes =
			 * consumofaturadoMes * numerofatorConsumo; int
			 * consumofaturadoMesInt = Util.arredondar(consumofaturadoMes);
			 * consumo.setConsumoCobradoMes(consumofaturadoMesInt); }
			 */
		} else if (idLeituraAnormalidadeConsumo != null && idLeituraAnormalidadeConsumo.intValue() == ConstantesSistema.NORMAL) {

			// Fazer nada ja calculado

		} else if (idLeituraAnormalidadeConsumo != null && idLeituraAnormalidadeConsumo.intValue() == ConstantesSistema.MAIOR_ENTRE_O_CONSUMO_MEDIO) {

			if (cMedio > consumo.getConsumoCobradoMes().intValue()) {
				consumo.setConsumoCobradoMes(cMedio);
				consumo.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_MEDIA_HIDR);
			}

		} else if (idLeituraAnormalidadeConsumo != null && idLeituraAnormalidadeConsumo.intValue() == ConstantesSistema.MENOR_ENTRE_O_CONSUMO_MEDIO) {
			if (cMedio < consumo.getConsumoCobradoMes().intValue()) {
				consumo.setConsumoCobradoMes(cMedio);
				consumo.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_MEDIA_HIDR);
			}
		}

		// 3.1.4. O consumo a Ser Cobrado no Mes sera igual
		// ao Consumo a Ser Cobrado no Mes multiplicado pelo
		// fator de multiplicação da quantidade de vezes a media
		// (CSAA_NNFATORCONSUMOMES(1,2 ou 3), dependendo do mes
		// calculado anteriormente
		if (numerofatorConsumo != null && idLeituraAnormalidadeConsumo != null && 
				idLeituraAnormalidadeConsumo != ConstantesSistema.MEDIA) {
			double consumofaturadoMes = consumo.getConsumoCobradoMes();
			consumofaturadoMes = consumofaturadoMes * numerofatorConsumo;
			int consumofaturadoMesInt = Util.arredondar(consumofaturadoMes);
			consumo.setConsumoCobradoMes(consumofaturadoMesInt);
		}		
	}

	
	
	
	
	public void verificarPercentualEsgotoAlternativo(ImovelConta imovel, Integer consumoFaturadoEsgoto) throws ControladorException {
		
		double percentualEsgoto = 0.00;
		/*
		 * CASO O IMÓVEL SEJA PARA FATURAR ESGOTO Essa verificação se faz
		 * necessária para o pré-faturamento.
		 */
		if (imovel.getIndcFaturamentoEsgoto()!=null && imovel.getIndcFaturamentoEsgoto().equals(ConstantesSistema.SIM)) {

			// Recupera o percentual de esgoto do imóvel.
			if(imovel.getPercentCobrancaEsgoto() != null){
				percentualEsgoto = imovel.getPercentCobrancaEsgoto().doubleValue();
			}

			// CASO O IMÓVEL SEJA PARA FATURAR ÁGUA
			if (imovel.getIndcFaturamentoAgua()!=null && imovel.getIndcFaturamentoAgua().equals(ConstantesSistema.SIM)
					&& consumoFaturadoEsgoto != null) {

				// Caso o percentual alternativo de esgoto seja diferente de
				// nulo

				if (imovel.getPercentualAlternativoEsgoto()!=null &&
						imovel.getPercentualAlternativoEsgoto().doubleValue() != Double.MIN_VALUE) {

					double qtdeEconomia = getControladorCategoriaSubcategoria(). 
							obterQuantidadeEconomiasTotal(imovel.getId());
					
					double consumoFaturadoEsgotoDouble = consumoFaturadoEsgoto;

					int consumoPorEconomia = Util.arredondar(consumoFaturadoEsgotoDouble / qtdeEconomia);

					// verificar se o consumo por economia eh
					// menor ou igual ao consumo do percentual alternativo
					if (consumoPorEconomia <= imovel.getConsumoPercentualAlternativoEsgoto()) {

						// enviar como percentual de esgoto o menor valor entre
						// percentual e percentualAlternativo
						if (imovel.getPercentualAlternativoEsgoto() !=null &&
								imovel.getPercentualAlternativoEsgoto().doubleValue() < percentualEsgoto) {
							percentualEsgoto = imovel.getPercentualAlternativoEsgoto().doubleValue();
						}
					}

				}
			}
		}

		imovel.setPercentCobrancaEsgoto(new BigDecimal(percentualEsgoto));
	}
	
	public double obterValorConta(Integer imovelId) throws ControladorException {

		double valorConta = ControladorImovelConta.getInstance().obterValorContaSemImposto(imovelId) - 
					ControladorContaImposto.getInstance().obterValorImpostoTotal(imovelId);
		
		if (valorConta < 0d) {
			valorConta = 0d;
		}
		
		return Util.arredondar(valorConta, 2);
	}

	/**
	 * Método identifica se o imóvel passado como parâmetro pode ser impresso
	 * (Se o mesmo NAO for imóvel condomínio OU, sendo imóvel condomínio for o 
	 * último imóvel retorna true)
	 * 
	 * @author Amelia Pessoa
	 * @param imovel
	 * @return
	 * @throws ControladorException
	 */
	public boolean permiteImprimir(ImovelConta imovel) throws ControladorException {
		if(( imovel.getIndcCondominio().equals(ConstantesSistema.NAO) && (imovel.getMatriculaCondominio()==null )
				//[RM8152][UC1303][SB019]
				//|| ( imovel.getMatriculaCondominio() != null && imovel.getIndcImovelImpresso().equals(ConstantesSistema.SIM)) 
				)){
			return true;
		} else {
			Integer idMacro = null;
			if(imovel.getIndcCondominio().equals(ConstantesSistema.SIM)){
				idMacro = imovel.getId();
			} else {
				idMacro = imovel.getMatriculaCondominio();
			}
					
			Integer idUltimo = getControladorImovelConta().obterIdUltimoImovelMicro(idMacro);
			if (idUltimo!=null && idUltimo.equals(imovel.getId())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Metodo que atualiza o resumo necessário para o rateio do imóvel
	 * condominio
	 * 
	 * @author Bruno Barros
	 * @date 11/03/2010
	 * @param consumoAgua
	 *            Consumo de agua novo, caso esteja atualizando o consumo de
	 *            esgoto, setar nulo
	 * @param consumoEsgoto
	 *            Consumo de esgoto novo, caso esteja atualizando o consumo de
	 *            agua, setar nulo
	 */
	public RateioConsumoHelper atualizarResumoEfetuarRateio(ImovelConta imovelMacro) throws ControladorException {

		ArrayList<Integer> colecaoMicros = getControladorImovelConta().buscarIdsImoveisMicro(imovelMacro.getId());
		
		RateioConsumoHelper helper = new RateioConsumoHelper(imovelMacro.getId()); 
		
		if (colecaoMicros!=null){
			
			// Selecionamos a ligação de agua do imovel macro
			// para verificarmos se o mesmo possue
			// tipo de rateio = 4
			HidrometroInstalado ligacaoAguaImovelMacro = getControladorHidrometroInstalado().
					buscarHidrometroInstaladoPorImovelTipoMedicao(imovelMacro.getId(), ConstantesSistema.LIGACAO_AGUA);
			
			for(Integer idImovel : colecaoMicros){
				ImovelConta imovel = 
						ControladorBasico.getInstance().pesquisarPorId(idImovel, new ImovelConta());
										
				// Calculamos a quantidade de economias total
				int quantidadeEconomiasImovel = getControladorCategoriaSubcategoria().obterQuantidadeEconomiasTotal(imovel.getId());
				
				HidrometroInstalado ligacaoAguaImovelMicro = getControladorHidrometroInstalado().
						buscarHidrometroInstaladoPorImovelTipoMedicao(imovel.getId(), ConstantesSistema.LIGACAO_AGUA);
				
				// Verificamose se o imóvel é faturado de agua
				if (imovel.getIndcFaturamentoAgua().equals(ConstantesSistema.SIM)){					

					// Verificamos se o imovel é medido ou não medido...
					if (ligacaoAguaImovelMicro != null) {
						helper.setQuantidadeEconomiasAguaMedidas(helper
								.getQuantidadeEconomiasAguaMedidas()
								+ quantidadeEconomiasImovel);
						// Caso contrário
					} else {
						helper.setQuantidadeEconomiasAguaNaoMedidas(helper
								.getQuantidadeEconomiasAguaNaoMedidas()
								+ quantidadeEconomiasImovel);
					}
				}

				// Verificamose se o imóvel é faturado de esgoto
				if (imovel.getIndcFaturamentoEsgoto().equals(ConstantesSistema.SIM)){	
				
					
					/*
					 * Considerar não medido de esgoto igual a não medido de
					 * água, ou seja, de acordo com a existência de hidrometro
					 * de ligação de água quando o tipo de rateio for igual a
					 * rateio não medido de água.
					 */
					if (ligacaoAguaImovelMacro != null
							&& ligacaoAguaImovelMacro.getTipoRateio()!=null &&
									ligacaoAguaImovelMacro.getTipoRateio().intValue() == ConstantesSistema.TIPO_RATEIO_NAO_MEDIDO_AGUA) {

						if (ligacaoAguaImovelMicro != null) {
							helper.setQuantidadeEconomiasEsgotoMedidas(helper
									.getQuantidadeEconomiasEsgotoMedidas()
									+ quantidadeEconomiasImovel);
							// Caso contrário
						} else {
							helper.setQuantidadeEconomiasEsgotoNaoMedidas(helper
											.getQuantidadeEconomiasEsgotoNaoMedidas()
											+ quantidadeEconomiasImovel);
						}
					} else {
						HidrometroInstalado ligacaoEsgotoImovelMicro = getControladorHidrometroInstalado().
								buscarHidrometroInstaladoPorImovelTipoMedicao(imovel.getId(), ConstantesSistema.LIGACAO_POCO);
						

						// Verificamos se o imovel é medido ou não medido...
						if (ligacaoEsgotoImovelMicro != null) {
							helper.setQuantidadeEconomiasEsgotoMedidas(helper
									.getQuantidadeEconomiasEsgotoMedidas()
									+ quantidadeEconomiasImovel);
							// Caso contrário
						} else {
							helper.setQuantidadeEconomiasEsgotoNaoMedidas(helper
											.getQuantidadeEconomiasEsgotoNaoMedidas()
											+ quantidadeEconomiasImovel);
						}
					}
				}

				// Adicionamos o consumo mínimo do imóvel ao total
				helper.setConsumoMinimoTotal(helper.getConsumoMinimoTotal() + imovel.getConsumoMinimoImovel());
				
				ConsumoHistorico consumoAgua = getControladorConsumoHistorico()
						.buscarConsumoHistoricoPorImovelIdLigacaoTipo(imovel.getId(),ConstantesSistema.LIGACAO_AGUA);
				
				if (consumoAgua != null) {

					// Removemos do total o consumo calculado anteriormente,
					// para
					// logo mais abaixo, adicionamos o novo consumo
					helper.setConsumoLigacaoAguaTotal(helper
							.getConsumoLigacaoAguaTotal()
							+ consumoAgua.getConsumoCobradoMesOriginal());
				}

				ConsumoHistorico consumoEsgoto = getControladorConsumoHistorico()
						.buscarConsumoHistoricoPorImovelIdLigacaoTipo(imovel.getId(),ConstantesSistema.LIGACAO_ESGOTO);
				if (consumoEsgoto != null) {

					// Removemos do total o consumo calculado anteriormente,
					// para
					// logo mais abaixo, adicionamos o novo consumo
					helper.setConsumoLigacaoEsgotoTotal(helper
									.getConsumoLigacaoEsgotoTotal()
									+ consumoEsgoto.getConsumoCobradoMesOriginal());

				}
			}			
		}
		
		return helper;
	}
	
	/**
	 * @author Amelia Pessoa
	 * @date 25/07/2012
	 * Instancia nova Thread e vai fazendo o cálculo da conta
	 */
	public void calcularEmBackground(final ImovelConta imovel, final Boolean proximo) throws ControladorException {		
		new Thread(new Runnable() {
			
			public void run() {
				try {
					calcularConta(imovel,false,proximo);
				} catch (ControladorException e) {
					e.printStackTrace();
				}
				
			}
		}).start();
	}

	@Override
	public void onLocationChanged(Location arg0) {
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	/** [UC0740] [SB0003] - Mudança na ordem de verficação de subsituição de hidrometro e virada de hidromêtro 
	 * 
	 * @author Fernanda Almeida
	 * @date 14/02/2013
	 * 
	 * @param hidrometroInstalado
	 * @param consumo
	 * @param leitura
	 * @param cMedio
	 * @return
	 * @throws ControladorException 
	 */
	public boolean controlaSubstituicaoHidrometro(HidrometroInstalado hidrometroInstalado, ConsumoHistorico consumo, Integer leitura, int cMedio) {

		boolean voltarFluxoPrincipal = false;
		
		// [SB0003] 1.1.
		if (!voltarFluxoPrincipal) {
			int n = hidrometroInstalado.getNumDigitosLeituraHidrometro();
			int _10n = Util.pow(10, n);
			int consumoCalculado = (hidrometroInstalado.getLeitura() + _10n) - obterLeituraAnterior(hidrometroInstalado);

			// [SB0003] 1.2.
			if (((consumoCalculado <= 200) || (consumoCalculado <= 2 * cMedio)) && (consumoCalculado > 0)) {

				// [SB0003] 1.2.1.
				consumo.setConsumoMedidoMes(consumoCalculado);
				consumo.setConsumoCobradoMes(consumoCalculado);
				consumo.setLeituraAtual(leitura);

				int sitAnt = hidrometroInstalado.getCodigoSituacaoLeituraAnterior();
				if (sitAnt == ConstantesSistema.LEITURA_SITU_REALIZADA || 
						sitAnt == ConstantesSistema.LEITURA_SITU_CONFIRMADA) {
					consumo.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_REAL);
				} else {
					consumo.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_ESTIMADO);
				}
				
				ConsumoAnormalidade consumoAnormalidade = new ConsumoAnormalidade(ConstantesSistema.CONSUMO_ANORM_VIRADA_HIDROMETRO);
				consumo.setConsumoAnormalidade(consumoAnormalidade);

				// [SB0003] 1.2.2.
				voltarFluxoPrincipal = true;
			}
		}

		// [SB0003] 2.1.
		if (!voltarFluxoPrincipal) {
			Date dataInstalacao = hidrometroInstalado.getDataInstalacaoHidrometro();

			if (Util.compararData(dataInstalacao, hidrometroInstalado.getDataLeituraAnterior()) >= 0
					&& Util.compararData(dataInstalacao, hidrometroInstalado.getDataLeitura()) <= 0) {

				// [SB0003] 2.1.1.
				int leituraSubstituicaoHidrometro = 0;
				if (hidrometroInstalado.getLeituraHidrometoInstalada() != null) {
					leituraSubstituicaoHidrometro = hidrometroInstalado.getLeituraHidrometoInstalada();
				}
				int leituraCalculada = leitura - leituraSubstituicaoHidrometro;

				if (leituraCalculada < 0) {

					leituraCalculada = 0;
				}

				consumo.setConsumoMedidoMes(leituraCalculada);
				int dias = (int) Util.obterQuantidadeDiasEntreDuasDatasPositivo(hidrometroInstalado.getDataLeitura(), dataInstalacao);

				if (dias > 9 && dias < 60) {
					double resultadoDivisao = Util.arredondar(((double) leituraCalculada / (double) dias), 3);
					consumo.setConsumoCobradoMes((int) Util.arredondar((resultadoDivisao * 30), 0));
					consumo.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_ESTIMADO);
				} else {
					consumo.setConsumoCobradoMes(cMedio);
					consumo.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_MEDIA_HIDR);
				}
				consumo.setLeituraAtual(leitura);
				ConsumoAnormalidade consumoAnormalidade = new ConsumoAnormalidade(ConstantesSistema.CONSUMO_ANORM_HIDR_SUBST_INFO);
				consumo.setConsumoAnormalidade(consumoAnormalidade);

				// [SB0003] 2.1.2.
				voltarFluxoPrincipal = true;
			}
		}
		
		return voltarFluxoPrincipal;
	}
	
}