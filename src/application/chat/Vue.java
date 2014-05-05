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

import javax.swing.border.CompoundBorder;

import systeme.rmi.*;
import modules.gestionUtilisateur.Utilisateur;

import javax.swing.JList;

public class Vue extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private List messageList;
	private JTextField txtIn;
	private List listCo;

	/**
	 * Launch the application.
	 */
	public static void main(final ClientRMI client, final ServeurRMI serveur) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Vue frame = new Vue(client, serveur);
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
	public Vue(final ClientRMI client, ServeurRMI serveur) {
		// Definition des éléments du chat
		//Crée une conversation
		
		
		
		
		
		// Général
		setTitle("IBN Chat room");

		JLabel lblUsersName = new JLabel(client.getUtilisateur().getLogin().toString());
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
				messageList.add(client.getUtilisateur().getLogin().toString() + " : " + txtIn.getText());
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
				messageList.add(client.getUtilisateur().getLogin().toString() + " : " + dialogue.getSelectedFile().getName());

			}
		});

		// Zone de chat
		messageList = new List();
		messageList.setBounds(10, 80, 345, 245);
		contentPane.add(messageList);
		
		//Liste utlisateurs connectés
		listCo = new List();
		listCo.setBounds(370, 82, 184, 314);
		contentPane.add(listCo);
		

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
	 * crée un bouton pour chaque utilisateur connecté
	 */
	public void afficheUtilisateursCo(final ServeurRMI serveur) {
		try {
			for (int i = 0; i < serveur.getUtilisateursInscrits().size() ; i++) {
				for( int j =0; j < serveur.getUtilisateursConnectes().size(); j++){
					if(serveur.getUtilisateursInscrits().get(i).getLogin().equals(serveur.getUtilisateursConnectes().get(j))){
						listCo.add(serveur.getUtilisateursInscrits().get(i).getLogin());
					}
				}
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
