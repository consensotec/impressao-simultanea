
package com.br.ipad.isc.controladores;
import android.content.Context;

import com.br.ipad.isc.bean.QualidadeAgua;
import com.br.ipad.isc.excecoes.ControladorException;

public interface IControladorQualidadeAgua {
	
	public void setContext(Context ctx );
	
	public QualidadeAgua buscarQualidadeAguaPorLocalidadeSetorComercial(Integer idLocalidade, Integer idSetorComercial) throws ControladorException;
	public QualidadeAgua buscarQualidadeAguaPorLocalidade(Integer idLocalidade) throws ControladorException;
	public QualidadeAgua buscarQualidadeAguaSemLocalidade() throws ControladorException;
		
}