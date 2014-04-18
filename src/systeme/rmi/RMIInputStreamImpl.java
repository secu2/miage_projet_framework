package systeme.rmi;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIInputStreamImpl implements Remote {
	private InputStream in;
    private byte[] b;
    public RMIInputStreamImpl(InputStream in) throws IOException {
        this.in = in;
        UnicastRemoteObject.exportObject(this, 1099);
    }
    /**
     * methode de fermeture du flux d'entree
     * @throws IOException
     * @throws RemoteException
     */
    public void fermeture() throws IOException, RemoteException {
        in.close();
    }
    /**
     * lecture d'un octet
     * @return la valeur du bit
     * @throws IOException
     * @throws RemoteException
     */
    public int lecture() throws IOException, RemoteException {
        return in.read();
    }
    /**
	 * lecture par taille 
	 * @param taille : taille de bytes à lire
	 * @return : tableau 
	 * @throws IOException
	 * @throws RemoteException
	 */
    public byte[] lectureBytes(int taille) throws IOException, RemoteException {
       
    	//initialisation du tableau à la taille du buffer si il n'est pas initialisé
    	if (b == null || b.length != taille)
            b = new byte[taille];
            
        int len2 = in.read(b);
        // si la taille est égale à 0 -> fin de fichier
        if (len2 < 0)
            return null; // Fin de fichier
        // si la taille du tableau definie est plus petite que le flux d'entree alors on copie le flux d'entree dans un tableau plus grand
        if (len2 != taille) {
           
            byte[] b2 = new byte[len2];
            System.arraycopy(b, 0, b2, 0, len2);
            return b2;
        }// return les tableau de bit lus
        else
            return b;
    }
}
