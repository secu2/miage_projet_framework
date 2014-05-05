package systeme.test;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.security.acl.Group;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

public class MainRmi {

	public static void main(String[] args) throws NoSuchAlgorithmException,
			UnsupportedEncodingException {

		Serveur serveur;
		try {
			
			
			//////////Lancement du serveur///////////////////
				serveur = new Serveur();
			//inscription d'un client
				serveur.getServeur().inscription("momo", "jojo");	
				serveur.getServeur().inscription("jojo", "momo");
			//connexion d'un client
				Client c = new Client("momo", "jojo");
				Client c1 = new Client("jojo","momo");
				
				
				try {
					c.telecharger(new File("/home/b/brocarcl/git/miage_projet_framework/docServeur/image.jpg"), new File("/home/b/brocarcl/Bureau/download.jpg"));
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			////////////Chargement d'un document /////////////////
				//Document document = new Document("/home/never/test.png");
				
				ArrayList<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
				ArrayList<Groupe> groupes =  new ArrayList<Groupe>(); 
				//utilisateurs.add(c1.getUtilisateur());
				//c.creerUnGroupe("Ibn");
		
				//c.ajouterUnUtlisateurDansGroupe(0, c1.getUtilisateur());
				DateFormat df = new SimpleDateFormat("dd-MM-yyyy"); 
				Date date=null;
				
				  try {
					date= df.parse("25-12-2010");
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		
				
			////Le client charge le document en fonction de la visibilité et de la date de fin de publication
				  /*try {
					c.charger(document.getFichier(),utilisateurs, null, document, null);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
	
				} */
			///Affiche les plublications visibles
				//c.supprimerUnePublication(c.getPublications().get(0)); 
				ArrayList<Publication> publications = c.getPublications();
				ArrayList<Publication> publicationsvisiblesC1 = c1.getPublicationsVisibles();
				
				//System.out.println(publicationsvisibles);
				System.out.println("fichier(s) visible(s) pour : "+c1.getUtilisateur().getLogin());
				for(Publication pub : publicationsvisiblesC1)
				{
					
				
					System.out.println(""+pub.getDocument().getEmplacement());
				} 
				System.out.println("publications de  : "+c.getUtilisateur().getLogin());
				for(Publication pub : publications)
				{
					
				
					System.out.println(""+pub.getDocument().getEmplacement());
				}
				
				//autorise publication a c1
				System.out.println("Autorisation a c1 ");
				
				c.autoriserPublicationUtilisateur(c.getPublications().get(0), c1.getUtilisateur());
				//c.autoriserUnePublicationGroupe(c.getPublications().get(0), c.getGroupes().get(0));
				publicationsvisiblesC1 = c1.getPublicationsVisibles();
				
				System.out.println("fichier(s) visible(s) pour : "+c1.getUtilisateur().getLogin());
				for(Publication pub : publicationsvisiblesC1)
				{
			
					System.out.println(""+pub.getDocument().getEmplacement());
				}
				//retire la publication a c1
				System.out.println("Retrait de l'autorisation");
				c.retirerUnePublicationutilisateur(c.getPublications().get(0), c1.getUtilisateur());
				//c.retirerUnePublicationGroupe(c.getPublications().get(0), c.getGroupes().get(0));
				System.out.println("fichier(s) visible(s) pour : "+c1.getUtilisateur().getLogin());
				
				publicationsvisiblesC1 = c1.getPublicationsVisibles();
				for(Publication pub : publicationsvisiblesC1)
				{
			
					System.out.println(""+pub.getDocument().getEmplacement());
				}
				
				/*
				//Creation d'un groupe
				System.out.println("Creation d'un groupe");
				c.creerUnGroupe("Ibn");
				c.creerUnGroupe("22");
				
				System.out.println("Liste des groupes de " +c.getUtilisateur().getLogin());
				
				ArrayList<Groupe> groupesProp = c.getGroupesProp();
				//int id=0;
				for(Groupe groupe : groupesProp)
				{
					System.out.println(groupe.getNomGroupe() +" id " + groupe.getIdGroupe());
					//id=groupe.getIdGroupe();
				}
				
				//Ajout d'un utilisateur
				System.out.println("Ajout d'un utilisateur");
				c.ajouterUnUtlisateurDansGroupe(0, c1.getUtilisateur());
				c.ajouterUnUtlisateurDansGroupe(1, c1.getUtilisateur());
				System.out.println("Affichage des groupes de "+c1.getUtilisateur().getLogin());
				
				ArrayList<Groupe> groupesApp =  c1.getGroupes();
				for(Groupe groupe : groupesApp)
				{
					System.out.println(groupe.getNomGroupe());
					//id=groupe.getIdGroupe();
				}
				System.out.println("Suppression de " +c1.getUtilisateur().getLogin() +"dans le groupe d'id 0");
				
				c.supprimerUnUtilsateurDuGroupe(0, c1.getUtilisateur());
				
				System.out.println("Affichage des groupes de "+c1.getUtilisateur().getLogin());
				
				 groupesApp =  c1.getGroupes();
				 for(Groupe groupe : groupesApp)
					{
						System.out.println(groupe.getNomGroupe());
						//id=groupe.getIdGroupe();
					}
				 System.out.println("Suppression du groupe d'id 0");
				 c.supprimerGroupe(0);
				 
				 groupesProp = c.getGroupesProp();
				 System.out.println("Liste des groupes de " +c.getUtilisateur().getLogin());
				 groupesProp = c.getGroupesProp();
					for(Groupe groupe : groupesProp)
					{
						System.out.println(groupe.getNomGroupe());
						
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
