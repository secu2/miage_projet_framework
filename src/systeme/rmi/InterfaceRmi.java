package systeme.rmi;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;


//cr�ation de l'interface propre au RMI

public interface InterfaceRmi extends Remote {
	 
	public String getTest() throws RemoteException;
}


