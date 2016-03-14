package danielolivermarco.anwendungslogik;

import java.io.Serializable;
import java.util.Random;

/**
 * Ansteuerung fuer die Rueckgabe
 * von zufaelligen Zahlen zwischen Eins und Sechs
 * 
 * @author Marco Geils <a href="mgeils@stud.hs-bremen.de">mgeils@stud.hs-bremen.de</a>
 *
 */

public class Wuerfel {		
	
	/**
	 * @param Die aktuell geworfene Zahl wird hier gespeichert
	 */
	private int wuerfelzahl;		
	
	/**
	 * Random Object fuer die Zufallswiedergabe	von Zahlen
	 */
	private Random random = new Random(); 
	
	/**
	 * generiert eine Wuerfelzahl zwischen Eins und Sechs
	 * 
	 * @return Eine Zufallszahl zwischen Eins und Sechs
	 */
	public int werfen() {
		wuerfelzahl = random.nextInt(6) + 1;
		return wuerfelzahl;
	}
	
}