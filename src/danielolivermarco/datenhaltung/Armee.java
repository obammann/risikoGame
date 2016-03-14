package danielolivermarco.datenhaltung;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Hier wird eine Armee gespeichert die verschiedene
 * Einheiten beinhaltet 
 * 
 * @author Marco
 *
 */
public class Armee implements Serializable  {
	/**
	 * @param Hier werden alle Einheiten einer Armee, 
	 * die in einem Land stationiert, in Bewegung zwecks einer Truppenverlegung und eine Angriffsarmee sein kann.
	 *  
	 */
	private ArrayList<Einheit> armeeInhalt = new ArrayList<Einheit>(); 
	/**
	 * @param Hier wird der jeweilige Besitzer der Armee gespeichert 
	 */
	private Spieler besitzer;
	
	/**
	 * Der Konstruktor fuer die Armee dieser erwartet einen Spieler
	 * 
	 * @param b Hier muss der Spieler uebergeben werden dem diese Armee gehoert
	 */
	public Armee(Spieler b){
		besitzer = b;
	}

	

	/**
	 * Hier kann eine Liste geliefert werden die die gesamte Armee angibt, 
	 * diese kann dann von anderen Objekten ausgelesen werden, 
	 * die dann wiederum die Einzelnen Inhalte dem Benutzer anzeigt.
	 * 
	 * @return Die Liste in der alle Einheiten der Armee gespeichert sind
	 */
	public ArrayList<Einheit> getArmee() {
		return armeeInhalt;
	}
	
	/**
	 * 
	 * Fuegt der Armee eine Einheit hinzu.
	 * Eventuell koennte man hier die Kosten abrechnen oder aehnliches (waere glaube ich besser anders wo)
	 * 
	 * @param eineEinheit Erwartet die Einheit die Hinzugefuegt werden soll
	 */
	public void armeeZuwachs(Einheit eineEinheit) {
		armeeInhalt.add(eineEinheit);
	}
	
	/**
	 * 
	 * @return Gibt den Besitzer der aktuellen Armee zurück
	 */
	public Spieler getBesitzer() {
		return besitzer;
	}
	
	
	/**
	 * Hier soll der zu Löschende Einheitentyp (nachdem eine Einheit einen Kampf verloren hat)
	 * geliefert werden. 
	 * Es wird geprueft ob die Einheit existiert und danach aus der Armee entfernt 
	 * 
	 * So wird dann zum Beispiel aus einer Armee mit 2 Schwert und 1 Bogen nur 1 Schwert und 1 Bogen...
	 * 
	 * @param eineEinheit
	 */
	public boolean remEinheit(Einheit eineEinheit) {
		if(armeeInhalt.contains(eineEinheit) == true) {
			armeeInhalt.remove(eineEinheit);
			return true;
		} else {
			return false;
		}
	}
	

}
