package danielolivermarco.cui;

import java.io.Serializable;

import danielolivermarco.datenhaltung.*;
/**
 * Gibt dem Spieler in der CUI Version alle moeglichen Meldungen aus.
 *
 */
public class SpielerInterface implements Serializable{
	
	private Spieler besitzer;
	/**
	 * Der Konstruktor speichert den Spieler dem das Interface gehoert
	 * @param s	 Ubergibt den Spieler dem das Interface gehoert 
	 */
	public SpielerInterface(Spieler s)  {
		setBesitzer(s);
	}
	/**
	 * 
	 * @return Besitzer des SpielerIntaces.
	 */
	public Spieler getBesitzer() {
		return besitzer;
	}
	/**
	 * Besitzer des SpielerInterfaces wird gesetzt.
	 * @param besitzer Besitzer des Interfaces.
	 */
	private void setBesitzer(Spieler besitzer) {
		this.besitzer = besitzer;
	}
	/**
	 * Willkommensanzeige fuer die Spieler. Es wird dem Spieler die Mission und der
	 * Einheitenpool angezeigt.
	 */
	public void halloAnz() {
		System.out.println("Hallo " + besitzer.getName());
		System.out.println("Willkommen beim Herr der Ringe Risiko");
		System.out.println("Deine Aufgabe ist es: ");
		missionAnz();
		System.out.println("Du hast zum Start dieses Spiels in deinem Einheitenpool: " + besitzer.getEinheitenPool().size() + " Schwert-Einheiten");
	}
	/**
	 * 
	 * @return Es wird die Nummer zurueckgegeben, die der Spieler gewaehlt hat.
	 */
	public int standartAnz() {
		System.out.println("Welche Information benoetigst du?");
		System.out.println("1: Alle Laender Ansehen");
		System.out.println("2: Meine Geldboerse ansehen");
		System.out.println("3: Meinen Einheitenpool ansehen");
		System.out.println("4: Meine Mission ansehen");
		System.out.println("5: Meine Laender ansehen");
		System.out.println("9: Beenden");
		int eingabe = IO.readInt();
		return eingabe;
	}
	/**
	 * Zeigt alle laender, deren Besitzer und die dort stationiert Armee
	 * wird immer dann angezeigt, wenn der Spieler ein Land von allen Laendern auswaehlen soll.
	 * @return Gibt die LandId zurueck des Landes, welches der Spieler ausgewaehlt hat
	 */
	public int laenderAnz() {
		int eingabe = 0;
		int s = 0;
		int b = 0;
		int p = 0;
		do {
			for (int i = 0; i < 42; i++) {
				if(besitzer.getSeinSpiel().getKarte().getLaender().get(i).getOwner() != null) { 
					System.out.print("Besitzer: " + besitzer.getSeinSpiel().getKarte().getLaender().get(i).getOwner().getName() + " ");
				}
				System.out.println(besitzer.getSeinSpiel().getKarte().getLaender().get(i).getNummer() + ": " + besitzer.getSeinSpiel().getKarte().getLaender().get(i).getName());
				 if(besitzer.getSeinSpiel().getKarte().getLaender().get(i).getSpielerArmee() != null) {
					for (int y = 0; y < besitzer.getSeinSpiel().getKarte().getLaender().get(i).getSpielerArmee().getArmee().size(); y++) {
						if(besitzer.getSeinSpiel().getKarte().getLaender().get(i).getSpielerArmee().getArmee().get(y).getClass().toString().equals("class danielolivermarco.datenhaltung.Schwert")) {
							s++;
						} else if(besitzer.getSeinSpiel().getKarte().getLaender().get(i).getSpielerArmee().getArmee().get(y).getClass().toString().equals("class danielolivermarco.datenhaltung.Bogen")) {
							b++;
						} else if(besitzer.getSeinSpiel().getKarte().getLaender().get(i).getSpielerArmee().getArmee().get(y).getClass().toString().equals("class danielolivermarco.datenhaltung.Pferd")) {
							p++;
						}
				}
				System.out.print("Hat folgende Armee: ");
				System.out.println(s + " Schwerter, " + b + " Bogenschuetzen, " + p + " Reiter");
				s = 0;
				b = 0;
				p = 0;
				} 
			}
			eingabe = IO.readInt();
		} while (eingabe < 1 || eingabe > 42);
		return eingabe;
	}
	/**
	 * Das Gold des Spielers wird angezeigt.
	 */
	public void geldAnz() {
		System.out.println("Du hast " + besitzer.getGeld() + " Gold zur Verfuegung!");
	}
	/**
	 * Es wird der Einheitenpool angezeigt.
	 */
	public void einheitenPoolAnz() {
		int s = 0;
		int b = 0;
		int p = 0;
		System.out.println("Du hast ein deinem Einheiten Pool folgende Einheiten: ");
		for (int i = 0; i < besitzer.getEinheitenPool().size(); i++) {
			if(besitzer.getEinheitenPool().get(i).getClass().toString().equals("class danielolivermarco.datenhaltung.Schwert")) {
					s++;
			} else if(besitzer.getEinheitenPool().get(i).getClass().toString().equals("class danielolivermarco.datenhaltung.Bogen")) {
					b++;
			} else if(besitzer.getEinheitenPool().get(i).getClass().toString().equals("class danielolivermarco.datenhaltung.Pferd")) {
					p++;
			}
		}
		System.out.println(s + " Schwerter");
		System.out.println(b + " Bogenschuetzen");
		System.out.println(p + " Reiter");
	}
	/**
	 * Es wird die Mission des Spielers angezeigt.
	 */
	public void missionAnz() {
		if (besitzer.getMission().getClass().toString().equals("class danielolivermarco.anwendungslogik.MissionVierKontinente")){
			System.out.println("Folgende Vier Kontinente zu erobern:");
			for (int i = 0; i < besitzer.getZuErobern().length; i++) {
				System.out.println(besitzer.getZuErobern()[i].getName());
			}
		} else if (besitzer.getMission().getClass().toString().equals("class danielolivermarco.anwendungslogik.MissionDreiEinheiten")) {
			System.out.println("Auf 15 deiner Laender mindestens drei Einheiten zu platzieren!");
		} else if (besitzer.getMission().getClass().toString().equals("class danielolivermarco.anwendungslogik.MissionSpielerToeten")) {
			System.out.println("Den Spieler " + besitzer.getToetungsziel().getName() + ", auszuloeschen!");
		}
	}
	/**
	 * Hier werden einem die eigenen Laender angezeigt
	 * Zeitgleich kann man sofort sehen wie die dortige Armee ausgestattet ist 
	 * Es wird natuerlich bei der Auswahl geprueft ob das jeweilige Land dem Spieler gehoert.
	 * 
	 * @return Die nummer des ausgewaehlten Landes wird zurueckgegeben 
	 */
	public int meineLaenderAnz() {	
		boolean funkt = false;
		int s = 0;
		int b = 0;
		int p = 0;
		int landId;
		for (int i = 0; i < besitzer.getBesitzLaender().size(); i++) {
			for(int y = 0; y < besitzer.getBesitzLaender().get(i).getSpielerArmee().getArmee().size(); y ++) {		
				if(besitzer.getBesitzLaender().get(i).getSpielerArmee().getArmee().get(y).getClass().toString().equals("class danielolivermarco.datenhaltung.Schwert")) {
					s++;
				} else if(besitzer.getBesitzLaender().get(i).getSpielerArmee().getArmee().get(y).getClass().toString().equals("class danielolivermarco.datenhaltung.Bogen")) {
					b++;
				} else if(besitzer.getBesitzLaender().get(i).getSpielerArmee().getArmee().get(y).getClass().toString().equals("class danielolivermarco.datenhaltung.Pferd")) {
					p++;
				}
			}
			System.out.print(besitzer.getBesitzLaender().get(i).getNummer() + ": " + besitzer.getBesitzLaender().get(i).getName() + ", hat folgende Armee: ");
			System.out.println(s + " Schwerter, " + b + " Bogenschuetzen, " + p + " Reiter");
			s = 0;
			b = 0;
			p = 0;
		}
		do {
			System.out.println("Bitte waehle eins aus");
			landId = IO.readInt();
			for (int i = 0; i < besitzer.getBesitzLaender().size(); i++) {
				if (besitzer.getBesitzLaender().get(i).getNummer() == landId) {
					funkt = true;
					return landId;
				} 
			}
			if (funkt == false) {
				System.out.println("Du besitzt kein Land mit dieser ID");
			}
		} while (funkt == false); 
		return landId;
	}
	/**
	 * Zeigt alle Nachbarlaender wie in der gesamten Laenderanzeige
	 * Es koennen vom Spieler auch nur die angezeigten Nachbarlaender ausgewaehlt werden
	 * diese Funktion wird z.B. in der Kampf und Verschiebenphase genutzt
	 * @param ursprungsLand  Benoetigt das Land dessen Nachbarn angezeigt werden sollen
	 * @return Die nummer des ausgewaehlten Landes wird zurueckgegeben 
	 */
	public int nachbarAnz(Land ursprungsLand) {
			boolean funkt = false;
			int s = 0;
			int b = 0;
			int p = 0;
			int landId;
			for (int i = 0; i < ursprungsLand.getNachbarn().length; i++) {
				for(int y = 0; y < ursprungsLand.getNachbarn()[i].getSpielerArmee().getArmee().size(); y ++) {		
					if(ursprungsLand.getNachbarn()[i].getSpielerArmee().getArmee().get(y).getClass().toString().equals("class danielolivermarco.datenhaltung.Schwert")) {
						s++;
					} else if(ursprungsLand.getNachbarn()[i].getSpielerArmee().getArmee().get(y).getClass().toString().equals("class danielolivermarco.datenhaltung.Bogen")) {
						b++;
					} else if(ursprungsLand.getNachbarn()[i].getSpielerArmee().getArmee().get(y).getClass().toString().equals("class danielolivermarco.datenhaltung.Pferd")) {
						p++;
					}
				}
				System.out.print(ursprungsLand.getNachbarn()[i].getNummer() + ": " + ursprungsLand.getNachbarn()[i].getName() + ", Besitzer: " + ursprungsLand.getNachbarn()[i].getOwner().getName() +  ", hat folgende Armee: ");
				System.out.println(s + " Schwerter, " + b + " Bogenschuetzen, " + p + " Reiter");
				s = 0;
				b = 0;
				p = 0;
			}
			System.out.println("Ursprungsland: " + ursprungsLand.getNummer() + ": " + ursprungsLand.getName());
			do {
				System.out.println("Bitte waehle eins aus");
				landId = IO.readInt();
				for (int i = 0; i < ursprungsLand.getNachbarn().length; i++) {
					if (ursprungsLand.getNachbarn()[i].getNummer() == landId) {
						funkt = true;
						return landId;
					} else if (ursprungsLand.getNummer() == landId) {
						funkt = true;
						return landId;
					}
				}
				if (funkt == false) {
					System.out.println("Das Land hat keinen Nachbarn mit dieser ID");
				}
			} while (funkt == false); 
			return landId;
		}
	/**
	 * Hier werden die Startlaender ausgewaehlt
	 * @return Die nummer des ausgewaehlten Landes wird zurueckgegeben 
	 */
	public int landAuswahlStart() {
		System.out.println("Spieler " + besitzer.getName());
		System.out.println("Du kannst nun eines deiner Startlaender auswaehlen!");
		System.out.println("waehle die ID des jeweiligen Landes welches du besetzen moechtest, " 
				+ "dann wird eine Infrantrieeinheit aus deinem Einheitenpool auf dem Land platziert.");
		int eingabe = laenderAnz();
		return eingabe;
	}
	/**
	 * Wenn ein Land ausgewaehlt wurde welches schon jemanden gehoert wird diese Ausgabe produziert 
	 */
	public void landAuswahlFehler() {
		System.out.println("Dieses Land gehoert schon jemanden, waehle bitte ein anderes!");
	}
	/**
	 * Hier werden auf die bereits verteilten Laender die uebrigen Einheiten 
	 * @return Die nummer des ausgewaehlten Landes wird zurueckgegeben 
	 */
	public int einheitenVeteilen() {
		System.out.println(besitzer.getName());
		einheitenPoolAnz();
		System.out.println("Diese Einheiten kannst du nun auf deine Laender verteilen.");
		System.out.println("Dies geschieht abwechselnd mit den anderen Spielern.");
		System.out.println("Waehle nun bitte aus auf welchem Land du einer deiner Schwertkaempfer platzieren moechtest!");
		int landId = meineLaenderAnz();
		return landId;		
	}

	/**
	 * Zeigt dem Spieler an was er fuer seinen Besitz 
	 * @param laenderGold Das Gold fuer die besetzten Laender.
	 * @param kontinentBonus Das Gold fuer die besetzten Kontinente.
	 */
	public void geldBekommenAnz(int laenderGold, int kontinentBonus) {
		System.out.println(besitzer.getName());
		System.out.println("Du hast fuer deinen Besitz an Laendern " + laenderGold + " Gold bekommen!");
		System.out.println("Fuer die Kontinente die du Bestitzt bekommst du noch einmal " + kontinentBonus + " extra Gold" );
	}
	
	/**
	 * Es koennen Einheiten gekauft werden.
	 * @return Gibt eine Zahl fuer eine bestimmte Einheit zurueck.
	 */
	public int einheitenKaufen() {
		System.out.println("Du kannst nun Einheiten fuer deinen EinheitenPool kaufen!");
		einheitenPoolAnz();
		geldAnz();
		System.out.println("1: Schwert-Einheit Kaufen: 100 gold.");
		System.out.println("2: Bogenschuetzen kaufen: 200 gold");
		System.out.println("3: Reiter kaufen: 400 gold");
		System.out.println("9: Kaufen beenden und zum Einheiten setzen gehen.");
		int eingabe = IO.readInt();
		return eingabe;
	}
	
	/**
	 * Es koennen Einheiten in eigenen Laendern platziert werden.
	 * @return Zahl fuer eine bestimmte Auswahl.
	 */
	public int einheitenSetzenAuswahl() {
		System.out.println(besitzer.getName());
		System.out.println("Du kannst nun einheiten aus deinem EinheitenPool in Bestimmte Laender setzen, oder dir Informationen holen!");
		System.out.println("1: Deine Laender ansehen (eins auswaehlen um zurueck zu kommen");
		System.out.println("2: Alle Laender ansehen (eins auswaehlen um zurueck zu kommen");
		System.out.println("3: Einheiten setzen");
		System.out.println("9: BuyAndSet Modus beenden");
		int eingabe = IO.readInt();
		if (eingabe == 3) {
			System.out.println("Waehle nun dein Land in welches du eine Einheit platzieren moechtest!");
		}
		return eingabe;
	}
	
	/**
	 * Auswahl der Einheit, die in das Land gesetzt werden soll.
	 * @return Zahl fuer bestimmte Auswahl.
	 */
	public int einheitSetzen() {
		einheitenPoolAnz();
		if (besitzer.getEinheitenPool().size() == 0) {
			keineEinheitenMehr();
			return 9;
		}
		System.out.println("Welche Einheit moechtest du in das Land setzen?");
		System.out.println("1: Schwerter");
		System.out.println("2: Bogenschuetze");
		System.out.println("3: Reiter");
		System.out.println("9: Keine");
		int eingabe = IO.readInt();
		return eingabe;
	}
	/**
	 * Wird als Beginn der Angriffsphase ausgegeben 
	 */
	public void angriffEinstieg() {
		System.out.println(besitzer.getName());
		System.out.println("Du kannst nun eine Armee aus einem deiner Laender zusammenstellen und damit ein benachbartes Land angreifen!");
		System.out.println("Beachte dabei aber, dass eine Einheit in deinem Land bleiben muss!");
	}
	/**
	 * Wird als Beginn der Verschiebenphase ausgegeben
	 */
	public void verschiebenEinstieg() {
		System.out.println(besitzer.getName());
		System.out.println("Du kannst nun eine Armee aus einem deiner Laender zusammenstellen und diese in ein benachbartes Land verschieben");
		System.out.println("Beachte dabei aber, dass eine Einheit in deinem Land bleiben muss!");
	}
	/**
	 * Hier waehlt der Spieler aus welchem Land er eine Armee zusammenstellen moechte 
	 * @return Die nummer des ausgewaehlten Landes wird zurueckgegeben 
	 */
	public int armeeZusammenstellenLand() {
		System.out.println("Waehle nun aus, aus welchem Land du eine Armee zusammenstellen moechtest");
		int landId = meineLaenderAnz();
		return landId;
	}
	
	/**
	 * Armee kann zusammengestellt werden.
	 * @return Zahl fuer bestimmte Auswahl.
	 */
	public int armeeZusammenstellenEinheiten() {
		System.out.println("Welche Einheit moechtest du deiner Armee aus diesem Land hinzufuegen?");
		System.out.println("1: Schwert");
		System.out.println("2: Bogenschuetzen");
		System.out.println("3: Reiter");
		System.out.println("9: Keine Einheiten mehr hinzufuegen");
		int eingabe = IO.readInt();
		return eingabe;
	}
	/**
	 * Falls nicht genuegend Einheiten in dem Land sind, wird diese Ausgabe produziert
	 */
	public void armeeZusammenstellenMindestEinheit() {
		System.out.println("Du benoetigst mindestens eine Einheit in dem Land!");
	}
	/**
	 * Wenn der Spieler in seinem EInheitenPool keine Einheiten mehr hat, wird diese Ausgabe produziert
	 */
	public void keineEinheitenMehr() {
		System.out.println("Du hast keine Einheiten mehr in deinem EinheitenPool, bitte beende den BuyAndSet Modus!");
	}
	/**
	 * Wenn der Spieler von einer bestimmten Sorte Einheiten keine mehr hat, wird diese Ausgabe produziert
	 */
	public void keineBestimmteEinheitMehr() {
		System.out.println("Du hast von dieser Einheit keine mehr!");
	}
	/**
	 * Wenn der Spieler eine nicht zur Verfuegung stehende Eingabe taetigt, wird diese Ausgabe produziert
	 */
	public void keineEingabe() {
		System.out.println("Es steht keine solche Eingabe zur Verfuegung!");
	}
	/**
	 * Wenn der Spieler im beim Verschieben versuct, seine Armee in ein Feindland zu verschieben, wird diese Ausgabe produziert
	 */
	public void nichtHierhinVerschieben() {
		System.out.println("Dies ist nicht dein Land, verschiebe deine Einheiten in ein anderes Land!");
	}
	/**
	 * 
	 * @return Zahl, ob weitere Einheiten verschoben werden sollen.
	 */
	public int verschiebenWeiter() {
		System.out.println("Moechtest du weitere Einheiten verschieben ?");
		System.out.println("1: Ja!");
		System.out.println("2: Nein!");
		int eingabe = IO.readInt();
		return eingabe;
	}
	/**
	 * 
	 * @return Zahl fuer weiteren Angriff.
	 */
	public int angriffWeiter() {
		System.out.println("Moechtest du einen weiteren Angriff durchfuehren?");
		System.out.println("1: Ja!");
		System.out.println("2: Nein!");
		int eingabe = IO.readInt();
		return eingabe;
	}
	/**
	 * Wird ausgegeben wenn ein Angriff abgebrochen wird.
	 */
	public void angriffAbgebrochen() {
		System.out.println("Dein Angriff wurde abgebrochen und die Armee in das Ursprungsland wieder eingegliedert!");	
	}
	/**
	 * Wird ausgegeben wenn ein Angriff verloren wurde
	 */
	public void angriffVerloren() {
		System.out.println("Du hast den Angriff verloren, alle deine Einheiten wurden vernichtet!");
	}
	/**
	 * Wird ausgegeben wenn ein Angriff gewonnen wurde 
	 */
	public void angriffGewonnen() {
		System.out.println("Du hast den Angriff gewonnen, das Land gehoert nun dir, deine Armee mit der du angegriffen hast wurde in dem Besiegten Land stationiert.");
	}
	/**
	 * Wird vor der Modiauswahl ausgegeben
	 */
	public void modusAuswahl() {
		System.out.println("Welchen Spielmodus wollt ihr spielen?");
		System.out.println("0: Laender automatisch verteilen, Einheiten manuell setzen.");
		System.out.println("1: Laender und Einheiten automatisch verteilen.");
		System.out.println("2: Laender und Einheiten manuell verteilen.");
	}
	/**
	 * Wenn der Spieler nicht mehr genug Geld fuer eine bestimmte Einheit hat, wird diese Ausgabe produziert
	 */
	public void nichtGenugGeld() {
		System.out.println("Dafuer hast du nicht genug Geld, kaufe etwas guenstigeres oder Beende die aktuelle Phase.");
		
	}
}
