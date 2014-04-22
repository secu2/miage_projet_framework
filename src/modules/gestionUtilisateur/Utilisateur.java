package modules.gestionUtilisateur;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import modules.documents.Document;
import modules.documents.social.Publication;

import systeme.BaseDeDonnees;
import systeme.tools.Encryptage;

/**
 * Utilisateur.java
 * @author never
 *
 */
public class Utilisateur {
	private String login;
    private String motDePasse;
    private ArrayList<Groupe> groupes;
    private ArrayList<Publication> publications;
     
    /**
     * Construit un objet Utilisateur en fournissant le login et le mot de passe
     * @param login
     * @param motDePasse
     * @throws UnsupportedEncodingException 
     * @throws NoSuchAlgorithmException 
     */
    public Utilisateur(String login, String motDePasse) throws NoSuchAlgorithmException, UnsupportedEncodingException{
    	this.login = login;
    	this.motDePasse = Encryptage.encrypterMotDePasse(motDePasse);
    }
    /**
     * Construit un utilisateur et l'enregistre dans la base de données
     * @param bdd : La base ou enregistrer l'utilisateur
     * @param login : Le login de l'utilisateur
     * @param motDePasse : Le mot de passe de l'utilisateur
     * @throws SQLException 
     * @throws UnsupportedEncodingException 
     * @throws NoSuchAlgorithmException 
     */
    public Utilisateur(BaseDeDonnees bdd, String login, String motDePasse) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException{
    	this.login = login;
    	// Affecte le mot de passe encrypté
    	this.motDePasse = Encryptage.encrypterMotDePasse(motDePasse);
    	sauvegarderPersistant(bdd);
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
	 * Crypte et Affecte un mot de passe pour cet utilisateur
	 * @param motDePasse
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	public void setMotDePasse(String motDePasse) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		this.motDePasse = Encryptage.encrypterMotDePasse(motDePasse);
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
	 * Permet de sauvegarder l'utilisateur dans la BDD
	 * Important pour la persistance des données
	 * @param bdd
	 * @throws SQLException 
	 */
	public void sauvegarderPersistant(BaseDeDonnees bdd) throws SQLException{
		bdd.sauvegarderUtilisateur(login, motDePasse);
	}
	
	/**
	 * Permet de vérifier la correspondance du mot de passe en clair de l'utilisateur avec celui fourni en parametre
	 * @param motDePasse en clair
	 * @return true si on autorise la connexion, false sinon.
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	public boolean autoriserConnexion(String motDePasse) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		return Encryptage.comparerMotsDePasse(this.motDePasse, Encryptage.encrypterMotDePasse(motDePasse));
	}
	
	/**
	 * Permet de vérifier la correspondance du mot de passe encrypté de l'utilisateur avec celui fourni en parametre
	 * @param motDePasse crypté en sha1
	 * @return true si on autorise la connexion, false sinon.
	 */
	public boolean autoriserConnexionSecurisee(String motDePasse){
		return Encryptage.comparerMotsDePasse(this.motDePasse, motDePasse);
	}
	
	/**
	 * Renvoie la liste des groupes de l'utilisateur
	 * @return groupes
	 */
	public ArrayList<Groupe> getGroupes() {
		return groupes;
	}
	
	/**
	 * Affecte une liste de groupes à l'utilisateur
	 * @param groupes
	 */
	public void setGroupes(ArrayList<Groupe> groupes) {
		this.groupes = groupes;
	}
	
	/**
	 * Permet de créer un groupe
	 * @param nomGroupe : nom du groupe
	 */
	public void creerUnGroupe(String nomGroupe){
		// TODO : Voir pour la gestion des id
		getGroupes().add(new Groupe(idGroupe, nomGroupe));
	}
	
	/**
	 * Supprime le groupe de la liste des groupes de l'utilisateur
	 * @param nomGroupe
	 */
	public void supprimerUnGroupe(String nomGroupe){
		// TODO : Voir pour la gestion des id
		getGroupes().remove();
	}
	
	/**
	 * Renvoie la liste des publications de l'utilisateur
	 * @return publications
	 */
	public ArrayList<Publication> getPublications(){
		return publications;
	}
	
	/**
	 * Créer une publication pour cet utilisateur et l'ajoute à l'ensemble des publications de l'utilisateur
	 * @param utilisateursAutorises
	 * @param groupesAutorises
	 * @param doc
	 */
	public void publierUnDocument(ArrayList<Utilisateur> utilisateursAutorises, ArrayList<Groupe> groupesAutorises, Document doc, Date dateFin){
		getPublications().add(new Publication(new Date(), dateFin, utilisateursAutorises, groupesAutorises, this, doc));
	}
	
	
	

}
