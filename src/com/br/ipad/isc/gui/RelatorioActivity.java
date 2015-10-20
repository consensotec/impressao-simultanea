package com.br.ipad.isc.gui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.br.ipad.isc.R;
import com.br.ipad.isc.fachada.Fachada;

public class RelatorioActivity extends BaseActivity {
    /** Called when the activity is first created. */
	
	private TextView totalImoveisAVisitar;
	private TextView totalImoveis;
	private TextView totalImoveisVisitados;
	private TextView percentualImoveisVisitados;
	private TextView totalImoveisVisitadosComAnormalidade;
	private TextView percentualVisitadosComAnormalidade;
	private TextView totalImoveisVisitadosSemAnormalidade;
	private TextView percentualVisitadosSemAnormalidade;
	private LinearLayout percentualConcluido;
	private LinearLayout percentualNaoConcluido;
	private Button btRelatorioPorQuadra;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relatorio_activity);
     
        //Barras de controle de porcentagem
        percentualConcluido = (LinearLayout) findViewById(R.id.concluido);
        android.widget.LinearLayout.LayoutParams parametrosPercentualConcluido = (LayoutParams) percentualConcluido.getLayoutParams();
        
        percentualNaoConcluido = (LinearLayout) findViewById(R.id.nao_concluido);
        android.widget.LinearLayout.LayoutParams parametrosPercentualNaoConcluido = (LayoutParams) percentualNaoConcluido.getLayoutParams();
        
        //Imoveis Visistados
        
        int quantidadeTotalImoveis = Fachada.getInstance().obterQuantidadeImoveis();
        int totalVisitados = Fachada.getInstance().obterQuantidadeImoveisVisitados();
        double percentualVisitados = (double)totalVisitados * 100 / (double)quantidadeTotalImoveis;
        
       
        
        totalImoveis = (TextView) findViewById(R.id.totalImoveis);
        totalImoveis.setText(quantidadeTotalImoveis+"");
        totalImoveisVisitados = (TextView) findViewById(R.id.totalImoveisVisitados);
        totalImoveisVisitados.setText(totalVisitados+"");
        totalImoveisAVisitar = (TextView) findViewById(R.id.totalImoveisAVisitar);
        totalImoveisAVisitar.setText(quantidadeTotalImoveis-totalVisitados+"");
        
        percentualImoveisVisitados = (TextView) findViewById(R.id.percentualImoveisVisitados);
      
   
       if(totalVisitados == 0 ){
    	   
    	   percentualImoveisVisitados.setText("0%");
    	   parametrosPercentualConcluido.weight = 1f;
    	   parametrosPercentualNaoConcluido.weight = 0f;
    	   
       } else{
    	   
    	   percentualImoveisVisitados.setText(String.format("%.1f", percentualVisitados) + "%");
    	   double totalConcluido = percentualVisitados / 100;
    	   parametrosPercentualConcluido.weight = (float) (1-totalConcluido);
    	   parametrosPercentualNaoConcluido.weight = (float) (totalConcluido);
    	   	
       }
       
       // Imoveis Visitados COM anormalidade 
       
        int totalComAnormalidade = Fachada.getInstance().obterQuantidadeImoveisVisitadosComAnormalidade();
        double percentualComAnormalidade = (double)totalComAnormalidade * 100 / (double)totalVisitados;
        
        totalImoveisVisitadosComAnormalidade = (TextView) findViewById(R.id.totalImoveisVisitadosComAnormalidade);
        totalImoveisVisitadosComAnormalidade.setText(totalComAnormalidade+"");
        percentualVisitadosComAnormalidade = (TextView) findViewById(R.id.percentualVisitadosComAnormalidade);
      
      
        if(totalVisitados == 0){
        	percentualVisitadosComAnormalidade.setText("0%");
        } else{
        	percentualVisitadosComAnormalidade.setText(String.format("%.1f", percentualComAnormalidade) + "%");
        }
        
        //Imoveis Visitados SEM anormalidade
 
        int totalSemAnormalidade = totalVisitados - totalComAnormalidade;
        double percentualSemAnormalidade = 100f - percentualComAnormalidade;
        
        totalImoveisVisitadosSemAnormalidade = (TextView) findViewById(R.id.totalImoveisVisitadosSemAnormalidade);
        totalImoveisVisitadosSemAnormalidade.setText(totalSemAnormalidade+"");
        percentualVisitadosSemAnormalidade = (TextView) findViewById(R.id.percentualVisitadosSemAnormalidade);
        
        if(totalVisitados == 0){
        	percentualVisitadosSemAnormalidade.setText("0%");
        } else{
        	percentualVisitadosSemAnormalidade.setText(String.format("%.1f", percentualSemAnormalidade) + "%");
        }
        
     
        
        btRelatorioPorQuadra = (Button) findViewById(R.id.btRelatorioPorQuadra);
        
        btRelatorioPorQuadra.setOnClickListener(new OnClickListener() {
			
        	public void onClick(View arg0) {
        		Intent i = new Intent(RelatorioActivity.this,RelatorioPorQuadraActivity.class);
				startActivity(i);
			}
        });
        
    }
    
	
	@Override
    public void onBackPressed() { 
		finish();
	}
}