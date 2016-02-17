package com.br.ipad.isc.util;



public class Device {

	// Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
    
    // Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

//    private StringBuffer buffer;
    
    public static String imprimirConta() {
		String retorno = null;
		
		retorno = "! 0 816 0 1720 1\r\n";
		retorno += "T 90 0 1 814 785 IMPRESSO EM: 07/03/2012 AS: 12:19:56\r\n";
		retorno += "T 7 0 240 53 FATURA MENSAL DE ÁGUA E ESGOTO\r\n";
		retorno += "T 7 0 440 95 55912874\r\n";
		retorno += "T 7 0 573 95 Março / 2012\r\n";
		retorno += "T 7 0 48 140 FABIO GOMES DO NASCIMENTO\r\n";
		retorno += "T 7 0 563 137 \r\n";
		retorno += "T 7 0 47 163 R DOMINGOS FERNANDES, BL. 127 - APT-207\r\n";
		retorno += "T 7 0 47 183 - MARCOS FREIRE JABOATAO DOS G\r\n";
		retorno += "T 7 0 41 258 029.140.999.0181.111\r\n";
		retorno += "T 7 0 320 258 1\r\n";
		retorno += "T 7 0 440 270 1\r\n";
		retorno += "T 7 0 45 323 9998\r\n";
		retorno += "T 7 0 182 323 A10B268149\r\n";
		retorno += "T 7 0 419 323 LIGADO\r\n";
		retorno += "T 7 0 624 323 LIGADO\r\n";
		retorno += "T 7 0 45 360 LEIT ATUAL INF:\r\n";
		retorno += "T 7 0 280 360 1470\r\n";
		retorno += "T 7 0 45 385 LEIT. ATUAL FAT:\r\n";
		retorno += "T 7 0 280 385 1470\r\n";
		retorno += "T 7 0 45 410 LEIT. ANT:\r\n";
		retorno += "T 7 0 280 410 43\r\n";
		retorno += "T 7 0 45 435 CONSUMO (m3):\r\n";
		retorno += "T 7 0 280 435 30\r\n";
		retorno += "T 7 0 410 435 CONSUMO RATEIO (m3): \r\n";
		retorno += "T 7 0 650 435 0\r\n";
		retorno += "T 7 0 410 360 DATA LEITURA ANT:\r\n";
		retorno += "T 7 0 650 360 15/02/2012\r\n";
		retorno += "T 7 0 410 385 DATA LEITURA ATUAL:\r\n";
		retorno += "T 7 0 650 385 06/03/2012\r\n";
		retorno += "T 7 0 410 410 DIAS CONSUMO:\r\n";
		retorno += "T 7 0 650 410 20\r\n";
		retorno += "LINE 45 470 790 470 2\r\n";
		retorno += "BOX 283 507 790 571 1\r\n";
		retorno += "BOX 283 570 790 639 1\r\n";
		retorno += "LINE 656 551 656 639 1\r\n";
		retorno += "LINE 425 551 425 639 1\r\n";
		retorno += "LINE 535 551 535 639 1\r\n";
		retorno += "LINE 250 515 250 690 1\r\n";
		retorno += "T 7 0 45 476 HISTÓRICO DE CONSUMO\r\n";
		retorno += "T 7 0 324 476 OPÇÃO PELO DEB. AUTOMÁTICO:\r\n"; 
		retorno += "T 7 0 649 476 55912877\r\n";
		retorno += "T 7 0 448 520 QUALIDADE DA AGUA\r\n";
		retorno += "T 7 0 65 506 REFERENCIA\r\n";
		retorno += "T 7 0 195 506 CONS\r\n"; 
		retorno += "T 7 0 85 533 02/2012\r\n";
		retorno += "T 7 0 205 533 08\r\n";
		retorno += "T 7 0 85 559 12/2011\r\n";
		retorno += "T 7 0 205 559 02\r\n";
		retorno += "T 7 0 85 585 11/2011\r\n";
		retorno += "T 7 0 205 585 04\r\n";
		retorno += "T 7 0 85 611 10/2011\r\n";
		retorno += "T 7 0 205 611 03\r\n";
		retorno += "T 7 0 85 637 09/2011\r\n";
		retorno += "T 7 0 205 637 06\r\n";
		retorno += "T 7 0 85 663 08/2011\r\n";
		retorno += "T 7 0 205 663 07\r\n";
		retorno += "T 7 0 65 690 MEDIA\r\n";
		retorno += "T 7 0 205 690 05\r\n";
		retorno += "T 7 0 287 547 PARAMETROS\r\n";
		retorno += "T 7 0 429 547 UNIDADE\r\n";
		retorno += "T 7 0 556 547 PADRAO\r\n";
		retorno += "T 7 0 662 547 VL. OBTIDO\r\n";
		retorno += "T 0 0 287 584 CLORO\r\n";
		retorno += "T 0 0 287 613 TURBIDEZ\r\n";
		retorno += "T 0 2 556 584 1\r\n";
		retorno += "T 0 2 429 584 mgCI/L\r\n";
		retorno += "T 0 2 429 613 uT\r\n";
		retorno += "T 0 2 690 584\r\n"; 
		retorno += "T 0 2 556 613 1\r\n";
		retorno += "T 0 2 690 613"; 
		retorno += "T 7 0 390 645 Reclamação de vazamento  de água\r\n";
		retorno += "T 7 0 390 670 e extravasamento de esgoto ligue\r\n";
		retorno += "T 7 0 390 695 para o número 0800 081 0185.\r\n";
		retorno += "LINE 45 720 790 720 2\r\n";
		retorno += "T 7 0 47 725 DESCRICAO\r\n";
		retorno += "T 7 0 530 725 CONSUMO\r\n";
		retorno += "T 7 0 680 725 TOTAL(R$)\r\n";
		retorno += "T 7 0 47 760 AGUA\r\n";
		retorno += "T 7 0 55 785 RESIDENCIAL 1 UNIDADE(S)\r\n";
		retorno += "T 7 0 73 810 ATE 10 M3 - 26,23 POR UNIDADE\r\n";
		retorno += "T 7 0 530 810 10 M3\r\n";
		retorno += "T 7 0 680 810 26,23\r\n";
		retorno += "T 7 0 73 835 11 M3 A 20 M3 - R$ 3,01 POR M3\r\n"; 
		retorno += "T 7 0 530 835 10 M3\r\n"; 
		retorno += "T 7 0 680 835 30,10\r\n";
		retorno += "T 7 0 73 860 21 M3 A 30 M3 - R$ 3,58 POR M3\r\n"; 
		retorno += "T 7 0 530 860 10 M3\r\n"; 
		retorno += "T 7 0 680 860 35,80\r\n";
		retorno += "T 7 0 53 885 ESGOTO\r\n"; 
		retorno += "T 7 0 530 885 17 M3\r\n";
		retorno += "T 7 0 680 885 23,65\r\n";
		retorno += "T 7 0 45 1248 VENCIMENTO:\r\n";
		retorno += "T 7 1 185 1248 10/04/2012\r\n";
		retorno += "T 4 0 600 1248 115,78\r\n";
		retorno += "T 7 0 45 1305 MENSAGEM:\r\n";
		retorno += "T 7 0 47 1335 NO ESTOURO DE CONSUMO REGISTRADO NESTA F\r\n";
		retorno += "T 7 0 47 1365 ATURA, SERA COBRADA 2 VEZES O CONSUMO ME\r\n";
		retorno += "T 7 0 47 1395 DIO, VERIFICAR EXISTENCIA DE VAZAMENTO.\r\n"; 
		retorno += "T 0 2 178 1523 55912874\r\n";
		retorno += "T 0 2 294 1523 03/2012\r\n";
		retorno += "T 0 2 408 1523 10/04/2012\r\n";
		retorno += "T 0 2 544 1523 30/06/2012\r\n";
		retorno += "T 0 2 695 1523 115,78\r\n";
		retorno += "T 7 0 70 1555 82860000001-0 15780018029-1 05591287401-2 03201270003-5\r\n";
		retorno += "B I2OF5 1 2 105 35 1576 82860000001157800180290559128740103201270003\r\n";
		retorno += "FORM\r\n";
		retorno += "PRINT\r\n"; 
		
		
//		try {

//			// Carregando Informações
//			String hidrometro = "NÃO MEDIDO";
//			String situacaoAgua = "3";
//			String situacaoEsgoto = "3";
//			String leituraAnterior = "919";
//			String leituraInformada = "940";
//			String leituraAtual = "940";
//			String consumo = "30";
//			String consumoRateio = "0";
//			String diasConsumo = "63";
//			
//			//RETIRAR//
////			Consumo consumoAgua = null;
////			Consumo consumoEsgoto = null;
////			ImovelReg8 registro8Agua = imovel.getRegistro8(Constantes.LIGACAO_AGUA);
////			ImovelReg8 registro8Poco = imovel.getRegistro8(Constantes.LIGACAO_POCO);
////			String dataLeituraAnterior = "";
////			String dataLeitura = "";
////			consumoAgua = imovel.getConsumoAgua();
////			consumoEsgoto = imovel.getConsumoEsgoto();
////
////			if (registro8Agua != null) {
////
////				hidrometro = registro8Agua.getNumeroHidrometro();
////				leituraAnterior = registro8Agua.getLeituraAnteriorFaturamento() + "";
////
////				dataLeituraAnterior = Util.dateToString(registro8Agua.getDataLeituraAnterior());
////				dataLeitura = Util.dateToString(registro8Agua.getDataLeitura());
////
////				if (consumoAgua != null) {
////
////					if (registro8Agua.getLeituraAtualFaturamento() != Constantes.NULO_INT) {
////
////						leituraAtual = registro8Agua.getLeituraAtualFaturamento() + "";
////						leituraInformada = String.valueOf(registro8Agua.getLeitura());
////						consumo = consumoAgua.getConsumoCobradoMes() + "";
////						diasConsumo = Long.toString(registro8Agua.getQtdDiasAjustado());
////
////					} else {
////						if (consumoAgua.getLeituraAtual() != Constantes.NULO_INT) {
////							leituraAtual = consumoAgua.getLeituraAtual() + "";
////
////							if (registro8Agua.getLeitura() == Constantes.NULO_INT) {
////								leituraInformada = "";
////							} else {
////								leituraInformada = String.valueOf(registro8Agua.getLeitura());
////							}
////
////						} else {
////							leituraAtual = "";
////						}
////
////						consumo = consumoAgua.getConsumoCobradoMes() + "";
////						if (registro8Agua.getQtdDiasAjustado() != Constantes.NULO_INT) {
////							diasConsumo = Long.toString(registro8Agua.getQtdDiasAjustado());
////						} else {
////							diasConsumo = Long.toString(consumoAgua.getDiasConsumo());
////						}
////						// mediaDia = (int) ( consumoAgua.getConsumoCobradoMes()
////						// /
////						// consumoAgua.getDiasConsumo() ) + "";
////					}
////
////				}
////
////			} else if (registro8Poco != null) {
////
////				hidrometro = registro8Poco.getNumeroHidrometro();
////				leituraAnterior = registro8Poco.getLeituraAnteriorFaturamento() + "";
////
////				dataLeituraAnterior = Util.dateToString(registro8Poco.getDataLeituraAnterior());
////				dataLeitura = Util.dateToString(registro8Poco.getDataLeitura());
////				// dataInstacao =
////				// Util.dateToString(registro8Poco.getDataInstalacao());
////
////				if (consumoEsgoto != null) {
////
////					if (registro8Poco.getLeituraAtualFaturamento() != Constantes.NULO_INT) {
////
////						leituraAtual = registro8Poco.getLeituraAtualFaturamento() + "";
////						leituraInformada = registro8Poco.getLeitura() + "";
////						consumo = consumoEsgoto.getConsumoCobradoMes() + "";
////						diasConsumo = Long.toString(registro8Poco.getQtdDiasAjustado());
////
////					} else {
////						if (consumoEsgoto.getLeituraAtual() != Constantes.NULO_INT) {
////							leituraAtual = consumoEsgoto.getLeituraAtual() + "";
////
////							if (registro8Poco.getLeitura() == Constantes.NULO_INT) {
////								leituraInformada = "";
////							} else {
////								leituraInformada = registro8Poco.getLeitura() + "";
////							}
////
////						} else {
////							leituraAtual = "";
////						}
////
////						consumo = consumoEsgoto.getConsumoCobradoMes() + "";
////						if (registro8Poco.getQtdDiasAjustado() != Constantes.NULO_INT) {
////							diasConsumo = Long.toString(registro8Poco.getQtdDiasAjustado());
////						} else {
////							diasConsumo = Long.toString(consumoEsgoto.getDiasConsumo());
////						}
////						// mediaDia = (int) (
////						// consumoEsgoto.getConsumoCobradoMes() /
////						// consumoEsgoto.getDiasConsumo() ) + "";
////					}
////
////				}
////			}
////
////			else if (registro8Agua == null && registro8Poco == null) {
////
////				if (consumoAgua != null) {
////
////					if (consumoAgua.getLeituraAtual() != Constantes.NULO_INT) {
////						leituraAtual = consumoAgua.getLeituraAtual() + "";
////					} else {
////						leituraAtual = "";
////					}
////
////					consumo = consumoAgua.getConsumoCobradoMes() + "";
////					diasConsumo = consumoAgua.getDiasConsumo() + "";
////
////				} else {
////					if (consumoEsgoto != null) {
////						if (consumoEsgoto.getLeituraAtual() != Constantes.NULO_INT) {
////							leituraAtual = consumoEsgoto.getLeituraAtual() + "";
////						} else {
////							leituraAtual = "";
////						}
////
////						consumo = consumoEsgoto.getConsumoCobradoMes() + "";
////						diasConsumo = consumoEsgoto.getDiasConsumo() + "";
////					}
////				}
////			}
//			// Fim de Carregamento de Informações
//
//			// INICIANDO SCRIPT
//
//			// Header
//			// retorno = "! 0 200 200 2080 1";
//			retorno = "! 0 816 0 1720 1\r\n";
//
//			// Data e Hora de Impressão
//			String data = "02/03/2012";
//			String hora = "11:28";
//
//			retorno += "T90 0 1 814 785 IMPRESSO EM: " + data + " " + "AS: " + hora + "\r\n";
//
//			retorno += formarLinha(7, 0, 240, 53, "FATURA MENSAL DE ÁGUA E ESGOTO", 0, 0);
//
//			// Matricula do imovel
//			retorno += formarLinha(7, 0, 440, 95, 123456789 + "", 0, 0);
//
//			// Ano mes de referencia da conta
//			// retorno += formarLinha(7, 0, 568, 95, "MÊS/ANO:", 0, 0);
//
//			String mes = "30/07";
//			String ano = "2012";
//
//			retorno += formarLinha(7, 0, 573, 95, mes + " / " + ano, 0, 0);
//
//			// Dados do cliente
//			String cpfCnpjFormatado = "";
//			// if (imovel.getCpfCnpjCliente() != null &&
//			// !imovel.getCpfCnpjCliente().equals("")) {
//			// cpfCnpjFormatado = imovel.getCpfCnpjCliente().trim();
//			// }
//
//			retorno += formarLinha(7, 0, 48, 140, "Saviola Vulgo Jack LeClair", 0, 0)
//					+ formarLinha(7, 0, 563, 137, cpfCnpjFormatado, 0, 0)
//					+ dividirLinha(7, 0, 47, 163, "Rua dos Artistas", 40, 20);
//
//			// Inscricao
//			retorno += formarLinha(7, 0, 41, 258, "096.0310.074.0195.003", 0, 0);
//
//			// Grupo de Faturamento
//			retorno += formarLinha(7, 0, 320, 258, "2" + "", 0, 0);
//
//			// Verificamos é por categoria ou subcategoria
////			Vector regsTipo2 = imovel.getRegistros2();
//			Vector quantidadeEconomias = /*categoriasEconomias(regsTipo2)*/ new Vector(1);
//
//			String qtdEconResidencial = "";
//			String qtdEconComercial = "";
//			String qtdEconIndustrial = "";
//			String qtdEconPublico = "";
//
//			for (int i = 0; i < quantidadeEconomias.size(); i++) {
//				Object[] dadosCategoria = (Object[]) quantidadeEconomias.elementAt(i);
//
//				System.out.println(dadosCategoria[0]);
//
//				if (dadosCategoria[0].toString().equals("RESIDENC")) {
//					qtdEconResidencial = dadosCategoria[1].toString();
//					retorno += formarLinha(7, 0, 440, 270, qtdEconResidencial + "", 0, 0);
//				} else if (dadosCategoria[0].toString().equals("COMERCIA")) {
//					qtdEconComercial = dadosCategoria[1].toString();
//					retorno += formarLinha(7, 0, 540, 270, qtdEconComercial + "", 0, 0);
//				} else if (dadosCategoria[0].toString().equals("INDUSTRI")) {
//					qtdEconIndustrial = dadosCategoria[1].toString();
//					retorno += formarLinha(7, 0, 640, 270, qtdEconIndustrial + "", 0, 0);
//				} else if (dadosCategoria[0].toString().equals("PUBLICO")) {
//					qtdEconPublico = dadosCategoria[1].toString();
//					retorno += formarLinha(7, 0, 740, 270, qtdEconPublico + "", 0, 0);
//				}
//
//			}
//
//			// Codigo da Rota
//			retorno += formarLinha(7, 0, 45, 323, "310" + "", 0, 0);
//
//			// Hidrometro
//			retorno += formarLinha(7, 0, 182, 323, hidrometro, 0, 0);
//
//			// Situacao da ligacao de Agua
//			if (situacaoAgua.length() > 13) {
//				retorno += formarLinha(7, 0, 419, 323, situacaoAgua.substring(0, 13), 0, 0);
//			} else {
//				retorno += formarLinha(7, 0, 419, 323, situacaoAgua, 0, 0);
//			}
//
//			// Situacao da ligacao de esgoto
//			retorno += formarLinha(7, 0, 624, 323, situacaoEsgoto, 0, 0);
//
//			// Leitura Atual Informada
//			retorno += formarLinha(7, 0, 45, 360, "LEIT ATUAL INF:", 0, 0)
//					+ formarLinha(7, 0, 280, 360, leituraInformada, 0, 0);
//
//			// Leitura Atual Faturada
//			retorno += formarLinha(7, 0, 45, 385, "LEIT. ATUAL FAT:", 0, 0)
//					+ formarLinha(7, 0, 280, 385, leituraAtual, 0, 0);
//
//			// Leitura Anterior
//			retorno += formarLinha(7, 0, 45, 410, "LEIT. ANT:", 0, 0) + formarLinha(7, 0, 280, 410, leituraAnterior, 0, 0);
//
//			// Consumo
////			if ((imovel.getIndcFaturamentoAgua() == Constantes.SIM || imovel.getIndcFaturamentoEsgoto() == Constantes.SIM)
////					&& imovel.getIdImovelCondominio() != Constantes.NULO_INT) {
////
////				if (imovel.getIndcFaturamentoAgua() == Constantes.SIM) {
////					consumoRateio = "" + imovel.getConsumoAgua().getConsumoRateio();
////					consumo = ""
////							+ (imovel.getConsumoAgua().getConsumoCobradoMes() - imovel.getConsumoAgua().getConsumoRateio());
////				} else if (imovel.getIndcFaturamentoEsgoto() == Constantes.SIM) {
////					consumoRateio = "" + imovel.getConsumoEsgoto().getConsumoRateio();
////					consumo = ""
////							+ (imovel.getConsumoEsgoto().getConsumoCobradoMes() - imovel.getConsumoEsgoto().getConsumoRateio());
////				}
//
//				retorno += formarLinha(7, 0, 45, 435, "CONSUMO (m3): ", 0, 0) + formarLinha(7, 0, 280, 435, consumo, 0, 0);
//
//				retorno += formarLinha(7, 0, 410, 435, "CONSUMO RATEIO (m3): ", 0, 0)
//						+ formarLinha(7, 0, 650, 435, consumoRateio, 0, 0);
////			} else {
////				retorno += formarLinha(7, 0, 45, 435, "CONSUMO (m3): ", 0, 0) + formarLinha(7, 0, 280, 435, consumo, 0, 0);
////			}
//
//			// Data Leitura Anterior
//			retorno += formarLinha(7, 0, 410, 360, "DATA LEITURA ANT:", 0, 0)
//					+ formarLinha(7, 0, 650, 360, "02/03/2012", 0, 0);
//
//			// Data Leitura Anterior Atual
//			retorno += formarLinha(7, 0, 410, 385, "DATA LEITURA ATUAL:", 0, 0)
//					+ formarLinha(7, 0, 650, 385, "02/03/2012", 0, 0);
//
//			// Numero de dias
//			retorno += formarLinha(7, 0, 410, 410, "DIAS CONSUMO:", 0, 0) + formarLinha(7, 0, 650, 410, diasConsumo, 0, 0);
//
//			// Divisória do quadro de Historico de Consumo/Qualidade da Agua
//			retorno += "LINE 45 470 790 470 2\r\n";
//
//			// Opcao Debito Automatico
//			/*
//			 * retorno += formarLinha(7, 0, 360, 480,
//			 * "OPCAO POR DEBT AUTOMATICO:", 0, 0) + formarLinha(7, 0, 685, 480,
//			 * ( imovel.getOpcaoDebitoAutomatico() == Constantes.NULO_INT ? "" :
//			 * imovel.getOpcaoDebitoAutomatico()+"" ), 0, 0);
//			 * 
//			 * // Historico de Consumo
//			 * 
//			 * int consumoMedio = 0;
//			 * 
//			 * if (imovel.getConsumoMedioLigacaoAgua() != 0){
//			 * 
//			 * consumoMedio = imovel.getConsumoMedioLigacaoAgua(); } else{
//			 * 
//			 * consumoMedio = imovel.getConsumoMedioLigacaoEsgoto(); }
//			 * 
//			 * retorno += formarLinha(7, 0, 47, 480, "HISTORICO DE CONSUMO", 0,
//			 * 0) + formarLinha(7, 0, 47, 515, "REFERENCIA", 0, 0) +
//			 * formarLinha(7, 0, 189, 515, "CONS", 0, 0) + formarLinha(7, 0,
//			 * 270, 515, "REFERENCIA", 0, 0) + formarLinha(7, 0, 412, 515,
//			 * "CONS", 0, 0) + formarLinha(7, 0, 490, 515, "REFERENCIA", 0, 0) +
//			 * formarLinha(7, 0, 635, 515, "CONS", 0, 0)
//			 * 
//			 * + "LINE 695 512 695 590 1\n"
//			 * 
//			 * + formarLinha(7, 0, 710, 515, "MEDIA", 0, 0) + formarLinha(7, 0,
//			 * 710, 540, consumoMedio + "", 0, 0);
//			 * 
//			 * int yInicial = 540; Vector registros3 = imovel.getRegistros3();
//			 * 
//			 * if (registros3 != null) { for (int i = 0, yHistorico = yInicial,
//			 * xHistorico = 47; i < registros3.size(); i++) { ImovelReg3 reg3 =
//			 * (ImovelReg3) registros3.elementAt(i);
//			 * 
//			 * if (registro8Agua != null) { if (reg3.getTipoLigacao() !=
//			 * Constantes.LIGACAO_AGUA) continue; } else if (registro8Poco !=
//			 * null) { if (reg3.getTipoLigacao() != Constantes.LIGACAO_POCO)
//			 * continue; }
//			 * 
//			 * retorno += formarLinha(7, 0, xHistorico, yHistorico,
//			 * Util.formatarAnoMesParaMesAno
//			 * (Integer.toString(reg3.getAnoMesReferencia())), 0, 0); retorno +=
//			 * formarLinha(7, 0, xHistorico + 142, yHistorico,
//			 * Integer.toString(Integer.parseInt(reg3.getConsumo())), 0, 0);
//			 * 
//			 * if ((i + 1) % 2 == 0) { xHistorico += 223; yHistorico = yInicial;
//			 * } else { yHistorico += 25; } } }
//			 * 
//			 * // Qualidade da Agua retorno += formarLinha(7, 0, 47, 610,
//			 * "QUALIDADE DA AGUA", 0, 0) + formarLinha(7, 0, 47, 645,
//			 * "PARAMETROS", 0, 0) + formarLinha(7, 0, 247, 645, "UNIDADE", 0,
//			 * 0) + formarLinha(7, 0, 360, 645, "PADRAO", 0, 0) + formarLinha(7,
//			 * 0, 480, 645, "VL. OBTIDO", 0, 0)
//			 * 
//			 * + formarLinha(7, 0, 47, 665, "CLORO", 0, 0) + formarLinha(7, 0,
//			 * 247, 665, "mgCl/L", 0, 0) + formarLinha(7, 0, 360, 665,
//			 * imovel.getCloroPadrao(), 0, 0);
//			 * 
//			 * if (Constantes.NULO_STRING != imovel.getNumeroCloroResidual() &&
//			 * !imovel.getNumeroCloroResidual().equals("")) { retorno +=
//			 * formarLinha(7, 0, 480, 665, imovel.getNumeroCloroResidual() + "",
//			 * 0, 0); }
//			 * 
//			 * retorno += formarLinha(7, 0, 47, 690, "TURBIDEZ", 0, 0) +
//			 * formarLinha(7, 0, 247, 690, "uT", 0, 0) + formarLinha(7, 0, 360,
//			 * 690, imovel.getTurbidezPadrao(), 0, 0);
//			 * 
//			 * if (Constantes.NULO_STRING != imovel.getNumeroTurbidez() &&
//			 * !imovel.getNumeroTurbidez().equals("")) { retorno +=
//			 * formarLinha(7, 0, 480, 690, imovel.getNumeroTurbidez() + "", 0,
//			 * 0); }
//			 */
//
//			retorno += "BOX 283 507 790 571 1\r\n" +
//			// linha de baixo do box
//					"BOX 283 570 790 639 1\r\n" +
//					// quarta linha vertical do box
//					"LINE 656 551 656 639 1\r\n" +
//					// segunda linha vertical do box
//					"LINE 425 551 425 639 1\r\n" +
//					// terceira linha vertical do box
//					"LINE 535 551 535 639 1\r\n" +
//					// linha vertical da referencia de consumo
//					"LINE 250 515 250 690 1\r\n";
//
//			retorno += formarLinha(7, 0, 45, 476, "HISTÓRICO DE CONSUMO", 0, 0)
//					+ formarLinha(7, 0, 324, 476, "OPÇÃO PELO DEB. AUTOMÁTICO: ", 0, 0)
//					+ formarLinha(7, 0, 649, 476, "2" + "", 0, 0)
//					+ formarLinha(7, 0, 448, 520, "QUALIDADE DA AGUA", 0, 0);
//
//			retorno += formarLinha(7, 0, 65, 506, "REFERENCIA", 0, 0) + formarLinha(7, 0, 195, 506, "CONS ", 0, 0);
//
//			// Ultimos Consumos
//			Vector regsTipo3 = /*imovel.getRegistros3()*/ new Vector(2);
//			String media = "0";
//
////			if (regsTipo3 != null) {
//				int sumConsumo = 0;
////
////				for (int i = 0; i < regsTipo3.size(); i++) {
////					ImovelReg3 reg3 = (ImovelReg3) regsTipo3.elementAt(i);
//
//					retorno += formarLinha(7, 0, 85, 533, "02/2012", 0, 2 * 26);
//					retorno += formarLinha(7, 0, 205, 533, "02/2012", 0, 2 * 26);
//					sumConsumo += /*Integer.parseInt(*/3/*)*/;
////				}
////				media = (int) (sumConsumo / regsTipo3.size()) + "";
////			}
//
//			retorno += formarLinha(7, 0, 65, 690, "MEDIA", 0, 0)
//					+ formarLinha(7, 0, 205, 690, "23", 0, 0);
//			// Qualidade da agua
//			retorno += formarLinha(7, 0, 287, 547, "PARAMETROS", 0, 0) + formarLinha(7, 0, 429, 547, "UNIDADE", 0, 0)
//					+ formarLinha(7, 0, 556, 547, "PADRAO", 0, 0) + formarLinha(7, 0, 662, 547, "VL. OBTIDO", 0, 0)
//					+ formarLinha(0, 0, 287, 584, "CLORO", 0, 0) + formarLinha(0, 0, 287, 613, "TURBIDEZ", 0, 0)
//					+ formarLinha(0, 2, 556, 584, "3", 0, 0);
//			retorno += formarLinha(0, 2, 429, 584, "mgCI/L", 0, 0) + formarLinha(0, 2, 429, 613, "uT", 0, 0);
////			if (Constantes.NULO_STRING != imovel.getNumeroCloroResidual()) {
//				retorno += formarLinha(0, 2, 690, 584, "2" + "", 0, 0);
////			}
//			retorno += formarLinha(0, 2, 556, 613, "2", 0, 0);
////			if (Constantes.NULO_STRING != imovel.getNumeroTurbidez()) {
//				retorno += formarLinha(0, 2, 690, 613, "2" + "", 0, 0);
////			}
//
//			// Mensagem inserida a pedido da compesa
//			retorno += formarLinha(7, 0, 390, 645, "Reclamação de vazamento  de água", 0, 0);
//			retorno += formarLinha(7, 0, 390, 670, "e extravasamento de esgoto ligue", 0, 0);
//			retorno += formarLinha(7, 0, 390, 695, "para o número 0800 081 0185.", 0, 0);
//
//			// Divisória do quadro de Historico de Consumo/Qualidade da Agua
//			retorno += "LINE 45 720 790 720 2\r\n";
//
//			// Descricao de Tarifa Agua/Esgoto
//			retorno += formarLinha(7, 0, 47, 725, "DESCRICAO", 0, 0) + formarLinha(7, 0, 530, 725, "CONSUMO", 0, 0)
//					+ formarLinha(7, 0, 680, 725, "TOTAL(R$)", 0, 0);
//
//			int ultimaLinhaAgua = 0;
//			int ultimaLinhaPoco = 0;
//			int quantidadeLinhasAtual = 0;
//			int quantidadeMaximaLinhas = 18;
//
////			Vector linhaAgua = this.gerarLinhasTarifaAgua(consumoAgua);
//			retorno += "33";
//
////			ultimaLinhaAgua = (((Integer) linhaAgua.elementAt(1)).intValue());
////
////			if (ultimaLinhaAgua != 0) {
////				quantidadeLinhasAtual = quantidadeLinhasAtual + ultimaLinhaAgua + 1;
////			}
//
//			ultimaLinhaAgua *= 25;
//			Vector tarifasPoco = /*this.gerarLinhasTarifaPoco()*/ new Vector(4);
//			ultimaLinhaPoco = ultimaLinhaAgua;
//
////			for (int i = 0; i < tarifasPoco.size(); i++) {
//				String[] tarifaPoco = (String[]) tarifasPoco.elementAt(1);
//				ultimaLinhaPoco = ultimaLinhaAgua + ((1 + 1) * 25);
//				quantidadeLinhasAtual++;
//				int deslocaDireitaColuna = 2;
////				if (i == 0 || i == 1 || i == 2) {
////					deslocaDireitaColuna = i;
////				} else {
////					deslocaDireitaColuna = 2;
////				}
////				if (tarifaPoco[0] != null) {
//					retorno += formarLinha(7, 0, 53, 760, tarifaPoco[0], deslocaDireitaColuna * 10, (2 + 1) * 25
//							+ ultimaLinhaAgua);
////				}
////				if (tarifaPoco[1] != null) {
//					retorno += formarLinha(7, 0, 530, 760, tarifaPoco[1], 0, (1 + 1) * 25 + ultimaLinhaAgua);
////				}
////				if (tarifaPoco[2] != null) {
//					retorno += formarLinha(7, 0, 680, 760, tarifaPoco[2], 0, (1 + 1) * 25 + ultimaLinhaAgua);
////				}
////			}
//
////			int indicadorDiscriminarDescricao = retornaIndicadorDiscriminar(quantidadeMaximaLinhas, quantidadeLinhasAtual, 'd');
//			Vector debitos = /*this.gerarLinhasDebitosCobrados(indicadorDiscriminarDescricao)*/ new Vector(3);
//			int ultimaLinhaDebito = ultimaLinhaPoco;
//
//			for (int i = 0; i < debitos.size(); i++) {
//				String[] debito = (String[]) debitos.elementAt(i);
//				ultimaLinhaDebito = ultimaLinhaPoco + ((i + 1) * 34);
//				quantidadeLinhasAtual++;
//				if (debito[0] != null) {
//					retorno += formarLinha(7, 0, 53, 760, "22,00", 0, (i + 1) * 25 + ultimaLinhaPoco);
//				}
//				if (debito[1] != null) {
//					retorno += formarLinha(7, 0, 530, 760, "22,00", 0, (i + 1) * 25 + ultimaLinhaPoco);
//				}
//				if (debito[2] != null) {
//					retorno += formarLinha(7, 0, 680, 760, "22,00", 0, (i + 1) * 25 + ultimaLinhaPoco);
//				}
//			}
//
////			indicadorDiscriminarDescricao = retornaIndicadorDiscriminar(quantidadeMaximaLinhas, quantidadeLinhasAtual, 'c');
//			Vector creditos = /*this.gerarLinhasCreditosRealizados(indicadorDiscriminarDescricao)*/ new Vector(3);
//			int ultimaLinhaCredito = ultimaLinhaDebito;
//
//			for (int i = 0; i < creditos.size(); i++) {
//				String[] credito = (String[]) creditos.elementAt(i);
//				ultimaLinhaCredito = ultimaLinhaDebito + ((i + 1) * 34);
//				if (credito[0] != null) {
//					retorno += formarLinha(7, 0, 53, 760, "99,99", 0, (i + 1) * 25 + ultimaLinhaDebito);
//				}
//				if (credito[1] != null) {
//					retorno += formarLinha(7, 0, 530, 760, "99,99", 0, (i + 1) * 25 + ultimaLinhaDebito);
//				}
//				if (credito[2] != null) {
//					retorno += formarLinha(7, 0, 680, 760, "99,99", 0, (i + 1) * 25 + ultimaLinhaDebito);
//				}
//			}
//
//			Vector impostos = /*this.gerarLinhasImpostosRetidos()*/ new Vector(3);
//			for (int i = 0; i < impostos.size(); i++) {
//				String[] imposto = (String[]) impostos.elementAt(i);
//				int deslocaDireitaColuna2;
//				if (i == 0 || i == 1) {
//					deslocaDireitaColuna = i;
//				} else {
//					deslocaDireitaColuna = 1;
//				}
//				if (imposto[0] != null) {
//					retorno += formarLinha(7, 0, 53, 770, "0,00", deslocaDireitaColuna * 10, (i + 1) * 25
//							+ ultimaLinhaCredito);
//				}
//				if (imposto[1] != null) {
//					retorno += formarLinha(7, 0, 530, 770, "0,00", 0, (i + 1) * 25 + ultimaLinhaCredito);
//				}
//				if (imposto[2] != null) {
//					retorno += formarLinha(7, 0, 680, 770, "0,00", 0, (i + 1) * 25 + ultimaLinhaCredito);
//				}
//			}
//
//			// Vencimento da Conta
//			retorno += formarLinha(7, 0, 45, 1248, "VENCIMENTO:", 0, 0);
//			retorno += formarLinha(7, 1, 185, 1248, "23/12/2890", 0, 0);
//
//			// Valor a Pagar
////			double valorTotalConta = imovel.getValorConta();
//			retorno += formarLinha(4, 0, 600, 1248, "1.000.000,00", 0, 0);
//
//			// Mensagem
//			retorno += formarLinha(7, 0, 45, 1305, "MENSAGEM:", 0, 0);
//
//			/*
//			 * retorno += formarLinha(7, 0, 47, 1330,
//			 * "ALÉM DO NUMERO 0800 081 0195 JÁ ESTÁ EM FUNCIONAMENTO  O", 0, 0)
//			 * + formarLinha(7, 0, 47, 1360,
//			 * "NOVO NÚMERO 0800 081 0185 EXCLUSIVO PARA SOLICITAÇÕES DE", 0, 0)
//			 * + formarLinha(7, 0, 47, 1390,
//			 * "SERVIÇOS DE VAZAMENTO NO RAMAL,NO MORRO E EXTRAVAZAMENTO DE ESGOTO."
//			 * , 0, 0);
//			 * 
//			 * 
//			 * }else{
//			 */
//			String mensagem = "É só um teste calma aê...";
////			if (imovel.getMensagemEstouroConsumo1() != null && !imovel.getMensagemEstouroConsumo1().equals("")) {
//				retorno += formarLinha(7, 0, 47, 1335, mensagem != null
//						&& mensagem.length() > 60 ? mensagem.substring(0, 60) : mensagem, 0, 0)
//						+ formarLinha(7, 0, 47, 1365, mensagem != null
//								&& mensagem.length() > 60 ? mensagem.substring(0, 60) : mensagem, 0, 0)
//						+ formarLinha(7, 0, 47, 1395, mensagem != null
//								&& mensagem.length() > 60 ? mensagem.substring(0, 60) : mensagem, 0, 0);
////			} else {
////				retorno += formarLinha(7, 0, 47, 1335, imovel.getMensagemConta1() != null
////						&& imovel.getMensagemConta1().length() > 60 ? imovel.getMensagemConta1().substring(0, 60) : imovel.getMensagemConta1(), 0, 0)
////						+ formarLinha(7, 0, 47, 1365, imovel.getMensagemConta2() != null
////								&& imovel.getMensagemConta2().length() > 60 ? imovel.getMensagemConta2().substring(0, 60) : imovel.getMensagemConta2(), 0, 0)
////						+ formarLinha(7, 0, 47, 1395, imovel.getMensagemConta3() != null
////								&& imovel.getMensagemConta3().length() > 60 ? imovel.getMensagemConta3().substring(0, 60) : imovel.getMensagemConta3(), 0, 0);
////			}
//			// }
//
//			// Canhoto
//			retorno += formarLinha(0, 2, 178, 1523, "23456666" + "", 0, 0)
//					+ formarLinha(0, 2, 294, 1523, "23/2345", 0, 0)
//					+ formarLinha(0, 2, 408, 1523, "23/2345", 0, 0)
//					+ formarLinha(0, 2, 544, 1523, "23/4453", 0, 0)
//					+ formarLinha(0, 2, 695, 1523, "22/2334", 0, 0);
//
//			// Codigo de Barras
////			System.out.println("##COD AGENCIA: " + imovel.getCodigoAgencia());
//
////			if (imovel.getCodigoAgencia() == null || imovel.getCodigoAgencia().equals("")) {
////				System.out.println("##COD AGENCIA DO IF: " + imovel.getCodigoAgencia());
//
//				String representacaoNumericaCodBarra = "82890000001-6 98746578356-9 74523145678-7 47452648256-0";/*Util.obterRepresentacaoNumericaCodigoBarra(new Integer(3), valorTotalConta, new Integer(Integer.parseInt(imovel.getInscricao().substring(0, 3))), new Integer(imovel.getMatricula()), Util.formatarAnoMesParaMesAnoSemBarra(imovel.getAnoMesConta()), new Integer(imovel.getDigitoVerificadorConta()), null, null, null, null, null, null);*/
//
//				String representacaoNumericaCodBarraFormatada = representacaoNumericaCodBarra.substring(0, 11).trim() + "-"
//						+ representacaoNumericaCodBarra.substring(11, 12).trim() + " "
//						+ representacaoNumericaCodBarra.substring(12, 23).trim() + "-"
//						+ representacaoNumericaCodBarra.substring(23, 24).trim() + " "
//						+ representacaoNumericaCodBarra.substring(24, 35).trim() + "-"
//						+ representacaoNumericaCodBarra.substring(35, 36).trim() + " "
//						+ representacaoNumericaCodBarra.substring(36, 47).trim() + "-"
//						+ representacaoNumericaCodBarra.substring(47, 48);
//
//				retorno += formarLinha(7, 0, 70, 1555, representacaoNumericaCodBarraFormatada, 0, 0);
//
//				String representacaoCodigoBarrasSemDigitoVerificador = representacaoNumericaCodBarra.substring(0, 11)
//						+ representacaoNumericaCodBarra.substring(12, 23) + representacaoNumericaCodBarra.substring(24, 35)
//						+ representacaoNumericaCodBarra.substring(36, 47);
//
//				retorno += "B I2OF5 1 2 105 35 1576 " + representacaoCodigoBarrasSemDigitoVerificador + "\r\n";
//
////			} else {
////				retorno += formarLinha(4, 0, 182, 1595, "DÉBITO AUTOMÁTICO", 0, 0);
////			}
//
//			retorno += "FORM\r\n" + "PRINT ";
//
//		} catch (Exception ex) {
//			ex.printStackTrace();
////			Util.mostrarErro("Erro na impressão...", ex);
////			FileManager.salvarErro(ex);
//		}
		System.out.println(retorno);
		return retorno;
	}
    
//    private static String formarLinha(int font, int tamanhoFonte, int x, int y, String texto, int adicionarColuna,
//			int adicionarLinha) {
//		return "T " + font + " " + tamanhoFonte + " " + (x + adicionarColuna) + " " + (y + adicionarLinha) + " " + texto
//				+ "\r\n";
//	}
//    
//    private static String dividirLinha(int fonte, int tamanhoFonte, int x, int y, String texto, int tamanhoLinha,
//			int deslocarPorLinha) {
//		String retorno = "";
//		int contador = 0;
//		int i;
//		for (i = 0; i < texto.length(); i += tamanhoLinha) {
//			contador += tamanhoLinha;
//			if (contador > texto.length()) {
//				retorno += "T " + fonte + " " + tamanhoFonte + " " + x + " " + y + " "
//						+ texto.substring(i, texto.length()).trim() + "\r\n";
//			} else {
//				retorno += "T " + fonte + " " + tamanhoFonte + " " + x + " " + y + " " + texto.substring(i, contador).trim()
//						+ "\r\n";
//			}
//			y += deslocarPorLinha;
//		}
//		return retorno;
//	}
}
