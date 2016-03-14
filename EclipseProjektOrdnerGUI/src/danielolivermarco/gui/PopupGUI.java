package danielolivermarco.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import danielolivermarco.anwendungslogik.ArmeeAufloeser;
import danielolivermarco.anwendungslogik.Mission;
import danielolivermarco.anwendungslogik.Spiel;
import danielolivermarco.datenhaltung.*;
/**
 * 
 * Organisiert Popups fuer viele Anzeigen waehrend des Spiels in der GUI Version
 *
 */
public class PopupGUI implements Serializable {
	
	/**
	 * @param Button, der fuer diverse Fenster verwendet wird
	 */
	private JButton unserButton;
	/**
	 * @param Fenster, das mehrfach verwendet wird
	 */
	private JDialog unserPopup;
	/**
	 * @param Label, das mehrfach verwendet wird
	 */
	private JLabel unserLabel;
	private Spiel dasSpiel;

	/**
	 * Das JDialog wird instanziert.
	 */
	public PopupGUI() {
		unserPopup = new JDialog();
	}

	/**
	 * Das Spiel wird gesetzt, das JDialog wird instanziert.
	 * @param seinSpiel Das Spiel wird uebergeben.
	 */
	public PopupGUI(Spiel seinSpiel) {
		setDasSpiel(seinSpiel);
		unserPopup = new JDialog();
	}
	
	/**
	 * Fenster, mit der Anzeige der Mission des jeweiligen Spielers.
	 * @param besitzer Spieler, dem seine Mission angezeigt werden soll
	 */
	public void missionsAnzeige(Spieler besitzer) {
		unserPopup.setLayout(new FlowLayout());
		unserPopup.setSize(400, 200);
		unserPopup.setTitle("Missionsanzeige");
		unserPopup.add(new JLabel("  Deine Aufgabe ist es"));
		JLabel mission = new JLabel();
		if (besitzer.getMission().getClass().toString().equals("class danielolivermarco.anwendungslogik.MissionVierKontinente")){
			mission.setText("folgende vier Kontinente zu erobern:");
			unserPopup.add(mission);
			for (int i = 0; i < besitzer.getZuErobern().length; i++) {
				unserPopup.add(new JLabel(besitzer.getZuErobern()[i].getName()));
			}
		} else if (besitzer.getMission().getClass().toString().equals("class danielolivermarco.anwendungslogik.MissionDreiEinheiten")) {
			mission.setText("auf 15 deiner Laender mindestens drei Einheiten zu platzieren!");
			unserPopup.add(mission);
		} else if (besitzer.getMission().getClass().toString().equals("class danielolivermarco.anwendungslogik.MissionSpielerToeten")) {
			mission.setText("den Spieler " + besitzer.getToetungsziel().getName() + ", auszuloeschen!");
			unserPopup.add(mission);
		}
		unserPopup.setVisible(true);
		unserPopup.setModal(true);
		unserPopup.setLocationRelativeTo(besitzer.getKarte());
	}
	
	/**
	 * Ein Fenster, das mehrfach verwendet wird. Hier werden Standardmeldungen ausgegeben.
	 * @param s Text der im Fenster angezeigt werden soll.
	 * @param aktiverS Bei dem Spieler soll das Fenster angezeigt werden.
	 */
	public void textAnzeigen(String s, Spieler aktiverS) {
		unserPopup.setLayout(new GridLayout(2,1));
		unserLabel = new JLabel(s);
		JPanel textPanel = new JPanel(); 
		textPanel.setLayout(new FlowLayout()); // macht eig. keinen Sinn wollte nur ausprobieren ob interne Panels ihre groesse behalten--> tun sie nicht...
		textPanel.setSize(100,30);
		textPanel.add(unserLabel);
		unserPopup.add(textPanel);
		unserButton = new JButton("OK");
		unserButton.setSize(20, 20);
		unserButton.isMaximumSizeSet();
		unserButton.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			if (e.getSource().equals(unserButton)) {
    				unserPopup.setVisible(false);
    				unserPopup.dispose();
    			}
    		}
    	});
		unserPopup.add(unserButton);
		unserPopup.getLayout().removeLayoutComponent(unserButton);
		unserPopup.pack();
		unserPopup.setVisible(true);
		unserPopup.setModal(true);
		unserPopup.setLocationRelativeTo(aktiverS.getKarte());
	}
	
	/**
	 * Das Fenster oeffnet sich am Anfang jeder Runde. Es wird gefragt, ob der Spieler
	 * sich Einheiten kaufen moechte.
	 * @param s Der Spieler, bei dem das Fenster angezeigt werden soll.
	 */
	public void obEinheitenKaufen(Spieler s) {
		final Spieler x = s;
		ActionListener jaEinheitenKaufen = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PopupGUI einkaufen = new PopupGUI(dasSpiel);
				unserPopup.dispose();
				einkaufen.einheitenKaufen(x);
			}
		};
		
		ActionListener keineEinheitenKaufen = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				unserPopup.dispose();
			}
		};
		
		JPanel text = new JPanel(new GridLayout(2,1));
		text.add(new JLabel(s.getName() + ", du hast " + s.getGeld() + " Gold zur Verfuegung, "));
		text.add(new JLabel(" moechtest du dir davon Einheiten kaufen?"));
		JPanel dieButtons = new JPanel(new GridLayout(1,2));
		JButton jaKaufen = new JButton("Ja");
		jaKaufen.addActionListener(jaEinheitenKaufen);
		JButton nichtKaufen = new JButton("Nein");
		nichtKaufen.addActionListener(keineEinheitenKaufen);
		dieButtons.add(jaKaufen);
		dieButtons.add(nichtKaufen);
		unserPopup.setLayout(new BorderLayout());
		unserPopup.add(text,BorderLayout.NORTH);
		unserPopup.add(dieButtons, BorderLayout.SOUTH);
		unserPopup.pack();
		unserPopup.setVisible(true);
		unserPopup.setLocationRelativeTo(s.getKarte());
	}
	
	
	/**
	 * Das Kaufen-Fenster, in dem der Spieler Einheiten (Schwerter, Bogen, Pferde) kaufen kann, die dann
	 * dem Einheitenpool hinzugefuegt werden.
	 * @param s Der Spieler, der Einheiten kaufen moechte.
	 */
	public void einheitenKaufen(Spieler s) {
		final Spieler x = s;

		ActionListener schwertKaufen = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (x.getGeld() >= 100) {
					Einheit einheit = new Schwert();
					x.getSeinSpiel().getDerRundenverwalter().getGoldUndKaufen().fuerPoolKaufenGui(x, einheit);
					PopupGUI textanzeige = new PopupGUI(dasSpiel);
					String anzeige = "Du hast eine Schwerteinheit gekauft, du besitzt nun noch " + x.getGeld() +" Gold.";
					textanzeige.textAnzeigen(anzeige, x);
				} else {
					PopupGUI textanzeige = new PopupGUI(dasSpiel);
					String anzeige = "Dafuer reicht dein Geld nicht mehr, waehle eine andere Einheit oder beende das Einheiten kaufen mit dem Button Abbrechen";
					textanzeige.textAnzeigen(anzeige, x);
				}
			}
		};
		
		ActionListener bogenKaufen = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (x.getGeld() >= 200) {
					Einheit einheit = new Bogen();
					x.getSeinSpiel().getDerRundenverwalter().getGoldUndKaufen().fuerPoolKaufenGui(x, einheit);
					PopupGUI textanzeige = new PopupGUI(dasSpiel);
					String anzeige = "Du hast einen Bogenschuetzen gekauft, du besitzt nun noch " + x.getGeld() +" Gold.";
					textanzeige.textAnzeigen(anzeige, x);
				} else {
					PopupGUI textanzeige = new PopupGUI(dasSpiel);
					String anzeige = "Dafuer reicht dein Geld nicht mehr, waehle eine andere Einheit oder beende das Einheiten kaufen mit dem Button Abbrechen";
					textanzeige.textAnzeigen(anzeige, x);
				}
			}
		};
		
		ActionListener pferdKaufen = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (x.getGeld() >= 400) {
					Einheit einheit = new Pferd();
					x.getSeinSpiel().getDerRundenverwalter().getGoldUndKaufen().fuerPoolKaufenGui(x, einheit);
					PopupGUI textanzeige = new PopupGUI(dasSpiel);
					String anzeige = "Du hast einen Reiter gekauft, du besitzt nun noch " + x.getGeld() +" Gold.";
					textanzeige.textAnzeigen(anzeige, x);
				} else {
					PopupGUI textanzeige = new PopupGUI(dasSpiel);
					String anzeige = "Dafuer reicht dein Geld nicht mehr, waehle eine andere Einheit oder beende das Einheiten kaufen mit dem Button Abbrechen";
					textanzeige.textAnzeigen(anzeige, x);
				}
			}
		};
		
		ActionListener weiter = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PopupGUI einheitenSetzenAnzeige = new PopupGUI(dasSpiel);
				String anzeige =x.getName() + ", du kannst nun ein Land auswaehlen um eine Einheit zu Platzieren.";
				einheitenSetzenAnzeige.textAnzeigen(anzeige, x);
				unserPopup.dispose();
			}
		};
		
		
		unserPopup.setLayout(new BorderLayout());
		JPanel centerBorderButtons = new JPanel(new GridLayout(1,6));
		
		JButton buttonSchwert = new JButton("Schwertkaempfer");
		buttonSchwert.addActionListener(schwertKaufen);
		centerBorderButtons.add(buttonSchwert);
		
		centerBorderButtons.add(new JLabel("Kostet 100 Gold"));
		
		JButton buttonBogen = new JButton("Bogenschuetzen");
		buttonBogen.addActionListener(bogenKaufen);
		centerBorderButtons.add(buttonBogen);
		
		centerBorderButtons.add(new JLabel("Kostet 200 Gold"));
		
		JButton buttonPferd = new JButton("Reiter");
		buttonPferd.addActionListener(pferdKaufen);
		centerBorderButtons.add(buttonPferd);
		
		centerBorderButtons.add(new JLabel("Kostet 400 Gold"));
		
		JButton weiterButton = new JButton("Weiter");
		weiterButton.addActionListener(weiter);
		
		unserPopup.add(new JLabel("                                                               Welchen Einheitentypen moechtest du kaufen?"), BorderLayout.NORTH);
		unserPopup.add(centerBorderButtons, BorderLayout.CENTER);
		unserPopup.add(weiterButton, BorderLayout.SOUTH);
		
		unserPopup.pack();
		unserPopup.setVisible(true);
		unserPopup.setLocationRelativeTo(s.getKarte());
		
	}
	
	/**
	 * Einheiten koennen aus dem Einheitenpool in einem Land platziert werden.
	 * @param s Der Spieler, der die Einheiten platzieren moechte.
	 * @param l Das Land, in dem die Einheiten platziert werden sollen.
	 */
	public void einheitenPlatzieren(Spieler s, Land l) {
		final Spieler x = s;
		final Land land = l;
		ActionListener schwertPlatzieren = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean einheitDa = false;
				Einheit einheit = null;
				for(int xyz = 0; xyz < x.getEinheitenPool().size(); xyz++) {
					if (x.getEinheitenPool().get(xyz).getClass().equals(new Schwert().getClass())) {
						einheitDa = true;
						einheit = x.getEinheitenPool().get(xyz);
					}
				}
				if (einheitDa) {
					x.getSeinSpiel().getDerRundenverwalter().getGoldUndKaufen().einheitenSetzenAuswahlGui(x, land, einheit);
				} else {
					PopupGUI textanzeige = new PopupGUI(dasSpiel);
					String anzeige = "Du hast von diesem Einheitentypen keine Einheiten mehr, waehle eine andere oder Beende diese Phase";
					textanzeige.textAnzeigen(anzeige, x);
				}
			}
		};
		
		ActionListener bogenPlatzieren = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean einheitDa = false;
				Einheit einheit = null;
				for(int xyz = 0; xyz < x.getEinheitenPool().size(); xyz++) {
					if (x.getEinheitenPool().get(xyz).getClass().equals(new Bogen().getClass())) {
						einheitDa = true;
						einheit = x.getEinheitenPool().get(xyz);
					}
				}
				if (einheitDa) {
					x.getSeinSpiel().getDerRundenverwalter().getGoldUndKaufen().einheitenSetzenAuswahlGui(x, land, einheit);
				} else {
					PopupGUI textanzeige = new PopupGUI(dasSpiel);
					String anzeige = "Du hast von diesem Einheitentypen keine Einheiten mehr, waehle eine andere oder Beende diese Phase";
					textanzeige.textAnzeigen(anzeige, x);
				}
			}
		};
		
		ActionListener pferdPlatzieren = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean einheitDa = false;
				Einheit einheit = null;
				for(int xyz = 0; xyz < x.getEinheitenPool().size(); xyz++) {
					if (x.getEinheitenPool().get(xyz).getClass().equals(new Pferd().getClass())) {
						einheitDa = true;
						einheit = x.getEinheitenPool().get(xyz);
					}
				}
				if (einheitDa) {
					x.getSeinSpiel().getDerRundenverwalter().getGoldUndKaufen().einheitenSetzenAuswahlGui(x, land, einheit);
				} else {
					PopupGUI textanzeige = new PopupGUI(dasSpiel);
					String anzeige = "Du hast von diesem Einheitentypen keine Einheiten mehr, waehle eine andere oder Beende diese Phase";
					textanzeige.textAnzeigen(anzeige, x);
				}
			}
		};
		
		ActionListener weiter = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PopupGUI einheitenSetzenAnzeige = new PopupGUI(dasSpiel);
				String anzeige =x.getName() + ", du kannst nun ein Land auswaehlen um eine Einheit zu Platzieren.";
				einheitenSetzenAnzeige.textAnzeigen(anzeige, x);
				unserPopup.dispose();
			}
		};
		
		
		unserPopup.setLayout(new BorderLayout());
		JPanel centerBorderButtons = new JPanel(new GridLayout(1,3));
		
		JButton buttonSchwert = new JButton("Schwertkaempfer");
		buttonSchwert.addActionListener(schwertPlatzieren);
		centerBorderButtons.add(buttonSchwert);
		
		JButton buttonBogen = new JButton("Bogenschuetzen");
		buttonBogen.addActionListener(bogenPlatzieren);
		centerBorderButtons.add(buttonBogen);
		
		JButton buttonPferd = new JButton("Reiter");
		buttonPferd.addActionListener(pferdPlatzieren);
		centerBorderButtons.add(buttonPferd);
		
		JButton weiterButton = new JButton("Weiter");
		weiterButton.addActionListener(weiter);
		
		unserPopup.add(new JLabel("                                           Welchen Einheitentypen moechtest du in diesem Land platzieren ?"), BorderLayout.NORTH);
		unserPopup.add(centerBorderButtons, BorderLayout.CENTER);
		unserPopup.add(weiterButton, BorderLayout.SOUTH);
		
		unserPopup.pack();
		unserPopup.setVisible(true);
		unserPopup.setLocationRelativeTo(s.getKarte());
		
	}
	
	/**
	 * Armee in einem bestimmten Land zusammenstellen. Diese Armee kann aufgeloest oder ausgewaehlt werden,
	 * um damit ein Nachbarland anzugreifen.
	 * @param s Der Spieler, der eine Armee zusammenstellen moechte.
	 * @param l Das Land, in dem die Armee zusammengestellt werden soll.
	 */
	public void armeeZusammenstellen(Spieler s, Land l) {
		final Spieler x = s;
		final Land ursprung = l;
		
			ActionListener schwertAuswaehlen = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					boolean einheitDa = false;
					Einheit einheit = null;
					for(int xyz = 0; xyz < ursprung.getSpielerArmee().getArmee().size(); xyz++) {
						if ((ursprung.getSpielerArmee().getArmee().size() > 1) && ursprung.getSpielerArmee().getArmee().get(xyz).getClass().equals(new Schwert().getClass())) {
							einheitDa = true;
							einheit = ursprung.getSpielerArmee().getArmee().get(xyz);
						}  else if (!(ursprung.getSpielerArmee().getArmee().size() > 1)) {
							PopupGUI textanzeige = new PopupGUI(dasSpiel);
							String anzeige = "Du brauchst mindestens eine Einheit im Land";
							textanzeige.textAnzeigen(anzeige, x);
						}
					}
					if (einheitDa) {
						x.getSeinSpiel().getDerRundenverwalter().getAttacke().getAngriffsArmee().armeeZuwachs(einheit);
						ursprung.getSpielerArmee().remEinheit(einheit);
						PopupGUI angreifenVerfuegbar = new PopupGUI(dasSpiel);
						String anzeige =x.getName() + ", du hast deiner Armee einen Schwertkaempfer hinzugefuegt.";
						angreifenVerfuegbar.textAnzeigen(anzeige, x);
					} else {
						PopupGUI textanzeige = new PopupGUI(dasSpiel);
						String anzeige = "Du hast von diesem Einheitentypen keine Einheiten mehr, waehle eine andere!";
						textanzeige.textAnzeigen(anzeige, x);
					}
				}
			};
			
			ActionListener bogenAuswaehlen = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					boolean einheitDa = false;
					Einheit einheit = null;
					for(int xyz = 0; xyz < ursprung.getSpielerArmee().getArmee().size(); xyz++) {
						if ((ursprung.getSpielerArmee().getArmee().size() > 1) && ursprung.getSpielerArmee().getArmee().get(xyz).getClass().equals(new Bogen().getClass())) {
							einheitDa = true;
							einheit = ursprung.getSpielerArmee().getArmee().get(xyz);
						} else if (!(ursprung.getSpielerArmee().getArmee().size() > 1)) {
							PopupGUI textanzeige = new PopupGUI(dasSpiel);
							String anzeige = "Du brauchst mindestens eine Einheit im Land";
							textanzeige.textAnzeigen(anzeige, x);
						}
					}
					if (einheitDa) {
						x.getSeinSpiel().getDerRundenverwalter().getAttacke().getAngriffsArmee().armeeZuwachs(einheit);
						ursprung.getSpielerArmee().remEinheit(einheit);
						PopupGUI angreifenVerfuegbar = new PopupGUI(dasSpiel);
						String anzeige =x.getName() + ", du hast deiner Armee einen Bogenschuetzen hinzugefuegt.";
						angreifenVerfuegbar.textAnzeigen(anzeige, x);
					} else {
						PopupGUI textanzeige = new PopupGUI(dasSpiel);
						String anzeige = "Du hast von diesem Einheitentypen keine Einheiten mehr, waehle eine andere!";
						textanzeige.textAnzeigen(anzeige, x);
					}
				}
			};
			
			ActionListener pferdAusaehlen = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					boolean einheitDa = false;
					Einheit einheit = null;
					for(int xyz = 0; xyz < ursprung.getSpielerArmee().getArmee().size(); xyz++) {
						if ((ursprung.getSpielerArmee().getArmee().size() > 1) && ursprung.getSpielerArmee().getArmee().get(xyz).getClass().equals(new Pferd().getClass())) {
							einheitDa = true;
							einheit = ursprung.getSpielerArmee().getArmee().get(xyz);
						}  else if (!(ursprung.getSpielerArmee().getArmee().size() > 1)) {
							PopupGUI textanzeige = new PopupGUI(dasSpiel);
							String anzeige = "Du brauchst mindestens eine Einheit im Land";
							textanzeige.textAnzeigen(anzeige, x);
						}
					}
					if (einheitDa) {
						x.getSeinSpiel().getDerRundenverwalter().getAttacke().getAngriffsArmee().armeeZuwachs(einheit);
						ursprung.getSpielerArmee().remEinheit(einheit);
						PopupGUI angreifenVerfuegbar = new PopupGUI(dasSpiel);
						String anzeige =x.getName() + ", du hast deiner Armee einen Reiter hinzugefuegt.";
						angreifenVerfuegbar.textAnzeigen(anzeige, x);
					} else if (!einheitDa) {
						PopupGUI textanzeige = new PopupGUI(dasSpiel);
						String anzeige = "Du hast von diesem Einheitentypen keine Einheiten mehr, waehle eine andere!";
						textanzeige.textAnzeigen(anzeige, x);
					}
				}
			};
			
			ActionListener armeeAuswaehlen = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (x.getSeinSpiel().getDerRundenverwalter().getAttacke().getAngriffsArmee().getArmee().size() == 0) {
						ArmeeAufloeser armeeAufloesenLogik = new ArmeeAufloeser();
						armeeAufloesenLogik.init(x.getSeinSpiel().getDerRundenverwalter().getAttacke().getAngriffsArmee(), ursprung);
						x.setHatArmeeImAermel(false);
						x.getSeinSpiel().getDerRundenverwalter().getAttacke().setAngriffsArmee(null);
						PopupGUI angreifenVerfuegbar = new PopupGUI(dasSpiel);
						String anzeige =" Die Armee hat keine Einheiten und wird daher aufgeloest";
						angreifenVerfuegbar.textAnzeigen(anzeige, x);
						unserPopup.dispose();
					} else {
						PopupGUI angreifenVerfuegbar = new PopupGUI(dasSpiel);
						String anzeige = null;
						if (x.isAngriffDurchfuehren() && !x.isAngriffDurchfuehrenBereitsGetan()) {
							 anzeige = x.getName() + ", du kannst nun ein Land auswaehlen um dieses mit deiner Armee anzugreifen";
						} else if( x.isVerschiebenDurchfuehren() && !x.isVerschiebenDurchfuehrenBereitsGetan()) {
							anzeige = x.getName() + ", du kannst nun eins deiner benachbarten Laender auswaehlen und dein Armee dorthin verschieben";
						}
						angreifenVerfuegbar.textAnzeigen(anzeige, x);
						unserPopup.dispose();
					}
				}
			};
			
			ActionListener armeeAufloesen = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ArmeeAufloeser armeeAufloesenLogik = new ArmeeAufloeser();
					armeeAufloesenLogik.init(x.getSeinSpiel().getDerRundenverwalter().getAttacke().getAngriffsArmee(), ursprung);
					x.setHatArmeeImAermel(false);
					x.getSeinSpiel().getDerRundenverwalter().getAttacke().setAngriffsArmee(null);
					PopupGUI armeeAufloesen = new PopupGUI(dasSpiel);
					String anzeige =x.getName() + ", deine grade erstellte Armee wurde im Ursprungsland wieder aufgeloest.";
					armeeAufloesen.textAnzeigen(anzeige, x);
					unserPopup.dispose();
				}
			};
			
			unserPopup.setLayout(new BorderLayout());
		
			JPanel northText = new JPanel(new GridLayout(2,1));
			
			JLabel text1 = new JLabel("Welche Einheiten moechtest du");
			JLabel text2 = new JLabel("deiner Armee hinzufuegen");
			
			northText.add(text1);
			northText.add(text2);
			
			JPanel centerBorderButtons = new JPanel(new GridLayout(1,3));
			
			JButton buttonSchwert = new JButton("Schwertkaempfer");
			buttonSchwert.addActionListener(schwertAuswaehlen);
			centerBorderButtons.add(buttonSchwert);
			
			JButton buttonBogen = new JButton("Bogenschuetzen");
			buttonBogen.addActionListener(bogenAuswaehlen);
			centerBorderButtons.add(buttonBogen);
			
			JButton buttonPferd = new JButton("Reiter");
			buttonPferd.addActionListener(pferdAusaehlen);
			centerBorderButtons.add(buttonPferd);
			
			JPanel southButtons = new JPanel(new GridLayout(1,2));
			
			JButton armeeAuswaehlenButton = new JButton("Armee auswaehlen");
			armeeAuswaehlenButton.addActionListener(armeeAuswaehlen);
			
			JButton armeeAufloesenButton = new JButton("Armee aufloesen");
			armeeAufloesenButton.addActionListener(armeeAufloesen);
			
			southButtons.add(armeeAuswaehlenButton);
			southButtons.add(armeeAufloesenButton);
			
			unserPopup.add(northText, BorderLayout.NORTH);
			unserPopup.add(centerBorderButtons, BorderLayout.CENTER);
			unserPopup.add(southButtons, BorderLayout.SOUTH);
			
			unserPopup.pack();
			unserPopup.setVisible(true);
			unserPopup.setLocationRelativeTo(s.getKarte());
	}
		
	/**
	 * Methode wird in KartenGui.java aufgerufen. Zeigt den Besitz des entsprechenden Spielers an.
	 * (Laender+Armee und die Kontinente)
	 * @param spielerIndex Hier wird der Index des Spielers in der Spielerliste (aus Spiel.java) uebergeben.
	 */
	public void laenderVomSpielerAnzeigen(int spielerIndex,Spieler besitzer) {
		Spieler spieler = dasSpiel.getSpielerListe().get(spielerIndex);
//		System.out.println(spieler.getName());
		FlowLayout flowLayout = new FlowLayout();
//		flowLayout.setHgap(30);
		unserPopup.setLayout(flowLayout);
		unserPopup.setTitle("Laender und Kontinente von "+spieler.getName());
		//Zeilenanzahl fuer Laender und Kontinente
		int zeilenLaender = spieler.getBesitzLaender().size();
		int zeilenKontinente = 10;
		//JPanel fuer Laender und Kontinente
		JPanel laender = new JPanel(new GridLayout(zeilenLaender,2,1,5));
		JPanel mitte = new JPanel(new GridLayout(zeilenLaender,1));
		for(int i=0;i<spieler.getBesitzLaender().size();i++) {
			Land land = spieler.getBesitzLaender().get(i);
			laender.add(new JLabel(land.getName()));
			int s = 0;
			int b = 0;
			int p = 0;
			for (int y = 0; y < land.getSpielerArmee().getArmee().size(); y++) {
				String klasse = land.getSpielerArmee().getArmee().get(y).getClass().toString();
				if(klasse.equals("class danielolivermarco.datenhaltung.Schwert")) {
						s++;
				} else if(klasse.equals("class danielolivermarco.datenhaltung.Bogen")) {
						b++;
				} else if(klasse.equals("class danielolivermarco.datenhaltung.Pferd")) {
						p++;
				}
			}
			laender.add(new JLabel(s+"x Schwert, "+b+"x Bogen, "+p+"x Pferd"));
			
			mitte.add(new JLabel("|   |"));
		}
		unserPopup.add(laender);
		unserPopup.add(mitte);
		
		JPanel kontinente = new JPanel(new GridLayout(zeilenKontinente,1,1,5));
		
		if(besitzer.getSpielerNr()==spieler.getSpielerNr()) {
			kontinente.add(new JLabel("Du besitzt folgende Kontinente:"));
		} else {
			kontinente.add(new JLabel(spieler.getName()+" besitzt folgende Kontinente:"));
		}
		
		for (int i = 0; i < dasSpiel.getKarte().getKontinente().length; i++) {
			if (dasSpiel.getKarte().getKontinente()[i].getOwner() == spieler) {
				kontinente.add(new JLabel(dasSpiel.getKarte().getKontinente()[i].getName()));
			}
		}

		if(kontinente.getComponentCount()==1) {
			if(besitzer.getSpielerNr()==spieler.getSpielerNr()) {
				kontinente.add(new JLabel("Du besitzt keine Kontinente."));
			} else {
				kontinente.add(new JLabel(spieler.getName()+" besitzt keine Kontinente."));
			}
		}
		unserPopup.add(kontinente);
		unserPopup.setVisible(true);
		unserPopup.pack();
		unserPopup.setLocationRelativeTo(besitzer.getKarte());
	}

	public void geldAnzeigen(Spieler spieler) {
		String text = "Du hast " + spieler.getGeld() + " Gold."; 
		this.textAnzeigen(text, spieler);
	}

	/**
	 * Zeigt die Einheiten an, die sich im Einheitenpool befinden.
	 * @param spieler Der Spieler, der den Einheitenpool angezeigt haben moechte.
	 */
	public void einheitenPoolAnzeigen(Spieler spieler) {
		final Spieler x = spieler;
		final JDialog einheitenPoolAnzeige = new JDialog();
		einheitenPoolAnzeige.setLayout(new BorderLayout());
		int s = 0;
		int b = 0;
		int p = 0;
		for (int y = 0; y < spieler.getEinheitenPool().size(); y++) {
			String klasse = spieler.getEinheitenPool().get(y).getClass().toString();
			if(klasse.equals("class danielolivermarco.datenhaltung.Schwert")) {
					s++;
			} else if(klasse.equals("class danielolivermarco.datenhaltung.Bogen")) {
					b++;
			} else if(klasse.equals("class danielolivermarco.datenhaltung.Pferd")) {
					p++;
			}
		}
		einheitenPoolAnzeige.add(new JLabel("Du hast in deinem Einheitenpool folgende Einheiten:"), BorderLayout.NORTH);
		einheitenPoolAnzeige.add(new JLabel("Schwertkaempfer: " + s + " Bogenschuetzen: " + b + " Reiter: " + p),BorderLayout.CENTER);
		
		ActionListener einheitenKaufen = new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if (x.isEinheitenKaufenUndSetzen() && !x.isEinheitKaufenUndSetzenBereitsGetan()) {
					PopupGUI einkaufen = new PopupGUI(dasSpiel);
					einheitenPoolAnzeige.dispose();
					einkaufen.einheitenKaufen(x);
				} else {
					PopupGUI textanzeige = new PopupGUI(dasSpiel);
					String anzeige = "Du bist nicht mehr in der richtigen Phase um Einheiten zu kaufen!";
					textanzeige.textAnzeigen(anzeige, x);
				}
				
			}
		};
		
		ActionListener ok = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				einheitenPoolAnzeige.dispose();
			}
		};
		
		JPanel buttonPanel = new JPanel(new GridLayout(1,2));
		JButton einheitenKaufenButton = new JButton("Einheiten Kaufen");
		JButton okButton = new JButton("Ok");
		
		einheitenKaufenButton.addActionListener(einheitenKaufen);
		okButton.addActionListener(ok);
		
		buttonPanel.add(einheitenKaufenButton);
		buttonPanel.add(okButton);
		
		einheitenPoolAnzeige.add(buttonPanel,BorderLayout.SOUTH);
		
		einheitenPoolAnzeige.setVisible(true);
		einheitenPoolAnzeige.pack();
		einheitenPoolAnzeige.setLocationRelativeTo(spieler.getKarte());
	}
	
	/**
	 * 
	 * @return Das Spiel.
	 */
	public Spiel getDasSpiel() {
		return dasSpiel;
	}

	/**
	 * Das Spiel wird gesetzt.
	 * @param dasSpiel Das Spiel wird uebergeben.
	 */
	public void setDasSpiel(Spiel dasSpiel) {
		this.dasSpiel = dasSpiel;
	}
}
