package modules.documents;

import java.io.File;

import modules.documents.social.Statistique;

/**
 * Document.java
 * @author never
 *
 */
public class Document {
	// D�finition et Gestion des documents pr�sents sur le drive. Son nom, son
	// propri�taire, son type, sa d�finition, sa description sa date
	// d�expiration, et sa visibilit� (par d�faut visible seulement par lui)

	private String nom;
	private float taille;
	private String format;
	private Statistique statistique; //Les statistiques du fichier
	private File emplacement;
	
	/**
	 * Constructeur de la classe Document 
	 * @param nom
	 * @param taille
	 * @param format
	 * @param statistique
	 * @param emplacement
	 */
	public Document(String nom, float taille, String format, Statistique statistique, File emplacement) {
		this.nom = nom;
		this.taille = taille;
		this.format = format;
		this.statistique = statistique;
		this.emplacement = emplacement;
	}
	
	

}
