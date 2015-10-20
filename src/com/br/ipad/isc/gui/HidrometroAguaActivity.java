package com.br.ipad.isc.gui;

import android.content.Intent;
import android.widget.EditText;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.HidrometroInstalado;
import com.br.ipad.isc.bean.LeituraAnormalidade;
import com.br.ipad.isc.util.ConstantesSistema;
import android.widget.Spinner;

public class HidrometroAguaActivity extends HidrometroBaseActivity {
	
	/**
	 * Métodos abstratos da classe HidrometroBaseActivity
	 */
	protected int getTipoLigacao(){
		return ConstantesSistema.LIGACAO_AGUA;
	}
	
	protected int getLayout(){
		return R.layout.hidrometro_activity;
	}
	
	public Intent getProximaIntent(){
		return new Intent(HidrometroAguaActivity.this,TabsActivity.class);
	}
	
	protected HidrometroInstalado getHidrometro(){
		return TabsActivity.hidrometroInstaladoAgua;
	}
	
	protected LeituraAnormalidade getAnormalidade(){
		return TabsActivity.anormalidadeAgua;
	}

	protected EditText getLeitura() {
		return HidrometroBaseActivity.leituraAgua;
	}

	protected void setLeitura(EditText leitura) {
		HidrometroBaseActivity.leituraAgua = leitura;		
	}
	
	protected EditText getAnormalidadeInformada() {
		return HidrometroBaseActivity.idAnormalidadeAgua;
	}

	protected void setAnormalidadeInformada(EditText anormalidadeInformada) {
		HidrometroBaseActivity.idAnormalidadeAgua = anormalidadeInformada;		
	}
	
	protected Spinner getSpinnerAnormalidade() {
		return HidrometroBaseActivity.anormalidadeSpinnerAgua;
	}
	
	protected void setSpinnerAnormalidade(Spinner spinnerAnormalidade) {
		HidrometroBaseActivity.anormalidadeSpinnerAgua = spinnerAnormalidade;		
	}
	
	protected void verificarErro(){
		//Caso haja água e poço e houve erro
		if(naoHouveErro && TabsActivity.hidrometroInstaladoPoco != null && 
				TabsActivity.hidrometroInstaladoPoco.getMatricula().getId().equals(TabsActivity.hidrometroInstaladoAgua.getMatricula().getId())){
			
			if(TabsActivity.hidrometroInstaladoAgua != null){
				
		        if(leituraAlteradaUnica(getTipoLigacao())){
		        	fachada.atualizarIndicadorImovelCalculado(imovel.getId(),ConstantesSistema.NAO);					
		        }
				
				if(leituraAgua.getText().toString().length() != 0){
					TabsActivity.hidrometroInstaladoAgua.setLeitura(Integer.valueOf(leituraAgua.getText().toString()));
				}else{
					TabsActivity.hidrometroInstaladoAgua.setLeitura(null);
				}
				if(TabsActivity.anormalidadeAgua != null ){
					TabsActivity.hidrometroInstaladoAgua.setAnormalidade(TabsActivity.anormalidadeAgua.getId());
				}

				fachada.atualizar(TabsActivity.hidrometroInstaladoAgua);				
				// Reseta a variável para que o hidrômetro possa ser atualizado
				naoHouveErro = true;
				}
		} else if(!naoHouveErro) naoHouveErro = true;
	}		
	
}