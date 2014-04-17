package systeme;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseDeDonnees {
	Connection connection;
	

	/**
	 * Créer une base de données sqlite de nom 'nomFichBD'.db et renvoie son objet Connection
	 * @param nomFichBD
	 * @return connection : objet de type Connection permettant la manipulation de la base de données
	 * @throws ClassNotFoundException
	 */
	public Connection creerAccesBD(String nomFichBD) throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		Connection connection = null;

		try {
			connection = DriverManager.getConnection("jdbc:sqlite:" + nomFichBD + ".db");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return connection;
	}
	
	
	
	
	
}
