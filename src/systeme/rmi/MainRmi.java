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

		Serveur serveur = new Serveur();
		serveur.inscription("momo", "jojo");
		//serveur.inscription("jojo", "salaud");
		//serveur.connexion("jojo", "salaud");
<<<<<<< HEAD
		ClientRMI c = new ClientRMI("momo", "jojo");
=======
		serveur.connexion("momo", "jojo");
		//Client c = serveur.getClientConnecte("momo");
		File testFile = new File("docServeur/image.jpg");
        long len = testFile.length();
        
        //String[] clients = clt2.getClients();
        
       /* for(int i =0; i< clients.length; i++)
        {
        	System.out.println(clients[i]);
        }*/
        /*
        long t;
        t = System.currentTimeMillis();
        try {
			c.getClientRMI().telecharger(serveur.getServeurRMI(), testFile, new File("C:/Users/chaiebm/Desktop/download.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        try {
        	c.getClientRMI().charger(serveur.getServeurRMI(), new File("C:/Users/chaiebm/Desktop/download.jpg"), new File("docServeur/test1.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		*/if (serveur.connexion("momo", "jojo")) {
>>>>>>> branch 'master' of https://github.com/secu2/miage_projet_framework.git

<<<<<<< HEAD
			//Client c = serveur.getClientConnecte("momo");
=======
			Client c = serveur.getClientConnecte("momo");/*
>>>>>>> branch 'master' of https://github.com/secu2/miage_projet_framework.git

			File testFile = new File("docServeur/image.jpg");
			long len = testFile.length();

			/*
			 * String[] clients = clt2.getClients();
			 * 
			 * for(int i =0; i< clients.length; i++) {
			 * System.out.println(clients[i]); }
			 * 
			 * long t; t = System.currentTimeMillis();
			 */
			/*try {

				c.getClientRMI().telecharger(serveur.getServeurRMI(), testFile,	new File("C:/Users/chaiebm/Desktop/download.jpg"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				c.getClientRMI().charger(serveur.getServeurRMI(),
						new File("C:/Users/chaiebm/Desktop/download.jpg"),
						new File("docServeur/test1.jpg"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
		/*	ArrayList <Client>clients = c.getClientRMI().getClientsConnectee();
			
			for(int i=0; i<clients.size(); i++)
			{
				System.out.println(clients.get(i).getUtilisateur().getLogin());
			}*/
	}

}
