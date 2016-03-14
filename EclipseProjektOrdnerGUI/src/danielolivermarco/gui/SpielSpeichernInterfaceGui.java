package danielolivermarco.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import danielolivermarco.anwendungslogik.Spiel;
/**
 * Regelt das Speichern in der GUI Version des Spiels
 *
 */
public class SpielSpeichernInterfaceGui implements Serializable {

	Spiel dasSpiel;
	
	/**
	 * Das Spiel wird gesetzt, die Methode init wird ausgefuehrt.
	 * @param dasSpiel Das Spiel wird uebergeben.
	 */
	public SpielSpeichernInterfaceGui(Spiel dasSpiel) {
		this.dasSpiel = dasSpiel;
		this.init();
	}
	
	/**
	 * Wird aufgerufen, wenn jeder Spieler gleich oft dran war (nach jeder Runde).
	 * Ein Fenster oeffnet sich, in dem gefragt wird, ob das Spiel gespeichert werden soll.
	 */
	public void init() {
		final JDialog speichernFenster = new JDialog();
		ActionListener jaButtonA = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dasSpiel.spielSpeichernGui();
				speichernFenster.dispose();
			}
		};
		
		ActionListener neinButtonA = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				speichernFenster.dispose();
			}
		};
		
		
		speichernFenster.setLayout(new BorderLayout());
		
		JPanel buttonFeld = new JPanel(new GridLayout(1,2));
		
		speichernFenster.add(new JLabel("Moechtet ihr das Spiel speichern?"), BorderLayout.NORTH);
		
		JButton jaButton = new JButton("Ja");
		JButton neinButton = new JButton("Nein");
		
		jaButton.addActionListener(jaButtonA);
		neinButton.addActionListener(neinButtonA);
		
		buttonFeld.add(jaButton);
		buttonFeld.add(neinButton);
		
		speichernFenster.add(buttonFeld, BorderLayout.SOUTH);
		
		speichernFenster.setVisible(true);
		speichernFenster.pack();
		speichernFenster.setLocationRelativeTo(null);
	}
	
}
