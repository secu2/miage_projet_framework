package systeme.rmi;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import systeme.Client;

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
	private Client client;
	
	/**
	 * 
	 * @throws RemoteException
	 */
	public ClientRMI(Client client) throws RemoteException{
		this.client = client;
	}

	/** permet d'envoyer un message
	 * @param Message message : le message à envoyer
	 */
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

	/**
	 * permet d'envoyer un message privé
	 * @param Message message : le message à envoyer 
	 */
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

/**
 * permet de recevoir un message
 * @param Message message : le message à recevoir 
 */
	public void recevoirMessage(Message message) throws RemoteException{
		vue.getListeMessage().add("[" + message.getDateEmission().getHours() + ":" +  message.getDateEmission().getMinutes() + "] "  + message.getExpeditaire() + " : " + message.getMessage());
	}
	
	public void recevoirMessagePrive(MessagePrive message)
			throws RemoteException {
		
		vue.getListeMessage().add("[Message privé de " + message.getExpeditaire() + "[" + message.getDateEmission().getHours() + ":" +  message.getDateEmission().getMinutes() + "] " + message.getMessage());
		
	}

	public Vue getVue()throws RemoteException{
		return vue;
	}
	
	public void setVue(Vue vue)throws RemoteException{
		this.vue = vue;
	}

	@Override
	public void actualiserListes() throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("je dois actualiser :(");
		System.out.println(client);
		vue.afficheUtilisateursCo(client);
		vue.afficheUtilisateursDeco(client);
		
	}
	
	public void actualiserListesUtilisateurs() throws RemoteException{
		Registry registry = LocateRegistry.getRegistry(REGISTRY_PORT);
		 Remote r;
		try {
			r = registry.lookup("fram");
			if (r instanceof InterfaceServeurRmi)
			{
				try {
					((InterfaceServeurRmi) r).actualiserListesUtilisateurs();				
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



	



	
	
	
}
