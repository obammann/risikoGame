package danielolivermarco.anwendungslogik;

import java.io.Serializable;

import danielolivermarco.datenhaltung.*;
import danielolivermarco.persistens.*;

/**
 * Organisiert das Verschieben der Einheiten eines Spielers 
 *
 */
public class VerschiebenModus implements Serializable{
	
	Spieler besitzer;
	Spiel dasSpiel;
	Armee verschiebenArmee;
	Land ursprung;
	Land ziel;

	/**
	 * Der Konstruktor speichert das Spiel um darueber auf verschiedene Objekte zuzugreifen
	 * @param s Uebergibt das Spiel. 
	 */
	public VerschiebenModus(Spiel s) {
		dasSpiel = s;
	}
	/**
	 * Initiert die Verschieben Phase in der CUI Version 
	 * Gibt dem Spieler aus was er nun tun kann und laesst diesen eine Armee zusammenstellen
	 * nach der zusammenstellung kann der Spieler die Armee verschieben. 
	 * 
	 * @param s Benoetigt den Spieler der grade in der Verschieben Phase ist. 
	 */
	public void init(Spieler s) {
		besitzer = s;
		besitzer.getPersoenlichesInterface().verschiebenEinstieg();
		ArmeeZusammensteller a = new ArmeeZusammensteller(dasSpiel);
		boolean verschieben = true;
		while (verschieben) {
			ursprung = a.land(besitzer);
			verschiebenArmee = a.init(besitzer);
			if(verschiebenArmee.getArmee().size() > 0) {
				verschieben = verschieben();
			}
		}
	}
	/**
	 * Prueft ob der Spieler die zusammengestellte Armee in das Land welches er ausgewaehlt hat verschieben kann 
	 * falls dies zutrifft laesst die Methode die Armee ueber den ArmeeAufloeser in das Land einfliessen.
	 * @return gibt true zurueck wenn das verschieben geklappt hat
	 */
	private boolean verschieben() {
		boolean nachbarAuswahl = true;
		while (nachbarAuswahl) {
			int zielId = besitzer.getPersoenlichesInterface().nachbarAnz(ursprung);
			ziel = dasSpiel.getKarte().getLaender().get(zielId - 1);
			if (ziel.getOwner() == besitzer) {
				ArmeeAufloeser armeeWeg = new ArmeeAufloeser();
				armeeWeg.init(verschiebenArmee, ziel);
				boolean verschiebenZuEnde = true;
				while (verschiebenZuEnde) {
					int weiter = besitzer.getPersoenlichesInterface().verschiebenWeiter();
					switch (weiter) {
						case 1 :	return true;
						case 2 :	return false;
						default :	besitzer.getPersoenlichesInterface().keineEingabe();
									break;
					}
				}
				nachbarAuswahl = false;				
				return true;
			} else {
				besitzer.getPersoenlichesInterface().nichtHierhinVerschieben();
				nachbarAuswahl = true; 
			}
		}
		return true;
		
	}
	/**
	 * Initiert die Verschieben Phase in der GUI 
	 * Gibt dem Spieler eine dementsprechende Meldung aus. 
	 * 
	 * @param spieler Benoetigt den Spieler der grade in der Verschieben Phase ist.
	 */
	public void initGui(Spieler spieler) {
		besitzer = spieler;
		besitzer.getPersoenlichesInterfaceGUI().verschiebenPhaseEinleitung();
	}
}