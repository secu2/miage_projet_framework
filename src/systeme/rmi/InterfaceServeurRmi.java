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
	public ArrayList<Publication> getPublicationsVisibles(Utilisateur utilisateur) throws RemoteException;
	public Utilisateur getUtilisateurInscrit(String login) throws RemoteException;
	public Object utilisateurConnecte(ClientRMI clientRMI) throws RemoteException;
	public ArrayList<Utilisateur> getUtilisateursInscrits() throws RemoteException;
	/**
	 * Renvoie la liste des publications d'un utilisateurs
	 * @param utilisateur : Objet utilisateur 
	 * @return ArrayList<Publication> : les publications de l'utilisateur utilisateur
	 * @throws RemoteException
	 */
	public ArrayList<Publication> getPublications(Utilisateur utilisateur) throws RemoteException;
	/**
	 * Supprime une publication 
	 * @param utilisateur : l'utilisateur propriétaire de la publication
	 * Ensure utilisateur.equals(publication().getProprietaire()) 
	 * @throws RemoteException
	 */
	public void supprimerUnePublication(Utilisateur utilisateur, Publication publication)throws RemoteException;
	/**
	 * Rend visible une publication a un utilisateur
	 * @param utilisateur : l'utilisateur à autoriser 
	 * @param publicaion : la publication concernée
	 * @throws RemoteException
	 */
	public void autoriserUnePublicationUtilisateur(Utilisateur utilisateur, Publication publication) throws RemoteException;
	/**
	 * Retire la visibilite d'un utilisateur
	 * @param utilisateur : l'utilisateur à retirer
	 * @param publication : la publication concernée
	 * @throws RemoteException
	 */
	public void retirerUnePublicationutilisateur(Utilisateur utilisateur, Publication publication) throws RemoteException;
	/**
	 * Rend visible une publication a un groupe
	 * @param groupe : le groupe à autorisier
	 * @param publication : la publication concernée
	 * @throws RemoteException
	 */
	public void autoriserUnePublicationGroupe(Groupe groupe, Publication publication) throws RemoteException;
	/**
	 * Retire la visibilite d'un groupe
	 * @param groupe : le groupe à retirer
	 * @param publication : la publication concernée
	 * @throws RemoteException
	 */
	public void retirerUnePublicationGroupe(Groupe groupe, Publication publication) throws RemoteException;
	/**
	 * Creer un groupe 
	 * @param nomGroupe :  le nom du groupe à creer 
	 * @param utilisateur : l'utilisateur propriétaire
	 * @throws RemoteException
	 */
	public void creerUnGroupe(String nomGroupe, String utilisateur)throws RemoteException;
	/**
	 * Ajoute un utilisateur dans un groupe
	 * @param idGroupe : le groupe concerné
	 * @param utilisateur : l'utilisateur à ajouter
	 * @throws RemoteException
	 */
	public void ajouterUnUtlisateurDansGroupe(int idGroupe, Utilisateur utilisateur)throws RemoteException;
	/**
	 * Supprime un utilisateur du groupe
	 * @param idGroupe : le groupe concerné
	 * @param utilisateur : l'utilisateur à supprimer
	 * @throws RemoteException
	 */
	public void supprimerUnUtilsateurDuGroupe(int idGroupe, Utilisateur utilisateur)throws RemoteException;
	/**
	 * Supprime un groupe
	 * @param idGroupe : le groupe à supprimer
	 * @throws RemoteException
	 */
	public void supprimerGroupe(int idGroupe)throws RemoteException;
	/**
	 * Retourne la liste des groupes
	 * @return ArrayList<Groupe> liste des groupes 
	 * @throws RemoteException
	 */
	public ArrayList<Groupe> getGroupes()throws RemoteException;
	/**
	 * Retourne la liste des groupes auxquelle l'utilisateur est propriétaire
	 * @param utilisateur : l'utilisateur concerné 
	 * @return ArrayList<Groupe> liste des groupes dont un utilisateur est propriétaire
	 * @throws RemoteException
	 */
	public ArrayList<Groupe> getGroupesProp(Utilisateur utilisateur) throws RemoteException;
	/**
	 * Retourne la liste des groupes aux quelles un utilisateur appartients
	 * @param utilisateur
	 * @return ArrayList<Groupe> liste des groupes qui appartiennent à un utilisateur
	 * @throws RemoteException
	 */
	public ArrayList<Groupe> getGroupes(Utilisateur utilisateur) throws RemoteException;
	/**
	 * Retour un Objet groupe pour un idgroupe
	 * @param idGroupe : le groupe à chercher
	 * @return Retour un Objet groupe pour un idgroupe
	 * @throws RemoteException
	 */
	public Groupe getGroupe(int idGroupe) throws RemoteException;
}	
	