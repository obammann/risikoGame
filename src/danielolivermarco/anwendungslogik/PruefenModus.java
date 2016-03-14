package danielolivermarco.anwendungslogik;

import java.io.Serializable;

import danielolivermarco.datenhaltung.*;
/**
 * Klasse die ueberprueft ob das Spiel einen Sieger hat 
 *
 */
public class PruefenModus implements Serializable{
	
	Spiel dasSpiel;

	/**
	 * Der Konstruktor speichert das Spiel um darueber auf verschiedene Objekte zuzugreifen
	 * @param s	 Ubergibt das Spiel. 
	 */
	public PruefenModus(Spiel s) {
		dasSpiel = s;
	}
	
	
	/**
	 * Ueberprueft ob es noch mehr als einen Spieler gibt und ob der Spieler
	 * der aktuell an der Reihe ist sein Mission erfuellt hat
	 * Sollte das der Fall sein wird true zurueckgegeben.
	 * 
	 * @param spieler bekommt den Spieler der aktuell an der Reihe ist.
	 * @return Gibt true zurueck wenn der Spieler der einzige ueberlebende ist oder er seine Mission erfuellt hat.
	 */
	public boolean init(Spieler spieler) {
		if (spieler.getMission().erfuellt()) {
			dasSpiel.setSieger(spieler);
			return true;
		}
		for (int i = 0; i < dasSpiel.getSpielerListe().size() ; i++) {
			if (dasSpiel.getSpielerListe().get(i).amLeben() == false) {
				Spieler zuLoeschen = dasSpiel.getSpielerListe().get(i);
				dasSpiel.getSpielerListe().remove(zuLoeschen);
			}
		}
		if (dasSpiel.getSpielerListe().size() == 1) {
			dasSpiel.setSieger(dasSpiel.getSpielerListe().get(0));
			return true;
		}
		return false;
	}

}
