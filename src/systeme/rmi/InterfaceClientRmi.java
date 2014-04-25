package systeme.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

import modules.gestionUtilisateur.Utilisateur;

public interface InterfaceClientRmi extends Remote {
	
public Utilisateur getUtilisateur() throws RemoteException;
public void envoyerMessage(String s, ClientRMI expeditaire) throws RemoteException;
public void envoyerMessagePrive(String s, ClientRMI expeditaire, ClientRMI destinataire) throws RemoteException;
public void recevoirMessage(String s, ClientRMI expeditaire) throws RemoteException;

}
