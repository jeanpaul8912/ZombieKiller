package interfaz.commands;

import java.awt.Cursor;

import interfaz.CursorObjectPool;
import interfaz.InterfazZombieKiller;
import interfaz.PanelCamp;
import mundo.SurvivorCamp;

public final class CommandUtils {

	private CommandUtils() {
		
	}
	
	public static void startGame(InterfazZombieKiller interfazZombieKiller) {
		PanelCamp panelCamp = PanelCamp.getPanel();
		panelCamp.setPrincipal(interfazZombieKiller);
		interfazZombieKiller.setPanelCampo(panelCamp);
		SurvivorCamp campo = interfazZombieKiller.getCampo();
		if(campo == null) {
			SurvivorCamp survivorCamp = new SurvivorCamp();
			interfazZombieKiller.setCampo(survivorCamp);
		}
		Cursor remingtonCursor = CursorObjectPool.getCursor("/img/Fondo/mira1.png");
		interfazZombieKiller.setMiraRemington(remingtonCursor);

		Cursor knifeCursor = CursorObjectPool.getCursor("/img/Fondo/Cuchillo.png");
		interfazZombieKiller.setCursorCuchillo(knifeCursor);
	}
	
}
