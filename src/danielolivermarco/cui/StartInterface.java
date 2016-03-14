package danielolivermarco.cui;

	/**
	 * Interface zum auswaehlen der Spieleranzahl 
	 * Einstellen ihrer Namen 
	 * und starten des Spiel
	 * Spaeter koennte man hier 
	 */
public class StartInterface {
	
	public StartInterface() {		
	}
	/**
	 * Begruesst beim Programmstart
	 */
	public void spielerzahlAuswaehlen() {
		System.out.println("Willkommen beim Herr der Ringe Risiko!");
		System.out.println("Wieviele Spieler sollen spielen?");
	}
	/**
	 * Fragt wie ein bestimmter Spieler heissen soll
	 * die Eingabe findet fuer diesen Fall in der Anfangsphase statt
	 * 
	 * @param a Benoetigt die Nummer des Spielers der benannt werden soll
	 */
	public void spielerNameAuswaehlen(int a) {
		System.out.println("Welchen Namen soll der Spieler Nr." + a + " tragen ?");
	}
	/**
	 * Falls zu wenige oder zu viele Spieler spielen sollen wird diese Anzeige ausgegeben 
	 */
	public void invalideZahl() {
		System.out.println("Es ist nicht moeglich mit dieser Anzahl an Spielern zu spielen, bitte gebe eine andere Spieleranzahl zwischen 2 und 4 ein!");
	}
}
