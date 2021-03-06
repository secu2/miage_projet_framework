package modules.documents;

import java.io.File;
import java.io.Serializable;

import modules.documents.social.Statistique;

/**
 * Document.java
 * 
 * @author never
 * 
 */
public class Document implements Serializable {
	// D�finition et Gestion des documents pr�sents sur le drive. Son nom, son
	// propri�taire, son type, sa d�finition, sa description sa date
	// d�expiration, et sa visibilit� (par d�faut visible seulement par lui)

	private String nom;
	private float taille;
	private Statistique statistique; // Les statistiques du fichier
	private String emplacement;
	private File fichier;

	/**
	 * Constructeur de la classe Document
	 * 
	 * @param nom
	 * @param taille
	 * @param format
	 * @param statistique
	 * @param emplacement
	 */
	public Document(String nom, Statistique statistique) {
		this.nom = nom;
		fichier = new File(nom);
		this.taille = fichier.length();
		this.statistique = statistique;
		this.emplacement = fichier.getPath().replaceAll("\\\\", "/");
	}

	/**
	 * Constructeur de la classe Document sans Statistique
	 * 
	 * @param nom
	 * @param taille
	 * @param format
	 * @param emplacement
	 */
	public Document(String nom) {
		this.nom = nom;
		fichier = new File(nom);
		this.taille = fichier.length();
		this.emplacement = fichier.getPath().replaceAll("\\\\", "/");
	}

	/**
	 * Renvoie le nom du document
	 * 
	 * @return nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Affecte un nouveau au document
	 * 
	 * @param nom
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Renvoie la taille du document
	 * 
	 * @return taille
	 */
	public float getTaille() {
		return taille;
	}

	/**
	 * Affecte une taille au document
	 * 
	 * @param taille
	 */
	public void setTaille(float taille) {
		this.taille = taille;
	}

	/**
	 * Renvoie l'objet Statistique du document
	 * 
	 * @return statistique
	 */
	public Statistique getStatistique() {
		return statistique;
	}

	/**
	 * Affecte un objet Statistique au document
	 * 
	 * @param statistique
	 */
	public void setStatistique(Statistique statistique) {
		this.statistique = statistique;
	}

	/**
	 * Renvoie l'emplacement du document
	 * 
	 * @return emplacement
	 */
	public String getEmplacement() {
		return emplacement;
	}

	/**
	 * Affecte l'emplacement du document
	 * 
	 * @param emplacement
	 */
	public void setEmplacement(String emplacement) {
		this.emplacement = emplacement;
	}

	public File getFichier() {
		return fichier;
	}

	public void setFichier(File fichier) {
		this.fichier = fichier;
	}

}
