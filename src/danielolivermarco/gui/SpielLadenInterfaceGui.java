 package danielolivermarco.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import danielolivermarco.anwendungslogik.Spiel;
/**
 * Fragt den Spieler in der GUI Version des Spieles ob er ein vorheriges Spiel laden moechte
 */
public class SpielLadenInterfaceGui implements Serializable {
	
	Spiel dasSpiel;
	SpielGUIClient client;
	
	/**
	 * Das Spiel und der Client werden gesetzt.
	 * @param dasSpiel Das Spiel.
	 * @param derClient Der SpielGUIClient.
	 */
	public SpielLadenInterfaceGui(Spiel dasSpiel, SpielGUIClient derClient) {
		this.dasSpiel = dasSpiel;
		this.client = derClient;
	}
	
	/**
	 * Fenster, das beim Starten des Programms geoeffnet wird. Es kann gewaehlt werden,
	 * ob das Spiel geladen werden soll oder nicht. (Die Speichern/Laden Funktion ist in der GUI
	 * noch nicht implementiert!)
	 */
	public void init() {
		final JDialog ladenFenster = new JDialog();
		ActionListener jaButtonA = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try (final ObjectInputStream ois = new ObjectInputStream(new FileInputStream("spielstandgui.ser"))){
					Spiel dasSpiel = (Spiel) ois.readObject();
					dasSpiel.getDerRundenverwalter().rundeGui();
				}catch (final IOException e1) {  }
				catch (final ClassNotFoundException p) {}
				ladenFenster.dispose();
			}
		};
		
		ActionListener neinButtonA = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					client.spielStarten();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				ladenFenster.dispose();
			}
		};
		
		
		ladenFenster.setLayout(new BorderLayout());
		
		JPanel buttonFeld = new JPanel(new GridLayout(1,2));
		
		ladenFenster.add(new JLabel("Moechtet ihr das Spiel laden?"), BorderLayout.NORTH);
		
		JButton jaButton = new JButton("Ja");
		JButton neinButton = new JButton("Nein");
		
		jaButton.addActionListener(jaButtonA);
		neinButton.addActionListener(neinButtonA);
		
		buttonFeld.add(jaButton);
		buttonFeld.add(neinButton);
		
		ladenFenster.add(buttonFeld, BorderLayout.SOUTH);
		
		ladenFenster.setVisible(true);
		ladenFenster.pack();
		ladenFenster.setLocationRelativeTo(null);
	}
	
	
}
