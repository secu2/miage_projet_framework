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
	
/** Envoie un message à tous les utilisateurs connectés sur le serveur
 * @param Message message : le message à envoyer
 */
public void envoyerMessage(Message message) throws RemoteException;

/**
 * Envoyer un message privé
 * @param Message message : le message à envoyer 
 */
public void envoyerMessagePrive(MessagePrive message) throws RemoteException;
/**
 * Envoie un message à plusieurs participants d'une conversation (utilisations et/ou groupes)
 * @param message
 */
public void envoyerMessageConversation(MessageConversation message) throws RemoteException;
/**
 * Méthode permettant de traiter le message distribué par le serveur aux différents clients connectés
 * @param Message message : le message à recevoir 
 */
public void recevoirMessage(Message message) throws RemoteException;

/**
 * Renvoie la vue de l'application de chat
 */
public Vue getVue() throws RemoteException;
/**
 * Affecte la vue de l'application de chat
 */
public void setVue(Vue vue) throws RemoteException;
/**
 * Actualise la liste des clients connecté et déconnectés dans la vue du chat graphique
 */
public void actualiserListes() throws RemoteException;
/**
 * Lance la raquête d'actualisation de la liste des utilisateurs connectés et déconnectés dans la vue du chat graphique
 * pour chaque client connecté
 * @throws RemoteException
 */
public void actualiserListesUtilisateurs() throws RemoteException;

}
