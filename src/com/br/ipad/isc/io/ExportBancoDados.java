
package com.br.ipad.isc.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import com.br.ipad.isc.repositorios.RepositorioBasico;
import com.br.ipad.isc.util.ConstantesSistema;

/**
 * Classe responsável por copiar a base de dados do celular e colar no SD card
 * 
 * @author Amélia Pessoa
 * @date 28/11/2012
 */
public class ExportBancoDados {

	
	public void exportarBancoNovoNome(Long getTime, String anoMes ){
		try { 
			File bancoDir = new File(ConstantesSistema.CAMINHO_BANCO);
			 if (!bancoDir.isDirectory()) {
				 bancoDir.mkdirs();
			 }
			 File destino = new File(ConstantesSistema.CAMINHO_BANCO+getTime.toString()+RepositorioBasico.NOME_BANCO+anoMes.substring(0, anoMes.length()-4));
			 
			 copyFile(new File(RepositorioBasico.CAMINHO_BANCO+RepositorioBasico.NOME_BANCO), destino);
		 } catch (Exception ex){
			 System.out.println(ex.getMessage());
		 }
	}
	
	private void copyFile(File source, File destination) throws IOException {
        if (destination.exists())
            destination.delete();

        FileChannel sourceChannel = null;
        FileChannel destinationChannel = null;

        try {
            sourceChannel = new FileInputStream(source).getChannel();
            destinationChannel = new FileOutputStream(destination).getChannel();
            sourceChannel.transferTo(0, sourceChannel.size(),
                    destinationChannel);
        } catch (Exception ex){
        	System.out.println(ex.getMessage());
        } finally {
        
            if (sourceChannel != null && sourceChannel.isOpen())
                sourceChannel.close();
            if (destinationChannel != null && destinationChannel.isOpen())
                destinationChannel.close();
       }
   }
}
