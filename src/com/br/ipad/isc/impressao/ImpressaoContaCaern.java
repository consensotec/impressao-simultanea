
package com.br.ipad.isc.impressao;

import java.util.ArrayList;
import java.util.Date;

import com.br.ipad.isc.bean.CategoriaSubcategoria;
import com.br.ipad.isc.bean.ConsumoAnormalidade;
import com.br.ipad.isc.bean.ConsumoAnormalidadeAcao;
import com.br.ipad.isc.bean.ConsumoAnteriores;
import com.br.ipad.isc.bean.ConsumoHistorico;
import com.br.ipad.isc.bean.ContaCategoria;
import com.br.ipad.isc.bean.ContaCategoriaConsumoFaixa;
import com.br.ipad.isc.bean.ContaDebito;
import com.br.ipad.isc.bean.ContaImposto;
import com.br.ipad.isc.bean.CreditoRealizado;
import com.br.ipad.isc.bean.DebitoCobrado;
import com.br.ipad.isc.bean.HidrometroInstalado;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.bean.QualidadeAgua;
import com.br.ipad.isc.bean.SistemaParametros;
import com.br.ipad.isc.excecoes.ControladorException;
import com.br.ipad.isc.util.ConstantesSistema;
import com.br.ipad.isc.util.Util;

/**
 * Classe que irá gera a string de retorno para impressão da conta ou da
 * notificação de débito
 *
 * @author Sergio Rolanski
 * @date 26/03/2010
 */
public class ImpressaoContaCaern extends ImpressaoCaern {
    
    private static ImpressaoContaCaern instancia;
     
    private ImpressaoContaCaern() { }

    public static ImpressaoContaCaern getInstancia(
        ImovelConta imovelInformado) {
    	if (instancia == null) {
        instancia = new ImpressaoContaCaern();
    	}
    	
    	instancia.imovel = imovelInformado;    	
    	return instancia;
    }

    public StringBuilder imprimirConta() {
   
	    try {
	        buffer = new StringBuilder();
	
	        // Tamanho da folha / unidade de medida / Line Feed como fim de comando
	        
    	    // Configuracao do papel
	   		if ( SistemaParametros.getInstancia().getContrasteConta() != 0 )		   		
	   			appendTexto("! 0 200 200 225 1\nIN-MILLIMETERS CONTRAST " + SistemaParametros.getInstancia().getContrasteConta() + "\nLT LF\n");
	   		else 
	   			appendTexto("! 0 200 200 225 1\nIN-MILLIMETERS\nLT LF\n");  	        
		        
	        // Escritório de atendimento
	        final int yEscAtendimento = 6;
	        
	        String endereco = Util.replaceAll(imovel.getEnderecoAtendimento()
	            .trim().toUpperCase(), " -   -", ",");
	        appendTextos(0, 0, 76, yEscAtendimento, endereco, 26, 2);
	        appendTexto70(76, yEscAtendimento + 5, SistemaParametros.getInstancia().getTelefone0800().toUpperCase());
	        appendTexto70(76, yEscAtendimento + 8,
	            formatarTelefone(imovel.getTelefoneLocalidadeDDD().trim().toUpperCase()));
	
	        // Título da conta
	        appendTexto("CENTER\n");
	        appendTexto70(0, 19, "CONTA DE CONSUMO DE AGUA/ESGOTO E SERVICOS");
	        
		    // Data e Hora de Impressão
		    Date date = new Date();
		    String data = Util.dateToString(date);
		    String hora = Util.dateToHoraString(date);
		    
		    appendTexto("LEFT\n");
		    appendTexto70(3, 23, "IMPRESSO EM "+data+" " + "AS "+hora);
	
	        // Matrícula / Referência
	        final int yDados = 29;
	        appendTexto70(58, yDados, Integer.toString(imovel.getId()));
	        appendTexto70(80, yDados, Util.formatarAnoMesParaMesAno(imovel
	            .getAnoMesConta().toString()));
	
	        // Dados do cliente
	        final int yNomeEndereco = 35;
	        appendTexto70(5, yNomeEndereco, imovel.getNomeUsuario());
	        appendTextos(7, 0, 5, yNomeEndereco + 3, cortarEndereco(imovel.getEndereco()), 3);
	
	        // Inscrição / Rota / Sequência da rota
	        final int yInsRotaSeq = 48;
	        appendTexto70(5, yInsRotaSeq, fachada.formatarInscricao(imovel.getInscricao()));
	        appendTexto70(42, yInsRotaSeq, Integer.toString(imovel.getCodigoRota()));
	        appendTexto70(53, yInsRotaSeq, Integer.toString(imovel.getSequencialRota()));
	
	        // Economias        
	        ArrayList<CategoriaSubcategoria> arrayListCategoriaSubcategoria = new ArrayList<CategoriaSubcategoria>();
			arrayListCategoriaSubcategoria = (ArrayList<CategoriaSubcategoria>) 
								fachada.buscarCategoriaSubcategoriaPorImovelId(imovel.getId());
			
	        int[] economias = getNumeroEconomias(arrayListCategoriaSubcategoria);
	        for (int i = 0, xEconomia = 65; i < economias.length; i++, xEconomia += 10)
	        if (economias[i] > 0)
	            appendTexto70(xEconomia, 49, Integer.toString(economias[i]));
	
	        // Extrai informções usadas nas próximas impressões
	        String hidrometro = "SEM MEDIDOR";
	        String situacaoAgua = fachada.getDescricaoSitLigacaoAgua(imovel.getSituacaoLigAgua());
	        String situacaoEsgoto = fachada.getDescricaoSitLigacaoEsgoto(imovel.getSituacaoLigEsgoto());
	        String leituraAnterior = null;
	        Integer leituraAtual = null;
	        Integer consumo = null;
	        Integer diasConsumo = null;
	        
	        Integer consumoRateioAgua = null;
	        Integer consumoRateioEsgoto = null;
	   
	        ConsumoHistorico consumoAgua = null;
	        ConsumoHistorico consumoEsgoto = null;	        
	
	        HidrometroInstalado hidrometroInstaladoAgua = fachada.
	        		buscarHidrometroInstaladoPorImovelTipoMedicao(imovel.getId(), ConstantesSistema.LIGACAO_AGUA);
	        
	        HidrometroInstalado hidrometroInstaladoPoco = fachada.
	        		buscarHidrometroInstaladoPorImovelTipoMedicao(imovel.getId(), ConstantesSistema.LIGACAO_ESGOTO);
	        	        
	        String dataLeitura = null;
	
	        consumoAgua = fachada.buscarConsumoHistoricoPorImovelIdTipoLigacao(imovel.getId(), ConstantesSistema.LIGACAO_AGUA);
	        consumoEsgoto = fachada.buscarConsumoHistoricoPorImovelIdTipoLigacao(imovel.getId(), ConstantesSistema.LIGACAO_ESGOTO);
	        
	        // Prioridade para as informações de água
	        if (hidrometroInstaladoAgua != null) {
		       
	        	hidrometro = hidrometroInstaladoAgua.getNumeroHidrometro();
		        leituraAnterior = fachada.obterLeituraAnterior(hidrometroInstaladoAgua)+"";
		        
		        dataLeitura = Util.dateToString(hidrometroInstaladoAgua.getDataLeitura());		        
	
		        if (consumoAgua != null) {
		            if (hidrometroInstaladoAgua.getLeituraAtualFaturamento() != null) {
		            	leituraAtual = hidrometroInstaladoAgua.getLeituraAtualFaturamento();
		            	consumo = consumoAgua.getConsumoCobradoMes();
		            	diasConsumo = consumoAgua.getDiasConsumo();
		            } else {
						if (consumoAgua.getLeituraAtual() != null) {
							leituraAtual = consumoAgua.getLeituraAtual();
						}

						consumo = consumoAgua.getConsumoCobradoMes();
						if (hidrometroInstaladoAgua.getQtdDiasAjustado() != null) {
							diasConsumo = hidrometroInstaladoAgua.getQtdDiasAjustado();
						} else {
							diasConsumo = consumoAgua.getDiasConsumo();
						}
		            }

		            if (consumoAgua.getConsumoRateio() != null) {
		            	consumoRateioAgua = consumoAgua.getConsumoRateio();
		            }
		        }
	
		        if (consumoEsgoto != null && consumoEsgoto.getConsumoRateio() != null) {
		            consumoRateioEsgoto = consumoEsgoto.getConsumoRateio();  
		        }
	
	        } else if (hidrometroInstaladoPoco != null) {
	        	//consumoEsgoto = fachada.buscarConsumoHistoricoPorImovelIdTipoLigacao(imovel.getId(), ConstantesSistema.LIGACAO_POCO);
	        	hidrometro = hidrometroInstaladoPoco.getNumeroHidrometro();
	        	leituraAnterior = hidrometroInstaladoPoco.getLeituraAnteriorFaturamento()+"";
	        	dataLeitura = Util.dateToString(hidrometroInstaladoPoco.getDataLeitura());
	
		        if (consumoEsgoto != null) {
		
		            if (hidrometroInstaladoPoco.getLeituraAtualFaturamento() != null) {
			            leituraAtual = hidrometroInstaladoPoco.getLeituraAtualFaturamento();
			            consumo = consumoEsgoto.getConsumoCobradoMes();
			            diasConsumo = hidrometroInstaladoPoco.getQtdDiasAjustado();
		            } else {
			            leituraAtual = consumoEsgoto.getLeituraAtual();
			            consumo = consumoEsgoto.getConsumoCobradoMes();
				            
						if (hidrometroInstaladoPoco.getQtdDiasAjustado() != null) {
							diasConsumo = hidrometroInstaladoPoco.getQtdDiasAjustado();
						} else {
							diasConsumo = consumoEsgoto.getDiasConsumo();

						}
					}
		
		            if (consumoEsgoto.getConsumoRateio() != null) {
		            	consumoRateioEsgoto = consumoEsgoto.getConsumoRateio();
		            }
		        }
	        } else if (hidrometroInstaladoAgua == null && hidrometroInstaladoPoco == null) {
				if (consumoAgua != null) {
					if (consumoAgua.getLeituraAtual() != null) {
						leituraAtual = consumoAgua.getLeituraAtual();
					}
					consumo = consumoAgua.getConsumoCobradoMes();
					diasConsumo = consumoAgua.getDiasConsumo();
					leituraAnterior = "";

					if (consumoAgua.getConsumoRateio() != null) {
						consumoRateioAgua = consumoAgua.getConsumoRateio();
					}
				} else {
					if (consumoEsgoto != null) {
						if (consumoEsgoto.getLeituraAtual() != null) {
							leituraAtual = consumoEsgoto.getLeituraAtual();
						}
						consumo = consumoEsgoto.getConsumoCobradoMes();
						diasConsumo = consumoEsgoto.getDiasConsumo();
					}

					if (consumoEsgoto != null && consumoEsgoto.getConsumoRateio() != null) {
						consumoRateioEsgoto = consumoEsgoto.getConsumoRateio();
					}
				}
			}
	
	        // Hidrômetro / Situação da agua / Situação da esgoto
	        final int yHdAgEs = 55;
	        appendTexto70(10, yHdAgEs, hidrometro);
	        appendTexto70(45, yHdAgEs, situacaoAgua);
	        appendTexto70(78, yHdAgEs, situacaoEsgoto);
	
	        int yCorpoConta = 60;
	        
	        // Consumo Agua / Consumo Rateio Agua / Consumo Rateio Esgoto
	        appendTexto70(5, yCorpoConta, "CONSUMO ÁGUA (M3): " + consumo);

	        if(imovel.getIndcCondominio().equals(ConstantesSistema.SIM) || imovel.getMatriculaCondominio()!=null){
				if (consumoRateioAgua != null) {
					appendTexto70(5, yCorpoConta + 3,
							"CONSUMO RATEIO ÁGUA (M3): " + consumoRateioAgua);
				}
				if (consumoRateioEsgoto != null) {
					appendTexto70(5, yCorpoConta + 6,
							"VOLUME RATEIO ESGOTO (M3): " + consumoRateioEsgoto);
				}
			}
	
	        // Data de leitura atual / Leitura Atual / Leitura Anterior / Dias
	        // consumo
	        appendTexto70(58, yCorpoConta, "DATA LEITURA:  " + (dataLeitura != null?dataLeitura:""));
	        appendTexto70(58, yCorpoConta + 3, "LEIT. ATUAL:   " + (leituraAtual != null?leituraAtual:""));
	        appendTexto70(58, yCorpoConta + 6, "LEIT. ANT.:    " + (leituraAnterior != null?leituraAnterior:""));
	        appendTexto70(58, yCorpoConta + 9, "DIAS CONSUMO:  " + (diasConsumo != null?diasConsumo:""));
	
	        yCorpoConta = gerarHistorico(hidrometroInstaladoAgua, hidrometroInstaladoPoco, yCorpoConta += 12) + 10;

	        double valorTotalConta = fachada.obterValorConta(imovel.getId()); 
	        //valorTotalConta = fachada.obterValorTotal(imovel.getId(), ConstantesSistema.LIGACAO_AGUA);
			//valorTotalConta += fachada.obterValorTotal(imovel.getId(), ConstantesSistema.LIGACAO_POCO);

			boolean emitirCarta = verificarEmitirCarta();
			if (emitirCarta) {
				emitirCarta(yCorpoConta, leituraAtual);
			} else {
				// Lançamentos - 82
				gerarLancamentosConta(yCorpoConta);

				// Tributos
				gerarTributosConta(valorTotalConta);
			}

	        // Mensagens
	        final int limiteMensagem = 66;
	        final int yMensagem = 162;

	        if (imovel.getMensagemContaAnormalidade1() != null && !imovel.getMensagemContaAnormalidade1().equals("")) {
	            appendTexto70(3, yMensagem,     substring(imovel.getMensagemContaAnormalidade1(), 0, limiteMensagem));
	            appendTexto70(3, yMensagem + 3, substring(imovel.getMensagemContaAnormalidade2(), 0, limiteMensagem));
	            appendTexto70(3, yMensagem + 6, substring(imovel.getMensagemContaAnormalidade3(), 0, limiteMensagem));
			} else {
	            if(valorTotalConta > ConstantesSistema.VALOR_LIMITE_CONTA){
	                appendTexto70(3, yMensagem, "Informamos que se encontra disponível no site www.caern.com.br, ");
	                appendTexto70(3, yMensagem + 3, "a sua ficha de compensação bancária.");
					appendTexto70(3, yMensagem + 6, substring(imovel.getMensagemConta3(), 0, limiteMensagem));
				} else {
	                appendTexto70(3, yMensagem, substring(imovel.getMensagemConta1(), 0, limiteMensagem));
	                appendTexto70(3, yMensagem + 3, substring(imovel.getMensagemConta2(), 0, limiteMensagem));
	                appendTexto70(3, yMensagem + 6, substring(imovel.getMensagemConta3(), 0, limiteMensagem));
	            }
	        }

			// Qualidade da água
			final int yQualidadeAgua = 181;

			QualidadeAgua qualidadeAgua = fachada.buscarQualidadeAguaPorLocalidadeSetorComercial(
					imovel.getIdLocalidade(), imovel.getIdSetorComercial());

			if (qualidadeAgua == null) {
				qualidadeAgua = fachada.buscarQualidadeAguaPorLocalidade(imovel.getIdLocalidade());
			}

			if (qualidadeAgua == null) {
				qualidadeAgua = fachada.buscarQualidadeAguaSemLocalidade();
			}

	        // Constantes
			if (qualidadeAgua != null) {
				appendTexto70(27, yQualidadeAgua, VerificarString(qualidadeAgua.getNumeroTurbidez())); // Turbidez
				appendTexto70(42, yQualidadeAgua, VerificarString(qualidadeAgua.getNumeroPh())); // PH

				if (qualidadeAgua.getNumeroColiformesTotais() == null) {
					appendTexto70(57, yQualidadeAgua, VerificarString(qualidadeAgua.getNumeroColiformesTotais())); // Colif. Totais
				} else {
					appendTexto70(57, yQualidadeAgua, qualidadeAgua.getNumeroColiformesTotais() + " %"); // Colif. Totais
				}

				appendTexto70(74, yQualidadeAgua, VerificarString(qualidadeAgua.getNumeroCloroResidual())); // Cloro
				appendTexto70(90, yQualidadeAgua, VerificarString(qualidadeAgua.getNumeroNitrato())); // Nitrato
			}

			if(emitirCarta) {
				if(!isNullOrEmpty(imovel.getCodigoAgencia())) {
					final int y = 192;
			        appendTexto("CENTER\n");
		        	appendTexto70(0, y, "NÃO SERÁ DEBITADO EM CONTA CORRENTE");
		            appendTexto("LEFT\n");
				}
			} else {
				// Código de barras / linha digitável
				gerarCodigoBarrasLinhaDigitavel(valorTotalConta);
			}

	        // Canhoto
	        final int yInfCanhoto = 216;
	        appendTexto70(5, yInfCanhoto, Integer.toString(imovel.getId()));
	        appendTexto70(29, yInfCanhoto, Util.formatarAnoMesParaMesAno(imovel.getAnoMesConta().toString()));
	        if(!emitirCarta) {
		        appendTexto70(54, yInfCanhoto, formatarData(imovel.getDataVencimento()));
		        appendTexto70(79, yInfCanhoto, Util.formatarDoubleParaMoedaReal(valorTotalConta));
	        }

	        // Comando que faz a impressão
	        appendTexto(comandoImpressao());
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        //Util.mostrarErro("Erro na impressão...", ex);
	    } 	

	    return buffer;
    }

    /**
     * Caso tenha faturamento de agua, adiciona as linhas correspondentes no
     * buffer.
     *
     * @return Retorna o valor y da ultima linha jogada no buffer
     */
    private int gerarLancamentoAgua(int yInicial) {
	    SistemaParametros sistemaParametros = SistemaParametros.getInstancia();
	    boolean tipoTarifaPorCategoria = sistemaParametros.getIndcTarifaCatgoria().equals(ConstantesSistema.SIM);

	    final int xEsquerda = 10, xMeio = 75, xDireita = 95, alturaLinha = 3;

	    //Vector registros2 = imovel.getRegistros2();

	    ArrayList<CategoriaSubcategoria> arrayListCategoriaSubcategoria = (ArrayList<CategoriaSubcategoria>) 
	    		fachada.buscarCategoriaSubcategoriaPorImovelId(imovel.getId());

	    if (arrayListCategoriaSubcategoria != null) {
	       
	    	ConsumoHistorico consumoAgua = null;
	    	
			consumoAgua = fachada.
					buscarConsumoHistoricoPorImovelIdTipoLigacao(imovel.getId(), ConstantesSistema.LIGACAO_AGUA);
			
	    	
	        appendTexto70(5, yInicial, "AGUA");
	        
	        yInicial += alturaLinha;
	
	        for (int i = 0; i < arrayListCategoriaSubcategoria.size(); i++) {
	        CategoriaSubcategoria dadosEconomiasSubcategorias = (CategoriaSubcategoria) arrayListCategoriaSubcategoria.get(i);
	        
	        ContaCategoria contaCategoriaAgua = new ContaCategoria();
			
			ArrayList<ContaCategoriaConsumoFaixa> arrayListContaCategoriaConsumoFaixa = new ArrayList<ContaCategoriaConsumoFaixa>();
	
			contaCategoriaAgua = fachada.buscarContaCategoriaPorCategoriaSubcategoriaId
								(dadosEconomiasSubcategorias.getId(), ConstantesSistema.LIGACAO_AGUA);
			
			if (contaCategoriaAgua == null)
	            continue;
			
			arrayListContaCategoriaConsumoFaixa = fachada.
					buscarContasCategoriasConsumosFaixasPorPorContaCategoriaId(contaCategoriaAgua.getId());
	
	        int quantidaEconomias = 0;
	        
	        if (dadosEconomiasSubcategorias.getFatorEconomiaCategoria() != null 
	        		 && dadosEconomiasSubcategorias.getFatorEconomiaCategoria() > 0 )
	            quantidaEconomias = Integer.parseInt(dadosEconomiasSubcategorias.getFatorEconomiaCategoria().toString());
	        else
	            quantidaEconomias = dadosEconomiasSubcategorias.getQtdEconomiasSubcategoria();
	
	        if (tipoTarifaPorCategoria) {
	            appendTexto70(xEsquerda, yInicial,
	                dadosEconomiasSubcategorias.getDescricaoCategoria()
	                    + " " + quantidaEconomias + " UNIDADE(S)");
	            yInicial += alturaLinha;
	        } else {
	            appendTexto70(xEsquerda, yInicial,
	                dadosEconomiasSubcategorias
	                    .getDescricaoAbreviadaSubcategoria()
	                    + " " + quantidaEconomias + " UNIDADE(S)");
	            yInicial += alturaLinha;
	        }
	
	        int consumoMinimo = 0;
	        if (imovel.getConsumoMinAgua() != null && imovel.getConsumoMinAgua() > imovel
	            .getConsumoMinimoImovel())
	            consumoMinimo = imovel.getConsumoMinAgua();
	        else
	            consumoMinimo = imovel.getConsumoMinimoImovel();
	
	        if (consumoAgua == null
	            && contaCategoriaAgua != null
	            && contaCategoriaAgua.getNumConsumo() <= consumoMinimo) {
	            appendTexto70(xEsquerda, yInicial, "TARIFA MINIMA "
	                + contaCategoriaAgua
	                    .getValorTarifaMinima().doubleValue() / quantidaEconomias
	                + " POR UNIDADE");
	            appendTexto70(xMeio, yInicial, true, consumoMinimo + " M3");
	            appendTexto70(
	                xDireita,
	                yInicial,
	                true,
	                Util
	                    .formatarDoubleParaMoedaReal(contaCategoriaAgua
	                        .getValorTarifaMinima().doubleValue()));
	            yInicial += alturaLinha;
	        } else if (contaCategoriaAgua != null
	            && arrayListContaCategoriaConsumoFaixa  != null
	            && arrayListContaCategoriaConsumoFaixa .size() > 0)
	            if (arrayListCategoriaSubcategoria.size() > 1) {
	            appendTexto70(xEsquerda, yInicial,
	                "CONSUMO ACUMULADO DAS FAIXAS");
	            appendTexto70(xMeio, yInicial, true,
	                ((int) contaCategoriaAgua.getNumConsumo().doubleValue())
	                    + " M3");
	            appendTexto70(
	                xDireita,yInicial,true,
	                Util.formatarDoubleParaMoedaReal(contaCategoriaAgua.getValorFaturado().doubleValue()));
	            yInicial += alturaLinha;
	            } else {
	            appendTexto70(
	                xEsquerda,
	                yInicial,
	                "ATE "
	                    + ((int) contaCategoriaAgua.getNumConsumoMinimo() / quantidaEconomias)
	                    + " M3 - "
	                    + Util
	                        .formatarDoubleParaMoedaReal(contaCategoriaAgua.getValorTarifaMinima().doubleValue() / quantidaEconomias)
	                    + " POR UNIDADE");
	            appendTexto70(xMeio, yInicial, true, contaCategoriaAgua .getNumConsumoMinimo() + " M3");
	            appendTexto70( xDireita, yInicial, true, Util
	                    .formatarDoubleParaMoedaReal(contaCategoriaAgua
	                        .getValorTarifaMinima().doubleValue()));
	            yInicial += alturaLinha;
	
	           
	            for (int j = 0; j < arrayListContaCategoriaConsumoFaixa.size(); j++, yInicial += alturaLinha) {
	            	ContaCategoriaConsumoFaixa faixa = (ContaCategoriaConsumoFaixa) arrayListContaCategoriaConsumoFaixa.get(j);
	
	                if (faixa.getNumConsumoFinal().intValue() == ConstantesSistema.LIMITE_SUPERIOR_FAIXA_FINAL) {
	                appendTexto70(
	                    xEsquerda,
	                    yInicial,
	                    "ACIMA DE "
	                        + (faixa
	                            .getNumConsumoInicial() - 1)
	                        + " M3 - R$ "
	                        + Util
	                            .formatarDoubleParaMoedaReal(faixa
	                                .getValorTarifa().doubleValue())
	                        + " POR M3");
	                appendTexto70(xMeio, yInicial, true, faixa
	                    .getNumConsumo()
	                    * quantidaEconomias + " M3");
	                appendTexto70(xDireita, yInicial, true, Util
	                    .formatarDoubleParaMoedaReal(faixa.getValorFaturado().doubleValue()
	                        * quantidaEconomias));
	                } else {
	                appendTexto70(
	                    xEsquerda,
	                    yInicial,
	                    faixa.getNumConsumoInicial()
	                        + " M3 A "
	                        + faixa.getNumConsumoFinal()
	                        + " M3 - R$ "
	                        + Util
	                            .formatarDoubleParaMoedaReal(faixa
	                                .getValorTarifa().doubleValue())
	                        + " POR M3");
	                appendTexto70(xMeio, yInicial, true, faixa
	                    .getNumConsumo()
	                    * quantidaEconomias + " M3");
	                appendTexto70(xDireita, yInicial, true, Util
	                    .formatarDoubleParaMoedaReal(faixa
	                        .getValorFaturado().doubleValue()
	                        * quantidaEconomias));
	                }
	            }
	            }
	        else if (contaCategoriaAgua != null) {
	            appendTexto70(xEsquerda, yInicial, "CONSUMO DE AGUA");
	            appendTexto70(xMeio, yInicial, true,
	                ((int) contaCategoriaAgua.getNumConsumo())
	                    + " M3");
	            appendTexto70(
	                xDireita,
	                yInicial,
	                true,
	                Util
	                    .formatarDoubleParaMoedaReal(contaCategoriaAgua.getValorFaturado().doubleValue()));
	            yInicial += alturaLinha;
	        }
	        }
	    }
	
	    return yInicial;
    }

    /**
     * Caso tenha faturamento de agua, adiciona as linhas correspondentes no
     * buffer.
     *
     * @return Retorna o valor y da ultima linha jogada no buffer
     */
    private int gerarLancamentoEsgoto(int yInicial) {
	    double valorEsgoto = 0d;
	    final int xEsquerda = 10, xDireita = 95, alturaLinha = 3;

	    ArrayList<CategoriaSubcategoria> arrayListaCategoriaSubcategoria = new ArrayList<CategoriaSubcategoria>();
		arrayListaCategoriaSubcategoria = (ArrayList<CategoriaSubcategoria>) fachada.buscarCategoriaSubcategoriaPorImovelId(imovel.getId());
		
	    
	    if (arrayListaCategoriaSubcategoria != null) {
	        for (int i = 0; i < arrayListaCategoriaSubcategoria.size(); i++) {
	        	
	        CategoriaSubcategoria categoriaSubcategoria = (CategoriaSubcategoria) arrayListaCategoriaSubcategoria.get(i);
	        Integer categoriaSubcategoriaId =  categoriaSubcategoria.getId();
			ContaCategoria contaCategoriaPoco = new ContaCategoria();
			
			contaCategoriaPoco = fachada.buscarContaCategoriaPorCategoriaSubcategoriaId
					(categoriaSubcategoriaId, ConstantesSistema.LIGACAO_POCO);
				
			
	
	        if (contaCategoriaPoco != null
	            && contaCategoriaPoco.getValorFaturado() != null)
	            valorEsgoto += contaCategoriaPoco.getValorFaturado().doubleValue();
	        }
	
	        if (valorEsgoto > 0) {
		        appendTexto70(5, yInicial, "ESGOTO");
		        yInicial += alturaLinha;
		
		        appendTexto70(xEsquerda, yInicial, imovel
		            .getPercentCobrancaEsgoto()
		            + "% DO VALOR DE ÁGUA");
		        appendTexto70(xDireita, yInicial, true,
		        		Util.formatarDoubleParaMoedaReal(valorEsgoto));
		        yInicial += alturaLinha;
	        }
	    }
	
	    return yInicial;
    }

    /**
     * Caso tenha serviços de debito ou credito, adiciona as linhas
     * correspondentes no buffer. Primeiro sao adicionada linhas de debito
     * seguidas das linhas de credito
     *
     * @return Retorna o valor y da ultima linha jogada no buffer
     */
    private int gerarLancamentoServicos(int yInicial) {
    	
    	final int xEsquerda = 5, xDireita = 95, alturaLinha = 3;

	    // Debitos
	    //Vector registros4 = imovel.getRegistros4();
    	
    	ArrayList<DebitoCobrado> arrayListDebitoCobrado = new ArrayList<DebitoCobrado>();
		arrayListDebitoCobrado = (ArrayList<DebitoCobrado>) fachada.buscarDebitoCobradoPorImovelId(imovel.getId());
		
    	
	    if (arrayListDebitoCobrado != null){
	        
	    	for (int i = 0; i < arrayListDebitoCobrado.size(); i++) {
	        	
		        DebitoCobrado dadosDebitosCobrados = (DebitoCobrado) arrayListDebitoCobrado.get(i);
		
		        String descricao = "";
		
		        if (dadosDebitosCobrados != null
		            && dadosDebitosCobrados.getDescricaoDebitoTipo() != null
		            && dadosDebitosCobrados.getDescricaoDebitoTipo().length() >= 45) {
		            descricao = dadosDebitosCobrados.getDescricaoDebitoTipo().substring(
		                0, 45);
		        } else {
		            descricao = dadosDebitosCobrados.getDescricaoDebitoTipo();
		        }
		
		        appendTexto70(xEsquerda, yInicial, descricao);
		        appendTexto70(xDireita, yInicial, true, Util
		            .formatarDoubleParaMoedaReal(dadosDebitosCobrados
		                .getValor().doubleValue()));
		        
		        yInicial += alturaLinha;
	        }
	    	
	    }

	    // Creditos 
	    ArrayList<CreditoRealizado> arrayListCreditoRealizado = new ArrayList<CreditoRealizado>();
	    arrayListCreditoRealizado = (ArrayList<CreditoRealizado>) fachada.buscarCreditoRealizadoPorImovelId(imovel.getId());
		
	
	    if (arrayListCreditoRealizado != null) {
	    	
	    	double valorContaSemCreditos = 0.d;
	    	valorContaSemCreditos = fachada.obterValorContaSemCreditos(imovel.getId());
		
	
	        for (int i = 0; i < arrayListCreditoRealizado.size(); i++) {
	        	
	        	CreditoRealizado dadosCreditosRealizado = (CreditoRealizado) arrayListCreditoRealizado.get(i);
	        	String descricao = "";
	
		        if (dadosCreditosRealizado != null
		            && dadosCreditosRealizado.getDescricaoCreditoTipo() != null
		            && dadosCreditosRealizado.getDescricaoCreditoTipo().length() >= 45) {
		            
		        		descricao = dadosCreditosRealizado.getDescricaoCreditoTipo().substring(0, 45);
		        		
		        } else {
		            descricao = dadosCreditosRealizado.getDescricaoCreditoTipo();
		        }
		
		        if (dadosCreditosRealizado.getValor().doubleValue() > valorContaSemCreditos) {
		            appendTexto70(xEsquerda, yInicial, descricao);
		            
		            appendTexto70(xDireita, yInicial, true, Util.formatarDoubleParaMoedaReal(valorContaSemCreditos));
		            yInicial += alturaLinha;
		            // Só vai até o serviço de credito que zerou a conta
		            break;
		        } else {
		            valorContaSemCreditos -= dadosCreditosRealizado.getValor().doubleValue();
		            appendTexto70(xEsquerda, yInicial, descricao);
		            appendTexto70(xDireita, yInicial, true, Util
		                .formatarDoubleParaMoedaReal(dadosCreditosRealizado
		                    .getValor().doubleValue()));
		        }
		        
		        yInicial += alturaLinha;
	        }
	    }
	
	    return yInicial;
    }

    /**
     * Caso exista impostos retidos, adiciona as linhas correspondentes no
     * buffer.
     *
     * @return Retorna o valor y da ultima linha jogada no buffer
     */
    private int gerarImpostosRetidos(int yInicial) {

	    ArrayList<ContaImposto> arrayListContaImpostos = new ArrayList<ContaImposto>();
		arrayListContaImpostos = (ArrayList<ContaImposto>) fachada.buscarContaImpostoPorImovelId(imovel.getId());

	    if (arrayListContaImpostos != null) {
	        final int xEsquerda = 5, xDireita = 95, alturaLinha = 3;
	        String dadosImposto = "";
	
	        for (int i = 0; i < arrayListContaImpostos.size(); i++) {
	        	ContaImposto contaImposto = (ContaImposto) arrayListContaImpostos.get(i);
		        String descricaoImposto = contaImposto.getDescricaoImposto();
		        String percentualAliquota = Util.formatarDoubleParaMoedaReal(contaImposto.getPercentualAlicota().doubleValue());
		        dadosImposto += descricaoImposto + "-" + percentualAliquota+ "% ";
	        }
	
	        double valorImposto = 0.d;
	        
	        valorImposto = fachada.obterValorImpostoTotal(imovel.getId());
	        appendTexto70(xEsquerda, yInicial,"DED. IMPOSTOS LEI FEDERAL N.9430 DE 27/12/1996");
	        appendTexto70(xDireita, yInicial, true,Util.formatarDoubleParaMoedaReal(valorImposto));
	        yInicial += alturaLinha;
	        appendTexto70(xEsquerda, yInicial, dadosImposto.trim());
	        yInicial += alturaLinha;
	    }
	
	    return yInicial;
    }

    /**
     * Caso exista faturas em atraso, adiciona as linhas correspondentes no
     * buffer.
     *
     * @return Retorna o valor y da ultima linha jogada no buffer
     */
    private int gerarFaturasEmAtraso(int yInicial) {
    	
    	ArrayList<ContaDebito> arrayListContaDebito = new ArrayList<ContaDebito>();
    	arrayListContaDebito = fachada.buscarContasDebitosPorIdImovel(imovel.getId());
		
	    if (arrayListContaDebito != null) {
	        int i = 0, x = 5;
	        final int alturaLinha = 3;
	
	        appendLinha(5, yInicial, 100, yInicial, 0.1f);
	        yInicial++;
	
	        appendTexto70(x, yInicial, "FATURAS EM ATRASO");
	        yInicial += alturaLinha;
	
	        double valorOutro = 0d;
	        int y = yInicial;
	
	        
	        for (i = 0; i < arrayListContaDebito.size(); i++) {
		        ContaDebito contaDebito = (ContaDebito) arrayListContaDebito.get(i);
		       
		        
		        double valorTotal = 
		        		contaDebito.getValorConta().doubleValue() + contaDebito.getValorAcrescimoImpontualidade().doubleValue();
		
		        if (i < 5) {
		            appendTexto70(x, y, "REF " + contaDebito.getAnoMesReferencia() + "    "
		                + Double.toString(valorTotal));
		
		            if (i == 2) {
			            x = 52;
			            y = yInicial;
		            } else
		            	y += alturaLinha;
		            
		        } else
		        	valorOutro += valorTotal;
		        }
		
		        if (valorOutro > 0)
		        appendTexto70(x, y, "OUTROS " + Double.toString(valorOutro));
	    }
	
	    return yInicial;
    }

    private void gerarLancamentosConta(int yCorpoConta) {
    	appendLinha(5, yCorpoConta, 100, yCorpoConta, 0.1f);
        appendTexto70(5, ++yCorpoConta, "DESCRICAO");
        appendTexto70(75, yCorpoConta, true, "CONSUMO");
        appendTexto70(95, yCorpoConta, true, "TOTAL(R$)");

        yCorpoConta = gerarLancamentoAgua(yCorpoConta += 3);
        yCorpoConta = gerarLancamentoEsgoto(yCorpoConta);
        yCorpoConta = gerarLancamentoServicos(yCorpoConta);
        yCorpoConta = gerarImpostosRetidos(yCorpoConta);
        yCorpoConta = gerarFaturasEmAtraso(yCorpoConta);
    }

    private void gerarTributosConta(double valorTotalConta) throws ControladorException {
        // Vencimento / Total a pagar
        final int yVencValor = 155;
        appendTexto70(26, yVencValor, formatarData(imovel.getDataVencimento()));

        double somaValorAguaEsgoto = fachada.obterSomaValorAguaEsgoto(imovel.getId());
        double pis = 1.65;
        double cofins = 7.6;
        
        appendTexto70(8, 143, "TRIBUTOS");	        
        appendTexto70(11, 146, "PIS");        
        appendTexto70(11, 149, "COFINS");
        
        appendTexto70(25, 143, "BASE DE CÁLCULO");
        appendTexto70(30, 146, Util.formatarDoubleParaMoedaReal(somaValorAguaEsgoto));        
        appendTexto70(30, 149, Util.formatarDoubleParaMoedaReal(somaValorAguaEsgoto));
        
        appendTexto70(52, 143, "PERCENTUAL(%)");
        appendTexto70(56, 146, String.valueOf(pis));// Percentual PIS
        appendTexto70(56, 149, String.valueOf(cofins));// Percentual COFINS
        
        appendTexto70(75, 143, "VALOR DO IMPOSTO");
        appendTexto70(82, 146, Util.formatarDoubleParaMoedaReal((0.01*pis)*somaValorAguaEsgoto)); // PIS
        appendTexto70(82, 149, Util.formatarDoubleParaMoedaReal((0.01*cofins)*somaValorAguaEsgoto)); // COFINS
        	        
        appendTexto70(95, yVencValor, true, Util.formatarDoubleParaMoedaReal(valorTotalConta));
    }

    private void gerarCodigoBarrasLinhaDigitavel(double valorTotalConta) throws ControladorException {
    	final int yLinhaDigitavel = 192;
    	
        String agencia = imovel.getCodigoAgencia();
        appendTexto("CENTER\n");
		if (valorTotalConta == 0) {
			appendTexto70(0, yLinhaDigitavel, "CONTA ZERADA DEVIDO A CREDITO.");
			appendTexto70(0, yLinhaDigitavel + 3, "APENAS PARA SIMPLES DEMONSTRAÇÃO.");
		} else if (isNullOrEmpty(agencia)) {
        	StringBuilder codigoBarrasSemFormatacao = CodigoDeBarras.getInstancia()
        			.obterRepresentacaoNumericaCodigoBarra(3,
        			valorTotalConta, Integer.parseInt(imovel.getInscricao().substring(0, 3)),
        			imovel.getId(), Util.formatarAnoMesParaMesAnoSemBarra(imovel.getAnoMesConta().toString()), 
                	imovel.getDigitoVerificadorConta(), 
                    null, null, null, null, null, null);

	        String linhaDigitavel = codigoBarrasSemFormatacao.substring(0,
	            11).trim()
	            + "  "
	            + codigoBarrasSemFormatacao.substring(11, 12).trim()
	            + "  "
	            + codigoBarrasSemFormatacao.substring(12, 23).trim()
	            + "  "
	            + codigoBarrasSemFormatacao.substring(23, 24).trim()
	            + "  "
	            + codigoBarrasSemFormatacao.substring(24, 35).trim()
	            + "  "
	            + codigoBarrasSemFormatacao.substring(35, 36).trim()
	            + "  "
	            + codigoBarrasSemFormatacao.substring(36, 47).trim()
	            + "  " + codigoBarrasSemFormatacao.substring(47, 48);

	        String codigoBarras = codigoBarrasSemFormatacao
	            .substring(0, 11)
	            + codigoBarrasSemFormatacao.substring(12, 23)
	            + codigoBarrasSemFormatacao.substring(24, 35)
	            + codigoBarrasSemFormatacao.substring(36, 47);

	        appendTexto(5, 0, 0, yLinhaDigitavel, false, linhaDigitavel);
	        appendBarcode(0, yLinhaDigitavel + 3, codigoBarras);
        } else {
        	appendTexto70(0, yLinhaDigitavel, "DEBITO AUTOMATICO");
        }

        appendTexto("LEFT\n");
    }

    private boolean verificarEmitirCarta() {
    	// Verifica se houve anormalidade por estouro de consumo
		ConsumoHistorico historico = fachada.buscarConsumoHistoricoPorImovelIdTipoLigacao(imovel.getId(),
				ConstantesSistema.LIGACAO_AGUA);
		ConsumoAnormalidade anormalidade = historico.getConsumoAnormalidade();

		if(ConstantesSistema.CONSUMO_ANORM_ESTOURO != anormalidade.getId()) {
			return false;
		}

		// Consulta a Ação para esta anormalidade de consumo
		int categoriaPrincipal = fachada.obterCategoriaPrincipal(imovel.getId()); 
		ArrayList<ConsumoAnormalidadeAcao> colecao = fachada.buscarConsumoAnormalidadeAcao(imovel.getCodigoPerfil(),
				anormalidade.getId(), categoriaPrincipal);

		if(Util.isVazioOrNulo(colecao)) {
			return false;
		}

		ConsumoAnormalidadeAcao acao = colecao.get(0);

		int anoMes;
		ConsumoAnteriores regMesAnterior;
		String[] mensagem = null;

		try {
			// Verificando o PRIMEIRO mês anterior
			anoMes = Util.subtrairMesDoAnoMes(imovel.getAnoMesConta(), 1);
			regMesAnterior = fachada.buscarConsumoAnterioresPorImovelAnormalidade(imovel.getId(),
					anormalidade.getId(), anoMes);

			if (regMesAnterior == null) {
				if (ConstantesSistema.SIM_SHORT.equals(acao.getIndicadorGeracaoCartaMes1())) {
					mensagem = Util.dividirString(acao.getMensagemConta(), 60);
					return true;
				}

				return false;
			}

			// Verificando o SEGUNDO mês anterior
			anoMes = Util.subtrairMesDoAnoMes(imovel.getAnoMesConta(), 2);
			regMesAnterior = fachada.buscarConsumoAnterioresPorImovelAnormalidade(imovel.getId(),
					anormalidade.getId(), anoMes);

			if (regMesAnterior == null) {
				if (ConstantesSistema.SIM_SHORT.equals(acao.getIndicadorGeracaoCartaMes2())) {
					mensagem = Util.dividirString(acao.getMensagemContaSegundoMes(), 60);
					return true;
				}

				return false;
			}

			// Verificando o TERCEIRO mês anterior
			anoMes = Util.subtrairMesDoAnoMes(imovel.getAnoMesConta(), 3);
			regMesAnterior = fachada.buscarConsumoAnterioresPorImovelAnormalidade(imovel.getId(),
					anormalidade.getId(), anoMes);

			if (regMesAnterior == null) {
				if (ConstantesSistema.SIM_SHORT.equals(acao.getIndicadorGeracaoCartaMes3())) {
					mensagem = Util.dividirString(acao.getMensagemContaTerceiroMes(), 60);
					return true;
				}

				return false;
			}

			// Verificando o Indicador de Cobrança Normal
			if (ConstantesSistema.NAO_SHORT.equals(acao.getIndicadorCobrancaConsumoNormal())) {
				mensagem = Util.dividirString(acao.getMensagemContaTerceiroMes(), 60);
				return true;
			}
		} finally {
			if (mensagem != null) {
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

		return false;
    }

    private void emitirCarta(int yCorpoConta, Integer leituraAtual) {
    	appendLinha(5, yCorpoConta, 100, yCorpoConta, 0.1f);
    	final int alturaLinha = 3;

    	String mensagem = String.format("Em %s, foi  registrada uma leitura de %sm3 no",
    			Util.dateToString(new Date()),
    			leituraAtual == null ? "" : leituraAtual);

    	appendTexto70(5, (yCorpoConta += alturaLinha), mensagem);
    	appendTexto70(5, (yCorpoConta += alturaLinha), "hidrômetro  de  seu imóvel, acarretando um  consumo muito");
    	appendTexto70(5, (yCorpoConta += alturaLinha), "superior ao esperado.");
    	appendTexto70(5, (yCorpoConta += alturaLinha), "Para  evitar maiores transtornos, a CAERN reteve sua conta");
    	appendTexto70(5, (yCorpoConta += alturaLinha), "para análise, a qual lhe será enviada posteriormente.");
    	appendTexto70(5, (yCorpoConta += alturaLinha), "Para maiores informações, consulte a Loja Virtual em nosso");
    	appendTexto70(5, (yCorpoConta += alturaLinha), "site ou ligue para um dos números de Telefone indicados em");
    	appendTexto70(5, (yCorpoConta += alturaLinha), "sua conta.");
    	appendTexto70(5, (yCorpoConta += alturaLinha), "Recomendamos verificar a existência de vazamentos em seu imóvel.");
    }
}
