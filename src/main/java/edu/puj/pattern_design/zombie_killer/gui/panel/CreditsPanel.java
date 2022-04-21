package edu.puj.pattern_design.zombie_killer.gui.panel;

import edu.puj.pattern_design.zombie_killer.gui.ZombieKillerGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CreditsPanel extends JPanel implements KeyListener {

    private ZombieKillerGUI principal;

    private static CreditsPanel panelSingleton;

    public CreditsPanel() {
        addKeyListener(this);
        setFocusable(true);
        setBackground(Color.black);
        JLabel labCreditos = new JLabel("En proceso, presiona \"Esc\" para volver");
        labCreditos.setForeground(Color.white);
        add(labCreditos);
    }

    public static CreditsPanel getPanel() {
        if (panelSingleton == null) {
            panelSingleton = new CreditsPanel();
        }

        return panelSingleton;
    }

    public void setPrincipal(ZombieKillerGUI zombieKillerGUI) {
        principal = zombieKillerGUI;
    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE)
            principal.showCredits();
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }

}
