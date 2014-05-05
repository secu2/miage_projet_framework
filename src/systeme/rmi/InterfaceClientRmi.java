package systeme.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

import application.chat.Vue;

import modules.chat.Message;
import modules.chat.MessageConversation;
import modules.chat.MessagePrive;
import modules.gestionUtilisateur.Utilisateur;

public interface InterfaceClientRmi extends Remote {
	

public void envoyerMessage(Message message) throws RemoteException;
public void envoyerMessagePrive(MessagePrive message) throws RemoteException;
public void envoyerMessageConversation(MessageConversation message) throws RemoteException;
public void recevoirMessage(Message message) throws RemoteException;
public Vue getVue() throws RemoteException;
public void setVue(Vue vue) throws RemoteException;

}
