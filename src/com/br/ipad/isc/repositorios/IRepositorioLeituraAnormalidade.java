package com.br.ipad.isc.repositorios;

import java.util.ArrayList;

import com.br.ipad.isc.bean.LeituraAnormalidade;
import com.br.ipad.isc.excecoes.RepositorioException;

public interface IRepositorioLeituraAnormalidade {

	public LeituraAnormalidade buscarLeituraAnormalidadePorIdComUsoAtivo(Integer id) throws RepositorioException;
	public ArrayList<LeituraAnormalidade> buscarLeiturasAnormalidadesComUsoAtivo() throws RepositorioException;
	public LeituraAnormalidade buscarLeituraAnormalidadeImovel(Integer idImovel,Integer tipoLigacao) throws RepositorioException;
}