package com.br.ipad.isc.impressao;

import java.util.ArrayList;

import com.br.ipad.isc.bean.CategoriaSubcategoria;



/**
 * Métodos de uso comum no momento de impressão
 * para a CAER
 * 
 * @author Carlos Chaves
 *
 */
public class ImpressaoCaer extends Impressao {
	
	protected int yPularLinha = 25;
	protected int xMargemDireita = 53;
	
	protected void gerarCategoriaSubcategoria(Integer imovelId){
		
		// Verificamos se é por categoria ou subcategoria
	    ArrayList<CategoriaSubcategoria> arrayListCategoriaSubcategoria = new ArrayList<CategoriaSubcategoria>();
		arrayListCategoriaSubcategoria = (ArrayList<CategoriaSubcategoria>) 
							fachada.buscarCategoriaSubcategoriaPorImovelId(imovelId);
	    
	    for (int i = 0; i < arrayListCategoriaSubcategoria.size(); i++) {
	    	
	    	CategoriaSubcategoria dadosCategoria = (CategoriaSubcategoria) arrayListCategoriaSubcategoria.get(i);
	    	appendTexto(formarLinha(0, 0, 510, 250, dadosCategoria.getDescricaoCategoria() + "", i * 83, 0).toString());
	    	appendTexto(formarLinha(7, 0, 520, 260, dadosCategoria.getQtdEconomiasSubcategoria() + "", i * 83, 0).toString());
	    }
	    
	}
	
	
    

}