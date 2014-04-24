package systeme;

/**
 * Attribut.java
 * @author Never
 *
 */
public class Attribut {
	
	 // Le nom de l'attribut dans la base de données
	private String nomAttributBD;
	// Sa valeur
	private String valeur;
	
	public Attribut(String nomAttributBD, String valeur) {
		this.nomAttributBD = nomAttributBD;
		this.valeur = valeur;
	}

	public String getNomAttributBD() {
		return nomAttributBD;
	}

	public void setNomAttributBD(String nomAttributBD) {
		this.nomAttributBD = nomAttributBD;
	}

	public String getValeur() {
		return valeur;
	}

	public void setValeur(String valeur) {
		this.valeur = valeur;
	} 
	
	

}
