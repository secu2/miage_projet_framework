package modules.chat;

import java.io.Serializable;
import java.util.Date;


public class Message implements Serializable{
	
	private String message;
	private String expeditaire; 
	private Date dateEmission;
	
	/**
	 * Construit un message avec le contenu du message, l'expeditaire ainsi que sa date d'emission
	 * @param message
	 * @param expeditaire
	 */
	public Message(String message, String expeditaire){
		this.dateEmission = new Date();
		this.message = message;
		this.expeditaire = expeditaire;
	}
	
	/**
	 * Retourne la date d'emission du message
	 * @return
	 */
	public Date getDateEmission(){
		return this.dateEmission;
	}
	
	/**
	 * Retourne l'expeditaire du message
	 * @return expeditaire
	 */
	public String getExpeditaire(){
		return expeditaire;
	}
	/**
	 * Renvoie le contenu du message
	 * @return message
	 */
	public String getMessage(){
		return this.message;
	}

	

}
