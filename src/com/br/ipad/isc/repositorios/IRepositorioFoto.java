package com.br.ipad.isc.repositorios;

import java.util.ArrayList;

import com.br.ipad.isc.bean.Foto;
import com.br.ipad.isc.excecoes.RepositorioException;

public interface IRepositorioFoto {
	
	public Foto buscarFotoTipo(Integer id, Integer fotoTipo, Integer medicaoTipo,Integer idLeituraAnormalidade,Integer idConsumoAnormalidade) throws RepositorioException;
	public ArrayList<Foto> buscarFotos(Integer idImovel, Integer medicaoTipo) throws RepositorioException;
	public ArrayList<Foto> buscarFotosPendentes() throws RepositorioException; 
	public ArrayList<Foto> buscarFotosPendentes(Integer id) throws RepositorioException;
	public ArrayList<Foto> buscarFotos(String selection,  String[] selectionArgs) throws RepositorioException;
	
}
