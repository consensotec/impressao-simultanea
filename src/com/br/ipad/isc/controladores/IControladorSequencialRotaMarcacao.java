
package com.br.ipad.isc.controladores;

import android.content.Context;

import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.bean.SequencialRotaMarcacao;
import com.br.ipad.isc.excecoes.ControladorException;

public interface IControladorSequencialRotaMarcacao  {
	
	public void setContext(Context ctx );
	public void gravarSequencialRotaMarcacao(ImovelConta imovel) throws ControladorException;
	public SequencialRotaMarcacao buscarSequencialRotaMarcacao(Integer idImovel) throws ControladorException;
	public void removerTodosSequencialRotaMarcacao() throws ControladorException;

}