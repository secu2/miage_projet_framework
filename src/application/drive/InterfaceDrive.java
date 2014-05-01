package application.drive;

import java.awt.EventQueue;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;

import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Point;

import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;

import java.awt.GridLayout;
import java.awt.FlowLayout;

import javax.swing.JPanel;

import java.awt.CardLayout;

import javax.swing.BoxLayout;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.JCheckBox;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPopupMenu;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;


public class InterfaceDrive {

	public JFrame fenetre;
	private JTable filesLocal;
	private JTable filesDistant;
	private JFileChooser choixFichier;
	private JTextField txtSelectionnerFichier;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfaceDrive window = new InterfaceDrive();
					window.fenetre.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public InterfaceDrive() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		fenetre = new JFrame();
		fenetre.setTitle("Fichiers");
		fenetre.setBounds(100, 100, 592, 412);
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel north = new JPanel();
		fenetre.getContentPane().add(north, BorderLayout.NORTH);
		north.setLayout(new BorderLayout(0, 0));
		
		JLabel labelMesFichiers = new JLabel("Mes Fichiers");
		north.add(labelMesFichiers, BorderLayout.WEST);
		
		JLabel labelPartagesAvecMoi = new JLabel("Fichiers partagés avec moi");
		north.add(labelPartagesAvecMoi, BorderLayout.EAST);

		JPanel middle = new JPanel();
		fenetre.getContentPane().add(middle, BorderLayout.CENTER);
		middle.setLayout(new BoxLayout(middle, BoxLayout.X_AXIS));

		JScrollPane mesFichiersPane = new JScrollPane();
		mesFichiersPane.setToolTipText("Les fichiers disponibles dans votre dossier utilisateur");
		middle.add(mesFichiersPane);
		
		filesLocal = new JTable();
		filesLocal.setModel(new DefaultTableModel(
				new Object[][] {
						{"test1.txt", "2Ko"},
						{"test.mp3", "12Mo"},
						{"lolwut.pdf", "1Mo"},
						{"niceOne.wtf", "1,32Mo"},
						{"wololo.jpg", "513Ko"},
						{"chaton.gif", "43Mo"},
				},
				new String[] {
						"Fichier", "Taille"
				}
				) {
			boolean[] columnEditables = new boolean[] {
					true, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		filesLocal.getColumnModel().getColumn(0).setPreferredWidth(240);
		filesLocal.getColumnModel().getColumn(1).setPreferredWidth(50);
		filesLocal.getColumnModel().getColumn(1).setMinWidth(30);
		filesLocal.getColumnModel().getColumn(1).setMaxWidth(50);
		filesLocal.getTableHeader().setReorderingAllowed(false);
		filesLocal.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		filesLocal.addMouseListener( new MouseAdapter()
		{
			public void mousePressed(MouseEvent e){
				if(SwingUtilities.isRightMouseButton(e)){
					Point p = e.getPoint();
					int rowNumber = filesLocal.rowAtPoint( p );
					ListSelectionModel model = filesLocal.getSelectionModel();
					model.setSelectionInterval(rowNumber, rowNumber);
				}
			}
		});
		mesFichiersPane.setViewportView(filesLocal);

		JPopupMenu menuContextuelLocal = new JPopupMenu();
		addPopup(filesLocal, menuContextuelLocal);

		JMenuItem mntmEnregistrer = new JMenuItem("Enregistrer");
		menuContextuelLocal.add(mntmEnregistrer);

		JMenuItem mntmPartager = new JMenuItem("Partager");
		menuContextuelLocal.add(mntmPartager);

		JMenuItem mntmSupprimer = new JMenuItem("Supprimer");
		menuContextuelLocal.add(mntmSupprimer);
		
		JMenu menu = new JMenu("Propriétés");
		menuContextuelLocal.add(menu);
		
		JMenuItem menuItem = new JMenuItem("Infos");
		menu.add(menuItem);


		JScrollPane fichiersDistantsPane = new JScrollPane();
		fichiersDistantsPane.setToolTipText("Les fichiers qui sont partagés avec vous");
		middle.add(fichiersDistantsPane);

		filesDistant = new JTable();
		filesDistant.setModel(new DefaultTableModel(
				new Object[][] {
						{"test1.txt", "2Ko"},
						{"test.mp3", "12Mo"},
						{"lolwut.pdf", "1Mo"},
						{"niceOne.wtf", "1,32Mo"},
						{"wololo.jpg", "513Ko"},
						{"chatonTropMignon.gif", "43Mo"},
				},
				new String[] {
						"Fichier", "Taille"
				}
				) {
			boolean[] columnEditables = new boolean[] {
					true, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		filesDistant.getColumnModel().getColumn(0).setPreferredWidth(240);
		filesDistant.getColumnModel().getColumn(1).setPreferredWidth(50);
		filesDistant.getColumnModel().getColumn(1).setMinWidth(30);
		filesDistant.getColumnModel().getColumn(1).setMaxWidth(50);
		filesDistant.getTableHeader().setReorderingAllowed(false);
		filesDistant.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		filesDistant.addMouseListener( new MouseAdapter()
		{
			public void mousePressed(MouseEvent e){
				if(SwingUtilities.isRightMouseButton(e)){
					Point p = e.getPoint();
					int rowNumber = filesDistant.rowAtPoint( p );
					ListSelectionModel model = filesDistant.getSelectionModel();
					model.setSelectionInterval(rowNumber, rowNumber);
				}
			}
		});
		fichiersDistantsPane.setViewportView(filesDistant);

		JPopupMenu menuContextuelDistant = new JPopupMenu();
		addPopup(filesDistant, menuContextuelDistant);

		JMenuItem mntmEnregistrerR = new JMenuItem("Enregistrer");
		menuContextuelDistant.add(mntmEnregistrerR);

		JMenuItem mntmPartagerR = new JMenuItem("Partager");
		menuContextuelDistant.add(mntmPartagerR);

		JMenuItem mntmSupprimerR = new JMenuItem("Supprimer");
		menuContextuelDistant.add(mntmSupprimerR);
		
		JMenu mnProprits = new JMenu("Propriétés");
		menuContextuelDistant.add(mnProprits);
		
		JMenuItem mntmInfosnTest = new JMenuItem("Infos");
		mnProprits.add(mntmInfosnTest);

		JPanel south = new JPanel();
		fenetre.getContentPane().add(south, BorderLayout.SOUTH);
		south.setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		south.add(splitPane, BorderLayout.WEST);
		
		JButton btnEnvoyer = new JButton("Envoyer");
		splitPane.setRightComponent(btnEnvoyer);
		choixFichier = new JFileChooser();
		txtSelectionnerFichier = new JTextField();
		txtSelectionnerFichier.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int returnVal = choixFichier.showOpenDialog(fenetre.getContentPane());

			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			       txtSelectionnerFichier.setText(choixFichier.getSelectedFile().getPath());
			    }
			}
		});
		txtSelectionnerFichier.setText("Selectionner fichier");
		splitPane.setLeftComponent(txtSelectionnerFichier);
		txtSelectionnerFichier.setColumns(10);
		
		JButton btnVersMesFichiers = new JButton("Vers mes fichiers");
		btnVersMesFichiers.setIcon(new ImageIcon(InterfaceDrive.class.getResource("/javax/swing/plaf/metal/icons/ocean/iconify-pressed.gif")));
		south.add(btnVersMesFichiers, BorderLayout.EAST);
		
		
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
