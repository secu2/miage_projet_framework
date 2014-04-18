package systeme.rmi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


/**
 * @author chaiebm
 *
 */
public class ClientRMI {
	
	public ClientRMI()
	{
		System.out.println("Start00 client");
		
		try {
			
			// obtention de l'objet distant à partir de son nom (lookup)
			Registry registry = LocateRegistry.getRegistry(1099);
    		Remote r = registry.lookup("fram");
			//System.out.println(r);
			
			if (r instanceof InterfaceRmi) {
				
				String rslt = ((InterfaceRmi) r).getTest();
				System.out.println("chaine envoyee = "+ rslt);
			}
			
			System.out.println("End client");
			
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	final public static int BUF_SIZE = 1024 * 64;
    
	
	
	/**
	 * Methode de copie de flux par byte
	 * @param inStream : le flux d'entrée
	 * @param outStream : le flux de sortie 
	 * @throws IOException  
	 */

    public static void copie(InputStream inStream, OutputStream outStream) throws IOException{
    	
        byte[] b = new byte[BUF_SIZE];
        int len;
        //Parcour du flux d'entree et copie dans le flux sortie
        while ((len = inStream.read(b)) >= 0) {
        	outStream.write(b, 0, len);
        }
        inStream.close();
        outStream.close();
    }
    /**
     * Methode qui permet de telecharger un fichier depuis le serveur
     * @param server : le serveurRMI
     * @param source : le fichier a telecharger
     * @param destination : le fichier de sortie
     * @throws IOException
     */
    
    public static void telecharger(ServeurRMI server, File source,  File destination) throws IOException {
        copie (server.getInputStream(source), new FileOutputStream(destination));
    }
    /**
     * Methode qui permet de charger un fichier sur le serveur
     * @param server : le serveurRMI
     * @param source : le fichier a charger
     * @param destination : le fichier de sortie
     * @throws IOException
     */
    
    public static void charger(ServeurRMI server, File source,  File destination) throws IOException {
    	copie (new FileInputStream(source),server.getOutputStream(destination));
    }
    
}