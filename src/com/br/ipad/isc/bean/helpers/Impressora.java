
package com.br.ipad.isc.bean.helpers;

import java.io.Serializable;

/**
 * [] Classe Básica - Impressora
 * 
 * @author Carlos Chaves
 * @since 17/07/2012
 */
public class Impressora implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String bluetoothAdress;
	private String bluetoothName;
	public String getBluetoothAdress() {
		return bluetoothAdress;
	}
	public void setBluetoothAdress(String bluetoothAdress) {
		this.bluetoothAdress = bluetoothAdress;
	}
	public String getBluetoothName() {
		return bluetoothName;
	}
	public void setBluetoothName(String bluetoothName) {
		this.bluetoothName = bluetoothName;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bluetoothAdress == null) ? 0 : bluetoothAdress.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Impressora other = (Impressora) obj;
		if (bluetoothAdress == null) {
			if (other.bluetoothAdress != null)
				return false;
		} else if (!bluetoothAdress.equals(other.bluetoothAdress))
			return false;
		return true;
	}
	
	
	
	

}
