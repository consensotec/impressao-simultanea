package com.br.ipad.isc.impressao;

import java.util.ArrayList;
import java.util.Date;

import com.br.ipad.isc.bean.CategoriaSubcategoria;
import com.br.ipad.isc.bean.ConsumoHistorico;
import com.br.ipad.isc.bean.HidrometroInstalado;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.bean.SistemaParametros;
import com.br.ipad.isc.util.ConstantesSistema;
import com.br.ipad.isc.util.Util;

public class ExtratoMacroCaern extends ImpressaoCaern{
	
	
	private static ExtratoMacroCaern instancia;

	
	private ExtratoMacroCaern() {
		super();
	}
	
	  public static ExtratoMacroCaern getInstancia(
		        ImovelConta imovelInformado) {
		    	if (instancia == null) {
		        instancia = new ExtratoMacroCaern();
		    	}
		    	
		    	instancia.imovel = imovelInformado;    	
		    	return instancia;
		    }

	
	
	/**
	 * Método que impre o extrato de consumo do macro medidor dos imóveis
	 * condomínio seguindo layout compesa
	 * 
	 * @author Carlos Chaves
	 * @date 28/08/2012
	 */
    public StringBuilder obterStringExtratoMacroCaern() {
    	
    try {
    	
    	buffer = new StringBuilder();
    	
        // Tamanho da folha / unidade de medida / Line Feed como fim de
        if ( SistemaParametros.getInstancia().getContrasteConta() != 0 )		   		
        	appendTexto("! 0 200 200 225 1\nIN-MILLIMETERS CONTRAST " + SistemaParametros.getInstancia().getContrasteConta() + "\nLT LF\n");
   		else 
   			appendTexto("! 0 200 200 225 1\nIN-MILLIMETERS\nLT LF\n");        	

        // Escritório de atendimento
        final int yEscAtendimento = 6;
        String endereco = Util.replaceAll(imovel.getEnderecoAtendimento().trim().toUpperCase(), " -   -", ",");
        String telefone = SistemaParametros.getInstancia().getTelefone0800().trim().toUpperCase();
        String telLocalidade = imovel.getTelefoneLocalidadeDDD().trim().toUpperCase();
        
        appendTextos(0, 0, 76, yEscAtendimento, endereco, 26, 2);
        appendTexto70(76, yEscAtendimento + 5, telefone);
        appendTexto70(76, yEscAtendimento + 8,formatarTelefone(telLocalidade));

        // Título da conta
        appendTexto("CENTER\n");
        appendTexto70(0, 19, "EXTRATO DE CONSUMO");
        appendTexto("LEFT\n");
        
        // Data e Hora de Impressão
	    Date date = new Date();
	    String data = Util.dateToString(date);
	    String hora = Util.dateToHoraString(date);
	    
	    appendTexto("LEFT\n");
	    appendTexto70(3, 23, "IMPRESSO EM "+data+" " + "AS "+hora);

        // Matrícula / Referência
        final int yDados = 29;
        appendTexto70(58, yDados, Integer.toString(imovel.getId()));
        appendTexto70(80, yDados, Util.formatarAnoMesParaMesAno(imovel.getAnoMesConta().toString()));

        // Dados do cliente
        final int yNomeEndereco = 35;
        appendTexto70(5, yNomeEndereco, imovel.getNomeUsuario());
        appendTextos(7, 0, 5, yNomeEndereco + 3, cortarEndereco(imovel.getEndereco()), 3);

        // Inscrição / Rota / Sequência da rota
        final int yInsRotaSeq = 48;
        appendTexto70(5, yInsRotaSeq, fachada.formatarInscricao(imovel.getInscricao()));
        appendTexto70(42, yInsRotaSeq, Integer.toString(imovel.getCodigoRota()));
        appendTexto70(53, yInsRotaSeq, Integer.toString(imovel.getSequencialRota()));

        // Ecônomias
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
        Integer leituraAnterior = null;
        Integer leituraAtual = null;
        Integer diasConsumo = null;
        Integer consumoRateioAgua = null;
        Integer consumoRateioEsgoto = null;
        
        ConsumoHistorico consumoAgua = null;
        ConsumoHistorico consumoEsgoto = null;
        

        HidrometroInstalado hidrometroInstaladoAgua = fachada.
        		buscarHidrometroInstaladoPorImovelTipoMedicao(imovel.getId(), ConstantesSistema.LIGACAO_AGUA);
        
        HidrometroInstalado hidrometroInstaladoPoco = fachada.
        		buscarHidrometroInstaladoPorImovelTipoMedicao(imovel.getId(), ConstantesSistema.LIGACAO_ESGOTO);
        
        String dataLeitura = "";
        
        consumoAgua = fachada.
        		buscarConsumoHistoricoPorImovelIdTipoLigacao(imovel.getId(), ConstantesSistema.LIGACAO_AGUA);
        
        consumoEsgoto = fachada.
				buscarConsumoHistoricoPorImovelIdTipoLigacao(imovel.getId(), ConstantesSistema.LIGACAO_ESGOTO);
	    
	    ImovelConta imovelAreaComum =  null;
	    ConsumoHistorico consumoAguaAreaComum = null;
		ConsumoHistorico consumoEsgotoAreaComum = null;

        // Prioridade para as informações de água
        if (hidrometroInstaladoAgua != null) {
        	
	        hidrometro = hidrometroInstaladoAgua.getNumeroHidrometro();
	        leituraAnterior = fachada.obterLeituraAnterior(hidrometroInstaladoAgua);
	        dataLeitura = Util.dateToString(hidrometroInstaladoAgua.getDataLeitura());
			
			if( hidrometroInstaladoAgua.getTipoRateio() !=null 
					&& hidrometroInstaladoAgua.getTipoRateio().intValue() == ConstantesSistema.TIPO_RATEIO_AREA_COMUM ){
				
				imovelAreaComum = fachada.obterImovelAreaComum(imovel.getId());
			}
			
			if(imovelAreaComum != null){
				
				consumoAguaAreaComum = fachada.
						buscarConsumoHistoricoPorImovelIdTipoLigacao(imovelAreaComum.getId(), ConstantesSistema.LIGACAO_AGUA);
				
				consumoEsgotoAreaComum = fachada.
		    			buscarConsumoHistoricoPorImovelIdTipoLigacao(imovelAreaComum.getId(), ConstantesSistema.LIGACAO_ESGOTO);
			}
	
	        if (consumoAgua != null) {
	
	            if (hidrometroInstaladoAgua.getLeituraAtualFaturamento() != null) {
	            	leituraAtual = hidrometroInstaladoAgua.getLeituraAtualFaturamento();
	            	diasConsumo = consumoAgua.getDiasConsumo();
			    } 
	            else {
	            	
	            	if (consumoAgua.getLeituraAtual() != null) {
	            		leituraAtual = consumoAgua.getLeituraAtual();
	            	}
	            	
	            	diasConsumo = consumoAgua.getDiasConsumo();
	            	
			    }
	            
			    if(consumoAguaAreaComum != null && consumoAguaAreaComum.getConsumoRateio() != null){
					
			    	consumoRateioAgua =  consumoAguaAreaComum.getConsumoRateio();
				}else{
					consumoRateioAgua =  consumoAgua.getConsumoRateio();
				}
			}
	
	        if(consumoEsgotoAreaComum != null && consumoEsgotoAreaComum.getConsumoRateio() != null){
					
				 consumoRateioEsgoto =  consumoEsgotoAreaComum.getConsumoRateio();
		    }else{ 
		    	
		    	if(consumoEsgoto != null && consumoEsgoto.getConsumoRateio() != null) {
		    		consumoRateioEsgoto = consumoEsgoto.getConsumoRateio();
		    	}
		    }


        } else if (hidrometroInstaladoPoco != null) {
	        
	        hidrometro = hidrometroInstaladoPoco.getNumeroHidrometro();
	        leituraAnterior = fachada.obterLeituraAnterior(hidrometroInstaladoPoco);
	        dataLeitura = Util.dateToString(hidrometroInstaladoPoco.getDataLeitura());
			
			if( (hidrometroInstaladoAgua != null && hidrometroInstaladoAgua.getTipoRateio() != null 
					&& hidrometroInstaladoAgua.getTipoRateio().intValue() == ConstantesSistema.TIPO_RATEIO_AREA_COMUM) 
					|| (hidrometroInstaladoPoco.getTipoRateio() != null 
					&& hidrometroInstaladoPoco.getTipoRateio().intValue() == ConstantesSistema.TIPO_RATEIO_AREA_COMUM)){
				
				imovelAreaComum = fachada.obterImovelAreaComum(imovel.getId());
			}
			
			if(imovelAreaComum != null){

				consumoEsgotoAreaComum = fachada.
		    			buscarConsumoHistoricoPorImovelIdTipoLigacao(imovelAreaComum.getId(), ConstantesSistema.LIGACAO_ESGOTO);
			}
	
	        if (consumoEsgoto != null) {
	
	            if (hidrometroInstaladoPoco.getLeituraAtualFaturamento() != null) {
	            	leituraAtual = hidrometroInstaladoPoco.getLeituraAtualFaturamento();
	            	diasConsumo = consumoEsgoto.getDiasConsumo();
	
	            } else {
	            	leituraAtual = consumoEsgoto.getLeituraAtual();
	            	diasConsumo  = consumoEsgoto.getDiasConsumo();
	            }
			    
	            if(consumoEsgotoAreaComum != null && consumoEsgotoAreaComum.getConsumoRateio() != null){
					
			    	consumoRateioEsgoto = consumoEsgotoAreaComum.getConsumoRateio();
				}else{
					if (consumoEsgoto.getConsumoRateio() != null) {
						consumoRateioEsgoto = consumoEsgoto.getConsumoRateio();
					}
				}
	        }
        }

        else if (hidrometroInstaladoAgua == null && hidrometroInstaladoPoco == null) {

	        if (consumoAgua != null) {
	
	            leituraAtual = consumoAgua.getLeituraAtual();
	            diasConsumo = consumoAgua.getDiasConsumo();
	        }
        }

        // Hidrômetro / Situação da agua / Situação da esgoto
        final int yHdAgEs = 55;
        appendTexto70(10, yHdAgEs, hidrometro);
        appendTexto70(45, yHdAgEs, situacaoAgua);
        appendTexto70(78, yHdAgEs, situacaoEsgoto);

        int yCorpoConta = 60;
       
        // Data de leitura atual / Leitura Atual / Leitura Anterior / Dias
        // consumo
        appendTexto70(5, yCorpoConta, "DATA LEITURA:  " + dataLeitura);
        appendTexto70(5, yCorpoConta + 3, "LEIT. ATUAL:   " + leituraAtual);
        appendTexto70(5, yCorpoConta + 6, "LEIT. ANT.:    "+ leituraAnterior);
        appendTexto70(5, yCorpoConta + 9, "DIAS CONSUMO:  " + diasConsumo);

	    yCorpoConta = gerarHistorico(hidrometroInstaladoAgua, hidrometroInstaladoPoco, yCorpoConta += 12) + 10;
	    
	    appendLinha(5, yCorpoConta, 100, yCorpoConta, 0.1f);
	    
	    appendTexto("CENTER\n");
	    appendTexto70(0, ++yCorpoConta, "EXTRATO DE CONSUMO DO MACROMEDIDOR");
	    appendTexto("LEFT\n");
	    
	    ++yCorpoConta;
	    
	    int consumoCobrado = 0;
	    
	    if (consumoAgua != null){
	    	consumoCobrado = consumoAgua.getConsumoCobradoMes();
	    }else{
	    	if (consumoEsgoto != null){
	    	  consumoCobrado = consumoEsgoto.getConsumoCobradoMes();
	    	}
	    }
	    
	    int consumoRateado = 0;
	    Integer tipoLigacao = null;
	    
    	if(consumoRateioAgua != null){
    		consumoRateado = consumoRateioAgua;
    		tipoLigacao = ConstantesSistema.LIGACAO_AGUA;
    	}else{
    		if(consumoRateioEsgoto != null){
	    		consumoRateado = consumoRateioEsgoto;
	    		tipoLigacao = ConstantesSistema.LIGACAO_ESGOTO;
	    	}
    	}
    	
    	appendTexto70(5, yCorpoConta + 3, "CONSUMO DO IMÓVEL CONDOMÍNIO (M3): " +consumoCobrado );
  	    appendTexto70(5, yCorpoConta + 6, "SOMA DOS CONSUMOS DOS IMÓVEIS VINCULADOS (M3): " +fachada.obterConsumoImoveisMicro(imovel.getId(),tipoLigacao) +" m3" );
  	    appendTexto70(5, yCorpoConta + 9, "QUANTIDADE DE IMÓVEIS VINCULADOS (M3): " + (fachada.obterQuantidadeImovelMicro(imovel.getId()) - 1) + "" );
  	    
  	  
    	if(imovelAreaComum == null){
    		if(((int) consumoRateado / fachada.obterQuantidadeImovelMicro(imovel.getId())) == 0){
    			consumoRateado = 0;
    		}
    	}
	    
		appendTexto70(5, yCorpoConta + 12, "CONSUMO RATEADO (M3): " + consumoRateado);
	    
	    yCorpoConta += 16;
	    
	    appendLinha(5, yCorpoConta, 100, yCorpoConta, 0.1f);
	    
	    appendTexto("CENTER\n");
	    appendTexto70(0, ++yCorpoConta, "IMPORTANTE");
	    appendTexto("LEFT\n");
	    
	    yCorpoConta += 4;
	    
	    String [] importante = new String[] {
		    "CASO O VALOR DO RATEIO ESTEJA ELEVADO:",
		    "",
		    "1 - Confirme a leitura do macro.",
		    "2 - Verifique os reservatórios.",
		    "3 - Verifique se há apartamento ligado clandestino.",
		    "",
		    "",
		    "QUALQUER IRREGULARIDADE COMUNIQUE A CAERN ATRAVÉS DO",
		    "SETOR DE ATENDIMENTO.",
		    "",
		    "RATEIO: Obtido através da diferença do consumo do",
		    "        macromedidor e os dos apartamentos."
	    };
	    
	    appendTextos(7, 0, 5, yCorpoConta, importante, 3);    
	    
	    // Comando que faz a impressão
	    appendTexto(comandoImpressao());

        
    } catch (Exception ex) {
        ex.printStackTrace();
    } 

    return buffer;
    }
    
}
