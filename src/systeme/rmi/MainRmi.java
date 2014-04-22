package systeme.rmi;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import systeme.Client;
import systeme.Serveur;

public class MainRmi {

	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		 
		Serveur serveur = new Serveur();
		serveur.inscription("momo", "jojo");
		serveur.connexion("momo", "joj");
		Client c = serveur.getClientConnecte("momo");
		File testFile = new File("docServeur/image.jpg");
        long len = testFile.length();
        
        //String[] clients = clt2.getClients();
        
       /* for(int i =0; i< clients.length; i++)
        {
        	System.out.println(clients[i]);
        }*/
        
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
		}
	}

}
