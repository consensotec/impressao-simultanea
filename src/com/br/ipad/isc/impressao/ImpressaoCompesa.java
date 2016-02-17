package com.br.ipad.isc.impressao;

import java.util.ArrayList;

import com.br.ipad.isc.bean.CategoriaSubcategoria;
import com.br.ipad.isc.util.ConstantesSistema;



/**
 * Métodos de uso comum no momento de impressão
 * para a COMPESA
 * 
 * @author Amelia Pessoa
 *
 */
public class ImpressaoCompesa extends Impressao {
	
	protected StringBuilder categoriasEconomias(ArrayList<CategoriaSubcategoria> arrayListCategoriaSubcategoria) {
	 	
		 StringBuilder retorno = new StringBuilder();
		 
		 for(int i=0 ; i < arrayListCategoriaSubcategoria.size(); i++){
				
				if(arrayListCategoriaSubcategoria.get(i).getCodigoCategoria().intValue() == (ConstantesSistema.RESIDENCIAL)){
					StringBuilder qtdEconResidencial = new StringBuilder();
					qtdEconResidencial.append(arrayListCategoriaSubcategoria.get(i).getQtdEconomiasSubcategoria());
					retorno.append(formarLinha(7, 0, 440, 273, qtdEconResidencial + "", 0, 0));
				}
				
				if(arrayListCategoriaSubcategoria.get(i).getCodigoCategoria().intValue() == (ConstantesSistema.INDUSTRIAL)){
					StringBuilder qtdEconIndustrial = new StringBuilder();
					qtdEconIndustrial.append(arrayListCategoriaSubcategoria.get(i).getQtdEconomiasSubcategoria());
					retorno.append(formarLinha(7, 0, 640, 270, qtdEconIndustrial + "", 0, 0));
				}
				
				if(arrayListCategoriaSubcategoria.get(i).getCodigoCategoria().intValue() == (ConstantesSistema.COMERCIAL)){
					StringBuilder qtdEconComercial = new StringBuilder();
					qtdEconComercial.append(arrayListCategoriaSubcategoria.get(i).getQtdEconomiasSubcategoria());
					retorno.append(formarLinha(7, 0, 540, 273, qtdEconComercial + "", 0, 0));
				}
				
				if(arrayListCategoriaSubcategoria.get(i).getCodigoCategoria().intValue() == (ConstantesSistema.PUBLICO)){
					StringBuilder qtdEconPublico = new StringBuilder();
					qtdEconPublico.append(arrayListCategoriaSubcategoria.get(i).getQtdEconomiasSubcategoria());
					retorno.append(formarLinha(7, 0, 740, 273, qtdEconPublico + "", 0, 0));
				}				
				
			}		 
		 return retorno;
	}
}