package com.br.ipad.isc.controladores;

import android.content.Intent;
import android.util.Log;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.excecoes.ConexaoImpressoraException;
import com.br.ipad.isc.excecoes.ControladorException;
import com.br.ipad.isc.excecoes.StatusImpressoraException;
import com.br.ipad.isc.excecoes.ZebraException;
import com.br.ipad.isc.gui.RateioActivity;
import com.br.ipad.isc.gui.TabsActivity;
import com.br.ipad.isc.impressao.ZebraUtils;
import com.br.ipad.isc.util.ConstantesSistema;
import com.br.ipad.isc.util.Util;



public class ControladorAlertaValidarConexaoImpressora extends ControladorAlertaBasico implements IControladorAlertaValidarConexaoImpressora {

	ZebraUtils zebra = new ZebraUtils(context);	    	
	private ImovelConta imovel;
		
	public ControladorAlertaValidarConexaoImpressora() {
		super();
	}
	
	public ControladorAlertaValidarConexaoImpressora(ImovelConta imovel) {
		super();
		this.imovel = imovel;
	}

	@Override
	public void alertaPerguntaSim() {
		
		//Se for imóvel condominio chama o rateio novamente
		if(imovel.isCondominio()){
			
			boolean permiteImprimir2Via = false;
			
			Integer idMacro = imovel.getMatriculaCondominio();
			
			//Se for o ultimo Micro
			if(idMacro==null){
				idMacro = imovel.getId();
			}
			
			try {
				permiteImprimir2Via = getControladorImovelConta().verificarRateioCondominio(idMacro);
			} catch (ControladorException e) {
				e.printStackTrace();
			}
			
			if(permiteImprimir2Via){
				
				imprimirConta(false);
				
			}else{
				
				//Chama RateioActivity para iniciar o rateio
				Intent i = new Intent(getContext(), RateioActivity.class);
				i.putExtra("macro", imovel);
				getContext().startActivity(i);
			}

			
		} else {
			imprimirConta(true);
		}
	}

	@Override
	public void alertaPerguntaNao() {		
		direcionarUltimoMicro();
	}

	@Override
	public void alertaMensagem() {
		direcionarUltimoMicro();
	}
	
	private void direcionarUltimoMicro(){
		//Se for imovel condominio voltar para a mesma tela (ultimo imovel micro)
		ImovelConta ultimoImovel = null;
		try {
			Integer id = getControladorImovelConta().obterIdUltimoImovelMicro(imovel.getId());
			ultimoImovel = (ImovelConta) ControladorBasico.getInstance().
					pesquisarPorId(id, new ImovelConta()); 
					
			if(imovel.isCondominio()){
				
				if(ultimoImovel==null){
					ultimoImovel = ControladorBasico.getInstance().
							pesquisarPorId(imovel.getId(), new ImovelConta()); 
				}
				
				Intent it = new Intent(getContext(), TabsActivity.class);
				it.putExtra("imovel", ultimoImovel);
				getContext().startActivity(it);
			}
		} catch (ControladorException e) {
			e.printStackTrace();
			Log.v(ConstantesSistema.CATEGORIA,e.getMessage());
		}
	}
	
	public void imprimirConta(boolean enviar){
		
		ControladorAlertaValidarConexaoImpressora controladorAlertaValidarConexaoImpressora = null;
		boolean contaImpressa = false;
		
		try {
			
			contaImpressa = ControladorImpressao.getInstance().enviarContaImpressora();
			
		} catch (ConexaoImpressoraException e) {
			
			controladorAlertaValidarConexaoImpressora = getControladorAlertaValidarConexaoImpressora(imovel);
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
			controladorAlertaValidarConexaoImpressora = getControladorAlertaValidarConexaoImpressora(imovel);
			contaImpressa = controladorAlertaValidarConexaoImpressora.defineAlerta(ConstantesSistema.ALERTA_MENSAGEM, context.getString(R.string.falha_conecao), 0);
			Log.v(ConstantesSistema.CATEGORIA,e.getMessage());
			e.getStackTrace();
			contaImpressa = false;
		}
		
		if (contaImpressa){
			
					
			if(enviar){
				ControladorImpressao.getInstance().atualizaDadosImpressaoImovel(imovel);
				Util.chamaProximo(getContext(), imovel);
			}else{
				Util.chamaProximoSemEnviar(getContext(), imovel);
			}
			
		}
		
	}
}