package danielolivermarco.datenhaltung;

import java.awt.Color;
import java.io.Serializable;

/**
 * 
 * Die Klasse der einzelnen Laender.
 * @author oliverbammann
 *
 */
public class Land implements Serializable{
	
	/**
	 * @param Der Name des Landes.
	 */
	private String name;
	
	/**
	 * @param Der Besitzer des Landes.
	 */
	private Spieler owner;
	
	/**
	 * @param Die Armee, die sich in dem Land befindet.
	 */
	private Armee spielerArmee;
	
	/**
	 * @param Die Nummer des Landes (jedes Land erhaelt eine Nummer).
	 */
	private int nummer;
	
	/**
	 * @param Ein Objektarray vom Typ Land fuer alle Nachbar Laender eines Landes.
	 */
	private Land[] nachbarn;
	
	private Color color;

	/**
	 * Konstruktor fuer die Klasse Land.
	 * @param n Hier wird der Name als String uebergeben.
	 * @param nr Hier wird die Nummer des Landes als Integer uebergeben.
	 */
	public Land(String n, int nr, int nachbarAnzahl, Color c) {
		name = n;
		setNummer(nr);
		nachbarn = new Land[nachbarAnzahl];
		color = c;
	}
	/**
	 * 
	 * @return gibt die Farbwerte des Landes zureuck
	 */
	public Color getColor() {
		return color;
	}
	/**
	 * 
	 * @return Gibt den Besitzer des Landes zurueck
	 */
	public Spieler getOwner() {
		return owner;
	}
	/**
	 * 
	 * @param s Benoetigt den Spieler, der der neue Besitzer des Landes wird
	 */
	public void setOwner(Spieler s) {
		owner = s;
	}
	/**
	 * 
	 * @return Gibt die Nummer des Landes zurueck
	 */
	public int getNummer() {
		return nummer;
	}
	/**
	 * 
	 * @param nummer Benoetigt die Nummer des Landes, die das Land erhaelt
	 */
	public void setNummer(int nummer) {
		this.nummer = nummer;
	}
	/**
	 * 
	 * @return Gibt den Namen des Landes zurueck
	 */
	public String getName() {
		return name;
	}
	/**
	 * 
	 * @return Gibt ein Array mit allen Nachbarlaendern des Landes zurueck
	 */
	public Land[] getNachbarn() {
		return nachbarn;
	}
	/**
	 * 
	 * @param nachbarn Benoetigt ein Array aus Laendern, welche als Nachbarlaender gesetzt werden
	 */
	public void setNachbarn(Land[] nachbarn) {
		this.nachbarn = nachbarn;
	}
	/**
	 * 
	 * @return Gibt die im Land stationierte Armee zurueck 
	 */
	public Armee getSpielerArmee() {
		return spielerArmee;
	}
	/**
	 * 
	 * @param a Benoetigt eine Armee die im Land stationiert wird
	 */
	public void setSpielerArmee(Armee a) {
		spielerArmee = a;
	}
}