package interfaz.commands;

import interfaz.InterfazZombieKiller;

public class StartGameCommand implements Command {

	@Override
	public void execute(InterfazZombieKiller interfazZombieKiller) {
		CommandUtils.startGame(interfazZombieKiller);
		interfazZombieKiller.iniciarNuevaPartida();
	}

}
