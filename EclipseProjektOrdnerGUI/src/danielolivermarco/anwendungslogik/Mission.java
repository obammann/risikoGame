package danielolivermarco.anwendungslogik;

import java.io.Serializable;

import danielolivermarco.datenhaltung.*;
/**
 * Superklasse fuer alle möglichen Missionen
 * Zum Spielstart erhaelt jeder Spieler eine Mission
 * 
 */
public class Mission implements Serializable{
	
	Spieler besitzer;
	/**
	 * Erstellt eine Mission die einem Spieler gehoert 
	 * @param eingabeBesitzer Erwartet den Spieler der diese Mission erfuellen soll
	 */
	public Mission(Spieler eingabeBesitzer){
		
		besitzer = eingabeBesitzer;
	}
	
	/**
	 * Implementiert in den Unterklassen, ob eine Mission erfuellt wurde
	 * @return gibt true zurueck wenn die Mission erfuellt wurde
	 */
	public boolean erfuellt(){
		return true;	
	}
}

