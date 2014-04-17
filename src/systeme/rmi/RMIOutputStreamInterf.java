package systeme.rmi;

import java.io.IOException;
import java.rmi.RemoteException;

public interface RMIOutputStreamInterf {
	public void write(int b) throws IOException, RemoteException;
	public void write(byte[] b, int off, int len) throws IOException, RemoteException;
	public void close() throws IOException, RemoteException;
}
