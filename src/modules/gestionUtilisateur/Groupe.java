package modules.gestionUtilisateur;

import java.util.Hashtable;

import systeme.BaseDeDonnees;


public class Groupe {
	private String nomGroupe;


	/**
	 * Constructeur de groupe sans sauvegarde en base de données
	 * @param idGroupe l'id du groupe, est unique
	 * @param nomGroupe le nom du groupe
	 */
	public Groupe(int idGroupe, String nomGroupe){
		//Création du groupe
		if(disponibiliteIdGroupe(idGroupe)){
			creerGroupe(nomGroupe);
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
	 * @param flags
	 */
	public Groupe(BaseDeDonnees bdd, int idGroupe, String nomGroupe){
		//Création du groupe
		if(disponibiliteIdGroupe(idGroupe)){
			creerGroupe(nomGroupe);
			sauvegarderPersistant(bdd);
		}else{
			//TODO: gestion d'erreur en cas de groupe déja existant
			System.err.println("Erreur: Le groupe "+idGroupe+"existe déja...");
		}
	}

	public void sauvegarderPersistant(BaseDeDonnees bdd) {
		bdd.sauvegarderGroupe(nomGroupe);
	}

	/**
	 * 
	 * @param idGroupe
	 * @param nomGroupe
	 * @param flags
	 */
	private void creerGroupe(String nomGroupe) {
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
