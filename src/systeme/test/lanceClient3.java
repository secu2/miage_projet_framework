package systeme.test;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Date;

import modules.chat.Message;
import modules.documents.Document;
import modules.documents.social.Publication;
import modules.gestionUtilisateur.Groupe;
import modules.gestionUtilisateur.Utilisateur;
import systeme.Client;
import systeme.rmi.ClientRMI;
import systeme.rmi.ServeurRMI;

public class lanceClient3 {

	public static void main(String[] args) throws NoSuchAlgorithmException,
			UnsupportedEncodingException {

		
		
		ClientRMI c = new ClientRMI("popo", "popo");
		//ClientRMI c1 = new ClientRMI("momoo", "jojo");
		
		c.envoyerMessage(new Message("Test ça marche de popo!!!!!!!!",c.getUtilisateur().getLogin()));
		c.recevoirMessageConversationAbsence();

	}
}
