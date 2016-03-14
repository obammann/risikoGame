package danielolivermarco.anwendungslogik;

import java.io.Serializable;
import java.util.ArrayList;
import danielolivermarco.datenhaltung.*;
/**
 * Mission die einen Spieler beauftragt einen anderen Spieler zu toeten
 * 
 *
 */
public class MissionSpielerToeten extends Mission implements Serializable{

	private Spieler toetungsziel; 
	
	/**
	 * Erstellt eine Mission die einem Spieler gehoert, die den Spieler beauftragt einen anderen Spieler zu toeten 
	 * 
	 * @param eingabeBesitzer Erwartet den Spieler dem die Mission gehoert 
	 * @param spielerListe Erwartet eine ArrayList mit allen Spielern des aktuellen Spiels
	 */
	public MissionSpielerToeten(Spieler eingabeBesitzer, ArrayList<Spieler> spielerListe) {
		super(eingabeBesitzer);
		toetungsziel = waehleZufaelligenSpieler( spielerListe);
		besitzer.setToetungsziel(toetungsziel);
	}
	
	/**
	 * Waehlt einen zufaelligen Spieler aus der SpielerListe aus und gibt diesen zurueck,
	 * wenn dieser nicht auch der Besitzer der Mission ist.
	 * 
	 * @param spielerListe erwartet die Liste mit allen Spielern des aktuellen Spiels
	 * @return Gibt einen zufaellig ausgewaehlten Spieler zurueck, jedoch nicht den Besitzer der Mission
	 */
	protected Spieler waehleZufaelligenSpieler( ArrayList<Spieler> spielerListe){
		// Besitzer der Mission in der Spielerliste finden und entfernen
		// da er sich nicht selbst als zu tötenden Speiler wählen darf
		int posVomSpielerDerMission = spielerListe.indexOf(besitzer);
		Spieler spielerDerMission = spielerListe.get(posVomSpielerDerMission);
		
		// zufälligen ANDEREN Spieler aus den übrigen wählen
		Spieler erg = null;
		while (erg == null) {
		    int anzahlSp = spielerListe.size();
		    int zufallsPos = (int)(Math.random() * anzahlSp);
		    if (spielerListe.get(zufallsPos) != spielerDerMission){
		    	erg =  spielerListe.get(zufallsPos);
		    }
		}
	    
	    // und zufälligen anderen Spieler zurückgeben
	    return erg;
		
	}
	/**
	 * Ueberprueft ob das Ziel der Toetung noch am Leben ist.
	 * @return gibt true zurueck wenn das Toetungsziel nicht mehr am Leben ist
	 */
	public boolean erfuellt (){
		return !toetungsziel.amLeben();
	}

}
