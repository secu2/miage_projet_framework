package systeme.test;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Date;

import modules.chat.Message;
import modules.chat.MessageConversation;
import modules.chat.MessagePrive;
import modules.documents.Document;
import modules.documents.social.Publication;
import modules.gestionUtilisateur.Groupe;
import modules.gestionUtilisateur.Utilisateur;
import systeme.Client;
import systeme.Client1;
import systeme.rmi.ServeurRMI;

public class lanceClient1 {

	public static void main(String[] args) throws NoSuchAlgorithmException,
			UnsupportedEncodingException, RemoteException {

		
		
		Client c = new Client("momo", "jojo");
		//ClientRMI c1 = new ClientRMI("momoo", "jojo");
		try {
			c.getUtilisateur().creerUnGroupe("amis");
			c.getUtilisateur().ajouterUtilisateurGroupe(c.getServeurRmiImpl().getUtilisateursInscrits().get(2), "amis");
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//c.envoyerMessage(new Message("Hey ça marche :'D ", c.getUtilisateur().getLogin()));
		ArrayList<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
		try {
			int idConversation = c.creerUneConversation(c.getServeurRmiImpl().getUtilisateursInscrits(), null);
			ArrayList<Groupe> groupes= new ArrayList<Groupe>();
			groupes.add(c.getUtilisateur().getGroupe("amis"));
			c.envoyerMessageConversation(new MessageConversation(idConversation, "Ca marche!!." , c.getUtilisateur().getLogin(), c.getServeurRmiImpl().getUtilisateursInscrits(), groupes));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//c.envoyerMessagePrive("Test réussi", c.getUtilisateurs().get(0));
		
		
	//	c.nouvelleConversation(utilisateurs,groupes);
	// c.envoyerMessage(message,conversation)
		
		//c.deconnexion();

		

	}
}
