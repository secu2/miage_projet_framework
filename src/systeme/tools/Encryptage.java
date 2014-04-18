package systeme.tools;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class Encryptage {
	
	/**
	 * Permet d'encrypter un mot de passe à 
	 * l'aide de l'algorythme sha1
	 * @param motDePasse : le mot de passe à encrypter
	 * @return Le mot de passe encrypté
	 * @throws NoSuchAlgorithmException Si l'algorythme d'encryptage n'est pas disponible
	 * @throws UnsupportedEncodingException
	 */
	public static String encrypterMotDePasse(String motDePasse) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		MessageDigest encryptage = MessageDigest.getInstance("SHA-1");
		encryptage.reset();
		encryptage.update(motDePasse.getBytes("UTF-8"));
		return byteToHex(encryptage.digest());
	}
	
	/**
	 * Méthode permettant transformer un tableau de bits en une chaine hexadecimale
	 * @param hash
	 * @return result : chaine hexadecimale
	 */
	private static String byteToHex(final byte[] hash){
		Formatter formatter = new Formatter();
		for(byte b : hash){
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
	    formatter.close();
	    return result;
	}
	
	/**
	 * Permet de comparer deux mots de passes
	 * @param password1
	 * @param password2
	 * @return true si les mots de passe correspondent, false sinon
	 */
	public static boolean comparerMotsDePasse(String password1, String password2){
		return password1.equals(password2);
	}
}
