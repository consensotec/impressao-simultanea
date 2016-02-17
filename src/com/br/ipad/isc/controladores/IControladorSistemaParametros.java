
package com.br.ipad.isc.controladores;

import android.content.Context;

import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.bean.SistemaParametros;
import com.br.ipad.isc.excecoes.ControladorException;

public interface IControladorSistemaParametros  {
	
	public void setContext(Context ctx );

	public SistemaParametros buscarSistemaParametro() throws ControladorException;	

	public void atualizarSistemaParametros(SistemaParametros sistemaParametros) throws ControladorException;

	public boolean validaSenhaApagar(String senha) throws ControladorException;

	public boolean validaSenhaAdm(String senha) throws ControladorException;
	
	public void atualizarQntImoveis() throws ControladorException;
	
	/**
	 * Atualiza os atributos idImovelCondominio e qntImovelCondominio
	 * de SistemaParametros
	 * 
	 * @param imovelMacro
	 */
	public void atualizarDadosImovelMacro(ImovelConta imovelMacro) throws ControladorException;
	
	public void atualizarArquivoCarregadoBD() throws ControladorException;
	public String atualizarIndicadorRotaMarcacaoAtiva(Integer indicadorRotaMarcacaoAtiva) throws ControladorException;
	public void atualizarRoteiroOnlineOffline(Integer indicador) throws ControladorException;

	public void atualizarIdImovelSelecionadoSistemaParametros(Integer idSelecionado) throws ControladorException;
	
	public void atualizarIdQtdImovelCondominioSistemaParametros(Integer idImovel, Integer qntImovelCondominio) throws ControladorException;
}