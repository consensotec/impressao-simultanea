package com.br.ipad.isc.gui;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.Foto;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.bean.LeituraAnormalidade;
import com.br.ipad.isc.bean.helpers.CameraHelper;
import com.br.ipad.isc.fachada.Fachada;
import com.br.ipad.isc.util.ConstantesSistema;
import com.br.ipad.isc.util.Util;

public class ImagemSalvarActivity extends Activity {

	private ImageView imagem;
	private Button btSalvar;
	private Button btTirarNovamente;
	private String diretorio;
	private Button btSair;
	private ImovelConta imovel;
	private Integer medicaoTipo;
	private Integer fotoTipo;
    private Integer idLeituraAnormalidade;
    private CameraHelper helper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Fachada.setContext(this);
		setContentView(R.layout.imagem_salvar_activity);

		//Informações necessárias para inserção da foto tirada no banco
		helper = (CameraHelper) getIntent().getSerializableExtra("helper");
		imovel = helper.getImovel();
		medicaoTipo = helper.getMedicaoTipo();
        idLeituraAnormalidade = helper.getIdLeituraAnormalidade();
        fotoTipo = helper.getFotoTipo();
		//
        
		imagem = (ImageView) findViewById(R.id.imagem);
		diretorio = getIntent().getStringExtra("foto");
		Bitmap foto = BitmapFactory.decodeFile(diretorio);

		//uso de menos memoria
		imagem.setDrawingCacheEnabled(false);

		imagem.setImageBitmap(foto);

		btSalvar = (Button) findViewById(R.id.btsalvar);
		btTirarNovamente = (Button) findViewById(R.id.btretirar);
		btSair = (Button) findViewById(R.id.btsair);

		btTirarNovamente.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				deletaFoto();
								
				Intent it = new Intent(ImagemSalvarActivity.this,CameraActivity.class);
				it.putExtra("helper", helper);
				startActivity(it);
			}
		});

		btSair.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				deletaFoto();
				
				finish();
			}
		});

		btSalvar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				String dataAtual = Util.convertDateToDateStrFile();

				Foto foto = new Foto();

				foto.setIndicadorTransmitido(ConstantesSistema.NAO);
				foto.setDataFoto(dataAtual);
				foto.setImovelConta(imovel);
				foto.setCaminho(diretorio);
				LeituraAnormalidade leituraAnormalidade = new LeituraAnormalidade();
				leituraAnormalidade.setId(idLeituraAnormalidade);
				foto.setLeituraAnormalidade(leituraAnormalidade);

				if  (fotoTipo.equals(ConstantesSistema.FOTO_IMOVEL)) {
					foto.setFotoTipo(ConstantesSistema.FOTO_IMOVEL);

				} else if ( fotoTipo.equals(ConstantesSistema.FOTO_ANORMALIDADE) ) {
					foto.setFotoTipo(ConstantesSistema.FOTO_ANORMALIDADE);
				}

				foto.setTipoMedicao(medicaoTipo);

				Fachada.getInstance().inserir(foto);				

				Intent it = new Intent(ImagemSalvarActivity.this,FotoActivity.class);
				it.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				it.putExtra("helper", helper);
              	
				startActivity(it);

			}
		});

	}

	protected void deletaFoto() {
		File foto = new File(diretorio);
		if(foto.isFile()){
			foto.delete();
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		deletaFoto();
		
		Intent it = new Intent(ImagemSalvarActivity.this, FotoActivity.class);
		it.putExtra("helper", helper);
		it.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		startActivity(it);
	}
}