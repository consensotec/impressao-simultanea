
package com.br.ipad.isc.fachada;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.br.ipad.isc.bean.CategoriaSubcategoria;
import com.br.ipad.isc.bean.ConsumoAnormalidadeAcao;
import com.br.ipad.isc.bean.ConsumoAnteriores;
import com.br.ipad.isc.bean.ConsumoHistorico;
import com.br.ipad.isc.bean.ContaCategoria;
import com.br.ipad.isc.bean.ContaCategoriaConsumoFaixa;
import com.br.ipad.isc.bean.ContaDebito;
import com.br.ipad.isc.bean.ContaImposto;
import com.br.ipad.isc.bean.CreditoRealizado;
import com.br.ipad.isc.bean.DebitoCobrado;
import com.br.ipad.isc.bean.Foto;
import com.br.ipad.isc.bean.HidrometroInstalado;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.bean.ImovelRevisitar;
import com.br.ipad.isc.bean.LeituraAnormalidade;
import com.br.ipad.isc.bean.ObjetoBasico;
import com.br.ipad.isc.bean.QualidadeAgua;
import com.br.ipad.isc.bean.SistemaParametros;
import com.br.ipad.isc.controladores.ControladorAlertaValidarErro;
import com.br.ipad.isc.controladores.ControladorAlertaValidarFoto;
import com.br.ipad.isc.controladores.ControladorAlertaValidarMensagemConexao;
import com.br.ipad.isc.controladores.ControladorBasico;
import com.br.ipad.isc.controladores.ControladorCategoriaSubcategoria;
import com.br.ipad.isc.controladores.ControladorConsumoAnormalidadeAcao;
import com.br.ipad.isc.controladores.ControladorConsumoAnteriores;
import com.br.ipad.isc.controladores.ControladorConsumoHistorico;
import com.br.ipad.isc.controladores.ControladorContaCategoria;
import com.br.ipad.isc.controladores.ControladorContaCategoriaConsumoFaixa;
import com.br.ipad.isc.controladores.ControladorContaDebito;
import com.br.ipad.isc.controladores.ControladorContaImposto;
import com.br.ipad.isc.controladores.ControladorContaOracle;
import com.br.ipad.isc.controladores.ControladorContaPostgres;
import com.br.ipad.isc.controladores.ControladorCreditoRealizado;
import com.br.ipad.isc.controladores.ControladorDebitoCobrado;
import com.br.ipad.isc.controladores.ControladorFoto;
import com.br.ipad.isc.controladores.ControladorHidrometroInstalado;
import com.br.ipad.isc.controladores.ControladorImovelConta;
import com.br.ipad.isc.controladores.ControladorImovelRevisitar;
import com.br.ipad.isc.controladores.ControladorImpressao;
import com.br.ipad.isc.controladores.ControladorLeituraAnormalidade;
import com.br.ipad.isc.controladores.ControladorLogFinalizacao;
import com.br.ipad.isc.controladores.ControladorQualidadeAgua;
import com.br.ipad.isc.controladores.ControladorRateioImovelCondominio;
import com.br.ipad.isc.controladores.ControladorSistemaParametros;
import com.br.ipad.isc.controladores.IControladorAlertaValidarMensagemConexao;
import com.br.ipad.isc.controladores.IControladorBasico;
import com.br.ipad.isc.controladores.IControladorCategoriaSubcategoria;
import com.br.ipad.isc.controladores.IControladorConsumoAnormalidadeAcao;
import com.br.ipad.isc.controladores.IControladorConsumoAnteriores;
import com.br.ipad.isc.controladores.IControladorConsumoHistorico;
import com.br.ipad.isc.controladores.IControladorConta;
import com.br.ipad.isc.controladores.IControladorContaCategoria;
import com.br.ipad.isc.controladores.IControladorContaCategoriaConsumoFaixa;
import com.br.ipad.isc.controladores.IControladorContaDebito;
import com.br.ipad.isc.controladores.IControladorContaImposto;
import com.br.ipad.isc.controladores.IControladorCreditoRealizado;
import com.br.ipad.isc.controladores.IControladorDebitoCobrado;
import com.br.ipad.isc.controladores.IControladorFoto;
import com.br.ipad.isc.controladores.IControladorHidrometroInstalado;
import com.br.ipad.isc.controladores.IControladorImovelConta;
import com.br.ipad.isc.controladores.IControladorImovelRevisitar;
import com.br.ipad.isc.controladores.IControladorImpressao;
import com.br.ipad.isc.controladores.IControladorLeituraAnormalidade;
import com.br.ipad.isc.controladores.IControladorLogFinalizacao;
import com.br.ipad.isc.controladores.IControladorQualidadeAgua;
import com.br.ipad.isc.controladores.IControladorSistemaParametros;
import com.br.ipad.isc.excecoes.ControladorException;
import com.br.ipad.isc.excecoes.FachadaException;
import com.br.ipad.isc.excecoes.NegocioException;
import com.br.ipad.isc.gui.TabsActivity;
import com.br.ipad.isc.util.ConstantesSistema;

public class Fachada {
	
	private static  Fachada instance;	
	
	private IControladorFoto controladorFoto;
	private IControladorCategoriaSubcategoria controladorCategoriaSubcategoria;
	private IControladorHidrometroInstalado controladorHidrometroInstalado;
	private IControladorConsumoAnteriores controladorConsumoAnteriores;
	//private IControladorConsumoAnormalidadeAcao controladorConsumoAnormalidadeAcao;
	private IControladorQualidadeAgua controladorQualidadeAgua;
	private IControladorSistemaParametros controladorSistemaParametros;
	private IControladorContaImposto controladorContaImposto;
	private IControladorCreditoRealizado controladorCreditoRealizado;
	private IControladorDebitoCobrado controladorDebitoCobrado;
	private IControladorConsumoHistorico controladorConsumoHistorico;
	private IControladorContaCategoria controladorContaCategoria;
	private IControladorContaDebito controladorContaDebito;
	//private IControladorImovel controladorImovel;
	private IControladorConta controladorConta;
	private IControladorImovelConta controladorImovelConta;

	private IControladorContaCategoriaConsumoFaixa controladorContaCategoriaConsumoFaixa;
	private IControladorLeituraAnormalidade controladorLeituraAnormalidade;
	//private IControladorConsumoTipo controladorConsumoTipo;
	//private ControladorAlertaValidarLeitura controladorAlertaValidarLeitura;
	private IControladorAlertaValidarMensagemConexao controladorAlertaValidarMensagemConexao;
	private ControladorAlertaValidarErro controladorAlertaValidarErro;
	private IControladorImpressao controladorImpressao;
	private IControladorImovelRevisitar controladorImovelRevisitar;
	//private IControladorSequencialRotaMarcacao controladorSequencialRotaMarcacao;
	private IControladorBasico controladorBasico;
	private ControladorAlertaValidarFoto controladorAlertaValidarFoto;
	private IControladorLogFinalizacao controladorLogFinalizacao;
	private IControladorConsumoAnormalidadeAcao controladorConsumoAnormalidadeAcao;

	private Fachada(){
		super();
	}
	
	private IControladorFoto getControladorFoto() {
		if(controladorFoto==null){
			controladorFoto = ControladorFoto.getInstance();
		}
		return controladorFoto;
	}
	
		
	private IControladorHidrometroInstalado getControladorHidrometroInstalado() {
		if(controladorHidrometroInstalado==null){
			controladorHidrometroInstalado = ControladorHidrometroInstalado.getInstance();
		}
		return controladorHidrometroInstalado;
	}
	
	private IControladorCategoriaSubcategoria getControladorCategoriaSubcategoria() {
		if(controladorCategoriaSubcategoria==null){
			controladorCategoriaSubcategoria = ( IControladorCategoriaSubcategoria ) ControladorCategoriaSubcategoria.getInstance();
		}
		return controladorCategoriaSubcategoria;
	}
	
	private IControladorConsumoAnteriores getControladorConsumoAnteriores() {
		if(controladorConsumoAnteriores==null){
			controladorConsumoAnteriores = ControladorConsumoAnteriores.getInstance();
		}
		return controladorConsumoAnteriores;
	}
		
	private IControladorQualidadeAgua getControladorQualidadeAgua() {
		if(controladorQualidadeAgua==null){
			controladorQualidadeAgua = ControladorQualidadeAgua.getInstance();
		}
		return controladorQualidadeAgua;
	}
	
	private IControladorSistemaParametros getControladorSistemaParametros() {
		if(controladorSistemaParametros==null){
			controladorSistemaParametros = ControladorSistemaParametros.getInstance();
		}
		return controladorSistemaParametros;
	}
	
	private IControladorContaImposto getControladorContaImposto() {
		if(controladorContaImposto==null){
			controladorContaImposto = ControladorContaImposto.getInstance();
		}
		return controladorContaImposto;
	}
	
	private IControladorCreditoRealizado getControladorCreditoRealizado() {
		if(controladorCreditoRealizado==null){
			controladorCreditoRealizado = ControladorCreditoRealizado.getInstance();
		}
		return controladorCreditoRealizado;
	}
	
	private IControladorDebitoCobrado getControladorDebitoCobrado() {
		if(controladorDebitoCobrado==null){
			controladorDebitoCobrado = ControladorDebitoCobrado.getInstance();
		}
		return controladorDebitoCobrado;
	}
	
	private IControladorConsumoHistorico getControladorConsumoHistorico() {
		if(controladorConsumoHistorico==null){
			controladorConsumoHistorico = ControladorConsumoHistorico.getInstance();
		}
		return controladorConsumoHistorico;
	}
	
	private IControladorContaCategoria getControladorContaCategoria() {
		if(controladorContaCategoria==null){
			controladorContaCategoria = ControladorContaCategoria.getInstance();
		}
		return controladorContaCategoria;
	}
	
	private IControladorContaDebito getControladorContaDebito() {
		if(controladorContaDebito==null){
			controladorContaDebito = ControladorContaDebito.getInstance();
		}
		return controladorContaDebito;
	}
	
	public IControladorImovelConta getControladorImovelConta() {
		if(controladorImovelConta==null){
			controladorImovelConta = ControladorImovelConta.getInstance();
		}
		return controladorImovelConta;
	}
	
	public IControladorLeituraAnormalidade getControladorLeituraAnormalidade() {
		if(controladorLeituraAnormalidade==null){
			controladorLeituraAnormalidade = ControladorLeituraAnormalidade.getInstance();
		}
		return controladorLeituraAnormalidade;
	}
	
	private IControladorImovelRevisitar getControladorImovelRevisitar() {		
		if(controladorImovelRevisitar==null){
			controladorImovelRevisitar = ControladorImovelRevisitar.getInstance();
		}
				
		return controladorImovelRevisitar;
	}
	
	private IControladorConta getControladorConta() {		
		String empresa = SistemaParametros.getInstancia().getCodigoEmpresaFebraban();
		if(empresa.equals(ConstantesSistema.CODIGO_FEBRABAN_COMPESA)){
			controladorConta = ControladorContaOracle.getInstance();
		} else {
			controladorConta = ControladorContaPostgres.getInstance();
		}
		
		return controladorConta;
	}	
	
	private IControladorContaCategoriaConsumoFaixa getControladorContaCategoriaConsumoFaixa() {
		if(controladorContaCategoriaConsumoFaixa==null){
			controladorContaCategoriaConsumoFaixa = ControladorContaCategoriaConsumoFaixa.getInstance();
		}
		return controladorContaCategoriaConsumoFaixa;
	}
	
	private IControladorImpressao getControladorImpressao() {
		if(controladorImpressao==null){
			controladorImpressao = ControladorImpressao.getInstance();
		}
		return controladorImpressao;
	}
	
	private IControladorAlertaValidarMensagemConexao getControladorAlertaValidarMensagemConexao(ImovelConta imovel, byte caminhoSucesso, byte caminhoErro,
			boolean enviou,int tipoFinalizacao,String nomeArquivo) {
		
		controladorAlertaValidarMensagemConexao = new ControladorAlertaValidarMensagemConexao( imovel,caminhoSucesso, caminhoErro,enviou,tipoFinalizacao,nomeArquivo);
		
		return controladorAlertaValidarMensagemConexao;
	}
	
	private IControladorAlertaValidarMensagemConexao getControladorAlertaValidarMensagemConexao(ImovelConta imovel, byte caminhoSucesso, byte caminhoErro,
			boolean enviou,int tipoFinalizacao) {
		
		controladorAlertaValidarMensagemConexao = new ControladorAlertaValidarMensagemConexao( imovel,caminhoSucesso, caminhoErro,enviou,tipoFinalizacao);
		
		return controladorAlertaValidarMensagemConexao;
	}

	private IControladorLogFinalizacao getControladorLogFinalizacao() {
		if(controladorLogFinalizacao==null){
			controladorLogFinalizacao = ControladorLogFinalizacao.getInstance();
		}
		return controladorLogFinalizacao;
	}

	private IControladorConsumoAnormalidadeAcao getControladorConsumoAnormalidadeAcao() {
		if(controladorConsumoAnormalidadeAcao==null){
			controladorConsumoAnormalidadeAcao = ControladorConsumoAnormalidadeAcao.getInstance();
		}
		return controladorConsumoAnormalidadeAcao;
	}

	/**
	 * Padr�o de projeto SINGLETON
	 * @author Bruno Barros
	 * @return Instancia da Facade
	 */
	public static Fachada getInstance() {
		if (instance == null) {
			instance = new Fachada();
		}
		return instance;
	}
	
	public static void setContext(Context ctx){
		ControladorBasico.getInstance().setContext(ctx);
	}
	
	/**
	 * @author Arthur Carvalho
	 * 
	 * @param foto
	 * @throws FachadaException
	 * @throws NegocioException
	 */
	public Foto buscarFotoTipo(Integer id, Integer fotoTipo, Integer medicaoTipo,Integer idLeituraAnormalidade,Integer idConsumoAnormalidade) throws FachadaException, NegocioException {
		try{
			return getControladorFoto().buscarFotoTipo(id, fotoTipo, medicaoTipo,idLeituraAnormalidade,idConsumoAnormalidade);
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}			
	}
	
	/**
	 * @author Arthur Carvalho
	 * 
	 * @param foto
	 * @throws FachadaException
	 * @throws NegocioException
	 */
	public ArrayList<Foto> buscarFotos(Integer idImovel, Integer medicaoTipo) throws FachadaException, NegocioException {
		try{
			return getControladorFoto().buscarFotos(idImovel, medicaoTipo);
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}			
	}
	
	/**
	 * @author Carlos Chaves
	 * @date 19/07/2012
	 * @param Integer imovelId
	 * @throws FachadaException
	 * @return ArrayList<CategoriaSubcategoria>
	 */
	public ArrayList<CategoriaSubcategoria> buscarCategoriaSubcategoriaPorImovelId(Integer imovelId) 
			throws FachadaException{
		try{
			return getControladorCategoriaSubcategoria().buscarCategoriaSubcategoriaPorImovelId(imovelId);
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}			
	}
	
	/**
	 * @author Carlos Chaves
	 * @date 19/07/2012
	 * @param Integer imovelId
	 * @throws FachadaException
	 * @return String tombamento
	 */
	public String obterTombamento(ImovelConta imovelConta, int tipoMedicao)
			throws FachadaException{
		try{
			return getControladorHidrometroInstalado().obterTombamento(imovelConta, tipoMedicao);
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}			
	}
	
	/**
	 * @author Carlos Chaves
	 * @date 19/07/2012
	 * @param Integer imovelId
	 * @throws FachadaException
	 * @return List<ConsumoAnteriores>
	 */
	public List<ConsumoAnteriores> buscarConsumoAnterioresPorImovelId(Integer imovelId) 
			throws FachadaException {
		try{
			return getControladorConsumoAnteriores().buscarConsumoAnterioresPorImovelId(imovelId);
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}			
	}
	
	/**
	 * @author Carlos Chaves
	 * @date 19/07/2012
	 * @throws FachadaException
	 * @return SistemaParametros
	 */
	public SistemaParametros buscarSistemaParametro() throws FachadaException {
		try{
			return getControladorSistemaParametros().buscarSistemaParametro();
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}			
	}
	
	/**
	 * @author Carlos Chaves
	 * @date 19/07/2012
	 * @param Integer imovelId
	 * @throws FachadaException
	 * @return Collection<ContaImposto>
	 */
	public Collection<ContaImposto> buscarContaImpostoPorImovelId(Integer imovelId) throws FachadaException {
		try{
			return getControladorContaImposto().buscarContaImpostoPorImovelId(imovelId);
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}			
	}
	
	/**
	 * @author Carlos Chaves
	 * @date 19/07/2012
	 * @param Integer imovelId
	 * @throws FachadaException
	 * @return Collection<CreditoRealizado>
	 */
	public Collection<CreditoRealizado> buscarCreditoRealizadoPorImovelId(Integer imovelId) throws FachadaException {
		try{
			return getControladorCreditoRealizado().buscarCreditoRealizadoPorImovelId(imovelId);
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}			
	}
	
	/**
	 * @author Carlos Chaves
	 * @date 19/07/2012
	 * @param Integer imovelId
	 * @throws FachadaException
	 * @return Collection<DebitoCobrado>
	 */
	public Collection<DebitoCobrado> buscarDebitoCobradoPorImovelId(Integer imovelId) throws FachadaException {
		try{
			return getControladorDebitoCobrado().buscarDebitoCobradoPorImovelId(imovelId);
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}			
	}
	
	/**
	 * @author Carlos Chaves
	 * @date 19/07/2012
	 * @param Integer imovelId
	 * @param Integer tipoMedicao
	 * @throws FachadaException
	 * @return HidrometroInstalado
	 */
	public HidrometroInstalado buscarHidrometroInstaladoPorImovelTipoMedicao
								(Integer imovelId, Integer tipoMedicao) throws FachadaException {
		try{
			return getControladorHidrometroInstalado().buscarHidrometroInstaladoPorImovelTipoMedicao(imovelId, tipoMedicao);
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}			
	}
	
	/**
	 * @author Carlos Chaves
	 * @date 19/07/2012
	 * @param Integer imovelId
	 * @param Integer tipoLigacao
	 * @throws FachadaException
	 * @return ConsumoHistorico
	 */
	public ConsumoHistorico buscarConsumoHistoricoPorImovelIdTipoLigacao
								(Integer imovelId, Integer tipoLigacao) throws FachadaException {
		try{		
			return getControladorConsumoHistorico().buscarConsumoHistoricoPorImovelIdLigacaoTipo(imovelId, tipoLigacao);
		} catch ( ControladorException e ){          
			throw new FachadaException( e.getMessage() );
		}			
	}
	
	/**
	 * @author Carlos Chaves
	 * @date 19/07/2012
	 * @param Integer categoriaSubcategoriaId
	 * @param Integer tipoMedicao
	 * @throws FachadaException
	 * @return ContaCategoria
	 */
	public ContaCategoria buscarContaCategoriaPorCategoriaSubcategoriaId
								(Integer categoriaSubcategoriaId, Integer tipoMedicao) throws FachadaException {
		try{		
			return getControladorContaCategoria().buscarContaCategoriaPorCategoriaSubcategoriaId(categoriaSubcategoriaId, tipoMedicao);
		} catch ( ControladorException e ){          
			throw new FachadaException( e.getMessage() );
		}			
	}
	
	/**
	 * @author Carlos Chaves
	 * @date 19/07/2012
	 * @param Integer imovelId
	 * @throws FachadaException
	 * @return Double
	 */
	public Double obterValorCreditoTotal (Integer imovelId) throws FachadaException {
		try{		
			return getControladorCreditoRealizado().obterValorCreditoTotal(imovelId);
		} catch ( ControladorException e ){          
			throw new FachadaException( e.getMessage() );
		}			
	}
	
	/**
	 * @author Carlos Chaves
	 * @date 19/07/2012
	 * @param Integer imovelId
	 * @throws FachadaException
	 * @return ArrayList<ContaDebito>
	 */
	public ArrayList<ContaDebito> buscarContasDebitosPorIdImovel(Integer imovelId) throws FachadaException {
		try{
			return getControladorContaDebito().buscarContasDebitosPorIdImovel(imovelId);
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}			
	}
	
	/**
	 * @author Carlos Chaves
	 * @date 19/07/2012
	 * @param Integer imovelId
	 * @throws FachadaException
	 * @return int
	 */
	public int getControladorCategoriaSubcategoria(Integer imovelId) throws FachadaException {
		try{
			return getControladorCategoriaSubcategoria().obterQuantidadeEconomiasTotal(imovelId);
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}			
	}
	
	
	/**
	 * @author Carlos Chaves
	 * @date 19/07/2012
	 * @param Integer idContaCategoria
	 * @throws FachadaException
	 * @return ArrayList<ContaCategoriaConsumoFaixa>
	 */
	public ArrayList<ContaCategoriaConsumoFaixa> buscarContasCategoriasConsumosFaixasPorPorContaCategoriaId(Integer idContaCategoria) throws FachadaException {
		try{
			return getControladorContaCategoriaConsumoFaixa().buscarContasCategoriasConsumosFaixasPorPorContaCategoriaId(idContaCategoria);
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}			
	}
	
	/**
	 * @author Carlos Chaves
	 * @date 20/07/2012
	 * @param Integer idSintuacaoLigAgua
	 * @throws FachadaException
	 * @return String
	 */
	public String getDescricaoSitLigacaoAgua(Integer idSintuacaoLigAgua) throws FachadaException {
		
		return getControladorImovelConta().getDescricaoSitLigacaoAgua(idSintuacaoLigAgua);
				
	}
	
	/**
	 * @author Carlos Chaves
	 * @date 20/07/2012
	 * @param Integer idSituacaoLigEsgoto
	 * @throws FachadaException
	 * @return String
	 */
	public String getDescricaoSitLigacaoEsgoto(Integer idSituacaoLigEsgoto) throws FachadaException {
		
		return getControladorImovelConta().getDescricaoSitLigacaoEsgoto(idSituacaoLigEsgoto);
				
	}
	

	/**
	 * @author Carlos Chaves
	 * @date 20/07/2012
	 * @param Integer imovelId
	 * @throws FachadaException
	 * @return double
	 */
	public double obterValorImpostoTotal(Integer imovelId) throws FachadaException {
	
		try {
			return getControladorContaImposto().obterValorImpostoTotal(imovelId);
		} catch (ControladorException e) {
			throw new FachadaException( e.getMessage() );
		}
	}
	
	/**
	 * @author Carlos Chaves
	 * @date 20/07/2012
	 * @param Integer imovelId
	 * @param Integer tipoMedicao
	 * @throws FachadaException
	 * @return double
	 */
	public double obterValorTotal(Integer imovelId, Integer tipoMedicao) throws FachadaException {
	
		try {
			return getControladorContaCategoria().obterValorTotal(imovelId, tipoMedicao);
		} catch (ControladorException e) {
			throw new FachadaException( e.getMessage() );
		}
	}
	
	/**
	 * @author Carlos Chaves
	 * @date 20/07/2012
	 * @param Integer imovelId
	 * @throws FachadaException
	 * @return double
	 */
	public double obterValorContaSemCreditos(Integer imovelId) throws FachadaException {
	
		try {
			return getControladorImovelConta().obterValorContaSemCreditos(imovelId);
		} catch (ControladorException e) {
			throw new FachadaException( e.getMessage() );
		}
	}
	

	
	/**
	 * @author Carlos Chaves
	 * @date 25/07/2012
	 * @param HidrometroInstalado hidrometroInstalado
	 * @return double
	 */
	public int obterLeituraAnterior(HidrometroInstalado hidrometroInstalado) throws FachadaException {
		
		return getControladorConta().obterLeituraAnterior(hidrometroInstalado);
		
	}
	
	/**
	 * @author Carlos Chaves
	 * @date 25/07/2012
	 * @param Integer imovelId
	 * @throws FachadaException
	 * @return double
	 */
	public double obterValorContaSemImposto(Integer imovelId) throws FachadaException {
		try {
			return getControladorImovelConta().obterValorContaSemImposto(imovelId);
		} catch (ControladorException e) {
			throw new FachadaException( e.getMessage() );
		}
	}
	
	/**
	 * @author Carlos Chaves
	 * @date 25/07/2012
	 * @param Integer imovelId
	 * @throws FachadaException
	 * @return double
	 */
	public double obterValorDebitoTotal(Integer imovelId) throws FachadaException {
		try {
			return getControladorDebitoCobrado().obterValorDebitoTotal(imovelId);
		} catch (ControladorException e) {
			throw new FachadaException( e.getMessage() );
		}
	}
	
	/**
	 * @author Carlos Chaves
	 * @date 25/07/2012
	 * @throws FachadaException
	 * @return ArrayList<ImovelConta>
	 */
	public ArrayList<ImovelConta> buscarImovelContas() throws FachadaException {
		try{
			return getControladorImovelConta().buscarImovelContas();
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}			
	}
	
	/**
	 * @author Carlos Chaves
	 * @date 26/07/2012
	 * @param ImovelConta imovel
	 * @throws FachadaException
	 * @return boolean 
	 */
	public boolean enviarAoCalcular(ImovelConta imovel) throws FachadaException {
		try{
			return getControladorImovelConta().enviarAoCalcular(imovel);
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}		
	}

	/**
	 * @author Fernanda Almeida
	 * @param proximo,posicao 
	 * @date 30/07/2012
	 * @throws FachadaException
	 */
	public ImovelConta buscarImovelContaPosicao(Integer posicao, Boolean proximo){
		try {
			return getControladorImovelConta().buscarImovelContaPosicao(posicao,proximo);
		} catch (ControladorException e) {
			throw new FachadaException( e.getMessage() );
		}
	}

	/**
	 * @author Amelia Pessoa
	 * @date 25/07/2012
	 * [UC0740] Calcular Consumo no Dispositivo M�vel
	 */
	public boolean calcularConta(ImovelConta imovel,Boolean imprimir,Boolean proximo) {
		try {
			 return getControladorConta().calcularConta(imovel,imprimir,proximo);
		} catch (ControladorException e) {
			throw new FachadaException( e.getMessage() );
		}
	}
	
	/**
	 * @author Amelia Pessoa
	 * @date 25/07/2012
	 * Instancia nova Thread e vai fazendo o c�lculo da conta
	 */
	public void calcularEmBackground(final ImovelConta imovel, final Boolean proximo) {		
		new Thread(new Runnable() {
			
			public void run() {
				calcularConta(imovel,false,proximo);
				
			}
		}).start();
	}
	

	/**
	 * @author Carlos Chaves
	 * @date 26/07/2012
	 * @throws FachadaException
	 * @return ArrayList<ImovelConta>
	 */
	public ArrayList<ImovelConta> buscarImovelContasLidos() throws FachadaException {

		try{
			return getControladorImovelConta().buscarImovelContasLidos();
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}
	}
	
	
	/**
	 * @author Carlos Chaves
	 * @date 26/07/2012
	 * @param Integer idLocalidade
	 * @throws FachadaException
	 * @return QualidadeAgua
	 */
	public QualidadeAgua buscarQualidadeAguaPorLocalidade(Integer idLocalidade) throws FachadaException {

		try{
			return getControladorQualidadeAgua().buscarQualidadeAguaPorLocalidade(idLocalidade);
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}
	}

	/**
	 * @author Carlos Chaves
	 * @date 26/07/2012
	 * @param Integer idLocalidade
	 * @param Integer idSetorComercial
	 * @throws FachadaException
	 * @return QualidadeAgua
	 */
	public QualidadeAgua buscarQualidadeAguaPorLocalidadeSetorComercial(Integer idLocalidade, Integer idSetorComercial) throws FachadaException {
		try{
			return getControladorQualidadeAgua().buscarQualidadeAguaPorLocalidadeSetorComercial(idLocalidade, idSetorComercial);
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}
	}
	
	/**
	 * @author Carlos Chaves
	 * @date 30/07/2012
	 * @param String inscricao
	 * @throws FachadaException
	 */
	public String formatarInscricao(String inscricao) throws FachadaException {
			return getControladorImovelConta().formatarInscricao(inscricao);
	}
	
	/**
	 * @author Fernanda Almeida
	 * @date 30/07/2012
	 * @param String inscricao, ImovelConta imovel
	 * @throws FachadaException
	 */
	public String formatarRota(String inscricao, ImovelConta imovel) throws FachadaException {
			return getControladorImovelConta().formatarRota(inscricao,imovel);
	}
	

	/**
	 * @author Fernanda Almeida
	 * @date 26/07/2012
	 * @throws FachadaException
	 * @return LeituraAnormalidade
	 */
	public LeituraAnormalidade buscarLeituraAnormalidadeImovel(Integer idImovel,Integer tipoLigacao) throws FachadaException {

		try{
			return getControladorLeituraAnormalidade().buscarLeituraAnormalidadeImovel(idImovel,tipoLigacao);
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}
	}
	
	/**
	 * @author S�vio Luiz
	 * @date 02/08/2012
	 * @throws FachadaException
	 * @return LeituraAnormalidade
	 */
	public boolean validarLeituraMensagem(HidrometroInstalado hidrometroInstalado,ImovelConta imovel,int idMensagem,boolean imprimir,boolean proximo) throws FachadaException {

		try{
			return getControladorHidrometroInstalado().validarLeituraMensagem(hidrometroInstalado,imovel,idMensagem,imprimir,proximo);
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}
	}
	
	/**
	 * @author Carlos Chaves
	 * @date 02/08/2012
	 * @param Integer imovelId
	 * @throws double
	 * @return boolean
	 */
	public double obterValorConta(Integer imovelId) throws FachadaException {
		
		try {
			return getControladorConta().obterValorConta(imovelId);
		} catch (ControladorException e) {
			throw new FachadaException( e.getMessage() );
		}
		
	}
	
	
	/**
	 * @author S�vio Luiz
	 * @date 06/08/2012
	 * @param Integer imovelId
	 * @throws double
	 * @return boolean
	 */
	public boolean verificarImpressaoConta(ImovelConta imovel, Context context,int idMensagem) throws FachadaException {
		
		try {
			return getControladorImpressao().verificarImpressaoConta(imovel, context,idMensagem, true);
		} catch (ControladorException e) {
			throw new FachadaException( e.getMessage() );
		}
		
	}
	
	/**
	 * @author Fernanda Almeida
	 * @date 05/08/2012
	 * @throws FachadaException
	 * @return SistemaParametros
	 */
	public boolean validaSenhaApagar(String senha) throws FachadaException {
		try{
			return getControladorSistemaParametros().validaSenhaApagar(senha);
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}			
	}
	
	/**
	 * @author Fernanda Almeida
	 * @date 05/08/2012
	 * @throws FachadaException
	 * @return ArrayList<ImovelConta> 
	 */
	public ArrayList<ImovelConta> buscarImovelContasNaoLidos() throws FachadaException {
		try{
			return getControladorImovelConta().buscarImovelContasNaoLidos();
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}			
	}

	public boolean validaSenhaAdm(String senha) throws FachadaException {
		try{
			return getControladorSistemaParametros().validaSenhaAdm(senha);
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}
	}
	
	/**
	 * @author Fernanda Almeida
	 * @date 08/08/2012
	 * @param String hidrometroNumero
	 * @return ImovelConta
	 * @throws FachadaException
	 
	 */
	public ImovelConta buscarImovelContaPorHidrometro(String hidrometroNumero) throws FachadaException {
		try{
			return getControladorImovelConta().buscarImovelContaPorHidrometro(hidrometroNumero);
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}
	}
	
	
	/**
	 * @author Fernanda Almeida
	 * @date 08/08/2012
	 * @param String hidrometroNumero
	 * @return ArrayList<ImovelConta>
	 * @throws FachadaException
	 
	 */
	public ArrayList<ImovelConta> buscarImovelContaPorQuadra(String numeroQuadra) throws FachadaException {
		try{
			return getControladorImovelConta().buscarImovelContaPorQuadra(numeroQuadra);
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}
	}
	
	/**
	 * Busca apenas leitura e anormalidade de hidrometro instalado
	 * @author Fernanda Almeida
	 * @date 08/08/2012
	 * @param Integer imovelId
	 * @param Integer tipoMedicao
	 * @return HidrometroInstalado
	 * @throws FachadaException
	 
	 */
	public HidrometroInstalado buscarLeituraHidrometroTipoMedicao(Integer imovelId, Integer tipoMedicao) throws FachadaException {
		try{
			return getControladorHidrometroInstalado().buscarLeituraHidrometroTipoMedicao(imovelId,tipoMedicao);
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}
	}

	
	/**
	 * M�todo identifica se o im�vel passado como par�metro pode ser impresso
	 * (Se o mesmo NAO for im�vel condom�nio OU, sendo im�vel condom�nio for o 
	 * �ltimo im�vel retorna true)
	 * 
	 * @author Amelia Pessoa
	 * @param imovel
	 * @return
	 * @throws ControladorException
	 */
	public boolean permiteImprimir(ImovelConta imovel) {
		try{
			return getControladorConta().permiteImprimir(imovel);
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}
	}

	/**
	 * @author Carlos Chaves
	 * @param posicao 
	 * @date 14/08/2012
	 * @throws FachadaException
	 */
	public ImovelConta buscarImovelContaPorPosicao(Integer posicao){
		try {
			return getControladorImovelConta().buscarImovelContaPorPosicao(posicao);
		} catch (ControladorException e) {
			throw new FachadaException( e.getMessage() );
		}
	}
	
	
	public void atualizarQntImoveis() {
		try{
			getControladorSistemaParametros().atualizarQntImoveis();
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}		
	}

	
	/**
	 * [UC0970] Efetuar Rateio de Consumo no Dispositivo Movel Metodo
	 * responsavel em efeturar a divisao da diferenca entre o consumo coletado
	 * no hidrometro macro e a soma dos hidrometros micro
	 * 
	 * [SB0002] Determinar Rateio de Agua
	 * 
	 * @date 26/11/2009
	 * @author Bruno Barros
	 * 
	 * @return Object[] 0, Boolean: Caso algum error tenha ocorrido, informamos
	 *         1, Boolean: Ainda existem imoveis a serem percorridos; 2,
	 *         Integer: Id do imovel condominio que ainda precisa ser percorrido
	 * 
	 * @param helper
	 *            Helper com os dados necessarios para execucao
	 */
	public ControladorRateioImovelCondominio efetuarRateio(ImovelConta imovelMacro, boolean completo) {
		try{
			return getControladorConta().efetuarRateio(imovelMacro, completo);
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}
	}
	
	/**
	 * Atualiza os atributos idImovelCondominio e qntImovelCondominio
	 * de SistemaParametros
	 * 
	 * @param imovelMacro
	 */
	public void atualizarDadosImovelMacro(ImovelConta imovel) {
		try{
			getControladorSistemaParametros().atualizarDadosImovelMacro(imovel);
		} catch (ControladorException e){
			throw new FachadaException( e.getMessage() );
		}		
	}
	
	/**
	 * @author Carlos Chaves
	 * @param  imovelId
	 * @date 20/08/2012
	 * @throws FachadaException
	 */
	public Integer obterQuantidadeEconomiasTotal(Integer imovelId){
		try {
			return getControladorCategoriaSubcategoria().obterQuantidadeEconomiasTotal(imovelId);
		} catch (ControladorException e) {
			throw new FachadaException( e.getMessage() );
		}
	}
	
	/**
	 * @author Carlos Chaves
	 * @param idImovelMacro 
	 * @date 20/08/2012
	 * @throws FachadaException
	 */
	public ImovelConta obterImovelAreaComum(Integer idImovelMacro){
		try {
			return getControladorImovelConta().obterImovelAreaComum(idImovelMacro);
		} catch (ControladorException e) {
			throw new FachadaException( e.getMessage() );
		}
	}
	
	/**
	 * @author Carlos Chaves
	 * @param  idImovelMacro
	 * @date 20/08/2012
	 * @throws FachadaException
	 */
	public Integer obterConsumoImoveisMicro(Integer idImovelMacro, Integer tipoLigacao){
		try {
			return getControladorConsumoHistorico().obterConsumoImoveisMicro(idImovelMacro,tipoLigacao);
		} catch (ControladorException e) {
			throw new FachadaException( e.getMessage() );
		}
	}
	
	/**
	 * Retorna o id do primeiro imovel micro n�o calculado.
	 * Se n�o houver nenhum, retorna null
	 * 
	 * @author Amelia Pessoa
	 *  
	 * @param idImovelMacro
	 * @return
	 */
	public Integer obterPosicaoImovelCondominioNaoCalculado(Integer idImovelMacro) {
		try {
			return getControladorImovelConta().obterPosicaoImovelCondominioNaoCalculado(idImovelMacro);
		} catch (ControladorException e) {
			throw new FachadaException( e.getMessage() );
		}
	}
	
	/**
	 * Imprime o extrato de macro de um imovel condiminio
	 * 
	 * @author Carlos Chaves
	 *  
	 * @param context, imovelMacro
	 * @return boolean
	 */
	public boolean imprimirExtratoMacro(Context context, ImovelConta imovelMacro){
		try {
			return getControladorImpressao().imprimirExtratoMacro(context, imovelMacro);
		} catch (ControladorException e) {
			throw new FachadaException( e.getMessage() );
		}
	}

	/**
	 * @author Fernanda Almeida
	 * @date 17/08/2012
	 */
	public ImovelRevisitar buscarImovelRevisitarPorImovel(Integer idImovel) {
		try{
			return getControladorImovelRevisitar().buscarImovelRevisitarPorImovel(idImovel);
		} catch (ControladorException e){
			throw new FachadaException( e.getMessage() );
		}		
	}
	
	/**
	 * @author Fernanda Almeida
	 * @date 17/08/2012
	 */
	public void setMatriculasRevisitar(String idsRevisitar) {
		try{
			getControladorImovelRevisitar().setMatriculasRevisitar(idsRevisitar);
		} catch (ControladorException e){
			throw new FachadaException( e.getMessage() );
		}		
	}

	public Integer obterQntDebitoCobradoPorImovelId(Integer imovelId) {
		try {
			return getControladorDebitoCobrado().obterQntDebitoCobradoPorImovelId(imovelId);
		} catch (ControladorException ex){
			throw new FachadaException( ex.getMessage() );
		}
	}
	
	public Integer obterQntContaImpostoPorImovelId(Integer imovelId) {
		try {
			return getControladorContaImposto().obterQntContaImpostoPorImovelId(imovelId);
		} catch (ControladorException ex){
			throw new FachadaException( ex.getMessage() );
		}
	}
	
	public Integer obterQntCreditoRealizadoPorImovelId(Integer imovelId) {
		try {
			return getControladorCreditoRealizado().obterQntCreditoRealizadoPorImovelId(imovelId);
		} catch (ControladorException ex){
			throw new FachadaException( ex.getMessage() );
		}
	}
	
	public Double obterTotalConsumoContasCategoriasConsumosFaixasPorPorContaCategoriaId(Integer idContaCategoria) {
		try {
			return getControladorContaCategoriaConsumoFaixa().
					obterTotalConsumoContasCategoriasConsumosFaixasPorPorContaCategoriaId(idContaCategoria);
		} catch (ControladorException ex){
			throw new FachadaException( ex.getMessage() );
		}
	}
	
	public Double obterTotalValorTarifaContasCategoriasConsumosFaixasPorPorContaCategoriaId(Integer idContaCategoria) {
		try {
			return getControladorContaCategoriaConsumoFaixa().
					obterTotalValorTarifaContasCategoriasConsumosFaixasPorPorContaCategoriaId(idContaCategoria);
		} catch (ControladorException ex){
			throw new FachadaException( ex.getMessage() );
		}
	}
	
	/**
	 * Verifica existencia de impressora configurada e
	 * retorna um booleano
	 * 
	 * @author Amelia Pessoa
	 * @return boolean
	 */
	public boolean verificarExistenciaImpressora(ImovelConta imovel){
		return getControladorImpressao().verificarExistenciaImpressora(imovel);
	}
	
	/**
	 * Retorna o id do �ltimo im�vel micro
	 * para o ImovelMacro passado como par�metro
	 * 
	 * @author Amelia Pessoa
	 * @param idImovelMacro
	 * @return
	 */
	public Integer obterIdUltimoImovelMicro(Integer idImovelMacro) {
		try {
			return getControladorImovelConta().obterIdUltimoImovelMicro(idImovelMacro);
		} catch (ControladorException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new FachadaException(ex.getMessage());
		}
	}

	/**
	 * Verifica se j� existe algum im�vel impresso para o
	 * im�vel macro passado como parametro 
	 * (Se todos foram impressos retorna false)
	 * 
	 * @author Amelia Pessoa
	 * @param id
	 * @return
	 */
	public boolean existeImovelImpresso(Integer idImovelMacro) {
		try {
			return getControladorImovelConta().existeImovelImpresso(idImovelMacro);
		} catch (ControladorException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new FachadaException(ex.getMessage());
		}
	}
	
	/**
	 * Retorna a quantidade de imoveis lido
	 * @author Carlos Chaves
	 * @return Integer
	 */
	public Integer obterQuantidadesImoveisLidos(){
		try {
			return getControladorImovelConta().obterQuantidadesImoveisLidos();
		} catch (ControladorException e) {
			throw new FachadaException( e.getMessage() );
		}
	}

	/**
	 * Retorna ImovelConta com base no sequencial passado como parametro
	 * @author Amelia Pessoa
	 * 
	 * @param sequencial
	 * @return ImovelConta
	 */
	public ImovelConta buscarImovelContaSequencial(int sequencial) {
		try {
			return getControladorImovelConta().buscarImovelContaSequencial(sequencial);
		} catch (ControladorException ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new FachadaException(ex.getMessage());
		}
	}

	
	public void atualizarArquivoCarregadoBD() {
		try{
			getControladorSistemaParametros().atualizarArquivoCarregadoBD();
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}		
	}

	
	/**
	 * @author Fernanda Almeida
	 * @date 05/08/2012
	 * @throws FachadaException
	 * @return ArrayList<ImovelConta> 
	 */
	public ArrayList<Integer> buscarIdsImoveisLidosNaoEnviados() throws FachadaException {
		try{
			return getControladorImovelConta().buscarIdsImoveisLidosNaoEnviados();
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}			
	}
	
	/**
	 * @author Fernanda Almeida
	 * @date 05/08/2012
	 * @throws FachadaException
	 * @return ArrayList<ImovelConta> 
	 */
	public ArrayList<Integer> buscarIdsImoveisLidos() throws FachadaException {
		try{
			return getControladorImovelConta().buscarIdsImoveisLidos();
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}			
	}
	
	/**
	 * @author Fernanda Almeida
	 * @date 05/08/2012
	 * @throws FachadaException
	 * @return ArrayList<ImovelConta> 
	 */
	public void buscarIdsImoveisLidosNaoEnviados(ImovelConta imovel, byte caminhoSucesso, byte caminhoErro,boolean enviou,int tipoFinalizacao,String nomeArquivo,
			int tipo,String msg,int idMensagem) throws FachadaException {
		
		getControladorAlertaValidarMensagemConexao(imovel, caminhoSucesso, caminhoErro,enviou,tipoFinalizacao,nomeArquivo).defineAlerta(tipo, msg, idMensagem);
					
	}

	/**
	 * Verifica se o imovel pode ser recalculado
	 * @author Carlos Chaves
	 * 
	 * @param Integer indicadorImovelImpresso
	 * @return boolean
	 * @throws FachadaException
	 */
	public boolean verificarBloqueioRecalcularConta(ImovelConta imovel) throws FachadaException { 
		try{
			return getControladorImovelConta().verificarBloqueioRecalcularConta(imovel);
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}		
	}
	

	public Integer obterQuantidadeImovelMicro(Integer idImovelMacro) throws FachadaException { 
		try{
			return getControladorImovelConta().obterQuantidadeImovelMicro(idImovelMacro);
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}		
	}
	
	/**
	 * Envia o im�vel calculado e n�o enviado ao gsan
	 * @params ImovelConta imovel
	 * @author Fernanda
	 * @return 
	 * @throws FachadaException
	 */
	public void enviarEmBackground(ImovelConta imovel) throws FachadaException { 
		try{
			getControladorImovelConta().enviarEmBackground(imovel);
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}		
	}
	
	/**
	 * Envia o im�vel calculado e n�o enviado ao gsan
	 * @params ImovelConta imovel
	 * @author Fernanda
	 * @return 
	 * @throws FachadaException
	 */
	public void alertaMensagemConexao(ImovelConta imovel, byte caminhoSucesso, byte caminhoErro,
			boolean enviou,int tipoFinalizacao,int tipo, String msg, int idMensagem) throws FachadaException { 
		
		IControladorAlertaValidarMensagemConexao controlador = getControladorAlertaValidarMensagemConexao(imovel, caminhoSucesso, caminhoErro, enviou, tipoFinalizacao);
		controlador.defineAlerta(tipo, msg, idMensagem);
	}
	
	/**
	 * Retorna  a quatidade total de imoveis
	 * @author Carlos Chaves
	 * 
	 * @return Integer
	 */
	public Integer obterQuantidadeImoveis() throws FachadaException { 
		try{
			return getControladorImovelConta().getQtdImoveis();
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}		
	}
	
	/**
	 * Retorna  a quatidade total de imoveis visitados
	 * @author Carlos Chaves
	 * 
	 * @return Integer
	 */
	public Integer obterQuantidadeImoveisVisitados() throws FachadaException { 
		try{
			return getControladorImovelConta().obterQuantidadeImoveisVisitados();
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}		
	}
	
	/**
	 * Retorna  a quatidade total de imoveis visitados com anormalidade
	 * @author Carlos Chaves
	 * 
	 * @return Integer
	 */
	public Integer obterQuantidadeImoveisVisitadosComAnormalidade() throws FachadaException { 
		try{
			return getControladorImovelConta().obterQuantidadeImoveisVisitadosComAnormalidade();
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}		
	}
	
	/**
	 * Retorna  a quatidade total de imoveis visitados sem anormalidade
	 * @author Carlos Chaves
	 * 
	 * @return Integer
	 */
//	public Integer obterQuantidadeImoveisVisitadosSemAnormalidade() throws FachadaException { 
//		try{
//			return getControladorImovelConta().obterQuantidadeImoveisVisitadosSemAnormalidade();
//		} catch ( ControladorException e ){
//			throw new FachadaException( e.getMessage() );
//		}		
//	}
	
	/**
	 * Retorna a quantidade de imoveis de uma quadra
	 * @param Quadra
	 * @author Carlos Chaves
	 * @return Integer
	 * @throws FachadaException
	 */
	public Integer obterQuantidadeImoveisPorQuadra(Integer numeroQuadra) throws FachadaException { 
		try{
			return getControladorImovelConta().obterQuantidadeImoveisPorQuadra(numeroQuadra);
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}		
	}
	
	/**
	 * Retorna a quantidade de imoveis visitados de uma quadra
	 * @param Quadra
	 * @author Carlos Chaves
	 * @return Integer
	 * @throws FachadaException
	 */
	public Integer obterQuantidadeImoveisVisitadosPorQuadra(Integer numeroQuadra) throws FachadaException { 
		try{
			return getControladorImovelConta().obterQuantidadeImoveisVisitadosPorQuadra(numeroQuadra);
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}		
	}
	
	/**
	 * Retorna a quantidade de imoveis nao visitados de uma quadra
	 * @param Quadra
	 * @author Carlos Chaves
	 * @return Integer
	 * @throws FachadaException
	 */
	public Integer obterQuantidadeImoveisNaoVisitadosPorQuadra(Integer numeroQuadra) throws FachadaException { 
		try{
			return getControladorImovelConta().obterQuantidadeImoveisNaoVisitadosPorQuadra(numeroQuadra);
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}		
	}
	
	/**
	 * Retorna os ids de todas as quadras
	 * 
	 * @author Carlos Chaves
	 * @return ArrayList<Integer>
	 * @throws FachadaException
	 */
	public ArrayList<Integer> buscarQuadras() throws FachadaException {
		try{
			return getControladorImovelConta().buscarQuadras();
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}			
	}
		
	/**
	 * @author Carlos Chaves
	 * @date 17/09/2012
	 * @param Integer indicadorRotaMarcacaoAtiva
	 * @throws FachadaException
	 
	 */
	public String atualizarIndicadorRotaMarcacaoAtiva(Integer indicadorRotaMarcacaoAtiva) throws FachadaException {
		try{
			return getControladorSistemaParametros().atualizarIndicadorRotaMarcacaoAtiva(indicadorRotaMarcacaoAtiva);
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}
	}

	/**
	 * Atualiza o atributo indicadorTransmissaoOffline de SistemaParametros
	 * 
	 * @author Maxwell Moreira
	 * @return void
	 */
	public void atualizarRoteiroOnlineOffline(Integer indicador) {
		try{
			getControladorSistemaParametros().atualizarRoteiroOnlineOffline(indicador);
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}		
	}
	
	/**
	 * Retorna im�vel a revisitar com 
	 * indicador de revisitado igual a n�o e diferente do im�vel atual
	 * 
	 * @return ImovelRevisitar
	 * @params Integer idImovel
	 * @author Fernanda Almeida
	 * @date 20/09/2012
	 */
	public ArrayList<ImovelRevisitar> buscarImovelNaoRevisitado() {
		try{
			return getControladorImovelRevisitar().buscarImovelNaoRevisitado();
		} catch (ControladorException e){
			throw new FachadaException( e.getMessage() );
		}		
	}
	
	/**
	 * Metodo que inverte o roteiro
	 * @author Carlos Chaves
	 * @date 19/09/2012
	 */
	public void inverterRoteiro() {
		try{
			getControladorImovelConta().inverterRoteiro();
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}		
	}

	public ControladorAlertaValidarErro getControladorAlertaValidarErro(ImovelConta imovel) {
		
		controladorAlertaValidarErro = new ControladorAlertaValidarErro( imovel );
		
		return controladorAlertaValidarErro;
	}
	
	public ControladorAlertaValidarFoto getControladorAlertaValidarFoto(TabsActivity tabsActivity, HidrometroInstalado hidrometroInstaladoAgua, HidrometroInstalado hidrometroInstaladoPoco,
			ImovelConta imovel, Integer posicao, boolean proximo, boolean imprimir, Integer medicaoTipo, Integer anormId,Integer idConsumoAnormalidade) {
		
		controladorAlertaValidarFoto = new ControladorAlertaValidarFoto( tabsActivity,hidrometroInstaladoAgua, hidrometroInstaladoPoco, imovel,posicao,proximo,imprimir,medicaoTipo,anormId,idConsumoAnormalidade);
		
		return controladorAlertaValidarFoto;
	}
	
	public void defineAlertaErro(ImovelConta imovel,int tipo,String msg, int idMensagem) {
		
		getControladorAlertaValidarErro(imovel).defineAlerta(tipo, msg, idMensagem);
		
	}
	
	public ControladorAlertaValidarErro getControladorAlertaValidarErro() {
		
		controladorAlertaValidarErro = new ControladorAlertaValidarErro();
		
		return controladorAlertaValidarErro;
	}
	
	/**
	 * @author Fernanda Almeida
	 * @date 26/09/2012
	 * @throws FachadaException
	 * @return ArrayList<Integer> 
	 */
	public ArrayList<Integer> buscarIdsImoveisLidosNaoEnviadosNaoCondominio() throws FachadaException {
		try{
			return getControladorImovelConta().buscarIdsImoveisLidosNaoEnviadosNaoCondominio();
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}			
	}
	
	/**
	 * Atualiza o indicador de imovel calculado, de acordo com o valor passado
	 * @author Fernanda
	 * @param Integer idImovel,Integer indicador
	 * @date 02/10/2012
	 */
	public void atualizarIndicadorImovelCalculado(Integer idImovel,Integer indicador) throws FachadaException {
		
		try{
			getControladorImovelConta().atualizarIndicadorImovelCalculado(idImovel,indicador);
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}	
	}

	private IControladorBasico getControladorBasico() {		
		if(controladorBasico==null){
			controladorBasico = ControladorBasico.getInstance();
		}				
		return controladorBasico;
	}
	
	/**
	 * Atualiza todos os campos do objeto no banco de dados
	 * @param objeto
	 * @throws FachadaException
	 */
	public void atualizar(ObjetoBasico objeto) throws FachadaException {
		try {
			getControladorBasico().atualizar(objeto);
		} catch (ControladorException e){
			throw new FachadaException(e.getMessage());
		}		
	}
	
	/**
	 * Remover objeto do BD
	 * @param objeto
	 * @throws FachadaException
	 */
	public void remover(ObjetoBasico objeto) throws FachadaException {
		try {
			getControladorBasico().remover(objeto);
		} catch (ControladorException e){
			throw new FachadaException(e.getMessage());
		}
	}
	
	/**
	 * Insere objeto no BD e retorna id gerado
	 * @param objeto
	 * @throws FachadaException
	 */
	public long inserir(ObjetoBasico objeto) throws FachadaException {
		try {
			return getControladorBasico().inserir(objeto);
		} catch (ControladorException e){
			throw new FachadaException(e.getMessage());
		}
	}
	
	/**
	 * Pesquisa objeto com base no id 
	 * Recebe como parametro objeto de tipo igual ao seu
	 * @author Amelia Pessoa
	 * @param objeto
	 * @throws FachadaException
	 */
	public <T extends ObjetoBasico> T pesquisarPorId(Integer id, T objetoTipo) throws FachadaException {
		try {
			return getControladorBasico().pesquisarPorId(id, objetoTipo);
		} catch (ControladorException e){
			throw new FachadaException(e.getMessage());
		}
	}
	
	/**
	 * Pesquisa lista de objetos 
	 * Recebe como parametro objeto de tipo igual ao seu
	 * @author Amelia Pessoa
	 * @param objeto
	 */
	public <T extends ObjetoBasico>  ArrayList<T> pesquisar(T objetoTipo) throws FachadaException {
		try {
			return getControladorBasico().pesquisar(objetoTipo);
		} catch (ControladorException e){
			throw new FachadaException(e.getMessage());
		}
	}
	
	/**
	 * @author Carlos Chaves
	 * @date 26/07/2012
	 * @param ImovelConta imovel
	 * @throws FachadaException
	 * @return boolean 
	 */
	public boolean enviarAoFinalizar(ImovelConta imovel) throws FachadaException {
		return getControladorImovelConta().enviarAoFinalizar(imovel);		
	}
	
	public LeituraAnormalidade buscarLeituraAnormalidadePorIdComUsoAtivo(Integer id) throws FachadaException {
		try {
			return getControladorLeituraAnormalidade().buscarLeituraAnormalidadePorIdComUsoAtivo(id);
		} catch (ControladorException e){
			throw new FachadaException(e.getMessage());
		}		
	}
	
	public ArrayList<LeituraAnormalidade> buscarLeiturasAnormalidadesComUsoAtivo()
			throws FachadaException {
		try {
			return getControladorLeituraAnormalidade().buscarLeiturasAnormalidadesComUsoAtivo();
		} catch (ControladorException e){
			throw new FachadaException(e.getMessage());
		}		
	}
	
	/**
	 * Retorna true se a orientacao default do celular dor LANDSCAPE
	 * @author Amelia Pessoa
	 * @param Contexto
	 * @return
	 */
	public boolean isOrientacaoLandscape(Context ctx){
		Display display = ((WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        if(display.getOrientation()==1){
        	return true;
        } else {
        	return false;
        }
	}

	public boolean verificarExistenciaBancoDeDados() {
		return getControladorBasico().verificarExistenciaBancoDeDados();		
	}
	
	public void carregaLinhaParaBD(String line) throws FachadaException {
		try {
			getControladorBasico().carregaLinhaParaBD(line);
		} catch (ControladorException e){
			throw new FachadaException(e.getMessage());
		}
	}
	
	public void apagarBanco() {
		getControladorBasico().apagarBanco();	
	}
	
	/**
	 * @author Arthur Carvalho
	 * 
	 * @param foto
	 * @throws FachadaException
	 * @throws NegocioException
	 */
	public ArrayList<Foto> buscarFotosPendentes() throws FachadaException, NegocioException {
		try{
			return getControladorFoto().buscarFotosPendentes();
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}			
	}
	
	/**
	 * @author Arthur Carvalho
	 * 
	 * @param foto
	 * @throws FachadaException
	 * @throws NegocioException
	 */
	public boolean enviarFotosOnline(Foto foto) throws FachadaException, NegocioException {
		try{
			return getControladorFoto().enviarFotosOnline(foto);
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}			
	}
	
	/**
	 * Metodo Respons�vel por enviar as fotos pendentes do imovel para o GSAN.
	 * 
	 */
	public boolean enviarFotosOnline(ImovelConta imovel){
		try { 
			return getControladorFoto().enviarFotosOnline(imovel);
		} catch (ControladorException e){
			throw new FachadaException(e.getMessage());
		}
	}
	
	/**
	 * @author Carlos Chaves
	 * @date 31/10/2012
	 * @throws FachadaException
	 * @return ArrayList<ImovelConta> 
	 */
	public ArrayList<Integer> buscarIdsImoveisCalculados() throws FachadaException {
		try{
			return getControladorImovelConta().buscarIdsImoveisCalculados();
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}			
	}
	
	/**
	 * Método que verifica se permite a impressao
	 * da 2 via de um imov
	 * @author Carlos Chaves	
	 * @param  ImovelMacro
	 * @throws FachadaException
	 * @return boolean
	 */
	public boolean permiteImprimir2ViaImovelMicro(Integer idImovelMacro) throws FachadaException {

		try {
			return getControladorImovelConta().verificarRateioCondominio(idImovelMacro);
		} catch (ControladorException e) {
			throw new FachadaException( e.getMessage() );
		}
	
	}
	
	/**
	 * Retorna o primeiro im�vel da lista de im�veis
	 * @author S�vio Luiz
	 * @date 26/12/2012
	 */
	public ImovelConta buscarPrimeiroImovel() throws FachadaException{
		try {
			return getControladorImovelConta().buscarPrimeiroImovel();
		} catch (ControladorException e) {
			throw new FachadaException( e.getMessage() );
		}
	}
	
	/**
	 * Retorna true se motolora defy pro
	 * @author Carlos Chaves
	 * @param Contexto
	 * @return
	 */
	public boolean isMotoralaDefyPro(Context ctx){
		if("XT560".equalsIgnoreCase(Build.PRODUCT)){
			return  true;
	     }else{
	    	 return  false;
	     }
	}

	public ArrayList<ImovelConta> buscarImoveisOrdenadosNovos() {
		try {
			return getControladorImovelConta().buscarImoveisOrdenadosNovos();
		} catch (ControladorException e) {
			throw new FachadaException( e.getMessage() );
		}
	}
	
	/**
	 * Verifica se todos os im�veis micro vinculado ao macro passado
	 * como parametro tem o indcRateio = SIM (inclusive o proprio macro)
	 * 
	 * @author Amelia Pessoa
	 * @param idImovelMacro
	 * @return boolean indicando se todos foram rateados
	 * @throws ControladorException
	 */
	public boolean verificarRateioCondominio(Integer idImovelMacro) throws FachadaException{
		try {
			return getControladorImovelConta().verificarRateioCondominio(idImovelMacro);
		} catch (ControladorException e) {
			throw new FachadaException( e.getMessage() );
		}
	}
	
	
	/**
	 * Metodo que atualiza os indicadores que indicam que o 
	 * imovel condominio n�o esta calculado
	 * @author Carlos Chaves
	 * @date 07/03/2013
	 * @param Integer idMacro
	 * @param Integer imovelCalculado
	 */	
	public void atualizarIndicadorImovelCondominioNaoCalculado(Integer idMacro) {
		try{
			getControladorImovelConta().atualizarIndicadorImovelCondominioNaoCalculado(idMacro);
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}		
	}
	
	/**
	 * Metodo que atualiza o indicador de permite continuar
	 * impressao do imovel macro
	 * @author Carlos Chaves
	 * @date 19/03/2013
	 * @param Integer idMacro
	 */
	public void atualizarIndicadorContinuaImpressao(Integer idImovelMacro, Integer indicadorContinuaImpressao){
		try{
			getControladorImovelConta().atualizarIndicadorContinuaImpressao(idImovelMacro,indicadorContinuaImpressao );
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}
	}
	
	
	/**
	 * Metodo que obtem o indicador de permite continuar
	 * impressao do imovel macro
	 * @author Carlos Chaves
	 * @date 19/03/2013
	 * @param Integer idMacro
	 */
	public Integer obterIndicadorPermiteContinuarImpressao(Integer idMacro) {
		try {
			return getControladorImovelConta().obterIndicadorPermiteContinuarImpressao(idMacro);
		} catch (ControladorException e) {
			throw new FachadaException( e.getMessage() );
		}
	}
	
	/**
	 * Retorna todos os im�vies lidos e n�o impressos, incluindo condom�nio
	 * @author Fernanda
	 * @date 14/05/2013
	 */
	public ArrayList<Integer> buscarIdsImoveisLidosNaoImpressos() {
		try {
			return getControladorImovelConta().buscarIdsImoveisLidosNaoImpressos();
		} catch (ControladorException e) {
			throw new FachadaException( e.getMessage() );
		}
	}
	
	/**
	 * Retorna todos os im�vies lidos e n�o impressos, incluindo condom�nio
	 * @author Fernanda
	 * @date 14/05/2013
	 */
	public void insereLogFinalizacao(String msg) {
		try {
			getControladorLogFinalizacao().inserir(msg);
		} catch (ControladorException e) {
			throw new FachadaException( e.getMessage() );
		}
	}
	
	/**
	 * Retornar a qualidade agua sem localidade
	 * @author Davi Menezes
	 * @date 22/10/2013
	 */
	public QualidadeAgua buscarQualidadeAguaSemLocalidade() {
		try{
			return getControladorQualidadeAgua().buscarQualidadeAguaSemLocalidade();
		}catch (ControladorException e) {
			throw new FachadaException( e.getMessage() );
		}
	}
	
	
	/**
	 * Retorna a soma dos valores de agua e esgoto
	 * @author Fábio Aguiar
	 * @date 01/06/2015
	 */
	public double obterSomaValorAguaEsgoto(Integer imovelId) throws ControladorException {
		
		try{
			return getControladorImovelConta().obterSomaValorAguaEsgoto(imovelId);
		}catch (ControladorException e) {
			throw new FachadaException( e.getMessage() );
		}

	}
	
	/**
	 * @author Jonathan Marcos
	 * @since 01/09/2015
	 * @param selection
	 * @param selectionArgs
	 * @return ArrayList<Foto>
	 */
	public ArrayList<Foto> buscarFotos(String selection,  String[] selectionArgs) {
		
		try{
			return getControladorFoto().buscarFotos(selection, selectionArgs);
		}catch (ControladorException e) {
			throw new FachadaException( e.getMessage() );
		}
	}

	/**
	 * @author André Miranda
	 * @date 26/10/2015
	 * @param Integer imovelId
	 * @throws FachadaException
	 * @return Integer id da categoria principal
	 */
	public Integer obterCategoriaPrincipal(Integer imovelId) throws FachadaException {
		try{
			return getControladorCategoriaSubcategoria().obterCategoriaPrincipal(imovelId);
		} catch ( ControladorException e ){
			throw new FachadaException( e.getMessage() );
		}			
	}

	/**
	 * @author André Miranda
	 * @date 26/10/2015
	 * @throws FachadaException
	 */
	public ArrayList<ConsumoAnormalidadeAcao> buscarConsumoAnormalidadeAcao(Integer perfilId, Integer anormalidade,
			Integer categoriaPrincipal) throws FachadaException {
		try {
			return getControladorConsumoAnormalidadeAcao().buscarConsumoAnormalidadeAcao(perfilId, anormalidade,
					categoriaPrincipal);
		} catch (ControladorException e) {
			throw new FachadaException(e.getMessage());
		}
	}

	/**
	 * @author André Miranda
	 * @date 26/10/2015
	 * @throws FachadaException
	 */
	public ConsumoAnteriores buscarConsumoAnterioresPorImovelAnormalidade(Integer imovelId,
			Integer idAnormalidadeConsumo, Integer anoMes) throws FachadaException {
		try {
			return getControladorConsumoAnteriores().buscarConsumoAnterioresPorImovelAnormalidade(imovelId,
					idAnormalidadeConsumo, anoMes);
		} catch (ControladorException e) {
			throw new FachadaException(e.getMessage());
		}
	}
}
