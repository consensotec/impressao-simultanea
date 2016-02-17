
package com.br.ipad.isc.controladores;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.ContaDebito;
import com.br.ipad.isc.bean.HidrometroInstalado;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.bean.LeituraAnormalidade;
import com.br.ipad.isc.bean.SistemaParametros;
import com.br.ipad.isc.excecoes.ConexaoImpressoraException;
import com.br.ipad.isc.excecoes.ControladorException;
import com.br.ipad.isc.excecoes.StatusImpressoraException;
import com.br.ipad.isc.excecoes.ZebraException;
import com.br.ipad.isc.impressao.ExtratoMacroCaern;
import com.br.ipad.isc.impressao.ExtratoMacroCompesa;
import com.br.ipad.isc.impressao.ImpressaoContaCaer;
import com.br.ipad.isc.impressao.ImpressaoContaCaern;
import com.br.ipad.isc.impressao.ImpressaoContaCompesaNovo;
import com.br.ipad.isc.impressao.NotificacaoDebitoCaern;
import com.br.ipad.isc.impressao.NotificacaoDebitoCompesa;
import com.br.ipad.isc.impressao.ZebraUtils;
import com.br.ipad.isc.util.ConstantesSistema;


public class ControladorImpressao extends ControladorBasico implements IControladorImpressao{
	
	private static ControladorImpressao instance;
	private StringBuilder conta;	
	//Constantes - ID'S das mensagem
	protected final int ID_MSG_IMOVEL_NAO_CALCULADO = 1;
	protected final int ID_MSG_IMOVEL_NAO_EMITIDO = 2;
	protected final int ID_MSG_VALOR_MENOR_PERMITIDO = 3;
	protected final int ID_MSG_VALOR_MAIOR_PERMITIDO = 4;
	protected final int ID_MSG_ANORMALIDADE_IMPRESSAO_AGUA = 5;
	protected final int ID_MSG_ANORMALIDADE_IMPRESSAO_ESGOTO = 6;
	
	public void resetarInstancia(){
		instance = null;
	}
	
	protected ControladorImpressao(){
		super();
	}
	
	public static ControladorImpressao getInstance(){
		if ( instance == null ){
			instance =  new ControladorImpressao();
		}		
		return instance;		
	}

	public boolean verificarExistenciaImpressora(ImovelConta imovel){
		if(!ConstantesSistema.SIMULADOR){
			return ZebraUtils.getInstance(context).verificaExistenciaImpressoraConfigigurada(context,imovel);
		}
		
		return true;
	}

	/**
	 * Verifica existencia de impressora configurada e
	 * retorna um booleano
	 * 
	 * @author Amelia Pessoa
	 * @return boolean
	 */
	public boolean verificaExistenciaImpressoraConfigurada(){
		if(!ConstantesSistema.SIMULADOR){
			return ZebraUtils.getInstance(context).verificaExistenciaImpressoraConfigurada();
		}
		
		return true;
	}
	
	public boolean verificarImpressaoConta(ImovelConta imovel,Context context,int idMensagem, boolean mostrarMensagens) throws ControladorException{
		
		boolean contaImpressa = false;
		boolean imovelNaoPermiteImpressao = false;
		
		ImovelConta imovelBase = (ImovelConta) ControladorBasico.getInstance().
				pesquisarPorId(imovel.getId(), imovel);				
		imovelNaoPermiteImpressao = imovelNaoPermiteImpressao(imovelBase,mostrarMensagens, idMensagem);
				
		if(!imovelNaoPermiteImpressao){
			
			boolean existeImpressoraConfigurada = verificarExistenciaImpressora(imovel);
			
			if(existeImpressoraConfigurada){
			
				ControladorAlertaValidarConexaoImpressora controladorAlertaValidarConexaoImpressora = null;
					
				try {
						
					contaImpressa = imprimirConta(imovelBase,getContext());
						
				} catch (ConexaoImpressoraException e) {
					
					controladorAlertaValidarConexaoImpressora = getControladorAlertaValidarConexaoImpressora(imovelBase);
					contaImpressa = controladorAlertaValidarConexaoImpressora.defineAlerta(ConstantesSistema.ALERTA_MENSAGEM, context.getString(R.string.falha_conecao), 0);
					Log.v(ConstantesSistema.CATEGORIA,e.getMessage());
					e.getStackTrace();
					contaImpressa = false;

				} 
				catch (StatusImpressoraException e) {
					controladorAlertaValidarConexaoImpressora = getControladorAlertaValidarConexaoImpressora(imovel);
					contaImpressa = controladorAlertaValidarConexaoImpressora.defineAlerta(ConstantesSistema.ALERTA_PERGUNTA, context.getString(R.string.str_erro_impressora, e.getMessage()) ,0);
				    Log.v(ConstantesSistema.CATEGORIA,e.getMessage());
					e.getStackTrace();
				}
				catch (ZebraException e) {
					controladorAlertaValidarConexaoImpressora = getControladorAlertaValidarConexaoImpressora(imovelBase);
					contaImpressa = controladorAlertaValidarConexaoImpressora.defineAlerta(ConstantesSistema.ALERTA_MENSAGEM, context.getString(R.string.falha_conecao), 0);
					Log.v(ConstantesSistema.CATEGORIA,e.getMessage());
					e.getStackTrace();
					contaImpressa = false;
				}
			}
		}

		return contaImpressa;
	}
	
	private boolean imovelNaoPermiteImpressao(ImovelConta imovelBase, boolean mostrarMensagens, int idMensagem) throws ControladorException{
		
		boolean imovelNaoPermiteImpressao = false;
		ControladorAlertaValidarImpressao controladorAlertaValidarImpressao = null;
		double valorTotal = ControladorConta.getInstance().obterValorConta(imovelBase.getId());
		
		HidrometroInstalado hidrometroInstaladoAgua = new HidrometroInstalado();
		HidrometroInstalado hidrometroInstaladoPoco = new HidrometroInstalado();
		
		hidrometroInstaladoAgua = ControladorHidrometroInstalado.getInstance().
				buscarHidrometroInstaladoPorImovelTipoMedicao(imovelBase.getId(), ConstantesSistema.LIGACAO_AGUA);

		hidrometroInstaladoPoco = ControladorHidrometroInstalado.getInstance().
				buscarHidrometroInstaladoPorImovelTipoMedicao(imovelBase.getId(), ConstantesSistema.LIGACAO_POCO);
		
		if(imovelBase.getIndcImovelCalculado() != null && imovelBase.getIndcImovelCalculado().equals(ConstantesSistema.NAO) && idMensagem < ID_MSG_IMOVEL_NAO_CALCULADO ){
			imovelNaoPermiteImpressao = true;
			
			if (mostrarMensagens){
				controladorAlertaValidarImpressao = getControladorAlertaValidarImpressao(imovelBase); 
				controladorAlertaValidarImpressao.defineAlerta(ConstantesSistema.ALERTA_MENSAGEM, getContext().getString(R.string.str_imovel_nao_calculado),ID_MSG_IMOVEL_NAO_CALCULADO);
			}
		}

		if (imovelBase.getIndcEmissaoConta().equals(ConstantesSistema.NAO) && idMensagem < ID_MSG_IMOVEL_NAO_EMITIDO) {

			imovelNaoPermiteImpressao = true;
			
			if (mostrarMensagens){
				controladorAlertaValidarImpressao = getControladorAlertaValidarImpressao(imovelBase); 
				controladorAlertaValidarImpressao.defineAlerta(ConstantesSistema.ALERTA_MENSAGEM, getContext().getString(R.string.str_imovel_nao_emitido),ID_MSG_IMOVEL_NAO_EMITIDO);
			}
			

		}else if (valorTotal<= SistemaParametros.getInstancia().getValorMinimEmissaoConta().doubleValue() ) {

			double valorCredito = ControladorCreditoRealizado.getInstance().obterValorCreditoTotal(imovelBase.getId());
			
			if( valorCredito == 0 && idMensagem < ID_MSG_VALOR_MENOR_PERMITIDO){
			
				
				imovelNaoPermiteImpressao = true;
				
				if (mostrarMensagens){
					controladorAlertaValidarImpressao = getControladorAlertaValidarImpressao(imovelBase); 
					controladorAlertaValidarImpressao.defineAlerta(ConstantesSistema.ALERTA_MENSAGEM, getContext().getString(R.string.str_valor_menor_permitido),ID_MSG_VALOR_MENOR_PERMITIDO);
				}
			}
		}

		else if(SistemaParametros.getInstancia().getCodigoEmpresaFebraban().equals(ConstantesSistema.CODIGO_FEBRABAN_COMPESA) && 
				valorTotal >= ConstantesSistema.VALOR_LIMITE_CONTA && 
				( imovelBase.getCodigoAgencia() == null || imovelBase.getCodigoAgencia().equals("") ) && 
				idMensagem < ID_MSG_VALOR_MAIOR_PERMITIDO){

			imovelNaoPermiteImpressao = true;
			
			if (mostrarMensagens){
				controladorAlertaValidarImpressao = getControladorAlertaValidarImpressao(imovelBase); 
				controladorAlertaValidarImpressao.defineAlerta(ConstantesSistema.ALERTA_MENSAGEM, getContext().getString(R.string.str_valor_maior_permitido),ID_MSG_VALOR_MAIOR_PERMITIDO);

			}

		} else if (hidrometroInstaladoAgua != null){

			Integer idAnormalidadeHidrometro = hidrometroInstaladoAgua.getAnormalidade();

			if(idAnormalidadeHidrometro != null){

				LeituraAnormalidade leituraAnormalidade = (LeituraAnormalidade) ControladorBasico.getInstance()
						.pesquisarPorId(idAnormalidadeHidrometro, new LeituraAnormalidade());
				
				if(leituraAnormalidade.getIndicadorNaoImpressaoConta().equals(ConstantesSistema.SIM) && idMensagem < ID_MSG_ANORMALIDADE_IMPRESSAO_AGUA){
					imovelNaoPermiteImpressao = true;
					
					if (mostrarMensagens){
						controladorAlertaValidarImpressao = getControladorAlertaValidarImpressao(imovelBase); 
						controladorAlertaValidarImpressao.defineAlerta(ConstantesSistema.ALERTA_MENSAGEM, getContext().getString(R.string.str_anormalidade_nao_impressao),ID_MSG_ANORMALIDADE_IMPRESSAO_AGUA);
					}
				}
			}
		} else if (hidrometroInstaladoPoco != null){

			Integer idAnormalidadeHidrometro = hidrometroInstaladoPoco.getAnormalidade();

			if(idAnormalidadeHidrometro != null){

				LeituraAnormalidade leituraAnormalidade = (LeituraAnormalidade) ControladorBasico.getInstance()
						.pesquisarPorId(idAnormalidadeHidrometro, new LeituraAnormalidade());
				
				if(leituraAnormalidade.getIndicadorNaoImpressaoConta().equals(ConstantesSistema.SIM) 
						&& idMensagem < ID_MSG_ANORMALIDADE_IMPRESSAO_ESGOTO){
					
					imovelNaoPermiteImpressao = true;
					
					if (mostrarMensagens){
						controladorAlertaValidarImpressao = getControladorAlertaValidarImpressao(imovelBase); 
						controladorAlertaValidarImpressao.defineAlerta(ConstantesSistema.ALERTA_MENSAGEM, getContext().getString(R.string.str_anormalidade_nao_impressao),ID_MSG_ANORMALIDADE_IMPRESSAO_ESGOTO);
					
				
					}
				}
			}
		}
		
		if (!imovelNaoPermiteImpressao) {
			imovelBase.setIndcNaoPermiteImpressao(ConstantesSistema.NAO);
			ControladorBasico.getInstance().atualizar(imovelBase);
		}else{
			imovelBase.setIndcNaoPermiteImpressao(ConstantesSistema.SIM);
			ControladorBasico.getInstance().atualizar(imovelBase);
		}
		
		return imovelNaoPermiteImpressao;
	}
	
	protected boolean imprimirConta(ImovelConta imovel, Context context) throws ControladorException, ZebraException{
		
		boolean retorno = false;
		
		SistemaParametros sistemaParametros =  SistemaParametros.getInstancia();
			
		if(ConstantesSistema.SIMULADOR){		
			if(sistemaParametros.getCodigoEmpresaFebraban().equals(ConstantesSistema.CODIGO_FEBRABAN_COMPESA)){
				ImpressaoContaCompesaNovo impressaoContaCompesaNovo = ImpressaoContaCompesaNovo.getInstancia(imovel);
				conta = impressaoContaCompesaNovo.imprimirConta();
			}else if (sistemaParametros.getCodigoEmpresaFebraban().equals(ConstantesSistema.CODIGO_FEBRABAN_CAERN)){
				ImpressaoContaCaern impressaoContaCaern = ImpressaoContaCaern.getInstancia(imovel);
				conta = impressaoContaCaern.imprimirConta();
			}else if (sistemaParametros.getCodigoEmpresaFebraban().equals(ConstantesSistema.CODIGO_FEBRABAN_CAER)){
				ImpressaoContaCaer impressaoContaCaer = ImpressaoContaCaer.getInstancia(imovel);
				conta = impressaoContaCaer.imprimirConta();
			}
			
			Log.v("STRING DE IMPRESSAO DA CONTA", conta.toString());
			
			int qntVezesImpressaoConta = imovel.getQntVezesImpressaoConta();
			imovel.setQntVezesImpressaoConta(qntVezesImpressaoConta+1);
			imovel.setIndcImovelImpresso(ConstantesSistema.SIM);
			ControladorBasico.getInstance().atualizar(imovel);
			retorno = true;

			// incrementa o valor do imovel selecionado em Sistema Parametros
			getControladorSistemaParametros().atualizarIdImovelSelecionadoSistemaParametros(imovel.getPosicao());
						
			//NOTIFICACAO DE DEBITO
			ArrayList<ContaDebito> arrayListContaDebito = new ArrayList<ContaDebito>();
			arrayListContaDebito = ControladorContaDebito.getInstance().buscarContasDebitosPorIdImovel(imovel.getId());
			
			if(arrayListContaDebito != null){
				ControladorImpressao.getInstance().imprimirNotificacaoDebito(imovel,context);
			}
			
			
		}else{
					
			if(sistemaParametros.getCodigoEmpresaFebraban().equals(ConstantesSistema.CODIGO_FEBRABAN_COMPESA)){
				ImpressaoContaCompesaNovo impressaoContaCompesaNovo = ImpressaoContaCompesaNovo.getInstancia(imovel);
				conta = impressaoContaCompesaNovo.imprimirConta();
			}else if (sistemaParametros.getCodigoEmpresaFebraban().equals(ConstantesSistema.CODIGO_FEBRABAN_CAERN)){
				ImpressaoContaCaern impressaoContaCaern = ImpressaoContaCaern.getInstancia(imovel);
				conta = impressaoContaCaern.imprimirConta();
			}else if (sistemaParametros.getCodigoEmpresaFebraban().equals(ConstantesSistema.CODIGO_FEBRABAN_CAER)){
				ImpressaoContaCaer impressaoContaCaer = ImpressaoContaCaer.getInstancia(imovel);
				conta = impressaoContaCaer.imprimirConta();
			}
			
			boolean contaImpressa = enviarContaImpressora();

			if (contaImpressa){
				
				atualizaDadosImpressaoImovel(imovel);
				
				/*
				 * Emitir conta comunicado 
				 * para CAER
				 * ID13345
				 * RM12029
				 * UC0749 Emitir Conta no Dispositivo Movel
				 * Verifica se é CAER e se o imovelConta possui
				 * conta comunicado
				 */
				if(sistemaParametros.getCodigoEmpresaFebraban().equals(ConstantesSistema.CODIGO_FEBRABAN_CAER)
						&& imovel.getContaComunicado()!=null && imovel.getContaComunicado().getId()!=null){
					ImpressaoContaCaer impressaoContaCaer = ImpressaoContaCaer.getInstancia(imovel);
					StringBuilder contaComunicadoCaer =  impressaoContaCaer.imprimirContaComunicado();
					enviarContaComunicadoCaernImpressora(contaComunicadoCaer);
				}
		
				retorno = true;
			}
		}
			
		return retorno;
	}
	
	
	public boolean enviarContaImpressora() throws ZebraException {
		
		boolean retorno = false;
		retorno = ZebraUtils.getInstance(context).imprimir(conta);
		
		return retorno;
	}
	
	private boolean imprimirNotificacaoDebito(ImovelConta imovel, Context context) throws ControladorException{
		
		boolean retorno = false;
		
		SistemaParametros sistemaParametros =  SistemaParametros.getInstancia();

		StringBuilder notificacaoDeDebito = null;
		
		if(sistemaParametros.getCodigoEmpresaFebraban().equals(ConstantesSistema.CODIGO_FEBRABAN_COMPESA)){
			NotificacaoDebitoCompesa notificacaoDebito = NotificacaoDebitoCompesa.getInstancia();
			notificacaoDeDebito = notificacaoDebito.imprimirNotificacaoDebito(imovel);
		}else if (sistemaParametros.getCodigoEmpresaFebraban().equals(ConstantesSistema.CODIGO_FEBRABAN_CAERN)){
			NotificacaoDebitoCaern notificacao = NotificacaoDebitoCaern.getInstancia();
			notificacaoDeDebito = notificacao.imprimirNotificacaoDebito(imovel);
		}else if (sistemaParametros.getCodigoEmpresaFebraban().equals(ConstantesSistema.CODIGO_FEBRABAN_CAER)){
			//NotificacaoDebitoCaer notificacao = NotificacaoDebitoCaer.getInstancia();
			//notificacaoDeDebito = notificacao.imprimirNotificacaoDebito(imovel);
		}
		

		if(ConstantesSistema.SIMULADOR){
			
			Log.v("STRING DE IMPRESSAO DE NOTIFICAO DE DEBITO", notificacaoDeDebito.toString());
			retorno = true;
			
		}else{
			
			try {
				if ( notificacaoDeDebito != null ){
					ZebraUtils.getInstance(context).imprimir(notificacaoDeDebito);
				}
				retorno = true;
			} catch (ZebraException e) {
				Log.v(ConstantesSistema.CATEGORIA,e.getMessage());
				e.getStackTrace();
			}
		}
	
		return retorno;
	}
	
	public void atualizaDadosImpressaoImovel (ImovelConta imovel) {
		try{
			int qntVezesImpressaoConta = imovel.getQntVezesImpressaoConta();
			imovel.setQntVezesImpressaoConta(qntVezesImpressaoConta+1);
			imovel.setIndcImovelImpresso(ConstantesSistema.SIM);
			
			//So atualiza indicador de nao enviado se nao for 2 via condominio
			Integer idMacro = imovel.getMatriculaCondominio();
         	if(!(idMacro!=null && getControladorImovelConta().verificarRateioCondominio(idMacro))){
				imovel.setIndcImovelEnviado(ConstantesSistema.NAO);
			}
			
			ControladorBasico.getInstance().atualizar(imovel);
			//NOTIFICACAO DE DEBITO
			ArrayList<ContaDebito> arrayListContaDebito = new ArrayList<ContaDebito>();
			arrayListContaDebito = ControladorContaDebito.getInstance().buscarContasDebitosPorIdImovel(imovel.getId());
			//Imprime a notificação de débito
			if(arrayListContaDebito != null){
				ControladorImpressao.getInstance().imprimirNotificacaoDebito(imovel, context);
				
			}
		}catch (ControladorException ex){
			//tratar
		}
	}
	

	public boolean imprimirExtratoMacro (Context context, ImovelConta imovelMacro) throws ControladorException{
		
		boolean retorno = false;
		
		StringBuilder extratoMacro = new StringBuilder();
		
		if(SistemaParametros.getInstancia().getCodigoEmpresaFebraban().equals(ConstantesSistema.CODIGO_FEBRABAN_COMPESA)){
			extratoMacro = ExtratoMacroCompesa.getInstancia(imovelMacro).obterStringExtratoMacroCompesa();
		}else if (SistemaParametros.getInstancia().getCodigoEmpresaFebraban().equals(ConstantesSistema.CODIGO_FEBRABAN_CAERN)){
			extratoMacro = ExtratoMacroCaern.getInstancia(imovelMacro).obterStringExtratoMacroCaern();
		}
		
		if(ConstantesSistema.SIMULADOR){
			Log.v("STRING DE IMPRESSAO DE EXTRATO MACRO", extratoMacro.toString());
			retorno = true;
		}else{
			try {
				ZebraUtils.getInstance(context).imprimir(extratoMacro);

				retorno = true;
			} catch (ZebraException e) {
				Log.v(ConstantesSistema.CATEGORIA,e.getMessage());
				e.getStackTrace();
			}
		}
		
		return retorno;
	}
	
	/**
	 * Método responsável por<br>
	 * enviar o buffer da conta<br>
	 * comunicado para impressora
	 * ID13345
	 * RM12029
	 * UC0749 Emitir Conta no Dispositivo Movel
	 * @author Jonathan Marcos
	 * @since 09/02/2015
	 * @param contaComunicadoCaern
	 * @return boolean
	 * @throws ZebraException
	 */
	public boolean enviarContaComunicadoCaernImpressora(StringBuilder contaComunicadoCaern) throws ZebraException {
		boolean retorno = false;
		retorno = ZebraUtils.getInstance(context).
				imprimir(contaComunicadoCaern);
		return retorno;
	}
}