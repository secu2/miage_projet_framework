package systeme.test;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Date;

import modules.documents.Document;
import modules.documents.social.Publication;
import modules.gestionUtilisateur.Groupe;
import modules.gestionUtilisateur.Utilisateur;
import systeme.Client;
import systeme.Serveur;
import systeme.rmi.ClientRMI;

public class lanceClient2 {

	public static void main(String[] args) throws NoSuchAlgorithmException,
			UnsupportedEncodingException {

		
		
		ClientRMI c = new ClientRMI("jojo", "jojo");
		//ClientRMI c1 = new ClientRMI("momoo", "jojo");
		
		c.envoyerMessage("Test ça marche !!!!!!!!");
		
		////Client import de fichier 
		
		Document document = new Document("C:/Users/chaiebm/Desktop/download.jpg");
		
		
		ArrayList<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
		ArrayList<Groupe> groupes =  new ArrayList<Groupe>(); 
		//utilisateurs.add(c1.getUtilisateur());

		
		//ArrayList<Publication> publications = c1.getUtilisateur().getPublications();
		
		
		
		//c1.deconnexion();

		// serveur.connexion("momo", "jojo");
		// Client c = serveur.getClientConnecte("momo");
		File testFile = new File("docServeur/image.jpg");
		long len = testFile.length();

		// String[] clients = clt2.getClients();

		/*
		 * for(int i =0; i< clients.length; i++) {
		 * System.out.println(clients[i]); }
		 * 
		 * long t; t = System.currentTimeMillis();
		 */
		/*try {
			c.telecharger(serveur.getServeurRMI(), testFile, new File(
					"C:/Users/chaiebm/Desktop/download.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
/**
		try {
			c.charger(serveur.getServeurRMI(), new File("C:/Users/chaiebm/Desktop/download.jpg"),utilisateurs, null, document, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}**/
		///Affiche les plublications visibles
		
		/**ArrayList<Publication> publicationsvisibles = c1.getPublicationsVisibles();
		
		for(Publication pub : publicationsvisibles)
		{
			
			
		System.out.println("fichier visible");
			System.out.println(""+pub.getDocument().getEmplacement());
		} **/
		
		/*
		ArrayList<ClientRMI> clients = c.getUtilisateurs();
		System.out.println("Utilisateurs connectés ");
		for(int i=0; i < clients.size(); i++)
		{
			System.out.println(clients.get(i).getUtilisateur().getLogin());
		}*/
		
		for(int i = 0 ; i < 10 ; i++){
			c.envoyerMessage("De jojo");
		}

	}
}
