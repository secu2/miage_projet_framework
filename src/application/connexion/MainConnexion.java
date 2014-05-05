package application.connexion;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.BorderLayout;

import javax.swing.JLabel;

import java.awt.Font;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Formatter;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JFormattedTextField;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.MaskFormatter;

import systeme.Client;
import application.chat.Vue;
import application.drive.InterfaceDrive;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainConnexion {

	private JFrame frmConnexionAuServeur;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JPasswordField passwordTextField;
	protected static MainConnexion window;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new MainConnexion();
					window.frmConnexionAuServeur.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws ParseException 
	 */
	public MainConnexion() throws ParseException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws ParseException 
	 */
	private void initialize() throws ParseException {
		frmConnexionAuServeur = new JFrame();
		frmConnexionAuServeur.setTitle("Connexion au serveur...");
		frmConnexionAuServeur.setResizable(false);
		frmConnexionAuServeur.setBounds(100, 100, 257, 197);
		frmConnexionAuServeur.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmConnexionAuServeur.getContentPane().setLayout(null);
		
		
		JLabel lblConnexionUn = new JLabel("Connexion à un serveur:");
		lblConnexionUn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblConnexionUn.setBounds(10, 11, 157, 17);
		frmConnexionAuServeur.getContentPane().add(lblConnexionUn);
		
		JLabel lblAdresseIp = new JLabel("Adresse IP:");
		lblAdresseIp.setBounds(10, 39, 109, 14);
		frmConnexionAuServeur.getContentPane().add(lblAdresseIp);
		
		JLabel lblNomDutilisateur = new JLabel("Nom d'utilisateur:");
		lblNomDutilisateur.setBounds(10, 64, 109, 14);
		frmConnexionAuServeur.getContentPane().add(lblNomDutilisateur);
		
		JLabel lblMotDePasse = new JLabel("Mot de passe:");
		lblMotDePasse.setBounds(10, 89, 109, 14);
		frmConnexionAuServeur.getContentPane().add(lblMotDePasse);
		
		final JRadioButton rdbtnDrive = new JRadioButton("Drive");
		rdbtnDrive.setSelected(true);
		buttonGroup.add(rdbtnDrive);
		rdbtnDrive.setBounds(33, 110, 59, 23);
		frmConnexionAuServeur.getContentPane().add(rdbtnDrive);
		
		JRadioButton rdbtnChat = new JRadioButton("Chat");
		buttonGroup.add(rdbtnChat);
		rdbtnChat.setBounds(107, 110, 109, 23);
		frmConnexionAuServeur.getContentPane().add(rdbtnChat);

		JFormattedTextField ipTextField = new JFormattedTextField(new IPAddressFormatter());
		ipTextField.setBounds(129, 36, 112, 20);
		frmConnexionAuServeur.getContentPane().add(ipTextField);
		
		
		final JFormattedTextField usernameTextField = new JFormattedTextField();
		usernameTextField.setBounds(129, 61, 112, 20);
		frmConnexionAuServeur.getContentPane().add(usernameTextField);
		
		passwordTextField = new JPasswordField();
		passwordTextField.setBounds(129, 86, 112, 20);
		frmConnexionAuServeur.getContentPane().add(passwordTextField);
		
		JButton btnConnexion = new JButton("Connexion");
		btnConnexion.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					Client c = new Client(usernameTextField.getText(), passwordTextField.getText());
					System.out.println(c);
					if(rdbtnDrive.isSelected()){
						new InterfaceDrive(c).fenetre.setVisible(true);
					}else{
						new Vue(c).setVisible(true);
					}

					window.frmConnexionAuServeur.setVisible(false);
				}
				catch(Exception e) {
					JOptionPane.showMessageDialog(frmConnexionAuServeur,
						    "Impossible de se connecter, vérifiez vos informations de connexion ("+e+")",
						    "Inane error",
						    JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
				
				

			}
		});
		btnConnexion.setBounds(10, 140, 231, 23);
		frmConnexionAuServeur.getContentPane().add(btnConnexion);
	}
	
	class IPAddressFormatter extends DefaultFormatter
	{
	   public String valueToString(Object value) throws ParseException
	   {
	      if (!(value instanceof byte[])) throw new ParseException("Not a byte[]", 0);
	      byte[] a = (byte[]) value;
	      if (a.length != 4) throw new ParseException("Length != 4", 0);
	      StringBuilder builder = new StringBuilder();
	      for (int i = 0; i < 4; i++)
	      {
	         int b = a[i];
	         if (b < 0) b += 256;
	         builder.append(String.valueOf(b));
	         if (i < 3) builder.append('.');
	      }
	      return builder.toString();
	   }

	   public Object stringToValue(String text) throws ParseException
	   {
	      StringTokenizer tokenizer = new StringTokenizer(text, ".");
	      byte[] a = new byte[4];
	      for (int i = 0; i < 4; i++)
	      {
	         int b = 0;
	         if (!tokenizer.hasMoreTokens()) throw new ParseException("Too few bytes", 0);
	         try
	         {
	            b = Integer.parseInt(tokenizer.nextToken());
	         }
	         catch (NumberFormatException e)
	         {
	            throw new ParseException("Not an integer", 0);
	         }
	         if (b < 0 || b >= 256) throw new ParseException("Byte out of range", 0);
	         a[i] = (byte) b;
	      }
	      if (tokenizer.hasMoreTokens()) throw new ParseException("Too many bytes", 0);
	      return a;
	   }
	}
}
