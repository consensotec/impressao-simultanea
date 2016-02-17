package com.br.ipad.isc.controladores;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.ConsumoHistorico;
import com.br.ipad.isc.bean.Foto;
import com.br.ipad.isc.bean.Foto.Fotos;
import com.br.ipad.isc.bean.HidrometroInstalado;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.bean.SistemaParametros;
import com.br.ipad.isc.bean.helpers.CameraHelper;
import com.br.ipad.isc.excecoes.ControladorException;
import com.br.ipad.isc.gui.ContaActivity;
import com.br.ipad.isc.gui.FotoActivity;
import com.br.ipad.isc.gui.HidrometroAguaActivity;
import com.br.ipad.isc.gui.HidrometroEsgotoActivity;
import com.br.ipad.isc.gui.TabsActivity;
import com.br.ipad.isc.util.ConstantesSistema;
import com.br.ipad.isc.util.Util;

public class ControladorAlertaValidarAnormalidadeConsumo extends ControladorAlertaBasico implements IControladorAlertaValidarAnormalidadeConsumo {

	private ImovelConta imovel;
	private boolean imprimir;
	private int tipoMedicao;
	private boolean proximo;
		
	public ControladorAlertaValidarAnormalidadeConsumo(ImovelConta imovel) {
		super();
	}
	
	public ControladorAlertaValidarAnormalidadeConsumo(ImovelConta imovel,Boolean imprimir, HidrometroInstalado hidrometroAgua, HidrometroInstalado hidrometroPoco, int tipoMedicao, boolean proximo) {
		super();
		this.imovel = imovel;
		this.imprimir = imprimir;
		this.tipoMedicao = tipoMedicao;
		this.proximo = proximo;
	}

	@Override
	public void alertaPerguntaSim() {
//		boolean resposta =false;
		
		ConsumoHistorico consumoHistorico = null;
		ArrayList<Foto> listaFotos = null;
		
		String selection = null;
		String[] selectionArgs = null;
		
		String mensagem = null;
		
		try {

			if(super.getIdMensagem()==1)
			{
				consumoHistorico = getControladorConsumoHistorico().
							buscarConsumoHistoricoPorImovelIdLigacaoTipo(imovel.getId(), tipoMedicao);
				
				if(consumoHistorico.getConsumoAnormalidade()!=null 
						&& (consumoHistorico.getConsumoAnormalidade().getIndicadorFotoAbrigatoria()!=null &&
						consumoHistorico.getConsumoAnormalidade().getIndicadorFotoAbrigatoria().equals(ConstantesSistema.SIM)))
				{
					final Integer idConsumoAnormalidade = consumoHistorico.getConsumoAnormalidade().getId();
					
					selection = Fotos.IMOVEL_CONTA_ID+" =? AND " + Fotos.MEDICAOTIPO + "=? AND " + Fotos.CONSUMO_ANORMALIDADE_ID+" =? ";
					
					selectionArgs = new String[]
					{
						String.valueOf(imovel.getId()),
						String.valueOf(tipoMedicao),
						String.valueOf(idConsumoAnormalidade)
					};
					
					listaFotos = getControladorFoto().buscarFotos(selection, selectionArgs);
					
					if(listaFotos!=null && listaFotos.size()!=0)
					{
						if(tipoMedicao==ConstantesSistema.LIGACAO_AGUA)
						{
							mensagem = getActivity().getString(R.string.str_foto_obrigatorio_agua_substituir);
						}
						else if(tipoMedicao==ConstantesSistema.LIGACAO_POCO)
						{
							mensagem = getActivity().getString(R.string.str_foto_obrigatorio_poco_substituir);
						}
						
						alertConsumoAnormalidadeSubstituirFoto(imovel, tipoMedicao, idConsumoAnormalidade, mensagem);
					}
					else
					{
						if(tipoMedicao==ConstantesSistema.LIGACAO_AGUA)
						{
							mensagem = getActivity().getString(R.string.str_foto_obrigatorio_agua);
						}
						else if(tipoMedicao==ConstantesSistema.LIGACAO_POCO)
						{
							mensagem = getActivity().getString(R.string.str_foto_obrigatorio_poco);
						}
						
						alertConsumoAnormalidadeObrigatorioFoto(idConsumoAnormalidade, mensagem);
					}
		 		}
				else
				{
					chamarProximoInterno();
				}
			}
		
		} catch (ControladorException e) {
			Log.e( ConstantesSistema.CATEGORIA , e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void tirarFotoConsumoAnormalidade(ImovelConta imovel,Integer tipoMedicao,Integer idConsumoAnormalidade){
		
		Intent camera = new Intent(getContext(), FotoActivity.class);
      	CameraHelper helper = new CameraHelper(imovel, tipoMedicao, null,idConsumoAnormalidade,null);
      	camera.putExtra(ConstantesSistema.FOTO_HELPER, helper);                          
      	 
      	if(SistemaParametros.getInstancia().getCodigoEmpresaFebraban()
      		 .equals(ConstantesSistema.CODIGO_FEBRABAN_COMPESA)){
      		camera.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
      	}
      	 
      	getContext().startActivity(camera);
	}
	
	private boolean validarFotosConsumoAnormalidade(ImovelConta imovel,Integer tipoMedicao,Integer idConsumoAnormalidade){
		
		boolean validado = false;
		
		ArrayList<Foto> listaFotos = null;
		
		String selection = Fotos.IMOVEL_CONTA_ID+" =? AND " + Fotos.FOTO_TIPO + "=? AND " + Fotos.MEDICAOTIPO + "=? AND " + Fotos.CONSUMO_ANORMALIDADE_ID+" =? ";
		
		String[] selectionArgs = new String[]
		{
			String.valueOf(imovel.getId()),
			String.valueOf(ConstantesSistema.FOTO_ANORMALIDADE),
			String.valueOf(tipoMedicao),
			String.valueOf(idConsumoAnormalidade)
		};
		
		try {
			
			listaFotos = getControladorFoto().buscarFotos(selection, selectionArgs);
		} catch (ControladorException e) {
			Log.e( ConstantesSistema.CATEGORIA , e.getMessage());
			e.printStackTrace();
		}
		
		if(listaFotos!=null && listaFotos.size()>0)
		{
			validado = true;
		}
		
		return validado;
	}
	
	private void alertConsumoAnormalidadeSubstituirFoto(final ImovelConta imovel,final Integer tipoMedicao,final Integer idConsumoAnormalidade,String mensagem){
		
		new AlertDialog.Builder(getContext()).setTitle(getActivity().getString(R.string.obrigatorio))
        .setMessage(mensagem)
        .setIcon(R.drawable.warning)
        .setCancelable(false)
        .setPositiveButton(getActivity().getString(R.string.str_sim),
                           new DialogInterface.OnClickListener() {
                              
                               public void onClick(DialogInterface dialog, int which) {
                              	
                            	   tirarFotoConsumoAnormalidade(imovel, tipoMedicao, idConsumoAnormalidade);
                               }
                           })
        .setNegativeButton(getActivity().getString(R.string.str_nao),
                           new DialogInterface.OnClickListener() {
                              
                               public void onClick(DialogInterface dialog, int which) {
                                
                            	   if(validarFotosConsumoAnormalidade(imovel,tipoMedicao,idConsumoAnormalidade))
                            	   {
                            		   chamarProximoInterno();
                            	   }
                            	   else
                            	   {
                            		   new AlertDialog.Builder(getContext()).setTitle(getActivity().getString(R.string.obrigatorio))
                                       .setMessage(getActivity().getString(R.string.str_informe_foto_anormalidade_imovel))
                                       .setIcon(R.drawable.warning)
                                       .setCancelable(false)
                                       .setNegativeButton(getActivity().getString(R.string.str_sim),null)
                                       .show();
                            	   }
                            	   
                               }
                           })
        .show();
	}
	
	private void alertConsumoAnormalidadeObrigatorioFoto(final Integer idConsumoAnormalidade,String mensagem){
		
		new AlertDialog.Builder(getContext()).setTitle(getActivity().getString(R.string.obrigatorio))
        .setMessage(mensagem)
        .setIcon(R.drawable.warning)
        .setCancelable(false)
        .setPositiveButton(getActivity().getString(R.string.str_sim),
                           new DialogInterface.OnClickListener() {
                              
                               public void onClick(DialogInterface dialog, int which) {
                              	
                            	   tirarFotoConsumoAnormalidade(imovel, tipoMedicao, idConsumoAnormalidade);
                               }
                           })
        .setNegativeButton(getActivity().getString(R.string.str_nao),
                           new DialogInterface.OnClickListener() {
                              
                               public void onClick(DialogInterface dialog, int which) {
                                   
                               }
                           })
        .show();
	}
	
	private void chamarProximoInterno(){
		
		try {
			getControladorSistemaParametros().atualizarIdImovelSelecionadoSistemaParametros(imovel.getPosicao());
			
			getControladorSequencialRotaMarcacao().gravarSequencialRotaMarcacao(imovel);
			
			if(imprimir){

				if(imovel.getMatriculaCondominio()!=null && imovel.getMatriculaCondominio().equals(SistemaParametros.getInstancia().getIdImovelCondominio())
						&& SistemaParametros.getInstancia().getQtdImovelCondominio().equals(imovel.getPosicaoImovelCondominio())){
					
					exbirMensagemImovelCondominioNaoCalculado(imovel, getContext());
				
				}else{
					boolean contaImpressa = getControladorImpressao().verificarImpressaoConta(imovel,getContext(), super.getIdMensagem(), true);	
					
					if(contaImpressa){
						Util.chamaProximo(getContext(), imovel);
					}
				}
				
			}else {
				mudarTab();
			}
		} catch (ControladorException e) {
			Log.e( ConstantesSistema.CATEGORIA , e.getMessage());
			e.printStackTrace();
		}
	}
	
	private Activity getActivity(){
		return (Activity)getContext();
	}

	@Override
	public void alertaPerguntaNao() {
		if(super.getIdMensagem()==1)
		{
			// 0 = apaga todos os campos, inclusive leitura
			apagaDados(imovel,tipoMedicao,0);
			
			//Recarrega
			Intent intent = new Intent(getContext(), TabsActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);    
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.putExtra("imovel", imovel);
			intent.putExtra("posicao", imovel.getPosicao()-1);
			((Activity) getContext()).finish();
			getContext().startActivity(intent);
		}
	}

	@Override
	public void alertaMensagem() {
		
		if(super.getIdMensagem()==1)
		{
			mudarTab();
		}
		else if(super.getIdMensagem()==2)
		{
			// chamar tela foto
		}
	}
	
	public void mudarTab(){
		//Se for imovel condominio 
		if(imovel.isCondominio()){
			// Caso seja o último imóvel do condomínio
			if(imovel.getMatriculaCondominio()!=null && imovel.getMatriculaCondominio().equals(SistemaParametros.getInstancia().getIdImovelCondominio())
					&& SistemaParametros.getInstancia().getQtdImovelCondominio().equals(imovel.getPosicaoImovelCondominio())){
				
				if(imprimir){
					exbirMensagemImovelCondominioNaoCalculado(imovel, getContext());
				}else{
					if(proximo){
						chamaProximo();
					}else {
						chamaAnterior();
					}
				}
				
			} else {
				if(proximo){
					chamaProximo();
				}else{
					chamaAnterior();
				}
				
			}
		} else {
			chamaProximo();
		}			
	}
	
	private void chamaProximo() {
		if(getContext().getClass().equals(HidrometroAguaActivity.class)){
			((HidrometroAguaActivity)getContext()).iniciarProximaIntent(imovel.getPosicao(), true);		
		}else if(getContext().getClass().equals(HidrometroEsgotoActivity.class)){
			((HidrometroEsgotoActivity) getContext()).iniciarProximaIntent(imovel.getPosicao(), true);
		}else if(getContext().getClass().equals(ContaActivity.class)){
			((ContaActivity) getContext()).iniciarProximaIntent(imovel.getPosicao(), true);	
		}else{
			((TabsActivity) getContext()).iniciarProximaIntent(imovel.getPosicao(), true);
		}
	}
	
	public void chamaAnterior() {
		if(getContext().getClass().equals(HidrometroAguaActivity.class)){
			((HidrometroAguaActivity)getContext()).iniciarProximaIntent(imovel.getPosicao(), false);		
		}else if(getContext().getClass().equals(HidrometroEsgotoActivity.class)){
			((HidrometroEsgotoActivity) getContext()).iniciarProximaIntent(imovel.getPosicao(), false);
		}else if(getContext().getClass().equals(ContaActivity.class)){
			((ContaActivity) getContext()).iniciarProximaIntent(imovel.getPosicao(), false);	
		}else{
			((TabsActivity) getContext()).iniciarProximaIntent(imovel.getPosicao(), false);
		}
	}

	public ImovelConta getImovel() {
		return imovel;
	}

	public void setImovel(ImovelConta imovel) {
		this.imovel = imovel;
	}
}