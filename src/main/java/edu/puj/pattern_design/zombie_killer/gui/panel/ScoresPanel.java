package edu.puj.pattern_design.zombie_killer.gui.panel;

import edu.puj.pattern_design.zombie_killer.gui.ZombieKillerGUI;
import edu.puj.pattern_design.zombie_killer.service.camp.impl.CharacterScore;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ScoresPanel extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;
    private static final String ORDEN_HEADSHOT = "Filtrar por tiros a la cabeza";
    private static final String ORDEN_BAJAS = "Filtrar por bajas";
    private static final String ORDEN_SCORE = "Filtrar por puntaje";
    private static final String BUSCAR_NOMBRE = "Buscar por nombre: ";
    private static final String SALIR = "salir";
    public static final String AGENCY_FB = "Agency FB";

    private final JLabel titulo;
    private final JButton butFiltroHeadShot;
    private final JButton butFiltroBajas;
    private final JButton butFiltroScore;
    private final JButton butSalir;
    private ZombieKillerGUI principal;
    private JTextField entradaBusqueda;

    private static ScoresPanel panelSingleton;

    public ScoresPanel() {
        setBackground(Color.BLACK);
        this.setBorder(new EmptyBorder(0, 0, 20, 0));
        Font f = new Font(AGENCY_FB, Font.BOLD, 50);
        titulo = new JLabel("Puntajes");
        titulo.setForeground(Color.WHITE);
        butFiltroHeadShot = new JButton(ORDEN_HEADSHOT);
        butFiltroHeadShot.addActionListener(this);
        butFiltroHeadShot.setActionCommand(ORDEN_HEADSHOT);
        butFiltroBajas = new JButton(ORDEN_BAJAS);
        butFiltroBajas.addActionListener(this);
        butFiltroBajas.setActionCommand(ORDEN_BAJAS);
        butFiltroScore = new JButton(ORDEN_SCORE);
        butFiltroScore.addActionListener(this);
        butFiltroScore.setActionCommand(ORDEN_SCORE);
        entradaBusqueda = new JTextField("", 20);
        entradaBusqueda.addActionListener(this);
        configurarCampoBusqueda(entradaBusqueda);
        butSalir = new JButton();
        configurarBoton(butSalir, getClass().getResource("/img/Palabras/volver.png"), SALIR);
        butSalir.setActionCommand(SALIR);
        JLabel texto = new JLabel("No se encontraron puntajes");
        texto.setForeground(Color.WHITE);
        titulo.setFont(f);
        setLayout(new BorderLayout());
        add(titulo, BorderLayout.NORTH);
        add(texto, BorderLayout.CENTER);
        add(butSalir, BorderLayout.SOUTH);
    }

    public static ScoresPanel getPanel() {
        if (panelSingleton == null) {
            panelSingleton = new ScoresPanel();
        }

        return panelSingleton;
    }

    public void setPrincipal(ZombieKillerGUI zombieKillerGUI) {
        principal = zombieKillerGUI;
    }

    public void agregarPanelSuperior(JLabel titulo) {
        JPanel principalEncabezado = new JPanel();
        JPanel panelBuscar = new JPanel();
        JPanel panelBorde = new JPanel();
        JPanel panelTitulo = new JPanel();
        JLabel etiquetaBuscar = new JLabel(BUSCAR_NOMBRE);
        etiquetaBuscar.setForeground(Color.WHITE);
        panelTitulo.setLayout(new BorderLayout());
        panelTitulo.add(titulo, BorderLayout.WEST);
        panelBuscar.add(etiquetaBuscar);
        panelBuscar.add(entradaBusqueda);

        panelBuscar.setBackground(Color.BLACK);
        panelTitulo.setBackground(Color.BLACK);

        panelTitulo.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
        panelBorde.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
        panelBuscar.setBorder(BorderFactory.createEmptyBorder(0, 500, 0, 0));
        principalEncabezado.setLayout(new BorderLayout());
        principalEncabezado.add(panelTitulo, BorderLayout.NORTH);
        principalEncabezado.add(panelBorde, BorderLayout.CENTER);
        principalEncabezado.add(panelBuscar, BorderLayout.SOUTH);
        add(principalEncabezado, BorderLayout.NORTH);
    }

    private void generaryAgregarLabels(List<CharacterScore> characterScores) {
        JLabel[] labScores = new JLabel[characterScores.size()];
        JLabel[] labNombres = new JLabel[characterScores.size()];
        JLabel[] labHeadShots = new JLabel[characterScores.size()];
        JLabel[] labBajas = new JLabel[characterScores.size()];
        JPanel auxPuntajes = new JPanel();
        auxPuntajes.setBorder(new EmptyBorder(0, 150, 0, 50));
        Font font = new Font(AGENCY_FB, Font.BOLD, 24);
        auxPuntajes.setBackground(Color.black);

        if (characterScores.size() > 10) {
            auxPuntajes.setLayout(new GridLayout(11, 4));
            titulo.setText("Top 10 Mejores Puntajes");
        } else {
            auxPuntajes.setLayout(new GridLayout(characterScores.size() + 1, 4));
        }

        JLabel labScore = new JLabel("Score");
        labScore.setForeground(Color.WHITE);
        JLabel labNombre = new JLabel("Nombre");
        labNombre.setForeground(Color.WHITE);
        JLabel labTC = new JLabel("Headshots");
        labTC.setForeground(Color.WHITE);
        JLabel labKills = new JLabel("Bajas");
        labKills.setForeground(Color.WHITE);
        labNombre.setFont(font);
        labScore.setFont(font);
        labKills.setFont(font);
        labTC.setFont(font);
        auxPuntajes.add(labNombre);
        auxPuntajes.add(labScore);
        auxPuntajes.add(labKills);
        auxPuntajes.add(labTC);

        for (int i = 0; i < characterScores.size() && i < 10; i++) {
            labScores[i] = new JLabel(characterScores.get(i).getScore() + "");
            labScores[i].setForeground(Color.WHITE);
            labScores[i].setVerticalAlignment(SwingConstants.TOP);
            labNombres[i] = new JLabel(characterScores.get(i).getKillerName());
            labNombres[i].setForeground(Color.WHITE);
            labNombres[i].setVerticalAlignment(SwingConstants.TOP);
            labHeadShots[i] = new JLabel(characterScores.get(i).getHeadShoots() + "");
            labHeadShots[i].setForeground(Color.WHITE);
            labHeadShots[i].setVerticalAlignment(SwingConstants.TOP);
            labBajas[i] = new JLabel(characterScores.get(i).getDowns() + "");
            labBajas[i].setForeground(Color.WHITE);
            labBajas[i].setVerticalAlignment(SwingConstants.TOP);
            auxPuntajes.add(labNombres[i]);
            auxPuntajes.add(labScores[i]);
            auxPuntajes.add(labBajas[i]);
            auxPuntajes.add(labHeadShots[i]);
        }

        auxPuntajes.setFont(font);
        add(auxPuntajes, BorderLayout.CENTER);
    }

    private void generarYAgregarBotones() {
        JPanel auxBotones = new JPanel();
        JPanel auxBotonSalir = new JPanel();
        JPanel botones = new JPanel();
        auxBotones.setBackground(Color.BLACK);
        auxBotonSalir.setBackground(Color.BLACK);
        auxBotones.add(butFiltroHeadShot);
        auxBotones.add(butFiltroBajas);
        auxBotones.add(butFiltroScore);
        auxBotonSalir.add(butSalir);
        auxBotones.setBorder(BorderFactory.createEmptyBorder(0, 380, 0, 0));
        botones.setLayout(new BorderLayout());
        botones.add(auxBotones, BorderLayout.NORTH);
        botones.add(auxBotonSalir, BorderLayout.SOUTH);
        add(botones, BorderLayout.SOUTH);

    }

    public void mostrarPuntajeDe(CharacterScore buscado) {
        if (buscado != null) {
            JLabel encontrado = new JLabel("Mejor puntaje del nombre buscado");
            Font f = new Font(AGENCY_FB, Font.BOLD, 50);
            encontrado.setFont(f);
            encontrado.setForeground(Color.WHITE);
            removeAll();
            agregarPanelSuperior(encontrado);
            ArrayList<CharacterScore> aMostrar = new ArrayList<>();
            aMostrar.add(buscado);
            generaryAgregarLabels(aMostrar);
            generarYAgregarBotones();
            updateUI();
        } else {
            JOptionPane.showMessageDialog(this, "No se encontro el nombre buscado en los puntajes");
        }
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        String c = arg0.getActionCommand();
        switch (c) {
            case ORDEN_BAJAS:
                principal.sortByDeadZombies();
                break;
            case ORDEN_HEADSHOT:
                principal.sortByHeadshots();
                break;
            case ORDEN_SCORE:
                principal.sortByScore();
                break;
        }
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
                ImageIcon iI = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/Palabras/volver.png")));
                butSalir.setIcon(iI);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                ImageIcon iI = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/Palabras/volver_p.png")));
                butSalir.setIcon(iI);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                principal.showScores();
            }
        });
    }

    public void configurarCampoBusqueda(JTextField nombreBuscado) {
        nombreBuscado.addActionListener(e -> {
            String nombre = nombreBuscado.getText();
            principal.searchByName(nombre);
        });
    }

    public void actualizarPuntajes(List<CharacterScore> characterScores) {
        if (!characterScores.isEmpty()) {
            removeAll();
            agregarPanelSuperior(titulo);
            generaryAgregarLabels(characterScores);
            generarYAgregarBotones();
            updateUI();

        }
    }
}
