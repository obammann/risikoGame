package danielolivermarco.gui;



import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import danielolivermarco.anwendungslogik.*;
import danielolivermarco.datenhaltung.*;
import danielolivermarco.persistens.*;
import danielolivermarco.cui.*;

/**
 * Hauptklasse des Spiels in der GUI Version, in dieser wird sich die Main befinden
 * 
 * @author Marco
 *
 */
public class SpielGUIClient implements Serializable {
	/**
	 * @param Die liste der Spieler dieses Spiels 
	 */
	private ArrayList<Spieler> spielerListe = new ArrayList<Spieler>(); 
	/**
	 * @param Die Anzahl der Spieler dieses Spiels 
	 */
	private int anzahlSpieler;
	
	/**
	 * @param Der Gewinner des Spiels
	 */
	private Spieler sieger;
	
	private Map karte; 
	
	boolean hatSpielGeladen = false;
	
	/**
	 * Hier wird das Spiel initiert: Interface, Missionen, Spieler werden erstellt. 
	 * 
	 * @throws IOException 
	 */
	public void init() throws IOException {
		interfaceEinstellen();
		missionenEinstellen();
		spielerEinstellen();
	}
	
	/**
	 * Fuer jeden Spieler wird ein neues persoenliches Interface erstellt
	 * SpielerInterface --> siehe Klasse SpielerInterface
	 */
	private void interfaceEinstellen() {
		for (int i = 0; i < spielerListe.size(); i++) {
			spielerListe.get(i).setPersoenlichesInterface(new SpielerInterface(spielerListe.get(i)));
		}
	}

	/**
	 * Bei einer Spieleranzahl von 2 oder 3 werden jedem Spieler 35 Schwerteinheiten
	 * seinem Einheitenpool hinzugefuegt.
	 * Bei einer Spieleranzahl von 4 werden jedem Spieler 30 Schwerteinheiten
	 * seinem Einheitenpool hinzugefuegt.
	 * @return Gibt true zurueck wenn die Einstellung der Spieler geklappt hat.
	 */
	public boolean spielerEinstellen() {
		if (spielerListe.size() == 2 || spielerListe.size() == 3) {
			for (int i = 0; i < spielerListe.size(); i++) {
				for (int y = 0; y < 35; y++){
					spielerListe.get(i).addEinheitenPool(new Schwert());
				}
			}
			return true;
		} else if (spielerListe.size() == 4) {
			for (int i = 0; i < spielerListe.size(); i++) {
				for (int y = 0; y < 30; y++){
					spielerListe.get(i).addEinheitenPool(new Schwert());
				}
			}
			return true;
		} else {
			return false;
		}
	}
	
	
	/**
	 * Hier werden die Missionen an die Spieler verteilt.
	 * Bei Spieleranzahl = 2 bekommt jeder Spieler die MissionVierKontinente.
	 * Bei Spieleranzahl = 3 werden die Missionen MissionVierKontinente und
	 * MissionDreiEinheiten random verteilt.
	 * Bei Spieleranzahl = 4 werden die Missionen MissionVierKontinente,
	 * MissionDreiEinheiten und MissionSpielerToeten random verteilt.
	 * Konnten die Missionen nicht zugeteilt werden, wird eine Fehlermeldung
	 * ausgegeben.
	 * Am Ende werden die Missionen der Spieler ausgegeben.
	 */
	private void missionenEinstellen() {
		Random random = new Random();
		if (anzahlSpieler == 2) {
			for (int i = 0; i < 2; i++) {
				spielerListe.get(i).setMission(new MissionVierKontinente(spielerListe.get(i), karte.getKontinente()));
			}
		} else if (anzahlSpieler == 3) {
			for (int y = 0; y < 3; y++) {
				int rand = random.nextInt(2) + 1; // gibt eine Zahl zwischen 1 und 2 zurueck
				if (rand == 1) {
					spielerListe.get(y).setMission(new MissionVierKontinente(spielerListe.get(y), karte.getKontinente()));
				} else if (rand == 2) {
					spielerListe.get(y).setMission(new MissionDreiEinheiten(spielerListe.get(y)));
				}
			}
		} else if (anzahlSpieler == 4) {
			for (int x = 0; x < 4; x++) {
				int rand = random.nextInt(3) + 1; // gibt eine Zahl zwischen 1 und 3 zurueck
				System.out.println(rand);
				if (rand == 1) {
					spielerListe.get(x).setMission(new MissionVierKontinente(spielerListe.get(x), karte.getKontinente()));
				} else if (rand == 2) {
					spielerListe.get(x).setMission(new MissionDreiEinheiten(spielerListe.get(x)));
				} else if (rand == 3) {
					spielerListe.get(x).setMission(new MissionSpielerToeten(spielerListe.get(x), this.spielerListe));
				}
			}
		} else {
			System.out.println("Ein Fataler Fehler tritt bei der Missionseinstellung auf!");
		}
		for (int i = 0; i < anzahlSpieler; i++)
		System.out.println(spielerListe.get(i).getMission());
		
	}
	
	/**
	 * Ein neues Mapobjekt wird erstellt und die Laender und
	 * Kontinente geladen.
	 */
	public void mapEinstellen() throws IOException {
		karte = new Map();
		karte.ladeLaender();
		karte.ladeKontinente();
	}
	
	/**
	 * 
	 * @return Gibt die Map (Karte) zurueck.
	 */
	public Map getKarte() {
		return this.karte;
	}
	
	/**
	 * 
	 * @return Gibt die Spielerliste zurueck.
	 */
	public ArrayList<Spieler> getSpielerListe() {
		return spielerListe;
	}
	
	/**
	 * 
	 * @return Gibt den Sieger zurueck.
	 */
	public Spieler getSieger() {
		return sieger;
	}

	/**
	 * Der Sieger wird gesetzt.
	 * @param sieger Der Sieger vom Typ Spieler wird uebergeben.
	 */
	public void setSieger(Spieler sieger) {
		this.sieger = sieger;
	}
	
	/**
	 * 
	 * @return Die Anzahl der Spieler.
	 */
	public int getAnzahlSpieler() {
		return anzahlSpieler;
	}

	/**
	 * Die Anzahl der Spieler wird gesetzt.
	 * @param anzahlSpieler Die Anzahl der Spieler wird uebergeben.
	 */
	public void setAnzahlSpieler(int anzahlSpieler) {
		this.anzahlSpieler = anzahlSpieler;
	}
	
	/**
	 * Das Spiel wird gestartet, indem ein neues Spielobjekt erstellt wird und das Spiel
	 * die Karte einstellt und eine Methode in der KartenGUI aufruft.
	 * @throws IOException
	 */
	public void spielStarten() throws IOException {
		Spiel dasSpiel = new Spiel();
		dasSpiel.mapEinstellen();
		dasSpiel.getStart().waehleSpielerAnz();
	}
	
	public static void main(String[] args) throws IOException {	
		SpielGUIClient derClient = new SpielGUIClient();
		Spiel dasSpiel = new Spiel();
		SpielLadenInterfaceGui dasLadenInterface = new SpielLadenInterfaceGui(dasSpiel, derClient);
		dasLadenInterface.init();

	}
}
