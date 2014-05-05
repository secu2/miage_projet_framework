package systeme;

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
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.security.NoSuchAlgorithmException;

import modules.gestionUtilisateur.Utilisateur;
import systeme.rmi.ServeurRMI;
import systeme.tools.Encryptage;



public class Serveur implements Serializable {
	static int REGISTRY_PORT = 1099;
	private ServeurRMI serveur; 
	
	/**
	 * Constructeur du serveur : 
	 * Créer un objet serveur  qui lancera le serveur localement
	 */
	public Serveur() {
		// TODO Auto-generated method stub
		try {
			
			LocateRegistry.createRegistry(REGISTRY_PORT);
	
			
			ServeurRMI informationImpl = new ServeurRMI(this);
			serveur = informationImpl;
			String url = "rmi://" + InetAddress.getLocalHost().getHostAddress() + "/fram";
			System.out.println("Enregistrement de l'objet avec l'url : " + url);
			Naming.rebind(url, informationImpl);
			
			
		
			 
			System.out.println("Serveur lancé");
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * Constructeur du serveur : 
	 * Créer un objet serveur où l'on aura précisé l'adresse
	 * @param adresse : adresse du serveur
	 */
	public Serveur(String adresse) {
		// TODO Auto-generated method stub
		try {
			
			LocateRegistry.createRegistry(REGISTRY_PORT);
	
			
			ServeurRMI informationImpl = new ServeurRMI(this);
			serveur = informationImpl;
			String url = "rmi://" + adresse + "/fram";
			System.out.println("Enregistrement de l'objet avec l'url : " + url);
			Naming.rebind(url, informationImpl);
			
			
		
			 
			System.out.println("Serveur lancé");
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
	}
	

/**
 * Provoque l'arret du serveur
 */
	public void stop()
	{
		try {
			Naming.unbind("rmi://" + InetAddress.getLocalHost().getHostAddress() + "/fram");
			//UnicastRemoteObject.unexportObject((Remote) this, true);
			System.out.println("Arret du serveur");
			System.exit(0);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

public ServeurRMI getServeur() {
	return serveur;
}

public void setServeur(ServeurRMI serveur) {
	this.serveur = serveur;
}
	
	
}
