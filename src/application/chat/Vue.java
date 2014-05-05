package application.chat;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JFileChooser;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextArea;
import javax.swing.JLabel;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.border.CompoundBorder;

import systeme.rmi.ClientRMI;
import modules.gestionUtilisateur.Utilisateur;
import modules.*;
import application.*;

public class Vue extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private List messageList;
	private JTextField txtIn;
	private JPanel panel_co;
	private JPanel panel_deco;

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

		// Zone des utilisateurs connéctés
		panel_co = new JPanel();
		panel_co.setBounds(370, 80, 184, 153);
		contentPane.add(panel_co);

		JLabel lblPersonnesConnectes = new JLabel("Personnes connect\u00E9es");
		lblPersonnesConnectes.setBounds(405, 57, 111, 14);
		contentPane.add(lblPersonnesConnectes);

		// Zone des utilisateurs déconnectées
		JLabel lblNonConncts = new JLabel("Non connect\u00E9s");
		lblNonConncts.setBounds(429, 244, 76, 14);
		contentPane.add(lblNonConncts);

		panel_deco = new JPanel();
		panel_deco.setBounds(370, 269, 184, 127);
		contentPane.add(panel_deco);

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

		// Bouton d'ajout d'un utilisateur dans la conversation
		JButton btnAdd = new JButton("+");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnAdd.setBounds(250, 53, 89, 23);
		contentPane.add(btnAdd);

		// Zone de chat
		messageList = new List();
		messageList.setBounds(10, 80, 345, 245);
		contentPane.add(messageList);

		// Rmpli les zones des co et deco
		afficheUtilisateursCo(client);
		afficheUtilisateursDeco(client);

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
	 * cré un bouton pour chaque utilisateur connecté
	 */
	public void afficheUtilisateursCo(final ClientRMI client) {
		for (int i = 0; i < client.getUtilisateurs().size(); i++) {
			JButton btnAdd = new JButton(client.getUtilisateur().getLogin().toString());
			btnAdd.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
				}
			});
			btnAdd.setBounds(271, 57, 89, 23);
			panel_co.add(btnAdd);

		}
	}
	/**
	 * cré un bouton pour chaque utilisateur deconnecté
	 */
	public void afficheUtilisateursDeco(final ClientRMI client) {
		for (int i = 0; i < client.getUtilisateurs().size(); i++) {
			JButton btnAdd = new JButton(client.getUtilisateur().getLogin().toString());
			btnAdd.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
				}
			});
			btnAdd.setBounds(271, 57, 89, 23);
			panel_deco.add(btnAdd);

		}
	}
}
