package com.br.ipad.isc.bean;

import java.io.Serializable;
import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.util.Log;

import com.br.ipad.isc.excecoes.RepositorioException;
import com.br.ipad.isc.repositorios.RepositorioBasico;
import com.br.ipad.isc.util.ConstantesSistema;
import com.br.ipad.isc.util.Util;

/**
 * [] Classe Básica - Sequencial Rota Marcaçao
 * 
 * @author Carlos Chaves
 * @since 14/09/2012
 */
public class SequencialRotaMarcacao extends ObjetoBasico implements Serializable {

	private static final long serialVersionUID = 1L;

	//Quantidade de pipes que existe no registro tipo 1.
	public static final int QUANTIDADE_REGISTRO_ARQUIVO_TEXTO = 75;
	
	private Integer id;
	private ImovelConta matricula;
	private Integer anoMesReferencia;
	
	public SequencialRotaMarcacao(Integer id, ImovelConta matricula,
			Integer anoMesReferencia) {
		super();
		this.id = id;
		this.matricula = matricula;
		this.anoMesReferencia = anoMesReferencia;
	}
	
	public SequencialRotaMarcacao() {
		super();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public ImovelConta getMatricula() {
		return matricula;
	}

	public void setMatricula(ImovelConta matricula) {
		this.matricula = matricula;
	}


	public Integer getAnoMesReferencia() {
		return anoMesReferencia;
	}

	public void setAnoMesReferencia(Integer anoMesReferencia) {
		this.anoMesReferencia = anoMesReferencia;
	}
	
	private static String[] colunas = new String[] { SequencialRotaMarcacoes.ID, SequencialRotaMarcacoes.MATRICULA,SequencialRotaMarcacoes.ANOMESREFERENCIA};

	                                   	  	
	public String[] getColunas(){
		return colunas;
	}	
	
	public static final class SequencialRotaMarcacoes implements BaseColumns {
		public static final String ID = "RMSE_ID";
		public static final String MATRICULA = "IMOV_ID";
		public static final String ANOMESREFERENCIA = "RMSE_AMREFERENCIA";
	}

	public String getNomeTabela(){
		return "rota_marcacao_sequencia";
	}
		
	public final class SequencialRotaMarcacoesTipos {
	
		public final String ID = " INTEGER PRIMARY KEY";
		public final String MATRICULA = "  INTEGER  NOT NULL CONSTRAINT [FK1_ROTA_MARCACAO_SEQUENCIA] REFERENCES [imovel_conta]([IMOV_ID]) ON DELETE RESTRICT ON UPDATE RESTRICT";
		public final String ANOMESREFERENCIA = " INTEGER NULL";
		
		private String[] tipos = new String[] {ID, MATRICULA, ANOMESREFERENCIA};
		
		public String[] getTipos(){
			return tipos;
		}
	}
	
	public ContentValues preencherValues() {
		ContentValues values = new ContentValues();
		
		if (getId()!=null){
			values.put(SequencialRotaMarcacoes.ID, getId());
		}
		
		values.put(SequencialRotaMarcacoes.MATRICULA, getMatricula().getId());
				
		if(getAnoMesReferencia()!=null){
			values.put(SequencialRotaMarcacoes.ANOMESREFERENCIA, getAnoMesReferencia());
		}
		return values;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<SequencialRotaMarcacao> preencherObjetos(Cursor cursor) {		
			
		int codigo = cursor.getColumnIndex(SequencialRotaMarcacoes.ID);
		int anoMesReferencia = cursor.getColumnIndex(SequencialRotaMarcacoes.ANOMESREFERENCIA);
		int matricula = cursor.getColumnIndex(SequencialRotaMarcacoes.MATRICULA);
	
		
		ArrayList<SequencialRotaMarcacao> arrayListSequencialRotaMarcacao = new ArrayList<SequencialRotaMarcacao>();
		
		do{
			SequencialRotaMarcacao sequencialRotaMarcacao = new SequencialRotaMarcacao();
								
			sequencialRotaMarcacao.setId(cursor.getInt(codigo));
			sequencialRotaMarcacao.setAnoMesReferencia(Util.getIntBanco(cursor, SequencialRotaMarcacoes.ANOMESREFERENCIA, anoMesReferencia));
			ImovelConta objImovelConta;
			
			try {
				objImovelConta = (ImovelConta)RepositorioBasico.getInstance()
						.pesquisarPorId(cursor.getInt(matricula), new ImovelConta());
				
				sequencialRotaMarcacao.setMatricula(objImovelConta);
			} catch (RepositorioException e) {
				Log.e( ConstantesSistema.CATEGORIA , e.getMessage());
				e.printStackTrace();
			}
			
			arrayListSequencialRotaMarcacao.add(sequencialRotaMarcacao);
			
		}while (cursor.moveToNext());
		
		return arrayListSequencialRotaMarcacao;
	}
	

}