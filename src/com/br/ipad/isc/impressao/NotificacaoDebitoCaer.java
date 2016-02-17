package com.br.ipad.isc.impressao;

import java.util.ArrayList;

import android.util.Log;

import com.br.ipad.isc.bean.ContaDebito;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.util.ConstantesSistema;
import com.br.ipad.isc.util.Util;

/**
 * [] Classe Básica - NotificacaoDebitoCaer
 * Classe resposavel por imprimir a notificação de debito da caer.
 * @author Carlos Chavges
 * @since 16/01/2013
 */
public class NotificacaoDebitoCaer extends ImpressaoCaer {

	private static NotificacaoDebitoCaer instancia;
	
	private final int xMargemTextoNotDeb = 40;
		
	private NotificacaoDebitoCaer() {
		super();
	}

	public static NotificacaoDebitoCaer getInstancia() {
		if (instancia == null) {
			instancia = new NotificacaoDebitoCaer();
		}
		return instancia;
	}
	
	
	/**
	 * @author Carlos Chaves
	 * @date 16/01/2013
	 * @return
	 */
    public StringBuilder imprimirNotificacaoDebito(ImovelConta imovel) {
	
	try {
		buffer = new StringBuilder();
		appendTexto("! 0 816 0 1720 1\n");

	    // Matricula do imovel, Ano mes de referencia da conta
		appendTexto("T 7 1 464 66 " + imovel.getId() + "\n");
		appendTexto("T 7 1 613 66 " + Util.retornaDescricaoAnoMes(imovel.getAnoMesConta().toString()) + "\n");

	    // Dados do cliente
	    String cpfCnpjFormatado = "";
	    
	    if (imovel.getCpfCnpjCliente() != null && !imovel.getCpfCnpjCliente().equals("")) {
	    	cpfCnpjFormatado = imovel.getCpfCnpjCliente().trim();
	    }
	    
	    appendTexto20(52, 164, imovel.getNomeUsuario().trim());
	    appendTexto20(52, 194, cpfCnpjFormatado);
	    appendTexto(dividirLinha(0, 2, 434, 164, imovel.getEndereco(), 40, 27).toString());

	    // Inscricao,Codigo da Rota, Sequencial da Rota
	    appendTexto70(48, 250, fachada.formatarInscricao(imovel.getInscricao()));
	    appendTexto70(315, 250, imovel.getCodigoRota() + "");
	    appendTexto70(415, 250, imovel.getSequencialRota() + "");

	    gerarCategoriaSubcategoria(imovel.getId());

	    // Numero do Hidrometro
	    String hidrometro = "NÃO MEDIDO";
	    String situacaoAgua = fachada.getDescricaoSitLigacaoAgua(imovel.getSituacaoLigAgua());
		String situacaoEsgoto = fachada.getDescricaoSitLigacaoEsgoto(imovel.getSituacaoLigEsgoto());

	    // Hidrometro, Data da instalação, Situacao da ligacao de Agua, Situacao da ligacao de esgoto
	    appendTexto70(48, 305, hidrometro);

	    if (situacaoAgua.length() > 13) {
	    	appendTexto70(446, 305, situacaoAgua.substring(0, 13));
	    } else {
	    	appendTexto70(446, 305, situacaoAgua);
	    }

	    appendTexto70(627, 305, situacaoEsgoto);

	    
	    appendTexto("CENTER\n");
	    appendTexto70(0, 345, "NOTIFICAÇÃO DE DÉBITOS - ATENÇÃO");
	    appendTexto("LEFT\n");

	    
	    
	    appendTexto70(xMargemTextoNotDeb,400,"APESAR  DA  TENTATIVA  ANTERIOR  DE COBRAR  AS IMPORTÂNCIAS EM");
	    appendTexto70(xMargemTextoNotDeb,425, "ATRASO, NÃO  HOUVE  QUALQUER  MANIFESTAÇÃO DA  VOSSA  SENHORIA");
	    appendTexto70(xMargemTextoNotDeb,450, "NO  SENTIDO  DE  CUMPRIR  COM  SUA  OBRIGAÇÃO, O QUE GARANTE A");
	    appendTexto70(xMargemTextoNotDeb,475, "SUSPENSÃO  IMEDIATA  DOS SERVIÇOS, CONFORME O ART. 40, V, §2º.");
	    appendTexto70(xMargemTextoNotDeb,500, "DA  LEI FEDERAL 11.445/2007.  ENTRETANTO, POR ACREDITAR NA BOA");
	    appendTexto70(xMargemTextoNotDeb,525, "RELAÇÃO EXISTENTE,  OPORTUNIZAMOS O PRAZO DE 15 (QUINZE)  DIAS");
	    appendTexto70(xMargemTextoNotDeb,550, "PARA QUITAÇÃO  DOS DÉBITOS DISCRIMINADOS  NESTE DOCUMENTO, SOB");
	    appendTexto70(xMargemTextoNotDeb,575, "PENA DE SUSPENSÃO DOS SERVIÇOS POSTOS A SUA  DISPOSIÇÃO. EVITE");
	    appendTexto70(xMargemTextoNotDeb,600, "TRANSTORNOS, REGULARIZE SUA SITUAÇÃO.");
	    appendTexto(formarLinha(7,1,xMargemTextoNotDeb,629, "CASO JÁ TENHA QUITADO O DÉBITO, DESCONSIDERE ESSE AVISO.",0,0).toString());

	    // Referencia Conta / Vencimento / Valor (R$)
	    appendTexto70(40, 688, "REFERENCIA CONTA");
	    appendTexto70(390, 688, "VENCIMENTO");
	    appendTexto70(687, 688, "VALOR(R$)");

	    
	    ArrayList<ContaDebito> arrayListContaDebito = new ArrayList<ContaDebito>();
		arrayListContaDebito = fachada.buscarContasDebitosPorIdImovel(imovel.getId());
		double valorDebitosAnteriores = 0;
		StringBuilder linhas = new StringBuilder();

		//Imprime apenas 17 Debitos por causa do espaço no papel
		for (int qtdLinhas = 0; (qtdLinhas < arrayListContaDebito.size() && qtdLinhas<17); qtdLinhas++) {
			 ContaDebito  dadosContaDebito = ((ContaDebito) arrayListContaDebito.get(qtdLinhas));
		
			if (dadosContaDebito.getAnoMesReferencia().equals("DB.ATE")) {
				linhas.append(formarLinha(7, 0, 40, 715, dadosContaDebito.getAnoMesReferencia() + "", 0, qtdLinhas * 25));
			} else {
				linhas.append(formarLinha(7, 0, 40, 715, Util.formatarAnoMesParaMesAno(dadosContaDebito.getAnoMesReferencia() + ""), 0, qtdLinhas * 25));
			}
			valorDebitosAnteriores += dadosContaDebito.getValorConta().doubleValue();
			linhas.append(formarLinha(7, 0, 390, 715, Util.dateToString(dadosContaDebito.getDataVencimentoConta()), 0,qtdLinhas * 25));
			linhas.append(formarLinha(7, 0, 687, 715, Util.formatarDoubleParaMoedaReal(dadosContaDebito.getValorConta().doubleValue()), 0,qtdLinhas * 25));
			
			if (qtdLinhas > 17) {
			    break;
			}
	    }

		buffer.append(linhas);

	    // Data de emissão do documento
		appendTexto(formarLinha(0, 2, 37, 1220, "EMISSAO:", 0, 0).toString());
		appendTexto(formarLinha(7, 1, 160, 1224, Util.dateToString(imovel.getDataEmissaoDocumento()), 0, 0).toString());

	    // Total a pagar
	    appendTexto(formarLinha(4, 0, 555, 1224, Util.formatarDoubleParaMoedaReal(valorDebitosAnteriores), 0, 0).toString());

	    String representacaoNumericaCodBarraFormatada = Util.formatarCodigoBarras(imovel.getNumeroCodigoBarraNotificacaoDebito());

	    appendTexto(formarLinha(5, 0, 66, 1290,representacaoNumericaCodBarraFormatada, 0, 0).toString());

	    if (representacaoNumericaCodBarraFormatada != null && !representacaoNumericaCodBarraFormatada.equals("")) {

		String representacaoCodigoBarrasSemDigitoVerificador = representacaoNumericaCodBarraFormatada.substring(0, 11)
			+ representacaoNumericaCodBarraFormatada.substring(14,25)
			+ representacaoNumericaCodBarraFormatada.substring(28,39)
			+ representacaoNumericaCodBarraFormatada.substring(42,53);

		appendTexto("B I2OF5 1 2 90 35 1315 "
			+ representacaoCodigoBarrasSemDigitoVerificador + "\n");
	    }

	    appendTexto20(320, 1460, "MATRICULA");
	    appendTexto20(423, 1460, "N. DOCUMENTO");
	    appendTexto20(538, 1460, "EMISSAO");
	    appendTexto20(674, 1460, "TOTAL A PAGAR");
	    appendTexto20(320, 1500, imovel.getId().toString());
	    
	    if(imovel.getIdDocumentoNotificacaoDebito()!=null 
	    		&& !imovel.getIdDocumentoNotificacaoDebito().equals("")){
	    	
	    	appendTexto20(423, 1500, imovel.getIdDocumentoNotificacaoDebito()+"");
	    }
	    
	    appendTexto20(538, 1500, Util.dateToString(imovel.getDataEmissaoDocumento()));
	    appendTexto20(674, 1500, Util.formatarDoubleParaMoedaReal(valorDebitosAnteriores));

	    appendTexto20(35, 1550, "RECEBI A NOTIFICACAO DOS DEBITOS ACIMA RELACIONADOS: " +
	    		"CICLO: " + Util.formatarAnoMesParaMesAno(imovel.getAnoMesConta()+""));
	    
	    appendTexto20(35, 1575, "HORA: " + Util.dateToHoraString(Util.dataAtual()));
	    appendTexto20(35, 1600, "LOCALIDADE: " + imovel.getLocalidade() + " / " + "SETOR: " + imovel.getInscricao().toString().substring(3, 6));
	    appendTexto20(200, 1625,"____________________________________________________________");
	    appendTexto20(340, 1650, "Assinatura do Recebedor");
	    appendTexto( "FORM\n" + "PRINT ");
	    
	} catch (Exception ex) {
	    ex.printStackTrace();
	    Log.e(ConstantesSistema.CATEGORIA, ex.getMessage());
	    Log.e(ConstantesSistema.CATEGORIA, "ERRO na notificação de debito.");
	}
	
	return buffer;
    
    }

	

}