package edu.puj.pattern_design.zombie_killer.gui.panel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class FunFactsPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JLabel title;
    private JPanel panelIzquierdo;
    private JPanel panelDerecho;
    private final Font letraTitulos = new Font("Agency FB", Font.BOLD, 30);

    public FunFactsPanel() {
        setLayout(new GridLayout(1, 2, 50, 0));
        this.setBorder(new EmptyBorder(0, 80, 0, 0));
        panelIzquierdo();
        panelDerecho();
        setBackground(Color.BLACK);
        this.add(panelIzquierdo);
        this.add(panelDerecho);
    }

    public void panelIzquierdo() {
        panelIzquierdo = new JPanel();
        JLabel[] information = new JLabel[4];
        panelIzquierdo.setLayout(
                new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        title = new JLabel("Datos Curiosos");
        title.setFont(letraTitulos);
        title.setForeground(Color.WHITE);
        information[0] = new JLabel(" No siempre matar de tiro a la cabeza da mas puntos.");
        information[1] = new JLabel(" Que el �ltimo tiro sea el que le vuele los sesos!");
        information[2] = new JLabel(" Puedes acuchillar a un enemigo en posicion de ataque, solo dispara...");
        information[3] = new JLabel(" El danio de la escopeta varia segun la distancia, usala sabiamente");
        panelIzquierdo.add(title);
        asignarTipoLetra(information, panelIzquierdo);
        panelIzquierdo.setBackground(Color.BLACK);
    }

    public void panelDerecho() {
        panelDerecho = new JPanel();
        JLabel[] instrucciones = new JLabel[5];
        panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS));
        title = new JLabel("Controles");
        title.setFont(letraTitulos);
        title.setForeground(Color.WHITE);
        instrucciones[0] = new JLabel("Presiona \"SHIFT\" para ver las estadisticas de la partida");
        instrucciones[1] = new JLabel("Presiona \"C\" para cambiar de arma");
        instrucciones[2] = new JLabel("Presiona \"SPACE\" para lanzar granada");
        instrucciones[3] = new JLabel("Presiona \"Click Izquierdo\" para disparar el arma equipada");
        instrucciones[4] = new JLabel("Presiona \"Click Derecho\" para recargar el arma equipada");

        panelDerecho.add(title);
        asignarTipoLetra(instrucciones, panelDerecho);
        panelDerecho.setBackground(Color.BLACK);
    }

    public void asignarTipoLetra(JLabel[] label, JPanel panel) {
        for (int i = 0; i < label.length; i++) {
            label[i].setFont(new Font("Agency FB", Font.ROMAN_BASELINE, 18));
            label[i].setForeground(Color.white);
            panel.add(label[i]);
        }
    }
}