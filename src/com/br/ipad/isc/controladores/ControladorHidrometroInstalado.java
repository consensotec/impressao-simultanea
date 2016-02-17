
package com.br.ipad.isc.controladores;

import java.util.Date;

import android.content.Context;
import android.util.Log;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.ConsumoAnteriores;
import com.br.ipad.isc.bean.HidrometroInstalado;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.bean.LeituraAnormalidade;
import com.br.ipad.isc.bean.SistemaParametros;
import com.br.ipad.isc.excecoes.ControladorException;
import com.br.ipad.isc.excecoes.RepositorioException;
import com.br.ipad.isc.repositorios.RepositorioBasico;
import com.br.ipad.isc.repositorios.RepositorioConsumoAnteriores;
import com.br.ipad.isc.repositorios.RepositorioHidrometroInstalado;
import com.br.ipad.isc.util.ConstantesSistema;
import com.br.ipad.isc.util.Util;

public class ControladorHidrometroInstalado  extends ControladorBasico implements IControladorHidrometroInstalado{
	
	private static ControladorHidrometroInstalado instance;
	private RepositorioHidrometroInstalado repositorioHidrometroInstalado;
	protected static Context context;
	
	public void resetarInstancia(){
		instance = null;
	}
	
	protected ControladorHidrometroInstalado(){
		super();
	}
	
	public static ControladorHidrometroInstalado getInstance(){
		if ( instance == null ){
			instance =  new ControladorHidrometroInstalado();
			instance.repositorioHidrometroInstalado = RepositorioHidrometroInstalado.getInstance();
		}
		
		return instance;		
	}

	public void setContext(Context ctx ) {
		context = ctx;		
	}
	
	public HidrometroInstalado buscarHidrometroInstaladoPorImovelTipoMedicao(Integer imovelId, Integer tipoMedicao) throws ControladorException {
		try {
			return repositorioHidrometroInstalado.buscarHidrometroInstaladoPorImovelTipoMedicao(imovelId, tipoMedicao);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}
	
	public HidrometroInstalado buscarLeituraHidrometroTipoMedicao(Integer imovelId, Integer tipoMedicao) throws ControladorException {
		try {
			return repositorioHidrometroInstalado.buscarLeituraHidrometroTipoMedicao(imovelId, tipoMedicao);
		} catch (RepositorioException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}
	
	public String obterTombamento(ImovelConta imovelConta, int tipoMedicao) throws ControladorException {
		
		HidrometroInstalado hidrometro = buscarHidrometroInstaladoPorImovelTipoMedicao
				(imovelConta.getId(), tipoMedicao);
				
		if (hidrometro!=null && hidrometro.getTombamento()!=null && !hidrometro.getTombamento().trim().equals("")){
			return hidrometro.getTombamento();
		}
		
		return null;
	}
	
	
	public boolean validarLeituraMensagem(HidrometroInstalado hidrometroInstalado,ImovelConta imovel,int idMensagem,boolean imprimir, boolean proximo) throws ControladorException {
		ControladorAlertaValidarLeitura controladorAlerta =null;
		boolean leituraInvalida = false;
		String strTipoValidacao = "";
		boolean validado = false;
		SistemaParametros sistemaParametros =  SistemaParametros.getInstancia();		
		
		boolean contaCalculada = false;
		
		//boolean leituraInvalida = true;

		try {
		
			if (hidrometroInstalado.getTipoMedicao() == ConstantesSistema.LIGACAO_AGUA) {
				strTipoValidacao = "água";
			} else {
				strTipoValidacao = "poço";
			}
	
			// Incializamos as leituras e as anormalidades
			Integer intLeitura = null;
			
			LeituraAnormalidade anormalidade = null;
			
			if (hidrometroInstalado.getLeitura() != null) {
				intLeitura = hidrometroInstalado.getLeitura();
			} else {
				intLeitura = null;
			}
			
			if (hidrometroInstalado.getAnormalidade() != null  && hidrometroInstalado.getAnormalidade().intValue() > 0){
				anormalidade = (LeituraAnormalidade) ControladorBasico.getInstance()
						.pesquisarPorId(hidrometroInstalado.getAnormalidade(), new LeituraAnormalidade());						
			}
			
			boolean validaMensagensAnormalidade = true;
			if (hidrometroInstalado.getTipoMedicao() == ConstantesSistema.LIGACAO_POCO) {
				HidrometroInstalado ligacaoAgua = hidrometroInstalado;
				if (ligacaoAgua != null) {
					validaMensagensAnormalidade = false;
				}
			}
			
			// Validamos a leitura do hidrometro
			Integer leituraEsperadaInicialAgua = hidrometroInstalado.getLeituraLimiteInferior();
			Integer leituraEsperadaFinalAgua = hidrometroInstalado.getLeituraLimiteSuperior();
			
			if (hidrometroInstalado.getLeitura() == null) {
				if (hidrometroInstalado.getAnormalidade() == null || hidrometroInstalado.getAnormalidade().intValue() == 0 && idMensagem < 1) {
					controladorAlerta = getControladorAlertaValidarLeitura(hidrometroInstalado,imovel,hidrometroInstalado.getTipoMedicao(),imprimir,proximo); 
					
					validado = controladorAlerta.defineAlerta(ConstantesSistema.ALERTA_MENSAGEM, 
							"Leitura e anormalidade de " + strTipoValidacao + " em branco!",1);
					leituraInvalida = true;
					return validado;					
				}


				if ( hidrometroInstalado.getAnormalidade() != null && hidrometroInstalado.getAnormalidade().intValue() != 0){

					if(anormalidade.getIndicadorAceitaLeitura()!=null && anormalidade.getIndicadorAceitaLeitura().intValue() == LeituraAnormalidade.TER_LEITURA && idMensagem < 2){
						
						hidrometroInstalado.setAnormalidade(null);
						RepositorioBasico.getInstance().atualizar(hidrometroInstalado);
						
						controladorAlerta = getControladorAlertaValidarLeitura(hidrometroInstalado,imovel,hidrometroInstalado.getTipoMedicao(),imprimir,proximo); 
						
						validado = controladorAlerta.defineAlerta(ConstantesSistema.ALERTA_MENSAGEM, 
								"Informe a Leitura da Anormalidade de " + strTipoValidacao + "!",2);
						leituraInvalida = true;
						return validado;
					}
				}

			}
				

			// if (intLeitura != 0) {
			if (hidrometroInstalado.getLeitura() != null ) {                                                                            
				if (hidrometroInstalado.getAnormalidade() != null && hidrometroInstalado.getAnormalidade().intValue() != 0){

					if (anormalidade.getIndicadorAceitaLeitura()!=null &&
							anormalidade.getIndicadorAceitaLeitura().intValue() == LeituraAnormalidade.NAO_TER_LEITURA && idMensagem < 3) {
						
						hidrometroInstalado.setLeitura(null);
						RepositorioBasico.getInstance().atualizar(hidrometroInstalado);
						
						controladorAlerta = getControladorAlertaValidarLeitura(hidrometroInstalado,imovel,hidrometroInstalado.getTipoMedicao(),imprimir,proximo); 
						
						validado = controladorAlerta.defineAlerta(ConstantesSistema.ALERTA_MENSAGEM, 
								"Essa anormalidade de " + strTipoValidacao + " não pode ter leitura!",3);
						
						leituraInvalida = true;
						return validado;
					}
				}
			}

			
			if (intLeitura != null
					&& (intLeitura < leituraEsperadaInicialAgua || intLeitura > leituraEsperadaFinalAgua)) {

				if (!leituraInvalida) {
					
					Integer leituraDigitadaAnterior = hidrometroInstalado.getLeituraAnteriorDigitada();
					
					if (leituraDigitadaAnterior != null
							&& intLeitura.equals(leituraDigitadaAnterior) ) {
						
						hidrometroInstalado.setLeitura(intLeitura);
						RepositorioBasico.getInstance().atualizar(hidrometroInstalado);
					}else{
						if(idMensagem < 4){							
							hidrometroInstalado.setLeitura(null);
							hidrometroInstalado.setLeituraAnteriorDigitada(intLeitura);
							
							RepositorioBasico.getInstance().atualizar(hidrometroInstalado);
							
							controladorAlerta = getControladorAlertaValidarLeitura(hidrometroInstalado,imovel,hidrometroInstalado.getTipoMedicao(),imprimir,proximo); 
							
							validado = controladorAlerta.defineAlerta(ConstantesSistema.ALERTA_MENSAGEM, 
									"Leitura de " + strTipoValidacao + " fora de faixa!",4);
	
							
	
							leituraInvalida = true;
	
							return validado;
						}
					}
				
				}
			
			}

			
			// Valida se a leitura e negativa
			// if ( intLeitura < 0 ) {
			if (intLeitura != null && intLeitura.intValue() < 0 && idMensagem < 5) {
				controladorAlerta = getControladorAlertaValidarLeitura(hidrometroInstalado,imovel,hidrometroInstalado.getTipoMedicao(),imprimir,proximo); 
				
				validado = controladorAlerta.defineAlerta(ConstantesSistema.ALERTA_MENSAGEM, "Leitura de " + strTipoValidacao + " negativa!",5);
				leituraInvalida = true;
				return validado;
			}
			
			
			
			if(hidrometroInstalado.getAnormalidade() != null  && hidrometroInstalado.getAnormalidade().intValue() > 0 && validaMensagensAnormalidade){
				// Caso seja informado um código de anormalidade atual e o
				// imóvel
				// não possua registro de anormalidade no mês anterior
				// (Anormalidade de Leitura igual a nulo do registro tipo 3 com
				// Matricula = ao imóvel que está sendo alterado e Ano/mês
				// ano de referência consumo = ano e mês anterior ao ano mês que
				// está sendo processado), exibir o seguinte alerta: “Este
				// imóvel não apresentou anormalidade informada no mês anterior.
				// Deseja confirmar o código?”;
				Integer mesAnterior = Util.subtrairMesDoAnoMes(Integer.valueOf(imovel.getAnoMesConta()).intValue(), 1); 	
				
					ConsumoAnteriores consumoAnteriores = RepositorioConsumoAnteriores.getInstance().buscarConsumoAnterioresPorImovelAnoMesPorTipoLigacao(
							imovel.getId(),	mesAnterior,hidrometroInstalado.getTipoMedicao());
					
					//[SB00005]1.1.1
					if((consumoAnteriores != null && (consumoAnteriores.getAnormalidadeLeitura()== null || consumoAnteriores.getAnormalidadeLeitura() <=0) && idMensagem < 6  )){
						controladorAlerta = getControladorAlertaValidarLeitura(hidrometroInstalado,imovel,hidrometroInstalado.getTipoMedicao(),imprimir,proximo); 
						validado = controladorAlerta.defineAlerta(ConstantesSistema.ALERTA_PERGUNTA, getContext().getString(R.string.str_hidrometro_alert_mes_anterior),6);
						leituraInvalida = true;
						return validado;
					
					}

					//1.1.2 
					// Caso o imóvel tenha apresentado registro de anormalidade
					// informada no mês anterior e o atual código seja diferente
					// (Anormalidade de Leitura do registro tipo 3 diferente de nulo
					// e Anormalidade de Leitura do registro tipo 3 diferente
					// da anormalidade informada do com Matricula = ao imóvel que
					// está sendo alterado e Ano/mês ano de referência
					// consumo = ano e mês anterior ao ano mês que está sendo
					// processado), exibir o seguinte alerta: “O código informado é
					// diferente do mês anterior. Deseja confirmar o código?”; ''
						
					if(consumoAnteriores != null && consumoAnteriores.getAnormalidadeLeitura() != null && consumoAnteriores.getAnormalidadeLeitura() > 0 && 
							!consumoAnteriores.getAnormalidadeLeitura().equals(hidrometroInstalado.getAnormalidade()) && idMensagem < 7){
						
						controladorAlerta = getControladorAlertaValidarLeitura(hidrometroInstalado,imovel,hidrometroInstalado.getTipoMedicao(),imprimir,proximo); 
						
						validado = controladorAlerta.defineAlerta(ConstantesSistema.ALERTA_PERGUNTA, 
								getContext().getString(R.string.str_hidrometro_alert_mes_anterior_diferente),7);
						leituraInvalida = true;
						return validado;
					
					}
						
					
					//1.1.3	
					if(hidrometroInstalado.getLocalInstalacao().equals("CALCADA")){
						
						//1.1.3.1
						if( anormalidade.getIndicadorCalcadaMensagem().equals(ConstantesSistema.SIM) && idMensagem < 8){
					
							controladorAlerta = getControladorAlertaValidarLeitura(hidrometroInstalado,imovel,hidrometroInstalado.getTipoMedicao(),imprimir,proximo); 
							validado = controladorAlerta.defineAlerta(ConstantesSistema.ALERTA_PERGUNTA, 
									getContext().getString(R.string.str_hidrometro_alert_hidrometro_calcada),8);
							leituraInvalida = true;
							return validado;
								
						}
					}
					
						
					//1.1.3.4
					int resultadoHidrometroLeitura = Util.compararData(hidrometroInstalado.getDataInstalacaoHidrometro(), hidrometroInstalado.getDataLeituraAnterior());
					int resultadoHidrometroDataAtual = Util.compararData(hidrometroInstalado.getDataInstalacaoHidrometro(), new Date());
					
					if (resultadoHidrometroLeitura == 1 && resultadoHidrometroDataAtual == -1) {
					
						if(	anormalidade.getIndicadorHidrometroMensagem().equals(ConstantesSistema.SIM) && idMensagem < 9){
							
							controladorAlerta = getControladorAlertaValidarLeitura(hidrometroInstalado,imovel,hidrometroInstalado.getTipoMedicao(),imprimir,proximo); 
							
							validado = controladorAlerta.defineAlerta(ConstantesSistema.ALERTA_PERGUNTA, 
									getContext().getString(R.string.str_hidrometro_alert_hidrometro_substituido),9);
							leituraInvalida = true;
							return validado;
						}
					}

					if(anormalidade != null && anormalidade.getIndicadorUso().intValue() != ConstantesSistema.SIM.intValue() &&
							idMensagem < 10){
						
						controladorAlerta = getControladorAlertaValidarLeitura(hidrometroInstalado,imovel,hidrometroInstalado.getTipoMedicao(),imprimir,proximo); 
						
						validado = controladorAlerta.defineAlerta(ConstantesSistema.ALERTA_MENSAGEM, 
								getContext().getString(R.string.str_hidrometro_alert_anormalidade_inativa),10);
						leituraInvalida = true;
						return validado;
					}
					
					
					
				}
			
				// Retornoa mensagem de agua e poço iguais
				if (hidrometroInstalado.getTipoMedicao() == ConstantesSistema.LIGACAO_AGUA && idMensagem < 11){
					
					HidrometroInstalado hidrometroPoco = getControladorHidrometroInstalado().
							buscarHidrometroInstaladoPorImovelTipoMedicao(imovel.getId(), ConstantesSistema.LIGACAO_POCO);
					
					if(hidrometroPoco != null && hidrometroPoco.getLeitura() != null &&
							hidrometroInstalado.getLeitura()!= null &&
							hidrometroInstalado.getLeitura().intValue() == hidrometroPoco.getLeitura().intValue()){
						
						controladorAlerta = getControladorAlertaValidarLeitura(hidrometroInstalado,imovel,hidrometroInstalado.getTipoMedicao(),imprimir,proximo); 
						
						validado = controladorAlerta.defineAlerta(ConstantesSistema.ALERTA_PERGUNTA, 
								getContext().getString(R.string.str_hidrometro_alert_agua_igual_poco),11);
						leituraInvalida = true;
						return validado;
						
					}
					
				}
				
				
				if(sistemaParametros.getCodigoEmpresaFebraban().equals(ConstantesSistema.CODIGO_FEBRABAN_CAERN) && 
						(hidrometroInstalado.getLeitura()!=null && hidrometroInstalado.getLeitura().equals(hidrometroInstalado.getLeituraAnteriorFaturamento()) ||
						 hidrometroInstalado.getLeitura()!=null && hidrometroInstalado.getLeituraAnteriorInformada() != null && hidrometroInstalado.getLeitura().equals(hidrometroInstalado.getLeituraAnteriorInformada())) && 
						idMensagem < 12){
					if(((imovel.getConsumoMinEsgoto() == ConstantesSistema.VOLUME_MINIMO_ESGOTO) || 
							(imovel.getConsumoMinEsgoto() == null)) &&
								((imovel.getTipoPoco() == null) || 
										(imovel.getTipoPoco() == 0))){
						controladorAlerta = getControladorAlertaValidarLeitura(hidrometroInstalado,imovel,hidrometroInstalado.getTipoMedicao(),imprimir,proximo); 
						validado = controladorAlerta.defineAlerta(ConstantesSistema.ALERTA_PERGUNTA, getContext().getString(R.string.str_confirmar_ocupacao_imovel),12);
						leituraInvalida = true;
						return validado;
					}
				}
				

				
				if(!leituraInvalida){
					if (hidrometroInstalado.getTipoMedicao() == ConstantesSistema.LIGACAO_AGUA){
						HidrometroInstalado hidrometroPoco = getControladorHidrometroInstalado().
								buscarHidrometroInstaladoPorImovelTipoMedicao(imovel.getId(), ConstantesSistema.LIGACAO_POCO);
						if(hidrometroPoco != null){
							contaCalculada = getControladorHidrometroInstalado().validarLeituraMensagem(hidrometroPoco, imovel,0,imprimir,proximo);
						}else{
							contaCalculada = getControladorConta().calcularConta(imovel,imprimir,proximo);
						}
					}else{
						contaCalculada = getControladorConta().calcularConta(imovel,imprimir,proximo);
						
					}
				}else{
					
					imovel.setIndcImovelCalculado(ConstantesSistema.NAO);
					imovel.setIndcImovelEnviado(ConstantesSistema.NAO);
					ControladorBasico.getInstance().atualizar(imovel);
					
				}
				
			
			} catch (RepositorioException e) {
				e.printStackTrace();
				Log.e(ConstantesSistema.CATEGORIA , e.getMessage());
				throw new ControladorException(context.getResources().getString(
						R.string.db_erro));
			}
			
		return contaCalculada;		
		
	}
	
}