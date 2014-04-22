package systeme;

import java.util.ArrayList;

import systeme.rmi.ServeurRMI;

import modules.gestionUtilisateur.Utilisateur;

public class Serveur {
	
	public ArrayList<Utilisateur> utilisateursInscrits;
	public ArrayList<Client> utilisateursConnectes;
	public ServeurRMI serveur;


}
