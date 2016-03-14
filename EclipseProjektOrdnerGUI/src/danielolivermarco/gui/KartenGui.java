package danielolivermarco.gui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;




import danielolivermarco.persistens.Map;
import danielolivermarco.datenhaltung.*;

/**
 * 
 * Die Darstellung der Karte in der GUI
 */
public class KartenGui extends JFrame implements Serializable {
	
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image mapImage;
	private BufferedImage mapBufImg;
	private Map map;
	private Spieler spieler;
	private Dimension dim;
	private int breiteInfoFeld;
	  
	private JButton phaseBeendenButton = new JButton("Phase Beenden");
	 
	/**
	 * Karte wird erstellt mit entsprechenden Buttons und der Hintergrundkarte (BufferedImage).
	 * Ein Mouseadapter gibt bei linkem Mausklick auf ein Land Infos aus.
	 * @param m Die Map, die zum Initialisieren der "map"-Variable dient.
	 * @param s Der Spieler, dem diese Karte gehoert.
	 */
	public KartenGui(Map m, Spieler s){
		
		this.map = m;
		this.spieler = s;
		breiteInfoFeld = 200;
  
		System.out.println("Lade Hintergrund Karte");
		
		//Aufloesung ermitteln
		Dimension aufloesung = Toolkit.getDefaultToolkit().getScreenSize();
		int mapHoehe = (int) aufloesung.getHeight()-30;
		//Bildgroesse wird uber die Aufloesungshoehe und das Seitenverhaeltnis (0,77343) bestimmt
		dim = new Dimension((int) (mapHoehe*0.77343), mapHoehe);
		
		Dimension windowSize = new Dimension(dim.width+breiteInfoFeld,dim.height);
		
		//Gui schliessen, stoppt das programm 
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.mapImage = (getToolkit().getImage(getClass().getResource("/map.png")));
		this.mapBufImg = new BufferedImage(dim.width+breiteInfoFeld, dim.height, BufferedImage.TYPE_INT_RGB);
		this.mapBufImg.getGraphics().drawImage(new ImageIcon(getClass().getResource("/map_color.png")).getImage(), breiteInfoFeld, 0, dim.width, dim.height, this);
		this.setSize(windowSize);
		this.setTitle(this.spieler.getName());
		
		this.spielerButtonsZeichnen();
		
		
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
		// Neuer MouseAdapter fuer die geklickten Laender
		this.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent me) {
				// X und Y Position des Mauszeigers
				int x = (int) me.getX();
				int y = (int) me.getY();
				
				// Farbe des BufferedImage an der Position des Mauszeigers (x, y)
				Color col = new Color(getBuffImage().getRGB(x,y));
				
				//Farbwerte werden mit jedem Land verglichen
				for (int i=0; i<map.getLaender().size();i++) {
					if(map.getLaender().get(i).getColor().equals(col)) {
						System.err.println(map.getLaender().get(i).getName() + " ist geklickt!");
						spieler.getPersoenlichesInterfaceGUI().landInfoByClick(map.getLaender().get(i));
					}
				}
			}
		});
		
		phaseBeendenButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				spieler.getSeinSpiel().getLogikAnspracheGui().phaseBeenden(spieler);
			}
		});
	}
	
	/**
	 * Diese Methode erstellt die Infobuttons auf der linken Seite des Fensters:
	 * Laender und Kontinente der Spieler und allgemeine Infos des Spielers dem das Fenster gehoert.
	 * Werden die Buttons geklickt, werden Methoden in der Klasse PopupGui ausgefuehrt. Es oeffnen
	 * sich die jeweiligen Fenster.
	 */
	private void spielerButtonsZeichnen() {
		int spielerAnzahl = spieler.getSeinSpiel().getSpielerListe().size();
		JPanel buttonContainer = new JPanel();
		BoxLayout dieBox = new BoxLayout(buttonContainer,BoxLayout.Y_AXIS);
		buttonContainer.setLayout(dieBox);
		
		// Buttons um den Besitz der anderen Spieler anzeigen zu lassen
		buttonContainer.add(new JLabel("Besitz anzeigen von: "));
		for (int i=0; i<spielerAnzahl;i++) {
			buttonContainer.add(new JButton(spieler.getSeinSpiel().getSpielerListe().get(i).getName()));
		}
		for (int y = 1; y < spielerAnzahl + 1; y++) {
			final JButton aktuellerButton =(JButton) buttonContainer.getComponent(y);
			aktuellerButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					int spielerAnzeigen = 0;
					for (int y = 0; y < spieler.getSeinSpiel().getSpielerListe().size(); y++) {
						if (aktuellerButton.getText() == spieler.getSeinSpiel().getSpielerListe().get(y).getName()) {
							spielerAnzeigen = y;
						}
					}
					PopupGUI spielerLaenderAnzeige = new PopupGUI(spieler.getSeinSpiel());
					spielerLaenderAnzeige.laenderVomSpielerAnzeigen(spielerAnzeigen, spieler);
				}
			});
		}
		
		buttonContainer.add(new JLabel("Deine Sachen:"));
		
		JButton missionAnzeigen = new JButton("Meine Mission");
		missionAnzeigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PopupGUI missionsPopup = new PopupGUI();
				missionsPopup.missionsAnzeige(spieler);
			}
		});
		buttonContainer.add(missionAnzeigen);
		
		JButton geldAnzeigen = new JButton("Mein Geldbeutel");
		geldAnzeigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PopupGUI missionsPopup = new PopupGUI();
				missionsPopup.geldAnzeigen(spieler);
			}
		});
		buttonContainer.add(geldAnzeigen);
		
		JButton einheitenPoolAnzeigen = new JButton("Mein EinheitenPool");
		einheitenPoolAnzeigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PopupGUI missionsPopup = new PopupGUI();
				missionsPopup.einheitenPoolAnzeigen(spieler);
			}
		});
		buttonContainer.add(einheitenPoolAnzeigen);
		
		// Hier muss noch: Mein aktuelles Gold, Mein EinheitenPool & Meine Aktuelle Aufgabe
		
		buttonContainer.add(phaseBeendenButton);
		
		buttonContainer.setVisible(true);
		this.add(buttonContainer);
	}

	/**
	 * 
	 * @return Das BufferedImage.
	 */
	public BufferedImage getBuffImage(){
		return this.mapBufImg;
	}
	
	/**
	 * Paint Methode wird ueberschrieben, "mapImage" wird gezeichnet
	 */
	public void paint(Graphics g){
		 Graphics2D g2 = (Graphics2D) g;
	        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
	          RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
	          RenderingHints.VALUE_RENDER_QUALITY);
		
		super.paint(g);
		g.drawImage(mapImage, breiteInfoFeld, 0, dim.width, dim.height, this);
	}

}
