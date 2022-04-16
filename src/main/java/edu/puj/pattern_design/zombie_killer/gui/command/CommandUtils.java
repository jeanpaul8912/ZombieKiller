package edu.puj.pattern_design.zombie_killer.gui.command;

import edu.puj.pattern_design.zombie_killer.gui.ZombieKillerGUI;
import edu.puj.pattern_design.zombie_killer.gui.CursorObjectPool;
import edu.puj.pattern_design.zombie_killer.gui.panel.PanelCamp;
import edu.puj.pattern_design.zombie_killer.service.camp.SurvivorCamp;

import java.awt.*;

public final class CommandUtils {

    private CommandUtils() {

    }

    public static void startGame(ZombieKillerGUI zombieKillerGUI) {
        PanelCamp panelCamp = PanelCamp.getPanel();
        panelCamp.setPrincipal(zombieKillerGUI);
        zombieKillerGUI.setPanelCampo(panelCamp);
        SurvivorCamp campo = zombieKillerGUI.getCamp();

        if (campo == null) {
            SurvivorCamp survivorCamp = new SurvivorCamp();
            zombieKillerGUI.setCamp(survivorCamp);
        }

        Cursor remingtonCursor = CursorObjectPool.getCursor("/img/Fondo/mira1.png");
        zombieKillerGUI.setRemingtonCursor(remingtonCursor);

        Cursor knifeCursor = CursorObjectPool.getCursor("/img/Fondo/Cuchillo.png");
        zombieKillerGUI.setKnifeCursor(knifeCursor);
    }

}
