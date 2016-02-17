package com.br.ipad.isc.gui;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.ConsumoAnormalidade;
import com.br.ipad.isc.bean.Foto;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.bean.LeituraAnormalidade;
import com.br.ipad.isc.bean.SistemaParametros;
import com.br.ipad.isc.bean.helpers.CameraHelper;
import com.br.ipad.isc.excecoes.FachadaException;
import com.br.ipad.isc.excecoes.NegocioException;
import com.br.ipad.isc.fachada.Fachada;
import com.br.ipad.isc.util.ConstantesSistema;
import com.br.ipad.isc.util.Util;

public class FotoActivity extends BaseActivity implements OnClickListener, OnLongClickListener {

    private Intent                    intent;

    private LinearLayout              llImovel;

    private LinearLayout              llAnormalidade;

    private ImageView                 imgImovel;

    private ImageView                 imgAnormalidade;

    private ImovelConta               imovel;

    private Integer                   tipoFoto;

    private TextView                  tvDate;

    private TextView                  tvImovel;

    private LinearLayout.LayoutParams llParams;
    
    private Integer                   idLeituraAnormalidade;
    
    private Integer 				  idConsumoAnormalidade;
    
    private Integer                   medicaoTipo;
    
    private CameraHelper              helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		Fachada.setContext(this);

		if (execute){	
	        setContentView(R.layout.foto);
	
	        llParams = new LinearLayout.LayoutParams(85,
	                                                 85);
	        
	        //Informações necessárias para inserção da foto tirada no banco
			helper = (CameraHelper) getIntent().getSerializableExtra(ConstantesSistema.FOTO_HELPER);
			imovel = helper.getImovel();
			medicaoTipo = helper.getMedicaoTipo();
	        idLeituraAnormalidade = helper.getIdLeituraAnormalidade();
	        idConsumoAnormalidade = helper.getIdConsumoAnormalidade();
			//
	        
	        tipoFoto = 0;
	
	        llImovel = (LinearLayout) findViewById(R.id.llImovel);
	        llAnormalidade = (LinearLayout) findViewById(R.id.llAnormalidade);
	
	        imgImovel = (ImageView) findViewById(R.id.imgImovel);
	        imgAnormalidade = (ImageView) findViewById(R.id.imgAnormalidade);
	
	        this.reloadImagesBitmaps();
	
	        llImovel.setOnClickListener(this);
	        llImovel.setOnLongClickListener(this);
	
	        llAnormalidade.setOnClickListener(this);
	        llAnormalidade.setOnLongClickListener(this);
	
	
	        tvImovel = (TextView) findViewById(R.id.imovelText);
	        tvImovel = (TextView) findViewById(R.id.imovelText);
	      
	        
	        tvImovel.setText(String.valueOf(imovel.getId()));
	               
	        tvDate = (TextView) findViewById(R.id.dateText);
	        tvDate = (TextView) findViewById(R.id.dateText);
	        tvDate.setText(Util.convertDateToDateStr(Util.getCurrentDateTime()));
        }
    }

    private void reloadImagesBitmaps() {
        
    	
		try {
			
			Foto fotoImovel = Fachada.getInstance().buscarFotoTipo(imovel.getId(), ConstantesSistema.FOTO_IMOVEL, medicaoTipo,idLeituraAnormalidade,idConsumoAnormalidade);

	    	if ( fotoImovel != null ) {
	    	
	            imgImovel.setImageBitmap(Util.getBitmap(fotoImovel.getCaminho()));
	            imgImovel.setLayoutParams(llParams);
	        } else {
	            imgImovel.setImageResource(R.drawable.camera);
	        }

	    	Foto fotoAnormalidade = Fachada.getInstance().buscarFotoTipo(imovel.getId(), ConstantesSistema.FOTO_ANORMALIDADE, medicaoTipo,idLeituraAnormalidade,idConsumoAnormalidade);

	    	if ( fotoAnormalidade != null ) {
	    	
	    		imgAnormalidade.setImageBitmap(Util.getBitmap(fotoAnormalidade.getCaminho()));
	            imgAnormalidade.setLayoutParams(llParams);
	        } else {
	        	 imgAnormalidade.setImageResource(R.drawable.camera);
	        }

	    	
	    	
		} catch (FachadaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NegocioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Override
    public void onClick(View v) {

        v.setBackgroundResource(android.R.drawable.list_selector_background);

        switch (v.getId()) {
            case R.id.llImovel:
                tipoFoto = ConstantesSistema.FOTO_IMOVEL;
                break;
            case R.id.llAnormalidade:
                tipoFoto = ConstantesSistema.FOTO_ANORMALIDADE;
                break;
           
            default:
                Log.e(ConstantesSistema.CATEGORIA, "onClick " + getClass().getName() + " " + v.getId());
        }

	      //Caso Compesa Iniciar Camera com circulo vermelho
	   	 if(SistemaParametros.getInstancia().getCodigoEmpresaFebraban()
	   			 .equals(ConstantesSistema.CODIGO_FEBRABAN_COMPESA)){
	   	
	   		if (tipoFoto != 0) {
	            intent = new Intent(FotoActivity.this,CameraActivity.class);
	            CameraHelper helper = new CameraHelper(imovel, medicaoTipo, idLeituraAnormalidade,idConsumoAnormalidade, tipoFoto);
	            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
	            intent.putExtra("helper", helper);
	            startActivity(intent);
	        }
	   		 
	   	 }else{
	         if (tipoFoto != 0) {
	             intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	             startActivityForResult(intent, ConstantesSistema.CAMERA);
	         }
	   	 }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        Util.existeSDCARD();

        // verifica se é o código de foto
        if (requestCode == ConstantesSistema.CAMERA) {

            // verifica se a foto foi salva
            if (resultCode == RESULT_OK) {
                try {

                	File pastaFoto = new File(ConstantesSistema.CAMINHO_FOTOS);
                	if(!pastaFoto.exists()){
                		pastaFoto.mkdirs();
                	}
        			

                    // pega a foto
                    Bundle bundle = data.getExtras();
                    
                    //Pesquisar a colecao de fotos do imovel e iterare por aquire
                    String dataFoto = Util.obterAAAAMMDDHHMMSS(new Date());
                    String dataAtual = Util.convertDateToDateStrFile();
                    
                    String caminhoFoto = ConstantesSistema.CAMINHO_FOTOS+ "/"+imovel.getId()+"_"+tipoFoto+"_";
                    
                    if(idLeituraAnormalidade!=null)
                    {
                    	caminhoFoto+=ConstantesSistema.FOTO_TIPO_LEITURA_ANORMALIDADE+"_"+idLeituraAnormalidade;
                    	
                    }
                    else if(idConsumoAnormalidade!=null)
                    {
                    	caminhoFoto+=ConstantesSistema.FOTO_TIPO_CONSUMO_ANORMALIDADE+"_"+idConsumoAnormalidade;
                    }
                    
                    caminhoFoto+="_"+dataFoto+".jpg";
                    
                    FileOutputStream out = new FileOutputStream( caminhoFoto );
                    Bitmap bmp = (Bitmap) bundle.get("data");

                    // transforma o arquivo na foto tirada e grava o bmp
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);

                    Foto foto = new Foto();
                    
                    foto.setIndicadorTransmitido(ConstantesSistema.NAO);
                    foto.setDataFoto(dataAtual);
                    foto.setImovelConta(imovel);
                    foto.setCaminho(caminhoFoto);
                    
                    if(idLeituraAnormalidade!=null)
                    {
                    	LeituraAnormalidade leituraAnormalidade = new LeituraAnormalidade();
                        
                    	leituraAnormalidade.setId(idLeituraAnormalidade);
                        
                    	foto.setLeituraAnormalidade(leituraAnormalidade);
                    }
                    else if(idConsumoAnormalidade!=null)
                    {
                    	ConsumoAnormalidade consumoAnormalidade = new ConsumoAnormalidade();
                    	
                    	consumoAnormalidade.setId(idConsumoAnormalidade);
                    	
                    	foto.setConsumoAnormalidade(consumoAnormalidade);
                    }
                    
                    if  (tipoFoto == ConstantesSistema.FOTO_IMOVEL) {
                    	foto.setFotoTipo(ConstantesSistema.FOTO_IMOVEL);
                    
                    } else if ( tipoFoto == ConstantesSistema.FOTO_ANORMALIDADE ) {
                    	foto.setFotoTipo(ConstantesSistema.FOTO_ANORMALIDADE);
                    }
                        
                    foto.setTipoMedicao(medicaoTipo);
                    
                    Fachada.getInstance().inserir(foto);

                    this.reloadImagesBitmaps();
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean onLongClick(View v) {

        v.setBackgroundResource(android.R.drawable.list_selector_background);

        switch (v.getId()) {
            case R.id.llImovel:
                tipoFoto = ConstantesSistema.FOTO_IMOVEL;
                break;
            case R.id.llAnormalidade:
                tipoFoto = ConstantesSistema.FOTO_ANORMALIDADE;
                break;
           
            default:
        }

        return true;
    }
    
    @Override
    public void onBackPressed() {
    	super.onBackPressed();
    }
    
 
}