package danielolivermarco.anwendungslogik;

import java.io.Serializable;

import danielolivermarco.datenhaltung.*;
import danielolivermarco.gui.SpielerInterfaceGUI;

/**
 * Regelt das Kaufen und setzen neuer Einheiten am Beginn einer Runde
 *
 */
public class BuyAndSet implements Serializable {	
	
	private Spiel dasSpiel;
	private Spieler spieler;
	/**
	 * Im Konstruktor wird das Spiel als Referenz fuer die Anfangsphase gespeichert.
	 * 
	 * @param s Uebergibt das aktuelle Spiel
	 * 
	 */
	public BuyAndSet(Spiel s) {
		dasSpiel = s;
	}

	/**
	 * Diese Methode wird angesteuern wenn die CUI Version des Spieles laeuft.
	 * Dem Spieler wird zuerst angezeigt was an Besitz hat und wieviel er dafuer an Gold bekommt.
	 * Dann wird ihm die Moeglichkeit gegeben Einheiten fuer seinen Einheitenpool zu kaufen.
	 * Danach kann er diese Einheiten auf seinen Laendern setzen.	
	 * 
	 * @param s uebergibt den Spieler der grade am Zug ist
	 */
	public void init(Spieler s) {
		spieler = s;
		wasIstInBesitz();
		fuerPoolKaufen();
		einheitenSetzenAuswahl();
	}
	
	/**
	 * Diese Methode wird angesteuern wenn die GUI Version des Spieles laeuft.
	 * Es wird ihm angezeigt was er an Besitz hat und wieviel Gold er dafuer bekommt 
	 * Danach leitet ihn die GUI durch weitere Funktionen der Phase. 
	 * 
	 * @param s uebergibt den Spieler der Grade am Zug ist
	 */
	public void initGui(Spieler s) {
		spieler = s;
		wasIstInBesitzGui();
	}

	/**
	 * Berechnet wieviel Gold der Spieler fuer seine Laender und Kontinente bekommt und gibt diese Info in einem Fenster aus.
	 * Sollte das die erste Runde sein bekommt der Spieler ausserdem noch eine Begruessng angezeigt
	 */
	private void wasIstInBesitzGui() {
		int laenderGold = spieler.getBesitzLaender().size() * 100;
		int kontinentBonus = 0;
		for (int i = 0; i < dasSpiel.getKarte().getKontinente().length; i++) {
			if (dasSpiel.getKarte().getKontinente()[i].setOwner()) {
				if (dasSpiel.getKarte().getKontinente()[i].getOwner() == spieler) {
					if (dasSpiel.getKarte().getKontinente()[i].getLaender().length == 4) {
						kontinentBonus += 200;
					} else if (dasSpiel.getKarte().getKontinente()[i].getLaender().length == 5) {
						kontinentBonus += 300;
					} else if (dasSpiel.getKarte().getKontinente()[i].getLaender().length == 7) {
						kontinentBonus += 400;
					} else if (dasSpiel.getKarte().getKontinente()[i].getLaender().length == 8) {
						kontinentBonus += 500;
					} else if (dasSpiel.getKarte().getKontinente()[i].getLaender().length == 11) {
						kontinentBonus += 700;
					}
				}
			}
		}
		SpielerInterfaceGUI sInterface = spieler.getPersoenlichesInterfaceGUI();
		sInterface.goldBekommenFenster(laenderGold, kontinentBonus);
		spieler.addGeld(laenderGold);
		spieler.addGeld(kontinentBonus);
		
		//Spieler bekommt in seiner ersten Runde das Willkommen Fenster angezeigt
		if(sInterface.isFirstRound()) {
			sInterface.welcomeAnzeige();
			sInterface.setFirstRound(false);
		}
	}

	/**
	 * Rechnet nach was der Spieler der grade am Zug ist bekommt und zeigt es diesem ueber das SpielerInterface an.
	 */
	private void wasIstInBesitz() {
		int laenderGold = spieler.getBesitzLaender().size() * 100;
		int kontinentBonus = 0;
		for (int i = 0; i < dasSpiel.getKarte().getKontinente().length; i++) {
			if (dasSpiel.getKarte().getKontinente()[i].setOwner()) {
				if (dasSpiel.getKarte().getKontinente()[i].getOwner() == spieler) {
					if (dasSpiel.getKarte().getKontinente()[i].getLaender().length == 4) {
						kontinentBonus += 200;
					} else if (dasSpiel.getKarte().getKontinente()[i].getLaender().length == 5) {
						kontinentBonus += 300;
					} else if (dasSpiel.getKarte().getKontinente()[i].getLaender().length == 7) {
						kontinentBonus += 400;
					} else if (dasSpiel.getKarte().getKontinente()[i].getLaender().length == 8) {
						kontinentBonus += 500;
					} else if (dasSpiel.getKarte().getKontinente()[i].getLaender().length == 11) {
						kontinentBonus += 700;
					}
				}
			}
		}
		spieler.getPersoenlichesInterface().geldBekommenAnz(laenderGold, kontinentBonus);
		spieler.addGeld(laenderGold);
		spieler.addGeld(kontinentBonus);
	}
	

	/**
	 * Kauf fuer den Spieler eine Einheit und fuegt diese dem Einheitenpool zu
	 * Die pruefung ob der Spieler genug geld hat findet in der GUI statt.
	 * 
	 * @param spieler Benoetigt den Spieler um den es geht
	 * @param einheit Benoetigt die Einheit die gekauft wurde 
	 */
	public void fuerPoolKaufenGui(Spieler spieler, Einheit einheit) {
		spieler.addEinheitenPool(einheit);
		spieler.addGeld(einheit.getKosten() * -1);		
	}
	
	/**
	 * Setzt die ausgewaehlte Einheit in ein ausgewaehltes Land
	 * @param s Benoetigt den Spieler um den es geht 
	 * @param l Benoetigt das Land in welches die Einheit gesetzt werden soll
	 * @param e Benoetigt die Einheit die in das Land gesetzt werden soll
	 */
	public void einheitenSetzenAuswahlGui(Spieler s,Land l, Einheit e) {
		l.getSpielerArmee().armeeZuwachs(e);
		s.getEinheitenPool().remove(e);
	}
	/**
	 * regelt das Einheitenkaufen in der CUI Version des Spieles 
	 */
	public void fuerPoolKaufen() {
		boolean fuerPool = true;
		while (fuerPool) {
			int eingabe = spieler.getPersoenlichesInterface().einheitenKaufen();
			switch (eingabe) {
			case 1	: 	Schwert s = new Schwert();
						if (spieler.getGeld() >= s.getKosten()) {
							spieler.addEinheitenPool(s);
							spieler.addGeld(s.getKosten() * -1);
						} else {
							spieler.getPersoenlichesInterface().nichtGenugGeld();
						}
						break;
			case 2	: 	Bogen b = new Bogen();
						if (spieler.getGeld() >= b.getKosten()) {
							spieler.addEinheitenPool(b);
							spieler.addGeld(b.getKosten() * -1);	
						} else {
							spieler.getPersoenlichesInterface().nichtGenugGeld();
						}
						break;
			case 3 	:	Pferd p = new Pferd();
						if (spieler.getGeld() >= p.getKosten()) {
							spieler.addEinheitenPool(p);
							spieler.addGeld(p.getKosten() * -1);
						} else {
							spieler.getPersoenlichesInterface().nichtGenugGeld();
						}
						break;
			case 9 	: 	fuerPool = false;
						break;
			default : 	spieler.getPersoenlichesInterface().keineEingabe();
						break;
			}
		}
	}
	/**
	 * Regelt das Einheiten setzen in der CUI Version des Spieles
	 */
	private void einheitenSetzenAuswahl() {
		boolean setzen = true; 
		while (setzen) {
			int auswahl = spieler.getPersoenlichesInterface().einheitenSetzenAuswahl();	//Auswahl in welches INterface er moechte.
			switch (auswahl) {
			case 1 	: 	spieler.getPersoenlichesInterface().meineLaenderAnz();
						break;
			case 2 	: 	spieler.getPersoenlichesInterface().laenderAnz();
						break;
			case 3 	:	int landId = spieler.getPersoenlichesInterface().meineLaenderAnz();
						einheitSetzen(landId);
						break;
			case 9	:	setzen = false;
						break;
			default :	spieler.getPersoenlichesInterface().keineEingabe();
						break;
			}
		}
	}

	/**
	 * Fragt den Spieler welche Einheit er in ein Bestimmtes Land setzen moechte 
	 * Danach wird die Einheigt ausgewaehlt und dem Land hinzugefuegt.
	 * @param landId Benoetigt das Land in welches eine Einheit gesetzt werden soll
	 */
	private void einheitSetzen(int landId) {
		int welcheEinheit = spieler.getPersoenlichesInterface().einheitSetzen();
		switch (welcheEinheit) {
			case 9 	: 	return;
			case 1 	:	for(int i = 0;i < spieler.getEinheitenPool().size(); i++) {
							if (spieler.getEinheitenPool().get(i).getClass().toString().equals("class danielolivermarco.datenhaltung.Schwert")) {
								dasSpiel.getKarte().getLaender().get(landId - 1).getSpielerArmee().armeeZuwachs(spieler.getEinheitenPool().get(i));
								spieler.getEinheitenPool().remove(spieler.getEinheitenPool().get(i));
							} else if (i == spieler.getEinheitenPool().size() - 1) {
								spieler.getPersoenlichesInterface().keineBestimmteEinheitMehr();
							}
						}
						break;
			case 2	:	for(int i = 0;i < spieler.getEinheitenPool().size(); i++) {
							if (spieler.getEinheitenPool().get(i).getClass().toString().equals("class danielolivermarco.datenhaltung.Bogen")) {
								dasSpiel.getKarte().getLaender().get(landId - 1).getSpielerArmee().armeeZuwachs(spieler.getEinheitenPool().get(i));
								spieler.getEinheitenPool().remove(spieler.getEinheitenPool().get(i));
								return;
							} else if (i == spieler.getEinheitenPool().size() - 1) {
								spieler.getPersoenlichesInterface().keineBestimmteEinheitMehr();
							}
						}
						break;
			case 3	:	for(int i = 0;i < spieler.getEinheitenPool().size(); i++) {
							if (spieler.getEinheitenPool().get(i).getClass().toString().equals("class danielolivermarco.datenhaltung.Pferd")) {
								dasSpiel.getKarte().getLaender().get(landId - 1).getSpielerArmee().armeeZuwachs(spieler.getEinheitenPool().get(i));
								spieler.getEinheitenPool().remove(spieler.getEinheitenPool().get(i));
								return;
							} else if (i == spieler.getEinheitenPool().size() - 1) {
								spieler.getPersoenlichesInterface().keineBestimmteEinheitMehr();
							}
						}
						break;
			default	: 	spieler.getPersoenlichesInterface().keineEingabe();
						break;
		}
	}
}
