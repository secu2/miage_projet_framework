package systeme.rmi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.TreeMap;

import systeme.Client;
import systeme.Serveur;
import systeme.tools.Encryptage;
import modules.chat.Conversation;
import modules.chat.Message;
import modules.chat.MessageConversation;
import modules.chat.MessagePrive;
import modules.documents.social.Publication;
import modules.gestionUtilisateur.Groupe;
import modules.gestionUtilisateur.Utilisateur;

//classe héritante de l'interface et définir les méthodes propres au serveur.
public class ServeurRmiImpl extends UnicastRemoteObject implements InterfaceServeurRmi {

	private static final long serialVersionUID = 2674880711467464646L;

	private OutputStream out;
	private InputStream in;
	private Serveur serveur;
	static int REGISTRY_PORT = 1099;

	/** 
	* Messages privés reçus lorsque le client n'était pas connecté
	* La clé de la TreeMap est le login de l'utilisateur qui donnent accès à une autre treemap
	* qui elle donne accès à l'ensemble des messages envoyés identifié par une clé qui est le login de l'expeditaire
	*/ 
	private TreeMap<String,TreeMap<String, ArrayList<MessagePrive>>> messagesPrivesUtilisateurs;
	/**
	 * Conversation que l'utilisateur a reçu alors qu'il était deconnecté
	 * La clé est le login de l'utilisateur, qui donne ainsi accès à sa liste de conversations 
	 */
	private TreeMap<String,ArrayList<Conversation>> conversationsUtilisateursAbsent;
	/**
	 * Conversations en cours (?)
	 */
	private TreeMap<Integer,Conversation> conversations;

	public ServeurRmiImpl(OutputStream out, InputStream in,Serveur serveur) throws RemoteException {
		super();
		this.out = out;
		this.in = in;
		this.serveur=serveur;
	}

	public ServeurRmiImpl(Serveur serveur) throws RemoteException {
		super();
		this.out = null;
		this.in = null;
		this.serveur = serveur;
		this.messagesPrivesUtilisateurs = new TreeMap<String, TreeMap<String,ArrayList<MessagePrive>>>();
		this.conversationsUtilisateursAbsent = new TreeMap<String, ArrayList<Conversation>>();
		this.conversations = new TreeMap<Integer, Conversation>();
	}

	public ServeurRmiImpl(OutputStream out,Serveur serveur) throws RemoteException {
		super();
		this.out = out;
		this.serveur=serveur;
	}

	public ServeurRmiImpl(InputStream in,Serveur serveur) throws RemoteException {
		super();
		this.in = in;
		this.serveur=serveur;
	}

	public String getTest() throws RemoteException {
		System.out.println("Invocation de la méthode getInformation()");
		return "Momo t'es moche ";
	}

	public OutputStream getOut() {
		return this.out;
	}

	public void setOut(OutputStream out) {
		this.out = out;
	}

	public InputStream getIn() {
		return this.in;

	}

	public void setIn(InputStream in) {
		this.in = in;
	}

	public Serveur getServeur() throws RemoteException
	{
		return this.serveur;
	}
	
	/**
	 * Renvoie la liste des clients connectés sur le serveur
	 * @return utilisateursConnectes
	 */
	public ArrayList<ClientRMI> getClientsconnectes() throws RemoteException
	{
		return getServeur().getUtilisateursConnectes();
	}
	
	/**
	 * Connecte un client au serveur
	 * @param login
	 * @param motDepasse
	 * @param c
	 */
	public boolean connexion(String login, String motDepasse ,ClientRMI c) throws RemoteException
	{
		return getServeur().connexion(login, motDepasse, c);
	}

	/**
	 * Ajoute un client à la liste des clientsRMI du serveur
	 * @param c : clientRMI
	 */
	public void ajouterClient(ClientRMI c) throws RemoteException {
		getServeur().ajouterClient(c);
	}

	/**
	 * Deconnecte un client
	 * @param c : clientRMI
	 */
	public void deconnexion(ClientRMI c) throws RemoteException {
		System.out.println(c.toString());
		getServeur().supprimerUnClient(c);
	}
	
	/**
	 * Envoi un message aux autres utilisateurs
	 * @param message
	 */
	public void envoiMessage(Message message)  throws RemoteException {
		getServeur().distribuerMessage(message);
	}
	
	/**
	 * Ajoute une publication à la liste des publications du serveur
	 * @param publication
	 */
	public void ajouterPublication(Publication publication)throws RemoteException
	{
		getServeur().AddPublication(publication);
	}
	
	
	/**
	 * Renvoie la treemap des messages privés des utilisateurs
	 * @return messagesPrivesUtilisateurs
	 */
	public TreeMap<String,TreeMap<String, ArrayList<MessagePrive>>> getMessagesPrivesUtilisateurs() throws RemoteException{
		return messagesPrivesUtilisateurs;
	}
	
	/**
	 * Renvoie la treemap des conversations des utilisateurs qui ont eu lieu lors de leur absence
	 * @return conversationsUtilisateursAbsent
	 */
	public TreeMap<String, ArrayList<Conversation>> getConversationsUtilisateursAbsents() throws RemoteException{
		return this.conversationsUtilisateursAbsent;
	}
	
	/**
	 * Renvoie la liste des conversations d'un utilisateur
	 * @param login
	 * @return liste des conversations de l'utilisateur si elle existe, null sinon
	 */
	public ArrayList<Conversation>  getConversationsUtilisateurAbsent(String login) throws RemoteException{
	
		return getConversationsUtilisateursAbsents().get(login);
	}
	
	/**
	 * Renvoie la conversation où l'utilisateur était absent
	 * @param login
	 * @param idConversation
	 * @return la conversation si il existe , null sinon
	 */
	public Conversation getConversation(String login, int idConversation)  throws RemoteException{
		Conversation conversation = null;

		if(getConversationsUtilisateurAbsent(login) != null){
			for(Conversation c :  getConversationsUtilisateurAbsent(login)){
				if(c.getIdConversation() == idConversation){
					conversation = c;
				}
			}
		}
		
		
		return conversation;	
	}
	
	/**
	 * Renvoie la treemap de conversations
	 * @return conversations
	 */
	public TreeMap<Integer, Conversation> getConversations() throws RemoteException{
		return this.conversations;
	}
	
	/**
	 * Retourne la conversation
	 * @param idConversation
	 * @return la conversation si il existe , null sinon
	 */
	public Conversation getConversation(int idConversation) throws RemoteException{
		return getConversations().get(new Integer(idConversation));
	}
	
	/**
	 * Renvoie la liste des messages privés entre l'expeditaire et le destinataire
	 * @param expeditaire
	 * @param destinataire
	 * @return la liste des messages privés entre l'expeditaire et le destinataire si elle existe, null sinon
	 */
	public ArrayList<MessagePrive> getListeMessagesPrives(String expeditaire, String destinataire) throws RemoteException{
		return getMessagesPrivesUtilisateurs().get(destinataire).get(expeditaire);
	}
	
	/**
	 * Renvoie le treemap des messages privés de l'utilisateur
	 * @param login
	 * @return la treemap des messages privés de l'utilisateur si elle existe, null sinon
	 */
	public TreeMap<String, ArrayList<MessagePrive>> getMapMessagesPrivesUtilisateur(String login)throws RemoteException {
		return getMessagesPrivesUtilisateurs().get(login);
	}

	/**
	 * Ajoute un message privé à la liste des messages privés de l'utilisateur 
	 * @param login
	 * @param message
	 */
	public void ajouterMessagePrive(String login, MessagePrive message) throws RemoteException {
		// la clé existe,, on va donc récupérer la map pour ce destinataire
		try {
			if(getMessagesPrivesUtilisateurs().containsKey(login)){

				// il existe déjà une liste de messages pour cet expeditaire
				if(getListeMessagesPrives(message.getExpeditaire(),login) != null){
					// on la récupère donc
					ArrayList<MessagePrive> messagesExpeditaire = getListeMessagesPrives(message.getExpeditaire(),login);
					// on l'ajoute donc aux autres
					messagesExpeditaire.add(message);
				}
				// elle n'existe pas : il faut la créer
				else{
					ArrayList<MessagePrive> listeMessagesPrives =  new ArrayList<MessagePrive>();
					// on ajoute le message
					listeMessagesPrives.add(message);

					// on ajoute une entrée pour cet expeditaire à la map des messages privés du destinataire
					try {
						getMessagesPrivesUtilisateurs().get(login).put(message.getExpeditaire(), listeMessagesPrives);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			// si la clé n'existe pas,  on crée la liste de messages privés
			else{
				// on créer la liste pour le destinataire car elle n'existe pas
				ArrayList<MessagePrive> listeMessagesPrives =  new ArrayList<MessagePrive>();
				// on ajoute le message à la liste
				listeMessagesPrives.add(message);
				// on créer également la treemap pour cet expeditaire 
				TreeMap<String,ArrayList<MessagePrive>> mapExpeditaire = new TreeMap<String, ArrayList<MessagePrive>>();
				// on créer une entrée dans la map pour cet expeditaire
				mapExpeditaire.put(message.getExpeditaire(),listeMessagesPrives);
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
	public void ajouterMessageConversation(Message message, int idConversation , String login) throws RemoteException{
		Conversation conversation = getConversation(login, idConversation);
		if(conversation != null){
			conversation.getListeMessages().add(message);
		}
	}
	
	
	/**
	 * Distribue le message d'une conversation
	 * 
	 * @param message
	 */
	public void distribuerMessageConversation(MessageConversation message) throws RemoteException{
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
							ClientRMI client = serveur.getClientConnecte(u.getLogin());

							// le client est connecté
							if (client != null) {
								// on envoie
								client.recevoirMessage(message);
							}
							// le client n'est pas connecté
							else {
								// on traite : ajout dans la liste des
								// conversations de l'utilisateur



								// il faut faire un test pour voir si l'entrée existe dans la treemap
								// sinon il faut la créer....
								if(getConversationsUtilisateurAbsent(u.getLogin()) == null){
									getConversationsUtilisateursAbsents().put(u.getLogin(), new ArrayList<Conversation>());
								}
								
								// si la conversation n'existe pas, on la récupère dans la liste des conversations 
								if(getConversation(u.getLogin(),message.getIdConversation()) == null){
									ArrayList<Conversation> conversations = getConversationsUtilisateurAbsent(u.getLogin());
									conversations.add(getConversations().get(message.getIdConversation()));
								}
								Conversation c = getConversation(u.getLogin(),message.getIdConversation());
								c.getListeMessages().add(message);
								
							}
							// cet utilisateur a été traité
							utilisateursDistribues.add(u.getLogin());
						}
					}
				}
			}
			ArrayList<Utilisateur> utilisateurs = message.getParticipants();
			if(utilisateurs != null){
				for (Utilisateur u : utilisateurs) {
					// si cet utilisateur n'a pas été traité
					// car possible qu'un utilisateur d'une liste
					// d'utilisateurs soit dans inclus dans un groupe
					// d'utilisateurs

					if (!utilisateursDistribues.contains(u.getLogin())) {
						ClientRMI client = serveur.getClientConnecte(u.getLogin());

						// le client est connecté
						if (client != null) {
							// on envoie
							client.recevoirMessage(message);
						}
						// le client n'est pas connecté
						else {

							// on traite : ajout dans la liste des
							// conversations de l'utilisateur
							

							// il faut faire un test pour voir si l'entrée existe dans la treemap
							// sinon il faut la créer....
							if(getConversationsUtilisateurAbsent(u.getLogin()) == null){
								getConversationsUtilisateursAbsents().put(u.getLogin(), new ArrayList<Conversation>());
							}
							
							// si la conversation n'existe pas, on la crée
							if(getConversation(u.getLogin(),message.getIdConversation()) == null){
								ArrayList<Conversation> conversations = getConversationsUtilisateurAbsent(u.getLogin());
								conversations.add(new Conversation(message.getIdConversation(), message.getParticipants(), message.getGroupesParticipants()));
							}
							Conversation c = getConversation(u.getLogin(),message.getIdConversation());
							c.getListeMessages().add(message);
						}
						// cet utilisateur a été traité
						utilisateursDistribues.add(u.getLogin());
					}
			}

			}
		}catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 

	}
	
	/**
	 * Créer une conversation entre un ensemble d'utilisateurs et un ensemble de groupes
	 * @param utilisateurs
	 * @param groupes
	 * @return idConversation vaut le numéro de la conversation si conversation créée ou -1 sinon
	 */
	public int creerUneConversation(ArrayList<Utilisateur> utilisateurs, ArrayList<Groupe> groupes) throws RemoteException{
		int idConversation = -1;
		try {
			 idConversation = getConversations().size();
			 getConversations().put(new Integer(idConversation), new Conversation(idConversation, utilisateurs, groupes));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return idConversation;
	}

	
	
	
	
}

