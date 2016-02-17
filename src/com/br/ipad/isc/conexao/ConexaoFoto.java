package com.br.ipad.isc.conexao;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

import com.br.ipad.isc.util.ConstantesSistema;
import com.br.ipad.isc.util.Util;

public class ConexaoFoto  {

    private static final byte SEND_PHOTOS = 8;
    
    public static synchronized boolean doFileUpload(
    		File file,
    		Integer idImovel,
    		Integer anoMesReferencia, 
    		Integer idAnormalidade,
    		Integer idFotoTipoLeituraConsumoAnormalidade,
    		Integer fotoTipo,
    		Integer tipoMedicao,
    		String uploadUrl) {
        HttpURLConnection conn = null;
        DataOutputStream dos = null;

        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        long fileSize = file.length();
        
        //Long longIMEI = new Long( imei );

        // Send request
        try {
            // Configure connection
            URL url = new URL(uploadUrl);
            
            if(ConstantesSistema.HOST.equals(ConstantesSistema.IP_CAERN_PRODUCAO)){
    			conn = ( HttpURLConnection ) url.openConnection(ConstantesSistema.PROXY_CAERN);
    		}else{
    			conn = ( HttpURLConnection ) url.openConnection();
    		}
            
            
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "multipart/form-data");

            dos = new DataOutputStream(conn.getOutputStream());

            dos.writeByte(SEND_PHOTOS);
            dos.writeInt(idImovel);
            dos.writeInt(idAnormalidade);
            dos.writeInt(anoMesReferencia);
            dos.writeInt(fotoTipo);
            dos.writeInt(tipoMedicao);
            
            dos.writeLong( new Long(fileSize ) );
            
            // Read file and create buffer
            FileInputStream fileInputStream = new FileInputStream(file);
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // Send file data
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            while (bytesRead > 0) {
                // Write buffer to socket
                dos.write(buffer, 0, bufferSize);

                // Update progress dialog
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }
           
            dos.writeInt(idFotoTipoLeituraConsumoAnormalidade);
            
            dos.flush();
            dos.close();
            fileInputStream.close();
            
        } catch (IOException ioe) {
        	ioe.printStackTrace();
            Log.e(ConstantesSistema.CATEGORIA, "Cannot upload file: " + ioe.getMessage(), ioe);
            return false;
        }catch (Exception e) {
        	e.printStackTrace();
            Log.e(ConstantesSistema.CATEGORIA, "Cannot upload file: " + e.getMessage(), e);
            return false;
        }

        try {
        	// Pegamos o retorno da requisição
        	InputStream in = null;
        	in = conn.getInputStream();
        	String valor = Util.getValorRespostaInputStream(in);
        	// Só foi ok, caso o servidor tenha enviado
        	// o caracter esperado
        	if (valor.equals(ConstantesSistema.RESPOSTA_OK)) {
        		return true;
        	} else {
        		return false;
        	}
        } catch (IOException ioex) {
        	ioex.printStackTrace();
            Log.e(ConstantesSistema.CATEGORIA, "Upload file failed: " + ioex.getMessage(), ioex);
            return false;
        } catch (Exception e) {
        	e.printStackTrace();
            Log.e(ConstantesSistema.CATEGORIA, "Upload file failed: " + e.getMessage(), e);
            return false;
        }
    }
}
