package systeme.rmi;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import systeme.Client;
import systeme.Serveur;

public class MainRmi {

	public static void main(String[] args) throws NoSuchAlgorithmException,
			UnsupportedEncodingException {

		
		ClientRMI c = new ClientRMI("momo", "jojo");
		

		System.out.println("Chat ............................");
		c.envoyerMessage("Bonjour", c);

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
