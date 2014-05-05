package systeme.rmi;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
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
	/**
	 * Renvoie la liste des clients connectés
	 * @return utilisateursConnectes
	 */
	public ArrayList<Client> getClientsconnectes() throws RemoteException;
	/**
	 * Ajoute un client à la liste des clients du serveur
	 * @param c : client
	 */
	public void ajouterClient(Client c) throws RemoteException;
	/**
	 * Realise une connexion entre un client et un serveur
	 * @param utilisateur : l'utilisateur à connecter
	 * @return true (Si connexion établie)
	 */
	public boolean connexion(String login, String motDepasse, Client c) throws RemoteException;
	/**
	 * Deconnecte un client du serveur
	 * @param c : le client a deconnecter
	 * @throws RemoteException
	 */
	public void deconnexion(Client c) throws RemoteException;
	/**
	 * Ajoute une publication à la liste des publications du serveur
	 * @param publication
	 */
	public void ajouterPublication(Publication publication)throws RemoteException;
	/**
	 * Ajoute un message privé à la liste des messages privés de l'utilisateur
	 * 
	 * @param login
	 * @param message
	 */
	public void ajouterMessagePrive(String login, MessagePrive message) throws RemoteException;
	/**
	 * Renvoie le treemap des messages privés de l'utilisateur
	 * @param login
	 * @return la treemap des messages privés de l'utilisateur si elle existe,
	 *         null sinon
	 */
	public TreeMap<String, ArrayList<MessagePrive>> getMapMessagesPrivesUtilisateur(String login)throws RemoteException;
	/**
	 * Renvoie la liste des messages privés entre l'expeditaire et le
	 * destinataire
	 * @param expeditaire
	 * @param destinataire
	 * @return la liste des messages privés entre l'expeditaire et le
	 *         destinataire si elle existe, null sinon
	 */
	public ArrayList<MessagePrive> getListeMessagesPrives(String expeditaire, String destinataire) throws RemoteException;
	/**
	 * Renvoie la treemap des messages privés des utilisateurs
	 * @return messagesPrivesUtilisateurs
	 */
	public TreeMap<String,TreeMap<String, ArrayList<MessagePrive>>> getMessagesPrivesUtilisateurs() throws RemoteException;
	/**
	 * Renvoie la treemap des conversations des utilisateurs qui ont eu lieu
	 * lors de leur absence 
	 * @return conversationsUtilisateursAbsent
	 */
	public TreeMap<String, ArrayList<Conversation>> getConversationsUtilisateursAbsents() throws RemoteException;
	/**
	 * Renvoie la treemap de conversations
	 * 
	 * @return conversations
	 */
	public TreeMap<Integer, Conversation> getConversations() throws RemoteException;
	/**
	 * Retourne la conversation
	 * 
	 * @param idConversation
	 * @return la conversation si il existe , null sinon
	 */
	public Conversation getConversation(int idConversation) throws RemoteException;

	/**
	 * Renvoie la liste des conversations d'un utilisateur
	 * @param login
	 * @return liste des conversations de l'utilisateur si elle existe, null sinon
	 */
	
	public ArrayList<Conversation>  getConversationsUtilisateurAbsent(String login) throws RemoteException;	
	/**
	 * Renvoie la conversation où l'utilisateur était absent
	 * @param login
	 * @param idConversation
	 * @return la conversation si il existe , null sinon
	 */
	public Conversation getConversation(String login, int idConversation) throws RemoteException;
	/**
	 * Ajoute un message à une conversation donnée pour un utilisateur donné
	 * @param message
	 * @param idConversation
	 * @param login
	 */
	public void ajouterMessageConversation(Message message, int idConversation , String login) throws RemoteException;
	/**
	 * Distribue le message d'une conversation à tous les participants
	 * @param message
	 */
	public void distribuerMessageConversation(MessageConversation message) throws RemoteException;
	/**
	 * Créer une conversation entre un ensemble d'utilisateurs et un ensemble de
	 * groupes
	 * 
	 * @param utilisateurs
	 * @param groupes
	 * @return idConversation vaut le numéro de la conversation si conversation
	 *         créée ou -1 sinon
	 */
	public int creerUneConversation(ArrayList<Utilisateur> utilisateurs, ArrayList<Groupe> groupes) throws RemoteException;
	/**
	 * Distribue le message privé de l'expediteur au destinataire
	 * 
	 * @param message
	 * @param expediteur
	 * @param destinataire
	 * @require : destinataire inscrit sur le serveur
	 * @require : expediteur inscrit et connecté sur le serveur
	 */
	public void distribuerMessagePrive(MessagePrive message) throws RemoteException;
	/**
	 * Distribue un message envoyé par un client à tous les clients connectés
	 * @param message
	 */
	public void distribuerMessage(Message message) throws RemoteException;
	
	/**
	 * Affiche l'ensemble des publications visibles pour un utilisateur
	 * @param utilisateur         : l'utilisateur a verifier
	 * @return ArrayList<Publication> : return publication visibles
	 */
	public ArrayList<Publication> getPublicationsVisibles(Utilisateur utilisateur) throws RemoteException;
	/**
	 * Renvoie l'utilisateur inscrit à partir de son login
	 * @param login : login de l'utilisateur
	 * @return utilisateur si existant , null sinon
	 */
	public Utilisateur getUtilisateurInscrit(String login) throws RemoteException;
	/**
	 * Renvoie l'utilisateur du clientRMI si trouvé dans la liste des clients
	 * connectés
	 * 
	 * @param cl
	 * @return l'utilisateur si il est connecté , null sinon
	 */
	public Utilisateur utilisateurConnecte(Client clientRMI) throws RemoteException;
	/**
	 * Renvoie la liste des utilisateurs inscrits
	 * @return utilisateursInscrits
	 */
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
	
	/**
	 * Verification de l'existance de l'utilisateur et inscription d'un
	 * utilisateur dont le mot de passe a déjà été encrypté côté client si
	 * l'utilisateur n'existe pas
	 * @param login
	 * @param motDePasse
	 * @return false si le login de l'utilisateur est déjà existant, true sinon
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public boolean inscription(String login, String motDePasse) throws NoSuchAlgorithmException, UnsupportedEncodingException,
			RemoteException;
	/**
	 * Verification de l'existance de l'utilisateur et inscription d'un
	 * utilisateur dont le mot de passe a déjà été encrypté côté client si
	 * l'utilisateur n'existe pas , on crée un repertoire pour l'utilisateur sur le
	 * serveur
	 * @param login
	 * @param motDePasse
	 * @return false si le login de l'utilisateur est déjà existant, true sinon
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public boolean inscriptionAvecRepertoireUtilisateur(String login,String motDePasse) throws RemoteException;
	/**
	 * Verification de l'existance de l'utilisateur et inscription d'un
	 * utilisateur dont le mot de passe a déjà été encrypté côté client si
	 * l'utilisateur n'existe pas
	 * 
	 * @param login
	 * @param motDePasse
	 * @return false si le login de l'utilisateur est déjà existant, true sinon
	 */
	public boolean inscriptionSecurisee(String login, String motDePasse) throws RemoteException;
	
	public void actualiserListesUtilisateurs() throws RemoteException;
	
	/**
	 * Permet de tester si l'utilisateur existe (est inscrit)
	 * @param login
	 *            : login de l'utilisateur
	 * @return true si l'utilisateur existe , false sinon
	 */
	public boolean utilisateurExistant(String login) throws RemoteException;

	/**
	 * Récupère un client connecté à partir de son login
	 * @param login
	 * @return le client si il est connecté , null sinon
	 * @require : login existant
	 */
	public Client getClientConnecte(String login) throws RemoteException;
	
	/**
	 * Supprime le clientRMI de la liste des clients connectés
	 * @param client
	 */
	public void supprimerUnClient(Client client) throws RemoteException;
	
	/**
	 * Renvoie l'index du client dans la liste des clients connectés
	 * 
	 * @param client
	 * @return si trouvé num = index du client connecté , -1 sinon
	 */
	public int indexClient(Client client) throws RemoteException;
	
	/**
	 * Créer un repertoire pour l'utilisateur
	 * @param u : utilisateur
	 * @param path  : chemin avant le nom d'utilisateur (Ex : /utilisateurs/ )
	 */
	public void creerUnRepertoireUtilisateur(Utilisateur u, String path)
			throws RemoteException;
	
	/**
	 * Créer un repertoire
	 * @param path : chemin du futur repertoire
	 */
	public void creerUnRepertoire(String path) throws RemoteException;
	

	/**
	 * Affecte l'ensemble des utilisateurs inscrits au serveur
	 * @throws RemoteException
	 */
	public void setUtilisateursInscrits(ArrayList<Utilisateur> utilisateursInscrits) throws RemoteException;
	
}	

	