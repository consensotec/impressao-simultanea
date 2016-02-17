
package com.br.ipad.isc.controladores;

import java.util.List;

import android.content.Context;

import com.br.ipad.isc.bean.ConsumoAnteriores;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.excecoes.ControladorException;

public interface IControladorConsumoAnteriores {
	
	public void setContext(Context ctx );
	
	public List<ConsumoAnteriores> buscarConsumoAnterioresPorImovelId(Integer imovelId) throws ControladorException;
	
	public ConsumoAnteriores buscarConsumoAnterioresPorImovelAnoMesPorTipoLigacao(Integer imovelId, Integer anoMes,Integer tiopValidacao) throws ControladorException;

	public List<ConsumoAnteriores> buscarConsumoAnterioresPorImovelAnormalidade(Integer imovelId, Integer idAnormalidadeConsumo) 
			throws ControladorException;
	
	public int obtemOrdemAnormalidade(ImovelConta imovel, int idAnormalidadeConsumo,int anoMesReferencia) 
			throws ControladorException;
	
	public List<ConsumoAnteriores> buscarConsumoAnterioresPorImovelTipoLigacao(Integer imovelId, Integer tipoMedicao) 
			throws ControladorException;
	public ConsumoAnteriores buscarConsumoAnterioresPorImovelAnormalidade(Integer imovelId, Integer idAnormalidadeConsumo, Integer anoMes) 
			throws ControladorException;
}