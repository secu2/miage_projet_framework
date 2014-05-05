package modules.gestionUtilisateur;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import systeme.BaseDeDonnees;
import systeme.Client;

/**
 * Groupe.java
 * @author never
 *
 */
public class Groupe implements Serializable{
	
	// identifiant du groupe
	private int idGroupe;
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
	public Groupe(String nomGroupe,Utilisateur u) {
		this.proprietaire = u;
		this.idGroupe = proprietaire.getGroupes().size();
		this.nomGroupe = nomGroupe;
		this.utilisateurs = new ArrayList<Utilisateur>();
	}
	
	/**
	 * Constructeur de groupe avec sauvegarde en base de données
	 * @param bdd : la base de données ou enregistrer
	 * @param idGroupe
	 * @param nomGroupe
	 * @throws SQLException 
	 */
	public Groupe(BaseDeDonnees bdd,String nomGroupe,Utilisateur u) throws SQLException{
		this.proprietaire = u;
		idGroupe = proprietaire.getGroupes().size();
		this.nomGroupe = nomGroupe;
		sauvegarderPersistant(bdd);
		this.utilisateurs = new ArrayList<Utilisateur>();
	}

	/**
	 * Sauvegarde sur le groupe dans la bd
	 * @param bdd
	 * @throws SQLException
	 */
	public void sauvegarderPersistant(BaseDeDonnees bdd) throws SQLException {
		bdd.sauvegarderGroupe(nomGroupe,proprietaire.getLogin(),idGroupe);
	}

	/**
	 * Affecte un nom de groupe au groupe
	 * @param nomGroupe
	 */
	private void setNomGroupe(String nomGroupe) {
		this.nomGroupe = nomGroupe;
	}

	/**
	 * Renvoie le nom du groupe
	 * @return nomGroupe
	 */
	public String getNomGroupe(){
		return nomGroupe;
	}
	
	/**
	 * Renvoie l'id du groupe
	 * @return idGroupe
	 */
	public int getIdGroupe(){
		return idGroupe;
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
	 * Retourne l'ensemble des utilisateurs du groupe
	 * @return utilisateurs
	 */
	public ArrayList<Utilisateur> getUtilisateurs(){
		return utilisateurs;
	}
	
	/**
	 * Ajout d'un utilisateur dans les listes de utilisateurs
	 * @param u
	 */
	public void ajouterUtilisateur(Utilisateur u){
		getUtilisateurs().add(u);
	}
	
	/**
	 * Supprime un utilisateur du groupe
	 * @param u
	 */
	public void supprimerUtilisateur(Utilisateur u)
	{
		getUtilisateurs().remove(indexUtilisateur(u));
	}
	
	/**
	 * Renvoie l'index de l'utilisateur dans la liste des utilisateurs
	 * @param utilisateur
	 * @return index si trouvé , -1 sinon
	 */
	public int indexUtilisateur(Utilisateur utilisateur) {
		int num = -1;
		int compteur = 0;
		for (Utilisateur util : getUtilisateurs()) {
			if (util.getLogin().equals(utilisateur.getLogin())) {
				num = compteur;
			}
			compteur++;
		}

		return num;
	}

	/**
	 * Renvoie le propriétaire d'un groupe
	 * @return proprietaire
	 */
	public Utilisateur getProprietaire() {
		return proprietaire;
	}

	/**
	 * Affecte un proprietaire au groupe
	 * @param proprietaire
	 */
	public void setProprietaire(Utilisateur proprietaire) {
		this.proprietaire = proprietaire;
	}
	
	
}
