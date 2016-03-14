

package danielolivermarco.anwendungslogik;

import java.io.Serializable;
import java.util.ArrayList;
import danielolivermarco.datenhaltung.*;
/**
 * Weist einem Spieler die Mission zu auf 15 seiner Laender mindestens 3 Einheiten oder mehr zu platzieren.
 *
 */
public class MissionDreiEinheiten extends Mission implements Serializable{

	/**
	 * Erstellt eine Mission die einem Spieler gehoert 
	 * @param eingabeBesitzer Erwartet den Spieler der diese Mission erfuellen soll
	 */
	public MissionDreiEinheiten(Spieler eingabeBesitzer) {
		super(eingabeBesitzer);
		
	}
	/**
	 * Geht alle besetzten Laender vom Spieler durch und ueberprueft ob in diesen 3 oder mehr Einheiten stehen 
	 * Wenn in 15 Laendern jeweils 3 oder mehr Einheiten stehen wird true zurueckgeliefert. 
	 * @return gibt true zurueck wenn die Mission erfuellt wurde
	 */
	public boolean erfuellt(){
		// Erst die Länder vom besitzenden Spieler holen
		ArrayList<Land> besitzerLaender = besitzer.getBesitzLaender();
		// wenn der Besitzer keine 15 Länder hat, ist die Mission nicht erfüllt
		if( besitzerLaender.size() < 15) return false;
		int laenderMit3 = 0;
		// danach jedes Land des Spielers (welcher die Missionskarte besitzt) angucken
		for( Land l : besitzerLaender){
		//wenn das angesehende Land mehr als 3 Einheiten hat, dann setze den zaehler für länder mit mehr als 3 einheiten einen hoch
			if( l.getSpielerArmee().getArmee().size() >= 3 ) laenderMit3++;
		}
		if(laenderMit3 >= 15){
			return true;
		}
		return false;
		
	}

}
