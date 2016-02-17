package com.br.ipad.isc.impressao;

import java.util.ArrayList;
import java.util.Date;

import com.br.ipad.isc.bean.CategoriaSubcategoria;
import com.br.ipad.isc.bean.ContaDebito;
import com.br.ipad.isc.bean.HidrometroInstalado;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.bean.SistemaParametros;
import com.br.ipad.isc.util.ConstantesSistema;
import com.br.ipad.isc.util.Util;

public class NotificacaoDebitoCaern extends ImpressaoCaern {

	private static NotificacaoDebitoCaern instancia;
		
	private NotificacaoDebitoCaern() {
		super();
	}

	public static NotificacaoDebitoCaern getInstancia() {
		if (instancia == null) {
			instancia = new NotificacaoDebitoCaern();
		}
		return instancia;
	}
	
	public StringBuilder imprimirNotificacaoDebito(ImovelConta imovel) {
	      try {
	        buffer = new StringBuilder(3000);
	        
	        // Tamanho da folha / unidade de medida / Line Feed como fim de comando
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
	        appendTexto70(0, 19, "AVISO DE DÉBITO");
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
	        appendTexto70(80, yDados, "********");

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
	          
	        
	        HidrometroInstalado hidrometroInstaladoAgua = new HidrometroInstalado();
	        HidrometroInstalado hidrometroInstaladoPoco = new HidrometroInstalado();
	        
	        hidrometroInstaladoAgua = fachada.
	        		buscarHidrometroInstaladoPorImovelTipoMedicao(imovel.getId(), ConstantesSistema.LIGACAO_AGUA);

	        hidrometroInstaladoPoco = fachada.
	        		buscarHidrometroInstaladoPorImovelTipoMedicao(imovel.getId(), ConstantesSistema.LIGACAO_POCO);
	        

	        // Prioridade para as informações de água
	        if (hidrometroInstaladoAgua != null)
	        	hidrometro = hidrometroInstaladoAgua.getNumeroHidrometro();
	        else if (hidrometroInstaladoPoco != null)
	        	hidrometro = hidrometroInstaladoPoco.getNumeroHidrometro();

	        // Hidrômetro / Situação da agua / Situação da esgoto
	        final int yHdAgEs = 55;
	        appendTexto70(10, yHdAgEs, hidrometro);
	        appendTexto70(45, yHdAgEs, situacaoAgua);
	        appendTexto70(78, yHdAgEs, situacaoEsgoto);

	        // Lista de Débitos
	        int yCorpoConta = 60;

	        appendTexto70(5, yCorpoConta, "REF.");
	     
	        appendTexto70(20, yCorpoConta, "VENC.");
	      
	        appendTexto70(40, yCorpoConta, "VALOR");

	        appendLinha(52, yCorpoConta, 52, yCorpoConta + 48, 0.1f);

	        appendTexto70(53, yCorpoConta, "REF.");
	        appendTexto70(68, yCorpoConta, "VENC.");
	        appendTexto70(88, yCorpoConta, "VALOR");

	        yCorpoConta += 3;

	        ArrayList<ContaDebito> arrayListContaDebito = new ArrayList<ContaDebito>();
	        arrayListContaDebito = fachada.buscarContasDebitosPorIdImovel(imovel.getId());
	        Double valorDebitosAnteriores = 0.d;
	        
	        
	        for (int i = 0, yDebito = yCorpoConta, xCol1 = 5, xCol2 = 20, xCol3 = 40; i < arrayListContaDebito.size()
	            && i < 20; i++, yDebito += 3) {

	        	ContaDebito contaDebito = (ContaDebito) arrayListContaDebito.get(i);
		
		        appendTexto70(xCol1, yDebito, Util
		            .formatarAnoMesParaMesAno(contaDebito.getAnoMesReferencia().toString()));
		        appendTexto70(xCol2, yDebito, Util.dateToString(contaDebito
		            .getDataVencimentoConta()));
		        appendTexto70(xCol3, yDebito, Util
		            .formatarDoubleParaMoedaReal(contaDebito.getValorConta().doubleValue()));
		
		        if (i == 14) {
		            xCol1 = 53;
		            xCol2 = 68;
		            xCol3 = 88;
		            yDebito = yCorpoConta;
		        }
	        
		        valorDebitosAnteriores += contaDebito.getValorConta().doubleValue();
	        }

	        // Mensagem de notificação
	        yCorpoConta += 49;
	        appendTexto("CENTER\n");
	        appendTexto70(5, yCorpoConta, "ATENCAO");
	        appendTexto("LEFT\n");

	        yCorpoConta += 6;
	        String[] notificacao = new String[] {
	            "APÓS O RECEBIMENTO DESTE AVISO, V.SA TERÁ 30 (TRINTA) DIAS PARA",
	            "REGULARIZAÇÃO DOS DÉBITOS ACIMA INDICADOS, SOB PENA DE",
	            "INTERRUPÇÃO DO FORNECIMENTO DE ÁGUA AO IMÓVEL E",
	            "NEGATIVAÇÃO DO RESPONSÁVEL JUNTO AO SPC/SERASA, CONFORME",
	            "NOS FACULTA A LEI FEDERAL N 11.445, DE 05/01/2007. CASO EXISTA",
	            "AVISO ANTERIOR, CUJO DÉBITO LISTADO AINDA NÃO FOI INTEGRALMENTE",
	            "REGULARIZADO, O SEU IMÓVEL ESTÁ SUJEITO A SER, A QUALQUER",
	            "MOMENTO, 'CORTADO'. SE O DÉBITO FOI REGULARIZADO,",
	            "DESCONSIDERE ESTE AVISO. MANTENHA OS RECIBOS QUITADOS OU OS",
	            "DOCUMENTOS DE REGULARIZAÇÃO NO SEU IMÓVEL, À DISPOSIÇÃO DO",
	            "NOSSO FUNCIONÁRIO OU REPRESENTANTE LEGAL." };

	        appendTextos(7, 0, 5, yCorpoConta, notificacao, 3);

	        // Vencimento / Total a pagar
	        final int yVencValor = 155;
	        appendTexto70(95, yVencValor, true, Util.formatarDoubleParaMoedaReal(valorDebitosAnteriores));

	        // Mensagens
	        // final int yMensagem = 160;
	        // appendTexto70(5, yMensagem, "NAO RECEBER APOS VENCIMENTO");
	        // Qualidade da água
	        final int yQualidadeAgua = 181;
	        appendTexto70(27, yQualidadeAgua, "***"); // Turbidez
	        appendTexto70(42, yQualidadeAgua, "***"); // PH
	        appendTexto70(57, yQualidadeAgua, "***"); // Colif. Totais
	        appendTexto70(74, yQualidadeAgua, "***"); // Cloro
	        appendTexto70(90, yQualidadeAgua, "***"); // Nitrato

	        // Código de barras / linha digitável
	        final int yLinhaDigitavel = 192;
	        appendTexto("CENTER\n");
	        String codigoBarrasSemFormatacao = imovel
	            .getNumeroCodigoBarraNotificacaoDebito();
	        String linhaDigitavel = codigoBarrasSemFormatacao.substring(0, 11)
	            .trim()
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
	        String codigoBarras = codigoBarrasSemFormatacao.substring(0, 11)
	            + codigoBarrasSemFormatacao.substring(12, 23)
	            + codigoBarrasSemFormatacao.substring(24, 35)
	            + codigoBarrasSemFormatacao.substring(36, 47);
	        appendTexto(5, 0, 0, yLinhaDigitavel, false, linhaDigitavel);
	        appendBarcode(0, yLinhaDigitavel + 3, codigoBarras);
	        appendTexto("LEFT\n");

	        // Canhoto
	        final int yInfCanhoto = 216;
	        appendTexto70(5, yInfCanhoto, Integer.toString(imovel
	            .getId()));
	        appendTexto70(29, yInfCanhoto, "********");
	        appendTexto70(54, yInfCanhoto, formatarData(imovel
	            .getDataVencimento()));
	        appendTexto70(79, yInfCanhoto, Util.formatarDoubleParaMoedaReal(valorDebitosAnteriores));

	        // Comando que faz a impressão
	        appendTexto("FORM\nPRINT\n");

	        
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    } 

	    return buffer;
	}
}