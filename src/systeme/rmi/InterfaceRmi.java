package systeme.rmi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import systeme.Client;
import systeme.Serveur;
import modules.documents.social.Publication;
import modules.gestionUtilisateur.Utilisateur;


//création de l'interface propre au RMI

public interface InterfaceRmi extends Remote {
	 
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
	public void envoiMessage(String s,ClientRMI c)  throws RemoteException;
	public void ajouterPublication(Publication publication)throws RemoteException;
}


