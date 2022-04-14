package interfaz.commands;

import interfaz.CursorObjectPool;
import interfaz.InterfazZombieKiller;
import interfaz.panels.PanelCamp;
import mundo.camp.SurvivorCamp;

import java.awt.*;

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
