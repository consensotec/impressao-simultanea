package com.br.ipad.isc.repositorios;

import java.util.ArrayList;
import java.util.Date;

import com.br.ipad.isc.bean.ConsumoTarifaCategoria;
import com.br.ipad.isc.excecoes.RepositorioException;

public interface IRepositorioConsumoTarifaCategoria {
	
	public ConsumoTarifaCategoria buscarConsumoTarifaCategoriaPorIds(ConsumoTarifaCategoria consumoTarifaCatg, Integer idImovel) throws RepositorioException;
	public ArrayList<ConsumoTarifaCategoria> buscarConsumosTarifasCategorias(Integer imovelId) throws RepositorioException;
	public ArrayList<ConsumoTarifaCategoria> buscarConsumosTarifasCategorias(int codigoTarifa, Date dataInicioVigencia) throws RepositorioException;
	public ArrayList<Date> buscarVigenciasConsumosTarifasCategorias(int codigoTarifa) throws RepositorioException;
	public ArrayList<ConsumoTarifaCategoria> buscarConsumoTarifaCategoriaPorCodigo(Integer codigoTarifa) throws RepositorioException;
}
