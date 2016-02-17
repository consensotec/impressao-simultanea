package com.br.ipad.isc.repositorios;

import com.br.ipad.isc.bean.SequencialRotaMarcacao;
import com.br.ipad.isc.excecoes.RepositorioException;

public interface IRepositorioSequencialRotaMarcacao {
	
	
	public SequencialRotaMarcacao buscarSequencialRotaMarcacao(Integer idImovel) throws RepositorioException;
	public void removerTodosSequencialRotaMarcacao() throws RepositorioException;

}