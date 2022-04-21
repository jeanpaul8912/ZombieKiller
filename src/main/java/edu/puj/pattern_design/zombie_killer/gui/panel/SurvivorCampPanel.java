package edu.puj.pattern_design.zombie_killer.gui.panel;

import edu.puj.pattern_design.zombie_killer.gui.ZombieKillerGUI;
import edu.puj.pattern_design.zombie_killer.service.camp.impl.Character;
import edu.puj.pattern_design.zombie_killer.service.defense_strategies.DefenseStrategyContext;
import edu.puj.pattern_design.zombie_killer.service.defense_strategies.ShootStrategy;
import edu.puj.pattern_design.zombie_killer.service.defense_strategies.SlashStrategy;
import edu.puj.pattern_design.zombie_killer.service.defense_strategies.ThrowGrenadeStrategy;
import edu.puj.pattern_design.zombie_killer.service.weapons.guns.GunWeapon;
import edu.puj.pattern_design.zombie_killer.service.zombies.Boss;
import edu.puj.pattern_design.zombie_killer.service.zombies.Zombie;
import edu.puj.pattern_design.zombie_killer.service.zombies.ZombieZigZag;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;

import static edu.puj.pattern_design.zombie_killer.service.constants.MainCharacterConstants.SALUD;
import static edu.puj.pattern_design.zombie_killer.service.constants.SurvivorCampConstants.EN_CURSO;
import static edu.puj.pattern_design.zombie_killer.service.constants.SurvivorCampConstants.INICIANDO_RONDA;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.CARGANDO;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.LISTA;
import static edu.puj.pattern_design.zombie_killer.service.constants.WeaponsConstants.RECHARGING;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.NODO;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.POS_ATAQUE;

@Slf4j
@Getter
@Setter
public class SurvivorCampPanel extends JPanel implements MouseListener, KeyListener {

    private static final long serialVersionUID = 3350712174974197350L;

    private final JLabel labPuntaje;

    private final JLabel labBajas;

    private final JLabel labRonda;

    private final JProgressBar labVidas;

    private final JLabel labGranadas;

    private final JLabel labBalas;

    private final JPanel mostrador;

    private Point ultimoDisparo;

    private ZombieKillerGUI principal;

    private ZombieZigZag chombiMasLejano;

    private Character matador;

    private GunWeapon armaEquipada;

    private Boss chief;

    private DefenseStrategyContext defenseStrategy;

    private static SurvivorCampPanel panelSingleton;

    public SurvivorCampPanel() {
        setLayout(new BorderLayout());
        Font tipo = new Font("Chiller", Font.PLAIN, 34);

        ultimoDisparo = new Point();
        setDebugGraphicsOptions(DebugGraphics.BUFFERED_OPTION);
        mostrador = new JPanel();
        mostrador.setBackground(Color.BLACK);
        mostrador.setLayout(new GridLayout(3, 1));

        labBajas = new JLabel();
        labBajas.setFont(tipo);
        labBajas.setForeground(Color.white);

        labRonda = new JLabel();
        labRonda.setFont(tipo);
        labRonda.setForeground(Color.white);

        JLabel labTirosALaCabeza = new JLabel();
        labTirosALaCabeza.setFont(tipo);
        labTirosALaCabeza.setForeground(Color.white);

        labPuntaje = new JLabel();
        labPuntaje.setFont(tipo);
        labPuntaje.setHorizontalAlignment(SwingConstants.CENTER);
        labPuntaje.setForeground(Color.white);

        labVidas = new JProgressBar();
        labVidas.setFont(tipo);
        labVidas.setMaximum(SALUD);
        labVidas.setForeground(Color.green);

        labGranadas = new JLabel();
        labGranadas.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/Fondo/Granada.png"))));
        labGranadas.setFont(tipo);
        labGranadas.setVerticalAlignment(SwingConstants.TOP);
        labGranadas.setForeground(Color.white);

        labBalas = new JLabel();
        labBalas.setFont(tipo);
        labBalas.setVerticalAlignment(SwingConstants.TOP);
        labBalas.setForeground(Color.white);

        addMouseListener(this);
        addKeyListener(this);
        setFocusable(true);

        mostrador.add(labBajas);
        mostrador.add(labRonda);
        mostrador.add(labTirosALaCabeza);
        mostrador.setVisible(false);

        add(labPuntaje, BorderLayout.NORTH);
        add(labVidas, BorderLayout.SOUTH);
        add(labBalas, BorderLayout.EAST);
        add(labGranadas, BorderLayout.WEST);
        add(mostrador, BorderLayout.CENTER);
    }

    public static SurvivorCampPanel getPanel() {
        if (panelSingleton == null) {
            panelSingleton = new SurvivorCampPanel();
        }

        return panelSingleton;
    }

    public void actualizarChombis(ZombieZigZag chombi) {
        chombiMasLejano = chombi;
    }

    public void actualizarEquipada(GunWeapon armaEquipada) {
        this.armaEquipada = armaEquipada;
        String armaActual = armaEquipada.getClass().getSimpleName();
        labBalas.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/Fondo/" + armaActual + ".png"))));
        labBalas.setText("" + armaEquipada.getAvailableBullets());
    }

    public void actualizarMatador(Character character) {
        matador = character;
        labVidas.setValue(matador.getHealth());
        labPuntaje.setText("Puntaje: " + matador.getScore());
        labBajas.setText("Bajas: " + matador.getKilling());
        labGranadas.setText(matador.getGrenades().getAvailableBullets() + "");
    }

    public void actualizarRonda() {
        labRonda.setText("Ronda: " + principal.darRondaActual());
        labBalas.setText(principal.getCamp().getCharacter().getPrincipalWeapon().getAvailableBullets() + "");
        labGranadas.setText(principal.getCamp().getCharacter().getGrenades().getAvailableBullets() + "");
    }

    public void incorporarJefe(Boss aMatar) {
        chief = aMatar;
    }

    @Override
    protected void paintComponent(Graphics arg0) {
        super.paintComponent(arg0);

        Image fondo = Toolkit.getDefaultToolkit()
                .getImage(this.getClass().getResource("/img/Fondo/escenario-fondo-azul.png"));
        arg0.drawImage(fondo, 0, 0, null);
        
        if (getDebugGraphicsOptions() == DebugGraphics.BUFFERED_OPTION) {
            requestFocusInWindow();
            cargarImagenes();
            setDebugGraphicsOptions(DebugGraphics.NONE_OPTION);
        }

        Zombie aPintar = chombiMasLejano.getInFront();

        if (aPintar != null)
            while (!aPintar.getCurrentStatus().equals(NODO)) {
                try {
                    int posX = aPintar.getPosX();
                    int posY = aPintar.getPosY();
                    Image imgZombie = Toolkit.getDefaultToolkit()
                            .getImage(this.getClass().getResource(aPintar.getURL(principal.getCamp().getCurrentRound())));
                    arg0.drawImage(imgZombie, posX, posY, null);
                    aPintar = aPintar.getInFront();
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    principal.pauseGame();
                }
            }

        if (chief != null) {
            Image chiefAPintar = Toolkit.getDefaultToolkit().getImage(getClass().getResource(
                    chief.getURL(principal.getCamp().getCurrentRound())));
            arg0.drawImage(chiefAPintar, chief.getPosX(), chief.getPosY(), null);
        }

        int ataqueX = (int) ultimoDisparo.getX();
        int ataqueY = (int) ultimoDisparo.getY();

        if (armaEquipada.isBlooded()) {
            fondo = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/img/Fondo/sangre.png"));
            arg0.drawImage(fondo, ataqueX - 33, ataqueY - 35, null);
        }

        if (matador.getKnife().getStatus().equals(CARGANDO)) {
            fondo = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/Fondo/punialada.png"));
            arg0.drawImage(fondo, ataqueX - 160, ataqueY - 30, null);
        } else if (armaEquipada.getStatus().equals(RECHARGING)) {
            fondo = Toolkit.getDefaultToolkit().getImage(this.getClass()
                    .getResource("/img/Fondo/disparo" + armaEquipada.getClass().getSimpleName() + ".png"));
            arg0.drawImage(fondo, ataqueX - 33, ataqueY - 35, null);
        } else if (matador.getGrenades().getStatus().equals(CARGANDO)) {
            fondo = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/Fondo/explosion.png"));
            arg0.drawImage(fondo, 250, 133, null);
        }

        if (matador.isBlooded()) {
            fondo = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/Fondo/boss_ataca.png"));
            arg0.drawImage(fondo, 0, 0, null);
        }

        if (principal.getEstadoPartida() == INICIANDO_RONDA) {
            fondo = Toolkit.getDefaultToolkit()
                    .getImage(getClass().getResource("/img/Palabras/ronda" + principal.darRondaActual() + ".png"));
            arg0.drawImage(fondo, 100, 300, null);
        }
    }

    private void cargarImagenes() {
        ImageIcon[] imagenesCargadas = new ImageIcon[262];
        int contador = 0;
        // 31 es la mayor cantidad de imagenes de una animacion

        for (int i = 0; i <= 31; i++) {
            contador += loadImage("/img/DragZombie/caminando/", imagenesCargadas, contador, i);

            if (i <= 24) {
                contador += loadImage("/img/WalkerZombie/caminando/", imagenesCargadas, contador, i);
                contador += loadImage("/img/WalkerZombie/caminando/shield/", imagenesCargadas, contador, i);

                if (i <= 21) {
                    contador += loadImage("/img/Boss/atacando/", imagenesCargadas, contador, i);
                    contador = loadWalkerZombieImages(imagenesCargadas, contador, i);
                }
            }
        }

        contador += loadImage("/img/Fondo/sangre.png", imagenesCargadas, contador);
        contador += loadImage("/img/Fondo/boss_ataca.png", imagenesCargadas, contador);
        contador += loadImage("/img/Fondo/disparoM1911.png", imagenesCargadas, contador);
        contador += loadImage("/img/Fondo/disparoRemington.png", imagenesCargadas, contador);
        contador += loadImage("/img/Fondo/fondoMenu.png", imagenesCargadas, contador);
        contador += loadImage("/img/Fondo/escenario-fondo-azul.png", imagenesCargadas, contador);
    }

    private int loadImage(String url, ImageIcon[] imagenesCargadas, int contador) {
        ImageIcon actual = new ImageIcon(Objects.requireNonNull(getClass().getResource(url)));
        imagenesCargadas[contador] = actual;
        return 1;
    }

    private int loadImage(String url, ImageIcon[] imagenesCargadas, int contador, int index) {
        ImageIcon actual = new ImageIcon(
                Objects.requireNonNull(getClass().getResource(url + String.format("%02d", index) + ".png")));
        imagenesCargadas[contador] = actual;
        return 1;
    }

    private int loadWalkerZombieImages(ImageIcon[] imagenesCargadas, int contador, int i) {
        if (i <= 17) {
            contador += loadImage("/img/WalkerZombie/muriendo/", imagenesCargadas, contador, i);
            contador += loadImage("/img/WalkerZombie/muriendoIncendiado/", imagenesCargadas, contador, i);
            contador += loadImage("/img/WalkerZombie/gruniendo/", imagenesCargadas, contador, i);
            contador += loadImage("/img/WalkerZombie/gruniendo/knife/", imagenesCargadas, contador, i);
            contador = loadBossImages(imagenesCargadas, contador, i);
        }

        return contador;
    }


    private int loadBossImages(ImageIcon[] imagenesCargadas, int contador, int i) {
        if (i <= 13) {
            contador += loadImage("/img/Boss/volando/", imagenesCargadas, contador, i);
            contador += loadImage("/img/WalkerZombie/atacando/", imagenesCargadas, contador, i);
            contador += loadImage("/img/WalkerZombie/atacando/knife/", imagenesCargadas, contador, i);
            contador += loadImage("/img/DragZombie/atacando/", imagenesCargadas, contador, i);
            contador = loadDragZombieImages(imagenesCargadas, contador, i);
        }

        return contador;
    }

    private int loadDragZombieImages(ImageIcon[] imagenesCargadas, int contador, int i) {
        if (i <= 11) {
            contador += loadImage("/img/DragZombie/muriendo/", imagenesCargadas, contador, i);
            contador += loadImage("/img/DragZombie/muriendoIncendiado/", imagenesCargadas, contador, i);
        }

        return contador;
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {

    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        if (principal.getEstadoPartida() == EN_CURSO)
            principal.pauseGame();
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
        if (principal.getEstadoPartida() == EN_CURSO) {
            if (arg0.getButton() == MouseEvent.BUTTON1) {
                int xPosition = arg0.getX();
                int yPosition = arg0.getY();
                if (armaEquipada.getStatus().equals(LISTA) && armaEquipada.getAvailableBullets() > 0) {
                    ultimoDisparo = arg0.getPoint();
                    defenseStrategy = new DefenseStrategyContext(new ShootStrategy(principal, xPosition, yPosition));
                    defenseStrategy.executeDefense();
                } else if (yPosition > POS_ATAQUE && matador.getKnife().getStatus().equals(LISTA)) {
                    ultimoDisparo = arg0.getPoint();
                    defenseStrategy = new DefenseStrategyContext(new SlashStrategy(principal, xPosition, yPosition));
                    defenseStrategy.executeDefense();
                } else if (armaEquipada.getAvailableBullets() == 0)
                    principal.reproduceSound("sin_balas");
                labPuntaje.setText("Puntaje: " + matador.getScore());
                labBajas.setText("Bajas: " + matador.getKilling());
            } else if (arg0.getButton() == MouseEvent.BUTTON3
                    && armaEquipada.getAvailableBullets() < armaEquipada.getMaxBullets())
                principal.loadCharacterWeapon();
            labBalas.setText("" + armaEquipada.getAvailableBullets());
        }
    }

    public void zombieAtaco() {
        labVidas.setValue(matador.getHealth());
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (principal.getEstadoPartida() == EN_CURSO) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyCode() == KeyEvent.VK_ALT
                    || e.getKeyCode() == KeyEvent.VK_P) {
                principal.pauseGame();
            } else if (e.getKeyCode() == KeyEvent.VK_C) {
                principal.changeWeapon();
                actualizarEquipada(matador.getPrincipalWeapon());
            } else if (e.getKeyCode() == KeyEvent.VK_SPACE && matador.getGrenades().getAvailableBullets() > 0) {
                defenseStrategy = new DefenseStrategyContext(new ThrowGrenadeStrategy(principal));
                defenseStrategy.executeDefense();
                labGranadas.setText("" + principal.getGrenade().getAvailableBullets());
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            mostrador.setVisible(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SHIFT)
            mostrador.setVisible(false);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    public void quitarSangreZombie() {
        ultimoDisparo.setLocation(-40, -40);
    }

}
