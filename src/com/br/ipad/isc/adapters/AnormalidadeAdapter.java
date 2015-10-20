
package com.br.ipad.isc.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.LeituraAnormalidade;


public class AnormalidadeAdapter extends BaseAdapter{
	
	private LayoutInflater inflater;
	private ArrayList<LeituraAnormalidade> leituraAnormalidade;
	private Context c;
	
	public AnormalidadeAdapter(Context context, ArrayList<LeituraAnormalidade> leituraAnormalidade){
		
		c = context;
		this.leituraAnormalidade = leituraAnormalidade;
		LeituraAnormalidade objReason = new LeituraAnormalidade();
		objReason.setId(0);
		this.leituraAnormalidade.add(0, objReason);
		inflater = (LayoutInflater) c
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		
		return leituraAnormalidade.size();
	}

	public Object getItem(int position) {
		
		return leituraAnormalidade.get(position);
	}

	public long getItemId(int position) {
		
		return position;
	}
	
	public int getPosition(LeituraAnormalidade leitAnormalidade){
		for(LeituraAnormalidade anormalidade : this.leituraAnormalidade){
			if(leitAnormalidade.getId().equals(anormalidade.getId())){
				return this.leituraAnormalidade.indexOf(anormalidade);
			}
		}
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		
		DisplayMetrics displayMetrics = new DisplayMetrics();
		WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE); 
		wm.getDefaultDisplay().getMetrics(displayMetrics);
		int screenWidth = displayMetrics.widthPixels;
		int screenHeight = displayMetrics.heightPixels;
		
		if(convertView==null){
			convertView = inflater.inflate(R.layout.anormalidade_adapter, null);
		}
		TextView name = (TextView) convertView.findViewById(R.id.descricao);
		if(position==0){
			name.setText("Selecione");
		}else{
			name.setText(leituraAnormalidade.get(position).getDescricaoAnormalidadeLeitura());
		}
		
		name.setWidth(250);
		name.setGravity(Gravity.CENTER_VERTICAL);
		convertView.setTag(getItem(position));
		
		return convertView;
	}

}
