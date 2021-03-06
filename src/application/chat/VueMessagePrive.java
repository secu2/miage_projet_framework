package application.chat;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JLabel;

import modules.chat.Message;
import modules.chat.MessagePrive;

import systeme.Client;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VueMessagePrive extends JFrame{
	/**
	 * @wbp.nonvisual location=189,9
	 */
	public VueMessagePrive(final Client c,final String destinataire) {
		setTitle("Envoyer un message privé");
		this.setSize(500, 300);
		this.setVisible(true);
		JPanel panel = new JPanel();
		panel.setToolTipText("");
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		final JTextArea textArea = new JTextArea();
		textArea.setBounds(12, 12, 420, 152);
		textArea.setRows(5);
		panel.add(textArea);
		
		JButton btnEnvoyer = new JButton("Envoyer");
		btnEnvoyer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MessagePrive message = new MessagePrive(textArea.getText(), c.getUtilisateur().getLogin(), destinataire);
				c.envoyerMessagePrive(message);
				dispose();
			}
		});
		btnEnvoyer.setBounds(167, 216, 117, 25);
		panel.add(btnEnvoyer);
	}
	
	
}
