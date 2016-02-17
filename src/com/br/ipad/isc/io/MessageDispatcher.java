
package com.br.ipad.isc.io;

/*
 * Copyright (C) 2007-2009 the GSAN - Sistema Integrado de Gestão de Serviços de Saneamento
 *
 * This file is part of GSAN, an integrated service management system for Sanitation
 *
 * GSAN is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License.
 *
 * GSAN is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA
 */


/**
 * Classe reponsável por enviar as mensagens de requisição de serviço ao
 * servidor.
 * 
 * @author Rafael Palermo de Araújo
 */
public class MessageDispatcher {

	private static MessageDispatcher instancia;
	
	// Tipos de parametros que podem ser retornados
	public static final String PARAMETRO_TIPO_ARQUIVO = "tipoArquivo=";
	public static final String PARAMETRO_ARQUIVO_ROTEIRO = "arquivoRoteiro=";
	public static final char CARACTER_FIM_PARAMETRO = '&';

	private char tipoArquivo;
	private int fileLength;

	public static MessageDispatcher getInstancia() {
		if (instancia == null) {
			instancia = new MessageDispatcher();
		}

		return instancia;
	}

	protected MessageDispatcher() {
		super();
	}

	public boolean controlarParametros(StringBuffer buffer, char caracter,StringBuffer bufferValorParametro, boolean ultimoCaracter) {

		/*
		 * Caso se ja um dos parametros abaixo só podemos comecar a receber o
		 * valor quando estivermos lendo o primeiro caracter pos identificador
		 * de parametros. Por isso a comparacao com o tamanho do buffer
		 */

		// Mensagem
		if ((buffer.toString().indexOf(PARAMETRO_TIPO_ARQUIVO) > -1 && buffer
				.toString().length() > PARAMETRO_TIPO_ARQUIVO.length())) {

			// Caso encontremos o caracter de fim de parametro
			if (caracter == CARACTER_FIM_PARAMETRO || ultimoCaracter) {

				if (buffer.toString().indexOf(PARAMETRO_TIPO_ARQUIVO) > -1) {
					this.setTipoArquivo(bufferValorParametro.toString().charAt(0));
				}

				return true;
			} else {
				bufferValorParametro.append(caracter);
			}
		}

		return false;
	}

	
	public void setTipoArquivo(char tipoArquivo) {
		this.tipoArquivo = tipoArquivo;
	}

	public char getTipoArquivo() {
		return tipoArquivo;
	}

	public int getFileLength() {
		return fileLength;
	}

	public void setFileLength(int fileLength) {
		this.fileLength = fileLength;
	}
	
}
