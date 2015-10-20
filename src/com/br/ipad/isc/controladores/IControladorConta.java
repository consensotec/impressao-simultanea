
package com.br.ipad.isc.controladores;

import android.content.Context;

import com.br.ipad.isc.bean.ConsumoAnormalidade;
import com.br.ipad.isc.bean.ConsumoHistorico;
import com.br.ipad.isc.bean.HidrometroInstalado;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.excecoes.ControladorException;

public interface IControladorConta {

	public void setContext(Context ctx);

	/**
	 * [UC0740] Calcular Consumo no Dispositivo Móvel
	 */
	public boolean calcularConta(ImovelConta imovel,Boolean imprimir, Boolean proximo) throws ControladorException;

	
	public void calcularValores(ImovelConta imovel, ConsumoHistorico consumoAgua, ConsumoHistorico consumoEsgoto) 
			throws ControladorException;


	public boolean verificarEstouroConsumo(ImovelConta imovel, ConsumoHistorico consumo, 
			HidrometroInstalado hidrometroInstalado, int tipoMedicao) throws ControladorException;

	public void verificarAltoConsumo(ImovelConta imovel, ConsumoHistorico consumo, 
			HidrometroInstalado hidrometroInstalado, int tipoMedicao);

	public void verificarBaixoConsumo(ImovelConta imovel, ConsumoHistorico consumo, 
			HidrometroInstalado hidrometroInstalado, int tipoMedicao);	

	/**
	 * Proposta: RN2011071073 - 08/11/2011 - Savio Luiz - RM1073 - CAERN
	 * (Calcular o minimo quando a leitura atual for menor que a leitura
	 * projetada).
	 * 
	 * [UC0101] - Consistir Leituras e Calcular Consumos [SB0024] - Recuperar
	 * Dados da Tabela de Consumo Anormalidade Acao
	 * 
	 * @since 16/11/2011
	 * @author Thúlio Araújo
	 * 
	 * @param imovel
	 * @param consumo
	 * @param reg8
	 * @param tipoMedicao
	 * @param reg11
	 * @param consumoAnormalidadeTipo
	 */
	public void recuperarDadosConsumoAnormalidadeAcao(ImovelConta imovel, ConsumoHistorico consumo, 
			HidrometroInstalado hidrometroInstalado, int tipoMedicao,
			ConsumoAnormalidade consumoAnormalidadeTipo) throws ControladorException;

	
	public void verificarPercentualEsgotoAlternativo(ImovelConta imovel, Integer consumoFaturadoEsgoto) throws ControladorException;
	
	public int obterLeituraAnterior(HidrometroInstalado hidrometroInstalado) ;
	
	public double obterValorConta(Integer imovelId) throws ControladorException ;
	
	/**
	 * Método identifica se o imóvel passado como parâmetro pode ser impresso
	 * (Se o mesmo NAO for imóvel condomínio OU, sendo imóvel condomínio for o 
	 * último imóvel retorna true)
	 * 
	 * @author Amelia Pessoa
	 * @param imovel
	 * @return
	 * @throws ControladorException
	 */
	public boolean permiteImprimir(ImovelConta imovel) throws ControladorException;
	
	/**
	 * [UC0970] Efetuar Rateio de Consumo no Dispositivo Movel Metodo
	 * responsavel em efeturar a divisão da diferença entre o consumo coletado
	 * no hidrometro macro e a soma dos hidrometros micro
	 * 
	 * [SB0002] Determinar Rateio de Agua
	 * 
	 * @date 26/11/2009
	 * @author Bruno Barros, Amelia Pessoa
	 * 
	 */
	public ControladorRateioImovelCondominio efetuarRateio(ImovelConta imovelMacro, boolean completo) throws ControladorException;
	
	/** [UC0740] [SB0003] - Mudança na ordem de verficação de subsituição de hidrometro e virada de hidromêtro 
	 * 
	 * @author Fernanda Almeida
	 * @date 14/02/2013
	 * 
	 * @param hidrometroInstalado
	 * @param consumo
	 * @param leitura
	 * @param cMedio
	 * @return
	 * @throws ControladorException 
	 */
	public boolean controlaSubstituicaoHidrometro(HidrometroInstalado hidrometroInstalado, ConsumoHistorico consumo,
			Integer leitura, int cMedio) throws ControladorException;
}