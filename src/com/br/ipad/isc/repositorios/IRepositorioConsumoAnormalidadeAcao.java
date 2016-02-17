package com.br.ipad.isc.repositorios;

import java.util.ArrayList;

import com.br.ipad.isc.bean.ConsumoAnormalidadeAcao;
import com.br.ipad.isc.excecoes.RepositorioException;

public interface IRepositorioConsumoAnormalidadeAcao {
	
	public ArrayList<ConsumoAnormalidadeAcao> buscarConsumoAnormalidadeAcao(Integer perfilId, Integer anormalidade, Integer categoriaPrincipal) throws RepositorioException;
	public ArrayList<ConsumoAnormalidadeAcao> buscarConsumoAnormalidadeAcao(Integer anormalidade, Integer categoriaPrincipal) throws RepositorioException;
	public ArrayList<ConsumoAnormalidadeAcao> buscarConsumoAnormalidadeAcao(Integer anormalidade) throws RepositorioException;
}
