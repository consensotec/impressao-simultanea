
package com.br.ipad.isc.controladores;

import java.util.ArrayList;

import android.content.Context;

import com.br.ipad.isc.bean.Foto;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.excecoes.ControladorException;

/**
 * [ISC] Interface Controlador Foto
 * 
 * @author Arthur Carvalho
 * @since 17/07/2012
 */
public interface IControladorFoto {

	public void setContext(Context ctx );	
	
	public Foto buscarFotoTipo(Integer id, Integer fotoTipo, Integer medicaoTipo,Integer idLeituraAnormalidade,Integer idConsumoAnormalidade) throws ControladorException;
	public ArrayList<Foto> buscarFotos(Integer idImovel, Integer medicaoTipo) throws ControladorException;
	public boolean enviarFotosOnline(ImovelConta imovel) throws ControladorException;
	public ArrayList<Foto> buscarFotosPendentes() throws ControladorException;
	public boolean enviarFotosOnline(Foto foto) throws ControladorException;
	public ArrayList<Foto> buscarFotos(String selection,  String[] selectionArgs) throws ControladorException;
}
