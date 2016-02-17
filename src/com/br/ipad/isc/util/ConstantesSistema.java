
package com.br.ipad.isc.util;

import java.net.InetSocketAddress;
import java.net.Proxy;

import android.os.Environment;


public class ConstantesSistema {

	// Esse parâmetro é usado para o sistema permitir carregar mais
	// de uma rota (true).
	// Para os sistemas que trabalham online esse parametro deve 
	// está como FALSE.
	// - CAERN - false.
	// - CAER  - true.
	// - CAEMA - true
	// - SSAE - true
	public static final boolean PERMITE_BAIXAR_MULTIPLAS_ROTAS =  false; 
	
	
	// Quantidade de imóveis para serem enviados
	public static final int FREQUENCIA_ENVIO_IMOVEIS = 1;
	
	//Apk de producao DEVE iniciar a variavel SIMULADOR = FALSE
	public static boolean SIMULADOR = false;

		
	//MENU	
	public static final int MENU_SAIR = 99;
	//public static final int MENU_CALCULAR = 2;
	//public static final int MENU_VOLTAR= 3;
	//public static final int MENU_FOTO= 4;
	 
	public static final String CATEGORIA = "ISC";
	
	
	public static final Proxy PROXY_CAERN = new Proxy(java.net.Proxy.Type.HTTP, new InetSocketAddress("proxy.caern.com.br",1616));
	public static final String  IP_CAERN_PRODUCAO = "http://mobile.caern.govrn:8080";
	//public static final String  IP_CAERN_PRODUCAO = "http://mobile.caern.govrn:8080";
	
	
//	public static final String HOST = IP_CAERN_PRODUCAO;//"http://192.168.64.80:8080"; //"http://192.168.63.10:8080";

	public static final String HOST = //IP_CAERN_PRODUCAO;		
			
			
			

								//Produção Comercial SAAE   
								//"http://gsan.saaejuazeiro.com.br:8080";

								//SAAE - HOMOLOGAÇÃO 
								//"http://201.56.20.51:8080";

								//CAER - HOMOLOGAÇÃO 
								//"http://201.56.20.51:8080";

								//CAER - PRODUÇÃO 
								//"http://201.56.20.55:8080";								
			
								//CAERN //Producao 
								//"http://mobile.caern.govrn:8080";

								//CAERN  //Homolog 
							    "http://10.18.0.246:8080"; 	
			
								//Caema producao	
								//"http://gsan.caema.ma.gov.br:8080/gsan/";										
				
			                    // endereço de proxy externo da CAERN
			                    //"http://10.18.0.246.caern.com.br:1616"						
																
									
								//AMBIENTE DE PRODUÇÃO DA CAERN
								// IP_CAERN_PRODUCAO;
	
									
									
								//CAER Produção 
								//"http://gsan.caer.com.br:8080";			
									  
			
								// ENDEREÇO - IP externos do GSAN na CAER.
			                    //PRODUÇÃO 
							    //"http://201.56.20.62:8080";
			                    //HOMOLOGAÇÃO
								//"http://201.56.20.51:8080"; 			
			                    // "http://192.168.4.6:8080";
			
			                    //IP Interno
			                    //"http://192.168.64.240:8680";
								//"http://192.168.5.4:8080";
	

	public static final String ACAO = HOST + "/gsan/processarRequisicaoDipositivoMovelImpressaoSimultaneaAndroidAction.do";	
    
	public static final Integer SIM = 1;    
	public static final Integer NAO = 2;
	
	public static final Short SIM_SHORT = 1;    
	public static final Short NAO_SHORT = 2;
		
	public static final String CAMINHO_SDCARD = Environment.getExternalStorageDirectory().toString(); //"/storage/";
	public static final String CAMINHO_ISC = CAMINHO_SDCARD+"/isc";
	public static final String CAMINHO_BANCO = CAMINHO_ISC+"/banco/";
	public static final String CAMINHO_RETORNO = CAMINHO_SDCARD+"/isc/Retorno/";
	public static final String CAMINHO_BACKUP = CAMINHO_SDCARD+"/Backup/isc/";
	public static final String CAMINHO_BACKUP_RETORNO = CAMINHO_SDCARD+"/isc/backupRetorno/";
	public static final String CAMINHO_OFFLINE = Environment.getExternalStorageDirectory()+"/isc/carregamento";
	public static final String CAMINHO_FOTOS = CAMINHO_ISC+"/fotos";
	public static final String CAMINHO_VERSAO = Environment.getExternalStorageDirectory()+"/isc/versao";
	public static final String CAMINHO_VERSAO_2 = Environment.getExternalStorageDirectory()+"/isc/teste";
	public static final String NOME_APK = "isc.apk";
	public static final String NOME_APK_2 = "isc2.apk";
	public static final int INSTALL_APK = 4741;
	
	
	public static final int ALERTA_MENSAGEM = 1;
	public static final int ALERTA_PERGUNTA = 2;

	// Valor limite para a conta
	public static final int VALOR_LIMITE_CONTA = 1000;

	//public static final int CONSUMO_TIPO_INDEFINIDO = 0;
	public static final int CONSUMO_TIPO_REAL = 1;
	//public static final int CONSUMO_TIPO_AJUSTADO = 2;
	public static final int CONSUMO_TIPO_MEDIA_HIDR = 3;
	//public static final int CONSUMO_TIPO_INFORMADO = 4;
	public static final int CONSUMO_TIPO_NAO_MEDIDO = 5;
	public static final int CONSUMO_TIPO_ESTIMADO = 6;
	public static final int CONSUMO_TIPO_MINIMO_FIX = 7;
	public static final int CONSUMO_TIPO_SEM = 8;
	public static final int CONSUMO_TIPO_MEDIA_IMOV = 9;
	public static final int CONSUMO_TIPO_FIXO_SITUACAO_ESPECIAL = 10;
	public static final int CONSUMO_TIPO_FIXO_CONTRATO_DEMANDA = 11;

	//public static final int LEITURA_SITU_INDEFINIDO = 0;
	public static final int LEITURA_SITU_REALIZADA = 1;
	//public static final int LEITURA_SITU_NAO_REALIZ = 2;
	public static final int LEITURA_SITU_CONFIRMADA = 3;
	//public static final int LEITURA_SITU_ALTERADA = 4;

	public static final int ANORM_HIDROMETRO_PARADO = 30;
	public static final int ANORM_HIDR_SEM_CONSUMO = 38;
	
	public static final int CONSUMO_ANORM_HIDROMETRO_PARADO = 19;
	public static final int CONSUMO_ANORM_HIDR_SEM_CONSUMO = 21;

	public static final int LIMITE_SUPERIOR_FAIXA_FINAL = 999999;
	
	/*
	 * Leitura Anormalidade Consumo
	 */
	public static final int NAO_OCORRE = 0;
	public static final int MINIMO = 1;
	public static final int MEDIA = 2;
	public static final int NORMAL = 3;
	public static final int MAIOR_ENTRE_O_CONSUMO_MEDIO = 5;
	public static final int MENOR_ENTRE_O_CONSUMO_MEDIO = 6;
	public static final int FIXO = 7;
	public static final int NAO_MEDIDO = 8;

	/**
	 * Leitura Anormalidade Leitura
	 */
	public static final int ANTERIOR_MAIS_A_MEDIA = 0;
	public static final int ANTERIOR = 1;
	public static final int ANTERIOR_MAIS_O_CONSUMO = 2;
	public static final int INFORMADO = 3;

	/* Debito Cobrado */
	public final static String DESCRICAO_CERDITO_NITRATO = "DEDUCAO JUDICIAL";
	public final static int CODIGO_CREDITO_NITRATO = 12;
	
	public final static String DESCRICAO_CREDITO_CONTRATO_DEMANDA = "DESCONTO CONTRATO DEMANDA";
	public final static int TARIFA_CORTADO_DEC_18_251_94 = 2500;
	
	//Consumo
	//public static final int CONSUMO_ANORM_INFORMADO = 2;
	public static final int CONSUMO_ANORM_BAIXO_CONSUMO = 4;
	public static final int CONSUMO_ANORM_ESTOURO = 5;
	public static final int CONSUMO_ANORM_ALTO_CONSUMO = 6;
	public static final int CONSUMO_ANORM_LEIT_MENOR_PROJ = 7;
	public static final int CONSUMO_ANORM_LEIT_MENOR_ANTE = 8;
	public static final int CONSUMO_ANORM_HIDR_SUBST_INFO = 9;
	public static final int CONSUMO_ANORM_LEITURA_N_INFO = 10;
	public static final int CONSUMO_ANORM_ESTOURO_MEDIA = 11;
	public static final int CONSUMO_MINIMO_FIXADO = 12;
	public static final int CONSUMO_ANORM_FORA_DE_FAIXA = 13;
	public static final int CONSUMO_ANORM_HIDR_SUBST_N_INFO = 14;
	public static final int CONSUMO_ANORM_VIRADA_HIDROMETRO = 16;
	public static final int ANORMALIDADE_LEITURA = 17;
	
	// Situação da ligação de Água
	public final static int POTENCIAL = 1;

	public final static int FACTIVEL = 2;

	public final static int LIGADO = 3;

	public final static int EM_FISCALIZACAO = 4;

	public final static int CORTADO = 5;

	public final static int SUPRIMIDO = 6;

	public final static int SUPR_PARC = 7;

	public final static int SUPR_PARC_PEDIDO = 8;

	public final static int EM_CANCELAMENTO = 9;

	// Situação da ligação de Esgoto
	public final static int LIG_FORA_USO = 5;

	public final static int TAMPONADO = 6;

	public final static int CONVERSAO = 9;
	
	public final static byte ENTER = 13;
	/**
	 * Valor decimal do código ascii para o Line-Feed (pula linha).
	 */
	public final static byte LINE = 10;
	/**
	 * Valor retornado quando o final do arquivo é atingido.
	 */
	public final static byte EOF = -1;
	
	/**
	 * COD. FEBRABAN da COMPESA
	 */
	public static final String CODIGO_FEBRABAN_COMPESA = "18";

	/**
	 * COD. FEBRABAN da CAER
	 */
	public static final String CODIGO_FEBRABAN_CAER = "4";

	/**
	 * COD. FEBRABAN da CAERN
	 */
	public static final String CODIGO_FEBRABAN_CAERN = "6";
	
	/**
	 * COD. FEBRABAN da CAERN
	 */
	public static final String CODIGO_FEBRABAN_CAEMA = "2";
	
	
	//CATEGORIAS
	public final static int RESIDENCIAL = 1;

	public final static int COMERCIAL = 2;

	public final static int INDUSTRIAL = 3;

	public final static int PUBLICO = 4;
	
	public final static int NITRATO = 9;
	
	public static final int MODULO_VERIFICADOR_10 = 10;
	public static final int MODULO_VERIFICADOR_11 = 11;
	
	/**
	 * Validate Error = ID_SEM_ERRO indica leitura com um valor válido.
	 */
	//public static final byte ID_OK = 2;

	public static final int ID_SEM_ERRO = Integer.MIN_VALUE;

//	public static final int ID_ABRIU_DIRETORIO = ID_SEM_ERRO + 1;
//	public static final int ID_ABRIU_ARQUIVO = ID_SEM_ERRO + 2;
//	public static final int ID_BAIXOU_COM_SUCESSO = ID_SEM_ERRO + 3;
//	public static final int ID_FINALIZOU_ROTEIRO = ID_SEM_ERRO + 4;
//	public static final int ID_IMPRIMIU_COM_SUCESSO = ID_SEM_ERRO + 5;

	//public static final int ID_AVISO_ROTEIRO_INVERTIDO = -2;
	//public static final int ID_AVISO_ROTEIRO_PERCORRIDO = -1;
	/**
	 * Validate Error = ID_ERRO_LEITURA_VALOR_NEGATIVO indica leitura com valor
	 * negativo.
	 */
	//public static final int ID_ERRO_LEITURA_VALOR_NEGATIVO = 0;

	/**
	 * Validate Error = ID_ERRO_LEITURA_FORA_DE_FAIXA indica leitura com valor
	 * fora da faixa.
	 */
	//public static final int ID_ERRO_LEITURA_FORA_DE_FAIXA = 1;

	/**
	 * Identificador de Erro de conexão.
	 */
	//public static final int ID_ERRO_CONEXAO = 2;

	/**
	 * Identificador de Erro de Arquivo não Encontrado.
	 */
	//public static final int ID_ERRO_ARQUIVO_NAO_ENCONTRADO = 3;

	/**
	 * Identificador de Erro do Servidor.
	 */
	//public static final int ID_ERRO_SERVIDOR = 4;

	//public static final int ID_ERRO_QUADRA_NAO_ENCONTRADA = 5;

	public static final int ID_ERRO_HIDROMETRO_NAO_ENCONTRADO = 6;
	public static final int ID_ERRO_MATRICULA_NAO_ENCONTRADA = 7;
	public static final int ID_ERRO_CHAVE_EM_BRANCO = 8;
	public static final int ID_ERRO_ROTEIRO_NAO_PERCORRIDO = 9;
	public static final int ID_ERRO_GERAR_ARQUIVO = 10;
	public static final int ID_ERRO_SEM_ROTA = 11;
	public static final int ID_ERRO_ESCOLHER_ARQUIVO = 12;
	public static final int ID_ERRO_SEQUENCIAL_NAO_ENCONTRADA = 13;
	public static final int ID_ERRO_ANORMALIDADE_SEM_LEITURA = 14;
	public static final int ID_ERRO_ANORMALIDADE_COM_LEITURA = 15;
	public static final int ID_ERRO_FALTA_LEITURA_PARA_GERAR_CONTA = 16;
	
	//Tarifas
	public static final int TIPO_CALCULO_TARIFA_3 = 3;
	public static final int TIPO_CALCULO_TARIFA_2 = 2;
	
	//Fotos
	public static final Integer FOTO_ANORMALIDADE = 10;	
	public static final Integer FOTO_IMOVEL = 9;		
	public static final int     CAMERA                   = 8291;
	
    public static final String SENHA_ADMINISTRADOR = "mobile";
    public static final String SENHA_LEITURISTA = "apagar";
    
//    public static final String SENHA_ADMINISTRADOR_CAERN = "mobile";
    public static final String SENHA_LEITURISTA_CAERN = "caico";
    
    public static final String SENHA_FINALIZAR_INCOMPLETO = "mobile";
	
    // Tipos de situação de leitura
    public static final int LEITURA_REALIZADA = 0;
    public static final int LEITURA_CONFIRMADA = 1;
    
    
    public static final short CALCULO_POR_CATEGORA = 1;
    
    public static final int INDC_FINALIZAR_ROTEIRO = 2;
    public static final int INDC_FINALIZAR_ROTEIRO_INCOMPLETO = 6;
    public static final int INDC_FINALIZAR_ROTEIRO_TODOS_IMOVEIS = 7;
    public static final int INDC_ENVIAR_LIDOS_ATE_AGORA = 0;
    
  
	/**
	 * Ligacao Tipo
	 */
	public static final int LIGACAO_AGUA = 1;
	public static final int LIGACAO_POCO = 2;
	public static final int LIGACAO_ESGOTO = 2;

	//Tarifa Tipo Calculo
	public static final int CALCULO_PROPORCIONAL = 2;
    public static final int CALCULO_SIMPLES = 3;
    
    //Alertas mensagem comunicação
    public static final byte MENU_PRINCIPAL = 1;
	public static final byte SAIR_APLICACAO = 2;
	public static final byte LISTA = 3;
    
	/**
	 * Identificador do status Fim de Escrita.
	 */
//	public static final int FIM_DE_ESCRITA = 6;
//	public static final int QUEBRA_CONTAS_IMOVEL_CONDOMINIO = 5;
//	public static final String CODIFICACAO = "UTF-8";
	
	// Tipos de Rateio
	public static final int TIPO_RATEIO_SEM_RATEIO = 0;
	public static final int TIPO_RATEIO_POR_IMOVEL = 1;
	public static final int TIPO_RATEIO_AREA_CONSTRUIDA = 2;
	public static final int TIPO_RATEIO_NUMERO_MORADORES = 3;
	public static final int TIPO_RATEIO_NAO_MEDIDO_AGUA = 4;
	public static final int TIPO_RATEIO_AREA_COMUM = 5;
	
    public static final char TIPO_ARQUIVO_TXT = 'T';
    public static final char TIPO_ARQUIVO_GZ = 'G';
    
    
    //******** CONSTANTES USADAS NA CONEXAO *****************************//
    public static final String RESPOSTA_OK = "*";
    public static final char RESPOSTA_OK_CHAR = '*';
	public static final String RESPOSTA_ERRO = "#";
	
	// Tipos de parametros que podem ser retornados
	public static final String PARAMETRO_IMOVEIS_PARA_REVISITAR = "imoveis=";
	public static final String PARAMETRO_MENSAGEM = "mensagem=";
	public static final String PARAMETRO_APK = "apk=";
	public static final String PARAMETRO_TIPO_ARQUIVO = "tipoArquivo=";
	public static final String PARAMETRO_ARQUIVO_ROTEIRO = "arquivoRoteiro=";

	public static final char CARACTER_FIM_PARAMETRO = '&';
	// Informa todos os tipos de requisição 
	public static final byte DOWNLOAD_ARQUIVO = 0;
//	private static final byte UPDATE_ARQUIVO = 2;
	public static final byte ENVIA_IMOVEL = 1;
	public static final byte DOWNLOAD_APK = 11;
	public static final byte VERIFICAR_VERSAO = 12;
	public static final byte FINALIZAR_ROTEIRO = 7;
	public static final byte PING = 15;
	public static final byte SINAL_INICIALIZACAO_ROTEIRO = 10;
	public static final byte BAIXAR_ROTA = 13;
	
	
	// Erros da classe
	public static final int ERRO_GENERICO = 0;
	public static final int ERRO_REQUISICAO_ABORTAR = 1;
	public static final int ERRO_DOWNLOAD_ARQUIVO = 2;
	public static final int ERRO_CARREGANDO_ARQUIVO = 3;
	public static final int ERRO_SERVIDOR_OFF_LINE = 4;
	public static final int ERRO_SINAL_INICIALIZACAO_ROTEIRO = 5;
	public static final int ERRO_SEM_ARQUIVO = 6;
	public static final int ERRO_CARREGANDO_ARQUIVO_REGISTRO_DUPLICADO = 7;
	
	//Botoes com ação para validar foto
	public static final int BOTAO_CALCULAR = 1;
	public static final int BOTAO_PROXIMO = 0;
	public static final int BOTAO_IMPRIMIR = 2;
	public static final int BOTAO_ANTERIOR = 4;

	// Erros retornado do gsan
	//public static final int ERRO_FINALIZADO = 10;
	
	public static final int OK = 100;
	
	public static final int ERRO_INSERIR_REGISTRO_BD = -1;
	
	public static final Integer VOLUME_MINIMO_ESGOTO = 0;
	
	/*
	 * Constantes referentes
	 * se a foto pertence a uma
	 * anormalidade de leitura ou
	 * anormalidade de consumo
	 */
	public static final Integer FOTO_TIPO_LEITURA_ANORMALIDADE = 1;
	
	public static final Integer FOTO_TIPO_CONSUMO_ANORMALIDADE = 2;
	
	// Constante helper FotoActivity
	public static final String FOTO_HELPER = "helper";
}