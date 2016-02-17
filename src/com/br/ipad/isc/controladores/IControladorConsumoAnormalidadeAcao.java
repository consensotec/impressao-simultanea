
package com.br.ipad.isc.controladores;

import java.util.ArrayList;

import android.content.Context;

import com.br.ipad.isc.bean.ConsumoAnormalidadeAcao;
import com.br.ipad.isc.excecoes.ControladorException;

public interface IControladorConsumoAnormalidadeAcao {
	
	public void setContext(Context ctx );
	
	public ArrayList<ConsumoAnormalidadeAcao> buscarConsumoAnormalidadeAcao(Integer perfilId, Integer anormalidade,
			Integer categoriaPrincipal) throws ControladorException;

	public Integer obterQtdConsumoAnormalidadeAcao(Integer perfilId, Integer anormalidade,
			Integer categoriaPrincipal) throws ControladorException;
	
	public ConsumoAnormalidadeAcao obterConsumoAnormalidadeAcao(int idAnormalidadeConsumo, int idCategoria, 
			int idPerfilImovel, int ordemMesAnormalidade) throws ControladorException;
	
	
}