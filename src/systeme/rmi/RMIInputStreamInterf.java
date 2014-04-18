package systeme.rmi;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIInputStreamInterf extends Remote {
	/**
	 * lecture par taille de bytes
	 * @param taille : taille de byte à lire
	 * @return : tableau 
	 * @throws IOException
	 * @throws RemoteException
	 */
    public byte[] lectureBytes(int taille) throws IOException, RemoteException;
    /**
     * lecture
     * @return la valeur du bit
     * @throws IOException
     * @throws RemoteException
     */
    public int lecture() throws IOException, RemoteException;
    /**
     * methode de fermeture
     * @throws IOException
     * @throws RemoteException
     */
    public void fermeture() throws IOException, RemoteException;
}
