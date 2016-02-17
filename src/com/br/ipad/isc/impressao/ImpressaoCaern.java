package com.br.ipad.isc.impressao;

import java.util.ArrayList;

import com.br.ipad.isc.bean.CategoriaSubcategoria;
import com.br.ipad.isc.bean.ConsumoAnteriores;
import com.br.ipad.isc.bean.HidrometroInstalado;
import com.br.ipad.isc.util.ConstantesSistema;
import com.br.ipad.isc.util.Util;


/**
 * Métodos de uso comum no momento de impressão
 * para a CAERN
 * 
 * @author Amelia Pessoa
 *
 */
public class ImpressaoCaern extends Impressao {
	
	/**
     * Lista as economias das categorias
     *
     * @return Retorna um array onde cada posicao tem o numero de economias da
     *         categoria: [0] Residencial [1] Comercial [2] Industrial [3]
     *         Publico
     */
    protected final int[] getNumeroEconomias(ArrayList<CategoriaSubcategoria> regsTipo2) {
    	int[] retorno = new int[4];

	    for (int i = 0; i < regsTipo2.size(); i++) {
	        CategoriaSubcategoria reg2 = (CategoriaSubcategoria) regsTipo2.get(i);
	
	        switch (reg2.getCodigoCategoria()) {
	        case ConstantesSistema.RESIDENCIAL:
	        retorno[0] += reg2.getQtdEconomiasSubcategoria();
	        break;
	
	        case ConstantesSistema.COMERCIAL:
	        retorno[1] += reg2.getQtdEconomiasSubcategoria();
	        break;
	
	        case ConstantesSistema.INDUSTRIAL:
	        retorno[2] += reg2.getQtdEconomiasSubcategoria();
	        break;
	
	        case ConstantesSistema.PUBLICO:
	        retorno[3] += reg2.getQtdEconomiasSubcategoria();
	        break;
	        }
	    }

    	return retorno;
    }
    
    
    /**
     * Gera linhas de historico de consumo e a media de consumo geral
     */
    protected int gerarHistorico(HidrometroInstalado hidrometroInstaladoAgua,
    		HidrometroInstalado hidrometroInstaladoPoco, int yInicial) {
    	
	    appendLinha(5, yInicial, 100, yInicial, 0.1f);
	    yInicial++;
	
	    // Historico de consumo
	    appendTexto70(5, yInicial, "HISTORICO DE CONSUMO");
	    yInicial++;
	
	    // Coluna 1
	    appendTexto70(5, yInicial + 3, "REF");
	    appendTexto70(18, yInicial + 3, "CONSUMO");
	
	    // Coluna 2
	    appendTexto70(33, yInicial + 3, "REF");
	    appendTexto70(46, yInicial + 3, "CONSUMO");
	
	    // Coluna 3
	    appendTexto70(61, yInicial + 3, "REF");
	    appendTexto70(74, yInicial + 3, "CONSUMO");
	
	    appendTexto70(89, yInicial + 3, "MEDIA");
	   
	    int consumoMedio = 0;
	   
	    if (imovel.getConsumoMedioLigacaoAgua() != null){
	       
	        consumoMedio = imovel.getConsumoMedioLigacaoAgua();
	    }
	    else{
	       
	        consumoMedio = imovel.getConsumoMedioEsgoto();
	    }
	   
	    appendTexto70(89, yInicial + 6, Integer.toString(consumoMedio));
	   
	   
	    yInicial += 6;
	
	    //Vector registros3 = imovel.getRegistros3();
	    
	    ArrayList<ConsumoAnteriores> arrayListConsumoAnteriores = (ArrayList<ConsumoAnteriores>) 
	    		fachada.buscarConsumoAnterioresPorImovelId(imovel.getId());
		
	    if (arrayListConsumoAnteriores != null) {
	        for (int i = 0, yHistorico = yInicial, xHistorico = 5; i < arrayListConsumoAnteriores.size(); i++) {
	        	
		        ConsumoAnteriores consumoAnteriores = (ConsumoAnteriores) arrayListConsumoAnteriores.get(i);
		
		        if (hidrometroInstaladoAgua != null) {
		            if (consumoAnteriores.getTipoLigacao().intValue() != ConstantesSistema.LIGACAO_AGUA)
		            continue;
		        } else if (hidrometroInstaladoPoco != null) {
		            if (consumoAnteriores.getTipoLigacao().intValue() != ConstantesSistema.LIGACAO_POCO)
		            continue;
		        }
		        
		        appendTexto70(xHistorico, yHistorico, Util
		            .formatarAnoMesParaMesAno(Integer.toString(consumoAnteriores
		                .getAnoMesReferencia())));
		        appendTexto70(xHistorico + 13, yHistorico, Integer
		            .toString(consumoAnteriores.getConsumo()));
		
		        if ((i + 1) % 2 == 0) {
		            xHistorico += 28;
		            yHistorico = yInicial;
		        } else
		            yHistorico += 3;
	        }
	    }
	    
	    return yInicial;
    }
}