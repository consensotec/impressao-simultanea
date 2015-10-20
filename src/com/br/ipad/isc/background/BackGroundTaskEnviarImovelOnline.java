
package com.br.ipad.isc.background;

import com.br.ipad.isc.bean.ImovelConta;

public class BackGroundTaskEnviarImovelOnline extends BackgroundTask {

	/**
	 * Identificador da requisição Cliente->Servidor de envio das atualizações
	 * pendentes dos imóveis.
	 */
//	public static final byte ENVIAR = 1;
//	private ImovelConta imovelParaProcessar = null;

	public BackGroundTaskEnviarImovelOnline(
			ImovelConta imovelParaProcessar) {
//		this.imovelParaProcessar = imovelParaProcessar;
	}

	protected BackGroundTaskEnviarImovelOnline() {
		super();
	}

	/**
	 * Metodo utilizado para criar a conexao e enviar os dados de retorno ao
	 * servidor.
	 */
	public void performTask() {

//		if (imovelParaProcessar.enviarAoCalcular()) {
//			Util.enviarImovelOnLine(imovelParaProcessar);
//		}
	}

	public void taskFinished() {
//		imovelParaProcessar = null;
	}

	public void taskStarted() {
		this.setPriority(Thread.MIN_PRIORITY);
	}

}
