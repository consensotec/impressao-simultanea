package com.br.ipad.isc.controladores;

import java.util.ArrayList;
import java.util.Date;

import com.br.ipad.isc.bean.ConsumoAnormalidade;
import com.br.ipad.isc.bean.ConsumoAnormalidadeAcao;
import com.br.ipad.isc.bean.ConsumoAnteriores;
import com.br.ipad.isc.bean.ConsumoHistorico;
import com.br.ipad.isc.bean.HidrometroInstalado;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.bean.SistemaParametros;
import com.br.ipad.isc.excecoes.ControladorException;
import com.br.ipad.isc.util.ConstantesSistema;
import com.br.ipad.isc.util.Util;

/**
 * Controlador Conta Postgres
 * Método exclusivos
 * 
 * @author Amelia Pessoa
 * @date 18/07/2012
 */
public class ControladorContaPostgres extends ControladorConta {

	private static ControladorContaPostgres instance;
	
	protected ControladorContaPostgres(){
		super();
	}
	
	public static ControladorContaPostgres getInstance(){
		if ( instance == null ){
			instance =  new ControladorContaPostgres();
		}		
		return instance;		
	}
	
	/**
	 * Proposta: RN2011071073 - 08/11/2011 - Savio Luiz - RM1073 - CAERN
	 * (Calcular o minimo quando a leitura atual for menor que a leitura projetada).
	 * 
	 * [UC0101] - Consistir Leituras e Calcular Consumos
	 * [SB0024] - Recuperar Dados da Tabela de Consumo Anormalidade Acao
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
			HidrometroInstalado hidrometroInstalado, int tipoMedicao, ConsumoAnormalidade consumoAnormalidadeTipo)
					throws ControladorException {
		
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
		
		SistemaParametros sistemaParametros =  SistemaParametros.getInstancia();
		
		int anormConsumo = consumoAnormalidadeTipo.getId();

		int idImovelPerfil = imovel.getCodigoPerfil();

		int categoriaPrincipal = getControladorCategoriaSubcategoria().obterCategoriaPrincipal(imovel.getId()); 

		ArrayList<ConsumoAnormalidadeAcao> colecao = getControladorConsumoAnormalidadeAcao().buscarConsumoAnormalidadeAcao(idImovelPerfil, anormConsumo,
				categoriaPrincipal);
		ConsumoAnormalidadeAcao consumoAnormalidadeAcao= null;
		if (colecao!=null) {
			consumoAnormalidadeAcao = colecao.get(0);
		}

		if (consumoAnormalidadeAcao != null) {
			Integer idLeituraAnormalidadeConsumo = null;
			Double numerofatorConsumo = null;
			String mensagemContaPrimeiroMes = consumoAnormalidadeAcao.getMensagemConta();
			String mensagemContaSegundoMes = consumoAnormalidadeAcao.getMensagemContaSegundoMes();
			String mensagemContaTerceiroMes = consumoAnormalidadeAcao.getMensagemContaTerceiroMes();

			int anoMes = Util.subtrairMesDoAnoMes(imovel.getAnoMesConta(), 1);

			ConsumoAnteriores reg3MesAnterior = getControladorConsumoAnteriores(). 
					buscarConsumoAnterioresPorImovelAnormalidade(imovel.getId(), anormConsumo, anoMes);

			ConsumoAnteriores reg3SegundoMesAnterior = null;
			ConsumoAnteriores reg3TerceiroMesAnterior = null;

			if (reg3MesAnterior == null || reg3MesAnterior.equals("")) {
				idLeituraAnormalidadeConsumo = consumoAnormalidadeAcao.getIdLeituraAnormalidadeConsumo();
				numerofatorConsumo = consumoAnormalidadeAcao.getFatorConsumo().doubleValue();
				consumo.setNumeroMesMotivoRevisao(1);

				if (mensagemContaPrimeiroMes != null) {
					String[] mensagem = null;
					if (sistemaParametros.getCodigoEmpresaFebraban().equals(
							ConstantesSistema.CODIGO_FEBRABAN_CAERN)) {
						mensagem = Util.dividirString(mensagemContaPrimeiroMes, 60);
					} else {
						mensagem = Util.dividirString(mensagemContaPrimeiroMes, 40);
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
			} else {
				anoMes = Util.subtrairMesDoAnoMes(imovel.getAnoMesConta(), 2);
				reg3SegundoMesAnterior = getControladorConsumoAnteriores(). 
						buscarConsumoAnterioresPorImovelAnormalidade(imovel.getId(), anormConsumo, anoMes);

				if (reg3SegundoMesAnterior == null || reg3SegundoMesAnterior.equals("")) {
					idLeituraAnormalidadeConsumo = consumoAnormalidadeAcao.getIdLeituraAnormalidadeConsumoSegundoMes();
					numerofatorConsumo = consumoAnormalidadeAcao.getFatorConsumoSegundoMes().doubleValue();
					consumo.setNumeroMesMotivoRevisao(2);

					if (mensagemContaSegundoMes != null) {
						String[] mensagem = null;
						if (sistemaParametros.getCodigoEmpresaFebraban().equals(
								ConstantesSistema.CODIGO_FEBRABAN_CAERN)) {
							mensagem = Util.dividirString(mensagemContaSegundoMes, 60);
						} else {
							mensagem = Util.dividirString(mensagemContaSegundoMes, 40);
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
				} else {
					anoMes = Util.subtrairMesDoAnoMes(imovel.getAnoMesConta(), 3);
					reg3TerceiroMesAnterior = getControladorConsumoAnteriores(). 
							buscarConsumoAnterioresPorImovelAnormalidade(imovel.getId(), anormConsumo, anoMes);

					if (reg3TerceiroMesAnterior == null || reg3TerceiroMesAnterior.equals("")) {
						idLeituraAnormalidadeConsumo = consumoAnormalidadeAcao.getIdLeituraAnormalidadeConsumoTerceiroMes();
						numerofatorConsumo = consumoAnormalidadeAcao.getFatorConsumoTerceiroMes().doubleValue();
						consumo.setNumeroMesMotivoRevisao(3);

						if (mensagemContaTerceiroMes != null) {
							String[] mensagem = null;
							if (sistemaParametros.getCodigoEmpresaFebraban().equals(
									ConstantesSistema.CODIGO_FEBRABAN_CAERN)) {
								mensagem = Util.dividirString(mensagemContaTerceiroMes, 60);
							} else {
								mensagem = Util.dividirString(mensagemContaTerceiroMes, 40);
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
					} else {
						// Caso tenha ocorrido no terceiro mês anterior em diante
						// o sistema verifica se o indicador de cobrar consumo normal = 1 
						if(ConstantesSistema.SIM_SHORT.equals(consumoAnormalidadeAcao.getIndicadorCobrancaConsumoNormal())) {
							idLeituraAnormalidadeConsumo = ConstantesSistema.NORMAL; 
						} else {
							idLeituraAnormalidadeConsumo = consumoAnormalidadeAcao.getIdLeituraAnormalidadeConsumoTerceiroMes();
							numerofatorConsumo = consumoAnormalidadeAcao.getFatorConsumoTerceiroMes().doubleValue();
							consumo.setNumeroMesMotivoRevisao(3);

							if (mensagemContaTerceiroMes != null) {
								String[] mensagem = null;
								if (sistemaParametros.getCodigoEmpresaFebraban().equals(
										ConstantesSistema.CODIGO_FEBRABAN_CAERN)) {
									mensagem = Util.dividirString(mensagemContaTerceiroMes, 60);
								} else {
									mensagem = Util.dividirString(mensagemContaTerceiroMes, 40);
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
						}
					}
				}
			}

			// Atualiza objeto
			ControladorBasico.getInstance().atualizar(imovel);

			// 3.1.1.1. O sistema gera a Anormalidade de Consumo com o valor
			// correspondente a estouro de consumo da tabela
			// CONSUMO_ANORMALIDADE
			consumo.setConsumoAnormalidade(consumoAnormalidadeTipo);

			if (idLeituraAnormalidadeConsumo != null && idLeituraAnormalidadeConsumo == ConstantesSistema.NAO_OCORRE) {

				consumo.setConsumoCobradoMes(cMedio);
				consumo.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_MEDIA_HIDR);

			} else if (idLeituraAnormalidadeConsumo != null && idLeituraAnormalidadeConsumo == ConstantesSistema.MINIMO) {

				// O Consumo a Ser Cobrado no Mes sera o valor retornado por [UC0105 - Obter Consumo Minimo da Ligacao]
				consumo.setConsumoCobradoMes(imovel.getConsumoMinimoImovel());
				// Seta o tipo de consumo
				consumo.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_SEM);

				// 1.1.5
			} else if ( idLeituraAnormalidadeConsumo != null && idLeituraAnormalidadeConsumo == ConstantesSistema.MEDIA) {

				// 1.1.1.2.3.1. Caso a anormalidade de consumo informada seja ALTO CONSUMO (6) ou ESTOURO DE CONSUMO (5),
				// entao o Consumo a Ser Cobrado no Mes sera o maior entre o consumo medio do hidrometro multiplicado 
				// pelo fator de multiplicacao da quantidade de vezes da media (CSAA_NNFATORCONSUMOMES (1,2,3) 
				// dependendo do mes calculado anteriormente) e o Consumo Total de Referencia.
				if ((anormConsumo == ConstantesSistema.CONSUMO_ANORM_ALTO_CONSUMO || anormConsumo == 
						ConstantesSistema.CONSUMO_ANORM_ESTOURO) && numerofatorConsumo != null){
					int consumofaturadoMesInt = Util.arredondar(cMedio * numerofatorConsumo);
					
					if (imovel.getConsumoEstouro() > consumofaturadoMesInt) {
						consumo.setConsumoCobradoMes(imovel.getConsumoEstouro());
					} else {
						consumo.setConsumoCobradoMes(consumofaturadoMesInt);
					}
				}else{
					consumo.setConsumoCobradoMes(cMedio);
					consumo.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_MEDIA_HIDR);
				}				
				
			} else if (idLeituraAnormalidadeConsumo != null && idLeituraAnormalidadeConsumo == ConstantesSistema.NORMAL) {

				// Fazer nada ja calculado

			} else if (idLeituraAnormalidadeConsumo != null && idLeituraAnormalidadeConsumo == ConstantesSistema.MAIOR_ENTRE_O_CONSUMO_MEDIO) {

				if (cMedio > consumo.getConsumoCobradoMes()) {
					consumo.setConsumoCobradoMes(cMedio);
					consumo.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_MEDIA_HIDR);
				}

			} else if (idLeituraAnormalidadeConsumo != null && idLeituraAnormalidadeConsumo == ConstantesSistema.MENOR_ENTRE_O_CONSUMO_MEDIO) {
				if (cMedio < consumo.getConsumoCobradoMes()) {
					consumo.setConsumoCobradoMes(cMedio);
					consumo.setTipoConsumo(ConstantesSistema.CONSUMO_TIPO_MEDIA_HIDR);
				}
			}

			// 3.1.4. O consumo a Ser Cobrado no Mes sera igual
			// ao Consumo a Ser Cobrado no Mes multiplicado pelo
			// fator de multiplicacao da quantidade de vezes a media
			// (CSAA_NNFATORCONSUMOMES(1,2 ou 3), dependendo do mes
			// calculado anteriormente
			if (numerofatorConsumo != null
					&& idLeituraAnormalidadeConsumo != null && idLeituraAnormalidadeConsumo != ConstantesSistema.MEDIA) {
				double consumofaturadoMes = consumo.getConsumoCobradoMes();
				consumofaturadoMes = consumofaturadoMes * numerofatorConsumo;
				int consumofaturadoMesInt = Util.arredondar(consumofaturadoMes);
				consumo.setConsumoCobradoMes(consumofaturadoMesInt);
			}
		}
	}

	/** [UC0740] [SB003] - Mudança na ordem de verficação de subsituição de hidrometro e virada de hidromêtro 
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
		
		return voltarFluxoPrincipal;
	}
}
