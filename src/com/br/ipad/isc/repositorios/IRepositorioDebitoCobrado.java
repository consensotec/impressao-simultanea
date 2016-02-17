package com.br.ipad.isc.repositorios;

import java.util.Collection;

import com.br.ipad.isc.bean.DebitoCobrado;
import com.br.ipad.isc.excecoes.RepositorioException;

public interface IRepositorioDebitoCobrado {
	
	public Collection<DebitoCobrado> buscarDebitoCobradoPorImovelId(Integer imovelId) throws RepositorioException;
	public DebitoCobrado buscarDebitoCobradoPorCodigo(Integer debitoCodigo,Integer imovelId) throws RepositorioException;
	public Double obterValorDebitoTotal(Integer imovelId) throws RepositorioException;
	public Integer obterQntDebitoCobradoPorImovelId(Integer imovelId) throws RepositorioException;
}