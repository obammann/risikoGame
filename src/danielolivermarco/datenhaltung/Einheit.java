package danielolivermarco.datenhaltung;

import java.io.Serializable;

/**
 * Die Superklasse für alle Einheiten
 * @author Marco
 *
 */
public abstract class Einheit implements Serializable {

	/**
	 * @param Die Kosten zum Kaufen einer Einheit 
	 */
	protected int kosten;
	
	/**
	 * @param Die moeglichen Wuerfelaktionen die eine Einheit zur Verfuegung hat
	 */
	protected int moeglicheWuerfel;
	/**
	 * Konstruktor der Einheiten Klasse, benoetigt zwei Integer, einen mit den Kosten und einen mit den moeglichen Wuerfelaktionen
	 * @param k Kosten der Einheit 
	 * @param m Moegliche Wuerfelanzahl der Einheit 
	 */
	public Einheit(int k, int m){
		kosten = k;
		moeglicheWuerfel = m;
	}
	
	/** Gibt die Kosten zurueck die zum Kauf noetig sind
	 * @return Kosten des Einheitentyps
	 */
	public int getKosten() {
		return kosten;
	}
	
	/**
	 * Gibt die maximale Wuerfelanzahl an die der Einheitentyp werfen kann
	 * @return Maximale Wuerfelanzahl des Einheitentyps
	 */
	public int getMoeglicheWuerfel() {
		return moeglicheWuerfel;
	}
}
