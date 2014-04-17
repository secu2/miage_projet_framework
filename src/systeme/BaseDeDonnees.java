package systeme;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import modules.gestionUtilisateur.Utilisateur;

public class BaseDeDonnees {
	
	// Objet connection permettant l'accès à la base de données
	Connection connection;
	

	/**
	 * Créer/Charge une base de données sqlite de nom 'nomFichBD'.sqlite et renvoie son objet Connection
	 * @param nomFichBD : nom de la base de données
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
	
	
	/**
	 * Créer/Charge une base de données sqlite ayant pour nom database.sqlite
	 * et l'initialise avec la structure de données par défaut si elle n'existe pas
	 * @return connection : objet de type Connection permettant la manipulation de la base de données
	 * @throws ClassNotFoundException
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public BaseDeDonnees() throws ClassNotFoundException, IOException, SQLException {
		Class.forName("org.sqlite.JDBC");
		boolean newDB = true;
		File dbFile = new File("database.sqlite");
		// test l'existence du fichier database.sqlite avant le chargement avec le driverManager
		if(dbFile.exists()){
			newDB = false;
		}
		
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:database.sqlite"); //Reminder: cette ligne cree automatiquement le fichier si il n'éxiste pas
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(newDB){
			this.chargerFichierSQL("data_framework_structure.sqlite");
		}
		
	}
	
	/**
	 * Méthode permettant de charger dans la base de données, le fichier sql situé à path
	 * @param path
	 * @throws SQLException
	 * @throws IOException
	 */
	public void chargerFichierSQL(String path) throws SQLException, IOException{
		InputStream inputstream = new FileInputStream(path);
		String sql = new String();
		int data = inputstream.read();
		while(data != -1) {
		  sql += (char)data;
		  data = inputstream.read();
		}
		inputstream.close();
		
		Statement statement = connection.createStatement();
		System.out.println(sql);
		statement.executeUpdate(sql);
		statement.close();
		connection.close();	
	}
	
	/**
	 * Créer un utilisateur dans la BD avec son login et son mot de passe
	 * @param login
	 * @param motDePasse
	 * @throws SQLException
	 */
	public void sauvegarderUtilisateur(String login, String motDePasse) throws SQLException{
		String sql = "INSERT INTO VALUES (nom_utilisateur,mot_de_passe) VALUES (?,?)";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setString(0, login);
		st.setString(1, motDePasse);
		st.execute();
	}
	
	/**
	 * Supprime un utilisateur à partir de son login
	 * @param login
	 * @throws SQLException 
	 */
	public void supprimerUtilisateur(String login) throws SQLException{
		String sql = "DELETE FROM Utilisateur WHERE nom_utilisateur = ?";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setString(0, login);
		st.execute();
	}
	
	/**
	public void creerTable(String nomTable , ArrayList<Attribut> attributs ){
		
	}
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws SQLException */
	
	/**
	 * Méthode de test de la BD
	 * @param args
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws SQLException
	 */
	public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException {
		BaseDeDonnees bd = new BaseDeDonnees();
	}
	
	

	
	
}
