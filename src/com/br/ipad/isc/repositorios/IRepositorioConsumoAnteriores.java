package com.br.ipad.isc.repositorios;

import java.util.List;

import com.br.ipad.isc.bean.ConsumoAnteriores;
import com.br.ipad.isc.excecoes.RepositorioException;

public interface IRepositorioConsumoAnteriores {
	
	public List<ConsumoAnteriores> buscarConsumoAnterioresPorImovelId(Integer imovelId) throws RepositorioException;
	public List<ConsumoAnteriores> buscarConsumoAnterioresPorImovelAnormalidade(Integer imovelId, Integer idAnormalidadeConsumo) 
			throws RepositorioException;
	public List<ConsumoAnteriores> buscarConsumoAnterioresPorImovelTipoLigacao(Integer imovelId, Integer tipoMedicao) 
			throws RepositorioException;
	public ConsumoAnteriores buscarConsumoAnterioresPorImovelAnoMesPorTipoLigacao(Integer imovelId, Integer anoMes,Integer tipoValidacao) throws RepositorioException;
	public ConsumoAnteriores buscarConsumoAnterioresPorImovelAnormalidade(Integer imovelId, Integer idAnormalidadeConsumo,
			Integer anoMes) throws RepositorioException;
}
