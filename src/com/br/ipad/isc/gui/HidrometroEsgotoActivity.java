package com.br.ipad.isc.gui;

import android.content.Intent;
import android.widget.EditText;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.HidrometroInstalado;
import com.br.ipad.isc.bean.LeituraAnormalidade;
import com.br.ipad.isc.util.ConstantesSistema;
import android.widget.Spinner;

public class HidrometroEsgotoActivity extends HidrometroBaseActivity  {
   
	/**
	 * Métodos abstratos da classe HidrometroBaseActivity
	 */
	protected int getTipoLigacao(){
		return ConstantesSistema.LIGACAO_ESGOTO;
	}
	
	protected int getLayout(){
		return R.layout.hidrometro_activity;
	}
	
	public Intent getProximaIntent(){
		return new Intent(HidrometroEsgotoActivity.this,TabsActivity.class);
	}
	
	protected HidrometroInstalado getHidrometro(){
		return TabsActivity.hidrometroInstaladoPoco;
	}
	
	protected LeituraAnormalidade getAnormalidade(){
		return TabsActivity.anormalidadePoco;
	}
	
	protected EditText getLeitura() {
		return HidrometroBaseActivity.leituraPoco;
	}

	protected void setLeitura(EditText leitura) {
		HidrometroBaseActivity.leituraPoco = leitura;		
	}
	
	protected EditText getAnormalidadeInformada() {
		return HidrometroBaseActivity.idAnormalidadePoco;
	}

	protected void setAnormalidadeInformada(EditText anormalidadeInformada) {
		HidrometroBaseActivity.idAnormalidadePoco = anormalidadeInformada;		
	}
	
	protected Spinner getSpinnerAnormalidade() {
		return HidrometroBaseActivity.anormalidadeSpinnerPoco;
	}
	
	protected void setSpinnerAnormalidade(Spinner spinnerAnormalidade) {
		HidrometroBaseActivity.anormalidadeSpinnerPoco = spinnerAnormalidade;		
	}
	
	protected void verificarErro(){
		//Caso haja água e poço e houve erro
		if(naoHouveErro && TabsActivity.hidrometroInstaladoAgua != null && 
				TabsActivity.hidrometroInstaladoAgua.getMatricula().getId().equals(TabsActivity.hidrometroInstaladoPoco.getMatricula().getId())){
			
			if(TabsActivity.hidrometroInstaladoPoco != null){
									
		        if(leituraAlteradaUnica(getTipoLigacao())){	        	
					fachada.atualizarIndicadorImovelCalculado(imovel.getId(),ConstantesSistema.NAO);				
		        }
				
				if(getLeitura().getText().toString().length() != 0){
					TabsActivity.hidrometroInstaladoPoco.setLeitura(Integer.valueOf(getLeitura().getText().toString()));
				}else{
					TabsActivity.hidrometroInstaladoPoco.setLeitura(null);
				}
				if(TabsActivity.anormalidadePoco != null ){
					TabsActivity.hidrometroInstaladoPoco.setAnormalidade(TabsActivity.anormalidadePoco.getId());
				}else{
					TabsActivity.hidrometroInstaladoPoco.setAnormalidade(null);
				}
				fachada.atualizar(TabsActivity.hidrometroInstaladoPoco);
				// Reseta a variável para que o hidrômetro possa ser atualizado
				naoHouveErro = true;		
				TabsActivity.fotoAgua =false;
			}			
		}else if(!naoHouveErro) naoHouveErro = true;
	}	
	
	
}