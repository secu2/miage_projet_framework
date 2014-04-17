package systeme;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class BaseDeDonnees {
	
	Connection connection;
	

	/**
	 * Créer une base de données sqlite de nom 'nomFichBD'.db et renvoie son objet Connection
	 * @param nomFichBD
	 * @return connection : objet de type Connection permettant la manipulation de la base de données
	 * @throws ClassNotFoundException
	 * @throws IOException 
	 */
	public BaseDeDonnees(String nomFichBD) throws ClassNotFoundException, IOException {
		Class.forName("org.sqlite.JDBC");
				
		
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:" + nomFichBD + ".sqlite");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void chargerFichierSQL(String path) throws FileNotFoundException, SQLException{
		File fichierSQL = new File(path);
		Scanner scanner = new Scanner(fichierSQL);
		String sql = new String();
		while(scanner.hasNext()){
			sql += " "+scanner.next();
		}
		Statement statement = connection.createStatement();
		System.out.println(sql);
		statement.executeUpdate(sql);
		statement.close();
		connection.close();
		
	}
	
	/**
	public void creerTable(String nomTable , ArrayList<Attribut> attributs ){
		
	}
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws SQLException */
	
	public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException {
		BaseDeDonnees bd = new BaseDeDonnees("database");
		bd.chargerFichierSQL("data_framework_structure.sqlite");
		
		
	}
	
	
	
	
	
}
