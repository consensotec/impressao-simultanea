
package com.br.ipad.isc.adapters;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.util.ConstantesSistema;


public class ListaImovelAdapter extends BaseAdapter{
	
	private LayoutInflater inflater;
	private ArrayList<ImovelConta> imovelContas;
	private Context c;
	
	public ListaImovelAdapter(Context context, ArrayList<ImovelConta> imovelContas){
		c = context;
		this.imovelContas = imovelContas;
		inflater = (LayoutInflater) c
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	

	public int getCount() {
		
		return imovelContas.size();
	}

	public Object getItem(int position) {
		
		return imovelContas.get(position);
	}

	public long getItemId(int position) {
		
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {	
		
		if(convertView==null){
			convertView = inflater.inflate(R.layout.lista_imoveis_adapter, parent,false);			
		}
					
		TextView ordem = (TextView) convertView.findViewById(R.id.posicao);
		DecimalFormat myFormat =  new DecimalFormat("000");
		ordem.setText(myFormat.format(imovelContas.get(position).getPosicao()));
		TextView nome = (TextView) convertView.findViewById(R.id.nome);
		nome.setText(imovelContas.get(position).getNomeUsuario());
		TextView endereco = (TextView) convertView.findViewById(R.id.endereco);
		endereco.setText(imovelContas.get(position).getEndereco());
		
		//Se for imóvel condomínio
		TextView posicaoCondominio = (TextView) convertView.findViewById(R.id.posicaoCondominio);
		if(imovelContas.get(position).isCondominio()){
			posicaoCondominio.setVisibility(View.VISIBLE);
			posicaoCondominio.setText(myFormat.format(imovelContas.get(position).getPosicaoImovelCondominio()));
		} else {
			posicaoCondominio.setText(myFormat.format(0));
			posicaoCondominio.setVisibility(View.INVISIBLE);
		}
						
		TextView status = (TextView) convertView.findViewById(R.id.status);
		if(imovelContas.get(position).getIndcImovelCalculado().equals(ConstantesSistema.SIM)
				&& imovelContas.get(position).getIndcImovelImpresso().equals(ConstantesSistema.SIM)){
			
			status.setBackgroundResource(R.drawable.bgimovelinicia);
			ordem.setBackgroundResource(R.drawable.bgnumero);

		}else if(imovelContas.get(position).getIndcImovelCalculado().equals(ConstantesSistema.NAO)
				&& imovelContas.get(position).getIndcImovelImpresso().equals(ConstantesSistema.NAO)){

			status.setBackgroundResource(R.drawable.bgimovelparado);
			ordem.setBackgroundResource(R.drawable.bgnumeroparado);
		}else{
			status.setBackgroundResource(R.drawable.bgimovelcalculado);
			ordem.setBackgroundResource(R.drawable.bgnumerocalculado);
		}
		
/*		HidrometroInstalado objHidrometroAgua = null;
		HidrometroInstalado objHidrometroEsgoto = null;
		try {
			objHidrometroAgua = Fachada.getInstance().buscarHidrometroInstaladoPorImovelTipoMedicao(imovelContas.get(position).getId(),HidrometroInstalado.TIPO_AGUA);
			objHidrometroEsgoto = Fachada.getInstance().buscarHidrometroInstaladoPorImovelTipoMedicao(imovelContas.get(position).getId(),HidrometroInstalado.TIPO_ESGOTO);
			
		} catch (FachadaException e) {
			Log.e( ConstantesSistema.CATEGORIA , e.getMessage());
			e.printStackTrace();
		}
		
		if(objHidrometroAgua != null){
			LinearLayout llAgua = (LinearLayout) convertView.findViewById(R.id.llHidrometroAgua);
			llAgua.setVisibility(View.VISIBLE);
			TextView hidrometroAgua = (TextView) convertView.findViewById(R.id.hidrometroAgua);
			hidrometroAgua.setText(objHidrometroAgua.getNumeroHidrometro()); */
//			TextView hidrometroAgua = new TextView(c);
//			hidrometroAgua.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
//			LinearLayout.LayoutParams aguaMargin = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//			aguaMargin.setMargins(10, 0, 2, 0);
//			hidrometroAgua.setTextColor(Color.BLACK);
//			hidrometroAgua.setText(R.string.str_imoveis_hidrometro_agua);
//			llAgua.addView(hidrometroAgua,aguaMargin);
//			
//			TextView hidrometroAguaText = new TextView(c);
//			hidrometroAguaText.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
//			hidrometroAguaText.setTextColor(Color.BLACK);
//			hidrometroAguaText.setTypeface(null, Typeface.BOLD);
//			hidrometroAguaText.setText(objHidrometroAgua.getNumeroHidrometro());
//			llAgua.addView(hidrometroAguaText);
			
		/*			}
		if(objHidrometroEsgoto != null){
			LinearLayout llEsgoto = (LinearLayout) convertView.findViewById(R.id.llHidrometroEsgoto);
			llEsgoto.setVisibility(View.VISIBLE);
			TextView hidrometroEsgoto = (TextView) convertView.findViewById(R.id.hidrometroEsgoto);
			hidrometroEsgoto.setText(objHidrometroEsgoto.getNumeroHidrometro()); */
//			
//			TextView hidrometroEsgoto = new TextView(c);
//			hidrometroEsgoto.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
//			LinearLayout.LayoutParams esgotoMargin = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//			esgotoMargin.setMargins(10, 0, 2, 0);
//			hidrometroEsgoto.setTextColor(Color.BLACK);
//			hidrometroEsgoto.setText(R.string.str_imoveis_hidrometro_esgoto);
//			llEsgoto.addView(hidrometroEsgoto,esgotoMargin);
//			
//			TextView hidrometroEsgotoText = new TextView(c);
//			hidrometroEsgotoText.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
//			hidrometroEsgotoText.setTextColor(Color.BLACK);
//			hidrometroEsgotoText.setTypeface(null, Typeface.BOLD);
//			hidrometroEsgotoText.setText(objHidrometroEsgoto.getNumeroHidrometro());
//			llEsgoto.addView(hidrometroEsgotoText);
//		}
		 
		convertView.setTag(getItem(position));
		
		return convertView;
	}
	

}


