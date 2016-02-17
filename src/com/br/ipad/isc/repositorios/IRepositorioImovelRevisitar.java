package com.br.ipad.isc.repositorios;

import java.util.ArrayList;

import com.br.ipad.isc.bean.ImovelRevisitar;
import com.br.ipad.isc.excecoes.RepositorioException;

public interface IRepositorioImovelRevisitar {
	
	public ImovelRevisitar buscarImovelRevisitarPorImovel(Integer imovelId) throws RepositorioException;
	public ArrayList<ImovelRevisitar> buscarImovelNaoRevisitado() throws RepositorioException;
	
}
