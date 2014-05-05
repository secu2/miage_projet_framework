package application.chat;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import systeme.Client;
import systeme.rmi.InterfaceServeurRmi;

import javax.swing.JTextField;
import javax.swing.JButton;

import application.connexion.MainConnexion;

public class Inscription extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static int REGISTRY_PORT = 1099;
	private JPanel contentPane;
	private JTextField textFieldLogin;
	private JTextField textFieldPass;
	private JTextField textFieldConfirmation;

	/**
	 * Launch the application.
	 */
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Inscription frame = new Inscription();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Inscription() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 259, 191);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblLogin = new JLabel("Login");
		lblLogin.setBounds(63, 46, 46, 14);
		contentPane.add(lblLogin);

		JLabel lblPass = new JLabel("Mot de passe");
		lblPass.setBounds(30, 71, 70, 14);
		contentPane.add(lblPass);

		JLabel lblTitre = new JLabel("Inscription au serveur :");
		lblTitre.setBounds(10, 11, 146, 14);
		lblTitre.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(lblTitre);

		textFieldLogin = new JTextField();
		textFieldLogin.setBounds(119, 43, 86, 20);
		contentPane.add(textFieldLogin);
		textFieldLogin.setColumns(10);

		textFieldPass = new JPasswordField();
		textFieldPass.setBounds(119, 68, 86, 20);
		contentPane.add(textFieldPass);
		textFieldPass.setColumns(10);

		JButton btnInscription = new JButton("Inscription");
		btnInscription.setBounds(119, 124, 89, 23);
		contentPane.add(btnInscription);

		JLabel lblConfirmation = new JLabel("Confirmation ");
		lblConfirmation.setBounds(33, 96, 76, 14);
		contentPane.add(lblConfirmation);

		textFieldConfirmation = new JPasswordField();
		textFieldConfirmation.setBounds(119, 93, 86, 20);
		contentPane.add(textFieldConfirmation);
		textFieldConfirmation.setColumns(10);

		btnInscription.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// verification de la correspondance des 2 mots de passe
				if (!(textFieldPass.getText().equals(textFieldConfirmation.getText()))) {
					JOptionPane.showMessageDialog(contentPane, "Les mots de passe ne correspondent pas", "Confirmation mdp", JOptionPane.ERROR_MESSAGE);
					textFieldPass.setText("");
					textFieldConfirmation.setText("");
				} else {

					Registry registry = null;
					try {
						registry = LocateRegistry.getRegistry(REGISTRY_PORT);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Remote r;

					try {
						r = registry.lookup("fram");
						((InterfaceServeurRmi) r).inscription(textFieldLogin.getText(), textFieldPass.getText());
					} catch (AccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NotBoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchAlgorithmException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					JOptionPane.showMessageDialog(contentPane, "Inscription effectuée", "Confirmation inscription", JOptionPane.INFORMATION_MESSAGE);

					try {
						new MainConnexion().main(null);
						dispose();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

	}
}
