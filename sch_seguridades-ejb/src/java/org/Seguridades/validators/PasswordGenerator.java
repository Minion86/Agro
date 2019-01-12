/**
 * Copyright 2013 MINISTERIO DE RELACIONES LABORALES - ECUADOR 
 * Todos los derechos reservados
 **/

package org.Seguridades.validators;

/**
 * @Agregar Aquí la descrpción de la clase
 * PasswordGenerator.java
 *   
 * @Date Aug 8, 2014 10:43:38 AM
 * @author Alex Román MRL(TIC'S)
 */
public class PasswordGenerator {
 
	public static String NUMEROS = "0123456789";
 
	public static String MAYUSCULAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
 
	public static String MINUSCULAS = "abcdefghijklmnopqrstuvwxyz";
 
	public static String ESPECIALES = "@#$%";
 
	//
	public static String getPinNumber() {
		return getPassword(NUMEROS, 4);
	}
 
	public static String getPassword() {
		return getPassword(8);
	}
 
	public static String getPassword(int length) {
		return getPassword(NUMEROS + MAYUSCULAS + MINUSCULAS, length);
	}
 
	public static String getPassword(String key, int length) {
		String pswd = "";
 
		for (int i = 0; i < length; i++) {
			pswd+=(key.charAt((int)(Math.random() * key.length())));
		}
 
		return pswd;
	}
}