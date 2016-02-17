package com.br.ipad.isc.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.util.Log;

import com.br.ipad.isc.excecoes.RepositorioException;
import com.br.ipad.isc.repositorios.RepositorioBasico;
import com.br.ipad.isc.util.ConstantesSistema;
import com.br.ipad.isc.util.Util;

/**
 * [] Classe Básica - Sistema Parámetro
 * 
 * @author Fernanda Almeida
 * @since 08/03/2012
 */
public class HidrometroInstalado extends ObjetoBasico implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public HidrometroInstalado(ArrayList<String> obj){
		insertFromFile(obj);
	}
	
	//Construtor Vazio
	public HidrometroInstalado(){}
	
	private Integer id;
	private ImovelConta matricula;
	private Integer tipoMedicao;
	private String numeroHidrometro;
	private Date dataInstalacaoHidrometro;
	private Integer numDigitosLeituraHidrometro;
	private Integer leituraAnteriorFaturamento;//
	private Date dataLeituraAnterior;
	private Integer codigoSituacaoLeituraAnterior;
	private Integer leituraLimiteInferior;
	private Integer leituraLimiteSuperior;
	private Integer consumoMedio;
	private String localInstalacao;
	private Integer leituraAnteriorInformada;//Informada
	private Integer tipoRateio;
	private Integer leituraHidrometoInstalada;//
	private Integer indcParalizacaoLeitura;
	
	private Integer consumoMinimoContratadoContratoDemanda;
	private Integer percentualDescontoContratoDemanda;
	private String tombamento;
	private Integer leitura = null;
	private Integer anormalidade = null;
	private Integer leituraAnteriorDigitada = null;
	private Date dataLeitura = null;
	
	private Integer qtdDiasAjustado = null;
	
	private Integer leituraAtualFaturamento = null;
	private Integer leituraAtualFaturamentoHelper = null;
	
	private Date ultimaAlteracao;
	
	//anormalidade apenas setada para caern quando [UC0704] item 6
	private static Integer anormalidadeFaturadaCaern = null;
		
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public ImovelConta getMatricula() {
		return matricula;
	}
	public void setMatricula(ImovelConta matricula) {
		this.matricula = matricula;
	}
	public void setIdString(String id) {
		this.id = Util.verificarNuloInt(id);
	}
	
	public Integer getLeitura() {
		return leitura;
	}
	public void setLeitura(Integer leitura) {
		this.leitura = leitura;
	}
	public Integer getAnormalidade() {
		return anormalidade;
	}
	public void setAnormalidade(Integer anormalidade) {
		this.anormalidade = anormalidade;
	}
	public Date getDataLeitura() {
		return dataLeitura;
	}
	public void setDataLeitura(Date dataLeitura) {
		this.dataLeitura = dataLeitura;;
	}
	public Integer getTipoMedicao() {
		return tipoMedicao;
	}
	public void setTipoMedicao(Integer tipoMedicao) {
		this.tipoMedicao = tipoMedicao;
	}
	public Integer getNumDigitosLeituraHidrometro() {
		return numDigitosLeituraHidrometro;
	}
	public void setNumDigitosLeituraHidrometro(Integer numDigitosLeituraHidrometro) {
		this.numDigitosLeituraHidrometro = numDigitosLeituraHidrometro;
	}
	public Integer getLeituraAnteriorDigitada() {
		return leituraAnteriorDigitada;
	}
	public void setLeituraAnteriorDigitada(Integer leituraAnteriorDigitada) {
		this.leituraAnteriorDigitada = leituraAnteriorDigitada;
	}
	public Date getDataLeituraAnterior() {
		return dataLeituraAnterior;
	}
	public void setDataLeituraAnterior(Date dataLeituraAnterior) {
		this.dataLeituraAnterior = dataLeituraAnterior;
	}
	public Integer getCodigoSituacaoLeituraAnterior() {
		return codigoSituacaoLeituraAnterior;
	}
	public void setCodigoSituacaoLeituraAnterior(Integer codigoSituacaoLeituraAnterior) {
		this.codigoSituacaoLeituraAnterior = codigoSituacaoLeituraAnterior;
	}
	public Integer getConsumoMedio() {
		return consumoMedio;
	}
	public void setConsumoMedio(Integer consumoMedio) {
		this.consumoMedio = consumoMedio;
	}
	public String getLocalInstalacao() {
		return localInstalacao;
	}
	public void setLocalInstalacao(String localInstalacao) {
		this.localInstalacao = localInstalacao;
	}
	public Integer getTipoRateio() {
		return tipoRateio;
	}
	public void setTipoRateio(Integer tipoRateio) {
		this.tipoRateio = tipoRateio;
	}
	public Integer getIndcParalizacaoLeitura() {
		return indcParalizacaoLeitura;
	}
	public void setIndcParalizacaoLeitura(Integer indcParalizacaoLeitura) {
		this.indcParalizacaoLeitura = indcParalizacaoLeitura;
	}
	
	public Integer getConsumoMinimoContratadoContratoDemanda() {
		return consumoMinimoContratadoContratoDemanda;
	}
	public void setConsumoMinimoContratadoContratoDemanda(Integer consumoMinimoContratadoContratoDemanda) {
		this.consumoMinimoContratadoContratoDemanda = consumoMinimoContratadoContratoDemanda;
	}
	public Integer getPercentualDescontoContratoDemanda() {
		return percentualDescontoContratoDemanda;
	}
	public void setPercentualDescontoContratoDemanda(Integer percentualDescontoContratoDemanda) {
		this.percentualDescontoContratoDemanda = percentualDescontoContratoDemanda;
	}
	public void setLeituraAnteriorFaturamento(Integer leituraAnteriorFaturamento) {
		this.leituraAnteriorFaturamento = leituraAnteriorFaturamento;
	}
	public String getNumeroHidrometro() {
		return numeroHidrometro;
	}
	public void setNumeroHidrometro(String numeroHidrometro) {
		this.numeroHidrometro = numeroHidrometro;
	}
	public Date getDataInstalacaoHidrometro() {
		return dataInstalacaoHidrometro;
	}
	public void setDataInstalacaoHidrometro(Date dataInstalacaoHidrometro) {
		this.dataInstalacaoHidrometro = dataInstalacaoHidrometro;
	}
	public Integer getLeituraAnteriorFaturamento() {
		return leituraAnteriorFaturamento;
	}
	public Integer getLeituraLimiteInferior() {
		return leituraLimiteInferior;
	}
	public void setLeituraLimiteInferior(Integer leituraLimiteInferior) {
		this.leituraLimiteInferior = leituraLimiteInferior;
	}
	public String getTombamento() {
		return tombamento;
	}
	public void setTombamento(String tombamento) {
		this.tombamento = tombamento;
	}
	public Integer getLeituraLimiteSuperior() {
		return leituraLimiteSuperior;
	}
	public void setLeituraLimiteSuperior(Integer leituraLimiteSuperior) {
		this.leituraLimiteSuperior = leituraLimiteSuperior;
	}
	public Integer getLeituraAnteriorInformada() {
		return leituraAnteriorInformada;
	}
	public void setLeituraAnteriorInformada(Integer leituraAnteriorInformada) {
		this.leituraAnteriorInformada = leituraAnteriorInformada;
	}
	public Integer getLeituraHidrometoInstalada() {
		return leituraHidrometoInstalada;
	}
	public void setLeituraHidrometoInstalada(Integer leituraHidrometoInstalada) {
		this.leituraHidrometoInstalada = leituraHidrometoInstalada;
	}
	public Date getUltimaAlteracao() {
		return ultimaAlteracao;
	}
	public void setUltimaAlteracao(Long ultimaAlteracao) {
		this.ultimaAlteracao = new Date(ultimaAlteracao);
	}
	public void setUltimaAlteracao(Date ultimaAlteracao) {
		this.ultimaAlteracao = ultimaAlteracao;
	}
	
	public Integer getQtdDiasAjustado(){
		return qtdDiasAjustado;
	}
	
	public void setQtdDiasAjustado(Integer qtdDiasAjustado){
		this.qtdDiasAjustado = qtdDiasAjustado;
	}

	public Integer getLeituraAtualFaturamento() {
		return leituraAtualFaturamento;
	}
	public void setLeituraAtualFaturamento(Integer leituraAtualFaturamento) {
		this.leituraAtualFaturamento = leituraAtualFaturamento;
	}
	
	public Integer getAnormalidadeFaturadaCaern() {
		return anormalidadeFaturadaCaern;
	}

	public void setAnormalidadeFaturadaCaern(Integer anormalidadeFaturadaCaern) {
		this.anormalidadeFaturadaCaern = anormalidadeFaturadaCaern;
	}

	private static String[] colunas = new String[] { HidrometrosInstalados.ID,HidrometrosInstalados.MATRICULA, HidrometrosInstalados.MEDICAOTIPO,  HidrometrosInstalados.NUMEROHIDROMETRO, HidrometrosInstalados.DATAINSTALACAOHIDROMETRO,
                                             HidrometrosInstalados.NUMERODIGITOSLEITURA, HidrometrosInstalados.LEITURAANTERIORFATURAMENTO, HidrometrosInstalados.DATALEITURAANORMALIDADETFATURAMENTO,
                                             HidrometrosInstalados.IDSITUACAOLEITURAANTERIOR, HidrometrosInstalados.LEITURALIMITEINFERIOR, HidrometrosInstalados.LEITURALIMITESUPERIOR,
                                             HidrometrosInstalados.CONSUMOMEDIO, HidrometrosInstalados.DESCRICAOHIDRLOCALINSTALACAO, HidrometrosInstalados.LEITURAANTERIORINFORMADA,
                                             HidrometrosInstalados.RATEIOTIPO, HidrometrosInstalados.LEITURAINSTALACAOHIDROMETRO, HidrometrosInstalados.INDICADOPARALISARLEITURA,
                                              HidrometrosInstalados.CONSUMOMINIMOCONTRATADO, HidrometrosInstalados.PERCDESCONTOCONTRATODEMANDA,
                                             HidrometrosInstalados.TOMBAMENTO,HidrometrosInstalados.LEITURACAMPO, HidrometrosInstalados.IDANORMALIDADECAMPO, 
                                             HidrometrosInstalados.DATALEITURACAMPO,HidrometrosInstalados.LEITURAATUALFATURAMENTO,HidrometrosInstalados.LEITURAATUALFATURAMENTOHELPER,HidrometrosInstalados.LEITURAANTERIORDIGITADA ,HidrometrosInstalados.ULTIMAALTERACAO};
	
	public String[] getColunas(){
		return colunas;
	}	
	
	public static final class HidrometrosInstalados implements BaseColumns {
		public static final String ID = "HIIN_ID";
		public static final String MATRICULA = "IMOV_ID";
		public static final String MEDICAOTIPO = "MEDT_ID";
		public static final String NUMEROHIDROMETRO  = "HIIN_NNHIDROMETRO";
		public static final String DATAINSTALACAOHIDROMETRO  = "HIIN_DTINSTALACAOHIDROMETRO";
		public static final String NUMERODIGITOSLEITURA  = "HIIN_NNDIGITOSLEITURA";
		public static final String LEITURAANTERIORFATURAMENTO  = "HIIN_NNLEITANTFATURAMENTO";
		public static final String DATALEITURAANORMALIDADETFATURAMENTO  = "HIIN_DTLEITANTFATURAMENTO";
		public static final String IDSITUACAOLEITURAANTERIOR  = "LTST_IDANTERIOR";
		public static final String LEITURALIMITEINFERIOR  = "HIIN_NNLEITLIMITEINFERIOR";
		public static final String LEITURALIMITESUPERIOR  = "HIIN_NNLEITLIMITESUPERIOR";
		public static final String CONSUMOMEDIO  = "HIIN_NNCONSUMOMEDIO";
		public static final String DESCRICAOHIDRLOCALINSTALACAO  = "HILI_DSHIDMTLOCALINSTALACAO";
		public static final String LEITURAANTERIORINFORMADA  = "HIIN_NNLEITANTINFORMADA";
		public static final String RATEIOTIPO  = "RTTP_ID";
		public static final String LEITURAINSTALACAOHIDROMETRO  = "HIDI_NNLEITINSTALACAOHIDMT";
		public static final String INDICADOPARALISARLEITURA  = "HIIN_ICPARALISARLEITURA";
		
		public static final String CONSUMOMINIMOCONTRATADO  = "HIIN_NNCONSUMOMINCONTRATADO";
		public static final String PERCDESCONTOCONTRATODEMANDA  = "HIIN_PCDESCONTOCONTRATODEMA";
		public static final String TOMBAMENTO  = "HIIN_NNTOMBAMENTO";
		public static final String LEITURACAMPO  = "HIIN_NNLEITURACAMPO";
		public static final String IDANORMALIDADECAMPO  = "LTAN_IDANORMALIDADECAMPO";
		public static final String DATALEITURACAMPO  = "HIIN_DTLEITURACAMPO";
		public static final String LEITURAATUALFATURAMENTO  = "HIIN_NNLEITURAATUALFATURAMENTO";
		public static final String LEITURAATUALFATURAMENTOHELPER  = "HIIN_NNLEITURAATUALFATURAMENTOHELPER";
		public static final String LEITURAANTERIORDIGITADA  = "HIIN_NNLEITURAANTERIORDIGITADA";
		public static final String ULTIMAALTERACAO = "HIIN_TMULTIMAALTERCAO";
	}
	
	private void insertFromFile(ArrayList<String> obj){
		
		ImovelConta matricula = new ImovelConta();		
		matricula.setId(Util.verificarNuloInt(obj.get(1)));
		setMatricula(matricula);

		if(obj.get(4).length() != 0){
			setTipoMedicao(Integer.parseInt(obj.get(2)));
		}
		setNumeroHidrometro(obj.get(3));	
		if(obj.get(4).length() != 0){
			Date dataFormatada = Util.convertDateStrToDate1(obj.get(4));
			setDataInstalacaoHidrometro(dataFormatada);
		}
		if(obj.get(5).length() != 0){
			setNumDigitosLeituraHidrometro(Integer.parseInt(obj.get(5)));
		}
		if(obj.get(6).length() != 0){
			setLeituraAnteriorFaturamento(Integer.parseInt(obj.get(6)));
		}
		if(obj.get(7).length() != 0){
			Date dataFormatada = Util.convertDateStrToDate1(obj.get(7));
			setDataLeituraAnterior(dataFormatada);		
		}
		if(obj.get(8).length() != 0){
			setCodigoSituacaoLeituraAnterior(Integer.parseInt(obj.get(8)));	
		}	
		if(obj.get(9).length() != 0){
			setLeituraLimiteInferior(Integer.parseInt(obj.get(9)));		
		}
		if(obj.get(10).length() != 0){
			setLeituraLimiteSuperior(Integer.parseInt(obj.get(10)));	
		}	
		if(obj.get(11).length() != 0){
			setConsumoMedio(Integer.parseInt(obj.get(11)));	
		}
		setLocalInstalacao(obj.get(12));		
		if(obj.get(13).length() != 0){
			setLeituraAnteriorInformada(Integer.parseInt(obj.get(13)));	
		}	
		if(obj.get(14).length() != 0){
			setTipoRateio(Integer.parseInt(obj.get(14)));	
		}
		if(obj.get(15).length() != 0){
			setLeituraHidrometoInstalada(Integer.parseInt(obj.get(15)));	
		}
		if(obj.get(16).length() != 0){	
			setIndcParalizacaoLeitura(Integer.parseInt(obj.get(16)));	
		}	
				
		if(obj.get(17).length() != 0){
			setConsumoMinimoContratadoContratoDemanda(Integer.parseInt(obj.get(17)));	
		}
		if(obj.get(18).length() != 0){		
			setPercentualDescontoContratoDemanda(Integer.parseInt(obj.get(18)));
		}	
		if(obj.get(19).length() != 0){	
			setTombamento(obj.get(19));
		}	
		
		setUltimaAlteracao(Util.getCurrentDateTime());			
	}

	public String getNomeTabela(){
		return "hidrometro_instalado";
	}
	
	public final class HidrometrosInstaladosTipos {
		public final String ID = " INTEGER PRIMARY KEY AUTOINCREMENT";
		public final String MATRICULA = "  INTEGER  NOT NULL CONSTRAINT [FK1_HIDROMETRO_INSTALADO] REFERENCES [imovel_conta]([IMOV_ID]) ON DELETE RESTRICT ON UPDATE RESTRICT";
		public final String MEDICAOTIPO = " INTEGER  NOT NULL";
		public final String NUMEROHIDROMETRO  = " VARCHAR(11) NULL";
		public final String DATAINSTALACAOHIDROMETRO  = " INTEGER NOT NULL";
		public final String NUMERODIGITOSLEITURA  = " INTEGER  NULL";
		public final String LEITURAANORMALIDADETFATURAMENTO  = " INTEGER  NOT NULL";
		public final String DATALEITURAANORMALIDADETFATURAMENTO  = " INTEGER NOT NULL";
		public final String IDSITUACAOLEITURAANTERIOR  = " INTEGER  NULL";
		public final String LEITURALIMITEINFERIOR  = " INTEGER  NOT NULL";
		public final String LEITURALIMITESUPERIOR  = " INTEGER  NOT NULL";
		public final String CONSUMOMEDIO  = " INTEGER  NOT NULL";
		public final String DESCRICAOHIDRLOCALINSTALACAO  = " VARCHAR(20) NOT NULL";
		public final String LEITURAANTERIORINFORMADA  = " INTEGER  NULL";
		public final String RATEIOTIPO  = " INTEGER  NULL";
		public final String LEITURAINSTALACAOHIDROMETRO  = " INTEGER  NULL";
		public final String INDICADOPARALISARLEITURA  = " INTEGER  NOT NULL";
		public final String CONSUMOMINIMOCONTRATADO  = " INTEGER  NULL";
		public final String PERCDESCONTOCONTRATODEMANDA  = " INTEGER  NULL";
		public final String TOMBAMENTO  = " VARCHAR(10) NULL";
		public final String LEITURACAMPO  = " INTEGER NULL";
		public final String IDANORMALIDADECAMPO  = " INTEGER NULL";
		public final String DATALEITURACAMPO  = " INTEGER NULL";
		public final String LEITURAATUALFATURAMENTO  = " INTEGER NULL";
		public final String LEITURAATUALFATURAMENTOHELPER  = " INTEGER NULL";
		public final String LEITURAANTERIORDIGITADA  = " INTEGER NULL";
		public final String ULTIMAALTERACAO = " INTEGER NOT NULL";
		
		private String[] tipos = new String[] {ID,MATRICULA, MEDICAOTIPO,  NUMEROHIDROMETRO, DATAINSTALACAOHIDROMETRO,
	        NUMERODIGITOSLEITURA, LEITURAANORMALIDADETFATURAMENTO, DATALEITURAANORMALIDADETFATURAMENTO,
	        IDSITUACAOLEITURAANTERIOR, LEITURALIMITEINFERIOR, LEITURALIMITESUPERIOR,
	        CONSUMOMEDIO, DESCRICAOHIDRLOCALINSTALACAO, LEITURAANTERIORINFORMADA,
	        RATEIOTIPO, LEITURAINSTALACAOHIDROMETRO, INDICADOPARALISARLEITURA,
	        CONSUMOMINIMOCONTRATADO, PERCDESCONTOCONTRATODEMANDA,
	        TOMBAMENTO,LEITURACAMPO, IDANORMALIDADECAMPO, 
	        DATALEITURACAMPO,LEITURAATUALFATURAMENTO,LEITURAATUALFATURAMENTOHELPER,LEITURAANTERIORDIGITADA ,ULTIMAALTERACAO};
		;
		
		public String[] getTipos(){
			return tipos;
		}
	}
	
	public ContentValues preencherValues() {
		ContentValues values = new ContentValues();
		
		values.put(HidrometrosInstalados.CONSUMOMEDIO, getConsumoMedio());
		values.put(HidrometrosInstalados.CONSUMOMINIMOCONTRATADO, getConsumoMinimoContratadoContratoDemanda());
		if(getDataInstalacaoHidrometro() != null){
			values.put(HidrometrosInstalados.DATAINSTALACAOHIDROMETRO, getDataInstalacaoHidrometro().getTime());	
		}
		if(getDataLeituraAnterior() != null){
			values.put(HidrometrosInstalados.DATALEITURAANORMALIDADETFATURAMENTO, getDataLeituraAnterior().getTime());
		}
		if(getDataLeitura() != null){
			values.put(HidrometrosInstalados.DATALEITURACAMPO,getDataLeitura().getTime());
		}
		values.put(HidrometrosInstalados.DESCRICAOHIDRLOCALINSTALACAO, getLocalInstalacao());
		values.put(HidrometrosInstalados.IDANORMALIDADECAMPO, getAnormalidade());
		values.put(HidrometrosInstalados.IDSITUACAOLEITURAANTERIOR, getCodigoSituacaoLeituraAnterior());
		values.put(HidrometrosInstalados.INDICADOPARALISARLEITURA, getIndcParalizacaoLeitura());
		values.put(HidrometrosInstalados.LEITURAANTERIORFATURAMENTO, getLeituraAnteriorFaturamento());
		values.put(HidrometrosInstalados.LEITURAANTERIORINFORMADA, getLeituraAnteriorInformada());
		values.put(HidrometrosInstalados.LEITURAANTERIORDIGITADA, getLeituraAnteriorDigitada());
		values.put(HidrometrosInstalados.LEITURACAMPO, getLeitura());
		values.put(HidrometrosInstalados.LEITURAINSTALACAOHIDROMETRO, getLeituraHidrometoInstalada());
		values.put(HidrometrosInstalados.LEITURALIMITEINFERIOR, getLeituraLimiteInferior());
		values.put(HidrometrosInstalados.LEITURALIMITESUPERIOR, getLeituraLimiteSuperior());
		values.put(HidrometrosInstalados.MATRICULA, getMatricula().getId());
		values.put(HidrometrosInstalados.NUMERODIGITOSLEITURA, getNumDigitosLeituraHidrometro());
		values.put(HidrometrosInstalados.NUMEROHIDROMETRO, getNumeroHidrometro());
		values.put(HidrometrosInstalados.MEDICAOTIPO, getTipoMedicao());
		values.put(HidrometrosInstalados.PERCDESCONTOCONTRATODEMANDA, getPercentualDescontoContratoDemanda());
		values.put(HidrometrosInstalados.RATEIOTIPO, getTipoRateio());
		values.put(HidrometrosInstalados.TOMBAMENTO, getTombamento());
		
		values.put(HidrometrosInstalados.LEITURAATUALFATURAMENTO,getLeituraAtualFaturamento());
		values.put(HidrometrosInstalados.LEITURAATUALFATURAMENTOHELPER,getLeituraAtualFaturamentoHelper());
		
		values.put(HidrometrosInstalados.ULTIMAALTERACAO, (new Date()).getTime());
		return values;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<HidrometroInstalado> preencherObjetos(Cursor cursor) {		
			
		int codigo = cursor.getColumnIndex(HidrometrosInstalados.ID);
		int consumoMedio = cursor.getColumnIndex(HidrometrosInstalados.CONSUMOMEDIO);
		int consumoMinimo = cursor.getColumnIndex(HidrometrosInstalados.CONSUMOMINIMOCONTRATADO);
		int dataInstalacaoHidrometro = cursor.getColumnIndex(HidrometrosInstalados.DATAINSTALACAOHIDROMETRO);
		int dataLeituraAnormalidade = cursor.getColumnIndex(HidrometrosInstalados.DATALEITURAANORMALIDADETFATURAMENTO);
		int dataLeituraCampo = cursor.getColumnIndex(HidrometrosInstalados.DATALEITURACAMPO);
		int descricaoHidrInstalacao = cursor.getColumnIndex(HidrometrosInstalados.DESCRICAOHIDRLOCALINSTALACAO);
		int idAnormalidadeCampo = cursor.getColumnIndex(HidrometrosInstalados.IDANORMALIDADECAMPO);
		int idSituacaoLeituraAnterior = cursor.getColumnIndex(HidrometrosInstalados.IDSITUACAOLEITURAANTERIOR);
		int indicadorParalisarLeitura = cursor.getColumnIndex(HidrometrosInstalados.INDICADOPARALISARLEITURA);
		int leituraAnteriorFaturamento = cursor.getColumnIndex(HidrometrosInstalados.LEITURAANTERIORFATURAMENTO);
		int leituraCampo = cursor.getColumnIndex(HidrometrosInstalados.LEITURACAMPO);
		int leituraAnteriorInformada = cursor.getColumnIndex(HidrometrosInstalados.LEITURAANTERIORINFORMADA);
		int leituraAnteriorDigitada = cursor.getColumnIndex(HidrometrosInstalados.LEITURAANTERIORDIGITADA);
		int leituraInstalacaoHidrometro = cursor.getColumnIndex(HidrometrosInstalados.LEITURAINSTALACAOHIDROMETRO);
		int leituraInferior = cursor.getColumnIndex(HidrometrosInstalados.LEITURALIMITEINFERIOR);
		int leituraSuperior = cursor.getColumnIndex(HidrometrosInstalados.LEITURALIMITESUPERIOR);
		int matricula = cursor.getColumnIndex(HidrometrosInstalados.MATRICULA);
		int medicaoTipo = cursor.getColumnIndex(HidrometrosInstalados.MEDICAOTIPO);
		int digitosLeitura = cursor.getColumnIndex(HidrometrosInstalados.NUMERODIGITOSLEITURA);
		int hidrometro = cursor.getColumnIndex(HidrometrosInstalados.NUMEROHIDROMETRO);
		int percentualDescontoContrato = cursor.getColumnIndex(HidrometrosInstalados.PERCDESCONTOCONTRATODEMANDA);
		int rateioTipo = cursor.getColumnIndex(HidrometrosInstalados.RATEIOTIPO);
		int tombamento = cursor.getColumnIndex(HidrometrosInstalados.TOMBAMENTO);
		int leituraAtualFaturamento = cursor.getColumnIndex(HidrometrosInstalados.LEITURAATUALFATURAMENTO);
		int leituraAtualFaturamentoHelper = cursor.getColumnIndex(HidrometrosInstalados.LEITURAATUALFATURAMENTOHELPER);
		int ultimaAlteracao = cursor.getColumnIndex(HidrometrosInstalados.ULTIMAALTERACAO);
		
		ArrayList<HidrometroInstalado> hidrometrosInstalados = new ArrayList<HidrometroInstalado>();
		
		do {	
			HidrometroInstalado hidrometroInstalado = new HidrometroInstalado();
			ImovelConta objImovelConta;
			try {
				objImovelConta = (ImovelConta)RepositorioBasico.getInstance()
						.pesquisarPorId(cursor.getInt(matricula), new ImovelConta());
				hidrometroInstalado.setMatricula(objImovelConta);
			} catch (RepositorioException e) {
				Log.e( ConstantesSistema.CATEGORIA , e.getMessage());
				e.printStackTrace();
			}
			hidrometroInstalado.setId(cursor.getInt(codigo));
			hidrometroInstalado.setConsumoMedio(Util.getIntBanco(cursor, HidrometrosInstalados.CONSUMOMEDIO, consumoMedio));
			hidrometroInstalado.setConsumoMinimoContratadoContratoDemanda(Util.getIntBanco(cursor, HidrometrosInstalados.CONSUMOMINIMOCONTRATADO, consumoMinimo));
			
			hidrometroInstalado.setDataInstalacaoHidrometro
				(Util.getDataBanco(cursor, HidrometrosInstalados.DATAINSTALACAOHIDROMETRO, dataInstalacaoHidrometro));
			
			hidrometroInstalado.setDataLeituraAnterior
				(Util.getDataBanco(cursor, HidrometrosInstalados.DATALEITURAANORMALIDADETFATURAMENTO, dataLeituraAnormalidade));
			
			hidrometroInstalado.setDataLeitura
				(Util.getDataBanco(cursor, HidrometrosInstalados.DATALEITURACAMPO, dataLeituraCampo));
			
			hidrometroInstalado.setLocalInstalacao(cursor.getString(descricaoHidrInstalacao));
			hidrometroInstalado.setAnormalidade(Util.getIntBanco(cursor, HidrometrosInstalados.IDANORMALIDADECAMPO, idAnormalidadeCampo));
			hidrometroInstalado.setCodigoSituacaoLeituraAnterior(Util.getIntBanco(cursor, HidrometrosInstalados.IDSITUACAOLEITURAANTERIOR, idSituacaoLeituraAnterior));
			hidrometroInstalado.setIndcParalizacaoLeitura(Util.getIntBanco(cursor, HidrometrosInstalados.INDICADOPARALISARLEITURA, indicadorParalisarLeitura));
			hidrometroInstalado.setLeitura(Util.getIntBanco(cursor, HidrometrosInstalados.LEITURACAMPO, leituraCampo));
			hidrometroInstalado.setLeituraAnteriorInformada(Util.getIntBanco(cursor, HidrometrosInstalados.LEITURAANTERIORINFORMADA, leituraAnteriorInformada));
			hidrometroInstalado.setLeituraHidrometoInstalada(Util.getIntBanco(cursor, HidrometrosInstalados.LEITURAINSTALACAOHIDROMETRO, leituraInstalacaoHidrometro));
			hidrometroInstalado.setLeituraLimiteInferior(Util.getIntBanco(cursor, HidrometrosInstalados.LEITURALIMITEINFERIOR, leituraInferior));
			hidrometroInstalado.setLeituraLimiteSuperior(Util.getIntBanco(cursor, HidrometrosInstalados.LEITURALIMITESUPERIOR, leituraSuperior));
			hidrometroInstalado.setTipoMedicao(Util.getIntBanco(cursor, HidrometrosInstalados.MEDICAOTIPO, medicaoTipo));
			hidrometroInstalado.setNumDigitosLeituraHidrometro(Util.getIntBanco(cursor, HidrometrosInstalados.NUMERODIGITOSLEITURA, digitosLeitura));
			hidrometroInstalado.setNumeroHidrometro(cursor.getString(hidrometro));
			hidrometroInstalado.setPercentualDescontoContratoDemanda(Util.getIntBanco(cursor, HidrometrosInstalados.PERCDESCONTOCONTRATODEMANDA, percentualDescontoContrato));
			hidrometroInstalado.setTipoRateio(Util.getIntBanco(cursor, HidrometrosInstalados.RATEIOTIPO, rateioTipo));
			hidrometroInstalado.setTombamento(cursor.getString(tombamento));
			hidrometroInstalado.setLeituraAnteriorFaturamento(Util.getIntBanco(cursor, HidrometrosInstalados.LEITURAANTERIORFATURAMENTO, leituraAnteriorFaturamento));
			hidrometroInstalado.setLeituraAtualFaturamento(Util.getIntBanco(cursor, HidrometrosInstalados.LEITURAATUALFATURAMENTO, leituraAtualFaturamento));
			hidrometroInstalado.setLeituraAtualFaturamentoHelper(Util.getIntBanco(cursor, HidrometrosInstalados.LEITURAATUALFATURAMENTOHELPER, leituraAtualFaturamentoHelper));
			hidrometroInstalado.setLeituraAnteriorDigitada(Util.getIntBanco(cursor, HidrometrosInstalados.LEITURAANTERIORDIGITADA, leituraAnteriorDigitada));
			
			hidrometroInstalado.setUltimaAlteracao(cursor.getLong(ultimaAlteracao));
			
			
			hidrometrosInstalados.add(hidrometroInstalado);
			
			
		} while (cursor.moveToNext());
		
		return hidrometrosInstalados;
	}

	public Integer getLeituraAtualFaturamentoHelper() {
		return leituraAtualFaturamentoHelper;
	}

	public void setLeituraAtualFaturamentoHelper(
			Integer leituraAtualFaturamentoHelper) {
		this.leituraAtualFaturamentoHelper = leituraAtualFaturamentoHelper;
	}

}
