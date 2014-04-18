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
import java.util.TreeMap;

import modules.gestionUtilisateur.Utilisateur;

/**
 * BaseDeDonnees.java
 * @author Never
 *
 */
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
		String sql = "INSERT INTO Utilisateur (nom_utilisateur,mot_de_passe) VALUES (?,?)";
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
	 * Créer un groupe de nom 'nomGroupe' dans la base de données
	 * @param nomGroupe : nom du groupe à créer
	 * @throws SQLException
	 */
	public void sauvegarderGroupe(String nomGroupe) throws SQLException{
		String sql = "INSERT INTO Groupe (nom_groupe) VALUES (?)";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setString(0, nomGroupe);
		st.execute();
	}
	
	/**
	 * Supprime un groupe à partir de son nom de groupe
	 * @param nomGroupe : nom du groupe à supprimer
	 * @throws SQLException 
	 */
	public void supprimerGroupe(String nomGroupe) throws SQLException{
		String sql = "DELETE FROM Groupe WHERE nom_groupe = ?";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setString(0, nomGroupe);
		st.execute();
	}
	
	/**
	 * M�thode de sauvegarde g�n�rique 
	 * Permet d'ins�rer dans une table un n-uplet contenant les attributs donn�s
	 * @param nomTable : nom de la table cible
	 * @param attributs : Ensemble des attributs de la table 
	 * @throws SQLException 
	 */
	public void sauvegarder(String nomTable, ArrayList<Attribut> attributs) throws SQLException{
		int nombreAttributs = attributs.size();
		String sql = "INSERT INTO " + nomTable + " (";
		// liste des attributs de la table
		for(int nb = 0 ; nb < nombreAttributs ; nb++){
			sql += attributs.get(nb).getNomAttributBD() +",";
		}
		sql += ") VALUES (";
		// ensemble des futures valeurs du n-uplet
		for(int nb = 0 ; nb < nombreAttributs ; nb++){
			sql+="?,";
		}
		sql += ")";
		
		PreparedStatement st = connection.prepareStatement(sql);
		
		// correspond au premier '?'
		st.setString(0, nomTable);
		// On pr�cise la valeur des attributs de la table dans l'ordre  
		// de d�nifition dans la requete
		for(int nb = 0 ; nb < nombreAttributs ; nb++){
			st.setString(nb, attributs.get(nb).getValeur());
		}
		st.execute();
	}
	
	

	/**
	 * Supprime un n-uplet d'une table en fonction d'un attribut
	 * @param attribut : attribut � consid�rer
	 * @param nomTable : nom de la table cible
	 * @throws SQLException
	 */
	public void supprimer(String nomTable, Attribut attribut) throws SQLException{
		String sql = "DELETE FROM " + nomTable + " WHERE " + attribut.getNomAttributBD() +" = ?";
		PreparedStatement st = connection.prepareStatement(sql);
		st.setString(0, attribut.getValeur());
		st.execute();
	}
	
	
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
