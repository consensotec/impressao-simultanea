
package com.br.ipad.isc.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.helpers.RelatorioPorQuadra;


public class ListaRelatorioPorQuadraAdapter extends BaseAdapter implements OnClickListener{
	
	private LayoutInflater inflater;
	private ArrayList<RelatorioPorQuadra> relatorioPorQuadra;
	private Context c;

	
	public ListaRelatorioPorQuadraAdapter(Context context, ArrayList<RelatorioPorQuadra> relatorioPorQuadra){
		c = context;
		this.relatorioPorQuadra = relatorioPorQuadra;
		inflater = (LayoutInflater) c
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	

	public int getCount() {
		
		return relatorioPorQuadra.size();
	}

	public Object getItem(int position) {
		
		return relatorioPorQuadra.get(position);
	}

	public long getItemId(int position) {
		
		return position;
	}

	
	public void onClick(View convertView) {
		
		LinearLayout detalhesQuadra = (LinearLayout) convertView.findViewById(R.id.detalhesQuadra);
		LinearLayout resumoQuadra = (LinearLayout) convertView.findViewById(R.id.resumoQuadra);
		TextView numeroQuadra = (TextView) convertView.findViewById(R.id.numeroQuadra);
		
		TextView percentualTotalRealizado = (TextView) convertView.findViewById(R.id.percentualTotalRealizadoQuadra);
		
		
		if(detalhesQuadra.getVisibility() == View.VISIBLE ){
			
			  
			detalhesQuadra.setVisibility(View.GONE);  
			TranslateAnimation slide = new TranslateAnimation(0, 0, 0, 0 );   
			slide.setDuration(1000);   
			slide.setFillAfter(true);   
			detalhesQuadra.startAnimation(slide);
			
			resumoQuadra.setBackgroundResource(R.drawable.fundocinza_bg);
			numeroQuadra.setTextColor(Color.BLACK);
			percentualTotalRealizado.setTextColor(Color.BLACK);
			
		}else{
			
			detalhesQuadra.setVisibility(View.VISIBLE);  
			TranslateAnimation slide = new TranslateAnimation(0, 0, 0, 0 );   
			slide.setDuration(1000);   
			slide.setFillAfter(true);   
			detalhesQuadra.startAnimation(slide);
			
			resumoQuadra.setBackgroundResource(R.drawable.fundoazulclaro_bg);
			numeroQuadra.setTextColor(Color.WHITE);
			percentualTotalRealizado.setTextColor(Color.WHITE);
		}
		
		 
	}
	
	
	public View getView(int position, View convertView, ViewGroup parent) {		
		if(convertView==null){
			convertView = inflater.inflate(R.layout.lista_relatorio_por_quadra_adapter, null);
		}
		
		convertView.setOnClickListener(this);
		
		
		int imoveisVisitados = relatorioPorQuadra.get(position).getTotalImoveisVisitados().intValue();
		int totalImoveis = relatorioPorQuadra.get(position).getTotalImoveis().intValue();
		int imoveisNaoVisitados = relatorioPorQuadra.get(position).getTotalImoveisNaoVisitados().intValue();
		int porcentagemRealizada = (int)(((double)imoveisVisitados/totalImoveis)*100);
		
		TextView totalImoveisQuadra = (TextView) convertView.findViewById(R.id.totalImoveisQuadra);
		totalImoveisQuadra.setText("Total: "+totalImoveis);
		
		TextView totalImoveisVisitadosQuadra = (TextView) convertView.findViewById(R.id.totalImoveisVisitadosQuadra);
		totalImoveisVisitadosQuadra.setText("Visitados: "+imoveisVisitados);
		
		TextView totalImoveisNaoVisitadosQuadra = (TextView) convertView.findViewById(R.id.totalImoveisNaoVisitadosQuadra);
		totalImoveisNaoVisitadosQuadra.setText("A visitar: "+imoveisNaoVisitados);
		
		TextView percentualTotalRealizado = (TextView) convertView.findViewById(R.id.percentualTotalRealizadoQuadra);
		percentualTotalRealizado.setText(porcentagemRealizada + "%" );
		
		TextView numeroQuadra = (TextView) convertView.findViewById(R.id.numeroQuadra);
		numeroQuadra.setText("Quadra "+ relatorioPorQuadra.get(position).getNumeroQuadra());

		//int numeroQuadraD = relatorioPorQuadra.get(position).getNumeroQuadra().intValue();
		
		TextView totalRealizado = (TextView) convertView.findViewById(R.id.totalRealizadoQuadraDetalhe);
		totalRealizado.setText("Realizado: " + porcentagemRealizada + "%" );
		
		convertView.setTag(getItem(position));
		
		
		
		return convertView;
	}
	

}


