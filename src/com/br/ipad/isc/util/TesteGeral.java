package com.br.ipad.isc.util;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.database.SQLException;
import android.util.Log;
import com.br.ipad.isc.bean.ConsumoAnteriores;
import com.br.ipad.isc.bean.HidrometroInstalado;
import com.br.ipad.isc.bean.ImovelConta;
import com.br.ipad.isc.controladores.ControladorBasico;
import com.br.ipad.isc.controladores.ControladorContaCategoria;
import com.br.ipad.isc.excecoes.ControladorException;
import com.br.ipad.isc.repositorios.RepositorioBasico;

public class TesteGeral extends ControladorBasico {

	private TesteGeral(){}
	private static TesteGeral instancia = new TesteGeral();
	
//	private String script =
//			"CREATE TABLE teste_geral (" +
//					" IMOV_ID INTEGER CONSTRAINT [FK1_teste_geral] REFERENCES [imovel_conta]([IMOV_ID]) ON DELETE RESTRICT ON UPDATE RESTRICT," +
//					" LEITURA_AGUA INTEGER NOT NULL," +
//					" LEITURA_POCO INTEGER NOT NULL," +
//					" AGUA NUMERIC(13,2) NOT NULL," +
//					" ESGOTO NUMERIC(13,2) NOT NULL," +
//					" DEBITO NUMERIC(13,2) NOT NULL," +
//					" CREDITO NUMERIC(13,2) NOT NULL," +
//					" IMPOSTO NUMERIC(13,2) NOT NULL," +
//					" TOTAL NUMERIC(13,2) NOT NULL);";
					
	public static void executar(){
		try {
			//Busca todos os imoveis e manda calcular
			ArrayList<ImovelConta> colecaoImoveis = instancia.getControladorImovelConta().buscarImovelContas();
			
			for (ImovelConta imovel:colecaoImoveis){
				instancia.calcular(imovel);
			}
			
		} catch (Exception ex){
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());			
		} finally {
			Log.e(ConstantesSistema.CATEGORIA, "TESTE GERAL FINALIZADO");	
		}
	}
	
	private void calcular(ImovelConta imovel) throws ControladorException{
		HidrometroInstalado hidrometroAgua = getControladorHidrometroInstalado().
				buscarHidrometroInstaladoPorImovelTipoMedicao(imovel.getId(), ConstantesSistema.LIGACAO_AGUA);
		HidrometroInstalado hidrometroPoco = getControladorHidrometroInstalado().
				buscarHidrometroInstaladoPorImovelTipoMedicao(imovel.getId(), ConstantesSistema.LIGACAO_POCO);
		
		Teste teste = new Teste();
		teste.setImovelId(imovel.getId());
		
		//Seta leitura = leitura anterior + media
		if (hidrometroAgua!=null){
			 List<ConsumoAnteriores> consumos = getControladorConsumoAnteriores(). 
					 buscarConsumoAnterioresPorImovelTipoLigacao(imovel.getId(), ConstantesSistema.LIGACAO_AGUA);
			 int leitura = consumos.get(0).getConsumo().intValue() + 
					 hidrometroAgua.getConsumoMedio().intValue();
			 hidrometroAgua.setLeitura(leitura);
			 teste.setLeituraAgua(leitura);
		}
		if (hidrometroPoco!=null){
			List<ConsumoAnteriores> consumos = getControladorConsumoAnteriores(). 
					 buscarConsumoAnterioresPorImovelTipoLigacao(imovel.getId(), ConstantesSistema.LIGACAO_POCO);
			int leitura = consumos.get(0).getConsumo().intValue() + 
					hidrometroPoco.getConsumoMedio().intValue();
			hidrometroPoco.setLeitura(leitura);
			teste.setLeituraPoco(leitura);
		}
		
		//Manda Calcular
		getControladorConta().calcularConta(imovel,false,true);
		
		//Recupera valores calculados e joga no BD
		double agua = ControladorContaCategoria.getInstance().obterValorTotal(imovel.getId(), ConstantesSistema.LIGACAO_AGUA);
		teste.setAgua(agua);
		double esgoto = ControladorContaCategoria.getInstance().obterValorTotal(imovel.getId(), ConstantesSistema.LIGACAO_POCO);
		teste.setEsgoto(esgoto);
		double valorCreditos = getControladorCreditoRealizado().obterValorCreditoTotal(imovel.getId());	
		teste.setCredito(valorCreditos);
		double debitos = getControladorDebitoCobrado().obterValorDebitoTotal(imovel.getId());
		teste.setDebito(debitos);
		double impostos = getControladorContaImposto().obterValorImpostoTotal(imovel.getId());
		teste.setImposto(impostos);
		teste.atualizarTotal();
		
		//RepositorioTeste.inserir(teste);
	}
}

class Teste {
	
	private int imovelId;
	private int leituraAgua;
	private int leituraPoco;
	private double agua;
	private double esgoto;
	private double debito;
	private double credito;
	private double imposto;
	private double total;
	
	public int getImovelId() {
		return imovelId;
	}
	public void setImovelId(int imovelId) {
		this.imovelId = imovelId;
	}
	public int getLeituraAgua() {
		return leituraAgua;
	}
	public void setLeituraAgua(int leituraAgua) {
		this.leituraAgua = leituraAgua;
	}
	public int getLeituraPoco() {
		return leituraPoco;
	}
	public void setLeituraPoco(int leituraPoco) {
		this.leituraPoco = leituraPoco;
	}
	public double getAgua() {
		return agua;
	}
	public void setAgua(double agua) {
		this.agua = agua;
	}
	public double getEsgoto() {
		return esgoto;
	}
	public void setEsgoto(double esgoto) {
		this.esgoto = esgoto;
	}
	public double getDebito() {
		return debito;
	}
	public void setDebito(double debito) {
		this.debito = debito;
	}
	public double getCredito() {
		return credito;
	}
	public void setCredito(double credito) {
		this.credito = credito;
	}
	public double getImposto() {
		return imposto;
	}
	public void setImposto(double imposto) {
		this.imposto = imposto;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	
	public void atualizarTotal() {

		double valorConta = this.getValorContaSemImposto() - imposto;
		if (valorConta < 0d) {
			valorConta = 0d;
		}
		total =  Util.arredondar(valorConta, 2);
	}
	private double getValorContaSemImposto() {

		double valorContaSemImposto = (agua + esgoto + debito)
				- credito;

		if (valorContaSemImposto < 0d) {
			valorContaSemImposto = 0d;
		}
		return Util.arredondar(valorContaSemImposto, 2);
	}
}

class RepositorioTeste extends RepositorioBasico {
	
	public final String NOME_TABELA = "teste_geral";
	
	public void inserir(Teste teste) {
		ContentValues values = new ContentValues();
		values.put("IMOV_ID", teste.getImovelId());
		values.put("LEITURA_AGUA", teste.getLeituraAgua());
		values.put("LEITURA_POCO", teste.getLeituraPoco());
		values.put("AGUA", teste.getAgua());
		values.put("ESGOTO", teste.getEsgoto());
		values.put("DEBITO", teste.getDebito());
		values.put("CREDITO", teste.getCredito());
		values.put("IMPOSTO", teste.getImposto());
		values.put("TOTAL", teste.getTotal());	
		
		try {
			db.insert(NOME_TABELA, null, values);
		} catch (SQLException sqe) {
			sqe.printStackTrace();
			Log.e( ConstantesSistema.CATEGORIA , sqe.getMessage());			
		}		
	}
}