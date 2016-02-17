package com.br.ipad.isc.repositorios;

import java.util.ArrayList;

import com.br.ipad.isc.bean.ContaCategoriaConsumoFaixa;
import com.br.ipad.isc.excecoes.RepositorioException;

public interface IRepositorioContaCategoriaConsumoFaixa {
	
	public ArrayList<ContaCategoriaConsumoFaixa> buscarContasCategoriasConsumosFaixasPorPorContaCategoriaId(Integer idContaCategoria) throws RepositorioException;
	
	public void removerContaCategoriaConsumoFaixa(String[] idsContaCategoria) throws RepositorioException;

	public Double obterTotalConsumoContasCategoriasConsumosFaixasPorPorContaCategoriaId
 		(Integer idContaCategoria) throws RepositorioException;
	
	public Double obterTotalValorTarifaContasCategoriasConsumosFaixasPorPorContaCategoriaId(
			Integer idContaCategoria) throws RepositorioException;
}
