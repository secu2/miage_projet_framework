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

import systeme.Client;
import systeme.rmi.*;
import modules.chat.Conversation;
import modules.chat.Message;
import modules.gestionUtilisateur.Groupe;
import modules.gestionUtilisateur.Utilisateur;

import javax.swing.JList;
import javax.swing.SwingConstants;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;

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
	private List messageList;
	private JTextField txtIn;
	private List listDeco;
	private List listCo;

	/**
	 * Launch the application.
	 */
	public static void main(final Client client) {
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
	public Vue(final Client c) {
		// Definition des éléments du chat
		//Nom de client
		c.setVue(this);
		login = c.getUtilisateur().getLogin();
		System.out.println(login);
		// Crée une conversation
		id_conv = 1;
		//ajout du client à la conversation
		//participants.add(client.getUtilisateur());
		//conversation = new Conversation(id_conv, participants, groupesParticipants);
		
		//Général
		setTitle("IBN Chat room");
		contentPane = new JPanel();
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

		// Boutons d'envoi de txt et de fichiers
		JButton btnEnvoitxt = new JButton("Envoyer");
		btnEnvoitxt.setBounds(271, 342, 89, 23);
		contentPane.add(btnEnvoitxt);

		// Met le message dans la liste
		btnEnvoitxt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Message mess = new Message(txtIn.getText(), login);
				c.envoyerMessage(mess);

				//messageList.add(login + " : " + client.recevoirMessage().get;);
				
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

		// Listes utlisateurs co/deco
		JLabel lblPersonnesConnectes = new JLabel("Personnes connect\u00E9es");
		lblPersonnesConnectes.setBounds(405, 57, 111, 14);
		contentPane.add(lblPersonnesConnectes);
		
		listDeco = new List();
		listDeco.setBounds(372, 252, 178, 144);
		contentPane.add(listDeco);
		
		
		JLabel lblPersonnesDeconnectes = new JLabel("Personnes deconnectées");
		lblPersonnesDeconnectes.setHorizontalAlignment(SwingConstants.CENTER);
		lblPersonnesDeconnectes.setBounds(372, 232, 179, 14);
		contentPane.add(lblPersonnesDeconnectes);
		
		listCo = new List();
		listCo.setBounds(370, 82, 184, 144);
		contentPane.add(listCo);		
		
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(listCo, popupMenu);
		
		JMenuItem mntmMessagePriv = new JMenuItem("Message privé");
		popupMenu.add(mntmMessagePriv);
		
		afficheUtilisateursDeco(c);
		afficheUtilisateursCo(c);

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
	 * crée une liste d'utilisateurs connectés
	 */
	public void afficheUtilisateursCo(final Client client) {
		for (int i = 0; i < client.getUtilisateursConnectes().size() ; i++) {
			listCo.add(client.getUtilisateursConnectes().get(i).getUtilisateur().getLogin());
		}
	}
	
	/**
	 * crée une liste d'utilisateurs deconnectés
	 */
	public void afficheUtilisateursDeco(final Client client) {

		for (int i = 0; i < client.getUtilisateursDeconnectes().size(); i++) {
			listDeco.add(client.getUtilisateursDeconnectes().get(i).getLogin());
		}
	}
	
	public List getListeMessage(){
		return this.messageList;
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
