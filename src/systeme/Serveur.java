package systeme;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.TreeMap;

import modules.chat.Conversation;
import modules.chat.Message;
import modules.chat.MessageConversation;
import modules.chat.MessagePrive;
import modules.documents.social.Publication;
import modules.gestionUtilisateur.Groupe;
import modules.gestionUtilisateur.Utilisateur;
import systeme.rmi.ServeurRMI;
import systeme.tools.Encryptage;



public class Serveur implements Serializable {
	static int REGISTRY_PORT = 1099;
	private ServeurRMI serveur; 
	
	/**
	 * Constructeur du serveur : 
	 * Créer un objet serveur  qui lancera le serveur localement
	 */
	public Serveur() {
		// TODO Auto-generated method stub
		try {
			
			LocateRegistry.createRegistry(REGISTRY_PORT);
	
			
			ServeurRMI informationImpl = new ServeurRMI(this);
			serveur = informationImpl;
			String url = "rmi://" + InetAddress.getLocalHost().getHostAddress() + "/fram";
			System.out.println("Enregistrement de l'objet avec l'url : " + url);
			Naming.rebind(url, informationImpl);
			
			
		
			 
			System.out.println("Serveur lancé");
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * Constructeur du serveur : 
	 * Créer un objet serveur où l'on aura précisé l'adresse
	 * @param adresse : adresse du serveur
	 */
	public Serveur(String adresse) {
		// TODO Auto-generated method stub
		try {
			
			LocateRegistry.createRegistry(REGISTRY_PORT);
	
			
			ServeurRMI informationImpl = new ServeurRMI(this);
			serveur = informationImpl;
			String url = "rmi://" + adresse + "/fram";
			System.out.println("Enregistrement de l'objet avec l'url : " + url);
			Naming.rebind(url, informationImpl);
			
			
		
			 
			System.out.println("Serveur lancé");
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
	}
	

/**
 * Provoque l'arret du serveur
 */
	public void stop()
	{
		try {
			Naming.unbind("rmi://" + InetAddress.getLocalHost().getHostAddress() + "/fram");
			//UnicastRemoteObject.unexportObject((Remote) this, true);
			System.out.println("Arret du serveur");
			System.exit(0);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

public ServeurRMI getServeur() {
	return serveur;
}

public void setServeur(ServeurRMI serveur) {
	this.serveur = serveur;
}

public ArrayList<Client> getClientsconnectes() throws RemoteException{
	return getServeur().getClientsconnectes();
}

public boolean connexion(String login, String motDepasse, Client c) throws RemoteException{
	return getServeur().connexion(login, motDepasse, c);
}

public void ajouterPublication(Publication publication)throws RemoteException{
	getServeur().AddPublication(publication);
}

public void ajouterMessagePrive(String login, MessagePrive message) throws RemoteException{
	getServeur().ajouterMessagePrive(login, message);
}

public TreeMap<String, ArrayList<MessagePrive>> getMapMessagesPrivesUtilisateur(String login)throws RemoteException{
	return getServeur().getMapMessagesPrivesUtilisateur(login);
}

public ArrayList<MessagePrive> getListeMessagesPrives(String expeditaire, String destinataire) throws RemoteException{
	return getServeur().getListeMessagesPrives(expeditaire, destinataire);
}

public TreeMap<String,TreeMap<String, ArrayList<MessagePrive>>> getMessagesPrivesUtilisateurs() throws RemoteException{
	return getServeur().getMessagesPrivesUtilisateurs();
}

public TreeMap<String, ArrayList<Conversation>> getConversationsUtilisateursAbsents() throws RemoteException{
	return getServeur().getConversationsUtilisateursAbsents();
}

public TreeMap<Integer, Conversation> getConversations() throws RemoteException{
	return getServeur().getConversations();
}

public Conversation getConversation(int idConversation) throws RemoteException{
	return getServeur().getConversation(idConversation);
	
}

/**
 * 
 * @param login
 * @return
 */
public ArrayList<Conversation>  getConversationsUtilisateurAbsent(String login) throws RemoteException{
	return getServeur().getConversationsUtilisateurAbsent(login);
}
/**
 * 
 * @param login
 * @param idConversation
 * @return
 */
public Conversation getConversation(String login, int idConversation) throws RemoteException{
	return getServeur().getConversation(login, idConversation);
}


public void ajouterMessageConversation(Message message, int idConversation , String login) throws RemoteException{
	getServeur().ajouterMessageConversation(message, idConversation, login);
}

public void distribuerMessageConversation(MessageConversation message) throws RemoteException{
	getServeur().distribuerMessage(message);
}

public int creerUneConversation(ArrayList<Utilisateur> utilisateurs, ArrayList<Groupe> groupes) throws RemoteException{
	return getServeur().creerUneConversation(utilisateurs, groupes);
}

public void distribuerMessagePrive(MessagePrive message) throws RemoteException{
	getServeur().distribuerMessagePrive(message);
}

public void distribuerMessage(Message message) throws RemoteException{
	getServeur().distribuerMessage(message);
}

public ArrayList<Publication> getPublicationsVisibles(Utilisateur utilisateur) throws RemoteException{
	return getPublicationsVisibles(utilisateur);
}

public Utilisateur getUtilisateurInscrit(String login) throws RemoteException{
	return getServeur().getUtilisateurInscrit(login);
}

public ArrayList<Utilisateur> getUtilisateursInscrits() throws RemoteException{
	return getServeur().getUtilisateursInscrits();
}

/**
 * Renvoie la liste des publications d'un utilisateurs
 * @param utilisateur : Objet utilisateur 
 * @return ArrayList<Publication> : les publications de l'utilisateur utilisateur
 * @throws RemoteException
 */
public ArrayList<Publication> getPublications(Utilisateur utilisateur) throws RemoteException{
	return getServeur().getPublications(utilisateur);
}
/**
 * Supprime une publication 
 * @param utilisateur : l'utilisateur propriétaire de la publication
 * Ensure utilisateur.equals(publication().getProprietaire()) 
 * @throws RemoteException
 */
public void supprimerUnePublication(Utilisateur utilisateur, Publication publication)throws RemoteException{
	getServeur().supprimerUnePublication(utilisateur, publication);
}
/**
 * Rend visible une publication a un utilisateur
 * @param utilisateur : l'utilisateur à autoriser 
 * @param publicaion : la publication concernée
 * @throws RemoteException
 */
public void autoriserUnePublicationUtilisateur(Utilisateur utilisateur, Publication publication) throws RemoteException{
	getServeur().autoriserUnePublicationUtilisateur(utilisateur, publication);
}
/**
 * Retire la visibilite d'un utilisateur
 * @param utilisateur : l'utilisateur à retirer
 * @param publication : la publication concernée
 * @throws RemoteException
 */
public void retirerUnePublicationutilisateur(Utilisateur utilisateur, Publication publication) throws RemoteException{
	getServeur().retirerUnePublicationutilisateur(utilisateur, publication);
}
/**
 * Rend visible une publication a un groupe
 * @param groupe : le groupe à autorisier
 * @param publication : la publication concernée
 * @throws RemoteException
 */
public void autoriserUnePublicationGroupe(Groupe groupe, Publication publication) throws RemoteException{
	getServeur().autoriserUnePublicationGroupe(groupe, publication);
}
/**
 * Retire la visibilite d'un groupe
 * @param groupe : le groupe à retirer
 * @param publication : la publication concernée
 * @throws RemoteException
 */
public void retirerUnePublicationGroupe(Groupe groupe, Publication publication) throws RemoteException{
	getServeur().retirerUnePublicationGroupe(groupe, publication);
}
/**
 * Creer un groupe 
 * @param nomGroupe :  le nom du groupe à creer 
 * @param utilisateur : l'utilisateur propriétaire
 * @throws RemoteException
 */
public void creerUnGroupe(String nomGroupe, String utilisateur)throws RemoteException{
	getServeur().creerUnGroupe(nomGroupe, utilisateur);
}
/**
 * Ajoute un utilisateur dans un groupe
 * @param idGroupe : le groupe concerné
 * @param utilisateur : l'utilisateur à ajouter
 * @throws RemoteException
 */
public void ajouterUnUtlisateurDansGroupe(int idGroupe, Utilisateur utilisateur)throws RemoteException{
	getServeur().ajouterUnUtlisateurDansGroupe(idGroupe, utilisateur);
}
/**
 * Supprime un utilisateur du groupe
 * @param idGroupe : le groupe concerné
 * @param utilisateur : l'utilisateur à supprimer
 * @throws RemoteException
 */
public void supprimerUnUtilsateurDuGroupe(int idGroupe, Utilisateur utilisateur)throws RemoteException{
	getServeur().supprimerUnUtilsateurDuGroupe(idGroupe, utilisateur);
}
/**
 * Supprime un groupe
 * @param idGroupe : le groupe à supprimer
 * @throws RemoteException
 */
public void supprimerGroupe(int idGroupe)throws RemoteException{
	getServeur().supprimerGroupe(idGroupe);
}
/**
 * Retourne la liste des groupes
 * @return ArrayList<Groupe> liste des groupes 
 * @throws RemoteException
 */
public ArrayList<Groupe> getGroupes() throws RemoteException{
	return getServeur().getGroupes();
}
/**
 * Retourne la liste des groupes auxquelle l'utilisateur est propriétaire
 * @param utilisateur : l'utilisateur concerné 
 * @return ArrayList<Groupe> liste des groupes dont un utilisateur est propriétaire
 * @throws RemoteException
 */
public ArrayList<Groupe> getGroupesProp(Utilisateur utilisateur) throws RemoteException{
	return getServeur().getGroupesProp(utilisateur);
}
/**
 * Retourne la liste des groupes aux quelles un utilisateur appartients
 * @param utilisateur
 * @return ArrayList<Groupe> liste des groupes qui appartiennent à un utilisateur
 * @throws RemoteException
 */
public ArrayList<Groupe> getGroupes(Utilisateur utilisateur) throws RemoteException{
	return getServeur().getGroupes(utilisateur);
}
/**
 * Retour un Objet groupe pour un idgroupe
 * @param idGroupe : le groupe à chercher
 * @return Retour un Objet groupe pour un idgroupe
 * @throws RemoteException
 */
public Groupe getGroupe(int idGroupe) throws RemoteException{
	return getServeur().getGroupe(idGroupe);
}

public boolean inscription(String login, String motDePasse)
		throws NoSuchAlgorithmException, UnsupportedEncodingException,
		RemoteException{
	return getServeur().inscription(login, motDePasse);
}

public boolean inscriptionAvecRepertoireUtilisateur(String login,
		String motDePasse) throws RemoteException{
	return getServeur().inscriptionAvecRepertoireUtilisateur(login, motDePasse);
}

public boolean inscriptionSecurisee(String login, String motDePasse)
		throws RemoteException{
	return getServeur().inscriptionSecurisee(login, motDePasse);

}

	
	
}
