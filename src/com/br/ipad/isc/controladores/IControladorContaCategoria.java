
package com.br.ipad.isc.controladores;

import android.content.Context;

import com.br.ipad.isc.bean.ContaCategoria;
import com.br.ipad.isc.excecoes.ControladorException;

public interface IControladorContaCategoria  {
	
	public void setContext(Context ctx );

	public ContaCategoria buscarContaCategoriaPorCategoriaSubcategoriaId(Integer categoriaSubcategoriaId, Integer tipoMedicao) 
			throws ControladorException;

	public Double obterValorTotal(Integer imovelId, Integer tipoMedicao) throws ControladorException;
	
	public void removerImovelContaCategoria(Integer idImovel,int tipoLigacao) throws ControladorException;
}