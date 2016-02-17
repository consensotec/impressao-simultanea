
package com.br.ipad.isc.controladores;

import android.util.Log;

import com.br.ipad.isc.excecoes.ControladorException;
import com.br.ipad.isc.util.ConstantesSistema;
import com.br.ipad.isc.util.Util;

/**
 * [UC1212] Gerar Arquivo Texto para as Ordens de Serviço de Acompanhamento por Equipe
 * 
 * - Controlador responsável pela coleta das informações
 * das OS para envio ao GSAN. 
 * 
 * @author bruno
 * @date 20/09/2011
 *
 */
public class ControladorRetorno extends ControladorBasico implements IControladorRetorno {
	
	private static final String REGISTER_TYPE_1 = "01"; 
	
	private static ControladorRetorno instance;
	
	//private ControladorImovelConta controladorImovelConta;
	
	protected ControladorRetorno(){
		super();
		//this.controladorImovelConta = ControladorImovelConta.getInstance();
	}
	
	public void resetInstance(){
		instance = null;
	}
	
	public static ControladorRetorno getInstance(){
		if ( instance == null ){
			return new ControladorRetorno();
		}else{
			return instance;
		}
	}
	
	/**
	 * [UC1212] Gerar Arquivo Texto para as Ordens de Serviço de Acompanhamento por Equipe
	 * 
	 * - Método responsável pela criação do arquivo de retorno
	 * 
	 * @param idOS - Id da OS
	 * @return
	 */
	public String geraRetornoImovel( Integer idOS ) throws ControladorException{
		StringBuilder sb = new StringBuilder("");
		
		try {
			sb.append( generateRegisterType1( idOS ) );
			
		} catch (ControladorException e) {
			throw new ControladorException( e.getMessage() );
		}
		
		Log.i( ConstantesSistema.CATEGORIA, sb.toString() );
		
		return sb.toString();
	}
	
	/**
	 * 
	 * [UC1212] Gerar Arquivo Texto para as Ordens de Serviço de Acompanhamento por Equipe
	 * 
	 * Gera registros tipo 1
	 * 
	 * @param idOS
	 * @return
	 * @throws ControladorException
	 */
	private String generateRegisterType1( Integer idOS ) throws ControladorException{
		StringBuilder sb = new StringBuilder("");
		
		sb.append( Util.stringPipe( REGISTER_TYPE_1 ) );
		
//		ImovelConta so =  controladorImovelConta.getImovelContaById( idOS );
//		
//		// Id da ordem de serviço
//		sb.append( Util.stringPipe( so.getId() ) );
//		
//		// Equipamento Especial Faltante
//		if ( so.getSpecialEquipments() != null ){
//			sb.append( Util.stringPipe( so.getSpecialEquipments().getId() ) );
//		} else {
//			sb.append( "|" );
//		}
//		
//		// Id da Ordem de Serviço Programação Não Encerramento Motivo;
//		if ( so.getOsProgramNotClosedReason() != null ){
//			sb.append( Util.stringPipe( so.getOsProgramNotClosedReason().getId() ) );
//		} else {
//			sb.append( "|" );
//		}
//		
//		// Id da Ordem de Serviço Situação
//		if ( so.getImovelContaSituation() != null ){
//			sb.append( Util.stringPipe( so.getImovelContaSituation().getId() ) );
//		} else {
//			sb.append( "|" );
//		}
//		
//		// Data da Programação;
//		sb.append( Util.stringPipe( Util.formatDate ( new Date() ) ) );
//		
//		// Descrição Ponto de Referência;
//		sb.append( Util.stringPipe( so.getReferencePointDescription() ) );
//		
//		// Número do Imóvel;
//		sb.append( so.getPropertyNumber() );
		
		return sb.toString() + "\n";
	}
	
		
}
