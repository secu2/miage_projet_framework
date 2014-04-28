package modules.chat;

import java.util.ArrayList;

import modules.gestionUtilisateur.Utilisateur;

public class Conversation {
	ArrayList<Message> listeMessages;
	ArrayList<Utilisateur> participants;
	
	/**
	 * Créer une conversation avec une liste de message
	 * @param listeMessages
	 */
	public Conversation (ArrayList<Message> listeMessages){
		this.listeMessages = listeMessages;
	}
	
	/**
	 * Renvoie la liste des messages de la conversation
	 * @return listeMessages
	 */
	public ArrayList<Message> getListeMessages(){
		return this.listeMessages;
	}
}
