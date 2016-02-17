
package com.br.ipad.isc.conexao;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;

import android.content.Context;
import android.util.Log;

import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.controladores.ControladorBasico;
import com.br.ipad.isc.controladores.ControladorImovelRevisitar;
import com.br.ipad.isc.controladores.ControladorRetorno;
import com.br.ipad.isc.excecoes.ControladorException;
import com.br.ipad.isc.util.ConstantesSistema;
import com.br.ipad.isc.util.Util;

/**
 * Classe responsável pela conexão do android
 * com o GSAN. Todas as requisições devem passar
 * por aqui
 * 
 * Essa classe deve sempre ser utilizada
 * a partir de uma thread, para que não
 * utilize a thread principal;
 * 
 * @author Fernanda Almeida
 * @date 01/04/2012
 *
 */
public class ConexaoWebServer {

	public static ConexaoWebServer instancia;
	private InputStream respostaOnline;
	
	private char tipoArquivo;
	private static String mensagemError = null;	
		
		
	// Contexto, para que, ao finalizarmos qualquer operação, possamos
	// mudar de tela
	private Context context = null;
	private HttpURLConnection conn = null;

	StringBuffer buffer = new StringBuffer();
	StringBuffer bufferValorParametro = new StringBuffer();
	
	private boolean serverOnline = false;
	
	private int fileLength;
	
	private boolean requestOK = false;
	
	// Identificadores das requisições
	private static final byte PACOTE_ATUALIZAR_MOVIMENTO = 1;
	private static final byte PACOTE_FINALIZAR_LEITURA = 2;
	public static final int ERRO_GENERICO = 0;		
	public static final int ERRO_ABORTAR = 2;
	public static final int ERRO_GERACAO_ARQUIVO = 3;
	public static final int ERRO_SINAL_FINALIZACAO = 5;
	public static final int ABORTADO = 7;
			
	/**
	 * Mensagem de requisição a ser enviada ao servidor.
	 */
	private byte[] mensagem;

	private static String respostaServidor = ConstantesSistema.RESPOSTA_ERRO;
	
	public static ConexaoWebServer getInstancia() {
		if (instancia == null) {
			instancia = new ConexaoWebServer();
		}

		return instancia;
	}

	public static String getRespostaServidor() {
		return respostaServidor;
	}

	/**
	 * Define a mensagem de requisição a ser enviada ao servidor.
	 * 
	 * @param mensagem
	 *            Mensagem empacotada.
	 */
	public void setMensagem(byte[] mensagem) {
		this.mensagem = mensagem;
	}
		
	public void setContext(Context context){
		this.context=context;
	}
	/**
	 * Contrutor padrão.
	 * 
	 * @param context
	 */
	public ConexaoWebServer( Context context ){
		super();
		this.context = context;
	}
	
	public ConexaoWebServer() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * Inicia a execução na nova thread criada.
	 */
	public synchronized boolean enviarMensagem() {

		boolean sucesso = false;

		synchronized (mensagem) {

			try {

				try {
					if(serverOnline()){
						this.comunica( ConstantesSistema.ACAO, this.mensagem );
						sucesso = true;
					}else{
						sucesso = false;
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
					Log.e( ConstantesSistema.CATEGORIA , e.getMessage());
					sucesso = false;
				} catch (IOException e) {
					e.printStackTrace();
					Log.e( ConstantesSistema.CATEGORIA , e.getMessage());
					sucesso = false;
				}

				// Verificamos se a resposta da conexão foi ok
				if (respostaServidor.equals(ConstantesSistema.RESPOSTA_OK) && sucesso) {
					sucesso = true;
				}else{
					sucesso = false;
				}

			} catch (Exception e) {
				// Informamos que um erro ocorreu
				//					System.out.println("Resposta Erro!");
				sucesso = false;
				Log.e(ConstantesSistema.CATEGORIA,e.getMessage());
				e.printStackTrace();
				respostaServidor = ConstantesSistema.RESPOSTA_ERRO;
				sucesso = false;
			} finally {
				if (conn != null) {
					conn.disconnect();
					
				} else {
					sucesso = false;
					respostaServidor = ConstantesSistema.RESPOSTA_ERRO;					
					sucesso = false;
				}
				

			}
		}
		return sucesso;
	}
	
	
	/**
	 * 
	 * Manda um sinal para GSAN informando que
	 * a rota foi finalizada com sucessoo.
	 * 
	 */	
	public boolean routeFinalizationSignal(){
		
		boolean sucesso = false;
		
		serverOnline = serverOnline();		
	
		// Verificamos se o servidor está online
		if ( serverOnline ){		
			Vector<Object> parametros = new Vector<Object>(2);
			parametros.add( new Byte(ConstantesSistema.FINALIZAR_ROTEIRO));
			parametros.add( new Long( Util.getIMEI( context ) ) );
		}
		
		return sucesso;
	}
	
	/**
	 * 
	 * Manda um sinal para GSAN informando que
	 * a rota foi inicializada com sucessoo.
	 * 
	 */	
	public boolean routeInitializationSignal(){
		
		boolean sucesso = false;
		
		serverOnline = serverOnline();		
	
		// Verificamos se o servidor está online
		if ( serverOnline ){
			Vector<Object> parametros = new Vector<Object>(2);
			parametros.add( new Byte(ConstantesSistema.SINAL_INICIALIZACAO_ROTEIRO ) );
			parametros.add( new Long( Util.getIMEI( context ) ) );
			
			try {
				InputStream in = this.comunicar( ConstantesSistema.ACAO, parametros );
				sucesso = in.read() == '*';
			} catch (MalformedURLException e) {
				e.printStackTrace();
				Log.e( ConstantesSistema.CATEGORIA , e.getMessage());
				sucesso = false;
			} catch (IOException e) {
				e.printStackTrace();
				Log.e( ConstantesSistema.CATEGORIA , e.getMessage());
				sucesso = false;
			}
		}
		
		return sucesso;
	}	
	
	
	/**
	 * 
	 * Manda a informação da OS para o GSAN
	 * 
	 * @param idImovel id da os que será enviada
	 * @return
	 */
	public boolean enviaImovel( Integer idImovel ) throws ControladorException {
		boolean sucesso = false;
		
		try {		
			serverOnline = serverOnline();
			
			// Verificamos se o servidor está online
			if ( serverOnline ){
				ControladorRetorno rc = ControladorRetorno.getInstance();
				String returnData = rc.geraRetornoImovel( idImovel );				
				
				Vector<Object> parametros = new Vector<Object>(3);
				parametros.add( new Byte(ConstantesSistema.ENVIA_IMOVEL ) );
				parametros.add( new Long( Util.getIMEI( context ) ) );
				parametros.add( new Long( returnData.getBytes().length ) );							
				
				parametros.add( returnData.getBytes("UTF-8") );
			
				if ( sucesso ){
					ImovelConta imovelConta = (ImovelConta) ControladorBasico.getInstance()
							.pesquisarPorId(idImovel, new ImovelConta());							
					imovelConta.setIndcImovelEnviado( ConstantesSistema.SIM );
					ControladorBasico.getInstance().atualizar(imovelConta);					
				}

				return sucesso;			
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e( ConstantesSistema.CATEGORIA , e.getMessage());
			sucesso = false;			
		}
		
		return sucesso;		
	}

			
	public InputStream comunica( String url, byte[] mensagem ) throws IOException, MalformedURLException, ControladorException {
		InputStream in = null;
		
		URL u = new URL( url );
		
		if(ConstantesSistema.HOST.equals(ConstantesSistema.IP_CAERN_PRODUCAO)){
			conn = ( HttpURLConnection ) u.openConnection(ConstantesSistema.PROXY_CAERN);
		}else{
			conn = ( HttpURLConnection ) u.openConnection();
		}
		
		synchronized (conn) {
			Vector<Object> parametros = new Vector<Object>(2);
			parametros.add(mensagem);
			
			// Mostramos no log qual a url do arquivo que estamos tentando baixar
			if (!parametros.isEmpty() && parametros.contains(ConstantesSistema.DOWNLOAD_ARQUIVO) && parametros.firstElement()
					.equals(ConstantesSistema.DOWNLOAD_ARQUIVO)){
				Log.i( ConstantesSistema.CATEGORIA, "Http.downloadArquivo: " + url );
			}
			else if (!parametros.isEmpty() && parametros.contains(ConstantesSistema.DOWNLOAD_APK) && parametros.firstElement().equals(ConstantesSistema.DOWNLOAD_APK)){
				Log.i( ConstantesSistema.CATEGORIA, "Http.downloadApk: " + url );
			}
						
			// Setamos os parâmetros da conexão
			conn.setRequestMethod( "POST" );
		    conn.setRequestProperty("Content-Type", "application/octet-stream");
		    conn.setRequestProperty("User-Agent", "Profile/MIDP-2.0 Configuration/CLDC-1.1");
		    conn.setRequestProperty("Content-Length", Integer.toString(mensagem.length));
		    
		    if ( parametros.contains(ConstantesSistema.PING ) ){
		    	conn.setConnectTimeout( 3000 );	
		    }
		    
			// Tanto informamos parametros na requisição quanto
		    // recebemos dados da mesma. Por isso, ambas as
		    // propriedades estão configuradas para verdadeiro
		    conn.setDoInput( true );			
			conn.setDoOutput( true );
			
			// Estabelecemos o canal de conexão
			conn.connect();
			
			// Enviamos a requisição em si
			conn.getOutputStream().write(mensagem );	
			
			// Selecionamos o tamanho do arquivo para que possamos
			// informar a barra de progresso, o seu MAX
			fileLength = conn.getContentLength();
			
			// Logamos o tamanho do arquivo
			Log.i( ConstantesSistema.CATEGORIA, "FileSize: " + fileLength );			
			
			// Pegamos o retorno da requisição
			in = conn.getInputStream();
			StringBuffer buffer = new StringBuffer();
			String valor = Util.getValorRespostaInputStream(in);
			// Só foi ok, caso o servidor tenha enviado
			// o caracter esperado
			if (valor.equals(ConstantesSistema.RESPOSTA_OK)) {
	
				for (int i = 1; i <= fileLength && fileLength!=1; i++) {
					// ---INICIO E71
					// char caracter = (char) isr.read();
					// ---FIM E71
	
					// ---INICIO E5
					// char caracter = (char) resposta.read();
					// ---FIM E5
					char caracter = Util.getCharValorRespostaInputStream(in);
					if(caracter != ' '){
					
					
						buffer.append(caracter);
		
						boolean ultimoCaracter = i == conn.getContentLength();
		
						if (controlarParametros(buffer, caracter, bufferValorParametro, ultimoCaracter)) {
		
							buffer = new StringBuffer();
							bufferValorParametro = new StringBuffer();
		
							continue;
						}
					}
	
					// Caso ache arquivos de retorno,
					// não haverão parametros subsequentes
					if (buffer.toString().indexOf(ConstantesSistema.PARAMETRO_ARQUIVO_ROTEIRO) > -1
							|| buffer.toString().indexOf(ConstantesSistema.PARAMETRO_APK) > -1) {
						fileLength = (int) conn.getContentLength() - (i + 1);
						break;
	
					}
				}
	
				buffer = null;
	
				respostaServidor = ConstantesSistema.RESPOSTA_OK;
	
				// Setamos a resposta encontrada
				this.setRespostaOnline((InputStream) in);
				// Em qualquer outra situação
			} else {
				// Guardamos a resposta
				respostaServidor = ConstantesSistema.RESPOSTA_ERRO;
	
				for (int i = 1; i <= conn.getContentLength(); i++) {
	
					// ---INICIO E71
					// char caracter = (char) isr.read();
					// ---FIM E71
	
					// ---INICIO E5
					// char caracter = (char) resposta.read();
					// ---FIM E5
	
					char caracter = Util.getCharValorRespostaInputStream(in);
					buffer.append(caracter);
	
					boolean ultimoCaracter = i == conn.getContentLength();
	
					if (controlarParametros(buffer, caracter, bufferValorParametro, ultimoCaracter)) {
						buffer = new StringBuffer();
						bufferValorParametro = new StringBuffer();
						continue;
					}
				}
			}
		}	
		// Retorna a requisição para tratamento do tipo de chamada solicitada
		return in;		
	}
	
	public InputStream comunicar( String url, Vector<Object> parametros ) throws IOException, MalformedURLException{
		InputStream in = null;
		URL u = new URL( url );
		
		if(ConstantesSistema.HOST.equals(ConstantesSistema.IP_CAERN_PRODUCAO)){
			conn = ( HttpURLConnection ) u.openConnection(ConstantesSistema.PROXY_CAERN);
		}else{
			conn = ( HttpURLConnection ) u.openConnection();
		}
		
		synchronized (conn) {
			// Mostramos no log qual a url do arquivo que estamos tentando baixar
			if (!parametros.isEmpty() && parametros.contains(ConstantesSistema.DOWNLOAD_ARQUIVO) && 
					parametros.firstElement().equals(ConstantesSistema.DOWNLOAD_ARQUIVO)){
				Log.i( ConstantesSistema.CATEGORIA, "Http.downloadArquivo: " + url );
			}
			else if (!parametros.isEmpty() && parametros.contains(ConstantesSistema.DOWNLOAD_APK) && 
					parametros.firstElement().equals(ConstantesSistema.DOWNLOAD_APK)){
				Log.i( ConstantesSistema.CATEGORIA, "Http.downloadApk: " + url );
			}else if (!parametros.isEmpty() && parametros.contains(ConstantesSistema.BAIXAR_ROTA) && 
					parametros.firstElement().equals(ConstantesSistema.BAIXAR_ROTA)){
				Log.i( ConstantesSistema.CATEGORIA, "Http.downloadRota: " + url );
			}
			
			// Empacotamos 2 objetos. O primeiro, contento
			// o tipo da requisição e o segundo com o IMEI
			// do celular
			byte[] pacoteParametros = empacotarParametros( parametros );		
			
			// Setamos os parâmetros da conexão
			conn.setRequestMethod( "POST" );
		    conn.setRequestProperty("Content-Type", "application/octet-stream");
		    conn.setRequestProperty("User-Agent", "Profile/MIDP-2.0 Configuration/CLDC-1.1");
		    conn.setRequestProperty("Content-Length", pacoteParametros.length+"");
		    conn.setRequestProperty("Accept-Encoding", "identity");
		    
		    if ( parametros.contains(ConstantesSistema.PING)){
		    	conn.setConnectTimeout( 3000 );	
		    }
		    
			// Tanto informamos parametros na requisição quanto
		    // recebemos dados da mesma. Por isso, ambas as
		    // propriedades estão configuradas para verdadeiro
			conn.setDoInput( true );
			conn.setDoOutput( true );
			// Estabelecemos o canal de conexão
			conn.connect();
			
			// Enviamos a requisição em si
			conn.getOutputStream().write( pacoteParametros );			
			
			// Selecionamos o tamanho do arquivo para que possamos
			// informar a barra de progresso, o seu MAX
			if(conn != null){
				
				fileLength = conn.getContentLength();
				// Logamos o tamanho do arquivo
				Log.i( ConstantesSistema.CATEGORIA, "FileSize: " + fileLength );			
				
				// Pegamos o retorno da requisição
				in = conn.getInputStream();
			}
		}
		
		// Retorna a requisição para tratamento do tipo de chamada solicitada
		return in;		
	}
			
	/**
	 * 
	 * Verifica se o servidor do GSAN está
	 * online
	 * 
	 * @autor Bruno Barros
	 * @date 01/09/2011
	 * 
	 * @return - Caso online, true, senão false
	 */
	public boolean serverOnline(){
				boolean sucesso = true;
		
		try {
//			 Verificamos se o servidor está online
			Vector<Object> parametros = new Vector<Object>(3);
			parametros.add( new Byte(ConstantesSistema.PING) );
			parametros.add( new Long( Util.getIMEI( context ) ) );

			InputStream in = this.comunicar( ConstantesSistema.ACAO, parametros );
			
			if(in != null){
				sucesso = in.read() == '*';
				return sucesso;
			}else{
				return false;
			}
					
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , e.getMessage());
			sucesso = false;			
		}
		
		return sucesso;		
	}
	
	
	
	public byte[] empacotarParametros(Vector<Object> parametros) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);

		byte[] resposta = null;

		parametros.trimToSize();

		// escreve os dados no OutputStream
		if (parametros != null) {
			int tamanho = parametros.size();
			for (int i = 0; i < tamanho; i++) {
				Object param = parametros.elementAt(i);
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
	public InputStream getRespostaOnline() {
		return this.respostaOnline;
	}

	public void setRespostaOnline(InputStream respostaOnline) {
		this.respostaOnline = respostaOnline;
	}

	public void setFileLength(int fileLength) {
		this.fileLength = fileLength;
	}

	public int getFileLength() {
		return fileLength;
	}

	public void setTipoArquivo(char tipoArquivo) {
		this.tipoArquivo = tipoArquivo;
	}

	public char getTipoArquivo() {
		return tipoArquivo;
	}

	// Só podemos pegar a mensagem de error uma vez
	public static String getMensagemError() {
		String temp = mensagemError;
		mensagemError = null;
		return temp;
	}
	
	public boolean controlarParametros(StringBuffer buffer, char caracter, StringBuffer bufferValorParametro,
			boolean ultimoCaracter) throws ControladorException {

		/*
		 * Caso se ja um dos parametros abaixo só podemos comecar a receber o
		 * valor quando estivermos lendo o primeiro caracter pos identificador
		 * de parametros. Por isso a comparacao com o tamanho do buffer
		 */

		// Mensagem
		if ((buffer.toString().indexOf(ConstantesSistema.PARAMETRO_MENSAGEM) > -1 && buffer.toString().length() > 
				ConstantesSistema.PARAMETRO_MENSAGEM.length())
				||
				// Imoveis a revisitar
				(buffer.toString().indexOf(ConstantesSistema.PARAMETRO_IMOVEIS_PARA_REVISITAR) > -1 && 
						buffer.toString().length() > ConstantesSistema.PARAMETRO_IMOVEIS_PARA_REVISITAR.length())
				||
				// Tipo de arquivo carregado
				(buffer.toString().indexOf(ConstantesSistema.PARAMETRO_TIPO_ARQUIVO) > -1 && 
						buffer.toString().length() > ConstantesSistema.PARAMETRO_TIPO_ARQUIVO.length())) {

			// Caso encontremos o caracter de fim de parametro
			if (caracter == ConstantesSistema.CARACTER_FIM_PARAMETRO || ultimoCaracter) {

				// Mensagem
				if (buffer.toString().indexOf(ConstantesSistema.PARAMETRO_MENSAGEM) > -1) {
					mensagemError = bufferValorParametro.toString();
//					SistemaParametros sistemaParametros = SistemaParametros.getInstancia();
//					sistemaParametros.setMensagemErro(mensagemError);
//					try {
//						ControladorSistemaParametros.getInstance().atualizarSistemaParametros(sistemaParametros);
//					} catch (ControladorException e) {
//						Log.e(tag, msg)
//						e.printStackTrace();
//					}
					// Imoveis a revisitar
				} else if (buffer.toString().indexOf(ConstantesSistema.PARAMETRO_IMOVEIS_PARA_REVISITAR) > -1) {
					// this.setMatriculasImoveisRevisitar(
					// bufferValorParametro.toString().getBytes() );
					ControladorImovelRevisitar.getInstance().setMatriculasRevisitar(bufferValorParametro.toString());
					// Tipo de arquivo carregado
				} else if (buffer.toString().indexOf(ConstantesSistema.PARAMETRO_TIPO_ARQUIVO) > -1) {
					this.setTipoArquivo(bufferValorParametro.toString().charAt(0));
				}

				return true;
			} else {
				bufferValorParametro.append(caracter);
			}
		}

		return false;
	}
	
	/**
	 * Repassa as requisições ao servidor.
	 * 
	 * @param parametros
	 *            Vetor de parâmetros da operação.
	 * @param recebeResposta
	 *            Boolean que diz se recebe ou não um InputStream do servidor
	 */
	public synchronized boolean iniciarServicoRede(ArrayList<Object> parametros, boolean enviarIMEI,Context context) {

		byte[] serverMsg = null;

		try {
			if (enviarIMEI) {
				// adiciona o IMEI como segundo parâmetro da mensagem
				parametros.add(1, Long.parseLong(Util.getIMEI(context)));
			}

			// transforma os parâmetros em array de bytes para enviar
		
			serverMsg = Util.empacotarParametros(parametros);
			setMensagem(serverMsg);
			requestOK = enviarMensagem();
		} catch (Exception e) {
			requestOK = false;
			e.printStackTrace();
		}
		return requestOK;		
	}

	
	public boolean enviarImovel(byte[] imovel,Context context) throws IOException {

		setContext(context);
		
		// cria o vetor de parâmetros
		ArrayList<Object> param = new ArrayList<Object>();
		ByteArrayOutputStream bais = new ByteArrayOutputStream();
		bais.write(PACOTE_ATUALIZAR_MOVIMENTO);
		bais.write(imovel);
		param.add(bais.toByteArray());
		param.trimToSize();
		requestOK = this.iniciarServicoRede(param, false,context);
		return requestOK;
	}

	/**
	 * Envia o arquivo gerado do imovel para o servidor
	 * 
	 * @param Array
	 *            de bytes com o arquivo
	 */
	public boolean finalizarLeitura(byte[] arquivoRetorno,Context context) throws IOException {

		setContext(context);
		
		// cria o vetor de parâmetros
		ArrayList<Object> param = new ArrayList<Object>();
		param.add(new Byte(PACOTE_FINALIZAR_LEITURA));
		param.add((byte[])arquivoRetorno);
		param.trimToSize();
		requestOK = this.iniciarServicoRede(param, true,context);
		return requestOK;
	}
	
	public boolean isRequestOK() {
		return requestOK;
	}
}