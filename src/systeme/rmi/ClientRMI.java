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
	/*
	 *Permet l'envoie d'un fichier sur le serveur  
	 */
	final public static int BUF_SIZE = 1024 * 64;
    
    public static void copy(InputStream in, OutputStream out) 
            throws IOException {
        System.out.println("using byte[] read/write");
        byte[] b = new byte[BUF_SIZE];
        int len;
        while ((len = in.read(b)) >= 0) {
            out.write(b, 0, len);
        }
        in.close();
        out.close();
    }
    
    public static void download(ServeurRMI server, File src, 
            File dest) throws IOException {
        copy (server.getInputStream(src), 
        new FileOutputStream(dest));
    }
    
    
    public static void upload(ServeurRMI server, File src, 
            File dest) throws IOException {
        copy (new FileInputStream(src), 
        server.getOutputStream(dest));
    }
    
}