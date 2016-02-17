
package com.br.ipad.isc.controladores;

import android.content.Context;

import com.br.ipad.isc.bean.HidrometroInstalado;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.excecoes.ControladorException;

public interface IControladorHidrometroInstalado  {
	
	
	public void setContext(Context ctx );
	
	public HidrometroInstalado buscarHidrometroInstaladoPorImovelTipoMedicao(Integer imovelId, Integer tipoMedicao) 
			throws ControladorException;
	
	public String obterTombamento(ImovelConta imovelConta, int tipoMedicao) throws ControladorException;
	
	public boolean validarLeituraMensagem(HidrometroInstalado hidrometroInstalado,ImovelConta imovel,int idMensagem, boolean imprimir, boolean proximo) throws ControladorException;

	public HidrometroInstalado buscarLeituraHidrometroTipoMedicao(Integer imovelId, Integer tipoMedicao) 
			throws ControladorException;

}