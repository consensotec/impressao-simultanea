
package com.br.ipad.isc.fachada;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Vector;

import android.content.Context;

import com.br.ipad.isc.conexao.ComunicacaoWebServer;
import com.br.ipad.isc.conexao.ConexaoWebServer;
import com.br.ipad.isc.io.ArquivoRetorno;

/**
 * A classe FachadaWebServer agrupa serviços que precisam da rede. Ela conecta quando
 * necessário, envia os dados para o servidor, recebe a resposta do servidor e
 * repassa para o listener.
 * 
 * @author Rafael Palermo de Araújo
 */
public class FachadaWebServer {

	public static boolean indcConfirmacaRecebimento = false;

	/**
	 * Identificador da requisição Cliente->Servidor de confirmar recebimento do
	 * roteiro.
	 */
	public static final byte CS_CONFIRMAR_RECEBIMENTO = 3;
	
	private boolean requestOK = false;

	private ConexaoWebServer conexaoWebServer;
	
	/**
	 * Com base no padrão de Projeto chamado Singleton, FachadaWebServer tem apenas
	 * uma única instância em todo o contexto da aplicação.
	 */
	private static FachadaWebServer instance;


	/**
	 * Retorna a instância da fachada de rede.
	 * 
	 * @return A instância da fachada de rede.
	 */
	public static FachadaWebServer getInstancia() {
		if (FachadaWebServer.instance == null) {
			FachadaWebServer.instance = new FachadaWebServer();
		}
		return FachadaWebServer.instance;
	}

	/**
	 * Repassa as requisições ao servidor.
	 * 
	 * @param parametros
	 *            Vetor de parâmetros da operação.
	 * @param recebeResposta
	 *            Boolean que diz se recebe ou não um InputStream do servidor
	 */
	public boolean iniciarServicoRede(ArrayList<Object> parametros, boolean enviarIMEI,Context context) {
		return ConexaoWebServer.getInstancia().iniciarServicoRede(parametros, enviarIMEI, context);		
	}

	/**
	 * Envia o arquivo gerado do imovel para o servidor
	 * 
	 * @param Array
	 *            de bytes com o arquivo
	 * @return 
	 */
	public boolean enviarImovel(byte[] imovel,Context context) throws IOException {
		return ConexaoWebServer.getInstancia().enviarImovel(imovel, context);
	}

	/**
	 * Envia o arquivo gerado do imovel para o servidor
	 * 
	 * @param Array
	 *            de bytes com o arquivo
	 */
	public void finalizarLeitura(byte[] arquivoRetorno,Context context) throws IOException {
		requestOK = ConexaoWebServer.getInstancia().finalizarLeitura(arquivoRetorno, context);
	}

	public boolean isRequestOK() {
		return requestOK;
	}

	public void iniciarConexaoWebServer(Context ctx){
		conexaoWebServer = new ConexaoWebServer(ctx);
	}
	
	public InputStream comunicar( String url, Vector<Object> parametros ) throws IOException, MalformedURLException{
		return this.conexaoWebServer.comunicar(url, parametros);
	}
	
	public int getFileLength() {
		return this.conexaoWebServer.getFileLength();
	}
	
	public boolean serverOnline(){
		return this.conexaoWebServer.serverOnline();
	}
	
	/**
	 * 
	 * Manda um sinal para GSAN informando que
	 * a rota foi inicializada com sucessoo.
	 * 
	 */	
	public boolean routeInitializationSignal(){
		return this.conexaoWebServer.routeInitializationSignal();
	}
	
	// Só podemos pegar a mensagem de error uma vez
	public static String getMensagemError() {
		return ConexaoWebServer.getMensagemError();
	}
	
	public void setContextComunicacaoWebServer(Context ctx){
		ComunicacaoWebServer.getInstancia().setContext(ctx);
	}
	
	 public Object[] comunicacao(short tipoFinalizacao, ArquivoRetorno arquivoRetorno, int posicao, boolean continua){	
		 return ComunicacaoWebServer.getInstancia()
				 .comunicacao(tipoFinalizacao, arquivoRetorno, posicao, continua);
	 }
	 
	 public int enviarDados(String nomeArquivo, short tipoFinalizacao,Context contexto, StringBuilder montaArquivo) {
		 return ComunicacaoWebServer.getInstancia()
				 .enviarDados(nomeArquivo, tipoFinalizacao, contexto, montaArquivo);
	 }
}