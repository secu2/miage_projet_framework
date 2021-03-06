package modules.documents.social;
import java.io.Serializable;
import java.rmi.RemoteException;
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
public class Publication implements Serializable {
    
    private Date dateDePublication;
    private Date dateFinPublication;
    // Liste des utilisateurs ayant accès à la publication
    private ArrayList<Utilisateur> visibiliteUtilisateur; 
    // Liste des groupes ayant accès à la publication
    private ArrayList<Groupe> visibiliteGroupe;
    private Utilisateur proprietaire;
    private Document document;
    private ArrayList<Commentaire> commentaire;
    
    /**
     * Constructeur de la classe Publication : construit un objet Publication
     * @param dateDePublication
     * @param dureeDeDisponibilite
     * @param visibiliteUtilisateur
     * @param visibiliteGroupe
     * @param proprietaire
     * @param document
     */
	public Publication(Date  dateDePublication, Date dateFinPublication,
			ArrayList<Utilisateur> visibiliteUtilisateur,
			ArrayList<Groupe> visibiliteGroupe, Utilisateur proprietaire,
			Document document) {
		this.dateDePublication = dateDePublication;
		this.dateFinPublication = dateFinPublication;
		if (visibiliteUtilisateur== null)
		{
			this.visibiliteUtilisateur= new ArrayList<Utilisateur>();
		}
		else
		{
			this.visibiliteUtilisateur = visibiliteUtilisateur;
		}
		if(visibiliteGroupe==null)
		{
			this.visibiliteGroupe= new ArrayList<Groupe>();
		}
		else
		{
			this.visibiliteGroupe = visibiliteGroupe;
		}
		this.proprietaire = proprietaire;
		this.document = document;
		//le proriétaire a la visibilite sur les document qu'il publie
		getVisibiliteUtilisateur().add(getProprietaire());
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
	 * Renvoie la date de fin de publication 
	 * @return dateFinPublication
	 */
	public Date getDateFinDisponibilite() {
		return dateFinPublication;
	}
	
	/**
	 * Renvoie la durée de disponibilité de la publication
	 * @return dureeDeDisponibilite
	 */
	public long getDureeDeDisponibilite() {
		return dateFinPublication.getTime() - dateDePublication.getTime();
	}


	/**
	 * Affecte la durée de disponibilité de la publication
	 * @param dureeDeDisponibilite
	 */
	public void setDateFinDisponibilite(Date dateFinDispo) {
		this.dateFinPublication = dateFinDispo;
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
	 * Permet de contrôler la visibilité du document pas un utilisateur
	 * @param utilisateur : l'utilisateur à contrôler
	 * @return : True si l'utilisateur est autorisé à consulter le document
	 */
	public boolean estVisible(Utilisateur utilisateur){
		
		return visibiliteUtilisateur.contains(utilisateur);
	}
	
	/**
	 * Permet de contrôler la visibilité du document pas un groupe d'utilisateurs
	 * @param groupe : le groupe à contôler
	 * @return : True si le groupe d'utilisateurs est autorisé à consulter le document
	 */
	public boolean estVisible(Groupe groupe){
		return visibiliteGroupe.contains(groupe);
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
	
	/**
	 * Ajoute un utilisateur à la liste des personnes ayant accès à la publication
	 * @param u
	 */
	public void autoriserAccesUtilisateur(Utilisateur u){
		getVisibiliteUtilisateur().add(u);
	}
	
	/**
	 * Ajoute un groupe à la liste des groupes ayant accès à la publication
	 * @param u
	 */
	public void autoriserAccesGroupe(Groupe g){
		getVisibiliteGroupe().add(g);
	}
	
	/**
	 * Retire un utilisateur à la liste des personnes ayant accès à la publication
	 * @param u
	 */
	public void retirerAccesUtilisateur(Utilisateur u){
		getVisibiliteUtilisateur().remove(indexUtilisateur(u));
	}
	
	/**
	 * Retire un groupe à la liste des groupes ayant accès à la publication
	 * @param u
	 */
	public void retirerAccesGroupe(Groupe g){
		getVisibiliteGroupe().remove(indexGroupe(g));
	}
    
	/**
	 * Teste si deux publications sont égales
	 * @param publi
	 * @return true si égales, false sinon
	 */
   public boolean equals(Publication publi)
   {
	   return this.getDateDePublication().equals(publi.getDateDePublication());
   }
    
   
   /**
    * Retirer tous les accès à la publication
    */
   public void retirerAcces()
   {
	   this.visibiliteGroupe=null;
	   this.visibiliteUtilisateur=null;
   }
    
   public int indexUtilisateur(Utilisateur utilisateur){
		int num = -1;
		int compteur = 0;
		for (Utilisateur util : getVisibiliteUtilisateur()) {
			if (util.getLogin().equals(utilisateur.getLogin())) {
				num = compteur;
			}
			compteur++;
		}

		return num;
	}
   
   /**
    * Renvoie l'index du groupe dans la liste des groupes ayant accès à la publication
    * @param groupe
    * @return index si trouvé, -1 sinon
    */
   public int indexGroupe(Groupe groupe)
   {
	   int num = -1;
		int compteur = 0;
		for (Groupe grp : getVisibiliteGroupe()) {
			if (grp.getIdGroupe()==groupe.getIdGroupe()) {
				num = compteur;
			}
			compteur++;
		}

		return num;
   }
    

}
