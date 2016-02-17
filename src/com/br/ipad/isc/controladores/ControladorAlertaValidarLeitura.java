package com.br.ipad.isc.controladores;

import android.content.Intent;
import android.util.Log;
import android.widget.EditText;

import com.br.ipad.isc.bean.ConsumoHistorico;
import com.br.ipad.isc.bean.HidrometroInstalado;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.bean.LeituraAnormalidade;
import com.br.ipad.isc.bean.SistemaParametros;
import com.br.ipad.isc.excecoes.ControladorException;
import com.br.ipad.isc.excecoes.RepositorioException;
import com.br.ipad.isc.fachada.Fachada;
import com.br.ipad.isc.gui.ContaActivity;
import com.br.ipad.isc.gui.HidrometroAguaActivity;
import com.br.ipad.isc.gui.HidrometroBaseActivity;
import com.br.ipad.isc.gui.HidrometroEsgotoActivity;
import com.br.ipad.isc.gui.TabsActivity;
import com.br.ipad.isc.repositorios.RepositorioBasico;
import com.br.ipad.isc.util.ConstantesSistema;

public class ControladorAlertaValidarLeitura extends ControladorAlertaBasico implements IControladorAlertaValidarLeitura {

	private HidrometroInstalado hidrometroInstalado;
	private int tipoMedicao;
	private ImovelConta imovel;
	private boolean imprimir;
	private boolean proximo;
		
	public ControladorAlertaValidarLeitura(HidrometroInstalado hidrometroInstalado, ImovelConta imovel,int tipoMedicao,boolean imprimir, boolean proximo) {
		super();
		this.hidrometroInstalado = hidrometroInstalado;
		this.imovel = imovel;
		this.tipoMedicao = tipoMedicao;
		this.imprimir = imprimir;
		this.proximo = proximo;
	}
	
	public ControladorAlertaValidarLeitura() {
		super();
	}

	@Override
	public void alertaPerguntaSim() { 
		try {
			
			if(super.getIdMensagem() == 12){
				hidrometroInstalado.setAnormalidadeFaturadaCaern(Integer.valueOf(ConstantesSistema.SIM));
			}
			
			boolean contaCalculada = getControladorHidrometroInstalado().validarLeituraMensagem(hidrometroInstalado, imovel, super.getIdMensagem(),imprimir,proximo);
			
			if(contaCalculada && imovel.getMatriculaCondominio()!=null && imovel.getMatriculaCondominio().equals(SistemaParametros.getInstancia().getIdImovelCondominio())
					&& SistemaParametros.getInstancia().getQtdImovelCondominio().equals(imovel.getPosicaoImovelCondominio())){
			
				if(imprimir){
					exbirMensagemImovelCondominioNaoCalculado(imovel, getContext());
				}else{
					if(proximo){
						chamaProximo(imovel.getPosicao());
					}else{
						chamaAnterior(imovel.getPosicao());
					}
				}
				
//			//Se for imovel condominio ir pra HidrometroAguaActivity
//			if(imovel.isCondominio()){
//				 
//				if(contaCalculada && imovel.getMatriculaCondominio()!=null && imovel.getMatriculaCondominio().equals(SistemaParametros.getInstancia().getIdImovelCondominio())
//						&& SistemaParametros.getInstancia().getQtdImovelCondominio().equals(imovel.getPosicaoImovelCondominio())){
//				
//					//Verificamos se todos os imóveis foram calculados
//					ImovelConta imovelMacro = (ImovelConta) ControladorBasico.getInstance().
//							pesquisarPorId(imovel.getMatriculaCondominio(), imovel);
//					
//    				final Integer posicao = Fachada.getInstance().obterPosicaoImovelCondominioNaoCalculado(imovelMacro.getId());
//    				if(posicao == null){
//    					
//						//Chama RateioActivity para iniciar o rateio    					
//						Intent i = new Intent(getContext(), RateioActivity.class);
//						i.putExtra("macro", imovelMacro);
//						getContext().startActivity(i);	
//    				}
//				}
					
			}else			
			//Se a conta foi calculada
			if(contaCalculada && !imprimir){
				
				if(proximo){
					chamaProximo(imovel.getPosicao());
				}else{
					chamaAnterior(imovel.getPosicao());
				}
				
	    	}else if(contaCalculada && imprimir){
				
				boolean contaImpressa = getControladorImpressao().verificarImpressaoConta(imovel,getContext(),0, true);
			
				if(contaImpressa){
					//Chama Proximo
					Intent intent = new Intent(getContext(), TabsActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);    
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.putExtra("imovel", imovel);
					intent.putExtra("posicao", imovel.getPosicao());
					getContext().startActivity(intent); 
				}
	    	} 
			
			} catch (ControladorException e) {
				Log.e( ConstantesSistema.CATEGORIA , e.getMessage());
				e.printStackTrace();
		}
	}

	@Override
	public void alertaPerguntaNao() {
		
		if(super.getIdMensagem() == 12){
			hidrometroInstalado.setAnormalidadeFaturadaCaern(Integer.valueOf(ConstantesSistema.NAO));

			boolean contaCalculada;
			try {
				contaCalculada = getControladorHidrometroInstalado().validarLeituraMensagem(hidrometroInstalado, imovel, super.getIdMensagem(),imprimir,proximo);
				
				if(contaCalculada && !imprimir){
					
					if(proximo){
						chamaProximo(imovel.getPosicao());
					}else{
						chamaAnterior(imovel.getPosicao());
					}
					
			   	}else if(contaCalculada && imprimir){			   		
			   		if ( imovel.ehUltimoImovelCondominio() ){
						exbirMensagemImovelCondominioNaoCalculado(imovel, getContext());
			   		} else {
						boolean contaImpressa = getControladorImpressao().verificarImpressaoConta(imovel,getContext(),0, true);
						
						if(contaImpressa){
							//Chama Proximo
							Intent intent = new Intent(getContext(), TabsActivity.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);    
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							intent.putExtra("imovel", imovel);
							intent.putExtra("posicao", imovel.getPosicao());
							getContext().startActivity(intent); 
						}			   			
			   		}
			   	}
			} catch (ControladorException e) {
				e.printStackTrace();
			}
		}else{
			if(tipoMedicao == ConstantesSistema.LIGACAO_AGUA){
				EditText anormalidade = HidrometroBaseActivity.idAnormalidadeAgua;
				EditText leitura = HidrometroBaseActivity.leituraAgua;
				anormalidade.setText("");
				leitura.setText("");
				
			}else{
				EditText anormalidade = HidrometroBaseActivity.idAnormalidadePoco;
				EditText leitura = HidrometroBaseActivity.leituraPoco;
				anormalidade.setText("");
				leitura.setText("");
			}
			apagaDados(imovel,tipoMedicao,0);
			apagaDados(imovel,ConstantesSistema.LIGACAO_POCO,0);
			
			recarrega();						
		}

	}

	@Override
	public void alertaMensagem() {
		
		if(idMensagem == 3){
			apagaDados(imovel,tipoMedicao,0);
		}
		
		recarrega();
	}
	
	public void recarrega(){
		if(getContext().getClass().equals(HidrometroAguaActivity.class)){
			HidrometroAguaActivity ParentActivity;
			ParentActivity = (HidrometroAguaActivity) getContext();
			ParentActivity.naoHouveErro = false;
			ParentActivity.recarrega(imovel,hidrometroInstalado);
		}else if(getContext().getClass().equals(ContaActivity.class)){
			ContaActivity ParentActivity;
			ParentActivity = (ContaActivity) getContext();
			ParentActivity.naoHouveErro = false;
			ParentActivity.recarrega(imovel,hidrometroInstalado);		
		}else if(getContext().getClass().equals(HidrometroEsgotoActivity.class)){
			HidrometroEsgotoActivity ParentActivity;
			ParentActivity = (HidrometroEsgotoActivity) getContext();
			ParentActivity.naoHouveErro = false;
			ParentActivity.recarrega(imovel,hidrometroInstalado);
		
		}
	}
		
	public HidrometroInstalado getHidrometroInstalado() {
		return hidrometroInstalado;
	}

	public void setHidrometroInstalado(HidrometroInstalado hidrometroInstalado) {
		this.hidrometroInstalado = hidrometroInstalado;
	}

	public ImovelConta getImovel() {
		return imovel;
	}

	public void setImovel(ImovelConta imovel) {
		this.imovel = imovel;
	}
	public int getTipoMedicao() {
		return tipoMedicao;
	}
	public void setTipoMedicao(int tipoMedicao) {
		this.tipoMedicao = tipoMedicao;
	}
}