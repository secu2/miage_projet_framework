package application.chat;

import java.awt.EventQueue;
import java.awt.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;

import java.awt.Font;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.border.CompoundBorder;

import systeme.rmi.*;
import modules.chat.Conversation;
import modules.chat.Message;
import modules.gestionUtilisateur.Groupe;
import modules.gestionUtilisateur.Utilisateur;

import javax.swing.JList;

public class Vue extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Conversation conversation;
	private int id_conv;
	private String login;
	private ArrayList<Utilisateur> participants;
	private ArrayList<Groupe> groupesParticipants;
	
	
	private JPanel contentPane;
	private JTextField textField;
	private List messageList;
	private JTextField txtIn;
	private List listCo;

	/**
	 * Launch the application.
	 */
	public static void main(final ClientRMI client) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Vue frame = new Vue(client);
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
	public Vue(final ClientRMI client) {
		// Definition des éléments du chat
		//Nom de client
		login = client.getUtilisateur().getLogin();
		// Crée une conversation
		id_conv = 1;
		//ajout du client à la conversation
		participants.add(client.getUtilisateur());
		conversation = new Conversation(id_conv, participants, groupesParticipants);

		// Général
		setTitle("IBN Chat room");

		JLabel lblUsersName = new JLabel(login);
		lblUsersName.setFont(new Font("Consolas", Font.BOLD, 16));
		lblUsersName.setBounds(176, 11, 184, 35);
		contentPane.add(lblUsersName);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 580, 446);
		contentPane = new JPanel();
		contentPane.setBorder(new CompoundBorder());
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblPersonnesConnectes = new JLabel("Personnes connect\u00E9es");
		lblPersonnesConnectes.setBounds(405, 57, 111, 14);
		contentPane.add(lblPersonnesConnectes);

		// Boutons d'envoi de txt et de fichiers
		JButton btnEnvoitxt = new JButton("Envoyer");
		btnEnvoitxt.setBounds(271, 342, 89, 23);
		contentPane.add(btnEnvoitxt);

		// Met le message dans la liste
		btnEnvoitxt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Message mess = new Message(txtIn.getText(), login );
				client.envoyerMessage(mess);
				messageList.add(login + " : " + client.recevoirMessage().get;);
				
			}
		});

		txtIn = new JTextField();
		txtIn.setText("Entrer du texte ici");
		txtIn.setBounds(10, 342, 250, 23);
		contentPane.add(txtIn);
		txtIn.setColumns(10);

		JButton btnEnvoiFichier = new JButton("Envoyer un fichier");
		btnEnvoiFichier.setBounds(114, 373, 119, 23);
		contentPane.add(btnEnvoiFichier);

		// Envoi le fichier et ajoute son nom dans la list
		btnEnvoiFichier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// cr�ation de la bo�te de dialogue
				JFileChooser dialogue = new JFileChooser();

				// affichage
				dialogue.showOpenDialog(null);

				// récupération du fichier sélectionné
				System.out.println("Fichier choisi : " + dialogue.getSelectedFile());

				// affiche le nom du fichier
				messageList.add(login + " : " + dialogue.getSelectedFile().getName());

			}
		});

		// Zone de chat
		messageList = new List();
		messageList.setBounds(10, 80, 345, 245);
		contentPane.add(messageList);

		// Liste utlisateurs connectés
		listCo = new List();
		listCo.setBounds(370, 82, 184, 314);
		contentPane.add(listCo);
		afficheUtilisateursCo(client);

		// <<<<<<< HEAD

		// // création de la boîte de dialogue
		// =======
		// // cr�ation de la bo�te de dialogue
		// >>>>>>> branch 'master' of
		// https://github.com/secu2/miage_projet_framework.git
		// JFileChooser dialogue = new JFileChooser();
		//
		// // affichage
		// dialogue.showOpenDialog(null);
		//
		// <<<<<<< HEAD
		// // récupération du fichier sélectionné
		// =======
		// // r�cup�ration du fichier s�lectionn�
		// >>>>>>> branch 'master' of
		// https://github.com/secu2/miage_projet_framework.git
		// System.out.println("Fichier choisi : " + dialogue.getSelectedFile());
	}

	/**
	 * Ajoute un utilisateur dans une conversation
	 * @param convers : conversation de l'ajout
	 * @param user : L'utilisateur à ajouter
	 */
	public void addUser(String convers, Utilisateur user) {

	}

	/**
	 * crée une liste pour chaque utilisateur connecté
	 */
	public void afficheUtilisateursCo(final ClientRMI client) {

		for (int i = 0; i < client.getUtilisateurs().size(); i++) {
			listCo.add(client.getUtilisateurs().get(i).getUtilisateur().getLogin());
		}
	}
	
	

}
