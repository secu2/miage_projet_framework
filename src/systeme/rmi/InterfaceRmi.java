package systeme.rmi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import systeme.Client;
import systeme.Serveur;
import modules.gestionUtilisateur.Utilisateur;


//création de l'interface propre au RMI

public interface InterfaceRmi extends Remote {
	 
	public String getTest() throws RemoteException;
	public Serveur getServeur() throws RemoteException;
	public ArrayList<ClientRMI> getClientsconnectee() throws RemoteException;
	public void ajouterClient(ClientRMI c) throws RemoteException;
	public boolean connexion(String login, String motDepasse, ClientRMI c) throws RemoteException;
}


