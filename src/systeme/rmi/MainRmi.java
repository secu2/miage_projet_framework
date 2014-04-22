package systeme.rmi;

import java.io.File;
import java.io.IOException;

public class MainRmi {

	public static void main(String[] args) {
		
		ServeurRMI srv = new ServeurRMI();
		ClientRMI clt = new ClientRMI();
		ClientRMI clt2 = new ClientRMI();
		File testFile = new File("docServeur/image.jpg");
        long len = testFile.length();
        
        long t;
        t = System.currentTimeMillis();
        try {
			clt.telecharger(srv, testFile, new File("C:/Users/Mohamed/Desktop/download.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        try {
			clt.charger(srv, new File("C:/Users/Mohamed/Desktop/download.jpg"), new File("docServeur/test1.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        
        
        
        
        
	}

}
