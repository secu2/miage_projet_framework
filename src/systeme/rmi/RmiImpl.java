package systeme.rmi;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

//classe héritante de l'interface et définir les méthodes propres au serveur.
public class RmiImpl extends UnicastRemoteObject implements InterfaceRmi {

	private static final long serialVersionUID = 2674880711467464646L;

	protected RmiImpl() throws RemoteException {
		super();
	}

	public String getTest() throws RemoteException {
		System.out.println("Invocation de la méthode getInformation()");
		return "Momo t'es moche ";
	}

	public byte[] readBytes(int len) throws IOException, RemoteException {
		return null;
	}

	public int read() throws IOException, RemoteException {
		return 0;
	}

	public void close() throws IOException, RemoteException {
	}

}
