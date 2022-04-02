package interfaz.commands;

import interfaz.InterfazZombieKiller;

public class SaveGameCommand implements Command {

	@Override
	public void execute(InterfazZombieKiller interfazZombieKiller) {
		interfazZombieKiller.guardarJuego();
	}

}
