package danielolivermarco.cui;

import java.io.Serializable;

import danielolivermarco.anwendungslogik.Spiel;
import danielolivermarco.datenhaltung.*;
import danielolivermarco.gui.PopupGUI;
/**
 * 
 * Erstellt die Endausgabe, wenn das Spiel vorbei ist.
 *
 */
public class Endbildschirm implements Serializable {
	
	Spiel dasSpiel;
	/**
	 * Der Konstruktor speichert das Spiel um darueber auf verschiedene Objekte zuzugreifen
	 * @param s	 Ubergibt das Spiel. 
	 */
	public Endbildschirm(Spiel s) {
		dasSpiel = s;

	}
	/**
	 * Erstellt fuer die CUI Version des Spiels eine Ausgabe welcher Spieler gewonnen hat, wenn das Spiel zu Ende ist 
	 */
	public void ende() {
		System.out.println(dasSpiel.getSieger().getName() + " hat gewonnen"); 
	}
	/**
	 * Erstellt fuer die GUI Version des Spiels eine Anzeige welcher Spieler gewonnen hat, wenn das Spiel zu Ende ist 
	 */
	public void initGui() {
		Spieler sieger = dasSpiel.getSieger();
		PopupGUI siegerAnzeigen = new PopupGUI(dasSpiel);
		String anzeige = sieger.getName() + ", hat das Spiel gewonnen! Du kannst nun keine Aktionen mehr durchfuehren ausser dir die Karte anzuschauen.";
		for (int i = 0; i < dasSpiel.getSpielerListe().size(); i++) {
			siegerAnzeigen.textAnzeigen(anzeige, dasSpiel.getSpielerListe().get(i));
		}
	}

}
