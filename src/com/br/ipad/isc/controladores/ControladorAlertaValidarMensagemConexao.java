package com.br.ipad.isc.controladores;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import android.content.Intent;
import android.util.Log;

import com.br.ipad.isc.R;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.bean.SistemaParametros;
import com.br.ipad.isc.conexao.ComunicacaoWebServer;
import com.br.ipad.isc.excecoes.ControladorException;
import com.br.ipad.isc.excecoes.FachadaException;
import com.br.ipad.isc.fachada.Fachada;
import com.br.ipad.isc.gui.FinalizaArquivoActivty;
import com.br.ipad.isc.gui.LoginActivity;
import com.br.ipad.isc.gui.MenuActivity;
import com.br.ipad.isc.gui.TabsActivity;
import com.br.ipad.isc.io.ArquivoRetorno;
import com.br.ipad.isc.io.ExportBancoDados;
import com.br.ipad.isc.util.ConstantesSistema;
import com.br.ipad.isc.util.Util;

public class ControladorAlertaValidarMensagemConexao extends ControladorAlertaBasico implements IControladorAlertaValidarMensagemConexao {

	private ImovelConta imovel;
	private byte caminhoSucesso;
	private byte caminhoErro;
	private boolean enviou;
	private int tipoFinalizacao;
	private String nomeArquivo;
	private String msgErro;
	private int progresso;
	private int posicao;
	
	public ControladorAlertaValidarMensagemConexao(ImovelConta imovel) {
		super();
		this.imovel = imovel;
	}
	
	public ControladorAlertaValidarMensagemConexao(ImovelConta imovel, byte caminhoSucesso, byte caminhoErro,boolean enviou,int tipoFinalizacao,String nomeArquivo) {
		super();
		this.caminhoErro = caminhoErro;
		this.caminhoSucesso = caminhoSucesso;
		this.imovel = imovel;
		this.enviou = enviou;
		this.tipoFinalizacao = tipoFinalizacao;
		this.nomeArquivo = nomeArquivo;
	}
	
	public ControladorAlertaValidarMensagemConexao(ImovelConta imovel, byte caminhoSucesso, byte caminhoErro,boolean enviou,int tipoFinalizacao) {
		super();
		this.caminhoErro = caminhoErro;
		this.caminhoSucesso = caminhoSucesso;
		this.imovel = imovel;
		this.enviou = enviou;
		this.tipoFinalizacao = tipoFinalizacao;
	}

	public ControladorAlertaValidarMensagemConexao(ImovelConta imovel, byte caminhoSucesso, byte caminhoErro,
			boolean enviou,int tipoFinalizacao,String nomeArquivo,
			String msgErro) {
		
		this.caminhoErro = caminhoErro;
		this.caminhoSucesso = caminhoSucesso;
		this.imovel = imovel;
		this.enviou = enviou;
		this.tipoFinalizacao = tipoFinalizacao;
		this.nomeArquivo = nomeArquivo;
		this.msgErro = msgErro;
	}

	public ControladorAlertaValidarMensagemConexao(ImovelConta imovel,String nomeArquivo,byte caminhoSucesso, 
			byte caminhoErro,boolean enviou, int progresso,int posicao,short tipoFinalizacao) {
		
		this.tipoFinalizacao = tipoFinalizacao;
		this.nomeArquivo = nomeArquivo;
		this.imovel = imovel;
		this.posicao = posicao;
		this.caminhoErro = caminhoErro;
		this.caminhoSucesso = caminhoSucesso;
		this.enviou = enviou;
		this.progresso = progresso;
		
	}
	
	public ControladorAlertaValidarMensagemConexao(byte caminhoSucesso, 
			byte caminhoErro,boolean enviou,String msgErro,String nomeArquivo) {
		
		//this.msgErro = msgErro;
		this.caminhoErro = caminhoErro;
		this.caminhoSucesso = caminhoSucesso;
		this.enviou = enviou;
		this.nomeArquivo = nomeArquivo;
		
	}


	@Override
	public void alertaPerguntaSim() {
//		boolean resposta =false;
//		try {

		if(caminhoErro == (byte) 0 && caminhoSucesso == ConstantesSistema.LISTA){

    		//Pega o arquivo de retorno
    		File file = new File(ConstantesSistema.CAMINHO_RETORNO +nomeArquivo);
    		
    		//Deleta o arquivo de retorno caso já existe
    		if(file.isFile()){
    			file.delete();
    		}    		

     		// reseta o string builder
     		ArquivoRetorno.montaArquivo = new StringBuilder();
			
     		if(getContext().getClass().equals(FinalizaArquivoActivty.class)){
     			FinalizaArquivoActivty ParentActivity;
    			ParentActivity = (FinalizaArquivoActivty) getContext();
    			Intent intent = new Intent(context, TabsActivity.class);
    			intent.putExtra("imovel", imovel);
    			ParentActivity.startActivity(intent);
    			ParentActivity.finish();
    		}else{
    			Intent intent = new Intent(context, TabsActivity.class);
    			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
    			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
    			intent.putExtra("imovel", imovel);
    			getContext().startActivity(intent);
    		}
     		
			
			
		}
						
		
//		} catch (ControladorException e) {
//			Log.e( ConstantesSistema.CATEGORIA , e.getMessage());
//			e.printStackTrace();
//		}
	}

	@Override
	public void alertaPerguntaNao() {		
			
		if(caminhoErro == (byte) 0 && caminhoSucesso == ConstantesSistema.LISTA){	
			
			Intent intent = new Intent(context, FinalizaArquivoActivty.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);    
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			
			intent.putExtra(getContext().getString(R.string.str_extra_progresso),progresso+"");

			// Gera o próximo imóvel
			intent.putExtra("posicao",posicao+1);  
			
			intent.putExtra(getContext().getString(R.string.str_extra_tipo_finalizacao), tipoFinalizacao+"");
    		
			getContext().startActivity(intent);
		
		}
	}

	@Override
	public void alertaMensagem() {
		Intent intent;
		SistemaParametros sistemaParametros = SistemaParametros.getInstancia();
		
		gravaDadosBackup();
		
		//if (sistemaParametros.getIndicadorTransmissaoOffline().equals(ConstantesSistema.NAO)){
			if(enviou){
				
		     	if (tipoFinalizacao == ArquivoRetorno.ARQUIVO_INCOMPLETO || tipoFinalizacao == ArquivoRetorno.ARQUIVO_COMPLETO
						|| tipoFinalizacao == ArquivoRetorno.ARQUIVO_TODOS_OS_CALCULADOS) {
	
		     		finalizaAplicacao();
		     		
		     	}else{
		     		ArrayList<Integer> ids = ComunicacaoWebServer.getIdsImoveisGerados();
			     	if (ids!= null && ids.size() > 0) {
							// Marcamos todos os imóveis enviados como já enviados
							for (int i = 0; i < ids.size(); i++) {					
								ImovelConta imovelConta = null;
								try {
									imovelConta = (ImovelConta) ControladorBasico.getInstance()
										.pesquisarPorId((Integer) ids.get(i), new ImovelConta());
								} catch (ControladorException e){
									Log.e(ConstantesSistema.CATEGORIA, e.getMessage());
									throw new FachadaException(e.getMessage());
								}
								
								imovelConta.setIndcImovelEnviado(ConstantesSistema.SIM);
								Fachada.getInstance().atualizar(imovelConta);
								
							}
			     	}
		     	}
		     	if(caminhoSucesso == ConstantesSistema.LISTA){
		    		
		     		intent = new Intent(getContext(),TabsActivity.class);
					ImovelConta imovelProximo = Fachada.getInstance().buscarImovelContaPosicao(sistemaParametros.getIdImovelSelecionado(),true);
		     		intent.putExtra("imovel",imovelProximo);
					getContext().startActivity(intent);
					
		     	}else if (caminhoSucesso == ConstantesSistema.MENU_PRINCIPAL){
	            	
		     		intent = new Intent(getContext(),MenuActivity.class);
		     		getContext().startActivity(intent);
		     		
		     	}else if(caminhoSucesso == ConstantesSistema.SAIR_APLICACAO){
		     	
		     		Util.sairAplicacao(getContext());
		     		
		     	}
			}else{
				
				// Caso envio de imóvel não tenha sucesso
				if(caminhoErro == ConstantesSistema.SAIR_APLICACAO && tipoFinalizacao == (int)ArquivoRetorno.ARQUIVO_LIDOS_ATE_AGORA){
//					finalizaAplicacao();
//					Util.sairAplicacao(getContext());
		    		
				}else if (tipoFinalizacao == (int)ArquivoRetorno.ARQUIVO_LIDOS_ATE_AGORA){
		     		intent = new Intent(getContext(),MenuActivity.class);
		     		getContext().startActivity(intent);
	         	
				}else{
					//Menssagem do GSAN
					if(msgErro !=null && msgErro.startsWith(getContext().getString(R.string.str_erro_quantidade_diferente))){
						
						if(ArquivoRetorno.ARQUIVO_COMPLETO == tipoFinalizacao ){
							File file = new File(ConstantesSistema.CAMINHO_RETORNO +nomeArquivo);
							deletarArquivo(file);
							
							intent = new Intent(getContext(),FinalizaArquivoActivty.class);
							intent.putExtra(getContext().getString(R.string.str_extra_tipo_finalizacao), ArquivoRetorno.ARQUIVO_TODOS_OS_CALCULADOS+"");
				    		getContext().startActivity(intent);
						}
						
						if(ArquivoRetorno.ARQUIVO_TODOS_OS_CALCULADOS == tipoFinalizacao){
							
							Util.sairAplicacao(getContext());
							
						}
						
					}else if(msgErro !=null && msgErro.equals(getContext().getString(R.string.str_erro_arquivo_finalizado))   ){
						//Caso o arquivo de retorno já esteja finalizado no GSAN. 
						// APAGAR ARQUIVO DE RETORNO.
						Util.apagarArquivoRetorno();
						
						//IR PARA TELA DE LOGIN
						intent = new Intent(getContext(),LoginActivity.class);
			     		getContext().startActivity(intent);
						
					}else{
						intent = new Intent(getContext(),LoginActivity.class);
			     		getContext().startActivity(intent);
					}
					
					
				}

			}

		// Zera os ids gerados para caso haja um novo envio de imóveis
		ComunicacaoWebServer.setIdsImoveisGerados(new ArrayList<Integer>());
	}

	
	
	private void gravaDadosBackup() {
		Date d = new Date();
		Long getTime = d.getTime();
//		String[] nomeSemExt = nomeArquivo.split(".");
		new ExportBancoDados().exportarBancoNovoNome(getTime,nomeArquivo);
		File fileBackup = new File(ConstantesSistema.CAMINHO_RETORNO+nomeArquivo);
        ControladorBasico.copiaArquivoBkp(fileBackup,getTime);
		
	}

	private void finalizaAplicacao() {
		ControladorBasico.getInstance().apagarBanco();
		
	}
	
	private void deletarArquivo(File file){
		//Deleta o arquivo de retorno caso já existe
		if(file.isFile()){
			file.delete();
		}
	}
	
}