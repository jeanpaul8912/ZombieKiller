package interfaz.commands;

import interfaz.InterfazZombieKiller;

public class ContinueGameCommand implements Command {

	@Override
	public void execute(InterfazZombieKiller interfazZombieKiller) {
		interfazZombieKiller.pausarJuego();
	}

}
