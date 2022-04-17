package edu.puj.pattern_design.zombie_killer.gui.panel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.DAMAGE;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.GRENADE_DAMAGE;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.INITIAL_BACKWARD;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.INITIAL_RECHARGE_TIME;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.REMINGTON_INITIAL_BACKWARD;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.REMINGTON_INITIAL_RECHARGE_TIME;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.LENTITUD1;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.SALUD_BOSS;

public class AttributesPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    // La cantidad de attributes y values debe ser la misma
    public AttributesPanel(String[] attributes, int[] values) {
        setBackground(Color.BLACK);
        setLayout(new GridLayout(values.length, 2, 20, 20));
        this.setBorder(new EmptyBorder(50, 30, 20, 220));
        JLabel[] labAtributos = new JLabel[attributes.length];
        JProgressBar[] barValores = new JProgressBar[values.length];

        Font letra = new Font("Agency FB", Font.BOLD, 24);
        for (int i = 0; i < values.length; i++) {

            labAtributos[i] = new JLabel(attributes[i]);
            labAtributos[i].setHorizontalAlignment(SwingConstants.RIGHT);
            labAtributos[i].setFont(letra);
            labAtributos[i].setForeground(Color.white);
            add(labAtributos[i]);

            barValores[i] = new JProgressBar();
            barValores[i].setForeground(Color.RED);
            barValores[i].setBackground(Color.WHITE);
            add(barValores[i]);

            switch (attributes[i]) {
                case DAMAGE:
                    barValores[i].setMaximum(GRENADE_DAMAGE);
                    break;
                case "Salud":
                    barValores[i].setMaximum(SALUD_BOSS);
                    break;
                case "Lentitud":
                    barValores[i].setMaximum(LENTITUD1);
                    break;
                case INITIAL_BACKWARD:
                    barValores[i].setMaximum(REMINGTON_INITIAL_BACKWARD + 100);
                    break;
                case INITIAL_RECHARGE_TIME:
                    barValores[i].setMaximum(REMINGTON_INITIAL_RECHARGE_TIME + 200);
                    break;
            }

            barValores[i].setValue(values[i]);
        }
    }
}
