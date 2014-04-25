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
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.security.NoSuchAlgorithmException;

import modules.gestionUtilisateur.Utilisateur;
import systeme.Serveur;
import systeme.tools.Encryptage;



public class ServeurRMI implements Serializable {
	static int REGISTRY_PORT = 1099;
	private Serveur serveur; 
	
	public ServeurRMI(Serveur serveur) {
		// TODO Auto-generated method stub
		try {
			
			this.serveur=serveur;
			LocateRegistry.createRegistry(REGISTRY_PORT);
	
			
			ServeurRmiImpl informationImpl = new ServeurRmiImpl(serveur);
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
	
	public InputStream getInputStream(File f,Serveur serv) throws IOException {
	   // return new RMIInputStream(new RMIInputStreamImpl(new FileInputStream(f)));
		 return  new ServeurRmiImpl(new FileInputStream(f),serv).getIn();
		 
	}
	
	public OutputStream getOutputStream(File f,Serveur serv) throws IOException {
	  // return new RMIOutputStream(new RMIOutputStreamImpl(new FileOutputStream(f)));
	    return new ServeurRmiImpl(new FileOutputStream(f),serv).getOut();   
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

public Serveur getServeur() {
	return serveur;
}

public void setServeur(Serveur serveur) {
	this.serveur = serveur;
}
	
	
}
