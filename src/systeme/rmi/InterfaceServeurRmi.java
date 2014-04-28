package systeme.rmi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.TreeMap;

import systeme.Client;
import modules.chat.Conversation;
import modules.chat.Message;
import modules.chat.MessageConversation;
import modules.chat.MessagePrive;
import modules.documents.social.Publication;
import modules.gestionUtilisateur.Groupe;
import modules.gestionUtilisateur.Utilisateur;


//création de l'interface propre au RMI

public interface InterfaceServeurRmi extends Remote {
	 
	public String getTest() throws RemoteException;
	public ArrayList<ClientRMI> getClientsconnectes() throws RemoteException;
	public void ajouterClient(ClientRMI c) throws RemoteException;
	public boolean connexion(String login, String motDepasse, ClientRMI c) throws RemoteException;
	/**
	 * Deconnecte un client du serveur
	 * @param c : le client a deconnecter
	 * @throws RemoteException
	 */
	public void deconnexion(ClientRMI c) throws RemoteException;
	public void envoiMessage(Message message)  throws RemoteException;
	public void ajouterPublication(Publication publication)throws RemoteException;
	public void ajouterMessagePrive(String login, MessagePrive message) throws RemoteException;
	public TreeMap<String, ArrayList<MessagePrive>> getMapMessagesPrivesUtilisateur(String login)throws RemoteException;
	public ArrayList<MessagePrive> getListeMessagesPrives(String expeditaire, String destinataire) throws RemoteException;
	public TreeMap<String,TreeMap<String, ArrayList<MessagePrive>>> getMessagesPrivesUtilisateurs() throws RemoteException;

	public TreeMap<String, ArrayList<Conversation>> getConversationsUtilisateursAbsents() throws RemoteException;
	public TreeMap<Integer, Conversation> getConversations() throws RemoteException;
	public Conversation getConversation(int idConversation) throws RemoteException;

	/**
	 * 
	 * @param login
	 * @return
	 */
	public ArrayList<Conversation>  getConversationsUtilisateurAbsent(String login) throws RemoteException;	
	/**
	 * 
	 * @param login
	 * @param idConversation
	 * @return
	 */
	public Conversation getConversation(String login, int idConversation) throws RemoteException;
	
	
	public void ajouterMessageConversation(Message message, int idConversation , String login) throws RemoteException;
	public void distribuerMessageConversation(MessageConversation message) throws RemoteException;
	public int creerUneConversation(ArrayList<Utilisateur> utilisateurs, ArrayList<Groupe> groupes) throws RemoteException;
	public void distribuerMessagePrive(MessagePrive message) throws RemoteException;
	public void distribuerMessage(Message message) throws RemoteException;
	public ArrayList<Publication> getPublicationsVisibles(
			Utilisateur utilisateur) throws RemoteException;
	public Utilisateur getUtilisateurInscrit(String login) throws RemoteException;
	public Object utilisateurConnecte(ClientRMI clientRMI) throws RemoteException;
	public ArrayList<Utilisateur> getUtilisateursInscrits() throws RemoteException;
}	
	