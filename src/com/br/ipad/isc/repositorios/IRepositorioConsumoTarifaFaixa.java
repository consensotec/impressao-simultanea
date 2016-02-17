package com.br.ipad.isc.repositorios;

import java.util.ArrayList;
import java.util.Date;

import com.br.ipad.isc.bean.ConsumoTarifaFaixa;
import com.br.ipad.isc.excecoes.RepositorioException;

public interface IRepositorioConsumoTarifaFaixa {
	
	public ArrayList<ConsumoTarifaFaixa> buscarConsumosTarifasFaixasPorTarifaCateg(Integer idTarifaCateg) throws RepositorioException;
	public ArrayList<ConsumoTarifaFaixa> buscarConsumosTarifaFaixaPorId(int idTarifa, Date dataInicioVigencia) throws RepositorioException;
	public ArrayList<ConsumoTarifaFaixa> buscarConsumosTarifaFaixaPorCodigo(int codigoTarifa, Date dataInicioVigencia) throws RepositorioException;
}
