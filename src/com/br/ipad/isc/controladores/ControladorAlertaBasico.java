package com.br.ipad.isc.controladores;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.ConsumoHistorico;
import com.br.ipad.isc.bean.HidrometroInstalado;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.excecoes.ControladorException;
import com.br.ipad.isc.fachada.Fachada;
import com.br.ipad.isc.gui.ContaActivity;
import com.br.ipad.isc.gui.HidrometroAguaActivity;
import com.br.ipad.isc.gui.HidrometroEsgotoActivity;
import com.br.ipad.isc.gui.RateioActivity;
import com.br.ipad.isc.gui.TabsActivity;
import com.br.ipad.isc.util.ConstantesSistema;


public abstract class ControladorAlertaBasico extends ControladorBasico {
	
	private int tipo;
	private String msg;
	protected int idMensagem;
	private boolean resposta;
	//private boolean imprimir;
			     
	protected ControladorAlertaBasico(){
		super();
	}
	 
	public boolean defineAlerta( int tipo, String msg, int idMensagem){

		this.idMensagem = idMensagem;
    	if(tipo == ConstantesSistema.ALERTA_MENSAGEM){ 
    		new AlertDialog.Builder(getContext()) 
			.setMessage( msg )
			.setCancelable(false)
			.setNeutralButton(getContext().getString(android.R.string.ok), 
				new DialogInterface.OnClickListener() {
					public void onClick(
						DialogInterface dialog,
						int which) {
						
						alertaMensagem();
						
					}
			}).show();
    	}
    	
    	if(tipo == ConstantesSistema.ALERTA_PERGUNTA){

    		new AlertDialog.Builder(getContext())
			.setMessage( msg )
			.setCancelable(false)
			.setPositiveButton(getContext().getString(R.string.str_sim), 
				new DialogInterface.OnClickListener() {
					public void onClick(
						DialogInterface dialog,
						int which) {
						
						alertaPerguntaSim();
						
					}
			})
			.setNegativeButton(getContext().getString(R.string.str_nao), 
				new DialogInterface.OnClickListener() {
					public void onClick(
						DialogInterface dialog,
						int which) {
						
						alertaPerguntaNao();				
					}
			})
			.show();
    	}
		return resposta;
    	
    }
	public abstract void alertaPerguntaSim();
	
	public abstract void alertaPerguntaNao();
	
	public abstract void alertaMensagem();

	public int getIdMensagem() {
		return idMensagem;
	}

	public void setIdMensagem(int idMensagem) {
		this.idMensagem = idMensagem;
	}

	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
    
	public void apagaDados(ImovelConta imovel, int tipoMedicao, int campo){

		try {
			imovel = Fachada.getInstance().pesquisarPorId(imovel.getId(), imovel) ;
			imovel.setIndcImovelCalculado(ConstantesSistema.NAO);
			
			if(imovel.isCondominio()){
				imovel.setIndcRateioRealizado(ConstantesSistema.NAO);
				imovel.setIndcImovelImpresso(ConstantesSistema.NAO);
				
				Integer idMacro = null;
				
				if(imovel.getMatriculaCondominio()!=null){
					idMacro = imovel.getMatriculaCondominio();
				}else{
					idMacro = imovel.getId();
				}
				
				getControladorImovelConta().atualizarIndicadorContinuaImpressao(idMacro, ConstantesSistema.NAO);
			}
			
			ControladorBasico.getInstance().atualizar(imovel);
			
			if(tipoMedicao == ConstantesSistema.LIGACAO_AGUA){
				ConsumoHistorico consumoHistoricoBaseAgua = getControladorConsumoHistorico().buscarConsumoHistoricoPorImovelIdLigacaoTipo(imovel.getId(),ConstantesSistema.LIGACAO_AGUA);
				if(consumoHistoricoBaseAgua != null && !consumoHistoricoBaseAgua.equals("")){
					ControladorBasico.getInstance().remover(consumoHistoricoBaseAgua);
				}
				
				HidrometroInstalado hidrometroInstaladoAgua = new HidrometroInstalado(); 
				hidrometroInstaladoAgua = ControladorHidrometroInstalado.getInstance().
						buscarHidrometroInstaladoPorImovelTipoMedicao(imovel.getId(), ConstantesSistema.LIGACAO_AGUA);
				if(hidrometroInstaladoAgua!=null){
					// só limpa o campo se tiver sido erro de leitura
					// anormalidade = 1
					if( campo == 0 ){
						hidrometroInstaladoAgua.setLeitura(null);	
						hidrometroInstaladoAgua.setLeituraAtualFaturamento(null);
						hidrometroInstaladoAgua.setLeituraAtualFaturamentoHelper(null);
						
					}
					hidrometroInstaladoAgua.setAnormalidade(null);
					ControladorBasico.getInstance().atualizar(hidrometroInstaladoAgua);
				}
			}else{			
				ConsumoHistorico consumoHistoricoBasePoco = getControladorConsumoHistorico().buscarConsumoHistoricoPorImovelIdLigacaoTipo(imovel.getId(),ConstantesSistema.LIGACAO_POCO);
				if(consumoHistoricoBasePoco != null && !consumoHistoricoBasePoco.equals("")){
					ControladorBasico.getInstance().remover(consumoHistoricoBasePoco);
				}
								
				
				HidrometroInstalado hidrometroInstaladoPoco = new HidrometroInstalado(); 
				hidrometroInstaladoPoco = ControladorHidrometroInstalado.getInstance().
						buscarHidrometroInstaladoPorImovelTipoMedicao(imovel.getId(), ConstantesSistema.LIGACAO_POCO);
				if(hidrometroInstaladoPoco!=null){
					// só limpa o campo se tiver sido erro de leitura
					// anormalidade = 1
					if( campo == 0 ){
						hidrometroInstaladoPoco.setLeitura(null);
					}
					hidrometroInstaladoPoco.setAnormalidade(null);
					ControladorBasico.getInstance().atualizar(hidrometroInstaladoPoco);
				}
			}

			
		} catch (ControladorException e) {
			e.printStackTrace();
			Log.e( ConstantesSistema.CATEGORIA , e.getMessage());
		}
	}
	
	public void chamaProximo(int posicao) {
		if(getContext().getClass().equals(HidrometroAguaActivity.class)){
			((HidrometroAguaActivity)getContext()).iniciarProximaIntent(posicao, true);		
		}else if(getContext().getClass().equals(HidrometroEsgotoActivity.class)){
			((HidrometroEsgotoActivity) getContext()).iniciarProximaIntent(posicao, true);
		}else if(getContext().getClass().equals(ContaActivity.class)){
			((ContaActivity) getContext()).iniciarProximaIntent(posicao, true);	
		}else{
			((TabsActivity) getContext()).iniciarProximaIntent(posicao, true);
		}
	}
	
	public void chamaAnterior(int posicao) {
		if(getContext().getClass().equals(HidrometroAguaActivity.class)){
			((HidrometroAguaActivity)getContext()).iniciarProximaIntent(posicao, false);		
		}else if(getContext().getClass().equals(HidrometroEsgotoActivity.class)){
			((HidrometroEsgotoActivity) getContext()).iniciarProximaIntent(posicao, false);
		}else if(getContext().getClass().equals(ContaActivity.class)){
			((ContaActivity) getContext()).iniciarProximaIntent(posicao, false);	
		}else{
			((TabsActivity) getContext()).iniciarProximaIntent(posicao, false);
		}
	}
	
	/**
	 * Exibi a mensagem que ainda existe
	 * imoveis não calculado dentro de um condominio. 
	 * @author Carlos Chaves
	 * @date 20/02/2013
	 * @param ImovelConta imovel
	 * @return 
	 * @throws ControladorException 
	 */
	public void exbirMensagemImovelCondominioNaoCalculado(ImovelConta imovel, Context ctx){
		
		try{
			ImovelConta imovelMacro = (ImovelConta) ControladorBasico.getInstance().
				pesquisarPorId(imovel.getMatriculaCondominio(), imovel);

			final Integer posicao = getControladorImovelConta().
					obterPosicaoImovelCondominioNaoCalculado(imovelMacro.getId());
		
			if(posicao!=null){
				new AlertDialog.Builder(ctx)
				.setTitle(ctx.getString(R.string.str_atencao))
				.setCancelable(false)
				.setMessage(ctx.getString(R.string.str_imov_condominio))
				.setNeutralButton(ctx.getString(android.R.string.ok), 
					new DialogInterface.OnClickListener() {
						public void onClick(
							DialogInterface dialog,
							int which) {
							chamaProximo(posicao-1);
						}
				}).show();
						    						    					
			} else {
				//Direciona para RateioActivity
				Intent i = new Intent(ctx, RateioActivity.class);
				i.putExtra("macro", imovelMacro);
				ctx.startActivity(i);		    					
			}
			
		}catch (ControladorException e) {
			e.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , e.getMessage());
		}
		
	}
}