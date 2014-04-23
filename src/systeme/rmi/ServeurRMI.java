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
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
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
	
			
			RmiImpl informationImpl = new RmiImpl(serveur);
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
	
	public InputStream getInputStream(File f) throws IOException {
	   // return new RMIInputStream(new RMIInputStreamImpl(new FileInputStream(f)));
		 return  new RmiImpl(new FileInputStream(f)).getIn();
		 
	}
	
	public OutputStream getOutputStream(File f) throws IOException {
	  // return new RMIOutputStream(new RMIOutputStreamImpl(new FileOutputStream(f)));
	    return new RmiImpl(new FileOutputStream(f)).getOut();   
	}
	
	
	
}
