
package com.br.ipad.isc.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;

import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.excecoes.FachadaException;
import com.br.ipad.isc.excecoes.ZebraException;
import com.br.ipad.isc.fachada.Fachada;
import com.br.ipad.isc.gui.TabsActivity;
import com.br.ipad.isc.impressao.ZebraUtils;
import com.br.ipad.isc.io.ArquivoRetorno;
import com.br.ipad.isc.io.GZip;

public class Util {
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private static SimpleDateFormat dateFormatFileName = new SimpleDateFormat("ddMMyyyyHHmmss");
//	private static SimpleDateFormat dateOnlyFormatFileName = new SimpleDateFormat("ddMMyyyy");
	private static SimpleDateFormat dateOnlyFormat = new SimpleDateFormat("dd/MM/yyyy");

    
	/**
	 * Função usada para converter um Date para String
	 * 
	 * @author Thúlio Araújo
	 * @param date
	 * @return String
	 */
	public static String convertDateToDateStr(Date date){
		String dateStr = dateFormat.format(date);
		return dateStr;
	}
	
	/**
	 * Função usada para converter um Date para String em formato de nome de arquivo 
	 * 
	 * @author Fernanda Almeida
	 * @param date
	 * @return String
	 */
	public static String convertDateToDateStrFile(){
		Date date = new Date();
		String dateStr = dateFormatFileName.format(date);
		return dateStr;
	}
	
	/**
	 * Função usada para converter um Date para String em formato de nome de arquivo 
	 * 
	 * @author Fernanda Almeida
	 * @param date
	 * @return String
	 */
//	public static String convertDateToDateOnlyStrFile(){
//		Date date = new Date();
//		String dateStr = dateOnlyFormatFileName.format(date);
//		return dateStr;
//	}
	
	/**
	 * Função usada para converter uma String para um Date
	 * 
	 * @author Thúlio Araújo
	 * @param dateStr
	 * @return Date
	 */
	public static Date convertDateStrToDate(String dateStr){
		Date date = null;
		try {
			date = (Date)dateFormat.parse(dateStr);
		} catch (ParseException e) {
			
			Log.e( ConstantesSistema.CATEGORIA , e.getMessage());
		}
		return date;
	}
	
	public static Date convertDateStrToDate1(String dateStr){
		String data = converterDataSemBarraParaDataComBarra(dateStr);
		Date date = null;
		try {
			date = (Date)dateOnlyFormat.parse(data);
		} catch (ParseException e) {
			
			Log.e( ConstantesSistema.CATEGORIA , e.getMessage());
		}
		return date;
	}
	
	/**
	 * Converte a data passada para o formato "DD/MM/YYYY"
	 * 
	 * @author: Rafael Pinto
	 * @date: 22/01/2008
	 * 
	 * @param String data no formato "YYYYMMDD"
	 * @return String data no formato "DD/MM/YYYY"
	 */
	public static String converterDataSemBarraParaDataComBarra(String data) {
		String retorno = "";

		if (data != null && !data.equals("") && data.trim().length() == 8) {

			retorno = data.substring(6, 8) 
				+ "/" 
				+ data.substring(4, 6) 
				+ "/" 
				+ data.substring(0, 4);

		}

		return retorno;
	}
	
	/**
	 * Função usada para retornar a data e hora atual
	 * 
	 * @author Thúlio Araújo
	 * @return Date
	 */
	public static Date getCurrentDateTime(){
		Date date = new Date();
		return date;
	}
	
	/**
	 * Método que verifica se a string passada já tem casa decimal
	 * 
	 * 
	 * @param data
	 * @autor Fernanda Almeida
	 * @return
	 */

//	public static boolean verificaSeBigDecimalQtt(String valor) { 
//		double maxVal = 9999.99;
//		boolean temCasaDecimal = false;
//		if(valor.equals(".") || valor.equals("-") ||  valor.equals("") || valor.equals("0")){
//			return false;
//		}
//		if(Double.parseDouble(valor) > maxVal){
//			return false;
//		}
//		// Validação para NUMERIC(6,2)
//		if(valor.matches("\\d{1,4}.?[1-9]{0,2}") && Double.parseDouble(valor) > 0){
//			temCasaDecimal = true;
//		}
//		return temCasaDecimal;
//		
//	}
	
	/**
	 * Método que verifica se a string passada já tem casa decimal
	 * 
	 * 
	 * @param data
	 * @autor Fernanda Almeida
	 * @return
	 */

//	public static boolean verificaSeBigDecimal(String valor) {
//
//		boolean temCasaDecimal = false;
//		if(valor.equals(".") || valor.equals("-") ||  valor.equals("") || valor.equals("0")){
//			return false;
//		}
//		BigDecimal valorBig = new BigDecimal(valor);
//		if(valorBig.intValue() > 0){	
//			temCasaDecimal = true;
//		}
//		return temCasaDecimal;
//	}	
	 
	
	public static String getIMEI( Context context ){
		TelephonyManager telephony;
		String retorno = "0";
		// == Adquirindo uma referência ao Sistema de Gerenciamento do Telefone
		telephony = (TelephonyManager) context.getSystemService( Context.TELEPHONY_SERVICE );

		// == Método que retorna o IMEI
		if(telephony.getDeviceId() != null){
			retorno = telephony.getDeviceId();
		}
		
		return retorno;
		
	}	
	
	/**
     * Retorna um Bitmap de modo que nao utilize muita memoria.
     * Previne o memoryOutOfBounds     
     */
	public static Bitmap decodeFile(File f){
	    try {
	        //Decode image size
	        BitmapFactory.Options o = new BitmapFactory.Options();
	        o.inJustDecodeBounds = true;
	        BitmapFactory.decodeStream(new FileInputStream(f),null,o);

	        //The new size we want to scale to
	        final int REQUIRED_SIZE=70;

	        //Find the correct scale value. It should be the power of 2.
	        int width_tmp=o.outWidth, height_tmp=o.outHeight;
	        int scale=1;
	        while(true){
	            if(width_tmp/2<REQUIRED_SIZE || height_tmp/2<REQUIRED_SIZE)
	                break;
	            width_tmp/=2;
	            height_tmp/=2;
	            scale*=2;
	        }

	        //Decode with inSampleSize
	        BitmapFactory.Options o2 = new BitmapFactory.Options();
	        o2.inSampleSize=scale;
	        return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
	    } catch (FileNotFoundException e) {}
	    return null;
	}
	
	/**
	 * 
	 * Adiciona um pipe ao final do objeto passado
	 * 
	 * @param  String a ter o pipe adicionado
	 * @return 
	 */
	public static String stringPipe( Object obj ){
		return obj + "|";
	}
	
	
	/**
	 * Converte a data passada em string
	 * 
	 * @author: Thiago Toscano, Thiago Toscano
	 * @date: 20/03/2006, 20/03/2006
	 * 
	 * @param data
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
//	public static String formatDate(Date data) {
//		
//		String retorno = "";
//		if (data != null) { // 1
//			Calendar dataCalendar = new GregorianCalendar();
//			StringBuffer dataBD = new StringBuffer();
//
//			dataCalendar.setTime(data);
//
//			if (dataCalendar.get(Calendar.DAY_OF_MONTH) > 9) {
//				dataBD.append(dataCalendar.get(Calendar.DAY_OF_MONTH) + "/");
//			} else {
//				dataBD.append("0" + dataCalendar.get(Calendar.DAY_OF_MONTH)
//						+ "/");
//			}
//
//			// Obs.: Janeiro no Calendar é mês zero
//			if ((dataCalendar.get(Calendar.MONTH) + 1) > 9) {
//				dataBD.append(dataCalendar.get(Calendar.MONTH) + 1 + "/");
//			} else {
//				dataBD.append("0" + (dataCalendar.get(Calendar.MONTH) + 1)
//						+ "/");
//			}
//
//			dataBD.append(dataCalendar.get(Calendar.YEAR));
//			retorno = dataBD.toString();
//		}
//		
//		return retorno;
//	}
	
	
/*	public static String convertImageToBase64( String path ){
		Bitmap bm = BitmapFactory.decodeFile(path);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();  
		bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object   
		byte[] b = baos.toByteArray(); 		
		
		return Base64.encodeBytes( b );

	}*/
	
	/**
	 * 
	 * Deleta todas as pastas e arquivos filhos de isc.
	 * Menos a pasta de carregadomento.	
	 * 
	 * 
	 * @param  Caminho da pasta isc no SDCARD
	 * @return 
	 */
	public static void deletarPastas(File fileOrDirectory){	
		// Deleta as pastas diferente de 'carregamento'
		if(!fileOrDirectory.getName().equals("carregamento") && !fileOrDirectory.getName().equals("backupRetorno") && !fileOrDirectory.getName().equals("banco")){
			if (fileOrDirectory.isDirectory()){
		        for (File child : fileOrDirectory.listFiles()){
		        	deletarPastas(child);
		        }
			}
		    fileOrDirectory.delete();
		}
	}	
		
	
	/**
	 * < <Descrição do método>>
	 * 
	 * @param data
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
//	public static String formatarDataComHora(Date data) {
//		StringBuffer dataBD = new StringBuffer();
//
//		if (data != null) {
//			Calendar dataCalendar = new GregorianCalendar();
//
//			dataCalendar.setTime(data);
//
//			if (dataCalendar.get(Calendar.DAY_OF_MONTH) > 9) {
//				dataBD.append(dataCalendar.get(Calendar.DAY_OF_MONTH) + "/");
//			} else {
//				dataBD.append("0" + dataCalendar.get(Calendar.DAY_OF_MONTH)
//						+ "/");
//			}
//
//			// Obs.: Janeiro no Calendar é mês zero
//			if ((dataCalendar.get(Calendar.MONTH) + 1) > 9) {
//				dataBD.append(dataCalendar.get(Calendar.MONTH) + 1 + "/");
//			} else {
//				dataBD.append("0" + (dataCalendar.get(Calendar.MONTH) + 1)
//						+ "/");
//			}
//
//			dataBD.append(dataCalendar.get(Calendar.YEAR));
//
//			dataBD.append(" ");
//
//			if (dataCalendar.get(Calendar.HOUR_OF_DAY) > 9) {
//				dataBD.append(dataCalendar.get(Calendar.HOUR_OF_DAY));
//			} else {
//				dataBD.append("0" + dataCalendar.get(Calendar.HOUR_OF_DAY));
//			}
//
//			dataBD.append(":");
//
//			if (dataCalendar.get(Calendar.MINUTE) > 9) {
//				dataBD.append(dataCalendar.get(Calendar.MINUTE));
//			} else {
//				dataBD.append("0" + dataCalendar.get(Calendar.MINUTE));
//			}
//
//			dataBD.append(":");
//
//			if (dataCalendar.get(Calendar.SECOND) > 9) {
//				dataBD.append(dataCalendar.get(Calendar.SECOND));
//			} else {
//				dataBD.append("0" + dataCalendar.get(Calendar.SECOND));
//			}
//		}
//
//		return dataBD.toString();
//	}
	
	/**
	 * Método que recebe uma string e converte para BigDecimal
	 * casas decimais
	 * 
	 * @param data
	 * @autor Thúlio Araújo
	 * @date 29/09/2011
	 * @return BigDecimal
	 */
//	public static BigDecimal convertStringToBigDecimal(String value) {
//		BigDecimal numberFormated = null;
//		try {
//			numberFormated = new BigDecimal(value);
//		} catch (NumberFormatException e) {
//			numberFormated = null;
//		}
//		return numberFormated;
//	}
	
	/**
     * @author Magno Gouveia
     * @since 15/09/2011
     * @param line
     *            linha do arquivo que deve ser parseada
     * @return um array com os campos do objeto
     */
    public static ArrayList<String> split(String line) {
        ArrayList<String> lines = new ArrayList<String>();

        char[] chars = line.toCharArray();

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < chars.length; i++) {
            if (chars[i] != '|') {
                sb.append(chars[i]);
            } else {
                lines.add(sb.toString());
                sb = new StringBuilder();
            }
        }

        return lines;
    }
	
	/**
	 * Verifica se duas datas são iguais
	 * 
	 * @author Thúlio Araújo
	 * @date 06/10/2011
	 * 
	 * @param primeiraData
	 *            <Descrição>
	 * @param segundaData
	 *            <Descrição>
	 * @return retorno
	 */
//	public static boolean isEqualsDates(Date firstDate, Date secondDate) {
//
//		boolean sucess = false;
//
//		Calendar d1 = Calendar.getInstance();
//		Calendar d2 = Calendar.getInstance();
//
//		d1.setTime(firstDate);
//		d2.setTime(secondDate);
//
//		d1.set(Calendar.HOUR_OF_DAY, 0);
//		d1.set(Calendar.MINUTE, 0);
//		d1.set(Calendar.SECOND, 0);
//		d1.set(Calendar.MILLISECOND, 0);
//
//		d2.set(Calendar.HOUR_OF_DAY, 0);
//		d2.set(Calendar.MINUTE, 0);
//		d2.set(Calendar.SECOND, 0);
//		d2.set(Calendar.MILLISECOND, 0);
//
//		if (d1.getTime().equals(d2.getTime())) {
//			sucess = true;
//		}
//
//		return sucess;
//	}
	
	/**
	 * Converte a data passada em string retorna AAAAMMDD
	 * 
	 * @author: Sávio Luiz
	 * @date: 09/04/2007
	 * 
	 * @param data
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public static String formatarDataSemBarra(Date data) {
		String retorno = "";
		if (data != null) { // 1
			Calendar dataCalendar = new GregorianCalendar();
			StringBuffer dataBD = new StringBuffer();

			dataCalendar.setTime(data);

			dataBD.append(dataCalendar.get(Calendar.YEAR));

			// Obs.: Janeiro no Calendar é mês zero
			if ((dataCalendar.get(Calendar.MONTH) + 1) > 9) {
				dataBD.append(dataCalendar.get(Calendar.MONTH) + 1);
			} else {
				dataBD.append("0" + (dataCalendar.get(Calendar.MONTH) + 1));
			}

			if (dataCalendar.get(Calendar.DAY_OF_MONTH) > 9) {
				dataBD.append(dataCalendar.get(Calendar.DAY_OF_MONTH));
			} else {
				dataBD.append("0" + dataCalendar.get(Calendar.DAY_OF_MONTH));
			}

			retorno = dataBD.toString();
		}
		return retorno;
	}
	
		
	/**
	 * Método para comparar duas datas e retornar o numero de dias da diferença
	 * entre elas
	 * 
	 * Author: Breno Santos Data: 03/10/2010
	 * 
	 * @param dataInicial
	 *            Data Inicial
	 * @param dataFinal
	 *            Data Final
	 * 
	 * @return int Quantidade de Dias
	 */
	public static double obterQuantidadeDiasEntreDuasDatas(Date data1, Date data2) {

		long dias;
		long umDia = 86400000; // um dia possui 86400000ms

		int data1SemHora = (int) (data1.getTime() / umDia);
		int data2SemHora = (int) (data2.getTime() / umDia);

		dias = (data1SemHora - data2SemHora);

		return dias;

	}	

	/**
	 * Verifica se o valor da String.trim() veio como null ou como
	 * ConstantesSistema.NULO_STRING, setando como ConstantesSistema.NULO_DOUBLE caso
	 * verdadadeiro
	 * 
	 * @param valor
	 * @return
	 */
//	public static Double verificarNuloDouble(String valor) {
//		if (valor == null || valor.trim().equals("")) {
//			return null;
//		} else {
//			return Double.parseDouble(valor.trim());
//		}
//	}

	/**
	 * Verifica se o valor da String.trim() veio como null ou como
	 * ConstantesSistema.NULO_STRING, setando como ConstantesSistema.NULO_STRING caso
	 * verdadadeiro
	 * 
	 * @param valor
	 * @return
	 */
//	public static String verificarNuloString(String valor) {
//		if (valor == null || valor.trim().equals("") || valor.trim().equals("null")) {
//			return "";
//		} else {
//			return valor.trim();
//		}
//	}

	/**
	 * Verifica se o valor da String.trim() veio como null ou como
	 * ConstantesSistema.NULO_STRING, setando como ConstantesSistema.NULO_INT caso
	 * verdadadeiro
	 * 
	 * @param valor
	 * @return
	 */
	public static Integer verificarNuloInt(String valor) {
		if (valor == null || valor.trim().equals("")) {
			return null;
		} else {
			return new Integer(valor.trim());
		}
	}

	/**
	 * Verifica se o valor da String.trim() veio como null ou como
	 * ConstantesSistema.NULO_STRING, setando como ConstantesSistema.NULO_INT caso
	 * verdadadeiro
	 * 
	 * @param valor
	 * @return
	 */
//	public static Short verificarNuloShort(String valor) {
//		if (valor == null || valor.trim().equals("")) {
//			return null;
//		} else {
//			return Short.parseShort(valor.trim());
//		}
//	}

	//public static void resumoMemoria() {
		// System.out.println( "" );
		// System.out.println(
		// "*******************************************************" );
		// System.out.println( "Memoria Usada: " +
		// Runtime.getRuntime().freeMemory() );
		// System.out.println( "Total Disponivel: " +
		// Runtime.getRuntime().totalMemory() );
		// System.out.println( "% Uso da memoria: " + ( (
		// Runtime.getRuntime().freeMemory() /
		// Runtime.getRuntime().totalMemory() ) * 100 ) );
		// System.out.println(
		// "*******************************************************" );
		// System.out.println( "" );
	//}

//	public static String encrypt(String str) {
//		int tab[] = { 77, 110, 70, 114, 90, 100, 86, 103, 111, 75 };
//		int i;
//		int value = 0;
//		int len = str.length();
//
//		String response = "";
//
//		for (i = 0; i < len; i++) {
//			value = (int) str.charAt(i);
//			response += (char) tab[(value - 48)];
//		}
//
//		return response;
//	}



	public static Date getData(String data) {

		if (data.equals("")) {
			return null;
		} else {
			Calendar calendario = Calendar.getInstance();
			calendario.set(Calendar.YEAR, Integer.valueOf(data.substring(6, 10)).intValue());
			calendario.set(Calendar.MONTH, Integer.valueOf(data.substring(3, 5)).intValue() - 1);
			calendario.set(Calendar.DAY_OF_MONTH, Integer.valueOf(data.substring(0, 2)).intValue());
			calendario.set(Calendar.HOUR, 0);
			calendario.set(Calendar.HOUR_OF_DAY, 0);
			calendario.set(Calendar.MINUTE, 0);
			calendario.set(Calendar.SECOND, 0);//20/07/2012
			calendario.set(Calendar.MILLISECOND, 0);

			return new Date(calendario.getTime().getTime());
		}

	}

//	public static double strToDouble(String str) {
//		StringBuffer buffer = new StringBuffer();
//		buffer.append(str);
//		buffer.insert(str.length() - 2, '.');
//
//		return Double.valueOf(buffer.toString()).doubleValue();
//	}

	public static int pow(int arg, int times) {
		int ret = 1;
		for (int i = 1; i <= times; i++) {
			ret = ret * arg;
		}
		return ret;
	}

	/**
	 * Arredonda as casas decimais.
	 * 
	 * @param valor
	 *            O valor a ser arrendondado.
	 * @param casasDecimais
	 *            O número de casas decimais.
	 * @return O valor arredondado. Ex.: arredondar(abcd.xyzw, 3) = abcd.xyz,
	 *         para w <5 = abcd.xyk, para w >= 5 e k = z + 1
	 */
	public static double arredondar(double valor, int casasDecimais) {

		int mult = pow(10, casasDecimais);
		valor = (double) valor * mult;
		int inteiro = (int) Math.floor(valor + 0.5);
		valor = (double) inteiro / mult;
		return valor;

	}

	/**
	 * 
	 * Trunca um número no numero de casa decimais informados.
	 * 
	 * @author Bruno Barros
	 * @date 09/11/2010
	 * 
	 * @param valor
	 *            Valor a ser truncado
	 * @param casasDecimais
	 *            quantidade de casas decimais
	 * @return numero truncado
	 */
	public static double truncar(double valor, int casasDecimais) {
		int mult = pow(10, casasDecimais);
		valor = (double) valor * mult;
		int inteiro = (int) valor;
		valor = (double) inteiro / mult;
		return valor;
	}

//	public static int convertArrayByteToInt(byte[] b) {
//		int i = 0;
//		i += unsignedByteToInt(b[0]) << 24;
//		i += unsignedByteToInt(b[1]) << 16;
//		i += unsignedByteToInt(b[2]) << 8;
//		i += unsignedByteToInt(b[3]) << 0;
//		return i;
//
//	}

	/**
	 * [UC0631] Processar requisições do Dispositivo móvel. Método auxiliar para
	 * converter byte em int.
	 * 
	 * @author Thiago Nascimento
	 * @date 14/08/2007
	 * @param b
	 * @return
	 */
//	public static int unsignedByteToInt(byte b) {
//		return (int) b & 0xFF;
//	}

//	/**
//	 * Diferença entre datas em dias
//	 * 
//	 * @param data1
//	 * @param data2
//	 * @return
//	 */
//	public static long obterDiferencasDatasDias(Date data1, Date data2) {
//		long dias;
//		long umDia = 86400000; // um dia possui 86400000ms
//		long date1, date2;
//		date1 = data1.getTime();
//		date2 = data2.getTime();
//		dias = (date2 - date1) / umDia;
//		return (dias < 0) ? dias * -1 : dias;
//	}
	
	/**
	 * Método para comparar duas data e retornar o numero de dias da diferença
	 * entre elas positivo (sempre retorna um resultado positivo, indepedente das datas iniciais e finais.
	 * 
	 * Author: Tiago Moreno - (12/09/2011)
	 * 
	 * @param dataInicial
	 *            Data Inicial
	 * @param dataFinal
	 *            Data Final
	 * 
	 * @return int Quantidade de Dias
	 */
	public static int obterQuantidadeDiasEntreDuasDatasPositivo(Date dataInicial,
			Date dataFinal) {

		GregorianCalendar startTime = new GregorianCalendar();
		GregorianCalendar endTime = new GregorianCalendar();

		GregorianCalendar curTime = new GregorianCalendar();
		GregorianCalendar baseTime = new GregorianCalendar();
		
		startTime.setTime(dataInicial);
		endTime.setTime(dataFinal);

		int multiplicadorDiferenca = 1;

		// Verifica a ordem de inicio das datas
		if (dataInicial.compareTo(dataFinal) < 0) {
			baseTime.setTime(dataFinal);
			curTime.setTime(dataInicial);
			multiplicadorDiferenca = 1;
		} else {
			baseTime.setTime(dataInicial);
			curTime.setTime(dataFinal);
			multiplicadorDiferenca = -1;
		}

		int resultadoAno = 0;
		int resultadoMeses = 0;
		int resultadoDias = 0;

		// Para cada mes e ano, vai de mes em mes pegar o ultimo dia para ir
		// acumulando
		// no total de dias. Ja leva em consideracao ano bissesto
		while (curTime.get(GregorianCalendar.YEAR) < baseTime
				.get(GregorianCalendar.YEAR)
				|| curTime.get(GregorianCalendar.MONTH) < baseTime
						.get(GregorianCalendar.MONTH)) {

			int max_day = curTime
					.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
			resultadoMeses += max_day;
			curTime.add(GregorianCalendar.MONTH, 1);

		}

		// Marca que é um saldo negativo ou positivo
		resultadoMeses = resultadoMeses * multiplicadorDiferenca;

		// Retirna a diferenca de dias do total dos meses
		resultadoDias += (endTime.get(GregorianCalendar.DAY_OF_MONTH) - startTime
				.get(GregorianCalendar.DAY_OF_MONTH));
		
		int resultado = resultadoAno + resultadoMeses + resultadoDias;
		
		if (resultado < 0){
			resultado = resultado * (-1);
		}

		return resultado;
	}

	/**
	 * Subtrai a data no formato AAAAMM Exemplo 200508 retorna 200507 Author:
	 * Sávio Luiz Data: 20/01/2006
	 * 
	 * @param data
	 *            com a barra
	 * @return Uma data no formato yyyyMM (sem a barra)
	 */
	public static int subtrairMesDoAnoMes(int anoMes, int qtdMeses) {

		String dataFormatacao = "" + anoMes;

		int ano = Integer.parseInt(dataFormatacao.substring(0, 4));
		int mes = Integer.parseInt(dataFormatacao.substring(4, 6));

		int mesTemp = (mes - qtdMeses);

		if (mesTemp <= 0) {
			mesTemp = (12 + mes) - qtdMeses;
			ano = ano - 1;
		}

		String anoMesSubtraido = null;
		String tamanhoMes = "" + mesTemp;

		if (tamanhoMes.length() == 1) {
			anoMesSubtraido = ano + "0" + mesTemp;
		} else {
			anoMesSubtraido = ano + "" + mesTemp;
		}
		return Integer.parseInt(anoMesSubtraido);
	}

//	public static int obterMes(int anoMes) {
//
//		String dataFormatacao = "" + anoMes;
//
//		int mes = Integer.parseInt(dataFormatacao.substring(4, 6));
//
//		return mes;
//	}

//	public static int obterAno(int anoMes) {
//
//		String dataFormatacao = "" + anoMes;
//
//		int ano = Integer.parseInt(dataFormatacao.substring(0, 4));
//
//		return ano;
//	}

	/**
	 * Método que recebe um java.util.Date e retorna uma String no formato
	 * dia/mês/ano.
	 * 
	 * @param date
	 *            Data a ser formatada.
	 * @return String no formato dia/mês/ano.
	 */
	public static String dateToString(Date date) {
		StringBuffer retorno = new StringBuffer();

		if (date != null && !date.equals("")) {

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			// Dia
			retorno.append(Util.adicionarZerosEsquerdaNumero(2, calendar.get(Calendar.DAY_OF_MONTH) + ""));
			retorno.append("/");
			// Mes
			retorno.append(Util.adicionarZerosEsquerdaNumero(2, calendar.get(Calendar.MONTH) + 1 + ""));
			// Ano
			retorno.append("/");
			retorno.append(calendar.get(Calendar.YEAR));
		}

		return retorno.toString();
	}

	/**
	 * Método que recebe um java.util.Date e retorna uma String no formato
	 * dia/mês/ano.
	 * 
	 * @param date
	 *            Data a ser formatada.
	 * @return String no formato dia/mês/ano.
	 */
//	public static String dateToStringSemBarras(Date date) {
//		StringBuffer retorno = new StringBuffer();
//
//		if (date != null && !date.equals("")) {
//
//			Calendar calendar = Calendar.getInstance();
//			calendar.setTime(date);
//			// Dia
//			retorno.append(Util.adicionarZerosEsquerdaNumero(2, calendar.get(Calendar.YEAR) + ""));
//			// Mes
//			retorno.append(Util.adicionarZerosEsquerdaNumero(2, calendar.get(Calendar.MONTH) + 1 + ""));
//			// Ano
//			retorno.append(Util.adicionarZerosEsquerdaNumero(2, calendar.get(Calendar.DAY_OF_MONTH) + ""));
//		}
//
//		return retorno.toString();
//	}

	/**
	 * Método que recebe um java.util.Date e retorna uma String no formato
	 * Hora:Minuto:Segundo.
	 * 
	 * @param date
	 *            Data a ser formatada.
	 * @return String no formato Hora:Minuto:Segundo.
	 */
	public static String dateToHoraString(Date date) {
		StringBuffer retorno = new StringBuffer();

		if (date != null && !date.equals("")) {

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			// Hora
			retorno.append(Util.adicionarZerosEsquerdaNumero(2, calendar.get(Calendar.HOUR_OF_DAY) + ""));
			retorno.append(":");
			// Minuto
			retorno.append(Util.adicionarZerosEsquerdaNumero(2, calendar.get(Calendar.MINUTE) + 1 + ""));
			// Segundo
			retorno.append(":");
			retorno.append(Util.adicionarZerosEsquerdaNumero(2, calendar.get(Calendar.SECOND) + ""));
		}

		return retorno.toString();
	}

	/**
	 * Compara duas datas sem verificar a hora.
	 * 
	 * @param data1
	 *            A primeira data
	 * @param data2
	 *            A segunda data
	 * @author Rafael Francisco Pinto
	 * @return -1 se a data1 for menor que a data2, 0 se as datas forem iguais,
	 *         1 se a data1 for maior que a data2.
	 */
	public static int compararData(Date data1, Date data2) {

		Calendar calendar1;
		Calendar calendar2;

		int ano1;
		int ano2;
		int mes1;
		int mes2;
		int dia1;
		int dia2;

		int resultado;

		calendar1 = Calendar.getInstance();
		calendar1.setTime(data1);

		ano1 = calendar1.get(Calendar.YEAR);
		mes1 = calendar1.get(Calendar.MONTH);
		dia1 = calendar1.get(Calendar.DAY_OF_MONTH);

		calendar2 = Calendar.getInstance();
		calendar2.setTime(data2);

		ano2 = calendar2.get(Calendar.YEAR);
		mes2 = calendar2.get(Calendar.MONTH);
		dia2 = calendar2.get(Calendar.DAY_OF_MONTH);

		if (ano1 == ano2) {

			if (mes1 == mes2) {

				if (dia1 == dia2) {
					resultado = 0;
				} else if (dia1 < dia2) {
					resultado = -1;
				} else {
					resultado = 1;
				}
			} else if (mes1 < mes2) {
				resultado = -1;
			} else {
				resultado = 1;
			}
		} else if (ano1 < ano2) {
			resultado = -1;
		} else {
			resultado = 1;
		}
		return resultado;
	}

	/**
	 * Retorna a descrição abreviada do ano Mes
	 * 
	 * @param anoMes
	 * @author Rafael Francisco Pinto
	 */

	public static String retornaDescricaoAnoMes(String anoMes) {

		int mes = Integer.parseInt(anoMes.substring(4, 6));
		String ano = anoMes.substring(0, 4);

		String descricao = retornaDescricaoMes(mes) + "/" + ano;

		return descricao;
	}

	/**
	 * Retorna a descrição do mes
	 * 
	 * @param mes
	 * @author Rafael Francisco Pinto
	 * @return a descrição do mês
	 */

	public static String retornaDescricaoMes(int mes) {

		String meses[] = { "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro",
							"Outubro", "Novembro", "Dezembro" };

		String mesPorExtenso = meses[mes - 1];// mes-1 pq o indice do array
		// começa no zero

		return mesPorExtenso;
	}

	/**
	 * Retorna a descrição do mes
	 * 
	 * @param anoMes
	 * @author Breno Santos
	 * @return a descrição do mês
	 */

	public static String retornaDescricaoMesDoAnoMes(String anoMes) {

		int mes = Integer.valueOf(anoMes.substring(4)).intValue();

		String meses[] = { "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro",
							"Outubro", "Novembro", "Dezembro" };

		String mesPorExtenso = meses[mes - 1];// mes-1 pq o indice do array
		// começa no zero

		return mesPorExtenso;
	}

	/**
	 * Retorna o valor de cnpjFormatado
	 * 
	 * @return O valor de cnpjFormatado
	 */
//	public static String formatarCnpj(String cnpj) {
//		String cnpjFormatado = cnpj;
//		String zeros = "";
//
//		if (cnpjFormatado != null) {
//
//			for (int a = 0; a < (14 - cnpjFormatado.length()); a++) {
//				zeros = zeros.concat("0");
//			}
//			// concatena os zeros ao numero
//			// caso o numero seja diferente de nulo
//			cnpjFormatado = zeros.concat(cnpjFormatado);
//
//			cnpjFormatado = cnpjFormatado.substring(0, 2) + "." + cnpjFormatado.substring(2, 5) + "."
//					+ cnpjFormatado.substring(5, 8) + "/" + cnpjFormatado.substring(8, 12) + "-"
//					+ cnpjFormatado.substring(12, 14);
//		}
//
//		return cnpjFormatado;
//	}

	/**
	 * Converte um valor double para uma string formatada
	 * 
	 * @param valor
	 *            valor a ser formatado
	 * @return String formatada
	 */
	public static String formatarDoubleParaMoedaReal(double d) {
		double valorArredondado = Util.arredondar(d, 2);
		int inteiro = (int) valorArredondado;
		double decimal = Util.arredondar(valorArredondado - (int) inteiro, 2);

		String inteiroString = inteiro + "";
		int contador = 0;
		String comPontoInvertido = "";

		// Agrupamos de 3 em 3
		for (int i = inteiroString.length() - 1; i >= 0; i--) {
			contador++;
			comPontoInvertido += inteiroString.charAt(i);

			if (contador % 3 == 0 && i != 0) {
				comPontoInvertido += ".";
			}
		}

		String comPonto = "";

		// Invertemos
		for (int i = comPontoInvertido.length() - 1; i >= 0; i--) {
			contador++;
			comPonto += comPontoInvertido.charAt(i);
		}

		String decimalString = decimal + "";
		decimalString = decimalString.substring(2, decimalString.length());

		// Colocamos a virgula
		return comPonto + ',' + adicionarZerosDireitaNumero(2, decimalString);
	}

	/**
	 * Separa uma String na primeira ocorrencia do caracter passado
	 * 
	 * @param string
	 *            String a ser dividida
	 * @param caracter
	 *            caracter que dividira a String
	 * @return vetor com 2 posicoes. A primeira com a primeira parte da String e
	 *         a segunda com a segunda parte da String
	 */
	public static  ArrayList<String> split(String texto, char separador) {

		if (texto == null) {
			return null;
		}

		// verifica se existe alguma informação na string texto
		int tamanhoTexto = texto.length();

		if (tamanhoTexto == 0) {
			return null;
		}

		ArrayList<String> lista = new ArrayList<String>();

		int i = 0;
		int start = 0;
		boolean permite = false;

		while (i < tamanhoTexto) {
			// percorre caracter a caracter tentando encontrar o separador
			// se encontrar o separador no primeiro carcater ele ignora
			if (texto.charAt(i) == separador) {
				if (permite) {
					// pegando o pedaço da string entre os separadores
					lista.add(texto.substring(start, i).trim());
					permite = false;
				}

				// recebo a posição de onde posso começar a pegar os caracteres,
				// até a próxima vez que encontrar o separador ou terminar os
				// caracteres
				start = ++i;
				continue;
			}

			permite = true;
			i++;
		}

		if (permite) {
			// guardando a informação em uma posição da lista
			lista.add(texto.substring(start, i).trim());
		}

		
		return lista;
	}

	public static String formatarAnoMesParaMesAno(String anoMes) {
		if(isInteger(anoMes)){
			String anoMesFormatado = "";
			String anoMesRecebido = anoMes;
			if (anoMesRecebido.length() < 6) {
				anoMesFormatado = anoMesRecebido;
			} else {
				String mes = anoMesRecebido.substring(4, 6);
				String ano = anoMesRecebido.substring(0, 4);
				anoMesFormatado = mes + "/" + ano;
			}
			
			return anoMesFormatado;
		}else{
			return anoMes;
		}
	}

	public static String formatarAnoMesParaMesAnoSemBarra(String anoMes) {

		String anoMesFormatado = "";
		String anoMesRecebido = anoMes;
		if (anoMesRecebido.length() < 6) {
			anoMesFormatado = anoMesRecebido;
		} else {
			String mes = anoMesRecebido.substring(4, 6);
			String ano = anoMesRecebido.substring(0, 4);
			anoMesFormatado = mes + ano;
		}
		return anoMesFormatado;
	}

	/**
	 * Adiciona zeros a esqueda do número informado tamamho máximo campo 6
	 * Número 16 retorna 000016
	 * 
	 * @param tamanhoMaximoCampo
	 *            Descrição do parâmetro
	 * @param numero
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public static String adicionarZerosEsquerdaNumero(int tamanhoMaximoCampo, String numero) {
		String zeros = "";

		String retorno = null;

		boolean ehNegativo = numero != null && numero.charAt(0) == '-';

		if (ehNegativo) {
			numero = numero.substring(1);
		}

		if (numero != null && !numero.equals("")) {
			for (int a = 0; a < (tamanhoMaximoCampo - numero.length()); a++) {
				zeros = zeros.concat("0");
			}
			// concatena os zeros ao numero
			// caso o numero seja diferente de nulo
			retorno = zeros.concat(numero);
		} else {
			for (int a = 0; a < tamanhoMaximoCampo; a++) {
				zeros = zeros.concat("0");
			}
			// retorna os zeros
			// caso o numero seja nulo
			retorno = zeros;
		}

		if (ehNegativo) {
			retorno = "-" + retorno.substring(1);
		}

		return retorno;
	}

	/**
	 * Adiciona zeros a esqueda do número informado tamamho máximo campo 6
	 * Número 16 retorna 000016
	 * 
	 * @param tamanhoMaximoCampo
	 *            Descrição do parâmetro
	 * @param numero
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
//	public static String adicionarCharEsquerda(int tamanhoMaximoCampo, String string, char c) {
//		String repetido = "";
//
//		String retorno = null;
//
//		if (string != null && !string.equals("")) {
//			for (int a = 0; a < (tamanhoMaximoCampo - string.length()); a++) {
//				repetido = repetido.concat(c + "");
//			}
//			// concatena os zeros ao numero
//			// caso o numero seja diferente de nulo
//			retorno = repetido.concat(string);
//		} else {
//			for (int a = 0; a < tamanhoMaximoCampo; a++) {
//				repetido = repetido.concat(c + "");
//			}
//			// retorna os zeros
//			// caso o numero seja nulo
//			retorno = repetido;
//		}
//
//		return retorno;
//	}

	/**
	 * Obtém a representação númerica do código de barras de um pagamento de
	 * acordo com os parâmetros informados [UC0229] Obter Representação Numérica
	 * do Código de Barras Formata a identificação do pagamento de acordo com o
	 * tipo de pagamento informado [SB0001] Obter Identificação do Pagamento
	 * 
	 * @author Pedro Alexandre
	 * @date 20/04/2006
	 * @param tipoPagamento
	 * @param idLocalidade
	 * @param matriculaImovel
	 * @param anoMesReferenciaConta
	 * @param digitoVerificadorRefContaModulo10
	 * @param idTipoDebito
	 * @param anoEmissaoGuiaPagamento
	 * @param sequencialDocumentoCobranca
	 * @param idTipoDocumento
	 * @param idCliente
	 * @param seqFaturaClienteResponsavel
	 * @return
	 */
	public static String obterIdentificacaoPagamento(Integer tipoPagamento, Integer idLocalidade, Integer matriculaImovel,
			String mesAnoReferenciaConta, Integer digitoVerificadorRefContaModulo10, Integer idTipoDebito,
			String anoEmissaoGuiaPagamento, String sequencialDocumentoCobranca, Integer idTipoDocumento, Integer idCliente,
			Integer seqFaturaClienteResponsavel) {

		// Cria a variável que vai armazenar o identificador do pagamento
		// formatado
		String identificacaoPagamento = "";

		// Caso o tipo de pagamento seja referente a conta
		if (tipoPagamento.intValue() == 3) {
			identificacaoPagamento = identificacaoPagamento + Util.adicionarZerosEsquerdaNumero(3, "" + idLocalidade);
			identificacaoPagamento = identificacaoPagamento + Util.adicionarZerosEsquerdaNumero(9, "" + matriculaImovel);
			identificacaoPagamento = identificacaoPagamento + "01";
			identificacaoPagamento = identificacaoPagamento + mesAnoReferenciaConta;
			identificacaoPagamento = identificacaoPagamento + digitoVerificadorRefContaModulo10;
			identificacaoPagamento = identificacaoPagamento + "000";

			// Caso o tipo de pagamento seja referente a guia de pagamento
			// (Imóvel)
		} else if (tipoPagamento.intValue() == 4) {
			identificacaoPagamento = identificacaoPagamento + Util.adicionarZerosEsquerdaNumero(3, "" + idLocalidade);
			identificacaoPagamento = identificacaoPagamento + Util.adicionarZerosEsquerdaNumero(9, "" + matriculaImovel);
			identificacaoPagamento = identificacaoPagamento + "01";
			identificacaoPagamento = identificacaoPagamento
					+ (Util.adicionarZerosEsquerdaNumero(3, idTipoDebito.toString()));
			identificacaoPagamento = identificacaoPagamento + anoEmissaoGuiaPagamento;
			identificacaoPagamento = identificacaoPagamento + "000";

			// Caso a tipo de pagamento seja referente a documento de cobrança
		} else if (tipoPagamento.intValue() == 5) {
			identificacaoPagamento = identificacaoPagamento + Util.adicionarZerosEsquerdaNumero(3, "" + idLocalidade);
			identificacaoPagamento = identificacaoPagamento + Util.adicionarZerosEsquerdaNumero(9, "" + matriculaImovel);
			identificacaoPagamento = identificacaoPagamento
					+ (Util.adicionarZerosEsquerdaNumero(9, sequencialDocumentoCobranca));
			identificacaoPagamento = identificacaoPagamento
					+ (Util.adicionarZerosEsquerdaNumero(2, idTipoDocumento.toString()));
			identificacaoPagamento = identificacaoPagamento + "1";

			// Caso o tipo de pagamento seja referente a guia de pagamento
			// (Cliente)
		} else if (tipoPagamento.intValue() == 6) {
			identificacaoPagamento = identificacaoPagamento + Util.adicionarZerosEsquerdaNumero(3, "" + idLocalidade);
			identificacaoPagamento = identificacaoPagamento + Util.adicionarZerosEsquerdaNumero(8, "" + idCliente);
			identificacaoPagamento = identificacaoPagamento + "000";
			identificacaoPagamento = identificacaoPagamento
					+ (Util.adicionarZerosEsquerdaNumero(3, idTipoDebito.toString()));
			identificacaoPagamento = identificacaoPagamento + anoEmissaoGuiaPagamento;
			identificacaoPagamento = identificacaoPagamento + "000";

			// Caso o tipo de pagamento seja referente a fatura do cliente
			// responsável
		} else if (tipoPagamento.intValue() == 7) {
			identificacaoPagamento = identificacaoPagamento + (Util.adicionarZerosEsquerdaNumero(9, idCliente.toString()));
			identificacaoPagamento = identificacaoPagamento + "00";
			identificacaoPagamento = identificacaoPagamento + mesAnoReferenciaConta;
			identificacaoPagamento = identificacaoPagamento + digitoVerificadorRefContaModulo10;
			identificacaoPagamento = identificacaoPagamento
					+ (Util.adicionarZerosEsquerdaNumero(6, seqFaturaClienteResponsavel.toString()));
			// Caso a tipo de pagamento seja referente a documento de cobrança
			// cliente
		} else if (tipoPagamento.intValue() == 8) {
			identificacaoPagamento = identificacaoPagamento + "000";
			identificacaoPagamento = identificacaoPagamento + (Util.adicionarZerosEsquerdaNumero(8, idCliente.toString()));
			identificacaoPagamento = identificacaoPagamento
					+ (Util.adicionarZerosEsquerdaNumero(9, sequencialDocumentoCobranca));
			identificacaoPagamento = identificacaoPagamento
					+ (Util.adicionarZerosEsquerdaNumero(2, idTipoDocumento.toString()));
			identificacaoPagamento = identificacaoPagamento + "00";
		}

		// Retorna o identificador do pagamento formatado
		return identificacaoPagamento;
	}

	/**
	 * Obtém a representação númerica do código de barras de um pagamento de
	 * acordo com os parâmetros informados
	 * 
	 * [UC0229] Obter Representação Numérica do Código de Barras
	 * 
	 * Formata a identificação do pagamento de acordo com o tipo de pagamento
	 * informado
	 * 
	 * [SB0001] Obter Identificação do Pagamento
	 * 
	 * @author Pedro Alexandre (modificado por Rogério Peixoto)
	 * @date 20/04/2006
	 * 
	 * @param tipoPagamento
	 * @param idLocalidade
	 * @param matriculaImovel
	 * @param anoMesReferenciaConta
	 * @param digitoVerificadorRefContaModulo10
	 * @param idTipoDebito
	 * @param anoEmissaoGuiaPagamento
	 * @param sequencialDocumentoCobranca
	 * @param idTipoDocumento
	 * @param idCliente
	 * @param seqFaturaClienteResponsavel
	 * @param idDebitoCreditoSituacaoAtual
	 * @return
	 */
	public static String obterIdentificacaoPagamentoCAER(Integer tipoPagamento, Integer idLocalidade,
			Integer matriculaImovel, String mesAnoReferenciaConta, Integer digitoVerificadorRefContaModulo10,
			Integer idTipoDebito, String anoEmissaoGuiaPagamento, String sequencialDocumentoCobranca,
			Integer idTipoDocumento, Integer idCliente, Integer seqFaturaClienteResponsavel) {
		
		// Cria a variável que vai armazenar o identificador do pagamento formatado
		String identificacaoPagamento = "";

		//Caso o tipo de pagamento seja referente a guia de pagamento (Imóvel)
		if (tipoPagamento.intValue() == 4) {
			
			identificacaoPagamento = identificacaoPagamento + Util.adicionarZerosEsquerdaNumero(3, "" + idLocalidade);
			identificacaoPagamento = identificacaoPagamento + Util.adicionarZerosEsquerdaNumero(9, "" + matriculaImovel);
			
			//Identifica o tamanho da matrícula do imóvel
			identificacaoPagamento = identificacaoPagamento + "1";
			
			identificacaoPagamento = identificacaoPagamento + (Util.adicionarZerosEsquerdaNumero(4, idTipoDebito.toString()));
			
			identificacaoPagamento = identificacaoPagamento + anoEmissaoGuiaPagamento;
			
			identificacaoPagamento = identificacaoPagamento + "000";
			
		} 
		//Caso o tipo de pagamento seja referente a guia de pagamento (Cliente)
		else if (tipoPagamento.intValue() == 6) {
			
			identificacaoPagamento = identificacaoPagamento + Util.adicionarZerosEsquerdaNumero(3, "" + idLocalidade);
			identificacaoPagamento = identificacaoPagamento + Util.adicionarZerosEsquerdaNumero(8, "" + idCliente);
			
			identificacaoPagamento = identificacaoPagamento + "00";
			
			identificacaoPagamento = identificacaoPagamento + (Util.adicionarZerosEsquerdaNumero(4, idTipoDebito.toString()));
			identificacaoPagamento = identificacaoPagamento + anoEmissaoGuiaPagamento;
			
			identificacaoPagamento = identificacaoPagamento + "000";

		}
		
		/*
		 * Caso o documento não seja do tipo de guia de pagamento ou guia de pagamento para cliente; a identificação do pagamento
		 * será feita no padrão do GSAN.
		 */ 
		if (identificacaoPagamento.equals("")){
			
			identificacaoPagamento = obterIdentificacaoPagamento(tipoPagamento, idLocalidade, matriculaImovel,
			mesAnoReferenciaConta, digitoVerificadorRefContaModulo10, idTipoDebito,
			anoEmissaoGuiaPagamento, sequencialDocumentoCobranca, idTipoDocumento, 
			idCliente, seqFaturaClienteResponsavel);
		}


		// Retorna o identificador do pagamento formatado
		return identificacaoPagamento;
	}

	public static String formatarMesAnoParaAnoMes(String data) {

		String mes = data.substring(0, 2);
		String ano = data.substring(2, 6);

		return ano + mes;
	}

	/**
	 * Adiciona zeros a esqueda do número truncando informado tamamho máximo
	 * campo 6 Número 16 retorna 000016
	 * 
	 * @param tamanhoMaximoCampo
	 *            Descrição do parâmetro
	 * @param numero
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
//	public static String adicionarZerosEsquedaNumeroTruncando(int tamanhoMaximoCampo, String numero) {
//
//		String zeros = "";
//
//		String retorno = null;
//
//		if (numero != null && !numero.equals("")) {
//			for (int a = 0; a < (tamanhoMaximoCampo - numero.length()); a++) {
//				zeros = zeros.concat("0");
//			}
//			// concatena os zeros ao numero
//			// caso o numero seja diferente de nulo
//			retorno = zeros.concat(numero);
//		} else {
//			for (int a = 0; a < tamanhoMaximoCampo; a++) {
//				zeros = zeros.concat("0");
//			}
//			// retorna os zeros
//			// caso o numero seja nulo
//			retorno = zeros;
//		}
//		if (tamanhoMaximoCampo < retorno.length()) {
//			// trunca a String
//			String strTruncado = retorno.substring(0, tamanhoMaximoCampo);
//			retorno = strTruncado;
//		}
//		return retorno;
//	}

	/**
	 * <Breve descrição sobre o caso de uso> [UC0260] Obter Dígito Verificador
	 * Módulo 10
	 * 
	 * @author Rafael Rossiter
	 * @date 17/03/2006
	 * @param numero
	 * @return
	 */
	public static Integer obterDigitoVerificadorModulo10(Long numero) {

		long entrada = numero.longValue();

		String entradaString = String.valueOf(entrada);

		int sequencia = 2;
		int contEntrada, digito, contAuxiliar, produto, contProduto;
		String produtoString;
		int somaDigitosProduto = 0;

		contAuxiliar = 1;
		for (contEntrada = 0; contEntrada < entradaString.length(); contEntrada++) {

			digito = Integer.parseInt(entradaString.substring(entradaString.length() - contAuxiliar, entradaString.length()
					- contEntrada));

			produto = digito * sequencia;
			produtoString = String.valueOf(produto);

			for (contProduto = 0; contProduto < produtoString.length(); contProduto++) {
				somaDigitosProduto = somaDigitosProduto
						+ Integer.parseInt(produtoString.substring(contProduto, contProduto + 1));
			}

			if (sequencia == 2) {
				sequencia = 1;
			} else {
				sequencia = 2;
			}

			contAuxiliar++;
		}

		int resto = (somaDigitosProduto % 10);

		int dac;
		if (resto == 0) {
			dac = 0;
		} else {
			dac = 10 - resto;
		}

		return new Integer(dac);
	}

	/**
	 * Obtém a representação númerica do código de barras de um pagamento de
	 * acordo com os parâmetros informados [UC0229] Obter Representação Numérica
	 * do Código de Barras Obtém o dígito verificador geral do código de barra
	 * com 43 posições [SB0002] Obter Dígito Verificador Geral
	 * 
	 * @author Pedro Alexandre
	 * @date 20/04/2006
	 * @param codigoBarraCom43Posicoes
	 * @return
	 */
	public static Integer obterDigitoVerificadorGeral(String codigoBarraCom43Posicoes, int moduloVerificador) {
		// Recupera o dígito verificador do módulo 11 para o código de barra com
		// 43 posições
		// Passando uma string como parâmetro

		Integer digitoVerificadorGeral = null;
		// obterDigitoVerificadorModulo11(codigoBarraCom43Posicoes);

		if (moduloVerificador == ConstantesSistema.MODULO_VERIFICADOR_11) {
			digitoVerificadorGeral = obterDigitoVerificadorModulo11(codigoBarraCom43Posicoes);
		} else {
			digitoVerificadorGeral = obterDigitoVerificadorModulo10(codigoBarraCom43Posicoes);
		}

		// Retorna o dígito verificador calculado
		return digitoVerificadorGeral;

	}

	/**
	 * <Breve descrição sobre o caso de uso> [UC0260] Obter Dígito Verificador
	 * Módulo 10
	 * 
	 * @author Rafael Rossiter
	 * @date 17/03/2006
	 * @param numero
	 * @return
	 */
	public static Integer obterDigitoVerificadorModulo10(String numero) {

		String entradaString = numero;

		int sequencia = 2;
		int contEntrada, digito, contAuxiliar, produto, contProduto;
		String produtoString;
		int somaDigitosProduto = 0;

		contAuxiliar = 1;
		for (contEntrada = 0; contEntrada < entradaString.length(); contEntrada++) {

			digito = Integer.parseInt(entradaString.substring(entradaString.length() - contAuxiliar, entradaString.length()
					- contEntrada));

			produto = digito * sequencia;
			produtoString = String.valueOf(produto);

			for (contProduto = 0; contProduto < produtoString.length(); contProduto++) {
				somaDigitosProduto = somaDigitosProduto
						+ Integer.parseInt(produtoString.substring(contProduto, contProduto + 1));
			}

			if (sequencia == 2) {
				sequencia = 1;
			} else {
				sequencia = 2;
			}

			contAuxiliar++;
		}

		int resto = (somaDigitosProduto % 10);

		int dac;
		if (resto == 0) {
			dac = 0;
		} else {
			dac = 10 - resto;
		}

		return new Integer(dac);
	}

	public static String replaceAll(String text, String searchString, String replacementString) {

		StringBuffer sBuffer = new StringBuffer();
		int pos = 0;

		while ((pos = text.indexOf(searchString)) != -1) {
			sBuffer.append(text.substring(0, pos) + replacementString);
			text = text.substring(pos + searchString.length());
		}

		sBuffer.append(text);

		return sBuffer.toString();
	}

	/**
	 * Adiciona zeros a direita do número informado tamamho máximo campo 6
	 * Número 16 retorna 000016
	 * 
	 * @param tamanhoMaximoCampo
	 *            Descrição do parâmetro
	 * @param numero
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public static String adicionarZerosDireitaNumero(int tamanhoMaximoCampo, String numero) {
		String retorno = "";

		String zeros = "";

		for (int i = 0; i < (tamanhoMaximoCampo - numero.length()); i++) {
			zeros += "0";
		}

		retorno += numero + zeros;

		return retorno;
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param data
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public static String formatarData(Date data) {
		StringBuffer dataBD = new StringBuffer();

		if (data != null) {
			Calendar dataCalendar = Calendar.getInstance();
			dataCalendar.setTime(data);

			dataBD.append(dataCalendar.get(Calendar.YEAR) + "-");

			// Obs.: Janeiro no Calendar é mês zero
			if ((dataCalendar.get(Calendar.MONTH) + 1) > 9) {
				dataBD.append(dataCalendar.get(Calendar.MONTH) + 1 + "-");
			} else {
				dataBD.append("0" + (dataCalendar.get(Calendar.MONTH) + 1) + "-");
			}

			if (dataCalendar.get(Calendar.DAY_OF_MONTH) > 9) {
				dataBD.append(dataCalendar.get(Calendar.DAY_OF_MONTH));
			} else {
				dataBD.append("0" + dataCalendar.get(Calendar.DAY_OF_MONTH));
			}

			dataBD.append(" ");

			if (dataCalendar.get(Calendar.HOUR_OF_DAY) > 9) {
				dataBD.append(dataCalendar.get(Calendar.HOUR_OF_DAY));
			} else {
				dataBD.append("0" + dataCalendar.get(Calendar.HOUR_OF_DAY));
			}

			dataBD.append(":");

			if (dataCalendar.get(Calendar.MINUTE) > 9) {
				dataBD.append(dataCalendar.get(Calendar.MINUTE));
			} else {
				dataBD.append("0" + dataCalendar.get(Calendar.MINUTE));
			}

			dataBD.append(":");

			if (dataCalendar.get(Calendar.SECOND) > 9) {
				dataBD.append(dataCalendar.get(Calendar.SECOND));
			} else {
				dataBD.append("0" + dataCalendar.get(Calendar.SECOND));
			}

			dataBD.append(".");

			dataBD.append(Util.adicionarZerosEsquerdaNumero(6, dataCalendar.get(Calendar.MILLISECOND) + ""));
		}

		return dataBD.toString();
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param data
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
//	public static String formatarDataSemHora(Date data) {
//		StringBuffer dataBD = new StringBuffer();
//
//		if (data != null) {
//			Calendar dataCalendar = Calendar.getInstance();
//			dataCalendar.setTime(data);
//
//			dataBD.append(dataCalendar.get(Calendar.YEAR) + "-");
//
//			// Obs.: Janeiro no Calendar é mês zero
//			if ((dataCalendar.get(Calendar.MONTH) + 1) > 9) {
//				dataBD.append(dataCalendar.get(Calendar.MONTH) + 1 + "-");
//			} else {
//				dataBD.append("0" + (dataCalendar.get(Calendar.MONTH) + 1) + "-");
//			}
//
//			if (dataCalendar.get(Calendar.DAY_OF_MONTH) > 9) {
//				dataBD.append(dataCalendar.get(Calendar.DAY_OF_MONTH));
//			} else {
//				dataBD.append("0" + dataCalendar.get(Calendar.DAY_OF_MONTH));
//			}
//
//			dataBD.append(" ");
//
//			// dataBD.append(".");
//
//			// dataBD.append( Util.adicionarZerosEsquerdaNumero( 6,
//			// dataCalendar.get(Calendar.MILLISECOND)+"" ) );
//		}
//
//		return dataBD.toString();
//	}

//	public static String formatarDataPTBR(String data) {
//
//		String ano = "";
//		String mes = "";
//		String dia = "";
//		String dataFormatada = "";
//
//		ano = data.substring(0, 4);
//		mes = data.substring(5, 7);
//		dia = data.substring(8, 11);
//
//		dataFormatada = dia + "/" + mes + "/" + ano;
//
//		return dataFormatada;
//	}

	/**
	 * Como o celular nao implementa timezone removemos sempre tres horas da
	 * hora atual O celular precisa ser sempre configurado para GMT-3:00
	 * 
	 * @author Bruno Barros
	 * @return Data menos 3 horas
	 */
	public static Date dataAtual() {
		// return new Date( (new Date()).getTime() - 10800000 );

		return new Date();
	}

	/**
	 * Método para retornar o valor do InputStream para o E71 e o E5
	 * 
	 * @param input
	 * @return string de retorno com o valor
	 * @throws IOException
	 */
	public static String getValorRespostaInputStream(InputStream input) throws IOException {
		char valor = ' ';
		try {

			// ---INICIO E5
			valor = (char) input.read();
			// ---FIM E5

		} catch (Exception e) {
			// ---INICIO E71
			InputStreamReader isr = new InputStreamReader(input);
			valor = (char) isr.read();
			// ---FIM E71

		}

		return String.valueOf(valor);
	}

	/**
	 * Método para retornar o valor do InputStream para o E71 e o E5
	 * 
	 * @param input
	 * @return char de retorno com o valor
	 * @throws IOException
	 */
	public static char getCharValorRespostaInputStream(InputStream input) throws IOException {
		char valor = ' ';
		try {

			// ---INICIO E5
			valor = (char) input.read();
			// ---FIM E5

		} catch (Exception e) {
			// ---INICIO E71
			InputStreamReader isr = new InputStreamReader(input);
			valor = (char) isr.read();
			// ---FIM E71

		}

		return valor;
	}

	// Complementa o tamanho da string com espaços em branco.
	// Autor:Sávio Luiz
//	public static String completaString(String str, int tm) {
//
//		String string = null;
//
//		int tamanhoString = 0;
//		if (str != null) {
//			tamanhoString = str.length();
//		}
//
//		// caso o tamanho da string seja maior do que o tamanho especificado
//		if (tamanhoString > tm) {
//			// trunca a String
//			string = str.substring(0, tm);
//
//		} else {
//			string = str;
//			for (int i = 0; i < (tm - tamanhoString); i++) {
//				string = string + " ";
//			}
//		}
//
//		return string;
//	}

	public static byte[] empacotarParametros(ArrayList<Object> parametros) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos =   new DataOutputStream(baos);

		byte[] resposta = null;

		parametros.trimToSize();

		// escreve os dados no OutputStream
		if (parametros != null) {
			int tamanho = parametros.size();
			for (int i = 0; i < tamanho; i++) {
				Object param = parametros.get(i);
				if (param instanceof Byte) {
					dos.writeByte(((Byte) param).byteValue());
				} else if (param instanceof Integer) {
					dos.writeInt(((Integer) param).intValue());
				} else if (param instanceof Short) {
					dos.writeShort(((Short) param).shortValue());					
				} else if (param instanceof Long) {
					dos.writeLong(((Long) param).longValue());
				} else if (param instanceof String) {
					dos.writeUTF((String) param);
				} else if (param instanceof byte[]) {
					dos.write((byte[]) param);
				}
			}
		}

		// pega os dados enpacotados
		resposta = baos.toByteArray();

		if (dos != null) {
			dos.close();
			dos = null;
		}
		if (baos != null) {
			baos.close();
			baos = null;
		}

		// retorna o array de bytes
		return resposta;
	}	
	
	/**
	 * Author: Pedro Alexandre Data: 08/01/2006 Adiciona nº de dias para uma
	 * data
	 * 
	 * @param numeroDias
	 * @param data
	 * @return data menos o nº de dias informado
	 */
	public static Date adicionarNumeroDiasDeUmaData(Date data, int numeroDias) {		
		Calendar c = Calendar.getInstance();
		c.setTime( data );
        c.add(Calendar.DATE, numeroDias);
		// retorna a nova data
		return c.getTime();
	}

	public static String formatarCodigoBarras(String codigoBarras) {
		String retorno = "";
		// return "82600000010-7 63620006190-2 01531394000-7 08200920003-9";
		if (codigoBarras != null && codigoBarras.length() >= 48) {
			retorno = codigoBarras.substring(0, 11).trim() + "-" + codigoBarras.substring(11, 12).trim() + " "
					+ codigoBarras.substring(12, 23).trim() + "-" + codigoBarras.substring(23, 24).trim() + " "
					+ codigoBarras.substring(24, 35).trim() + "-" + codigoBarras.substring(35, 36).trim() + " "
					+ codigoBarras.substring(36, 47).trim() + "-" + codigoBarras.substring(47, 48);
		}

		return retorno;
	}

	public static int getMes(Date date) {
		Calendar dataCalendar = Calendar.getInstance();
		dataCalendar.setTime(date);

		return (dataCalendar.get(Calendar.MONTH) + 1);
	}

	public static int getAno(Date date) {
		Calendar dataCalendar = Calendar.getInstance();
		dataCalendar.setTime(date);

		return dataCalendar.get(Calendar.YEAR);
	}

//	public static int getAnoMes(Date date) {
//		int mes = getMes(date);
//		String sMes = mes + "";
//		if (sMes.length() == 1) {
//			sMes = "0" + sMes;
//		}
//		int ano = getAno(date);
//
//		return Integer.parseInt(ano + "" + sMes);
//	}

	/**
	 * Retorna o IMEI (International Mobile Equipment Identifier) do
	 * dispositivo.
	 * 
	 * @return O IMEI do dispositivo.
	 */
//	public static long getIMEI() {
//
//		long imei = 0;
//
//		// chamada proprietária da NOKIA
//		// String strIMEI = System.getProperty("com.nokia.mid.imei");
//
//		String strIMEI = "356211003394768";
//
//		if (strIMEI != null) {
//			imei = Long.parseLong(strIMEI);
//		}
//
//		return imei;
//	}

	/**
	 * Recebe um número double que será arredondado para um inteiro, levando o
	 * seguinte criterio: - Se a parte fracionada do numero for superior a 0,5
	 * adicionamos 1 ao inteiro retornado, senão, retornamos apenas a parte
	 * inteira sem o incremento
	 * 
	 * @author Bruno / Sávio
	 * @date 09/12/2009
	 * @param numero
	 *            Número double para ser convertido para inteiro
	 * @return
	 */
	public static int arredondar(double numero) {
		int inteiro = (int) numero;
		double fracionado = numero - inteiro;

		if (fracionado >= .5) {
			++inteiro;
		}

		return inteiro;
	}

	/**
	 * Transforma uma string de ISO-8859-1 para UTF-8
	 * 
	 * @param string
	 * @return
	 */
//	public static String reencode(String string) {
//
//		String retorno = "";
//
//		try {
//			retorno = new String(string.getBytes("ISO-8859-1"));// , "UTF-8" );
//		} catch (Exception e) {
//			retorno = string;
//		}
//
//		return retorno;
//	}


//	public static String retirarCaracteresString(String string, String caractere) {
//
//		if (string != null && caractere != null) {
//
//			int tamanhoString = string.length();
//			String stringFinal = "";
//
//			for (int i = 0; i < tamanhoString; i++) {
//				char c = string.charAt(i);
//
//				if (String.valueOf(c).equals(caractere) == false) {
//					stringFinal += String.valueOf(c);
//				}
//			}
//
//			return stringFinal;
//
//		} else {
//			return null;
//		}
//	}

	/**
	 * @author Rogerio Peixoto
	 * @param valorConta
	 * @param valorLimiteConta
	 * @param imovelSelecionado
	 * @return validacao
	 * 
	 *         Método valida a impressao de contas acima do limite estabelecido.
	 * 
	 */
	public static boolean validarImpressaoValorAcimaLimite(double valorConta, int valorLimiteConta,
			ImovelConta imovelSelecionado) {

		boolean validacao = false;

		// Se o limite da conta for acima do limite estabelecido a conta so sera
		// impressa se o imovel estiver em debito automatico.
		if (valorConta > valorLimiteConta
				&& (imovelSelecionado.getCodigoAgencia() == null || imovelSelecionado.getCodigoAgencia().equals(""))) {
			validacao = true;
		}
		return validacao;
	}

	/**
	 * 
	 * Divide uma String em várias partes, dependendo do tamanho máximo
	 * permitido
	 * 
	 * @author Bruno Barros
	 * @date 25/03/2010
	 * @param mensagem
	 *            Mensagem a ser quebrada
	 * @param max
	 *            máximo de caracteres por linha
	 * @return String quebradas por linhas
	 * 
	 */
	public static String[] dividirString(String mensagem, int max) {

		// Encontramos em quantas strings precisaremos dividir
		short qtdLinhas = (short) (mensagem.length() / max);

		// Verificamos se sobrou alguma coisa para a ultima linha
		if (mensagem.length() % max != 0) {
			qtdLinhas++;
		}

		String[] retorno = new String[qtdLinhas];
		int limiteString = mensagem.length();

		for (int i = 0; i < qtdLinhas; i++) {
			int inicio = max * i;
			int fim = (max * (i + 1));

			retorno[i] = mensagem.substring(inicio, (fim > limiteString ? limiteString : fim));
		}

		return retorno;
	}

	public static int divideDepoisMultiplica(int numerador, int denominador, int numeroMultiplicado) {

		double resultado = 0;
		double numeradorDouble = numerador;
		double denominadorDouble = denominador;

		numeradorDouble = numeradorDouble * 10000;

		denominadorDouble = denominadorDouble * 10000;

		resultado = Util.arredondar(numeradorDouble / denominadorDouble, 4);

		resultado = resultado * numeroMultiplicado;

		return (int) Util.arredondar(resultado, 0);

	}

	public static int quantidadeDiasMes(Calendar data) {

		int mes = data.get(Calendar.MONTH);
		int ano = data.get(Calendar.YEAR);
		int qtdDiasMes = 0;

		if (mes == Calendar.JANUARY) {
			qtdDiasMes = 31;
		}

		else if (mes == Calendar.FEBRUARY) {

			if (verificarAnoBissexto(ano)) {
				qtdDiasMes = 29;
			} else {
				qtdDiasMes = 28;
			}
		}

		else if (mes == Calendar.MARCH) {
			qtdDiasMes = 31;
		}

		else if (mes == Calendar.APRIL) {
			qtdDiasMes = 30;
		}

		else if (mes == Calendar.MAY) {
			qtdDiasMes = 31;
		}

		else if (mes == Calendar.JUNE) {
			qtdDiasMes = 30;
		}

		else if (mes == Calendar.JULY) {
			qtdDiasMes = 31;
		}

		else if (mes == Calendar.AUGUST) {
			qtdDiasMes = 30;
		}

		else if (mes == Calendar.SEPTEMBER) {
			qtdDiasMes = 31;
		}

		else if (mes == Calendar.OCTOBER) {
			qtdDiasMes = 30;
		}

		else if (mes == Calendar.NOVEMBER) {
			qtdDiasMes = 31;
		}

		else if (mes == Calendar.DECEMBER) {
			qtdDiasMes = 30;
		}

		return qtdDiasMes;
	}

	public static boolean verificarAnoBissexto(int ano) {

		boolean anoBissexto = false;
		String anoString = String.valueOf(ano);
		int ultimosDigitosAno = Integer.parseInt(anoString.substring(2, anoString.length()));

		if (ultimosDigitosAno != 00 && ano % 4 == 0) {
			anoBissexto = true;
		}

		if (ultimosDigitosAno == 00 && ano % 400 == 0) {
			anoBissexto = true;
		}

		return anoBissexto;
	}

//	public static String replace(String text, String searchStr, String replacementStr) {
//		// String buffer to store str
//		StringBuffer sb = new StringBuffer();
//
//		// Search for search
//		int searchStringPos = text.indexOf(searchStr);
//		int startPos = 0;
//		int searchStringLength = searchStr.length();
//
//		// Iterate to add string
//		while (searchStringPos != -1) {
//			sb.append(text.substring(startPos, searchStringPos)).append(replacementStr);
//			startPos = searchStringPos + searchStringLength;
//			searchStringPos = text.indexOf(searchStr, startPos);
//		}
//
//		// Create string
//		sb.append(text.substring(startPos, text.length()));
//
//		return sb.toString();
//	}

	/**
	 * 
	 * Ordena um vetor de INTEIROS
	 * 
	 * @author Bruno Barros
	 * @date 12/08/2010
	 * 
	 * @param vec
	 *            - Vetor a ser ordenado
	 * 
	 */
//	@SuppressWarnings("rawtypes")
//	public static void bubbleSort(Vector vec) {
//		for (int i = 0; i < vec.size(); i++) {
//			for (int j = 0; j < vec.size() - (1 + i); j++) {
//				Integer t1 = (Integer) vec.elementAt(j);
//				Integer t2 = (Integer) vec.elementAt(j + 1);
//
//				if (t1.intValue() > t2.intValue()) {
//					vec.setElementAt(t2, j);
//					vec.setElementAt(t1, j + 1);
//				}
//			}
//		}
//	}

	public static Integer obterDigitoVerificadorModulo11(String numero) {

		String wnumero = numero;
		int param = 2;
		int soma = 0;

		for (int ind = (wnumero.length() - 1); ind >= 0; ind--) {
			if (param > 9) {
				param = 2;
			}
			soma = soma + (Integer.parseInt(wnumero.substring(ind, ind + 1)) * param);
			param = param + 1;
		}

		int resto = soma % 11;
		int dv;

		if ((resto == 0) || (resto == 1)) {
			dv = 0;
		} else {
			dv = 11 - resto;
		}

		return new Integer(dv);

	}

	/**
	 * 
	 * [UC0229] Obter Representação Numérica do Código de Barras
	 * 
	 * Obtém o dígito verificador da divisão do código de barra com 11 posições,
	 * de acordo com o modulo passado como parametro.
	 * 
	 * 
	 * ConstantesSistema.MODULO_VERIFICADOR_10
	 * 
	 * ConstantesSistema.MODULO_VERIFICADOR_11
	 * 
	 * @author Breno Santos
	 * @date 10/03/2010
	 * 
	 * @param codigoBarraCom43Posicoes
	 * @param moduloVerificador
	 * @return
	 */
	public static Integer obterDigitoVerificador(Long codigoBarraCom43Posicoes, int moduloVerificador) {

		Integer digitoVerificadorGeral = null;

		if (moduloVerificador == ConstantesSistema.MODULO_VERIFICADOR_11) {

			digitoVerificadorGeral = obterDigitoVerificadorModulo11(codigoBarraCom43Posicoes);

		} else {

			digitoVerificadorGeral = obterDigitoVerificadorModulo10(codigoBarraCom43Posicoes);

		}

		// Retorna o dígito verificador calculado
		return digitoVerificadorGeral;
	}

	/**
	 * [UC0261] - Obter Dígito Verificador Módulo 11 Author : Breno Santos Data
	 * : 14/10/2010
	 * 
	 * Calcula o dígito verificador do código de barras no módulo 11(onze)
	 * 
	 * @param numero
	 *            Número do código de barra no formato long para calcular o
	 *            dígito veficador
	 * @return digito verificador do módulo 11(onze)
	 */
	public static Integer obterDigitoVerificadorModulo11(Long numero) {

		// converte o número recebido para uma string
		String entradaString = String.valueOf(numero);

		// inicia o sequêncial de multiplicação para 2(dois)
		int sequencia = 2;

		// cria as variáveis que serão utilizadas no calculo
		int digito, contAuxiliar;

		// variável que vai armazenar a soma da múltiplicação de cada dígito
		int somaDigitosProduto = 0;

		// contador auxiliar
		contAuxiliar = 1;

		// laço para calcular a soma da múltiplicação de cada dígito
		for (int i = 0; i < entradaString.length(); i++) {

			// recupera o dígito da string
			digito = Integer.valueOf((entradaString.substring(entradaString.length() - contAuxiliar, entradaString.length()
					- i))).intValue();

			// multiplica o digito pelo sequência e acumula o resultado
			somaDigitosProduto = somaDigitosProduto + (digito * sequencia);

			// se osequência for igual a 9(nove)
			if (sequencia == 9) {
				// a sequência volta para 2(dois)
				sequencia = 2;
			} else {
				// incrementa a sequência mais 1
				++sequencia;
			}

			// incrementa o contador auxiliar
			contAuxiliar++;
		}

		// calcula o resto da divisão
		int resto = (somaDigitosProduto % 11);

		// variável que vai armazenar o dígito verificador
		int dac;

		// se o resto for 0(zero) ou 1(1)
		if (resto == 0 || resto == 1) {
			// o dígito verificador vai ser 0(zero)
			dac = 0;
		} else if (resto == 10) {
			// o dígito verificador vai ser 1(um)
			dac = 1;
		} else {
			// o dígito verificador vai ser a diferença
			dac = 11 - resto;
		}
		// retorna o dígito verificador calculado
		return new Integer(dac);
	}
	
	
	 public static void existeSDCARD() {
        File noMedia = new File(ConstantesSistema.CAMINHO_SDCARD + "/.nomedia");

        if (!noMedia.exists()) {
            noMedia.mkdirs();
            try {
                noMedia.createNewFile();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
	 
 	/**
	 * @author Carlos Chaves
	 * @date 31/07/2012
	 * @param String nomeArquivo
	 * @param String conteudo
	 * @param String caminho
	 * @throws IOException
	 */
	 
	 public static void escreverArquivoTexto(String nomeArquivo, String conteudo, String caminho) throws IOException{
		 try
		 {
			 File root = new File(caminho);
			 if (!root.exists()) {
				 root.mkdirs();
			 }

			 File gpxfile = new File(root, nomeArquivo);
			 FileWriter writer = new FileWriter(gpxfile);
			 writer.append(conteudo);
			 writer.flush();
			 writer.close();
		 }
		 catch(IOException e)
		 {
			 e.printStackTrace();
		 }
	 } 
	 /**
		 * Separa um arquivo texto por linhas.
		 * 
		 * @param arquivo
		 *            Dados do arquivo texto.
		 * @param maxLinhas
		 *            Número máximo de linhas. Caso seja zero, retorna todas as
		 *            linhas
		 * @return Vetor de linhas do arquivo texto.
		 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })	 
		public static Vector carregaVetorLinhas(InputStream arquivo, int maxLinhas) throws IOException {
			Vector vetor = new Vector();
			vetor.removeAllElements();
			StringBuffer buffer = new StringBuffer();

			int i = 0;
			while (i != ConstantesSistema.EOF && (maxLinhas == 0 || vetor.size() < maxLinhas)) {
				i = arquivo.read();
				// System.out.println("Valor char: "+(char)i);
				// se for enter (0D ou 13)...
				if (i == ConstantesSistema.ENTER) {
					// ...pula para o próximo caractere
					i = arquivo.read();
				}
				// se for quebra linha (0A ou 10)...
				if (i == ConstantesSistema.LINE || i == ConstantesSistema.EOF) {
					// ...salva o registro
					String line = buffer.toString();

					if ("".equals(line) || line == null) {
						continue;
					}
					vetor.addElement(line);
					buffer.delete(0, buffer.length());
				} else {
					buffer.append((char) i);
				}
			}

			buffer = null;
			return vetor;
		}
	 /**
		 * Lê o arquivo de retorno e retorna um vetor com a linhas geradas.
		 * 
		 * @author Bruno Barros
		 * @data 19/10/2009
		 * @return vetor com as linhas do arquivo de retorno
		 */
	    @SuppressWarnings("rawtypes")
		public static Vector lerArquivoRetorno(String arquivo) throws IOException {

			Vector linhas = null;

			File file = new File(ConstantesSistema.CAMINHO_ISC+"/Retorno/"+arquivo );
			
			InputStream instream = null;
		    
			try {
				//InputStream do arquivo selecionado
				instream = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				Log.e( ConstantesSistema.CATEGORIA , e.getMessage());
			}
		
			boolean response = (instream != null);

			if (response) {
				linhas = Util.carregaVetorLinhas(instream, 0);
				instream.close();
				instream = null;
			}
			
			return linhas;
		}

	 
	 /**
	     * 
	     * Formata um campo e o retorna ja com |
	     * 
	     * @param parametro - Qualque object pode ser concatenado
	     * 
	     * @return 
	     */
	    public static String formatarCampoParaConcatenacao( Object parametro ){
	    	if ( parametro == null ){
	    		return "|";
	    	} else {
	    		return parametro + "|"; 
	    	}
	    }
	 
	    public static Bitmap getBitmap(String caminho) {

	        Bitmap bmp = null;

	        File foto = getArquivoFoto(caminho);

	        if (foto.exists()) {
	            bmp = Util.decodeFile(foto);
	        }

	        return bmp;
	    }
	    
	    public static File getArquivoFoto(String caminho) {
	        return new File(caminho);
	    }
	    

	    /**
		 * < <Descrição do método>>
		 * 
		 * @param data
		 *            Descrição do parâmetro
		 * @return Descrição do retorno
		 */
//		public static String formatarDataHHMMSS(Date data) {
//			StringBuffer dataBD = new StringBuffer();
//
//			if (data != null) {
//				Calendar dataCalendar = new GregorianCalendar();
//
//				dataCalendar.setTime(data);
//
//				if (dataCalendar.get(Calendar.HOUR_OF_DAY) > 9) {
//					dataBD.append(dataCalendar.get(Calendar.HOUR_OF_DAY));
//				} else {
//					dataBD.append("0" + dataCalendar.get(Calendar.HOUR_OF_DAY));
//				}
//
//				if (dataCalendar.get(Calendar.MINUTE) > 9) {
//					dataBD.append(dataCalendar.get(Calendar.MINUTE));
//				} else {
//					dataBD.append("0" + dataCalendar.get(Calendar.MINUTE));
//				}
//
//				dataBD.append(":");
//
//				if (dataCalendar.get(Calendar.SECOND) > 9) {
//					dataBD.append(dataCalendar.get(Calendar.SECOND));
//				} else {
//					dataBD.append("0" + dataCalendar.get(Calendar.SECOND));
//				}
//			}
//
//			return dataBD.toString();
//		}
		
		public static String obterAAAAMMDDHHMMSS(Date data) {
			StringBuffer dataBD = new StringBuffer();

			if (data != null) {
				Calendar dataCalendar = new GregorianCalendar();

				dataCalendar.setTime(data);
				
				dataBD.append(dataCalendar.get(Calendar.YEAR));
				if (dataCalendar.get(Calendar.MONTH) >= 9) {
					dataBD.append(dataCalendar.get(Calendar.MONTH)+1);
				}else {
					dataBD.append("0" + (dataCalendar.get(Calendar.MONTH)+1));
				}	
				if (dataCalendar.get(Calendar.DAY_OF_MONTH) > 9) {
					dataBD.append(dataCalendar.get(Calendar.DAY_OF_MONTH));
				}else {
					dataBD.append("0" + dataCalendar.get(Calendar.DAY_OF_MONTH));
				}
				if (dataCalendar.get(Calendar.HOUR_OF_DAY) > 9) {
					dataBD.append(dataCalendar.get(Calendar.HOUR_OF_DAY));
				} else {
					dataBD.append("0" + dataCalendar.get(Calendar.HOUR_OF_DAY));
				}

				if (dataCalendar.get(Calendar.MINUTE) > 9) {
					dataBD.append(dataCalendar.get(Calendar.MINUTE));
				} else {
					dataBD.append("0" + dataCalendar.get(Calendar.MINUTE));
				}
				if (dataCalendar.get(Calendar.SECOND) > 9) {
					dataBD.append(dataCalendar.get(Calendar.SECOND));
				} else {
					dataBD.append("0" + dataCalendar.get(Calendar.SECOND));
				}
			}

			return dataBD.toString();
		}
		
		
		public static Integer  getIntBanco (Cursor cursor, String columnName, int columnIndex){
		
			if (! cursor.isNull(cursor.getColumnIndex(columnName)) ){
				return cursor.getInt(columnIndex);
			}else{
				return null;
			}
			
		}
		
		public static BigDecimal  getDoubleBanco (Cursor cursor, String columnName, int columnIndex){
			
			if (! cursor.isNull(cursor.getColumnIndex(columnName)) ){
				return BigDecimal.valueOf(cursor.getDouble(columnIndex));
			}else{
				return null;
			}
			
		}
		/**
		 * Metodo utilizado para obter dadas que estao como LONG no banco da dados.
		 * @author Carlos Chaves
		 * @param Cursor cursor
		 * @param String columnName
		 * @param int columnIndex
		 * @return Date
		 */
		public static Date  getDataBanco (Cursor cursor, String columnName, int columnIndex){
			
			if (! cursor.isNull(cursor.getColumnIndex(columnName)) ){
				return new Date(Long.valueOf(cursor.getLong(columnIndex)));
			}else{
				return null;
			}
			
		}
				
		public static String convertDataToStrValues(Date dataBanco){
			if(dataBanco != null){
				String dataStr = Util.convertDateToDateStr(dataBanco);
				return dataStr;
			}else{
				return null;
			}
		}
		
		public static Date convertStrToDataBusca(String dataStrBanco){
			if(dataStrBanco != null){
				Date data = Util.convertDateStrToDate(dataStrBanco);
				return data;
			}else{
				return null;
			}
		}
		
		public static Date convertStrToDataArquivo(String dataStrArquivo){
			if(verificarNuloInt(dataStrArquivo) != null){
				Date dataFormatada = Util.convertDateStrToDate1(dataStrArquivo);
				return dataFormatada;
			}else{
				return null;
			}
		}
		
		/**
		 * Verifica se o valor da String.trim() veio como null ou como
		 * ConstantesSistema.NULO_STRING, setando como ConstantesSistema.NULO_DOUBLE caso
		 * verdadadeiro
		 * 
		 * @param valor
		 * @return
		 */
		public static BigDecimal verificarNuloBigDecimal(String valor) {
			if (valor == null || valor.trim().equals("")) {
				return null;
			} else {
				return new BigDecimal(valor);
			}
		}

		
		public static InputStream inflateFile(InputStream is, int tamanhoInput) throws IOException {

			DataInputStream disArquivoCompactado = new DataInputStream(is);
			byte[] arrayArquivoCompactado = new byte[tamanhoInput];
			disArquivoCompactado.readFully(arrayArquivoCompactado);
			arrayArquivoCompactado = GZip.inflate(arrayArquivoCompactado);

			ByteArrayInputStream byteArray = new ByteArrayInputStream(arrayArquivoCompactado);

			disArquivoCompactado.close();
			disArquivoCompactado = null;
			arrayArquivoCompactado = null;

			return byteArray;
		}
		
		/**
		 * Chama próximo imóvel
		 * 
		 * @author Fernanda
		 */
		public static void chamaProximo(Context context,ImovelConta imovel){
			
			Fachada.getInstance().enviarEmBackground(imovel);
			
			Intent it = new Intent(context, TabsActivity.class);
			ImovelConta imovelProximo = Fachada.getInstance().buscarImovelContaPosicao(imovel.getPosicao(),true);
			it.putExtra("imovel", imovelProximo);
			context.startActivity(it);
		}
		
		/**
		 * Chama próximo imóvel
		 * 
		 * @author Carlos Chaves
		 */
		public static void chamaProximoSemEnviar(Context context,ImovelConta imovel){
			
			Intent it = new Intent(context, TabsActivity.class);
			ImovelConta imovelProximo = Fachada.getInstance().buscarImovelContaPosicao(imovel.getPosicao(),true);
			it.putExtra("imovel", imovelProximo);
			context.startActivity(it);
		}
		
		/**
		 * Chama imóvel anterior
		 * 
		 * @author Davi Menezes
		 */
		public static void chamaAnterior(Context context,ImovelConta imovel){
			
			Fachada.getInstance().enviarEmBackground(imovel);
			
			Intent it = new Intent(context, TabsActivity.class);
			ImovelConta imovelProximo = Fachada.getInstance().buscarImovelContaPosicao(imovel.getPosicao(),false);
			it.putExtra("imovel", imovelProximo);
			context.startActivity(it);
		}


		/**
		 * Diferença entre datas em dias
		 * 
		 * @param data1
		 * @param data2
		 * @return
		 */
		public static long obterDiferencasDatasDias(Date data1, Date data2) {
			long dias;
			long umDia = 86400000; // um dia possui 86400000ms
			long date1, date2;
			date1 = data1.getTime();
			date2 = data2.getTime();
			dias = (date2 - date1) / umDia;
			return (dias < 0) ? dias * -1 : dias;
		}
		
		/**
		 * Fechar apk
		 * 
		 * @author Carlos Chaves
		 * @param Context, boolean
		 */
		public static void sairAplicacao(final Context context){
			
			Bluetooth.desativarBluetooth();
			
			//Chama a activity main do manifest
			/*Intent i  = ((Activity) context).getBaseContext().getPackageManager().getLaunchIntentForPackage( ((Activity) context).getBaseContext().getPackageName() );
	        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        context.startActivity(i);*/
	         
	        //Chama tela home do android
	        Intent startMain = new Intent(Intent.ACTION_MAIN);
	        startMain.addCategory(Intent.CATEGORY_HOME);
	        startMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
	        context.startActivity(startMain);
	        
	        
	        try {
				ZebraUtils.getInstance(context).disconnect();
			} catch (ZebraException e) {
				e.printStackTrace();
			}
	        
	        android.os.Process.killProcess(android.os.Process.myPid());

		}
		
//		public static File[] verificaArquivoRetorno(){
//			File file = new File(ConstantesSistema.CAMINHO_RETORNO);
//			
//			File[] prefFiles = file.listFiles();
//			 
//			if(prefFiles != null && prefFiles.length != 0){
//				return prefFiles;
//			}else{
//				return null;
//			}
//		}
		
		public static String getVersaoSistema(Context context){
	    	
	    	PackageInfo pinfo = null;
	    	
			try {
				pinfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}
	    	
			return pinfo.versionName;
	    	
	    }
		
		public static ShapeDrawable desenhaCirculo(Context context, ShapeDrawable circulo){
			
			// pega a resolução do aparelho
			DisplayMetrics dm = context.getResources().getDisplayMetrics();

			int width = 165;
			int height = 165;
			
			// para o círculo ficar no meio
			int x = dm.widthPixels/2 - width/2;
			int y = dm.heightPixels/2 - height/2-30;
			
			circulo.getPaint().setStyle(Paint.Style.STROKE);
			circulo.getPaint().setStrokeWidth(3);
			circulo.getPaint().setARGB(255, 255, 0, 0);
			circulo.setBounds(x, y, x + width, y + height);
			
			return circulo;
		}
		
		public static void enviaEmBackground(ImovelConta imovel,Context c) {
			try {
    				
				Fachada.setContext(c);
				Fachada.getInstance().enviarEmBackground(imovel);
				
			} catch (FachadaException e) {
				Log.e(ConstantesSistema.CATEGORIA,e.getMessage());
				e.printStackTrace();
			}	
		}
		
		/**
		 * Método que recebe um java.util.Date e retorna uma String no formato
		 * dia/mês/ano.
		 * 
		 * @param date
		 *            Data a ser formatada.
		 * @return String no formato dia/mês/ano.
		 */
		public static String dataFormatada(Date date) {
			StringBuffer retorno = new StringBuffer();

			if (date != null && !date.equals("")) {

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				// Dia
				retorno.append(Util.adicionarZerosEsquerdaNumero(2, calendar.get(Calendar.DAY_OF_MONTH) + ""));
				retorno.append("_");
				// Mes
				retorno.append(Util.adicionarZerosEsquerdaNumero(2, calendar.get(Calendar.MONTH) + 1 + ""));
				// Ano
				retorno.append("_");
				retorno.append(calendar.get(Calendar.YEAR));
			}

			return retorno.toString();
		}
	    
//	    public static int getVersionCode(Context context){
//	    	
//	    	PackageInfo pinfo = null;
//	    	
//			try {
//				pinfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
//			} catch (NameNotFoundException e) {
//				e.printStackTrace();
//			}
//	    	
//			return pinfo.versionCode;
//	    }
		
		public static void apagarArquivoRetorno () {
			//reseta o string builder para que não haja duplicação de registros
	        ArquivoRetorno.montaArquivo = new StringBuilder();
	        apagarArquivos(ConstantesSistema.CAMINHO_RETORNO);
		}
		
		public static void apagarArquivos(String caminho){
			//Deleta os arquivo da pasta de retorno, caso haja algum
			File filesDir = new File( caminho );
			File[] prefFiles = filesDir.listFiles();
	        if(prefFiles != null){
		        for (File file : prefFiles) {
		        	file.delete();
		        }
	        }
		}
		
		/**
		 * Verifica se uma String é um número Inteiro  
		 * 
		 * @author Carlos Chaves
		 * @date 10/01/2013
		 * */
		public static Boolean isInteger(String str){
			Boolean retorno;
			try {  
				Integer.parseInt(str);  
		        retorno = Boolean.TRUE;
			} catch (NumberFormatException e) {       
				retorno = Boolean.FALSE;
			}
			return retorno;
		}
		
		public static int getVersaoCode(Context context){
	    	
	    	PackageInfo pinfo = null;
	    	
			try {
				pinfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}
	    	
			return pinfo.versionCode;
	    	
	    }
		
		/**
		 * Método responsável por<br>
		 * por fazer parser String para<br>
		 * Short
		 * @author Jonathan Marcos
		 * @since 04/03/2015
		 * @param valor
		 * @return Short
		 */
		public static Short verificarNuloShort(String valor) {
			if (valor == null || valor.trim().equals("")) {
				return null;
			} else {
				return new Short(valor.trim());
			}
		}
}
