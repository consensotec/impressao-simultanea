
package com.br.ipad.isc.bean.helpers;

import java.io.Serializable;

import com.br.ipad.isc.bean.ImovelConta;

/**
 * [] Classe que guarda os dados necessários para guardar a foto tirada no banco
 * 
 * @author Fernanda Almeida
 * @since 05/11/2012
 */
public class CameraHelper implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private ImovelConta imovel;
	private Integer medicaoTipo;
	private Integer idLeituraAnormalidade;
	private Integer idConsumoAnormalidade;
	private Integer fotoTipo;
	
		
	public CameraHelper(ImovelConta imovel, Integer medicaoTipo,
			Integer idLeituraAnormalidade,Integer idConsumoAnormalidade, Integer fotoTipo) {
		super();
		this.imovel = imovel;
		this.medicaoTipo = medicaoTipo;
		this.idLeituraAnormalidade = idLeituraAnormalidade;
		this.idConsumoAnormalidade = idConsumoAnormalidade;
		this.fotoTipo = fotoTipo;
	}
	
	public ImovelConta getImovel() {
		return imovel;
	}
	
	public void setImovel(ImovelConta imovel) {
		this.imovel = imovel;
	}
	
	public Integer getMedicaoTipo() {
		return medicaoTipo;
	}
	
	public void setMedicaoTipo(Integer medicaoTipo) {
		this.medicaoTipo = medicaoTipo;
	}
	
	public Integer getIdLeituraAnormalidade() {
		return idLeituraAnormalidade;
	}
	
	public void setIdLeituraAnormalidade(Integer idLeituraAnormalidade) {
		this.idLeituraAnormalidade = idLeituraAnormalidade;
	}
	
	public Integer getIdConsumoAnormalidade() {
		return idConsumoAnormalidade;
	}

	public void setIdConsumoAnormalidade(Integer idConsumoAnormalidade) {
		this.idConsumoAnormalidade = idConsumoAnormalidade;
	}

	public Integer getFotoTipo() {
		return fotoTipo;
	}
	
	public void setFotoTipo(Integer fotoTipo) {
		this.fotoTipo = fotoTipo;
	}
}