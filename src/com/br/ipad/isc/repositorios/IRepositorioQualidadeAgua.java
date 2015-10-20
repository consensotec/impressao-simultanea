package com.br.ipad.isc.repositorios;

import com.br.ipad.isc.bean.QualidadeAgua;
import com.br.ipad.isc.excecoes.RepositorioException;

public interface IRepositorioQualidadeAgua {
	
	public QualidadeAgua buscarQualidadeAguaPorLocalidadeSetorComercial(Integer idLocalidade, Integer idSetorComercial) throws RepositorioException;
	public QualidadeAgua buscarQualidadeAguaPorLocalidade(Integer idLocalidade) throws RepositorioException;
	public QualidadeAgua buscarQualidadeAguaSemLocalidade() throws RepositorioException;

}
