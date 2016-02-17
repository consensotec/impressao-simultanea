package com.br.ipad.isc.impressao;
import java.util.ArrayList;
import java.util.Date;

import android.util.Log;

import com.br.ipad.isc.bean.CategoriaSubcategoria;
import com.br.ipad.isc.bean.ConsumoAnteriores;
import com.br.ipad.isc.bean.ConsumoHistorico;
import com.br.ipad.isc.bean.ContaCategoria;
import com.br.ipad.isc.bean.ContaCategoriaConsumoFaixa;
import com.br.ipad.isc.bean.ContaComunicado;
import com.br.ipad.isc.bean.ContaImposto;
import com.br.ipad.isc.bean.CreditoRealizado;
import com.br.ipad.isc.bean.DebitoCobrado;
import com.br.ipad.isc.bean.HidrometroInstalado;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.bean.QualidadeAgua;
import com.br.ipad.isc.bean.SistemaParametros;
import com.br.ipad.isc.fachada.Fachada;
import com.br.ipad.isc.repositorios.RepositorioBasico;
import com.br.ipad.isc.util.ConstantesSistema;
import com.br.ipad.isc.util.Util;

/**
 * ImpressaoContaCaer
 * 
 * Classe que irá gerar a string de retorno para impressão da conta/notificação
 * de débito/extrato consumo macromedidor
 * 
 * @author Carlos Chaves
 * @date 14/01/2013
 * 
 */				
public class ImpressaoContaCaer  extends ImpressaoCaer {

	private static ImpressaoContaCaer instancia;
	private int quantidadeLinhasAtual = 0;
	private int quantidadeMaximaLinhas = 18;
    
    private ImpressaoContaCaer() { }

    public static ImpressaoContaCaer getInstancia(
        ImovelConta imovelInformado) {
    	if (instancia == null) {
        instancia = new ImpressaoContaCaer();
    	}
    	
    	instancia.imovel = imovelInformado;    	
    	return instancia;
    }

    /**
     * imprimirConta
     * 
     * Método que imprime conta padrão da CAER
     * 
     * @author Carlos Chaves
     * @date 14/01/2013
     * @return retorno
     */
    
    public StringBuilder imprimirConta() {

    	buffer = new StringBuilder();

    	try {
    	    // Configuracao do papel
	   		if ( SistemaParametros.getInstancia().getContrasteConta() != 0 )		   		
	   			appendTexto("! 0 816 0 1720 1 CONTRAST " + SistemaParametros.getInstancia().getContrasteConta() + " \n");
	   		else 
	   			appendTexto("! 0 816 0 1720 1 \n");    		

    	    // Caixas e linhas divisórias
    		appendTexto( 
    	    	//"BOX 26 370 810 459 1\n" + // BOX CABEÇALHO CONSUMO
    		    //"BOX 28 330 798 366 1\n" + 	// BOX CONSUMO
    		    "LINE 308 336 308 360 1\n" + // 1ª LINHA SEPARADORA CONSUMO
    		    "LINE 399 336 399 360 1\n" + // 2ª LINHA SEPARADORA CONSUMO
    		    "LINE 647 336 647 360 1\n" + // 3ª LINHA SEPARADORA CONSUMO
    		    "LINE 35 360 805 360 1\n" + // LINHA SEPARADORA CONSUMO / LEITURA FATURADA
    		    "LINE 35 450 805 450 1\n" + // LINHA SEPARADORA LEITURAS / ULTIMOS CONSUMOS
    		    "LINE 142 488 142 639 1\n" + // LINHA SEPARADORA DE ÚLTIMOS CONSUMOS
    		    "BOX 279 480 798 500 1\n" + // BOX QUALIDADE DA ÁGUA 1o CABEÇALHO
    		    "BOX 279 500 798 542 1\n" + // BOX QUALIDADE DA ÁGUA 2o CABEÇALHO
    		    "BOX 279 542 798 665 1\n" + // BOX QUALIDADE DA ÁGUA
    		    "LINE 400 542 400 665 1\n" + // 1ª LINHA SEPARADORA QUALIDADE DA ÁGUA
    		    "LINE 480 542 480 665 1\n" + // 2ª LINHA SEPARADORA QUALIDADE DA ÁGUA
    		    "LINE 560 542 560 665 1\n" + // 3ª LINHA SEPARADORA QUALIDADE DA ÁGUA
    		    "LINE 640 542 640 665 1\n" + // 4ª LINHA SEPARADORA QUALIDADE DA ÁGUA
    		    "LINE 720 542 720 665 1\n" + // 5ª LINHA SEPARADORA QUALIDADE DA ÁGUA
    		    "LINE 280 580 798 580 1\n" + // 1ª LINHA SEPARADORA QUALIDADE DA ÁGUA HORIZONTAL
    		    "LINE 280 610 798 610 1\n" + // 2ª LINHA SEPARADORA QUALIDADE DA ÁGUA HORIZONTAL
    		    "LINE 280 640 798 640 1\n");  // 3ª LINHA SEPARADORA QUALIDADE DA ÁGUA HORIZONTAL
    		
    		// Data e Hora de Impressão
    		buffer.append("T90 0 1 814 785 IMPRESSO EM: " + Util.convertDateToDateStr(new Date()) + "\n");
    		
    		// Matrícula do Imóvel
    		appendTexto("T 7 1 464 66 " + imovel.getId() + "\n");

    	    // Ano mes de referencia da conta
    		appendTexto("T 7 1 613 66 "+ Util.retornaDescricaoAnoMes(imovel.getAnoMesConta()+"")+ "\n");

    	    // Dados do cliente
    	    String cpfCnpjFormatado = "";
    	    if (imovel.getCpfCnpjCliente() != null  && !imovel.getCpfCnpjCliente().equals("")) {
    	    	cpfCnpjFormatado = imovel.getCpfCnpjCliente().trim();
    	    }
    	    appendTexto(formarLinha(0, 2, 52, 164, imovel.getNomeUsuario().trim(), 0, 0).toString());
    	    //appendTexto(formarLinha(0, 2, 52, 194, cpfCnpjFormatado, 0, 0).toString());
    	    appendTexto(dividirLinha(0, 2, 434, 164, imovel.getEndereco(), 40, 27).toString());

    	    // Inscricao, Codigo da Rota, Sequencial da Rota
    	    appendTexto(formarLinha(7, 0, 45, 250, fachada.formatarInscricao(imovel.getInscricao()), 0, 0).toString());
    	    appendTexto(formarLinha(7, 0, 315, 250, imovel.getCodigoRota() + "", 0, 0).toString());
    	    appendTexto(formarLinha(7, 0, 415, 250, imovel.getSequencialRota() + "", 0, 0).toString());

//    	    // Verificamos se é por categoria ou subcategoria
//    	    ArrayList<CategoriaSubcategoria> arrayListCategoriaSubcategoria = new ArrayList<CategoriaSubcategoria>();
//			arrayListCategoriaSubcategoria = (ArrayList<CategoriaSubcategoria>) 
//								fachada.buscarCategoriaSubcategoriaPorImovelId(imovel.getId());
//    	    
//    	    for (int i = 0; i < arrayListCategoriaSubcategoria.size(); i++) {
//    	    	
//    	    	CategoriaSubcategoria dadosCategoria = (CategoriaSubcategoria) arrayListCategoriaSubcategoria.get(i);
//    	    	appendTexto(formarLinha(0, 0, 510, 250, dadosCategoria.getDescricaoCategoria() + "", i * 83, 0).toString());
//    	    	appendTexto(formarLinha(7, 0, 520, 260, dadosCategoria.getQtdEconomiasSubcategoria() + "", i * 83, 0).toString());
//    	    }
    	    
    	    gerarCategoriaSubcategoria(imovel.getId());

    	    // Extrai informções usadas nas próximas impressões
	        String hidrometro = "NÃO MEDIDO";
	        String situacaoAgua = fachada.getDescricaoSitLigacaoAgua(imovel.getSituacaoLigAgua());
	        String situacaoEsgoto = fachada.getDescricaoSitLigacaoEsgoto(imovel.getSituacaoLigEsgoto());
	        String leituraAnterior = null;
	        Integer leituraAtual = null;
	        String consumo = null;
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
	        String dataInstacao = null;
	        String dataLeituraAnterior = null;
	
	        consumoAgua = fachada.
	        		buscarConsumoHistoricoPorImovelIdTipoLigacao(imovel.getId(), ConstantesSistema.LIGACAO_AGUA);
	        
	        consumoEsgoto = fachada.buscarConsumoHistoricoPorImovelIdTipoLigacao(imovel.getId(), ConstantesSistema.LIGACAO_ESGOTO);
	        
	        // Prioridade para as informações de água
	        if (hidrometroInstaladoAgua != null) {
		       
	        	hidrometro = hidrometroInstaladoAgua.getNumeroHidrometro();
		        leituraAnterior = fachada.obterLeituraAnterior(hidrometroInstaladoAgua)+"";
		        
		        dataLeitura = Util.dateToString(hidrometroInstaladoAgua.getDataLeitura());	
		        dataInstacao = Util.dateToString(hidrometroInstaladoAgua.getDataInstalacaoHidrometro());
		        dataLeituraAnterior = Util.dateToString(hidrometroInstaladoAgua.getDataLeituraAnterior());
	
		        if (consumoAgua != null) {
		            if (hidrometroInstaladoAgua.getLeituraAtualFaturamento() != null) {
		            	
		            	leituraAtual = hidrometroInstaladoAgua.getLeituraAtualFaturamento();
		            	consumo = consumoAgua.getConsumoCobradoMes()+"";
		            	diasConsumo = consumoAgua.getDiasConsumo();
		
		            } else {
		            	
		            	if (consumoAgua.getLeituraAtual() != null) {
		            		leituraAtual = consumoAgua.getLeituraAtual();
		            	}
		            	
			            
			            consumo = consumoAgua.getConsumoCobradoMes()+"";
			            
		            if(hidrometroInstaladoAgua.getQtdDiasAjustado() != null){
		            	
		                diasConsumo = hidrometroInstaladoAgua.getQtdDiasAjustado();
		                
		            }else{
		            	
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
	        	
	        	hidrometro = hidrometroInstaladoPoco.getNumeroHidrometro();
	        	leituraAnterior = hidrometroInstaladoPoco.getLeituraAnteriorFaturamento()+"";
	        	dataLeitura = Util.dateToString(hidrometroInstaladoPoco.getDataLeitura());
	
		        if (consumoEsgoto != null) {
		
		            if (hidrometroInstaladoPoco.getLeituraAtualFaturamento() != null) {
		            	
			            leituraAtual = hidrometroInstaladoPoco.getLeituraAtualFaturamento();
			            consumo = consumoEsgoto.getConsumoCobradoMes()+"";
			            diasConsumo = hidrometroInstaladoPoco.getQtdDiasAjustado();
		
		            } else {
		            	
			            leituraAtual = consumoEsgoto.getLeituraAtual();
			            consumo = consumoEsgoto.getConsumoCobradoMes()+"";
				            
			            if(hidrometroInstaladoPoco.getQtdDiasAjustado() != null){
			            	
			                diasConsumo = hidrometroInstaladoPoco.getQtdDiasAjustado();
			                
			            }else{
			            	
			                diasConsumo = consumoEsgoto.getDiasConsumo();
			                
			            }
		
		            }
		
		            if (consumoEsgoto.getConsumoRateio() != null) {
		            	
		            	consumoRateioEsgoto = consumoEsgoto.getConsumoRateio();
		            	
		            }
		
		        }
	        } else if (hidrometroInstaladoAgua == null && hidrometroInstaladoPoco == null) {
	
		        if (consumoAgua != null) {
		        	if(consumoAgua.getLeituraAtual() != null){
		        		leituraAtual = consumoAgua.getLeituraAtual();
		        	}
		            consumo = consumoAgua.getConsumoCobradoMes()+"";
		            diasConsumo = consumoAgua.getDiasConsumo();
		            leituraAnterior = "";
		            
		            if (consumoAgua.getConsumoRateio() != null) {
		            	consumoRateioAgua = consumoAgua.getConsumoRateio();
		            }
		            
		        }else{
		        	if (consumoEsgoto != null) {
						if (consumoEsgoto.getLeituraAtual() != null) {
							leituraAtual = consumoEsgoto.getLeituraAtual();
						} 

						consumo = consumoEsgoto.getConsumoCobradoMes()+"";
			            diasConsumo = consumoEsgoto.getDiasConsumo();
					}
		        	
		        	if (consumoEsgoto != null && consumoEsgoto.getConsumoRateio() != null) {
			            consumoRateioEsgoto = consumoEsgoto.getConsumoRateio();  
			        }
		        }
		        
		        
	        }

    	    // Hidrometro, Data da Instalação
    	    appendTexto(formarLinha(7, 0, 50, 305, hidrometro, 0, 0).toString());
    	    appendTexto(formarLinha(7, 0, 271, 305, (dataInstacao!=null?dataInstacao:""), 0, 0).toString());

    	    // Situação da ligação de Agua
    	    if (situacaoAgua.length() > 13) {
    	    	appendTexto(formarLinha(7, 0, 446, 305, situacaoAgua.substring(0, 13), 0, 0).toString());
    	    } else {
    	    	appendTexto(formarLinha(7, 0, 446, 305, situacaoAgua, 0, 0).toString());
    	    }

    	    // Situação da ligação de esgoto
    	    appendTexto(formarLinha(7, 0, 627, 305, situacaoEsgoto, 0, 0).toString());

    	    // Leitura Anterior
    	    appendTexto(formarLinha(7, 0, 188, 335, "ANTERIOR", 0, 0).toString());
    	    appendTexto(formarLinha(7, 0, 205, 368, (leituraAnterior!=null?leituraAnterior:""), 0, 0).toString());

    	    //Leitura Anterior Informada
    	  //  appendTexto(formarLinha(7, 0, 208, 397, leituraInformadaAnterior, 0,0).toString());

    	    // Leitura Atual
    	    appendTexto(formarLinha(7, 0, 331, 335, "ATUAL", 0, 0).toString());
    	    appendTexto(formarLinha(7, 0, 346, 368, (leituraAtual!=null?leituraAtual+"":""), 0, 0).toString());

    //	    appendTexto(formarLinha(7, 0, 347, 398, leituraInformada, 0, 0).toString());

    	    // Consumo
    	    if ((imovel.getIndcFaturamentoAgua() == ConstantesSistema.SIM || imovel.getIndcFaturamentoEsgoto() == ConstantesSistema.SIM)
    		    && imovel.getMatriculaCondominio() != null) {

	    		if (imovel.getIndcFaturamentoAgua() == ConstantesSistema.SIM) {
	    		    consumo += "/" + consumoAgua.getConsumoRateio();
	    		    
	    		} else if (imovel.getIndcFaturamentoEsgoto() == ConstantesSistema.SIM) {
	    		    consumo += "/"
	    			    + consumoEsgoto.getConsumoRateio();
	    		}
	
	    		appendTexto(formarLinha(7, 0, 414, 335, "CONSUMO/RATEIO (m3)",0, 0).toString());
	    		appendTexto(formarLinha(7, 0, 511, 368, consumo, 0, 0).toString());
	    		
    	    } else {
    	    	appendTexto(formarLinha(7, 0, 414, 335, "CONSUMO (m3)", 0, 0).toString());
    	    	appendTexto(formarLinha(7, 0, 511, 368, consumo, 0, 0).toString());
    	    }
    	    
    	    appendTexto(formarLinha(7, 0, 662, 335, "NUM DE DIAS", 0, 0).toString());
    	    appendTexto(formarLinha(7, 0, 726, 368, (diasConsumo!=null?diasConsumo+"":"0"), 0, 0).toString());

    	    // Data das Leituras
    	    appendTexto(formarLinha(7, 0, 41, 370, "LEITURA FAT.", 0, 0).toString());
    	    appendTexto(formarLinha(7, 0, 41, 397, "LEITURA INF.", 0, 0).toString());
    	    
    	    appendTexto(formarLinha(7, 0, 41, 424, "DT. LEITURA", 0, 0).toString());
    	    appendTexto(formarLinha(7, 0, 183, 425, (dataLeituraAnterior!=null?dataLeituraAnterior:""), 0, 0).toString());
    	    appendTexto(formarLinha(7, 0, 322, 424, (dataLeitura!=null?dataLeitura:""), 0, 0).toString());


    	    // Ultimos Consumos
    	    appendTexto(formarLinha(7, 0, 55, 466, "ULTIMOS CONSUMOS", 0, 0).toString());
    	    
    	    ArrayList<ConsumoAnteriores> arrayListConsumoAnteriores = (ArrayList<ConsumoAnteriores>) 
    	    		fachada.buscarConsumoAnterioresPorImovelId(imovel.getId());
    	    String media = "0";
    	    
    	    if (arrayListConsumoAnteriores != null) {
	    		int sumConsumo = 0;
	    		
	    		for (int i = 0; i < arrayListConsumoAnteriores.size(); i++) {
	    			
	    			ConsumoAnteriores consumoAnterior = (ConsumoAnteriores) arrayListConsumoAnteriores.get(i);
	    			appendTexto(formarLinha(0, 2, 54, 493, consumoAnterior.getAnoMesReferencia() + "", 0, i * 26).toString());
	    		    String anormalidade;
	    		   
	    		    if (consumoAnterior.getAnormalidadeLeitura() != null) {
	    		    	anormalidade = consumoAnterior.getAnormalidadeLeitura() + "";
	    		    } else {
	    		    	anormalidade = consumoAnterior.getAnormalidadeConsumo() + "";
	    		    }
	    		    
	    		    appendTexto(formarLinha(0, 2, 152, 493, consumoAnterior.getConsumo() + "-" + anormalidade, 0, i * 26).toString());
	    		    sumConsumo += consumoAnterior.getConsumo();
	    		}
	    		
	    		media = (int) (sumConsumo / arrayListConsumoAnteriores.size()) + "";
    	    }
    	    
    	    appendTexto(formarLinha(7, 0, 47, 650, "MEDIA", 0, 0).toString());
    	    appendTexto(formarLinha(7, 0, 175, 650, media, 0, 0).toString());
    	    
    	    //FIM Ultimos Consumos
    	    
    	    // QUALIDADE DA AGUA
//    	    appendTexto(formarLinha(7, 0, 444, 466, "QUALIDADE DA AGUA", 0, 0).toString());
    	    
    	    QualidadeAgua  qualidadeAgua = new QualidadeAgua();
    	    
 	        qualidadeAgua = fachada.
 	    		   buscarQualidadeAguaPorLocalidadeSetorComercial(imovel.getIdLocalidade(),  imovel.getIdSetorComercial());
 	       
 	        if(qualidadeAgua == null){
 	    	   qualidadeAgua = fachada.buscarQualidadeAguaPorLocalidade(imovel.getIdLocalidade());
 	        }
 	       
 	        if(qualidadeAgua == null){
 	    	   //qualidadeAgua = (QualidadeAgua)(fachada.pesquisar(new QualidadeAgua())).get(0);
 	    	   qualidadeAgua = fachada.buscarQualidadeAguaSemLocalidade();
 	        }
    	    
    	    // CABEÇALHO
 	        appendTexto(formarLinha(0, 0, 360, 485, "Qualidade de Agua Distribuida ao Consumidor", 0, 0).toString());
 	        appendTexto(formarLinha(0, 0, 300, 510, "Informacoes das Amostras Realizadas na Rede de Distribuicao", 0, 0).toString());
 	        appendTexto(formarLinha(0, 0, 400, 525, "DECRETO FEDERAL N.º 5440 / 2005 G.M", 0, 0).toString());
 	        
 	        appendTexto(formarLinha(0, 0, 300, 550, "AMOSTRAS", 0, 0).toString());
 	        appendTexto(formarLinha(0, 0, 285, 590, "EXIGIDAS", 0, 0).toString());
 	        appendTexto(formarLinha(0, 0, 285, 620, "ANALISADAS", 0, 0).toString());
 	        appendTexto(formarLinha(0, 0, 285, 650, "CONFORMES", 0, 0).toString());
 	        
//    	    appendTexto(formarLinha(0, 0, 283, 502, "PARAMETROS", 0, 0).toString());
//    	    appendTexto(formarLinha(0, 0, 376, 502, "PADRAO", 0, 0).toString());
//    		appendTexto(formarLinha(0, 0, 449, 502, "VL.MEDIO", 0, 0).toString());
//			appendTexto(formarLinha(0, 0, 535, 502, "PARAMETROS", 0, 0).toString());
//			appendTexto(formarLinha(0, 0, 633, 502, "PADRAO", 0, 0).toString());
//			appendTexto(formarLinha(0, 0, 712, 502, "VL.MEDIO", 0, 0).toString());

    	    // PARÂMETROS
    	    // ESQUERDA
//			appendTexto(formarLinha(0, 0, 288, 535, "CLORO", 0, 0).toString());
//			appendTexto(formarLinha(0, 0, 288, 569, "TURBIDEZ", 0, 0).toString());
//			appendTexto(formarLinha(0, 0, 288, 601, "COR", 0, 0).toString());
//			appendTexto(formarLinha(0, 0, 288, 632, "PH", 0, 0).toString());
    	    
    	    // DIREITA
//			appendTexto(formarLinha(0, 0, 536, 535, "FLUOR", 0, 0).toString());
//    		appendTexto(formarLinha(0, 0, 536, 569, "FERRO", 0, 0).toString());
//    		appendTexto(formarLinha(0, 0, 536, 601, "CL.TOTAIS", 0, 0).toString());
//    		appendTexto(formarLinha(0, 0, 536, 632, "CL.FECAIS", 0, 0).toString());

    	    // PADRAO ESQUERDA
    	    // VERIFICAÇÃO PARA QUEBRA DE LINHA DA PALAVRA "MÍNIMO"
//    	    if ((verificarString(qualidadeAgua.getCloroPadrao()).length()) > 7) {
//    	    	
//    	    	appendTexto(formarLinha(0, 0, 372, 530, verificarString(qualidadeAgua.getCloroPadrao()).substring(0, 7), 0, 0).toString());
//    	    	appendTexto(formarLinha(0, 0, 372, 542, verificarString(qualidadeAgua.getCloroPadrao() .substring(7,verificarString(qualidadeAgua.getCloroPadrao() ).length())), 0, 0).toString());
//    	    } else {
//    	    	appendTexto(formarLinha(0, 0, 372, 535, verificarString(qualidadeAgua.getCloroPadrao()).substring(0, 7), 0, 0).toString());
//    	    }
    	    
//    	    appendTexto(formarLinha(0, 0, 370, 569, verificarString(qualidadeAgua.getTurbidezPadrao()),0, 0).toString());
//    	    appendTexto(formarLinha(0, 0, 370, 601, verificarString(qualidadeAgua.getCorPadrao()), 0, 0).toString());
//    	    appendTexto(formarLinha(0, 0, 370, 632, verificarString(qualidadeAgua.getPhPadrao()), 0, 0).toString());

    	    // PADRAO DIREITA
    	    // VERIFICAÇÃO PARA QUEBRA DE LINHA DA PALAVRA "MÁXIMO"
//    	    if ((verificarString(qualidadeAgua.getFluorPadrao())).length() > 7) {
//    	    	
//    	    	appendTexto(formarLinha(0, 0, 634, 530, verificarString(qualidadeAgua.getFluorPadrao()+"").substring(0, 7), 0, 0).toString());
//    	    	appendTexto(formarLinha(0, 0, 634, 546, verificarString(qualidadeAgua.getFluorPadrao()).substring(7,(verificarString(qualidadeAgua.getFluorPadrao()) + "".trim()).length()), 0, 0).toString());
//    	    } else {
//    	    	appendTexto(formarLinha(0, 0, 634, 535, verificarString(qualidadeAgua.getFluorPadrao()),0, 0).toString());
//    	    }

    	    // VERIFICAÇÃO PARA QUEBRA DE LINHA DA PALAVRA "MÁXIMO"
//    	    if ((verificarString(qualidadeAgua.getFerroPadrao()) + "").length() > 7) {
//    	    	appendTexto(formarLinha(0, 0, 634, 567, verificarString(qualidadeAgua.getFerroPadrao())
//    			.substring(0, 7), 0, 0).toString());
//    	    	appendTexto(formarLinha(0, 0, 634, 580, verificarString(qualidadeAgua.getFerroPadrao()).substring(7,(verificarString(qualidadeAgua.getFerroPadrao())).length()), 0, 0).toString());
//    	    } else {
//    	    	appendTexto(formarLinha(0, 0, 634, 569, verificarString(qualidadeAgua.getFerroPadrao()),	0, 0).toString());
//    	    }
    	    
//    	    appendTexto(formarLinha(0, 0, 634, 601, verificarString(qualidadeAgua.getColiformesTotaisPadrao()), 0, 0).toString());
//    	    appendTexto(formarLinha(0, 0, 634, 632, verificarString(qualidadeAgua.getColiformesTotaisPadrao()), 0, 0).toString());

    	    // VL.MEDIO ESQUERDA
//    	    appendTexto(formarLinha(0, 0, 457, 535, verificarString(qualidadeAgua.getNumeroCloroResidual()+"") , 0, 0).toString());
//    	    appendTexto(formarLinha(0, 0, 457, 569, verificarString(qualidadeAgua.getNumeroTurbidez()+"") , 0, 0).toString());
//		    appendTexto(formarLinha(0, 0, 457, 601, verificarString(qualidadeAgua.getNumeroCor()+"") , 0, 0).toString());
//		    appendTexto(formarLinha(0, 0, 457, 632, verificarString(qualidadeAgua.getNumeroPh()+"") ,0,0).toString());
    	    
    	    // VL.MEDIO DIREITA
//    	    appendTexto(formarLinha(0, 0, 714, 535,verificarString(qualidadeAgua.getNumeroFluor()+"") , 0, 0).toString());
//    	    appendTexto(formarLinha(0, 0, 714, 569, verificarString(qualidadeAgua.getNumeroFerro()+"") ,0, 0).toString());
//    	    appendTexto(formarLinha(0, 0, 714, 601, verificarString(qualidadeAgua.getNumeroColiformesTotais()+"") , 0, 0).toString());
//    	    appendTexto(formarLinha(0, 0, 714, 632, verificarString(qualidadeAgua.getNumeroColiformesFecais()+"") , 0, 0).toString());
 	        
 	        if (qualidadeAgua != null){
	 	        // Qualidade de Água - CLORO RESIDUAL 	        
	 	        appendTexto(formarLinha(0, 0, 410, 550, "CLORO", 0, 0).toString());
	 	        appendTexto(formarLinha(0, 0, 420, 590, qualidadeAgua.getQuantidadeCloroExigidas() != null ? qualidadeAgua.getQuantidadeCloroExigidas().toString() : "", 0, 0).toString());
	 	        appendTexto(formarLinha(0, 0, 420, 620, qualidadeAgua.getQuantidadeCloroAnalisadas() != null ? qualidadeAgua.getQuantidadeCloroAnalisadas().toString() : "", 0, 0).toString());
	 	        appendTexto(formarLinha(0, 0, 420, 650, qualidadeAgua.getQuantidadeCloroConforme() != null ? qualidadeAgua.getQuantidadeCloroConforme().toString() : "", 0, 0).toString());
	 	        
	 	        // Qualidade de Água - TURBIDEZ
	 	        appendTexto(formarLinha(0, 0, 485, 550, "TURBIDEZ", 0, 0).toString());
	 	        appendTexto(formarLinha(0, 0, 505, 590, qualidadeAgua.getQuantidadeTurbidezExigidas() != null ? qualidadeAgua.getQuantidadeTurbidezExigidas().toString() : "", 0, 0).toString());
	 	        appendTexto(formarLinha(0, 0, 505, 620, qualidadeAgua.getQuantidadeTurbidezAnalisadas() != null ? qualidadeAgua.getQuantidadeTurbidezAnalisadas().toString() : "", 0, 0).toString());
	 	        appendTexto(formarLinha(0, 0, 505, 650, qualidadeAgua.getQuantidadeTurbidezConforme() != null ? qualidadeAgua.getQuantidadeTurbidezConforme().toString() : "", 0, 0).toString());
	
	 	        // Qualidade de Água - COR
	 	        appendTexto(formarLinha(0, 0, 590, 550, "COR", 0, 0).toString());
	 	        appendTexto(formarLinha(0, 0, 590, 590, qualidadeAgua.getQuantidadeCorExigidas() != null ? qualidadeAgua.getQuantidadeCorExigidas().toString() : "", 0, 0).toString());
	 	        appendTexto(formarLinha(0, 0, 590, 620, qualidadeAgua.getQuantidadeCorAnalisadas() != null ? qualidadeAgua.getQuantidadeCorAnalisadas().toString() : "", 0, 0).toString());
	 	        appendTexto(formarLinha(0, 0, 590, 650, qualidadeAgua.getQuantidadeCorConforme() != null ? qualidadeAgua.getQuantidadeCorConforme().toString() : "", 0, 0).toString());
	 	        
	 	        // Qualidade de Água - COLIFORMES TOTAIS
	 	        appendTexto(formarLinha(0, 0, 650, 550, "C.TOTAIS", 0, 0).toString());
	 	        appendTexto(formarLinha(0, 0, 665, 590, qualidadeAgua.getQuantidadeColiformesTotaisExigidas() != null ? qualidadeAgua.getQuantidadeColiformesTotaisExigidas().toString() : "", 0, 0).toString());
	 	        appendTexto(formarLinha(0, 0, 665, 620, qualidadeAgua.getQuantidadeColiformesTotaisAnalisadas() != null ? qualidadeAgua.getQuantidadeColiformesTotaisAnalisadas().toString() : "", 0, 0).toString());
	 	        appendTexto(formarLinha(0, 0, 665, 650, qualidadeAgua.getQuantidadeColiformesTotaisConforme() != null ? qualidadeAgua.getQuantidadeColiformesTotaisConforme().toString() : "", 0, 0).toString());
	 	        
	 	        // Qualidade de Água - ESCHERICHIA COLI
	 	        appendTexto(formarLinha(0, 0, 730, 550, "E.COLI", 0, 0).toString());
	 	        appendTexto(formarLinha(0, 0, 740, 590, qualidadeAgua.getQuantidadeColiformesTermoTolerantesExigidas() != null ? qualidadeAgua.getQuantidadeColiformesTermoTolerantesExigidas().toString() : "", 0, 0).toString());
	 	        appendTexto(formarLinha(0, 0, 740, 620, qualidadeAgua.getQuantidadeColiformesTermoTolerantesAnalisadas() != null ? qualidadeAgua.getQuantidadeColiformesTermoTolerantesAnalisadas().toString() : "", 0, 0).toString());
	 	        appendTexto(formarLinha(0, 0, 740, 650, qualidadeAgua.getQuantidadeColiformesTermoTolerantesConforme() != null ? qualidadeAgua.getQuantidadeColiformesTermoTolerantesConforme().toString() : "", 0, 0).toString());
 	        } 	        
    	    
 	        appendTexto(formarLinha(7, 0, xMargemDireita, 685, "DESCRICAO", 0, 0).toString());
    	    appendTexto(formarLinha(7, 0, 571, 685, "CONSUMO", 0, 0).toString());
    	    appendTexto(formarLinha(7, 0, 687, 685, "TOTAL(R$)", 0, 0).toString());
    	    
    	    //TODO
    	    int  yCorpoConta = 720;
    	    yCorpoConta = gerarLancamentoAgua(yCorpoConta);
    	    yCorpoConta = gerarLinhasTarifaPoco(yCorpoConta);
    	    yCorpoConta = gerarLinhasDebitosCobrados(yCorpoConta);
    	    yCorpoConta = gerarLinhasCreditosRealizados(yCorpoConta);
    	    gerarLinhasImpostosRetidos(yCorpoConta);
    	    
    	    appendTexto(formarLinha(0, 2, 37, 1224, "VENCIMENTO:", 0, 0).toString());
    	    appendTexto(formarLinha(7, 1, 160, 1224, Util.dateToString(imovel.getDataVencimento()), 0, 0).toString());
    	    
    	    double valorTotalConta = fachada.obterValorConta(imovel.getId()); 
    	    appendTexto(formarLinha(4, 0, 550, 1224, Util
    		    .formatarDoubleParaMoedaReal(valorTotalConta), 0, 0).toString());
    	

    	    if (imovel.getMensagemContaAnormalidade1() != null && !imovel.getMensagemContaAnormalidade1().equals("")) {
    		
    	    	appendTexto(formarLinha( 7, 0, 47, 1280,
    	    		imovel.getMensagemContaAnormalidade1() != null && imovel.getMensagemContaAnormalidade1().length() > 45 ? 
    	    		imovel.getMensagemContaAnormalidade1().substring(0, 45) : 
    	    		imovel.getMensagemContaAnormalidade1(), 0, 0 ).toString());
    			
    		    appendTexto(formarLinha(7, 0, 47, 1310, 
    		    	imovel.getMensagemContaAnormalidade2() != null && imovel.getMensagemContaAnormalidade2().length() > 45 ? 
    		        imovel.getMensagemContaAnormalidade2().substring(0, 45) :
    		        imovel.getMensagemContaAnormalidade2(), 0, 0).toString());
    		     
    		    appendTexto(formarLinha(7, 0, 47, 1340,
    		    	imovel.getMensagemContaAnormalidade3() != null && imovel.getMensagemContaAnormalidade3().length() > 45 ? 
    		    	imovel.getMensagemContaAnormalidade3().substring(0, 45) : 
    		    	imovel.getMensagemContaAnormalidade3(), 0, 0).toString());
    			
    		      appendTexto(formarLinha( 7, 0, 47, 1370, "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX", 0, 0).toString());
    			  appendTexto(formarLinha( 7, 0, 47, 1400, "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX", 0, 0).toString());
    		
    	    } else {
    	    	appendTexto(formarLinha(7, 0, 47, 1280, imovel.getMensagemConta1() , 0, 0).toString());
    	    	appendTexto(formarLinha(7, 0, 47, 1310, imovel.getMensagemConta2(), 0, 0).toString());
    	    	appendTexto(formarLinha(7, 0, 47, 1340, imovel.getMensagemConta3(), 0, 0).toString());
    	    	appendTexto(formarLinha(7, 0, 47, 1370, imovel.getMensagemConta4(), 0, 0).toString());
    	    	appendTexto(formarLinha(7, 0, 47, 1400, imovel.getMensagemConta5(), 0, 0).toString());
    	    	
    	    }
    	    
    	    appendTexto(formarLinha(0, 2, 320, 1460, "MATRICULA", 0, 0).toString());
    	    appendTexto(formarLinha(0, 2, 423, 1460, "REFERENCIA", 0, 0).toString());
    	    appendTexto(formarLinha(0, 2, 538, 1460, "VENCIMENTO", 0, 0).toString());
    	    appendTexto(formarLinha(0, 2, 674, 1460, "TOTAL A PAGAR", 0, 0).toString());
    	    appendTexto(formarLinha(0, 2, 320, 1500, imovel.getId() + "",0, 0).toString());
    	    appendTexto(formarLinha(0, 2, 423, 1500, Util.formatarAnoMesParaMesAno(imovel.getAnoMesConta()+""),0, 0).toString());
    	    appendTexto(formarLinha(0, 2, 538, 1500, Util.dateToString(imovel.getDataVencimento()), 0, 0).toString());
    	    appendTexto(formarLinha(0, 2, 674, 1500, Util.formatarDoubleParaMoedaReal(valorTotalConta), 0, 0).toString());

    	    if (imovel.getCodigoAgencia() == null || imovel.getCodigoAgencia().equals("")) {
    		
    	    	StringBuilder representacaoNumericaCodBarra = 
	    			CodigoDeBarras.getInstancia().obterRepresentacaoNumericaCodigoBarra(3,
	    				valorTotalConta,
	    				Integer.parseInt(imovel.getInscricao().substring(0, 3)),
	    				imovel.getId(),Util.formatarAnoMesParaMesAnoSemBarra(imovel.getAnoMesConta()+""), 
	    				imovel.getDigitoVerificadorConta(), null,null, null, null, null, null);
	    		
	    		String representacaoNumericaCodBarraFormatada = representacaoNumericaCodBarra
	    			.substring(0, 11).trim() + "-"
	    			
	    			+ representacaoNumericaCodBarra.substring(11, 12).trim() + " "
	    			+ representacaoNumericaCodBarra.substring(12, 23).trim() + "-"
	    			+ representacaoNumericaCodBarra.substring(23, 24).trim() + " "
	    			+ representacaoNumericaCodBarra.substring(24, 35).trim() + "-"
	    			+ representacaoNumericaCodBarra.substring(35, 36).trim() + " "
	    			+ representacaoNumericaCodBarra.substring(36, 47).trim() + "-"
	    			+ representacaoNumericaCodBarra.substring(47, 48);
	    		
	    		 appendTexto(formarLinha(5, 0, 66, 1548,representacaoNumericaCodBarraFormatada, 0, 0).toString());
	    		
	    		String representacaoCodigoBarrasSemDigitoVerificador = representacaoNumericaCodBarra.substring(0, 11)
	    			+ representacaoNumericaCodBarra.substring(12, 23)
	    			+ representacaoNumericaCodBarra.substring(24, 35)
	    			+ representacaoNumericaCodBarra.substring(36, 47);
	    		
	    		 appendTexto("B I2OF5 1 2 90 35 1571 " + representacaoCodigoBarrasSemDigitoVerificador + "\n");
    		
    	    } else {
    	    	 appendTexto(formarLinha(4, 0, 182, 1590, "DÉBITO AUTOMÁTICO", 0, 0).toString());
    	    }
    	    
    	    appendTexto("T90 0 2 808 1414 Via do Cliente\n");
    	    appendTexto("T90 0 2 808 1665 Via da CAER\n");
    	    appendTexto("FORM\n" + "PRINT ");

    	} catch (Exception ex) {
    	    ex.printStackTrace();
    	    Log.e(ConstantesSistema.CATEGORIA, ex.getMessage());
    	    Log.e(ConstantesSistema.CATEGORIA, "ERRO na geração da conta.");
    	}
    	
    	return buffer;
        }
    
    
//    private String verificarString(String valor) {
//    	if (valor == null || valor.trim().equals("") ||  valor.equals("null"))
//    	    return "--";
//
//    	return valor.trim();
//    }
    
   
    private int gerarLancamentoAgua(int yLancamentoAgua) {

    	// Verificamos se o tipo de calculo é por categoria ou por subcategoria
    	SistemaParametros sistemaParametros = SistemaParametros.getInstancia();
	    boolean tipoTarifaPorCategoria = sistemaParametros.getIndcTarifaCatgoria().equals(ConstantesSistema.SIM);
	    
    	ArrayList<CategoriaSubcategoria> arrayListCategoriaSubcategoria = (ArrayList<CategoriaSubcategoria>) 
	    		fachada.buscarCategoriaSubcategoriaPorImovelId(imovel.getId());
    	
    	if (arrayListCategoriaSubcategoria != null) {
    		
    		ConsumoHistorico consumoAgua = null;
	    	
			consumoAgua = fachada.
					buscarConsumoHistoricoPorImovelIdTipoLigacao(imovel.getId(), ConstantesSistema.LIGACAO_AGUA);
    		
    		appendTexto70(xMargemDireita, yLancamentoAgua, "AGUA");
    		
    	    for (int i = 0; i <arrayListCategoriaSubcategoria.size(); i++) {
    	    
    	    	CategoriaSubcategoria dadosEconomiasSubcategorias 
    	    		= (CategoriaSubcategoria) arrayListCategoriaSubcategoria.get(i);
	    		
    	    	ContaCategoria contaCategoriaAgua = new ContaCategoria();
    	    	
    			ArrayList<ContaCategoriaConsumoFaixa> arrayListContaCategoriaConsumoFaixa = new ArrayList<ContaCategoriaConsumoFaixa>();
    	
    			contaCategoriaAgua = fachada.buscarContaCategoriaPorCategoriaSubcategoriaId
    								(dadosEconomiasSubcategorias.getId(), ConstantesSistema.LIGACAO_AGUA);
    			
    			if (contaCategoriaAgua == null){
    	            continue;
    			}

    			yLancamentoAgua +=34;
	    		
	    		int quantidaEconomias = 0;
	    		if (dadosEconomiasSubcategorias.getFatorEconomiaCategoria() != null 
	   	        		 && dadosEconomiasSubcategorias.getFatorEconomiaCategoria() > 0)  {
	    		    quantidaEconomias = dadosEconomiasSubcategorias.getFatorEconomiaCategoria();
	    		} else {
	    		    quantidaEconomias = dadosEconomiasSubcategorias.getQtdEconomiasSubcategoria();
	    		}
	    		// 3.3.1
	    		String descricao = "";
	    		if (tipoTarifaPorCategoria) {
	    		    descricao = dadosEconomiasSubcategorias.getDescricaoCategoria() + " " + quantidaEconomias + " " + "UNIDADE(S)";
	    		    // 3.3.1.1, 3.3.1.2 e 3.3.2.2, 3.3.3
	    		    if (descricao.length() > 40) {
	    		    	appendTexto70(63, yLancamentoAgua, descricao.substring(0, 40));
	    		    } else {
	    		    	appendTexto70(63, yLancamentoAgua, descricao);
	    		    }
	    		} else {
	    		    descricao = dadosEconomiasSubcategorias.getDescricaoAbreviadaSubcategoria() + " " + quantidaEconomias + " " + "UNIDADE(S)";
	    		    // 3.3.2.1, 3.3.1.2 e 3.3.2.2, 3.3.3
	    		    if (descricao.length() > 40) {
	    		    	appendTexto70(63, yLancamentoAgua, descricao.substring(0, 40));
	    		    } else {
	    		    	appendTexto70(63, yLancamentoAgua, descricao);
	    		    }
	    		}
	    		
	    		int consumoMinimo = 0;
	    		if (imovel.getConsumoMinAgua() != null && imovel.getConsumoMinAgua() > imovel
	    	            .getConsumoMinimoImovel()) {
	    		    consumoMinimo = imovel.getConsumoMinAgua();
	    		} else {
	    		    consumoMinimo = imovel.getConsumoMinimoImovel();
	    		}
	    		
	    		// 3.4
	    		if (consumoAgua == null 
	    				&& contaCategoriaAgua != null
	    				&& contaCategoriaAgua.getNumConsumo() <= consumoMinimo) {
	    		   
	    			yLancamentoAgua +=34;
	    		    descricao = "";
	    		    // 3.4.2
	    		    descricao = "TARIFA MINIMA " + Util.formatarDoubleParaMoedaReal(contaCategoriaAgua
		                    .getValorTarifaMinima().doubleValue()/ quantidaEconomias) + " POR UNIDADE ";
	    		    
	    		    appendTexto70(63, yLancamentoAgua, descricao);
	    		    descricao = consumoMinimo + " M3";
	    		    // 3.4.3
	    		    appendTexto70(571, yLancamentoAgua, descricao);
	    		    // 3.4.4
	    		    appendTexto70(697, yLancamentoAgua, Util.formatarDoubleParaMoedaReal(contaCategoriaAgua
	                        .getValorTarifaMinima().doubleValue()));
	    		    // 3.5
	    		} else {
	    		    // 3.5.1
	    		    if (contaCategoriaAgua != null
	    		            && arrayListContaCategoriaConsumoFaixa  != null
	    		            && arrayListContaCategoriaConsumoFaixa .size() > 0) {
	    			
	    		    	yLancamentoAgua +=34;
	    			// 3.5.1.1
	    			if (arrayListCategoriaSubcategoria.size() > 1) {
	    			    
	    			    descricao = "CONSUMO ACUMULADO DAS FAIXAS";
	    			    // 3.5.1.1.2.1
	    			    appendTexto70(73, yLancamentoAgua, descricao);
	    			    appendTexto70(571, yLancamentoAgua,((int) contaCategoriaAgua.getNumConsumo().doubleValue())+ " M3");
	    			    // 3.5.1.1.2.2
	    			    appendTexto70(697,yLancamentoAgua,Util.formatarDoubleParaMoedaReal(contaCategoriaAgua.getValorFaturado().doubleValue()));
	    			} else {
	    			    
	    			    descricao = "CONSUMO DE AGUA";
	    			  
	    			    appendTexto70(xMargemDireita, yLancamentoAgua, descricao);
	    			    
	    			    for (int j = 0; j < arrayListContaCategoriaConsumoFaixa.size(); j++) {
		    				// qtdLinhas++;
		    				// 3.5.1.2.5
	    			    	ContaCategoriaConsumoFaixa faixa = (ContaCategoriaConsumoFaixa) arrayListContaCategoriaConsumoFaixa.get(j);
		    				// 3.5.1.2.5.1
		    				if (faixa.getNumConsumoFinal().intValue() == ConstantesSistema.LIMITE_SUPERIOR_FAIXA_FINAL) {
		    				    
		    					appendTexto70(571, yLancamentoAgua, faixa.getNumConsumo() + " M3 ");
		    				    // 3.5.1.2.5.1.3.2
		    				    // 3.5.1.2.5.1.4
		    					appendTexto70(697, yLancamentoAgua, Util.formatarDoubleParaMoedaReal(faixa.getValorFaturado().doubleValue()));
		    				    // 3.5.1.2.5.2
		    				} else {
		    					appendTexto70(571, yLancamentoAgua, faixa.getNumConsumo() + " M3 ");		    				   
		    					appendTexto70(697, yLancamentoAgua, Util.formatarDoubleParaMoedaReal(faixa.getValorFaturado().doubleValue()));
		    				}
	    			    }
	    			}
	    		    } else {
		    			if (contaCategoriaAgua != null) {
		    			    
		    				yLancamentoAgua +=34;
		    			    descricao = "CONSUMO DE AGUA";
		    			    // 3.5.1.1.2.1
		    			    appendTexto70(73, yLancamentoAgua, descricao);
		    			    appendTexto70(571, yLancamentoAgua,((int) contaCategoriaAgua.getNumConsumo())+ " M3");
		    			    // 3.5.1.1.2.2
		    			    appendTexto70(697,yLancamentoAgua,Util.formatarDoubleParaMoedaReal(contaCategoriaAgua.getValorFaturado().doubleValue()));
		    			}
	    		    }
	    		}
    	    }
    	     
    	}
    	yLancamentoAgua +=34;
    	return yLancamentoAgua;
        }
    
    
    /**
	 * [SB0004] - Gerar Linhas da Tarifa de Esgoto
	 * 
	 * @return Os dados estão dividos em 3 partes Descricao, de indice 0
	 *         Consumo, de indice 1 Valor, de indice 2
	 */
	private int gerarLinhasTarifaPoco(int yTarifaEsgoto) {
		double valorEsgoto = 0d;
		int consumoAgua = 0;
		int consumoEsgoto = 0;
		double valorAgua = 0d;
		// 3
		
		ArrayList<CategoriaSubcategoria> arrayListCategoriaSubcategoria = new ArrayList<CategoriaSubcategoria>();
		arrayListCategoriaSubcategoria = (ArrayList<CategoriaSubcategoria>) fachada.buscarCategoriaSubcategoriaPorImovelId(imovel.getId());
		
		
		if (arrayListCategoriaSubcategoria != null) {
			
			for (int i = 0; i < arrayListCategoriaSubcategoria.size(); i++) {
				
				CategoriaSubcategoria categoriaSubcategoria = (CategoriaSubcategoria) arrayListCategoriaSubcategoria.get(i);
				// 1 
				Integer categoriaSubcategoriaId =  categoriaSubcategoria.getId();
				
				ContaCategoria contaCategoriaPoco = new ContaCategoria();
				ContaCategoria contaCategoriaAgua = new ContaCategoria();
				
				contaCategoriaPoco = fachada.buscarContaCategoriaPorCategoriaSubcategoriaId
						(categoriaSubcategoriaId, ConstantesSistema.LIGACAO_POCO);
				
				contaCategoriaAgua = fachada.buscarContaCategoriaPorCategoriaSubcategoriaId
						(categoriaSubcategoriaId, ConstantesSistema.LIGACAO_AGUA);
				
				if(contaCategoriaPoco != null) {
					if (contaCategoriaPoco.getValorFaturado() != null) {
						valorEsgoto += contaCategoriaPoco.getValorFaturado().doubleValue();
					}
					if (contaCategoriaPoco.getValorFaturado() != null
							&& contaCategoriaPoco.getNumConsumo() != null) {
						consumoEsgoto += contaCategoriaPoco.getNumConsumo();
					}
				}
			
				if(contaCategoriaAgua != null) {
					if (contaCategoriaAgua.getValorFaturado() != null) {
						if (contaCategoriaAgua.getNumConsumo() != null) {
							consumoAgua += contaCategoriaAgua.getNumConsumo();
						}
						if (contaCategoriaAgua.getValorFaturado() != null) {
							valorAgua += contaCategoriaAgua.getValorFaturado().doubleValue();
						}
					}
				}
			}
		
			if (consumoAgua == consumoEsgoto && valorAgua != 0) {
				if (valorEsgoto != 0) {
					// 1.2.1
					appendTexto70(xMargemDireita, yTarifaEsgoto, "ESGOTO");
					// 1.2.3
					appendTexto70(350, yTarifaEsgoto, imovel.getPercentCobrancaEsgoto().doubleValue() + " % DO VALOR DE ÁGUA");
					// 1.4
					appendTexto70(697, yTarifaEsgoto, Util.formatarDoubleParaMoedaReal(valorEsgoto));
				}
			} else {
				if (valorEsgoto != 0) {
					// 1.2.1
					appendTexto70(xMargemDireita, yTarifaEsgoto, "ESGOTO");
					// 1.3.1
					appendTexto70(350, yTarifaEsgoto, consumoEsgoto + " M3");
					// 1.4
					appendTexto70(697, yTarifaEsgoto, Util.formatarDoubleParaMoedaReal(valorEsgoto));
				}
			}
		}
		
		return yTarifaEsgoto+34;
	}
	
	private int retornaIndicadorDiscriminar(int quantidadeMaximaLinhas, int quantidadeLinhasAtual, char servicos) {
		int indicadorDiscriminarDescricao = 1;
		int linhasRestantesDescricao = 0;
		
		Fachada fachada = Fachada.getInstance();
			
		Integer qntDebitoCobrado = fachada.obterQntDebitoCobradoPorImovelId(imovel.getId());
		Integer qntContaImposto = fachada.obterQntContaImpostoPorImovelId(imovel.getId());
		Integer qntCreditoRealizado = fachada.obterQntCreditoRealizadoPorImovelId(imovel.getId());
			
		switch (servicos) {
		case 'd':
			// linhas que ainda aparecerão depois do débio (crédito e imposto)
			// linhas de crédito
			if (qntCreditoRealizado != null) {
				linhasRestantesDescricao = linhasRestantesDescricao + 1;
			}
			// linhas de imposto
			if (qntContaImposto != null) {
				linhasRestantesDescricao = linhasRestantesDescricao + 2;
			}
			// linhas de débito
			if (qntDebitoCobrado != null) {
				// linhasRestantesDescricao = linhasRestantesDescricao + 1;
				int limiteDescriminar = quantidadeMaximaLinhas - quantidadeLinhasAtual - linhasRestantesDescricao;
				int quantidadeDebitos = qntDebitoCobrado.intValue();
				if (quantidadeDebitos > limiteDescriminar) {
					indicadorDiscriminarDescricao = 2;
				}
			}
			break;
		case 'c':
			// linhas que ainda aparecerão depois do débio (crédito e imposto)
			// linhas de imposto
			if (qntContaImposto != null) {
				linhasRestantesDescricao = linhasRestantesDescricao + 2;
			}
			// linhas de credito
			if (qntCreditoRealizado != null) {
				// linhasRestantesDescricao = linhasRestantesDescricao + 1;
				int limiteDescriminar = quantidadeMaximaLinhas - quantidadeLinhasAtual - linhasRestantesDescricao;
				int quantidadeCreditos = qntCreditoRealizado.intValue();
				if (quantidadeCreditos > limiteDescriminar) {
					indicadorDiscriminarDescricao = 2;
				}
			}
			break;
		}
		return indicadorDiscriminarDescricao;
	}
	
	/**
	 * [SB0005] - Gerar Linhas da Debitos Cobrados
	 * 
	 */
	private int gerarLinhasDebitosCobrados(int yDebitoCobrado) {
	
		int indicadorDiscriminarDescricao = 
				retornaIndicadorDiscriminar(quantidadeMaximaLinhas, quantidadeLinhasAtual, 'd');
		
		ArrayList<StringBuilder[]> debitos = new ArrayList<StringBuilder[]>();
		// Os dados estão dividos em 2 partes
		// Descricao, de indice 0
		// Valor, de indice 1
		StringBuilder[] dados = new StringBuilder[2];
		
		ArrayList<DebitoCobrado> arrayListDebitoCobrado = new  ArrayList<DebitoCobrado>();
		arrayListDebitoCobrado = (ArrayList<DebitoCobrado>) fachada.buscarDebitoCobradoPorImovelId(imovel.getId());

		// 3
		if (arrayListDebitoCobrado != null) {
			// caso seja para discriminar os dados dos débitos
			if (indicadorDiscriminarDescricao == 1) {
				for (int i = 0; i < arrayListDebitoCobrado.size(); i++) {
					DebitoCobrado dadosDebitosCobrados = (DebitoCobrado) arrayListDebitoCobrado.get(i);
					// 1.1.2
					dados[0] = new StringBuilder(dadosDebitosCobrados.getDescricaoDebitoTipo());
					// 1.1.3
					dados[1] = new StringBuilder(Util.formatarDoubleParaMoedaReal(dadosDebitosCobrados.getValor().doubleValue()));
					debitos.add(dados);
					dados = new StringBuilder[2];
				}
			} else {
				double soma = 0d;
				for (int i = 0; i < arrayListDebitoCobrado.size(); i++) {
					DebitoCobrado dadosDebitosCobrados = (DebitoCobrado) arrayListDebitoCobrado.get(i);
					soma += dadosDebitosCobrados.getValor().doubleValue();
				}
				// 1.1.2
				dados[0] = new StringBuilder("DEBITOS");
				// 1.1.3
				dados[1] = new StringBuilder(Util.formatarDoubleParaMoedaReal(soma));
				debitos.add(dados);
			}
		}
		
		if (debitos!=null){
			
			for (int i = 0; i < debitos.size(); i++) {			
				
				StringBuilder[] debito = debitos.get(i);
				
				if (debito[0] != null) {
					appendTexto70(xMargemDireita, yDebitoCobrado, debito[0]);
				}
				if (debito[1] != null) {
					appendTexto70(697, yDebitoCobrado, debito[1]);
				}
				
				yDebitoCobrado+=yPularLinha;
			}
			debitos = null;
		}
		return yDebitoCobrado;
	}
	
	/**
	 * [SB0006] - Gerar Linhas da Creditos Realizados
	 * 
	 */
	 private int gerarLinhasCreditosRealizados(int yCreditoRealizado) {
		 
		 int indicadorDiscriminarDescricao 
		 		= retornaIndicadorDiscriminar( quantidadeMaximaLinhas, quantidadeLinhasAtual, 'c');
		 
		 	ArrayList<StringBuilder[]> creditos = new ArrayList<StringBuilder[]>();
			// Os dados estão dividos em 2 partes
			// Descricao, de indice 0
			// Valor, de indice 1
			StringBuilder[] dados = null;
			
			ArrayList<CreditoRealizado> arrayListCreditoRealizado = new ArrayList<CreditoRealizado>();
			arrayListCreditoRealizado = (ArrayList<CreditoRealizado>) fachada.buscarCreditoRealizadoPorImovelId(imovel.getId());
			
			// 3
			if (arrayListCreditoRealizado != null) {
				// caso seja para discriminar os dados dos débitos
				if (indicadorDiscriminarDescricao == 1) {
					// caso o valor do crédito seja maior que o valor da conta sem o
					// crédito
					double valorContaSemCreditos = 0d;
					boolean valorCreditoMaiorValorConta = false;
					boolean naoEmitirMaisCreditos = false;
					
					double valorResidual = 0;  
					double valorCreditos = 0;
					
					valorCreditos = fachada.obterValorCreditoTotal(imovel.getId());
					valorContaSemCreditos = fachada.obterValorContaSemCreditos(imovel.getId());
					
					if (valorCreditos != 0d) {
						if (valorContaSemCreditos < valorCreditos) {
							valorResidual = valorCreditos - valorContaSemCreditos;							
						}
					}
					
					if (valorResidual != 0d) {
						valorCreditoMaiorValorConta = true;
					}
					
					for (int i = 0; i < arrayListCreditoRealizado.size(); i++) {
						dados = new StringBuilder[2];					
						CreditoRealizado dadosCreditosRealizado = (CreditoRealizado) arrayListCreditoRealizado.get(i);
						// caso o valor dos créditos n seja maior que o valor da
						// conta sem os créditos
						if (!valorCreditoMaiorValorConta) {
							
							// 1.1.2
							dados[0] = new StringBuilder(dadosCreditosRealizado.getDescricaoCreditoTipo());
							// 1.1.3
							dados[1] = new StringBuilder(""+Util.formatarDoubleParaMoedaReal(dadosCreditosRealizado.getValor().doubleValue()));
						}
						// //caso o valor dos créditos seja maior que o valor das
						// contas sem os créditos
						else {
							if (!naoEmitirMaisCreditos) {
								double valorCredito = dadosCreditosRealizado.getValor().doubleValue();
								double valorContaSemCreditosMenorCredito = valorContaSemCreditos - valorCredito;
								// emite as créditos até o valor dos creditos ser
								// menor que o valor da conta
								if (valorContaSemCreditosMenorCredito < 0d) {
									valorCredito = valorContaSemCreditos;
									naoEmitirMaisCreditos = true;
								} else {
									valorContaSemCreditos = valorContaSemCreditosMenorCredito;
								}
								
								// 1.1.2
								dados[0] = new StringBuilder(dadosCreditosRealizado.getDescricaoCreditoTipo());
								// 1.1.3
								dados[1] = new StringBuilder(""+Util.formatarDoubleParaMoedaReal(valorCredito));
							}
						}
						
						creditos.add(dados);
					}
				} else {
					dados = new StringBuilder[2];
					double soma =0.d;
					
					 for ( int i = 0; i < arrayListCreditoRealizado.size(); i++ ){
					
						 CreditoRealizado dadosCreditosRealizado = (CreditoRealizado ) arrayListCreditoRealizado.get(i);
						 soma += dadosCreditosRealizado.getValor().doubleValue();
					 }
					
					// 1.1.2
					dados[0] = new StringBuilder("CREDITOS");
					// 1.1.3
					dados[1] = new StringBuilder(Util.formatarDoubleParaMoedaReal(soma));
					creditos.add(dados);
				}
			}
			if (creditos!=null){
				for (int i = 0; i < creditos.size(); i++) {			
					StringBuilder[] credito = creditos.get(i);
				
					if (credito[0] != null) {
						appendTexto70(xMargemDireita, yCreditoRealizado, credito[0].toString());
					}
					if (credito[1] != null) {
						appendTexto70(697, yCreditoRealizado, credito[1].toString());
					}
					
					yCreditoRealizado+=yPularLinha;
				}
				creditos = null;
			}
			
			return yCreditoRealizado;
		}
	 
		/**
		 * [SB0007] - Gerar Linhas Impostos Retidos
		 * 
		 * @return Os dados estão dividos em 3 partes Descricao, de indice 0
		 *         Consumo, de indice 1 Valor, de indice 2
		 */
		 private void gerarLinhasImpostosRetidos(int yImposto) {
			
			ArrayList<ContaImposto> arrayContaImposto = 
					(ArrayList<ContaImposto>) fachada.buscarContaImpostoPorImovelId(imovel.getId());
					
			if (arrayContaImposto != null) {
				
				String dadosImposto = "";
				for (int i = 0; i < arrayContaImposto.size(); i++) {
					ContaImposto contaImposto = (ContaImposto) arrayContaImposto.get(i);
					String descricaoImposto = contaImposto.getDescricaoImposto();
					String percentualAliquota = Util.formatarDoubleParaMoedaReal(contaImposto.getPercentualAlicota().doubleValue());
					dadosImposto += descricaoImposto + "-" + percentualAliquota + "% ";
				}
			
				// 1.1.2
				appendTexto70(xMargemDireita,yImposto, "DED. IMPOSTOS LEI FEDERAL N.9430 DE 27/12/1996");
				
				// 1.1.3
				double valorImposto = 0.d;
				valorImposto = fachada.obterValorImpostoTotal(imovel.getId());
				appendTexto70(697,yImposto, Util.formatarDoubleParaMoedaReal(valorImposto));
				
				yImposto+=yPularLinha;
				
				// 1.1.2
				appendTexto70(xMargemDireita,yImposto, dadosImposto);

			}
			
		}
		
		/**
		 * Método responsável por<br>
		 * imprimir a conta comunicado
		 * @author Jonathan Marcos
		 * @since 09/02/2014 
		 * @return StringBuilder
		 */
		public StringBuilder imprimirContaComunicado() {
		   	buffer = new StringBuilder();
		   	try {
		   	    /*
		   	    *  Configuracao do papel
		   	    */
		   		if ( SistemaParametros.getInstancia().getContrasteConta() != 0 )		   		
		   			appendTexto("! 0 816 0 1720 1 CONTRAST " + SistemaParametros.getInstancia().getContrasteConta() + " \n");
		   		else 
		   			appendTexto("! 0 816 0 1720 1 \n");
		    	
		   		/*
		   		*  Data e Hora de Impressão
		   		*/
	    		buffer.append("T90 0 1 814 785 IMPRESSO EM: " + Util.convertDateToDateStr(new Date()) + "\n");
	    		
	    		/*
	    		*  Matrícula do Imóvel
	    		*/
	    		appendTexto("T 7 1 464 66 " + imovel.getId() + "\n");
		    	
	    		/*
		    	*  Ano mes de referencia 
		    	*  da conta
		    	*/
	    		appendTexto("T 7 1 613 66 "+ Util.retornaDescricaoAnoMes(imovel.getAnoMesConta()+"")+ "\n");
	    	    
	    		/*
	    		* Nome e endereço usuário
	    		*/
	    	   appendTexto(formarLinha(0, 2, 52, 164, imovel.getNomeUsuario().trim(), 0, 0).toString());
	    	   appendTexto(dividirLinha(0, 2, 434, 164, imovel.getEndereco(), 40, 27).toString());
		    	
	    	    /*
		    	* Inscricao, 
		    	* Codigo da Rota,
		    	* Sequencial da Rota
		    	*/
	    	   appendTexto(formarLinha(7, 0, 45, 250, fachada.formatarInscricao(imovel.getInscricao()), 0, 0).toString());
	    	   appendTexto(formarLinha(7, 0, 315, 250, imovel.getCodigoRota() + "", 0, 0).toString());
	    	   appendTexto(formarLinha(7, 0, 415, 250, imovel.getSequencialRota() + "", 0, 0).toString());
	    	   
	    	   /*
	    	    * Economias
	    	    */
	    	   gerarCategoriaSubcategoria(imovel.getId());
	    	   
	    	   /*
	    	    *  Extrai informções usadas 
	    	    *  nas próximas impressões
	    	    */
		       String hidrometro = "NÃO MEDIDO";
		       String dataInstacao = null;
		       String situacaoAgua = fachada.getDescricaoSitLigacaoAgua(imovel.getSituacaoLigAgua());
		       String situacaoEsgoto = fachada.getDescricaoSitLigacaoEsgoto(imovel.getSituacaoLigEsgoto());
		       
		       HidrometroInstalado hidrometroInstaladoAgua = fachada.
		       		buscarHidrometroInstaladoPorImovelTipoMedicao(imovel.getId(), ConstantesSistema.LIGACAO_AGUA);
		       
		       HidrometroInstalado hidrometroInstaladoPoco = fachada.
		       		buscarHidrometroInstaladoPorImovelTipoMedicao(imovel.getId(), ConstantesSistema.LIGACAO_ESGOTO);
		       
		       if(hidrometroInstaladoAgua!=null){
		       	hidrometro = hidrometroInstaladoAgua.getNumeroHidrometro();
		       	dataInstacao = Util.dateToString(hidrometroInstaladoAgua.getDataInstalacaoHidrometro());
		       } else if(hidrometroInstaladoPoco!=null){
		       	hidrometro = hidrometroInstaladoPoco.getNumeroHidrometro();
		       	dataInstacao = Util.dateToString(hidrometroInstaladoPoco.getDataInstalacaoHidrometro());
		       }
		       
		       /*
		        *  Hidrometro, 
		        *  Data da Instalação
		        */
	    	   appendTexto(formarLinha(7, 0, 50, 305, hidrometro, 0, 0).toString());
	    	   appendTexto(formarLinha(7, 0, 271, 305, (dataInstacao!=null?dataInstacao:""), 0, 0).toString());
		    	
	    	   /*
	    	    *  Situação da 
	    	    *  ligação de Agua
	    	    */
	    	   if (situacaoAgua.length() > 13) {
	    	   	appendTexto(formarLinha(7, 0, 446, 305, situacaoAgua.substring(0, 13), 0, 0).toString());
	    	   } else {
	    	   	appendTexto(formarLinha(7, 0, 446, 305, situacaoAgua, 0, 0).toString());
	    	   }
		    	
	    	   /*
	    	    *  Situação da 
	    	    *  ligação de esgoto
	    	    */
	    	   appendTexto(formarLinha(7, 0, 627, 305, situacaoEsgoto, 0, 0).toString());
	    	   
	    	   /*
	    	    * Texto conta comunicado
	    	    */
	    	   ContaComunicado contaComunicado = null;
	    	   if(imovel.getContaComunicado()!=null && imovel.getContaComunicado().getId()!=null){
	    		  contaComunicado = (ContaComunicado)RepositorioBasico.getInstance()
						.pesquisarPorId(imovel.getContaComunicado().getId(), new ContaComunicado());
	    		  String texto = contaComunicado.getDescricao();
		    	    
				ArrayList<String> listaLinhasContaComunicado = alinharTextoAhEsquerda(texto);
			   	int posicaoy = 340;
				for(int posicao=0;posicao<listaLinhasContaComunicado.size();posicao++){
					appendTexto(formarLinha(7, 0, 48, posicaoy, listaLinhasContaComunicado.get(posicao), 0, 0).toString());
					posicaoy+=23;
				}	
	    	 } 
		
	    	 appendTexto("FORM\n" + "PRINT ");
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	return buffer;
 }
		
	/**
	 * Método responsável por<br>
	 * alinhar o texto da conta<br>
	 * comunicado a esquerda
	 * @author Jonathan Marcos
	 * @since 09/02/2015
	 * @param texto
	 * @return ArrayList<String>
	 */
	public ArrayList<String> alinharTextoAhEsquerda(String texto){
		/*
		 * Quebra por palavra
		 */
		String[] arrayPalavras = texto.split(" ");
		
		/*
		 * Lista de linha da conta 
		 * comunicado
		 */
		ArrayList<String> listaLinhasContaComunicado = new ArrayList<String>();
		
		/*
		 * Linha da conta comunicado
		 */
		String linha = ContaComunicado.INICIO_LINHA;
		
		for(int posicaoPalavra = 0;posicaoPalavra<arrayPalavras.length;posicaoPalavra++){
			String palavra = arrayPalavras[posicaoPalavra];
			if(palavra.compareTo("")==0){
				linha += " ";
			}else if(palavra.contains(ContaComunicado.CONJUNTO_CARACTERE_ENTER)){
				
				if(palavra.compareTo(ContaComunicado.CONJUNTO_CARACTERE_ENTER)!=0){
					String[] arrayEnter = palavra.split(ContaComunicado.CONJUNTO_CARACTERE_ENTER);
					for(int posicaoEnter = 0;posicaoEnter<arrayEnter.length;posicaoEnter++){
						if(arrayEnter[posicaoEnter].compareTo("")==0){
							listaLinhasContaComunicado.add(linha);
							linha = ContaComunicado.INICIO_LINHA;
						}else{
							if(verificarInicioLinha(linha)){
								linha = arrayEnter[posicaoEnter];
							}else{
								linha +=" "+arrayEnter[posicaoEnter];
							}
							
							if(posicaoEnter+1!=arrayEnter.length){
								listaLinhasContaComunicado.add(linha);
								linha = ContaComunicado.INICIO_LINHA;
							}
						}
					}

					if(palavra.endsWith(ContaComunicado.CONJUNTO_CARACTERE_ENTER)){
						listaLinhasContaComunicado.add(linha);
						linha = ContaComunicado.INICIO_LINHA;
					}
					
				}else{
					listaLinhasContaComunicado.add(linha);
					linha = ContaComunicado.INICIO_LINHA;
				}
			}else{
				if(palavra.length()>=62){
					
					listaLinhasContaComunicado.add(linha);
					
					String palavraIgualOUMaior62 = palavra;
					
					int totalDeCaracterePalavraIgualOUMaior62 = palavraIgualOUMaior62.length();
					
					int quantidadeInteracoes = totalDeCaracterePalavraIgualOUMaior62/62;
					int resto = totalDeCaracterePalavraIgualOUMaior62 - (quantidadeInteracoes*62);
					
					for(int posicaoQuantidadeInteracoes = 0;posicaoQuantidadeInteracoes<quantidadeInteracoes;posicaoQuantidadeInteracoes++){
						linha = palavraIgualOUMaior62.substring(posicaoQuantidadeInteracoes*62, (posicaoQuantidadeInteracoes*62)+62);
						listaLinhasContaComunicado.add(linha);
					}
					
					if(resto!=0){
						linha = palavraIgualOUMaior62.substring(quantidadeInteracoes*62, totalDeCaracterePalavraIgualOUMaior62);
					}
				}else if(linha.length()+palavra.length()<62){
					if(verificarInicioLinha(linha)){
						linha+=palavra;
					}else{
						linha+=" "+palavra;
					}
				}else if(linha.length()+palavra.length()>62){
					listaLinhasContaComunicado.add(linha);
					linha = palavra;
				}	
			}
		}
		
		if(!verificarInicioLinha(linha)){
			listaLinhasContaComunicado.add(linha);
		}
			
		return listaLinhasContaComunicado;
	}
	
	/**
	 * Método responsável por<br>
	 * verificar o início da<br>
	 * linha da conta comunicado
	 * @author Jonathan Marcos
	 * @since 12/02/2015
	 * @param linha
	 * @return boolean
	 */
	public boolean verificarInicioLinha(String linha){
		return linha.compareTo(ContaComunicado.INICIO_LINHA)==0 ? true : false;
	}
}
