package systeme.rmi;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import modules.gestionUtilisateur.Utilisateur;


//création de l'interface propre au RMI

public interface InterfaceRmi extends Remote {
	 
	public String getTest() throws RemoteException;
	
	
}


