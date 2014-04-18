package modules.documents.social;
import java.util.ArrayList;
import java.util.Date;

import modules.documents.Document;
import modules.gestionUtilisateur.Groupe;
import modules.gestionUtilisateur.Utilisateur;

/**
 * Publication
 * @author never
 *
 */
public class Publication {
    
    private Date dateDePublication;
    private int dureeDeDisponibilite;
    // Liste des utilisateurs ayant accès à la publication
    private ArrayList<Utilisateur> visibiliteUtilisateur; 
    // Liste des groupes ayant accès à la publication
    private ArrayList<Groupe> visibiliteGroupe;
    private Utilisateur proprietaire;
    private Document document;
    
    /**
     * Constructeur de la classe Publication : construit un objet Publication
     * @param dateDePublication
     * @param dureeDeDisponibilite
     * @param visibiliteUtilisateur
     * @param visibiliteGroupe
     * @param proprietaire
     * @param document
     */
	public Publication(Date dateDePublication, int dureeDeDisponibilite,
			ArrayList<Utilisateur> visibiliteUtilisateur,
			ArrayList<Groupe> visibiliteGroupe, Utilisateur proprietaire,
			Document document) {
		this.dateDePublication = dateDePublication;
		this.dureeDeDisponibilite = dureeDeDisponibilite;
		this.visibiliteUtilisateur = visibiliteUtilisateur;
		this.visibiliteGroupe = visibiliteGroupe;
		this.proprietaire = proprietaire;
		this.document = document;
	}

	/**
	 * Renvoie la date de publication de la publication 
	 * @return dateDePublication
	 */
	public Date getDateDePublication() {
		return dateDePublication;
	}

	/**
	 * Affecte une date à la publication
	 * @param dateDePublication
	 */
	public void setDateDePublication(Date dateDePublication) {
		this.dateDePublication = dateDePublication;
	}

	/**
	 * Renvoie la durée de disponibilité de la publication
	 * @return dureeDeDisponibilite
	 */
	public int getDureeDeDisponibilite() {
		return dureeDeDisponibilite;
	}

	/**
	 * Affecte la durée de disponibilité de la publication
	 * @param dureeDeDisponibilite
	 */
	public void setDureeDeDisponibilite(int dureeDeDisponibilite) {
		this.dureeDeDisponibilite = dureeDeDisponibilite;
	}

	/**
	 * Renvoie la liste des utilisateurs ayant accès à la publication
	 * @return visibiliteUtilisateur
	 */
	public ArrayList<Utilisateur> getVisibiliteUtilisateur() {
		return visibiliteUtilisateur;
	}

	/**
	 * Affecte une liste d'utilisateurs qui auront accès à la publication
	 * @param visibiliteUtilisateur
	 */
	public void setVisibiliteUtilisateur(
			ArrayList<Utilisateur> visibiliteUtilisateur) {
		this.visibiliteUtilisateur = visibiliteUtilisateur;
	}

	
	/**
	 * Renvoie la liste des groupes ayant accès à la publication
	 * @return visibiliteGroupe
	 */
	public ArrayList<Groupe> getVisibiliteGroupe() {
		return visibiliteGroupe;
	}

	/**
	 * Affecte une liste de groupes qui auront accès à la publication
	 * @param visibiliteGroupe
	 */
	public void setVisibiliteGroupe(ArrayList<Groupe> visibiliteGroupe) {
		this.visibiliteGroupe = visibiliteGroupe;
	}

	/**
	 * Renvoie le propriétaire de la publication
	 * @return proprietaire
	 */
	public Utilisateur getProprietaire() {
		return proprietaire;
	}

	/**
	 * Affecte le propriétaire de la publication
	 * @param proprietaire
	 */
	public void setProprietaire(Utilisateur proprietaire) {
		this.proprietaire = proprietaire;
	}

	/**
	 * Renvoie le document publié
	 * @return document
	 */
	public Document getDocument() {
		return document;
	}

	/**
	 * Affecte le document à la publication
	 * @param document
	 */
	public void setDocument(Document document) {
		this.document = document;
	}
	
	
	
    
    
    
    
    

}
