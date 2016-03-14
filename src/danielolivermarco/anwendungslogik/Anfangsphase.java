package danielolivermarco.anwendungslogik;

import java.io.Serializable;
import java.util.Random;

import danielolivermarco.cui.IO;
import danielolivermarco.datenhaltung.*;

/**
 * Nach der Erstellung des Spiels kümmert sich das Anfangsphase Objekt 
 * um die Verteilung der Länder und der übrigen Einheiten der Spieler
 * 
 */
public class Anfangsphase implements Serializable {
	
	private Spiel dasSpiel;
	Random rand = new Random();
	/**
	 * Im Konstruktor wird das Spiel als Referenz fuer die Anfangsphase gespeichert.
	 * 
	 */
	public Anfangsphase(Spiel s) {
		dasSpiel = s;
	}
	/**
	 * Hier wird der Modus ausgewaehlt den der Spielersteller gewaehlt hat.
	 * Der Modus unterscheidet sich im verteilen der Laender und der restlichen Einheiten die der Spieler besitzt.
	 * Es kann entweder alles, nur die restlichen Einheiten oder gar nichts zufaellig verteilt werden	
	 * @param modusNr Bekommt die dementsprechende Nummer von der GUI, je nachdem welchen Modus der Spieler waehlt
	 */
	public void initGUI(int modusNr) {
		switch (modusNr) {
		case 0:		laenderAutoVerteilen();
					restlicheVerteilenGUI();
					dasSpiel.starteSpiel();
					break;
		case 1:		dasSpiel.starteSpiel();
					laenderAutoVerteilen();
					restlicheAutoVerteilen();
					dasSpiel.getDerRundenverwalter().initGui();
					break;
		case 2:		dasSpiel.starteSpiel();
					laenderAuswahlGUI();
					break;
		}
	}
	/**
	 * Hier wird der Modus ausgewaehlt den der Spielersteller gewaehlt hat.
	 * Der Modus unterscheidet sich im verteilen der Laender und der restlichen Einheiten die der Spieler besitzt.
	 * Es kann entweder alles, nur die restlichen Einheiten oder gar nichts zufaellig verteilt werden	
	 */
	public void initCUI() {
		for (int i = 0; i < dasSpiel.getSpielerListe().size(); i++) {
			dasSpiel.getSpielerListe().get(i).getPersoenlichesInterface().halloAnz();
		}
		dasSpiel.getSpielerListe().get(0).getPersoenlichesInterface().modusAuswahl();
		int modusNr = IO.readInt();
		switch (modusNr) {
		case 0:		laenderAutoVerteilen();
					restlicheVerteilen();
					break;
		case 1:		laenderAutoVerteilen();
					restlicheAutoVerteilen();
					break;
		case 2:		laenderAuswahl();
					restlicheVerteilen();
					break;
		}
	}
	
	/**
	 * Wenn alle Laender verteilt sind hat der Spieler noch restliche Einheiten in seinem Einheitenpool die er zum start bekommen hat 
	 * diese muss er nun alle verteilen,
	 * dies geschieht hier automatisch
	 */
	private void restlicheAutoVerteilen() {
		while (dasSpiel.getSpielerListe().get(dasSpiel.getSpielerListe().size()-1).getEinheitenPool().isEmpty() != true) {
			for (int y = 0; y < dasSpiel.getSpielerListe().size(); y++) {
				if (dasSpiel.getSpielerListe().get(y).getEinheitenPool().isEmpty() != true) {
					int index = rand.nextInt(dasSpiel.getSpielerListe().get(y).getBesitzLaender().size()); 
					int landId = dasSpiel.getSpielerListe().get(y).getBesitzLaender().get(index).getNummer();
					einheitEinfuegen(landId, y);
				}
			}
		}		
	}
	/**
	 * Zu Beginn eines jeden Spieles koennen die Spieler abwechselnd die Laender auswaehlen
	 * Dazu dient diese Methode die ueberprueft ob es noch unbesetzte Laender gibt und falls dem so ist
	 * die Spieler Reihum durchgeht und diesen ein noch nicht bestztes Land zuweist.
	 * Bei 4 Spielern bekommen die ersten beiden Spieler jeweils ein Land mehr. 
	 */
	private void laenderAutoVerteilen() {
		boolean verteilt = false;
		int landId = 0;
		if (dasSpiel.getSpielerListe().size() != 4) {
			for (int i = 0; i < 42; i += dasSpiel.getSpielerListe().size()) {
				for (int y = 0; y < dasSpiel.getSpielerListe().size(); y++)	 {
					while (!verteilt) {
						landId = rand.nextInt(42); 
						if (dasSpiel.getKarte().getLaender().get(landId).getOwner() == null) {
							verteilt = true;
						}
					}	
					landZuteilen(landId, y);	
					verteilt = false;
				}
			}
		} else {
			for (int i = 0; i < 10; i++) {
				for (int y = 0; y < dasSpiel.getSpielerListe().size(); y++)	 {
					while (!verteilt) {
						landId = rand.nextInt(42); 
						if (dasSpiel.getKarte().getLaender().get(landId).getOwner() == null) {
							verteilt = true;
						}
					}	
					landZuteilen(landId, y);	
					verteilt = false;
				} 
			}
			for (int k = 0; k < 2; k++) {
				while (!verteilt) {
					landId = rand.nextInt(42); 
					if (dasSpiel.getKarte().getLaender().get(landId).getOwner() == null) {
						verteilt = true;
					}
				}	
				landZuteilen(landId, k);	
				verteilt = false;
			}
		}

		
	}
	/**
	 * Zu Beginn eines jeden Spieles koennen die Spieler abwechselnd die Laender auswaehlen
	 * Dazu dient diese Methode die ueberprueft ob es noch unbesetzte Laender gibt und falls dem so ist
	 * die Spieler abwechselnd ueber die CUI eines Auswaelen laesst
	 * Bei 4 Spielern bekommen die ersten beiden Spieler jeweils ein Land mehr. 
	 */
	private void laenderAuswahl() {
		if(dasSpiel.getSpielerListe().size() == 2) {
			for (int i = 0; i < 42/2; i++) {
				for (int y = 0; y < dasSpiel.getSpielerListe().size(); y++) {
					boolean funkt = false;
					do {
						int landId = dasSpiel.getSpielerListe().get(y).getPersoenlichesInterface().landAuswahlStart() - 1;
						if (dasSpiel.getKarte().getLaender().get(landId).getOwner() == null) {
							landZuteilen(landId, y);
							funkt = true;
						} else {
							dasSpiel.getSpielerListe().get(y).getPersoenlichesInterface().landAuswahlFehler();
						}						
					} while (funkt == false);
				}
			}
		} else if (dasSpiel.getSpielerListe().size() == 3) {
			for (int i = 0; i < 42/3; i++) {
				for (int y = 0; y < dasSpiel.getSpielerListe().size(); y++) {
					boolean funkt = false;
					do {
						int landId = dasSpiel.getSpielerListe().get(y).getPersoenlichesInterface().landAuswahlStart() - 1;
						if (dasSpiel.getKarte().getLaender().get(landId).getOwner() == null) {
							landZuteilen(landId, y);
							funkt = true;
						} else {
							dasSpiel.getSpielerListe().get(y).getPersoenlichesInterface().landAuswahlFehler();
						}						
					} while (funkt == false);
				}
			}
		} else if (dasSpiel.getSpielerListe().size() == 4) {
			for (int i = 0; i < 10; i++) {
				for (int y = 0; y < dasSpiel.getSpielerListe().size(); y++) {
					boolean funkt = false;
					do {
						int landId = dasSpiel.getSpielerListe().get(y).getPersoenlichesInterface().landAuswahlStart() - 1;
						if (dasSpiel.getKarte().getLaender().get(landId).getOwner() == null) {
							landZuteilen(landId, y);
							funkt = true;
						} else {
							dasSpiel.getSpielerListe().get(y).getPersoenlichesInterface().landAuswahlFehler();
						}						
						
					} while (funkt == false);
				}
			}
			for (int y = 0; y < 2; y++) {
				boolean funkt = false;
				do {
					int landId = dasSpiel.getSpielerListe().get(y).getPersoenlichesInterface().landAuswahlStart() - 1;
					if (dasSpiel.getKarte().getLaender().get(landId).getOwner() == null) {
						landZuteilen(landId, y);
						funkt = true;
					} else {
						dasSpiel.getSpielerListe().get(y).getPersoenlichesInterface().landAuswahlFehler();
					}						
					
				} while (funkt == false);
			}
		}
	}	
	/**
	 * Wenn alle Laender verteilt sind hat der Spieler noch restliche Einheiten in seinem Einheitenpool die er zum start bekommen hat 
	 * diese muss er nun alle verteilen,
	 * dies geschieht hier durch auswahl des Spielers in der CUI 
	 */
	private void restlicheVerteilen() {
		while (dasSpiel.getSpielerListe().get(dasSpiel.getSpielerListe().size()-1).getEinheitenPool().isEmpty() != true) {
			for (int y = 0; y < dasSpiel.getSpielerListe().size(); y++) {
				if (dasSpiel.getSpielerListe().get(y).getEinheitenPool().isEmpty() != true) {
					int landId = dasSpiel.getSpielerListe().get(y).getPersoenlichesInterface().einheitenVeteilen();
					einheitEinfuegen(landId, y);
				}
			}
		}
	}
	/**
	 * Wenn alle Laender verteilt sind hat der Spieler noch restliche Einheiten in seinem Einheitenpool die er zum start bekommen hat 
	 * diese muss er nun alle verteilen,
	 * dies geschieht hier durch auswahl des Spielers in der GUI
	 */
	public void restlicheVerteilenGUI() {
		boolean weiter = false;
		int spielerNummer = 0;
		for (int y = 0; y < dasSpiel.getSpielerListe().size(); y++) {
			if (!dasSpiel.getSpielerListe().get(y).getEinheitenPool().isEmpty()) {
				weiter = true;
			}
		}
		if (weiter) {
			boolean nochNichtBoolean = true;
			int einheitenAnzahl = dasSpiel.getSpielerListe().get(0).getEinheitenPool().size();
			for (int i = 0; i < dasSpiel.getSpielerListe().size(); i++) {
				if ((dasSpiel.getSpielerListe().get(i).getEinheitenPool().size() > einheitenAnzahl) && nochNichtBoolean) {
						spielerNummer = i;
						nochNichtBoolean = false;
				}
			}
			dasSpiel.getSpielerListe().get(spielerNummer).setEinheitEinfuegenAnfang(true);
		} else {
			this.dasSpiel.getDerRundenverwalter().initGui();
		}
	}
/**
 * Im ersten Schritt prüft diese Methode ob noch Laender zu verteilen sind. 
 * Im Zweiten Schritt wird durch die Schleifen der Spieler bestimmt der aktuell dran ist ein Land auszuwaehlen
 * Wenn dieser Spieler ueber die GUI nun ein Land ausgewaehlt hat wird ihm dieses Zugeteilt und eine seiner Einheiten  darauf gesetzt.
 */
	public void laenderAuswahlGUI() {
		int spielerNummer = 0;
		boolean weiter = false;
		for (int y = 0; y < 42; y++) {
			if (dasSpiel.getKarte().getLaender().get(y).getOwner() == null) {
				weiter = true;
			}
		}
		if (weiter) {
			boolean nochNichtBoolean = true;
			int laenderAnzahl = dasSpiel.getSpielerListe().get(0).getBesitzLaender().size();
			for (int i = 0; i < dasSpiel.getSpielerListe().size(); i++) {
				if (dasSpiel.getSpielerListe().get(i).getBesitzLaender().size() < laenderAnzahl && nochNichtBoolean) {
						spielerNummer = i;
						nochNichtBoolean = false;
				}
			}
			dasSpiel.getSpielerListe().get(spielerNummer).setLandAuswaehlenAnfang(true);
		} else {
			this.restlicheVerteilenGUI();
		}
	}
	/**
	 * Teilt das ausgewaehlte Land dem Spieler zu und setzt eine Einheit aus des Spielers Einheitenpool in das Land. 
	 * @param lId Bekommt die LandID des Landes welches einem Spieler zugeteilt wird
	 * @param x	Stellt den Index dar den der Spieler dem das Land zugeteilt werden soll, in der ArrayList "SpielerListe" des Spiels besitzt.
	 */
	public void landZuteilen(int lId, int x) {
		Land land = dasSpiel.getKarte().getLaender().get(lId);
		Spieler spieler = dasSpiel.getSpielerListe().get(x);
		land.setOwner(dasSpiel.getSpielerListe().get(x));
		land.setSpielerArmee(new Armee(spieler));
		spieler.addBesitzLaender(land);
		Einheit insLand = spieler.getEinheitenPool().get(spieler.getEinheitenPool().size() -1);
		land.getSpielerArmee().armeeZuwachs(insLand);
		spieler.remEinheitenPool(insLand);	
	}
	/**
	 * Hier wird einem Land in der Anfangsphase eine Einheit eingefuegt
	 * @param landId Das Land in dem die Einheit eingefuegt werden soll
	 * @param y der Index des Spielers dessen Land es ist. 
	 */
	public void einheitEinfuegen(int landId, int y) {
		Einheit insLand = dasSpiel.getSpielerListe().get(y).getEinheitenPool().get(dasSpiel.getSpielerListe().get(y).getEinheitenPool().size() - 1);
		dasSpiel.getKarte().getLaender().get(landId - 1).getSpielerArmee().armeeZuwachs(insLand);
		dasSpiel.getSpielerListe().get(y).remEinheitenPool(insLand);
	}
}
