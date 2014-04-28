package systeme.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

import modules.chat.Message;
import modules.chat.MessagePrive;
import modules.gestionUtilisateur.Utilisateur;

public interface InterfaceClientRmi extends Remote {
	

public void envoyerMessage(Message message) throws RemoteException;
public void envoyerMessagePrive(MessagePrive message) throws RemoteException;
public void recevoirMessage(Message message) throws RemoteException;

}
