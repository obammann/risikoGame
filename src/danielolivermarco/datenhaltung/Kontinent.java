package danielolivermarco.datenhaltung;

import java.io.Serializable;

/**
 * Die Klasse der Kontinente,enthalten bestimmte Laender.
 * @author oliverbammann
 *
 */
public class Kontinent implements Serializable{
	private String name;
	private Spieler owner;
	private Land[] laender;
	
	/**
	 * Im Konstruktor des Kontinents wird der Name und die Anzahl der Laender, die den Kontinent formen, gesetzt
	 * Durch die Anzahl der Laender wird die groesse des Arrays der Laender bestimmt... 
	 * @param n
	 * @param laenderAnzahl
	 */
	public Kontinent(String n, int laenderAnzahl) {
		this.setName(n);
		this.laender = new Land[laenderAnzahl];
	}
	/**
	 * Ueberprueft ob der Kontinent sich komplett in Besitz eines Spielers befindet
	 * @return gibt true zurueck wenn der Kontinent einem Spieler gehoert
	 */
	public boolean setOwner() {
		this.owner = laender[0].getOwner();
		for (Land l:laender) {
			if (l.getOwner()!=owner) {
				this.owner = null;
				return false;
			}
		}
		return true;
		//Variable owner setzen, wenn ein Spieler alle Laender eines Kontinents besitzt
	}
	/**
	 * 
	 * @return gibt den aktuellen Besitzer zurueck
	 */
	public Spieler getOwner(){
		return owner;
	}
	/**
	 * 
	 * @return gibt ein Array mit allen Laendern zurueck die in dem Kontinent enthalten sind
	 */
	public Land[] getLaender() {
		return laender;
	}
	/**
	 * 
	 * @param laender Benoetigt ein Array mit Laendern die in dem Kontinen enthalten sein sollen
	 */
	public void setLaender(Land[] laender) {
		this.laender = laender;
	}
	/**
	 * 
	 * @return Gibt den Namen des Kontinents zurueck
	 */
	public String getName() {
		return name;
	}
	/**
	 * 
	 * @param name Benoetigt einen Namen wie der Kontinent heissen soll
	 */
	public void setName(String name) {
		this.name = name;
	}
}