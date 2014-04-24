package systeme.rmi;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import modules.documents.Document;
import systeme.Client;
import systeme.Serveur;

public class MainRmi {

	public static void main(String[] args) throws NoSuchAlgorithmException,
			UnsupportedEncodingException {

		Serveur serveur = new Serveur();
		
		serveur.inscription("momo", "jojo");
		
		serveur.inscription("momoo", "jojo");
		
		ClientRMI c = new ClientRMI("momo", "jojo");
		ClientRMI c1 = new ClientRMI("momoo", "jojo");
		
		////Client import de fichier 
		
		Document document = new Document("C:/Users/chaiebm/Desktop/download.jpg");
		
		System.out.println(""+document.getTaille());
		
		
		c1.deconnexion();

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
		}

		try {
			c.charger(serveur.getServeurRMI(), new File("C:/Users/chaiebm/Desktop/download.jpg"), new File(
					"docServeur/test1.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}*/
		ArrayList<ClientRMI> clients = c.getUtilisateurs();
		System.out.println("Utilisateurs connectés ");
		for(int i=0; i < clients.size(); i++)
		{
			System.out.println(clients.get(i).getUtilisateur().getLogin());
		}

	}
}
