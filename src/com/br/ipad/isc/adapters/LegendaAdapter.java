
package com.br.ipad.isc.adapters;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.br.ipad.isc.R;


public class LegendaAdapter extends BaseAdapter  implements OnClickListener{
	
	private LayoutInflater inflater;
	private ArrayList<HashMap<String, Object>> legendasMap;
	private Context c;

	
	public LegendaAdapter(Context context,ArrayList<HashMap<String, Object>> spinnerList){
		c = context;
		this.legendasMap = spinnerList;
		inflater = (LayoutInflater) c
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	


	public View getView(int position, View convertView, ViewGroup parent) {		
		if(convertView==null){
			convertView = inflater.inflate(R.layout.legenda_adapter, null);
		}
		
		if(position != 0){
			((LinearLayout) convertView.findViewById(R.id.llLegenda))
	        .setPadding(10, 10, 10, 10);
		}
			
		((TextView) convertView.findViewById(R.id.legenda))
        .setText((String) legendasMap.get(position).get("Legenda"));
		
		((ImageView) convertView.findViewById(R.id.imagemLegenda))
		        .setBackgroundResource((Integer) legendasMap.get(position).get("Icon"));
				
		convertView.setTag(getItem(position));
				
		return convertView;
	}

	@Override
	public void onClick(View view) {
		
		LinearLayout llLegenda = (LinearLayout) view.findViewById(R.id.llLegenda);
		llLegenda.setPadding(10,10, 10,10);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return legendasMap.size();
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return legendasMap.get(position);
	}
	

}


