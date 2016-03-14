package danielolivermarco.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import danielolivermarco.anwendungslogik.*;
import danielolivermarco.datenhaltung.*;
/**
 * Stellt das Hauptsaechliche Interface des Spielers in der GUI Version dar
 *
 */
public class SpielerInterfaceGUI implements Serializable   {
	/**
	 * @param Besitzer des Interfaces
	 */
	private Spieler besitzer;
	/**
	 * @param Ist fuer das Welcome Fenster der Spieler, wird nach der ersten Runde auf False gesetzt
	 */
	boolean firstRound = true;
	
	/**
	 * Besitzer des Interfaces wird gesetzt.
	 * @param s Besitzer des Interfaces wird uebergeben.
	 */
	public SpielerInterfaceGUI(Spieler s) {
		besitzer = s;
	}
	
	/**
	 * 
	 * @return Gibt true zurueck, wenn der Spieler in der ersten Runde ist.
	 */
	public boolean isFirstRound() {
		return firstRound;
	}

	/**
	 * Setzt Parameter auf true, wenn erste Runde des Spielers zuende ist. 
	 * @param firstRound
	 */
	public void setFirstRound(boolean firstRound) {
		this.firstRound = firstRound;
	}

	/**
	 * Willkommensanzeige fuer jeden Spieler. Es werden ein paar Infos gezeigt,
	 * sowie die Mission des Spielers.
	 * Der Button schliesst das Fenster.
	 */
	public void welcomeAnzeige() {
		final JDialog welcome = new JDialog();
		String info = "Auf der linken Seite des Fensters findest du alle Infos zu deinem Spiel.";
		String info2 = "Klicke auf OK wenn du bereit bist, viel Spaﬂ.";
		welcome.setLayout(new FlowLayout());
		welcome.setSize(600, 200);

		welcome.setTitle("Willkommen " + besitzer.getName());
		welcome.add(new JLabel("Willkommen beim Herr der Ringe-Risiko!"));
		welcome.add(new JLabel(info));
		welcome.add(new JLabel("Klicke dazu einfach die Button an."));
		JPanel missionPanel = new JPanel();
		missionPanel.add(new JLabel("Deine Aufgabe ist es"));
		
		//entsprechende Mission fuer den Spieler ausgeben
		JLabel mission = new JLabel();
		if (besitzer.getMission().getClass().toString().equals("class danielolivermarco.anwendungslogik.MissionVierKontinente")){
			mission.setText("folgende vier Kontinente zu erobern:");
			missionPanel.add(mission);
			for (int i = 0; i < besitzer.getZuErobern().length; i++) {
				missionPanel.add(new JLabel(besitzer.getZuErobern()[i].getName()));
			}
		} else if (besitzer.getMission().getClass().toString().equals("class danielolivermarco.anwendungslogik.MissionDreiEinheiten")) {
			mission.setText("auf 15 deiner Laender mindestens drei Einheiten zu platzieren!");
			missionPanel.add(mission);
		} else if (besitzer.getMission().getClass().toString().equals("class danielolivermarco.anwendungslogik.MissionSpielerToeten")) {
			mission.setText("den Spieler " + besitzer.getToetungsziel().getName() + ", auszuloeschen!");
			missionPanel.add(mission);
		}
		welcome.add(missionPanel);
		welcome.add(new JLabel(info2));
		
		// Weiterbutton
    	final JButton okButton = new JButton("OK");
    	okButton.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			if (e.getSource().equals(okButton)) {
    				welcome.setVisible(false);
    				welcome.dispose();
    			}
    		}
    	});
    	welcome.add(okButton);
    	welcome.requestFocus();
		welcome.setVisible(true);
		welcome.setModal(true);
		welcome.setLocationRelativeTo(besitzer.getKarte());
	}
	
	/**
	 * Bei Mausklick auf ein Land oeffnet sich ein Fenster. Es werden der
	 * Landname, der Besitzer und die Armee, die sich in dem Land befindet, angezeigt.
	 * @param x x-Koordinate des JDialog Fensters
	 * @param y y-Koordinate des JDialog Fensters
	 * @param l Die Infos die in dem Fenster stehen gehˆren zu diesem Land (l), welches
	 * beim Methodenaufruf als Parameter uebergeben wird.
	 */
	public void landInfoByClick(final Land l) {
		final JDialog landInfo = new JDialog();
		landInfo.setSize(400, 200);
		landInfo.setLayout(new FlowLayout());
		if(l.getOwner()==null) {
			landInfo.add(new JLabel("Moechtest du " + l.getName() + " auswaehlen "));
			landInfo.add(new JLabel("             und mit einer Schwerteinheit besetzen?          "));
			final JButton auswaehlen = new JButton("Auswaehlen");
			auswaehlen.addActionListener(new ActionListener() {
	    		public void actionPerformed(ActionEvent e) {
	    			if (e.getSource().equals(auswaehlen)) {
	    				besitzer.getSeinSpiel().getLogikAnspracheGui().laenderAuswahl(l, besitzer);
	    				landInfo.dispose();
	    			}
	    		}
	    	});
			landInfo.add(auswaehlen);
		} else if (l.getOwner().getSpielerNr()==besitzer.getSpielerNr()) {
			landInfo.add(new JLabel(l.getName()+" ist in deinem Besitz"));
		} else if(l.getOwner()==null) {
			landInfo.add(new JLabel(l.getName()+" hat noch keinen Besitzer"));
		} else {
			landInfo.add(new JLabel(l.getName()+" ist im Besitz von "+l.getOwner().getName()));
		}
		
		int s = 0;
		int b = 0;
		int p = 0;
		//Anzahl der Einheiten werden ausgegeben
		if (l.getOwner()!=null) {
			for (int i = 0; i < l.getSpielerArmee().getArmee().size(); i++) {
				String klasse = l.getSpielerArmee().getArmee().get(i).getClass().toString();
				if(klasse.equals("class danielolivermarco.datenhaltung.Schwert")) {
						s++;
				} else if(klasse.equals("class danielolivermarco.datenhaltung.Bogen")) {
						b++;
				} else if(klasse.equals("class danielolivermarco.datenhaltung.Pferd")) {
						p++;
				}
			}

			if (l.getOwner().getSpielerNr()==besitzer.getSpielerNr()) {
				landInfo.add(new JLabel("Du hast in deinem Land folgende Einheiten:"));
				landInfo.add(new JLabel(s+" Schwerteinheiten, "+b+" Bogenschuetzen und "+p+" Berittene Einheiten."));
			} else {
				landInfo.add(new JLabel(l.getOwner().getName()+" hat in seinem Land folgende Einheiten:"));
				landInfo.add(new JLabel(s+" Schwerteinheiten, "+b+" Bogenschuetzen und "+p+" Berittene Einheiten."));
			}
			final JButton auswaehlenGui = new JButton("Auswaehlen");
			auswaehlenGui.setLocation(landInfo.getWidth()/2,landInfo.getHeight()/2);
			auswaehlenGui.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					besitzer.getSeinSpiel().getLogikAnspracheGui().landWurdeAusgewaehlt(l, besitzer);
    				landInfo.dispose();
				}
			});
			auswaehlenGui.setVisible(true);
			landInfo.add(auswaehlenGui);
		}
		
		//Button zum Auswaehlen der Laender zu Beginn der Runde, wenn der Modus auf
		//"Laender manuell auswaehlen" eingestellt ist

		
		//Button zum Abbrechen (Fenster schliessen)
		final JButton abbrechen = new JButton("Abbrechen");
		abbrechen.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			if (e.getSource().equals(abbrechen)) {
    				landInfo.setVisible(false);
    				landInfo.dispose();
    			}
    		}
    	});
		landInfo.add(abbrechen);
		
//		landInfo.setLocation(x, y);
		landInfo.setLocationRelativeTo(besitzer.getKarte());
		landInfo.setVisible(true);
		landInfo.setResizable(false);

	}

	/**
	 * Initialisiert die "Buy and Set" Phase mit der Info an den Spieler
	 * @param laenderGold Bekommt aus der Logik was der Spieler fuer seine Laender bekommt und gibt das aus
	 * @param kontinentBonus Bekommt aus der Logik was der Spieler fuer seine Kontinente bekommt
	 */
	public void goldBekommenFenster(int laenderGold, int kontinentBonus) {
		final JDialog geldFuerBesitz = new JDialog();
		geldFuerBesitz.setLayout(new BorderLayout());
		JPanel infos = new JPanel();
		infos.setLayout(new BorderLayout());
		geldFuerBesitz.setSize(400,200);
		infos.add(new JLabel("                                                 " + besitzer.getName()), BorderLayout.NORTH);
		infos.add(new JLabel("Du hast fuer deine besetzten Laender " + laenderGold + " Gold bekommen!"), BorderLayout.CENTER);
		infos.add(new JLabel("Du hast fuer deine besetzten Kontinente " + kontinentBonus + " Gold bekommen!"), BorderLayout.SOUTH);
		final JButton okButton = new JButton("Ok");
		okButton.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			if (e.getSource().equals(okButton)) {
    				geldFuerBesitz.setVisible(false);
    				geldFuerBesitz.dispose();
    				PopupGUI obEinkaufen = new PopupGUI(besitzer.getSeinSpiel());
    				obEinkaufen.obEinheitenKaufen(besitzer);
    			}
    		}
    	});
		geldFuerBesitz.add(infos, BorderLayout.NORTH);
		geldFuerBesitz.add(okButton, BorderLayout.CENTER);
		
		geldFuerBesitz.pack();
		geldFuerBesitz.setVisible(true);
		geldFuerBesitz.setLocationRelativeTo(besitzer.getKarte());
	}
	
	/**
	 * Fenster, das sich oeffnet, wenn die Angriffsphase beginnt.
	 */
	public void angriffsPhaseEinleitung() {
		final JDialog angriffsPhaseEinleitung = new JDialog();
		final JPanel anzeige = new JPanel();
		anzeige.setLayout(new BorderLayout());
		JLabel text1 = new JLabel("Du kannst nun angreifen oder diese Phase Beenden");
		JLabel text2 = new JLabel("Um eine AngriffsArmee zu erstellen waehle eines deiner Laender aus");
		JLabel text3 = new JLabel("Oder gehe direkt in die Verschieben Phase mit dem Phase Beenden Button");
		
		anzeige.add(text1,BorderLayout.NORTH);
		anzeige.add(text2,BorderLayout.CENTER);
		anzeige.add(text3,BorderLayout.SOUTH);
		
		angriffsPhaseEinleitung.add(anzeige,BorderLayout.NORTH);
		
		final JButton okButton = new JButton("Ok");
		okButton.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			if (e.getSource().equals(okButton)) {
    				angriffsPhaseEinleitung.dispose();
    			}
    		}
    	});
		angriffsPhaseEinleitung.add(okButton, BorderLayout.CENTER);
		
		angriffsPhaseEinleitung.pack();
		angriffsPhaseEinleitung.setVisible(true);
		angriffsPhaseEinleitung.setLocationRelativeTo(besitzer.getKarte());
	}
	
	/**
	 * Fenster, das sich oeffnet, wenn die Verschiebenphase beginnt
	 */
	public void verschiebenPhaseEinleitung() {
		final JDialog verschiebenPhaseEinleitung = new JDialog();
		final JPanel anzeige = new JPanel();
		anzeige.setLayout(new BorderLayout());
		JLabel text1 = new JLabel("Du kannst nun eine Armee verschieben oder diese Runde beenden");
		JLabel text2 = new JLabel("Um eine Armee zum verschieben zu erstellen waehle eines deiner Laender aus");
		JLabel text3 = new JLabel("Oder beende deine Runde mit dem Phase Beenden Button");
		
		anzeige.add(text1,BorderLayout.NORTH);
		anzeige.add(text2,BorderLayout.CENTER);
		anzeige.add(text3,BorderLayout.SOUTH);
		
		verschiebenPhaseEinleitung.add(anzeige,BorderLayout.NORTH);
		
		final JButton okButton = new JButton("Ok");
		okButton.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			if (e.getSource().equals(okButton)) {
    				verschiebenPhaseEinleitung.dispose();
    			}
    		}
    	});
		verschiebenPhaseEinleitung.add(okButton, BorderLayout.CENTER);
		
		verschiebenPhaseEinleitung.pack();
		verschiebenPhaseEinleitung.setVisible(true);
		verschiebenPhaseEinleitung.setLocationRelativeTo(besitzer.getKarte());
		
	}
	
}


