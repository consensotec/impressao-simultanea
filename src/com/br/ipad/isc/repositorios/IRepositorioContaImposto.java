package com.br.ipad.isc.repositorios;

import java.util.Collection;
import com.br.ipad.isc.bean.ContaImposto;
import com.br.ipad.isc.excecoes.RepositorioException;

public interface IRepositorioContaImposto {
	
	public Collection<ContaImposto> buscarContaImpostoPorImovelId(Integer imovelId) throws RepositorioException;
	public Integer obterQntContaImpostoPorImovelId(Integer imovelId) throws RepositorioException;
}