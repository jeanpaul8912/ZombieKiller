package interfaz.commands;

import interfaz.InterfazZombieKiller;
import interfaz.panels.PanelComoJugar;

public class HowToPlayCommand implements Command {

	@Override
	public void execute(InterfazZombieKiller interfazZombieKiller) {
		PanelComoJugar panelComoJugar = PanelComoJugar.getPanel();
		panelComoJugar.setPrincipal(interfazZombieKiller);
		interfazZombieKiller.setPanelComoJugar(panelComoJugar);
		interfazZombieKiller.mostrarComoJugar();
	}

}
