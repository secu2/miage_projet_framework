package systeme.test;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Date;

import modules.documents.Document;
import modules.documents.social.Publication;
import modules.gestionUtilisateur.Groupe;
import modules.gestionUtilisateur.Utilisateur;
import systeme.Client;
import systeme.Client1;
import systeme.Serveur;
import systeme.rmi.ServeurRMI;

public class lanceServeur {

	public static void main(String[] args) throws NoSuchAlgorithmException,
			UnsupportedEncodingException {

		Serveur serveur;
		try {
			serveur = new Serveur();
			serveur.getServeur().inscription("momo", "jojo");
			serveur.getServeur().inscription("jojo", "jojo");
			serveur.getServeur().inscription("popo", "popo");
			
		
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		// TODO : Faire l'echange de messages asynchrones -> Création d'un tableau de message qui sera transmit à la connexion puis vidé?
		// TODO : Voir si il est mieux de tout passer le serveur dans l'implémentation de l'interface
	}
} 
