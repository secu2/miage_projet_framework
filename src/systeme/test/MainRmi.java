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
import systeme.Serveur;
import systeme.rmi.ClientRMI;
import systeme.rmi.ServeurRMI;

public class MainRmi {

	public static void main(String[] args) throws NoSuchAlgorithmException,
			UnsupportedEncodingException {

		Serveur serveur;
		try {
			serveur = new Serveur();
			serveur.getServeur().inscription("momo", "jojo");
			
			
			ClientRMI c = new ClientRMI("momo", "jojo");
			//ClientRMI c1 = new ClientRMI("momoo", "jojo");
			
			//c.envoyerMessage("Test");
			
			////Client import de fichier 
			
		//	Document document = new Document("C:/Users/chaiebm/Desktop/download.jpg");
			Document document = new Document("/home/never/ddl/download.jpg");
			
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
			/** try {
				c.telecharger(serveur, testFile, new File(
						"/home/never/ddl/download.jpg"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} **/

			try {
				c.charger(serveur, new File("/home/never/ddl/download.jpg"),utilisateurs, null, document, null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			} 
			///Affiche les plublications visibles
			
			ArrayList<Publication> publicationsvisibles = c.getPublicationsVisibles();
			System.out.println(publicationsvisibles);
			for(Publication pub : publicationsvisibles)
			{
				
			System.out.println("fichier visible");
				System.out.println(""+pub.getDocument().getEmplacement());
			} 
			
			
			ArrayList<ClientRMI> clients = c.getUtilisateurs();
			System.out.println("Utilisateurs connectés ");
			for(int i=0; i < clients.size(); i++)
			{
				System.out.println(clients.get(i).getUtilisateur().getLogin());
			}

		
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
