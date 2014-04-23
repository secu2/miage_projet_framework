package systeme.rmi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import systeme.Client;
import systeme.Serveur;

/**
 * @author chaiebm
 * 
 */
public class ClientRMI {
	static int REGISTRY_PORT = 1099;


	public ClientRMI(String login, String motDePasse) {

		try {


			// obtention de l'objet distant à partir de son nom (lookup)
			Registry registry = LocateRegistry.getRegistry(REGISTRY_PORT);
			Remote r = registry.lookup("fram");
			if (r instanceof RmiImpl) {
				//Serveur serveur = ((RmiImpl) r).getServeur();
				System.out.println("Start00 client");
				if (((RmiImpl) r).connexion(login, motDePasse)) {
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

	


	public ArrayList<Client> getClientsConnectee() {
		ArrayList<Client> clientsConnectes = null;
		
		try {
			Registry registry = LocateRegistry.getRegistry(REGISTRY_PORT);
			Remote r = registry.lookup("fram");
			System.out.println(r);
			if (r instanceof RmiImpl) {
				System.out.println("ldl");
				try {
					clientsConnectes = ((RmiImpl) r).getClientsconnectee();
				} catch (RemoteException e) {

					e.printStackTrace();
				}
			}
		} catch (AccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return clientsConnectes;
	}

}
