package interfaz.panels;

import interfaz.InterfazZombieKiller;
import mundo.weapons.whites.Knife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static mundo.constants.ZombieKillerConstants.M1911_DAMAGE;
import static mundo.constants.ZombieKillerConstants.M1911_INITIAL_BACKWARD;
import static mundo.constants.ZombieKillerConstants.M1911_INITIAL_RECHARGE_TIME;
import static mundo.constants.ZombieKillerConstants.REMINGTON_DAMAGE;
import static mundo.constants.ZombieKillerConstants.REMINGTON_INITIAL_BACKWARD;
import static mundo.constants.ZombieKillerConstants.REMINGTON_INITIAL_RECHARGE_TIME;

public class PanelArmas extends JPanel implements ActionListener {

    private static final String ANTERIOR = "a";

    private static final String POSTERIOR = "p";

    private final JLabel[] labArmas;

    private final PanelAtributos[] panelAtributos;

    private final InterfazZombieKiller principal;

    public PanelArmas(InterfazZombieKiller inter) {
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());
        principal = inter;

        labArmas = new JLabel[4];
        panelAtributos = new PanelAtributos[4];

        JButton butAnterior = new JButton("<");
        butAnterior.setActionCommand(ANTERIOR);
        butAnterior.addActionListener(this);

        JButton butPosterior = new JButton(">");
        butPosterior.setActionCommand(POSTERIOR);
        butPosterior.addActionListener(this);

        ImageIcon perfil;
        perfil = new ImageIcon(getClass().getResource("/img/Fondo/perfilRemington.png"));
        labArmas[0] = new JLabel(perfil);
        perfil = new ImageIcon(getClass().getResource("/img/Fondo/perfilM1911.png"));
        labArmas[1] = new JLabel(perfil);
        perfil = new ImageIcon(getClass().getResource("/img/Fondo/perfilGranada.png"));
        labArmas[2] = new JLabel(perfil);
        perfil = new ImageIcon(getClass().getResource("/img/Fondo/perfilCuchillo.png"));
        labArmas[3] = new JLabel(perfil);

        inicializarAtributos();

        add(labArmas[1], BorderLayout.CENTER);
        add(panelAtributos[1], BorderLayout.SOUTH);
        add(labArmas[2], BorderLayout.CENTER);
        add(panelAtributos[2], BorderLayout.SOUTH);
        add(labArmas[3], BorderLayout.CENTER);
        add(panelAtributos[3], BorderLayout.SOUTH);
        add(labArmas[0], BorderLayout.CENTER);
        add(panelAtributos[0], BorderLayout.SOUTH);
        add(butAnterior, BorderLayout.WEST);
        add(butPosterior, BorderLayout.EAST);
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
        int[] valoresCuchillo = {Knife.DANIO};
        panelAtributos[3] = new PanelAtributos(atributosCuchillo, valoresCuchillo);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (arg0.getActionCommand().equals(ANTERIOR))
            verIzquierda();
        if (arg0.getActionCommand().equals(POSTERIOR))
            verDerecha();
    }

    private void verDerecha() {
        labArmas[principal.darArmaMostrada()].setVisible(false);
        panelAtributos[principal.darArmaMostrada()].setVisible(false);
        int aMostrar = principal.cambiarArmaVisibleDerecha();
        labArmas[principal.darArmaMostrada()].setVisible(true);
        panelAtributos[principal.darArmaMostrada()].setVisible(true);
        add(labArmas[aMostrar], BorderLayout.CENTER);
        add(panelAtributos[aMostrar], BorderLayout.SOUTH);
    }

    private void verIzquierda() {
        labArmas[principal.darArmaMostrada()].setVisible(false);
        panelAtributos[principal.darArmaMostrada()].setVisible(false);
        int aMostrar = principal.cambiarArmaVisibleIzquierda();
        labArmas[principal.darArmaMostrada()].setVisible(true);
        panelAtributos[principal.darArmaMostrada()].setVisible(true);
        add(labArmas[aMostrar], BorderLayout.CENTER);
        add(panelAtributos[aMostrar], BorderLayout.SOUTH);
    }
}
