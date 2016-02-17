package com.br.ipad.isc.repositorios;

import java.util.ArrayList;
import com.br.ipad.isc.bean.HidrometroInstalado;
import com.br.ipad.isc.excecoes.RepositorioException;

public interface IRepositorioHidrometroInstalado {
	
	public HidrometroInstalado buscarHidrometroInstaladoPorImovelTipoMedicao(Integer imovelId, 
			Integer tipoMedicao) throws RepositorioException;
	public HidrometroInstalado buscarLeituraHidrometroTipoMedicao(Integer imovelId, 
			Integer tipoMedicao) throws RepositorioException;
	public ArrayList<HidrometroInstalado> buscarHidrometroInstaladoImovel(Integer imovelId) throws RepositorioException;
}
