package com.br.ipad.isc.impressao;

import java.io.UnsupportedEncodingException;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.bean.SistemaParametros;
import com.br.ipad.isc.excecoes.ConexaoImpressoraException;
import com.br.ipad.isc.excecoes.StatusImpressoraException;
import com.br.ipad.isc.excecoes.ZebraException;
import com.br.ipad.isc.gui.ListaImpressorasActivity;
import com.br.ipad.isc.util.Bluetooth;
import com.br.ipad.isc.util.ConstantesSistema;
import com.br.ipad.isc.util.SettingsHelper;
import com.zebra.android.comm.BluetoothPrinterConnection;
import com.zebra.android.comm.ZebraPrinterConnection;
import com.zebra.android.comm.ZebraPrinterConnectionException;
import com.zebra.android.printer.PrinterLanguage;
import com.zebra.android.printer.PrinterStatus;
import com.zebra.android.printer.ZebraPrinter;
import com.zebra.android.printer.ZebraPrinterFactory;
import com.zebra.android.printer.ZebraPrinterLanguageUnknownException;

public class ZebraUtils{

	private Context ctx;
	private ZebraPrinterConnection zebraPrinterConnection;
	private static ZebraUtils instance;

	
	public ZebraUtils(Context context) {
		Bluetooth.ativarBluetooth();
		this.ctx = context;
	}

	
	public static ZebraUtils getInstance(Context context){
		if ( instance == null ){
			instance =  new ZebraUtils(context);
		}		
		return instance;		
	}
	
	@SuppressWarnings("unused")
	public ZebraPrinter connect() throws ZebraException {
		ZebraPrinter printer = null;
		
		
		if (zebraPrinterConnection == null || !zebraPrinterConnection.isConnected()) {
			initConnection();
		}
		
		if (zebraPrinterConnection != null && zebraPrinterConnection.isConnected()) {
			try {
				
				printer = ZebraPrinterFactory.getInstance(zebraPrinterConnection);
				//printer = ZebraPrinterFactory.getInstance(printer.getPrinterControlLanguage(), zebraPrinterConnection);
				//Comando abaixo utilizado para poder imprimir com o defy
				PrinterLanguage pl = printer.getPrinterControlLanguage();
				
			} catch (ZebraPrinterConnectionException e) {
				printer = null;
				disconnect();
				Bluetooth.resetarBluetooth();
				throw new ConexaoImpressoraException();
			} catch (ZebraPrinterLanguageUnknownException e) {
				printer = null;
				disconnect();
				Log.v(ConstantesSistema.CATEGORIA,e.getMessage());
				Bluetooth.resetarBluetooth();
				throw new ZebraException("Erro de conexão.");
			}
		}
		return printer;
	}

	public void disconnect() throws ZebraException {
		try {
			if (zebraPrinterConnection != null) {
				zebraPrinterConnection.close();
			}
		} catch (ZebraPrinterConnectionException e) {
			throw new ConexaoImpressoraException();
		}
	}

	public ZebraPrinterConnection getPrinterConnection() {
		return this.zebraPrinterConnection;
	}

	
	public boolean imprimir(StringBuilder conta) throws ZebraException {
		
		boolean retorno = false;

		ZebraPrinter printer = connect();

		if (printer != null) {
			byte[] contaCodificada = this.codificarConta(conta);
			retorno = sendLabel(contaCodificada);
		} 
				
		return retorno;
	}
	
	private void initConnection() throws ZebraException {
		
		zebraPrinterConnection = new BluetoothPrinterConnection(SettingsHelper.getBluetoothAddress(ctx.getApplicationContext()));

		try {			
			if(zebraPrinterConnection != null){
			
				zebraPrinterConnection.open();

			}
		} catch (ZebraPrinterConnectionException ups) {
			ups.printStackTrace();
			disconnect();
			throw new ConexaoImpressoraException();
		}catch (Exception e) {
              e.printStackTrace();
        }
	}
	
	
	
	

	public boolean sendLabel(byte[] label) throws ZebraException {
		
		boolean retorno = false;
		try {
			ZebraPrinter printer  = ZebraPrinterFactory.getInstance(zebraPrinterConnection);
		    PrinterStatus printerStatus = printer.getCurrentStatus();

		     if (printerStatus.isReadyToPrint) {
		         zebraPrinterConnection.write(label); 
		         retorno = true;
		     } else if (printerStatus.isPaused) {
		    		throw new StatusImpressoraException("Impressora em pausa.");
		     } else if (printerStatus.isHeadOpen) {
		    	 throw new StatusImpressoraException("A impressora está com a tampa aberta.");
		     } else if (printerStatus.isPaperOut) {
		    	 throw new StatusImpressoraException("A Impressora está sem papel.");
		     } else {
		    	 throw new ConexaoImpressoraException();
		     }
			
		} catch (ZebraPrinterConnectionException e) {
			retorno = false;
			throw new ConexaoImpressoraException();
		} catch (ZebraPrinterLanguageUnknownException e) {
			retorno = false;
			throw new ZebraException("Erro de conexão.");
		}
		
		if ( SistemaParametros.getInstancia().getContrasteConta() != 0 ){		
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return retorno;
		
	}


	private byte[] codificarConta(StringBuilder conta) {
		
		byte[] configLabel = null;
		String cpclConfigLabel = conta.toString();
		
		try {
			configLabel = cpclConfigLabel.getBytes("ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			Log.v(ConstantesSistema.CATEGORIA,e.getMessage());
			e.printStackTrace();
		}
		return configLabel;
	}
	
	public boolean verificaExistenciaImpressoraConfigigurada(Context ctx, ImovelConta imovel){
		
		String bluetoothAddress = SettingsHelper.getBluetoothAddress(ctx);
        String bluetoothName = SettingsHelper.getPrinterName(ctx);
       
        if( bluetoothAddress!=null && !bluetoothAddress.equals("") 
        		&& bluetoothName != null && !bluetoothName.equals("")){
       
        	return true;
       
        }else{
        	
        	//CASO NAO TENHA IMPRESSORA CONFIGURADA CHAMAR A TELA PARA CONFIGURAR IMPRESSORA
			Intent intent = new Intent(ctx, ListaImpressorasActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);    
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.putExtra("imovel", imovel);
			ctx.startActivity(intent);
        	return false;
        }
	
	}
	
	/**
	 * Verifica existencia de impressora configurada e
	 * retorna um booleano
	 * 
	 * @author Amelia Pessoa
	 * @return boolean
	 */
	public boolean verificaExistenciaImpressoraConfigurada(){
		
		String bluetoothAddress = SettingsHelper.getBluetoothAddress(ctx);
        String bluetoothName = SettingsHelper.getPrinterName(ctx);
       
        if( bluetoothAddress!=null && !bluetoothAddress.equals("") 
        		&& bluetoothName != null && !bluetoothName.equals("")){
       
        	return true;
       
        }else{
        	//CASO NAO TENHA IMPRESSORA CONFIGURADA CHAMAR A TELA PARA CONFIGURAR IMPRESSORA
			Intent intent = new Intent(ctx, ListaImpressorasActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);    
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			ctx.startActivity(intent);
        	return false;
        }	
	}


}