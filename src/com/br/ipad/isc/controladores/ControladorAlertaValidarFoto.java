package com.br.ipad.isc.controladores;

import android.content.Intent;

import com.br.ipad.isc.bean.HidrometroInstalado;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.bean.SistemaParametros;
import com.br.ipad.isc.bean.helpers.CameraHelper;
import com.br.ipad.isc.gui.FotoActivity;
import com.br.ipad.isc.gui.TabsActivity;
import com.br.ipad.isc.util.ConstantesSistema;

public class ControladorAlertaValidarFoto extends ControladorAlertaBasico {

	private HidrometroInstalado hidrometroInstaladoAgua;
	private Integer posicao;
	private TabsActivity tabsActivity;
	private boolean imprimir;
	private Integer medicaoTipo;
	private Integer anormId;
	private Integer idConsumoAnormalidade;
	private boolean proximo;
	private HidrometroInstalado hidrometroInstaladoPoco;
	private ImovelConta imovel;
		
	public ControladorAlertaValidarFoto(TabsActivity tabsActivity, HidrometroInstalado hidrometroInstaladoAgua, HidrometroInstalado hidrometroInstaladoPoco,
			ImovelConta imovel, Integer posicao, boolean proximo, boolean imprimir, Integer medicaoTipo, Integer anormId,Integer idConsumoAnormalidade) {
		super();
		this.hidrometroInstaladoAgua = hidrometroInstaladoAgua;
		this.hidrometroInstaladoPoco = hidrometroInstaladoPoco;
		this.imovel = imovel;
		this.posicao = posicao;
		this.proximo = proximo;
		this.tabsActivity = tabsActivity;
		this.imprimir = imprimir;
		this.medicaoTipo = medicaoTipo;
		this.anormId = anormId;
		this.idConsumoAnormalidade = idConsumoAnormalidade;
		
	}
	
	public ControladorAlertaValidarFoto() {
		super();
	}

	@Override
	public void alertaPerguntaSim() { 
		Intent camera;
		 camera = new Intent(getContext(), FotoActivity.class);
     	 CameraHelper helper = new CameraHelper(imovel, medicaoTipo, anormId,idConsumoAnormalidade,null);
     	 camera.putExtra("helper", helper);                          
     	 
     	 if(SistemaParametros.getInstancia().getCodigoEmpresaFebraban()
     			 .equals(ConstantesSistema.CODIGO_FEBRABAN_COMPESA)){
     		camera.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
     	 }
     	 
     	getContext().startActivity(camera);
			
			
	}

	@Override
	public void alertaPerguntaNao() {	
		if(TabsActivity.fotoAgua){
//			TabsActivity.fotoAgua = false;
			tabsActivity.validarFoto(posicao, proximo, idMensagem);
		}else{
			// Caso "chama próximo"
			if(idMensagem == 0){
				
				tabsActivity.validaChamaProximo(posicao,proximo);
				
			//Caso calcular
			}else if( idMensagem == 1){
				
				tabsActivity.validaCalcular(proximo);
				
			//Caso imprimir
			}else if( idMensagem == 2 ){
				
				tabsActivity.validarImprimir();
			
			//Caso "chama anterior"
			}else if( idMensagem == 4){
				
				tabsActivity.validaChamaAnterior(posicao, proximo);
			}
		}
	}
	

	@Override
	public void alertaMensagem() {
	
	}

	
	public ImovelConta getImovel() {
		return imovel;
	}

	public HidrometroInstalado getHidrometroInstaladoAgua() {
		return hidrometroInstaladoAgua;
	}

	public void setHidrometroInstaladoAgua(
			HidrometroInstalado hidrometroInstaladoAgua) {
		this.hidrometroInstaladoAgua = hidrometroInstaladoAgua;
	}

	public Integer getPosicao() {
		return posicao;
	}

	public void setPosicao(Integer posicao) {
		this.posicao = posicao;
	}

	public boolean isProximo() {
		return proximo;
	}

	public void setProximo(boolean proximo) {
		this.proximo = proximo;
	}

	public HidrometroInstalado getHidrometroInstaladoPoco() {
		return hidrometroInstaladoPoco;
	}

	public TabsActivity getTabsActivity() {
		return tabsActivity;
	}

	public void setTabsActivity(TabsActivity tabsActivity) {
		this.tabsActivity = tabsActivity;
	}

	public boolean isImprimir() {
		return imprimir;
	}

	public void setImprimir(boolean imprimir) {
		this.imprimir = imprimir;
	}

	public Integer getMedicaoTipo() {
		return medicaoTipo;
	}

	public void setMedicaoTipo(Integer medicaoTipo) {
		this.medicaoTipo = medicaoTipo;
	}

	public Integer getAnormId() {
		return anormId;
	}

	public void setAnormId(Integer anormId) {
		this.anormId = anormId;
	}

	public Integer getIdConsumoAnormalidade() {
		return idConsumoAnormalidade;
	}

	public void setIdConsumoAnormalidade(Integer idConsumoAnormalidade) {
		this.idConsumoAnormalidade = idConsumoAnormalidade;
	}

	public void setHidrometroInstaladoPoco(
			HidrometroInstalado hidrometroInstaladoPoco) {
		this.hidrometroInstaladoPoco = hidrometroInstaladoPoco;
	}

	public void setImovel(ImovelConta imovel) {
		this.imovel = imovel;
	}

}