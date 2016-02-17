
package com.br.ipad.isc.conexao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import android.content.Context;
import android.util.Log;

import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.bean.SistemaParametros;
import com.br.ipad.isc.controladores.ControladorBasico;
import com.br.ipad.isc.controladores.ControladorImovelConta;
import com.br.ipad.isc.excecoes.ControladorException;
import com.br.ipad.isc.fachada.Fachada;
import com.br.ipad.isc.io.ArquivoRetorno;
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
public class ComunicacaoWebServer {

	private static ArrayList<Integer> idsImoveisGerados = new ArrayList<Integer>();
    private static ComunicacaoWebServer instancia;
//	  
	
	public static ArrayList<Integer> getIdsImoveisGerados() {
		return idsImoveisGerados;
	}

	public static void setIdsImoveisGerados(ArrayList<Integer> idsImoveisGerados) {
		ComunicacaoWebServer.idsImoveisGerados = idsImoveisGerados;
	}
	
	public ComunicacaoWebServer(){
	}
	
	public void setContext(Context context){
		ConexaoWebServer.getInstancia().setContext(context);
	}
	
	 public static ComunicacaoWebServer getInstancia() {
		    if (instancia == null) {
		        instancia = new ComunicacaoWebServer();
		    }

	    	return instancia;
	 }
	 
	 @SuppressWarnings("unchecked")
	 public Object[] comunicacao(short tipoFinalizacao, ArquivoRetorno arquivoRetorno, int posicao, boolean continua){	

		Object[] retorno = null;
		retorno = arquivoRetorno.gerarArquivoRetorno( tipoFinalizacao,posicao,continua );
		
		if(retorno != null ){
			
			ArrayList<Integer> idGerado = (ArrayList<Integer>) retorno[1];
			if(idGerado != null && idGerado.size()!= 0){
				idsImoveisGerados.add(idGerado.get(0));
			}
						
		}		
		
		return retorno;
		
	 }
	
	@SuppressWarnings("rawtypes")
    public int enviarDados(String nomeArquivo, short tipoFinalizacao,Context contexto, StringBuilder montaArquivo) {
		Vector linhasRetorno;
		int retorno = ConexaoWebServer.ERRO_GENERICO;

		ConexaoWebServer.getInstancia().setContext(contexto);	
		if(ConexaoWebServer.getInstancia().serverOnline()){
			
			try {

				if(tipoFinalizacao != ArquivoRetorno.ARQUIVO_LIDOS_ATE_AGORA){
					
					linhasRetorno = Util.lerArquivoRetorno(nomeArquivo); 				
		
					StringBuilder arquivoImovel = new StringBuilder();
					//int linhasLidas = 0;
		
					if(linhasRetorno != null){
						for (int i = 0; i < linhasRetorno.size(); i++) {
							String linha = (String) linhasRetorno.elementAt(i);
			
							//linhasLidas++;
			
							for (int j = 0; j < linha.length(); j++) {
								arquivoImovel.append(linha.charAt(j));
			
							}
			
							arquivoImovel.append("\n");
			
						}
			
						if (tipoFinalizacao == ArquivoRetorno.ARQUIVO_INCOMPLETO || tipoFinalizacao == ArquivoRetorno.ARQUIVO_COMPLETO
								|| tipoFinalizacao == ArquivoRetorno.ARQUIVO_TODOS_OS_CALCULADOS) {
							
							boolean enviado = ConexaoWebServer.getInstancia().finalizarLeitura(arquivoImovel.toString().getBytes(),contexto);
							if(enviado){
								retorno = ConstantesSistema.OK;
							}
			
						}
						
					}else{
						retorno = ConstantesSistema.ERRO_GENERICO;
					}
					
				} else if(montaArquivo != null && montaArquivo.length() != 0){
					boolean enviado = ConexaoWebServer.getInstancia().enviarImovel(montaArquivo.toString().getBytes(),contexto);
					if(enviado){
						retorno = ConstantesSistema.OK;
					}
	
				}
			} catch (IOException e) {
				Log.e(ConstantesSistema.CATEGORIA, e.getMessage());
				e.printStackTrace();
			}
			
		}else{
			retorno = ConstantesSistema.ERRO_SERVIDOR_OFF_LINE;
		}
		
		return retorno;
    }
    

	
	/**
	 * Envia o imovel passado para o servidor
	 * 
	 * @author Fernanda Almeida
	 * @date 03/09/2012
	 * @param imovelParaProcessar
	 *            imovel a ser enviado
	 */
	public static boolean enviarImovelOnLine(ImovelConta imovelParaProcessar,Context contexto) throws ControladorException {
		boolean enviou = false;
		if (!imovelParaProcessar.isCondominio()) {
			SistemaParametros sistemaParametros = SistemaParametros.getInstancia();
			
			if (sistemaParametros.getIndicadorTransmissaoOffline().equals(ConstantesSistema.NAO)){
				int quantidadeEnvioImovel =1;
				
				if (quantidadeEnvioImovel <= 1) {
					enviou = comunicaImovelOnline(imovelParaProcessar,contexto);
				} 
			}
		}			

		return enviou;
	}
	
	/**
	 * Envia o imovel passado para o servidor
	 * 
	 * @author Bruno Barros
	 * @date 23/11/2009
	 * @param imovelParaProcessar
	 *            imovel a ser enviado
	 */
	public static void enviarImovelOnLine(ArrayList<ImovelConta> imoveisParaProcessar,Context contexto) {

		StringBuffer mensagem = new StringBuffer();

		for (int i = 0; i < imoveisParaProcessar.size(); i++) {
			mensagem = mensagem.append(ArquivoRetorno.getInstancia().gerarArquivoRetornoOnLine((ImovelConta) imoveisParaProcessar.get(i)));
		}

		try {
			ConexaoWebServer.getInstancia().enviarImovel(mensagem.toString().getBytes(),contexto);
		} catch (IOException e) {
			Log.e(ConstantesSistema.CATEGORIA,e.getMessage());
			e.printStackTrace();
		}

		
		if (ConexaoWebServer.getInstancia().isRequestOK()) {

			ImovelConta imovel = imoveisParaProcessar.get(0);
			try {
				ControladorImovelConta.getInstance().atualizarIndicadorImovelEnviado(imovel.getMatriculaCondominio().toString());
			
			} catch (ControladorException e) {
				Log.e(ConstantesSistema.CATEGORIA,e.getMessage());
				e.printStackTrace();
			}
		
		}
		
		for (ImovelConta imovelContaFoto : imoveisParaProcessar) {
			Fachada.getInstance().enviarFotosOnline(imovelContaFoto);
			
		}
	}
	private synchronized static boolean comunicaImovelOnline(ImovelConta imovel,Context contexto) throws ControladorException {
		// Marcamos esse imovel medido na rota de
		// marcação quando o mesmo calculado. Os não
		// Medidos serão marcador apenas quando forem impressos
		// TODO
//		if (conf.getIndcRotaMarcacaoAtivada() == ConstantesSistema.SIM
//				&& (imovel.getIndcImovelCalculado() == ConstantesSistema.SIM && imovel.getSequencialRotaMarcacao() == null)) {
//			imovel.setSequencialRotaMarcacao(conf.getSequencialAtualRotaMarcacao());
//			Repositorio.salvarObjeto(imovel);
//		}
		
		ArquivoRetorno arquivoRetorno = new ArquivoRetorno();
		StringBuffer mensagem = arquivoRetorno.gerarArquivoRetornoOnLine(imovel);

		boolean enviou = false;

		try {
						
			System.out.println(mensagem.toString());
			
			enviou = ConexaoWebServer.getInstancia().enviarImovel(mensagem.toString().getBytes(),contexto);

			if (enviou) {				
				
				ImovelConta imovelContaBase = (ImovelConta) ControladorBasico.getInstance()
					.pesquisarPorId(imovel.getId(), imovel);
				imovelContaBase.setIndcImovelEnviado(ConstantesSistema.SIM);
				
				ControladorBasico.getInstance().atualizar(imovelContaBase);
								
			} else {
//				System.out.println("Passou direto");
			}
			
		} catch (IOException e) {
			Log.e(ConstantesSistema.CATEGORIA,e.getMessage());
			e.printStackTrace();
		}
		
		return enviou;
	}
	

	public static boolean enviaCalculado(ImovelConta imovel,Context contexto) throws ControladorException {
		boolean retorno = true;
		if(imovel.getIndcImovelCalculado().equals(ConstantesSistema.SIM)){
    		retorno = enviarImovelOnLine(imovel,contexto);
		}
		
		return retorno;
	}
}

