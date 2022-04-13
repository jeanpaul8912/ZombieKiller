package interfaz.commands;

import interfaz.InterfazZombieKiller;

public class LoadGameCommand implements Command {

	@Override
	public void execute(InterfazZombieKiller interfazZombieKiller) {
		CommandUtils.startGame(interfazZombieKiller);
		interfazZombieKiller.cargarJuego();
	}

}
