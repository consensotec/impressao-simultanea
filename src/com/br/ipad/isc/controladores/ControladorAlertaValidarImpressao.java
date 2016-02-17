package com.br.ipad.isc.controladores;

import android.util.Log;

import com.br.ipad.isc.bean.HidrometroInstalado;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.excecoes.ControladorException;
import com.br.ipad.isc.util.ConstantesSistema;
import com.br.ipad.isc.util.Util;

public class ControladorAlertaValidarImpressao extends ControladorAlertaBasico implements IControladorAlertaValidarLeitura {

	private HidrometroInstalado hidrometroInstalado;
	private int tipoMedicao;
	private ImovelConta imovel;
		
	public ControladorAlertaValidarImpressao(ImovelConta imovel) {
		super();
		this.imovel = imovel;
	}
	
	public ControladorAlertaValidarImpressao() {
		super();
	}

	@Override
	public void alertaPerguntaSim() {
//		boolean resposta =false;
		try {

			boolean contaImpressa = getControladorImpressao().verificarImpressaoConta(imovel,getContext(), super.getIdMensagem(), true);
		
			
			if(contaImpressa){
				Util.chamaProximo(getContext(), imovel);

			}
			
			} catch (ControladorException e) {
				Log.e( ConstantesSistema.CATEGORIA , e.getMessage());
				e.printStackTrace();
		}
	}

	@Override
	public void alertaPerguntaNao() {		
			
	}

	@Override
	public void alertaMensagem() {

		Util.chamaProximo(getContext(), imovel);

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