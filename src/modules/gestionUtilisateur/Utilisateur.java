package modules.gestionUtilisateur;

public class Utilisateur {
    
    private String login;
    private String motDePasse;
    
    /**
     * Construit un objet Utilisateur en fournissant le login et le mot de passe
     * @param login
     * @param motDePasse
     */
    public Utilisateur(String login,String motDePasse){
    	this.login = login;
    	this.motDePasse = motDePasse;
    }
    
    /**
     * Renvoie l'identifiant (login) de l'utilisateur
     * @return login
     */
	public String getLogin() {
		return login;
	}
	
	/**
	 * Affecte le login de l'utilisateur
	 * @param login
	 */
	public void setLogin(String login) {
		this.login = login;
	}
	
	/**
	 * Affecte un mot de passe pour cet utilisateur
	 * @param motDePasse
	 */
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}
    
    

}
