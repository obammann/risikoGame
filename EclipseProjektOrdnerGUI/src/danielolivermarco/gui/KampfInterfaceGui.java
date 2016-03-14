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

import danielolivermarco.anwendungslogik.Kampf;
import danielolivermarco.datenhaltung.Spieler;
/**
 * Gibt Meldungen fuer Kaempfe in der GUI Version heraus 
 *
 */
public class KampfInterfaceGui implements Serializable {
	
	private Spieler aktiverS;
	private Spieler gegnerS;
	private boolean angreifer = false;
	private Kampf kampf;

	/**
	 * Der aktive Spieler, der Gegner und der Kampf werden gesetzt.
	 * @param aktiverS Der aktive Spieler.
	 * @param gegnerS Der Gegner.
	 * @param kampf Der Kampf.
	 */
	public KampfInterfaceGui(Spieler aktiverS, Spieler gegnerS,
			Kampf kampf) {
		this.aktiverS = aktiverS;
		this.gegnerS = gegnerS;
		if (kampf.angreiferS.equals(aktiverS)) {
			angreifer = true;
		}
		this.kampf = kampf;
	}
	
	/**
	 * Beim Start eines Kampfes wird diese Methode aufgerufen. Es oeffnet sich ein Fenster,
	 * in dem die Anzahl der Einheiten des Angreifers und des Verteidigers angezeigt werden.
	 * Angreifender und verteidigender Spieler koennen auswaehlen, welche Einheit sie in den
	 * Kampf schicken wollen.
	 */
	public void init() {
		
		final JDialog anzeigeUndAuswahl = new JDialog();
		anzeigeUndAuswahl.setLayout(new BorderLayout());
		
		anzeigeUndAuswahl.setTitle("Ein Kampf findet statt!");
		
		JPanel linkeAnzeigeNorth = new JPanel(new GridLayout(4,1));
		JPanel rechteAnzeigeNorth = new JPanel(new GridLayout(4,1));
		JPanel anzeigeNorth = new JPanel(new GridLayout(1,2));
		
		JLabel textL1 = new JLabel(aktiverS.getName() + ", du hast folgende Einheiten: ");
		JLabel textL2 = new JLabel();
		JLabel textL3 = new JLabel();
		JLabel textL4 = new JLabel();
		
		JLabel textR1 = new JLabel("Dein Gegner " + gegnerS.getName() + "hat folgende Einheiten: ");
		JLabel textR2 = new JLabel();
		JLabel textR3 = new JLabel();
		JLabel textR4 = new JLabel();
		
		
		if (angreifer == true){
			textL2.setText(kampf.getASchwerterSize() + " Schwertkaempfer");
		} else {
			textL2.setText(kampf.getVSchwerterSize() + " Schwertkaempfer");
		} 
		if (angreifer == true) {
			textL3.setText(kampf.getABogenSize() + " Bogenschuetzen");
		} else {
			textL3.setText(kampf.getVBogenSize() + " Bogenschuetzen");
		}
		if (angreifer == true) {
			textL4.setText(kampf.getAPferdeSize() + " Reiter");
		} else {
			textL4.setText(kampf.getVPferdeSize() + " Reiter");
		}

		
		if (angreifer == true){
			textR2.setText(kampf.getVSchwerterSize() + " Schwertkaempfer");
		} else {
			textR2.setText(kampf.getASchwerterSize() + " Schwertkaempfer");
		} 
		if (angreifer == true) {
			textR3.setText(kampf.getVBogenSize() + " Bogenschuetzen");
		} else {
			textR3.setText(kampf.getABogenSize() + " Bogenschuetzen");
		}
		if (angreifer == true) {
			textR4.setText(kampf.getVPferdeSize() + " Reiter");
		} else {
			textR4.setText(kampf.getAPferdeSize() + " Reiter");
		}
		
		linkeAnzeigeNorth.add(textL1);
		linkeAnzeigeNorth.add(textL2);
		linkeAnzeigeNorth.add(textL3);
		linkeAnzeigeNorth.add(textL4);
		
		rechteAnzeigeNorth.add(textR1);
		rechteAnzeigeNorth.add(textR2);
		rechteAnzeigeNorth.add(textR3);
		rechteAnzeigeNorth.add(textR4);
		
		anzeigeNorth.add(linkeAnzeigeNorth);
		anzeigeNorth.add(rechteAnzeigeNorth);
		
		ActionListener schwertAuswaehlen = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (angreifer == true && (kampf.getASchwerterSize() > 0)) {
					kampf.setAuswahlA(1);
					kampf.setAngreiferHatGewaehlt(true);
					kampf.kampfGui();
					anzeigeUndAuswahl.dispose();
				} else if (angreifer != true && (kampf.getVSchwerterSize() > 0)){
					kampf.setAuswahlV(1);
					kampf.setVerteidigerHatGewaehlt(true);
					kampf.kampfGui();
					anzeigeUndAuswahl.dispose();
				} else {
					PopupGUI fehler = new PopupGUI();
					String text = "Du hast in deiner Armee diesen Einheitentypen nicht mehr!";
					fehler.textAnzeigen(text, aktiverS);
				}
			}
		};
		
		ActionListener bogenAuswaehlen = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (angreifer == true && (kampf.getABogenSize() > 0)) {
					kampf.setAuswahlA(2);
					kampf.setAngreiferHatGewaehlt(true);
					kampf.kampfGui();
					anzeigeUndAuswahl.dispose();
				} else if (angreifer != true && (kampf.getVBogenSize() > 0)) {
					kampf.setAuswahlV(2);
					kampf.setVerteidigerHatGewaehlt(true);
					kampf.kampfGui();
					anzeigeUndAuswahl.dispose();
				} else {
					PopupGUI fehler = new PopupGUI();
					String text = "Du hast in deiner Armee diesen Einheitentypen nicht mehr!";
					fehler.textAnzeigen(text, aktiverS);
				}
			}
		};
		
		ActionListener pferdAuswaehlen = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (angreifer == true && (kampf.getAPferdeSize() > 0)) {
					kampf.setAuswahlA(3);
					kampf.setAngreiferHatGewaehlt(true);
					kampf.kampfGui();
					anzeigeUndAuswahl.dispose();
				} else if (angreifer != true && (kampf.getVPferdeSize() > 0)) {
					kampf.setAuswahlV(3);
					kampf.setVerteidigerHatGewaehlt(true);
					kampf.kampfGui();
					anzeigeUndAuswahl.dispose();
				} else {
					PopupGUI fehler = new PopupGUI();
					String text = "Du hast in deiner Armee diesen Einheitentypen nicht mehr!";
					fehler.textAnzeigen(text, aktiverS);
				}
			}
		};
		
		JPanel buttonsSouth = new JPanel(new GridLayout(1,3));
		
		JButton button1 = new JButton("Schwertkaempfer");
		JButton button2 = new JButton("Bogenschuetzen");
		JButton button3 = new JButton("Reiter");
		
		button1.addActionListener(schwertAuswaehlen);
		button2.addActionListener(bogenAuswaehlen);
		button3.addActionListener(pferdAuswaehlen);
		
		buttonsSouth.add(button1);
		buttonsSouth.add(button2);
		buttonsSouth.add(button3);
		
		anzeigeUndAuswahl.add(anzeigeNorth, BorderLayout.NORTH);
		anzeigeUndAuswahl.add(new JLabel("Welchen Einheitentypen willst du in den Kampf schicken?"), BorderLayout.CENTER);
		anzeigeUndAuswahl.add(buttonsSouth,BorderLayout.SOUTH);
		
		anzeigeUndAuswahl.pack();
		anzeigeUndAuswahl.setVisible(true);
		
		
		anzeigeUndAuswahl.setLocationRelativeTo(aktiverS.getKarte());
		
	}

	/**
	 * Ergebnisse des Wuerfelns beim Angriff werden angezeigt.
	 * @param zahlA Wuerfelaugenzahl des Angreifers
	 * @param zahlV Wuerfelaugenzahl des Verteidigers
	 * @param wurf Zeigt an, wie viele Wuerfe schon gewuerfelt wurden
	 */
	public void einzelkampfAnzeigen(int zahlA, int zahlV, int wurf) {
		final JDialog einKampfErgebnis = new JDialog();
		einKampfErgebnis.setLayout(new BorderLayout());
	
		ActionListener okButtonA = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				einKampfErgebnis.dispose();
			}
		};
		
		einKampfErgebnis.setTitle("Wuerfelwurf " + wurf);
		
		JPanel centerTexte = new JPanel(new GridLayout(2,1));
		
		JLabel text1 = new JLabel(aktiverS.getName());
		JLabel text2 = new JLabel();
		JLabel text3 = new JLabel();
		
		if (angreifer) {
			text2.setText("Deine Einheit hat eine " + zahlA + " geworfen.");
			text3.setText("Dein Gegner hat eine " + zahlV + " geworfen");
		} else {
			text2.setText("Deine Einheit hat eine " + zahlV + " geworfen.");
			text3.setText("Dein Gegner hat eine " + zahlA + " geworfen");
		}
		
		centerTexte.add(text2);
		centerTexte.add(text3);
		
		JButton okButton = new JButton("ok");
		okButton.addActionListener(okButtonA);
		
		einKampfErgebnis.add(text1,BorderLayout.NORTH);
		einKampfErgebnis.add(centerTexte,BorderLayout.CENTER);
		einKampfErgebnis.add(okButton, BorderLayout.SOUTH);
		
		einKampfErgebnis.pack();
		einKampfErgebnis.setVisible(true);
		einKampfErgebnis.setLocationRelativeTo(aktiverS.getKarte());
	}

	/**
	 * Fenster, das anzeigt, dass die Einheit des Spielers verloren hat.
	 */
	public void einheitVerloren() {
		final JDialog kampfVerloren = new JDialog();
		JLabel text1 = new JLabel(aktiverS.getName() + ",deine Einheit hat verloren!");
		
		kampfVerloren.setLayout(new GridLayout(2,1));
		kampfVerloren.add(text1);
		
		ActionListener okButtonA = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				kampfVerloren.dispose();
			}
		};
		
		JButton okButton = new JButton("ok");
		okButton.addActionListener(okButtonA);
		
		kampfVerloren.add(okButton);
		
		kampfVerloren.pack();
		kampfVerloren.setVisible(true);
		kampfVerloren.setLocationRelativeTo(aktiverS.getKarte());
	}

	/**
	 * Fenster, das anzeigt, dass die Einheit des Spielers gewonnen hat.
	 */
	public void einheitGewonnen() {
		final JDialog kampfGewonnen = new JDialog();
		JLabel text1 = new JLabel(aktiverS.getName() + ", deine Einheit hat gewonnen!");
		
		kampfGewonnen.setLayout(new GridLayout(2,1));
		kampfGewonnen.add(text1);
		
		ActionListener okButtonA = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				kampfGewonnen.dispose();
			}
		};
		
		JButton okButton = new JButton("ok");
		okButton.addActionListener(okButtonA);
		
		kampfGewonnen.add(okButton);
		
		kampfGewonnen.pack();
		kampfGewonnen.setVisible(true);
		kampfGewonnen.setLocationRelativeTo(aktiverS.getKarte());
	}

	/**
	 * Fenster, das anzeigt, dass der Spieler den Kampf verloren hat.
	 */
	public void kampfVerloren() {
		PopupGUI verloren = new PopupGUI();
		String text;
		if (angreifer) {
			text = aktiverS.getName() + "Du hast den Kampf verloren, deine Armee ist besiegt!";
		} else {
			text = aktiverS.getName() + "Du hast den Kampf verloren und dein Land verloren!";
		}
		verloren.textAnzeigen(text, aktiverS);
	}

	/**
	 * Fenster, das anzeigt, dass der Spieler den Kampf gewonnen hat.
	 */
	public void kampfGewonnen() {
		PopupGUI gewonnen = new PopupGUI();
		String text;
		if (angreifer) {
			text = aktiverS.getName() + "Du hast den Kampf gewonnen und das Land erobert!";
		} else {
			text = aktiverS.getName() + "Du hast den Kampf gewonnen und dein Land verteidigt!";
		}
		gewonnen.textAnzeigen(text, aktiverS);
	}
	
	

}
