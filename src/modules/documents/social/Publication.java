package modules.documents.social;
import java.util.ArrayList;
import java.util.Date;

import modules.documents.Document;
import modules.gestionUtilisateur.Utilisateur;



public class Publication {
    
    private Date dateDePublication;
    private int dureeDeDisponibilite;
    private ArrayList<Utilisateur> visibilite;
    private Utilisateur proprietaire;
    private Document document;
	
    
    
    public Publication(Date dateDePublication, int dureeDeDisponibilite, ArrayList<Utilisateur> visibilite,
			Utilisateur proprietaire, Document document) {
		this.dateDePublication = dateDePublication;
		this.dureeDeDisponibilite = dureeDeDisponibilite;
		this.visibilite = visibilite;
		this.proprietaire = proprietaire;
		this.document = document;
	}
    
    

}
