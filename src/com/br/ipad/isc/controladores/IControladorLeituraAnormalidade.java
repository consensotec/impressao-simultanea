
package com.br.ipad.isc.controladores;

import java.util.ArrayList;

import android.content.Context;

import com.br.ipad.isc.bean.LeituraAnormalidade;
import com.br.ipad.isc.excecoes.ControladorException;

public interface IControladorLeituraAnormalidade {
	
	public void setContext(Context ctx );		

	public LeituraAnormalidade buscarLeituraAnormalidadePorIdComUsoAtivo(Integer id) throws ControladorException;
	public ArrayList<LeituraAnormalidade> buscarLeiturasAnormalidadesComUsoAtivo() throws ControladorException;
	public LeituraAnormalidade buscarLeituraAnormalidadeImovel(Integer idImovel,Integer tipoLigacao) throws ControladorException;

}