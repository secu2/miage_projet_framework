package systeme.rmi;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import modules.gestionUtilisateur.Utilisateur;

//TODO : A commenter
public class ClientRmiImpl  extends UnicastRemoteObject implements InterfaceClientRmi,Serializable{
	static int REGISTRY_PORT = 1099;

	
	/**
	 * 
	 * @throws RemoteException
	 */
	public ClientRmiImpl() throws RemoteException{
	}
	
	
	public void envoyerMessage(String message, String expeditaire) throws RemoteException{
		 Registry registry = LocateRegistry.getRegistry(REGISTRY_PORT);
		 Remote r;
		try {
			r = registry.lookup("fram");
			if (r instanceof InterfaceServeurRmi)
			{
				try {
					((InterfaceServeurRmi) r).getServeur().distribuerMessage(message,expeditaire);				
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (NotBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

	
	public void envoyerMessagePrive(String message, String loginExpediteur, String loginDestinataire) throws RemoteException{
		Registry registry = LocateRegistry.getRegistry(REGISTRY_PORT);
		 Remote r;
		try {
			r = registry.lookup("fram");
			if (r instanceof InterfaceServeurRmi)
			{
				try {
					((InterfaceServeurRmi) r).getServeur().distribuerMessagePrive(message,loginExpediteur,loginDestinataire);				
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (NotBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	
	public void recevoirMessage(String message, ClientRMI expeditaire) throws RemoteException{
		System.out.println(expeditaire.getUtilisateur().getLogin() + " : " + message);
	}


	
	
	
}
