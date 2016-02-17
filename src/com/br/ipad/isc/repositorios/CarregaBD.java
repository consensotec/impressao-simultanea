
package com.br.ipad.isc.repositorios;

import java.io.File;
import java.util.ArrayList;

import android.util.Log;

import com.br.ipad.isc.bean.CategoriaSubcategoria;
import com.br.ipad.isc.bean.ConsumoAnormalidade;
import com.br.ipad.isc.bean.ConsumoAnormalidadeAcao;
import com.br.ipad.isc.bean.ConsumoAnteriores;
import com.br.ipad.isc.bean.ConsumoTarifaCategoria;
import com.br.ipad.isc.bean.ConsumoTarifaFaixa;
import com.br.ipad.isc.bean.ConsumoTipo;
import com.br.ipad.isc.bean.ContaComunicado;
import com.br.ipad.isc.bean.ContaDebito;
import com.br.ipad.isc.bean.ContaImposto;
import com.br.ipad.isc.bean.CreditoRealizado;
import com.br.ipad.isc.bean.DebitoCobrado;
import com.br.ipad.isc.bean.FaturamentoSituacaoTipo;
import com.br.ipad.isc.bean.HidrometroInstalado;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.bean.LeituraAnormalidade;
import com.br.ipad.isc.bean.QualidadeAgua;
import com.br.ipad.isc.bean.SistemaParametros;
import com.br.ipad.isc.excecoes.RepositorioException;
import com.br.ipad.isc.util.ConstantesSistema;
import com.br.ipad.isc.util.Util;

/**
 * [GSANAS] Repository - Activit
 * 
 * @author Fernanda Almeida
 * @since 06/07/2011
 */
public class CarregaBD extends RepositorioBasico {

	private static CarregaBD instancia;
	private CarregaBD(){}
	/**
	 * Padrão de projeto SINGLETON
	 * 
	 */
	public static CarregaBD getInstance(){
		if ( instancia == null ){
			return  new CarregaBD();
		} else{
			return instancia;
		}
	}
	
	// Tipos de linha
	public final String IMOVEL_CONTA = "01";
	public final String CATEGORIA_SUBCATEGORIA = "02";
	public final String CONSUMO_ANTERIORES = "03";
	public final String DEBITO_COBRADO = "04";
	public final String CREDITO_REALIZADO = "05";
	public final String CONTA_IMPOSTO = "06";
	public final String CONTA_DEBITO = "07";
	public final String HIDROMETRO_INSTALADO = "08";
	public final String CONSUMO_TARIFA_CATEGORIA = "09";
	public final String CONSUMO_TARIFA_FAIXA = "10";
	public final String SISTEMA_PARAMETRO = "11";
	public final String CONSUMO_ANORMALIDADE_ACAO = "12";
	public final String CONSUMO_ANORMALIDADE= "13";
	public final String LEITURA_ANORMALIDADE= "14";
	public final String QUALIDADE_AGUA = "15";
	public final String FATURAMENTO_SITUACAO_TIPO = "16";
	public final String CONSUMO_TIPO = "17";
	public final String CONTA_COMUNICADO = "18";
	
	public static Integer CONTADOR_IMOVEL = 0;
	public static long idConsumoCategoria =0;

	private static int contadorImovelCondominio;
	
	public Integer carregaLinhaParaBD(String line) throws RepositorioException {
		
		RepositorioBasico repositorioBasico = RepositorioBasico.getInstance();
		RepositorioImovelConta repositorioImovelConta = RepositorioImovelConta.getInstance();
		repositorioBasico.resetarInstancias();
		
		Integer idImovel = null;
		// ArrayListe que contem todos os elementos que presentes
		// linha
		// Quebramos a linha em uma lista de Strings
		ArrayList<String> linhaElementos = Util.split(line);
		
		//TODO
		//Verificar se o arquivo é valido
        if(linhaElementos.size() == 0){
        	//Arquivo Invalido...
        	Log.v(ConstantesSistema.CATEGORIA, "Arquivo Invalido");
        }
		
		String tipoRegistro = linhaElementos.get(0);
				
		//1
		if (tipoRegistro.equals(IMOVEL_CONTA)) {
						
			// Conta a posicao do imovel para a 
			// insercao do campo IMCT_NNPOSICAO
			CONTADOR_IMOVEL++;
						
			if(linhaElementos.get(1).length() != 0){
				idImovel = Integer.parseInt(linhaElementos.get(1));
			}

			ImovelConta imovelConta = new ImovelConta(linhaElementos,CONTADOR_IMOVEL);
			
			if(CONTADOR_IMOVEL.intValue() == 1){
				
		    	File pastaBackup = new File(ConstantesSistema.CAMINHO_BACKUP_RETORNO);
		    	File pastaBanco = new File(ConstantesSistema.CAMINHO_BANCO);
		    	
		    	verificaArquivoDeletar(pastaBackup,imovelConta);
		    	verificaArquivoDeletar(pastaBanco,imovelConta);
		    	
			}
			
			// Conta a posicao do imovel condominio para a 
			// insercao do campo IMCT_NNPOSICAOIMOVELCONDOMINIO
			if(imovelConta.getIndcCondominio().equals(ConstantesSistema.SIM)){
				contadorImovelCondominio = 1;
				imovelConta.setPosicaoImovelCondominio(contadorImovelCondominio);
				contadorImovelCondominio++;				
			} else if(imovelConta.getMatriculaCondominio()!=null){
				imovelConta.setPosicaoImovelCondominio(contadorImovelCondominio);
				contadorImovelCondominio++;
			}
			repositorioImovelConta.inserirImovelContaVencimento(imovelConta);
		}
		
		//2
		if (tipoRegistro.equals(CATEGORIA_SUBCATEGORIA)) {
			CategoriaSubcategoria categoriaSubcategoria = new CategoriaSubcategoria(linhaElementos);
			repositorioBasico.inserir(categoriaSubcategoria);
		}
		
		//3
		if (tipoRegistro.equals(CONSUMO_ANTERIORES)) {
			ConsumoAnteriores consumoAnteriores = new ConsumoAnteriores(linhaElementos);
			repositorioBasico.inserir(consumoAnteriores);
		}

		//4
		if (tipoRegistro.equals(DEBITO_COBRADO)) {
			DebitoCobrado debitoCobrado = new DebitoCobrado(linhaElementos);
			repositorioBasico.inserir(debitoCobrado);
		}
		
		//5
		if (tipoRegistro.equals(CREDITO_REALIZADO)) {
			CreditoRealizado creditoRealizado = new CreditoRealizado(linhaElementos);
			repositorioBasico.inserir(creditoRealizado);
		}
		
		//6
		if (tipoRegistro.equals(CONTA_IMPOSTO)) {
			ContaImposto contaImposto = new ContaImposto(linhaElementos);
			repositorioBasico.inserir(contaImposto);
		}
		
		//7
		if (tipoRegistro.equals(CONTA_DEBITO)) {
			ContaDebito contaDebito = new ContaDebito(linhaElementos);
			repositorioBasico.inserir(contaDebito);
		}
		
		//8
		if (tipoRegistro.equals(HIDROMETRO_INSTALADO)) {
			HidrometroInstalado hidrometroInstalado = new HidrometroInstalado(linhaElementos);
			repositorioBasico.inserir(hidrometroInstalado);
		}
		
		//9
		if (tipoRegistro.equals(CONSUMO_TARIFA_CATEGORIA)) {
			ConsumoTarifaCategoria consumoTarifaCategoria = new ConsumoTarifaCategoria(linhaElementos);
			idConsumoCategoria = repositorioBasico.inserir(consumoTarifaCategoria);
		}
		
		//10
		if (tipoRegistro.equals(CONSUMO_TARIFA_FAIXA)) {
			ConsumoTarifaFaixa consumoTarifaFaixa = new ConsumoTarifaFaixa(linhaElementos, idConsumoCategoria);
			repositorioBasico.inserir(consumoTarifaFaixa);
		}
		
		//11
		if (tipoRegistro.equals(SISTEMA_PARAMETRO)) {
			SistemaParametros sistemaParametros = new SistemaParametros(linhaElementos);
			repositorioBasico.inserir(sistemaParametros);
		}
		
		//12
		if (tipoRegistro.equals(CONSUMO_ANORMALIDADE)) {
			ConsumoAnormalidade consumoAnormalidade = new ConsumoAnormalidade(linhaElementos);
			repositorioBasico.inserir(consumoAnormalidade);
		}
		
		//13
		if (tipoRegistro.equals(CONSUMO_ANORMALIDADE_ACAO)) {
			ConsumoAnormalidadeAcao consumoAnormalidadeAcao = new ConsumoAnormalidadeAcao(linhaElementos);
			repositorioBasico.inserir(consumoAnormalidadeAcao);
		}
		
		//14
		if (tipoRegistro.equals(LEITURA_ANORMALIDADE)) {
			LeituraAnormalidade leituraAnormalidade = new LeituraAnormalidade(linhaElementos);
			repositorioBasico.inserir(leituraAnormalidade);
		}
		
		//15
		if (tipoRegistro.equals(QUALIDADE_AGUA)) {
			QualidadeAgua qualidadeAgua = new QualidadeAgua(linhaElementos);
			repositorioBasico.inserir(qualidadeAgua);
		}
		
		//16
		if (tipoRegistro.equals(FATURAMENTO_SITUACAO_TIPO)) {
			FaturamentoSituacaoTipo faturamentoSituacaoTipo = new FaturamentoSituacaoTipo(linhaElementos); 	
			repositorioBasico.inserir(faturamentoSituacaoTipo);			
		}
		
		//17
		if (tipoRegistro.equals(CONSUMO_TIPO)) {
			ConsumoTipo consumoTipo = new ConsumoTipo(linhaElementos); 	
			repositorioBasico.inserir(consumoTipo);			
		}
		
		//18
		if(tipoRegistro.equals(CONTA_COMUNICADO)){
			ContaComunicado contaComunicado = new ContaComunicado(linhaElementos);
			repositorioBasico.inserir(contaComunicado);
		}
		
		linhaElementos.removeAll(linhaElementos);
		return idImovel;
	}
	
	/**
	 * Deleta o arquivo de backup caso o novo arquivo sendo inserido possua ano mes diferente do backup
	 * @author Fernanda
	 * @date 16/04/2013
	 * 
	 */
	private void verificaArquivoDeletar(File pasta, ImovelConta imovelConta) {
		if(pasta.isDirectory()){
			
			if(pasta.getName().contains("banco")){
				
				File[] prefFiles = pasta.listFiles();
		        if(prefFiles != null){
			        for (File f : prefFiles) {
			        	Integer anoMesBackup = new Integer(f.getName().substring(f.getName().length()-6, f.getName().length()));
			        	//Caso ano mes antigo seja diferente do novo ano mes novo, deleta
			        	if(!anoMesBackup.equals(imovelConta.getAnoMesConta())){
			        		f.delete();
			        	}
			        }
		        }				
			}else{
			
		        File[] prefFiles = pasta.listFiles();
		        if(prefFiles != null){
			        for (File f : prefFiles) {
			        	Integer anoMesBackup = new Integer(f.getName().substring(f.getName().length()-10, f.getName().length()-4));
			        	//Caso ano mes antigo seja diferente do novo ano mes novo, deleta
			        	if(!anoMesBackup.equals(imovelConta.getAnoMesConta())){
			        		f.delete();
			        	}
			        }
		        }
			}
		}
		
	}
}
