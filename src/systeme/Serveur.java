package systeme;

import java.io.File;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.acl.Group;
import java.util.ArrayList;

import systeme.rmi.ClientRMI;
import systeme.rmi.ServeurRMI;
import systeme.tools.Encryptage;
import modules.documents.social.Publication;
import modules.gestionUtilisateur.Groupe;
import modules.gestionUtilisateur.Utilisateur;

public class Serveur implements Serializable {
	
	private ArrayList<Utilisateur> utilisateursInscrits;
	private ArrayList<ClientRMI> utilisateursConnectes;
	private ServeurRMI serveur;
	private ArrayList<Publication> publications;
	
	public Serveur(){
		utilisateursInscrits =  new ArrayList<Utilisateur>();
		utilisateursConnectes = new ArrayList<ClientRMI>();
		publications = new ArrayList<Publication>();
		serveur = new ServeurRMI(this);
	}
	
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
	public ArrayList<ClientRMI> getUtilisateursConnectes(){
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
	public void ajouterClient(ClientRMI c){
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
				if(user.getLogin().equals(login)){
					utilisateur = user;
				}
			}
		}
		
		return utilisateur;
	}
	
	
	/**
	 * Verification de l'existance de l'utilisateur et
	 * inscription d'un utilisateur dont le mot de passe a déjà été encrypté côté client si l'utilisateur n'existe pas
	 * @param login
	 * @param motDePasse
	 * @return false si le login de l'utilisateur est déjà existant, true sinon
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
	 * Verification de l'existance de l'utilisateur et
	 * inscription d'un utilisateur dont le mot de passe a déjà été encrypté côté client si l'utilisateur n'existe pas
	 * @param login
	 * @param motDePasse
	 * @return false si le login de l'utilisateur est déjà existant, true sinon
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
	
	/**
	 * Verification de l'existance de l'utilisateur et
	 * inscription d'un utilisateur dont le mot de passe a déjà été encrypté côté client si l'utilisateur n'existe pas
	 * Créer un repertoire pour l'utilisateur sur le serveur
	 * @param login
	 * @param motDePasse
	 * @return false si le login de l'utilisateur est déjà existant, true sinon
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	public boolean inscriptionAvecRepertoireUtilisateur(String login, String motDePasse){
		boolean existant = false;
		try {
			existant = inscription(login, motDePasse);
			creerUnRepertoireUtilisateur(getUtilisateurInscrit(login), "/git/miage_projet_framework/docServeur/");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return existant;
		

	}
	
	/**
	 * Realise une connexion entre un client et un serveur
	 * @param utilisateur : l'utilisateur � connecter
	 * @return true (Si connection �tablit)
	 */
	public boolean connexion(String login,  String motDepasse , ClientRMI c) {

		boolean result = false;
		
		
			Utilisateur util;
			try {
				if(utilisateurExistant(login)){
					// on récupère l'objet utilisateur
					util = getUtilisateurInscrit(login);
					// Test si le mdp est correct
					if(util.autoriserConnexion(motDepasse)){
						result = true;
					}	
				}
				
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

		return result;
	}
	
	/**
	 * 
	 * @param login
	 * @return
	 */
	public ClientRMI getClientConnecte(String login){
		ClientRMI cl = null;
		for(ClientRMI c :  getUtilisateursConnectes() ){
			if(c.getUtilisateur().getLogin().equals(login)){
				cl = c;
			}
		}
		
		return cl;
	}
	
	/**
	 * Affecte le serveur rmi au serveur
	 * @param serv
	 */
	public void setServeurRMI(ServeurRMI serv){
		this.serveur = serv;
	}
	
	/**
	 * Renvoie le serveur RMI
	 * @return serveur
	 */
	public ServeurRMI getServeurRMI(){
		return serveur;
	}
	
	/**
	 * Supprime le clientRMI de la liste des clients connectés
	 * @param client
	 */
	public void supprimerUnClient(ClientRMI client)
	{
		System.out.println(""+getUtilisateursConnectes().size());
		System.out.println(client.toString());
		getUtilisateursConnectes().remove(client);
		System.out.println(""+getUtilisateursConnectes().size());
		getUtilisateursConnectes().remove(indexClient(client));
	}
	
	/**
	 * Renvoie l'index du client dans la liste des clients connectés
	 * @param client
	 * @return si trouvé num = index du client connecté  , -1 sinon
	 */
	public int indexClient(ClientRMI client){
		int num = -1;
		int compteur = 0;
		for(ClientRMI cl : getUtilisateursConnectes()){
			if(cl.getUtilisateur().equals(client.getUtilisateur())){
				num = compteur;
			}
			compteur++;
		}
		
		
		
		return num;
	}

	/**
	 * Créer un repertoire pour l'utilisateur 
	 * @param u : utilisateur
	 * @param path : chemin avant le nom d'utilisateur (Ex : /utilisateurs/ )
	 */
	public void creerUnRepertoireUtilisateur(Utilisateur u , String path){
		File repertoire = new File(path+""+u.getLogin());
		u.ajouterRepertoire(repertoire);
		repertoire.mkdirs();
	}
	
	/**
	 * Créer un repertoire
	 * @param path : chemin du futur repertoire
	 */
	public void creerUnRepertoire(String path){
		File repertoire = new File(path);
		repertoire.mkdirs();
	}
	
	/**
	 * Distribue un message envoyé par un client aux autres clients 
	 * @param message
	 */
	public void distribuerMessage(String message,ClientRMI expediteur){
		for(ClientRMI c : getUtilisateursConnectes()){
			c.recevoirMessage(message,expediteur);
		}
	}
	
	/**
	 * Renvoie l'utilisateur du clientRMI si trouvé dans la liste des clients connectés
	 * @param cl
	 * @return l'utilisateur si il est connecté , null sinon
	 */
	public Utilisateur utilisateurConnecte(ClientRMI cl){
		Utilisateur u = null;
		if(getUtilisateursConnectes() != null){
			int num = indexClient(cl);
			if(num != -1){
				u = getUtilisateursConnectes().get(indexClient(cl)).getUtilisateur();
			}
		}
		return u;
	}

	public ArrayList<Publication> getPublications() {
		return publications;
	}

	public void setPublications(ArrayList<Publication> publications) {
		this.publications = publications;
	}
	
	public void AddPublication(Publication publication)
	{
		getPublications().add(publication);
	}
	/**
	 * Affiche l'ensemble des publications visibles pour un utilisateur 
	 * @param utilisateur : l'utilisateur a verifier
	 * @return ArrayList<Publication> : return publication visibles
	 */
	public ArrayList<Publication> getPublicationsVisibles(Utilisateur utilisateur)
	{
		
		ArrayList<Publication> publicationsVisibles = new ArrayList<Publication>();
		ArrayList<Publication> publications = getPublications(); 
		
		try {
		for(int i=0; i < publications.size(); i++ )
		{
			
			
			ArrayList<Utilisateur> utilisateurs= publications.get(i).getVisibiliteUtilisateur();
			ArrayList<Groupe> groupes = publications.get(i).getVisibiliteGroupe();
			if(utilisateurs==null)
			{
				throw new NullPointerException();
			}
			for(Utilisateur util : utilisateurs)
			{
				if(util.equals(utilisateur))
				{
					publicationsVisibles.add(publications.get(i));
				}
			}
			/*for(Groupe groupe : groupes )
			{
				ArrayList<Groupe> groupesUtilisateurs = utilisateur.getGroupes();
				for(Groupe groupeUtilisateur : groupesUtilisateurs)
				{
					if(groupeUtilisateur.equals(groupe));
					{
						publicationsVisibles.add(publications.get(i));
					}
				}
			}*/
				
		}
		}
		catch(NullPointerException e)
		{
			e.printStackTrace();
		}
		
		return publicationsVisibles; 
	}

	
}
