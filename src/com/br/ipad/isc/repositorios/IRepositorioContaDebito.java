package com.br.ipad.isc.repositorios;

import java.util.ArrayList;

import com.br.ipad.isc.bean.ContaDebito;
import com.br.ipad.isc.excecoes.RepositorioException;

public interface IRepositorioContaDebito {
	
	public ArrayList<ContaDebito> buscarContasDebitosPorIdImovel(Integer idImovel) throws RepositorioException;
	
}
