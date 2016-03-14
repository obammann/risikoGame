package danielolivermarco.anwendungslogik;



import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import danielolivermarco.datenhaltung.*;
import danielolivermarco.gui.LogikAnspracheGui;
import danielolivermarco.gui.StartGUI;
import danielolivermarco.cui.*;
import danielolivermarco.persistens.*;

/**
 * Hauptklasse des Spiels, hier werden Dinge wie die Spieler und die einzelnen Phasen 
 * die das Spiel haeufiger durchgeht abgespeichert und zum Aufruf zur Verfuegung gestellt.
 *
 */
public class Spiel implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private ArrayList<Spieler> spielerListe = new ArrayList<Spieler>(); 

	private int anzahlSpieler;

	private Spieler sieger;
	
	private StartGUI start;
	
	private Map karte;
	private Anfangsphase dieAnfangsphase;
	private Rundenverwaltung derRundenverwalter; 
	private LogikAnspracheGui logikAnspracheGui;
	
	/**
	 * Im Konstruktor werden die benoetigten Phasen bereits erstellt und 
	 * ueber die Setter gespeichert. 
	 * zudem werden die eventuell benoetigten GUI Objekte erstellt.
	 */
	public Spiel() {
		setDieAnfangsphase(new Anfangsphase(this));
		setDerRundenverwalter(new Rundenverwaltung(this));
		start = new StartGUI(this);
		logikAnspracheGui = new LogikAnspracheGui();
	}
	/**
	 * Startet fuer jeden Spieler die GUI
	 */
	public void starteSpiel() {
		for (int i = 0; i<this.getAnzahlSpieler();i++) {
			this.spielerListe.get(i).start();
		}
	}
	/**
	 * Erstellt fuer das Spiel in der GUI Version die Interfaces und stellt die Spieler und deren Mission ein 
	 * 
	 * @throws IOException
	 */
	public void init() throws IOException {
		if(spielerEinstellen() != true){// Missionspool, Einheitenpool je nach Spieleranzahl
			System.out.println("Ein fataler Fehler tritt beim Spielereinstellen auf");
			return;
		}
		interfaceEinstellen();
		missionenEinstellen();
	}
	/**
	 * Erstellt das Spiel in der CUI Version, laedt die Karte und stellt die Spieler,
	 * deren Interfaces und die Missionen ein.
	 * @throws IOException
	 */
	public void initCUI() throws IOException {
		mapEinstellen();  	// Kontinente laden, Laender laden etc 
		spielerwahl();
		if(spielerEinstellen() != true){// Missionspool, Einheitenpool je nach Spieleranzahl
			System.out.println("Ein fataler Fehler tritt beim Spielereinstellen auf");
			return;
		}
		interfaceEinstellen();
		missionenEinstellen();
	}
	/**
	 * Gibt jedem Spieler sein Interface in der CUI Version
	 */
	private void interfaceEinstellen() {
		for (int i = 0; i < spielerListe.size(); i++) {
			spielerListe.get(i).setPersoenlichesInterface(new SpielerInterface(spielerListe.get(i)));
		}
	}
	/**
	 * Wird beim start der CUI Version angesteuert und sorgt dafuer das beim Spiel die Spielerzahl eingestellt wird
	 */
	public void spielerwahl() {
		boolean richtigeAnzahl = false;
		StartInterface s1 = new StartInterface(); 
		do {
			s1.spielerzahlAuswaehlen();
			anzahlSpieler = IO.readInt();
			if (anzahlSpieler <= 1 || anzahlSpieler > 4) {
				s1.invalideZahl();
			} else {
				richtigeAnzahl = true; 
			}
		} while (richtigeAnzahl == false);
		for (int i = 1; i < anzahlSpieler + 1; i++) {
			s1.spielerNameAuswaehlen(i);
			String name = IO.readString();
			spielerListe.add(new Spieler(name, i, this));
		}
	}
	/**
	 * Gibt jedem Spieler beim Spielstart seine Anfangseinheiten 
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
	 * Nach der Spielererstellung wird je nachdem wieviele Spieler spielen 
	 * verschiedene Missionen an die Spieler gegeben.
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
	}
	/**
	 * Laedt die Karte
	 * @throws IOException
	 */
	public void mapEinstellen() throws IOException {
		karte = new Map();
		karte.ladeLaender();
		karte.ladeKontinente();
	}
	/**
	 * Setzt die Spielerzahl
	 * @param spielerAnz Benoetigt die zahl der Spieler als Integer
	 */
	public void spielerwahl(int spielerAnz) {
		anzahlSpieler = spielerAnz;
	}
	/**
	 * 
	 * @param spielerNr Benoetigt die Spielernummer des Spielers dem folgend ein Name in der GUI gegeben werden soll
	 */
	public void spielerErzeugen(int spielerNr) {
		start.spielerNamen(spielerNr);
	}
	/**
	 * Fuegt dem Spiel in der SpielerListe einen Spieler zu
	 * @param name Benoetigt den Namen des Spielers	
	 * @param nr Benoetigt die Spielernummer des Spielers
	 */
	public void spielerAdd(String name, int nr) {
		spielerListe.add(new Spieler(name, nr, this));
	}
	/**
	 * 
	 * @return gibt die Karte des Spiels zurueck
	 */
	public Map getKarte() {
		return this.karte;
	}
	/**
	 * 
	 * @return gibt die Liste der Spieler zurueck
	 */
	public ArrayList<Spieler> getSpielerListe() {
		return spielerListe;
	}
	/**
	 * 
	 * @return Gibt den Sieger des Spiels wieder
	 */
	public Spieler getSieger() {
		return sieger;
	}
	/**
	 * Stellt den Sieger des Spiels ein
	 * @param sieger Benoetigt den Spieler der gewonnen hat
	 */
	public void setSieger(Spieler sieger) {
		this.sieger = sieger;
	}
	/**
	 * Bekommt von der CUI die Anfangsphase und den Rundenverwalter und speichert diesen 
	 * @param dieAnfangsphase Benoetigt die Anfangsphase des aktuellen Spiels
	 * @param derRundenverwalter Benoetigt den Rundenverwalter des aktuellen Spiels
	 */
	public void einfuegen(Anfangsphase dieAnfangsphase,
			Rundenverwaltung derRundenverwalter) {
		this.dieAnfangsphase = dieAnfangsphase;
		this.derRundenverwalter = derRundenverwalter;
		
	}
	/**
	 * 
	 * @return Gibt die Anfangsphase zurueck
	 */
	public Anfangsphase getDieAnfangsphase() {
		return dieAnfangsphase;
	}
	/**
	 * 
	 * @param dieAnfangsphase Benoetigt die Anfangsphase die gespeichert wird
	 */
	public void setDieAnfangsphase(Anfangsphase dieAnfangsphase) {
		this.dieAnfangsphase = dieAnfangsphase;
	}
	/**
	 * 
	 * @return Gibt den Rundenverwalter zurueck
	 */
	public Rundenverwaltung getDerRundenverwalter() {
		return derRundenverwalter;
	}
	/**
	 * 
	 * @param derRundenverwalter Benoetigt den Rundernverwalter der gespeichert wird 
	 */
	public void setDerRundenverwalter(Rundenverwaltung derRundenverwalter) {
		this.derRundenverwalter = derRundenverwalter;
	}

	/**
	 * 
	 * @return gibt die StartGui zurueck
	 */
	public StartGUI getStart() {
		return start;
	}

	/**
	 * 
	 * @param start Benotigt die StartGUI, die gespeichert wird
	 */
	public void setStart(StartGUI start) {
		this.start = start;
	}
	/**
	 * 
	 * @return Gibt die Anzahl der Spieler zurueck
	 */
	public int getAnzahlSpieler() {
		return anzahlSpieler;
	}
	/**
	 * Setzt die Anzahl der Spieler 
	 * @param anzahlSpieler 
	 */
	public void setAnzahlSpieler(int anzahlSpieler) {
		this.anzahlSpieler = anzahlSpieler;
	}
	/**
	 * 
	 * @return Gibt die LogikAnspracheGui zurueck
	 */
	public LogikAnspracheGui getLogikAnspracheGui() {
		return logikAnspracheGui;
	}
	/**
	 * 
	 * @param anfangsphaseGui Benoetigt die LogikAnspracheGui die gespeichert wird 
	 */
	public void setAnfangsphaseGui(LogikAnspracheGui anfangsphaseGui) {
		this.logikAnspracheGui = anfangsphaseGui;
	}
	/**
	 * Speichert das Spiel in der CUI Version des Spiels 
	 */
	public void spielSpeichern(){
		// fragen ob gespeichert werden soll, wenn nein raus
		LadenSpeichernInterface ladenspeichern = new LadenSpeichernInterface();
		if( ladenspeichern.willSpeichern() ){
			// alle für den Spielstand wichtigen Attribute serialisieren ( Spiel, AngriffsModus, BuyAndSet, VerschiebenModus, PruefenmModus )
			// und in Datei speichern
			try (final ObjectOutputStream oop = new ObjectOutputStream(new FileOutputStream("spielstandcui.ser"))){
				try{
					//Rundenverwaltung speichert sich selber
					
					oop.writeObject(this);
				}catch (NotSerializableException e){
					e.printStackTrace();
				}
			}catch (IOException e){
				System.err.println("Error");
			}
			// dann evtl fragen ob weitergespielt werden soll
		}
	}
	/**
	 * Speichert das Spiel in der GUI Version des Spiels 
	 */
	public void spielSpeichernGui(){
			try (final ObjectOutputStream oop = new ObjectOutputStream(new FileOutputStream("spielstandgui.ser"))){
				try {
					oop.writeObject(this);
				}catch (NotSerializableException e){
					e.printStackTrace();
				}
			}catch (IOException e){
				System.err.println("Error");
			}
	}
}