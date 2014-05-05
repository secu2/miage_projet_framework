package systeme.rmi;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import application.chat.Vue;

import modules.chat.Message;
import modules.chat.MessageConversation;
import modules.chat.MessagePrive;
import modules.gestionUtilisateur.Utilisateur;

//TODO : A commenter
public class ClientRMI  extends UnicastRemoteObject implements InterfaceClientRmi,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static int REGISTRY_PORT = 1099;
	private Vue vue;
	
	/**
	 * 
	 * @throws RemoteException
	 */
	public ClientRMI() throws RemoteException{
	}
	
	
	public void envoyerMessage(Message message) throws RemoteException{
		 Registry registry = LocateRegistry.getRegistry(REGISTRY_PORT);
		 Remote r;
		try {
			r = registry.lookup("fram");
			if (r instanceof InterfaceServeurRmi)
			{
				try {
					((InterfaceServeurRmi) r).distribuerMessage(message);				
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

	
	public void envoyerMessagePrive(MessagePrive message) throws RemoteException{
		Registry registry = LocateRegistry.getRegistry(REGISTRY_PORT);
		 Remote r;
		try {
			r = registry.lookup("fram");
			if (r instanceof InterfaceServeurRmi)
			{
				try {
					((InterfaceServeurRmi) r).distribuerMessagePrive(message);				
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
	
	public void envoyerMessageConversation(MessageConversation message) throws RemoteException{
		Registry registry = LocateRegistry.getRegistry(REGISTRY_PORT);
		 Remote r;
		try {
			r = registry.lookup("fram");
			if (r instanceof InterfaceServeurRmi)
			{
				try {
					((InterfaceServeurRmi) r).distribuerMessageConversation(message);				
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


	public void recevoirMessage(Message message) throws RemoteException{
		vue.getListeMessage().add("[" + message.getDateEmission().getHours() + ":" +  message.getDateEmission().getMinutes() + "] "  + message.getExpeditaire() + " : " + message.getMessage());
	}
	

	public Vue getVue()throws RemoteException{
		return vue;
	}
	
	public void setVue(Vue vue)throws RemoteException{
		this.vue = vue;
	}



	
	
	
}
