package application.chat;

import java.awt.BorderLayout;
import java.awt.EventQueue;

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

import javax.swing.border.CompoundBorder;

import modules.gestionUtilisateur.Utilisateur;
import modules.*;
public class Vue extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Vue frame = new Vue();
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
	public Vue() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 580, 446);
		contentPane = new JPanel();
		contentPane.setBorder(new CompoundBorder());
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
		tabbedPane.setBounds(10, 80, 350, 251);
		contentPane.add(tabbedPane);
		
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("New tab", null, panel, null);
		panel.setLayout(null);
	
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(0, 0, 345, 223);
		textArea.setRows(4);
		panel.add(textArea);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(370, 80, 184, 153);
		contentPane.add(panel_1);
		
		JButton btnEnvoitxt = new JButton("Envoyer");
		btnEnvoitxt.setBounds(271, 342, 89, 23);
		contentPane.add(btnEnvoitxt);
		
		JButton btnEnvoiFichier = new JButton("Envoyer un fichier");
		btnEnvoiFichier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnEnvoiFichier.setBounds(114, 373, 119, 23);
		contentPane.add(btnEnvoiFichier);
		
		JLabel lblNewLabel = new JLabel("IBN Chat room");
		lblNewLabel.setFont(new Font("Consolas", Font.BOLD, 15));
		lblNewLabel.setBounds(10, 11, 111, 14);
		contentPane.add(lblNewLabel);
		
		textField_1 = new JTextField();
		textField_1.setBounds(10, 342, 250, 23);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblUsersName = new JLabel("User's name");
		lblUsersName.setFont(new Font("Consolas", Font.BOLD, 16));
		lblUsersName.setBounds(370, 11, 184, 35);
		contentPane.add(lblUsersName);
		
		JLabel lblPersonnesConnectes = new JLabel("Personnes connect\u00E9es");
		lblPersonnesConnectes.setBounds(405, 57, 111, 14);
		contentPane.add(lblPersonnesConnectes);
		
		JLabel lblNonConncts = new JLabel("Non connect\u00E9s");
		lblNonConncts.setBounds(429, 244, 76, 14);
		contentPane.add(lblNonConncts);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(370, 269, 184, 127);
		contentPane.add(panel_2);
		
//		 // création de la boîte de dialogue
//        JFileChooser dialogue = new JFileChooser();
//         
//        // affichage
//        dialogue.showOpenDialog(null);
//         
//        // récupération du fichier sélectionné
//        System.out.println("Fichier choisi : " + dialogue.getSelectedFile());
	}
	
	/**
	 * Cré un nouvel onglet qui contiendra la zone de texte d'une nouvel conversation
	 * @param user : l'utilisateur avec qui on démarre la converse
	 */
	public void nouvelConverse(Utilisateur user){
		
	}
	
	
}
