package edu.puj.pattern_design.zombie_killer.gui.command;

import edu.puj.pattern_design.zombie_killer.gui.ZombieKillerGUI;
import edu.puj.pattern_design.zombie_killer.gui.panel.CreditsPanel;

public class CreditsCommand implements Command {

    @Override
    public void execute(ZombieKillerGUI zombieKillerGUI) {
        CreditsPanel creditsPanel = CreditsPanel.getPanel();
        creditsPanel.setPrincipal(zombieKillerGUI);
        zombieKillerGUI.setCreditsPanel(creditsPanel);
        zombieKillerGUI.mostrarCreditos();
    }

}
