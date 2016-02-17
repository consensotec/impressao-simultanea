
package com.br.ipad.isc.repositorios;

import com.br.ipad.isc.bean.CategoriaSubcategoria;
import com.br.ipad.isc.bean.ContaComunicado;
import com.br.ipad.isc.bean.ContaComunicado.ContaComunicadosTipos;
import com.br.ipad.isc.bean.LogFinalizacao;
import com.br.ipad.isc.bean.CategoriaSubcategoria.CategoriasSubcategorias;
import com.br.ipad.isc.bean.CategoriaSubcategoria.CategoriasSubcategoriasTipos;
import com.br.ipad.isc.bean.ConsumoAnormalidade;
import com.br.ipad.isc.bean.ConsumoAnormalidade.ConsumoAnormalidadesTipos;
import com.br.ipad.isc.bean.ConsumoAnormalidadeAcao;
import com.br.ipad.isc.bean.ConsumoAnormalidadeAcao.ConsumoAnormalidadeAcoesTipos;
import com.br.ipad.isc.bean.ConsumoAnteriores;
import com.br.ipad.isc.bean.ConsumoAnteriores.ConsumosAnterioresTipos;
import com.br.ipad.isc.bean.ConsumoHistorico;
import com.br.ipad.isc.bean.ConsumoHistorico.ConsumosHistoricosTipos;
import com.br.ipad.isc.bean.ConsumoTarifaCategoria;
import com.br.ipad.isc.bean.ConsumoTarifaCategoria.ConsumosTarifasCategorias;
import com.br.ipad.isc.bean.ConsumoTarifaCategoria.ConsumosTarifasCategoriasTipos;
import com.br.ipad.isc.bean.ConsumoTarifaFaixa;
import com.br.ipad.isc.bean.ConsumoTarifaFaixa.ConsumosTarifasFaixas;
import com.br.ipad.isc.bean.ConsumoTarifaFaixa.ConsumosTarifasFaixasTipos;
import com.br.ipad.isc.bean.ConsumoTipo;
import com.br.ipad.isc.bean.ConsumoTipo.ConsumosTiposTipos;
import com.br.ipad.isc.bean.ContaCategoria;
import com.br.ipad.isc.bean.ContaCategoria.ContasCategorias;
import com.br.ipad.isc.bean.ContaCategoria.ContasCategoriasTipos;
import com.br.ipad.isc.bean.ContaCategoriaConsumoFaixa;
import com.br.ipad.isc.bean.ContaCategoriaConsumoFaixa.ContasCategoriasConsumosFaixas;
import com.br.ipad.isc.bean.ContaCategoriaConsumoFaixa.ContasCategoriasConsumosFaixasTipos;
import com.br.ipad.isc.bean.ContaDebito;
import com.br.ipad.isc.bean.ContaDebito.ContasDebitosTipos;
import com.br.ipad.isc.bean.ContaImposto;
import com.br.ipad.isc.bean.ContaImposto.ContaImpostosTipos;
import com.br.ipad.isc.bean.CreditoRealizado;
import com.br.ipad.isc.bean.CreditoRealizado.CreditoRealizadoTipos;
import com.br.ipad.isc.bean.DebitoCobrado;
import com.br.ipad.isc.bean.DebitoCobrado.DebitosCobradosTipos;
import com.br.ipad.isc.bean.FaturamentoSituacaoTipo;
import com.br.ipad.isc.bean.FaturamentoSituacaoTipo.FaturamentoSituacaoTipoTipos;
import com.br.ipad.isc.bean.Foto;
import com.br.ipad.isc.bean.Foto.FotosTipos;
import com.br.ipad.isc.bean.HidrometroInstalado;
import com.br.ipad.isc.bean.HidrometroInstalado.HidrometrosInstaladosTipos;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.bean.ImovelConta.ImovelContasTipos;
import com.br.ipad.isc.bean.ImovelRevisitar;
import com.br.ipad.isc.bean.ImovelRevisitar.ImoveisRevisitarTipos;
import com.br.ipad.isc.bean.LeituraAnormalidade;
import com.br.ipad.isc.bean.LeituraAnormalidade.LeiturasAnormalidadesTipos;
import com.br.ipad.isc.bean.LogFinalizacao.LogFinalizacoesTipos;
import com.br.ipad.isc.bean.QualidadeAgua;
import com.br.ipad.isc.bean.QualidadeAgua.QualidadeAguaTipos;
import com.br.ipad.isc.bean.SequencialRotaMarcacao;
import com.br.ipad.isc.bean.SequencialRotaMarcacao.SequencialRotaMarcacoesTipos;
import com.br.ipad.isc.bean.SistemaParametros;
import com.br.ipad.isc.bean.SistemaParametros.SistemasParametrosTipos;

public final class BDScript {
	
	/**
	 * Retorna script para geração do banco de dados
	 * 
	 */
	public String[] obterScriptBanco(){
		
		//Inicia variáveis
		SequencialRotaMarcacoesTipos sequencialRotaMarcacoes = (new SequencialRotaMarcacao()).new SequencialRotaMarcacoesTipos();
		SistemasParametrosTipos sistemaParametros = (new SistemaParametros()).new SistemasParametrosTipos();
		ConsumoAnormalidadesTipos consumoAnormalidade = (new ConsumoAnormalidade()).new ConsumoAnormalidadesTipos();
		ImovelContasTipos imovelConta = (new ImovelConta()).new ImovelContasTipos();
		HidrometrosInstaladosTipos hidrometroInstalado = (new HidrometroInstalado()).new HidrometrosInstaladosTipos();
		ConsumosHistoricosTipos consumoHistorico = (new ConsumoHistorico()).new ConsumosHistoricosTipos();
		ImoveisRevisitarTipos imovelRevisitar = (new ImovelRevisitar()).new ImoveisRevisitarTipos();
		CategoriasSubcategoriasTipos categoriaSubcategoria = (new CategoriaSubcategoria()).new CategoriasSubcategoriasTipos();
		ConsumoAnormalidadeAcoesTipos consumoAnormalidadeAcao = (new ConsumoAnormalidadeAcao()).new ConsumoAnormalidadeAcoesTipos();
		LeiturasAnormalidadesTipos leituraAnormalidade = (new LeituraAnormalidade()).new LeiturasAnormalidadesTipos();
		QualidadeAguaTipos qualidadeAgua = (new QualidadeAgua()).new QualidadeAguaTipos();
		FaturamentoSituacaoTipoTipos faturamentoSituacaoTipo = (new FaturamentoSituacaoTipo()).new FaturamentoSituacaoTipoTipos();
		CreditoRealizadoTipos creditoRealizado = (new CreditoRealizado()).new CreditoRealizadoTipos();
		ContaImpostosTipos contaImposto = (new ContaImposto()).new ContaImpostosTipos();
		ConsumosTarifasCategoriasTipos consumoTarifaCategoria = (new ConsumoTarifaCategoria()).new ConsumosTarifasCategoriasTipos();
		ConsumosTarifasFaixasTipos consumosTarifaFaixa = (new ConsumoTarifaFaixa()).new ConsumosTarifasFaixasTipos();
		ContasCategoriasTipos contaCategoria = (new ContaCategoria()).new ContasCategoriasTipos();
		ContasCategoriasConsumosFaixasTipos contaCategoriaConsumoFaixa = (new ContaCategoriaConsumoFaixa()).new ContasCategoriasConsumosFaixasTipos();
		ConsumosAnterioresTipos consumoAnteriores = (new ConsumoAnteriores()).new ConsumosAnterioresTipos();
		ContasDebitosTipos contaDebito = (new ContaDebito()).new ContasDebitosTipos();
		DebitosCobradosTipos debitoCobrado = (new DebitoCobrado()).new DebitosCobradosTipos();
		ConsumosTiposTipos consumoTipo = (new ConsumoTipo()).new ConsumosTiposTipos();
		FotosTipos foto = (new Foto()).new FotosTipos();
		LogFinalizacoesTipos logFinalizacao = (new LogFinalizacao()).new LogFinalizacoesTipos();
		ContaComunicadosTipos contaComunicadosTipos = (new ContaComunicado()).new ContaComunicadosTipos();
		
		SequencialRotaMarcacao SequencialRotaMarcacao = new SequencialRotaMarcacao();
		SistemaParametros SistemaParametros = new SistemaParametros();
		ConsumoAnormalidade ConsumoAnormalidade = new ConsumoAnormalidade();
		ImovelConta ImovelConta = new ImovelConta();
		HidrometroInstalado HidrometroInstalado = new HidrometroInstalado();
		ConsumoHistorico ConsumoHistorico = new ConsumoHistorico();
		ImovelRevisitar ImovelRevisitar = new ImovelRevisitar();
		CategoriaSubcategoria CategoriaSubcategoria = new CategoriaSubcategoria();
		ConsumoAnormalidadeAcao ConsumoAnormalidadeAcao = new ConsumoAnormalidadeAcao();
		LeituraAnormalidade LeituraAnormalidade = new LeituraAnormalidade();
		QualidadeAgua QualidadeAgua = new QualidadeAgua();
		FaturamentoSituacaoTipo FaturamentoSituacaoTipo = new FaturamentoSituacaoTipo();
		CreditoRealizado CreditoRealizado = new CreditoRealizado();
		ContaImposto ContaImposto = new ContaImposto();
		ConsumoTarifaCategoria ConsumoTarifaCategoria = new ConsumoTarifaCategoria();
		ConsumoTarifaFaixa ConsumoTarifaFaixa = new ConsumoTarifaFaixa();
		ContaCategoria ContaCategoria = new ContaCategoria();
		ContaCategoriaConsumoFaixa ContaCategoriaConsumoFaixa = new ContaCategoriaConsumoFaixa();
		ConsumoAnteriores ConsumoAnteriores = new ConsumoAnteriores();
		ContaDebito ContaDebito = new ContaDebito();
		DebitoCobrado DebitoCobrado = new DebitoCobrado();
		ConsumoTipo ConsumoTipo = new ConsumoTipo();
		Foto Foto = new Foto();
		LogFinalizacao LogFinalizacao = new LogFinalizacao();
		ContaComunicado contaComunicado = new ContaComunicado();
		
		//Usa o método createTable para criar o script de cada tabela e junta-os num array
		String[] SCRIPT_CRIAR_BANCO = new String[] {
				                                                            
			createTable(LeituraAnormalidade.getNomeTabela(), LeituraAnormalidade.getColunas(), 
					leituraAnormalidade.getTipos()).toString(),
			
			createTable(QualidadeAgua.getNomeTabela(), QualidadeAgua.getColunas(), 
					qualidadeAgua.getTipos()).toString(),
			  
			createTable(FaturamentoSituacaoTipo.getNomeTabela(), FaturamentoSituacaoTipo.getColunas(), 
					faturamentoSituacaoTipo.getTipos()).toString(),
			
			createTable(SistemaParametros.getNomeTabela(), SistemaParametros.getColunas(), 
					sistemaParametros.getTipos()).toString(),	
			
			createTable(ConsumoAnormalidade.getNomeTabela(), ConsumoAnormalidade.getColunas(), 
					consumoAnormalidade.getTipos()).toString(),	
			
			createTable(ImovelConta.getNomeTabela(), ImovelConta.getColunas(), 
					imovelConta.getTipos()).toString(),
					 
			createTable(CreditoRealizado.getNomeTabela(), CreditoRealizado.getColunas(), 
					creditoRealizado.getTipos()).toString(),
			
			createTable(ContaImposto.getNomeTabela(), ContaImposto.getColunas(), 
					contaImposto.getTipos()).toString(),
				
			createTable(HidrometroInstalado.getNomeTabela(), HidrometroInstalado.getColunas(), 
					hidrometroInstalado.getTipos()).toString(),
					
			createTable(ConsumoHistorico.getNomeTabela(), ConsumoHistorico.getColunas(), 
					consumoHistorico.getTipos()).toString(),	
					
			createTable(ImovelRevisitar.getNomeTabela(), ImovelRevisitar.getColunas(), 
					imovelRevisitar.getTipos()).toString(),	
				
			createTable(ConsumoTarifaCategoria.getNomeTabela(), ConsumoTarifaCategoria.getColunas(), 
					consumoTarifaCategoria.getTipos()).toString(),	
			
			createTable(ConsumoTarifaFaixa.getNomeTabela(), ConsumoTarifaFaixa.getColunas(), 
					consumosTarifaFaixa.getTipos()).toString(),
			
			createTable(ContaCategoria.getNomeTabela(), ContaCategoria.getColunas(), 
					contaCategoria.getTipos()).toString(),
			
			createTable(ContaCategoriaConsumoFaixa.getNomeTabela(), ContaCategoriaConsumoFaixa.getColunas(), 
					contaCategoriaConsumoFaixa.getTipos()).toString(),
			
			createTable(ConsumoAnteriores.getNomeTabela(), ConsumoAnteriores.getColunas(), 
					consumoAnteriores.getTipos()).toString(),
			
			createTable(ContaDebito.getNomeTabela(), ContaDebito.getColunas(), 
					contaDebito.getTipos()).toString(),
				
			createTable(CategoriaSubcategoria.getNomeTabela(), CategoriaSubcategoria.getColunas(), 
					categoriaSubcategoria.getTipos()).toString(),
					
			createTable(DebitoCobrado.getNomeTabela(), DebitoCobrado.getColunas(), 
					debitoCobrado.getTipos()).toString(),
			
			createTable(ConsumoAnormalidadeAcao.getNomeTabela(), ConsumoAnormalidadeAcao.getColunas(), 
					consumoAnormalidadeAcao.getTipos()).toString(),
				
			createTable(ConsumoTipo.getNomeTabela(), ConsumoTipo.getColunas(), 
					consumoTipo.getTipos()).toString(),
				
			createTable(Foto.getNomeTabela(), Foto.getColunas(), 
					foto.getTipos()).toString(),
				
			createTable(SequencialRotaMarcacao.getNomeTabela(), SequencialRotaMarcacao.getColunas(), 
					sequencialRotaMarcacoes.getTipos()).toString(),

			createTable(LogFinalizacao.getNomeTabela(), LogFinalizacao.getColunas(), 
					logFinalizacao.getTipos()).toString(),
					
			"CREATE UNIQUE INDEX consumo_tarifa_categoria_idx ON "+new ConsumoTarifaCategoria().getNomeTabela()+ "("+
					ConsumosTarifasCategorias.CONSUMOTARIFA+","+ConsumosTarifasCategorias.IDCATEGORIA +","+
					ConsumosTarifasCategorias.IDSUBCATEGORIA+","+ConsumosTarifasCategorias.DATAVIGENCIA+  ");",
					
			"CREATE UNIQUE INDEX consumo_tarifa_faixa_idx ON "+new ConsumoTarifaFaixa().getNomeTabela()+ "("+
					ConsumosTarifasFaixas.CONSUMOTARIFACATEGORIA+","+ ConsumosTarifasFaixas.DATAVIGENCIA+","+
					ConsumosTarifasFaixas.CONSUMOFAIXAINICIO + ");",
					
			"CREATE UNIQUE INDEX categoria_subcategoria_idx ON "+new CategoriaSubcategoria().getNomeTabela()+ "("+
					CategoriasSubcategorias.MATRICULA+","+ CategoriasSubcategorias.IDCATEGORIA+","+
					CategoriasSubcategorias.IDSUBCATEGORIA + ");",
					
			"CREATE UNIQUE INDEX conta_categoria_idx ON "+new ContaCategoria().getNomeTabela()+ "("+
					ContasCategorias.CATEGORIASUBCATEGORIA+","+ ContasCategorias.TIPOLIGACAO + ");",
					
			"CREATE UNIQUE INDEX conta_catg_cons_fx_idx ON "+new ContaCategoriaConsumoFaixa().getNomeTabela()+ "("+
					ContasCategoriasConsumosFaixas.CONTACATEGORIA+","+ ContasCategoriasConsumosFaixas.NUMCONSUMOINICIAL + ");",
					
			createTable(contaComunicado.getNomeTabela(), contaComunicado.getColunas(), 
					contaComunicadosTipos.getTipos()).toString(),

		};
		
		return SCRIPT_CRIAR_BANCO;
	}
	
	public String[] obterScriptExcluirBanco(){

		//Usa o método createTable para criar o script de cada tabela e junta-os num array
		String[] SCRIPT_DELETAR_BANCO = new String[] {


				"DROP TABLE IF EXISTS "+new SequencialRotaMarcacao().getNomeTabela()+";",
				"DROP TABLE IF EXISTS "+new Foto().getNomeTabela()+";",
				"DROP TABLE IF EXISTS "+new ConsumoTipo().getNomeTabela()+";",
				"DROP TABLE IF EXISTS "+new ConsumoAnormalidadeAcao().getNomeTabela()+";",
				"DROP TABLE IF EXISTS "+new DebitoCobrado().getNomeTabela()+";",
				"DROP TABLE IF EXISTS "+new CategoriaSubcategoria().getNomeTabela()+";",
				"DROP TABLE IF EXISTS "+new ContaDebito().getNomeTabela()+";",
				"DROP TABLE IF EXISTS "+new ConsumoAnteriores().getNomeTabela()+";",
				"DROP TABLE IF EXISTS "+new ContaCategoriaConsumoFaixa().getNomeTabela()+";",
				"DROP TABLE IF EXISTS "+new ContaCategoria().getNomeTabela()+";",
				"DROP TABLE IF EXISTS "+new ConsumoTarifaFaixa().getNomeTabela()+";",
				"DROP TABLE IF EXISTS "+new ConsumoTarifaCategoria().getNomeTabela()+";",
				"DROP TABLE IF EXISTS "+new ImovelRevisitar().getNomeTabela()+";",
				"DROP TABLE IF EXISTS "+new ConsumoHistorico().getNomeTabela()+";",
				"DROP TABLE IF EXISTS "+new HidrometroInstalado().getNomeTabela()+";",
				"DROP TABLE IF EXISTS "+new ContaImposto().getNomeTabela()+";",
				"DROP TABLE IF EXISTS "+new CreditoRealizado().getNomeTabela()+";",
				"DROP TABLE IF EXISTS "+new ImovelConta().getNomeTabela()+";",
				"DROP TABLE IF EXISTS "+new ConsumoAnormalidade().getNomeTabela()+";",
				"DROP TABLE IF EXISTS "+new SistemaParametros().getNomeTabela()+";",
				"DROP TABLE IF EXISTS "+new FaturamentoSituacaoTipo().getNomeTabela()+";",
				"DROP TABLE IF EXISTS "+new QualidadeAgua().getNomeTabela()+";",
				"DROP TABLE IF EXISTS "+new LeituraAnormalidade().getNomeTabela()+";",
				"DROP TABLE IF EXISTS "+new LogFinalizacao().getNomeTabela()+";",
				"DROP TABLE IF EXISTS "+new ContaComunicado().getNomeTabela()+";"

		};

		
		return SCRIPT_DELETAR_BANCO;
	}
	
	/**
	 * Com base nos array dos nomes de tabela e tipos constantes nas classes básicas
	 * cria a query de criação da tabela
	 * 
	 * @param String nomeTabela, String[] colunas, String[] tipos
	 * @return StringBuilder
	 */
	private StringBuilder createTable(String nomeTabela, String[] colunas, String[] tipos){
		StringBuilder retorno = new StringBuilder(" CREATE TABLE "+nomeTabela+" ( ");
		for(int i=0;i<colunas.length;i++){
			retorno.append(colunas[i]+tipos[i]);
			if(i!=(colunas.length-1)){
				retorno.append(", ");
			}
		}
		retorno.append(" );");
		return retorno;
	}

}