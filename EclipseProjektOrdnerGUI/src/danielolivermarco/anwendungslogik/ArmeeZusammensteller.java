package danielolivermarco.anwendungslogik;

import java.io.Serializable;

import danielolivermarco.datenhaltung.*;
import danielolivermarco.gui.PopupGUI;


/**
 * 
 * Organisiert das Zusammenstellen einer Armee eines Spielers im Angriffs und Verschieben Modus 
 * 
 */
public class ArmeeZusammensteller implements Serializable {

	private Spiel dasSpiel;
	private Land ursprungsLand;
	
	/**
	 * Der Konstruktor speichert das Spiel um darueber auf verschiedene Objekte zuzugreifen
	 * @param s	 Uebergibt das Spiel. 
	 */
	public ArmeeZusammensteller(Spiel s) {
		dasSpiel = s;
	}
	/**
	 * Sorgt in der CUI Version des Spiels dafuer das der Spieler eine Armee zusammenstellen kann
	 * Dazu wird geprueft ob die jeweilige Einheit die der Spieler der Armee hinzufuegen moechte vorhanden ist 
	 * und ob das Land noch mindestens eine Einheit beinhaltet.
	 * Wenn der Spieler fertig ist wird die zusammengestelte Armee zurueckgegeben
	 * 
	 * @return gibt die Armee zurueck die der Spieler zusammengestellt hat
	 */
	public Armee init(Spieler besitzer) {
		Armee a = new Armee(besitzer);
		boolean zusammenstellen = true;
		while (zusammenstellen) {
			int auswahl = besitzer.getPersoenlichesInterface().armeeZusammenstellenEinheiten();
			switch (auswahl) {
			case 1	:	if (ursprungsLand.getSpielerArmee().getArmee().size() == 1) {
							besitzer.getPersoenlichesInterface().armeeZusammenstellenMindestEinheit();
							break;
						}
						for(int i = 0;i < ursprungsLand.getSpielerArmee().getArmee().size(); i++) {
							if (ursprungsLand.getSpielerArmee().getArmee().get(i).getClass().toString().equals("class danielolivermarco.datenhaltung.Schwert")) {
								Einheit sx = ursprungsLand.getSpielerArmee().getArmee().get(i);
								a.armeeZuwachs(sx);
								ursprungsLand.getSpielerArmee().getArmee().remove(sx);
								break;
							} else if (i == ursprungsLand.getSpielerArmee().getArmee().size() - 1) {
								besitzer.getPersoenlichesInterface().keineBestimmteEinheitMehr();
							}
						}
						break;
			case 2	:	if (ursprungsLand.getSpielerArmee().getArmee().size() == 1) {
							besitzer.getPersoenlichesInterface().armeeZusammenstellenMindestEinheit();
							break;
						}
						for(int i = 0;i < ursprungsLand.getSpielerArmee().getArmee().size(); i++) {
							if (ursprungsLand.getSpielerArmee().getArmee().get(i).getClass().toString().equals("class danielolivermarco.datenhaltung.Bogen")) {
								Einheit bx = ursprungsLand.getSpielerArmee().getArmee().get(i);
								a.armeeZuwachs(bx);
								ursprungsLand.getSpielerArmee().getArmee().remove(bx);
								break;
							} else if (i == ursprungsLand.getSpielerArmee().getArmee().size() - 1) {
								besitzer.getPersoenlichesInterface().keineBestimmteEinheitMehr();
							}
						}
						break;
			case 3	: 	if (ursprungsLand.getSpielerArmee().getArmee().size() == 1) {
							besitzer.getPersoenlichesInterface().armeeZusammenstellenMindestEinheit();
							break;
						}
						for(int i = 0;i < ursprungsLand.getSpielerArmee().getArmee().size(); i++) {
							if (ursprungsLand.getSpielerArmee().getArmee().get(i).getClass().toString().equals("class danielolivermarco.datenhaltung.Pferd")) {
								Einheit px = ursprungsLand.getSpielerArmee().getArmee().get(i);
								a.armeeZuwachs(px);
								ursprungsLand.getSpielerArmee().getArmee().remove(px);
								break;
							} else if (i == ursprungsLand.getSpielerArmee().getArmee().size() - 1) {
								besitzer.getPersoenlichesInterface().keineBestimmteEinheitMehr();
							}
						}
						break;
			case 9 	:	zusammenstellen = false;
						break;
			default	:	besitzer.getPersoenlichesInterface().keineEingabe();
						break;
			}
		}
		return a;
	}
	/**
	 * Sorgt in der GUI Version des Spiels dafuer das der Spieler eine Armee zusammenstellen kann
	 * Dazu wird in die GUI Klasse PopupGUI angesteuert in der der Spieler die einzelnen Einheiten fuer die Armee auswaelen kann
	 * 
	 * @param s Benoetigt den Spieler der eine Armee zusammenstellt
	 * @param l	Benoetigt das Land aus dem die Armee zusammengestelt werden soll
	 */
	public void initGui(Spieler s, Land l) {
		ursprungsLand = l; 
		AngriffsModus angriffsPhase = s.getSeinSpiel().getDerRundenverwalter().getAttacke();
		angriffsPhase.setUrsprung(ursprungsLand);
		angriffsPhase.setAngriffsArmee(new Armee(s));
		PopupGUI angriffZusammenstellen = new PopupGUI(s.getSeinSpiel());
		s.setHatArmeeImAermel(true);
		angriffZusammenstellen.armeeZusammenstellen(s, l);
	}
	/**
	 * Fragt in der CUI Version des Spieles den Spieler aus welchem Land der Spieler eine Armee zusammenstellen moechte
	 * 
	 * @param besitzer uebergibt den Spieler der ein Land auswaehlen soll
	 * @return gibt die LandId des vom Spieler gewaehlten landes zurueck
	 */
	public Land land(Spieler besitzer) {
		int landId = besitzer.getPersoenlichesInterface().armeeZusammenstellenLand();
		ursprungsLand = dasSpiel.getKarte().getLaender().get(landId - 1);
		return ursprungsLand;
	}
	
}
