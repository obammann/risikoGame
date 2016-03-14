package danielolivermarco.gui;


import java.io.Serializable;

import danielolivermarco.anwendungslogik.ArmeeAufloeser;
import danielolivermarco.anwendungslogik.ArmeeZusammensteller;
import danielolivermarco.anwendungslogik.Kampf;
import danielolivermarco.datenhaltung.*;


/**
 * 
 * Hier wird in der GUI Version geprueft was geschehen muss wenn ein Spieler in seinem 
 * Interface eine Aktion ausloest.
 */
public class LogikAnspracheGui implements Serializable {
	
	/**
	 * Methode wird ausgefuehrt, wenn ein Land von einem Spieler ausgewaehlt wird und die Laender manuell
	 * ausgewaehlt werden muessen.
	 * @param l Das Land das ausgewaehlt wurde.
	 * @param s Der Spieler, der das Land ausgewaehlt hat.
	 */
	public void laenderAuswahl(Land l, Spieler s) {
		if (s.isLandAuswaehlenAnfang() && l.getOwner() == null) {
			int uebergabeIntSpieler = s.getSpielerNr() - 1;
			int uebergabeIntLand = l.getNummer() - 1;
			s.getSeinSpiel().getDieAnfangsphase().landZuteilen(uebergabeIntLand, uebergabeIntSpieler);
			s.setLandAuswaehlenAnfang(false);
			s.getSeinSpiel().getDieAnfangsphase().laenderAuswahlGUI();
		} else if (s.isLandAuswaehlenAnfang() && l.getOwner() != null) {
		} else if (s.isEinheitEinfuegenAnfang() == false) {
			Spieler aktuellerSpieler = null;
			for (int i = 0; i < s.getSeinSpiel().getSpielerListe().size(); i++) {
				if (s.getSeinSpiel().getSpielerListe().get(i).isLandAuswaehlenAnfang()) {
					aktuellerSpieler = s.getSeinSpiel().getSpielerListe().get(i);
				}
			}
			PopupGUI popup = new PopupGUI();
			popup.textAnzeigen(aktuellerSpieler.getName() + " ist grade dran, warte bitte noch!", s);
		}
	}
	
	/**
	 * Wird immer Angesteuert wenn ein land ausgewaehlt wurde
	 * Die Methode prueft welche Boolean beim Spieler auf true sind und ob betreffender Spieler eine Armee zusammengestellt hat,
	 * und je nachdem was zutrifft werden entsprechende Aktionen in den verschiedenen Phasen durchgefuehrt
	 * @param l Land, das ausgewaehlt wurde.
	 * @param s Spieler, der das Land ausgewaehlt hat.
	 */
	public void landWurdeAusgewaehlt(Land l, Spieler s) {
		
		
		if (s.isEinheitEinfuegenAnfang() && (l.getOwner() == s) && s.getEinheitenPool().isEmpty() == false) {
			int uebergabeIntSpieler = s.getSpielerNr() - 1;
			int uebergabeIntLand = l.getNummer();
			s.getSeinSpiel().getDieAnfangsphase().einheitEinfuegen(uebergabeIntLand, uebergabeIntSpieler);
			s.setEinheitEinfuegenAnfang(false);
			s.getSeinSpiel().getDieAnfangsphase().restlicheVerteilenGUI();
		} else if (s.isEinheitenKaufenUndSetzen() && !s.isEinheitKaufenUndSetzenBereitsGetan()) {
			if (l.getOwner() == s) {
				PopupGUI einheitZufuegen = new PopupGUI(s.getSeinSpiel());
				einheitZufuegen.einheitenPlatzieren(s, l);
			} else if (l.getOwner() != s) {
				PopupGUI textAnzeigen = new PopupGUI(s.getSeinSpiel());
				String anzeige = "Das ist nicht dein Land";
				textAnzeigen.textAnzeigen(anzeige, s);
			}
		} 
		
		
		
		else if (s.isAngriffDurchfuehren() && !s.isAngriffDurchfuehrenBereitsGetan() && !s.isHatArmeeImAermel()) {
			if (l.getOwner() == s) {
				ArmeeZusammensteller derZusammensteller = new ArmeeZusammensteller(s.getSeinSpiel());
				derZusammensteller.initGui(s, l);
			} else if (l.getOwner() != s) {
				PopupGUI textAnzeigen = new PopupGUI(s.getSeinSpiel());
				String anzeige = "Das ist nicht dein Land";
				textAnzeigen.textAnzeigen(anzeige, s);
			}
		} else if (s.isAngriffDurchfuehren() && !s.isAngriffDurchfuehrenBereitsGetan() && s.isHatArmeeImAermel()) {
			boolean nichtMehrWeiter = false;
			if (l.getOwner() != s) {
				for (int i = 0; i < l.getNachbarn().length; i++) {
					if (!nichtMehrWeiter && l.getNachbarn()[i] == s.getSeinSpiel().getDerRundenverwalter().getAttacke().getUrsprung()) {
						Kampf derKampf = new Kampf(s.getSeinSpiel().getDerRundenverwalter().getAttacke().getAngriffsArmee() , l.getSpielerArmee(), l);
						nichtMehrWeiter = true;
						s.setKaempftGrade(true);
						derKampf.initGui();
					} else if (!nichtMehrWeiter && i == l.getNachbarn().length - 1){
						PopupGUI textAnzeigen = new PopupGUI(s.getSeinSpiel());
						String anzeige = "Du musst ein NachbarLand auswaehen.";
						textAnzeigen.textAnzeigen(anzeige, s);
					}
				}
			} else if (l.getOwner() == s && !(l == s.getSeinSpiel().getDerRundenverwalter().getAttacke().getUrsprung())) {
				PopupGUI textAnzeigen = new PopupGUI(s.getSeinSpiel());
				String anzeige = "Dieses Land kannst du nicht angreifen";
				textAnzeigen.textAnzeigen(anzeige, s);
			} else if (l.getOwner() == s && (l == s.getSeinSpiel().getDerRundenverwalter().getAttacke().getUrsprung())) {
				PopupGUI textAnzeigen = new PopupGUI(s.getSeinSpiel());
				String anzeige = "Deine Armee wurde im Ursprungsland wieder aufgeloest";
				textAnzeigen.textAnzeigen(anzeige, s);
				ArmeeAufloeser aufloeser = new ArmeeAufloeser();
				aufloeser.init(s.getSeinSpiel().getDerRundenverwalter().getAttacke().getAngriffsArmee(), l);
				s.setHatArmeeImAermel(false);
				s.getSeinSpiel().getDerRundenverwalter().getAttacke().setAngriffsArmee(null);
			}
		}
		
		
		
		
		else if(s.isVerschiebenDurchfuehren() && !s.isVerschiebenDurchfuehrenBereitsGetan() && !s.isHatArmeeImAermel()) {
			if (l.getOwner() == s) {
				ArmeeZusammensteller derZusammensteller = new ArmeeZusammensteller(s.getSeinSpiel());
				derZusammensteller.initGui(s, l);
			} else if (l.getOwner() != s) {
				PopupGUI textAnzeigen = new PopupGUI(s.getSeinSpiel());
				String anzeige = "Das ist nicht dein Land";
				textAnzeigen.textAnzeigen(anzeige, s);
			}
		} else if (s.isVerschiebenDurchfuehren() && !s.isVerschiebenDurchfuehrenBereitsGetan() && s.isHatArmeeImAermel()) {
			boolean nichtMehrWeiter = false;
			if (l.getOwner() == s) {
				for (int i = 0; i < l.getNachbarn().length; i++) {
					if (!nichtMehrWeiter && l.getNachbarn()[i] == s.getSeinSpiel().getDerRundenverwalter().getAttacke().getUrsprung()) {
						nichtMehrWeiter = true;
						PopupGUI textAnzeigen = new PopupGUI(s.getSeinSpiel());
						String anzeige = "Deine Armee wurde im Zielland aufgeloest";
						textAnzeigen.textAnzeigen(anzeige, s);
						ArmeeAufloeser aufloeser = new ArmeeAufloeser();
						aufloeser.init(s.getSeinSpiel().getDerRundenverwalter().getAttacke().getAngriffsArmee(), l);
						s.setHatArmeeImAermel(false);
						s.getSeinSpiel().getDerRundenverwalter().getAttacke().setAngriffsArmee(null);						
					} else if (!nichtMehrWeiter && i == l.getNachbarn().length - 1){
						PopupGUI textAnzeigen = new PopupGUI(s.getSeinSpiel());
						String anzeige = "Du musst ein NachbarLand auswaehen.";
						textAnzeigen.textAnzeigen(anzeige, s);
					}
				}
			} else if (l.getOwner() != s && !(l == s.getSeinSpiel().getDerRundenverwalter().getAttacke().getUrsprung())) {
				PopupGUI textAnzeigen = new PopupGUI(s.getSeinSpiel());
				String anzeige = "In dieses Land kannst du deine Armee nicht verschieben.";
				textAnzeigen.textAnzeigen(anzeige, s);
			} else if (l.getOwner() == s && (l == s.getSeinSpiel().getDerRundenverwalter().getAttacke().getUrsprung())) {
				PopupGUI textAnzeigen = new PopupGUI(s.getSeinSpiel());
				String anzeige = "Deine Armee wurde im Ursprungsland wieder aufgeloest";
				textAnzeigen.textAnzeigen(anzeige, s);
				ArmeeAufloeser aufloeser = new ArmeeAufloeser();
				aufloeser.init(s.getSeinSpiel().getDerRundenverwalter().getAttacke().getAngriffsArmee(), l);
				s.setHatArmeeImAermel(false);
				s.getSeinSpiel().getDerRundenverwalter().getAttacke().setAngriffsArmee(null);
			}
		}
	}

	
	/**
	 * Wird angesteuert wenn ein Spieler auf den PhaseBeendenButton Klickt
	 * Wenn der Spieler nicht dran es geschieht nichts, falls doch werden eventuelle noch nicht aufgeloeste Armeen
	 * aufgeloest und die naechste Phase gestartet sowie markiert, dass der Spieler die Phase fuer diese Runde beendet hat. 
	 * @param s Uebergibt welcher Spieler den Button angeklickt hat. 
	 */
	public void phaseBeenden(Spieler s) {
		if (s.isEinheitenKaufenUndSetzen() && !s.isEinheitKaufenUndSetzenBereitsGetan()) {
			s.setEinheitKaufenUndSetzenBereitsGetan(true);
			s.getSeinSpiel().getDerRundenverwalter().initGui();
		}
		
		
		else if (s.isAngriffDurchfuehren() && !s.isAngriffDurchfuehrenBereitsGetan()) {
			if (s.isKaempftGrade()) {
				PopupGUI kaempfstGrade = new PopupGUI(s.getSeinSpiel());
				String anzeige ="Beende erst deinen Kampf!";
				kaempfstGrade.textAnzeigen(anzeige, s);
			} else if (s.isHatArmeeImAermel()) {
				ArmeeAufloeser armeeAufloesenLogik = new ArmeeAufloeser();
				armeeAufloesenLogik.init(s.getSeinSpiel().getDerRundenverwalter().getAttacke().getAngriffsArmee(), s.getSeinSpiel().getDerRundenverwalter().getAttacke().getUrsprung());
				s.setHatArmeeImAermel(false);
				s.getSeinSpiel().getDerRundenverwalter().getAttacke().setAngriffsArmee(null);
				PopupGUI armeeAufloesen = new PopupGUI(s.getSeinSpiel());
				String anzeige =s.getName() + ", da du die Phase beendest wird deine grade erstellte Armee im Ursprungsland wieder aufgeloest.";
				armeeAufloesen.textAnzeigen(anzeige, s);
				s.setAngriffDurchfuehrenBereitsGetan(true);
				s.getSeinSpiel().getDerRundenverwalter().initGui();
			} else {
				s.setAngriffDurchfuehrenBereitsGetan(true);
				s.getSeinSpiel().getDerRundenverwalter().initGui();
			}
		} 
		
		
		else if(s.isVerschiebenDurchfuehren() && !s.isVerschiebenDurchfuehrenBereitsGetan()) {
			if (s.isHatArmeeImAermel()) {
				ArmeeAufloeser armeeAufloesenLogik = new ArmeeAufloeser();
				armeeAufloesenLogik.init(s.getSeinSpiel().getDerRundenverwalter().getAttacke().getAngriffsArmee(), s.getSeinSpiel().getDerRundenverwalter().getAttacke().getUrsprung());
				s.setHatArmeeImAermel(false);
				s.getSeinSpiel().getDerRundenverwalter().getAttacke().setAngriffsArmee(null);
				PopupGUI armeeAufloesen = new PopupGUI(s.getSeinSpiel());
				String anzeige =s.getName() + ", da du die Phase beendest wird deine grade erstellte Armee im Ursprungsland wieder aufgeloest.";
				armeeAufloesen.textAnzeigen(anzeige, s);
				s.setVerschiebenDurchfuehrenBereitsGetan(true);
				if (s.getSeinSpiel().getSpielerListe().get(s.getSeinSpiel().getSpielerListe().size() - 1) == s) {
					s.getSeinSpiel().getDerRundenverwalter().getPruefen().init(s);
					s.getSeinSpiel().getDerRundenverwalter().rundenWechsel();
					s.getSeinSpiel().getDerRundenverwalter().initGui();
				} else if (s.getSeinSpiel().getSpielerListe().get(s.getSeinSpiel().getSpielerListe().size() - 1) != s) {
					s.getSeinSpiel().getDerRundenverwalter().initGui();
				}
			} else {
				s.setVerschiebenDurchfuehrenBereitsGetan(true);
				if (s.getSeinSpiel().getSpielerListe().get(s.getSeinSpiel().getSpielerListe().size() - 1) == s) {
					s.getSeinSpiel().getDerRundenverwalter().getPruefen().init(s);
					s.getSeinSpiel().getDerRundenverwalter().rundenWechsel();
					s.getSeinSpiel().getDerRundenverwalter().initGui();
				} else if (s.getSeinSpiel().getSpielerListe().get(s.getSeinSpiel().getSpielerListe().size() - 1) != s) {
					s.getSeinSpiel().getDerRundenverwalter().initGui();
				}
				
			}

		}
	}


	
	
}
