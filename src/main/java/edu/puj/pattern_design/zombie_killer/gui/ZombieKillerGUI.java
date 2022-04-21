package edu.puj.pattern_design.zombie_killer.gui;

import edu.puj.pattern_design.zombie_killer.gui.facade.ThreadsFacade;
import edu.puj.pattern_design.zombie_killer.gui.panel.CreditsPanel;
import edu.puj.pattern_design.zombie_killer.gui.panel.HowToPlayPanel;
import edu.puj.pattern_design.zombie_killer.gui.panel.MenuPanel;
import edu.puj.pattern_design.zombie_killer.gui.panel.ScoresPanel;
import edu.puj.pattern_design.zombie_killer.gui.panel.SurvivorCampPanel;
import edu.puj.pattern_design.zombie_killer.service.attack_strategies.AttackStrategyContext;
import edu.puj.pattern_design.zombie_killer.service.attack_strategies.BossZombieAttackStrategy;
import edu.puj.pattern_design.zombie_killer.service.attack_strategies.WalkerZombieAttackStrategy;
import edu.puj.pattern_design.zombie_killer.service.camp.SurvivorCamp;
import edu.puj.pattern_design.zombie_killer.service.camp.impl.CharacterScore;
import edu.puj.pattern_design.zombie_killer.service.camp.impl.SurvivorCampImpl;
import edu.puj.pattern_design.zombie_killer.service.exceptions.InvalidNameException;
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
import java.util.Objects;

import static edu.puj.pattern_design.zombie_killer.service.constants.SurvivorCampConstants.ALTO_PANTALLA;
import static edu.puj.pattern_design.zombie_killer.service.constants.SurvivorCampConstants.ANCHO_PANTALLA;
import static edu.puj.pattern_design.zombie_killer.service.constants.SurvivorCampConstants.EN_CURSO;
import static edu.puj.pattern_design.zombie_killer.service.constants.SurvivorCampConstants.INICIANDO_RONDA;
import static edu.puj.pattern_design.zombie_killer.service.constants.SurvivorCampConstants.PAUSADO;
import static edu.puj.pattern_design.zombie_killer.service.constants.SurvivorCampConstants.SIN_PARTIDA;

@Slf4j
@Getter
@Setter
public class ZombieKillerGUI extends JFrame {

    private static final long serialVersionUID = 1L;

    private SurvivorCamp camp;

    private GunWeapon currentWeapon;

    private final MenuPanel menuPanel;

    private SurvivorCampPanel survivorCampoPanel;

    private HowToPlayPanel howToPlayPanel;

    private ScoresPanel scoresPanel;

    private CreditsPanel creditsPanel;

    private final Cursor m1911Cursor;

    private Cursor remingtonCursor;

    private Cursor knifeCursor;

    private Boss boss;

    private GunWeapon grenade;

    private Weapon knife;

    private ThreadsFacade facade;

    private AttackStrategyContext attackStrategy;

    public ZombieKillerGUI() throws IOException {
        long start = System.currentTimeMillis();

        BorderLayout custom = new BorderLayout();
        setLayout(custom);

        new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/Fondo/iconozombie.png")));
        new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/Fondo/fondoMenu.png")));

        m1911Cursor = CursorObjectPool.getCursor("/img/Fondo/mira1p.png");
        setCursor(m1911Cursor);
        menuPanel = new MenuPanel(this);
        add(menuPanel, BorderLayout.CENTER);
        setSize(ANCHO_PANTALLA, ALTO_PANTALLA);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);

        long finish = System.currentTimeMillis();
        double elapsed = (finish - start) / 1000.0;
        log.info("Elapsed: {}, Start = {}, Finish = {}", elapsed, start, finish);
        facade = new ThreadsFacade(this);
    }

    public char getEstadoPartida() {
        if (camp == null) {
            return SIN_PARTIDA;
        }

        return camp.getGameStatus();
    }

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

    private void partidaIniciada() {
        setCursor(knifeCursor);
        CharacterScore actual = camp.getRootScores();
        camp = new SurvivorCampImpl();
        camp.updateScores(actual);
        camp.setGameStatus(EN_CURSO);
        currentWeapon = camp.getCharacter().getPrincipalWeapon();
        survivorCampoPanel.actualizarMatador(camp.getCharacter());
        survivorCampoPanel.actualizarEquipada(currentWeapon);
        survivorCampoPanel.actualizarChombis(camp.getZombieFarNode());
        survivorCampoPanel.incorporarJefe(null);
        add(survivorCampoPanel, BorderLayout.CENTER);
        survivorCampoPanel.requestFocusInWindow();
        facade.initializeEnemyThreads();
        menuPanel.setVisible(false);
        survivorCampoPanel.setVisible(true);
    }

    public boolean estaCargando() {
        return survivorCampoPanel.getDebugGraphicsOptions() == DebugGraphics.BUFFERED_OPTION;
    }

    public void cargarJuego() {
        try {
            SurvivorCamp survivorCamp = camp.loadGame();
            gameDate(survivorCamp);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public void gameDate(SurvivorCamp partida) {
        try {
            CharacterScore currentScore = camp.getRootScores();
            camp.setGameStatus(SIN_PARTIDA);
            camp = partida;
            camp.updateScores(currentScore);
            survivorCampoPanel.actualizarMatador(camp.getCharacter());
            survivorCampoPanel.actualizarChombis(camp.getZombieFarNode());
            currentWeapon = camp.getCharacter().getPrincipalWeapon();
            survivorCampoPanel.actualizarEquipada(currentWeapon);
            survivorCampoPanel.actualizarRonda();
            changeCursor();
            menuPanel.setVisible(false);
            survivorCampoPanel.setVisible(true);
            camp.setGameStatus(EN_CURSO);
            add(survivorCampoPanel, BorderLayout.CENTER);
            survivorCampoPanel.requestFocusInWindow();
            facade.initializeEnemyThreads();
            startZombieSounds();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public void gameRestoreData(SurvivorCamp survivorCamp) {
        try {
            setCursor(knifeCursor);
            CharacterScore actual = survivorCamp.getRootScores();
            camp = new SurvivorCampImpl();
            camp.updateScores(actual);
            camp.setGameStatus(EN_CURSO);
            currentWeapon = survivorCamp.getCharacter().getPrincipalWeapon();
            survivorCamp.getCharacter().setBlooded(false);
            survivorCampoPanel.actualizarMatador(survivorCamp.getCharacter());
            survivorCampoPanel.actualizarEquipada(currentWeapon);
            camp.updateRound(survivorCamp.getCurrentRound());
            survivorCampoPanel.actualizarRonda();
            changeCursor();
            survivorCampoPanel.actualizarChombis(camp.getZombieFarNode());
            survivorCampoPanel.incorporarJefe(null);
            add(survivorCampoPanel, BorderLayout.CENTER);
            survivorCampoPanel.requestFocusInWindow();
            facade.initializeEnemyThreads();
            menuPanel.setVisible(false);
            survivorCampoPanel.setVisible(true);
            startZombieSounds();
            survivorCampoPanel.repaint();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public void saveGame() {
        try {
            camp.saveGame();
            JOptionPane.showMessageDialog(this, "Partida Guardada");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public void refresh() {
        survivorCampoPanel.repaint();
    }

    public void startZombieSounds() {
        facade.initializeZombieSoundThread("zombies");
    }

    public void endZombieSounds() {
        facade.soundStop();
    }

    public void generateZombie(int level) {
        Zombie zombie = camp.generateZombie(level);

        if (zombie instanceof WalkerZombie) {
            attackStrategy = new AttackStrategyContext(new WalkerZombieAttackStrategy());
            WalkerZombieAttackStrategy walkerZombieAttackStrategy = new WalkerZombieAttackStrategy();
            walkerZombieAttackStrategy.moveInDirection(zombie);
        }
    }

    public void shootOnCharacter() {
        reproduceSound("meDio");
        attackStrategy = new AttackStrategyContext(new BossZombieAttackStrategy());
        attackStrategy.enemyAttacks(camp);
        survivorCampoPanel.zombieAtaco();
    }

    public void pauseGame() {
        char status = camp.pauseGame();

        if (status == PAUSADO) {
            endZombieSounds();
            menuPanel.setVisible(true);
            survivorCampoPanel.setVisible(false);
            menuPanel.updateUI();
            menuPanel.requestFocusInWindow();
        } else {
            startZombieSounds();
            survivorCampoPanel.setVisible(true);
            menuPanel.setVisible(false);
            survivorCampoPanel.updateUI();
            survivorCampoPanel.requestFocusInWindow();
        }
    }

    public void loadCharacterWeapon() {
        camp.getCharacter().reloadPrincipalWeapon();
        reproduceSound("carga" + currentWeapon.getClass().getSimpleName());
        facade.initializeWeaponsThread("armaDeFuego");
    }

    public void reproduceSound(String ruta) {
        facade.initializeGeneralSoundThread(ruta);
    }

    public void changeWeapon() {
        camp.changeWeapon();
        currentWeapon = camp.getCharacter().getPrincipalWeapon();
        changeCursor();
    }

    public void changeCursor() {
        if (currentWeapon instanceof Remington) {
            setCursor(remingtonCursor);
        } else {
            setCursor(m1911Cursor);
        }
    }

    public void endBloodEffect() {
        currentWeapon.setBlooded(false);
        survivorCampoPanel.quitarSangreZombie();
    }

    public int darRondaActual() {
        return camp.getCurrentRound();
    }

    public void subirDeRonda(int level) {
        endZombieSounds();
        reproduceSound("sirena");
        camp.updateCurrentRound(level);
        camp.setGameStatus(INICIANDO_RONDA);
        survivorCampoPanel.actualizarRonda();
    }

    public void generateBoss() {
        boss = camp.generateBoss();
        survivorCampoPanel.incorporarJefe(boss);
        BossZombieAttackStrategy bossZombieAttackStrategy = new BossZombieAttackStrategy();
        attackStrategy = new AttackStrategyContext(bossZombieAttackStrategy);
        bossZombieAttackStrategy.moveInDirection(boss);
        facade.initializeBossThread();
    }

    public void showHowToPlay() {
        if (menuPanel.isVisible()) {
            menuPanel.setVisible(false);
            howToPlayPanel.setVisible(true);
            add(howToPlayPanel, BorderLayout.CENTER);
        } else {
            howToPlayPanel.setVisible(false);
            menuPanel.setVisible(true);
        }
    }

    public void showScores() {
        if (menuPanel.isVisible()) {
            scoresPanel.actualizarPuntajes(camp.sortScoresByScores());
            menuPanel.setVisible(false);
            scoresPanel.setVisible(true);
            add(scoresPanel, BorderLayout.CENTER);
        } else {
            scoresPanel.setVisible(false);
            menuPanel.setVisible(true);
        }
    }

    public void showCredits() {
        if (menuPanel.isVisible()) {
            menuPanel.setVisible(false);
            creditsPanel.setVisible(true);
            add(creditsPanel, BorderLayout.CENTER);
        } else {
            creditsPanel.setVisible(false);
            menuPanel.setVisible(true);
        }
    }

    public void gameEnded() {
        boolean seLlamoDeNuevo = false;
        int aceptoGuardarScore = JOptionPane.showConfirmDialog(this,
                "Su puntaje fue: " + camp.getCharacter().getScore() + ", con " + camp.getCharacter().getKilling()
                        + " bajas y en la Ronda " + camp.getCurrentRound() + ". Desea guardar su puntaje?",
                "Juego Terminado", JOptionPane.YES_NO_OPTION);
        if (aceptoGuardarScore == JOptionPane.YES_OPTION) {
            String nombrePlayer = JOptionPane.showInputDialog(this, "Escribe tu nombre");

            if (nombrePlayer != null && !nombrePlayer.equals("")) {
                try {
                    camp.verifyName(nombrePlayer);
                    camp.addToBestScores(nombrePlayer);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this,
                            "Error al guardar el puntaje, es posible que haya abierto el juego desde \"Acceso rapido\"");
                } catch (InvalidNameException e) {
                    log.error(e.getMessage(), e);
                    JOptionPane.showMessageDialog(this, e.getMessage());
                    gameEnded();
                }
            } else {
                seLlamoDeNuevo = true;
                gameEnded();
            }
        }

        if(loadCheckpoint()) {
            SurvivorCamp survivorCamp = camp.getMemento();
            System.out.println("memento obtenido, ronda actual: "+survivorCamp.getCurrentRound());
            gameRestoreData(survivorCamp);
        } else if(!seLlamoDeNuevo) {
            int aceptoJugar = JOptionPane.showConfirmDialog(this, "Desea volver a jugar?", "Juego Terminado",
                    JOptionPane.YES_NO_OPTION);
            if (aceptoJugar == JOptionPane.YES_OPTION)
                iniciarNuevaPartida();
            else {
                survivorCampoPanel.setVisible(false);
                menuPanel.setVisible(true);
            }
        }

        endZombieSounds();
    }

    private boolean loadCheckpoint() {
        int playFromLastCheckPoint = JOptionPane.showConfirmDialog(this, "Desea volver a jugar desde el ultimo Checkpoint?", "Juego Terminado",
                JOptionPane.YES_NO_OPTION);
        if (playFromLastCheckPoint == JOptionPane.YES_OPTION) {
            return true;
        }

        return false;
    }

    public void victory() {
        String nombrePlayer = JOptionPane.showInputDialog(this,
                "Enhorabuena, has pasado todas los niveles de dificultad. Su puntaje final es: "
                        + camp.getCharacter().getScore() + ". Escribe tu nombre");

        if (nombrePlayer != null && !nombrePlayer.equals("")) {
            try {
                camp.verifyName(nombrePlayer);
                camp.addToBestScores(nombrePlayer);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this,
                        "Error al guardar el puntaje, es posible que haya abierto el juego desde \"Acceso rapido\"");
            } catch (InvalidNameException e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
                victory();
            }
        } else {
            victory();
        }

        menuPanel.setVisible(true);
        survivorCampoPanel.setVisible(false);
        endZombieSounds();
    }

    public void sortByDeadZombies() {
        scoresPanel.actualizarPuntajes(camp.sortScoresByDeadZombies());
    }

    public void sortByHeadshots() {
        scoresPanel.actualizarPuntajes(camp.sortScoresByHeadShots());
    }

    public void searchByName(String nombreBuscado) {
        if (nombreBuscado != null) {
            CharacterScore buscado = camp.searchByScoreOfPlayerName(nombreBuscado);
            scoresPanel.mostrarPuntajeDe(buscado);
        }
    }

    public void sortByScore() {
        scoresPanel.actualizarPuntajes(camp.sortScoresByScores());
    }

}
