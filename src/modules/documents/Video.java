package modules.documents;

import java.io.File;

import modules.documents.social.Statistique;

/**
 * Video.java
 * @author never
 *
 */
public class Video extends Document {
	

	private String encodage;
	private String tailleAffichage;
	private String formatAffichage;
	
	/**
	 * Constructeur de la classe Vidéo : construit un objet Video sans statistique
	 * @param nom
	 * @param taille
	 * @param format
	 * @param statistique
	 * @param emplacement
	 * @param encodage
	 * @param tailleAffichage
	 * @param formatAffichage
	 */
	public Video(String nom, float taille, String format,
			String emplacement, String encodage,
			String tailleAffichage, String formatAffichage) {
		super(nom, taille, format, emplacement);
		this.encodage = encodage;
		this.tailleAffichage = tailleAffichage;
		this.formatAffichage = formatAffichage;
	}
	
	/**
	 * Constructeur de la classe Vidéo : construit un objet Video
	 * @param nom
	 * @param taille
	 * @param format
	 * @param statistique
	 * @param emplacement
	 * @param encodage
	 * @param tailleAffichage
	 * @param formatAffichage
	 */
	public Video(String nom, float taille, String format,
			Statistique statistique, String emplacement, String encodage,
			String tailleAffichage, String formatAffichage) {
		super(nom, taille, format, statistique, emplacement);
		this.encodage = encodage;
		this.tailleAffichage = tailleAffichage;
		this.formatAffichage = formatAffichage;
	}

	/**
	 * Renvoie la méthode d'encodage de la vidéo
	 * @return encodage
	 */
	public String getEncodage() {
		return encodage;
	}

	/**
	 * Affecte l'encodage de la vidéo
	 * @param encodage
	 */
	public void setEncodage(String encodage) {
		this.encodage = encodage;
	}

	/**
	 * Renvoie la taille d'affichage de la vidéo
	 * @return tailleAffichage
	 */
	public String getTailleAffichage() {
		return tailleAffichage;
	}

	/**
	 * Affecte la taille d'affichage de la vidéo
	 * @param tailleAffichage
	 */
	public void setTailleAffichage(String tailleAffichage) {
		this.tailleAffichage = tailleAffichage;
	}

	/**
	 * Renvoie le format d'affichage de la vidéo
	 * @return formatAffichage
	 */
	public String getFormatAffichage() {
		return formatAffichage;
	}

	/**
	 * Affecte le format d'affichage de la vidéo
	 * @param formatAffichage
	 */
	public void setFormatAffichage(String formatAffichage) {
		this.formatAffichage = formatAffichage;
	}
	
	

	
	
	
}
