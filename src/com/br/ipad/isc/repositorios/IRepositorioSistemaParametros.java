package com.br.ipad.isc.repositorios;

import com.br.ipad.isc.bean.SistemaParametros;
import com.br.ipad.isc.excecoes.RepositorioException;

public interface IRepositorioSistemaParametros {
	
	public SistemaParametros buscarSistemaParametro() throws RepositorioException;
	public void atualizarSistemaParametros(SistemaParametros consumoAnormalidade) throws RepositorioException;
		
	public void atualizarQntImoveis() throws RepositorioException;
	public void atualizarArquivoCarregadoBD() throws RepositorioException;

	public void atualizarIndicadorRotaMarcacaoAtiva(Integer indicadorRotaMarcacaoAtiva) throws RepositorioException;
	public void atualizarRoteiroOnlineOffline(Integer indicador) throws RepositorioException;
	public void atualizarIdImovelSelecionadoSistemaParametros(Integer idSelecionado) throws RepositorioException;
	public void atualizarIdQtdImovelCondominioSistemaParametros(Integer idImovel, Integer qntImovelCondominio) throws RepositorioException;
}