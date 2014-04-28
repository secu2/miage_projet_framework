package systeme;

import java.io.File;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.NoSuchAlgorithmException;
import java.security.acl.Group;
import java.util.ArrayList;
import java.util.TreeMap;

import systeme.rmi.ClientRMI;
import systeme.rmi.InterfaceClientRmi;
import systeme.rmi.InterfaceServeurRmi;
import systeme.rmi.ServeurRMI;
import systeme.tools.Encryptage;
import modules.chat.Conversation;
import modules.chat.Message;
import modules.chat.MessagePrive;
import modules.documents.social.Publication;
import modules.gestionUtilisateur.Groupe;
import modules.gestionUtilisateur.Utilisateur;

public class Serveur implements Serializable {

	private ArrayList<Utilisateur> utilisateursInscrits;
	private ArrayList<ClientRMI> utilisateursConnectes;
	private ServeurRMI serveur;
	private ArrayList<Publication> publications;
	static int REGISTRY_PORT = 1099;
	

	/**
	 * Conversation que l'utilisateur a reçu alors qu'il était deconnecté
	 * La clé est le login de l'utilisateur, qui donne ainsi accès à sa liste de conversations 
	 */
	private TreeMap<String,ArrayList<Conversation>> conversationsUtilisateurs;
	
	public Serveur() {
		utilisateursInscrits = new ArrayList<Utilisateur>();
		utilisateursConnectes = new ArrayList<ClientRMI>();
		publications = new ArrayList<Publication>();
		conversationsUtilisateurs = new TreeMap<String, ArrayList<Conversation>>();
		serveur = new ServeurRMI(this);
	}
	

	/**
	 * Renvoie la liste des utilisateurs inscrits
	 * 
	 * @return utilisateursInscrits
	 */
	public ArrayList<Utilisateur> getUtilisateursInscrits() {
		return utilisateursInscrits;
	}

	/**
	 * Renvoie la liste des clients connectés
	 * 
	 * @return utilisateursConnectes
	 */
	public ArrayList<ClientRMI> getUtilisateursConnectes() {
		return utilisateursConnectes;
	}

	/**
	 * Permet de tester si l'utilisateur de login 'login' existe
	 * 
	 * @param login
	 *            : login de l'utilisateur
	 * @return true si l'utilisateur existe , false sinon
	 */
	public boolean utilisateurExistant(String login) {
		boolean existant = false;
		for (Utilisateur u : getUtilisateursInscrits()) {
			if (u.getLogin().equals(login)) {
				existant = true;
			}
		}
		return existant;
	}

	/**
	 * Ajoute un client à la liste des clients du serveur
	 * 
	 * @param c
	 *            : client
	 */
	public void ajouterClient(ClientRMI c) {
		getUtilisateursConnectes().add(c);
	}

	/**
	 * Ajoute un utilisatuer à la liste des utilisateurs inscrits sur le serveur
	 * 
	 * @param u
	 *            : utilisateur
	 */
	public void ajouterUtilisateur(Utilisateur u) {
		getUtilisateursInscrits().add(u);
	}

	/**
	 * Renvoie l'utilisateur de login 'login'
	 * 
	 * @param login
	 *            : login de l'utilisateur
	 * @return utilisateur si existant , null sinon
	 */
	public Utilisateur getUtilisateurInscrit(String login) {
		Utilisateur utilisateur = null;
		if (utilisateurExistant(login)) {
			for (Utilisateur user : getUtilisateursInscrits()) {
				if (user.getLogin().equals(login)) {
					utilisateur = user;
				}
			}
		}

		return utilisateur;
	}

	/**
	 * Verification de l'existance de l'utilisateur et inscription d'un
	 * utilisateur dont le mot de passe a déjà été encrypté côté client si
	 * l'utilisateur n'existe pas
	 * 
	 * @param login
	 * @param motDePasse
	 * @return false si le login de l'utilisateur est déjà existant, true sinon
	 */
	public boolean inscriptionSecurisee(String login, String motDePasse) {

		boolean existant = false;
		if (!utilisateurExistant(login)) {
			Utilisateur u = new Utilisateur(login);
			u.setMotDePasseSecurise(motDePasse);
			ajouterUtilisateur(u);
		} else {
			existant = true;
		}

		return existant;

	}

	/**
	 * Verification de l'existance de l'utilisateur et inscription d'un
	 * utilisateur dont le mot de passe a déjà été encrypté côté client si
	 * l'utilisateur n'existe pas
	 * 
	 * @param login
	 * @param motDePasse
	 * @return false si le login de l'utilisateur est déjà existant, true sinon
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public boolean inscription(String login, String motDePasse)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {

		boolean existant = false;
		if (!utilisateurExistant(login)) {
			Utilisateur u = new Utilisateur(login, motDePasse);
			ajouterUtilisateur(u);
		} else {
			existant = true;
		}

		return existant;

	}

	/**
	 * Verification de l'existance de l'utilisateur et inscription d'un
	 * utilisateur dont le mot de passe a déjà été encrypté côté client si
	 * l'utilisateur n'existe pas Créer un repertoire pour l'utilisateur sur le
	 * serveur
	 * 
	 * @param login
	 * @param motDePasse
	 * @return false si le login de l'utilisateur est déjà existant, true sinon
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public boolean inscriptionAvecRepertoireUtilisateur(String login,
			String motDePasse) {
		boolean existant = false;
		try {
			existant = inscription(login, motDePasse);
			creerUnRepertoireUtilisateur(getUtilisateurInscrit(login),
					"/git/miage_projet_framework/docServeur/");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return existant;

	}

	/**
	 * Realise une connexion entre un client et un serveur
	 * 
	 * @param utilisateur
	 *            : l'utilisateur � connecter
	 * @return true (Si connection �tablit)
	 */
	public boolean connexion(String login, String motDepasse, ClientRMI c) {

		boolean result = false;

		Utilisateur util;
		try {
			if (utilisateurExistant(login)) {
				// on récupère l'objet utilisateur
				util = getUtilisateurInscrit(login);
				// Test si le mdp est correct
				if (util.autoriserConnexion(motDepasse)) {
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
	public ClientRMI getClientConnecte(String login) {
		ClientRMI cl = null;
		for (ClientRMI c : getUtilisateursConnectes()) {
			if(c.getUtilisateur().equals(getUtilisateurInscrit(login))){
				cl = c;
			}
		}

		return cl;
	}

	/**
	 * Affecte le serveur rmi au serveur
	 * 
	 * @param serv
	 */
	public void setServeurRMI(ServeurRMI serv) {
		this.serveur = serv;
	}

	/**
	 * Renvoie le serveur RMI
	 * 
	 * @return serveur
	 */
	public ServeurRMI getServeurRMI() {
		return serveur;
	}

	/**
	 * Supprime le clientRMI de la liste des clients connectés
	 * 
	 * @param client
	 */
	public void supprimerUnClient(ClientRMI client) {
		getUtilisateursConnectes().remove(indexClient(client));
	}

	/**
	 * Renvoie l'index du client dans la liste des clients connectés
	 * 
	 * @param client
	 * @return si trouvé num = index du client connecté , -1 sinon
	 */
	public int indexClient(ClientRMI client) {
		int num = -1;
		int compteur = 0;
		for (ClientRMI cl : getUtilisateursConnectes()) {
			if (cl.getUtilisateur().equals(client.getUtilisateur())) {
				num = compteur;
			}
			compteur++;
		}

		return num;
	}

	/**
	 * Créer un repertoire pour l'utilisateur
	 * 
	 * @param u
	 *            : utilisateur
	 * @param path
	 *            : chemin avant le nom d'utilisateur (Ex : /utilisateurs/ )
	 */
	public void creerUnRepertoireUtilisateur(Utilisateur u, String path) {
		File repertoire = new File(path + "" + u.getLogin());
		u.ajouterRepertoire(repertoire);
		repertoire.mkdirs();
	}

	/**
	 * Créer un repertoire
	 * 
	 * @param path
	 *            : chemin du futur repertoire
	 */
	public void creerUnRepertoire(String path) {
		File repertoire = new File(path);
		repertoire.mkdirs();
	}

	/**
	 * Distribue un message envoyé par un client aux autres clients
	 * 
	 * @param message
	 */
	public void distribuerMessage(Message message) {
		Registry registry;
		try {
			registry = LocateRegistry.getRegistry(REGISTRY_PORT);
			for (ClientRMI c : getUtilisateursConnectes()) {
				// String url = "rmi://" +
				// InetAddress.getLocalHost().getHostAddress() + "/" +
				// c.getUtilisateur().getLogin();
				try {
					ClientRMI expediteur = getClientConnecte(message.getExpeditaire());
					Remote rem = registry.lookup(c.getUtilisateur().getLogin());
					((InterfaceClientRmi) rem).recevoirMessage(message);
					// c.recevoirMessage(message);

				} catch (AccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NotBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	/**
	 * Distribue le message privé de l'expediteur au destinataire
	 * @param message
	 * @param expediteur
	 * @param destinataire
	 * @require : destinataire inscrit sur le serveur
	 * @require : expediteur inscrit et connecté sur le serveur
	 */
	public void distribuerMessagePrive(MessagePrive message){
		// si le destinataire est connecté
		if (getClientConnecte(message.getDestinataire()) != null) {
			Registry registry;
			try {
				registry = LocateRegistry.getRegistry(REGISTRY_PORT);
				Remote rem;
				try {
					// on récupère le client du destinataire sur le serveur
					ClientRMI destinataire = getClientConnecte(message.getDestinataire());
					rem = registry.lookup(destinataire.getUtilisateur().getLogin());
					
				
					// On l'affiche du côté du destinataire
					((InterfaceClientRmi) rem).recevoirMessage(message);
					// on l'affiche également du côté de l'expediteur
					
					// on récupère le client du expediteur sur le serveur
					ClientRMI expediteur = getClientConnecte(message.getExpeditaire());
					// on récupère l'objet distant du client expediteur du message
					Remote remo = registry.lookup(expediteur.getUtilisateur().getLogin());
					((InterfaceClientRmi) remo).recevoirMessage(message);

				} catch (AccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NotBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}		

		} else {
			System.out.println("Le destinataire n'est pas connecté, le message sera remis à sa prochaine connexion");
			Registry registry;
			try {
				registry = LocateRegistry.getRegistry(REGISTRY_PORT);
				Remote r = registry.lookup("fram");
				((InterfaceServeurRmi) r).ajouterMessagePrive(message.getDestinataire(),message);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		//	ajouterMessagePrive(loginDestinataire,new MessagePrive(message, loginExpediteur, loginDestinataire));
			
		}

	}

	/**
	 * Renvoie l'utilisateur du clientRMI si trouvé dans la liste des clients connectés
	 * @param cl
	 * @return l'utilisateur si il est connecté , null sinon
	 */
	public Utilisateur utilisateurConnecte(ClientRMI cl) {
		Utilisateur u = null;
		if (getUtilisateursConnectes() != null) {
			int num = indexClient(cl);
			if (num != -1) {
				u = getUtilisateursConnectes().get(indexClient(cl))
						.getUtilisateur();
			}
		}
		return u;
	}
	
	
	/**
	 * Renvoie l'ensemble de publications
	 * @return publications
	 */
	public ArrayList<Publication> getPublications() {
		return publications;
	}

	/**
	 * Affecte un ensemble de publications à l'ensemble de publications du serveur
	 * @param publications
	 */
	public void setPublications(ArrayList<Publication> publications) {
		this.publications = publications;
	}

	/**
	 * Ajoute une publication à la liste des publications contenues sur le serveur
	 * @param publication
	 */
	public void AddPublication(Publication publication) {
		getPublications().add(publication);
	}

	/**
	 * Affiche l'ensemble des publications visibles pour un utilisateur
	 * 
	 * @param utilisateur
	 *            : l'utilisateur a verifier
	 * @return ArrayList<Publication> : return publication visibles
	 */
	public ArrayList<Publication> getPublicationsVisibles(
			Utilisateur utilisateur) {

		ArrayList<Publication> publicationsVisibles = new ArrayList<Publication>();
		ArrayList<Publication> publications = getPublications();

		try {
			for (int i = 0; i < publications.size(); i++) {

				ArrayList<Utilisateur> utilisateurs = publications.get(i)
						.getVisibiliteUtilisateur();
				ArrayList<Groupe> groupes = publications.get(i)
						.getVisibiliteGroupe();
				if (utilisateurs == null) {
					throw new NullPointerException();
				}
				for (Utilisateur util : utilisateurs) {
					if (util.equals(utilisateur)) {
						publicationsVisibles.add(publications.get(i));
					}
				}
				/*
				 * for(Groupe groupe : groupes ) { ArrayList<Groupe>
				 * groupesUtilisateurs = utilisateur.getGroupes(); for(Groupe
				 * groupeUtilisateur : groupesUtilisateurs) {
				 * if(groupeUtilisateur.equals(groupe)); {
				 * publicationsVisibles.add(publications.get(i)); } } }
				 */

			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		return publicationsVisibles;
	}
	
	

	
}

