package com.br.ipad.isc.repositorios;

import com.br.ipad.isc.bean.ContaCategoria;
import com.br.ipad.isc.excecoes.RepositorioException;

public interface IRepositorioContaCategoria {
	
	public ContaCategoria buscarContaCategoriaPorCategoriaSubcategoriaId(Integer categoriaSubcategoriaId, Integer tipoMedicao) throws RepositorioException;
	public Double obterValorTotal(Integer imovelId, Integer tipoMedicao) throws RepositorioException;
	public void removerImovelContaCategoria(Integer idImovel,int tipoLigacao) throws RepositorioException;
}
