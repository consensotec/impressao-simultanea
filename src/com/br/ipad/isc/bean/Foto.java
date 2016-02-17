
package com.br.ipad.isc.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import com.br.ipad.isc.util.Util;

/**
 * <p>
 * Classe responsável pelo objeto Foto
 * 
 * @author Arthur Carvalho
 * @since 14/07/2012
 */
public class Foto extends ObjetoBasico implements Serializable {

    private static final long    serialVersionUID = -1958209598085014819L;
    
    private Integer id;
    
    private ImovelConta imovelConta;

    private String caminho;

    private Integer indicadorTransmitido;
    
    private Date dataFoto;
    
    private LeituraAnormalidade leituraAnormalidade;
    
    private ConsumoAnormalidade consumoAnormalidade;
    
    private Integer fotoTipo;
    
    private Integer tipoMedicao;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ImovelConta getImovelConta() {
		return imovelConta;
	}

	public void setImovelConta(ImovelConta imovelConta) {
		this.imovelConta = imovelConta;
	}

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

	public Integer getIndicadorTransmitido() {
		return indicadorTransmitido;
	}

	public void setIndicadorTransmitido(Integer indicadorTransmitido) {
		this.indicadorTransmitido = indicadorTransmitido;
	}

	public Date getDataFoto() {
		return dataFoto;
	}

	public void setDataFoto(String dataFoto) {
		if ( dataFoto != null ) {
			this.dataFoto = Util.getData(dataFoto);
		}
	}

	public LeituraAnormalidade getLeituraAnormalidade() {
		return leituraAnormalidade;
	}

	public void setLeituraAnormalidade(LeituraAnormalidade leituraAnormalidade) {
		this.leituraAnormalidade = leituraAnormalidade;
	}
	
	public ConsumoAnormalidade getConsumoAnormalidade() {
		return consumoAnormalidade;
	}

	public void setConsumoAnormalidade(ConsumoAnormalidade consumoAnormalidade) {
		this.consumoAnormalidade = consumoAnormalidade;
	}

	public Integer getFotoTipo() {
		return fotoTipo;
	}

	public void setFotoTipo(Integer fotoTipo) {
		this.fotoTipo = fotoTipo;
	}

	public Integer getTipoMedicao() {
		return tipoMedicao;
	}

	public void setTipoMedicao(Integer tipoMedicao) {
		this.tipoMedicao = tipoMedicao;
	}

	private static String[] colunas = new String[] {Fotos.ID, 
		Fotos.IMOVEL_CONTA_ID, Fotos.CAMINHO, Fotos.INDICADOR_TRANSMITIDO,
		Fotos.DATA, Fotos.LEITURA_ANORMALIDADE_ID,Fotos.CONSUMO_ANORMALIDADE_ID,
		Fotos.FOTO_TIPO,Fotos.MEDICAOTIPO};

	public String[] getColunas(){
		return colunas;
	}	

	public static final class Fotos implements BaseColumns {
        public static final String ID                    	= "FOTO_ID";
        public static final String IMOVEL_CONTA_ID       	= "IMOV_ID";
        public static final String CAMINHO               	= "FOTO_CAMINHO";
        public static final String INDICADOR_TRANSMITIDO 	= "FOTO_ICTRANSMITIDO";
        public static final String DATA                  	= "FOTO_DTFOTO";
        public static final String LEITURA_ANORMALIDADE_ID  = "LTAN_ID";
        public static final String CONSUMO_ANORMALIDADE_ID  = "CSAN_ID";
        public static final String FOTO_TIPO			    = "FOTO_TIPO";
        public static final String MEDICAOTIPO			    = "FOTO_MEDICAOTIPO";        
    }
  
	public String getNomeTabela(){
		return "foto";
	}
	
	public final class FotosTipos {
		public final String ID = " INTEGER PRIMARY KEY AUTOINCREMENT ";	
        public final String IMOVEL_CONTA_ID       	= " INTEGER CONSTRAINT [FK1_FOTO] REFERENCES [imovel_conta]([IMOV_ID]) ON DELETE RESTRICT ON UPDATE RESTRICT ";
        public final String CAMINHO               	= " VARCHAR(90) NOT NULL ";
        public final String INDICADOR_TRANSMITIDO 	= " INTEGER NOT NULL ";
        public final String DATA                  	= " TIMESTAMP NOT NULL ";
        public final String LEITURA_ANORMALIDADE_ID = " INTEGER CONSTRAINT [FK2_FOTO] REFERENCES [leitura_anormalidade]([LTAN_ID]) ON DELETE RESTRICT ON UPDATE RESTRICT ";
        public final String CONSUMO_ANORMALIDADE_ID = " INTEGER CONSTRAINT [FK3_FOTO] REFERENCES [consumo_anormalidade]([CSAN_ID]) ON DELETE RESTRICT ON UPDATE RESTRICT ";
        public final String FOTO_TIPO			    = " INTEGER NOT NULL ";
        public final String MEDICAOTIPO			    = " INTEGER NOT NULL ";

		private String[] tipos = new String[] {
				ID, IMOVEL_CONTA_ID, CAMINHO, INDICADOR_TRANSMITIDO, DATA,
				LEITURA_ANORMALIDADE_ID,CONSUMO_ANORMALIDADE_ID, FOTO_TIPO, MEDICAOTIPO};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
	
	public ContentValues preencherValues() {
		ContentValues values = new ContentValues();
		
		values.put( Fotos.IMOVEL_CONTA_ID, getImovelConta().getId() );
		values.put( Fotos.CAMINHO, getCaminho() );
		values.put( Fotos.INDICADOR_TRANSMITIDO, getIndicadorTransmitido() );
		
		if(getLeituraAnormalidade()!=null)
		{
			values.put( Fotos.LEITURA_ANORMALIDADE_ID, getLeituraAnormalidade().getId() );
		}
		
		if(getConsumoAnormalidade()!=null)
		{
			values.put(Fotos.CONSUMO_ANORMALIDADE_ID, getConsumoAnormalidade().getId());
		}
		
		values.put( Fotos.FOTO_TIPO, getFotoTipo() );
		values.put( Fotos.MEDICAOTIPO, getTipoMedicao() );
		
		if ( getDataFoto() != null ) {
			String dataStr = Util.convertDateToDateStr(Util.getCurrentDateTime());
			values.put( Fotos.DATA, dataStr );
		}
		
		return values;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Foto> preencherObjetos(Cursor cursor) {		
		ArrayList<Foto> retorno = null;
		
		if (cursor.moveToFirst()) {	
			int codigo = cursor.getColumnIndex(Fotos.ID);
			int idImovel = cursor.getColumnIndex(Fotos.IMOVEL_CONTA_ID);
			int caminho = cursor.getColumnIndex(Fotos.CAMINHO);
			int indicadorTransmitido = cursor.getColumnIndex(Fotos.INDICADOR_TRANSMITIDO);
			int data = cursor.getColumnIndex(Fotos.DATA);
			int idLeituraAnormalidade = cursor.getColumnIndex(Fotos.LEITURA_ANORMALIDADE_ID);
			int idConsumoAnormalidade = cursor.getColumnIndex(Fotos.CONSUMO_ANORMALIDADE_ID);
			int fotoTipo = cursor.getColumnIndex(Fotos.FOTO_TIPO);
			int medicaoTipo = cursor.getColumnIndex(Fotos.MEDICAOTIPO);
			
			retorno = new ArrayList<Foto>();
			
			do {	
				
				Foto foto = new Foto();
				foto.setId(cursor.getInt(codigo));
				ImovelConta imovelConta = new ImovelConta();
				imovelConta.setId(cursor.getInt(idImovel));
				foto.setImovelConta(imovelConta);
				foto.setCaminho(cursor.getString(caminho));
				foto.setIndicadorTransmitido(cursor.getInt(indicadorTransmitido));
				foto.setDataFoto(cursor.getString(data));
				
				if(Util.getIntBanco(cursor,Fotos.LEITURA_ANORMALIDADE_ID,idLeituraAnormalidade) != null)
				{
					LeituraAnormalidade leituraAnormalidade = new LeituraAnormalidade();
					
					leituraAnormalidade.setId(cursor.getInt(idLeituraAnormalidade));
					
					foto.setLeituraAnormalidade(leituraAnormalidade);
				}
				
				if(Util.getIntBanco(cursor,Fotos.CONSUMO_ANORMALIDADE_ID,idConsumoAnormalidade) != null)
				{
					ConsumoAnormalidade consumoAnormalidade = new ConsumoAnormalidade();
					
					consumoAnormalidade.setId(cursor.getInt(idConsumoAnormalidade));
					
					foto.setConsumoAnormalidade(consumoAnormalidade);
				}
				
				foto.setFotoTipo(cursor.getInt(fotoTipo));
				foto.setTipoMedicao(cursor.getInt(medicaoTipo));
				
				retorno.add(foto);
				
			} while (cursor.moveToNext());
		}		
		return retorno;
	}
}