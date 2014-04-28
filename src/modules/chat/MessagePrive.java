package modules.chat;

import java.io.Serializable;
import java.util.Date;

import systeme.rmi.ClientRMI;

public class MessagePrive extends Message implements Serializable {

	private String destinataire;
	
	/**
	 * Créer un message privé avec le contenu du message, l'expeditaire,  le destinataire ainsi que la date d'emission du message
	 * @param message
	 * @param expeditaire
	 * @param destinataire
	 */
	public MessagePrive(String message, String expeditaire , String destinataire) {
		super(message, expeditaire);
		this.destinataire = destinataire;
	}
	
	/**
	 * Renvoie le destinataire du message privé
	 * @return
	 */
	public String getDestinataire(){
		return this.destinataire;
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

	
}
