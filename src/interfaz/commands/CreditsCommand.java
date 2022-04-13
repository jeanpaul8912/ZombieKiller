package interfaz.commands;

import interfaz.InterfazZombieKiller;
import interfaz.panels.PanelCreditos;

public class CreditsCommand implements Command {

	@Override
	public void execute(InterfazZombieKiller interfazZombieKiller) {
		PanelCreditos panelCreditos = PanelCreditos.getPanel();
		panelCreditos.setPrincipal(interfazZombieKiller);
		interfazZombieKiller.setPanelCreditos(panelCreditos);
		interfazZombieKiller.mostrarCreditos();
	}

}
