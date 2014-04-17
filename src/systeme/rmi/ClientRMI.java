package systeme.rmi;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class ClientRMI {
	
	public static void main(String[] args)
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
}
