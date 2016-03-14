package danielolivermarco.anwendungslogik;

import java.io.Serializable;

import danielolivermarco.cui.*;
import danielolivermarco.datenhaltung.*;

/**
 * Regelt alle Aktionen die im AngriffsModus vor sich gehen, 
 * speichert den Spieler der grade einen Angriff durchfuehrt sowie die Armee die dieser auswaehlt um anzugreifen.
 * 
 *
 */
public class AngriffsModus implements Serializable {
	
	private Spieler besitzer;
	private Spiel dasSpiel;
	private Armee angriffsArmee;
	private Land ursprung;
	private Land ziel;
	/**
	 * Der Konstruktor speichert das Spiel um darueber auf verschiedene Objekte zuzugreifen
	 * @param s	 Ubergibt das Spiel. 
	 */
	public AngriffsModus(Spiel s) {
		dasSpiel = s;
	}
	/**
	 * Initiert und durchlaeuft die Kampfphase, 
	 * Erstellt einen ArmeeZusammensteller und gibt dem Spieler aus was er nun tun kann.
	 * Beendet die Kampfphase wenn er aus der Methode angriff false zurueckbekommt.  
	 * @param s Bekommt den Spieler der aktuell in der Angriffsphase ist. 
	 */
	public void init(Spieler s) {
		besitzer = s;
		besitzer.getPersoenlichesInterface().angriffEinstieg();
		ArmeeZusammensteller a = new ArmeeZusammensteller(dasSpiel);
		boolean weiterAngreifen = true;
		while (weiterAngreifen) {
			ursprung = a.land(s);
			angriffsArmee = a.init(s);
			if (angriffsArmee != null) {
				weiterAngreifen = angriff();
			}
		}
	}
	/**
	 * Die Methode wird in der CUI Version des Spiels angesprochen. 
	 * Hier wird der Spieler gefragt aus welchem Land er eine Armee zusammenstellen moechte
	 * Wenn er dieses ausgewaehlt hat wird er im ArmeeZusammensteller durch das Zusammenstellen der Armee gefuehrt 
	 * Danach waehlt der Spieler das Land aus welches er angreifen moechte,
	 * danach werden die gesammelten Daten an ein neu erstelltes Kampf objekt uebergeben welches den Kampf organisiert und durchfuehrt
	 * Nach beendetem Kampf wird, falls der angreifende Spieler gewonnen hat an die Methode "nachGewonnenemKampf" geleitet (aus dem Kampf Objekt)
	 * Der Spieler wird in jedem fall gefragt ob er einen weiteren Angriff durchfuehren moechte.
	 * 
	 * @return gibt true zurueck wenn der Spieler weiter angreifen moechte false wenn der Spieler die Kamfphase beenden moechte.
	 */
	private boolean angriff() {
		boolean nachbarAuswahl = true;
		while (nachbarAuswahl) {
			ArmeeAufloeser aufloeser = new ArmeeAufloeser(); 
			int zielId = besitzer.getPersoenlichesInterface().nachbarAnz(ursprung);
			ziel = dasSpiel.getKarte().getLaender().get(zielId - 1);
			if (ziel.getOwner() == besitzer) {
				besitzer.getPersoenlichesInterface().angriffAbgebrochen();
				aufloeser.init(angriffsArmee, ziel); 
				while (true) {
					int eingabe = besitzer.getPersoenlichesInterface().angriffWeiter(); 
					switch (eingabe) {
					case 1	:	return true;
					case 2	:	return false;
					default	: 	besitzer.getPersoenlichesInterface().keineEingabe();
								break;
					}
				}
			} else if (ziel.getOwner() != besitzer) {
				Kampf k = new Kampf(angriffsArmee, ziel.getSpielerArmee());
				Spieler sieger = k.init();
				if (sieger == besitzer) {
					nachGewonnenemKampf();
					besitzer.getPersoenlichesInterface().angriffGewonnen(); 
					while (true) {
						int eingabe = besitzer.getPersoenlichesInterface().angriffWeiter(); 
						switch (eingabe) {
						case 1	:	return true;
						case 2	:	return false;
						default	: 	besitzer.getPersoenlichesInterface().keineEingabe();
									break;
						}
					}
				} else if (sieger == ziel.getOwner()) {
					besitzer.getPersoenlichesInterface().angriffVerloren(); 
					while (true) {
						int eingabe = besitzer.getPersoenlichesInterface().angriffWeiter(); 
						switch (eingabe) {
						case 1	:	return true;
						case 2	:	return false;
						default	: 	besitzer.getPersoenlichesInterface().keineEingabe();
									break;
						}
					}
				} else {
					System.out.println("Ein fataler Fehler trat im Angriffsmodus auf!");
				}
			}
		}
		return true;	
	}
	/**
	 * Wenn der Angreifer gewonnen hat wird diese Methode aufgerufen 
	 * Hier wird das Land in den Besitz des Angreifers verschoben sowie seine Angriffsarmee 
	 * in diesem Land aufgeloest 
	 */
	private void nachGewonnenemKampf() {
		ziel.getOwner().getBesitzLaender().remove(ziel);
		ziel.setOwner(besitzer);
		ziel.setSpielerArmee(angriffsArmee);
		besitzer.addBesitzLaender(ziel);
	}
	/**
	 * Hier wird die Angriffsphase initiert wenn die GUI Version des Spiels gestartet wurde. 
	 * Dem Spieler wird ueber sein Persoenliches Interface eine dementsprechende Anzeige generiert
	 * @param spieler
	 */
	public void initGui(Spieler spieler) {
		besitzer = spieler;
		besitzer.getPersoenlichesInterfaceGUI().angriffsPhaseEinleitung();
	}
	/**
	 * 
	 * @return gibt die Armee zurueck mit der der Spieler angreift
	 */
	public Armee getAngriffsArmee() {
		return angriffsArmee;
	}
	/**
	 * 
	 * @param angriffsArmee Setzt die Armee mit der Angegriffen wird.
	 */
	public void setAngriffsArmee(Armee angriffsArmee) {
		this.angriffsArmee = angriffsArmee;
	}
	/**
	 * 
	 * @return gibt das Ursprungsland zurueck aus dem der Spieler seine Angriffsarmee erstellt hat
	 */
	public Land getUrsprung() {
		return ursprung;
	}
	/**
	 * 
	 * @param ursprung Setzt das UrsprungsLand welches in der Angriffsphase angegriffen wird
	 */
	public void setUrsprung(Land ursprung) {
		this.ursprung = ursprung;
	}
}
