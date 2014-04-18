package modules.documents;

public class Audio extends Document {
	
	private String frequenceEchantillonage;
	private String debit; 
	
	/**
	 * Construit un objet audio à partir de sa fréquence d'echantillonnage
	 * et de son débit
	 * @param frequence
	 * @param debit
	 */
	public Audio(String frequence, String debit){
		super();
		this.frequenceEchantillonage = frequence;
		this.debit = debit;
	}
}
