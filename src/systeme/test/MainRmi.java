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
			
			
			//////////Lancement du serveur///////////////////
				serveur = new Serveur();
			//inscription d'un client
				serveur.getServeur().inscription("momo", "jojo");	
			//connexion d'un client
				ClientRMI c = new ClientRMI("momo", "jojo");
			////////////Chargement d'un document /////////////////
				Document document = new Document("C:/Users/Mohamed/Desktop/download.jpg");
				
				ArrayList<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
				ArrayList<Groupe> groupes =  new ArrayList<Groupe>(); 
				utilisateurs.add(c.getUtilisateur());	
				
			////Le client charge le document en fonction de la visibilité et de la date de fin de publication
				try {
					c.charger(serveur, new File("C:/Users/Mohamed/Desktop/download.jpg"),utilisateurs, null, document, null);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
	
				} 
			///Affiche les plublications visibles
			
				ArrayList<Publication> publicationsvisibles = c.getPublicationsVisibles();
				System.out.println(publicationsvisibles);
				for(Publication pub : publicationsvisibles)
				{
					
				System.out.println("fichier(s) visible(s) pour : "+c.getUtilisateur().getLogin());
					System.out.println(""+pub.getDocument().getEmplacement());
				} 
			
			
			//ClientRMI c1 = new ClientRMI("momoo", "jojo");
			
			//c.envoyerMessage("Test");
			
			////Client import de fichier 
			
		//	Document document = new Document("C:/Users/chaiebm/Desktop/download.jpg");
			

			
			//ArrayList<Publication> publications = c1.getUtilisateur().getPublications();
			
			
			
			//c1.deconnexion();

			// serveur.connexion("momo", "jojo");
			// Client c = serveur.getClientConnecte("momo");
			//File testFile = new File("docServeur/image.jpg");
			//long len = testFile.length();

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

			
			
			/*
			ArrayList<ClientRMI> clients = c.getUtilisateurs();
			System.out.println("Utilisateurs connectés ");
			for(int i=0; i < clients.size(); i++)
			{
				System.out.println(clients.get(i).getUtilisateur().getLogin());
			}
			*/
		
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
