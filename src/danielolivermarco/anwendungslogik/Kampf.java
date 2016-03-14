package danielolivermarco.anwendungslogik;

import java.io.Serializable;
import java.util.ArrayList;

import danielolivermarco.cui.KampfInterface;
import danielolivermarco.datenhaltung.*;
import danielolivermarco.gui.KampfInterfaceGui;


/**
 * Organisiert die Kaempfe in Risiko Mittelerde.
 */

public class Kampf implements Serializable {

	private Armee angreifer;				
	public Spieler angreiferS;					
	private Armee verteidiger;				
	public Spieler verteidigerS;			
	private Einheit[] aSchwert;					
	private Einheit[] aBogen;
	private Einheit[] aPferd;
	private Einheit[] vSchwert;					// einzelnen Referenzen auf die Truppen des Verteidigers
	private Einheit[] vBogen;
	private Einheit[] vPferd;
	private KampfInterface kampfUIA;
	private KampfInterface kampfUIV;
	private KampfInterfaceGui kampfUIAGui;
	private KampfInterfaceGui kampfUIVGui;
	private boolean angreiferHatGewaehlt = false;
	private boolean verteidigerHatGewaehlt = false;
	private Land zielland;
	
	private int auswahlA;
	private int auswahlV;
	
	private Einheit angreifendeE;			// für den Zweikampf der Einheiten
	private Einheit verteidigendeE;			// für den Zweikampf der Einheiten
	
	private Einheit loeschen;
	
	/**
	 * Konstruiert einen neuen Kampf stellt alle Benoetigten Instanzvariablen ein
	 * @param angriff	Erwartet die Armee die das Land angreift
	 * @param verteidigung	Erwartet die Armee die das Land verteidigen wird 
	 */
	public Kampf(Armee angriff, Armee verteidigung){
		angreifer = angriff;
		angreiferS = angriff.getBesitzer();
		verteidiger = verteidigung;
		verteidigerS = verteidigung.getBesitzer();
	}
	/**
	 * Konstruiert einen neuen Kampf stellt alle Benoetigten Instanzvariablen ein
	 * @param angriff	Erwartet die Armee die das Land angreift
	 * @param verteidigung	Erwartet die Armee die das Land verteidigen wird 
	 * @param ziel	Erwartet das Land um was gekaempft wird
	 */
	public Kampf(Armee angriff, Armee verteidigung, Land ziel){
		angreifer = angriff;
		angreiferS = angriff.getBesitzer();
		verteidiger = verteidigung;
		verteidigerS = verteidigung.getBesitzer();
		this.zielland = ziel;
	}
	/**
	 * 
	 * @return gibt die anzahl der Schwertkaempfer beim Angreifer an. 
	 */
	public int getASchwerterSize() {
		return aSchwert.length;
	}
	/**
	 * @return gibt die anzahl der Bogenschuetzen beim Angreifer an.
	 */
	public int getABogenSize() {
		return aBogen.length;
	}
	/**
	 * 
	 * @return gibt die anzahl der Reiter beim Angreifer an.
	 */
	public int getAPferdeSize() {
		return aPferd.length;
	}
	/**
	 * 
	 * @return gibt die anzahl der Schwertkaempfer beim Verteidiger an.
	 */
	public int getVSchwerterSize() {
		return vSchwert.length;
	}
	/**
	 * 
	 * @return gibt die anzahl der Bogenschuetzen beim Verteidiger an.
	 */
	public int getVBogenSize() {
		return vBogen.length;
	}
	/**
	 * 
	 * @return gibt die anzahl der Reiter beim Verteidiger an.
	 */
	public int getVPferdeSize() {
		return vPferd.length;
	}
	/**
	 * 
	 * @return gibt an ob der Agreifer bereits ausgewaehlt hat welche Einheit er in den Kampf schicken moechte
	 */
	public boolean isAngreiferHatGewaehlt() {
		return angreiferHatGewaehlt;
	}

	/**
	 * Setzt ob der Angreifer bereits eine Einheit gewaehlt hat 
	 * @param angreiferHatGewaehlt erwartet einen Boolean ob der Angreifer bereits eine Einheit gewaehlt hat
	 */
	public void setAngreiferHatGewaehlt(boolean angreiferHatGewaehlt) {
		this.angreiferHatGewaehlt = angreiferHatGewaehlt;
	}
	/**
	 * 
	 * @return gibt an ob der Verteidiger bereits ausgewaehlt hat welche Einheit er in den Kampf schicken moechte
	 */
	public boolean isVerteidigerHatGewaehlt() {
		return verteidigerHatGewaehlt;
	}

	/**
	 *
	 * Setzt ob der Angreifer bereits eine Einheit gewaehlt hat 
	 * @param verteidigerHatGewaehlt erwartet einen Boolean ob der Angreifer bereits eine Einheit gewaehlt hat
	 */
	public void setVerteidigerHatGewaehlt(boolean verteidigerHatGewaehlt) {
		this.verteidigerHatGewaehlt = verteidigerHatGewaehlt;
	}
	/**
	 * Gibt die Zahl, die fuer einen Einheitentypen steht, zurueck, die der Angreifer gewaehlt hat 
	 * @return Gibt die Zahl die fuer einen Einheitentypen steht zurueck
	 */
	public int getAuswahlA() {
		return auswahlA;
	}

	/**
	 * Setzt die Zahl, die fuer einen Einheitentypen steht, zurueck, die der Angreifer gewaehlt hat 
	 * @param auswahlA Benoetigt einen Integer der fuer einen Bestimmten Einheitentyp steht
	 */
	public void setAuswahlA(int auswahlA) {
		this.auswahlA = auswahlA;
	}
	/**
	 *  Gibt die Zahl, die fuer einen Einheitentypen steht, zurueck, die der Angreifer gewaehlt hat 
	 * @return Gibt die Zahl die fuer einen Einheitentypen steht zurueck
	 */
	public int getAuswahlV() {
		return auswahlV;
	}

	/**
	 * Setzt die Zahl, die fuer einen Einheitentypen steht, zurueck, die der Verteidiger gewaehlt hat.
	 * @param auswahlV Benoetigt einen Integer der fuer einen Bestimmten Einheitentyp steht
	 */
	public void setAuswahlV(int auswahlV) {
		this.auswahlV = auswahlV;
	}
	/**
	 * Startet einen Kampf in der GUI Version des Spiels 
	 * erstellt dazu Interfaces fuer die jeweiligen Spieler und stoesst die ersten Eingaben von den Spielern an
	 */
	public void initGui() {
		armeenZuweisen(angreifer, verteidiger);
		kampfUIAGui = new KampfInterfaceGui(angreiferS, verteidigerS, this);
		kampfUIVGui = new KampfInterfaceGui(verteidigerS, angreiferS, this);
		kampfUIAGui.init();
		kampfUIVGui.init();
	}
	/**
	 * Oeffnet in der GUI Version des Spieles Fenster fuer die betreffende Spieler 
	 * und organisiert deren Auswahl der Einheiten sowie den dann stattfindenden Kampf
	 * Gibt zudem auf der GUI aus ob eine Einheit verloren hat oder gewonnen hat sowie 
	 * den Sieger am ende des Kampfes.
	 */
	public void kampfGui() {
		if (angreiferHatGewaehlt && verteidigerHatGewaehlt) {
			if (auswahlA == 1) {
				angreifendeE = aSchwert[aSchwert.length - 1];
			} else if (auswahlA == 2) {
				angreifendeE = aBogen[aBogen.length - 1];
			} else if (auswahlA == 3) {
				angreifendeE = aPferd[aPferd.length - 1];
			}
			if (auswahlV == 1) {
				verteidigendeE = vSchwert[vSchwert.length - 1];
			} else if (auswahlV == 2) {
				verteidigendeE = vBogen[vBogen.length - 1];
			} else if (auswahlV == 3) {
				verteidigendeE = vPferd[vPferd.length - 1];
			}
			loeschen = kaempfenGui(angreifendeE, verteidigendeE);
			angreiferHatGewaehlt = false;
			verteidigerHatGewaehlt = false;
			if(angreifer.getArmee().contains(loeschen)) {
				kampfUIAGui.einheitVerloren();
				kampfUIVGui.einheitGewonnen();
				angreifer.remEinheit(loeschen);
			} else if (verteidiger.getArmee().contains(loeschen)) {
				kampfUIAGui.einheitGewonnen();
				kampfUIVGui.einheitVerloren();
				verteidiger.remEinheit(loeschen);
			}
			if (((angreifer.getArmee().isEmpty() == true) || (verteidiger.getArmee().isEmpty()) == true)) {
				if (angreifer.getArmee().isEmpty()) {
					angreiferS.setKaempftGrade(false);
					angreiferS.getSeinSpiel().getDerRundenverwalter().getAttacke().setAngriffsArmee(null);
					angreiferS.setHatArmeeImAermel(false);
					kampfUIAGui.kampfVerloren();
					kampfUIVGui.kampfGewonnen();	
				} else {
					angreiferS.setKaempftGrade(false);
					zielland.setOwner(angreiferS);
					zielland.setSpielerArmee(angreifer);
					zielland.getOwner().getBesitzLaender().remove(zielland);
					angreiferS.addBesitzLaender(zielland);
					angreiferS.getSeinSpiel().getDerRundenverwalter().getAttacke().setAngriffsArmee(null);
					angreiferS.setHatArmeeImAermel(false);
					kampfUIVGui.kampfVerloren();
					kampfUIAGui.kampfGewonnen();	
				}
			}  else {
				this.initGui();
			}
		} else {
			System.out.println("Kampf wartet auf zweite Spielereingabe");
		}
	}
	
	/**
	 * Laesst beide Spieler in der CUI Version die Einheiten waehlen die diese in den Kampf schicken wollen,
	 * laesst diese gegeneinander kaempfen, gibt das jeweilige Ergebnis aus,
	 * tut diese Schritte solange bis eine Armee leer ist, 
	 * und gibt dann den Sieger aus,
	 * zusaetzlich wird das Land dem Sieger uebergeben und die jeweilige Restarmee in das betreffende Land eingegliedert.
	 * @return Gibt den Spieler zurueck der Gewonnen hat
	 */
	public Spieler init(){
		kampfUIA = new KampfInterface(angreiferS, verteidigerS, this);
		kampfUIV = new KampfInterface(verteidigerS, angreiferS, this);
		do {
			armeenZuweisen(angreifer, verteidiger);
			kampfUIA.init();
			kampfUIV.init();
			auswahlA = kampfUIA.einheitenWahl();
			auswahlV = kampfUIV.einheitenWahl();
			if (auswahlA == 1) {
				angreifendeE = aSchwert[aSchwert.length - 1];
			} else if (auswahlA == 2) {
				angreifendeE = aBogen[aBogen.length - 1];
			} else if (auswahlA == 3) {
				angreifendeE = aPferd[aPferd.length - 1];
			}
			if (auswahlV == 1) {
				verteidigendeE = vSchwert[vSchwert.length - 1];
			} else if (auswahlV == 2) {
				verteidigendeE = vBogen[vBogen.length - 1];
			} else if (auswahlV == 3) {
				verteidigendeE = vPferd[vPferd.length - 1];
			}
			loeschen = kaempfen(angreifendeE, verteidigendeE);
			if(angreifer.getArmee().contains(loeschen)) {
				kampfUIA.einheitVerloren();
				kampfUIV.einheitGewonnen();
				angreifer.remEinheit(loeschen);
			} else if (verteidiger.getArmee().contains(loeschen)) {
				kampfUIA.einheitGewonnen();
				kampfUIV.einheitVerloren();
				verteidiger.remEinheit(loeschen);
			}
		} while((angreifer.getArmee().isEmpty() || verteidiger.getArmee().isEmpty()) == false); 	
		if(angreifer.getArmee().isEmpty()) {
			return verteidigerS;
		} else {
			return angreiferS;
		}
	}
	/**
	 * Hier werden in den einzelnen ReferenzArrays die jeweils richtigen Einheiten gespeichert 
	 * Dann werden die Referenzadressen der Einheiten in den denentsprechenden Arrays gespeichert
	 * dort dienen diese zum leichteren Zugriff auf die Einheiten waehrend des Kampfes.
	 * 
	 * @param angr Benoetigt die Armee des Angreifers 
	 * @param vtd Benoetigt die Armee des Verteidigers 
	 */
	public void armeenZuweisen(Armee angr, Armee vtd){
		arraysEinstellen(angr, vtd);
		ArrayList<Einheit> listeA = angr.getArmee();
		ArrayList<Einheit> listeV = vtd.getArmee();
		int angrSchwerter = 0;
		int angrBogen = 0;
		int angrPferde = 0;
		for(int i = 0;i < listeA.size(); i ++) {
			if(listeA.get(i).getClass().toString().equals("class danielolivermarco.datenhaltung.Schwert")) {
				aSchwert[angrSchwerter] = listeA.get(i);
				angrSchwerter++;
			} else if(listeA.get(i).getClass().toString().equals("class danielolivermarco.datenhaltung.Bogen")) {
				aBogen[angrBogen] = listeA.get(i);
				angrBogen++;
			} else if(listeA.get(i).getClass().toString().equals("class danielolivermarco.datenhaltung.Pferd")) {
				aPferd[angrPferde] = listeA.get(i);
				angrPferde++;
			}
		}
		int vertSchwerter = 0;
		int vertBogen = 0;
		int vertPferde = 0;
		for(int y = 0;y < listeV.size(); y ++) {
			if(listeV.get(y).getClass().toString().equals("class danielolivermarco.datenhaltung.Schwert")) {
				vSchwert[vertSchwerter] = listeV.get(y);
				vertSchwerter++;
			} else if(listeV.get(y).getClass().toString().equals("class danielolivermarco.datenhaltung.Bogen")) {
				vBogen[vertBogen] = listeV.get(y);
				vertBogen++;
			} else if(listeV.get(y).getClass().toString().equals("class danielolivermarco.datenhaltung.Pferd")) {
				vPferd[vertPferde] = listeV.get(y);
				vertPferde++;
			}
		}
	}
	/**
	 * Hier werden die Referenzarrays eingestellt. 
	 * Dafuer wird fuer Angreifer und Verteidiger jeweils ein Array pro Einheitentyp erstellt
	 * , die Groesse bestimmt die Anzahl des jeweiligen Einheitentyps
	 * @param angr Benoetigt die Armee des Angreifers
	 * @param vert Benoetigt die Armee des Verteidigers
	 */
	public void arraysEinstellen(Armee angr, Armee vert) {
		int as = 0, ab = 0, ap = 0, vs = 0, vb = 0, vp = 0;
		ArrayList<Einheit> listeA = angr.getArmee();
		ArrayList<Einheit> listeV = vert.getArmee();
		for(int i = 0;i < listeA.size(); i ++) {
			if(listeA.get(i).getClass().toString().equals("class danielolivermarco.datenhaltung.Schwert")) {
				as++;
			} else if(listeA.get(i).getClass().toString().equals("class danielolivermarco.datenhaltung.Bogen")) {
				ab++;
			} else if(listeA.get(i).getClass().toString().equals("class danielolivermarco.datenhaltung.Pferd")) {
				ap++;
			}
		}
		for(int y = 0;y < listeV.size(); y ++) {
			if(listeV.get(y).getClass().toString().equals("class danielolivermarco.datenhaltung.Schwert")) {
				vs++;
			} else if(listeV.get(y).getClass().toString().equals("class danielolivermarco.datenhaltung.Bogen")) {
				vb++;
			} else if(listeV.get(y).getClass().toString().equals("class danielolivermarco.datenhaltung.Pferd")) {
				vp++;
			}
		}
		aSchwert = new Einheit[as];
		aBogen = new Einheit[ab];
		aPferd = new Einheit[ap];
		vSchwert = new Einheit[vs];
		vBogen = new Einheit[vb];
		vPferd = new Einheit[vp];
	}
	/**
	 *  laesst die Einheiten entsprechend den Regeln werfen und gibt die jeweiligen Wuerfelaktionen in der GUI aus
	 * sowie die verlorene Einheit zurueck, damit diese geloescht wird. 
	 * 
	 * @param a Benoetigt die Einheit des Angreifers
	 * @param v Benoetigt die Einheit des Verteidigers
	 * @return gibt die zu löschende einheit zurück
	 */
	public Einheit kaempfenGui(Einheit a, Einheit v) {
		Wuerfel w1 = new Wuerfel();
		int wurf = 0;
		int anzahlA = a.getMoeglicheWuerfel();
		int anzahlV = v.getMoeglicheWuerfel();
		int zahlA = 0;
		int zahlV = 0;
		do {
			wurf++;
			if (anzahlA > 0 && zahlA <= zahlV) {
				zahlA = w1.werfen();
				anzahlA--;
			}
			if (anzahlV > 0 && zahlV < zahlA) {
				zahlV = w1.werfen();
				anzahlV--;
			}
			kampfUIAGui.einzelkampfAnzeigen(zahlA, zahlV, wurf);
			kampfUIVGui.einzelkampfAnzeigen(zahlA, zahlV, wurf);
			if (zahlV == 6) {
				return a;
			}
			if (anzahlA == 0 && (zahlV >= zahlA)) {
				return a;
			}
			if (anzahlV == 0 && (zahlA > zahlV)) {
				return v;
			}

		} while (anzahlA > 0 || anzahlV > 0);
		if (zahlA > zahlV) {
			return v;
		} else {
			return a;
		}
	}
	/**
	 * laesst die Einheiten entsprechend den Regeln werfen und gibt die jeweiligen Wuerfelaktionen aus
	 * sowie die verlorene Einheit zurueck, damit diese geloescht wird. 
	 * 
	 * @param a Benoetigt die Einheit des Angreifers
	 * @param v Benoetigt die Einheit des Verteidigers
	 * @return gibt die zu löschende einheit zurück
	 */
	public Einheit kaempfen(Einheit a, Einheit v) {
		Wuerfel w1 = new Wuerfel();
		int anzahlA = a.getMoeglicheWuerfel();
		int anzahlV = v.getMoeglicheWuerfel();
		int zahlA = 0;
		int zahlV = 0;
		do {
			if (anzahlA > 0 && zahlA <= zahlV) {
				zahlA = w1.werfen();
				anzahlA--;
			}
			if (anzahlV > 0 && zahlV < zahlA) {
				zahlV = w1.werfen();
				anzahlV--;
			}
			kampfUIA.werfenAnzeige(zahlA, zahlV);
			kampfUIV.werfenAnzeige(zahlV, zahlA);
			if (zahlV == 6) {
				return a;
			}
			if (anzahlA == 0 && (zahlV >= zahlA)) {
				return a;
			}
			if (anzahlV == 0 && (zahlA > zahlV)) {
				return v;
			}

		} while (anzahlA > 0 || anzahlV > 0);
		if (zahlA > zahlV) {
			return v;
		} else {
			return a;
		}
	}
}
