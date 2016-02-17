
package com.br.ipad.isc.controladores;

import android.content.Context;

import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.excecoes.ControladorException;


public interface IControladorImpressao {
		

	public  boolean verificarImpressaoConta(ImovelConta imovel,Context context,int idMensagem, boolean mostrarMensagens) throws ControladorException;
	
	public void atualizaDadosImpressaoImovel (ImovelConta imovel) throws ControladorException;
	
	public boolean verificarExistenciaImpressora(ImovelConta imovel);
	
	public boolean imprimirExtratoMacro (Context context, ImovelConta imovelMacro) throws ControladorException;

}