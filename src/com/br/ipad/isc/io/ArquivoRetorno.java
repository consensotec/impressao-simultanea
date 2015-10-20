
package com.br.ipad.isc.io;

import java.util.ArrayList;
import java.util.Date;

import android.util.Log;

import com.br.ipad.isc.bean.CategoriaSubcategoria;
import com.br.ipad.isc.bean.ConsumoHistorico;
import com.br.ipad.isc.bean.ContaCategoria;
import com.br.ipad.isc.bean.ContaCategoriaConsumoFaixa;
import com.br.ipad.isc.bean.ContaImposto;
import com.br.ipad.isc.bean.HidrometroInstalado;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.bean.SequencialRotaMarcacao;
import com.br.ipad.isc.bean.SistemaParametros;
import com.br.ipad.isc.controladores.ControladorBasico;
import com.br.ipad.isc.excecoes.ControladorException;
import com.br.ipad.isc.fachada.Fachada;
import com.br.ipad.isc.util.ConstantesSistema;
import com.br.ipad.isc.util.Util;

public class ArquivoRetorno extends ControladorBasico {

    private static ArquivoRetorno instancia;
    public static final short ARQUIVO_COMPLETO = 0;
    public static final short ARQUIVO_LIDOS_ATE_AGORA = 1;
    public static final short ARQUIVO_INCOMPLETO = 2;
    public static final short ARQUIVO_TODOS_OS_CALCULADOS = 3;
    
    private Fachada fachada = Fachada.getInstance();
    private ArrayList<Integer> arrayListImovelConta;   


	public static StringBuilder montaArquivo = new StringBuilder();	
	

	public void setArrayListImovelConta(int tipoFinalizacao) {
    	try {
    		if ( tipoFinalizacao == ArquivoRetorno.ARQUIVO_TODOS_OS_CALCULADOS) {
    			 arrayListImovelConta = getControladorImovelConta().buscarIdsImoveisLidos();
    			 
    		}else if(tipoFinalizacao == ArquivoRetorno.ARQUIVO_INCOMPLETO){
	        	arrayListImovelConta = fachada.buscarIdsImoveisCalculados();
    		}else if(tipoFinalizacao == ArquivoRetorno.ARQUIVO_COMPLETO){
    			 arrayListImovelConta = getControladorImovelConta().buscarIdsImoveisLidosNaoEnviados();
    		// lidos até agora
    		}else{
    			 arrayListImovelConta = getControladorImovelConta().buscarIdsImoveisLidosNaoEnviadosNaoCondominio();
    		}
		} catch (ControladorException e) {
			Log.e(ConstantesSistema.CATEGORIA, e.getMessage());
			e.printStackTrace();
		}
	}
    
    public String getCaminhoArquivoRetorno(short tipoArquivo) {
    
        
        // primiro imovel
        ImovelConta primeiroImovel = fachada.buscarPrimeiroImovel();
     

        if (tipoArquivo == ARQUIVO_TODOS_OS_CALCULADOS) {
	        return "GCOMPLETO"
	            + Util.adicionarZerosEsquerdaNumero(3, primeiroImovel.getIdLocalidade() + "" )  + primeiroImovel.getInscricao().substring(3, 6) 
	            + primeiroImovel.getCodigoRota() + primeiroImovel.getAnoMesConta() + ".txt";
	    } else {
	        return "G"
	        		+ Util.adicionarZerosEsquerdaNumero(3, primeiroImovel.getIdLocalidade() + "" )  + primeiroImovel.getInscricao().substring(3, 6) 
		            + primeiroImovel.getCodigoRota() + primeiroImovel.getAnoMesConta() + ".txt";
	    }
    }


    public ArquivoRetorno() {
    	super(); 
    	
    }
   
    
    public int getTotalImoveis(){
    	return arrayListImovelConta.size();
    }
    
    public static ArquivoRetorno getInstancia() {
	    if (instancia == null) {
	        instancia = new ArquivoRetorno();
	    }

    	return instancia;
    }

    private String gerarRegistroTipo1(ImovelConta imovel) {

    	StringBuilder registrosTipo1 = new StringBuilder();

		HidrometroInstalado hidrometoInstaladoAgua = new HidrometroInstalado();
		HidrometroInstalado hidrometoInstaladoPoco = new HidrometroInstalado();
		
		hidrometoInstaladoAgua = fachada.buscarHidrometroInstaladoPorImovelTipoMedicao(imovel.getId(), ConstantesSistema.LIGACAO_AGUA);
		hidrometoInstaladoPoco = fachada.buscarHidrometroInstaladoPorImovelTipoMedicao(imovel.getId(), ConstantesSistema.LIGACAO_POCO);
		
		boolean temAgua = false;
		boolean temEsgoto = false;

		ConsumoHistorico consumoAgua = new ConsumoHistorico();
		ConsumoHistorico consumoEsgoto = new ConsumoHistorico();
		
		consumoAgua = fachada.buscarConsumoHistoricoPorImovelIdTipoLigacao(imovel.getId(), ConstantesSistema.LIGACAO_AGUA);
		consumoEsgoto = fachada.buscarConsumoHistoricoPorImovelIdTipoLigacao(imovel.getId(), ConstantesSistema.LIGACAO_ESGOTO);
		
		if (consumoAgua != null) {

			temAgua = true;

			String indicadorSituacao = "" + ConstantesSistema.LEITURA_REALIZADA;

			if (hidrometoInstaladoAgua != null && hidrometoInstaladoAgua.getLeituraLimiteSuperior()!=null && hidrometoInstaladoAgua.getLeitura()!= null && hidrometoInstaladoAgua.getLeitura()!= null  
				&& hidrometoInstaladoAgua.getLeituraLimiteInferior() >= hidrometoInstaladoAgua.getLeituraLimiteInferior()
					&& hidrometoInstaladoAgua.getLeitura() <= hidrometoInstaladoAgua.getLeituraLimiteSuperior()) {
				indicadorSituacao = "" + ConstantesSistema.LEITURA_REALIZADA;
			} else {
				if (hidrometoInstaladoAgua != null) {
					indicadorSituacao = "" + ConstantesSistema.LEITURA_CONFIRMADA;
				}
			}

			String anormalidadeLeitura = null;
			Date dataLeitura = new Date();
			
			if (hidrometoInstaladoAgua != null) {
				
				if(hidrometoInstaladoAgua.getAnormalidade() != null){
					anormalidadeLeitura = hidrometoInstaladoAgua.getAnormalidade().toString();
				}
				
				if(hidrometoInstaladoAgua.getDataLeitura() != null){
					dataLeitura = hidrometoInstaladoAgua.getDataLeitura() ;
				}
				

			} else {
				if (hidrometoInstaladoPoco != null && hidrometoInstaladoPoco.getDataLeitura() != null) {
					dataLeitura = hidrometoInstaladoPoco.getDataLeitura();
				}
			}
			
			// 1 - Tipo de Registro
			registrosTipo1.append( Util.formatarCampoParaConcatenacao("1") );
			
			// 2 -Matricula
			registrosTipo1.append(Util.formatarCampoParaConcatenacao(imovel.getId()));
			
			// 3 - Tipo de Medição - AGUA 
			registrosTipo1.append( Util.formatarCampoParaConcatenacao(ConstantesSistema.LIGACAO_AGUA));
			
			// 4 - Ano/Mês do faturamento
			registrosTipo1.append( Util.formatarCampoParaConcatenacao( Util.formatarAnoMesParaMesAnoSemBarra(imovel.getAnoMesConta().toString() )));
			
			// 5 - Número da Conta
			if(imovel.getNumeroConta() != null){
				registrosTipo1.append(Util.formatarCampoParaConcatenacao( imovel.getNumeroConta() ));
			}else{
				registrosTipo1.append(Util.formatarCampoParaConcatenacao("0"));
			}
			
			
			// 6 - Grupo de faturamento
			registrosTipo1.append(Util.formatarCampoParaConcatenacao( imovel.getGrupoFaturamento() ));
			
			// 7 - Código da rota
			registrosTipo1.append(Util.formatarCampoParaConcatenacao( imovel.getCodigoRota() ));
			
			// 8 - Leitura do Hidrômetro
			if(hidrometoInstaladoAgua != null && hidrometoInstaladoAgua.getLeitura() != null ){
				registrosTipo1.append(Util.formatarCampoParaConcatenacao(hidrometoInstaladoAgua.getLeitura() ) );
			}else{
				registrosTipo1.append(Util.formatarCampoParaConcatenacao(null) );
			}
			
			
			// 9 - Anormalidade de Leitura
			registrosTipo1.append( anormalidadeLeitura != null ? Util.formatarCampoParaConcatenacao(anormalidadeLeitura) : Util.formatarCampoParaConcatenacao(0) );
			
			// 10 - Data e hora da leitura
			registrosTipo1.append( Util.formatarCampoParaConcatenacao( Util.formatarData(dataLeitura) ) );
			
			// 11 - Indicador de situação da leitura
			registrosTipo1.append( Util.formatarCampoParaConcatenacao( indicadorSituacao ) );
			
			// 12 - Leitura de faturamento
			registrosTipo1.append(consumoAgua.getLeituraAtual() != null ? Util.formatarCampoParaConcatenacao( consumoAgua.getLeituraAtual() ) : Util.formatarCampoParaConcatenacao(null));
			
			// 13 - Consumo Medido no Mes
			registrosTipo1.append(consumoAgua.getConsumoMedidoMes() != null ? Util.formatarCampoParaConcatenacao( consumoAgua.getConsumoMedidoMes() ) : Util.formatarCampoParaConcatenacao(null));
			
			// 14 - Consumo Cobrado no Mes
			registrosTipo1.append(consumoAgua.getConsumoCobradoMes() != null ? Util.formatarCampoParaConcatenacao( consumoAgua.getConsumoCobradoMes() ): Util.formatarCampoParaConcatenacao(null));
			
			// 15 - Consumo Rateio Agua
			registrosTipo1.append(consumoAgua.getConsumoRateio() != null ? Util.formatarCampoParaConcatenacao( consumoAgua.getConsumoRateio() ): Util.formatarCampoParaConcatenacao("0"));
			
			// 16 - Consumo Rateio Esgoto
			if(consumoEsgoto != null && consumoEsgoto.getConsumoRateio() != null ){
				registrosTipo1.append(Util.formatarCampoParaConcatenacao( consumoEsgoto.getConsumoRateio() ) );
			}else{
				registrosTipo1.append(Util.formatarCampoParaConcatenacao( "0" ) );
			}
			
			
			// 17 - Tipo de Consumo
			registrosTipo1.append(consumoAgua.getTipoConsumo() != null ? Util.formatarCampoParaConcatenacao( consumoAgua.getTipoConsumo() ) : Util.formatarCampoParaConcatenacao(null) );
			
			// 18 - Anormalidade de Consumo
			registrosTipo1.append(consumoAgua.getConsumoAnormalidade() != null ? Util.formatarCampoParaConcatenacao( consumoAgua.getConsumoAnormalidade().getId() ) : Util.formatarCampoParaConcatenacao(null));
			
			// 19 - Indicador de emissao de conta
			registrosTipo1.append( Util.formatarCampoParaConcatenacao( imovel.getIndcImovelImpresso() ) );
			
			// 20 - Inscricao do Imovel
			registrosTipo1.append( Util.formatarCampoParaConcatenacao( imovel.getInscricao() ) );
			
			// 21 - Indicador Geração da Conta
			registrosTipo1.append(Util.formatarCampoParaConcatenacao( ConstantesSistema.SIM ));
			
			// 22 - Consumo Imóveis MICRO Sem Rateio
			if(consumoAgua.getConsumoCobradoMesImoveisMicro() != null){
				registrosTipo1.append(Util.formatarCampoParaConcatenacao( consumoAgua.getConsumoCobradoMesImoveisMicro()));
			}else{
				registrosTipo1.append(Util.formatarCampoParaConcatenacao(null));
			}
			
			
			// 23 - Anormalidade de faturamento
			if(consumoAgua.getAnormalidadeLeituraFaturada() != null && consumoAgua.getAnormalidadeLeituraFaturada().getId() != null){
				registrosTipo1.append(Util.formatarCampoParaConcatenacao( consumoAgua.getAnormalidadeLeituraFaturada().getId()) );
			}else{
				registrosTipo1.append(Util.formatarCampoParaConcatenacao(null));
			}
			
			// 24 - ID do documento de cobrança
			registrosTipo1.append( Util.formatarCampoParaConcatenacao( imovel.getIdDocumentoNotificacaoDebito() ) );
			
			// 25 - Leitura Anterior do Hidrômetro
			if(hidrometoInstaladoAgua != null){
				registrosTipo1.append(Util.formatarCampoParaConcatenacao( fachada.obterLeituraAnterior(hidrometoInstaladoAgua)) );
			}else{
				registrosTipo1.append(Util.formatarCampoParaConcatenacao(null));
			}
			
			// 26 - Quantidade de vezes que a conta foi impressa
			registrosTipo1.append( Util.formatarCampoParaConcatenacao( imovel.getQntVezesImpressaoConta() ) );
			
			// 27 - Valor de agua
			registrosTipo1.append( Util.formatarCampoParaConcatenacao( Util.formatarDoubleParaMoedaReal(fachada.obterValorTotal(imovel.getId(), ConstantesSistema.LIGACAO_AGUA) ) ));
			
			// 28 - Valor de Esgoto
			registrosTipo1.append( Util.formatarCampoParaConcatenacao( Util.formatarDoubleParaMoedaReal(fachada.obterValorTotal(imovel.getId(), ConstantesSistema.LIGACAO_ESGOTO)  ) ));
			
			// 29 - Valor de debitos
			registrosTipo1.append( Util.formatarCampoParaConcatenacao( Util.formatarDoubleParaMoedaReal(fachada.obterValorDebitoTotal(imovel.getId() ) ) ));
			
			// 30 Valor de creditos
			registrosTipo1.append( Util.formatarCampoParaConcatenacao( Util.formatarDoubleParaMoedaReal(fachada.obterValorCreditoTotal(imovel.getId() ) ) ));
			
			// 31 - Valor de impostos
			registrosTipo1.append(Util.formatarCampoParaConcatenacao( Util.formatarDoubleParaMoedaReal(fachada.obterValorImpostoTotal(imovel.getId() ) ) ));
			
			// 32 - Numero coordenada X  (Latitude) 
			registrosTipo1.append(Util.formatarCampoParaConcatenacao( imovel.getNumeroCoordenadaX() ));
			
			// 33 - Numero coordenada Y  (Longitude) 
			registrosTipo1.append(Util.formatarCampoParaConcatenacao( imovel.getNumeroCoordenadaY() ));
						
			//Quebra de linha
			registrosTipo1.append("\n");

//			System.out.println(registrosTipo1);
		}
		
		

		if (consumoEsgoto != null) {

			temEsgoto = true;

			String indicadorSituacao = "" + ConstantesSistema.LEITURA_REALIZADA;


			if (hidrometoInstaladoPoco != null && hidrometoInstaladoPoco.getLeitura() != null && 
					hidrometoInstaladoPoco.getLeitura().intValue() >= hidrometoInstaladoPoco.getLeituraLimiteInferior().intValue()
					&& hidrometoInstaladoPoco.getLeitura().intValue() <= hidrometoInstaladoPoco.getLeituraLimiteSuperior().intValue()) {
				indicadorSituacao = "" + ConstantesSistema.LEITURA_REALIZADA;

			} else {
				if (hidrometoInstaladoPoco != null) {
					indicadorSituacao = "" + ConstantesSistema.LEITURA_CONFIRMADA;
				}
			}

			String anormalidadeLeitura = "";
			
			Date dataLeitura = new Date();
			
			if (hidrometoInstaladoPoco != null && !hidrometoInstaladoPoco.equals("")) {
				if(hidrometoInstaladoPoco.getAnormalidade() != null && !hidrometoInstaladoPoco.getAnormalidade().equals("")){
					anormalidadeLeitura = "" + hidrometoInstaladoPoco.getAnormalidade();
				}
				dataLeitura = hidrometoInstaladoPoco.getDataLeitura();
			} else {
				//DECIDIMOS QUE  QUANDO NAO TEM HIDROMETRO NAO NAO ANORMALIDADE
				if (hidrometoInstaladoPoco  == null) {
					anormalidadeLeitura = "" ;
				}
				if (hidrometoInstaladoAgua != null && !hidrometoInstaladoAgua.equals("") && hidrometoInstaladoAgua.getDataLeitura() != null) {
					dataLeitura = hidrometoInstaladoAgua.getDataLeitura();
				}
			}

			// 1 - Tipo de Registro
			registrosTipo1.append(Util.formatarCampoParaConcatenacao("1"));
			
			// 2 - Matricula
			registrosTipo1.append( Util.formatarCampoParaConcatenacao( imovel.getId() ) );
			
			// 3 - Tipo de Medição
			registrosTipo1.append(Util.formatarCampoParaConcatenacao("2"));
			
			// 4 - Ano/Mês do faturamento
			registrosTipo1.append( Util.formatarCampoParaConcatenacao( Util.formatarAnoMesParaMesAnoSemBarra(imovel.getAnoMesConta().toString() )));
			
			// 5 - Número da Conta
			if(imovel.getNumeroConta() != null){
				registrosTipo1.append(Util.formatarCampoParaConcatenacao( imovel.getNumeroConta() ));
			}else{
				registrosTipo1.append(Util.formatarCampoParaConcatenacao("0"));
			}
						
			
			
			// 6 - Grupo de faturamento
			registrosTipo1.append(Util.formatarCampoParaConcatenacao( imovel.getGrupoFaturamento() ));
			
			// 7 - Código da rota
			registrosTipo1.append(Util.formatarCampoParaConcatenacao( imovel.getCodigoRota() ) );
			
			// 8 - Leitura do Hidrômetro
			if(hidrometoInstaladoPoco != null && hidrometoInstaladoPoco.getLeitura() !=null ){
				registrosTipo1.append(Util.formatarCampoParaConcatenacao( hidrometoInstaladoPoco.getLeitura()) );
			}else{
				registrosTipo1.append( Util.formatarCampoParaConcatenacao(null) );
			}

			// 9 - Anormalidade de Leitura
			registrosTipo1.append( Util.formatarCampoParaConcatenacao( anormalidadeLeitura ) );
			
			// 10 - Data e hora da leitura
			registrosTipo1.append( Util.formatarCampoParaConcatenacao( Util.formatarData(dataLeitura) ) );
			
			// 11 -  Indicador de situação da leitura
			registrosTipo1.append( Util.formatarCampoParaConcatenacao( indicadorSituacao ) );
			
			// 12 - Leitura de faturamento
			registrosTipo1.append(consumoEsgoto.getLeituraAtual() != null ? Util.formatarCampoParaConcatenacao( consumoEsgoto.getLeituraAtual() ) : Util.formatarCampoParaConcatenacao(null));
			
			// 13 - Consumo Medido no Mes
			registrosTipo1.append(consumoEsgoto.getConsumoMedidoMes() != null ? Util.formatarCampoParaConcatenacao(consumoEsgoto.getConsumoMedidoMes()) : Util.formatarCampoParaConcatenacao(null));
			
			// 14 - Consumo Cobrado no Mes
			registrosTipo1.append(consumoEsgoto.getConsumoCobradoMes() != null ? Util.formatarCampoParaConcatenacao( consumoEsgoto.getConsumoCobradoMes() ) : Util.formatarCampoParaConcatenacao(null));
			
			// 15 - Consumo Rateio Agua
			if(consumoAgua != null && consumoAgua.getConsumoRateio() != null ){
				registrosTipo1.append( Util.formatarCampoParaConcatenacao( consumoAgua.getConsumoRateio() ) );
			}else{
				registrosTipo1.append(Util.formatarCampoParaConcatenacao("0"));
			}
			
			
			// 16 - Consumo Rateio Esgoto
			registrosTipo1.append(consumoEsgoto.getConsumoRateio() != null ? Util.formatarCampoParaConcatenacao( consumoEsgoto.getConsumoRateio() ) : Util.formatarCampoParaConcatenacao("0"));
			
			// 17 - Tipo de Consumo
			registrosTipo1.append(consumoEsgoto.getTipoConsumo() != null ? Util.formatarCampoParaConcatenacao( consumoEsgoto.getTipoConsumo() ) : Util.formatarCampoParaConcatenacao(null));
			
			// 18 - Anormalidade de Consumo
			registrosTipo1.append(consumoEsgoto.getConsumoAnormalidade() != null ? Util.formatarCampoParaConcatenacao( consumoEsgoto.getConsumoAnormalidade().getId() ) : Util.formatarCampoParaConcatenacao(null));
			
			// 19 - Indicador de emissao de conta
			registrosTipo1.append( Util.formatarCampoParaConcatenacao( imovel.getIndcImovelImpresso() ) );
			
			// 20 - Inscricao do Imovel
			registrosTipo1.append( Util.formatarCampoParaConcatenacao( imovel.getInscricao() ) );
			
			// 21 - Indicador Geração da Conta
			registrosTipo1.append( Util.formatarCampoParaConcatenacao( ConstantesSistema.SIM ) );
			
			// 22 - Consumo Imóveis MICRO Sem Rateio
			if(consumoEsgoto.getConsumoCobradoMesImoveisMicro() != null){
				registrosTipo1.append(Util.formatarCampoParaConcatenacao( consumoEsgoto.getConsumoCobradoMesImoveisMicro()));
			}else{
				registrosTipo1.append(Util.formatarCampoParaConcatenacao(null));
			}
			
			
			// 23 - Anormalidade de faturamento
			registrosTipo1.append(consumoEsgoto.getAnormalidadeLeituraFaturada() != null ? Util.formatarCampoParaConcatenacao( consumoEsgoto.getAnormalidadeLeituraFaturada().getId() ) : Util.formatarCampoParaConcatenacao(null));
			
			// 24 - ID do documento de cobrança
			registrosTipo1.append(Util.formatarCampoParaConcatenacao( imovel.getIdDocumentoNotificacaoDebito() ));
			
			// 25 - Leitura Anterior do Hidrômetro
			registrosTipo1.append(hidrometoInstaladoPoco != null ? Util.formatarCampoParaConcatenacao( fachada.obterLeituraAnterior(hidrometoInstaladoPoco) ) : Util.formatarCampoParaConcatenacao(null));
			
			// 26 - Quantidade de vezes que a conta foi impressa
			registrosTipo1.append( Util.formatarCampoParaConcatenacao( imovel.getQntVezesImpressaoConta()) );
			
			// 27 - Valor de agua
			registrosTipo1.append( Util.formatarCampoParaConcatenacao(Util.formatarDoubleParaMoedaReal(fachada.obterValorTotal(imovel.getId(), ConstantesSistema.LIGACAO_AGUA))));
			
			// 28 - Valor de Esgoto
			registrosTipo1.append( Util.formatarCampoParaConcatenacao(Util.formatarDoubleParaMoedaReal(fachada.obterValorTotal(imovel.getId(), ConstantesSistema.LIGACAO_ESGOTO))));
			
			// 29 - Valor de debitos
			registrosTipo1.append(Util.formatarCampoParaConcatenacao(Util.formatarDoubleParaMoedaReal(fachada.obterValorDebitoTotal(imovel.getId()))));
			
			// 30 Valor de creditos
			registrosTipo1.append( Util.formatarCampoParaConcatenacao(Util.formatarDoubleParaMoedaReal(fachada.obterValorCreditoTotal(imovel.getId()))));
			
			// 31 - Valor de impostos
			registrosTipo1.append(Util.formatarCampoParaConcatenacao(Util.formatarDoubleParaMoedaReal(fachada.obterValorImpostoTotal(imovel.getId()))));
			
			// 32 - Numero coordenada X  (Latitude) 
			registrosTipo1.append(Util.formatarCampoParaConcatenacao( imovel.getNumeroCoordenadaX() ));
			
			// 33 - Numero coordenada Y  (Longitude) 
			registrosTipo1.append(Util.formatarCampoParaConcatenacao( imovel.getNumeroCoordenadaY() ));
			
			//Quebra de linha
			registrosTipo1.append("\n");

//			System.out.println(registrosTipo1);
		}

		// Caso nao tenha consumo nem de agua nem de esgoto
		// geramos um registro tipo 1 apenas com os débitos.
		if (!temAgua && !temEsgoto) {
			
			// 1 - Tipo de Registro
			registrosTipo1.append(Util.formatarCampoParaConcatenacao("1"));
			
			// 2 - Matricula
			registrosTipo1.append(Util.formatarCampoParaConcatenacao(imovel.getId()));
			
			// 3 - Tipo de Medição
			registrosTipo1.append(Util.formatarCampoParaConcatenacao("1"));
			
			// 4 - Ano/Mês do faturamento
			registrosTipo1.append(Util.formatarCampoParaConcatenacao( Util.formatarAnoMesParaMesAnoSemBarra(imovel.getAnoMesConta().toString())));
			
			// 5 - Número da Conta
			if(imovel.getNumeroConta() != null){
				registrosTipo1.append(Util.formatarCampoParaConcatenacao( imovel.getNumeroConta() ));
			}else{
				registrosTipo1.append(Util.formatarCampoParaConcatenacao("0"));
			}
						
			
			// 6 - Grupo de faturamento
			registrosTipo1.append(Util.formatarCampoParaConcatenacao(imovel.getGrupoFaturamento()));
			
			// 7 - Código da rota
			registrosTipo1.append(Util.formatarCampoParaConcatenacao(imovel.getCodigoRota()));
			
			// 8 - Leitura do Hidrômetro
			if(hidrometoInstaladoAgua != null && hidrometoInstaladoAgua.getLeitura() != null){
				registrosTipo1.append(Util.formatarCampoParaConcatenacao(hidrometoInstaladoAgua.getLeitura()));
			}else{
				registrosTipo1.append(Util.formatarCampoParaConcatenacao(null));
			}
			
			// 9 - Anormalidade de Leitura
			if(hidrometoInstaladoAgua != null &&  hidrometoInstaladoAgua.getAnormalidade() != null){
				registrosTipo1.append(Util.formatarCampoParaConcatenacao( hidrometoInstaladoAgua.getAnormalidade()));
			}else{
				registrosTipo1.append(Util.formatarCampoParaConcatenacao(null));
			}
			
			
			// 10 - Data e hora da leitura
			registrosTipo1.append(Util.formatarCampoParaConcatenacao( Util.formatarData(new Date())) );
			
			// 11 - Indicador de situação da leitura
			registrosTipo1.append(Util.formatarCampoParaConcatenacao(null));
			
			// 12 - Leitura de faturamento
			if(hidrometoInstaladoAgua != null && hidrometoInstaladoAgua.getLeitura() != null){
				registrosTipo1.append(Util.formatarCampoParaConcatenacao( hidrometoInstaladoAgua.getLeitura()));
			}else{
				registrosTipo1.append(Util.formatarCampoParaConcatenacao(null));
			}
			
			// 13 - Consumo Medido no Mes
			registrosTipo1.append(Util.formatarCampoParaConcatenacao(null));
			
			// 14 - Consumo Cobrado no Mes
			registrosTipo1.append(Util.formatarCampoParaConcatenacao(null));
			
			// 15 - Consumo Rateio Agua
			registrosTipo1.append(Util.formatarCampoParaConcatenacao("0"));
			
			// 16 - Consumo Rateio Esgoto
			registrosTipo1.append(Util.formatarCampoParaConcatenacao("0"));
			
			// 17 - Tipo de Consumo
			registrosTipo1.append(Util.formatarCampoParaConcatenacao(null));
			
			// 18 - Anormalidade de Consumo
			registrosTipo1.append(Util.formatarCampoParaConcatenacao(null));
			
			// 19 - Indicador de emissao de conta
			registrosTipo1.append(Util.formatarCampoParaConcatenacao( imovel.getIndcImovelImpresso()));
			
			// 20 - Inscricao do Imovel
			registrosTipo1.append(Util.formatarCampoParaConcatenacao( imovel.getInscricao()));
			
			// 21 - Indicador Geração da Conta
			registrosTipo1.append( Util.formatarCampoParaConcatenacao( ConstantesSistema.SIM));
			
			// 22 - Consumo Imóveis MICRO Sem Rateio
			registrosTipo1.append(Util.formatarCampoParaConcatenacao(null));
		
			// 23 - Anormalidade de faturamento
			registrosTipo1.append(Util.formatarCampoParaConcatenacao(null));
			
			// 24 - ID do documento de cobrança
			registrosTipo1.append(Util.formatarCampoParaConcatenacao( imovel.getIdDocumentoNotificacaoDebito()));
			
			// 25 - Leitura Anterior do Hidrômetro
			if(hidrometoInstaladoAgua != null ){
				registrosTipo1.append(Util.formatarCampoParaConcatenacao( fachada.obterLeituraAnterior(hidrometoInstaladoAgua)));
			}else{
				registrosTipo1.append(Util.formatarCampoParaConcatenacao(null));
			}
			
			// 26 - Quantidade de vezes que a conta foi impressa
			registrosTipo1.append(Util.formatarCampoParaConcatenacao( imovel.getQntVezesImpressaoConta() ));
			
			// 27 - Valor de agua
			registrosTipo1.append(Util.formatarCampoParaConcatenacao(Util.formatarDoubleParaMoedaReal(fachada.obterValorTotal(imovel.getId(), ConstantesSistema.LIGACAO_AGUA))));
			
			// 28 - Valor de Esgoto
			registrosTipo1.append(Util.formatarCampoParaConcatenacao(Util.formatarDoubleParaMoedaReal(fachada.obterValorTotal(imovel.getId(), ConstantesSistema.LIGACAO_ESGOTO))));
			
			// 29 - Valor de debitos
			registrosTipo1.append(Util.formatarCampoParaConcatenacao(Util.formatarDoubleParaMoedaReal(fachada.obterValorDebitoTotal(imovel.getId()))));
			
			// 30 Valor de creditos
			registrosTipo1.append(Util.formatarCampoParaConcatenacao(Util.formatarDoubleParaMoedaReal(fachada.obterValorCreditoTotal(imovel.getId()))));
			
			// 31 - Valor de impostos
			registrosTipo1.append(Util.formatarCampoParaConcatenacao(Util.formatarDoubleParaMoedaReal(fachada.obterValorImpostoTotal(imovel.getId()))));
			
			// 32 - Numero coordenada X  (Latitude) 
			registrosTipo1.append(Util.formatarCampoParaConcatenacao( imovel.getNumeroCoordenadaX() ));
			
			// 33 - Numero coordenada Y  (Longitude) 
			registrosTipo1.append(Util.formatarCampoParaConcatenacao( imovel.getNumeroCoordenadaY() ));
			
			//Quebra de linha
			registrosTipo1.append("\n");
			
//			System.out.println(registrosTipo1);
		}
		
		return registrosTipo1.toString();
	}

    private String gerarRegistroTipo2e3(ImovelConta imovel) {

    	//Tipo de Registro 2 – Dados do Faturamento por Categoria
    	//Tipo de Registro 3 – Dados do Faturamento por Categoria e Faixa de Consumo
    	
    	StringBuilder registrosTipo2e3 = new StringBuilder();
		
		SistemaParametros sistemaParametros = SistemaParametros.getInstancia();
		
		ArrayList<CategoriaSubcategoria> arrayListCatetoriaSubcategorias = new ArrayList<CategoriaSubcategoria>();
		arrayListCatetoriaSubcategorias = (ArrayList<CategoriaSubcategoria>) fachada.buscarCategoriaSubcategoriaPorImovelId(imovel.getId());
		

		if (arrayListCatetoriaSubcategorias != null) {

			for (int i = 0; i < arrayListCatetoriaSubcategorias.size(); i++) {

				CategoriaSubcategoria registroTipo2 = (CategoriaSubcategoria) arrayListCatetoriaSubcategorias.get(i);

				ContaCategoria contaCategoriaAgua = new ContaCategoria();
				contaCategoriaAgua = fachada.
						buscarContaCategoriaPorCategoriaSubcategoriaId(registroTipo2.getId(), ConstantesSistema.LIGACAO_AGUA);
				
				ContaCategoria contaCategoriaEsgoto = new ContaCategoria();
				contaCategoriaEsgoto = fachada.
						buscarContaCategoriaPorCategoriaSubcategoriaId(registroTipo2.getId(), ConstantesSistema.LIGACAO_ESGOTO);

				// 1 - Tipo de registro
				registrosTipo2e3.append(Util.formatarCampoParaConcatenacao("2"));
				
				// 2 - Matrícula
				registrosTipo2e3.append(Util.formatarCampoParaConcatenacao( imovel.getId() ));
				
				// 3 - Código da categoria
				registrosTipo2e3.append(Util.formatarCampoParaConcatenacao(registroTipo2.getCodigoCategoria()));
				
				// 4 - Código da subcategoria
				short indicadorTarifa = sistemaParametros.getIndcTarifaCatgoria().shortValue();
				
				String codigoSubcategoria = "";
				
				if (indicadorTarifa == ConstantesSistema.CALCULO_POR_CATEGORA) {
					codigoSubcategoria = Util.formatarCampoParaConcatenacao("0");
				} else {
					codigoSubcategoria = Util.formatarCampoParaConcatenacao( registroTipo2.getCodigoSubcategoria() );
				}
				registrosTipo2e3.append(codigoSubcategoria);
				
				// 5 - Valor faturado de água na categoria
				registrosTipo2e3.append(contaCategoriaAgua != null ?  Util.formatarCampoParaConcatenacao( Util.formatarDoubleParaMoedaReal(contaCategoriaAgua.getValorFaturado().doubleValue())) : Util.formatarCampoParaConcatenacao(Util.formatarDoubleParaMoedaReal(0)));
				
				// 6 - Consumo faturado de água na categoria
				registrosTipo2e3.append(contaCategoriaAgua != null ?  Util.formatarCampoParaConcatenacao( contaCategoriaAgua.getNumConsumo()) : Util.formatarCampoParaConcatenacao("0"));
				
				// 7 - Valor da tarifa mínima de água da categoria
				registrosTipo2e3.append( contaCategoriaAgua != null ?  Util.formatarCampoParaConcatenacao(Util.formatarDoubleParaMoedaReal(contaCategoriaAgua.getValorTarifaMinima().doubleValue())) : Util.formatarCampoParaConcatenacao(Util.formatarDoubleParaMoedaReal(0)));
				
				// 8 - Consumo mínimo de água da categoria;
				registrosTipo2e3.append(contaCategoriaAgua != null ? Util.formatarCampoParaConcatenacao( contaCategoriaAgua.getNumConsumoMinimo()): Util.formatarCampoParaConcatenacao("0"));
				
				// 9 - Valor faturado de esgoto na categoria
				registrosTipo2e3.append( contaCategoriaEsgoto != null ? Util.formatarCampoParaConcatenacao( Util.formatarDoubleParaMoedaReal(contaCategoriaEsgoto.getValorFaturado().doubleValue())) : Util.formatarCampoParaConcatenacao(Util.formatarDoubleParaMoedaReal(0)) );
				
				// 10 - Consumo faturado de esgoto na categoria
				registrosTipo2e3.append(contaCategoriaEsgoto != null ? Util.formatarCampoParaConcatenacao(contaCategoriaEsgoto.getNumConsumo()) : Util.formatarCampoParaConcatenacao("0"));
				
				// 11 - Valor da tarifa mínima de esgoto da categoria
				registrosTipo2e3.append( contaCategoriaEsgoto != null ? Util.formatarCampoParaConcatenacao( Util.formatarDoubleParaMoedaReal(contaCategoriaEsgoto.getValorTarifaMinima().doubleValue())) :  Util.formatarCampoParaConcatenacao(Util.formatarDoubleParaMoedaReal(0)) );
				
				// 12 - Consumo mínimo de esgoto da categoria
				registrosTipo2e3.append( contaCategoriaEsgoto != null ? Util.formatarCampoParaConcatenacao( contaCategoriaEsgoto.getNumConsumoMinimo()) :  Util.formatarCampoParaConcatenacao("0") );
				
				//Quebra de linha
				registrosTipo2e3.append("\n");
//				System.out.println(registrosTipo2e3);

				ArrayList<ContaCategoriaConsumoFaixa> arrayListContaCategoriaConsumoFaixaAgua = new ArrayList<ContaCategoriaConsumoFaixa>();
				ArrayList<ContaCategoriaConsumoFaixa> arrayListContaCategoriaConsumoFaixaEsgoto = new ArrayList<ContaCategoriaConsumoFaixa>();
				
				
				if(contaCategoriaAgua != null){
					arrayListContaCategoriaConsumoFaixaAgua = fachada.
							buscarContasCategoriasConsumosFaixasPorPorContaCategoriaId(contaCategoriaAgua.getId());
				}
				
				
				if(contaCategoriaEsgoto != null){
					arrayListContaCategoriaConsumoFaixaEsgoto = fachada.
							buscarContasCategoriasConsumosFaixasPorPorContaCategoriaId(contaCategoriaEsgoto.getId());
				}
				
				
				if (arrayListContaCategoriaConsumoFaixaAgua != null) {
					for (int j = 0; j < arrayListContaCategoriaConsumoFaixaAgua.size(); j++) {
						ContaCategoriaConsumoFaixa faixaAgua = (ContaCategoriaConsumoFaixa) arrayListContaCategoriaConsumoFaixaAgua.get(j);

						ContaCategoriaConsumoFaixa faixaEsgoto = new ContaCategoriaConsumoFaixa();

						if (arrayListContaCategoriaConsumoFaixaEsgoto != null && !arrayListContaCategoriaConsumoFaixaEsgoto.isEmpty()) {

							for (int k = 0; k < arrayListContaCategoriaConsumoFaixaEsgoto.size(); k++) {

								faixaEsgoto = (ContaCategoriaConsumoFaixa) arrayListContaCategoriaConsumoFaixaEsgoto.get(k);

								if (faixaAgua.equals(faixaEsgoto)) {
									break;
								}
							}
						}

						// 1 - Tipo de registro (1)
						registrosTipo2e3.append(Util.formatarCampoParaConcatenacao("3"));
						
						// 2 - Matrícula (9);
						registrosTipo2e3.append(Util.formatarCampoParaConcatenacao(imovel.getId()));
						
						// 3 - Código da categoria (1)
						registrosTipo2e3.append(Util.formatarCampoParaConcatenacao(registroTipo2.getCodigoCategoria()));
								
						// 4 - Código da subcategoria (3)
						registrosTipo2e3.append(Util.formatarCampoParaConcatenacao(registroTipo2.getCodigoSubcategoria()));
						
						// 5 - Consumo faturado de água na faixa (6)
						registrosTipo2e3.append(Util.formatarCampoParaConcatenacao(faixaAgua.getNumConsumo()));
						
						// 6 - Valor faturado de água na faixa (13,2)
						registrosTipo2e3.append(Util.formatarCampoParaConcatenacao( Util.formatarDoubleParaMoedaReal(faixaAgua.getValorFaturado().doubleValue())));
						
						// 7 - Limite inicial de consumo na faixa (6);
						registrosTipo2e3.append(Util.formatarCampoParaConcatenacao(faixaAgua.getNumConsumoInicial()));
						
						// 8 - Limite final de consumo na faixa (6);
						registrosTipo2e3.append(Util.formatarCampoParaConcatenacao(faixaAgua.getNumConsumoFinal()));
						
						// 9 - Valor da tarifa de água na faixa (13,2);
						registrosTipo2e3.append(Util.formatarCampoParaConcatenacao(Util.formatarDoubleParaMoedaReal(faixaAgua.getValorTarifa().doubleValue())));
						
						// 10 - Valor da tarifa de esgoto na faixa (13,2);
						registrosTipo2e3.append(Util.formatarCampoParaConcatenacao( faixaEsgoto.getValorTarifa() != null ? Util.formatarDoubleParaMoedaReal(faixaEsgoto.getValorTarifa().doubleValue()) : Util.formatarDoubleParaMoedaReal(0)));
						
						// 11 - Consumo Faturado de esgoto na faixa (6)
						registrosTipo2e3.append(faixaEsgoto.getNumConsumo() !=null ? Util.formatarCampoParaConcatenacao(faixaEsgoto.getNumConsumo()) : Util.formatarCampoParaConcatenacao(0) );
						
						// 12 - Valor faturado de esgoto na faixa (13,2)
						registrosTipo2e3.append(Util.formatarCampoParaConcatenacao(faixaEsgoto.getValorFaturado() != null ? Util.formatarDoubleParaMoedaReal(faixaEsgoto.getValorFaturado().doubleValue()) : Util.formatarDoubleParaMoedaReal(0)));
						
						//Quebra de linha
						registrosTipo2e3.append("\n");
//						System.out.println(registrosTipo2e3);
					}
				} else if (arrayListContaCategoriaConsumoFaixaEsgoto != null) {

					ContaCategoriaConsumoFaixa faixaEsgoto = new ContaCategoriaConsumoFaixa();

					for (int k = 0; k < arrayListContaCategoriaConsumoFaixaEsgoto.size(); k++) {

						faixaEsgoto = (ContaCategoriaConsumoFaixa) arrayListContaCategoriaConsumoFaixaEsgoto.get(k);

						// 1 - Tipo de registro (1)
						registrosTipo2e3.append(Util.formatarCampoParaConcatenacao("3"));
						
						// 2 - Matrícula (9);
						registrosTipo2e3.append(Util.formatarCampoParaConcatenacao(imovel.getId()));
						
						// 3 - Código da categoria (1)
						registrosTipo2e3.append(Util.formatarCampoParaConcatenacao(registroTipo2.getCodigoCategoria()));
						
						// 4 - Código da subcategoria (3)
						registrosTipo2e3.append(Util.formatarCampoParaConcatenacao(registroTipo2.getCodigoSubcategoria()));
						
						// 5 - Consumo faturado de água na faixa (6)
						registrosTipo2e3.append(Util.formatarCampoParaConcatenacao("0"));
						
						// 6 - Valor faturado de água na faixa (13,2)
						registrosTipo2e3.append(Util.formatarCampoParaConcatenacao(Util.formatarDoubleParaMoedaReal(0)));
						
						// 7 - Limite inicial de consumo na faixa (6);
						registrosTipo2e3.append(Util.formatarCampoParaConcatenacao(faixaEsgoto.getNumConsumoInicial()));

						// 8 - Limite final de consumo na faixa (6);
						registrosTipo2e3.append(Util.formatarCampoParaConcatenacao(faixaEsgoto.getNumConsumoFinal()));
						
						// 9 - Valor da tarifa de água na faixa (13,2);
						registrosTipo2e3.append(Util.formatarCampoParaConcatenacao(Util.formatarDoubleParaMoedaReal(0)));
						
						// 10 - Valor da tarifa de esgoto na faixa (13,2);
						registrosTipo2e3.append(Util.formatarCampoParaConcatenacao( Util.formatarDoubleParaMoedaReal(faixaEsgoto.getValorTarifa().doubleValue())));
						
						// 11 - Consumo Faturado de esgoto na faixa (6)
						registrosTipo2e3.append(faixaEsgoto.getNumConsumo() != null ? Util.formatarCampoParaConcatenacao(faixaEsgoto.getNumConsumo()) : Util.formatarCampoParaConcatenacao("0"));
						
						// 12 - Valor faturado de esgoto na faixa (13,2)
						registrosTipo2e3.append(Util.formatarCampoParaConcatenacao(Util.formatarDoubleParaMoedaReal(faixaEsgoto.getValorFaturado().doubleValue())));
						
						//QUBRA DE LINHA
						registrosTipo2e3.append("\n");
//						System.out.println(registrosTipo2e3);
					}
				}
			}
		}
		
		return registrosTipo2e3.toString();
	}

    private String gerarRegistroTipo4(ImovelConta imovel) {
    	
    	//Tipo de Registro 4 – Dados dos Impostos
    	
    	StringBuilder registrosTipo4 = new StringBuilder();
    	ArrayList<ContaImposto> arrayListContaImposto = new ArrayList<ContaImposto>();
    	arrayListContaImposto = (ArrayList<ContaImposto>) fachada.buscarContaImpostoPorImovelId(imovel.getId());
   
        if (arrayListContaImposto != null) {
            for (int i = 0; i < arrayListContaImposto.size(); i++) {
	            ContaImposto contaImposto = (ContaImposto) arrayListContaImposto.get(i);
	       
	            // 1- Tipo de registro
	            registrosTipo4.append(Util.formatarCampoParaConcatenacao("4"));
	               
	            // 2 - Matricula
	            registrosTipo4.append(Util.formatarCampoParaConcatenacao(imovel.getId()));
	           
	            // 3 - Tipo de Imposto
	            registrosTipo4.append(Util.formatarCampoParaConcatenacao(contaImposto.getTipoImposto()));
	           
	            // 4 - Descrição do imposto
	            registrosTipo4.append(Util.formatarCampoParaConcatenacao(contaImposto.getDescricaoImposto()));
	            
	            // 5 - Percentual da Alíquota
	            registrosTipo4.append(Util.formatarCampoParaConcatenacao(Util.formatarDoubleParaMoedaReal(contaImposto.getPercentualAlicota().doubleValue())));
 
	            // 6 - Valor do Imposto
	            double valorContaSemImposto = fachada.obterValorContaSemImposto(imovel.getId());
	            double valorImposto = valorContaSemImposto * Util.arredondar((contaImposto.getPercentualAlicota().doubleValue() / 100), 7);
	            registrosTipo4.append(Util.formatarCampoParaConcatenacao( Util.formatarDoubleParaMoedaReal(valorImposto)));
	            
	            //Quebra de linha
	            registrosTipo4.append("\n");
//	            System.out.println(registrosTipo4);
            }
        }
        
        return registrosTipo4.toString();
    }
   
    private String gerarRegistroTipo0(int tipoFinalizacao,ImovelConta imovel) {

    	//Tipo de Registro 0 – Dados Finalização Rota 
    	
    	StringBuilder registroTipo0 = new StringBuilder();
       
        // 1 - Tipo de registro
        registroTipo0.append(Util.formatarCampoParaConcatenacao("0"));          
        
        // 2 - Tipo de finalização
        registroTipo0.append(Util.formatarCampoParaConcatenacao( tipoFinalizacao ));
        
        // 3 - Localidade
        registroTipo0.append(Util.formatarCampoParaConcatenacao(imovel.getInscricao().substring(0, 3)));
        
        // 4 - Setor Comercial
        registroTipo0.append(Util.formatarCampoParaConcatenacao(imovel.getInscricao().substring(3, 6)));
        
        // 5 - Rota
        registroTipo0.append(Util.formatarCampoParaConcatenacao(imovel.getCodigoRota() ));
        
        // 6 - Id do Primeiro Imóvel
        registroTipo0.append(Util.formatarCampoParaConcatenacao(imovel.getId() ));
       
        //Quebra de linha
        registroTipo0.append("\n");
        
//        System.out.println(registroTipo0);
        
        return registroTipo0.toString();
    }
    
    public StringBuilder gerarRegistroTipo5(SequencialRotaMarcacao sequencialRotaMarcacao) {

    	//Tipo de Registro 5 – Dados Atualização Sequencial Do Imóvel 
    	
    	StringBuilder registroTipo5 = new StringBuilder();
       
        // 1 - Tipo de registro
        registroTipo5.append(Util.formatarCampoParaConcatenacao("5"));          
        
        // 2 - Matricula
        registroTipo5.append(Util.formatarCampoParaConcatenacao( sequencialRotaMarcacao.getMatricula().getId() ));
                
        // 3 - Sequencial Rota Marcacao
        registroTipo5.append(Util.formatarCampoParaConcatenacao( sequencialRotaMarcacao.getId() ));
        
        // 4 - Ano Mes Referencia
        registroTipo5.append(Util.formatarCampoParaConcatenacao( sequencialRotaMarcacao.getAnoMesReferencia() ));
       
        //Quebra de linha
        registroTipo5.append("\n");
        
//        System.out.println(registroTipo5);
        
        return registroTipo5;
    }

    /**
     * Métodos gera o arquivo de retorno de todos os imóveis não
     * enviados até o momento; passa completo = true se for para gerar com todos imóveis de uma vez
     * @param continua 
     * @param completo
     *            Progress bar que irá mostrar o progresso do processo
     * @return Vetor de objetos com dois objetos 1 - boolean com indicação de
     *         erro na geração 2 - todas os id's dos imóveis que foram gerados
     *         para envio
     */
    public Object[] gerarArquivoRetorno( short tipoArquivoRetorno,int posicao, boolean continua){
   
        Object[] retorno = new Object[3];
        
        ArrayList<Integer> idsImoveisGerados = new ArrayList<Integer>();

        StringBuilder arquivo = new StringBuilder();
        ImovelConta primeiroImovel = null;
	    ArrayList<ImovelConta> arrayListImovelContaCompleto = new ArrayList<ImovelConta>();

	    SistemaParametros sistemaParametros =  SistemaParametros.getInstancia();
        
		if(arrayListImovelConta != null && arrayListImovelConta.size() != 0){
	    	Integer indice = arrayListImovelConta.get(posicao);
	 	   	        	            
	        primeiroImovel = (ImovelConta)fachada.pesquisarPorId(indice, new ImovelConta());
	        
		    arrayListImovelContaCompleto.add(primeiroImovel);	
		}else{
			    		    
		    primeiroImovel = fachada.buscarImovelContaPorPosicao(1);
		}
		
		//Caso o status de finalização estela para offline, gera aqrquivo completo
	    if(sistemaParametros.getIndicadorTransmissaoOffline().equals(ConstantesSistema.SIM) 
	    		&& tipoArquivoRetorno == ArquivoRetorno.ARQUIVO_COMPLETO){
	    	
	    	tipoArquivoRetorno = ArquivoRetorno.ARQUIVO_TODOS_OS_CALCULADOS;
	    }		    
     
    	
    try {
 
        // Criamos o registro tipo 0
        if (( tipoArquivoRetorno == ARQUIVO_COMPLETO && posicao==0 )){               
        	arquivo = arquivo.append( this.gerarRegistroTipo0( ConstantesSistema.INDC_FINALIZAR_ROTEIRO , primeiroImovel) );       
        } else if ( (tipoArquivoRetorno == ARQUIVO_TODOS_OS_CALCULADOS && posicao==0 ) ){ 
        	arquivo = arquivo.append( this.gerarRegistroTipo0( ConstantesSistema.INDC_FINALIZAR_ROTEIRO_TODOS_IMOVEIS , primeiroImovel ) );
        } else if ( (tipoArquivoRetorno == ARQUIVO_INCOMPLETO && posicao==0 )){
        	arquivo = arquivo.append( this.gerarRegistroTipo0( ConstantesSistema.INDC_FINALIZAR_ROTEIRO_INCOMPLETO , primeiroImovel ) );
        }
       
        ImovelConta imovel = new ImovelConta();
        
        if(arrayListImovelContaCompleto != null){
	        for (int i = 0; i < arrayListImovelContaCompleto.size(); i++) {
	
	        	imovel = (ImovelConta)fachada.pesquisarPorId(arrayListImovelContaCompleto.get(i).getId(), new ImovelConta());
	        		        		
	        	/*Caso estejamos enviando apenas os lidos e não enviados até agora
	         verificamos se o imovel deve ser enviado imediatamente. Como imóveis
	         condominio podem ser impressos parcialmente, como no caso de conta
	         menor do que o valor limite ou valor do crédito maior do que o valor
	         da conta, não enviamos imóvel condominio para o arquivo de não enviados
	         e lidos até agora. Esse procedimento só deve ser feito na finalização
	         do roteiro. Caso estejamos enviando o imóvel completo, enviamos todos
	         os calculados e não enviados até agora;*/
	        	// Condição 1
	        	
	        	
	
	        	if ( ( tipoArquivoRetorno == ArquivoRetorno.ARQUIVO_LIDOS_ATE_AGORA &&
	        			fachada.enviarAoCalcular(imovel) && !imovel.isCondominio() ) ||
	        			// Condição 2
	        			(tipoArquivoRetorno == ArquivoRetorno.ARQUIVO_COMPLETO   &&
	        			fachada.enviarAoFinalizar(imovel) ) ||
	        			// Condicao 3
	        			((tipoArquivoRetorno == ArquivoRetorno.ARQUIVO_TODOS_OS_CALCULADOS ||
	        			tipoArquivoRetorno == ArquivoRetorno.ARQUIVO_INCOMPLETO) &&
	        			imovel.getIndcImovelCalculado().equals(ConstantesSistema.SIM))) {           
	
	     
	        		
	        		// Criamos os registros tipo 1
	        		arquivo.append(this.gerarRegistroTipo1(imovel));
	        		// Criamos os registros tipo 2 e 3                
	        		arquivo.append(this.gerarRegistroTipo2e3(imovel));
	        		// Criamos os registros tipo 4                
	        		arquivo.append(this.gerarRegistroTipo4(imovel));
	
	        		idsImoveisGerados.add(imovel.getId());
	        			
	        		imovel = null; 
	
	        	} 
	        	
	        	     
	        }
        }
       
//		//Guarda num StringBuilder cada imóvel que está sendo enviado
//		// para no final gerar o arquivo completo
        montaArquivo.append(arquivo.toString());
        
        String nomeArquivo = this.getCaminhoArquivoRetorno(tipoArquivoRetorno);
        if(retorno[0] == null || !retorno[0].equals(Boolean.TRUE)){
       
	        retorno[0] = Boolean.FALSE;
	        retorno[1] = idsImoveisGerados;
       
        }
        
        if(nomeArquivo != null){
         	retorno[2] = nomeArquivo;
         }
        		
		if(getTotalImoveis()-1 - posicao ==0){
		 	arrayListImovelConta = new ArrayList<Integer>();
		 	
		 	 if ( ( tipoArquivoRetorno == ArquivoRetorno.ARQUIVO_COMPLETO ||
		 			tipoArquivoRetorno == ArquivoRetorno.ARQUIVO_TODOS_OS_CALCULADOS ) &&
		      			SistemaParametros.getInstancia().getIndicadorRotaMarcacaoAtiva().equals(ConstantesSistema.SIM) ) {

		      		 ArrayList<SequencialRotaMarcacao> arrayListSequencialRotaMarcacao  = new ArrayList<SequencialRotaMarcacao>();
		      		 arrayListSequencialRotaMarcacao = Fachada.getInstance().
		      				 pesquisar(new SequencialRotaMarcacao());

		      		 if(arrayListSequencialRotaMarcacao!=null){
		      			 
		      			 for(int i=0; arrayListSequencialRotaMarcacao.size() > i; i++){
		      				 
		      				SequencialRotaMarcacao sequencialRotaMarcacao = new SequencialRotaMarcacao();
		      				sequencialRotaMarcacao = arrayListSequencialRotaMarcacao.get(i);
		      				ArquivoRetorno.montaArquivo.append(gerarRegistroTipo5(sequencialRotaMarcacao).toString());
		      			 }
		          		 
		      		 }
		      		
		      	}
		}
       
   
    } catch (Exception e) {
    	e.printStackTrace();
		Log.e( ConstantesSistema.CATEGORIA , e.getMessage());
        retorno[0] = Boolean.TRUE;
    }

    return retorno;
    
    }
        
    public StringBuffer gerarArquivoRetornoOnLine(ImovelConta imovel) {
	    StringBuffer arquivo = new StringBuffer();
	
	    // Criamos os registros tipo 1
	    arquivo.append(this.gerarRegistroTipo1(imovel));
	    // Criamos os registros tipo 2 e 3
	    arquivo.append(this.gerarRegistroTipo2e3(imovel));
	    // Criamos os registros tipo 4    
	    arquivo.append(this.gerarRegistroTipo4(imovel));
	
	    return arquivo;
    }
    

	public ArrayList<Integer> getArrayListImovelConta() {
		return arrayListImovelConta;
	}

}