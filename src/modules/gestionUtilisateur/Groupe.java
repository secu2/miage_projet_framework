package modules.gestionUtilisateur;

import java.util.Hashtable;


public class Groupe {
	private int idGroupe;
	private String nomGroupe;
	private Hashtable flags;


	/**
	 * Constructeur de groupe
	 * @param idGroupe l'id du groupe, est unique
	 * @param nomGroupe le nom du groupe
	 * @param flags les configurations du groupe
	 */
	public Groupe(int idGroupe, String nomGroupe, Hashtable flags){
		flags = new Hashtable();
		//Création du groupe
		if(disponibiliteIdGroupe(idGroupe)){
			creerGroupe(idGroupe, nomGroupe, flags);
		}else{
			//TODO: gestion d'erreur en cas de groupe déja existant
			System.err.println("Erreur: Le groupe "+idGroupe+"existe déja...");
		}
	}

	/**
	 * 
	 * @param idGroupe
	 * @param nomGroupe
	 * @param flags
	 */
	private void creerGroupe(int idGroupe, String nomGroupe, Hashtable flags) {
		this.idGroupe = idGroupe;
		this.nomGroupe = nomGroupe;
		this.flags = flags;
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
	
	/**
	 * Permet d'ajouter un flag booleen au groupe
	 * @param id
	 * @param valeur
	 */
	public void newFlag(String id, boolean valeur){
		
	}
	/**
	 * Permet d'ajouter un flag String au groupe
	 * @param id
	 * @param valeur
	 */
	public void newFlag(String id, String valeur){

	}
	
	/**
	 * Permet d'ajouter un flag int au groupe
	 * @param id
	 * @param valeur
	 */
	public void newFlag(String id, int valeur){

	}
}
