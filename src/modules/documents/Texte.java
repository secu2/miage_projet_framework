package modules.documents;

import java.io.File;

import modules.documents.social.Statistique;

/**
 * Texte.java
 * @author never
 *
 */
public class Texte extends Document {

	/**
	 * Constructeur de la classe Texte : construit un objet Texte
	 * @param nom
	 * @param taille
	 * @param format
	 * @param statistique
	 * @param emplacement
	 */
	public Texte(String nom, float taille, String format,
			Statistique statistique, String emplacement) {
		super(nom, taille, format, statistique, emplacement);
	}
	
	/**
	 * Constructeur de la classe Texte : construit un objet Texte sans Statistique
	 * @param nom
	 * @param taille
	 * @param format
	 * @param statistique
	 * @param emplacement
	 */
	public Texte(String nom, float taille, String format,
			String emplacement) {
		super(nom, taille, format, emplacement);
	}
	
	
}
