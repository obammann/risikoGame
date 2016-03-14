package danielolivermarco.cui;

import java.io.Serializable;

import danielolivermarco.anwendungslogik.*;
import danielolivermarco.datenhaltung.*;
/**
 * Erstellt ausgaben fuer einen laufenden Kampf
 */
public class KampfInterface {

	private Spieler aktiverS;
	private Spieler gegnerS;
	private boolean angreifer = false;
	private Kampf kampf;
	/**
	 * Im Konstruktor speichert die Klasse welchem Spieler das Interface gehoert und 
	 * welcher Kampf die ausgaben liefert
	 * @param s	Benoetigt den Spieler dem das Interface gehoert
	 * @param g Benoetigt den gegenerischen Spieler zu Anzeigezwecken
	 * @param k	Benoetigt den Kampf der das Interface ansteuert 
	 */
	public KampfInterface(Spieler s, Spieler g, Kampf k){
		aktiverS = s;
		gegnerS = g;
		kampf = k;
		if (kampf.angreiferS.equals(aktiverS)) {
			angreifer = true;
		}
	}
	/**
	 * Hier wird die Anzeige generiert die dem Spieler seine Einheiten
	 * und die Einheiten seines Gegeners anzeigt
	 */
	public void init() {
		System.out.println(aktiverS.getName() + ", du hast folgende Einheiten:");
		System.out.print("Schwerter: ");
		if (angreifer == true){
			System.out.println(kampf.getASchwerterSize());
		} else {
			System.out.println(kampf.getVSchwerterSize());
		} 
		System.out.print("Bogen: ");
		if (angreifer == true) {
			System.out.println(kampf.getABogenSize());
		} else {
			System.out.println(kampf.getVBogenSize());
		}
		System.out.print("Pferde: ");
		if (angreifer == true) {
			System.out.println(kampf.getAPferdeSize());
		} else {
			System.out.println(kampf.getVPferdeSize());
		}
		System.out.print("Der ");
		if (angreifer == true){
			System.out.print("Verteidiger ");
		} else {
			System.out.print("Angreifer ");
		}
		System.out.println(gegnerS.getName() + " hat folgende Einheiten:");
		System.out.print("Schwerter: ");
		if (angreifer == true){
			System.out.println(kampf.getVSchwerterSize());
		} else {
			System.out.println(kampf.getASchwerterSize());
		} 
		System.out.print("Bogen: ");
		if (angreifer == true) {
			System.out.println(kampf.getVBogenSize());
		} else {
			System.out.println(kampf.getABogenSize());
		}
		System.out.print("Pferde: ");
		if (angreifer == true) {
			System.out.println(kampf.getVPferdeSize());
		} else {
			System.out.println(kampf.getAPferdeSize());
		}
	}
	/**
	 * Hier wird dem Spieler seine zur Verfuegung stehenden Einheiten gezeigt,
	 * aus diesen kann er dann eine Auswaehlen
	 * Falls er diesen Einheitentypen nicht mehr hat wird das hier erkannt und angezeigt.
	 * @return Gibt einen Integer zurueck der fuer einen bestimmten Einheitentypen steht 
	 */
	public int einheitenWahl(){
		int eingabe = 0;
		System.out.println(aktiverS.getName() + " Welchen Einheitentypen moechtest du in den Kampf schicken?");
		System.out.println("Schwert: 1");
		System.out.println("Bogen: 2");
		System.out.println("Pferd: 3");
		boolean vorhanden = false;
		if (angreifer == true) {
			while (vorhanden == false) {
				eingabe = IO.readInt();
				if (eingabe == 1 && (kampf.getASchwerterSize() > 0)) {
					vorhanden = true;
					return eingabe;
				} else if (eingabe == 2 && (kampf.getABogenSize() > 0)) {
					vorhanden = true;
					return eingabe;
				} else if (eingabe == 3 && (kampf.getAPferdeSize() > 0)) {
					vorhanden = true;
					return eingabe;
				}
				System.out.println("Du hast keine dieser Einheiten mehr, bitte waehle eine andere!");
			}
		} else {
			while (vorhanden == false) {
				eingabe = IO.readInt();
				if (eingabe == 1 && (kampf.getVSchwerterSize() > 0)) {
					vorhanden = true;
					return eingabe;
				} else if (eingabe == 2 && (kampf.getVBogenSize() > 0)) {
					vorhanden = true;
					return eingabe;
				} else if (eingabe == 3 && (kampf.getVPferdeSize() > 0)) {
					vorhanden = true;
					return eingabe;
				}
				System.out.println("Du hast keine dieser Einheiten mehr, bitte waehle eine andere!");
			}
		}
		return 0;
	}
	/**
	 * Die Wuerfelergebnisse eines Wurfes werden angezeigt
	 * @param a Benoetigt die Anzahl der Augen des eigenen Wurfes 
	 * @param g Benoetigt die Anzahl der Augen des gegnerischen Wurfes 
	 */
	public void werfenAnzeige(int a, int g) { 
			System.out.println(aktiverS.getName() + ", deine Einheit hat eine " + a + " geworfen.");
			System.out.println("Dein Gegner hat eine " + g + " geworfen.");
	}
	/**
	 * Ausgabe bei verlorener Einheit
	 */
	public void einheitVerloren() {
		System.out.println(aktiverS.getName() + ", deine Einheit hat verloren!");
	}
	/**
	 * Ausgabe bei siegreichem Zweikampf zwischen zwei Einheiten
	 */
	public void einheitGewonnen() {
		System.out.println(aktiverS.getName() + ", deine Einheit hat gewonnen!");
	}
}
