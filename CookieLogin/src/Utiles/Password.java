package Utiles;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

/** Archivo creado para la securización del sistema de logueo*/

public class Password {
	
	/** Creación del token */
	public String token() {
		try {
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			byte[] bytes = new byte[16];
			random.nextBytes(bytes);
			return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Ha habido un error en el servidor");
			return null;
		}
	}
	
	/** Esta función se ha utilizado para la generación de la password inicialmente
	 * pero posteriormente no es necesaria utilizarla */
	
	public void creaPass(byte[] password) throws Exception {
		byte[] salt = generaSalt(12);
		byte[] info = concatena(password, salt);

		MessageDigest mensaje = MessageDigest.getInstance("SHA-256");
		
		byte[] hash = mensaje.digest(info);
		
		// Creación de ficheros para almacenar información relevante
		saveBytes(salt, "salt.bin");
		saveBytes(hash, "password.bin");
		
		clearArray(info);
		clearArray(password);
		clearArray(salt);
		clearArray(hash);
	}
	
	/**Comprobación de la pass */
	public boolean checkPassword(byte[] password) throws Exception{
		
		byte[] salt = loadBytes("salt.bin");
		byte[] info = concatena(password, salt);
		MessageDigest mensaje = MessageDigest.getInstance("SHA-256");
		byte[] UsuarioHash  = mensaje.digest(info);
		byte[] PassHash = loadBytes("password.bin");
		boolean boolArray = Arrays.equals(UsuarioHash, PassHash);
		
		// Limpiamos datos
		clearArray(password);
		clearArray(info);
		clearArray(PassHash);
		clearArray(UsuarioHash);
		return boolArray;
	}
	
	/** Gestión del fichero de Bytes */
	
	public void saveBytes(byte[] byteArray, String fileName) {
		try {
			FileOutputStream fOS = new FileOutputStream(fileName);
			try {
				fOS.write(byteArray);
				fOS.close();
			} catch (IOException e) {
				System.out.println("Error en ficheros");
			}
		} catch (FileNotFoundException e) {
			System.out.println("Error en ficheros");
		}
	}
	
	/** Se carga la pass en el fichero */
	private byte[] loadBytes(String data) {
		File file = new File(data);
		byte[] bytes;
		try {
			bytes = Files.readAllBytes(file.toPath());
			return bytes;
		} catch (IOException e) {
			System.out.println("Error en ficheros");
			return null;
		}
	}
	
	/** Concatenación de arrays */
	
	private byte[] concatena(byte[] bA, byte[] bB) {
		int length = bA.length + bB.length;
		byte[] newArray = new byte[length];
		for(int i = 0; i < bA.length; i ++) {
			newArray[i] = bA[i];
		};				
		for(int i = 0; i < bB.length; i++) {
			newArray[bA.length + i] = bB[i];
		};
		return newArray;
	}
	
	/** Función para generar salt */
	private byte[] generaSalt(int n) {
		try {
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			byte[] randomBytes = new byte[n];
			random.nextBytes(randomBytes);
			return randomBytes;
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Error en servidor");
			return null;
		}
	}
	
	/** Clear Array */
	
	private void clearArray(byte[] b) {
		for(int i = 0; i < b.length; i++) {
			b[i] = 0;
		}
	}
	
	
}
