package com.br.ipad.isc.repositorios;

import com.br.ipad.isc.bean.ConsumoHistorico;
import com.br.ipad.isc.excecoes.RepositorioException;

public interface IRepositorioConsumoHistorico {
	
	public ConsumoHistorico buscarConsumoHistoricoPorImovelIdLigacaoTipo(Integer imovelId, Integer ligacaoTipo) throws RepositorioException;
	public Integer obterConsumoImoveisMicro(Integer idImovelMacro,Integer tipoLigacao) throws RepositorioException;
	public Integer obterQuantidadeRegistroConsumoHistorico() throws RepositorioException;	
	
}
