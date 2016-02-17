package com.br.ipad.isc.repositorios;

import java.util.Collection;
import com.br.ipad.isc.bean.CreditoRealizado;
import com.br.ipad.isc.excecoes.RepositorioException;

public interface IRepositorioCreditoRealizado {
	
	public Collection<CreditoRealizado> buscarCreditoRealizadoPorImovelId(Integer imovelId) throws RepositorioException;
	public Double obterValorCreditoTotal(Integer imovelId) throws RepositorioException;
	public CreditoRealizado buscarCreditoRealizadoPorDescricao(String descricaoCredito,Integer imovelId) throws RepositorioException;
	
	public Integer obterQntCreditoRealizadoPorImovelId(Integer imovelId) throws RepositorioException;
}