package systeme.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public class ClientRMI {
	
	public static void main(String[] args)
	{
		System.out.println("Start client");
		
		try {
			
			// obtention de l'objet distant à partir de son nom (lookup)
			Remote r = Naming.lookup("rmi://152.77.116.114/fram");
			//System.out.println(r);
			
			if (r instanceof InterfaceRmi) {
				
				String rslt = ((InterfaceRmi) r).getTest();
				System.out.println("chaine envoyee = "+ rslt);
			}
			
			System.out.println("End client");
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
