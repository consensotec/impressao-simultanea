
package com.br.ipad.isc.controladores;

import java.util.ArrayList;

import android.content.Context;

import com.br.ipad.isc.bean.HidrometroInstalado;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.bean.ObjetoBasico;
import com.br.ipad.isc.excecoes.ControladorException;
import com.br.ipad.isc.excecoes.RepositorioException;

public interface IControladorBasico {
	
	public void setContext(Context ctx);			
	
	public ControladorHidrometroInstalado getControladorHidrometroInstalado();

	public ControladorCategoriaSubcategoria getControladorCategoriaSubcategoria();

	public ControladorDebitoCobrado getControladorDebitoCobrado();

	public ControladorImovelConta getControladorImovelConta();

	public ControladorConsumoAnteriores getControladorConsumoAnteriores();

	public ControladorSistemaParametros getControladorSistemaParametros();

	public ControladorContaCategoria getControladorContaCategoria();

	public ControladorConsumoHistorico getControladorConsumoHistorico();

	public ControladorCreditoRealizado getControladorCreditoRealizado();

	public ControladorConsumoAnormalidadeAcao getControladorConsumoAnormalidadeAcao();
	
	public ControladorConsumoTarifaCategoria getControladorConsumoTarifaCategoria();
	
	public ControladorConsumoTarifaFaixa getControladorConsumoTarifaFaixa();
	
	public ControladorContaCategoriaConsumoFaixa getControladorContaCategoriaConsumoFaixa();
	
	public ControladorContaImposto getControladorContaImposto();
	
	public ControladorConta getControladorConta();	
	
	public ControladorImovel getControladorImovel();

	public ControladorAlertaValidarLeitura getControladorAlertaValidarLeitura(HidrometroInstalado hidrometro,ImovelConta imovel,int tipoMedicao,boolean imprimir, boolean proximo);

	public ControladorFoto getControladorFoto();

	
	/**
	 * Atualiza todos os campos do objeto no banco de dados
	 * @param objeto
	 * @throws RepositorioException
	 */
	public void atualizar(ObjetoBasico objeto) throws ControladorException;
	
	/**
	 * Remover objeto do BD
	 * @param objeto
	 * @throws RepositorioException
	 */
	public void remover(ObjetoBasico objeto) throws ControladorException;
	
	/**
	 * Insere objeto no BD e retorna id gerado
	 * @param objeto
	 * @throws RepositorioException
	 */
	public long inserir(ObjetoBasico objeto) throws ControladorException;
	
	/**
	 * Pesquisa objeto com base no id 
	 * Recebe como parametro objeto de tipo igual ao seu
	 * @author Amelia Pessoa
	 * @param objeto
	 * @throws RepositorioException
	 */
	public <T extends ObjetoBasico> T pesquisarPorId(Integer id, T objetoTipo) throws ControladorException;
	
	/**
	 * Pesquisa lista de objetos 
	 * Recebe como parametro objeto de tipo igual ao seu
	 * @author Amelia Pessoa
	 * @param objeto
	 * @throws RepositorioException
	 */
	public <T extends ObjetoBasico>  ArrayList<T> pesquisar(T objetoTipo) throws ControladorException;
	
	public boolean verificarExistenciaBancoDeDados();
	
	public void carregaLinhaParaBD(String line) throws ControladorException;
	
	public void apagarBanco();
}