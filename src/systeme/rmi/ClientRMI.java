package systeme.rmi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

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
	private Registry registry;
	private Remote r;
	
	public ClientRMI(String login, String motDePasse) {

		try {


			// obtention de l'objet distant à partir de son nom (lookup)
			 registry = LocateRegistry.getRegistry(REGISTRY_PORT);
			 r = registry.lookup("fram");
			if (r instanceof InterfaceRmi) {
				Serveur serveur = ((InterfaceRmi) r).getServeur();
				if (serveur.connexion(login, motDePasse,this)) {
					this.utilisateur = serveur.getUtilisateurInscrit(login);
					((InterfaceRmi) r).ajouterClient(this);
					System.out.println(serveur.toString());
					System.out.println("Start00 client");
				} else {
					throw new ErreurConnexion("Login mot de passe invalide");
					
				}
			}
			
			


		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ErreurConnexion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	


	final public static int BUF_SIZE = 1024 * 64;

	/**
	 * Methode de copie de flux par byte
	 * 
	 * @param inStream
	 *            : le flux d'entrï¿½e
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
		copie(server.getInputStream(source), new FileOutputStream(destination));
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

	public static void charger(ServeurRMI server, File source, File destination)
			throws IOException {
		copie(new FileInputStream(source), server.getOutputStream(destination));
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
		
		if (r instanceof InterfaceRmi)
		{
			try {
				clients = ((InterfaceRmi) r).getClientsconnectee();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return clients;
	}
	
	public Utilisateur getUtilisateur(){
		return this.utilisateur;
	}

	
}
