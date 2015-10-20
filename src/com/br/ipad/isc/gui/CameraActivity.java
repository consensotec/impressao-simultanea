package com.br.ipad.isc.gui;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ImageFormat;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.hardware.Camera;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.helpers.CameraHelper;
import com.br.ipad.isc.util.ConstantesSistema;
import com.br.ipad.isc.util.Util;

public class CameraActivity extends Activity {
	private CustomDrawableView preview=null;
	private SurfaceHolder previewHolder=null;
	private Camera camera=null;
	private boolean inPreview=false;
	private boolean cameraConfigured=false;

	private CameraHelper helper;
	
	private Button btTirarFoto;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);

		//Caso não haja sdcard ou não esteja no modo correto, manda pra tela de escolher o tipo de foto novamente
		if(!isSDPresent){
			
			new AlertDialog.Builder(CameraActivity.this)
			.setMessage(getString(R.string.str_camera_sd))
			.setNeutralButton(getString(android.R.string.ok), 
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int which) {
						Intent it = new Intent(CameraActivity.this,FotoActivity.class);
						it.putExtra("helper", helper);
						it.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
						startActivity(it);
					}
			}).show();
			
		}
		
		setContentView(R.layout.camera_activity);

		//Informações necessárias para inserção da foto tirada no banco
		helper = (CameraHelper) getIntent().getSerializableExtra("helper");
        //
        
		btTirarFoto = (Button) findViewById(R.id.btTirarFoto);
		
		btTirarFoto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				btTirarFoto.setEnabled(false);
				try{
					camera.takePicture(mShutterCallback, mPictureCallbackRaw, mPictureCallbackJpeg);
				}catch(Exception e){
					Log.e(ConstantesSistema.CATEGORIA, e.getMessage());
					e.printStackTrace();
				}
			}
		});

		preview=(CustomDrawableView)findViewById(R.id.preview);
		previewHolder=preview.getHolder();
		previewHolder.addCallback(surfaceCallback);
		previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	@Override
	public void onResume() {
		super.onResume();

		camera=Camera.open();
		startPreview();
	}

	@Override
	public void onPause() {
		super.onPause();
		btTirarFoto.setEnabled(true);
		if (inPreview) {
			camera.stopPreview();
		}
		if(camera != null){
			camera.release();
			camera=null;
			inPreview=false;
		}

	}
	
	@Override
	protected void onDestroy() {
		
		super.onDestroy();
		if (inPreview) {
			camera.stopPreview();
		}
		if(camera != null){
			camera.release();
			camera=null;
			inPreview=false;
		}
	}


	private Camera.Size getBestPreviewSize(int width, int height,
			Camera.Parameters parameters) {
		Camera.Size result=null;

		for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
			//Caso galaxy Y
			if(width==427){
			
				if (size.width<=width && size.height<=height) {
					if (result==null) {
						result=size;
					}
					else {
						int resultArea=result.width*result.height;
						int newArea=size.width*size.height;

						if (newArea>resultArea) {
							result=size;
						}
					}
				}
			}else{
			
				//Caso Defy
				if (size.width>=width && size.height>=height) {
					if (result==null) {
						result=size;
					}
					else {
						int resultArea=result.width*result.height;
						int newArea=size.width*size.height;
	
						if (newArea>resultArea) {
							result=size;
						}
					}
				}
			}
		}

		return(result);
	}
	
	
	private void initPreview(int width, int height) {
		if (camera!=null && previewHolder.getSurface()!=null) {
			try {
				camera.setPreviewDisplay(previewHolder);
			}
			catch (Throwable t) {
				Log.e("PreviewDemo-surfaceCallback",
						"Exception in setPreviewDisplay()", t);
				Toast
				.makeText(CameraActivity.this, t.getMessage(), Toast.LENGTH_LONG)
				.show();
			}
			if (!cameraConfigured) {
				Camera.Parameters parameters=camera.getParameters();
				if(parameters.getSupportedPreviewSizes() == null){
					camera.release();
				}
				Camera.Size size=getBestPreviewSize(width, height, parameters);

				if (size != null ) {
					parameters.setPreviewSize(size.width, size.height);

					parameters.getZoom();
//					parameters.set("orientation", "portrait");
//					parameters.setRotation(90);
					parameters.setPictureFormat(ImageFormat.JPEG);
					
					
					
					camera.setParameters(parameters);
					cameraConfigured=true;
				}
			}
		}
	}

	private void startPreview() {
		if (cameraConfigured && camera!=null) {
			camera.startPreview();
			inPreview=true;
		}
	}

	SurfaceHolder.Callback surfaceCallback=new SurfaceHolder.Callback() {
		public void surfaceCreated(SurfaceHolder holder) {
//			camera.setDisplayOrientation(90);
			// no-op -- wait until surfaceChanged()
		}

		public void surfaceChanged(SurfaceHolder holder,
				int format, int width,
				int height) {			
//			camera.setDisplayOrientation(90);
			initPreview(width, height);
			startPreview();
		}

		public void surfaceDestroyed(SurfaceHolder holder) {
			if(camera!=null){
				camera.release();
			}
		}
	};

	Camera.PictureCallback mPictureCallbackRaw = new Camera.PictureCallback() {
		public void onPictureTaken(byte[] data, Camera c) {
			Log.e(getClass().getSimpleName(), "PICTURE CALLBACK RAW: " + data);

		};
	};
	
	Camera.ShutterCallback mShutterCallback = new Camera.ShutterCallback() {
		public void onShutter() {
			Log.e(getClass().getSimpleName(), "SHUTTER CALLBACK");
		}
	};

	Camera.PictureCallback mPictureCallbackJpeg= new Camera.PictureCallback() {
		public void onPictureTaken(byte[] data, Camera c) {

			new SavePhotoTask().execute(data);
			if(camera != null){
				//camera.startPreview();
				//inPreview=true;
			}
		}

		//			onp
	};

	class SavePhotoTask extends AsyncTask<byte[], String, String> {
		@Override
		protected String doInBackground(byte[]... data) {

	        Util.existeSDCARD();
	        File pastaFoto = new File(ConstantesSistema.CAMINHO_ISC+"/fotos");
			if(!pastaFoto.isDirectory()){
				pastaFoto.mkdirs();
			}
			
			final String nomeArquivo = ConstantesSistema.CAMINHO_FOTOS + "/"+ helper.getImovel().getId()+"_"+helper.getFotoTipo()+"_"+Util.obterAAAAMMDDHHMMSS(new Date())+".jpg";
			File imagesFolder = new File(ConstantesSistema.CAMINHO_FOTOS +"/"+ nomeArquivo);
			
			Uri uriSavedImage = Uri.fromFile(imagesFolder);

			Bitmap bmp = BitmapFactory.decodeByteArray(data[0], 0,data[0].length);     
			
			//Desenha o circulo na tela
			ShapeDrawable circulo = new ShapeDrawable(new OvalShape());
			Util.desenhaCirculo(getApplicationContext(), circulo);
			
			Bitmap cs = Bitmap.createBitmap(getWindowManager().getDefaultDisplay().getWidth(), getWindowManager().getDefaultDisplay().getHeight(), Bitmap.Config.ARGB_8888);
			Canvas comboImage = new Canvas(cs);
			circulo.draw(comboImage);
			
			// Junta, em uma imagem, a foto tirada com o círculo
			Bitmap bmpRetorno = combinaImagens(bmp, cs);

			OutputStream imageFileOS;
			try {
				imageFileOS = getContentResolver().openOutputStream(uriSavedImage);
				//Cria a imagem com 70% de qualidade
				bmpRetorno.compress(Bitmap.CompressFormat.JPEG, 70, imageFileOS);

				imageFileOS.flush();
				imageFileOS.close();


			} catch (FileNotFoundException e) {
				Log.e(ConstantesSistema.CATEGORIA,e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				Log.e(ConstantesSistema.CATEGORIA,e.getMessage());
				e.printStackTrace();
			}

			Intent it = new Intent(CameraActivity.this,ImagemSalvarActivity.class);
			it.putExtra("foto", imagesFolder.getAbsolutePath());
            it.putExtra("helper", helper);
			startActivity(it);

			return(null);
		}
	}

	public Bitmap combinaImagens(Bitmap background, Bitmap foreground) { 

		int width = 0, height = 0;
				
		// tamanho da tela
		width = getWindowManager().getDefaultDisplay().getWidth();
		height = getWindowManager().getDefaultDisplay().getHeight();

		// Desenha uma imagem em cima da outra
		Bitmap cs = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas comboImage = new Canvas(cs);
//		Matrix m = new Matrix();
//		m.setRotate(90);
//		m.setTranslate(0, 0);
//		background = Bitmap.createBitmap(background, 0, 0, background.getWidth(), background.getHeight(), m, true);
		background = Bitmap.createScaledBitmap(background, width, height, true);
		
		
		comboImage.drawBitmap(background,0,0, null);
		comboImage.drawBitmap(foreground, 0,0, null);

		return cs;
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent it = new Intent(CameraActivity.this, FotoActivity.class);
		it.putExtra("helper", helper);
		it.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		startActivity(it);
	}
	
	
}