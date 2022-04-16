package edu.puj.pattern_design.zombie_killer.gui.command;

import edu.puj.pattern_design.zombie_killer.gui.ZombieKillerGUI;
import edu.puj.pattern_design.zombie_killer.gui.panel.PanelCreditos;

public class CreditsCommand implements Command {

    @Override
    public void execute(ZombieKillerGUI zombieKillerGUI) {
        PanelCreditos panelCreditos = PanelCreditos.getPanel();
        panelCreditos.setPrincipal(zombieKillerGUI);
        zombieKillerGUI.setPanelCreditos(panelCreditos);
        zombieKillerGUI.mostrarCreditos();
    }

}
