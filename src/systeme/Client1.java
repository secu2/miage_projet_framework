package systeme;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

import jus.util.assertion.Require;
import modules.chat.Conversation;
import modules.chat.Message;
import modules.chat.MessageConversation;
import modules.chat.MessagePrive;
import modules.documents.Document;
import modules.documents.social.Publication;
import modules.gestionUtilisateur.Groupe;
import modules.gestionUtilisateur.Utilisateur;

public class Client1 {

	private Client client;
	
	/**
	 * Créer un client
	 * @param u
	 * @param motDePasse
	 */
	public Client1(Utilisateur u,String motDePasse){
		client = new Client(u.getLogin(),motDePasse);
	}
	
	/**
	 * Renvoie le client RMI
	 * @return client
	 */
	public Client getClientRMI(){
		return client;
	}
	
	/**
	 * Methode qui permet de telecharger un fichier depuis le serveur
	 * 
	 * @param server
	 *            : le serveurRMI
	 * @param source
	 *            : le fichier a telecharger
	 * @param destination
	 *            : le fichier de sortie
	 * @throws IOException
	 */

	public void telecharger(File source,
			File destination) throws IOException {
		
		getClientRMI().telecharger(source, destination);
	}

	/**
	 * Methode qui permet de charger un fichier sur le serveur
	 * 
	 * @param server
	 *            : le serveurRMI
	 * @param source
	 *            : le fichier a charger 2 * @param destination : le fichier de
	 *            sortie
	 * @throws IOException
	 */

	public void charger(File source,ArrayList<Utilisateur> utilisateurs, ArrayList<Groupe> groupes, Document document, Date dateFinPublication)
			throws IOException {
		getClientRMI().charger(source, utilisateurs, groupes, document, dateFinPublication);
	}



	
	public ArrayList<Utilisateur> getUtilisateurs()
	{
		return getClientRMI().getUtilisateurs();
	}
	
	/**
	 * @return Utilisateur : l'utilisateur associer à ce client 
	 */
	
	public Utilisateur getUtilisateur(){
		return getClientRMI().getUtilisateur();
	}
	
	/**
	 * Deconnecte le client
	 */
	
	public void deconnexion()
	{
		getClientRMI().deconnexion();
	}
	
	
	
	/**
	 * retourne l'ensemble des publications visibles pour un utilisateur
	 * @return ArrayList<Publication> : return publication visibles
	 */
	public ArrayList<Publication> getPublicationsVisibles()
	{
		return getClientRMI().getPublicationsVisibles();
	}
	/**
	 * Renvoie la liste des publications d'un utilisateurs
	 * @return ArrayList<Publication> : les publications de l'utilisateur
	 */
	public ArrayList<Publication> getPublications()
	{
		return getClientRMI().getPublications();
	}
	
	public void supprimerUnePublication(Publication publication)
	{
		getClientRMI().supprimerUnePublication(publication);
	}
	/**
	 * Rend visible une publication a un utilisateur
	 * @param utilisateur : l'utilisateur à autoriser 
	 * @param publicaion : la publication concernée
	 * Require seul le prorietaire de la publication est autorise a l'utiliser
	 */
	public void autoriserPublicationUtilisateur(Publication publication, Utilisateur utilisateur)
	{
		getClientRMI().autoriserPublicationUtilisateur(publication, utilisateur);
	}
	/**
	 * Retire la visibilite d'une personnes
	 * @param utilisateur : l'utilisateur à retirer
	 * @param publication : la publication concernée
	 * Require seul le prorietaire de la publication est autorise a l'utiliser
	 */
	public void retirerUnePublicationutilisateur(Publication publication, Utilisateur utilisateur)
	{
		getClientRMI().retirerUnePublicationutilisateur(publication, utilisateur);
	}
	/**
	 * Rend visible une publication a un groupe
	 * @param groupe : le groupe à autorisier
	 * @param publication : la publication concernée
	 */
	public void autoriserUnePublicationGroupe(Publication publication, Groupe groupe)
	{
		getClientRMI().autoriserUnePublicationGroupe(publication, groupe);
	}
	
	/**
	 * Retire la visibilite d'un groupe
	 * @param groupe : le groupe à retirer
	 * @param publication : la publication concernée
	 */
	public void retirerUnePublicationGroupe(Publication publication, Groupe groupe)
	{
		getClientRMI().retirerUnePublicationGroupe(publication, groupe);
	}
	
	/**
	 * Creer un groupe 
	 * @param nomGroupe :  le nom du groupe à creer 
	 */
	public void creerUnGroupe(String nomGroupe)
	{
		getClientRMI().creerUnGroupe(nomGroupe);
	}
	
	/**
	 * Ajoute un utilisateur dans un groupe
	 * @param idGroupe : le groupe concerné
	 * @param utilisateur : l'utilisateur à ajouter
	 * @throws RemoteException
	 */
	public void ajouterUnUtlisateurDansGroupe(int idGroupe, Utilisateur utilisateur)
	{
		getClientRMI().ajouterUnUtlisateurDansGroupe(idGroupe, utilisateur);
	}
	
	/**
	 * Supprime un utilisateur du groupe
	 * @param idGroupe : le groupe concerné
	 * @param utilisateur : l'utilisateur à supprimer
	 * @throws RemoteException
	 */
	public void supprimerUnUtilsateurDuGroupe(int idGroupe, Utilisateur utilisateur)
	{
		getClientRMI().supprimerUnUtilsateurDuGroupe(idGroupe, utilisateur);
	}
	
	/**
	 * Supprime un groupe
	 * @param idGroupe : le groupe à supprimer
	 * @throws RemoteException
	 */
	public void supprimerGroupe(int idGroupe)
	{
		getClientRMI().supprimerGroupe(idGroupe);
	}
	
	/**
	 * Retourne la liste des groupes auxquelle l'utilisateur est propriétaire
	 * @return ArrayList<Groupe> liste des groupes dont un utilisateur est propriétaire
	 */
	public ArrayList<Groupe> getGroupesProp() 
	{
		return getClientRMI().getGroupesProp();
	}
	
	/**
	 * Retourne la liste des groupes aux quelles un utilisateur appartients
	 * @return ArrayList<Groupe> liste des groupes qui appartiennent à un utilisateur
	 */
	public ArrayList<Groupe> getGroupes()
	{
		return getClientRMI().getGroupes();
	}
	
	public boolean estProprietaire(int idGroupe)
	{
		return getClientRMI().estProprietaire(idGroupe);
	}
	
	/**
	 * true si l'objet courant est proprietaire d'une publication
	 * @param publication
	 * @return true si l'objet courant est proprietaire d'une publication
	 */
	public boolean estProprietaire(Publication publication)
	{
		return getClientRMI().estProprietaire(publication);
	}
	
	/**
	 * Envoie un message 
	 * @param message
	 */
	public void envoyerMessagePrive(MessagePrive message){

		getClientRMI().envoyerMessagePrive(message);
	}
	
	/**
	 * Envoie un message qui concerne une conversation
	 * @param message
	 */
	public void envoyerMessageConversation(MessageConversation message){
		getClientRMI().envoyerMessageConversation(message);
	}
	
	/**
	 * Envoie un message 
	 * @param message
	 */
	public void envoyerMessage(Message message){

		getClientRMI().envoyerMessage(message);
	}
	
	/**
	 * Reception d'un message
	 * @param message
	 * @param expediteur
	 */
	
	public void recevoirMessage(Message message){
		getClientRMI().recevoirMessage(message);
	}
	
	/**
	 * Reçois les messages privés reçus durant l'absence
	 */
	public void recevoirMessagePriveAbsence(){
		getClientRMI().recevoirMessagePriveAbsence();
	}
	
	/**
	 * Reçois les messages des conversations reçus durant son absence
	 */
	public void recevoirMessageConversationAbsence(){
		getClientRMI().recevoirMessageConversationAbsence();
	}
	
	/**
	 * Créer une conversation
	 * @param utilisateurs
	 * @param groupes
	 * @return l'id de la conversation si crée, -1 sinon
	 */
	public int creerUneConversation(ArrayList<Utilisateur> utilisateurs, ArrayList<Groupe> groupes){
		return getClientRMI().creerUneConversation(utilisateurs, groupes);
	}

	
	
	
	
}
