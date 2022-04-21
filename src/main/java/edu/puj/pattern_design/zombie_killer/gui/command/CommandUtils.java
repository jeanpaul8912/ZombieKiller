package edu.puj.pattern_design.zombie_killer.gui.command;

import edu.puj.pattern_design.zombie_killer.gui.ZombieKillerGUI;
import edu.puj.pattern_design.zombie_killer.gui.CursorObjectPool;
import edu.puj.pattern_design.zombie_killer.gui.panel.SurvivorCampPanel;
import edu.puj.pattern_design.zombie_killer.service.camp.SurvivorCamp;
import edu.puj.pattern_design.zombie_killer.service.camp.impl.SurvivorCampImpl;

import java.awt.*;

public final class CommandUtils {

    private CommandUtils() {

    }

    public static void startGame(ZombieKillerGUI zombieKillerGUI) {
        SurvivorCampPanel survivorCampPanel = SurvivorCampPanel.getPanel();
        survivorCampPanel.setPrincipal(zombieKillerGUI);
        zombieKillerGUI.setSurvivorCampoPanel(survivorCampPanel);
        SurvivorCamp campo = zombieKillerGUI.getCamp();

        if (campo == null) {
            SurvivorCamp survivorCamp = new SurvivorCampImpl();
            zombieKillerGUI.setCamp(survivorCamp);
        }

        Cursor remingtonCursor = CursorObjectPool.getCursor("/img/Fondo/mira1.png");
        zombieKillerGUI.setRemingtonCursor(remingtonCursor);

        Cursor knifeCursor = CursorObjectPool.getCursor("/img/Fondo/Cuchillo.png");
        zombieKillerGUI.setKnifeCursor(knifeCursor);
    }

}
