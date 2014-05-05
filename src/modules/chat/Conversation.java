package modules.chat;

import java.io.Serializable;
import java.util.ArrayList;

import modules.gestionUtilisateur.Groupe;
import modules.gestionUtilisateur.Utilisateur;

public class Conversation implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idConversation;
	private ArrayList<Message> listeMessages;
	private ArrayList<Utilisateur> participants;
	private ArrayList<Groupe> groupesParticipants;

	/**
	 * Créer une conversation avec une liste de message
	 * @param listeMessages
	 */
	public Conversation (int idConversation,ArrayList<Utilisateur> participants,ArrayList<Groupe> groupesParticipants) {
		this.listeMessages = new ArrayList<Message>();
		this.participants = participants;
		this.groupesParticipants = groupesParticipants;
		this.idConversation = idConversation;
	}
	
	/**
	 * Renvoie la liste des messages de la conversation
	 * @return listeMessages
	 */
	public ArrayList<Message> getListeMessages(){
		return this.listeMessages;
	}
	
	/**
	 * Renvoie la liste des messages de la conversation
	 * @return listeMessages
	 */
	public ArrayList<Utilisateur> getListeParticipants(){
		return this.participants;
	}
	
	/**
	 * Renvoie la liste des messages de la conversation
	 * @return listeMessages
	 */
	public ArrayList<Groupe> getListeGroupesParticipants(){
		return this.groupesParticipants;
	}
	
	/**
	 * Renvoie l'id de la conversation
	 * @return idConversation
	 */
	public int getIdConversation(){
		return idConversation;
	}
	
	
}
