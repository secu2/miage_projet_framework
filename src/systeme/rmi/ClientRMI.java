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
	 * Constructeur de la classe ClientRMI
	 * @param client 
	 * @throws RemoteException
	 */
	public ClientRMI(Client client) throws RemoteException{
		this.client = client;
	}

	/** Envoie un message à tous les utilisateurs connectés sur le serveur
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
	 * Envoyer un message privé
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
	
	/**
	 * Envoie un message à plusieurs participants d'une conversation (utilisations et/ou groupes)
	 * @param message
	 */
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
 * Méthode permettant de traiter le message distribué par le serveur aux différents clients connectés
 * @param Message message : le message à recevoir 
 */
	public void recevoirMessage(Message message) throws RemoteException{
		if(message instanceof MessagePrive){
			if(message.getExpeditaire().equals(client.getUtilisateur().getLogin())){
				vue.getListeMessage().add("[Message privé envoyé à  " + ((MessagePrive)message).getDestinataire() + "[" + message.getDateEmission().getHours() + ":" +  message.getDateEmission().getMinutes() + "] " + message.getMessage());
			}
			else{
				vue.getListeMessage().add("[Message privé de " + message.getExpeditaire() + "[" + message.getDateEmission().getHours() + ":" +  message.getDateEmission().getMinutes() + "] " + message.getMessage());
			}
			}else
		{
			
				vue.getListeMessage().add("[" + message.getDateEmission().getHours() + ":" +  message.getDateEmission().getMinutes() + "] "  + message.getExpeditaire() + " : " + message.getMessage());
			
		}
	}
	
	/**
	 * Renvoie la vue de l'application de chat
	 */
	public Vue getVue()throws RemoteException{
		return vue;
	}
	
	/**
	 * Affecte la vue de l'application de chat
	 */
	public void setVue(Vue vue)throws RemoteException{
		this.vue = vue;
	}

	/**
	 * Actualise la liste des clients connecté et déconnectés dans la vue du chat graphique
	 */
	public void actualiserListes() throws RemoteException {
		getVue().afficheUtilisateursCo(client);
		getVue().afficheUtilisateursDeco(client);
		
	}
	
	/**
	 * Lance la raquête d'actualisation de la liste des utilisateurs connectés et déconnectés dans la vue du chat graphique
	 * pour chaque client connecté
	 * @throws RemoteException
	 */
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
