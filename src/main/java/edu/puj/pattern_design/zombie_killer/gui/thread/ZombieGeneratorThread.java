package edu.puj.pattern_design.zombie_killer.gui.thread;

import edu.puj.pattern_design.zombie_killer.gui.ZombieKillerGUI;
import edu.puj.pattern_design.zombie_killer.service.camp.SurvivorCamp;
import org.apache.commons.lang3.math.NumberUtils;

import static edu.puj.pattern_design.zombie_killer.service.constants.SurvivorCampConstants.EN_CURSO;
import static edu.puj.pattern_design.zombie_killer.service.constants.SurvivorCampConstants.PAUSADO;
import static edu.puj.pattern_design.zombie_killer.service.constants.SurvivorCampConstants.SIN_PARTIDA;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.MURIENDO_INCENDIADO;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.NODO;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.NUMERO_ZOMBIES_RONDA;

public class ZombieGeneratorThread extends Thread {

    private final ZombieKillerGUI principal;
    private final SurvivorCamp campo;

    public ZombieGeneratorThread(ZombieKillerGUI principal, SurvivorCamp campo) {
        this.principal = principal;
        this.campo = campo;
    }

    @Override
    public void run() {
        try {
            while (principal.estaCargando()) {
                sleep(5000);
            }

            principal.changeCursor();
            int contadorZombiesPorNivel = campo.getZombiesGeneratedCount();
            int nivel = campo.getCurrentRound();

            while (campo.getGameStatus() != SIN_PARTIDA) {
                if (contadorZombiesPorNivel % NUMERO_ZOMBIES_RONDA == NumberUtils.INTEGER_ZERO) { // Si se terminan los Zombies
                    while (!campo.getZombieFarNode().getInFront().getCurrentStatus().equals(NODO) && campo.getCharacter().getHealth() > 0) {
                        sleep(1000);
                    }

                    // Si aun existe partida , subir nivel e iniciar
                    if (campo.getGameStatus() != SIN_PARTIDA) {
                        while (campo.getGameStatus() == PAUSADO) {
                            sleep(500);
                        }

                        nivel++;
                        principal.subirDeRonda(nivel);
                        sleep(2000);
                        principal.startZombieSounds();
                        campo.setGameStatus(EN_CURSO);
                    }
                }

                if (nivel < 10 && campo.getGameStatus() != SIN_PARTIDA) {
                    if (!campo.getZombieFarNode().getInFront().getCurrentStatus().equals(MURIENDO_INCENDIADO))
                        principal.generateZombie(nivel);
                    contadorZombiesPorNivel++;
                    sleep(1400);
                } else if (nivel == 10) {
                    principal.generateBoss();

                    while (campo.getGameStatus() != SIN_PARTIDA) {
                        sleep(500);
                    }
                }

                while (campo.getGameStatus() == PAUSADO) {
                    sleep(500);
                }
            }

            if (campo.getCharacter().getHealth() <= 0) {
                principal.reproduceSound("meMuero");
                principal.gameEnded();
            } else if (campo.getBoss() != null && campo.getBoss().getHealth() <= 0) {
                principal.victory();
            }
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }
}
