package com.br.ipad.isc.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import com.br.ipad.isc.fachada.Fachada;
import com.br.ipad.isc.util.Util;

/**
 * [] Classe Básica - Sistema Parametro
 * 
 * @author Fernanda Almeida
 * @since 08/03/2012
 */
public class SistemaParametros extends ObjetoBasico implements Serializable {

	private static SistemaParametros instancia=null;
	
	public SistemaParametros(ArrayList<String> obj){
		insertFromFile(obj);
	}
	
	//Construtor Vazio
	public SistemaParametros(){}
	
	public static SistemaParametros getInstancia(){
		if(instancia==null){
			instancia = Fachada.getInstance().buscarSistemaParametro();			
		}
		return instancia;
	}
	public static void resetarInstancia(){
		instancia = null;
		instancia = Fachada.getInstance().buscarSistemaParametro();	
	}
	
	private static final long serialVersionUID = 1L;
		
	private Integer id;
	private Date dataReferenciaArrecadacao;
	private String codigoEmpresaFebraban;
	private String telefone0800;
	private String cnpjEmpresa;
	private String inscricaoEstadualEmpresa;
	private BigDecimal valorMinimEmissaoConta;
	private BigDecimal percentToleranciaRateio;
	private Integer decrementoMaximoConsumoEconomia;
	private Integer incrementoMaximoConsumoEconomia;
	private Integer indcTarifaCatgoria;
	private String login;
	private String senha;
	private Date dataAjusteLeitura;
	private Integer indicadorAjusteConsumo;
	private Integer indicadorTransmissaoOffline;
	private String versaoCelular;
	private Integer indicadorRotaMarcacao;
	private Integer indcBloquearReemissaoConta;
	private Integer qtdDiasAjusteConsumo;
	private Integer moduloVerificadorCodigoBarras;
	private Date dataInicio;
	private Date dataFim;
	private Integer indicadorPercentualColetaEsgoto;
	private Integer indicadorDesconsiderarRateioEsgoto;
	private Integer idImovelSelecionado;	
	private Integer qtdImoveis;
	//[UC1305] - Carregar Arquivo Texto Impressão Simultânea de Contas / RN20140912125
	private Integer maxDiasNovaLigacao;
	private Integer qtdImovelCondominio;
	private Integer idImovelCondominio;
	private Integer  indicadorBancoCarregado;
	private Integer  indicadorRotaMarcacaoAtiva;
	private Integer indicadorArmazenarCoordenadas;
	private Integer indicadorRateioAreaComumImovelNaoFat;
	private Integer indicadorSistemaLeitura;
	private Date ultimaAlteracao;
	private Integer contrasteConta;
	
	public Integer getContrasteConta() {
		return contrasteConta;
	}

	public void setContrasteConta(Integer contrasteConta) {
		this.contrasteConta = contrasteConta;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setIdString(String id) {
		this.id = Util.verificarNuloInt(id);
	}
	
	public Date getDataReferenciaArrecadacao() {
		return dataReferenciaArrecadacao;
	}
	public void setDataReferenciaArrecadacao(Date date) {
		this.dataReferenciaArrecadacao = date;
	}
	public String getTelefone0800() {
		return telefone0800;
	}
	public void setTelefone0800(String telefone0800) {
		this.telefone0800 = telefone0800;
	}
	public String getCnpjEmpresa() {
		return cnpjEmpresa;
	}
	public void setCnpjEmpresa(String cnpjEmpresa) {
		this.cnpjEmpresa = cnpjEmpresa;
	}
	public String getInscricaoEstadualEmpresa() {
		return inscricaoEstadualEmpresa;
	}
	public void setInscricaoEstadualEmpresa(String inscricaoEstadualEmpresa) {
		this.inscricaoEstadualEmpresa = inscricaoEstadualEmpresa;
	}
	public BigDecimal getValorMinimEmissaoConta() {
		return valorMinimEmissaoConta;
	}
	public void setValorMinimEmissaoConta(BigDecimal valorMinimEmissaoConta) {
		this.valorMinimEmissaoConta = valorMinimEmissaoConta;
	}
	public BigDecimal getPercentToleranciaRateio() {
		return percentToleranciaRateio;
	}
	public void setPercentToleranciaRateio(BigDecimal percentToleranciaRateio) {
		this.percentToleranciaRateio = percentToleranciaRateio;
	}
	public Integer getDecrementoMaximoConsumoEconomia() {
		return decrementoMaximoConsumoEconomia;
	}
	public void setDecrementoMaximoConsumoEconomia(Integer decrementoMaximoConsumoEconomia) {
		this.decrementoMaximoConsumoEconomia = decrementoMaximoConsumoEconomia;
	}
	public Integer getIncrementoMaximoConsumoEconomia() {
		return incrementoMaximoConsumoEconomia;
	}
	public void setIncrementoMaximoConsumoEconomia(Integer incrementoMaximoConsumoEconomia) {
		this.incrementoMaximoConsumoEconomia = incrementoMaximoConsumoEconomia;
	}
	public Integer getIndcTarifaCatgoria() {
		return indcTarifaCatgoria;
	}
	public void setIndcTarifaCatgoria(Integer indcTarifaCatgoria) {
		this.indcTarifaCatgoria = indcTarifaCatgoria;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public Date getDataAjusteLeitura() {
		return dataAjusteLeitura;
	}
	public void setDataAjusteLeitura(Date dataAjusteLeitura) {
		this.dataAjusteLeitura = dataAjusteLeitura;
	}
	public Integer getIndicadorAjusteConsumo() {
		return indicadorAjusteConsumo;
	}
	public void setIndicadorAjusteConsumo(Integer indicadorAjusteConsumo) {
		this.indicadorAjusteConsumo = indicadorAjusteConsumo;
	}
	public Integer getIndicadorTransmissaoOffline() {
		return indicadorTransmissaoOffline;
	}
	public void setIndicadorTransmissaoOffline(Integer indicadorTransmissaoOffline) {
		this.indicadorTransmissaoOffline = indicadorTransmissaoOffline;
	}
	public String getVersaoCelular() {
		return versaoCelular;
	}
	public void setVersaoCelular(String versaoCelular) {
		this.versaoCelular = versaoCelular;
	}
	public Integer getIndicadorRotaMarcacao() {
		return indicadorRotaMarcacao;
	}
	public void setIndicadorRotaMarcacao(Integer indicadorRotaMarcacao) {
		this.indicadorRotaMarcacao = indicadorRotaMarcacao;
	}
	public Integer getIndcBloquearReemissaoConta() {
		return indcBloquearReemissaoConta;
	}
	public void setIndcBloquearReemissaoConta(Integer indcBloquearReemissaoConta) {
		this.indcBloquearReemissaoConta = indcBloquearReemissaoConta;
	}
	public Integer getQtdDiasAjusteConsumo() {
		return qtdDiasAjusteConsumo;
	}
	public void setQtdDiasAjusteConsumo(Integer qtdDiasAjusteConsumo) {
		this.qtdDiasAjusteConsumo = qtdDiasAjusteConsumo;
	}
	public Integer getModuloVerificadorCodigoBarras() {
		return moduloVerificadorCodigoBarras;
	}
	public void setModuloVerificadorCodigoBarras(Integer moduloVerificadorCodigoBarras) {
		this.moduloVerificadorCodigoBarras = moduloVerificadorCodigoBarras;
	}
	public Date getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	public Integer getIndicadorPercentualColetaEsgoto() {
		return indicadorPercentualColetaEsgoto;
	}
	public void setIndicadorPercentualColetaEsgoto(Integer indicadorPercentualColetaEsgoto) {
		this.indicadorPercentualColetaEsgoto = indicadorPercentualColetaEsgoto;
	}
	public String getCodigoEmpresaFebraban() {
		return codigoEmpresaFebraban;
	}
	public void setCodigoEmpresaFebraban(String codigoEmpresaFebraban) {
		this.codigoEmpresaFebraban = codigoEmpresaFebraban;
	}

	public Date getUltimaAlteracao() {
		return ultimaAlteracao;
	}
	public void setUltimaAlteracao(String ultimaAlteracao) {
		this.ultimaAlteracao = Util.getData(ultimaAlteracao);
	}
	
	public Integer getQtdImoveis() {
		return qtdImoveis;
	}
	public void setQtdImoveis(Integer qtdImoveis) {
		this.qtdImoveis = qtdImoveis;
	}
	//[UC1305] - Carregar Arquivo Texto Impressão Simultânea de Contas / RN20140912125
	public Integer getMaxDiasNovaLigacao() {
		return maxDiasNovaLigacao;
	}

	public void setMaxDiasNovaLigacao(Integer maxDiasNovaLigacao) {
		this.maxDiasNovaLigacao = maxDiasNovaLigacao;
	}
	
	public Integer getIndicadorDesconsiderarRateioEsgoto() {
		return indicadorDesconsiderarRateioEsgoto;
	}
	public void setIndicadorDesconsiderarRateioEsgoto(Integer indicadorDesconsiderarRateioEsgoto) {
		this.indicadorDesconsiderarRateioEsgoto = indicadorDesconsiderarRateioEsgoto;
	}
	
	public Integer getIdImovelSelecionado() {
		return idImovelSelecionado;
	}
	public void setIdImovelSelecionado(Integer idImovelSelecionado) {
		this.idImovelSelecionado = idImovelSelecionado;
	}
	
	public Integer getQtdImovelCondominio() {
		return qtdImovelCondominio;
	}
	public void setQtdImovelCondominio(Integer qtdImovelCondominio) {
		this.qtdImovelCondominio = qtdImovelCondominio;
	}
	public Integer getIdImovelCondominio() {
		return idImovelCondominio;
	}
	public void setIdImovelCondominio(Integer idImovelCondominio) {
		this.idImovelCondominio = idImovelCondominio;
	}
	
	public Integer getIndicadorBancoCarregado() {
		return indicadorBancoCarregado;
	}
	
	public void setIndicadorBancoCarregado(Integer indicadorBancoCarregado) {
		this.indicadorBancoCarregado = indicadorBancoCarregado;
	}
	
	public Integer getIndicadorArmazenarCoordenadas() {
		return indicadorArmazenarCoordenadas;
	}
	public void setIndicadorArmazenarCoordenadas(
			Integer indicadorArmazenarCoordenadas) {
		this.indicadorArmazenarCoordenadas = indicadorArmazenarCoordenadas;
	}
	
	public Integer getIndicadorRotaMarcacaoAtiva() {
		return indicadorRotaMarcacaoAtiva;
	}
	public void setIndicadorRotaMarcacaoAtiva(Integer indicadorRotaMarcacaoAtiva) {
		this.indicadorRotaMarcacaoAtiva = indicadorRotaMarcacaoAtiva;
	}

	public Integer getIndicadorRateioAreaComumImovelNaoFat() {
		return indicadorRateioAreaComumImovelNaoFat;
	}

	public void setIndicadorRateioAreaComumImovelNaoFat(
			Integer indicadorRateioAreaComumImovelNaoFat) {
		this.indicadorRateioAreaComumImovelNaoFat = indicadorRateioAreaComumImovelNaoFat;
	}
	
	public Integer getIndicadorSistemaLeitura() {
		return indicadorSistemaLeitura;
	}

	public void setIndicadorSistemaLeitura(Integer indicadorSistemaLeitura) {
		this.indicadorSistemaLeitura = indicadorSistemaLeitura;
	}
	
	private static String[] colunas = new String[] { SistemasParametros.ID, SistemasParametros.CODIGOEMPRESAFEBRABAN,  SistemasParametros.DATAARRECADACAO, SistemasParametros.TELEFONE,
         SistemasParametros.CNPJEMPRESA, SistemasParametros.INSCRICAOESTADUAL, SistemasParametros.MINIMOEMISSAOCONTA,
         SistemasParametros.PERCENTUALTOLERANCIARATEIO, SistemasParametros.DECREMENTOMAXCONSUMOECONOMIA, SistemasParametros.INCREMENTOMAXCONSUMOECONOMIA,
         SistemasParametros.INDICADORTARIFACATEGORIA, SistemasParametros.LOGIN, SistemasParametros.SENHA,
         SistemasParametros.DATALEITURAAJUSTE, SistemasParametros.INDICADORCONSUMOAJUSTE, SistemasParametros.INDICADORTRANSMISSAOOFFLINE,
         SistemasParametros.VERSAOCELULAR, SistemasParametros.INDICADORBLOQUEIOCONTA, SistemasParametros.INDICADORROTAMARCACAO,
         SistemasParametros.QTDEDIASCONSUMOAJUSTE, SistemasParametros.MODULODIGITOVERIFICADOR, SistemasParametros.DATAINICIOBLOQUEIO,
         SistemasParametros.DATAFIMBLOQUEIO, SistemasParametros.INDICADOPERCENTUALCOLETAESGOTO,
         SistemasParametros.INDICADORDESCRATEIOESGOTO, SistemasParametros.IDIMOVELSELECIONADO,SistemasParametros.QNTIMOVEIS,
         SistemasParametros.MAXDIASNOVALIGACAO, SistemasParametros.QNTIMOVELCONDOMINIO,SistemasParametros.IDIMOVELCONDOMINIO,
         SistemasParametros.INDICADORBANCOCARREGADO, SistemasParametros.INDICADORROTAMARCACAOATIVA,
         SistemasParametros.INDICADOR_COORDENADAS, SistemasParametros.INDICADOR_RATEIO_AREA_COMUM_IMOV_NFAT,
         SistemasParametros.INDICADOR_SISTEMA_LEITURA, SistemasParametros.ULTIMAALTERACAO,
		 SistemasParametros.CONTRASTECONTA};
	
	public String[] getColunas(){
		return colunas;
	}		
	
	public String getNomeTabela(){
		return "sistema_parametros";
	}
	
	public static final class SistemasParametros implements BaseColumns {
		public static final String ID = "PARM_ID";
		public static final String CODIGOEMPRESAFEBRABAN = "PARM_CDEMPRESAFEBRABAN";
		public static final String DATAARRECADACAO = "PARM_DTARRECADACAO";
		public static final String TELEFONE = "PARM_NNTELEFONE";
		public static final String CNPJEMPRESA = "PARM_NNCNPJEMPRESA";
		public static final String INSCRICAOESTADUAL = "PARM_NNINSCRICAOESTADUAL";
		public static final String MINIMOEMISSAOCONTA = "PARM_VLMINIMOEMISSAOCONTA";
		public static final String PERCENTUALTOLERANCIARATEIO = "PARM_PCTOLERANCIARATEIO";
		public static final String DECREMENTOMAXCONSUMOECONOMIA = "PARM_NNCONSRATEIODECREMENTOMAX";
		public static final String INCREMENTOMAXCONSUMOECONOMIA = "PARM_NNCONSRATEIOINCREMENTOMAX";
		public static final String INDICADORTARIFACATEGORIA = "PARM_ICTARIFACATEGORIA";
		public static final String LOGIN = "PARM_NMLOGIN";
		public static final String SENHA = "PARM_NMSENHA";
		public static final String DATALEITURAAJUSTE = "ROTA_DTLEITURAAJUSTE";
		public static final String INDICADORCONSUMOAJUSTE = "ROTA_ICCONSUMOAJUSTE";
		public static final String INDICADORTRANSMISSAOOFFLINE = "ROTA_ICTRANSMISSAOOFFLINE";
		public static final String VERSAOCELULAR = "PARM_NMVERSAOCELULAR";
		public static final String INDICADORBLOQUEIOCONTA = "PARM_ICBLOQUEIOCONTAMOBILE";
		public static final String INDICADORROTAMARCACAO = "PARM_ICROTAMARCACAO";
		public static final String QTDEDIASCONSUMOAJUSTE = "PARM_NNDIASCONSUMOAJUSTE";
		public static final String MODULODIGITOVERIFICADOR = "PARM_NNMODULODIGITOVERIF";
		public static final String DATAINICIOBLOQUEIO = "PARM_DTINICIOBLOQUEIO";
		public static final String DATAFIMBLOQUEIO = "PARM_DTFIMBLOQUEIO";
		public static final String INDICADOPERCENTUALCOLETAESGOTO = "PARM_ICPERCENTUALCOLETAESGOTO";		
		public static final String INDICADORDESCRATEIOESGOTO = "PARM_ICDESCRATEIOESGOTO";
		public static final String IDIMOVELSELECIONADO = "PARM_IDIMOVSELECIONADO";
		public static final String QNTIMOVEIS = "PARM_QTIMOVEIS";
		//[UC1305] - Carregar Arquivo Texto Impressão Simultânea de Contas / RN20140912125
		public static final String MAXDIASNOVALIGACAO = "PARM_NNMAXDIASNOVALIGACAO";
		public static final String QNTIMOVELCONDOMINIO = "PARM_QTIMOVELCONDOMINIO";
		public static final String IDIMOVELCONDOMINIO = "PARM_IDIMOVELCONDOMINIO";
		public static final String INDICADORBANCOCARREGADO = "PARM_ICBANCOCARREGADO";
		public static final String INDICADORROTAMARCACAOATIVA = "PARM_ICROTAMARCACAOATIVA";
		public static final String INDICADOR_COORDENADAS = "PARM_ICARMAZENARCOORDENADAS";
		public static final String INDICADOR_RATEIO_AREA_COMUM_IMOV_NFAT = "PARM_ICRATEIOAREACOUMIMOVNFAT";
		public static final String INDICADOR_SISTEMA_LEITURA = "PARM_ICSISTEMALEITURA";
		public static final String ULTIMAALTERACAO = "PARM_TMULTIMAALTERACAO";
		public static final String CONTRASTECONTA = "PARM_NNCONTRASTECONTA";
	}
	
	private void insertFromFile(ArrayList<String> obj){
		
		setCodigoEmpresaFebraban(obj.get(1));
		
		setDataReferenciaArrecadacao(Util.convertStrToDataArquivo(obj.get(2)));
		
		setTelefone0800(obj.get(3));	
		setCnpjEmpresa(obj.get(4));	
		setInscricaoEstadualEmpresa(obj.get(5));
		if(obj.get(6).length()!=0){	
			BigDecimal valorMinimo = new BigDecimal(obj.get(6));
			setValorMinimEmissaoConta(valorMinimo);	
		}
		if(obj.get(7).length()!=0){
			BigDecimal percentual = new BigDecimal(obj.get(7));
			setPercentToleranciaRateio(percentual);	
		}
		if(obj.get(8).length()!=0){
			setDecrementoMaximoConsumoEconomia(Integer.parseInt(obj.get(8)));
		}
		if(obj.get(9).length()!=0){
			setIncrementoMaximoConsumoEconomia(Integer.parseInt(obj.get(9)));
		}
		if(obj.get(10).length()!=0){
			setIndcTarifaCatgoria(Integer.parseInt(obj.get(10)));
		}
		setLogin(obj.get(11));	
		setSenha(obj.get(12));	
		
		setDataAjusteLeitura(Util.convertStrToDataArquivo(obj.get(13)));
		
		if(obj.get(14).length()!=0){
			setIndicadorAjusteConsumo(Integer.parseInt(obj.get(14)));
		}
		if(obj.get(15).length()!=0){
			setIndicadorTransmissaoOffline(Integer.parseInt(obj.get(15)));
		}
		setVersaoCelular(obj.get(16));	
		if(obj.get(17).length()!=0){
			setIndcBloquearReemissaoConta(Integer.parseInt(obj.get(17)));
		}
		if(obj.get(18).length()!=0){
			setIndicadorRotaMarcacao(Integer.parseInt(obj.get(18)));
		}
		if(obj.get(19).length()!=0){
			setQtdDiasAjusteConsumo(Integer.parseInt(obj.get(19)));
		}
		if(obj.get(20).length()!=0){
			setModuloVerificadorCodigoBarras(Integer.parseInt(obj.get(20)));
		}
		
		setDataInicio(Util.convertStrToDataArquivo(obj.get(21)));
				
		setDataFim(Util.convertStrToDataArquivo(obj.get(22)));
		
		if(obj.get(23).length()!=0){
			setIndicadorPercentualColetaEsgoto(Integer.parseInt(obj.get(23)));	
		}
		String date = Util.convertDateToDateStr(Util.getCurrentDateTime());	
		setUltimaAlteracao(date);
		
		if(obj.get(24).length()!=0){
			setIndicadorDesconsiderarRateioEsgoto(Integer.parseInt(obj.get(24)));
		}
		
		if ( obj.get(25) != null && !obj.get(25).equals("") ) {
			setIndicadorArmazenarCoordenadas(Integer.parseInt(obj.get(25)));
		}

		if ( obj.get(26) != null && !obj.get(26).equals("") ) {
			setIndicadorRateioAreaComumImovelNaoFat(Integer.parseInt(obj.get(26)));
		}
		
		if ( obj.get(27) != null && !obj.get(27).equals("") ) {
			setIndicadorSistemaLeitura(Integer.parseInt(obj.get(27)));
		}else{
			setIndicadorSistemaLeitura(2);
		}
		//[UC1305] - Carregar Arquivo Texto Impressão Simultânea de Contas / RN20140912125
		if ( obj.get(28) != null && !obj.get(28).equals("") ) {
			setMaxDiasNovaLigacao(Integer.parseInt(obj.get(28)));
		}
		
		setIdImovelSelecionado(0);	
		setContrasteConta(0);
	}
	
	
	public final class SistemasParametrosTipos {
		
		public final String ID = " INTEGER PRIMARY KEY AUTOINCREMENT";
		public final String CODIGOEMPRESAFEBRABAN = " VARCHAR(6) NOT NULL";
		public final String DATAARRECADACAO = " DATE NOT NULL";
		public final String TELEFONE = " VARCHAR(12) NULL";
		public final String CNPJEMPRESA = " VARCHAR(14) NULL";
		public final String INSCRICAOESTADUAL = " VARCHAR(20) NULL";
		public final String MINIMOEMISSAOCONTA = " NUMERIC(13,2) NOT NULL";
		public final String PERCENTUALTOLERANCIARATEIO = " NUMERIC(3,1) NULL";
		public final String DECREMENTOMAXCONSUMOECONOMIA = " INTEGER NULL";
		public final String INCREMENTOMAXCONSUMOECONOMIA = " INTEGER NULL";
		public final String INDICADORTARIFACATEGORIA = " INTEGER NOT NULL";
		public final String LOGIN = " VARCHAR(11) NOT NULL";
		public final String SENHA = " VARCHAR(40) NOT NULL";
		public final String DATALEITURAAJUSTE = " INTEGER NULL";
		public final String INDICADORCONSUMOAJUSTE = " INTEGER NULL DEFAULT 2";
		public final String INDICADORTRANSMISSAOOFFLINE = " INTEGER NOT NULL";
		public final String VERSAOCELULAR = " VARCHAR(10) NULL";
		public final String INDICADORBLOQUEIOCONTA = " INTEGER NOT NULL";
		public final String INDICADORROTAMARCACAO = " INTEGER NOT NULL";
		public final String QTDEDIASCONSUMOAJUSTE = " INTEGER NULL";
		public final String MODULODIGITOVERIFICADOR = " INTEGER NOT NULL";
		public final String DATAINICIOBLOQUEIO = " DATE NOT NULL";
		public final String DATAFIMBLOQUEIO = " DATE NOT NULL";
		public final String INDICADOPERCENTUALCOLETAESGOTO = " INTEGER NULL  DEFAULT 2";		
		public final String INDICADORDESCRATEIOESGOTO = " INTEGER NULL  DEFAULT 2";
		public final String IDIMOVELSELECIONADO = " INTEGER NULL";
		public final String QNTIMOVEIS = " INTEGER NULL";
		//[UC1305] - Carregar Arquivo Texto Impressão Simultânea de Contas / RN20140912125
		public final String MAXDIASNOVALIGACAO = " INTEGER NULL DEFAULT 1";
		public final String QNTIMOVELCONDOMINIO = " INTEGER NULL";
		public final String IDIMOVELCONDOMINIO = " INTEGER NULL";
		public final String INDICADORBANCOCARREGADO = " INTEGER NOT NULL DEFAULT 2";
		public final String INDICADORROTAMARCACAOATIVA = " INTEGER NOT NULL DEFAULT 2";
		public final String INDICADOR_COORDENADAS = " INTEGER NULL";
		public final String INDICADOR_RATEIO_AREA_COMUM_IMOV_NFAT = " INTEGER NOT NULL DEFAULT 2";
		public final String INDICADOR_SISTEMA_LEITURA = " INTEGER NOT NULL DEFAULT 2";
		public final String ULTIMAALTERACAO = " TIMESTAMP NOT NULL";
		public final String CONTRASTECONTA = " INTEGER NOT NULL DEFAULT 0";
		
		private String[] tipos = new String[] {ID, CODIGOEMPRESAFEBRABAN,  DATAARRECADACAO, TELEFONE,
		        CNPJEMPRESA, INSCRICAOESTADUAL, MINIMOEMISSAOCONTA,
		        PERCENTUALTOLERANCIARATEIO, DECREMENTOMAXCONSUMOECONOMIA, INCREMENTOMAXCONSUMOECONOMIA,
		        INDICADORTARIFACATEGORIA, LOGIN, SENHA,
		        DATALEITURAAJUSTE, INDICADORCONSUMOAJUSTE, INDICADORTRANSMISSAOOFFLINE,
		        VERSAOCELULAR, INDICADORBLOQUEIOCONTA, INDICADORROTAMARCACAO,
		        QTDEDIASCONSUMOAJUSTE, MODULODIGITOVERIFICADOR, DATAINICIOBLOQUEIO,
		        DATAFIMBLOQUEIO, INDICADOPERCENTUALCOLETAESGOTO,
		        INDICADORDESCRATEIOESGOTO, IDIMOVELSELECIONADO,QNTIMOVEIS,
		        MAXDIASNOVALIGACAO,QNTIMOVELCONDOMINIO, IDIMOVELCONDOMINIO, INDICADORBANCOCARREGADO,
		        INDICADORROTAMARCACAOATIVA, INDICADOR_COORDENADAS, INDICADOR_RATEIO_AREA_COMUM_IMOV_NFAT,
		        INDICADOR_SISTEMA_LEITURA,ULTIMAALTERACAO, CONTRASTECONTA};	
			
		public String[] getTipos(){
			return tipos;
		}
	}
	
	public ContentValues preencherValues() {
		ContentValues values = new ContentValues();
		
		values.put(SistemasParametros.CNPJEMPRESA, getCnpjEmpresa());
		values.put(SistemasParametros.CODIGOEMPRESAFEBRABAN, getCodigoEmpresaFebraban());
		
		values.put(SistemasParametros.DATAARRECADACAO, Util.convertDataToStrValues(getDataReferenciaArrecadacao()));
		values.put(SistemasParametros.DATAFIMBLOQUEIO, Util.convertDataToStrValues(getDataFim()));
		values.put(SistemasParametros.DATAINICIOBLOQUEIO, Util.convertDataToStrValues(getDataInicio()));
		
		if(getDataAjusteLeitura()!=null){
			values.put(SistemasParametros.DATALEITURAAJUSTE, getDataAjusteLeitura().getTime());
		}
		
		values.put(SistemasParametros.DECREMENTOMAXCONSUMOECONOMIA, getDecrementoMaximoConsumoEconomia());
		values.put(SistemasParametros.INCREMENTOMAXCONSUMOECONOMIA, getIncrementoMaximoConsumoEconomia());
		values.put(SistemasParametros.INDICADOPERCENTUALCOLETAESGOTO, getIndicadorPercentualColetaEsgoto());
		values.put(SistemasParametros.INDICADORBLOQUEIOCONTA, getIndcBloquearReemissaoConta());
		values.put(SistemasParametros.INDICADORCONSUMOAJUSTE, getIndicadorAjusteConsumo());
		values.put(SistemasParametros.INDICADORTARIFACATEGORIA, getIndcTarifaCatgoria());
		values.put(SistemasParametros.INDICADORROTAMARCACAO, getIndicadorRotaMarcacao());
		values.put(SistemasParametros.INDICADORTRANSMISSAOOFFLINE, getIndicadorTransmissaoOffline());
		values.put(SistemasParametros.INSCRICAOESTADUAL, getInscricaoEstadualEmpresa());
		values.put(SistemasParametros.LOGIN, getLogin());
		if(getValorMinimEmissaoConta() != null){
			values.put(SistemasParametros.MINIMOEMISSAOCONTA, getValorMinimEmissaoConta().toString());
		}
		values.put(SistemasParametros.MODULODIGITOVERIFICADOR, getModuloVerificadorCodigoBarras());
		if(getPercentToleranciaRateio() != null){
			values.put(SistemasParametros.PERCENTUALTOLERANCIARATEIO, getPercentToleranciaRateio().toString());
		}
		values.put(SistemasParametros.QTDEDIASCONSUMOAJUSTE, getQtdDiasAjusteConsumo());
		values.put(SistemasParametros.SENHA, getSenha());
		values.put(SistemasParametros.TELEFONE, getTelefone0800());
		values.put(SistemasParametros.VERSAOCELULAR, getVersaoCelular());
		
		values.put(SistemasParametros.INDICADORDESCRATEIOESGOTO, getIndicadorDesconsiderarRateioEsgoto());
		if(getIdImovelSelecionado()!=null){
			values.put(SistemasParametros.IDIMOVELSELECIONADO, getIdImovelSelecionado());
		}
		values.put(SistemasParametros.QNTIMOVEIS, getQtdImoveis());
		//[UC1305] - Carregar Arquivo Texto Impressão Simultânea de Contas / RN20140912125
		if (getMaxDiasNovaLigacao() != null) {
			values.put(SistemasParametros.MAXDIASNOVALIGACAO, getMaxDiasNovaLigacao());
		}
		values.put(SistemasParametros.QNTIMOVELCONDOMINIO, getQtdImovelCondominio());
		values.put(SistemasParametros.IDIMOVELCONDOMINIO, getIdImovelCondominio());
		values.put(SistemasParametros.INDICADOR_COORDENADAS, getIndicadorArmazenarCoordenadas());
		values.put(SistemasParametros.INDICADOR_RATEIO_AREA_COMUM_IMOV_NFAT, getIndicadorRateioAreaComumImovelNaoFat());
		values.put(SistemasParametros.INDICADOR_SISTEMA_LEITURA, getIndicadorSistemaLeitura());
		
		String dataStr = Util.convertDateToDateStr(Util.getCurrentDateTime());
		values.put(SistemasParametros.ULTIMAALTERACAO, dataStr);
		
		values.put(SistemasParametros.CONTRASTECONTA, getContrasteConta());
		
		return values;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<SistemaParametros> preencherObjetos(Cursor cursor) {		
		
		ArrayList<SistemaParametros> retorno = null;
		
		if (cursor.moveToFirst()) {	
			int codigo = cursor.getColumnIndex(SistemasParametros.ID);
			int bloqueioConta = cursor.getColumnIndex(SistemasParametros.INDICADORBLOQUEIOCONTA);
			int cnpjEmpresa = cursor.getColumnIndex(SistemasParametros.CNPJEMPRESA);
			int codigoEmpresaFebraban = cursor.getColumnIndex(SistemasParametros.CODIGOEMPRESAFEBRABAN);
			int dataArrecadacao = cursor.getColumnIndex(SistemasParametros.DATAARRECADACAO);
			int dataFimBloqueio = cursor.getColumnIndex(SistemasParametros.DATAFIMBLOQUEIO);
			int dataInicioBloqueio = cursor.getColumnIndex(SistemasParametros.DATAINICIOBLOQUEIO);
			int dataLeituraAjuste = cursor.getColumnIndex(SistemasParametros.DATALEITURAAJUSTE);
			int decrementoMaxConsumo = cursor.getColumnIndex(SistemasParametros.DECREMENTOMAXCONSUMOECONOMIA);
			int incrementoMaxConsumo = cursor.getColumnIndex(SistemasParametros.INCREMENTOMAXCONSUMOECONOMIA);
			int indicadorPercEsgoto = cursor.getColumnIndex(SistemasParametros.INDICADOPERCENTUALCOLETAESGOTO);
			int indicadorConsAjuste = cursor.getColumnIndex(SistemasParametros.INDICADORCONSUMOAJUSTE);
			int indicadorTarifaCategoria = cursor.getColumnIndex(SistemasParametros.INDICADORTARIFACATEGORIA);
			int indicadorRotaMarcacao = cursor.getColumnIndex(SistemasParametros.INDICADORROTAMARCACAO);
			int indicadorTransmissaoOff = cursor.getColumnIndex(SistemasParametros.INDICADORTRANSMISSAOOFFLINE);
			int inscricaoEstadual = cursor.getColumnIndex(SistemasParametros.INSCRICAOESTADUAL);
			int login = cursor.getColumnIndex(SistemasParametros.LOGIN);
			int minimoEmissaoConta = cursor.getColumnIndex(SistemasParametros.MINIMOEMISSAOCONTA);
			int moduloDigitoVerificador = cursor.getColumnIndex(SistemasParametros.MODULODIGITOVERIFICADOR);
			int percToleranciaRateio = cursor.getColumnIndex(SistemasParametros.PERCENTUALTOLERANCIARATEIO);
			int qtdeDiasConsumo = cursor.getColumnIndex(SistemasParametros.QTDEDIASCONSUMOAJUSTE);
			int senha = cursor.getColumnIndex(SistemasParametros.SENHA);
			int telefone = cursor.getColumnIndex(SistemasParametros.TELEFONE);
			int versaoCelular = cursor.getColumnIndex(SistemasParametros.VERSAOCELULAR);
			int indRateioEsgoto = cursor.getColumnIndex(SistemasParametros.INDICADORDESCRATEIOESGOTO);
			int idImovelSelec = cursor.getColumnIndex(SistemasParametros.IDIMOVELSELECIONADO);
			int qtdImoveis = cursor.getColumnIndex(SistemasParametros.QNTIMOVEIS);
			//[UC1305] - Carregar Arquivo Texto Impressão Simultânea de Contas / RN20140912125
			int maxDiasNovaLigacao = cursor.getColumnIndex(SistemasParametros.MAXDIASNOVALIGACAO);
			int qtdImovelCondominio = cursor.getColumnIndex(SistemasParametros.QNTIMOVELCONDOMINIO);
			int idImovelCondominio = cursor.getColumnIndex(SistemasParametros.IDIMOVELCONDOMINIO);
			int indicadorBancoCarregado = cursor.getColumnIndex(SistemasParametros.INDICADORBANCOCARREGADO);
			int indicadorRotaMarcacaoAtiva = cursor.getColumnIndex(SistemasParametros.INDICADORROTAMARCACAOATIVA);
			int indicadorCoordenadas = cursor.getColumnIndex(SistemasParametros.INDICADOR_COORDENADAS);
			int indicadorRateioAreaComum = cursor.getColumnIndex(SistemasParametros.INDICADOR_RATEIO_AREA_COMUM_IMOV_NFAT);
			int indicadorSistemaLeitura = cursor.getColumnIndex(SistemasParametros.INDICADOR_SISTEMA_LEITURA);
			int ultimaAlteracao = cursor.getColumnIndex(SistemasParametros.ULTIMAALTERACAO);
			int contrasteConta = cursor.getColumnIndex(SistemasParametros.CONTRASTECONTA);
			
			retorno = new ArrayList<SistemaParametros>();			
			do{
				SistemaParametros sistemaParametros = new SistemaParametros();
				
				sistemaParametros.setId(cursor.getInt(codigo));
				sistemaParametros.setIndcBloquearReemissaoConta(Util.getIntBanco(cursor, SistemasParametros.INDICADORBLOQUEIOCONTA, bloqueioConta));
				sistemaParametros.setCnpjEmpresa(cursor.getString(cnpjEmpresa));
				sistemaParametros.setCodigoEmpresaFebraban(cursor.getString(codigoEmpresaFebraban));

				sistemaParametros.setDataReferenciaArrecadacao(Util.convertStrToDataBusca(cursor.getString(dataArrecadacao)));
				sistemaParametros.setDataFim(Util.convertStrToDataBusca(cursor.getString(dataFimBloqueio)));
				sistemaParametros.setDataInicio(Util.convertStrToDataBusca(cursor.getString(dataInicioBloqueio)));
				
				sistemaParametros.setDataAjusteLeitura(Util.getDataBanco(cursor, SistemasParametros.DATALEITURAAJUSTE, dataLeituraAjuste));
				
				sistemaParametros.setDecrementoMaximoConsumoEconomia(Util.getIntBanco(cursor, SistemasParametros.DECREMENTOMAXCONSUMOECONOMIA, decrementoMaxConsumo));
				sistemaParametros.setIncrementoMaximoConsumoEconomia(Util.getIntBanco(cursor, SistemasParametros.INCREMENTOMAXCONSUMOECONOMIA, incrementoMaxConsumo));
				sistemaParametros.setIndicadorPercentualColetaEsgoto(Util.getIntBanco(cursor, SistemasParametros.INDICADOPERCENTUALCOLETAESGOTO, indicadorPercEsgoto));
				sistemaParametros.setIndicadorAjusteConsumo(Util.getIntBanco(cursor, SistemasParametros.INDICADORCONSUMOAJUSTE, indicadorConsAjuste));
				sistemaParametros.setIndcTarifaCatgoria(Util.getIntBanco(cursor, SistemasParametros.INDICADORTARIFACATEGORIA, indicadorTarifaCategoria));
				sistemaParametros.setIndicadorRotaMarcacao(Util.getIntBanco(cursor, SistemasParametros.INDICADORROTAMARCACAO, indicadorRotaMarcacao));
				sistemaParametros.setIndicadorTransmissaoOffline(Util.getIntBanco(cursor, SistemasParametros.INDICADORTRANSMISSAOOFFLINE, indicadorTransmissaoOff));
				sistemaParametros.setInscricaoEstadualEmpresa(cursor.getString(inscricaoEstadual));
				sistemaParametros.setLogin(cursor.getString(login));
				sistemaParametros.setValorMinimEmissaoConta(Util.getDoubleBanco(cursor, SistemasParametros.MINIMOEMISSAOCONTA, minimoEmissaoConta));
				sistemaParametros.setModuloVerificadorCodigoBarras(Util.getIntBanco(cursor, SistemasParametros.MODULODIGITOVERIFICADOR, moduloDigitoVerificador));
				sistemaParametros.setPercentToleranciaRateio(Util.getDoubleBanco(cursor, SistemasParametros.PERCENTUALTOLERANCIARATEIO, percToleranciaRateio));
				sistemaParametros.setQtdDiasAjusteConsumo(Util.getIntBanco(cursor, SistemasParametros.QTDEDIASCONSUMOAJUSTE, qtdeDiasConsumo));
				sistemaParametros.setSenha(cursor.getString(senha));
				sistemaParametros.setTelefone0800(cursor.getString(telefone));
				sistemaParametros.setVersaoCelular(cursor.getString(versaoCelular));
				sistemaParametros.setIndicadorDesconsiderarRateioEsgoto(Util.getIntBanco(cursor, SistemasParametros.INDICADORDESCRATEIOESGOTO, indRateioEsgoto));
				sistemaParametros.setIdImovelSelecionado(Util.getIntBanco(cursor, SistemasParametros.IDIMOVELSELECIONADO, idImovelSelec));
				sistemaParametros.setQtdImoveis(cursor.getInt(qtdImoveis));
				//[UC1305] - Carregar Arquivo Texto Impressão Simultânea de Contas / RN20140912125
				sistemaParametros.setMaxDiasNovaLigacao(cursor.getInt(maxDiasNovaLigacao));
				sistemaParametros.setQtdImovelCondominio(cursor.getInt(qtdImovelCondominio));
				sistemaParametros.setIdImovelCondominio(cursor.getInt(idImovelCondominio));
				sistemaParametros.setIndicadorBancoCarregado(cursor.getInt(indicadorBancoCarregado));
				sistemaParametros.setIndicadorRotaMarcacaoAtiva(cursor.getInt(indicadorRotaMarcacaoAtiva));
				sistemaParametros.setIndicadorArmazenarCoordenadas(cursor.getInt(indicadorCoordenadas));
				sistemaParametros.setIndicadorRateioAreaComumImovelNaoFat(cursor.getInt(indicadorRateioAreaComum));
				sistemaParametros.setIndicadorSistemaLeitura(cursor.getInt(indicadorSistemaLeitura));
				sistemaParametros.setUltimaAlteracao(cursor.getString(ultimaAlteracao));
				sistemaParametros.setContrasteConta(cursor.getInt(contrasteConta));
				
				retorno.add(sistemaParametros);					
			} while (cursor.moveToNext());
		}		
		return retorno;		
	}


}