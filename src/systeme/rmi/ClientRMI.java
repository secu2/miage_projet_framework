package systeme.rmi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;

import javax.management.modelmbean.RequiredModelMBean;

import jus.util.assertion.Ensure;
import jus.util.assertion.Require;
import modules.documents.Document;
import modules.documents.social.Publication;
import modules.gestionUtilisateur.Groupe;
import modules.gestionUtilisateur.Utilisateur;
import systeme.Client;
import systeme.Serveur;

/**
 * @author chaiebm
 * 
 */


public class ClientRMI  implements Serializable{
	static int REGISTRY_PORT = 1099;

	private Utilisateur utilisateur;
	private InterfaceServeurRmi serv;
	private InterfaceClientRmi cl;
	
	public ClientRMI(String login, String motDePasse) {

		try {


			// obtention de l'objet distant à partir de son nom (lookup)
			 Registry registry = LocateRegistry.getRegistry(REGISTRY_PORT);
			 Remote r = registry.lookup("fram");
			if (r instanceof InterfaceServeurRmi) {
				this.serv = (InterfaceServeurRmi)r;
				Serveur serveur = ((InterfaceServeurRmi) r).getServeur();
				if (serveur.connexion(login, motDePasse,this)) {		
					
					this.utilisateur = serveur.getUtilisateurInscrit(login);
					String url = "rmi://" + InetAddress.getLocalHost().getHostAddress() + "/" + this.utilisateur.getLogin();
					System.out.println("Enregistrement de l'objet client avec l'url : " + url);
					cl = new ClientRmiImpl(this.utilisateur);
					try {
						Naming.rebind(url, cl);
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// si l'utilisateur n 'est pas présent dans la liste des connectés
					if(serveur.utilisateurConnecte(this) == null){
						((InterfaceServeurRmi) r).ajouterClient(this);
					}
					else{
						System.out.println("déjà connecté");
						System.out.println(this.getUtilisateur().getLogin());
					}
					System.out.println(utilisateur.getLogin()+ " se connecte");
				} else {
					//throw new ErreurConnexion("Login mot de passe invalide");
					throw new Ensure("Login mot de passe invalide");
					
				}
			}
			
			


		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	final public static int BUF_SIZE = 1024 * 64;

	/**
	 * Methode de copie de flux par byte
	 * 
	 * @param inStream
	 *            : le flux d'entr�e
	 * @param outStream
	 *            : le flux de sortie
	 * @throws IOException
	 */

	public static void copie(InputStream inStream, OutputStream outStream)
			throws IOException {

		byte[] b = new byte[BUF_SIZE];
		int len;
		// Parcour du flux d'entree et copie dans le flux sortie
		while ((len = inStream.read(b)) >= 0) {
			// outStream.write(b, 0, len);
			outStream.write(b);
		}
		inStream.close();
		outStream.close();
	}

	/**
	 * Methode qui permet de telecharger un fichier depuis le serveur
	 * 
	 * @param server
	 *            : le serveurRMI
	 * @param source
	 *            : le fichier a telecharger
	 * @param destination
	 *            : le fichier de sortie
	 * @throws IOException
	 */

	public static void telecharger(ServeurRMI server, File source,
			File destination) throws IOException {
		copie(server.getInputStream(source,server.getServeur()), new FileOutputStream(destination));
	}

	/**
	 * Methode qui permet de charger un fichier sur le serveur
	 * 
	 * @param server
	 *            : le serveurRMI
	 * @param source
	 *            : le fichier a charger 2 * @param destination : le fichier de
	 *            sortie
	 * @throws IOException
	 */

	public void charger(ServeurRMI server, File source,ArrayList<Utilisateur> utilisateurs, ArrayList<Groupe> groupes, Document document, Date dateFinPublication)
			throws IOException {
		//"/git/miage_projet_framework/docServeur/"+this.getUtilisateur().getLogin()+
		String dest ="/git/miage_projet_framework/docServeur/"+this.getUtilisateur().getLogin()+"/"+source.getName();
		System.out.println(dest);
		File destination = new File(dest);
		
		//if (r instanceof InterfaceServeurRmi) {
			
			Serveur serveur = getServeurRmiImpl().getServeur();
			copie(new FileInputStream(source), server.getOutputStream(destination,serveur));
			this.getUtilisateur().publierUnDocument(utilisateurs, groupes, document, dateFinPublication);
			getServeurRmiImpl().ajouterPublication(new Publication(new Date(), dateFinPublication, utilisateurs, groupes, this.getUtilisateur(), document));
		//}
	}


	/*public String[] getClients() {
		String[] clients = null;
		String[] result = null;
		int indice = 0;
		try {
			//clients = registry.list();
			result = new String[clients.length];
			for (int i = 0; i < clients.length; i++) {
				if (!clients[i].equals("fram")) {
					result[indice] = clients[i];
					indice++;
				}
			}
		} catch (AccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}*/

	
	public ArrayList<ClientRMI> getUtilisateurs()
	{
		ArrayList<ClientRMI> clients = null;
		
		
			try {
				clients = getServeurRmiImpl().getClientsconnectes();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return clients;
	}
	
	/**
	 * @return Utilisateur : l'utilisateur associer à ce client 
	 */
	
	public Utilisateur getUtilisateur(){
		return this.utilisateur;
	}
	
	/**
	 * Deconnecte le client
	 */
	
	public void deconnexion()
	{
		
			try {
				// Suppression de la liste des connectés sur le serveur
				getServeurRmiImpl().deconnexion(this);		
				try {
					// Suppression de l'objet client distant sur le registry
					LocateRegistry.getRegistry(REGISTRY_PORT).unbind(getUtilisateur().getLogin());
				} catch (NotBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	
	
	/**
	 * Affiche l'ensemble des publications visibles pour un utilisateur
	 * @return ArrayList<Publication> : return publication visibles
	 */
	public ArrayList<Publication> getPublicationsVisibles()
	{
		ArrayList<Publication> publicationsVisibles = null;
		
			try {
				publicationsVisibles = getServeurRmiImpl().getServeur().getPublicationsVisibles(this.getUtilisateur());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		
		
		return publicationsVisibles;
	}
	
	
	
	/**
	 * Envoie un message 
	 * @param message
	 */
	public void envoyerMessagePrive(String message,ClientRMI destinataire){

			try {
				getClientRmiImpl().envoyerMessage(message, this);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	/**
	 * Envoie un message 
	 * @param message
	 */
	public void envoyerMessage(String message){

			try {
				getClientRmiImpl().envoyerMessage(message, this);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	}
	
	/**
	 * Reception d'un message
	 * @param message
	 * @param expediteur
	 */
	
	public void recevoirMessage(String message){
		try {
			getClientRmiImpl().recevoirMessage(message, this);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Renvoie l'objet distant du client
	 * @return cl
	 */
	public InterfaceClientRmi getClientRmiImpl(){
		return cl;
	}
	
	/**
	 * Renvoie l'objet distant du serveur 
	 * @return serv
	 */
	public InterfaceServeurRmi getServeurRmiImpl(){
		return serv;
	}
	
	
	
}
