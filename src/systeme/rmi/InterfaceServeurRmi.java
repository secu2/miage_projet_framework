package systeme.rmi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.TreeMap;

import systeme.Client;
import systeme.Serveur;
import modules.chat.Message;
import modules.chat.MessagePrive;
import modules.documents.social.Publication;
import modules.gestionUtilisateur.Utilisateur;


//création de l'interface propre au RMI

public interface InterfaceServeurRmi extends Remote {
	 
	public String getTest() throws RemoteException;
	public Serveur getServeur() throws RemoteException;
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
}


