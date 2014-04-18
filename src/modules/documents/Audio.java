package modules.documents;

import java.io.File;

import modules.documents.social.Statistique;

/**
 * Audio.java
 * @author never
 *
 */
public class Audio extends Document {
	
	private String frequenceEchantillonnage;
	private String debit; 
	

	/**
	 * 
	 * Constructeur de la classe Audio : construit un objet audio à partir de sa fréquence d'echantillonnage
	 * et de son débit	
	 * @param nom
	 * @param taille
	 * @param format
	 * @param statistique
	 * @param emplacement
	 * @param frequenceEchantillonnage
	 * @param debit
	 */
	public Audio(String nom, float taille, String format,
			Statistique statistique, String emplacement,
			String frequenceEchantillonnage, String debit) {
		super(nom, taille, format, statistique, emplacement);
		this.frequenceEchantillonnage = frequenceEchantillonnage;
		this.debit = debit;
	}
	
	/**
	 * Renvoie la fréquence d'echantillonnage
	 * @return frequenceEchantillonnage
	 */
	public String getFrequenceEchantillonnage() {
		return frequenceEchantillonnage;
	}

	

	/**
	 * Affecte une fréquence d'echantillonnage au document audio
	 * @param frequenceEchantillonnage
	 */
	public void setFrequenceEchantillonnage(String frequenceEchantillonage) {
		this.frequenceEchantillonnage = frequenceEchantillonnage;
	}

	/**
	 * Renvoie le débit du document audio
	 * @return debit
	 */
	public String getDebit() {
		return debit;
	}

	/**
	 * Affecte un débit au document audio
	 * @param debit
	 */
	public void setDebit(String debit) {
		this.debit = debit;
	}
	
	
}
