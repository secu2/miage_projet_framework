package systeme.test;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Date;

import modules.chat.Message;
import modules.chat.MessagePrive;
import modules.documents.Document;
import modules.documents.social.Publication;
import modules.gestionUtilisateur.Groupe;
import modules.gestionUtilisateur.Utilisateur;
import systeme.Client;
import systeme.Serveur;
import systeme.rmi.ClientRMI;

public class lanceClient1 {

	public static void main(String[] args) throws NoSuchAlgorithmException,
			UnsupportedEncodingException {

		
		
		ClientRMI c = new ClientRMI("momo", "jojo");
		//ClientRMI c1 = new ClientRMI("momoo", "jojo");
		
		c.envoyerMessage(new Message("Hey ça marche :'D ", c.getUtilisateur().getLogin()));
		for(int i = 0; i < 10 ; i++){
			c.envoyerMessagePrive(new MessagePrive("Bonjour jojo " + i, c.getUtilisateur().getLogin(), "jojo"));
		}
		//c.envoyerMessagePrive("Test réussi", c.getUtilisateurs().get(0));
		
	
	
		
		//c.deconnexion();

		

	}
}
