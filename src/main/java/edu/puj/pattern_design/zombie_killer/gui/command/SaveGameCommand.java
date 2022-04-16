package edu.puj.pattern_design.zombie_killer.gui.command;

import edu.puj.pattern_design.zombie_killer.gui.ZombieKillerGUI;

public class SaveGameCommand implements Command {

    @Override
    public void execute(ZombieKillerGUI zombieKillerGUI) {
        zombieKillerGUI.guardarJuego();
    }

}
