package danielolivermarco.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import danielolivermarco.anwendungslogik.*;
/**
 * Regelt die Eingaben vom Spielersteller vor wirklichem Start des Spiels
 *
 */
public class StartGUI extends JFrame implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @param Button, der ein neues Fenster oeffnet
	 */
	private JButton weiterButton;
	/**
	 * @param Hier wird die Anzahl der Spieler gespeichert
	 */
	private int spielerAnzahl;
	/**
	 * @param Hier wird das Spiel gespeichert
	 */
	private Spiel spiel;
	/**
	 * @param Siehe Methode Spielernamen
	 */
	private int spielerNr = 1;

	public StartGUI(Spiel spiel) {
		super();
		this.spiel = spiel;
	}

	/**
	 * Spieleranzahl kann ueber ein Dropdownmenue gewaehlt werden.
	 * Ueber den Weiterbutton gelangt man in die Methode spielerErzeugen der Klasse Spiel	 
	 */
	public void waehleSpielerAnz() {
		this.setSize(400, 200);
		
    	this.setLayout(new BorderLayout());
    	
    	final JLabel welcome = new JLabel("Willkommen bei Herr der Ringe Risiko.");
    	this.add(welcome, BorderLayout.NORTH);
    	
    	final JPanel center = new JPanel();
    	center.setLayout(new FlowLayout());
    	center.add(new JLabel("Wie viele Spieler seid ihr?"));
    	
    	//Array mit der Auswahl der moeglichen Spieleranzahlen
    	String[] comboBoxListe = {"2", "3", "4"};
    	final JComboBox spielerAnzAuswahl = new JComboBox (comboBoxListe);
    	center.add(spielerAnzAuswahl);
    	this.add(center, BorderLayout.CENTER);
    	
    	weiterButton = new JButton("Weiter");
    	weiterButton.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			if (e.getSource().equals(weiterButton)) {
    				setSpielerAnzahl(2 + spielerAnzAuswahl.getSelectedIndex());//+2 da der Index der Combobox abgefragt wird (bei 0 ist 2, bei 1 ist 3..)
    				setVisible(false);
    				dispose();
    				spiel.spielerwahl(getSpielerAnzahl());
    				remove(center);
    				remove(spielerAnzAuswahl);
    				remove(weiterButton);
    				remove(welcome);
    				spiel.spielerErzeugen(getSpielerNr());
    			}
    		}
    	});
    	this.add(weiterButton, BorderLayout.SOUTH);
    	
    	this.setVisible(true);
    	this.setLocationRelativeTo(null);
	}
	
	/**
	 * Fuer jeden Spieler kann ein Spielername im Textfeld eingegeben werden.
	 * Die Eingabe erfolgt nach und nach, d.h. zuerst wird der Spielername von Spieler 1
	 * eingegeben, nachdem OK geklickt wurde, wird der Spielername von Spieler 2 eingegeben usw...
	 * @param nr Wird beim Methodenaufruf uebergeben, gibt beim Erstellen des Spielers die Spielernummer an
	 */
	public void spielerNamen(final int nr) {
		this.setLayout(new FlowLayout());
		final JLabel label = new JLabel("Spieler" + nr + ", wie lautet Dein Name?");
		this.add(label);
		final JTextField textfield = new JTextField("Spieler "+nr);
		this.add(textfield);
		weiterButton = new JButton("Weiter");
		weiterButton.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			if (e.getSource().equals(weiterButton) && spielerNr < spiel.getAnzahlSpieler()) {
    				final String name = textfield.getText();
    				spiel.spielerAdd(name, nr);
    				setVisible(false);
    				dispose();
    				spielerNr++;
    				remove(textfield);
    				remove(label);
    				remove(weiterButton);
    				spiel.spielerErzeugen(spielerNr);
    			} else if (e.getSource().equals(weiterButton) && spielerNr == spiel.getAnzahlSpieler()) {
    				final String name = textfield.getText();
    				spiel.spielerAdd(name, nr);
        			setVisible(false);
        			dispose();
    				remove(textfield);
       				remove(label);
       				remove(weiterButton);
       				StartGUI.this.spielModus();
       				try {
						spiel.init();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
    			}
    			
    		}
    	});
		this.add(weiterButton);
		this.setVisible(true);
    	this.setLocationRelativeTo(null);
	}
	
	/**
	 * Hier wird der Modus ausgewaehlt: Laender automatisch verteilen, Einheiten automatisch verteilen etc.
	 * Nach Klicken des Weiterbuttons wird die Methode initGUI in der Klasse Anfangsphase ausgefuehrt.
	 */
	public void spielModus() {
		this.setSize(600, 200);
		
		final JLabel label = new JLabel("Welchen Spielmodi moechtet ihr spielen?");
		this.add(label);
		String[] comboBoxListe = {"Laender zufaellig auswaehlen und Einheiten manuell verteilen.", "Laender und Einheiten zufaellig verteilen.", "Laender und Einheiten manuell verteilen."};
    	final JComboBox modusAuswahl = new JComboBox (comboBoxListe);
    	this.add(modusAuswahl);
    	weiterButton = new JButton("Weiter");
    	weiterButton.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			if (e.getSource().equals(weiterButton)) {
    				setVisible(false);
        			dispose();
        			remove(label);
       				remove(weiterButton);
    				spiel.getDieAnfangsphase().initGUI(modusAuswahl.getSelectedIndex());
    			}
    		}
    	});
    	
		this.add(weiterButton);
		this.setVisible(true);
    	this.setLocationRelativeTo(null);
	}
	
	/**
	 * @return Gibt die Spieleranzahl zurueck.
	 */
	public int getSpielerAnzahl() {
		return spielerAnzahl;
	}

	/**
	 * Setzt die Spieleranzahl.
	 * @param spielerAnzahl Uebergibt die Spieleranzahl.
	 */
	public void setSpielerAnzahl(int spielerAnzahl) {
		this.spielerAnzahl = spielerAnzahl;
	}
	
	/**
	 * 
	 * @return Gibt die Spielernummer zurueck.
	 */
	public int getSpielerNr() {
		return spielerNr;
	}

	/**
	 * Setzt die Spielernummer.
	 * @param spielerNr Uebergibt die Spielernummer.
	 */
	public void setSpielerNr(int spielerNr) {
		this.spielerNr = spielerNr;
	}
}
