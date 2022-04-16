package edu.puj.pattern_design.zombie_killer.gui.panel;

import edu.puj.pattern_design.zombie_killer.gui.ZombieKillerGUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.KNIFE_DAMAGE;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.M1911_DAMAGE;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.M1911_INITIAL_BACKWARD;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.M1911_INITIAL_RECHARGE_TIME;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.REMINGTON_DAMAGE;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.REMINGTON_INITIAL_BACKWARD;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.REMINGTON_INITIAL_RECHARGE_TIME;

public class PanelArmas extends JPanel {

    private static final long serialVersionUID = 1L;

    private final JLabel[] labArmas;

    private final PanelAtributos[] panelAtributos;

    private int aMostrar = 0;

    public PanelArmas(ZombieKillerGUI zombieKillerGUI) {
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());
        labArmas = new JLabel[4];
        panelAtributos = new PanelAtributos[4];
        JLabel infoImagen = new JLabel("Click en la imagen para conocer m�s armas.");
        infoImagen.setFont(new Font("Agency FB", Font.BOLD, 20));
        infoImagen.setForeground(Color.WHITE);
        infoImagen.setBorder(new EmptyBorder(0, 80, 0, 0));

        Image perfil;
        Icon iconoEscalado;
        perfil = new ImageIcon(getClass().getResource("/img/Fondo/perfilRemington.png")).getImage().getScaledInstance(300, 300, 300);
        iconoEscalado = new ImageIcon(perfil);
        labArmas[0] = new JLabel(iconoEscalado);
        labArmas[0].setToolTipText("Fusiles de cerrojo fabricados para emplear munici�n de diversos calibres.");
        perfil = new ImageIcon(getClass().getResource("/img/Fondo/perfilM1911.png")).getImage().getScaledInstance(300, 200, 300);
        iconoEscalado = new ImageIcon(perfil);
        labArmas[1] = new JLabel(iconoEscalado);
        labArmas[1].setToolTipText("Arma de fuego de repetici�n que se caracteriza por llevar la munici�n en un tambor.");
        perfil = new ImageIcon(getClass().getResource("/img/Fondo/perfilGranada.png")).getImage().getScaledInstance(300, 200, 300);
        iconoEscalado = new ImageIcon(perfil);
        labArmas[2] = new JLabel(iconoEscalado);
        labArmas[2].setToolTipText("Proyectil peque�o que contiene explosivos o gas en su interior y que se lanza a mano.");
        perfil = new ImageIcon(getClass().getResource("/img/Fondo/perfilCuchillo.png")).getImage().getScaledInstance(300, 200, 300);
        iconoEscalado = new ImageIcon(perfil);
        labArmas[3] = new JLabel(iconoEscalado);
        labArmas[3].setToolTipText("Utensilio para cortar tiene una hoja de metal alargada y afilada por un solo lado.");

        inicializarAtributos();
        add(infoImagen, BorderLayout.NORTH);
        add(labArmas[1], BorderLayout.CENTER);
        add(panelAtributos[1], BorderLayout.SOUTH);
        add(labArmas[2], BorderLayout.CENTER);
        add(panelAtributos[2], BorderLayout.SOUTH);
        add(labArmas[3], BorderLayout.CENTER);
        add(panelAtributos[3], BorderLayout.SOUTH);
        add(labArmas[0], BorderLayout.CENTER);
        add(panelAtributos[0], BorderLayout.SOUTH);


        for (int i = 0; i < labArmas.length; i++) {
            labArmas[i].addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent event) {
                    verArma();
                }
            });
        }
    }

    public void inicializarAtributos() {
        String[] atributosRemington = {
                "Danio inicial",
                "Retroceso inicial",
                "Tiempo de carga inicial"
        };
        int[] valoresRemington = {
                REMINGTON_DAMAGE,
                REMINGTON_INITIAL_BACKWARD,
                REMINGTON_INITIAL_RECHARGE_TIME
        };
        panelAtributos[0] = new PanelAtributos(atributosRemington, valoresRemington);

        String[] atributosM1911 = {
                "Danio",
                "Retroceso",
                "Tiempo de Carga"
        };
        int[] valoresM1911 = {
                M1911_DAMAGE,
                M1911_INITIAL_BACKWARD,
                M1911_INITIAL_RECHARGE_TIME
        };
        panelAtributos[1] = new PanelAtributos(atributosM1911, valoresM1911);

        String[] atributosGrenade = {"Danio"};
        int[] valoresGrenade = {REMINGTON_DAMAGE};

        panelAtributos[2] = new PanelAtributos(atributosGrenade, valoresGrenade);

        String[] atributosCuchillo = {"Danio"};
        int[] valoresCuchillo = {KNIFE_DAMAGE};
        panelAtributos[3] = new PanelAtributos(atributosCuchillo, valoresCuchillo);
    }

    private void verArma() {

        labArmas[aMostrar].setVisible(false);
        panelAtributos[aMostrar].setVisible(false);
        if (aMostrar == 3) {
            aMostrar = -1;
        }
        aMostrar++;
        labArmas[aMostrar].setVisible(true);
        panelAtributos[aMostrar].setVisible(true);
        add(labArmas[aMostrar], BorderLayout.CENTER);
        add(panelAtributos[aMostrar], BorderLayout.SOUTH);
    }

    public int getaMostrar() {
        return aMostrar;
    }

    public void setaMostrar(int aMostrar) {
        this.aMostrar = aMostrar;
    }
}
