
package com.br.ipad.isc.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.SistemaParametros;
import com.br.ipad.isc.bean.helpers.Menu;
import com.br.ipad.isc.util.ConstantesSistema;


public class ListaMenuAdapter extends BaseAdapter{
	
	private LayoutInflater inflater;
	private ArrayList<Menu> menu;
	private Context c;
	
	public ListaMenuAdapter(Context context, ArrayList<Menu> menu){
		c = context;
		this.menu = menu;
		inflater = (LayoutInflater) c
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	

	
	public int getCount() {
		
		return menu.size();
	
	}

	public Object getItem(int position) {
		
		return menu.get(position);
	}

	public long getItemId(int position) {
		
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {		
		if(convertView==null){
			convertView = inflater.inflate(R.layout.lista_menu_adapter, null);
		}
		
		ImageView image = (ImageView) convertView.findViewById(R.id.imgmenu);
		
		
		TextView nome = (TextView) convertView.findViewById(R.id.nome);
		nome.setText(menu.get(position).getNome());
		
		if(menu.get(position).getNome().equals(c.getString(R.string.str_menu_lista))){
			image.setImageResource(R.drawable.listimoveis);
		} else if(menu.get(position).getNome().equals(c.getString(R.string.str_menu_consulta))){
			image.setImageResource(R.drawable.consimovel);
		} else if(menu.get(position).getNome().equals(c.getString(R.string.str_menu_finaliza))){
			image.setImageResource(R.drawable.finalroteiro);
		} else if(menu.get(position).getNome().equals(c.getString(R.string.str_menu_relatorio))){
			image.setImageResource(R.drawable.relatorios);
		} else if(menu.get(position).getNome().equals(c.getString(R.string.str_menu_impressora))){
			image.setImageResource(R.drawable.selimpressora);
		} else if(menu.get(position).getNome().equals(c.getString(R.string.str_menu_apagar))){
			image.setImageResource(R.drawable.excluir);
		} else if(menu.get(position).getNome().equals(c.getString(R.string.str_menu_finaliza_incompleto))){
			image.setImageResource(R.drawable.roteiroincomp);
		} else if(menu.get(position).getNome().equals(c.getString(R.string.str_menu_enviar_finalizados))){
			image.setImageResource(R.drawable.envimoveis);	
		} else if(menu.get(position).getNome().equals(c.getString(R.string.str_menu_roteiro_offline))){
			image.setImageResource(R.drawable.roteirooff);	
		} else if(menu.get(position).getNome().equals(c.getString(R.string.str_menu_roteiro_online))){
			image.setImageResource(R.drawable.roteiroon);	
		} else if(menu.get(position).getNome().equals(c.getString(R.string.str_menu_gerar_retorno))){
			image.setImageResource(R.drawable.arqretorno);	
		} else if(menu.get(position).getNome().equals(c.getString(R.string.str_menu_rota))){
			
			if(SistemaParametros.getInstancia().getIndicadorRotaMarcacaoAtiva().equals(ConstantesSistema.SIM)){
				image.setImageResource(R.drawable.rotamarcacao);	
			}else{
				//ROTA INATIVA
				image.setImageResource(R.drawable.rotamarcacao);
			}
			
		} else if(menu.get(position).getNome().equals(c.getString(R.string.str_menu_sair))){
			image.setImageResource(R.drawable.exit);
		} else if(menu.get(position).getNome().equals(c.getString(R.string.str_menu_exportar_bd))){
			image.setImageResource(R.drawable.export_database);
		} else if(menu.get(position).getNome().equals(c.getString(R.string.str_menu_contraste))){
			image.setImageResource(R.drawable.contraste);			
		} else {
			image.setImageResource(R.drawable.noicon);
		}
		
		TextView detalhe = (TextView) convertView.findViewById(R.id.detalhe);
		detalhe.setText(menu.get(position).getLegenda());
				 
		convertView.setTag(getItem(position));
		
		return convertView;
	}
	

}


