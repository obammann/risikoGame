package danielolivermarco.anwendungslogik;



import java.io.Serializable;

import danielolivermarco.datenhaltung.*;

/**
 * 
 * Regelt das Aufloesen einer Armee in einem Land.
 *
 */
public class ArmeeAufloeser implements Serializable {
	
	public ArmeeAufloeser() {
	}
	/**
	 * Nimmt eine Armee und ein Land entgegen um die Armee in die im Land stationierte Armee einzugliedern
	 * 
	 * @param a Erwartet eine Armee die in das Land einfliessen soll
	 * @param l	In der bereits stationierten Armee dieses Landes fliesst die aufzuloesene Armee ein
	 */
	public void init(Armee a, Land l) {
		for (int i = 0; i < a.getArmee().size(); i++) {
			l.getSpielerArmee().armeeZuwachs(a.getArmee().get(i));
		}
	}
}
