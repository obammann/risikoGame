package danielolivermarco.datenhaltung;

import java.io.Serializable;
import java.util.ArrayList;

import danielolivermarco.anwendungslogik.*;
import danielolivermarco.cui.*;
import danielolivermarco.gui.*;
/**
 * Die Klasse Spieler speichert alle wichtigen Dinge die der Spieler besitzt 
 * bzw. die das Spiel zur Anzeige von Informationen benoetigt.
 * Diese Informationen sind ueber den Spieler auch wieder abrufbar. 
 * @author Marco
 *
 */
public class Spieler implements Serializable{

	/**
	 * @param So heisst der Spieler
	 */
	private String name;
	/**
	 * Die Spielinterne Nummer des Spielers
	 */
	private int spielerNr;
	private Spiel seinSpiel;
	private Mission mission;
	private Kontinent[] zuErobern;
	private Spieler toetungsziel;
	private int geld;
	private ArrayList<Einheit> einheitenPool = new ArrayList<Einheit>();
	private ArrayList<Kontinent> besitzKontinente = new ArrayList<Kontinent>();
	private ArrayList<Land> besitzLaender = new ArrayList<Land>();
	private SpielerInterface persoenlichesInterface;
	private SpielerInterfaceGUI persoenlichesInterfaceGUI;
	private KartenGui karte;
	
	private boolean landAuswaehlenAnfang = false;
	private boolean einheitEinfuegenAnfang = false;
	
	private boolean einheitenKaufenUndSetzen = false;
	private boolean einheitKaufenUndSetzenBereitsGetan = false;
	
	private boolean angriffDurchfuehren = false;
	private boolean angriffDurchfuehrenBereitsGetan = false;	
	
	private boolean verschiebenDurchfuehren = false;
	private boolean verschiebenDurchfuehrenBereitsGetan = false;
	
	private boolean hatArmeeImAermel = false;
	
	private boolean kaempftGrade = false;
	
	/**
	 * Bei der Erstellung des Spielers werden Sachen wie,
	 * sein Name, 
	 * seine Nummer,
	 * sein Spiel
	 * sein Interface und sein anfaengliches Geld gesetzt bzw. initialisiert
	 * @param n Der Name des Spielers wird uebergeben
	 * @param nr Die Nummer des Spielers wird uebergeben
	 * @param s Das Spiel des Spielers wird uebergeben
	 */
	public Spieler(String n, int nr, Spiel s){
		name = n;
		setSpielerNr(nr);
		setSeinSpiel(s);
		geld = 0;
		setPersoenlichesInterfaceGUI(new SpielerInterfaceGUI(this));  //neues GUI Spielerinterface fuer jeden Spieler
	}
	/**
	 * Erstellt fuer den Spieler in der GUI Version die Klickbare Karte und speicert diese.
	 */
	public void start() {
		setKarte(new KartenGui(seinSpiel.getKarte(), this)); //Karte laden
	}
	/**
	 * 
	 * @return Gibt den Namen des Spielers zurueck
	 */
	public String getName() {
		return name;
	}
	/**
	 * 
	 * @return Gibt den EinheitenPool (in Form einer ArrayList in der nur Einheiten zugelassen sind) des Spielers zurueck
	 */
	public ArrayList<Einheit> getEinheitenPool() {
		return this.einheitenPool;
	}
	/**
	 * 
	 * @param e Benoetigt die Einheit die dem Einheitenpool hinzugefuegt werden soll
	 */
	public void addEinheitenPool(Einheit e) {
		this.einheitenPool.add(e);
	}
	/**
	 * 
	 * @param e Benoetigt die Einheit die aus dem Einheitenpool entfernt werden soll
	 */
	public void remEinheitenPool(Einheit e) {
		if (this.einheitenPool.contains(e)) {
			this.einheitenPool.remove(e);
		} else {
			System.out.println("Ein fataler Fehler trat bei dem Entfernen aus dem Einheitenpool auf");
		}
	}
	/**
	 * 
	 * @return Gibt zurueck wieviel Geld der Spieler aktuell hat
	 */
	public int getGeld() {
		return geld;
	}
	/**
	 * 
	 * @param plus Benoetigt den Betrag der zum Geld des Spielers hinzuaddiert werden soll
	 */
	public void addGeld(int plus) {
		this.geld += plus;
	}
	/**
	 * 
	 * @param minus Benoetigt den Betrag der vom Geld des Spielers abgezogen werden soll
	 */
	public void remGeld(int minus) {
		this.geld -= minus;
	}
	/**
	 * 
	 * @return Gibt die Spielernummer zurueck
	 */
	public int getSpielerNr() {
		return spielerNr;
	}
	/**
	 * 
	 * @param spielerNr Benoetigt die Spielernummer die der Spieler besitzen soll
	 */
	public void setSpielerNr(int spielerNr) {
		this.spielerNr = spielerNr;
	}
	/**
	 * 
	 * @return gibt die Mission des Spielers zurueck
	 */
	public Mission getMission() {
		return mission;
	}
	/**
	 * 
	 * @param m Benoetigt die Mission die der Spieler haben soll
	 */
	public void setMission(Mission m) {
		this.mission = m;
	}
	/**
	 * 
	 * @return Gibt das Spiel des Spielers zurueck
	 */
	public Spiel getSeinSpiel() {
		return seinSpiel;
	}
	/**
	 * 
	 * @param seinSpiel Benoetigt das Spiel was der Spieler speichern soll
	 */
	public void setSeinSpiel(Spiel seinSpiel) {
		this.seinSpiel = seinSpiel;
	}
	/**
	 * 
	 * @return Gibt eine ArrayListe zurueck in der alle besetzten Kontinente des Spielers sind
	 */
	public ArrayList<Kontinent> getBesitzKontinente() {
		return besitzKontinente;
	}
	/**
	 * 
	 * @param besitzKontinente Benoetigt eine ArrayList mit den Kontinenten die der Spieler besitzt
	 */
	public void addBesitzKontinente(ArrayList<Kontinent> besitzKontinente) {
		this.besitzKontinente = besitzKontinente;
	}
	/**
	 * 
	 * @return gibt die besetzten Laender des Spielers zurueck
	 */
	public ArrayList<Land> getBesitzLaender() {
		return besitzLaender;
	}
	/**
	 * 
	 * @param l Benoetigt ein Land welches der Spieler besitzen soll 
	 */
	public void addBesitzLaender(Land l) {
		this.besitzLaender.add(l);
	}
	/**
	 * 
	 * @param rl Benoetigt das Land welches der Spieler nicht mehr besitzen soll
	 */
	public void remBesitzLaender(Land rl) {
		this.besitzLaender.remove(rl);
	}
	/**
	 * 
	 * @return Gibt das Interface der CUI Version zurueck
	 */
	public SpielerInterface getPersoenlichesInterface() {
		return persoenlichesInterface;
	}
	/**
	 * 
	 * @param pi Benoetigt das Interface der CUI Version welches der Spieler speichert 
	 */
	public void setPersoenlichesInterface(SpielerInterface pi) {
		this.persoenlichesInterface = pi;
	}
	/**
	 * 
	 * @return gibt true zurueck wenn der Spieler lebt 
	 */
	public boolean amLeben() {
		if (besitzLaender.size() == 0 ) {
			return false;
		} else {
			return true; 
		}
	}
	/**
	 * 
	 * @return gibt das Toetungsziel zurueck, wenn der Spieler die Entsprechende Mission hat
	 */
	public Spieler getToetungsziel() {
		return toetungsziel;
	}
	/**
	 * 
	 * @param toetungsziel Benoetigt einen Spieler den der Spieler ausloeschen soll, im Falle, das der Spieler die demnetsprechende Mission erhaelt.
	 */
	public void setToetungsziel(Spieler toetungsziel) {
		this.toetungsziel = toetungsziel;
	}
	/**
	 * 
	 * @return Gibt die zu Erobernde Kontinente zurueck, wenn der Spieler die Entsprechende Mission hat
	 */
	public Kontinent[] getZuErobern() {
		return zuErobern;
	}
	/**
	 * 
	 * @param zuErobern Benoetigt ein Array mit Kontinenten die der Spieler erobern soll, im Falle, das der Spieler die demnetsprechende Mission erhaelt.
	 */
	public void setZuErobern(Kontinent[] zuErobern) {
		this.zuErobern = zuErobern;
	}
	/**
	 * 
	 * @return Gibt das Persoenliche Interface in der GUI Version zurueck 
	 */
	public SpielerInterfaceGUI getPersoenlichesInterfaceGUI() {
		return persoenlichesInterfaceGUI;
	}
	/**
	 * 
	 * @param persoenlichesInterfaceGUI Benoetigt das Interface der GUI um dieses beim Spieler zu speichern
	 */
	public void setPersoenlichesInterfaceGUI(SpielerInterfaceGUI persoenlichesInterfaceGUI) {
		this.persoenlichesInterfaceGUI = persoenlichesInterfaceGUI;
	}
	/**
	 * 
	 * @return Gibt die GUI-Abbildung der Karte, die der Spieler gespeichert hat, zurueck
	 */
	public KartenGui getKarte() {
		return karte;
	}
	/**
	 * 
	 * @param karte Benoetigt die GUI-Abbildung der Spielkarte (Objekt KartenGui)
	 */
	public void setKarte(KartenGui karte) {
		this.karte = karte;
	}
	/**
	 * 
	 * @return Gibt true zurueck wenn der Spieler in der Anfangsphase dran ist damit ein Land auszuwaehlen 
	 */
	public boolean isLandAuswaehlenAnfang() {
		return landAuswaehlenAnfang;
	}
	/**
	 * 
	 * @param landAuswaehlenAnfang Benoetigt einen Boolean der angibt ob der Spieler in der Anfangsphase dran ist damit ein Land auszuwaehlen 
	 */
	public void setLandAuswaehlenAnfang(boolean landAuswaehlenAnfang) {
		this.landAuswaehlenAnfang = landAuswaehlenAnfang;
	}
	/**
	 * 
	 * @return Gibt true zurueck wenn der Spieler in der Anfangsphase dran ist damit eine Einheit in eins seiner Laender einzufuegen 
	 */
	public boolean isEinheitEinfuegenAnfang() {
		return einheitEinfuegenAnfang;
	}
	/**
	 * 
	 * @param einheitEinfuegenAnfang Setzt den entsprechenden Boolean wenn der Spieler an der Reihe ist, in der Anfangsphase seine Einheiten auf seine Laender aufzuteilen
	 */
	public void setEinheitEinfuegenAnfang(boolean einheitEinfuegenAnfang) {
		this.einheitEinfuegenAnfang = einheitEinfuegenAnfang;
	}
	/**
	 * 
	 * @return Gibt true zurueck wenn der Spieler in der aktuellen Runde dran und im BuyAndSet Modus ist.
	 */
	public boolean isEinheitenKaufenUndSetzen() {
		return einheitenKaufenUndSetzen;
	}
	/**
	 * 
	 * @param einheitenKaufenUndSetzen Setzt den entsprechenden Boolean wenn der Spieler an der Reihe ist, die dementsprechenden Funktionen auszufuehren die er im BuyAndSet Modus tun kann
	 */
	public void setEinheitenKaufenUndSetzen(boolean einheitenKaufenUndSetzen) {
		this.einheitenKaufenUndSetzen = einheitenKaufenUndSetzen;
	}
	/**
	 * 
	 * @return  Gibt true zurueck wenn der Spieler in der aktuellen Runde dran und im Angriffs Modus ist.
	 */
	public boolean isAngriffDurchfuehren() {
		return angriffDurchfuehren;
	}
	/**
	 * 
	 * @param angriffDurchfuehren Setzt den entsprechenden Boolean wenn der Spieler an der Reihe ist, einen oder mehrere Angriffe durchzufuehren
	 */
	public void setAngriffDurchfuehren(boolean angriffDurchfuehren) {
		this.angriffDurchfuehren = angriffDurchfuehren;
	}
	/**
	 * 
	 * @return  Gibt true zurueck wenn der Spieler in der aktuellen Runde dran und im Verschieben Modus ist.
	 */
	public boolean isVerschiebenDurchfuehren() {
		return verschiebenDurchfuehren;
	}
	/**
	 * 
	 * @param verschiebenDurchfuehren Setzt den entsprechenden Boolean wenn der Spieler an der Reihe ist, Armeen zu verschieben
	 */
	public void setVerschiebenDurchfuehren(boolean verschiebenDurchfuehren) {
		this.verschiebenDurchfuehren = verschiebenDurchfuehren;
	}
	/**
	 * 
	 * @return gibt true zurueck, wenn der Spieler in der aktuellen Runde bereits mit dem BuyAndSet Modus fertig ist 
	 */
	public boolean isEinheitKaufenUndSetzenBereitsGetan() {
		return einheitKaufenUndSetzenBereitsGetan;
	}
	/**
	 * 
	 * @param einheitKaufenUndSetzenBereitsGetan Setzt einen entsprechenden Boolean, wenn der Spieler in der aktuellen Runde die Phase bereits durchlaufen hat. 
	 */
	public void setEinheitKaufenUndSetzenBereitsGetan(
			boolean einheitKaufenUndSetzenBereitsGetan) {
		this.einheitKaufenUndSetzenBereitsGetan = einheitKaufenUndSetzenBereitsGetan;
	}
	/**
	 * 
	 * @return gibt true zurueck, wenn der Spieler in der aktuellen Runde bereits mit dem Angriffs Modus fertig ist 
	 */
	public boolean isAngriffDurchfuehrenBereitsGetan() {
		return angriffDurchfuehrenBereitsGetan;
	}
	/**
	 * 
	 * @param angriffDurchfuehrenBereitsGetan Setzt einen entsprechenden Boolean, wenn der Spieler in der aktuellen Runde die Phase bereits durchlaufen hat. 
	 */
	public void setAngriffDurchfuehrenBereitsGetan(
			boolean angriffDurchfuehrenBereitsGetan) {
		this.angriffDurchfuehrenBereitsGetan = angriffDurchfuehrenBereitsGetan;
	}
	/**
	 * 
	 * @return gibt true zurueck, wenn der Spieler in der aktuellen Runde bereits mit dem Verschieben Modus fertig ist 
	 */
	public boolean isVerschiebenDurchfuehrenBereitsGetan() {
		return verschiebenDurchfuehrenBereitsGetan;
	}
	/**
	 * 
	 * @param verschiebenDurchfuehrenBereitsGetan Setzt einen entsprechenden Boolean, wenn der Spieler in der aktuellen Runde die Phase bereits durchlaufen hat. 
	 */
	public void setVerschiebenDurchfuehrenBereitsGetan(
			boolean verschiebenDurchfuehrenBereitsGetan) {
		this.verschiebenDurchfuehrenBereitsGetan = verschiebenDurchfuehrenBereitsGetan;
	}
	/**
	 * 
	 * @return gibt true zurueck wenn der Spieler noch eine unaufgeloeste Armee aus einem Land zusammengestellt hat
	 */
	public boolean isHatArmeeImAermel() {
		return hatArmeeImAermel;
	}
	/**
	 * 
	 * @param hatArmeeImAermel setzt den Boolean entsprechend je nachdem ob der Spieler gerade eine Armee erstellt hat und ob diese noch nicht aufgeloest ist
	 */
	public void setHatArmeeImAermel(boolean hatArmeeImAermel) {
		this.hatArmeeImAermel = hatArmeeImAermel;
	}
	/**
	 * 
	 * @return gibt True zurueck, wenn der Spieler grade kaempft
	 */
	public boolean isKaempftGrade() {
		return kaempftGrade;
	}
	/**
	 * 
	 * @param kaempftGrade setzt den Boolean entsprechend ob der Spieler grade kaempft oder auch nicht. 
	 */
	public void setKaempftGrade(boolean kaempftGrade) {
		this.kaempftGrade = kaempftGrade;
	}
}