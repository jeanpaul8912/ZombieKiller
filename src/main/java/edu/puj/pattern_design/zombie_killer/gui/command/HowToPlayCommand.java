package edu.puj.pattern_design.zombie_killer.gui.command;

import edu.puj.pattern_design.zombie_killer.gui.ZombieKillerGUI;
import edu.puj.pattern_design.zombie_killer.gui.panel.HowToPlayPanel;

public class HowToPlayCommand implements Command {

    @Override
    public void execute(ZombieKillerGUI zombieKillerGUI) {
        HowToPlayPanel howToPlayPanel = HowToPlayPanel.getPanel();
        howToPlayPanel.setPrincipal(zombieKillerGUI);
        zombieKillerGUI.setHowToPlayPanel(howToPlayPanel);
        zombieKillerGUI.showHowToPlay();
    }

}
