package com.br.ipad.isc.gui;

import java.util.ArrayList;

import android.content.Intent;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.ConsumoHistorico;
import com.br.ipad.isc.bean.ConsumoTipo;
import com.br.ipad.isc.bean.HidrometroInstalado;
import com.br.ipad.isc.bean.ImovelRevisitar;
import com.br.ipad.isc.bean.LeituraAnormalidade;
import com.br.ipad.isc.bean.SistemaParametros;
import com.br.ipad.isc.fachada.Fachada;
import com.br.ipad.isc.util.ConstantesSistema;
import com.br.ipad.isc.util.Util;

public class ContaActivity extends HidrometroBaseActivity {
	
	private TextView leituraAgua;
	private TextView consumoAgua;
	private TextView consumoTipoAgua;
	private TextView anormalidadeTipo;
	private TextView leituraPoco;
	private TextView consumoEsgoto;
	private TextView consumoTipoPoco;
	private TextView valor;		
	protected TextView endereco;
	private TextView valorAgua;
	private TextView valorDebito;
	private TextView valorCredito;
	private TextView valorEsgoto;
	private TextView anormalidadeEsgoto;
	private TextView diasConsumo;
		
	/**
	 * Métodos abstratos da classe HidrometroBaseActivity
	 */
	protected int getTipoLigacao(){
		return 0;
	}
	
	protected int getLayout(){
		return R.layout.conta_activity;
	}
	
	public Intent getProximaIntent(){
		return new Intent(ContaActivity.this,TabsActivity.class);
	}
	
	protected HidrometroInstalado getHidrometro(){
		return null;
	}
	
	protected LeituraAnormalidade getAnormalidade(){
		return null;
	}
	
	protected void verificarErro(){}
	
	protected EditText getLeitura() {
		return null;
	}

	protected void setLeitura(EditText leitura) {}
	
	protected EditText getAnormalidadeInformada() {
		return null;
	}

	protected void setAnormalidadeInformada(EditText anormalidadeInformada) {}
	
	protected Spinner getSpinnerAnormalidade() {
		return null;
	}
	
	protected void setSpinnerAnormalidade(Spinner spinnerAnormalidade) {}
	
	
	
    protected void setUpWidgets() {
    	
    	status = (ImageView) findViewById(R.id.status);
		
    	nomeEndereco = (TextView) findViewById(R.id.nomeEndereco);
    	
    	nomeEndereco.setFocusableInTouchMode(true);
    	nomeEndereco.requestFocus();
    	
    	
		//Caso calculado e não impresso
		if(imovel.getIndcImovelCalculado().equals(ConstantesSistema.SIM) && imovel.getIndcImovelImpresso().equals(ConstantesSistema.NAO)){
		
			status.setImageResource(R.drawable.bolacalculado);
		// Caso calculado e impresso
		}else if(imovel.getIndcImovelCalculado().equals(ConstantesSistema.SIM) && imovel.getIndcImovelImpresso().equals(ConstantesSistema.SIM)){
			
			status.setImageResource(R.drawable.bolaimpresso);
		}
		
    	if(imovel!=null){	
			//Se for imovel condominio exibir a posicao no condominio
			if(imovel.isCondominio()){
				TextView posicaoCondominio = (TextView) findViewById(R.id.posicaoCondominio);
				String condominio = "Condomínio: "+imovel.getPosicaoImovelCondominio()
						+"/"+SistemaParametros.getInstancia().getQtdImovelCondominio();
					posicaoCondominio.setText(condominio);
					
					posicaoCondominio.setVisibility(View.VISIBLE);	
					
					nomeEndereco.setVisibility(View.GONE);
			}
    	}
    
    	//Exibe a imagem da bola vermelha se há imóvel a revisitar
    	ArrayList<ImovelRevisitar> imoveisRevisitar = Fachada.getInstance().buscarImovelNaoRevisitado();
		if(imoveisRevisitar != null){
			ImageView revisitarConta = (ImageView) findViewById(R.id.revisitarConta);
			revisitarConta.setVisibility(View.VISIBLE);
		}
    	
    	ConsumoHistorico consumoHistoricoAgua = fachada.
			buscarConsumoHistoricoPorImovelIdTipoLigacao(imovel.getId(),ConstantesSistema.LIGACAO_AGUA);
		
		ConsumoHistorico consumoHistoricoPoco = fachada.
		buscarConsumoHistoricoPorImovelIdTipoLigacao(imovel.getId(),ConstantesSistema.LIGACAO_POCO);
				        
		rotaFinalizada = (TextView) findViewById(R.id.rota_finalizada);
		endereco = (TextView) findViewById(R.id.endereco);
		endereco.setEllipsize(TruncateAt.MARQUEE);
		endereco.setText(imovel.getEndereco());
	    endereco.setSelected(true);
	    endereco.setSingleLine(true);
        
		//2.	Dados da leitura ligação água
		if(consumoHistoricoAgua != null){
			leituraAgua = (TextView) findViewById(R.id.leitura);
			if(consumoHistoricoAgua.getLeituraAtual() != null){
				leituraAgua.setText(consumoHistoricoAgua.getLeituraAtual()+"");
			}
		
			consumoAgua = (TextView) findViewById(R.id.consumoAgua);
			if(consumoHistoricoAgua.getConsumoCobradoMes() != null){
				consumoAgua.setText(consumoHistoricoAgua.getConsumoCobradoMes()+"");
			}
			
			consumoTipoAgua = (TextView) findViewById(R.id.consumoTipoAgua);
			if(consumoHistoricoAgua.getTipoConsumo() != null){
				ConsumoTipo consumoTipo = (ConsumoTipo) fachada.pesquisarPorId(consumoHistoricoAgua.getTipoConsumo(),new ConsumoTipo());
				if(consumoTipo != null){
					consumoTipoAgua.setText(consumoTipo.getDescricao());
				}
			}
			
			
			anormalidadeTipo = (TextView) findViewById(R.id.anormalidadeTipoAgua);
			LeituraAnormalidade leituraAnormalidade = fachada.buscarLeituraAnormalidadeImovel(imovel.getId(),ConstantesSistema.LIGACAO_AGUA);
			if(leituraAnormalidade != null){
				anormalidadeTipo.setText(leituraAnormalidade.getDescricaoAnormalidadeLeitura());
			} else {
				anormalidadeTipo.setText("");
			}
			
			valorAgua = (TextView) findViewById(R.id.valorAgua);
			Double valorTotalAgua = Fachada.getInstance().obterValorTotal(imovel.getId(),  ConstantesSistema.LIGACAO_AGUA);
			if(valorTotalAgua != null){
				valorAgua.setText(Util.formatarDoubleParaMoedaReal(valorTotalAgua));
			}
			
			diasConsumo = (TextView) findViewById(R.id.diasConsumo);
			if(consumoHistoricoAgua.getDiasConsumo()!=null){
				diasConsumo.setText(consumoHistoricoAgua.getDiasConsumo()+"");
			}
					
		}
		
		//3.	Dados da leitura ligação poço
		if(consumoHistoricoPoco != null){
			leituraPoco = (TextView) findViewById(R.id.leituraPoco);
			if(consumoHistoricoPoco.getLeituraAtual() != null){
				leituraPoco.setText(consumoHistoricoPoco.getLeituraAtual()+"");
			}
			
			consumoEsgoto = (TextView) findViewById(R.id.consumoEsgoto);
			if(consumoHistoricoPoco.getConsumoCobradoMes() != null){
				consumoEsgoto.setText(consumoHistoricoPoco.getConsumoCobradoMes()+"");
			}
				
			consumoTipoPoco = (TextView) findViewById(R.id.consumoTipoEsgoto);
			if(consumoHistoricoPoco.getTipoConsumo() != null){
				ConsumoTipo objConsumoTipoPoco = (ConsumoTipo) fachada.pesquisarPorId(consumoHistoricoPoco.getTipoConsumo(),new ConsumoTipo());
				if(objConsumoTipoPoco != null){
					consumoTipoPoco.setText(objConsumoTipoPoco.getDescricao());
				}
			}
			
			anormalidadeEsgoto = (TextView) findViewById(R.id.anormalidadeEsgoto);
			LeituraAnormalidade leituraAnormalidadeEsgoto = fachada.buscarLeituraAnormalidadeImovel(imovel.getId(),ConstantesSistema.LIGACAO_ESGOTO);
			if(leituraAnormalidadeEsgoto != null){
				anormalidadeEsgoto.setText(leituraAnormalidadeEsgoto.getDescricaoAnormalidadeLeitura());
			} else {
				anormalidadeEsgoto.setText("");
			}
			
			valorEsgoto = (TextView) findViewById(R.id.valorEsgoto);
			Double valorTotalEsgoto = Fachada.getInstance().obterValorTotal(imovel.getId(), ConstantesSistema.LIGACAO_ESGOTO);
			if(valorTotalEsgoto != null){
				valorEsgoto.setText(Util.formatarDoubleParaMoedaReal(valorTotalEsgoto));
			}
		}
				
		valorCredito = (TextView) findViewById(R.id.valorCredito);
		Double credito = Fachada.getInstance().obterValorCreditoTotal(imovel.getId());
		if(credito != null){
			valorCredito.setText(Util.formatarDoubleParaMoedaReal(credito));
    	}
		
		valorDebito = (TextView) findViewById(R.id.valorDebito);
		Double debito = Fachada.getInstance().obterValorDebitoTotal(imovel.getId());
		if(debito != null){
			valorDebito.setText(Util.formatarDoubleParaMoedaReal(debito));
		}
		
		valor = (TextView) findViewById(R.id.valor);
		valor.setText(Util.formatarDoubleParaMoedaReal(Fachada.getInstance().obterValorConta(imovel.getId())));
	}
	
	@Override
	protected void onResume() {
		super.onResume();
        setUpWidgets();   
	}	
	
	protected boolean atualizarHistorico(){ return true; }
	
}