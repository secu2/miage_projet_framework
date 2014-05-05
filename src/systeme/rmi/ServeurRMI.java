package systeme.rmi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

import jus.util.assertion.Require;

import systeme.Client;
import systeme.Serveur;
import systeme.rmi.ServeurRMI;
import systeme.tools.Encryptage;
import modules.chat.Conversation;
import modules.chat.Message;
import modules.chat.MessageConversation;
import modules.chat.MessagePrive;
import modules.documents.social.Publication;
import modules.gestionUtilisateur.Groupe;
import modules.gestionUtilisateur.Utilisateur;

//classe héritante de l'interface et définir les méthodes propres au serveur.
public class ServeurRMI extends UnicastRemoteObject implements
		InterfaceServeurRmi {

	private static final long serialVersionUID = 2674880711467464646L;

	private Serveur serveur;

	private ArrayList<Utilisateur> utilisateursInscrits;
	private ArrayList<Client> utilisateursConnectes;
	private ArrayList<Publication> publications;
	static int REGISTRY_PORT = 1099;

	/**
	 * Messages privés reçus lorsque le client n'était pas connecté La clé de la
	 * TreeMap est le login de l'utilisateur qui donnent accès à une autre
	 * treemap qui elle donne accès à l'ensemble des messages envoyés identifié
	 * par une clé qui est le login de l'expeditaire
	 */
	private TreeMap<String, TreeMap<String, ArrayList<MessagePrive>>> messagesPrivesUtilisateurs;
	/**
	 * Conversation que l'utilisateur a reçu alors qu'il était deconnecté La clé
	 * est le login de l'utilisateur, qui donne ainsi accès à sa liste de
	 * conversations
	 */
	private TreeMap<String, ArrayList<Conversation>> conversationsUtilisateursAbsent;
	/**
	 * Conversations en cours (?)
	 */
	private TreeMap<Integer, Conversation> conversations;


	public ServeurRMI(Serveur serveur) throws RemoteException {
		super();

		this.messagesPrivesUtilisateurs = new TreeMap<String, TreeMap<String, ArrayList<MessagePrive>>>();
		this.conversationsUtilisateursAbsent = new TreeMap<String, ArrayList<Conversation>>();
		this.conversations = new TreeMap<Integer, Conversation>();

		utilisateursInscrits = new ArrayList<Utilisateur>();
		utilisateursConnectes = new ArrayList<Client>();
		publications = new ArrayList<Publication>();
		this.serveur = serveur;
		
		
	}

	public String getTest() throws RemoteException {
		System.out.println("Invocation de la méthode getInformation()");
		return "JoJo t'es moche ";
	}



	/**
	 * Renvoie la liste des clients connectés sur le serveur
	 * 
	 * @return utilisateursConnectes
	 */
	public ArrayList<Client> getClientsconnectes() throws RemoteException {
		return getUtilisateursConnectes();
	}

	/**
	 * Deconnecte un client
	 * @param c: clientRMI
	 */
	public void deconnexion(Client c) throws RemoteException {
		System.out.println(c.toString());
		supprimerUnClient(c);
	}

	/**
	 * Envoi un message aux autres utilisateurs
	 * @param message
	 */
	public void envoiMessage(Message message) throws RemoteException {
		distribuerMessage(message);
	}

	/**
	 * Ajoute une publication à la liste des publications du serveur
	 * @param publication
	 */
	public void ajouterPublication(Publication publication)
			throws RemoteException {
		AddPublication(publication);
	}

	/**
	 * Renvoie la treemap des messages privés des utilisateurs
	 * @return messagesPrivesUtilisateurs
	 */
	public TreeMap<String, TreeMap<String, ArrayList<MessagePrive>>> getMessagesPrivesUtilisateurs()
			throws RemoteException {
		return messagesPrivesUtilisateurs;
	}

	/**
	 * Renvoie la treemap des conversations des utilisateurs qui ont eu lieu
	 * lors de leur absence 
	 * @return conversationsUtilisateursAbsent
	 */
	public TreeMap<String, ArrayList<Conversation>> getConversationsUtilisateursAbsents()
			throws RemoteException {
		return this.conversationsUtilisateursAbsent;
	}

	/**
	 * Renvoie la liste des conversations d'un utilisateur
	 * @param login
	 * @return liste des conversations de l'utilisateur si elle existe, null sinon
	 */
	public ArrayList<Conversation> getConversationsUtilisateurAbsent(
			String login) throws RemoteException {

		return getConversationsUtilisateursAbsents().get(login);
	}

	/**
	 * Renvoie la conversation où l'utilisateur était absent
	 * @param login
	 * @param idConversation
	 * @return la conversation si il existe , null sinon
	 */
	public Conversation getConversation(String login, int idConversation)
			throws RemoteException {
		Conversation conversation = null;

		if (getConversationsUtilisateurAbsent(login) != null) {
			for (Conversation c : getConversationsUtilisateurAbsent(login)) {
				if (c.getIdConversation() == idConversation) {
					conversation = c;
				}
			}
		}

		return conversation;
	}

	/**
	 * Renvoie la treemap de conversations
	 * 
	 * @return conversations
	 */
	public TreeMap<Integer, Conversation> getConversations()
			throws RemoteException {
		return this.conversations;
	}

	/**
	 * Retourne la conversation
	 * 
	 * @param idConversation
	 * @return la conversation si il existe , null sinon
	 */
	public Conversation getConversation(int idConversation)
			throws RemoteException {
		return getConversations().get(new Integer(idConversation));
	}

	/**
	 * Renvoie la liste des messages privés entre l'expeditaire et le
	 * destinataire
	 * 
	 * @param expeditaire
	 * @param destinataire
	 * @return la liste des messages privés entre l'expeditaire et le
	 *         destinataire si elle existe, null sinon
	 */
	public ArrayList<MessagePrive> getListeMessagesPrives(String expeditaire,
			String destinataire) throws RemoteException {
		return getMessagesPrivesUtilisateurs().get(destinataire).get(
				expeditaire);
	}

	/**
	 * Renvoie le treemap des messages privés de l'utilisateur
	 * 
	 * @param login
	 * @return la treemap des messages privés de l'utilisateur si elle existe,
	 *         null sinon
	 */
	public TreeMap<String, ArrayList<MessagePrive>> getMapMessagesPrivesUtilisateur(
			String login) throws RemoteException {
		return getMessagesPrivesUtilisateurs().get(login);
	}

	/**
	 * Ajoute un message privé à la liste des messages privés de l'utilisateur
	 * 
	 * @param login
	 * @param message
	 */
	public void ajouterMessagePrive(String login, MessagePrive message)
			throws RemoteException {
		// la clé existe,, on va donc récupérer la map pour ce destinataire
		try {
			if (getMessagesPrivesUtilisateurs().containsKey(login)) {

				// il existe déjà une liste de messages pour cet expeditaire
				if (getListeMessagesPrives(message.getExpeditaire(), login) != null) {
					// on la récupère donc
					ArrayList<MessagePrive> messagesExpeditaire = getListeMessagesPrives(
							message.getExpeditaire(), login);
					// on l'ajoute donc aux autres
					messagesExpeditaire.add(message);
				}
				// elle n'existe pas : il faut la créer
				else {
					ArrayList<MessagePrive> listeMessagesPrives = new ArrayList<MessagePrive>();
					// on ajoute le message
					listeMessagesPrives.add(message);

					// on ajoute une entrée pour cet expeditaire à la map des
					// messages privés du destinataire
					try {
						getMessagesPrivesUtilisateurs().get(login).put(
								message.getExpeditaire(), listeMessagesPrives);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			// si la clé n'existe pas, on crée la liste de messages privés
			else {
				// on créer la liste pour le destinataire car elle n'existe pas
				ArrayList<MessagePrive> listeMessagesPrives = new ArrayList<MessagePrive>();
				// on ajoute le message à la liste
				listeMessagesPrives.add(message);
				// on créer également la treemap pour cet expeditaire
				TreeMap<String, ArrayList<MessagePrive>> mapExpeditaire = new TreeMap<String, ArrayList<MessagePrive>>();
				// on créer une entrée dans la map pour cet expeditaire
				mapExpeditaire.put(message.getExpeditaire(),
						listeMessagesPrives);
				// on créer une entrée dans la map pour le destinataire

				getMessagesPrivesUtilisateurs().put(login, mapExpeditaire);

			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param message
	 * @param idConversation
	 * @param login
	 */
	public void ajouterMessageConversation(Message message, int idConversation,
			String login) throws RemoteException {
		Conversation conversation = getConversation(login, idConversation);
		if (conversation != null) {
			conversation.getListeMessages().add(message);
		}
	}

	/**
	 * Distribue le message d'une conversation
	 * 
	 * @param message
	 */
	public void distribuerMessageConversation(MessageConversation message)
			throws RemoteException {
		try {

			ArrayList<Groupe> groupes = message.getGroupesParticipants();
			ArrayList<String> utilisateursDistribues = new ArrayList<String>();
			if (groupes != null) {
				for (Groupe g : groupes) {
					ArrayList<Utilisateur> utilisateurs = g.getUtilisateurs();
					for (Utilisateur u : utilisateurs) {
						// si cet utilisateur n'a pas été traité
						// car possible qu'un utilisateur d'une liste
						// d'utilisateurs soit dans inclus dans un groupe
						// d'utilisateurs
						if (!utilisateursDistribues.contains(u.getLogin())) {
							Client client = getClientConnecte(u.getLogin());

							// le client est connecté
							if (client != null) {
								// on envoie
								client.recevoirMessage(message);
							}
							// le client n'est pas connecté
							else {
								// on traite : ajout dans la liste des
								// conversations de l'utilisateur

								// il faut faire un test pour voir si l'entrée
								// existe dans la treemap
								// sinon il faut la créer....
								if (getConversationsUtilisateurAbsent(u
										.getLogin()) == null) {
									getConversationsUtilisateursAbsents().put(
											u.getLogin(),
											new ArrayList<Conversation>());
								}

								// si la conversation n'existe pas, on la
								// récupère dans la liste des conversations
								if (getConversation(u.getLogin(),
										message.getIdConversation()) == null) {
									ArrayList<Conversation> conversations = getConversationsUtilisateurAbsent(u
											.getLogin());
									conversations.add(getConversations().get(
											message.getIdConversation()));
								}
								Conversation c = getConversation(u.getLogin(),
										message.getIdConversation());
								c.getListeMessages().add(message);

							}
							// cet utilisateur a été traité
							utilisateursDistribues.add(u.getLogin());
						}
					}
				}
			}
			ArrayList<Utilisateur> utilisateurs = message.getParticipants();
			if (utilisateurs != null) {
				for (Utilisateur u : utilisateurs) {
					// si cet utilisateur n'a pas été traité
					// car possible qu'un utilisateur d'une liste
					// d'utilisateurs soit dans inclus dans un groupe
					// d'utilisateurs

					if (!utilisateursDistribues.contains(u.getLogin())) {
						Client client = getClientConnecte(u.getLogin());

						// le client est connecté
						if (client != null) {
							// on envoie
							client.recevoirMessage(message);
						}
						// le client n'est pas connecté
						else {

							// on traite : ajout dans la liste des
							// conversations de l'utilisateur

							// il faut faire un test pour voir si l'entrée
							// existe dans la treemap
							// sinon il faut la créer....
							if (getConversationsUtilisateurAbsent(u.getLogin()) == null) {
								getConversationsUtilisateursAbsents().put(
										u.getLogin(),
										new ArrayList<Conversation>());
							}

							// si la conversation n'existe pas, on la crée
							if (getConversation(u.getLogin(),
									message.getIdConversation()) == null) {
								ArrayList<Conversation> conversations = getConversationsUtilisateurAbsent(u
										.getLogin());
								conversations.add(new Conversation(message
										.getIdConversation(), message
										.getParticipants(), message
										.getGroupesParticipants()));
							}
							Conversation c = getConversation(u.getLogin(),
									message.getIdConversation());
							c.getListeMessages().add(message);
						}
						// cet utilisateur a été traité
						utilisateursDistribues.add(u.getLogin());
					}
				}

			}
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	/**
	 * Créer une conversation entre un ensemble d'utilisateurs et un ensemble de
	 * groupes
	 * 
	 * @param utilisateurs
	 * @param groupes
	 * @return idConversation vaut le numéro de la conversation si conversation
	 *         créée ou -1 sinon
	 */
	public int creerUneConversation(ArrayList<Utilisateur> utilisateurs,
			ArrayList<Groupe> groupes) throws RemoteException {
		int idConversation = -1;
		try {
			idConversation = getConversations().size();
			getConversations().put(new Integer(idConversation),
					new Conversation(idConversation, utilisateurs, groupes));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return idConversation;
	}

	// private TreeMap<Integer, Conversation> conversations;

	/**
	 * Renvoie la liste des utilisateurs inscrits
	 * 
	 * @return utilisateursInscrits
	 */
	public ArrayList<Utilisateur> getUtilisateursInscrits()
			throws RemoteException {
		return utilisateursInscrits;
	}

	/**
	 * Renvoie la liste des clients connectés
	 * 
	 * @return utilisateursConnectes
	 */
	public ArrayList<Client> getUtilisateursConnectes()
			throws RemoteException {
		return utilisateursConnectes;
	}

	/**
	 * Permet de tester si l'utilisateur de login 'login' existe
	 * 
	 * @param login
	 *            : login de l'utilisateur
	 * @return true si l'utilisateur existe , false sinon
	 */
	public boolean utilisateurExistant(String login) throws RemoteException {
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
	public void ajouterClient(Client c) throws RemoteException {
		getUtilisateursConnectes().add(c);
	}

	/**
	 * Ajoute un utilisatuer à la liste des utilisateurs inscrits sur le serveur
	 * 
	 * @param u
	 *            : utilisateur
	 */
	public void ajouterUtilisateur(Utilisateur u) throws RemoteException {
		getUtilisateursInscrits().add(u);
	}

	/**
	 * Renvoie l'utilisateur de login 'login'
	 * 
	 * @param login
	 *            : login de l'utilisateur
	 * @return utilisateur si existant , null sinon
	 */
	public Utilisateur getUtilisateurInscrit(String login)
			throws RemoteException {
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
	public boolean inscriptionSecurisee(String login, String motDePasse)
			throws RemoteException {

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
			throws NoSuchAlgorithmException, UnsupportedEncodingException,
			RemoteException {

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
			String motDePasse) throws RemoteException {
		boolean existant = false;
		try {
			existant = inscription(login, motDePasse);
			String chemin = System.getProperty("user.dir") + "/docServeur/";
			System.out.println(chemin);
			creerUnRepertoireUtilisateur(getUtilisateurInscrit(login),chemin);
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
	public boolean connexion(String login, String motDepasse, Client c)
			throws RemoteException {

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
	public Client getClientConnecte(String login) throws RemoteException {
		Client cl = null;
		for (Client c : getUtilisateursConnectes()) {
			if (c.getUtilisateur().equals(getUtilisateurInscrit(login))) {
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
	public void setServeurRMI(Serveur serv) throws RemoteException {
		this.serveur = serv;
	}

	/**
	 * Renvoie le serveur RMI
	 * 
	 * @return serveur
	 */
	public Serveur getServeurRMI() throws RemoteException {
		return serveur;
	}

	/**
	 * Supprime le clientRMI de la liste des clients connectés
	 * 
	 * @param client
	 */
	public void supprimerUnClient(Client client) throws RemoteException {
		getUtilisateursConnectes().remove(indexClient(client));
	}

	/**
	 * Renvoie l'index du client dans la liste des clients connectés
	 * 
	 * @param client
	 * @return si trouvé num = index du client connecté , -1 sinon
	 */
	public int indexClient(Client client) throws RemoteException {
		int num = -1;
		int compteur = 0;
		for (Client cl : getUtilisateursConnectes()) {
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
	public void creerUnRepertoireUtilisateur(Utilisateur u, String path)
			throws RemoteException {
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
	public void creerUnRepertoire(String path) throws RemoteException {
		File repertoire = new File(path);
		repertoire.mkdirs();
	}

	/**
	 * Distribue un message envoyé par un client à tous les clients connectés
	 * 
	 * @param message
	 */
	public void distribuerMessage(Message message) throws RemoteException {
		Registry registry;
		try {
			registry = LocateRegistry.getRegistry(REGISTRY_PORT);
			for (Client c : getUtilisateursConnectes()) {
				try {
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
	 * 
	 * @param message
	 * @param expediteur
	 * @param destinataire
	 * @require : destinataire inscrit sur le serveur
	 * @require : expediteur inscrit et connecté sur le serveur
	 */
	public void distribuerMessagePrive(MessagePrive message)
			throws RemoteException {
		// si le destinataire est connecté
		if (getClientConnecte(message.getDestinataire()) != null) {
			Registry registry;
			try {
				registry = LocateRegistry.getRegistry(REGISTRY_PORT);
				Remote rem;
				try {
					// on récupère le client du destinataire sur le serveur
					Client destinataire = getClientConnecte(message
							.getDestinataire());
					rem = registry.lookup(destinataire.getUtilisateur()
							.getLogin());

					// On l'affiche du côté du destinataire
					((InterfaceClientRmi) rem).recevoirMessagePrive(message);
					// on l'affiche également du côté de l'expediteur

					// on récupère le client du expediteur sur le serveur
					Client expediteur = getClientConnecte(message
							.getExpeditaire());
					// on récupère l'objet distant du client expediteur du
					// message
					Remote remo = registry.lookup(expediteur.getUtilisateur()
							.getLogin());
					((InterfaceClientRmi) remo).recevoirMessagePrive(message);

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
			System.out
					.println("Le destinataire n'est pas connecté, le message sera remis à sa prochaine connexion");
			Registry registry;
			try {
				registry = LocateRegistry.getRegistry(REGISTRY_PORT);
				Remote r = registry.lookup("fram");
				((InterfaceServeurRmi) r).ajouterMessagePrive(
						message.getDestinataire(), message);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// ajouterMessagePrive(loginDestinataire,new MessagePrive(message,
			// loginExpediteur, loginDestinataire));

		}
	}

	/**
	 * Renvoie l'utilisateur du clientRMI si trouvé dans la liste des clients
	 * connectés
	 * 
	 * @param cl
	 * @return l'utilisateur si il est connecté , null sinon
	 */
	public Utilisateur utilisateurConnecte(Client cl) throws RemoteException {
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
	 * 
	 * @return publications
	 */
	public ArrayList<Publication> getPublications() {
		return publications;
	}

	/**
	 * Affecte un ensemble de publications à l'ensemble de publications du
	 * serveur
	 * 
	 * @param publications
	 */
	public void setPublications(ArrayList<Publication> publications)
			throws RemoteException {
		this.publications = publications;
	}

	/**
	 * Ajoute une publication à la liste des publications contenues sur le
	 * serveur
	 * 
	 * @param publication
	 */
	public void AddPublication(Publication publication) throws RemoteException {
		getPublications().add(publication);
	}

	/**
	 * Affiche l'ensemble des publications visibles pour un utilisateur
	 * @param utilisateur         : l'utilisateur a verifier
	 * @return ArrayList<Publication> : return publication visibles
	 */
	public ArrayList<Publication> getPublicationsVisibles(Utilisateur utilisateur) throws RemoteException {

		ArrayList<Publication> publicationsVisibles = new ArrayList<Publication>();
		ArrayList<Publication> publications = getPublications();
		Date dateCourante = new Date();
		try {
			for (int i = 0; i < publications.size(); i++) {

				ArrayList<Utilisateur> utilisateurs = publications.get(i).getVisibiliteUtilisateur();
				ArrayList<Groupe> groupes = publications.get(i).getVisibiliteGroupe();
				if (utilisateurs == null || groupes == null) {
					
				}
				else
				{
					for (Utilisateur util : utilisateurs) {
						if (util.equals(utilisateur)) {
							if((publications.get(i).getDateFinDisponibilite()==null)||(publications.get(i).getDateFinDisponibilite().after(dateCourante)))
							{
								publicationsVisibles.add(publications.get(i));
							}
						}
					}
					
					for (Groupe groupe : groupes) {
						
						for(Utilisateur util : groupe.getUtilisateurs())
						{
							if(util.getLogin().equals(utilisateur.getLogin()))
							{
								if((publications.get(i).getDateFinDisponibilite()==null)||(publications.get(i).getDateFinDisponibilite().after(dateCourante)))
								{
									publicationsVisibles.add(publications.get(i));
								}
							}
						}
						
					}
				}

			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		return publicationsVisibles;
	}
	/**
	 * Renvoie la liste des publications d'un utilisateurs
	 * @param utilisateur : Objet utilisateur 
	 * @return ArrayList<Publication> : les publications de l'utilisateur utilisateur
	 * @throws RemoteException
	 */
	public ArrayList<Publication> getPublications(Utilisateur utilisateur)
			throws RemoteException {
		ArrayList<Publication> publicationsProp = new ArrayList<Publication>();
		ArrayList<Publication> lespublications = getPublications();

		for (Publication pub : lespublications) {
			if (pub.getProprietaire().equals(utilisateur)) {
				publicationsProp.add(pub);
			}
		}
		return publicationsProp;
	}
	/**
	 * Rend visible une publication a un utilisateur
	 * @param utilisateur : l'utilisateur à autoriser 
	 * @param publicaion : la publication concernée
	 * @throws RemoteException
	 */
	public void supprimerUnePublication(Utilisateur utilisateur,
			Publication publication) throws RemoteException {
		ArrayList<Publication> lespublications = getPublications();

		boolean supprimer = false;
		for (Publication pub : lespublications) {
			if (pub.equals(publication)) {
				if (!(pub.getProprietaire().getLogin().equals(utilisateur
						.getLogin()))) {
					throw new Require(
							"Suppression impossible car l'utilisateur : "
									+ utilisateur.getLogin()
									+ " n'est pas propriétaire de cette publication.");
				} else {
					supprimer = true;
				}
			}
		}
		if (supprimer) {
			supprimerUnePublication(publication);
		}

	}
	/**
	 * Supprime une publication
	 * @param pub : la publication
	 * @throws RemoteException
	 */
	public void supprimerUnePublication(Publication pub) throws RemoteException {
		try {
			getPublications().remove(indexPublication(pub));
		//	pub.getDocument().getFichier().;
			File file = new File(pub.getDocument().getEmplacement());
			file.delete();
		} catch (RemoteException e) {

			e.printStackTrace();
		}
	}
/**
 * Retourn l'index d'une publication
 * @param publication
 * @return int : index
 * @throws RemoteException
 */
	public int indexPublication(Publication publication) throws RemoteException {
		int num = -1;
		int compteur = 0;
		for (Publication pub : getPublications()) {
			if (pub.getDateDePublication().equals(
					publication.getDateDePublication())) {
				num = compteur;
			}
			compteur++;
		}

		return num;
	}
	/**
	 * Rend visible une publication a un utilisateur
	 * @param utilisateur : l'utilisateur à autoriser 
	 * @param publicaion : la publication concernée
	 * @throws RemoteException
	 */
	public void autoriserUnePublicationUtilisateur(Utilisateur utilisateur,
			Publication publication) throws RemoteException {
		getPublications().get(indexPublication(publication))
				.autoriserAccesUtilisateur(utilisateur);
	}
	/**
	 * Retire la visibilite d'un utilisateur
	 * @param utilisateur : l'utilisateur à retirer
	 * @param publication : la publication concernée
	 * @throws RemoteException
	 */
	public void retirerUnePublicationutilisateur(Utilisateur utilisateur,
			Publication publication) throws RemoteException {
		getPublications().get(indexPublication(publication))
				.retirerAccesUtilisateur(utilisateur);
	}
	/**
	 * Rend visible une publication a un groupe
	 * @param groupe : le groupe à autorisier
	 * @param publication : la publication concernée
	 * @throws RemoteException
	 */
	public void autoriserUnePublicationGroupe(Groupe groupe,
			Publication publication) throws RemoteException {
		getPublications().get(indexPublication(publication))
				.autoriserAccesGroupe(groupe);
	}
	/**
	 * Retire la visibilite d'un groupe
	 * @param groupe : le groupe à retirer
	 * @param publication : la publication concernée
	 * @throws RemoteException
	 */
	public void retirerUnePublicationGroupe(Groupe groupe,
			Publication publication) throws RemoteException {
		getPublications().get(indexPublication(publication))
				.retirerAccesGroupe(groupe);
	}
	/**
	 * Creer un groupe 
	 * @param nomGroupe :  le nom du groupe à creer 
	 * @param utilisateur : l'utilisateur propriétaire
	 * @throws RemoteException
	 */
	public void creerUnGroupe(String nomGroupe, String utilisateur)
			throws RemoteException {
		Utilisateur utili = getUtilisateurInscrit(utilisateur);
		utili.creerUnGroupe(nomGroupe);
		// System.out.println(""+utili.getGroupes().size());
	}
	/**
	 * Ajoute un utilisateur dans un groupe
	 * @param idGroupe : le groupe concerné
	 * @param utilisateur : l'utilisateur à ajouter
	 * @throws RemoteException
	 */
	public void ajouterUnUtlisateurDansGroupe(int idGroupe,
			Utilisateur utilisateur) throws RemoteException {
		Groupe groupe = null;
		Utilisateur util1 = null;
		for (Utilisateur util : getUtilisateursInscrits()) {
			if (util.getGroupe(idGroupe) != null) {
				groupe = util.getGroupe(idGroupe);
			}

			if (util.getLogin().equals(utilisateur.getLogin())) {
				util1 = util;
			}
		}
		groupe.ajouterUtilisateur(utilisateur);
		util1.ajouterGroupe(groupe);
	}
	/**
	 * Supprime un utilisateur du groupe
	 * @param idGroupe : le groupe concerné
	 * @param utilisateur : l'utilisateur à supprimer
	 * @throws RemoteException
	 */
	public void supprimerUnUtilsateurDuGroupe(int idGroupe,
			Utilisateur utilisateur) throws RemoteException {
		Groupe groupeTrouve = null;
		Utilisateur util1 = null;
		for (Utilisateur util : getUtilisateursInscrits()) {
			if (util.getGroupe(idGroupe) != null) {
				groupeTrouve = util.getGroupe(idGroupe);
			}
			if (util.getLogin().equals(utilisateur.getLogin())) {
				util1 = util;
			}

		}
		groupeTrouve.supprimerUtilisateur(utilisateur);
		util1.supprimerUnGroupe(idGroupe);
	}
	/**
	 * Supprime un groupe
	 * @param idGroupe : le groupe à supprimer
	 * @throws RemoteException
	 */
	public void supprimerGroupe(int idGroupe) throws RemoteException {
		Groupe groupeTrouve = null;
		for (Utilisateur util : getUtilisateursInscrits()) {
			if (util.getGroupe(idGroupe) != null) {
				groupeTrouve = util.getGroupe(idGroupe);
			}
		}
		Utilisateur prorietaire = groupeTrouve.getProprietaire();
		prorietaire.supprimerUnGroupe(idGroupe);
	}
	/**
	 * Retourne la liste des groupes
	 * @return ArrayList<Groupe> liste des groupes 
	 * @throws RemoteException
	 */
	public ArrayList<Groupe> getGroupes() throws RemoteException {
		ArrayList<Groupe> lesGroupes = new ArrayList<Groupe>();

		for (Utilisateur utli : getUtilisateursInscrits()) {
			for (Groupe groupe : utli.getGroupes()) {
				lesGroupes.add(groupe);
			}
		}

		return lesGroupes;
	}
	/**
	 * Retourne la liste des groupes auxquelle l'utilisateur est propriétaire
	 * @param utilisateur : l'utilisateur concerné 
	 * @return ArrayList<Groupe> liste des groupes dont un utilisateur est propriétaire
	 * @throws RemoteException
	 */
	public ArrayList<Groupe> getGroupesProp(Utilisateur utilisateur)
			throws RemoteException {
		ArrayList<Groupe> lesGroupes = new ArrayList<Groupe>();
		ArrayList<Groupe> lesGroupesRech = new ArrayList<Groupe>();

		for (Utilisateur util : getUtilisateursInscrits()) {
			if (util.getLogin().equals(utilisateur.getLogin())) {
				lesGroupesRech = util.getGroupes();
			}
		}
		for (Groupe groupe : lesGroupesRech) {
			if (groupe.getProprietaire().getLogin()
					.equals(utilisateur.getLogin())) {
				lesGroupes.add(groupe);
			}
		}
		return lesGroupes;
	}
/**
 * Retourn tous les groupes d'un utilisateur
 */
	public ArrayList<Groupe> getGroupes(Utilisateur utilisateur)
			throws RemoteException {

		ArrayList<Groupe> lesGroupes = new ArrayList<Groupe>();
		ArrayList<Groupe> lesGroupesRech = new ArrayList<Groupe>();

		for (Utilisateur util : getUtilisateursInscrits()) {
			if (util.getLogin().equals(utilisateur.getLogin())) {
				lesGroupesRech = util.getGroupes();
			}
		}
		for (Groupe groupe : lesGroupesRech) {
			if (groupe.getProprietaire().getLogin()
					.equals(utilisateur.getLogin())) {
				lesGroupes.add(groupe);
			}
		}

		return lesGroupesRech;

	}
	/**
	 * Retour un Objet groupe pour un idgroupe
	 * @param idGroupe : le groupe à chercher
	 * @return Retour un Objet groupe pour un idgroupe
	 * @throws RemoteException
	 */
	public Groupe getGroupe(int idGroupe) throws RemoteException {
		ArrayList<Groupe> lesGroupesRech = new ArrayList<Groupe>();

		Groupe groupe = null;
		for (Utilisateur util : getUtilisateursInscrits()) {
			lesGroupesRech = util.getGroupes();

			for (Groupe gpr : lesGroupesRech) {
				if (gpr.getIdGroupe() == idGroupe) {
					groupe = gpr;
				}
			}
		}

		return groupe;

	}
	
	 

}
