package edu.puj.pattern_design.zombie_killer.gui.panel;

import edu.puj.pattern_design.zombie_killer.gui.ZombieKillerGUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

public class PanelComoJugar extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static final String SALIR = "Salir";
    private final JButton butSalir;
    private ZombieKillerGUI principal;

    private static PanelComoJugar panelSingleton;

    public PanelComoJugar() {
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(50, 0, 20, 0));
        PanelDatosCuriosos panelDatosC = new PanelDatosCuriosos();
        PanelArmas panelArmas = new PanelArmas(principal);
        butSalir = new JButton();
        configurarBoton(butSalir, getClass().getResource("/img/Palabras/volver.png"), SALIR);
        butSalir.setActionCommand(SALIR);
        JPanel aux = new JPanel();
        aux.setLayout(new BorderLayout());
        aux.add(panelDatosC, BorderLayout.NORTH);
        aux.add(panelArmas, BorderLayout.CENTER);
        this.add(aux);
        add(butSalir, BorderLayout.SOUTH);
    }

    public static PanelComoJugar getPanel() {
        if (panelSingleton == null) {
            panelSingleton = new PanelComoJugar();
        }

        return panelSingleton;
    }

    public void setPrincipal(ZombieKillerGUI zombieKillerGUI) {
        principal = zombieKillerGUI;
    }

    public void configurarBoton(JButton aEditar, URL rutaImagen, String comando) {
        aEditar.setBorder(null);
        aEditar.setFocusable(false);
        aEditar.setContentAreaFilled(false);
        aEditar.setActionCommand(comando);
        ImageIcon letras = new ImageIcon(rutaImagen);
        aEditar.setIcon(letras);
        aEditar.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ImageIcon iI = new ImageIcon(getClass().getResource("/img/Palabras/volver.png"));
                butSalir.setIcon(iI);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                ImageIcon iI = new ImageIcon(getClass().getResource("/img/Palabras/volver_p.png"));
                butSalir.setIcon(iI);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                principal.mostrarComoJugar();
            }
        });
    }
}
