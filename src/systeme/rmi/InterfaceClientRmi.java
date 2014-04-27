package systeme.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

import modules.gestionUtilisateur.Utilisateur;

public interface InterfaceClientRmi extends Remote {
	

public void envoyerMessage(String s, String expeditaire) throws RemoteException;
public void envoyerMessagePrive(String s, String expeditaire, String destinataire) throws RemoteException;
public void recevoirMessage(String s, ClientRMI expeditaire) throws RemoteException;

}
