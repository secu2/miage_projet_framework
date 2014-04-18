package modules.documents;


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
	private String emplacement;
	
	/**
	 * Constructeur de la classe Document 
	 * @param nom
	 * @param taille
	 * @param format
	 * @param statistique
	 * @param emplacement
	 */
	public Document(String nom, float taille, String format, Statistique statistique, String emplacement) {
		this.nom = nom;
		this.taille = taille;
		this.format = format;
		this.statistique = statistique;
		this.emplacement = emplacement;
	}
	
	/**
	 * Constructeur de la classe Document sans Statistique
	 * @param nom
	 * @param taille
	 * @param format
	 * @param emplacement
	 */
	public Document(String nom, float taille, String format,String emplacement) {
		this.nom = nom;
		this.taille = taille;
		this.format = format;
		this.emplacement = emplacement;
	}

	/**
	 * Renvoie le nom du document
	 * @return nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Affecte un nouveau au document
	 * @param nom
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Renvoie la taille du document
	 * @return taille
	 */
	public float getTaille() {
		return taille;
	}

	/**
	 * Affecte une taille au document
	 * @param taille
	 */
	public void setTaille(float taille) {
		this.taille = taille;
	}

	/**
	 * Renvoie le format du document
	 * @return format
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * Affecte un format au document
	 * @param format
	 */
	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * Renvoie l'objet Statistique du document
	 * @return statistique
	 */
	public Statistique getStatistique() {
		return statistique;
	}

	/**
	 * Affecte un objet Statistique au document
	 * @param statistique
	 */
	public void setStatistique(Statistique statistique) {
		this.statistique = statistique;
	}

	/**
	 * Renvoie l'emplacement du document
	 * @return emplacement
	 */
	public String getEmplacement() {
		return emplacement;
	}

	/**
	 * Affecte l'emplacement du document
	 * @param emplacement
	 */
	public void setEmplacement(String emplacement) {
		this.emplacement = emplacement;
	}
	
	
	
	

}
