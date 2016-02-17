package com.br.ipad.isc.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.util.Log;

import com.br.ipad.isc.controladores.ControladorImovelConta;
import com.br.ipad.isc.excecoes.ControladorException;
import com.br.ipad.isc.excecoes.RepositorioException;
import com.br.ipad.isc.repositorios.RepositorioBasico;
import com.br.ipad.isc.util.ConstantesSistema;
import com.br.ipad.isc.util.Util;

/**
 * [] Classe Básica - Imóvel Conta
 * 
 * @author Fernanda Almeida
 * @since 08/03/2012
 */
public class ImovelConta extends ObjetoBasico implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private FaturamentoSituacaoTipo faturamentoSituacaoTipo;
	private String nomeGerenciaRegional;
	private Integer numeroQuadra;
	private String localidade;
	private String nomeUsuario;
	private Date dataVencimento;
	private Date dataValidadeConta;
	private String inscricao;
	private String endereco;
	private Integer anoMesConta;
	private Integer digitoVerificadorConta;
	private Integer cliente;
	private String nomeResponsavel;
	private String enderecoEntrega;
	private Integer situacaoLigAgua;
	private Integer situacaoLigEsgoto;
	private String descricaoBanco;
	private String codigoAgencia;
	private Integer matriculaCondominio;
	private Integer indcCondominio;
	private Integer codigoPerfil;
	private Integer consumoMedioLigacaoAgua;
	private Integer indcFaturamentoAgua;
	private Integer indcFaturamentoEsgoto;
	private Integer indcEmissaoConta;
	private Integer consumoMinAgua;
	private Integer consumoMinEsgoto;
	private BigDecimal percentColetaEsgoto;
	private BigDecimal percentCobrancaEsgoto;
	private Integer tipoCalculoTarifa;
	private Integer tipoPoco;
	private Integer codigoTarifa;
	private Integer consumoEstouro;
	private Integer altoConsumo;
	private Integer baixoConsumo;
	private BigDecimal fatorMultEstouro;
	private BigDecimal fatorMultMediaAltoConsumo;
	private BigDecimal percentBaixoConsumo;
	private Integer consumoMaximo;
	//private Integer consumoMedioNaoMedido;
	private Integer numeroConta;
	private Integer grupoFaturamento;
	private Integer codigoRota;
	private String enderecoAtendimento;
	private String telefoneLocalidadeDDD;
	private Integer sequencialRota;
	private String mensagemConta1;
	private String mensagemConta2;
	private String mensagemConta3;
	private String mensagemConta4;
	private String mensagemConta5;
	private Integer consumoMinimoImovel;
	private Integer idDocumentoNotificacaoDebito;
	private String numeroCodigoBarraNotificacaoDebito;
	private String cpfCnpjCliente;
	private Integer consumoAguaMedidoHistoricoFaturamento;
	private Integer consumoAguaNaoMedidoHistoricoFaturamento;
	private Integer volumeEsgotoMedidoHistoricoFaturamento;
	private Integer volumeEsgotoNaoMedidoHistoricoFaturamento;
	private Date dataLeituraAnterior;
	private Integer indicadorParalizarFaturamentoAgua;
	private Integer indicadorParalizarFaturamentoEsgoto;
	private Date dataEmissaoDocumento;
	private BigDecimal percentualAlternativoEsgoto;
	private Integer consumoPercentualAlternativoEsgoto;
	private Integer consumoMedioEsgoto;
	private Integer codigoDebitoAutomatico;
	private Integer indicadorAbastecimentoAgua;
	private Integer indicadorImovelSazonal;
	private Integer indcConsumoReal;
	private Integer numeroDiasCorte;
	private Date dataCorte;
	private Integer indicadorImovelRateioNegativo;
	private Integer indcImovelCalculado;
	private Integer indcImovelImpresso;
	private Integer indcImovelEnviado;
	private Integer posicao;
	private String mensagemContaAnormalidade1;
	private String mensagemContaAnormalidade2;
	private String mensagemContaAnormalidade3;	
	private Integer idLocalidade;
	private Integer idSetorComercial;
	private Integer qntVezesImpressaoConta;
	private Integer indcNaoPermiteImpressao;
	//[UC1305] - Carregar Arquivo Texto Impressão Simultânea de Contas / RN20140912125
	private Date dataLigacaoAgua;
	private Date dataLigacaoRestabelecimento;
	
	private Integer indcAreaComum;
	private Integer posicaoImovelCondominio;
	private Integer indcRateioRealizado;
	private BigDecimal numeroCoordenadaX;
	private BigDecimal numeroCoordenadaY;
	private Integer indcContinuaImpressao;
	

	private Date ultimaAlteracao;
	
	private ContaComunicado contaComunicado;
	
	private Short ligacaoAguaSituacaoIndicadorLeituraReal;
	
	/* Construtor Mínimo */
	public ImovelConta() {
		super();
	}
	
	public ImovelConta(ArrayList<String> obj, Integer posicao){
		insertFromFile(obj, posicao);
	}
	
	public ImovelConta(Integer idImovel) {
		super();
		this.id = idImovel;
	}
		
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
//	public Integer getConsumoMedioNaoMedido() {
//		return consumoMedioNaoMedido;
//	}
//	public void setConsumoMedioNaoMedido(Integer consumoMedioNaoMedido) {
//		this.consumoMedioNaoMedido = consumoMedioNaoMedido;
//	}
	public FaturamentoSituacaoTipo getFaturamentoSituacaoTipo() {
		return faturamentoSituacaoTipo;
	}
	public void setFaturamentoSituacaoTipo(FaturamentoSituacaoTipo faturamentoSituacaoTipo) {
		this.faturamentoSituacaoTipo = faturamentoSituacaoTipo;
	}
	public String getNomeGerenciaRegional() {
		return nomeGerenciaRegional;
	}
	public void setNomeGerenciaRegional(String nomeGerenciaRegional) {
		this.nomeGerenciaRegional = nomeGerenciaRegional;
	}
	public Integer getTipoCalculoTarifa() {
		return tipoCalculoTarifa;
	}
	public void setTipoCalculoTarifa(Integer tipoCalculoTarifa) {
		this.tipoCalculoTarifa = tipoCalculoTarifa;
	}
	public String getLocalidade() {
		return localidade;
	}
	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}
	public String getNomeUsuario() {
		return nomeUsuario;
	}
	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}
	public Integer getNumeroQuadra() {
		return numeroQuadra;
	}
	public void setNumeroQuadra(Integer numeroQuadra) {
		this.numeroQuadra = numeroQuadra;
	}
	public Date getDataVencimento() {
		return dataVencimento;
	}
	public Date getDataValidadeConta() {
		return dataValidadeConta;
	}
	public String getInscricao() {
		return inscricao;
	}
	public void setInscricao(String inscricao) {
		this.inscricao = inscricao;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public Integer getAnoMesConta() {
		return anoMesConta;
	}
	public void setAnoMesConta(Integer anoMesConta) {
		this.anoMesConta = anoMesConta;
	}
	public Integer getDigitoVerificadorConta() {
		return digitoVerificadorConta;
	}
	public void setDigitoVerificadorConta(Integer digitoVerificadorConta) {
		this.digitoVerificadorConta = digitoVerificadorConta;
	}
	public Integer getPosicao() {
		return posicao;
	}
	public void setPosicao(Integer posicao) {
		this.posicao = posicao;
	}
	public Integer getCliente() {
		return cliente;
	}
	public void setCliente(Integer cliente) {
		this.cliente = cliente;
	}
	public String getNomeResponsavel() {
		return nomeResponsavel;
	}
	public void setNomeResponsavel(String nomeResponsavel) {
		this.nomeResponsavel = nomeResponsavel;
	}
	public String getEnderecoEntrega() {
		return enderecoEntrega;
	}
	public void setEnderecoEntrega(String enderecoEntrega) {
		this.enderecoEntrega = enderecoEntrega;
	}
	public String getMensagemConta1() {
		return mensagemConta1;
	}
	public void setMensagemConta1(String mensagemConta1) {
		this.mensagemConta1 = mensagemConta1;
	}
	public String getMensagemConta2() {
		return mensagemConta2;
	}
	public void setMensagemConta2(String mensagemConta2) {
		this.mensagemConta2 = mensagemConta2;
	}
	public String getMensagemConta3() {
		return mensagemConta3;
	}
	public void setMensagemConta3(String mensagemConta3) {
		this.mensagemConta3 = mensagemConta3;
	}
	public String getMensagemConta4() {
		return mensagemConta4;
	}
	public void setMensagemConta4(String mensagemConta4) {
		this.mensagemConta4 = mensagemConta4;
	}
	public String getMensagemConta5() {
		return mensagemConta5;
	}
	public void setMensagemConta5(String mensagemConta5) {
		this.mensagemConta5 = mensagemConta5;
	}
	public Date getDataLeituraAnterior() {
		return dataLeituraAnterior;
	}
	public Integer getConsumoMedioEsgoto() {
		return consumoMedioEsgoto;
	}
	public void setConsumoMedioEsgoto(Integer consumoMedioEsgoto) {
		this.consumoMedioEsgoto = consumoMedioEsgoto;
	}
	public Integer getCodigoDebitoAutomatico() {
		return codigoDebitoAutomatico;
	}
	public void setCodigoDebitoAutomatico(Integer codigoDebitoAutomatico) {
		this.codigoDebitoAutomatico = codigoDebitoAutomatico;
	}
	public Integer getNumeroDiasCorte() {
		return numeroDiasCorte;
	}
	public void setNumeroDiasCorte(Integer numeroDiasCorte) {
		this.numeroDiasCorte = numeroDiasCorte;
	}
	public Integer getSituacaoLigAgua() {
		return situacaoLigAgua;
	}
	public void setSituacaoLigAgua(Integer situacaoLigAgua) {
		this.situacaoLigAgua = situacaoLigAgua;
	}
	public Integer getSituacaoLigEsgoto() {
		return situacaoLigEsgoto;
	}
	public void setSituacaoLigEsgoto(Integer situacaoLigEsgoto) {
		this.situacaoLigEsgoto = situacaoLigEsgoto;
	}
	public String getDescricaoBanco() {
		return descricaoBanco;
	}
	public void setDescricaoBanco(String descricaoBanco) {
		this.descricaoBanco = descricaoBanco;
	}
	public String getCodigoAgencia() {
		return codigoAgencia;
	}
	public void setCodigoAgencia(String codigoAgencia) {
		this.codigoAgencia = codigoAgencia;
	}
	public Integer getMatriculaCondominio() {
		return matriculaCondominio;
	}
	public void setMatriculaCondominio(Integer matriculaCondominio) {
		this.matriculaCondominio = matriculaCondominio;
	}
	public Integer getIndcCondominio() {
		return indcCondominio;
	}
	public void setIndcCondominio(Integer indcCondominio) {
		this.indcCondominio = indcCondominio;
	}
	public Integer getCodigoPerfil() {
		return codigoPerfil;
	}
	public void setCodigoPerfil(Integer codigoPerfil) {
		this.codigoPerfil = codigoPerfil;
	}
	public Integer getConsumoMedioLigacaoAgua() {
		return consumoMedioLigacaoAgua;
	}
	public void setConsumoMedioLigacaoAgua(Integer consumoMedioLigacaoAgua) {
		this.consumoMedioLigacaoAgua = consumoMedioLigacaoAgua;
	}
	public Integer getIndcFaturamentoAgua() {
		return indcFaturamentoAgua;
	}
	public void setIndcFaturamentoAgua(Integer indcFaturamentoAgua) {
		this.indcFaturamentoAgua = indcFaturamentoAgua;
	}
	public Integer getIndcFaturamentoEsgoto() {
		return indcFaturamentoEsgoto;
	}
	public void setIndcFaturamentoEsgoto(Integer indcFaturamentoEsgoto) {
		this.indcFaturamentoEsgoto = indcFaturamentoEsgoto;
	}
	public Integer getIndcEmissaoConta() {
		return indcEmissaoConta;
	}
	public void setIndcEmissaoConta(Integer indcEmissaoConta) {
		this.indcEmissaoConta = indcEmissaoConta;
	}
	public Integer getConsumoMinAgua() {
		return consumoMinAgua;
	}
	public void setConsumoMinAgua(Integer consumoMinAgua) {
		this.consumoMinAgua = consumoMinAgua;
	}
	public Integer getConsumoMinEsgoto() {
		return consumoMinEsgoto;
	}
	public void setConsumoMinEsgoto(Integer consumoMinEsgoto) {
		this.consumoMinEsgoto = consumoMinEsgoto;
	}
	public BigDecimal getPercentColetaEsgoto() {
		return percentColetaEsgoto;
	}
	public void setPercentColetaEsgoto(BigDecimal percentColetaEsgoto) {
		this.percentColetaEsgoto = percentColetaEsgoto;
	}
	public BigDecimal getPercentCobrancaEsgoto() {
		return percentCobrancaEsgoto;
	}
	public void setPercentCobrancaEsgoto(BigDecimal percentCobrancaEsgoto) {
		this.percentCobrancaEsgoto = percentCobrancaEsgoto;
	}
	public Integer getTipoPoco() {
		return tipoPoco;
	}
	public void setTipoPoco(Integer tipoPoco) {
		this.tipoPoco = tipoPoco;
	}
	public Integer getConsumoEstouro() {
		return consumoEstouro;
	}
	public void setConsumoEstouro(Integer consumoEstouro) {
		this.consumoEstouro = consumoEstouro;
	}
	public Integer getAltoConsumo() {
		return altoConsumo;
	}
	public void setAltoConsumo(Integer altoConsumo) {
		this.altoConsumo = altoConsumo;
	}
	public Integer getBaixoConsumo() {
		return baixoConsumo;
	}
	public void setBaixoConsumo(Integer baixoConsumo) {
		this.baixoConsumo = baixoConsumo;
	}
	public BigDecimal getFatorMultEstouro() {
		return fatorMultEstouro;
	}
	public void setFatorMultEstouro(BigDecimal fatorMultEstouro) {
		this.fatorMultEstouro = fatorMultEstouro;
	}
	public BigDecimal getFatorMultMediaAltoConsumo() {
		return fatorMultMediaAltoConsumo;
	}
	public void setFatorMultMediaAltoConsumo(BigDecimal fatorMultMediaAltoConsumo) {
		this.fatorMultMediaAltoConsumo = fatorMultMediaAltoConsumo;
	}
	public BigDecimal getPercentBaixoConsumo() {
		return percentBaixoConsumo;
	}
	public void setPercentBaixoConsumo(BigDecimal percentBaixoConsumo) {
		this.percentBaixoConsumo = percentBaixoConsumo;
	}
	public Integer getConsumoMaximo() {
		return consumoMaximo;
	}
	public void setConsumoMaximo(Integer consumoMaximo) {
		this.consumoMaximo = consumoMaximo;
	}
	public Integer getNumeroConta() {
		return numeroConta;
	}
	public void setNumeroConta(Integer numeroConta) {
		this.numeroConta = numeroConta;
	}
	public Integer getGrupoFaturamento() {
		return grupoFaturamento;
	}
	public void setGrupoFaturamento(Integer grupoFaturamento) {
		this.grupoFaturamento = grupoFaturamento;
	}
	public Integer getCodigoRota() {
		return codigoRota;
	}
	public void setCodigoRota(Integer codigoRota) {
		this.codigoRota = codigoRota;
	}
	public String getEnderecoAtendimento() {
		return enderecoAtendimento;
	}
	public void setEnderecoAtendimento(String enderecoAtendimento) {
		this.enderecoAtendimento = enderecoAtendimento;
	}
	public String getTelefoneLocalidadeDDD() {
		return telefoneLocalidadeDDD;
	}
	public void setTelefoneLocalidadeDDD(String telefoneLocalidadeDDD) {
		this.telefoneLocalidadeDDD = telefoneLocalidadeDDD;
	}
	public Integer getSequencialRota() {
		return sequencialRota;
	}
	public void setSequencialRota(Integer sequencialRota) {
		this.sequencialRota = sequencialRota;
	}
	
	public Integer getConsumoMinimoImovel() {
		return consumoMinimoImovel;
	}
	public void setConsumoMinimoImovel(Integer consumoMinimoImovel) {
		this.consumoMinimoImovel = consumoMinimoImovel;
	}
	public Integer getIdDocumentoNotificacaoDebito() {
		return idDocumentoNotificacaoDebito;
	}
	public void setIdDocumentoNotificacaoDebito(Integer idDocumentoNotificacaoDebito) {
		this.idDocumentoNotificacaoDebito = idDocumentoNotificacaoDebito;
	}
	public String getNumeroCodigoBarraNotificacaoDebito() {
		return numeroCodigoBarraNotificacaoDebito;
	}
	public void setNumeroCodigoBarraNotificacaoDebito(String numeroCodigoBarraNotificacaoDebito) {
		this.numeroCodigoBarraNotificacaoDebito = numeroCodigoBarraNotificacaoDebito;
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
	public String getCpfCnpjCliente() {
		return cpfCnpjCliente;
	}
	public void setCpfCnpjCliente(String cpfCnpjCliente) {
		this.cpfCnpjCliente = cpfCnpjCliente;
	}
	public Integer getIndicadorParalizarFaturamentoAgua() {
		return indicadorParalizarFaturamentoAgua;
	}
	public void setIndicadorParalizarFaturamentoAgua(Integer indicadorParalizarFaturamentoAgua) {
		this.indicadorParalizarFaturamentoAgua = indicadorParalizarFaturamentoAgua;
	}
	public Integer getIndicadorParalizarFaturamentoEsgoto() {
		return indicadorParalizarFaturamentoEsgoto;
	}
	public void setIndicadorParalizarFaturamentoEsgoto(Integer indicadorParalizarFaturamentoEsgoto) {
		this.indicadorParalizarFaturamentoEsgoto = indicadorParalizarFaturamentoEsgoto;
	}
	public Date getDataEmissaoDocumento() {
		return dataEmissaoDocumento;
	}
	public BigDecimal getPercentualAlternativoEsgoto() {
		return percentualAlternativoEsgoto;
	}
	public void setPercentualAlternativoEsgoto(BigDecimal percentualAlternativoEsgoto) {
		this.percentualAlternativoEsgoto = percentualAlternativoEsgoto;
	}
	public Integer getConsumoPercentualAlternativoEsgoto() {
		return consumoPercentualAlternativoEsgoto;
	}
	public void setConsumoPercentualAlternativoEsgoto(Integer consumoPercentualAlternativoEsgoto) {
		this.consumoPercentualAlternativoEsgoto = consumoPercentualAlternativoEsgoto;
	}
	public Integer getIndicadorAbastecimentoAgua() {
		return indicadorAbastecimentoAgua;
	}
	public void setIndicadorAbastecimentoAgua(Integer indicadorAbastecimentoAgua) {
		this.indicadorAbastecimentoAgua = indicadorAbastecimentoAgua;
	}
	public Integer getIndicadorImovelSazonal() {
		return indicadorImovelSazonal;
	}
	public void setIndicadorImovelSazonal(Integer indicadorImovelSazonal) {
		this.indicadorImovelSazonal = indicadorImovelSazonal;
	}
	public Integer getIndcConsumoReal() {
		return indcConsumoReal;
	}
	public void setIndcConsumoReal(Integer indcConsumoReal) {
		this.indcConsumoReal = indcConsumoReal;
	}
	
	public Date getDataCorte() {
		return dataCorte;
	}
	public void setDataCorte(Date dataCorte) {
		this.dataCorte = dataCorte;
	}
	public void setDataCorte(Long dataCorte) {
		this.dataCorte = new Date(dataCorte);
	}
	
	public Integer getIndicadorImovelRateioNegativo() {
		return indicadorImovelRateioNegativo;
	}
	public void setIndicadorImovelRateioNegativo(Integer indicadorImovelRateioNegativo) {
		this.indicadorImovelRateioNegativo = indicadorImovelRateioNegativo;
	}
	public Integer getIndcImovelCalculado() {
		return indcImovelCalculado;
	}
	public void setIndcImovelCalculado(Integer indcImovelCalculado) {
		this.indcImovelCalculado = indcImovelCalculado;
	}
	public Integer getIndcImovelImpresso() {
		return indcImovelImpresso;
	}
	public void setIndcImovelImpresso(Integer indcImovelImpresso) {
		this.indcImovelImpresso = indcImovelImpresso;
	}
	public Integer getIndcImovelEnviado() {
		return indcImovelEnviado;
	}
	public Integer getConsumoAguaMedidoHistoricoFaturamento() {
		return consumoAguaMedidoHistoricoFaturamento;
	}
	public void setConsumoAguaMedidoHistoricoFaturamento(
			Integer consumoAguaMedidoHistoricoFaturamento) {
		this.consumoAguaMedidoHistoricoFaturamento = consumoAguaMedidoHistoricoFaturamento;
	}
	public Integer getConsumoAguaNaoMedidoHistoricoFaturamento() {
		return consumoAguaNaoMedidoHistoricoFaturamento;
	}
	public void setConsumoAguaNaoMedidoHistoricoFaturamento(
			Integer consumoAguaNaoMedidoHistoricoFaturamento) {
		this.consumoAguaNaoMedidoHistoricoFaturamento = consumoAguaNaoMedidoHistoricoFaturamento;
	}
	public Integer getVolumeEsgotoMedidoHistoricoFaturamento() {
		return volumeEsgotoMedidoHistoricoFaturamento;
	}
	public void setVolumeEsgotoMedidoHistoricoFaturamento(
			Integer volumeEsgotoMedidoHistoricoFaturamento) {
		this.volumeEsgotoMedidoHistoricoFaturamento = volumeEsgotoMedidoHistoricoFaturamento;
	}
	public Integer getVolumeEsgotoNaoMedidoHistoricoFaturamento() {
		return volumeEsgotoNaoMedidoHistoricoFaturamento;
	}
	public void setVolumeEsgotoNaoMedidoHistoricoFaturamento(
			Integer volumeEsgotoNaoMedidoHistoricoFaturamento) {
		this.volumeEsgotoNaoMedidoHistoricoFaturamento = volumeEsgotoNaoMedidoHistoricoFaturamento;
	}
	public void setIndcImovelEnviado(Integer indcImovelEnviado) {
		this.indcImovelEnviado = indcImovelEnviado;
	}
	public void setIdString(String id) {
		this.id = Util.verificarNuloInt(id);
	}
	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}
	public void setDataVencimento(Long dataVencimento) {
		this.dataVencimento = new Date(dataVencimento);
	}
	public void setDataValidadeConta(Date dataValidadeConta) {
		this.dataValidadeConta = dataValidadeConta;
	}
	public void setDataValidadeConta(Long dataValidadeConta) {
		this.dataValidadeConta = new Date(dataValidadeConta);
	}
	public void setDataLeituraAnterior(Date dataLeituraAnterior) {
		this.dataLeituraAnterior = dataLeituraAnterior;
	}
	public void setDataLeituraAnterior(Long dataLeituraAnterior) {
		this.dataLeituraAnterior = new Date(dataLeituraAnterior);
	}
	public void setDataEmissaoDocumento(Date dataEmissaoDocumento) {
		this.dataEmissaoDocumento = dataEmissaoDocumento;
	}
	public void setDataEmissaoDocumento(Long dataEmissaoDocumento) {
		this.dataEmissaoDocumento = new Date(dataEmissaoDocumento);
	}
	public Integer getCodigoTarifa() {
		return codigoTarifa;
	}
	public void setCodigoTarifa(Integer codigoTarifa) {
		this.codigoTarifa = codigoTarifa;
	}
	public Date getUltimaAlteracao() {
		return ultimaAlteracao;
	}
	public void setUltimaAlteracao(String ultimaAlteracao) {
		this.ultimaAlteracao = Util.getData(ultimaAlteracao);
	}
	
	public String getMensagemContaAnormalidade1() {
		return mensagemContaAnormalidade1;
	}
	public void setMensagemContaAnormalidade1(String mensagemContaAnormalidade1) {
		this.mensagemContaAnormalidade1 = mensagemContaAnormalidade1;
	}
	public String getMensagemContaAnormalidade2() {
		return mensagemContaAnormalidade2;
	}
	public void setMensagemContaAnormalidade2(String mensagemContaAnormalidade2) {
		this.mensagemContaAnormalidade2 = mensagemContaAnormalidade2;
	}
	public String getMensagemContaAnormalidade3() {
		return mensagemContaAnormalidade3;
	}
	public void setMensagemContaAnormalidade3(String mensagemContaAnormalidade3) {
		this.mensagemContaAnormalidade3 = mensagemContaAnormalidade3;
	}
	
	public Integer getQntVezesImpressaoConta() {
		return qntVezesImpressaoConta;
	}
	
	public void setQntVezesImpressaoConta(Integer qntVezesImpressaoConta) {
		this.qntVezesImpressaoConta = qntVezesImpressaoConta;
	}
	
	public Integer getIndcNaoPermiteImpressao() {
		return indcNaoPermiteImpressao;
	}
	public void setIndcNaoPermiteImpressao(Integer indcNaoPermiteImpressao) {
		this.indcNaoPermiteImpressao = indcNaoPermiteImpressao;
	}
	//[UC1305] - Carregar Arquivo Texto Impressão Simultânea de Contas / RN20140912125
	public Date getDataLigacaoAgua() {
		return dataLigacaoAgua;
	}
	public void setDataLigacaoAgua(Date dataLigacaoAgua) {
		this.dataLigacaoAgua = dataLigacaoAgua;
	}
	public void setDataLigacaoAgua(Long dataLigacaoAgua) {
		this.dataLigacaoAgua = new Date(dataLigacaoAgua);
	}
	public Date getDataLigacaoRestabelecimento() {
		return dataLigacaoRestabelecimento;
	}
	public void setDataLigacaoRestabelecimento(Date dataLigacaoRestabelecimento) {
		this.dataLigacaoRestabelecimento = dataLigacaoRestabelecimento;
	}
	public void setDataLigacaoRestabelecimento(Long dataLigacaoRestabelecimento) {
		this.dataLigacaoRestabelecimento = new Date(dataLigacaoRestabelecimento);
	}

	public Integer getIndcAreaComum() {
		return indcAreaComum;
	}
	public void setIndcAreaComum(Integer indcAreaComum) {
		this.indcAreaComum = indcAreaComum;
	}

	public Integer getPosicaoImovelCondominio() {
		return posicaoImovelCondominio;
	}
	public void setPosicaoImovelCondominio(Integer posicaoImovelCondominio) {
		this.posicaoImovelCondominio = posicaoImovelCondominio;
	}
	public Integer getIndcRateioRealizado() {
		return indcRateioRealizado;
	}

	public void setIndcRateioRealizado(Integer indcRateioRealizado) {
		this.indcRateioRealizado = indcRateioRealizado;
	}
	
	
	public BigDecimal getNumeroCoordenadaX() {
		return numeroCoordenadaX;
	}

	public void setNumeroCoordenadaX(BigDecimal numeroCoordenadaX) {
		this.numeroCoordenadaX = numeroCoordenadaX;
	}

	public BigDecimal getNumeroCoordenadaY() {
		return numeroCoordenadaY;
	}

	public void setNumeroCoordenadaY(BigDecimal numeroCoordenadaY) {
		this.numeroCoordenadaY = numeroCoordenadaY;
	}
	
	public ContaComunicado getContaComunicado() {
		return contaComunicado;
	}

	public void setContaComunicado(ContaComunicado contaComunicado) {
		this.contaComunicado = contaComunicado;
	}

	public Short getLigacaoAguaSituacaoIndicadorLeituraReal() {
		return ligacaoAguaSituacaoIndicadorLeituraReal;
	}

	public void setLigacaoAguaSituacaoIndicadorLeituraReal(
			Short ligacaoAguaSituacaoIndicadorLeituraReal) {
		this.ligacaoAguaSituacaoIndicadorLeituraReal = ligacaoAguaSituacaoIndicadorLeituraReal;
	}

	private static String[] colunas = new String[] { ImovelContas.ID, ImovelContas.FATURAMENTOSITUACAOTIPO, 
	   ImovelContas.NOMEGERENCIA,
       ImovelContas.LOCALIDADE, ImovelContas.NOMEUSUARIO, ImovelContas.DATAVENCIMENTOCONTA,
       ImovelContas.DATAVALIDADECONTA, ImovelContas.NUMEROINSCRICAO, ImovelContas.ENDERECO,
       ImovelContas.ANOMESREFERENCIACONTA, ImovelContas.DIGITOVERIFICADORCONTA, ImovelContas.CLIENTE,ImovelContas.RESPONSAVEL,
       ImovelContas.ENDERECOENTREGA, ImovelContas.SITUACAOLIGACAOAGUA, ImovelContas.SITUACAOLIGACAOESGOTO,
       ImovelContas.BANCODEBITOAUTO,ImovelContas.CODIGOAGENCIADEBITOAUTO, ImovelContas.IDIMOVELCONDOMINIO, ImovelContas.INDICADORIMOVELCONDOMINIO,
       ImovelContas.PERFIL, ImovelContas.NUMEROCONMEDIOAGUA, ImovelContas.INDICADORFATURAGUA,
       ImovelContas.INDICADORFATURESGOTO, ImovelContas.INDICADOREMISSAOCONTA, ImovelContas.NUMEROCONSUMOMINAGUA,
      ImovelContas.NUMEROCONSUMOMINESGOTO, ImovelContas.PERCENTUALCOLETAESGOTO, ImovelContas.PERCENTUALESGOTO,
      ImovelContas.POCO,ImovelContas.CODIGOTARIFA, ImovelContas.NUMEROCONSUMOREFESTOUROC, ImovelContas.NUMEROCONSIMOREFALTOC,
      ImovelContas.NUMEROCONSUMOREFBAIXOC, ImovelContas.NUMEROVEZESMEDIAESTOURO, ImovelContas.NUMEROVEZESMEDIAALTOCONSUMO,
      ImovelContas.PERCENTUALMEDIABAIXOCONSUMO,ImovelContas.NUMEROCONSUMOMAXIMOEC,ImovelContas.FATURAMENTOGRUPO,
      ImovelContas.CODIGOROTA, ImovelContas.CONTA,ImovelContas.TIPOCALCULOTARIFA, 
      ImovelContas.ENDERECOATENDIMENTO,ImovelContas.NUMEROFONEDDD, ImovelContas.NUMEROSEQUENCIALROTA,
      ImovelContas.MENSAGEMCONTA1, ImovelContas.MENSAGEMCONTA2,ImovelContas.MENSAGEMCONTA3,
      ImovelContas.MENSAGEMCONTA4,ImovelContas.MENSAGEMCONTA5,
      ImovelContas.NUMEROCONSUMOMINIMOVEL, ImovelContas.IDDOCUMENTOCOB,
      ImovelContas.NUMEROCODBARRASDOCCOBRANCA,ImovelContas.CPFCNPJCLIENTE,ImovelContas.NUMCONSUMOAGUAMEDIDO,ImovelContas.NUMCONSUMOAGUANAOMEDIDO,
      ImovelContas.NUMVOLUMEESGOTOMEDIDO,ImovelContas.NUMVOLUMEESGOTONAOMEDIDO,ImovelContas.DATALEITURAANTERIORNMEDIDO,
      ImovelContas.INDICADORABASTECIMENTO, ImovelContas.INDICADORIMOVELSAZONAL, ImovelContas.INDICADORPARALISARAGUA,
      ImovelContas.INDICADORPARALISARESGOTO,ImovelContas.CODIGODEBITOAUTOMATICO, ImovelContas.NUMEROCONSUMOPCALTERNATIVO, ImovelContas.DATAEMISSAODOCCOBRAN,
      ImovelContas.PERCALTERNATIVOESGOTO,ImovelContas.NUMEROCONMEDIOESGOTO, ImovelContas.INDICADORONSUMOREALAGUA, ImovelContas.NUMERODIASCORTEAGUA,
      ImovelContas.DATACORTEAGUA, ImovelContas.INDICADORRATEIONEGATIVO, ImovelContas.INDICADORIMOVCALCULADO, 
      ImovelContas.NUMEROQUADRA,
      ImovelContas.INDICADORIMOVELIMPRESSO,ImovelContas.POSICAO,ImovelContas.INDICADORIMOVELENVIADO,
      ImovelContas.MENSAGEMCONTAANORMALIDADE1, ImovelContas.MENSAGEMCONTAANORMALIDADE2,
      ImovelContas.MENSAGEMCONTAANORMALIDADE3,ImovelContas.IDLOCALIDADE,ImovelContas.IDSETORCOMERCIAL,
      ImovelContas.QUANTIDADEIMPRESSAO , ImovelContas.INDICADORNAOPERMITEIMPRESSAO,
      //[UC1305] - Carregar Arquivo Texto Impressão Simultânea de Contas / RN20140912125
      ImovelContas.DATALIGACAOAGUA, ImovelContas.DATALIGACAORESTABELECIMENTO,
      ImovelContas.INDICADORAREACOMUM, ImovelContas.POSICAOIMOVELCONDOMINIO,
      ImovelContas.INDICADORRATEIOREALIZADO, ImovelContas.ULTIMAALTERACAO,
      ImovelContas.COORDENADAX, ImovelContas.COORDENADAY, ImovelContas.INDICADORCONTINUAIMPRESSAO,
      ImovelContas.CONTA_COMUNICADO,ImovelContas.LIGACAO_AGUA_SITUACAO_INDICADOR_LEITURA_REAL};
	                                   	  	
	public String[] getColunas(){
		return colunas;
	}	
	
	public static final class ImovelContas implements BaseColumns {
		public static final String ID = "IMOV_ID";
		public static final String FATURAMENTOSITUACAOTIPO = "FTST_ID";
		public static final String NOMEGERENCIA = "IMCT_NMGERENCIA";
		public static final String LOCALIDADE = "IMCT_DSLOCALIDADE";   
		public static final String NOMEUSUARIO = "IMCT_NMCLIENTEUSUARIO";
		public static final String DATAVENCIMENTOCONTA = "IMCT_DTVENCIMENTOCONTA";
		public static final String DATAVALIDADECONTA = "IMCT_DTVALIDADECONTA";
		public static final String NUMEROINSCRICAO = "IMCT_NMINSCRICAO";
		public static final String ENDERECO = "IMCT_DSENDERECO";
		public static final String ANOMESREFERENCIACONTA = "IMCT_AMREFERENCIACONTA";   
		public static final String DIGITOVERIFICADORCONTA = "IMCT_DGVERIFICADORCONTA";
		public static final String CLIENTE = "CLIE_ID";
		public static final String RESPONSAVEL = "IMCT_NMCLIENTERESPONSAVEL";
		public static final String ENDERECOENTREGA = "IMCT_DSENDERECOENTREGA";
		public static final String SITUACAOLIGACAOAGUA = "LAST_ID";
		public static final String SITUACAOLIGACAOESGOTO = "LEST_ID";
		public static final String BANCODEBITOAUTO = "IMCT_DSBANCODEBAUTO";   
		public static final String CODIGOAGENCIADEBITOAUTO = "IMCT_CDAGENCIADEBAUTO";
		public static final String IDIMOVELCONDOMINIO = "IMCT_IDIMOVELCONDOMINIO";
		public static final String INDICADORIMOVELCONDOMINIO = "IMCT_ICIMOVELCONDOMINIO";
		public static final String PERFIL = "IPER_ID";
		public static final String NUMEROCONMEDIOAGUA = "IMCT_NNCONMEDIOAGUA";
		public static final String INDICADORFATURAGUA = "IMCT_ICFATURAGUA";   
		public static final String INDICADORFATURESGOTO = "IMCT_ICFATURESGOTO";
		public static final String INDICADOREMISSAOCONTA = "IMCT_ICEMISSAOCONTA";
		public static final String NUMEROCONSUMOMINAGUA = "IMCT_NNCONSMINAGUA";
		public static final String NUMEROCONSUMOMINESGOTO = "IMCT_NNCONSMINESGOTO";
		public static final String PERCENTUALCOLETAESGOTO = "IMCT_PCCOLETAESGOTO";
		public static final String PERCENTUALESGOTO = "IMCT_PCESGOTO";   
		public static final String POCO = "POCO_ID";  
		public static final String CODIGOTARIFA = "IMCT_CDTARIFA";
		public static final String NUMEROCONSUMOREFESTOUROC = "IMCT_NNCONSUMOREFEC";
		public static final String NUMEROCONSIMOREFALTOC = "IMCT_NNCONSIMOREFAC";
		public static final String NUMEROCONSUMOREFBAIXOC = "IMCT_NNCONSUMOREFBC";
		public static final String NUMEROVEZESMEDIAESTOURO = "IMCT_NNVEZESMEDIAESTOURO";
		public static final String NUMEROVEZESMEDIAALTOCONSUMO = "IMCT_NNVEZESMEDIAALTOCONSUMO";   
		public static final String PERCENTUALMEDIABAIXOCONSUMO = "IMCT_PCMEDIABAIXOCONSUMO";
		public static final String NUMEROCONSUMOMAXIMOEC = "IMCT_NNCONSUMOMAXIMOEC";
		public static final String FATURAMENTOGRUPO = "FTGR_ID";
		public static final String CODIGOROTA = "ROTA_CDROTA";
		public static final String CONTA = "CNTA_ID";
		public static final String TIPOCALCULOTARIFA = "IMCT_IDTARIFATIPOCALCULO";
		public static final String ENDERECOATENDIMENTO = "IMCT_ENDERECOATENDIMENTO";   
		public static final String NUMEROFONEDDD = "IMCT_NNFONEDDD";
		public static final String NUMEROSEQUENCIALROTA = "IMCT_NNSEQUENCIALROTA";
		public static final String MENSAGEMCONTA1 = "IMCT_DSMENSAGEMCONTA1";
		public static final String MENSAGEMCONTA2 = "IMCT_DSMENSAGEMCONTA2";
		public static final String MENSAGEMCONTA3 = "IMCT_DSMENSAGEMCONTA3";
		public static final String MENSAGEMCONTA4 = "IMCT_DSMENSAGEMCONTA4";
		public static final String MENSAGEMCONTA5 = "IMCT_DSMENSAGEMCONTA5";
		public static final String NUMEROCONSUMOMINIMOVEL = "IMCT_NNCONSUMOMINIMOVEL";   
		public static final String IDDOCUMENTOCOB = "IMCT_IDDOCUMENTOCOB";
		public static final String NUMEROCODBARRASDOCCOBRANCA = "IMCT_NNCODBARRASDOCCOB";
		public static final String CPFCNPJCLIENTE = "IMCT_NNCPJCNPJCLIENTE";
		public static final String NUMCONSUMOAGUAMEDIDO  = "FTSH_NNCONSUMOAGUAMEDIDO";
		public static final String NUMCONSUMOAGUANAOMEDIDO  = "FTSH_NNCONSUMOAGUANAOMEDIDO";
		public static final String NUMVOLUMEESGOTOMEDIDO  = "FTSH_NNVOLUMEESGOTOMEDIDO";
		public static final String NUMVOLUMEESGOTONAOMEDIDO  = "FTSH_NNVOLUMEESGOTONAOMEDIDO";
		public static final String DATALEITURAANTERIORNMEDIDO = "IMCT_DTLEITURAANTNMEDIDO";
		public static final String INDICADORABASTECIMENTO = "IMCT_ICABASTECIMENTO";
		public static final String INDICADORIMOVELSAZONAL = "IMCT_ICIMOVELSAZONAL";   
		public static final String INDICADORPARALISARAGUA = "IMCT_ICPARALISARAGUA";
		public static final String INDICADORPARALISARESGOTO = "IMCT_ICPARALISARESGOTO";
		public static final String CODIGODEBITOAUTOMATICO = "IMCT_CDDEBAUTOMATICO";
		public static final String PERCALTERNATIVOESGOTO = "IMCT_PCALTERNATIVOESGOTO";
		public static final String NUMEROCONSUMOPCALTERNATIVO = "IMCT_NNCONSPCALTERNATIVO";
		public static final String DATAEMISSAODOCCOBRAN = "IMCT_DTEMISSAODOCCOBRAN";   
		public static final String NUMEROCONMEDIOESGOTO = "IMCT_NNCONMEDIOESGOTO";
		public static final String INDICADORONSUMOREALAGUA = "IMCT_ICONSUMOREALAGUA";
		public static final String NUMERODIASCORTEAGUA = "IMCT_NNDIASCORTEAGUA";
		public static final String DATACORTEAGUA = "IMCT_DTCORTEAGUA";
		public static final String INDICADORRATEIONEGATIVO = "IMCT_ICRATEIONEGATIVO";
		public static final String INDICADORIMOVCALCULADO = "IMCT_ICIMOVCALCULADO";   
		public static final String INDICADORIMOVELIMPRESSO = "IMCT_ICIMOVIMPRESSO";
		public static final String INDICADORIMOVELENVIADO = "IMCT_ICIMOVENVIADO"; 
		public static final String POSICAO = "IMCT_NNPOSICAOIMOVEL";
		public static final String MENSAGEMCONTAANORMALIDADE1 = "IMCT_DSMENSCONTAANOR1";
		public static final String MENSAGEMCONTAANORMALIDADE2 = "IMCT_DSMENSCONTAANOR2";
		public static final String MENSAGEMCONTAANORMALIDADE3 = "IMCT_DSMENSCONTAANOR3";
		public static final String NUMEROQUADRA = "IMCT_NNQUADRA"; 
		public static final String IDLOCALIDADE = "LOCA_ID"; 
		public static final String IDSETORCOMERCIAL = "STCM_ID";
		public static final String QUANTIDADEIMPRESSAO = "IMCT_QTDIMPRESSAO";
		public static final String INDICADORNAOPERMITEIMPRESSAO = "IMCT_ICNAOPERMITEIMPRESSAO";
		//[UC1305] - Carregar Arquivo Texto Impressão Simultânea de Contas / RN20140912125
		public static final String DATALIGACAOAGUA = "IMCT_DTLIGACAOAGUA";
		public static final String DATALIGACAORESTABELECIMENTO = "IMCT_DTLIGACAORESTABELECIMENTO";
		
		public static final String INDICADORAREACOMUM = "IMCT_ICAREACOMUM";
		public static final String POSICAOIMOVELCONDOMINIO = "IMCT_NNPOSICAOIMOVELCONDOMINIO";
		public static final String INDICADORRATEIOREALIZADO = "IMCT_ICRATEIOREALIZADO";
		public static final String ULTIMAALTERACAO = "IMCT_TMULTIMAALTERACAO";
		public static final String COORDENADAX = "IMCT_NNCOORDENADAX";
		public static final String COORDENADAY = "IMCT_NNCOORDENADAY";
		public static final String INDICADORCONTINUAIMPRESSAO = "IMCT_ICCONTINUAIMPRESSAO";
		public static final String CONTA_COMUNICADO = "COMU_ID";
		public static final String LIGACAO_AGUA_SITUACAO_INDICADOR_LEITURA_REAL = "LAST_ICLEITURAREAL";
	}
	
	private void insertFromFile(ArrayList<String> obj, Integer posicao){
		int contador = 0;
		setPosicao(posicao);
		
		setIdString(obj.get(++contador));	
		setNomeGerenciaRegional(obj.get(++contador));	
		setLocalidade(obj.get(++contador));	
		setNomeUsuario(obj.get(++contador));
		
		setDataVencimento(Util.convertStrToDataArquivo(obj.get(++contador)));
		setDataValidadeConta(Util.convertStrToDataArquivo(obj.get(++contador)));
		
		setInscricao(obj.get(++contador));	
		setEndereco(obj.get(++contador));
		
		setAnoMesConta(Util.verificarNuloInt(obj.get(++contador)));
		setDigitoVerificadorConta(Util.verificarNuloInt(obj.get(++contador)));
		setCliente(Util.verificarNuloInt(obj.get(++contador)));
		
		setNomeResponsavel(obj.get(++contador));	
		setEnderecoEntrega(obj.get(++contador));
		
		setSituacaoLigAgua(Util.verificarNuloInt(obj.get(++contador)));
		setSituacaoLigEsgoto(Util.verificarNuloInt(obj.get(++contador)));
				
		setDescricaoBanco(obj.get(++contador));
		setCodigoAgencia(obj.get(++contador));
		
		setMatriculaCondominio(Util.verificarNuloInt(obj.get(++contador)));
		setIndcCondominio(Util.verificarNuloInt(obj.get(++contador)));
		setCodigoPerfil(Util.verificarNuloInt(obj.get(++contador)));
		setConsumoMedioLigacaoAgua(Util.verificarNuloInt(obj.get(++contador)));
		setIndcFaturamentoAgua(Util.verificarNuloInt(obj.get(++contador)));
		setIndcFaturamentoEsgoto(Util.verificarNuloInt(obj.get(++contador)));
		setIndcEmissaoConta(Util.verificarNuloInt(obj.get(++contador)));
		setConsumoMinAgua(Util.verificarNuloInt(obj.get(++contador)));
		setConsumoMinEsgoto(Util.verificarNuloInt(obj.get(++contador)));
		setPercentColetaEsgoto(Util.verificarNuloBigDecimal(obj.get(++contador)));
		setPercentCobrancaEsgoto(Util.verificarNuloBigDecimal(obj.get(++contador)));
		setTipoPoco(Util.verificarNuloInt(obj.get(++contador)));
		setCodigoTarifa(Util.verificarNuloInt(obj.get(++contador)));
		setConsumoEstouro(Util.verificarNuloInt(obj.get(++contador)));
		setAltoConsumo(Util.verificarNuloInt(obj.get(++contador)));
		setBaixoConsumo(Util.verificarNuloInt(obj.get(++contador)));
		
		setFatorMultEstouro(Util.verificarNuloBigDecimal(obj.get(++contador)));
		setFatorMultMediaAltoConsumo(Util.verificarNuloBigDecimal(obj.get(++contador)));
		setPercentBaixoConsumo(Util.verificarNuloBigDecimal(obj.get(++contador)));
		
		setConsumoMaximo(Util.verificarNuloInt(obj.get(++contador)));
		setGrupoFaturamento(Util.verificarNuloInt(obj.get(++contador)));
		setCodigoRota(Util.verificarNuloInt(obj.get(++contador)));
		setNumeroConta(Util.verificarNuloInt(obj.get(++contador)));
		setTipoCalculoTarifa(Util.verificarNuloInt(obj.get(++contador)));
		
		setEnderecoAtendimento(obj.get(++contador));
		setTelefoneLocalidadeDDD(obj.get(++contador));

		setSequencialRota(Util.verificarNuloInt(obj.get(++contador)));
		
		setMensagemConta1(obj.get(++contador));
		setMensagemConta2(obj.get(++contador));
		setMensagemConta3(obj.get(++contador));
		
		//Se a empresa for CAER
		setMensagemConta4(obj.get(++contador));
		setMensagemConta5(obj.get(++contador));
		
		setConsumoMinimoImovel(Util.verificarNuloInt(obj.get(++contador)));
		setIdDocumentoNotificacaoDebito(Util.verificarNuloInt(obj.get(++contador)));
		
		setNumeroCodigoBarraNotificacaoDebito(obj.get(++contador));
		setCpfCnpjCliente(obj.get(++contador));
		int contadorFatSituacaoTipo = ++contador; 		
		if(contadorFatSituacaoTipo != 0){
			FaturamentoSituacaoTipo fatSitTipo = new FaturamentoSituacaoTipo();
			fatSitTipo.setIdString(obj.get(contadorFatSituacaoTipo));
			setFaturamentoSituacaoTipo(fatSitTipo);
		}

		setConsumoAguaMedidoHistoricoFaturamento(Util.verificarNuloInt(obj.get(++contador)));
		setConsumoAguaNaoMedidoHistoricoFaturamento(Util.verificarNuloInt(obj.get(++contador)));
		setVolumeEsgotoMedidoHistoricoFaturamento(Util.verificarNuloInt(obj.get(++contador)));
		setVolumeEsgotoNaoMedidoHistoricoFaturamento(Util.verificarNuloInt(obj.get(++contador)));
		
		setDataLeituraAnterior(Util.convertStrToDataArquivo(obj.get(++contador)));

		setIndicadorAbastecimentoAgua(Util.verificarNuloInt(obj.get(++contador)));
		setIndicadorImovelSazonal(Util.verificarNuloInt(obj.get(++contador)));
		setIndicadorParalizarFaturamentoAgua(Util.verificarNuloInt(obj.get(++contador)));
		setIndicadorParalizarFaturamentoEsgoto(Util.verificarNuloInt(obj.get(++contador)));
		setCodigoDebitoAutomatico(Util.verificarNuloInt(obj.get(++contador)));
		setPercentualAlternativoEsgoto(Util.verificarNuloBigDecimal(obj.get(++contador)));
		setConsumoPercentualAlternativoEsgoto(Util.verificarNuloInt(obj.get(++contador)));
		
		setDataEmissaoDocumento(Util.convertStrToDataArquivo(obj.get(++contador)));

		setConsumoMedioEsgoto(Util.verificarNuloInt(obj.get(++contador)));
		setIndcConsumoReal(Util.verificarNuloInt(obj.get(++contador)));
		setNumeroDiasCorte(Util.verificarNuloInt(obj.get(++contador)));
			
		setDataCorte(Util.convertStrToDataArquivo(obj.get(++contador)));
		setIndicadorImovelRateioNegativo(Util.verificarNuloInt(obj.get(++contador)));
		setNumeroQuadra(Util.verificarNuloInt(obj.get(++contador)));
		setIdLocalidade(Util.verificarNuloInt(obj.get(++contador)));
		setIdSetorComercial(Util.verificarNuloInt(obj.get(++contador)));
		
		String date = Util.convertDateToDateStr(Util.getCurrentDateTime());	
		setUltimaAlteracao(date);
		
		setIndcAreaComum(Util.verificarNuloInt(obj.get(++contador)));
		
		//[UC1305] - Carregar Arquivo Texto Impressão Simultânea de Contas / RN20140912125
		setDataLigacaoAgua(Util.convertStrToDataArquivo(obj.get(++contador)));
		setDataLigacaoRestabelecimento(Util.convertStrToDataArquivo(obj.get(++contador)));
		
		//Seta indicadores com valor inicial
		Integer nao = new Integer(ConstantesSistema.NAO);
		setIndcImovelImpresso(nao);
		setIndcImovelCalculado(nao);
		setIndcImovelEnviado(nao);
		setIndcRateioRealizado(nao);
		
		ContaComunicado contaComunicado = new ContaComunicado();
		contaComunicado.setIdString(obj.get(++contador));
		setContaComunicado(contaComunicado);
		
		setLigacaoAguaSituacaoIndicadorLeituraReal(Util.verificarNuloShort(obj.get(++contador)));
		
	}
		
	public final class ImovelContasTipos {
		public final String ID = " INTEGER PRIMARY KEY";
		public final String FATURAMENTOSITUACAOTIPO = " INTEGER NULL";
		public final String NOMEGERENCIA = " VARCHAR(25) NOT NULL";
		public final String LOCALIDADE = " VARCHAR(25) NOT NULL";   
		public final String NOMEUSUARIO = " VARCHAR(15) NOT NULL";
		public final String DATAVENCIMENTOCONTA = " INTEGER NULL";
		public final String DATAVALIDADECONTA = " INTEGER NULL";
		public final String NUMEROINSCRICAO = " VARCHAR(16) NOT NULL";
		public final String ENDERECO = " VARCHAR(70) NULL";
		public final String ANOMESREFERENCIACONTA = " INTEGER NULL";   
		public final String DIGITOVERIFICADORCONTA = " INTEGER NULL";
		public final String CLIENTE = " INTEGER NULL";
		public final String RESPONSAVEL = " VARCHAR(50) NULL";
		public final String ENDERECOENTREGA = " VARCHAR(75) NULL";
		public final String SITUACAOLIGACAOAGUA = " NOT NULL";
		public final String SITUACAOLIGACAOESGOTO = " NOT NULL";
		public final String BANCODEBITOAUTO = " VARCHAR(15) NULL";   
		public final String CODIGOAGENCIADEBITOAUTO = " VARCHAR(5) NULL";
		public final String IDIMOVELCONDOMINIO = " INTEGER NULL";
		public final String INDICADORIMOVELCONDOMINIO = " INTEGER NULL DEFAULT 2";
		public final String PERFIL = " NOT NULL";
		public final String NUMEROONSUMOMEDIONMEDIDO = " INTEGER NOT NULL";		
		public final String INDICADORFATURAGUA = " INTEGER NULL DEFAULT 2";   
		public final String INDICADORFATURESGOTO = " INTEGER NULL DEFAULT 2";
		public final String INDICADOREMISSAOCONTA = " INTEGER NULL DEFAULT 2";
		public final String NUMEROCONSUMOMINAGUA = " INTEGER NULL";
		public final String NUMEROCONSUMOMINESGOTO = " INTEGER NULL";
		public final String PERCENTUALCOLETAESGOTO = " NUMERIC(5,2) NULL";
		public final String PERCENTUALESGOTO = " NUMERIC(5,2) NULL";   
		public final String POCO = " INTEGER NULL";  
		public final String CODIGOTARIFA = " INTEGER NOT NULL";
		public final String NUMEROCONSUMOREFESTOUROC = " INTEGER NOT NULL";
		public final String NUMEROCONSIMOREFALTOC = " INTEGER NOT NULL";
		public final String NUMEROCONSUMOREFBAIXOC = " INTEGER NOT NULL";		
		public final String NUMEROVEZESMEDIAESTOURO = " NUMERIC(3,1) NULL";
		public final String NUMEROVEZESMEDIAALTOCONSUMO = " NUMERIC(3,1) NULL";   
		public final String PERCENTUALMEDIABAIXOCONSUMO = " NUMERIC(5,2) NULL";
		public final String NUMEROCONSUMOMAXIMOEC = " INTEGER NOT NULL";
		public final String FATURAMENTOGRUPO = " INTEGER NOT NULL";
		public final String CODIGOROTA = " INTEGER NOT NULL";
		public final String CONTA = " INTEGER NULL";
		public final String TIPOCALCULOTARIFA = " INTEGER NOT NULL";
		public final String ENDERECOATENDIMENTO = " VARCHAR(70) NULL";   
		public final String NUMEROFONEDDD = " VARCHAR(11) NULL";
		public final String NUMEROSEQUENCIALROTA = " INTEGER NULL";
		public final String MENSAGEMCONTA1 = " VARCHAR(100) NULL";
		public final String MENSAGEMCONTA2 = " VARCHAR(100) NULL";
		public final String MENSAGEMCONTA3 = " VARCHAR(100) NULL";
		public final String MENSAGEMCONTA4 = " VARCHAR(100) NULL";
		public final String MENSAGEMCONTA5 = " VARCHAR(100) NULL";		
		public final String NUMEROCONSUMOMINIMOVEL = " INTEGER NOT NULL";   
		public final String IDDOCUMENTOCOB = " INTEGER NULL";
		public final String NUMEROCODBARRASDOCCOBRANCA = " VARCHAR(48) NULL";
		public final String CPFCNPJCLIENTE = " VARCHAR(14) NULL";
		public final String NUMCONSUMOAGUAMEDIDO  = " INTEGER NULL";
		public final String NUMCONSUMOAGUANAOMEDIDO  = " INTEGER NULL";
		public final String NUMVOLUMEESGOTOMEDIDO  = " INTEGER NULL";
		public final String NUMVOLUMEESGOTONAOMEDIDO  = " INTEGER NULL";
		public final String DATALEITURAANTERIORNMEDIDO = " INTEGER NOT NULL";		
		public final String INDICADORABASTECIMENTO = " INTEGER NULL DEFAULT 2";
		public final String INDICADORIMOVELSAZONAL = " INTEGER NULL DEFAULT 2";   
		public final String INDICADORPARALISARAGUA = " INTEGER NULL DEFAULT 2";
		public final String INDICADORPARALISARESGOTO = " INTEGER NULL DEFAULT 2";
		public final String CODIGODEBITOAUTOMATICO = " INTEGER NULL";
		public final String PERCALTERNATIVOESGOTO = " NUMERIC(5,2) NULL";
		public final String NUMEROCONSUMOPCALTERNATIVO = " INTEGER NULL";
		public final String DATAEMISSAODOCCOBRAN = " INTEGER NULL";   
		public final String NUMEROCONMEDIOESGOTO = " INTEGER NULL";
		
		public final String INDICADORONSUMOREALAGUA = " INTEGER NULL DEFAULT 2";
		public final String NUMERODIASCORTEAGUA = " INTEGER NULL";
		public final String DATACORTEAGUA = " INTEGER NULL";
		public final String INDICADORRATEIONEGATIVO = " INTEGER NULL DEFAULT 2";
		public final String INDICADORIMOVCALCULADO = " INTEGER NULL DEFAULT 2";   
		public final String INDICADORIMOVELIMPRESSO = " INTEGER NULL DEFAULT 2";
		public final String INDICADORIMOVELENVIADO = " INTEGER NULL DEFAULT 2"; 
		public final String POSICAO = " INTEGER NOT NULL";
		public final String MENSAGEMCONTAANORMALIDADE1 = " VARCHAR(120) NULL";
		public final String MENSAGEMCONTAANORMALIDADE2 = " VARCHAR(120) NULL";
		public final String MENSAGEMCONTAANORMALIDADE3 = " VARCHAR(120) NULL";		
		public final String NUMEROQUADRA = " VARCHAR(100) NULL"; 
		public final String IDLOCALIDADE = " INTEGER NOT NULL"; 
		public final String IDSETORCOMERCIAL = " INTEGER NOT NULL";
		public final String QUANTIDADEIMPRESSAO = " INTEGER NULL DEFAULT 0";
		public final String INDICADORNAOPERMITEIMPRESSAO = " INTEGER NULL DEFAULT 2";
		//[UC1305] - Carregar Arquivo Texto Impressão Simultânea de Contas / RN20140912125
		public final String DATALIGACAOAGUA = " INTEGER NULL";
		public final String DATALIGACAORESTABELECIMENTO = " INTEGER NULL";
		
		public final String INDICADORAREACOMUM = " INTEGER NULL DEFAULT 2";
		public final String POSICAOIMOVELCONDOMINIO = " INTEGER NULL";
		public final String INDICADORRATEIOREALIZADO = " INTEGER NULL DEFAULT 2";
		public final String ULTIMAALTERACAO = " INTEGER NOT NULL";
		public final String COORDENADAX = " NUMERIC(18,12) NULL";   
		public final String COORDENADAY = " NUMERIC(18,12) NULL";
		public final String INDICADORCONTINUAIMPRESSAO = " INTEGER NULL DEFAULT 2";
		public final String CONTA_COMUNICADO = " INTEGER NULL";
		public final String LIGACAO_AGUA_SITUACAO_INDICADOR_LEITURA_REAL = " INTEGER NULL";
		
		private String[] tipos = new String[] {ID, FATURAMENTOSITUACAOTIPO, 
			   NOMEGERENCIA,
		       LOCALIDADE, NOMEUSUARIO, DATAVENCIMENTOCONTA,
		       DATAVALIDADECONTA, NUMEROINSCRICAO, ENDERECO,
		       ANOMESREFERENCIACONTA, DIGITOVERIFICADORCONTA, CLIENTE,RESPONSAVEL,
		       ENDERECOENTREGA, SITUACAOLIGACAOAGUA, SITUACAOLIGACAOESGOTO,
		       BANCODEBITOAUTO,CODIGOAGENCIADEBITOAUTO, IDIMOVELCONDOMINIO, INDICADORIMOVELCONDOMINIO,
		       PERFIL, NUMEROONSUMOMEDIONMEDIDO, INDICADORFATURAGUA,
		       INDICADORFATURESGOTO, INDICADOREMISSAOCONTA, NUMEROCONSUMOMINAGUA,
		      NUMEROCONSUMOMINESGOTO, PERCENTUALCOLETAESGOTO, PERCENTUALESGOTO,
		      POCO,CODIGOTARIFA, NUMEROCONSUMOREFESTOUROC, NUMEROCONSIMOREFALTOC,
		      NUMEROCONSUMOREFBAIXOC, NUMEROVEZESMEDIAESTOURO, NUMEROVEZESMEDIAALTOCONSUMO,
		      PERCENTUALMEDIABAIXOCONSUMO,NUMEROCONSUMOMAXIMOEC,FATURAMENTOGRUPO,
		      CODIGOROTA, CONTA,TIPOCALCULOTARIFA, 
		      ENDERECOATENDIMENTO,NUMEROFONEDDD, NUMEROSEQUENCIALROTA,
		      MENSAGEMCONTA1, MENSAGEMCONTA2,MENSAGEMCONTA3,MENSAGEMCONTA4,MENSAGEMCONTA5,
		      NUMEROCONSUMOMINIMOVEL, IDDOCUMENTOCOB,
		      NUMEROCODBARRASDOCCOBRANCA,CPFCNPJCLIENTE,NUMCONSUMOAGUAMEDIDO,NUMCONSUMOAGUANAOMEDIDO,
		      NUMVOLUMEESGOTOMEDIDO,NUMVOLUMEESGOTONAOMEDIDO,DATALEITURAANTERIORNMEDIDO,
		      INDICADORABASTECIMENTO, INDICADORIMOVELSAZONAL, INDICADORPARALISARAGUA,
		      INDICADORPARALISARESGOTO,CODIGODEBITOAUTOMATICO, NUMEROCONSUMOPCALTERNATIVO, DATAEMISSAODOCCOBRAN,
		      PERCALTERNATIVOESGOTO,NUMEROCONMEDIOESGOTO, INDICADORONSUMOREALAGUA, NUMERODIASCORTEAGUA,
		      DATACORTEAGUA, INDICADORRATEIONEGATIVO, INDICADORIMOVCALCULADO, 
		      NUMEROQUADRA,
		      INDICADORIMOVELIMPRESSO,POSICAO,INDICADORIMOVELENVIADO,
		      MENSAGEMCONTAANORMALIDADE1, MENSAGEMCONTAANORMALIDADE2,
		      MENSAGEMCONTAANORMALIDADE3,IDLOCALIDADE,IDSETORCOMERCIAL,
		      QUANTIDADEIMPRESSAO , INDICADORNAOPERMITEIMPRESSAO,
		      //[UC1305] - Carregar Arquivo Texto Impressão Simultânea de Contas / RN20140912125
		      DATALIGACAOAGUA, DATALIGACAORESTABELECIMENTO,
		      INDICADORAREACOMUM, POSICAOIMOVELCONDOMINIO, 
		      INDICADORRATEIOREALIZADO, ULTIMAALTERACAO, COORDENADAX, COORDENADAY, 
		      INDICADORCONTINUAIMPRESSAO,CONTA_COMUNICADO,LIGACAO_AGUA_SITUACAO_INDICADOR_LEITURA_REAL};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
	
	public ContentValues preencherValues() {
		ContentValues values = new ContentValues();
		
		values.put(ImovelContas.ID, getId());
		values.put(ImovelContas.ANOMESREFERENCIACONTA, getAnoMesConta());
		values.put(ImovelContas.POSICAO, getPosicao());
		values.put(ImovelContas.BANCODEBITOAUTO, getDescricaoBanco());
		values.put(ImovelContas.CLIENTE, getCliente());
		values.put(ImovelContas.CODIGOAGENCIADEBITOAUTO, getCodigoAgencia());
		values.put(ImovelContas.CODIGODEBITOAUTOMATICO, getCodigoDebitoAutomatico());
		values.put(ImovelContas.CODIGOROTA, getCodigoRota());
		values.put(ImovelContas.CONTA, getNumeroConta());
		values.put(ImovelContas.CPFCNPJCLIENTE, getCpfCnpjCliente());
		
		if(getDataCorte()!=null){
			values.put(ImovelContas.DATACORTEAGUA, getDataCorte().getTime());
		}
		if(getDataEmissaoDocumento()!=null){
			values.put(ImovelContas.DATAEMISSAODOCCOBRAN, getDataEmissaoDocumento().getTime());
		}
		if(getDataValidadeConta()!=null){
			values.put(ImovelContas.DATAVALIDADECONTA, getDataValidadeConta().getTime());
		}
	
		if(getDataLeituraAnterior()!=null){
			values.put(ImovelContas.DATALEITURAANTERIORNMEDIDO, getDataLeituraAnterior().getTime());
		}
		if(getDataVencimento()!=null){
			values.put(ImovelContas.DATAVENCIMENTOCONTA, getDataVencimento().getTime());
		}
		
		values.put(ImovelContas.DIGITOVERIFICADORCONTA, getDigitoVerificadorConta());
		values.put(ImovelContas.ENDERECO, getEndereco());
		values.put(ImovelContas.ENDERECOATENDIMENTO, getEnderecoAtendimento());
		values.put(ImovelContas.ENDERECOENTREGA, getEnderecoEntrega());
		values.put(ImovelContas.FATURAMENTOGRUPO, getGrupoFaturamento());
		if(getFaturamentoSituacaoTipo()!=null){
			values.put(ImovelContas.FATURAMENTOSITUACAOTIPO, getFaturamentoSituacaoTipo().getId());
		}
		values.put(ImovelContas.IDDOCUMENTOCOB, getIdDocumentoNotificacaoDebito());
		values.put(ImovelContas.IDIMOVELCONDOMINIO, getMatriculaCondominio());
		values.put(ImovelContas.INDICADORABASTECIMENTO, getIndicadorAbastecimentoAgua());
		values.put(ImovelContas.INDICADOREMISSAOCONTA, getIndcEmissaoConta());
		values.put(ImovelContas.INDICADORFATURAGUA, getIndcFaturamentoAgua());
		values.put(ImovelContas.INDICADORFATURESGOTO, getIndcFaturamentoEsgoto());
		values.put(ImovelContas.INDICADORIMOVCALCULADO, getIndcImovelCalculado());
		values.put(ImovelContas.INDICADORIMOVELCONDOMINIO, getIndcCondominio());
		values.put(ImovelContas.INDICADORIMOVELENVIADO, getIndcImovelEnviado());
		values.put(ImovelContas.INDICADORIMOVELIMPRESSO, getIndcImovelImpresso());
		values.put(ImovelContas.INDICADORIMOVELSAZONAL, getIndicadorImovelSazonal());
		values.put(ImovelContas.INDICADORONSUMOREALAGUA, getIndcConsumoReal());
		values.put(ImovelContas.INDICADORPARALISARAGUA, getIndicadorParalizarFaturamentoAgua());
		values.put(ImovelContas.INDICADORPARALISARESGOTO, getIndicadorParalizarFaturamentoEsgoto());
		values.put(ImovelContas.INDICADORRATEIONEGATIVO, getIndicadorImovelRateioNegativo());
		values.put(ImovelContas.LOCALIDADE, getLocalidade());
		values.put(ImovelContas.MENSAGEMCONTA1, getMensagemConta1());
		values.put(ImovelContas.MENSAGEMCONTA2, getMensagemConta2());
		values.put(ImovelContas.MENSAGEMCONTA3, getMensagemConta3());
		values.put(ImovelContas.MENSAGEMCONTA4, getMensagemConta4());
		values.put(ImovelContas.MENSAGEMCONTA5, getMensagemConta5());
		values.put(ImovelContas.NOMEGERENCIA, getNomeGerenciaRegional());
		values.put(ImovelContas.NOMEUSUARIO, getNomeUsuario());
		values.put(ImovelContas.NUMEROCODBARRASDOCCOBRANCA, getNumeroCodigoBarraNotificacaoDebito());
		values.put(ImovelContas.NUMEROCONMEDIOESGOTO, getConsumoMedioEsgoto());
		values.put(ImovelContas.NUMEROCONSIMOREFALTOC, getAltoConsumo());
		values.put(ImovelContas.NUMEROCONSUMOMAXIMOEC, getConsumoMaximo());
		values.put(ImovelContas.NUMEROCONSUMOMINAGUA, getConsumoMinAgua());
		values.put(ImovelContas.NUMEROCONSUMOMINESGOTO, getConsumoMinEsgoto());
		values.put(ImovelContas.NUMEROCONSUMOMINIMOVEL, getConsumoMinimoImovel());
		values.put(ImovelContas.NUMEROCONSUMOPCALTERNATIVO, getConsumoPercentualAlternativoEsgoto());
		values.put(ImovelContas.NUMEROCONSUMOREFBAIXOC, getBaixoConsumo());
		values.put(ImovelContas.NUMEROCONSUMOREFESTOUROC, getConsumoEstouro());
		values.put(ImovelContas.NUMERODIASCORTEAGUA, getNumeroDiasCorte());
		values.put(ImovelContas.NUMEROFONEDDD, getTelefoneLocalidadeDDD());
		values.put(ImovelContas.NUMEROINSCRICAO, getInscricao());
		values.put(ImovelContas.NUMEROCONMEDIOAGUA, getConsumoMedioLigacaoAgua());
		values.put(ImovelContas.NUMEROSEQUENCIALROTA, getSequencialRota());
		if(getFatorMultMediaAltoConsumo() != null){
			values.put(ImovelContas.NUMEROVEZESMEDIAALTOCONSUMO, getFatorMultMediaAltoConsumo().toString());
		}
		if(getFatorMultEstouro() != null){
			values.put(ImovelContas.NUMEROVEZESMEDIAESTOURO, getFatorMultEstouro().toString());
		}
		if(getPercentualAlternativoEsgoto() != null){
			values.put(ImovelContas.PERCALTERNATIVOESGOTO, getPercentualAlternativoEsgoto().toString());
		}
		if(getPercentColetaEsgoto() != null){
			values.put(ImovelContas.PERCENTUALCOLETAESGOTO, getPercentColetaEsgoto().toString());
		}
		if(getPercentCobrancaEsgoto() != null){
			values.put(ImovelContas.PERCENTUALESGOTO, getPercentCobrancaEsgoto().toString());
		}
		if(getPercentBaixoConsumo() != null){
			values.put(ImovelContas.PERCENTUALMEDIABAIXOCONSUMO, getPercentBaixoConsumo().toString());
		}
		values.put(ImovelContas.PERFIL, getCodigoPerfil());
		values.put(ImovelContas.POCO, getTipoPoco());
		values.put(ImovelContas.RESPONSAVEL, getNomeResponsavel());
		values.put(ImovelContas.SITUACAOLIGACAOAGUA, getSituacaoLigAgua());
		values.put(ImovelContas.SITUACAOLIGACAOESGOTO, getSituacaoLigEsgoto());
		values.put(ImovelContas.TIPOCALCULOTARIFA, getTipoCalculoTarifa());
		values.put(ImovelContas.CODIGOTARIFA, getCodigoTarifa());
		values.put(ImovelContas.NUMCONSUMOAGUAMEDIDO,getConsumoAguaMedidoHistoricoFaturamento());
		values.put(ImovelContas.NUMCONSUMOAGUANAOMEDIDO,getConsumoAguaNaoMedidoHistoricoFaturamento());
		values.put(ImovelContas.NUMVOLUMEESGOTOMEDIDO,getVolumeEsgotoMedidoHistoricoFaturamento());
		values.put(ImovelContas.NUMVOLUMEESGOTONAOMEDIDO,getVolumeEsgotoNaoMedidoHistoricoFaturamento());
		values.put(ImovelContas.NUMEROQUADRA, getNumeroQuadra());
		values.put(ImovelContas.MENSAGEMCONTAANORMALIDADE1, getMensagemContaAnormalidade1());
		values.put(ImovelContas.MENSAGEMCONTAANORMALIDADE2, getMensagemContaAnormalidade2());
		values.put(ImovelContas.MENSAGEMCONTAANORMALIDADE3, getMensagemContaAnormalidade3());
		values.put(ImovelContas.IDLOCALIDADE, getIdLocalidade());
		values.put(ImovelContas.IDSETORCOMERCIAL, getIdSetorComercial());
		
		if(getQntVezesImpressaoConta()!=null){
			values.put(ImovelContas.QUANTIDADEIMPRESSAO, getQntVezesImpressaoConta());
		}
		
		if(getIndcNaoPermiteImpressao()!=null){
			values.put(ImovelContas.INDICADORNAOPERMITEIMPRESSAO, getIndcNaoPermiteImpressao());
		}
		//[UC1305] - Carregar Arquivo Texto Impressão Simultânea de Contas / RN20140912125
		if (getDataLigacaoAgua() != null) {
			values.put(ImovelContas.DATALIGACAOAGUA, getDataLigacaoAgua().getTime());
		}
		if (getDataLigacaoRestabelecimento() != null) {
			values.put(ImovelContas.DATALIGACAORESTABELECIMENTO, getDataLigacaoRestabelecimento().getTime());
		}
		
		values.put(ImovelContas.INDICADORAREACOMUM, getIndcAreaComum());
		values.put(ImovelContas.POSICAOIMOVELCONDOMINIO, getPosicaoImovelCondominio());
		
		values.put(ImovelContas.INDICADORRATEIOREALIZADO, getIndcRateioRealizado());
		
		values.put(ImovelContas.ULTIMAALTERACAO, (new Date()).getTime());
		
		if ( getNumeroCoordenadaX()!= null ) {
			values.put(ImovelContas.COORDENADAX, getNumeroCoordenadaX().toString());
		}
		
		if ( getNumeroCoordenadaY() != null ) {
			values.put(ImovelContas.COORDENADAY, getNumeroCoordenadaY().toString());
		}
		
		values.put(ImovelContas.INDICADORCONTINUAIMPRESSAO, getIndcContinuaImpressao());
		
		if(getContaComunicado()!=null){
			values.put(ImovelContas.CONTA_COMUNICADO, getContaComunicado().getId());
		}
		
		if(getLigacaoAguaSituacaoIndicadorLeituraReal()!=null){
			values.put(ImovelContas.LIGACAO_AGUA_SITUACAO_INDICADOR_LEITURA_REAL, getLigacaoAguaSituacaoIndicadorLeituraReal());
		}
		
		return values;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ImovelConta> preencherObjetos(Cursor cursor) {		
			
		int codigo = cursor.getColumnIndex(ImovelContas.ID);
		int amConta = cursor.getColumnIndex(ImovelContas.ANOMESREFERENCIACONTA);
		int bancoDebAuto = cursor.getColumnIndex(ImovelContas.BANCODEBITOAUTO);
		int cliente = cursor.getColumnIndex(ImovelContas.CLIENTE);
		int codAgenciaDebAuto = cursor.getColumnIndex(ImovelContas.CODIGOAGENCIADEBITOAUTO);
		int codDebitoAuto = cursor.getColumnIndex(ImovelContas.CODIGODEBITOAUTOMATICO);
		int codigoRota = cursor.getColumnIndex(ImovelContas.CODIGOROTA);
		int conta = cursor.getColumnIndex(ImovelContas.CONTA);
		int cpfCnpjCliente = cursor.getColumnIndex(ImovelContas.CPFCNPJCLIENTE);
		int dataCorteAgua = cursor.getColumnIndex(ImovelContas.DATACORTEAGUA);
		int dataEmissaoDocCobranca = cursor.getColumnIndex(ImovelContas.DATAEMISSAODOCCOBRAN);
		int dataLeituraAntMedido = cursor.getColumnIndex(ImovelContas.DATALEITURAANTERIORNMEDIDO);
		int dataValidadeConta = cursor.getColumnIndex(ImovelContas.DATAVALIDADECONTA);
		int dataVencimentoConta = cursor.getColumnIndex(ImovelContas.DATAVENCIMENTOCONTA);
		int digitoVerificador = cursor.getColumnIndex(ImovelContas.DIGITOVERIFICADORCONTA);
		int endereco = cursor.getColumnIndex(ImovelContas.ENDERECO);
		int enderecoAtendimento = cursor.getColumnIndex(ImovelContas.ENDERECOATENDIMENTO);
		int enderecoEntrega = cursor.getColumnIndex(ImovelContas.ENDERECOENTREGA);
		int faturamentoGrupo = cursor.getColumnIndex(ImovelContas.FATURAMENTOGRUPO);
		int faturamentoSitTipo = cursor.getColumnIndex(ImovelContas.FATURAMENTOSITUACAOTIPO);
		int consumoAguaMedido = cursor.getColumnIndex(ImovelContas.NUMCONSUMOAGUAMEDIDO);
		int consumoAguaNaoMedido = cursor.getColumnIndex(ImovelContas.NUMCONSUMOAGUANAOMEDIDO);
		int volumeEsgotoMedido = cursor.getColumnIndex(ImovelContas.NUMVOLUMEESGOTOMEDIDO);
		int volumeEsgotoNaoMedido = cursor.getColumnIndex(ImovelContas.NUMVOLUMEESGOTONAOMEDIDO);
		int idDocumentoCobranca = cursor.getColumnIndex(ImovelContas.IDDOCUMENTOCOB);
		int idImovelCondominio = cursor.getColumnIndex(ImovelContas.IDIMOVELCONDOMINIO);
		int indicadorAbastecimento = cursor.getColumnIndex(ImovelContas.INDICADORABASTECIMENTO);
		int indicadorEmissaoConta = cursor.getColumnIndex(ImovelContas.INDICADOREMISSAOCONTA);
		int indicadorFaturaAgua = cursor.getColumnIndex(ImovelContas.INDICADORFATURAGUA);
		int indicadorFaturaEsgoto = cursor.getColumnIndex(ImovelContas.INDICADORFATURESGOTO);
		int indicadorImovelCalculado = cursor.getColumnIndex(ImovelContas.INDICADORIMOVCALCULADO);
		int indicadorImovelCondominio = cursor.getColumnIndex(ImovelContas.INDICADORIMOVELCONDOMINIO);
		int indicadorImovelEnviado = cursor.getColumnIndex(ImovelContas.INDICADORIMOVELENVIADO);
		int indicadorImovelImpresso = cursor.getColumnIndex(ImovelContas.INDICADORIMOVELIMPRESSO);
		int indicadorImovelSazonal = cursor.getColumnIndex(ImovelContas.INDICADORIMOVELSAZONAL);
		int indicadorConsumoRealAgua = cursor.getColumnIndex(ImovelContas.INDICADORONSUMOREALAGUA);
		int indicadorParalisarAgua = cursor.getColumnIndex(ImovelContas.INDICADORPARALISARAGUA);
		int indicadorParalisarEsgoto = cursor.getColumnIndex(ImovelContas.INDICADORPARALISARESGOTO);
		int indicadorRateioNegativo = cursor.getColumnIndex(ImovelContas.INDICADORRATEIONEGATIVO);
		int localidade = cursor.getColumnIndex(ImovelContas.LOCALIDADE);
		int mensagemConta1 = cursor.getColumnIndex(ImovelContas.MENSAGEMCONTA1);
		int mensagemConta2 = cursor.getColumnIndex(ImovelContas.MENSAGEMCONTA2);
		int mensagemConta3 = cursor.getColumnIndex(ImovelContas.MENSAGEMCONTA3);
		int mensagemConta4 = cursor.getColumnIndex(ImovelContas.MENSAGEMCONTA4);
		int mensagemConta5 = cursor.getColumnIndex(ImovelContas.MENSAGEMCONTA5);
		int nomeGerencia = cursor.getColumnIndex(ImovelContas.NOMEGERENCIA);
		int nomeUsuario = cursor.getColumnIndex(ImovelContas.NOMEUSUARIO);
		int numeroCodBarrasDocumento = cursor.getColumnIndex(ImovelContas.NUMEROCODBARRASDOCCOBRANCA);
		int numeroConsMedioEsgoto = cursor.getColumnIndex(ImovelContas.NUMEROCONMEDIOESGOTO);
		int numeroConsAltoConsumo = cursor.getColumnIndex(ImovelContas.NUMEROCONSIMOREFALTOC);
		int numeroConsMaxEstouro = cursor.getColumnIndex(ImovelContas.NUMEROCONSUMOMAXIMOEC);
		int numeroConsMinimoAgua = cursor.getColumnIndex(ImovelContas.NUMEROCONSUMOMINAGUA);
		int numeroConsMinimoEsgoto = cursor.getColumnIndex(ImovelContas.NUMEROCONSUMOMINESGOTO);
		int numeroConsMinimoImovel = cursor.getColumnIndex(ImovelContas.NUMEROCONSUMOMINIMOVEL);
		int numeroConsPercAlternativo = cursor.getColumnIndex(ImovelContas.NUMEROCONSUMOPCALTERNATIVO);
		int numeroConsBaixoConsumo = cursor.getColumnIndex(ImovelContas.NUMEROCONSUMOREFBAIXOC);
		int numeroConsEstouro = cursor.getColumnIndex(ImovelContas.NUMEROCONSUMOREFESTOUROC);
		int numeroDiasCorte = cursor.getColumnIndex(ImovelContas.NUMERODIASCORTEAGUA);
		int numeroFoneDDD = cursor.getColumnIndex(ImovelContas.NUMEROFONEDDD);
		int numeroInscricao = cursor.getColumnIndex(ImovelContas.NUMEROINSCRICAO);
		int numeroConsMedioAgua = cursor.getColumnIndex(ImovelContas.NUMEROCONMEDIOAGUA);
		int numeroSequencialRota = cursor.getColumnIndex(ImovelContas.NUMEROSEQUENCIALROTA);
		int fatorAltoConsumo = cursor.getColumnIndex(ImovelContas.NUMEROVEZESMEDIAALTOCONSUMO);
		int fatorEstouro = cursor.getColumnIndex(ImovelContas.NUMEROVEZESMEDIAESTOURO);
		int percentualAlternEsgoto = cursor.getColumnIndex(ImovelContas.PERCALTERNATIVOESGOTO);
		int percentualColetaEsgoto = cursor.getColumnIndex(ImovelContas.PERCENTUALCOLETAESGOTO);
		int percentualEsgoto = cursor.getColumnIndex(ImovelContas.PERCENTUALESGOTO);
		int percentualMediaBaixoCons= cursor.getColumnIndex(ImovelContas.PERCENTUALMEDIABAIXOCONSUMO);
		int perfil = cursor.getColumnIndex(ImovelContas.PERFIL);
		int poco = cursor.getColumnIndex(ImovelContas.POCO);
		int codigoTarifa = cursor.getColumnIndex(ImovelContas.CODIGOTARIFA);
		int responsavel = cursor.getColumnIndex(ImovelContas.RESPONSAVEL);
		int situacaoLigacaoAgua = cursor.getColumnIndex(ImovelContas.SITUACAOLIGACAOAGUA);
		int situacaoLigacaoEsgoto = cursor.getColumnIndex(ImovelContas.SITUACAOLIGACAOESGOTO);				
		int tipoCalculoTarifa = cursor.getColumnIndex(ImovelContas.TIPOCALCULOTARIFA);			
		int posicao = cursor.getColumnIndex(ImovelContas.POSICAO);			
		int quadra = cursor.getColumnIndex(ImovelContas.NUMEROQUADRA);
		int cdTarifa = cursor.getColumnIndex(ImovelContas.CODIGOTARIFA);
		int mensagemContaAnormalidade1 = cursor.getColumnIndex(ImovelContas.MENSAGEMCONTAANORMALIDADE1);
		int mensagemContaAnormalidade2 = cursor.getColumnIndex(ImovelContas.MENSAGEMCONTAANORMALIDADE2);
		int mensagemContaAnormalidade3 = cursor.getColumnIndex(ImovelContas.MENSAGEMCONTAANORMALIDADE3);
		int idLocalidade = cursor.getColumnIndex(ImovelContas.IDLOCALIDADE);
		int idSetorComercial = cursor.getColumnIndex(ImovelContas.IDSETORCOMERCIAL);
		int quantidadeVezesImpressaoConta = cursor.getColumnIndex(ImovelContas.QUANTIDADEIMPRESSAO);
		int indicadorNaoPermiteImpressao = cursor.getColumnIndex(ImovelContas.INDICADORNAOPERMITEIMPRESSAO);
		//[UC1305] - Carregar Arquivo Texto Impressão Simultânea de Contas / RN20140912125
		int dataLigacaoAgua = cursor.getColumnIndex(ImovelContas.DATALIGACAOAGUA);
		int dataLigacaoRestabelecimento = cursor.getColumnIndex(ImovelContas.DATALIGACAORESTABELECIMENTO);
		
		int indicadorAreaComum = cursor.getColumnIndex(ImovelContas.INDICADORAREACOMUM);
		int posicaoImvCond = cursor.getColumnIndex(ImovelContas.POSICAOIMOVELCONDOMINIO);
		int rateioRealizado = cursor.getColumnIndex(ImovelContas.INDICADORRATEIOREALIZADO);
		
		//DataUltimaAlteracao não é carrado no objetos pois
		//não é utilizado em nenhum canto do sistema.
		//Correcao devido a RM5420
		//int ultimaAlteracao = cursor.getColumnIndex(ImovelContas.ULTIMAALTERACAO);
		
		int coordenadaX = cursor.getColumnIndex(ImovelContas.COORDENADAX);
		int coordenadaY = cursor.getColumnIndex(ImovelContas.COORDENADAY);
		int indicadorContinuaImpressao = cursor.getColumnIndex(ImovelContas.INDICADORCONTINUAIMPRESSAO);
		int contaComunicado = cursor.getColumnIndex(ImovelContas.CONTA_COMUNICADO);
		int ligacaoAguaSituacaoIndicadorLeituraReal = cursor.getColumnIndex(ImovelContas.LIGACAO_AGUA_SITUACAO_INDICADOR_LEITURA_REAL);
		
		ArrayList<ImovelConta> imoveisContas = new ArrayList<ImovelConta>();
		
		do{
			ImovelConta imovelConta = new ImovelConta();
			
			if (Util.getIntBanco(cursor,ImovelContas.FATURAMENTOSITUACAOTIPO,faturamentoSitTipo) != null){
		
				FaturamentoSituacaoTipo obFaturamento;
				try {
					obFaturamento = (FaturamentoSituacaoTipo) RepositorioBasico.getInstance()
							.pesquisarPorId(cursor.getInt(faturamentoSitTipo), new FaturamentoSituacaoTipo());
	
					imovelConta.setFaturamentoSituacaoTipo(obFaturamento);
				} catch (RepositorioException e) {
					Log.e( ConstantesSistema.CATEGORIA , e.getMessage());
					e.printStackTrace();
				}
			}
							
			imovelConta.setId(cursor.getInt(codigo));
			imovelConta.setAnoMesConta(Util.getIntBanco(cursor, ImovelContas.ANOMESREFERENCIACONTA, amConta));
			imovelConta.setDescricaoBanco(cursor.getString(bancoDebAuto));
			imovelConta.setCodigoAgencia(cursor.getString(codAgenciaDebAuto));
			imovelConta.setCliente(Util.getIntBanco(cursor, ImovelContas.CLIENTE, cliente));
			imovelConta.setCodigoDebitoAutomatico(Util.getIntBanco(cursor, ImovelContas.CODIGODEBITOAUTOMATICO, codDebitoAuto));
			imovelConta.setCodigoRota(Util.getIntBanco(cursor, ImovelContas.CODIGOROTA, codigoRota));
			imovelConta.setNumeroConta(Util.getIntBanco(cursor, ImovelContas.CONTA,conta));
			imovelConta.setCpfCnpjCliente(cursor.getString(cpfCnpjCliente ));
		
			imovelConta.setDataCorte(Util.getDataBanco(cursor, ImovelContas.DATACORTEAGUA, dataCorteAgua));
			
			imovelConta.setDataEmissaoDocumento(Util.getDataBanco(cursor, ImovelContas.DATAEMISSAODOCCOBRAN, dataEmissaoDocCobranca));			
			
			imovelConta.setDataLeituraAnterior(Util.getDataBanco(cursor, ImovelContas.DATALEITURAANTERIORNMEDIDO, dataLeituraAntMedido));
			
			imovelConta.setDataValidadeConta(Util.getDataBanco(cursor, ImovelContas.DATAVALIDADECONTA, dataValidadeConta));
			
			imovelConta.setDataVencimento(Util.getDataBanco(cursor, ImovelContas.DATAVENCIMENTOCONTA, dataVencimentoConta));
			
			imovelConta.setDigitoVerificadorConta(Util.getIntBanco(cursor, ImovelContas.DIGITOVERIFICADORCONTA,digitoVerificador));
			imovelConta.setCodigoTarifa(Util.getIntBanco(cursor, ImovelContas.CODIGOTARIFA,cdTarifa));
			imovelConta.setEndereco(cursor.getString(endereco));
			imovelConta.setEnderecoAtendimento(cursor.getString(enderecoAtendimento));
			imovelConta.setEnderecoEntrega(cursor.getString(enderecoEntrega));
			imovelConta.setGrupoFaturamento(Util.getIntBanco(cursor, ImovelContas.FATURAMENTOGRUPO,faturamentoGrupo));
							
			imovelConta.setIdDocumentoNotificacaoDebito(Util.getIntBanco(cursor, ImovelContas.IDDOCUMENTOCOB,idDocumentoCobranca));
			imovelConta.setMatriculaCondominio(Util.getIntBanco(cursor, ImovelContas.IDIMOVELCONDOMINIO,idImovelCondominio));
			imovelConta.setIndicadorAbastecimentoAgua(Util.getIntBanco(cursor, ImovelContas.INDICADORABASTECIMENTO,indicadorAbastecimento));
			imovelConta.setIndcEmissaoConta(Util.getIntBanco(cursor, ImovelContas.INDICADOREMISSAOCONTA,indicadorEmissaoConta));
			imovelConta.setIndcFaturamentoAgua(Util.getIntBanco(cursor, ImovelContas.INDICADORFATURAGUA,indicadorFaturaAgua));
			imovelConta.setIndcFaturamentoEsgoto(Util.getIntBanco(cursor, ImovelContas.INDICADORFATURESGOTO,indicadorFaturaEsgoto));
			imovelConta.setIndcImovelCalculado(Util.getIntBanco(cursor, ImovelContas.INDICADORIMOVCALCULADO,indicadorImovelCalculado));
			imovelConta.setIndcCondominio(Util.getIntBanco(cursor, ImovelContas.INDICADORIMOVELCONDOMINIO,indicadorImovelCondominio));
			imovelConta.setIndcImovelEnviado(Util.getIntBanco(cursor, ImovelContas.INDICADORIMOVELENVIADO,indicadorImovelEnviado));
			imovelConta.setIndcImovelImpresso(Util.getIntBanco(cursor, ImovelContas.INDICADORIMOVELIMPRESSO,indicadorImovelImpresso));
			imovelConta.setIndicadorImovelSazonal(Util.getIntBanco(cursor, ImovelContas.INDICADORIMOVELSAZONAL,indicadorImovelSazonal));
			imovelConta.setIndcConsumoReal(Util.getIntBanco(cursor, ImovelContas.INDICADORONSUMOREALAGUA,indicadorConsumoRealAgua));
			imovelConta.setIndicadorParalizarFaturamentoAgua(Util.getIntBanco(cursor, ImovelContas.INDICADORPARALISARAGUA,indicadorParalisarAgua));
	
			imovelConta.setIndicadorParalizarFaturamentoEsgoto(Util.getIntBanco(cursor, ImovelContas.INDICADORPARALISARESGOTO,indicadorParalisarEsgoto));
			imovelConta.setIndicadorImovelRateioNegativo(Util.getIntBanco(cursor, ImovelContas.INDICADORRATEIONEGATIVO,indicadorRateioNegativo));
			imovelConta.setLocalidade(cursor.getString(localidade));
			imovelConta.setMensagemConta1(cursor.getString(mensagemConta1));
			imovelConta.setMensagemConta2(cursor.getString(mensagemConta2));
			imovelConta.setMensagemConta3(cursor.getString(mensagemConta3));
			imovelConta.setMensagemConta4(cursor.getString(mensagemConta4));
			imovelConta.setMensagemConta5(cursor.getString(mensagemConta5));
			imovelConta.setNomeGerenciaRegional(cursor.getString(nomeGerencia));
			imovelConta.setNomeUsuario(cursor.getString(nomeUsuario));
			imovelConta.setNumeroCodigoBarraNotificacaoDebito(cursor.getString(numeroCodBarrasDocumento));
			imovelConta.setConsumoMedioEsgoto(Util.getIntBanco(cursor, ImovelContas.NUMEROCONMEDIOESGOTO,numeroConsMedioEsgoto));
			imovelConta.setAltoConsumo(Util.getIntBanco(cursor, ImovelContas.NUMEROCONSIMOREFALTOC,numeroConsAltoConsumo));
			imovelConta.setConsumoMaximo(Util.getIntBanco(cursor, ImovelContas.NUMEROCONSUMOMAXIMOEC,numeroConsMaxEstouro));
			imovelConta.setConsumoMinEsgoto(Util.getIntBanco(cursor, ImovelContas.NUMEROCONSUMOMINESGOTO,numeroConsMinimoEsgoto));
			imovelConta.setConsumoMinAgua(Util.getIntBanco(cursor, ImovelContas.NUMEROCONSUMOMINAGUA,numeroConsMinimoAgua));
			imovelConta.setConsumoMinimoImovel(Util.getIntBanco(cursor, ImovelContas.NUMEROCONSUMOMINIMOVEL,numeroConsMinimoImovel));
			imovelConta.setConsumoPercentualAlternativoEsgoto(Util.getIntBanco(cursor, ImovelContas.NUMEROCONSUMOPCALTERNATIVO,numeroConsPercAlternativo));
			imovelConta.setBaixoConsumo(Util.getIntBanco(cursor, ImovelContas.NUMEROCONSUMOREFBAIXOC,numeroConsBaixoConsumo));
			imovelConta.setConsumoEstouro(Util.getIntBanco(cursor, ImovelContas.NUMEROCONSUMOREFESTOUROC,numeroConsEstouro));
			imovelConta.setNumeroDiasCorte(Util.getIntBanco(cursor, ImovelContas.NUMERODIASCORTEAGUA,numeroDiasCorte));
			imovelConta.setTelefoneLocalidadeDDD(cursor.getString(numeroFoneDDD));
			imovelConta.setInscricao(cursor.getString(numeroInscricao ));
			imovelConta.setConsumoMedioLigacaoAgua(Util.getIntBanco(cursor, ImovelContas.NUMEROCONMEDIOAGUA,numeroConsMedioAgua));
			imovelConta.setSequencialRota(Util.getIntBanco(cursor, ImovelContas.NUMEROSEQUENCIALROTA,numeroSequencialRota));
			imovelConta.setFatorMultMediaAltoConsumo(Util.getDoubleBanco(cursor, ImovelContas.NUMEROVEZESMEDIAALTOCONSUMO,fatorAltoConsumo));
			imovelConta.setFatorMultEstouro(Util.getDoubleBanco(cursor, ImovelContas.NUMEROVEZESMEDIAESTOURO,fatorEstouro));
			imovelConta.setPercentualAlternativoEsgoto(Util.getDoubleBanco(cursor, ImovelContas.PERCALTERNATIVOESGOTO,percentualAlternEsgoto));
			imovelConta.setPercentColetaEsgoto(Util.getDoubleBanco(cursor, ImovelContas.PERCENTUALCOLETAESGOTO,percentualColetaEsgoto));
			imovelConta.setPercentCobrancaEsgoto(Util.getDoubleBanco(cursor, ImovelContas.PERCENTUALESGOTO,percentualEsgoto));
			imovelConta.setPercentBaixoConsumo(Util.getDoubleBanco(cursor, ImovelContas.PERCENTUALMEDIABAIXOCONSUMO,percentualMediaBaixoCons));
			imovelConta.setCodigoPerfil(Util.getIntBanco(cursor, ImovelContas.PERFIL,perfil));
			imovelConta.setTipoPoco(Util.getIntBanco(cursor, ImovelContas.POCO,poco));
			
			imovelConta.setNomeResponsavel(cursor.getString(responsavel ));
			imovelConta.setSituacaoLigAgua(Util.getIntBanco(cursor, ImovelContas.SITUACAOLIGACAOAGUA,situacaoLigacaoAgua));
			imovelConta.setSituacaoLigEsgoto(Util.getIntBanco(cursor, ImovelContas.SITUACAOLIGACAOESGOTO,situacaoLigacaoEsgoto));
			imovelConta.setTipoCalculoTarifa(Util.getIntBanco(cursor, ImovelContas.TIPOCALCULOTARIFA,tipoCalculoTarifa));	
			imovelConta.setPosicao(Util.getIntBanco(cursor, ImovelContas.POSICAO,posicao));		
			imovelConta.setCodigoTarifa(Util.getIntBanco(cursor, ImovelContas.CODIGOTARIFA,codigoTarifa));
			imovelConta.setMensagemContaAnormalidade1(cursor.getString(mensagemContaAnormalidade1));
			imovelConta.setMensagemContaAnormalidade2(cursor.getString(mensagemContaAnormalidade2));
			imovelConta.setMensagemContaAnormalidade3(cursor.getString(mensagemContaAnormalidade3));
			imovelConta.setNumeroQuadra(Util.getIntBanco(cursor, ImovelContas.NUMEROQUADRA,quadra));					
			imovelConta.setConsumoAguaMedidoHistoricoFaturamento(Util.getIntBanco(cursor, ImovelContas.NUMCONSUMOAGUAMEDIDO,consumoAguaMedido));			
			imovelConta.setConsumoAguaNaoMedidoHistoricoFaturamento(Util.getIntBanco(cursor, ImovelContas.NUMERODIASCORTEAGUA,consumoAguaNaoMedido));	
			imovelConta.setVolumeEsgotoMedidoHistoricoFaturamento(Util.getIntBanco(cursor, ImovelContas.NUMVOLUMEESGOTOMEDIDO,volumeEsgotoMedido));	
			imovelConta.setVolumeEsgotoNaoMedidoHistoricoFaturamento(Util.getIntBanco(cursor, ImovelContas.NUMVOLUMEESGOTONAOMEDIDO,volumeEsgotoNaoMedido));	
			imovelConta.setIdLocalidade(Util.getIntBanco(cursor, ImovelContas.LOCALIDADE,idLocalidade));	
			imovelConta.setIdSetorComercial(Util.getIntBanco(cursor, ImovelContas.IDSETORCOMERCIAL,idSetorComercial));	
			imovelConta.setQntVezesImpressaoConta(Util.getIntBanco(cursor, ImovelContas.QUANTIDADEIMPRESSAO,quantidadeVezesImpressaoConta));
			imovelConta.setIndcNaoPermiteImpressao(Util.getIntBanco(cursor, ImovelContas.INDICADORNAOPERMITEIMPRESSAO,indicadorNaoPermiteImpressao));
			//[UC1305] - Carregar Arquivo Texto Impressão Simultânea de Contas / RN20140912125
			imovelConta.setDataLigacaoAgua(Util.getDataBanco(cursor, ImovelContas.DATALIGACAOAGUA,dataLigacaoAgua));
			imovelConta.setDataLigacaoRestabelecimento(Util.getDataBanco(cursor, ImovelContas.DATALIGACAORESTABELECIMENTO,dataLigacaoRestabelecimento));
			
			imovelConta.setIndcAreaComum(Util.getIntBanco(cursor, ImovelContas.INDICADORAREACOMUM,indicadorAreaComum));
			imovelConta.setPosicaoImovelCondominio(Util.getIntBanco(cursor, ImovelContas.POSICAOIMOVELCONDOMINIO,posicaoImvCond));
			imovelConta.setIndcRateioRealizado(Util.getIntBanco(cursor, ImovelContas.INDICADORRATEIOREALIZADO,rateioRealizado));
				
			//DataUltimaAlteracao não é carrado no objetos pois
			//não é utilizado em nenhum canto do sistema.
			//Correcao devido a RM5420
			//imovelConta.setUltimaAlteracao(cursor.getString(ultimaAlteracao));
			
			imovelConta.setNumeroCoordenadaX(Util.getDoubleBanco(cursor, ImovelContas.COORDENADAX, coordenadaX));
			imovelConta.setNumeroCoordenadaY(Util.getDoubleBanco(cursor, ImovelContas.COORDENADAY, coordenadaY));
			imovelConta.setIndcContinuaImpressao(Util.getIntBanco(cursor, ImovelContas.INDICADORCONTINUAIMPRESSAO,indicadorContinuaImpressao));
			
			if (Util.getIntBanco(cursor,ImovelContas.CONTA_COMUNICADO,contaComunicado) != null){
				
				ContaComunicado objetoContaComunicado;
				try {
					objetoContaComunicado = (ContaComunicado) RepositorioBasico.getInstance()
							.pesquisarPorId(cursor.getInt(contaComunicado), new ContaComunicado());
	
					imovelConta.setContaComunicado(objetoContaComunicado);
				} catch (RepositorioException e) {
					Log.e( ConstantesSistema.CATEGORIA , e.getMessage());
					e.printStackTrace();
				}
			}
			
			imovelConta.setLigacaoAguaSituacaoIndicadorLeituraReal(cursor.getShort(ligacaoAguaSituacaoIndicadorLeituraReal));
			
			imoveisContas.add(imovelConta);
			
		}while (cursor.moveToNext());
		
		return imoveisContas;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ImovelConta other = (ImovelConta) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public boolean isCondominio(){
		return this.getIndcCondominio().equals(ConstantesSistema.SIM) || 
				(this.getMatriculaCondominio()!=null && this.getMatriculaCondominio().intValue()!=0);
	}
	
	public String getNomeTabela(){
		return "imovel_conta";
	}
	
	public String getNameId(){
		return "IMOV_ID";
	}

	public Integer getIndcContinuaImpressao() {
		return indcContinuaImpressao;
	}

	public void setIndcContinuaImpressao(Integer indcContinuaImpressao) {
		this.indcContinuaImpressao = indcContinuaImpressao;
	}
	
	public boolean ehUltimoImovelCondominio() throws ControladorException{
		if ( isCondominio() && this.getMatriculaCondominio() != null ){
			return this.getId().intValue() == ControladorImovelConta.getInstance().obterIdUltimoImovelMicro( this.getMatriculaCondominio() ).intValue();
		} else {
			return false;
		}
	}
}