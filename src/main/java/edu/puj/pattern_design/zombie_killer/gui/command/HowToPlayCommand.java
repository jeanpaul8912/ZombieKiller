package edu.puj.pattern_design.zombie_killer.gui.command;

import edu.puj.pattern_design.zombie_killer.gui.ZombieKillerGUI;
import edu.puj.pattern_design.zombie_killer.gui.panel.PanelComoJugar;

public class HowToPlayCommand implements Command {

    @Override
    public void execute(ZombieKillerGUI zombieKillerGUI) {
        PanelComoJugar panelComoJugar = PanelComoJugar.getPanel();
        panelComoJugar.setPrincipal(zombieKillerGUI);
        zombieKillerGUI.setPanelComoJugar(panelComoJugar);
        zombieKillerGUI.mostrarComoJugar();
    }

}
