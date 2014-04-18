package modules.documents;

import modules.documents.social.Statistique;

public class Document {
	// D�finition et Gestion des documents pr�sents sur le drive. Son nom, son
	// propri�taire, son type, sa d�finition, sa description sa date
	// d�expiration, et sa visibilit� (par d�faut visible seulement par lui)

	private String nom;
	private float taille;
	private String format;
	private Statistique statistique; //Les statistiques du fichier

}
