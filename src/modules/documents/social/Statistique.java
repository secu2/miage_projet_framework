package modules.documents.social;


public class Statistique {

	private int nbTelechargement;
    private int nbVue;
    
    /**
     * Permet de récupérer le nombre de vues du fichier
     * @return nombre de vues du fichier
     */
    public int getNbVue() {
		return nbVue;
	}
    
    /**
     * Permet de récupérer le nombre de téléchargements du fichier
     * @return nombre de téléchargements du fichier
     */
	public int getNbTelechargement() {
		return nbTelechargement;
	}
	
	/**
	 * Ajoute un téléchargement aux statistiques du fichier
	 */
	public void estTelecharge(){
		nbTelechargement++;
	}
	
	/**
	 * Ajoute une vue au téléchargement du fichier
	 */
	public void estVu(){
		nbVue++;
	}

}
