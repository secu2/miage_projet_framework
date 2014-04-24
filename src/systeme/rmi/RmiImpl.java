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
import modules.gestionUtilisateur.Utilisateur;

//classe héritante de l'interface et définir les méthodes propres au serveur.
public class RmiImpl extends UnicastRemoteObject implements InterfaceRmi {

	private static final long serialVersionUID = 2674880711467464646L;

	private OutputStream out;
	private InputStream in;
	private Serveur serveur;

	public RmiImpl(OutputStream out, InputStream in) throws RemoteException {
		super();
		this.out = out;
		this.in = in;
	}

	public RmiImpl(Serveur serveur) throws RemoteException {
		super();
		this.out = null;
		this.in = null;
		this.serveur = serveur;

	}

	public RmiImpl(OutputStream out) throws RemoteException {
		super();
		this.out = out;
	}

	public RmiImpl(InputStream in) throws RemoteException {
		super();
		this.in = in;
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
		getServeur().supprimerUnClient(c);
	}
	
	
	
}

