package danielolivermarco.anwendungslogik;

import java.io.Serializable;
import danielolivermarco.datenhaltung.*;

/**
 * Mission die einen Spieler beauftragt vier zufaellig ausgewaehlte Kontinente zu erobern.
 *
 */
public class MissionVierKontinente extends Mission implements Serializable{
	
	
	Kontinent[] zuErobern;
	/**
	 * Erstellt eine Mission, die den Spieler beauftragt vier bestimmte Kontinente zu erobern
	 * die Kontinente erhaelt er ueber die Methode vierZufaelligZiehen
	 * @param eingabeBesitzer erwartet den Besitzer der Mission
	 * @param alleKontinente erwartet ein Array in dem alle moeglichen Kontinente des Spiels abgespeichert sind 
	 */
	public MissionVierKontinente(Spieler eingabeBesitzer, Kontinent[] alleKontinente){
		super(eingabeBesitzer);
		zuErobern = vierZufaelligZiehen(alleKontinente);
		besitzer.setZuErobern(zuErobern);
	}
	
	/**
	 * Wird angesteuert um ueber die Math Random Klasse zufaellig 4 Kontinente auszuwaehlen und diese zurueckzugeben 
	 * 
	 * @param alleKontinente erwartet ein Array was alle Kontinente des Spiels beinhaltet
	 * @return	gibt ein Array mit vier Zufaellig ausgewaehlten Kontinenten zurueck
	 */
	protected Kontinent[] vierZufaelligZiehen(Kontinent[] alleKontinente){
		int erster;
		int zweiter;
		int dritter;
		int vierter;
		//ziehe ersten Kontinent
		erster = (int)( Math.random() * alleKontinente.length);
		//ziehe zweiten Kontinent (darf nicht wie der erste sein)
		zweiter = (int)( Math.random() * alleKontinente.length);
		while( erster == zweiter){
			zweiter = (int)( Math.random() * alleKontinente.length);
		}
		//ziehe dritten Kontinent (darf nicht wie der erste und zweite sein)
		dritter = (int)( Math.random() * alleKontinente.length);
		while( dritter == zweiter || dritter == erster){
			dritter = (int)( Math.random() * alleKontinente.length);
		}
		//ziehe vietren Kontinent (darf nicht wie der erste, zwei und dritte sein)
		vierter = (int)( Math.random() * alleKontinente.length);
		while( vierter == erster || vierter == zweiter || vierter == dritter){
			vierter = (int)( Math.random() * alleKontinente.length);
		}
		Kontinent[] ergebnis = new Kontinent[4];
		ergebnis[0] = alleKontinente[erster];
		ergebnis[1] = alleKontinente[zweiter];
		ergebnis[2] = alleKontinente[dritter];
		ergebnis[3] = alleKontinente[vierter];
		
		return ergebnis;
		
	}
	
	/**
	 * Ueberprueft jeden Kontinent den der Spieler als Eroberungsziel hat ob dieser Kontinent einen Besitzer hat 
	 * und falls ja ob der Besitzer auch der Besitzer der Mission ist. Wenn der Spieler dem die Mission gehoert
	 * alle erforderlichen Kontinente besitzt wird hier true zurueckgegeben. 
	 * 
	 * @return gibt true zurueck wenn die vier Kontinente dem Besitzer der Mission gehoeren 
	 */
	public boolean erfuellt(){
		
		boolean ergebnis = true;
		//Wenn der Besitzer des Kontinetes nicht gleich ist mit dem Beitzer der Missionskarte, dann raus mit false
		for( Kontinent k: zuErobern){
			if( k.setOwner()) {
				if (!k.getOwner().equals(besitzer)) {
					return false;
				}
			//wenn alle Kontininte den Besitzer der Missionskarte gehören, dann return ergebnis (true)
			} else if (k.setOwner() != true) {
				return false;
			}
		}
		return ergebnis;
		
	}
}









