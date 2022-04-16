package edu.puj.pattern_design.zombie_killer.gui.command;

import edu.puj.pattern_design.zombie_killer.gui.ZombieKillerGUI;

public class LoadGameCommand implements Command {

    @Override
    public void execute(ZombieKillerGUI zombieKillerGUI) {
        CommandUtils.startGame(zombieKillerGUI);
        zombieKillerGUI.cargarJuego();
    }

}
