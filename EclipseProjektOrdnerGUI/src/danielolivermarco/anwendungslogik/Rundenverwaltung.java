package danielolivermarco.anwendungslogik;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import danielolivermarco.datenhaltung.*;
import danielolivermarco.gui.SpielSpeichernInterfaceGui;
import danielolivermarco.persistens.LadenSpeichernInterface;
import danielolivermarco.cui.*;

/**
 * 
 * Geht im Spiel die einzelnen Runden Schritt fuer Schritt durch.
 *
 */
public class Rundenverwaltung implements Serializable{
	                       
	private Spiel dasSpiel;
	
	private BuyAndSet goldUndKaufen; 
	private AngriffsModus attacke;			// armee aus bestimmten Land zusammenstellen und die nachbarn damit angreifen 
	private VerschiebenModus verschieben;	// armee aus bestimmten Land zusammenstellen und ins Nachbarland verschieben
	private PruefenModus pruefen;			// hier muss geprueft werden ob ein Spieler ausgeloescht wurde, und dieser muss dann aus dem Spiel entfernt werden
											// zusaetzlich ob nur ein Spieler noch uebrig ist und ob jemand seine Mission erfuellt hat.
	boolean jemandGewinnt = false;
	
	/**
	 * Der Konstruktor speichert das Spiel um darueber auf verschiedene Objekte zuzugreifen
	 * @param s	 Ubergibt das Spiel. 
	 */
	public Rundenverwaltung(Spiel s) {
		dasSpiel = s;
	}
	/**
	 * Initiert den Rundenverwalter in der CUI Version des Spiels und startet die erste Runde 
	 */
	public void initCUI() {
		goldUndKaufen = new BuyAndSet(dasSpiel); 		
		attacke = new AngriffsModus(dasSpiel);
		verschieben = new VerschiebenModus(dasSpiel);
		pruefen = new PruefenModus(dasSpiel);
		runde();
	}
	/**
	 * Initiert den Rundenverwalter in der GUI Version des Spiels und startet die erste Runde 
	 */
	public void initGui() {
		goldUndKaufen = new BuyAndSet(dasSpiel); 		 
		attacke = new AngriffsModus(dasSpiel);
		verschieben = new VerschiebenModus(dasSpiel);
		pruefen = new PruefenModus(dasSpiel);
		rundeGui();
	}
	/**
	 * Geht alle Spieler durch und laesst diese die einzelnen Phasen erledigen
	 * Sorgt zusaetzlich dafuer das die Kontinente immer den richtigen Besitzer haben
	 * Immer wenn ein Spieler seine Verschieben und damit letzte Phase beendet hat, wird geprueft ob es einen Sieger des Spiels gibt
	 * Wenn alle Spieler dran waren, ist wieder Spieler Eins dran
	 *  
	 */
	public void runde(){
		int i = 0;
		do {			
			if (i == dasSpiel.getSpielerListe().size()) {
				dasSpiel.spielSpeichern();
				i = 0;
			}
			for( Kontinent konti: dasSpiel.getKarte().getKontinente()){
				konti.setOwner();
			}
			goldUndKaufen.init(dasSpiel.getSpielerListe().get(i));
			attacke.init(dasSpiel.getSpielerListe().get(i));
			verschieben.init(dasSpiel.getSpielerListe().get(i));
			jemandGewinnt = pruefen.init(dasSpiel.getSpielerListe().get(i));
			if(jemandGewinnt == false) {
				i++;
			}
		} while (jemandGewinnt == false);
		Endbildschirm ende = new Endbildschirm(dasSpiel);
		ende.ende();
	}
	/**
	 * Wird immer aus der GUI angesteuert, wenn ein Spieler dran war. 
	 * Setzt je nachdem welcher Spieler grade dran ist dementsprechende booleans auf true bzw. false
	 * anhand der gesetzten booleans werden dem jeweiligen Spieler dementsprechende ausgaben auf der GUI produziert.
	 * Hier findet zusaetzlich die Pruefung statt, ob es einen Sieger gibt und falls dies zutrifft wird das Spiel beendet. 
	 */
	public void rundeGui(){
		boolean nichtMehrWeiter = false;
		if(dasSpiel.getSieger() != null) {
			jemandGewinnt = true;
		}
			if (jemandGewinnt) {
				Endbildschirm ende = new Endbildschirm(dasSpiel);
				ende.initGui();
			} else{
				for (int y = 0; y < dasSpiel.getSpielerListe().size(); y++) {
				Spieler spieler = dasSpiel.getSpielerListe().get(y);
				if(!spieler.isEinheitenKaufenUndSetzen() && !nichtMehrWeiter && !spieler.isEinheitKaufenUndSetzenBereitsGetan()) {
					spieler.setEinheitenKaufenUndSetzen(true);
					nichtMehrWeiter = true;
				} else if (!spieler.isAngriffDurchfuehren() && !nichtMehrWeiter && !spieler.isAngriffDurchfuehrenBereitsGetan()) {
					spieler.setAngriffDurchfuehren(true);
					nichtMehrWeiter = true;
				} else if (!spieler.isVerschiebenDurchfuehren() && !nichtMehrWeiter && !spieler.isVerschiebenDurchfuehrenBereitsGetan()) {
					spieler.setVerschiebenDurchfuehren(true);
					nichtMehrWeiter = true;
				}
			}
			for (int i = 0; i < dasSpiel.getSpielerListe().size(); i++) {
				Spieler spieler = dasSpiel.getSpielerListe().get(i);
				if (spieler.isEinheitenKaufenUndSetzen() && !spieler.isEinheitKaufenUndSetzenBereitsGetan()) {
					goldUndKaufen.initGui(spieler);
				} else if (spieler.isAngriffDurchfuehren() && !spieler.isAngriffDurchfuehrenBereitsGetan() && !spieler.isHatArmeeImAermel()) {
					attacke.initGui(spieler);
				} else if (spieler.isVerschiebenDurchfuehren() && !spieler.isVerschiebenDurchfuehrenBereitsGetan()) {
					verschieben.initGui(spieler);
				} 
			}
			}
	}
	/**
	 * Wird aus der GUI angesteuert und setzt alle Booleans der Spieler auf die Ausgangswerte zurueck,
	 * so wird dafuer gesorgt das die rundeGUI Methode wieder durch zugriff auf diese Booleans erkennt wer nun was tun darf.
	 * dies geschieht immer dann wenn der letzte Spieler seine Runde beendet hat.
	 */
	public void rundenWechsel() {
		SpielSpeichernInterfaceGui spielSpeichern = new SpielSpeichernInterfaceGui(dasSpiel);
		for (int k = 0; k < dasSpiel.getSpielerListe().size(); k++) {
			Spieler spieler1 = dasSpiel.getSpielerListe().get(k);
			spieler1.setEinheitenKaufenUndSetzen(false);
			spieler1.setEinheitKaufenUndSetzenBereitsGetan(false);
			spieler1.setAngriffDurchfuehren(false);
			spieler1.setAngriffDurchfuehrenBereitsGetan(false);
			spieler1.setVerschiebenDurchfuehren(false);
			spieler1.setVerschiebenDurchfuehrenBereitsGetan(false);
			for( Kontinent konti: dasSpiel.getKarte().getKontinente()){
				konti.setOwner();
			}
		}
	}
	/**
	 * 
	 * @return Gibt den BuyAndSetModus zurueck
	 */
	public BuyAndSet getGoldUndKaufen() {
		return goldUndKaufen;
	}
	/**
	 * 
	 * @param goldUndKaufen Benoetigt Eine BuyAndSetModus Objekt welches gespeichert wird 
	 */
	public void setGoldUndKaufen(BuyAndSet goldUndKaufen) {
		this.goldUndKaufen = goldUndKaufen;
	}
	/**
	 * 
	 * @return gibt den Angriffsmodi zurueck
	 */
	public AngriffsModus getAttacke() {
		return attacke;
	}
	/**
	 * 
	 * @param attacke Beotigt einen Angriffsmodus der gespeichert wird 
	 */
	public void setAttacke(AngriffsModus attacke) {
		this.attacke = attacke;
	}
	/**
	 * 
	 * @return gibt den VerschiebenModus zurueck
	 */
	public VerschiebenModus getVerschieben() {
		return verschieben;
	}
	/**
	 * 
	 * @param verschieben Benoetigt einen VerschiebenModus der gespeichert wird 
	 */
	public void setVerschieben(VerschiebenModus verschieben) {
		this.verschieben = verschieben;
	}
	/**
	 * 
	 * @return gibt den Pruefen Modus zurueck
	 */
	public PruefenModus getPruefen() {
		return pruefen;
	}
	/**
	 * 
	 * @param pruefen Benoetigt den Pruefen Modus der gespeichert wird
	 */
	public void setPruefen(PruefenModus pruefen) {
		this.pruefen = pruefen;
	}
}