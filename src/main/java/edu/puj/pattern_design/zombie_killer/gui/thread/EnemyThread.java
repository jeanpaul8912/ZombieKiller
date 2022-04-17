package edu.puj.pattern_design.zombie_killer.gui.thread;

import edu.puj.pattern_design.zombie_killer.gui.ZombieKillerGUI;
import edu.puj.pattern_design.zombie_killer.service.attack_strategies.AttackStrategyContext;
import edu.puj.pattern_design.zombie_killer.service.attack_strategies.WalkerZombieAttackStrategy;
import edu.puj.pattern_design.zombie_killer.service.attack_strategies.DragZombieAttackStrategy;
import edu.puj.pattern_design.zombie_killer.service.camp.SurvivorCamp;
import edu.puj.pattern_design.zombie_killer.service.zombies.WalkerZombie;
import edu.puj.pattern_design.zombie_killer.service.zombies.DragZombie;
import edu.puj.pattern_design.zombie_killer.service.zombies.Zombie;

import static edu.puj.pattern_design.zombie_killer.service.constants.CampConstants.PAUSADO;
import static edu.puj.pattern_design.zombie_killer.service.constants.CampConstants.SIN_PARTIDA;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.ATACANDO;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.MURIENDO;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.MURIENDO_INCENDIADO;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.NODO;

public class EnemyThread extends Thread {

    private final ZombieKillerGUI principal;
    private final Zombie nodoCercano;
    private final SurvivorCamp campo;

    public EnemyThread(ZombieKillerGUI principal, Zombie nodoCercano, SurvivorCamp campo) {
        this.principal = principal;
        this.nodoCercano = nodoCercano;
        this.campo = campo;
    }

    @Override
    public void run() {
        try {
            while (campo.getGameStatus() != SIN_PARTIDA) {
                Zombie enMovimiento = nodoCercano.getInBack();
                AttackStrategyContext attackStrategy = null;

                while (!enMovimiento.getCurrentStatus().equals(NODO)) {
                    if (enMovimiento instanceof WalkerZombie) {
                        attackStrategy = new AttackStrategyContext(new WalkerZombieAttackStrategy());
                    } else if (enMovimiento instanceof DragZombie) {
                        attackStrategy = new AttackStrategyContext(new DragZombieAttackStrategy());
                    }

                    attackStrategy.executeAttack(enMovimiento);

                    String estado = enMovimiento.getCurrentStatus();
                    if (estado.equals(ATACANDO)) {
                        if (enMovimiento instanceof WalkerZombie) {
                            if (enMovimiento.getCurrentFrame() == 8) {
                                principal.leDaAPersonaje();
                            } else if (enMovimiento.getCurrentFrame() == 13) {
                                attackStrategy.enemyFinishAttack(campo);
                            }
                        } else if (enMovimiento instanceof DragZombie) {
                            if (enMovimiento.getCurrentFrame() == 13) {
                                principal.leDaAPersonaje();
                            } else if (enMovimiento.getCurrentFrame() == 16) {
                                attackStrategy.enemyFinishAttack(campo);
                            }
                        }
                    } else if (estado.equals(MURIENDO) || estado.equals(MURIENDO_INCENDIADO)) {
                        if (enMovimiento instanceof WalkerZombie) {
                            if (enMovimiento.getCurrentFrame() == 17) {
                                if (estado.equals(MURIENDO)) {
                                    enMovimiento.eliminate();
                                } else {
                                    nodoCercano.setInBack(campo.getZombieFarNode());
                                    campo.getZombieFarNode().setInFront(nodoCercano);
                                    enMovimiento = nodoCercano;
                                }
                            }
                        } else if (enMovimiento instanceof DragZombie) {
                            if (enMovimiento.getCurrentFrame() == 11) {
                                if (estado.equals(MURIENDO)) {
                                    enMovimiento.eliminate();
                                } else {
                                    nodoCercano.setInBack(campo.getZombieFarNode());
                                    campo.getZombieFarNode().setInFront(nodoCercano);
                                    enMovimiento = nodoCercano;
                                }
                            }
                        }
                    }

                    enMovimiento = enMovimiento.getInBack();
                }

                while (campo.getGameStatus() == PAUSADO) {
                    sleep(500);
                }

                sleep(nodoCercano.getInBack().getSpeed());
                principal.refrescar();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}