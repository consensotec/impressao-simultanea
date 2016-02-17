package com.br.ipad.isc.gui;

import java.util.ArrayList;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.CategoriaSubcategoria;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.fachada.Fachada;

public class InformacoesGeraisActivity extends BaseActivity {
    /** Called when the activity is first created. */

	private ImovelConta imovel;
	private TextView endereco;
	private TextView usuario;
	private TextView matricula;
	private TextView inscricao;
	private TextView sequencial;
	private TableLayout tabelaCategorias;
	
	ArrayList<CategoriaSubcategoria> colCategorias;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if (execute){	
	        setContentView(R.layout.informacoes_gerais);
	        
	        imovel = (ImovelConta) getIntent().getSerializableExtra("imovel");
	        imovel = (ImovelConta) Fachada.getInstance()
							.pesquisarPorId(imovel.getId(), imovel);					
			
	        if(imovel!=null){
	        	setUpWidgets();
	        }
        }              
    }    

	private void setUpWidgets() {
		tabelaCategorias  = (TableLayout) findViewById(R.id.tabelaCategorias);  
		
		TextView ligacaoEsgoto = new TextView(InformacoesGeraisActivity.this);
		TextView ligacaoAgua = new TextView(InformacoesGeraisActivity.this);
				
		ligacaoAgua = (TextView) findViewById(R.id.ligacaoAgua);
		ligacaoEsgoto = (TextView) findViewById(R.id.ligacaoEsgoto);
		
		endereco = (TextView) findViewById(R.id.endereco);
		endereco.setText(imovel.getEndereco());
	
		usuario = (TextView) findViewById(R.id.usuario);
		usuario.setText(imovel.getNomeUsuario());		
		
		matricula = (TextView) findViewById(R.id.matricula);
		matricula.setText(imovel.getId()+"");
		
		inscricao = (TextView) findViewById(R.id.inscricao);
		inscricao.setText(imovel.getInscricao()+"");
		
		if (imovel.getSequencialRota()!=null){
			sequencial = (TextView) findViewById(R.id.sequencial);
			sequencial.setText(imovel.getSequencialRota().toString());
		}
		
		ligacaoAgua.setText(Fachada.getInstance().getDescricaoSitLigacaoAgua(imovel.getSituacaoLigAgua()));
		ligacaoEsgoto.setText(Fachada.getInstance().getDescricaoSitLigacaoAgua(imovel.getSituacaoLigEsgoto()));
		
		colCategorias = Fachada.getInstance().
				buscarCategoriaSubcategoriaPorImovelId(imovel.getId());
						
		for(CategoriaSubcategoria categoria : colCategorias){
			
			TableRow row = new TableRow(getApplicationContext());
			tabelaCategorias.addView(row);
			
			// Economia
			TextView economia = new TextView(getApplicationContext());
			economia.setTextSize(12);
			economia.setGravity(Gravity.CENTER);
			economia.setTextColor(Color.BLACK);
			economia.setHeight(34);
			economia.setWidth(20);
			economia.setBackgroundColor(Integer.parseInt("d6e8f4", 16)+0xFF000000);
			economia.setText(categoria.getQtdEconomiasSubcategoria()+"");
			row.addView(economia);
			
			//Cod. Sub
			TextView codigo = new TextView(getApplicationContext(),null,R.style.row);
			codigo.setTextSize(12);
			codigo.setGravity(Gravity.CENTER);
			codigo.setTextColor(Color.BLACK);
			codigo.setBackgroundColor(Integer.parseInt("d6e8f4", 16)+0xFF000000);
			codigo.setHeight(35);
			codigo.setText(categoria.getDescricaoAbreviadaCategoria());
			row.addView(codigo);
			
			//Subcategoria
			TextView sub = new TextView(getApplicationContext(),null,R.style.row);
			sub.setTextSize(12);
			sub.setGravity(Gravity.CENTER);
			sub.setTextColor(Color.BLACK);
			sub.setBackgroundColor(Integer.parseInt("d6e8f4", 16)+0xFF000000);
			sub.setHeight(35);
			sub.setText(categoria.getDescricaoAbreviadaSubcategoria());
			row.addView(sub);
			
				
			//Linha divisória
			TextView linha = new TextView(getApplicationContext());
			LinearLayout.LayoutParams margin = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			margin.setMargins(12,0,15,0);
			linha.setBackgroundColor(Color.parseColor("#afb9c2"));
			linha.setHeight(1);
			tabelaCategorias.addView(linha,margin);	
			
		}
	}
	
	@Override
    public void onBackPressed() { 
		finish();
	}
	

}