package systeme;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import systeme.rmi.ServeurRMI;
import systeme.tools.Encryptage;

import modules.gestionUtilisateur.Utilisateur;

public class Serveur {
	
	public ArrayList<Utilisateur> utilisateursInscrits;
	public ArrayList<Client> utilisateursConnectes;
	public ServeurRMI serveur;
	
	/**
	 * Renvoie la liste des utilisateurs inscrits
	 * @return utilisateursInscrits
	 */
	public ArrayList<Utilisateur> getUtilisateursInscrits(){
		return utilisateursInscrits;
	}
	
	/**
	 * Renvoie la liste des clients connectés
	 * @return utilisateursConnectes
	 */
	public ArrayList<Client> getUtilisateursConnectes(){
		return utilisateursConnectes;
	}
	
 	/**
 	 * Permet de tester si l'utilisateur de login 'login' existe
 	 * @param login : login de l'utilisateur
 	 * @return true si l'utilisateur existe , false sinon
 	 */
	public boolean utilisateurExistant(String login){
		boolean existant = false;
		for(Utilisateur u : getUtilisateursInscrits()){
			if(u.getLogin().equals(login)){
				existant = true;
			}
		}
		return existant;
	}
	
	/**
	 * Ajoute un client à la liste des clients du serveur
	 * @param c : client
	 */
	public void ajouterClient(Client c){
		getUtilisateursConnectes().add(c);
	}
	
	/**
	 * Ajoute un utilisatuer à la liste des utilisateurs inscrits sur le serveur
	 * @param u : utilisateur
	 */
	public void ajouterUtilisateur(Utilisateur u){
		getUtilisateursInscrits().add(u);
	}
	
	/**
	 * Renvoie l'utilisateur de login 'login'
	 * @param login : login de l'utilisateur
	 * @return utilisateur si existant , null sinon
	 */
	public Utilisateur getUtilisateurInscrit(String login){
		Utilisateur utilisateur = null;
		if(utilisateurExistant(login)){
			for(Utilisateur user : getUtilisateursInscrits()){
				if(user.equals(login)){
					utilisateur = user;
				}
			}
		}
		
		return utilisateur;
	}
	
	
	/**
	 * Inscription d'un utilisateur dont le mot de passe a déjà été encrypté côté client
	 * @param login
	 * @param motDePasse
	 * @return false si le login de l'utilisateur est éjà existant, true sinon
	 */
	public boolean inscriptionSecurisee(String login, String motDePasse){
		
		boolean existant = false;
		if(!utilisateurExistant(login)){
			Utilisateur u = new Utilisateur(login);
			u.setMotDePasseSecurise(motDePasse);
			ajouterUtilisateur(u);
		}
		else{
			existant = true;
		}
		
		return existant;
		
	}
	
	
	
	/**
	 * Inscription d'un utilisateur
	 * @param login
	 * @param motDePasse
	 * @return false si le login de l'utilisateur est éjà existant, true sinon
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	public boolean inscription(String login, String motDePasse) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		

		boolean existant = false;
		if(!utilisateurExistant(login)){
			Utilisateur u = new Utilisateur(login,motDePasse);
			ajouterUtilisateur(u);
		}
		else{
			existant = true;
		}
		
		return existant;
		

	}

}
