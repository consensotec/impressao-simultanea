
package com.br.ipad.isc.repositorios;

import java.util.ArrayList;

import com.br.ipad.isc.bean.ObjetoBasico;
import com.br.ipad.isc.excecoes.RepositorioException;

public interface IRepositorioBasico {
	
	public boolean verificarExistenciaBancoDeDados();
	
	/**
	 * Atualiza todos os campos do objeto no banco de dados
	 * @author Amelia Pessoa
	 * @param objeto
	 * @throws RepositorioException
	 */
	public void atualizar(ObjetoBasico objeto) throws RepositorioException;
	
	/**
	 * Remover objeto do BD
	 * @author Amelia Pessoa
	 * @param objeto
	 * @throws RepositorioException
	 */
	public void remover(ObjetoBasico objeto) throws RepositorioException;
	
	/**
	 * Insere objeto no BD e retorna id gerado
	 * @author Amelia Pessoa
	 * @param objeto
	 * @throws RepositorioException
	 */
	public long inserir(ObjetoBasico objeto) throws RepositorioException;
	
	/**
	 * Pesquisa objeto com base no id 
	 * Recebe como parametro objeto de tipo igual ao seu
	 * @author Amelia Pessoa
	 * @param objeto
	 * @throws RepositorioException
	 */
	public <T extends ObjetoBasico> T pesquisarPorId(Integer id, T objetoTipo) throws RepositorioException;
	
	/**
	 * Pesquisa lista de objetos 
	 * Recebe como parametro objeto de tipo igual ao seu
	 * @author Amelia Pessoa
	 * @param objeto
	 * @throws RepositorioException
	 */
	public <T extends ObjetoBasico>  ArrayList<T> pesquisar(T objetoTipo) throws RepositorioException;
}