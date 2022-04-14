package threads;

import interfaz.InterfazZombieKiller;
import mundo.attackStrategies.AttackStrategyContext;
import mundo.attackStrategies.CaminanteAttackStrategy;
import mundo.attackStrategies.RastreroAttackStrategy;
import mundo.camp.SurvivorCamp;
import mundo.zombies.Caminante;
import mundo.zombies.Rastrero;
import mundo.zombies.Zombie;

import static mundo.constants.CampConstants.PAUSADO;
import static mundo.constants.CampConstants.SIN_PARTIDA;
import static mundo.constants.ZombiesConstants.ATACANDO;
import static mundo.constants.ZombiesConstants.MURIENDO;
import static mundo.constants.ZombiesConstants.MURIENDO_INCENDIADO;
import static mundo.constants.ZombiesConstants.NODO;

public class HiloEnemigo extends Thread {

    private final InterfazZombieKiller principal;
    private final Zombie nodoCercano;
    private final SurvivorCamp campo;

    public HiloEnemigo(InterfazZombieKiller principal, Zombie nodoCercano, SurvivorCamp campo) {
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