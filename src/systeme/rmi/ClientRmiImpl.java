package systeme.rmi;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import modules.gestionUtilisateur.Utilisateur;

public class ClientRmiImpl  extends UnicastRemoteObject implements InterfaceClientRmi,Serializable{
	static int REGISTRY_PORT = 1099;

	private Utilisateur util;
	
	public ClientRmiImpl(Utilisateur util) throws RemoteException{
		this.util = util;
	}
	
	public Utilisateur getUtilisateur() throws RemoteException{
		return this.util;
	}
	
	public void envoyerMessage(String message, ClientRMI expeditaire) throws RemoteException{
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

	
	public void envoyerMessagePrive(String message, ClientRMI expeditaire, ClientRMI destinataire) throws RemoteException{
		Registry registry = LocateRegistry.getRegistry(REGISTRY_PORT);
		 Remote r;
		try {
			r = registry.lookup("fram");
			if (r instanceof InterfaceServeurRmi)
			{
				try {
					((InterfaceServeurRmi) r).getServeur().distribuerMessagePrive(message,expeditaire,destinataire);				
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
