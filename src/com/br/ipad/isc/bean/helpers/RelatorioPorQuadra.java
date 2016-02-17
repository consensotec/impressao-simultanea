package com.br.ipad.isc.bean.helpers;

import java.io.Serializable;

/**
 * [] Classe Básica - RelatorioPorQuadra
 * 
 * @author Carlos Chaves
 * @since 12/09/2012
 */
public class RelatorioPorQuadra implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer totalImoveis;
	private Integer totalImoveisVisitados;
	private Integer totalImoveisNaoVisitados;
	private Integer numeroQuadra;
	

	public RelatorioPorQuadra(Integer totalImoveis,Integer totalImoveisVisitados, Integer totalImoveisNaoVisitados, Integer numeroQuadra) {
		super();
		this.totalImoveis = totalImoveis;
		this.totalImoveisVisitados = totalImoveisVisitados;
		this.totalImoveisNaoVisitados = totalImoveisNaoVisitados;
		this.numeroQuadra = numeroQuadra;
	}
	public Integer getTotalImoveis() {
		return totalImoveis;
	}
	public void setTotalImoveis(Integer totalImoveis) {
		this.totalImoveis = totalImoveis;
	}
	public Integer getTotalImoveisVisitados() {
		return totalImoveisVisitados;
	}
	public void setTotalImoveisVisitados(Integer totalImoveisVisitados) {
		this.totalImoveisVisitados = totalImoveisVisitados;
	}
	public Integer getTotalImoveisNaoVisitados() {
		return totalImoveisNaoVisitados;
	}
	public void setTotalImoveisNaoVisitados(Integer totalImoveisNaoVisitados) {
		this.totalImoveisNaoVisitados = totalImoveisNaoVisitados;
	}
	public Integer getNumeroQuadra() {
		return numeroQuadra;
	}
	public void setNumeroQuadra(Integer numeroQuadra) {
		this.numeroQuadra = numeroQuadra;
	}
	
	
	
}
