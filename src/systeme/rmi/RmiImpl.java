package systeme.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


//classe h�ritante de l'interface et d�finir les m�thodes propres au serveur.
public class RmiImpl extends UnicastRemoteObject implements InterfaceRmi {

	private static final long serialVersionUID = 2674880711467464646L;

	protected RmiImpl() throws RemoteException {
		super();
	}

	public String getTest() throws RemoteException {
		System.out.println("Invocation de la m�thode getInformation()");
		return "Momo t'es moche ";
	}
}
