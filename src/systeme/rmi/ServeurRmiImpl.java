package systeme.rmi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.TreeMap;

import systeme.Client;
import systeme.Serveur;
import systeme.tools.Encryptage;
import modules.chat.Message;
import modules.chat.MessagePrive;
import modules.documents.social.Publication;
import modules.gestionUtilisateur.Utilisateur;

//classe héritante de l'interface et définir les méthodes propres au serveur.
public class ServeurRmiImpl extends UnicastRemoteObject implements InterfaceServeurRmi {

	private static final long serialVersionUID = 2674880711467464646L;

	private OutputStream out;
	private InputStream in;
	private Serveur serveur;
	/** 
	* Messages privés reçus lorsque le client n'était pas connecté
	* La clé de la TreeMap est le login de l'utilisateur qui donnent accès à une autre treemap
	* qui elle donne accès à l'ensemble des messages envoyés identifié par une clé qui est le login de l'expeditaire
	*/ 
	private TreeMap<String,TreeMap<String, ArrayList<MessagePrive>>> messagesPrivesUtilisateurs;

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
	
	public ArrayList<ClientRMI> getClientsconnectes() throws RemoteException
	{
		return getServeur().getUtilisateursConnectes();
	}
	
	public boolean connexion(String login, String motDepasse ,ClientRMI c) throws RemoteException
	{
		return getServeur().connexion(login, motDepasse, c);
	}

	public void ajouterClient(ClientRMI c) throws RemoteException {
		getServeur().ajouterClient(c);
	}

	public void deconnexion(ClientRMI c) throws RemoteException {
		System.out.println(c.toString());
		getServeur().supprimerUnClient(c);
	}
	
	public void envoiMessage(Message message)  throws RemoteException {
		getServeur().distribuerMessage(message);
	}
	
	public void ajouterPublication(Publication publication)throws RemoteException
	{
		getServeur().AddPublication(publication);
	}
	
	
	/**
	 * 
	 * @return
	 */
	public TreeMap<String,TreeMap<String, ArrayList<MessagePrive>>> getMessagesPrivesUtilisateurs() throws RemoteException{
		return messagesPrivesUtilisateurs;
	}
	
	/**
	 * 
	 * @param expeditaire
	 * @param destinataire
	 * @return
	 */
	public ArrayList<MessagePrive> getListeMessagesPrives(String expeditaire, String destinataire) throws RemoteException{
		return getMessagesPrivesUtilisateurs().get(destinataire).get(expeditaire);
	}
	
	/**
	 * 
	 * @param login
	 * @return 
	 */
	public TreeMap<String, ArrayList<MessagePrive>> getMapMessagesPrivesUtilisateur(String login)throws RemoteException {
		return getMessagesPrivesUtilisateurs().get(login);
	}

	/**
	 * 
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

	
	
}

