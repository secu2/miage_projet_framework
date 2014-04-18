package modules.gestionUtilisateur;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import systeme.BaseDeDonnees;

/**
 * Groupe.java
 * @author never
 *
 */
public class Groupe {
	// nom du groupe
	private String nomGroupe;
	// Liste des utilisateurs appartenants à ce groupe
	private ArrayList<Utilisateur> utilisateurs;
	// proprietaire du groupe
	private Utilisateur proprietaire;

	/**
	 * Constructeur de groupe sans sauvegarde en base de données
	 * @param idGroupe l'id du groupe, est unique
	 * @param nomGroupe le nom du groupe
	 */
	public Groupe(int idGroupe, String nomGroupe){
		//Création du groupe
		if(disponibiliteIdGroupe(idGroupe)){
			setNomGroupe(nomGroupe);
		}else{
			//TODO: gestion d'erreur en cas de groupe déja existant
			System.err.println("Erreur: Le groupe "+idGroupe+"existe déja...");
		}
	}
	
	/**
	 * Constructeur de groupe avec sauvegarde en base de données
	 * @param bdd : la base de données ou enregistrer
	 * @param idGroupe
	 * @param nomGroupe
	 * @throws SQLException 
	 */
	public Groupe(BaseDeDonnees bdd, int idGroupe, String nomGroupe) throws SQLException{
		//Création du groupe
		if(disponibiliteIdGroupe(idGroupe)){
			setNomGroupe(nomGroupe);
			sauvegarderPersistant(bdd);
		}else{
			//TODO: gestion d'erreur en cas de groupe déja existant
			System.err.println("Erreur: Le groupe "+idGroupe+"existe déja...");
		}
	}

	public void sauvegarderPersistant(BaseDeDonnees bdd) throws SQLException {
		bdd.sauvegarderGroupe(nomGroupe,proprietaire.getLogin());
	}

	/**
	 * @param nomGroupe
	 */
	private void setNomGroupe(String nomGroupe) {
		this.nomGroupe = nomGroupe;
	}

	/**
	 * Permet de vérifier si le groupe possédant l'idGroupe 
	 * communiqué éxiste déja dans la base de donnée des groupes
	 * @param idGroupe
	 * @return true/false
	 */
	public boolean disponibiliteIdGroupe(int idGroupe){
		//TODO: Vérifier que le groupe n'est pas déja présent dans la base de données
		return true;
	}
}
