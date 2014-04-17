package modules.gestionUtilisateur;

import java.util.Random;

/**
 * Utilisateur.java
 * @author never
 *
 */
public class Utilisateur {
    
    private String login;
    private String motDePasse;
    
    /**
     * Construit un objet Utilisateur en fournissant le login et le mot de passe
     * @param login
     * @param motDePasse
     */
    public Utilisateur(String login,String motDePasse){
    	this.login = login;
    	this.motDePasse = motDePasse;
    }
    
    /**
     * Renvoie l'identifiant (login) de l'utilisateur
     * @return login
     */
	public String getLogin() {
		return login;
	}
	
	/**
	 * Affecte le login de l'utilisateur
	 * @param login
	 */
	public void setLogin(String login) {
		this.login = login;
	}
	
	/**
	 * Affecte un mot de passe pour cet utilisateur
	 * @param motDePasse
	 */
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}
	
	/**
	 * Génére un nouveau mot de passe aléatoirement et le renvoie
	 * @param n : taille du mot de passe à générer
	 * @return motDePasse : le mot de passe généré aléatoirement
	 */
	public String genererMotDePasse(int n){
		String motDePasse = new String();
		String alphabet = new String("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"); //9
		int tailleAlphabet = alphabet.length(); 

		Random r = new Random();

		// Génère un mot de passe de taille n
		for (int i=0; i<n; i++){
			motDePasse = motDePasse + alphabet.charAt(r.nextInt(tailleAlphabet)); 
		}
		
		return motDePasse;
	}
	
	/**
	 * 
	 */
	public void ajouterUtilisateurBD(){
		
	}
	
	
    
    

}
