
package com.br.ipad.isc.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.helpers.Impressora;


public class ListaImpressoraAdapter extends BaseAdapter{
	
	private LayoutInflater inflater;
	private ArrayList<Impressora> impressoras;
	private Context c;
	
	public ListaImpressoraAdapter(Context context, ArrayList<Impressora> impressoras){
		c = context;
		this.impressoras = impressoras;
		inflater = (LayoutInflater) c
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	

	public int getCount() {
		
		return impressoras.size();
	}

	public Object getItem(int position) {
		
		return impressoras.get(position);
	}

	public long getItemId(int position) {
		
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {		
		if(convertView==null){
			convertView = inflater.inflate(R.layout.lista_impressoras_adapter, null);
		}
				
		TextView nome = (TextView) convertView.findViewById(R.id.nomeImpressora);
		nome.setText(impressoras.get(position).getBluetoothName());
		TextView endereco = (TextView) convertView.findViewById(R.id.enderecoBluetooth);
		endereco.setText(impressoras.get(position).getBluetoothAdress());
		
				
 
		convertView.setTag(getItem(position));
		
		return convertView;
	}
	

}


