package danielolivermarco.persistens;

import java.io.Serializable;

import danielolivermarco.cui.IO;
/**
 * 
 * Fragt den Spieler in der CUI ob er speichern oder laden moechte
 *
 */
public class LadenSpeichernInterface implements Serializable {
	/**
	 * Fragt den Spieler ob er ein gespeichertes Spiel laden moechte
	 * @return gibt true zurueck falls der Spieler ein Spiel laden moechte
	 */
	public boolean willLaden(){
		System.out.println("Möchten Sie das Spiel laden? 1 für ja: ");
		int eingabe = IO.readInt();
		if(eingabe == 1){
			return true;
		}
		return false;
	}
	/**
	 * Fragt den Spieler ob dieser das aktuelle Spiel speichern moechte
	 * @return gibt true zurueck wenn der Spieler das Spiel speichern moechte
	 */
	public boolean willSpeichern(){
		System.out.println("Möchten Sie das Spiel speichern ? speichern mit 2 ");
		int eingabe = IO.readInt();
		if(eingabe == 2){
			return true;
		}
		return false;
	}
	
}
