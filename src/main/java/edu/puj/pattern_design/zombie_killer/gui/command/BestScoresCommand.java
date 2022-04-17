package edu.puj.pattern_design.zombie_killer.gui.command;

import edu.puj.pattern_design.zombie_killer.gui.ZombieKillerGUI;
import edu.puj.pattern_design.zombie_killer.gui.panel.ScoresPanel;
import edu.puj.pattern_design.zombie_killer.service.camp.SurvivorCamp;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.io.IOException;

@Slf4j
public class BestScoresCommand implements Command {

    @Override
    public void execute(ZombieKillerGUI zombieKillerGUI) {
        try {
            ScoresPanel scoresPanel = ScoresPanel.getPanel();
            scoresPanel.setPrincipal(zombieKillerGUI);
            zombieKillerGUI.setScoresPanel(scoresPanel);
            SurvivorCamp camp = zombieKillerGUI.getCamp();

            if (camp == null) {
                camp = new SurvivorCamp();
                zombieKillerGUI.setCamp(camp);
            }

            camp.loadScores();
            zombieKillerGUI.mostrarPuntajes();
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage(), e);
            JOptionPane.showMessageDialog(null, "Hubo un error al guardar los ultimos puntajes.");
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            JOptionPane.showMessageDialog(null, "No se han encontrado puntajes anteriores.");
        }
    }

}
