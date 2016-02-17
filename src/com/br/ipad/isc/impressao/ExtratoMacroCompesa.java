package com.br.ipad.isc.impressao;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.br.ipad.isc.bean.CategoriaSubcategoria;
import com.br.ipad.isc.bean.ConsumoAnteriores;
import com.br.ipad.isc.bean.ConsumoHistorico;
import com.br.ipad.isc.bean.HidrometroInstalado;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.util.ConstantesSistema;
import com.br.ipad.isc.util.Util;

public class ExtratoMacroCompesa extends ImpressaoCompesa {

	
	private static ExtratoMacroCompesa instancia;

	
	private ExtratoMacroCompesa() {
		super();
	}

	public static ExtratoMacroCompesa getInstancia(ImovelConta imovelInformado) {
		if (instancia == null) {
			instancia = new ExtratoMacroCompesa();
		}
		instancia.imovel = imovelInformado;
		return instancia;
	}
	
	/**
	 * Método que impre o extrato de consumo do macro medidor dos imóveis
	 * condomínio seguindo layout compesa
	 * 
	 * @author Bruno Barros e Rogério Peixoto, Carlos Chaves
	 * @date 09/02/2010, 24/08/2012
	 */
	public StringBuilder obterStringExtratoMacroCompesa() {
		
		final int xMargem45 = 45;
		final int xMargemDadosCosumoMacro = 600;
		
		final int yMargem25 = 25;
		final int yMargem35 = 35;
		final int yMargem105 = 105;
		
		
		int yCorpoDados = 480;
		
		try {
			
			buffer = new StringBuilder();

			// Carregando Informações
			String hidrometro = "NÃO MEDIDO";
			
			Integer idSintuacaoLigAgua = imovel.getSituacaoLigAgua();
			String situacaoAgua = fachada.getDescricaoSitLigacaoAgua(idSintuacaoLigAgua);
			
			Integer idSintuacaoLigEsgoto = imovel.getSituacaoLigEsgoto(); 
			String situacaoEsgoto = fachada.getDescricaoSitLigacaoEsgoto(idSintuacaoLigEsgoto);
			
			String leituraAnterior = "";
			String leituraInformada = "";
			String leituraAtual = "";
			String consumo = "";
			String diasConsumo = "";
					
			ConsumoHistorico consumoAgua = null;
			ConsumoHistorico consumoEsgoto = null;
			
			HidrometroInstalado hidrometroInstaladoAgua = fachada.
					buscarHidrometroInstaladoPorImovelTipoMedicao(imovel.getId(), ConstantesSistema.LIGACAO_AGUA);
			
			HidrometroInstalado hidrometroInstaladoPoco = fachada.
					buscarHidrometroInstaladoPorImovelTipoMedicao(imovel.getId(), ConstantesSistema.LIGACAO_POCO);
			
			String dataLeituraAnterior = "";
			String dataLeitura = "";
			
			consumoAgua = fachada.
					buscarConsumoHistoricoPorImovelIdTipoLigacao(imovel.getId(), ConstantesSistema.LIGACAO_AGUA);
			
			consumoEsgoto = fachada.
						buscarConsumoHistoricoPorImovelIdTipoLigacao(imovel.getId(), ConstantesSistema.LIGACAO_ESGOTO);
			
			ImovelConta imovelAreaComum = null;

			if (hidrometroInstaladoAgua != null) {

				hidrometro = hidrometroInstaladoAgua.getNumeroHidrometro();
				leituraAnterior = String.valueOf(fachada.obterLeituraAnterior(hidrometroInstaladoAgua));

				dataLeituraAnterior = Util.dateToString(hidrometroInstaladoAgua.getDataLeituraAnterior());
				dataLeitura = Util.dateToString(hidrometroInstaladoAgua.getDataLeitura());

				if (imovel.getIndcAreaComum().equals(ConstantesSistema.SIM)){
					
					if (hidrometroInstaladoAgua != null 
							&& hidrometroInstaladoAgua.getTipoRateio() != null
							&& hidrometroInstaladoAgua.getTipoRateio().intValue() == ConstantesSistema.TIPO_RATEIO_AREA_COMUM) {
						imovelAreaComum = new ImovelConta();
						imovelAreaComum = fachada.obterImovelAreaComum(imovel.getId());
					}
				}

				if (consumoAgua != null) {

					if (hidrometroInstaladoAgua.getLeituraAtualFaturamento() != null) {

						leituraAtual = hidrometroInstaladoAgua.getLeituraAtualFaturamento() + "";
						leituraInformada = String.valueOf(hidrometroInstaladoAgua.getLeitura());
						consumo = consumoAgua.getConsumoCobradoMes() + "";
						
						if(hidrometroInstaladoAgua.getQtdDiasAjustado() != null){
							diasConsumo = Long.toString(hidrometroInstaladoAgua.getQtdDiasAjustado());
						}
						

					} else {
						if (consumoAgua.getLeituraAtual() != null) {
							leituraAtual = consumoAgua.getLeituraAtual() + "";

							if (hidrometroInstaladoAgua.getLeitura() == null) {
								leituraInformada = "";
							} else {
								leituraInformada = String.valueOf(hidrometroInstaladoAgua.getLeitura());
							}

						} else {
							leituraAtual = "";
						}

						consumo = consumoAgua.getConsumoCobradoMes() + "";
						diasConsumo = consumoAgua.getDiasConsumo() + "";
					}

				}

			} else if (hidrometroInstaladoPoco != null) {

				hidrometro = hidrometroInstaladoPoco.getNumeroHidrometro();
				leituraAnterior = hidrometroInstaladoPoco.getLeituraAnteriorFaturamento() + "";

				dataLeituraAnterior = Util.dateToString(hidrometroInstaladoPoco.getDataLeituraAnterior());
				dataLeitura = Util.dateToString(hidrometroInstaladoPoco.getDataLeitura());
				
				
				if (imovel.getIndcAreaComum().equals(ConstantesSistema.SIM)){
					if (hidrometroInstaladoPoco != null 
							&& hidrometroInstaladoPoco.getTipoRateio() != null
							&& hidrometroInstaladoPoco.getTipoRateio().intValue() == ConstantesSistema.TIPO_RATEIO_AREA_COMUM) {
						imovelAreaComum = new ImovelConta();
						imovelAreaComum = fachada.obterImovelAreaComum(imovel.getId());
					}
				}

				if (consumoEsgoto != null) {

					if (hidrometroInstaladoPoco.getLeituraAtualFaturamento() != null) {

						leituraAtual = hidrometroInstaladoPoco.getLeituraAtualFaturamento() + "";
						leituraInformada = hidrometroInstaladoPoco.getLeitura() + "";
						consumo = consumoEsgoto.getConsumoCobradoMes() + "";
						
						if(hidrometroInstaladoPoco.getQtdDiasAjustado() != null){
							diasConsumo = Long.toString(hidrometroInstaladoPoco.getQtdDiasAjustado());
						}

					} else {
						if (consumoEsgoto.getLeituraAtual() != null ) {
							leituraAtual = consumoEsgoto.getLeituraAtual() + "";

							if (hidrometroInstaladoPoco.getLeitura() == null) {
								leituraInformada = "";
							} else {
								leituraInformada = hidrometroInstaladoPoco.getLeitura() + "";
							}

						} else {
							leituraAtual = "";
						}

						consumo = consumoEsgoto.getConsumoCobradoMes() + "";
						diasConsumo = consumoEsgoto.getDiasConsumo() + "";
					}

				}
			}

			else if (hidrometroInstaladoAgua == null && hidrometroInstaladoPoco == null) {

				if (consumoAgua != null) {

					if (consumoAgua.getLeituraAtual() != null) {
						leituraAtual = consumoAgua.getLeituraAtual() + "";
					} else {
						leituraAtual = "";
					}

					consumo = consumoAgua.getConsumoCobradoMes() + "";
					diasConsumo = consumoAgua.getDiasConsumo() + "";

				} else {
					if (consumoEsgoto != null) {
						if (consumoEsgoto.getLeituraAtual() != null ) {
							leituraAtual = consumoEsgoto.getLeituraAtual() + "";
						} else {
							leituraAtual = "";
						}

						consumo = consumoEsgoto.getConsumoCobradoMes() + "";
						diasConsumo = consumoEsgoto.getDiasConsumo() + "";
					}
				}
			}
			
			ConsumoHistorico consumoAguaAreaComum = null;
			ConsumoHistorico consumoEsgotoAreaComum = null;
			
			if(imovelAreaComum != null){
				
				consumoAguaAreaComum = fachada.
						buscarConsumoHistoricoPorImovelIdTipoLigacao(imovelAreaComum.getId(), ConstantesSistema.LIGACAO_AGUA);
				
				consumoEsgotoAreaComum = fachada.
							buscarConsumoHistoricoPorImovelIdTipoLigacao(imovelAreaComum.getId(), ConstantesSistema.LIGACAO_ESGOTO);
				
			}
			
			// Fim de Carregamento de Informações

			appendTexto("! 0 816 0 1720 1\n");

			 // Título da conta
	        appendTexto("CENTER\n");
	        appendTexto70(0, 53, "EXTRATO MACRO MEDIDOR");
	        appendTexto("LEFT\n");
						
			// Matricula do imovel
			appendTexto70(440, 95, imovel.getId().toString());
			
			// Ano mes de referencia da conta
			String mes = Util.retornaDescricaoMesDoAnoMes(imovel.getAnoMesConta().toString());
			String ano = imovel.getAnoMesConta().toString().substring(0, 4);
			appendTexto70(572, 95, mes + " / " + ano);
			

			// Dados do cliente
			String cpfCnpjFormatado = "";
			if (imovel.getCpfCnpjCliente() != null && !imovel.getCpfCnpjCliente().equals("")) {
				cpfCnpjFormatado = imovel.getCpfCnpjCliente().trim();
			}
			
			appendTexto70(xMargem45, 142, imovel.getNomeUsuario());
			appendTexto70(360, 142, cpfCnpjFormatado);
			appendTextos(7, 0, xMargem45, 165, cortarEndereco(imovel.getEndereco()), 23);
			
			
			// Inscricao
			appendTexto70(41, 255, fachada.formatarInscricao(imovel.getInscricao()));
			
			// Grupo de Faturamento
			appendTexto70(320, 255, imovel.getGrupoFaturamento().toString());

			// Verificamos é por categoria ou subcategoria
			ArrayList<CategoriaSubcategoria> arrayListCategoriaSubcategoria = new ArrayList<CategoriaSubcategoria>();
			
			arrayListCategoriaSubcategoria = (ArrayList<CategoriaSubcategoria>) 
								fachada.buscarCategoriaSubcategoriaPorImovelId(imovel.getId());


			categoriasEconomias(arrayListCategoriaSubcategoria);

			final int yRota = 320;
			
			// Codigo da Rota
			appendTexto70(xMargem45, yRota, imovel.getCodigoRota().toString());
			
			// Hidrometro
			appendTexto70(182, yRota, hidrometro);
			
			// Situacao da ligacao de Agua
			if (situacaoAgua.length() > 13) {
				appendTexto70(419, yRota, situacaoAgua.substring(0, 13));
			} else {
				appendTexto70(419, yRota, situacaoAgua);
				
			}

			// Situacao da ligacao de esgoto
			appendTexto70(624, yRota, situacaoEsgoto);

			// Leitura Atual Informada
			appendTexto70(xMargem45, 360, "LEIT ATUAL INF:");
			appendTexto70(280, 360, leituraInformada);

			// Leitura Atual Faturada
			appendTexto70(xMargem45, 385, "LEIT. ATUAL FAT:");
			appendTexto70(280, 385, leituraAtual);

			// Leitura Anterior
			appendTexto70(xMargem45, 410,  "LEIT. ANT:");
			appendTexto70(280, 410, leituraAnterior);

			// Consumo
			if ((imovel.getIndcFaturamentoAgua().equals(ConstantesSistema.SIM) || imovel.getIndcFaturamentoEsgoto().equals(ConstantesSistema.SIM))
					&& imovel.getMatriculaCondominio() != null) {

				String consumoRateio = "";

				if (imovel.getIndcFaturamentoAgua().equals(ConstantesSistema.SIM)) {
					
					if (imovelAreaComum != null && hidrometroInstaladoAgua != null 
							&& hidrometroInstaladoAgua.getTipoRateio() !=null 
							&& hidrometroInstaladoAgua.getTipoRateio().intValue() == ConstantesSistema.TIPO_RATEIO_AREA_COMUM 
							&& consumoAguaAreaComum !=  null
							&& consumoAguaAreaComum.getConsumoRateio() !=  null) {

						consumoRateio = "" + consumoAguaAreaComum.getConsumoRateio();
						
					} else {
						
						if(consumoAgua !=null && consumoAgua.getConsumoRateio() != null){
							consumoRateio += consumoAgua.getConsumoRateio().toString();
						}
						
						
					}

				} else if (imovel.getIndcFaturamentoEsgoto().equals(ConstantesSistema.SIM)) {

					if (imovelAreaComum != null && hidrometroInstaladoPoco != null 
							&& hidrometroInstaladoPoco.getTipoRateio() !=null 
							&& hidrometroInstaladoPoco.getTipoRateio().intValue() == ConstantesSistema.TIPO_RATEIO_AREA_COMUM
							&& consumoEsgotoAreaComum != null
							&& consumoEsgotoAreaComum.getConsumoRateio() != null) {

						consumoRateio = "" + consumoEsgotoAreaComum.getConsumoRateio();
						
					} else {
						
						if(consumoEsgoto !=null && consumoEsgoto.getConsumoRateio() != null){
							consumoRateio += consumoEsgoto.getConsumoRateio().toString();
						}
						
						
					}
				}
				
				appendTexto70(xMargem45, 435, "CONSUMO (m3):");
				appendTexto70(280, 435, consumo);

				appendTexto70(410, 435, "CONSUMO RATEIO (m3):");
				appendTexto70(650, 435, consumoRateio);
				
			} else {
				appendTexto70(xMargem45, 435, "CONSUMO (m3):");
				appendTexto70(280, 435, consumo);
			}

			// Data Leitura Anterior
			appendTexto70(410, 360, "DATA LEITURA ANT:");
			appendTexto70(650, 360, dataLeituraAnterior);

			// Data Leitura Anterior Atual			
			appendTexto70(410, 385, "DATA LEITURA ATUAL:");
			appendTexto70(650, 385, dataLeitura);

			// Numero de dias
			appendTexto70(410, 410, "DIAS CONSUMO:");
			appendTexto70(650, 410, diasConsumo);

			// Divisória do quadro de Historico de Consumo/Qualidade da Agua
			appendTexto("LINE 41 470 787 470 2\n");

			// Historico de Consumo

			int consumoMedio = 0;

			if (imovel.getConsumoMedioLigacaoAgua() != 0) {

				consumoMedio = imovel.getConsumoMedioLigacaoAgua();
			} else {

				consumoMedio = imovel.getConsumoMedioEsgoto();
			}
						
			appendTexto70(xMargem45, yCorpoDados, "HISTORICO DE CONSUMO");
			
			yCorpoDados+=yMargem35;
			appendTexto70(xMargem45, yCorpoDados, "REFERENCIA");
			appendTexto70(189, yCorpoDados, "CONS:");
			appendTexto70(270, yCorpoDados, "REFERENCIA");
			appendTexto70(412, yCorpoDados, "CONS");
			appendTexto70(490, yCorpoDados, "REFERENCIA");
			appendTexto70(635, yCorpoDados, "CONS");
			
			appendTexto("LINE 695 512 695 590 1\n");
			appendTexto70(710, yCorpoDados, "MEDIA");
			
			yCorpoDados+=yMargem25;
			appendTexto70(710, yCorpoDados, consumoMedio+"");
		
			List<ConsumoAnteriores> consumoAnteriores;
			consumoAnteriores = fachada.buscarConsumoAnterioresPorImovelId(imovel.getId());

			if (consumoAnteriores != null) {
				
				int yHistorico = yCorpoDados, xHistorico = xMargem45;
				
				for (int i = 0; i < consumoAnteriores.size(); i++) {
					ConsumoAnteriores consumoAnterior = (ConsumoAnteriores) consumoAnteriores.get(i);

					if (hidrometroInstaladoAgua != null) {
						if (consumoAnterior.getTipoLigacao() != ConstantesSistema.LIGACAO_AGUA)
							continue;
					} else if (hidrometroInstaladoPoco != null) {
						if (consumoAnterior.getTipoLigacao() != ConstantesSistema.LIGACAO_POCO)
							continue;
					}
									
					appendTexto70(xHistorico, yHistorico, Util.formatarAnoMesParaMesAno(Integer.toString(consumoAnterior.getAnoMesReferencia())));
					appendTexto70(xHistorico + 142, yHistorico, Util.adicionarZerosEsquerdaNumero(2, consumoAnterior.getConsumo().toString()));

					if ((i + 1) % 2 == 0) {
						xHistorico += 223;
						yHistorico = yCorpoDados;
					} else {
						yHistorico += 25;
					}
				} 
			}
			
			yCorpoDados += yMargem105;
			
			appendTexto70(200, yCorpoDados, "EXTRATO DE CONSUMO DO MACROMEDIDOR");
			
			yCorpoDados+=yMargem25+10;
			appendTexto70(xMargem45, yCorpoDados, "CONSUMO DO IMÓVEL CONDOMÍNIO");

			Integer tipoLigacao = null;
			if(consumoAgua != null){
				appendTexto70(xMargemDadosCosumoMacro, yCorpoDados, consumoAgua.getConsumoCobradoMes() + " m3");
				tipoLigacao = ConstantesSistema.LIGACAO_AGUA;
				
			}else if(consumoEsgoto != null){
				appendTexto70(xMargemDadosCosumoMacro, yCorpoDados, consumoEsgoto.getConsumoCobradoMes() + " m3");
				tipoLigacao = ConstantesSistema.LIGACAO_ESGOTO;
			}
			
			if(tipoLigacao != null){
				yCorpoDados+=yMargem25;
				appendTexto70(xMargem45, yCorpoDados, "SOMA DOS CONSUMOS DOS IMÓVEIS VINCULADOS");
				appendTexto70(xMargemDadosCosumoMacro, yCorpoDados, fachada.obterConsumoImoveisMicro(imovel.getId(),tipoLigacao) + " m3");
			}
							
			yCorpoDados+=yMargem25;
			appendTexto70(xMargem45, yCorpoDados, "QUANTIDADE IMÓVEIS VINCULADOS");
			appendTexto70(xMargemDadosCosumoMacro, yCorpoDados, fachada.obterQuantidadeImovelMicro(imovel.getId()) - 1 + "");
			
			yCorpoDados+=yMargem25;
		
			if (imovelAreaComum != null && hidrometroInstaladoAgua != null 
					&& hidrometroInstaladoAgua.getTipoRateio() != null
					&& hidrometroInstaladoAgua.getTipoRateio().intValue() == ConstantesSistema.TIPO_RATEIO_AREA_COMUM 
					&& consumoAguaAreaComum.getConsumoRateio() != null) {
		
				appendTexto70(xMargem45, yCorpoDados, "CONSUMO RATEADO");
				appendTexto70(xMargemDadosCosumoMacro, yCorpoDados, consumoAguaAreaComum.getConsumoRateio() + " m3");
				
			} else {
				ConsumoHistorico consumoGenerico = null;
				if( consumoAgua!= null ){
					consumoGenerico = consumoAgua;
				}else if( consumoEsgoto != null ){
					consumoGenerico = consumoEsgoto;
				}
				
				if( consumoGenerico!= null ){
					if ( consumoGenerico.getConsumoRateio() != null 
							&& consumoGenerico.getConsumoRateio() < 0 && imovel.getIndicadorImovelRateioNegativo().equals(ConstantesSistema.NAO)){
						
						appendTexto70(xMargem45, yCorpoDados, "CONSUMO RATEADO");
						appendTexto70(xMargemDadosCosumoMacro, yCorpoDados, " 0 m3");
						
					}else{
						if (((int) consumoGenerico.getConsumoRateio() / ( fachada.obterQuantidadeImovelMicro(imovel.getId()) ) - 1) == 0) {
							
							appendTexto70(xMargem45, yCorpoDados, "CONSUMO RATEADO");
							appendTexto70(xMargemDadosCosumoMacro, yCorpoDados, " 0 m3");
							
						} else {
								
							appendTexto70(xMargem45, yCorpoDados, "CONSUMO RATEADO");
							appendTexto70(xMargemDadosCosumoMacro, yCorpoDados, consumoGenerico.getConsumoRateio() + " m3");
						}
					}
				}
			}

			yCorpoDados+=yMargem25;
			
			ConsumoHistorico consumoGenerico = null;
			if( consumoAgua!= null ){
				consumoGenerico = consumoAgua;
			}else if( consumoEsgoto != null ){
				consumoGenerico = consumoEsgoto;
			}
			
			if (consumoGenerico!=null && consumoGenerico.getConsumoRateio() !=null && 
					consumoGenerico.getConsumoRateio() < 0 && imovel.getIndicadorImovelRateioNegativo().equals(ConstantesSistema.NAO)){
				
				appendTexto70(xMargem45, yCorpoDados, "CONSUMO RATEADO POR UNIDADE");
				appendTexto70(xMargemDadosCosumoMacro, yCorpoDados, "0 m3");
			
			}else{
				appendTexto70(xMargem45, yCorpoDados, "CONSUMO RATEADO POR UNIDADE");
				appendTexto70(xMargemDadosCosumoMacro, yCorpoDados, (int) consumoGenerico.getConsumoRateio()/ (fachada.obterQuantidadeImovelMicro(imovel.getId())-1) + " m3");
			}

			yCorpoDados+=55;
			
			appendTexto70(360, yCorpoDados, "IMPORTANTE");
			
			 // Box Importante
			 appendTexto("BOX 45 830 790 865 1\n");
			
			 yCorpoDados = 875;
			 
			 appendTexto70(53, yCorpoDados, "CASO O VALOR DO RATEIO ESTEJA ELEVADO");
			 
			 yCorpoDados+=yMargem25;
			 appendTexto70(63, 900, "1. Confirme a leitura do macro");
			 
			 yCorpoDados+=yMargem25;
			 appendTexto70(63, yCorpoDados, "2. Verifique os reservatórios");
			 
			 yCorpoDados+=yMargem25;
			 appendTexto70(63, yCorpoDados, "3. Verifique se há apartamento ligado clandestino");
			 
			 yCorpoDados+=yMargem25;
			 appendTexto70(53, yCorpoDados, "QUALQUER IRREGULARIDADE COMUNIQUE A COMPESA ATRAVÉS DO");
			 
			 yCorpoDados+=yMargem25;
			 appendTexto70(53, yCorpoDados, "SETOR DE ATENDIMENTO");
			 
			 yCorpoDados+=yMargem25;
			 appendTexto70(53, yCorpoDados, "RATEIO: Obtido atraves da diferença do consumo entre");
			 
			 yCorpoDados+=yMargem25;
			 appendTexto70(53, yCorpoDados, "o macromedidor e os consumos dos apartamentos");
			 
			// Canhoto
			appendTexto70(155, 1518, imovel.getId().toString());

			//Comando para imprimir
			appendTexto(comandoImpressao());

		} catch (Exception ex) {
			ex.printStackTrace();
			Log.v(ConstantesSistema.CATEGORIA, ex.getMessage());
		}
		
		
		return buffer;
	}
	
}
