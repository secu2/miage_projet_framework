package systeme;

import systeme.rmi.ClientRMI;
import modules.gestionUtilisateur.Utilisateur;

public class Client {

	private Utilisateur utilisateur;
	private ClientRMI client;
	
	/**
	 * Créer un client
	 * @param u
	 * @param motDePasse
	 */
	public Client(Utilisateur u,String motDePasse){
		setUtilisateur(u);
		client = new ClientRMI(u.getLogin(),motDePasse);
	}
	
	/**
	 * Affecte un utilisateur au client
	 * @param u
	 */
	public void setUtilisateur(Utilisateur u){
		utilisateur = u;
	}
	
	/**
	 * Renvoie l'utilisateur lié au client
	 * @return utilisateur
	 */
	public Utilisateur getUtilisateur(){
		return utilisateur;
	}
	
	public ClientRMI getClientRMI(){
		return client;
	}
	
	
	
	
}
