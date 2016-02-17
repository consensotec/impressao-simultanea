
package com.br.ipad.isc.controladores;

import android.content.Context;

import com.br.ipad.isc.bean.ConsumoHistorico;
import com.br.ipad.isc.bean.HidrometroInstalado;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.excecoes.ControladorException;

public interface IControladorConsumoHistorico {
	
	public void setContext(Context ctx );

	public ConsumoHistorico buscarConsumoHistoricoPorImovelIdLigacaoTipo(Integer imovelId, Integer ligacaoTipo) throws ControladorException;
	
	/**
	 * [SB0010] - Ajuste do Consumo para o Múltiplo da Quantidade de Economias
	 */
	public void ajustarConsumo(ImovelConta imovel, ConsumoHistorico consumo, int qtdEconomias, Integer leituraAnterior, 
			int tipoMedicao) throws ControladorException;

	
	/**
	 * [UC0101] - Consistir Leituras e Calcular Consumos [SF0017] - Ajuste
	 * Mensal de Consumo
	 */
	public void ajusteMensalConsumo(ImovelConta imovel, 
			HidrometroInstalado hidrometroInstalado, int tipoMedicao, ConsumoHistorico consumo) throws ControladorException;
	
	public Integer obterConsumoImoveisMicro(Integer idImovelMacro, Integer tipoLigacao) throws ControladorException;
	
	public Integer obterQuantidadeRegistroConsumoHistorico() throws ControladorException;
}