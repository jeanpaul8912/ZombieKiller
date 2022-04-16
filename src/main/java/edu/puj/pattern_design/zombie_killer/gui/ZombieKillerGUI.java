package edu.puj.pattern_design.zombie_killer.gui;

import edu.puj.pattern_design.zombie_killer.gui.facade.ThreadsFacade;
import edu.puj.pattern_design.zombie_killer.gui.panel.PanelCamp;
import edu.puj.pattern_design.zombie_killer.gui.panel.PanelComoJugar;
import edu.puj.pattern_design.zombie_killer.gui.panel.PanelCreditos;
import edu.puj.pattern_design.zombie_killer.gui.panel.PanelMenu;
import edu.puj.pattern_design.zombie_killer.gui.panel.PanelPuntajes;
import edu.puj.pattern_design.zombie_killer.service.attack_strategies.AttackStrategyContext;
import edu.puj.pattern_design.zombie_killer.service.attack_strategies.BossZombieAttackStrategy;
import edu.puj.pattern_design.zombie_killer.service.attack_strategies.WalkerZombieAttackStrategy;
import edu.puj.pattern_design.zombie_killer.service.camp.CharacterScore;
import edu.puj.pattern_design.zombie_killer.service.camp.SurvivorCamp;
import edu.puj.pattern_design.zombie_killer.service.exceptions.NombreInvalidoException;
import edu.puj.pattern_design.zombie_killer.service.weapons.Weapon;
import edu.puj.pattern_design.zombie_killer.service.weapons.guns.GunWeapon;
import edu.puj.pattern_design.zombie_killer.service.weapons.guns.Remington;
import edu.puj.pattern_design.zombie_killer.service.zombies.Boss;
import edu.puj.pattern_design.zombie_killer.service.zombies.WalkerZombie;
import edu.puj.pattern_design.zombie_killer.service.zombies.Zombie;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static edu.puj.pattern_design.zombie_killer.service.constants.CampConstants.ALTO_PANTALLA;
import static edu.puj.pattern_design.zombie_killer.service.constants.CampConstants.ANCHO_PANTALLA;
import static edu.puj.pattern_design.zombie_killer.service.constants.CampConstants.EN_CURSO;
import static edu.puj.pattern_design.zombie_killer.service.constants.CampConstants.INICIANDO_RONDA;
import static edu.puj.pattern_design.zombie_killer.service.constants.CampConstants.PAUSADO;
import static edu.puj.pattern_design.zombie_killer.service.constants.CampConstants.SIN_PARTIDA;

@Slf4j
@Getter
@Setter
public class ZombieKillerGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    /**
     * Campo de juego que contiene a todo el mundo
     */
    private SurvivorCamp camp;
    /**
     * Arma que el jugador tiene equipada
     */
    private GunWeapon currentWeapon;
    /**
     * Panel del menu principal cualquier boton muestra otro panel representatitvo a
     * el
     */
    private final PanelMenu panelMenu;
    /**
     * Panel del campo de juego
     */
    private PanelCamp panelCampo;
    /**
     * Panel que muestra las instrucciones de juego Muestra las estadisticas de las
     * armas
     */
    private PanelComoJugar panelComoJugar;
    /**
     * Panel que muestra los puntajes de los jugadores
     */
    private PanelPuntajes panelPuntajes;
    /**
     * Panel que muestra los creditos de las personas que participaron
     */
    private PanelCreditos panelCreditos;
    /**
     * Cursor de la mira de la pistola
     */
    private final Cursor m1911Cursor;
    /**
     * Cursor de la mira de la escopeta
     */
    private Cursor remingtonCursor;
    /**
     * Cursor temporal del cuchillo
     */
    private Cursor knifeCursor;

    private Boss boss;

    private GunWeapon grenade;

    private Weapon knife;

    private ThreadsFacade facade;

    private AttackStrategyContext attackStrategy;

    /**
     * Constructor de la clase principal del juego Aqui se inicializan todos los
     * componentes necesarios para empezar a jugar
     */
    public ZombieKillerGUI() throws IOException {
        long start = System.currentTimeMillis();

        BorderLayout custom = new BorderLayout();
        setLayout(custom);

        new ImageIcon(getClass().getResource("/img/Fondo/iconozombie.png"));
        new ImageIcon(getClass().getResource("/img/Fondo/fondoMenu.png"));

        m1911Cursor = CursorObjectPool.getCursor("/img/Fondo/mira1p.png");
        setCursor(m1911Cursor);
        panelMenu = new PanelMenu(this);
        add(panelMenu, BorderLayout.CENTER);
        setSize(ANCHO_PANTALLA, ALTO_PANTALLA);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);

        long finish = System.currentTimeMillis();
        double elapsed = (finish - start) / 1000.0;
        System.out.printf("Elapsed: %1$f, Start = %2$d, Finish = %3$d%n", elapsed, start, finish);
        facade = new ThreadsFacade(this);
    }

    /**
     * Obtiene el estado actual de la partida
     *
     * @return estado
     */
    public char getEstadoPartida() {
        if (camp == null) {
            return SIN_PARTIDA;
        }

        return camp.getGameStatus();
    }

    /**
     * Inicia una partida desde 0
     */
    public void iniciarNuevaPartida() {
        if (camp.getGameStatus() != SIN_PARTIDA) {
            int respuesta = JOptionPane.showConfirmDialog(this,
                    "En este momento se encuentra en una partida, segudo que desea salir?", "Iniciar Nueva Partida",
                    JOptionPane.YES_NO_OPTION);

            if (respuesta == JOptionPane.YES_OPTION) {
                camp.setGameStatus(SIN_PARTIDA);
                partidaIniciada();
            }
        } else {
            partidaIniciada();
        }
    }

    /**
     * Metodo auxiliar que inicializa y actualiza la informacion en los componentes
     * visibles
     */
    private void partidaIniciada() {
        setCursor(knifeCursor);
        CharacterScore actual = camp.getRootScores();
        camp = new SurvivorCamp();
        camp.updateScores(actual);
        camp.setGameStatus(EN_CURSO);
        currentWeapon = camp.getCharacter().getPrincipalWeapon();
        panelCampo.actualizarMatador(camp.getCharacter());
        panelCampo.actualizarEquipada(currentWeapon);
        panelCampo.actualizarChombis(camp.getZombieFarNode());
        panelCampo.incorporarJefe(null);
        add(panelCampo, BorderLayout.CENTER);
        panelCampo.requestFocusInWindow();
        facade.initializeEnemyThreads();
        panelMenu.setVisible(false);
        panelCampo.setVisible(true);
    }

    /**
     * pregunta si en el PanelCamp se estan cargando las imagenes
     *
     * @return true si aun se estan cargando
     */
    public boolean estaCargando() {
        return panelCampo.getDebugGraphicsOptions() == DebugGraphics.BUFFERED_OPTION;
    }

    /**
     * obtiene el puntaje/score actual del personaje
     *
     * @return puntaje
     */
    public int getPuntajeActual() {
        return camp.getCharacter().getScore();
    }

    /**
     * Carga la partida guardada y actualiza todos los componentes que la usan
     */
    public void cargarJuego() {
        try {
            CharacterScore actuales = camp.getRootScores();
            SurvivorCamp partida = camp.loadGame();
            camp.setGameStatus(SIN_PARTIDA);
            camp = partida;
            camp.updateScores(actuales);
            panelCampo.actualizarMatador(camp.getCharacter());
            panelCampo.actualizarChombis(camp.getZombieFarNode());
            currentWeapon = camp.getCharacter().getPrincipalWeapon();
            panelCampo.actualizarEquipada(currentWeapon);
            panelCampo.actualizarRonda();
            cambiarPuntero();
            panelMenu.setVisible(false);
            panelCampo.setVisible(true);
            camp.setGameStatus(EN_CURSO);
            add(panelCampo, BorderLayout.CENTER);
            panelCampo.requestFocusInWindow();
            panelCampo.requestFocusInWindow();
            facade.initializeEnemyThreads();
            iniciarGemi2();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    /**
     * Guarda la partida que esta en curso
     */
    public void guardarJuego() {
        try {
            camp.guardarPartida();
            JOptionPane.showMessageDialog(this, "Partida Guardada");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    /**
     * repinta el panelCampo para mostrar los zombies en movimiento
     */
    public void refrescar() {
        panelCampo.repaint();
    }

    /**
     * inicia el sonido de los zombies
     */
    public void iniciarGemi2() {
        facade.initializeZombieSoundThread("zombies");
    }

    /**
     * termina el sonido de los zombies
     */
    public void terminarGemi2() {
        facade.soundStop();
    }

    /**
     * genera un zombie en la partida dada la ronda actual
     *
     * @param nivel
     */
    public void generarZombie(int nivel) {
        Zombie chombi = camp.generateZombie(nivel);

        if (chombi instanceof WalkerZombie) {
            attackStrategy = new AttackStrategyContext(new WalkerZombieAttackStrategy());
            WalkerZombieAttackStrategy walkerZombieAttackStrategy = new WalkerZombieAttackStrategy();
            walkerZombieAttackStrategy.moveInDirection(chombi);
        }
    }

    /**
     * Ejecuta los efectos tras ser atacado por un enemigo
     */
    public void leDaAPersonaje() {
        reproducir("meDio");
        attackStrategy = new AttackStrategyContext(new BossZombieAttackStrategy());
        attackStrategy.enemyAttacks(camp);
        panelCampo.zombieAtaco();
    }

    /**
     * Pausa y despausa el juego
     */
    public void pausarJuego() {
        char estado = camp.pauseGame();

        if (estado == PAUSADO) {
            terminarGemi2();
            panelMenu.setVisible(true);
            panelCampo.setVisible(false);
            panelMenu.updateUI();
            panelMenu.requestFocusInWindow();
        } else {
            iniciarGemi2();
            panelCampo.setVisible(true);
            panelMenu.setVisible(false);
            panelCampo.updateUI();
            panelCampo.requestFocusInWindow();
        }
    }

    /**
     * reabastece la carga del arma principal
     */
    public void cargarArmaPersonaje() {
        camp.getCharacter().reloadPrincipalWeapon();
        reproducir("carga" + currentWeapon.getClass().getSimpleName());
        facade.initializeWeaponsThread("armaDeFuego");
    }

    /**
     * reproduce cualquier sonido que se encuentre en la carpeta sonidos
     *
     * @param ruta
     */
    public void reproducir(String ruta) {
        facade.initializeGeneralSoundThread(ruta);
    }

    /**
     * cambia el arma del personaje y actualiza aqui
     */
    public void cambiarArma() {
        camp.cambiarArma();
        currentWeapon = camp.getCharacter().getPrincipalWeapon();
        cambiarPuntero();
    }

    /**
     * cambia el cursor de acuerdo al arma principal
     */
    public void cambiarPuntero() {
        if (currentWeapon instanceof Remington)
            setCursor(remingtonCursor);
        else
            setCursor(m1911Cursor);
    }

    /**
     * termina el efecto del disparo con sangre
     */
    public void terminarEfectoDeSangre() {
        currentWeapon.setBlooded(false);
        panelCampo.quitarSangreZombie();
    }

    /**
     * obtiene la ronda en la que se encuentra
     */
    public int darRondaActual() {
        return camp.getCurrentRound();
    }

    /**
     * sube la ronda actual, suena la sirena al avanzar
     *
     * @param nivel
     */
    public void subirDeRonda(int nivel) {
        terminarGemi2();
        reproducir("sirena");
        camp.updateCurrentRound(nivel);
        camp.setGameStatus(INICIANDO_RONDA);
        panelCampo.actualizarRonda();
    }

    /**
     * genera el jefe con su respectivo hilo
     */
    public void generarBoss() {
        boss = camp.generateBoss();
        panelCampo.incorporarJefe(boss);
        BossZombieAttackStrategy bossZombieAttackStrategy = new BossZombieAttackStrategy();
        attackStrategy = new AttackStrategyContext(bossZombieAttackStrategy);
        bossZombieAttackStrategy.moveInDirection(boss);
        facade.initializeBossThread();
    }

    /**
     * Muestra el Panel de Como jugar / Lo oculta
     */
    public void mostrarComoJugar() {
        if (panelMenu.isVisible()) {
            panelMenu.setVisible(false);
            panelComoJugar.setVisible(true);
            add(panelComoJugar, BorderLayout.CENTER);
        } else {
            panelComoJugar.setVisible(false);
            panelMenu.setVisible(true);
        }
    }

    /**
     * Muestra el Panel donde se encuentran los puntjes / lo oculta
     */
    public void mostrarPuntajes() {
        if (panelMenu.isVisible()) {
            panelPuntajes.actualizarPuntajes(camp.ordenarPuntajePorScore());
            panelMenu.setVisible(false);
            panelPuntajes.setVisible(true);
            add(panelPuntajes, BorderLayout.CENTER);
        } else {
            panelPuntajes.setVisible(false);
            panelMenu.setVisible(true);
        }
    }

    /**
     * Muestra el Panel donde se encuentran los creditos / lo oculta
     */
    public void mostrarCreditos() {
        if (panelMenu.isVisible()) {
            panelMenu.setVisible(false);
            panelCreditos.setVisible(true);
            add(panelCreditos, BorderLayout.CENTER);
        } else {
            panelCreditos.setVisible(false);
            panelMenu.setVisible(true);
        }
    }

    /**
     * Cambia el arma que se esta viendo por el de la derecha
     *
     * @return numero de referencia al arma de la derecha
     */
    public int cambiarArmaVisibleDerecha() {
        return camp.moverArmaVisibleDerecha();
    }

    /**
     * Cambia el arma que se esta viendo por el de la izquierda
     *
     * @return numero de referencia al arma de la izquierda
     */
    public int cambiarArmaVisibleIzquierda() {
        return camp.moverArmaVisibleIzquierda();
    }

    /**
     * Metodo llamado cuando el personaje muere para verificar si el jugador desea
     * seguir o no
     */
    public void juegoTerminado() {
        boolean seLlamoDeNuevo = false;
        int aceptoGuardarScore = JOptionPane.showConfirmDialog(this,
                "Su puntaje fue: " + camp.getCharacter().getScore() + ", con " + camp.getCharacter().getKilling()
                        + " bajas y en la Ronda " + camp.getCurrentRound() + ". Desea guardar su puntaje?",
                "Juego Terminado", JOptionPane.YES_NO_OPTION);
        if (aceptoGuardarScore == JOptionPane.YES_OPTION) {
            String nombrePlayer = JOptionPane.showInputDialog(this, "Escribe tu nombre");

            if (nombrePlayer != null && !nombrePlayer.equals(""))
                try {
                    camp.verificarNombre(nombrePlayer);
                    camp.aniadirMejoresPuntajes(nombrePlayer);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this,
                            "Error al guardar el puntaje, es posible que haya abierto el juego desde \"Acceso rapido\"");
                } catch (NombreInvalidoException e) {
                    log.error(e.getMessage(), e);
                    JOptionPane.showMessageDialog(this, e.getMessage());
                    juegoTerminado();
                }
            else {
                seLlamoDeNuevo = true;
                juegoTerminado();
            }
        }

        if (!seLlamoDeNuevo) {
            int aceptoJugar = JOptionPane.showConfirmDialog(this, "Desea volver a jugar?", "Juego Terminado",
                    JOptionPane.YES_NO_OPTION);
            if (aceptoJugar == JOptionPane.YES_OPTION)
                iniciarNuevaPartida();
            else {
                panelCampo.setVisible(false);
                panelMenu.setVisible(true);
            }
        }

        terminarGemi2();
    }

    /**
     * Metodo que se ejecuta cuando el Boss muere
     */
    public void victoria() {
        String nombrePlayer = JOptionPane.showInputDialog(this,
                "Enhorabuena, has pasado todas los niveles de dificultad. Su puntaje final es: "
                        + camp.getCharacter().getScore() + ". Escribe tu nombre");

        if (nombrePlayer != null && !nombrePlayer.equals(""))
            try {
                camp.verificarNombre(nombrePlayer);
                camp.aniadirMejoresPuntajes(nombrePlayer);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this,
                        "Error al guardar el puntaje, es posible que haya abierto el juego desde \"Acceso rapido\"");
            } catch (NombreInvalidoException e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
                victoria();
            }
        else {
            victoria();
        }

        panelMenu.setVisible(true);
        panelCampo.setVisible(false);
        terminarGemi2();
    }

    /**
     * Llama al metodo de ordenar por bajas
     */
    public void ordenarPorBajas() {
        panelPuntajes.actualizarPuntajes(camp.ordenarPuntajePorBajas());
    }

    /**
     * Llama al metodo de ordenar por bajas con tiro a la cabeza
     */
    public void ordenarPorHeadshot() {
        panelPuntajes.actualizarPuntajes(camp.ordenarPuntajePorTirosALaCabeza());
    }

    /**
     * busca el mejor puntaje del nombre
     */
    public void buscarPorNombre(String nombreBuscado) {
        if (nombreBuscado != null) {
            CharacterScore buscado = camp.buscarPuntajeDe(nombreBuscado);
            panelPuntajes.mostrarPuntajeDe(buscado);
        }
    }

    /**
     * Llama al metodo de ordenar por puntaje
     */
    public void ordenarPorScore() {
        panelPuntajes.actualizarPuntajes(camp.ordenarPuntajePorScore());
    }

}
