
package com.br.ipad.isc.controladores;

import java.util.ArrayList;

import android.content.Context;

import com.br.ipad.isc.bean.CategoriaSubcategoria;
import com.br.ipad.isc.excecoes.ControladorException;


public interface IControladorCategoriaSubcategoria {
	
	public void setContext(Context ctx );		

	public ArrayList<CategoriaSubcategoria> buscarCategoriaSubcategoriaPorImovelId(Integer imovelId) throws ControladorException;

	public Integer obterQuantidadeEconomiasTotal(Integer imovelId) throws ControladorException;
	
	public Integer obterCategoriaPrincipal(Integer imovelId) throws ControladorException;
	
	public Integer obterQuantidadeEconomias(Integer imovelId, Integer codigoCategoria, Integer codigoSubcategoria) throws ControladorException;

}