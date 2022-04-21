package edu.puj.pattern_design.zombie_killer.gui.panel;

import edu.puj.pattern_design.zombie_killer.gui.ZombieKillerGUI;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

@Getter
@Setter
public class HowToPlayPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final String SALIR = "Salir";
    private final JButton butSalir;
    private ZombieKillerGUI principal;

    private static HowToPlayPanel panelSingleton;

    public HowToPlayPanel() {
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(50, 0, 20, 0));
        FunFactsPanel panelDatosC = new FunFactsPanel();
        WeaponsPanel weaponsPanel = new WeaponsPanel();
        butSalir = new JButton();
        configurarBoton(butSalir, getClass().getResource("/img/Palabras/volver.png"), SALIR);
        butSalir.setActionCommand(SALIR);
        JPanel aux = new JPanel();
        aux.setLayout(new BorderLayout());
        aux.add(panelDatosC, BorderLayout.NORTH);
        aux.add(weaponsPanel, BorderLayout.CENTER);
        this.add(aux);
        add(butSalir, BorderLayout.SOUTH);
    }

    public static HowToPlayPanel getPanel() {
        if (panelSingleton == null) {
            panelSingleton = new HowToPlayPanel();
        }

        return panelSingleton;
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
                principal.showHowToPlay();
            }
        });
    }
}
