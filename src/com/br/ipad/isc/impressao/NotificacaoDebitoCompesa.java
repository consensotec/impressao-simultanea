package com.br.ipad.isc.impressao;

import java.util.ArrayList;

import com.br.ipad.isc.bean.CategoriaSubcategoria;
import com.br.ipad.isc.bean.ContaDebito;
import com.br.ipad.isc.bean.HidrometroInstalado;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.fachada.Fachada;
import com.br.ipad.isc.util.ConstantesSistema;
import com.br.ipad.isc.util.Util;

public class NotificacaoDebitoCompesa extends ImpressaoCompesa {

	private static NotificacaoDebitoCompesa instancia;
		
	private NotificacaoDebitoCompesa() {
		super();
	}

	public static NotificacaoDebitoCompesa getInstancia() {
		if (instancia == null) {
			instancia = new NotificacaoDebitoCompesa();
		}		
		return instancia;
	}
	
	/**
	 * @author Rogério Peixoto e Bruno Barros
	 * @date 09/12/2010
	 * @return
	 */
	public StringBuilder imprimirNotificacaoDebito(ImovelConta imovel) {
		StringBuilder retorno = new StringBuilder();
		
		Fachada fachada = Fachada.getInstance();
		
		try {
			retorno.append("! 0 816 0 1720 1\n");

			retorno.append(formarLinha(7, 0, 272, 53, "NOTIFICAÇÃO DE DÉBITO", 0, 0));

			// Matricula do imovel
			retorno.append(formarLinha(7, 0, 440, 95, imovel.getId().toString(), 0, 0));

			// Numero do Documento de Notificacao de Debito
			retorno.append(formarLinha(7, 0, 570, 95, "Nº DOC:", 0, 0));
			retorno.append(formarLinha(7, 0, 670, 95, imovel.getIdDocumentoNotificacaoDebito().toString(), 0, 0));

			// Dados do cliente
			String cpfCnpjFormatado = "";
			if (imovel.getCpfCnpjCliente() != null && !imovel.getCpfCnpjCliente().equals("")) {
				cpfCnpjFormatado = imovel.getCpfCnpjCliente().trim();
			}

			retorno.append(formarLinha(7, 0, 48, 140, imovel.getNomeUsuario(), 0, 0));
			retorno.append(formarLinha(7, 0, 360, 137, cpfCnpjFormatado, 0, 0));
			retorno.append(dividirLinha(7, 0, 47, 163, imovel.getEndereco(), 40, 20));

			// Inscricao
			retorno.append(formarLinha(7, 0, 41, 260, fachada.formatarInscricao(imovel.getInscricao()), 0, 0));

			// Grupo de Faturamento
			retorno.append(formarLinha(7, 0, 320, 260, imovel.getGrupoFaturamento() + "", 0, 0));

			// Verificamos é por categoria ou subcategoria
			ArrayList<CategoriaSubcategoria> arrayListCategoriaSubcategoria = (ArrayList<CategoriaSubcategoria>) 
								fachada.buscarCategoriaSubcategoriaPorImovelId(imovel.getId());						
			retorno.append(categoriasEconomias(arrayListCategoriaSubcategoria));
			//Performace
			arrayListCategoriaSubcategoria = null;

			// Codigo da Rota
			retorno.append(formarLinha(7, 0, 45, 325, imovel.getCodigoRota().toString(), 0, 0));

			// Numero do Hidrometro
			String hidrometro = "NÃO MEDIDO";
			String situacaoAgua = fachada.getDescricaoSitLigacaoAgua(imovel.getSituacaoLigAgua());
			String situacaoEsgoto = fachada.getDescricaoSitLigacaoEsgoto(imovel.getSituacaoLigEsgoto());
			
			HidrometroInstalado hidrometroInstaladoAgua = fachada.
					buscarHidrometroInstaladoPorImovelTipoMedicao(imovel.getId(), ConstantesSistema.LIGACAO_AGUA);			
			
			if (hidrometroInstaladoAgua != null) {
				hidrometro = hidrometroInstaladoAgua.getNumeroHidrometro();
			} else {
				HidrometroInstalado hidrometroInstaladoPoco = fachada.
						buscarHidrometroInstaladoPorImovelTipoMedicao(imovel.getId(), ConstantesSistema.LIGACAO_POCO);
				if (hidrometroInstaladoPoco != null) {
					hidrometro = hidrometroInstaladoPoco.getNumeroHidrometro();
				}
			}

			// Hidrometro
			retorno.append(formarLinha(7, 0, 182, 325, hidrometro, 0, 0));

			// Situacao da ligacao de Agua
			if (situacaoAgua.length() > 13) {
				retorno.append(formarLinha(7, 0, 419, 325, situacaoAgua.substring(0, 13), 0, 0));
			} else {
				retorno.append(formarLinha(7, 0, 419, 325, situacaoAgua, 0, 0));
			}

			// Situacao da ligacao de esgoto
			retorno.append(formarLinha(7, 0, 624, 325, situacaoEsgoto, 0, 0));

			// Mensagem de Notificação de Débito
			retorno.append(formarLinha(7, 1, 230, 365, "NOTIFICAÇÃO DE DÉBITOS - ATENÇÃO", 0, 0));
			retorno.append(formarLinha(7, 0, 40, 415, "A PARTIR DO  RECEBIMENTO  DESTE AVISO, DE ACORDO COM O  DECRETO", 0, 0));
			retorno.append(formarLinha(7, 0, 40, 440, "ESTADUAL  Nº  18.251 DE  21.12.94, FICA  VOSSA SENHORIA  CIENTE", 0, 0));
			retorno.append(formarLinha(7, 0, 40, 465, "QUE, CASO  OS   DÉBITOS   NÃO SEJAM   LIQUIDADOS, EFETUAREMOS A", 0, 0));
			retorno.append(formarLinha(7, 0, 40, 490, "SUSPENSÃO   DO   SEU   ABASTECIMENTO   DE   ÁGUA.  MANTENHA  OS", 0, 0));
			retorno.append(formarLinha(7, 0, 40, 515, "DOCUMENTOS   DE  REGULARIZAÇÃO EM  SEU IMOVEL PARA APRESENTAÇÃO", 0, 0));
			retorno.append(formarLinha(7, 0, 40, 540, "AOS  NOSSOS FUNCIONÁRIOS.  PAGUE  EM  DIA SUAS  CONTAS, EVITE O", 0, 0));
			retorno.append(formarLinha(7, 0, 40, 565, "CORTE E A NEGATIVAÇÃO DO SEU NOME NO SPC SERASA.               ", 0, 0));
			retorno.append(formarLinha(7, 1, 40, 590, "CASO JÁ TENHA QUITADO O DÉBITO, DESCONSIDERE ESSE AVISO.", 0, 0));

			// Linha Separadora
			retorno.append("LINE 42 642 790 642 2\n");

			// Referencia Conta / Vencimento / Valor (R$)
			retorno.append(formarLinha(7, 0, 42, 655, "REFERENCIA CONTA", 0, 0));
			retorno.append(formarLinha(7, 0, 400, 655, "VENCIMENTO", 0, 0));
			retorno.append(formarLinha(7, 0, 682, 655, "VALOR(R$)", 0, 0));
			
			ArrayList<ContaDebito> arrayListContaDebito = new ArrayList<ContaDebito>();
			arrayListContaDebito = fachada.buscarContasDebitosPorIdImovel(imovel.getId());
			double valorDebitosAnteriores = 0;
			StringBuilder linhas = new StringBuilder();

			//Imprime apenas 17 Debitos por causa do espaço no papel
			for (int qtdLinhas = 0; (qtdLinhas < arrayListContaDebito.size() && qtdLinhas<17); qtdLinhas++) {
				 ContaDebito  dadosContaDebito = ((ContaDebito) arrayListContaDebito.get(qtdLinhas));

				if (dadosContaDebito.getAnoMesReferencia().equals("DB.ATE")) {
					linhas.append(formarLinha(7, 0, 42, 680, dadosContaDebito.getAnoMesReferencia() + "", 
							0, qtdLinhas * 25));
				} else {
					linhas.append(formarLinha(7, 0, 42, 680, Util.formatarAnoMesParaMesAno(dadosContaDebito.
							getAnoMesReferencia() + ""), 0, qtdLinhas * 25));
				}
				valorDebitosAnteriores += dadosContaDebito.getValorConta().doubleValue();
				linhas.append(formarLinha(7, 0, 400, 680, Util.dateToString(dadosContaDebito.getDataVencimentoConta()),
						0, qtdLinhas * 25));
				linhas.append(formarLinha(7, 0, 685, 680, Util.formatarDoubleParaMoedaReal(dadosContaDebito.getValorConta()
						.doubleValue()), 0, qtdLinhas * 25));				
			}

			retorno.append(linhas);

			// Data de emissão do documento
			retorno.append(formarLinha(7, 0, 45, 1251, "EMISSÃO:", 0, 0));
			retorno.append(formarLinha(7, 1, 185, 1245, Util.dateToString(imovel.getDataEmissaoDocumento()), 0, 0));

			// Total a pagar
			retorno.append(formarLinha(4, 0, 600, 1245, Util.formatarDoubleParaMoedaReal(valorDebitosAnteriores), 0, 0));

			// Codigo de Barras
			String representacaoNumericaCodBarraFormatada = Util.formatarCodigoBarras(imovel.getNumeroCodigoBarraNotificacaoDebito());

			retorno.append(formarLinha(7, 0, 70, 1305, representacaoNumericaCodBarraFormatada, 0, 0));

			if (representacaoNumericaCodBarraFormatada != null && !representacaoNumericaCodBarraFormatada.equals("")) {

				String representacaoCodigoBarrasSemDigitoVerificador = representacaoNumericaCodBarraFormatada.substring(0, 11)
						+ representacaoNumericaCodBarraFormatada.substring(14, 25)
						+ representacaoNumericaCodBarraFormatada.substring(28, 39)
						+ representacaoNumericaCodBarraFormatada.substring(42, 53);

				retorno.append("B I2OF5 1 2 105 35 1332 " + representacaoCodigoBarrasSemDigitoVerificador + "\n");
			}

			//Canhoto
			retorno.append(formarLinha(0, 2, 178, 1525, imovel.getId() + "", 0, 0));
			retorno.append(formarLinha(0, 2, 294, 1525, imovel.getIdDocumentoNotificacaoDebito().toString(), 0, 0));
			retorno.append(formarLinha(0, 2, 695, 1525, Util.formatarDoubleParaMoedaReal(valorDebitosAnteriores), 0, 0));

			retorno.append(formarLinha(0, 2, 35, 1556, "RECEBI A NOTIFICACAO DOS DEBITOS ACIMA RELACIONADOS:"
					+ "                 " + "CICLO: " + Util.formatarAnoMesParaMesAno(imovel.getAnoMesConta().toString()), 0, 0));
			retorno.append(formarLinha(0, 2, 35, 1581, "HORA: " + Util.dateToHoraString(Util.dataAtual()), 0, 0));
			retorno.append(formarLinha(0, 2, 35, 1606, "LOCALIDADE: " + Util.adicionarZerosEsquerdaNumero(3, imovel.getIdLocalidade().toString()) + " / " + "SETOR: "
					+ imovel.getInscricao().toString().substring(3, 6), 0, 0));
			retorno.append(formarLinha(0, 2, 200, 1635, "____________________________________________________________", 0, 0));
			retorno.append(formarLinha(0, 2, 340, 1660, "Assinatura do Recebedor", 0, 0));
			
			retorno.append(comandoImpressao());

		} catch (Exception ex) {
			ex.printStackTrace();			
		}

		return retorno;
	}
}