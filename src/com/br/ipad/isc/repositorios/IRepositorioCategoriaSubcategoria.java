package com.br.ipad.isc.repositorios;

import java.util.Collection;

import com.br.ipad.isc.bean.CategoriaSubcategoria;
import com.br.ipad.isc.excecoes.RepositorioException;

public interface IRepositorioCategoriaSubcategoria {
	
	public Collection<CategoriaSubcategoria> buscarCategoriaSubcategoriaPorImovelId(Integer imovelId) throws RepositorioException;
	public Integer obterQuantidadeEconomiasTotal(Integer imovelId) throws RepositorioException;
	public Integer obterCategoriaPrincipal(Integer imovelId) throws RepositorioException;
	public Integer obterQuantidadeEconomias(Integer imovelId, Integer codigoCategoria, Integer codigoSubcategoria) throws RepositorioException;
}