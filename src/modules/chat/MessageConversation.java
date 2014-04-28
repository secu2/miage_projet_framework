package modules.chat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import modules.gestionUtilisateur.Groupe;
import modules.gestionUtilisateur.Utilisateur;

public class MessageConversation extends Message implements Serializable{

	private static final long serialVersionUID = 1L;
	private ArrayList<Utilisateur> participants;
	private ArrayList<Groupe> groupesParticipants;
	private int idConversation;
	
	public MessageConversation(int idConversation, String message, String expeditaire , ArrayList<Utilisateur> participants , ArrayList<Groupe> groupesParticipants) {
		super(message, expeditaire);
		this.participants = participants;
		this.groupesParticipants = groupesParticipants;
		this.idConversation = idConversation;
	}

	/**
	 * Retourne la date d'emission du message
	 * @return
	 */
	public Date getDateEmission(){
		return super.getDateEmission();
	}
	
	/**
	 * Retourne l'expeditaire du message
	 * @return expeditaire
	 */
	public String getExpeditaire(){
		return super.getExpeditaire();
	}
	
	/**
	 * Renvoie le contenu du message
	 * @return message
	 */
	public String getMessage(){
		return super.getMessage();
	}
	

	/**
	 * Renvoie la liste des utilisateurs participants
	 * @return participants
	 */
	public ArrayList<Utilisateur> getParticipants() {
		return participants;
	}

	/**
	 * Renvoie la liste des groupes participants
	 * @return groupesParticipants
	 */
	public ArrayList<Groupe> getGroupesParticipants() {
		return groupesParticipants;
	}
	
	/**
	 * Renvoie l'id de la conversation du message
	 * @return idConversation
	 */
	public int getIdConversation(){
		return idConversation;
	}
	
	

}
