package danielolivermarco.cui;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import danielolivermarco.anwendungslogik.*;
import danielolivermarco.persistens.LadenSpeichernInterface;

/**
 * 
 * Klasse von der die CUI Version des Spiels gestartet wird.
 *
 */
public class SpielCUIClient implements Serializable {
/**
 * Main fuer die GUI Version des Spiels 
 * Fragt ob man ein bereits gespeichertes Spiel laden moechte,
 * wenn dies nicht der Fall ist wird ein neues Spiel und die benotigten Modi erstellt 
 * und initialisiert. 
 */
public static void main(String[] args) throws IOException {
	LadenSpeichernInterface laden = new LadenSpeichernInterface();
	
		if(laden.willLaden()){ 
			try (final ObjectInputStream ois = new ObjectInputStream(new FileInputStream("spielstandcui.ser"))){
				Spiel dasSpiel = (Spiel) ois.readObject();
				dasSpiel.getDerRundenverwalter().runde();
			}catch (final IOException e) {  }
			catch (final ClassNotFoundException p) {}
		}else{
		Spiel dasSpiel = new Spiel();
		Anfangsphase dieAnfangsphase = new Anfangsphase(dasSpiel);
		Rundenverwaltung derRundenverwalter = new Rundenverwaltung(dasSpiel);
		dasSpiel.einfuegen(dieAnfangsphase, derRundenverwalter);
		dasSpiel.initCUI();				// Spielerzahl einstellen, Spieler benennen, Missionen vergeben und Einheitenpool fuellen
		dieAnfangsphase.initCUI();			// Laender auswaehlen und restliche Einheiten verteilen
		derRundenverwalter.initCUI();
		}
	}
}
