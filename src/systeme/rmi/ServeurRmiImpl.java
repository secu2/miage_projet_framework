package systeme.rmi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import systeme.Client;
import systeme.Serveur;
import systeme.tools.Encryptage;
import modules.documents.social.Publication;
import modules.gestionUtilisateur.Utilisateur;

//classe héritante de l'interface et définir les méthodes propres au serveur.
public class ServeurRmiImpl extends UnicastRemoteObject implements InterfaceServeurRmi {

	private static final long serialVersionUID = 2674880711467464646L;

	private OutputStream out;
	private InputStream in;
	private Serveur serveur;

	public ServeurRmiImpl(OutputStream out, InputStream in,Serveur serveur) throws RemoteException {
		super();
		this.out = out;
		this.in = in;
		this.serveur=serveur;
	}

	public ServeurRmiImpl(Serveur serveur) throws RemoteException {
		super();
		this.out = null;
		this.in = null;
		this.serveur = serveur;

	}

	public ServeurRmiImpl(OutputStream out,Serveur serveur) throws RemoteException {
		super();
		this.out = out;
		this.serveur=serveur;
	}

	public ServeurRmiImpl(InputStream in,Serveur serveur) throws RemoteException {
		super();
		this.in = in;
		this.serveur=serveur;
	}

	public String getTest() throws RemoteException {
		System.out.println("Invocation de la méthode getInformation()");
		return "Momo t'es moche ";
	}

	public OutputStream getOut() {
		return this.out;
	}

	public void setOut(OutputStream out) {
		this.out = out;
	}

	public InputStream getIn() {
		return this.in;

	}

	public void setIn(InputStream in) {
		this.in = in;
	}

	public Serveur getServeur() throws RemoteException
	{
		return this.serveur;
	}
	
	public ArrayList<ClientRMI> getClientsconnectes() throws RemoteException
	{
		return getServeur().getUtilisateursConnectes();
	}
	
	public boolean connexion(String login, String motDepasse ,ClientRMI c) throws RemoteException
	{
		return getServeur().connexion(login, motDepasse, c);
	}

	public void ajouterClient(ClientRMI c) throws RemoteException {
		getServeur().ajouterClient(c);

	}

	public void deconnexion(ClientRMI c) throws RemoteException {
		System.out.println(c.toString());
		getServeur().supprimerUnClient(c);
	}
	
	public void envoiMessage(String s,String c)  throws RemoteException {
		getServeur().distribuerMessage(s,c);
	}
	
	public void ajouterPublication(Publication publication)throws RemoteException
	{
		getServeur().AddPublication(publication);
	}


	
	
}

