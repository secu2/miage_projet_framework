package modules.documents.social;

import modules.gestionUtilisateur.Utilisateur;

/**
 * Commentaire.java
 * @author never
 *
 */
public class Commentaire {
   
	private Utilisateur auteur;
    private String commentaire;
    
    /**
     * Construire un nouveau commentaire
     * @param auteur : l'auteur du commentaire
     * @param commentaire : le commentaire
     */
    public Commentaire(Utilisateur auteur, String commentaire){
    	this.auteur = auteur;
    	this.commentaire = commentaire;
    }
    
    /**
     * Permet de modifier le commentaire initial
     * @param nouveauCommentaire : Le nouveau commentaire, remplacera l'original
     */
    public void modifierCommentaire(String nouveauCommentaire){
    	this.commentaire = nouveauCommentaire;
    }

    /**
     * Permet de récupérer l'auteur du commentaire
     * @return l'auteur du commentaire
     */
	public Utilisateur getAuteur() {
		return auteur;
	}

	/**
	 * Permet de récupérer le commentaire
	 * @return le commentaire
	 */
	public String getCommentaire() {
		return commentaire;
	}
    
    

}
