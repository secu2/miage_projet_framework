package systeme.rmi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;



public class ServeurRMI {
	static int REGISTRY_PORT = 1099;
	
	
	public ServeurRMI() {
		// TODO Auto-generated method stub
		try {
			LocateRegistry.createRegistry(REGISTRY_PORT);
			 
			/*System.out.println("Mise en place du Security Manager ...");
			if (System.getSecurityManager() == null) {
				System.setSecurityManager(new RMISecurityManager());
			}*/
			
			RmiImpl informationImpl = new RmiImpl();
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
