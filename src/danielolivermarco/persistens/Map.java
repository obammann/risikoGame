package danielolivermarco.persistens;

import java.awt.Color;
import java.io.*;
import java.util.ArrayList;

import danielolivermarco.datenhaltung.*;
import danielolivermarco.cui.*;
import danielolivermarco.anwendungslogik.*;
/**
 * Die Klasse Map.
 * @author oliverbammann
 *
 */
public class Map implements Serializable{
	/**
	 * @param Ein Objektarray vom Typ Kontinent fuer alle Kontinente.
	 */
	private Kontinent[] kontinente;
	/**
	 * @param Eine Arraylist, in der alle Laender gespeichert werden.
	 */
	private ArrayList<Land> laender = new ArrayList<Land>();
	

	public Map() {
		
	}
	
	/**
	 * Es werden die Laendernummer, der Name, die Farbe, Nachbarlaenderanzahl und
	 * die Nachbarlaender aus einer Textdatei geladen. Anschliessend wird in der
	 * Arraylist laender das Land gespeichert. Im letzten Schritt werden noch jedem Land
	 * seine Nachbarlaender zugeordnet.
	 * @throws IOException
	 */
	public void ladeLaender() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("res/laenderMitNr.txt"));
//		Anfang der Textdatei wird markiert, diese Markierung wird nach 3000 Zeichen verworfen
		br.mark(3000);
		String zeile = br.readLine();
//		Laender werden erstellt
		while (zeile != null) {
//			Laendernummer wird in einem INT gespeichert
			int laenderNr = Integer.parseInt(zeile);
			
//			Name des Landes
			String name = br.readLine();
			
//			Farbe des Landes wird in RGB Werten ausgelesen und zuerst in einem String Array gespeichert
			String[] rgb = new String[3];
			rgb = br.readLine().split(",");
			
//			die 3 RGB Werte werden in 3 Integer gespeichert
			int r = Integer.parseInt(rgb[0]);
			int g = Integer.parseInt(rgb[1]);
			int b = Integer.parseInt(rgb[2]);
			
//			Anzahl der Nachbarlaender wird gespeichert
			zeile = br.readLine();
			int nachbarLaenderAnz = Integer.parseInt(zeile);
			
//			eine Zeile Leerlauf, da noch nicht alle Laender erstellt sind (diese Zeile wird in der naechsten while-Schleife genutzt
			zeile = br.readLine();
			
//			laendernummer des naechsten Landes...
			zeile = br.readLine();
			
//			Land wird erstellt
			laender.add(new Land(name, laenderNr, nachbarLaenderAnz, new Color(r, g, b)));
		}
//		springt zurueck zur Markierung
		br.reset();
		zeile = br.readLine();
//		Nachbarlaender werden den entsprechenden Laendern zugeordnet
		while (zeile != null) {
			Land[] nachbarn;
			String[] nachbarNamen;
			String land = br.readLine();
			
//			Leerlauf wegen Farbwerten
			zeile = br.readLine();
			
//			Anzahl der Nachbarlaender
			zeile = br.readLine();
			int nachbarLaenderAnz = Integer.parseInt(zeile);
			
//			Array vom Typ Land wird erstellt
			nachbarn = new Land[nachbarLaenderAnz];
			
//			Nachbarlaender werden in Stringarray gespeichert
			nachbarNamen = new String[nachbarLaenderAnz];
			nachbarNamen = br.readLine().split(",");
			
//			Nachbarn werden den Laendern zugeordnet
			for (int i=0;i<nachbarNamen.length;i++) {
				for (int j=0;j<laender.size();j++) {
					if (laender.get(j).getName().equals(nachbarNamen[i])) {
						nachbarn[i] = laender.get(j);
					}
				}
			}
			for (Land l:laender) {
				if (l.getName().equals(land)) {
					l.setNachbarn(nachbarn);
				}
			}
			zeile = br.readLine();
		}
		br.close();
	}
	
	/**
	 * Kontinente werden geladen. Gleiche Funktionsweise wie bei ladeLaender.
	 * @throws IOException
	 */
	public void ladeKontinente() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("res/kontinente.txt"));
		String zeile = br.readLine();
		int kontinenteIndex = 0;
		int anzKontinente = Integer.parseInt(zeile);
		kontinente = new Kontinent[anzKontinente];
		zeile = br.readLine();
		while (zeile != null) {
			int anzLaender = Integer.parseInt(zeile);
			String[] kontinentLaender = new String[anzLaender];
			Land[] kontLaender = new Land[anzLaender];
			String name = br.readLine();
			kontinente[kontinenteIndex] = new Kontinent(name, anzLaender);
			kontinentLaender = br.readLine().split(",");
			for (int i=0;i<kontinentLaender.length;i++) {
				for (int j=0;j<laender.size();j++) {
					if (laender.get(j).getName().equals(kontinentLaender[i])) {
						kontLaender[i] = laender.get(j);
					}
				}
			}
			kontinente[kontinenteIndex].setLaender(kontLaender);
			kontinenteIndex++;
			zeile = br.readLine();
		}
		br.close();
	}
	/**
	 * 
	 * @return gibt ein Array mit allen Kontinenten zurueck 
	 */
	public Kontinent[] getKontinente() {
		return this.kontinente;
	}
	/**
	 * 
	 * @return gibt eine ArrayList mit allen Laendern zurueck
	 */
	public ArrayList<Land> getLaender() {
		return laender;
	}
}