
package com.br.ipad.isc.controladores;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;

import com.br.ipad.isc.bean.Foto;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.conexao.ConexaoFoto;
import com.br.ipad.isc.conexao.ConexaoWebServer;
import com.br.ipad.isc.excecoes.ControladorException;
import com.br.ipad.isc.excecoes.RepositorioException;
import com.br.ipad.isc.repositorios.RepositorioFoto;
import com.br.ipad.isc.util.ConstantesSistema;

/**
 * [ISC] Controlador Foto
 * 
 * @author Arthur Carvalho
 * @since 17/07/2012
 */
public class ControladorFoto extends ControladorBasico implements IControladorFoto {
	
	private static ControladorFoto instance;
	private RepositorioFoto repositorioFoto;
	protected static Context context;
	
	public void resetarInstancia(){
		instance = null;
	}
	
	protected ControladorFoto(){
		super();
	}
	
	public static ControladorFoto getInstance(){
		if ( instance == null ){
			instance =  new ControladorFoto();
			instance.repositorioFoto = RepositorioFoto.getInstance();
		}
		
		return instance;		
	}

	public void setContext(Context ctx ) {
		context = ctx;		
	}	
	
	@Override
	public Foto buscarFotoTipo(Integer id, Integer tipoFoto, Integer medicaoTipo,Integer idLeituraAnormalidade,Integer idConsumoAnormalidade) throws ControladorException {
		Foto foto = null;
		try {
			foto = repositorioFoto.buscarFotoTipo(id, tipoFoto, medicaoTipo,idLeituraAnormalidade,idConsumoAnormalidade);
		} catch (RepositorioException e) {
			e.printStackTrace();
		}
		return foto;
	}

	public ArrayList<Foto> buscarFotos(Integer idImovel, Integer medicaoTipo) throws ControladorException {
		ArrayList<Foto> colecaoFotos = null;
		try {
			colecaoFotos = repositorioFoto.buscarFotos(idImovel, medicaoTipo);
		} catch (RepositorioException e) {
			e.printStackTrace();
		}
		return colecaoFotos ;
	}
	
	public ArrayList<Foto> buscarFotosPendentes() throws ControladorException {
		ArrayList<Foto> colecaoFotos = null;
		try {
			colecaoFotos = repositorioFoto.buscarFotosPendentes();
		} catch (RepositorioException e) {
			e.printStackTrace();
		}
		return colecaoFotos ;
	}
	
	public ArrayList<Foto> buscarFotosPendentes(Integer idImovel) throws ControladorException {
		ArrayList<Foto> colecaoFotos = null;
		try {
			colecaoFotos = repositorioFoto.buscarFotosPendentes(idImovel);
		} catch (RepositorioException e) {
			e.printStackTrace();
		}
		return colecaoFotos ;
	}
	
	/**
	 * Metodo Responsável por enviar as fotos pendentes do imovel para o GSAN.
	 * 
	 */
	public boolean enviarFotosOnline(ImovelConta imovel) throws ControladorException {
		
		boolean sucess = true;
		ArrayList<Foto> fotos = this.buscarFotosPendentes(imovel.getId());
			
		if(fotos!=null){
			for ( int j = 0; j < fotos.size(); j ++ ){
				sucess = false;
				
				Integer idAnormalidade = null;
				Integer idFotoTipoLeituraConsumoAnormalidade = null;
				
				Foto foto = fotos.get( j );
				ImovelConta imovelConta = (ImovelConta) ControladorBasico.getInstance()
						.pesquisarPorId(foto.getImovelConta().getId(), foto.getImovelConta());
												
				File imageFile = new File(foto.getCaminho()); 
				
				if(foto.getLeituraAnormalidade()!=null)
				{
					idAnormalidade = foto.getLeituraAnormalidade().getId();
					
					idFotoTipoLeituraConsumoAnormalidade = ConstantesSistema.FOTO_TIPO_LEITURA_ANORMALIDADE;
				}
				else if(foto.getConsumoAnormalidade()!=null)
				{
					idAnormalidade = foto.getConsumoAnormalidade().getId();
					
					idFotoTipoLeituraConsumoAnormalidade = ConstantesSistema.FOTO_TIPO_CONSUMO_ANORMALIDADE;
				}
				
				// Se for gerado Online
				ConexaoWebServer web = new ConexaoWebServer( getContext() );							
	        	if ( web.serverOnline() ){ 	
	        		
					sucess = ConexaoFoto.doFileUpload(
							imageFile,
							foto.getImovelConta().getId(),
							imovelConta.getAnoMesConta(),
							idAnormalidade,
							idFotoTipoLeituraConsumoAnormalidade,
							foto.getFotoTipo(),
							foto.getTipoMedicao(),
							ConstantesSistema.ACAO);
					
					if ( sucess ){ 
						foto.setIndicadorTransmitido( ConstantesSistema.SIM );
						ControladorBasico.getInstance().atualizar(foto);
					}
				}
			}
		}
		return sucess;
	}
	
	/**
	 * Metodo Responsável por enviar as fotos pendentes do imovel para o GSAN.
	 * 
	 */
	public boolean enviarFotosOnline(Foto foto) throws ControladorException {
		
		boolean sucess = false;

				Integer idAnormalidade = null;
				Integer idFotoTipoLeituraConsumoAnormalidade = null;
		
				ImovelConta imovelConta = (ImovelConta) ControladorBasico.getInstance()
						.pesquisarPorId(foto.getImovelConta().getId(), foto.getImovelConta());
												
				File imageFile = new File(foto.getCaminho()); 
				
				if(foto.getLeituraAnormalidade()!=null)
				{
					idAnormalidade = foto.getLeituraAnormalidade().getId();
					
					idFotoTipoLeituraConsumoAnormalidade = ConstantesSistema.FOTO_TIPO_LEITURA_ANORMALIDADE;
				}
				else if(foto.getConsumoAnormalidade()!=null)
				{
					idAnormalidade = foto.getConsumoAnormalidade().getId();
					
					idFotoTipoLeituraConsumoAnormalidade = ConstantesSistema.FOTO_TIPO_CONSUMO_ANORMALIDADE;
				}
				
				// Se for gerado Online
				ConexaoWebServer web = new ConexaoWebServer( getContext() );							
	        	if ( web.serverOnline() ){ 	
	        		
					sucess = ConexaoFoto.doFileUpload(
							imageFile,
							foto.getImovelConta().getId(),
							imovelConta.getAnoMesConta(),
							idAnormalidade,
							idFotoTipoLeituraConsumoAnormalidade,
							foto.getFotoTipo(),
							foto.getTipoMedicao(),
							ConstantesSistema.ACAO);
					
					if ( sucess ){ 
						foto.setIndicadorTransmitido( ConstantesSistema.SIM );
						ControladorBasico.getInstance().atualizar(foto);
					}
				}
		return sucess;
	}
	
	public ArrayList<Foto> buscarFotos(String selection,  String[] selectionArgs) throws ControladorException {
		
		try {
			return repositorioFoto.buscarFotos(selection,selectionArgs);
		} catch (RepositorioException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
