package systeme.rmi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

//classe héritante de l'interface et définir les méthodes propres au serveur.
public class RmiImpl extends UnicastRemoteObject implements InterfaceRmi {

	private static final long serialVersionUID = 2674880711467464646L;

	private OutputStream out;
	private InputStream in;

	public RmiImpl(OutputStream out,InputStream in) throws RemoteException {
		super();
		this.out = out;
		this.in = in;
	}
	
	public RmiImpl() throws RemoteException {
		super();
		this.out = null;
		this.in = null;
		
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

	public byte[] readBytes(int len) throws IOException, RemoteException {
		return null;
	}

	public int read() throws IOException, RemoteException {
		return 0;
	}

	public void close() throws IOException, RemoteException {
	}

	public OutputStream getOut() {
		return this.out;
	}
	
	public void setOut( OutputStream out)
	{
		this.out=out;
	}
	
	public InputStream getIn()
	{
		return this.in;
		
	}
	public void setIn( InputStream in)
	{
		this.in = in ;
	}
	

}
