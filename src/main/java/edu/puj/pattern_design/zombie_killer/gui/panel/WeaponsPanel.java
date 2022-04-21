package edu.puj.pattern_design.zombie_killer.gui.panel;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.DAMAGE;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.IMG_FONDO_PERFIL_CUCHILLO_PNG;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.IMG_FONDO_PERFIL_GRANADA_PNG;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.IMG_FONDO_PERFIL_M_1911_PNG;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.IMG_FONDO_PERFIL_REMINGTON_PNG;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.INITIAL_BACKWARD;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.INITIAL_DAMAGE;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.INITIAL_RECHARGE_TIME;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.KNIFE_DAMAGE;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.M1911_DAMAGE;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.M1911_INITIAL_BACKWARD;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.M1911_INITIAL_RECHARGE_TIME;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.REMINGTON_DAMAGE;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.REMINGTON_INITIAL_BACKWARD;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.REMINGTON_INITIAL_RECHARGE_TIME;

@Getter
@Setter
public class WeaponsPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private final JLabel[] labArmas;

    private final AttributesPanel[] attributesPanelAtributos;

    private int aMostrar = 0;

    public WeaponsPanel() {
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());
        labArmas = new JLabel[4];
        attributesPanelAtributos = new AttributesPanel[4];
        JLabel infoImagen = new JLabel("Click en la imagen para conocer más armas.");
        infoImagen.setFont(new Font("Agency FB", Font.BOLD, 20));
        infoImagen.setForeground(Color.WHITE);
        infoImagen.setBorder(new EmptyBorder(0, 80, 0, 0));

        Image perfil;
        Icon iconoEscalado;
        perfil = new ImageIcon(getClass().getResource(IMG_FONDO_PERFIL_REMINGTON_PNG)).getImage().getScaledInstance(300, 300, 300);
        iconoEscalado = new ImageIcon(perfil);
        labArmas[0] = new JLabel(iconoEscalado);
        labArmas[0].setToolTipText("Fusiles de cerrojo fabricados para emplear munición de diversos calibres.");
        perfil = new ImageIcon(getClass().getResource(IMG_FONDO_PERFIL_M_1911_PNG)).getImage().getScaledInstance(300, 200, 300);
        iconoEscalado = new ImageIcon(perfil);
        labArmas[1] = new JLabel(iconoEscalado);
        labArmas[1].setToolTipText("Arma de fuego de repetición que se caracteriza por llevar la munición en un tambor.");
        perfil = new ImageIcon(getClass().getResource(IMG_FONDO_PERFIL_GRANADA_PNG)).getImage().getScaledInstance(300, 200, 300);
        iconoEscalado = new ImageIcon(perfil);
        labArmas[2] = new JLabel(iconoEscalado);
        labArmas[2].setToolTipText("Proyectil pequeño que contiene explosivos o gas en su interior y que se lanza a mano.");
        perfil = new ImageIcon(getClass().getResource(IMG_FONDO_PERFIL_CUCHILLO_PNG)).getImage().getScaledInstance(300, 200, 300);
        iconoEscalado = new ImageIcon(perfil);
        labArmas[3] = new JLabel(iconoEscalado);
        labArmas[3].setToolTipText("Utensilio para cortar, tiene una hoja de metal alargada y afilada por un solo lado.");

        inicializarAtributos();
        add(infoImagen, BorderLayout.NORTH);
        add(labArmas[1], BorderLayout.CENTER);
        add(attributesPanelAtributos[1], BorderLayout.SOUTH);
        add(labArmas[2], BorderLayout.CENTER);
        add(attributesPanelAtributos[2], BorderLayout.SOUTH);
        add(labArmas[3], BorderLayout.CENTER);
        add(attributesPanelAtributos[3], BorderLayout.SOUTH);
        add(labArmas[0], BorderLayout.CENTER);
        add(attributesPanelAtributos[0], BorderLayout.SOUTH);


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
                INITIAL_DAMAGE,
                INITIAL_BACKWARD,
                INITIAL_RECHARGE_TIME
        };
        int[] valoresRemington = {
                REMINGTON_DAMAGE,
                REMINGTON_INITIAL_BACKWARD,
                REMINGTON_INITIAL_RECHARGE_TIME
        };
        attributesPanelAtributos[0] = new AttributesPanel(atributosRemington, valoresRemington);

        String[] atributosM1911 = {
                INITIAL_DAMAGE,
                INITIAL_BACKWARD,
                INITIAL_RECHARGE_TIME
        };
        int[] valoresM1911 = {
                M1911_DAMAGE,
                M1911_INITIAL_BACKWARD,
                M1911_INITIAL_RECHARGE_TIME
        };
        attributesPanelAtributos[1] = new AttributesPanel(atributosM1911, valoresM1911);

        String[] atributosGrenade = {DAMAGE};
        int[] valoresGrenade = {REMINGTON_DAMAGE};

        attributesPanelAtributos[2] = new AttributesPanel(atributosGrenade, valoresGrenade);

        String[] atributosCuchillo = {DAMAGE};
        int[] valoresCuchillo = {KNIFE_DAMAGE};
        attributesPanelAtributos[3] = new AttributesPanel(atributosCuchillo, valoresCuchillo);
    }

    private void verArma() {

        labArmas[aMostrar].setVisible(false);
        attributesPanelAtributos[aMostrar].setVisible(false);
        if (aMostrar == 3) {
            aMostrar = -1;
        }
        aMostrar++;
        labArmas[aMostrar].setVisible(true);
        attributesPanelAtributos[aMostrar].setVisible(true);
        add(labArmas[aMostrar], BorderLayout.CENTER);
        add(attributesPanelAtributos[aMostrar], BorderLayout.SOUTH);
    }

}
