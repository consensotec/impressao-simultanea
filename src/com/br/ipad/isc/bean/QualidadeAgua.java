package com.br.ipad.isc.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import com.br.ipad.isc.util.Util;

/**
 * [] Classe Básica - Qualidade Água
 * 
 * @author Fernanda Almeida
 * @since 08/03/2012
 */
public class QualidadeAgua extends ObjetoBasico implements Serializable {

	public QualidadeAgua(ArrayList<String> obj){
		insertFromFile(obj);
	}
	
	//Construtor default
	public QualidadeAgua(){}
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer idLocalidade;
	private Integer idSetorComercial;
	private String turbidezPadrao;
	private String phPadrao;
	private String corPadrao;
	private String cloroPadrao;
	private String fluorPadrao;
	private String ferroPadrao;
	private String coliformesTotaisPadrao;
	private String coliformesFecaisPadrao;
	private String nitratoPadrao;
	private String coliformesTermoTolerantesPadrao;
	private Integer amReferenciaQualidadeAgua;
	private BigDecimal numeroCloroResidual;
	private BigDecimal numeroTurbidez;
	private BigDecimal numeroPh;
	private BigDecimal numeroCor;
	private BigDecimal numeroFluor;
	private BigDecimal numeroFerro;
	private BigDecimal numeroColiformesTotais;
	private BigDecimal numeroColiformesFecais;
	private BigDecimal numeroNitrato;
	private BigDecimal numeroColiformesTermoTolerantes;
	private String descricaoFonteCapacitacao;
	private Integer quantidadeTurbidezExigidas;
	private Integer quantidadeCorExigidas;
	private Integer quantidadeCloroExigidas;
	private Integer quantidadeFluorExigidas;
	private Integer quantidadeColiformesTotaisExigidas;
	private Integer quantidadeColiformesFecaisExigidas;
	private Integer quantidadeColiformesTermoTolerantesExigidas;
	private Integer quantidadeTurbidezAnalisadas;
	private Integer quantidadeCorAnalisadas;
	private Integer quantidadeCloroAnalisadas;
	private Integer quantidadeFluorAnalisadas;
	private Integer quantidadeColiformesTotaisAnalisadas;
	private Integer quantidadeColiformesFecaisAnalisadas;
	private Integer quantidadeColiformesTermoTolerantesAnalisadas;
	private Integer quantidadeTurbidezConforme;
	private Integer quantidadeCorConforme;
	private Integer quantidadeCloroConforme;
	private Integer quantidadeFluorConforme;
	private Integer quantidadeColiformesTotaisConforme;
	private Integer quantidadeColiformesFecaisConforme;
	private Integer quantidadeColiformesTermoTolerantesConforme;
	private Date ultimaAlteracao;
		
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setIdString(String id) {
		this.id = Util.verificarNuloInt(id);
	}

	
	public Integer getQuantidadeCorConforme() {
		return quantidadeCorConforme;
	}
	public void setQuantidadeCorConforme(Integer quantidadeCorConforme) {
		this.quantidadeCorConforme = quantidadeCorConforme;
	}
	
	public Integer getQuantidadeCloroConforme() {
		return quantidadeCloroConforme;
	}
	public void setQuantidadeCloroConforme(Integer quantidadeCloroConforme) {
		this.quantidadeCloroConforme = quantidadeCloroConforme;
	}
	
	public String getTurbidezPadrao() {
		return turbidezPadrao;
	}
	public void setTurbidezPadrao(String turbidezPadrao) {
		this.turbidezPadrao = turbidezPadrao;
	}
	public String getPhPadrao() {
		return phPadrao;
	}
	public void setPhPadrao(String phPadrao) {
		this.phPadrao = phPadrao;
	}
	public String getCorPadrao() {
		return corPadrao;
	}
	public void setCorPadrao(String corPadrao) {
		this.corPadrao = corPadrao;
	}
	public String getCloroPadrao() {
		return cloroPadrao;
	}
	public void setCloroPadrao(String cloroPadrao) {
		this.cloroPadrao = cloroPadrao;
	}
	public String getFluorPadrao() {
		return fluorPadrao;
	}
	public void setFluorPadrao(String fluorPadrao) {
		this.fluorPadrao = fluorPadrao;
	}
	public String getFerroPadrao() {
		return ferroPadrao;
	}
	public void setFerroPadrao(String ferroPadrao) {
		this.ferroPadrao = ferroPadrao;
	}
	public String getColiformesTotaisPadrao() {
		return coliformesTotaisPadrao;
	}
	public void setColiformesTotaisPadrao(String coliformesTotaisPadrao) {
		this.coliformesTotaisPadrao = coliformesTotaisPadrao;
	}
	public String getColiformesFecaisPadrao() {
		return coliformesFecaisPadrao;
	}
	public void setColiformesFecaisPadrao(String coliformesFecaisPadrao) {
		this.coliformesFecaisPadrao = coliformesFecaisPadrao;
	}
	public String getNitratoPadrao() {
		return nitratoPadrao;
	}
	public void setNitratoPadrao(String nitratoPadrao) {
		this.nitratoPadrao = nitratoPadrao;
	}
	public String getColiformesTermoTolerantesPadrao() {
		return coliformesTermoTolerantesPadrao;
	}
	public void setColiformesTermoTolerantesPadrao(String coliformesTermoTolerantesPadrao) {
		this.coliformesTermoTolerantesPadrao = coliformesTermoTolerantesPadrao;
	}
	public Integer getAmReferenciaQualidadeAgua() {
		return amReferenciaQualidadeAgua;
	}
	public void setAmReferenciaQualidadeAgua(Integer amReferenciaQualidadeAgua) {
		this.amReferenciaQualidadeAgua = amReferenciaQualidadeAgua;
	}
	public BigDecimal getNumeroCloroResidual() {
		return numeroCloroResidual;
	}
	public void setNumeroCloroResidual(BigDecimal numeroCloroResidual) {
		this.numeroCloroResidual = numeroCloroResidual;
	}
	public BigDecimal getNumeroTurbidez() {
		return numeroTurbidez;
	}
	public void setNumeroTurbidez(BigDecimal numeroTurbidez) {
		this.numeroTurbidez = numeroTurbidez;
	}
	public BigDecimal getNumeroPh() {
		return numeroPh;
	}
	public void setNumeroPh(BigDecimal numeroPh) {
		this.numeroPh = numeroPh;
	}
	public BigDecimal getNumeroCor() {
		return numeroCor;
	}
	public void setNumeroCor(BigDecimal numeroCor) {
		this.numeroCor = numeroCor;
	}
	public BigDecimal getNumeroFluor() {
		return numeroFluor;
	}
	public void setNumeroFluor(BigDecimal numeroFluor) {
		this.numeroFluor = numeroFluor;
	}
	public BigDecimal getNumeroFerro() {
		return numeroFerro;
	}
	public void setNumeroFerro(BigDecimal numeroFerro) {
		this.numeroFerro = numeroFerro;
	}
	public BigDecimal getNumeroColiformesTotais() {
		return numeroColiformesTotais;
	}
	public void setNumeroColiformesTotais(BigDecimal numeroColiformesTotais) {
		this.numeroColiformesTotais = numeroColiformesTotais;
	}
	public BigDecimal getNumeroColiformesFecais() {
		return numeroColiformesFecais;
	}
	public void setNumeroColiformesFecais(BigDecimal numeroColiformesFecais) {
		this.numeroColiformesFecais = numeroColiformesFecais;
	}
	public BigDecimal getNumeroNitrato() {
		return numeroNitrato;
	}
	public void setNumeroNitrato(BigDecimal numeroNitrato) {
		this.numeroNitrato = numeroNitrato;
	}
	public BigDecimal getNumeroColiformesTermoTolerantes() {
		return numeroColiformesTermoTolerantes;
	}
	public void setNumeroColiformesTermoTolerantes(BigDecimal numeroColiformesTermoTolerantes) {
		this.numeroColiformesTermoTolerantes = numeroColiformesTermoTolerantes;
	}
	public String getDescricaoFonteCapacitacao() {
		return descricaoFonteCapacitacao;
	}
	public void setDescricaoFonteCapacitacao(String descricaoFonteCapacitacao) {
		this.descricaoFonteCapacitacao = descricaoFonteCapacitacao;
	}
	public Integer getQuantidadeTurbidezExigidas() {
		return quantidadeTurbidezExigidas;
	}
	public void setQuantidadeTurbidezExigidas(Integer quantidadeTurbidezExigidas) {
		this.quantidadeTurbidezExigidas = quantidadeTurbidezExigidas;
	}
	public Integer getQuantidadeCorExigidas() {
		return quantidadeCorExigidas;
	}
	public void setQuantidadeCorExigidas(Integer quantidadeCorExigidas) {
		this.quantidadeCorExigidas = quantidadeCorExigidas;
	}
	public Integer getQuantidadeCloroExigidas() {
		return quantidadeCloroExigidas;
	}
	public void setQuantidadeCloroExigidas(Integer quantidadeCloroExigidas) {
		this.quantidadeCloroExigidas = quantidadeCloroExigidas;
	}
	public Integer getQuantidadeFluorExigidas() {
		return quantidadeFluorExigidas;
	}
	public void setQuantidadeFluorExigidas(Integer quantidadeFluorExigidas) {
		this.quantidadeFluorExigidas = quantidadeFluorExigidas;
	}
	public Integer getQuantidadeColiformesTotaisExigidas() {
		return quantidadeColiformesTotaisExigidas;
	}
	public void setQuantidadeColiformesTotaisExigidas(Integer quantidadeColiformesTotaisExigidas) {
		this.quantidadeColiformesTotaisExigidas = quantidadeColiformesTotaisExigidas;
	}
	public Integer getQuantidadeColiformesFecaisExigidas() {
		return quantidadeColiformesFecaisExigidas;
	}
	public void setQuantidadeColiformesFecaisExigidas(Integer quantidadeColiformesFecaisExigidas) {
		this.quantidadeColiformesFecaisExigidas = quantidadeColiformesFecaisExigidas;
	}
	public Integer getQuantidadeColiformesTermoTolerantesExigidas() {
		return quantidadeColiformesTermoTolerantesExigidas;
	}
	public Integer getIdLocalidade() {
		return idLocalidade;
	}
	public void setIdLocalidade(Integer idLocalidade) {
		this.idLocalidade = idLocalidade;
	}
	public Integer getIdSetorComercial() {
		return idSetorComercial;
	}
	public void setIdSetorComercial(Integer idSetorComercial) {
		this.idSetorComercial = idSetorComercial;
	}
	public void setQuantidadeColiformesTermoTolerantesExigidas(Integer quantidadeColiformesTermoTolerantesExigidas) {
		this.quantidadeColiformesTermoTolerantesExigidas = quantidadeColiformesTermoTolerantesExigidas;
	}
	public Integer getQuantidadeTurbidezAnalisadas() {
		return quantidadeTurbidezAnalisadas;
	}
	public void setQuantidadeTurbidezAnalisadas(Integer quantidadeTurbidezAnalisadas) {
		this.quantidadeTurbidezAnalisadas = quantidadeTurbidezAnalisadas;
	}
	public Integer getQuantidadeCorAnalisadas() {
		return quantidadeCorAnalisadas;
	}
	public void setQuantidadeCorAnalisadas(Integer quantidadeCorAnalisadas) {
		this.quantidadeCorAnalisadas = quantidadeCorAnalisadas;
	}
	public Integer getQuantidadeCloroAnalisadas() {
		return quantidadeCloroAnalisadas;
	}
	public void setQuantidadeCloroAnalisadas(Integer quantidadeCloroAnalisadas) {
		this.quantidadeCloroAnalisadas = quantidadeCloroAnalisadas;
	}
	public Integer getQuantidadeFluorAnalisadas() {
		return quantidadeFluorAnalisadas;
	}
	public void setQuantidadeFluorAnalisadas(Integer quantidadeFluorAnalisadas) {
		this.quantidadeFluorAnalisadas = quantidadeFluorAnalisadas;
	}
	public Integer getQuantidadeColiformesTotaisAnalisadas() {
		return quantidadeColiformesTotaisAnalisadas;
	}
	public void setQuantidadeColiformesTotaisAnalisadas(Integer quantidadeColiformesTotaisAnalisadas) {
		this.quantidadeColiformesTotaisAnalisadas = quantidadeColiformesTotaisAnalisadas;
	}
	public Integer getQuantidadeColiformesFecaisAnalisadas() {
		return quantidadeColiformesFecaisAnalisadas;
	}
	public void setQuantidadeColiformesFecaisAnalisadas(Integer quantidadeColiformesFecaisAnalisadas) {
		this.quantidadeColiformesFecaisAnalisadas = quantidadeColiformesFecaisAnalisadas;
	}
	public Integer getQuantidadeColiformesTermoTolerantesAnalisadas() {
		return quantidadeColiformesTermoTolerantesAnalisadas;
	}
	public void setQuantidadeColiformesTermoTolerantesAnalisadas(Integer quantidadeColiformesTermoTolerantesAnalisadas) {
		this.quantidadeColiformesTermoTolerantesAnalisadas = quantidadeColiformesTermoTolerantesAnalisadas;
	}
	public Integer getQuantidadeTurbidezConforme() {
		return quantidadeTurbidezConforme;
	}
	public void setQuantidadeTurbidezConforme(Integer quantidadeTurbidezConforme) {
		this.quantidadeTurbidezConforme = quantidadeTurbidezConforme;
	}
	public Integer getQuantidadeFluorConforme() {
		return quantidadeFluorConforme;
	}
	public void setQuantidadeFluorConforme(Integer quantidadeFluorConforme) {
		this.quantidadeFluorConforme = quantidadeFluorConforme;
	}
	public Integer getQuantidadeColiformesTotaisConforme() {
		return quantidadeColiformesTotaisConforme;
	}
	public void setQuantidadeColiformesTotaisConforme(Integer quantidadeColiformesTotaisConforme) {
		this.quantidadeColiformesTotaisConforme = quantidadeColiformesTotaisConforme;
	}
	public Integer getQuantidadeColiformesFecaisConforme() {
		return quantidadeColiformesFecaisConforme;
	}
	public void setQuantidadeColiformesFecaisConforme(Integer quantidadeColiformesFecaisConforme) {
		this.quantidadeColiformesFecaisConforme = quantidadeColiformesFecaisConforme;
	}
	public Integer getQuantidadeColiformesTermoTolerantesConforme() {
		return quantidadeColiformesTermoTolerantesConforme;
	}
	public void setQuantidadeColiformesTermoTolerantesConforme(Integer quantidadeColiformesTermoTolerantesConforme) {
		this.quantidadeColiformesTermoTolerantesConforme = quantidadeColiformesTermoTolerantesConforme;
	}
	
	public Date getUltimaAlteracao() {
		return ultimaAlteracao;
	}
	public void setUltimaAlteracao(String ultimaAlteracao) {
		this.ultimaAlteracao = Util.getData(ultimaAlteracao);
	}
	
	private static String[] colunas = new String[] { QualidadesAguas.ID,  QualidadesAguas.IDLOCALIDADE, QualidadesAguas.IDSETORCOMERCIAL,
		QualidadesAguas.DESCRICAOPADRAOTURBIDEZ, QualidadesAguas.DESCRICAOPADRAOPH, QualidadesAguas.DESCRICAOPADRAOCOR,
     QualidadesAguas.DESCRICAOPADRAOCLORO, QualidadesAguas.DESCRICAOPADRAOFLUOR, QualidadesAguas.DESCRICAOPADRAOFERRO,
     QualidadesAguas.DESCRICAOPADRAOCOLIFORMESTOTAIS, QualidadesAguas.DESCRICAOPADRAOCOLIFORMESFECAIS, QualidadesAguas.DESCRICAOPADRAONITRATO,
     QualidadesAguas.DESCRICAOPADRAOCOLIFTERMO, QualidadesAguas.ANOMESREFERENCIA, QualidadesAguas.NUMEROCLORORESIDUAL,
     QualidadesAguas.NUMEROTURBIDEZ, QualidadesAguas.NUMEROPH, QualidadesAguas.NUMEROCOR,
     QualidadesAguas.NUMEROFLUOR,QualidadesAguas.NUMEROFERRO, QualidadesAguas.NUMEROCOLIFORMESTOTAIS, QualidadesAguas.NUMEROCOLIFORMESFECAIS,
     QualidadesAguas.NUMERONITRATO, QualidadesAguas.NUMEROCOLIFORMESTERMO, QualidadesAguas.DESCRICAOFONTECAPTACAO,
     QualidadesAguas.QUANTIDADEEXIGIDASTURBIDEZ, QualidadesAguas.QUANTIDADEEXIGIDASCOR, QualidadesAguas.QUANTIDADEEXIGIDASCLORO,
    QualidadesAguas.QUANTIDADEEXIGIDASFLUOR, QualidadesAguas.QUANTIDADEEXIGIDASCOLIFTOTAIS, QualidadesAguas.QUANTIDADEEXIGIDASCOLIFFECAIS,
    QualidadesAguas.QUANTIDADEEXIGIDASCOLIFTERMO, QualidadesAguas.QUANTIDADETURBIDEZANALISADAS, QualidadesAguas.QUANTIDADECORANALISADAS,
    QualidadesAguas.QUANTIDADECLOROANALISADAS, QualidadesAguas.QUANTIDADEFLUORANALISADAS, QualidadesAguas.QUANTIDADECOLIFTOTAISANALISADAS,
    QualidadesAguas.QUANTIDADECOLIFFECAISCANALISADAS,QualidadesAguas.QUANTIDADECOLIFTERMOANALISADAS,QualidadesAguas.QUANTIDADETURBIDEZCONFORME,
    QualidadesAguas.QUANTIDADECORCONFORME, QualidadesAguas.QUANTIDADECLOROCONFORME, QualidadesAguas.QUANTIDADEFLUORCONFORME,
    QualidadesAguas.QUANTIDADECOLIFTOTAISCONFORME, QualidadesAguas.QUANTIDADECOLIFFECCAISCONFORME,
    QualidadesAguas.QUANTIDADECOLIFTERMOCONFORME, QualidadesAguas.ULTIMAALTERACAO};
	
	public String[] getColunas(){
		return colunas;
	}	
	
	public static final class QualidadesAguas implements BaseColumns {
		public static final String ID = "QLAG_ID";
		public static final String IDLOCALIDADE = "LOCA_ID";
		public static final String IDSETORCOMERCIAL = "STCM_ID";
		public static final String DESCRICAOPADRAOTURBIDEZ = "QLAG_DSPADRAOTURBIDEZ";
		public static final String DESCRICAOPADRAOPH = "QLAG_DSPADRAOPH";
		public static final String DESCRICAOPADRAOCOR = "QLAG_DSPADRAOCOR";
		public static final String DESCRICAOPADRAOCLORO = "QLAG_DSPADRAOCLORO";
		public static final String DESCRICAOPADRAOFLUOR = "QLAG_DSPADRAOFLUOR";
		public static final String DESCRICAOPADRAOFERRO = "QLAG_DSPADRAOFERRO";
		public static final String DESCRICAOPADRAOCOLIFORMESTOTAIS = "QLAG_DSPADRAOCOLIFORMESTOTAIS";
		public static final String DESCRICAOPADRAOCOLIFORMESFECAIS = "QLAG_DSPADRAOCOLIFORMESFECAIS";
		public static final String DESCRICAOPADRAONITRATO = "QLAG_DSPADRAONITRATO";
		public static final String DESCRICAOPADRAOCOLIFTERMO = "QLAG_DSPADRAOCOLIFTERMO";
		public static final String ANOMESREFERENCIA = "QLAG_AMREFERENCIA";
		public static final String NUMEROCLORORESIDUAL = "QLAG_NNCLORORESIDUAL";
		public static final String NUMEROTURBIDEZ = "QLAG_NNINDICETURBIDEZ";
		public static final String NUMEROPH  = "QLAG_NNINDICEPH";
		public static final String NUMEROCOR = "QLAG_NNINDICECOR";
		public static final String NUMEROFLUOR = "QLAG_NNINDICEFLUOR";
		public static final String NUMEROFERRO = "QLAG_NNINDICEFERRO";
		public static final String NUMEROCOLIFORMESTOTAIS= "QLAG_NNINDICECOLIFORMESTOTAIS";
		public static final String NUMEROCOLIFORMESFECAIS  = "QLAG_NNINDICECOLIFORMESFECAIS";
		public static final String NUMERONITRATO = "QLAG_NNNITRATO";
		public static final String NUMEROCOLIFORMESTERMO = "QLAG_NNINDICECOLIFTERMO";
		public static final String DESCRICAOFONTECAPTACAO = "QLAG_DSFONTECAPTACAO";
		public static final String QUANTIDADEEXIGIDASTURBIDEZ = "QLAG_QTTURBIDEZEXIGIDAS";
		public static final String QUANTIDADEEXIGIDASCOR = "QLAG_QTCOREXIGIDAS";
		public static final String QUANTIDADEEXIGIDASCLORO = "QLAG_QTCLOROEXIGIDAS";
		public static final String QUANTIDADEEXIGIDASFLUOR = "QLAG_QTFLUOREXIGIDAS";
		public static final String QUANTIDADEEXIGIDASCOLIFTOTAIS = "QLAG_QTCOLIFTOTEXIGIDAS";
		public static final String QUANTIDADEEXIGIDASCOLIFFECAIS= "QLAG_QTCOLIFFECEXIGIDAS";
		public static final String QUANTIDADEEXIGIDASCOLIFTERMO  = "QLAG_QTCOLIFTERMOSEXIGIDAS";
		public static final String QUANTIDADETURBIDEZANALISADAS  = "QLAG_QTTURBIDEZANALISADAS";
		public static final String QUANTIDADECORANALISADAS  = "QLAG_QTCORANALISADAS";
		public static final String QUANTIDADECLOROANALISADAS  = "QLAG_QTCLOROANALISADAS";
		public static final String QUANTIDADEFLUORANALISADAS  = "QLAG_QTFLUORANALISADAS";
		public static final String QUANTIDADECOLIFTOTAISANALISADAS  = "QLAG_QTCOLIFTOTANLS";
		public static final String QUANTIDADECOLIFFECAISCANALISADAS  = "QLAG_QTCOLIFFECANLS";
		public static final String QUANTIDADECOLIFTERMOANALISADAS  = "QLAG_QTCOLIFTERMOANLS";
		public static final String QUANTIDADETURBIDEZCONFORME  = "QLAG_QTTURBIDEZCONFORME";
		public static final String QUANTIDADECORCONFORME  = "QLAG_QTCORCONFORME";
		public static final String QUANTIDADECLOROCONFORME  = "QLAG_QTCLOROCONFORME";
		public static final String QUANTIDADEFLUORCONFORME  = "QLAG_QTFLUORCONFORME";
		public static final String QUANTIDADECOLIFTOTAISCONFORME  = "QLAG_QTCOLIFTOTCONFORME";
		public static final String QUANTIDADECOLIFFECCAISCONFORME = "QLAG_QTCOLIFFECCONFORME";
		public static final String QUANTIDADECOLIFTERMOCONFORME  = "QLAG_QTCOLIFTERMOCONFORME";
		public static final String ULTIMAALTERACAO = "QLAG_TMULTIMAALTERACAO";
	}
	
	private void insertFromFile(ArrayList<String> obj){
		
		if(obj.get(2).length()!=0){
			setIdLocalidade(Integer.parseInt(obj.get(2)));
		}
		if(obj.get(3).length()!=0){
			setIdSetorComercial(Integer.parseInt(obj.get(3)));
		}
		// qualidade padrao
		setTurbidezPadrao(obj.get(4));	
		setPhPadrao(obj.get(5));	
		setCorPadrao(obj.get(6));	
		setCloroPadrao(obj.get(7));	
		setFluorPadrao(obj.get(8));	
		setFerroPadrao(obj.get(9));	
		setColiformesTotaisPadrao(obj.get(10));	
		setColiformesFecaisPadrao(obj.get(11));	
		setNitratoPadrao(obj.get(12));	
		setColiformesTermoTolerantesPadrao(obj.get(13));	

		if(obj.get(14).length()!=0){
			setAmReferenciaQualidadeAgua(Integer.parseInt(obj.get(14)));	
		}
		//qualidade água
		if(obj.get(15).length()!=0){
			BigDecimal cloroResidual = new BigDecimal(obj.get(15));
			setNumeroCloroResidual(cloroResidual);	
		}
		if(obj.get(16).length()!=0){
			BigDecimal turbidez = new BigDecimal(obj.get(16));
			setNumeroTurbidez(turbidez);	
		}
		if(obj.get(17).length()!=0){
			BigDecimal ph = new BigDecimal(obj.get(17));
			setNumeroPh(ph);	
		}
		if(obj.get(18).length()!=0){
			BigDecimal cor = new BigDecimal(obj.get(18));
			setNumeroCor(cor);	
		}
		if(obj.get(19).length()!=0){
			BigDecimal fluor = new BigDecimal(obj.get(19));
			setNumeroFluor(fluor);	
		}
		if(obj.get(20).length()!=0){
			BigDecimal ferro = new BigDecimal(obj.get(20));
			setNumeroFerro(ferro);	
		}
		if(obj.get(21).length()!=0){
			BigDecimal coliTotais = new BigDecimal(obj.get(21));
			setNumeroColiformesTotais(coliTotais);	
		}
		if(obj.get(22).length()!=0){
			BigDecimal coliFecais = new BigDecimal(obj.get(22));
			setNumeroColiformesFecais(coliFecais);
		}
		if(obj.get(23).length()!=0){
			BigDecimal nitrato = new BigDecimal(obj.get(23));
			setNumeroNitrato(nitrato);	
		}
		if(obj.get(24).length()!=0){
			BigDecimal coliTermos = new BigDecimal(obj.get(24));		
			setNumeroColiformesTermoTolerantes(coliTermos);	
		}
		if(obj.get(25).length()!=0){
			setDescricaoFonteCapacitacao(obj.get(25));	
		}
		if(obj.get(26).length()!=0){
			setQuantidadeTurbidezExigidas(Integer.parseInt(obj.get(26)));	
		}
		if(obj.get(27).length()!=0){
			setQuantidadeCorExigidas(Integer.parseInt(obj.get(27)));		
		}
		if(obj.get(28).length()!=0){
			setQuantidadeCloroExigidas(Integer.parseInt(obj.get(28)));	
		}
		if(obj.get(29).length()!=0){	
			setQuantidadeFluorExigidas(Integer.parseInt(obj.get(29)));	
		}
		if(obj.get(30).length()!=0){	
			setQuantidadeColiformesTotaisExigidas(Integer.parseInt(obj.get(30)));	
		}
		if(obj.get(31).length()!=0){	
			setQuantidadeColiformesFecaisExigidas(Integer.parseInt(obj.get(31)));
		}
		if(obj.get(32).length()!=0){		
			setQuantidadeColiformesTermoTolerantesExigidas(Integer.parseInt(obj.get(32)));
		}
		if(obj.get(33).length()!=0){
			setQuantidadeTurbidezAnalisadas(Integer.parseInt(obj.get(33)));	
		}
		if(obj.get(34).length()!=0){	
			setQuantidadeCorAnalisadas(Integer.parseInt(obj.get(34)));	
		}
		if(obj.get(35).length()!=0){	
			setQuantidadeCloroAnalisadas(Integer.parseInt(obj.get(35)));
		}
		if(obj.get(36).length()!=0){		
			setQuantidadeFluorAnalisadas(Integer.parseInt(obj.get(36)));
		}
		if(obj.get(37).length()!=0){	
			setQuantidadeColiformesTotaisAnalisadas(Integer.parseInt(obj.get(37)));	
		}
		if(obj.get(38).length()!=0){	
			setQuantidadeColiformesFecaisAnalisadas(Integer.parseInt(obj.get(38)));	
		}
		if(obj.get(39).length()!=0){	
			setQuantidadeColiformesTermoTolerantesAnalisadas(Integer.parseInt(obj.get(39)));
		}
		if(obj.get(40).length()!=0){
			setQuantidadeTurbidezConforme(Integer.parseInt(obj.get(40)));	
		}
		if(obj.get(41).length()!=0){	
			setQuantidadeCorConforme(Integer.parseInt(obj.get(41)));	
		}
		if(obj.get(42).length()!=0){	
			setQuantidadeCloroConforme(Integer.parseInt(obj.get(42)));	
		}
		if(obj.get(43).length()!=0){	
			setQuantidadeFluorConforme(Integer.parseInt(obj.get(43)));	
		}
		if(obj.get(44).length()!=0){	
			setQuantidadeColiformesTotaisConforme(Integer.parseInt(obj.get(44)));	
		}
		if(obj.get(45).length()!=0){	
			setQuantidadeColiformesFecaisConforme(Integer.parseInt(obj.get(45)));		
		}
		if(obj.get(46).length()!=0){
			setQuantidadeColiformesTermoTolerantesConforme(Integer.parseInt(obj.get(46)));
		}
		String date = Util.convertDateToDateStr(Util.getCurrentDateTime());		
		setUltimaAlteracao(date);			
	}
	
	public String getNomeTabela(){
		return "qualidade_agua";
	}
	
	public final class QualidadeAguaTipos {
		public final String ID = " INTEGER PRIMARY KEY AUTOINCREMENT ";
		public final String IDLOCALIDADE = " INTEGER NULL ";
		public final String IDSETORCOMERCIAL = " INTEGER NULL ";		
		public final String DESCRICAOPADRAOTURBIDEZ = " VARCHAR(20) NULL ";
		public final String DESCRICAOPADRAOPH = " VARCHAR(20) NULL ";
		public final String DESCRICAOPADRAOCOR = " VARCHAR(20) NULL ";
		public final String DESCRICAOPADRAOCLORO = " VARCHAR(20) NULL ";
		public final String DESCRICAOPADRAOFLUOR = " VARCHAR(20) NULL ";
		public final String DESCRICAOPADRAOFERRO = " VARCHAR(20) NULL ";
		public final String DESCRICAOPADRAOCOLIFORMESTOTAIS = " VARCHAR(20) NULL ";
		public final String DESCRICAOPADRAOCOLIFORMESFECAIS = " VARCHAR(20) NULL ";
		public final String DESCRICAOPADRAONITRATO = " VARCHAR(20) NULL ";
		public final String DESCRICAOPADRAOCOLIFTERMO = " VARCHAR(20) NULL ";
		public final String ANOMESREFERENCIA = " INTEGER NULL ";		
		public final String NUMEROCLORORESIDUAL = " NUMERIC(5,2) NULL ";
		public final String NUMEROTURBIDEZ = " NUMERIC(5,2) NULL ";
		public final String NUMEROPH  = " NUMERIC(5,2) NULL ";
		public final String NUMEROCOR = " NUMERIC(5,2) NULL ";
		public final String NUMEROFLUOR = " NUMERIC(5,2) NULL ";
		public final String NUMEROFERRO = " NUMERIC(5,2) NULL ";
		public final String NUMEROCOLIFORMESTOTAIS= " NUMERIC(5,2) NULL ";
		public final String NUMEROCOLIFORMESFECAIS  = " NUMERIC(5,2) NULL ";
		public final String NUMERONITRATO = " NUMERIC(5,2) NULL ";
		public final String NUMEROCOLIFORMESTERMO = " NUMERIC(5,2) NULL ";
		public final String DESCRICAOFONTECAPTACAO = " VARCHAR(30) NULL ";		
		public final String QUANTIDADEEXIGIDASTURBIDEZ = " INTEGER NULL ";
		public final String QUANTIDADEEXIGIDASCOR = " INTEGER NULL ";
		public final String QUANTIDADEEXIGIDASCLORO = " INTEGER NULL ";
		public final String QUANTIDADEEXIGIDASFLUOR = " INTEGER NULL ";
		public final String QUANTIDADEEXIGIDASCOLIFTOTAIS = " INTEGER NULL ";
		public final String QUANTIDADEEXIGIDASCOLIFFECAIS= " INTEGER NULL ";
		public final String QUANTIDADEEXIGIDASCOLIFTERMO  = " INTEGER NULL ";
		public final String QUANTIDADETURBIDEZANALISADAS  = " INTEGER NULL ";
		public final String QUANTIDADECORANALISADAS  = " INTEGER NULL ";
		public final String QUANTIDADECLOROANALISADAS  = " INTEGER NULL ";
		public final String QUANTIDADEFLUORANALISADAS  = " INTEGER NULL ";
		public final String QUANTIDADECOLIFTOTAISANALISADAS  = " INTEGER NULL ";
		public final String QUANTIDADECOLIFFECAISCANALISADAS  = " INTEGER NULL ";
		public final String QUANTIDADECOLIFTERMOANALISADAS  = " INTEGER NULL ";
		public final String QUANTIDADETURBIDEZCONFORME  = " INTEGER NULL ";
		public final String QUANTIDADECORCONFORME  = " INTEGER NULL ";
		public final String QUANTIDADECLOROCONFORME  = " INTEGER NULL ";
		public final String QUANTIDADEFLUORCONFORME  = " INTEGER NULL ";
		public final String QUANTIDADECOLIFTOTAISCONFORME  = " INTEGER NULL ";
		public final String QUANTIDADECOLIFFECCAISCONFORME = " INTEGER NULL ";
		public final String QUANTIDADECOLIFTERMOCONFORME  = " INTEGER NULL ";		
		public final String ULTIMAALTERACAO = " TIMESTAMP NOT NULL ";
			  
		private String[] tipos = new String[] {
				ID, IDLOCALIDADE, IDSETORCOMERCIAL, DESCRICAOPADRAOTURBIDEZ, DESCRICAOPADRAOPH, DESCRICAOPADRAOCOR,
				DESCRICAOPADRAOCLORO, DESCRICAOPADRAOFLUOR, DESCRICAOPADRAOFERRO, DESCRICAOPADRAOCOLIFORMESTOTAIS,
				DESCRICAOPADRAOCOLIFORMESFECAIS, DESCRICAOPADRAONITRATO, DESCRICAOPADRAOCOLIFTERMO, ANOMESREFERENCIA,
				NUMEROCLORORESIDUAL, NUMEROTURBIDEZ, NUMEROPH, NUMEROCOR, NUMEROFLUOR, NUMEROFERRO, NUMEROCOLIFORMESTOTAIS,
				NUMEROCOLIFORMESFECAIS, NUMERONITRATO, NUMEROCOLIFORMESTERMO, DESCRICAOFONTECAPTACAO,
				QUANTIDADEEXIGIDASTURBIDEZ, QUANTIDADEEXIGIDASCOR, QUANTIDADEEXIGIDASCLORO, QUANTIDADEEXIGIDASFLUOR,
				QUANTIDADEEXIGIDASCOLIFTOTAIS, QUANTIDADEEXIGIDASCOLIFFECAIS, QUANTIDADEEXIGIDASCOLIFTERMO, QUANTIDADETURBIDEZANALISADAS,
				QUANTIDADECORANALISADAS, QUANTIDADECLOROANALISADAS, QUANTIDADEFLUORANALISADAS, QUANTIDADECOLIFTOTAISANALISADAS,
				QUANTIDADECOLIFFECAISCANALISADAS, QUANTIDADECOLIFTERMOANALISADAS, QUANTIDADETURBIDEZCONFORME,
				QUANTIDADECORCONFORME, QUANTIDADECLOROCONFORME, QUANTIDADEFLUORCONFORME, QUANTIDADECOLIFTOTAISCONFORME,
				QUANTIDADECOLIFFECCAISCONFORME, QUANTIDADECOLIFTERMOCONFORME, ULTIMAALTERACAO};	
		
		public String[] getTipos(){
			return tipos;
		}
	}

	public ContentValues preencherValues() {
		ContentValues values = new ContentValues();
		values.put(QualidadesAguas.ID, getId());
		values.put(QualidadesAguas.IDLOCALIDADE, getIdLocalidade());
		values.put(QualidadesAguas.IDSETORCOMERCIAL, getIdSetorComercial());
		values.put(QualidadesAguas.ANOMESREFERENCIA, getAmReferenciaQualidadeAgua());
		values.put(QualidadesAguas.DESCRICAOFONTECAPTACAO, getDescricaoFonteCapacitacao());
		values.put(QualidadesAguas.DESCRICAOPADRAOCLORO, getCloroPadrao());
		values.put(QualidadesAguas.DESCRICAOPADRAOCOLIFORMESFECAIS, getColiformesFecaisPadrao());
		values.put(QualidadesAguas.DESCRICAOPADRAOCOLIFORMESTOTAIS, getColiformesTotaisPadrao());
		values.put(QualidadesAguas.DESCRICAOPADRAOCOLIFTERMO, getColiformesTermoTolerantesPadrao());
		values.put(QualidadesAguas.DESCRICAOPADRAOCOR, getCorPadrao());
		values.put(QualidadesAguas.DESCRICAOPADRAOFERRO, getFerroPadrao());
		values.put(QualidadesAguas.DESCRICAOPADRAOFLUOR, getFluorPadrao());
		values.put(QualidadesAguas.DESCRICAOPADRAONITRATO, getNitratoPadrao());
		values.put(QualidadesAguas.DESCRICAOPADRAOPH, getPhPadrao());
		values.put(QualidadesAguas.DESCRICAOPADRAOTURBIDEZ, getTurbidezPadrao());
		if(getNumeroCloroResidual()!=null){
			values.put(QualidadesAguas.NUMEROCLORORESIDUAL, getNumeroCloroResidual().toString());
		}
		if(getNumeroColiformesFecais()!=null){
			values.put(QualidadesAguas.NUMEROCOLIFORMESFECAIS, getNumeroColiformesFecais().toString());
		}
		if(getNumeroColiformesTermoTolerantes()!=null){
			values.put(QualidadesAguas.NUMEROCOLIFORMESTERMO, getNumeroColiformesTermoTolerantes().toString());
		}
		if(getNumeroColiformesTotais()!=null){
			values.put(QualidadesAguas.NUMEROCOLIFORMESTOTAIS, getNumeroColiformesTotais().toString());
		}
		if(getNumeroCor()!=null){
			values.put(QualidadesAguas.NUMEROCOR, getNumeroCor().toString());
		}
		if(getNumeroFerro()!=null){
			values.put(QualidadesAguas.NUMEROFERRO, getNumeroFerro().toString());
		}
		if(getNumeroFluor()!=null){
			values.put(QualidadesAguas.NUMEROFLUOR, getNumeroFluor().toString());
		}
		if(getNumeroNitrato()!=null){
			values.put(QualidadesAguas.NUMERONITRATO, getNumeroNitrato().toString());
		}
		if(getNumeroPh()!=null){
			values.put(QualidadesAguas.NUMEROPH, getNumeroPh().toString());
		}
		if(getNumeroTurbidez()!=null){
			values.put(QualidadesAguas.NUMEROTURBIDEZ, getNumeroTurbidez().toString());
		}
		
		values.put(QualidadesAguas.QUANTIDADECLOROANALISADAS, getQuantidadeCloroAnalisadas());
		values.put(QualidadesAguas.QUANTIDADECLOROCONFORME, getQuantidadeCloroConforme());
		values.put(QualidadesAguas.QUANTIDADECOLIFFECAISCANALISADAS, getQuantidadeColiformesFecaisAnalisadas());
		values.put(QualidadesAguas.QUANTIDADECOLIFFECCAISCONFORME, getQuantidadeColiformesFecaisConforme());
		values.put(QualidadesAguas.QUANTIDADECOLIFTERMOANALISADAS, getQuantidadeColiformesTermoTolerantesAnalisadas());
		values.put(QualidadesAguas.QUANTIDADECOLIFTERMOCONFORME, getQuantidadeColiformesTermoTolerantesConforme());
		values.put(QualidadesAguas.QUANTIDADECORANALISADAS, getQuantidadeCorAnalisadas());
		values.put(QualidadesAguas.QUANTIDADECORCONFORME, getQuantidadeCorConforme());
		values.put(QualidadesAguas.QUANTIDADEEXIGIDASCLORO, getQuantidadeCloroExigidas());
		values.put(QualidadesAguas.QUANTIDADEEXIGIDASCOLIFFECAIS, getQuantidadeColiformesFecaisExigidas());
		values.put(QualidadesAguas.QUANTIDADEEXIGIDASCOLIFTERMO, getQuantidadeColiformesTermoTolerantesExigidas());
		values.put(QualidadesAguas.QUANTIDADEEXIGIDASCOLIFTOTAIS, getQuantidadeColiformesTotaisExigidas());
		values.put(QualidadesAguas.QUANTIDADEEXIGIDASCOR, getQuantidadeCorExigidas());
		values.put(QualidadesAguas.QUANTIDADEEXIGIDASFLUOR, getQuantidadeFluorExigidas());
		values.put(QualidadesAguas.QUANTIDADEEXIGIDASTURBIDEZ, getQuantidadeTurbidezExigidas());
		values.put(QualidadesAguas.QUANTIDADEFLUORANALISADAS, getQuantidadeFluorAnalisadas());
		values.put(QualidadesAguas.QUANTIDADEFLUORCONFORME, getQuantidadeFluorConforme());
		values.put(QualidadesAguas.QUANTIDADETURBIDEZANALISADAS, getQuantidadeTurbidezAnalisadas());
		values.put(QualidadesAguas.QUANTIDADETURBIDEZCONFORME, getQuantidadeTurbidezConforme());
		values.put(QualidadesAguas.QUANTIDADECOLIFTOTAISANALISADAS, getQuantidadeColiformesTotaisAnalisadas());
		values.put(QualidadesAguas.QUANTIDADECOLIFTOTAISCONFORME, getQuantidadeColiformesTotaisConforme());
		String dataStr = Util.convertDateToDateStr(Util.getCurrentDateTime());
		values.put(QualidadesAguas.ULTIMAALTERACAO, dataStr);
		
		return values;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<QualidadeAgua> preencherObjetos(Cursor cursor) {		
		ArrayList<QualidadeAgua> retorno = null;
		
		if (cursor.moveToFirst()) {	
			int codigo = cursor.getColumnIndex(QualidadesAguas.ID);
			int localidade = cursor.getColumnIndex(QualidadesAguas.IDLOCALIDADE);
			int setorComercial = cursor.getColumnIndex(QualidadesAguas.IDSETORCOMERCIAL);
			int amReferencia = cursor.getColumnIndex(QualidadesAguas.ANOMESREFERENCIA);
			int descricaoFonteCapt = cursor.getColumnIndex(QualidadesAguas.DESCRICAOFONTECAPTACAO);
			int padraoCloro = cursor.getColumnIndex(QualidadesAguas.DESCRICAOPADRAOCLORO);
			int padraoCoLifFecais = cursor.getColumnIndex(QualidadesAguas.DESCRICAOPADRAOCOLIFORMESFECAIS);
			int padraoColifTotais = cursor.getColumnIndex(QualidadesAguas.DESCRICAOPADRAOCOLIFORMESTOTAIS);
			int padraoColifTermo = cursor.getColumnIndex(QualidadesAguas.DESCRICAOPADRAOCOLIFTERMO);
			int padraoCor = cursor.getColumnIndex(QualidadesAguas.DESCRICAOPADRAOCOR);
			int padraoFerro = cursor.getColumnIndex(QualidadesAguas.DESCRICAOPADRAOFERRO);
			int padraoFluor = cursor.getColumnIndex(QualidadesAguas.DESCRICAOPADRAOFLUOR);
			int padraoNitrato = cursor.getColumnIndex(QualidadesAguas.DESCRICAOPADRAONITRATO);
			int padraoPh = cursor.getColumnIndex(QualidadesAguas.DESCRICAOPADRAOPH);
			int padraoTurbidez= cursor.getColumnIndex(QualidadesAguas.DESCRICAOPADRAOTURBIDEZ);
			
			int numCloroResidual = cursor.getColumnIndex(QualidadesAguas.NUMEROCLORORESIDUAL);	
			int numColifFecais = cursor.getColumnIndex(QualidadesAguas.NUMEROCOLIFORMESFECAIS);
			int numColifTermo = cursor.getColumnIndex(QualidadesAguas.NUMEROCOLIFORMESTERMO);
			int numColifTotais = cursor.getColumnIndex(QualidadesAguas.NUMEROCOLIFORMESTOTAIS);
			int numCor = cursor.getColumnIndex(QualidadesAguas.NUMEROCOR);
			int numFerro = cursor.getColumnIndex(QualidadesAguas.NUMEROFERRO);
			int numFluor = cursor.getColumnIndex(QualidadesAguas.NUMEROFLUOR);
			int numNitrato = cursor.getColumnIndex(QualidadesAguas.NUMERONITRATO);
			int numPh = cursor.getColumnIndex(QualidadesAguas.NUMEROPH);
			int numTurbidez = cursor.getColumnIndex(QualidadesAguas.NUMEROTURBIDEZ);
			
			int qtdeCloroAnalisadas = cursor.getColumnIndex(QualidadesAguas.QUANTIDADECLOROANALISADAS);
			int qtdeCloroConforme = cursor.getColumnIndex(QualidadesAguas.QUANTIDADECLOROCONFORME);
			int qtdeColifFecaisAnalisadas = cursor.getColumnIndex(QualidadesAguas.QUANTIDADECOLIFFECAISCANALISADAS);
			int qtdeColifFecaisConforme = cursor.getColumnIndex(QualidadesAguas.QUANTIDADECOLIFFECCAISCONFORME);
			int qtdeColifTermoAnalisadas = cursor.getColumnIndex(QualidadesAguas.QUANTIDADECOLIFTERMOANALISADAS);
			int qtdeColifTermoConforme = cursor.getColumnIndex(QualidadesAguas.QUANTIDADECOLIFTERMOCONFORME);
			int qtdeColifTotaisAnalisadas = cursor.getColumnIndex(QualidadesAguas.QUANTIDADECOLIFTOTAISANALISADAS);
			int qtdeColifTotaisConforme = cursor.getColumnIndex(QualidadesAguas.QUANTIDADECOLIFTOTAISCONFORME);
			int qtdeCorAnalisadas = cursor.getColumnIndex(QualidadesAguas.QUANTIDADECORANALISADAS);
			int qtdeCorConforme = cursor.getColumnIndex(QualidadesAguas.QUANTIDADECORCONFORME);
			int qtdeExigidaCloro = cursor.getColumnIndex(QualidadesAguas.QUANTIDADEEXIGIDASCLORO);
			int qtdeExigidaColifFecais = cursor.getColumnIndex(QualidadesAguas.QUANTIDADEEXIGIDASCOLIFFECAIS);
			int qtdeExigidaColifTermo = cursor.getColumnIndex(QualidadesAguas.QUANTIDADEEXIGIDASCOLIFTERMO);
			int qtdeExigidaColifTotais = cursor.getColumnIndex(QualidadesAguas.QUANTIDADEEXIGIDASCOLIFTOTAIS);				
			int qtdeExigidaCor = cursor.getColumnIndex(QualidadesAguas.QUANTIDADEEXIGIDASCOR);
			int qtdeExigidaFluor = cursor.getColumnIndex(QualidadesAguas.QUANTIDADEEXIGIDASFLUOR);
			int qtdeExigidaTurbidez = cursor.getColumnIndex(QualidadesAguas.QUANTIDADEEXIGIDASTURBIDEZ);
			int qtdeFluorAnalisadas = cursor.getColumnIndex(QualidadesAguas.QUANTIDADEFLUORANALISADAS);
			int qtdeFluorConforme = cursor.getColumnIndex(QualidadesAguas.QUANTIDADEFLUORCONFORME);
			int qtdeTurbidezAnalisadas = cursor.getColumnIndex(QualidadesAguas.QUANTIDADETURBIDEZANALISADAS);
			int qtdeTurbidezConforme = cursor.getColumnIndex(QualidadesAguas.QUANTIDADETURBIDEZCONFORME);
			
			int ultimaAlteracao = cursor.getColumnIndex(QualidadesAguas.ULTIMAALTERACAO);
			
			retorno = new ArrayList<QualidadeAgua>();			

			do {	
				QualidadeAgua qualidadeAgua = new QualidadeAgua();
				qualidadeAgua.setId(Util.getIntBanco(cursor, QualidadesAguas.ID, codigo));
				qualidadeAgua.setIdLocalidade(Util.getIntBanco(cursor, QualidadesAguas.IDLOCALIDADE, localidade));
				qualidadeAgua.setIdSetorComercial(Util.getIntBanco(cursor, QualidadesAguas.IDSETORCOMERCIAL, setorComercial));
				qualidadeAgua.setAmReferenciaQualidadeAgua(Util.getIntBanco(cursor, QualidadesAguas.ANOMESREFERENCIA, amReferencia));
				qualidadeAgua.setColiformesFecaisPadrao(cursor.getString(padraoCoLifFecais ));
				qualidadeAgua.setCloroPadrao(cursor.getString(padraoCloro));
				qualidadeAgua.setColiformesTermoTolerantesPadrao(cursor.getString(padraoColifTermo ));
				qualidadeAgua.setColiformesTotaisPadrao(cursor.getString(padraoColifTotais ));
				qualidadeAgua.setCorPadrao(cursor.getString(padraoCor ));
				qualidadeAgua.setPhPadrao(cursor.getString(padraoPh ));
				qualidadeAgua.setTurbidezPadrao(cursor.getString(padraoTurbidez ));
				qualidadeAgua.setDescricaoFonteCapacitacao(cursor.getString(descricaoFonteCapt ));
				qualidadeAgua.setFerroPadrao(cursor.getString(padraoFerro));
				qualidadeAgua.setFluorPadrao(cursor.getString(padraoFluor));				
				qualidadeAgua.setNitratoPadrao(cursor.getString(padraoNitrato));
				
				if (!cursor.isNull( cursor.getColumnIndex(QualidadesAguas.NUMEROCLORORESIDUAL) )){
					qualidadeAgua.setNumeroCloroResidual(Util.getDoubleBanco(cursor, QualidadesAguas.NUMEROCLORORESIDUAL, numCloroResidual));
				}
				
				if (!cursor.isNull( cursor.getColumnIndex(QualidadesAguas.NUMEROCOLIFORMESFECAIS) )){
					qualidadeAgua.setNumeroColiformesFecais(Util.getDoubleBanco(cursor, QualidadesAguas.NUMEROCOLIFORMESFECAIS, numColifFecais));
				}
				
				if (!cursor.isNull( cursor.getColumnIndex(QualidadesAguas.NUMEROCOLIFORMESTERMO) )){
					qualidadeAgua.setNumeroColiformesTermoTolerantes(Util.getDoubleBanco(cursor, QualidadesAguas.NUMEROCOLIFORMESTERMO, numColifTermo));
				}
				
				if (!cursor.isNull( cursor.getColumnIndex(QualidadesAguas.NUMEROCOLIFORMESTOTAIS) )){
					qualidadeAgua.setNumeroColiformesTotais(Util.getDoubleBanco(cursor, QualidadesAguas.NUMEROCOLIFORMESTOTAIS, numColifTotais));
				}
				
				if (!cursor.isNull( cursor.getColumnIndex(QualidadesAguas.NUMEROCOR) )){
					qualidadeAgua.setNumeroCor(Util.getDoubleBanco(cursor, QualidadesAguas.NUMEROCOR, numCor));
				}
				
				if (!cursor.isNull( cursor.getColumnIndex(QualidadesAguas.NUMEROFERRO) )){
					qualidadeAgua.setNumeroFerro(Util.getDoubleBanco(cursor, QualidadesAguas.NUMEROFERRO, numFerro));
				}
				
				if (!cursor.isNull( cursor.getColumnIndex(QualidadesAguas.NUMEROFLUOR) )){
					qualidadeAgua.setNumeroFluor(Util.getDoubleBanco(cursor, QualidadesAguas.NUMEROFLUOR, numFluor));
				}
				
				if (!cursor.isNull( cursor.getColumnIndex(QualidadesAguas.NUMERONITRATO) )){
					qualidadeAgua.setNumeroNitrato(Util.getDoubleBanco(cursor, QualidadesAguas.NUMERONITRATO, numNitrato));
				}
				
				if (!cursor.isNull( cursor.getColumnIndex(QualidadesAguas.NUMEROPH) )){
					qualidadeAgua.setNumeroPh(Util.getDoubleBanco(cursor, QualidadesAguas.NUMEROPH, numPh));
				}
				
				if (!cursor.isNull( cursor.getColumnIndex(QualidadesAguas.NUMEROTURBIDEZ) )){
					qualidadeAgua.setNumeroTurbidez(Util.getDoubleBanco(cursor, QualidadesAguas.NUMEROTURBIDEZ, numTurbidez));
				}
				
				qualidadeAgua.setQuantidadeCloroAnalisadas(Util.getIntBanco(cursor, QualidadesAguas.QUANTIDADECLOROANALISADAS, qtdeCloroAnalisadas));
				qualidadeAgua.setQuantidadeCloroConforme(Util.getIntBanco(cursor, QualidadesAguas.QUANTIDADECLOROCONFORME, qtdeCloroConforme));
				qualidadeAgua.setQuantidadeColiformesFecaisAnalisadas(Util.getIntBanco(cursor, QualidadesAguas.QUANTIDADECOLIFFECAISCANALISADAS, qtdeColifFecaisAnalisadas));
				qualidadeAgua.setQuantidadeColiformesFecaisConforme(Util.getIntBanco(cursor, QualidadesAguas.QUANTIDADECOLIFFECCAISCONFORME, qtdeColifFecaisConforme));
				qualidadeAgua.setQuantidadeColiformesTermoTolerantesAnalisadas(Util.getIntBanco(cursor, QualidadesAguas.QUANTIDADECOLIFTERMOANALISADAS, qtdeColifTermoAnalisadas));
				qualidadeAgua.setQuantidadeColiformesTermoTolerantesConforme(Util.getIntBanco(cursor, QualidadesAguas.QUANTIDADECOLIFTERMOCONFORME, qtdeColifTermoConforme));
				qualidadeAgua.setQuantidadeColiformesTotaisAnalisadas(Util.getIntBanco(cursor, QualidadesAguas.QUANTIDADECOLIFTOTAISANALISADAS, qtdeColifTotaisAnalisadas));
				qualidadeAgua.setQuantidadeColiformesTotaisConforme(Util.getIntBanco(cursor, QualidadesAguas.QUANTIDADECOLIFTOTAISCONFORME, qtdeColifTotaisConforme));
				qualidadeAgua.setQuantidadeCorAnalisadas(Util.getIntBanco(cursor, QualidadesAguas.QUANTIDADECORANALISADAS, qtdeCorAnalisadas));
				qualidadeAgua.setQuantidadeCorConforme(Util.getIntBanco(cursor, QualidadesAguas.QUANTIDADECORCONFORME, qtdeCorConforme));
				qualidadeAgua.setQuantidadeCloroExigidas(Util.getIntBanco(cursor, QualidadesAguas.QUANTIDADEEXIGIDASCLORO, qtdeExigidaCloro));
				qualidadeAgua.setQuantidadeColiformesFecaisExigidas(Util.getIntBanco(cursor, QualidadesAguas.QUANTIDADEEXIGIDASCOLIFFECAIS, qtdeExigidaColifFecais));
				qualidadeAgua.setQuantidadeColiformesTermoTolerantesExigidas(Util.getIntBanco(cursor, QualidadesAguas.QUANTIDADEEXIGIDASCOLIFTERMO, qtdeExigidaColifTermo));
				qualidadeAgua.setQuantidadeColiformesTotaisExigidas(Util.getIntBanco(cursor, QualidadesAguas.QUANTIDADEEXIGIDASCOLIFTOTAIS, qtdeExigidaColifTotais));
				qualidadeAgua.setQuantidadeCorExigidas(Util.getIntBanco(cursor, QualidadesAguas.QUANTIDADEEXIGIDASCOR, qtdeExigidaCor));
				qualidadeAgua.setQuantidadeFluorExigidas(Util.getIntBanco(cursor, QualidadesAguas.QUANTIDADEEXIGIDASFLUOR, qtdeExigidaFluor));
				qualidadeAgua.setQuantidadeTurbidezExigidas(Util.getIntBanco(cursor, QualidadesAguas.QUANTIDADEEXIGIDASTURBIDEZ, qtdeExigidaTurbidez));
				qualidadeAgua.setQuantidadeFluorAnalisadas(Util.getIntBanco(cursor, QualidadesAguas.QUANTIDADEFLUORANALISADAS, qtdeFluorAnalisadas));
				qualidadeAgua.setQuantidadeFluorConforme(Util.getIntBanco(cursor, QualidadesAguas.QUANTIDADEFLUORCONFORME, qtdeFluorConforme));
				qualidadeAgua.setQuantidadeTurbidezAnalisadas(Util.getIntBanco(cursor, QualidadesAguas.QUANTIDADETURBIDEZANALISADAS, qtdeTurbidezAnalisadas));
				qualidadeAgua.setQuantidadeTurbidezConforme(Util.getIntBanco(cursor, QualidadesAguas.QUANTIDADETURBIDEZCONFORME, qtdeTurbidezConforme));
				qualidadeAgua.setUltimaAlteracao(cursor.getString(ultimaAlteracao));
			
				retorno.add(qualidadeAgua);
				
			} while (cursor.moveToNext());
		}		
		return retorno;
	}
}