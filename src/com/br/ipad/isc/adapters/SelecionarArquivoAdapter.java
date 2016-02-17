
package com.br.ipad.isc.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.br.ipad.isc.R;

public class SelecionarArquivoAdapter extends BaseAdapter{
	
	private LayoutInflater inflater;
	private ArrayList<String> files;
	private Context c;
	
	public SelecionarArquivoAdapter(Context context, ArrayList<String> files){
		c = context;
		this.files = files;
		inflater = (LayoutInflater) c
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		
		return files.size();
	}

	public Object getItem(int position) {
		
		return files.get(position);
	}

	public long getItemId(int position) {
		
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView==null){
			convertView = inflater.inflate(R.layout.selecionar_arquivo_adapter, null);
		}
		TextView name = (TextView) convertView.findViewById(R.id.name);
		name.setText(files.get(position));
		if(position % 2 == 0){
			name.setBackgroundColor(Color.WHITE);
		}else{
			name.setBackgroundColor(Color.parseColor("#d8d7d7"));
		}
		
		convertView.setTag(getItem(position));
		
		return convertView;
	}

}
