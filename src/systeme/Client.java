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
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

import javax.management.modelmbean.RequiredModelMBean;

import application.chat.Vue;

import jus.util.assertion.Ensure;
import jus.util.assertion.Require;
import modules.chat.Conversation;
import modules.chat.Message;
import modules.chat.MessageConversation;
import modules.chat.MessagePrive;
import modules.documents.Document;
import modules.documents.social.Publication;
import modules.gestionUtilisateur.Groupe;
import modules.gestionUtilisateur.Utilisateur;
import systeme.rmi.ClientRMI;
import systeme.rmi.InterfaceClientRmi;
import systeme.rmi.InterfaceServeurRmi;

/**
 * @author chaiebm
 * @param <K>
 * 
 */


public class Client  implements Serializable{
	static int REGISTRY_PORT = 1099;

	private Utilisateur utilisateur;
	private InterfaceServeurRmi serv;
	private InterfaceClientRmi cl;

	/**
	 * Créer un objet client RMI qui sera connecté à un serveur local
	 * @param login
	 * @param motDePasse
	 * @require : login présent dans la liste des inscrits et mot de passe correct
	 * @ensure : objet clientRMI crée
	 */
	public Client(String login, String motDePasse) {

		try {


			// obtention de l'objet distant à partir de son nom (lookup)
			 Registry registry = LocateRegistry.getRegistry(REGISTRY_PORT);
			 Remote r = registry.lookup("fram");
			if (r instanceof InterfaceServeurRmi) {
				this.serv = (InterfaceServeurRmi)r;
				if (getServeurRmiImpl().connexion(login, motDePasse,this)) {		
					
					this.utilisateur = getServeurRmiImpl().getUtilisateurInscrit(login);
					String url = "rmi://" + InetAddress.getLocalHost().getHostAddress() + "/" + this.utilisateur.getLogin();
					System.out.println("Enregistrement de l'objet client avec l'url : " + url);
					cl = new ClientRMI(this);
					try {
						Naming.rebind(url, cl);
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					

					
					// si l'utilisateur n 'est pas présent dans la liste des connectés
					if(getServeurRmiImpl().utilisateurConnecte(this) == null){
						((InterfaceServeurRmi) r).ajouterClient(this);
					}
					else{
						System.out.println("déjà connecté");
						System.out.println(this.getUtilisateur().getLogin());
					}
					System.out.println(utilisateur.getLogin()+ " se connecte");
				} else {
					//throw new ErreurConnexion("Login mot de passe invalide");
					throw new Ensure("Login mot de passe invalide");
					
				}
			}
			
			


		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	/**
	 * Créer un objet client RMI qui sera connecté au serveur RMI distant (à l'adresse donnée)
	 * @param login
	 * @param motDePasse
	 * @param adresse
	 * @require : adresse correcte, login présent dans la liste des inscrits sur le serveur et mot de passe correct 
	 * @ensure : objet clientRMI crée
	 */
	public Client(String login, String motDePasse,String adresse) {

		try {


			// obtention de l'objet distant à partir de son nom (lookup)
			 Registry registry = LocateRegistry.getRegistry(REGISTRY_PORT);
			 Remote r = registry.lookup("fram");
			if (r instanceof InterfaceServeurRmi) {
				this.serv = (InterfaceServeurRmi)r;
				if (getServeurRmiImpl().connexion(login, motDePasse,this)) {		
					
					this.utilisateur = getServeurRmiImpl().getUtilisateurInscrit(login);
					String url = "rmi://" + adresse + "/" + this.utilisateur.getLogin();
					System.out.println("Enregistrement de l'objet client avec l'url : " + url);
					cl = new ClientRMI(this);
					try {
						Naming.rebind(url, cl);
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					

					
					// si l'utilisateur n 'est pas présent dans la liste des connectés
					if(getServeurRmiImpl().utilisateurConnecte(this) == null){
						((InterfaceServeurRmi) r).ajouterClient(this);
					}
					else{
						System.out.println("déjà connecté");
						System.out.println(this.getUtilisateur().getLogin());
					}
					System.out.println(utilisateur.getLogin()+ " se connecte");
				} else {
					//throw new ErreurConnexion("Login mot de passe invalide");
					throw new Ensure("Login mot de passe invalide");
					
				}
			}
			
			


		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	final public static int BUF_SIZE = 1024 * 64;

	/**
	 * Methode de copie de flux par byte
	 * @param inStream  : le flux d'entr�e
	 * @param outStream : le flux de sortie
	 * @throws IOException
	 */

	public static void copie(InputStream inStream, OutputStream outStream)
			throws IOException {

		byte[] b = new byte[BUF_SIZE];
		int len;
		// Parcour du flux d'entree et copie dans le flux sortie
		while ((len = inStream.read(b)) >= 0) {
			// outStream.write(b, 0, len);
			outStream.write(b);
		}
		inStream.close();
		outStream.close();
	}

	/**
	 * Methode qui permet de telecharger un fichier depuis le serveur 
	 * @param server : le serveurRMI
	 * @param source : le fichier a telecharger
	 * @param destination : le fichier de sortie
	 * @throws IOException
	 */

	public void telecharger(File source,
			File destination) throws IOException {
		copie(new FileInputStream(source), new FileOutputStream(destination));
		
	}

	/**
	 * Methode qui permet de charger un fichier sur le serveur
	 * @param server : le serveurRMI
	 * @param source : le fichier a charger 2 * @param destination : le fichier de sortie
	 * @throws IOException
	 */

	public void charger(File source,ArrayList<Utilisateur> utilisateurs, ArrayList<Groupe> groupes, Document document, Date dateFinPublication)
			throws IOException {
		//"/git/miage_projet_framework/docServeur/"+this.getUtilisateur().getLogin()+
		//String dest ="/git/miage_projet_framework/docServeur/"+this.getUtilisateur().getLogin()+"/"+source.getName();
		String dest = System.getProperty("user.dir")+"/docServeur/"+this.getUtilisateur().getLogin()+"/"+source.getName();
		//System.out.println(dest);
		File destination = new File(dest);
		document.setEmplacement(dest);
		
		//if (r instanceof InterfaceServeurRmi) {
			
			copie(new FileInputStream(source),  new FileOutputStream(destination));
			//this.getUtilisateur().publierUnDocument(utilisateurs, groupes, document, dateFinPublication);
			getServeurRmiImpl().ajouterPublication(new Publication(new Date(), dateFinPublication, utilisateurs, groupes, this.getUtilisateur(), document));
		//}
	}


/**
 * tous les utilisateur inscrit sur le serveur
 * @return ArrayList<Utilisateur> : retourne liste de tous les utilisateur inscrit sur le serveur
 */
	public ArrayList<Utilisateur> getUtilisateurs()
	{
		ArrayList<Utilisateur> clients = null;
		
		try {
			clients = getServeurRmiImpl().getUtilisateursInscrits();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return clients;
	}
	
	/**
	 *  liste des clients connectées
	 * @return ArrayList<ClientRMI> :  liste des clients connectées
	 */
	
	public ArrayList<Client> getUtilisateursConnectes()
	{
		ArrayList<Client> clients = null;
		
		
			try {
				clients = getServeurRmiImpl().getClientsconnectes();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return clients;
	}
	/***
	 * retrourne list des utilisateurs deconnectées
	 * @return ArrayList<Utilisateur> utilisateurs deconnectées
	 */
	public ArrayList<Utilisateur> getUtilisateursDeconnectes()
	{
		ArrayList<Utilisateur> utilisateurDeconnectees = new ArrayList<Utilisateur>();
		
		for(Utilisateur user: getUtilisateurs()){
			for(Client userIn : getUtilisateursConnectes()){
				if(!(user.getLogin().equals(userIn.getUtilisateur().getLogin())))
				{
					utilisateurDeconnectees.add(user);
				}
			}
		}
		
		return utilisateurDeconnectees;
	}
	
	/**
	 * @return Utilisateur : l'utilisateur associer à ce client 
	 */
	
	public Utilisateur getUtilisateur(){
		return this.utilisateur;
	}
	
	/**
	 * Deconnecte le client
	 */
	
	public void deconnexion()
	{
		
			try {
				// Suppression de la liste des connectés sur le serveur
				getServeurRmiImpl().deconnexion(this);		
				try {
					// Suppression de l'objet client distant sur le registry
					LocateRegistry.getRegistry(REGISTRY_PORT).unbind(getUtilisateur().getLogin());
				} catch (NotBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	
	
	/**
	 * retourne l'ensemble des publications visibles pour un utilisateur
	 * @return ArrayList<Publication> : return publication visibles
	 */
	public ArrayList<Publication> getPublicationsVisibles()
	{
		ArrayList<Publication> publicationsVisibles = new ArrayList<Publication>();
		
			try {
				publicationsVisibles = getServeurRmiImpl().getPublicationsVisibles(this.getUtilisateur());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		
		
		return publicationsVisibles;
	}
	/**
	 * Renvoie la liste des publications d'un utilisateurs
	 * @return ArrayList<Publication> : les publications de l'utilisateur
	 */
	public ArrayList<Publication> getPublications()
	{
		ArrayList<Publication> publications = null;
		try {
			publications = getServeurRmiImpl().getPublications(this.getUtilisateur());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return publications;
	}
	/**
	 * supprime une publication
	 * @param publication : La publication a supprimer
	 */
	public void supprimerUnePublication(Publication publication)
	{
		if(!estProprietaire(publication))
		{
			throw new Require("Action impossible car "+ this.getUtilisateur().getLogin() +" n'est pas propriétaire de cette publication.");
		}
		try {
			getServeurRmiImpl().supprimerUnePublication(this.getUtilisateur(), publication);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Rend visible une publication a un utilisateur
	 * @param utilisateur : l'utilisateur à autoriser 
	 * @param publicaion : la publication concernée
	 * Require seul le prorietaire de la publication est autorise a l'utiliser
	 */
	public void autoriserPublicationUtilisateur(Publication publication, Utilisateur utilisateur)
	{
		
		if(!estProprietaire(publication))
		{
			throw new Require("Action impossible car "+ this.getUtilisateur().getLogin() +" n'est pas propriétaire de cette publication.");
		}
		try {
			getServeurRmiImpl().autoriserUnePublicationUtilisateur(utilisateur, publication);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Retire la visibilite d'une personnes
	 * @param utilisateur : l'utilisateur à retirer
	 * @param publication : la publication concernée
	 * Require seul le prorietaire de la publication est autorise a l'utiliser
	 */
	public void retirerUnePublicationutilisateur(Publication publication, Utilisateur utilisateur)
	{
		if(!estProprietaire(publication))
		{
			throw new Require("Action impossible car "+ this.getUtilisateur().getLogin() +" n'est pas propriétaire de cette publication.");
		}
		try {
			getServeurRmiImpl().retirerUnePublicationutilisateur(utilisateur, publication);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * retire la visibilité d'un document
	 * @param publication : la publication concernée
	 */
	public void seRetirerUnePublicationutilisateur(Publication publication)
	{
		try {
			getServeurRmiImpl().retirerUnePublicationutilisateur(this.utilisateur, publication);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Rend visible une publication a un groupe
	 * @param groupe : le groupe à autorisier
	 * @param publication : la publication concernée
	 */
	public void autoriserUnePublicationGroupe(Publication publication, Groupe groupe)
	{
		if(!estProprietaire(publication))
		{
			throw new Require("Action impossible car "+ this.getUtilisateur().getLogin() +" n'est pas propriétaire de cette publication.");
		}
		try {
			getServeurRmiImpl().autoriserUnePublicationGroupe(groupe, publication);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Retire la visibilite d'un groupe
	 * @param groupe : le groupe à retirer
	 * @param publication : la publication concernée
	 */
	public void retirerUnePublicationGroupe(Publication publication, Groupe groupe)
	{
		if(!estProprietaire(publication))
		{
			throw new Require("Action impossible car "+ this.getUtilisateur().getLogin() +" n'est pas propriétaire de cette publication.");
		}
		try {
			getServeurRmiImpl().retirerUnePublicationGroupe(groupe, publication);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Creer un groupe 
	 * @param nomGroupe :  le nom du groupe à creer 
	 */
	public void creerUnGroupe(String nomGroupe)
	{
		try {
			getServeurRmiImpl().creerUnGroupe(nomGroupe, this.getUtilisateur().getLogin());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Ajoute un utilisateur dans un groupe
	 * @param idGroupe : le groupe concerné
	 * @param utilisateur : l'utilisateur à ajouter
	 * @throws RemoteException
	 */
	public void ajouterUnUtlisateurDansGroupe(int idGroupe, Utilisateur utilisateur)
	{
		if (!(this.estProprietaire(idGroupe)))
		{
			throw new Require("Action impossible car "+ this.getUtilisateur().getLogin() +" n'est pas propriétaire de ce groupe.");
		}
		try {
			getServeurRmiImpl().ajouterUnUtlisateurDansGroupe(idGroupe, utilisateur);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Supprime un utilisateur du groupe
	 * @param idGroupe : le groupe concerné
	 * @param utilisateur : l'utilisateur à supprimer
	 * @throws RemoteException
	 */
	public void supprimerUnUtilsateurDuGroupe(int idGroupe, Utilisateur utilisateur)
	{
		if (!(this.estProprietaire(idGroupe)))
		{
			throw new Require("Action impossible car "+ this.getUtilisateur().getLogin() +" n'est pas propriétaire de ce groupe.");
		}
		try {
			getServeurRmiImpl().supprimerUnUtilsateurDuGroupe(idGroupe, utilisateur);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Supprime un groupe
	 * @param idGroupe : le groupe à supprimer
	 * @throws RemoteException
	 */
	public void supprimerGroupe(int idGroupe)
	{
		if (!(this.estProprietaire(idGroupe)))
		{
			throw new Require("Action impossible car "+ this.getUtilisateur().getLogin() +" n'est pas propriétaire de ce groupe.");
		}
		try {
			getServeurRmiImpl().supprimerGroupe(idGroupe);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Retourne la liste des groupes auxquelle l'utilisateur est propriétaire
	 * @return ArrayList<Groupe> liste des groupes dont un utilisateur est propriétaire
	 */
	public ArrayList<Groupe> getGroupesProp() 
	{
		ArrayList<Groupe> groupes = null;
		try {
			groupes = getServeurRmiImpl().getGroupesProp(this.getUtilisateur());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return groupes;
	}
	
	/**
	 * Retourne la liste des groupes aux quelles un utilisateur appartients
	 * @return ArrayList<Groupe> liste des groupes qui appartiennent à un utilisateur
	 */
	public ArrayList<Groupe> getGroupes()
	{
		ArrayList<Groupe> groupes = null;
		try {
			groupes = getServeurRmiImpl().getGroupes(this.getUtilisateur());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return groupes;
	}
	
	public boolean estProprietaire(int idGroupe)
	{
		Groupe groupe = null;
		
		try {
			groupe = getServeurRmiImpl().getGroupe(idGroupe);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return groupe.getProprietaire().getLogin().equals(this.getUtilisateur().getLogin());
	}
	
	/**
	 * true si l'objet courant est proprietaire d'une publication
	 * @param publication
	 * @return true si l'objet courant est proprietaire d'une publication
	 */
	public boolean estProprietaire(Publication publication)
	{
		return this.getUtilisateur().getLogin().equals(publication.getProprietaire().getLogin());
	}
	
	/**
	 * Envoie un message 
	 * @param message
	 */
	public void envoyerMessagePrive(MessagePrive message){

			try {
				getClientRmiImpl().envoyerMessagePrive(message);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	/**
	 * Envoie un message qui concerne une conversation
	 * @param message
	 */
	public void envoyerMessageConversation(MessageConversation message){
		try {
			getClientRmiImpl().envoyerMessageConversation(message);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * Envoie un message 
	 * @param message
	 */
	public void envoyerMessage(Message message){

			try {
				getClientRmiImpl().envoyerMessage(message);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	}
	
	/**
	 * Reception d'un message
	 * @param message
	 * @param expediteur
	 */
	
	public void recevoirMessage(Message message){
		try {
			getClientRmiImpl().recevoirMessage(message);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Reçois les messages privés reçus durant l'absence
	 */
	public void recevoirMessagePriveAbsence(){
		// on test si l'utilisateur a reçu des messages connectés pendant son absence, si c'est le cas
		// on les affiche
		try {
			TreeMap<String, ArrayList<MessagePrive>> mapUtilisateur = getServeurRmiImpl().getMapMessagesPrivesUtilisateur(getUtilisateur().getLogin());
			
			Set cles = mapUtilisateur.keySet();
			Iterator it = cles.iterator();
			
			while(it.hasNext()){
				ArrayList<MessagePrive> messages = mapUtilisateur.get(it.next());
				for(Message m : messages){
					recevoirMessage(m);
				}
			}
			// je supprime la clé pour cet utilisateur
			getServeurRmiImpl().getMessagesPrivesUtilisateurs().remove(this.getUtilisateur().getLogin());
			

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Reçois les messages des conversations reçus durant son absence
	 */
	public void recevoirMessageConversationAbsence(){
		try {
			// on affiche les messages reçus
			ArrayList<Conversation> conversations= getServeurRmiImpl().getConversationsUtilisateurAbsent(this.getUtilisateur().getLogin());
			for(Conversation c : conversations){
				for(Message m : c.getListeMessages()){
					recevoirMessage(m);
				}
			}
			// on les supprime ensuite....
			getServeurRmiImpl().getConversationsUtilisateurAbsent(this.getUtilisateur().getLogin()).clear();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Créer une conversation
	 * @param utilisateurs
	 * @param groupes
	 * @return l'id de la conversation si crée, -1 sinon
	 */
	public int creerUneConversation(ArrayList<Utilisateur> utilisateurs, ArrayList<Groupe> groupes){
		int id = -1;
		try {
			id = getServeurRmiImpl().creerUneConversation(utilisateurs, groupes);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return id;
		
	}

	
	
	/**
	 * Renvoie l'objet distant du client
	 * @return cl
	 */
	public InterfaceClientRmi getClientRmiImpl(){
		return cl;
	}
	
	/**
	 * Renvoie l'objet distant du serveur 
	 * @return serv
	 */
	public InterfaceServeurRmi getServeurRmiImpl(){
		return serv;
	}
	
	public Vue getVue() throws RemoteException{
		return getClientRmiImpl().getVue();
	}
	
	public void setVue(Vue vue){
		try {
			getClientRmiImpl().setVue(vue);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void actualiserListes() throws RemoteException{
		getClientRmiImpl().actualiserListes();

	}
	
	
	
	
}
