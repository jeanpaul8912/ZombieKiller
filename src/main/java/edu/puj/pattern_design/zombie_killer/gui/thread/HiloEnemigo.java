package edu.puj.pattern_design.zombie_killer.gui.thread;

import edu.puj.pattern_design.zombie_killer.gui.ZombieKillerGUI;
import edu.puj.pattern_design.zombie_killer.service.attack_strategies.AttackStrategyContext;
import edu.puj.pattern_design.zombie_killer.service.attack_strategies.CaminanteAttackStrategy;
import edu.puj.pattern_design.zombie_killer.service.attack_strategies.RastreroAttackStrategy;
import edu.puj.pattern_design.zombie_killer.service.camp.SurvivorCamp;
import edu.puj.pattern_design.zombie_killer.service.zombies.Caminante;
import edu.puj.pattern_design.zombie_killer.service.zombies.Rastrero;
import edu.puj.pattern_design.zombie_killer.service.zombies.Zombie;

import static edu.puj.pattern_design.zombie_killer.service.constants.CampConstants.PAUSADO;
import static edu.puj.pattern_design.zombie_killer.service.constants.CampConstants.SIN_PARTIDA;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.ATACANDO;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.MURIENDO;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.MURIENDO_INCENDIADO;
import static edu.puj.pattern_design.zombie_killer.service.constants.ZombiesConstants.NODO;

public class HiloEnemigo extends Thread {

    private final ZombieKillerGUI principal;
    private final Zombie nodoCercano;
    private final SurvivorCamp campo;

    public HiloEnemigo(ZombieKillerGUI principal, Zombie nodoCercano, SurvivorCamp campo) {
        this.principal = principal;
        this.nodoCercano = nodoCercano;
        this.campo = campo;
    }

    @Override
    public void run() {
        try {
            while (campo.getEstadoJuego() != SIN_PARTIDA) {
                Zombie enMovimiento = nodoCercano.getAtras();
                AttackStrategyContext attackStrategy = null;

                while (!enMovimiento.getEstadoActual().equals(NODO)) {
                    if (enMovimiento instanceof Caminante) {
                        attackStrategy = new AttackStrategyContext(new CaminanteAttackStrategy());
                    } else if (enMovimiento instanceof Rastrero) {
                        attackStrategy = new AttackStrategyContext(new RastreroAttackStrategy());
                    }

                    attackStrategy.executeAttack(enMovimiento);

                    String estado = enMovimiento.getEstadoActual();
                    if (estado.equals(ATACANDO)) {
                        if (enMovimiento instanceof Caminante) {
                            if (enMovimiento.getFrameActual() == 8) {
                                principal.leDaAPersonaje();
                            } else if (enMovimiento.getFrameActual() == 13) {
                                attackStrategy.enemigoTerminaSuGolpe(campo);
                            }
                        } else if (enMovimiento instanceof Rastrero) {
                            if (enMovimiento.getFrameActual() == 13) {
                                principal.leDaAPersonaje();
                            } else if (enMovimiento.getFrameActual() == 16) {
                                attackStrategy.enemigoTerminaSuGolpe(campo);
                            }
                        }
                    } else if (estado.equals(MURIENDO) || estado.equals(MURIENDO_INCENDIADO)) {
                        if (enMovimiento instanceof Caminante) {
                            if (enMovimiento.getFrameActual() == 17) {
                                if (estado.equals(MURIENDO)) {
                                    enMovimiento.eliminarse();
                                } else {
                                    nodoCercano.setAtras(campo.getZombNodoLejano());
                                    campo.getZombNodoLejano().setAlFrente(nodoCercano);
                                    enMovimiento = nodoCercano;
                                }
                            }
                        } else if (enMovimiento instanceof Rastrero) {
                            if (enMovimiento.getFrameActual() == 11) {
                                if (estado.equals(MURIENDO)) {
                                    enMovimiento.eliminarse();
                                } else {
                                    nodoCercano.setAtras(campo.getZombNodoLejano());
                                    campo.getZombNodoLejano().setAlFrente(nodoCercano);
                                    enMovimiento = nodoCercano;
                                }
                            }
                        }
                    }

                    enMovimiento = enMovimiento.getAtras();
                }

                while (campo.getEstadoJuego() == PAUSADO) {
                    sleep(500);
                }

                sleep(nodoCercano.getAtras().getLentitud());
                principal.refrescar();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}